package com.taptap.services.update.download.core.download;

import com.taptap.services.update.download.core.Util;
import com.taptap.services.update.download.core.cause.ResumeFailedCause;
import com.taptap.services.update.download.core.exception.FileBusyAfterRunException;
import com.taptap.services.update.download.core.exception.InterruptException;
import com.taptap.services.update.download.core.exception.PreAllocateException;
import com.taptap.services.update.download.core.exception.ResumeFailedException;
import com.taptap.services.update.download.core.exception.ServerCanceledException;
import com.taptap.services.update.download.core.file.MultiPointOutputStream;
import java.io.IOException;
import java.net.SocketException;

/* JADX INFO: loaded from: classes.dex */
public class DownloadCache {
    private volatile boolean fileBusyAfterRun;
    private final MultiPointOutputStream outputStream;
    private volatile boolean preAllocateFailed;
    private volatile boolean preconditionFailed;
    private volatile IOException realCause;
    private String redirectLocation;
    private volatile boolean serverCanceled;
    private volatile boolean unknownError;
    private volatile boolean userCanceled;

    DownloadCache(MultiPointOutputStream multiPointOutputStream) {
        this.outputStream = multiPointOutputStream;
    }

    private DownloadCache() {
        this.outputStream = null;
    }

    MultiPointOutputStream getOutputStream() {
        MultiPointOutputStream multiPointOutputStream = this.outputStream;
        if (multiPointOutputStream != null) {
            return multiPointOutputStream;
        }
        throw new IllegalArgumentException();
    }

    void setRedirectLocation(String str) {
        this.redirectLocation = str;
    }

    String getRedirectLocation() {
        return this.redirectLocation;
    }

    boolean isPreconditionFailed() {
        return this.preconditionFailed;
    }

    public boolean isUserCanceled() {
        return this.userCanceled;
    }

    boolean isServerCanceled() {
        return this.serverCanceled;
    }

    boolean isUnknownError() {
        return this.unknownError;
    }

    boolean isFileBusyAfterRun() {
        return this.fileBusyAfterRun;
    }

    public boolean isPreAllocateFailed() {
        return this.preAllocateFailed;
    }

    IOException getRealCause() {
        return this.realCause;
    }

    ResumeFailedCause getResumeFailedCause() {
        return ((ResumeFailedException) this.realCause).getResumeFailedCause();
    }

    public boolean isInterrupt() {
        return this.preconditionFailed || this.userCanceled || this.serverCanceled || this.unknownError || this.fileBusyAfterRun || this.preAllocateFailed;
    }

    public void setPreconditionFailed(IOException iOException) {
        this.preconditionFailed = true;
        this.realCause = iOException;
    }

    void setUserCanceled() {
        this.userCanceled = true;
    }

    public void setFileBusyAfterRun() {
        this.fileBusyAfterRun = true;
    }

    public void setServerCanceled(IOException iOException) {
        this.serverCanceled = true;
        this.realCause = iOException;
    }

    public void setUnknownError(IOException iOException) {
        this.unknownError = true;
        this.realCause = iOException;
    }

    public void setPreAllocateFailed(IOException iOException) {
        this.preAllocateFailed = true;
        this.realCause = iOException;
    }

    public void catchException(IOException iOException) {
        if (isUserCanceled()) {
            return;
        }
        if (iOException instanceof ResumeFailedException) {
            setPreconditionFailed(iOException);
            return;
        }
        if (iOException instanceof ServerCanceledException) {
            setServerCanceled(iOException);
            return;
        }
        if (iOException == FileBusyAfterRunException.SIGNAL) {
            setFileBusyAfterRun();
            return;
        }
        if (iOException instanceof PreAllocateException) {
            setPreAllocateFailed(iOException);
            return;
        }
        if (iOException != InterruptException.SIGNAL) {
            setUnknownError(iOException);
            if (iOException instanceof SocketException) {
                return;
            }
            Util.d("DownloadCache", "catch unknown error " + iOException);
        }
    }

    static class PreError extends DownloadCache {
        PreError(IOException iOException) {
            super(null);
            setUnknownError(iOException);
        }
    }
}
