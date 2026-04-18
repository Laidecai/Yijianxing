package com.taptap.services.update.download.core.download;

import com.taptap.services.update.download.DownloadListener;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.OkDownload;
import com.taptap.services.update.download.core.Util;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.connection.DownloadConnection;
import com.taptap.services.update.download.core.exception.DownloadSecurityException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class ConnectTrial {
    private static final String TAG = "ConnectTrial";
    private boolean acceptRange;
    private final BreakpointInfo info;
    private long instanceLength;
    private int responseCode;
    private String responseEtag;
    private String responseFilename;
    private final DownloadTask task;
    private static final Pattern CONTENT_DISPOSITION_QUOTED_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*\"([^\"]*)\"");
    private static final Pattern CONTENT_DISPOSITION_NON_QUOTED_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*(.*)");

    public ConnectTrial(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
        this.task = downloadTask;
        this.info = breakpointInfo;
    }

    public void executeTrial() throws IOException {
        OkDownload.with().downloadStrategy().inspectNetworkOnWifi(this.task);
        OkDownload.with().downloadStrategy().inspectNetworkAvailable();
        DownloadConnection downloadConnectionCreate = OkDownload.with().connectionFactory().create(this.task.getUrl());
        try {
            if (!Util.isEmpty(this.info.getEtag())) {
                downloadConnectionCreate.addHeader(Util.IF_MATCH, this.info.getEtag());
            }
            downloadConnectionCreate.addHeader(Util.RANGE, "bytes=0-0");
            Map<String, List<String>> headerMapFields = this.task.getHeaderMapFields();
            if (headerMapFields != null) {
                Util.addUserRequestHeaderField(headerMapFields, downloadConnectionCreate);
            }
            DownloadListener downloadListenerDispatch = OkDownload.with().callbackDispatcher().dispatch();
            downloadListenerDispatch.connectTrialStart(this.task, downloadConnectionCreate.getRequestProperties());
            DownloadConnection.Connected connectedExecute = downloadConnectionCreate.execute();
            this.task.setRedirectLocation(connectedExecute.getRedirectLocation());
            Util.d(TAG, "task[" + this.task.getId() + "] redirect location: " + this.task.getRedirectLocation());
            this.responseCode = connectedExecute.getResponseCode();
            this.acceptRange = isAcceptRange(connectedExecute);
            this.instanceLength = findInstanceLength(connectedExecute);
            this.responseEtag = findEtag(connectedExecute);
            this.responseFilename = findFilename(connectedExecute);
            Map<String, List<String>> responseHeaderFields = connectedExecute.getResponseHeaderFields();
            if (responseHeaderFields == null) {
                responseHeaderFields = new HashMap<>();
            }
            downloadListenerDispatch.connectTrialEnd(this.task, this.responseCode, responseHeaderFields);
            if (isNeedTrialHeadMethodForInstanceLength(this.instanceLength, connectedExecute)) {
                trialHeadMethodForInstanceLength();
            }
        } finally {
            downloadConnectionCreate.release();
        }
    }

    public long getInstanceLength() {
        return this.instanceLength;
    }

    public boolean isAcceptRange() {
        return this.acceptRange;
    }

    public boolean isChunked() {
        return this.instanceLength == -1;
    }

    public String getResponseEtag() {
        return this.responseEtag;
    }

    public String getResponseFilename() {
        return this.responseFilename;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public boolean isEtagOverdue() {
        return (this.info.getEtag() == null || this.info.getEtag().equals(this.responseEtag)) ? false : true;
    }

    private static boolean isAcceptRange(DownloadConnection.Connected connected) throws IOException {
        if (connected.getResponseCode() == 206) {
            return true;
        }
        return "bytes".equals(connected.getResponseHeaderField(Util.ACCEPT_RANGES));
    }

    private static String findFilename(DownloadConnection.Connected connected) throws IOException {
        return parseContentDisposition(connected.getResponseHeaderField(Util.CONTENT_DISPOSITION));
    }

    private static String parseContentDisposition(String str) throws IOException {
        String strGroup;
        if (str == null) {
            return null;
        }
        try {
            Matcher matcher = CONTENT_DISPOSITION_QUOTED_PATTERN.matcher(str);
            if (matcher.find()) {
                strGroup = matcher.group(1);
            } else {
                Matcher matcher2 = CONTENT_DISPOSITION_NON_QUOTED_PATTERN.matcher(str);
                strGroup = matcher2.find() ? matcher2.group(1) : null;
            }
            if (strGroup != null && strGroup.contains("../")) {
                throw new DownloadSecurityException("The filename [" + strGroup + "] from the response is not allowable, because it contains '../', which can raise the directory traversal vulnerability");
            }
            return strGroup;
        } catch (IllegalStateException unused) {
            return null;
        }
    }

    private static String findEtag(DownloadConnection.Connected connected) {
        return connected.getResponseHeaderField(Util.ETAG);
    }

    private static long findInstanceLength(DownloadConnection.Connected connected) {
        long contentRangeFoInstanceLength = parseContentRangeFoInstanceLength(connected.getResponseHeaderField(Util.CONTENT_RANGE));
        if (contentRangeFoInstanceLength != -1) {
            return contentRangeFoInstanceLength;
        }
        if (!parseTransferEncoding(connected.getResponseHeaderField(Util.TRANSFER_ENCODING))) {
            Util.w(TAG, "Transfer-Encoding isn't chunked but there is no valid instance length found either!");
        }
        return -1L;
    }

    boolean isNeedTrialHeadMethodForInstanceLength(long j, DownloadConnection.Connected connected) {
        String responseHeaderField;
        if (j != -1) {
            return false;
        }
        String responseHeaderField2 = connected.getResponseHeaderField(Util.CONTENT_RANGE);
        return (responseHeaderField2 == null || responseHeaderField2.length() <= 0) && !parseTransferEncoding(connected.getResponseHeaderField(Util.TRANSFER_ENCODING)) && (responseHeaderField = connected.getResponseHeaderField("Content-Length")) != null && responseHeaderField.length() > 0;
    }

    void trialHeadMethodForInstanceLength() throws IOException {
        DownloadConnection downloadConnectionCreate = OkDownload.with().connectionFactory().create(this.task.getUrl());
        DownloadListener downloadListenerDispatch = OkDownload.with().callbackDispatcher().dispatch();
        try {
            downloadConnectionCreate.setRequestMethod("HEAD");
            Map<String, List<String>> headerMapFields = this.task.getHeaderMapFields();
            if (headerMapFields != null) {
                Util.addUserRequestHeaderField(headerMapFields, downloadConnectionCreate);
            }
            downloadListenerDispatch.connectTrialStart(this.task, downloadConnectionCreate.getRequestProperties());
            DownloadConnection.Connected connectedExecute = downloadConnectionCreate.execute();
            downloadListenerDispatch.connectTrialEnd(this.task, connectedExecute.getResponseCode(), connectedExecute.getResponseHeaderFields());
            this.instanceLength = Util.parseContentLength(connectedExecute.getResponseHeaderField("Content-Length"));
        } finally {
            downloadConnectionCreate.release();
        }
    }

    private static boolean parseTransferEncoding(String str) {
        return str != null && str.equals("chunked");
    }

    private static long parseContentRangeFoInstanceLength(String str) {
        if (str == null) {
            return -1L;
        }
        String[] strArrSplit = str.split("/");
        if (strArrSplit.length >= 2) {
            try {
                return Long.parseLong(strArrSplit[1]);
            } catch (NumberFormatException unused) {
                Util.w(TAG, "parse instance length failed with " + str);
            }
        }
        return -1L;
    }
}
