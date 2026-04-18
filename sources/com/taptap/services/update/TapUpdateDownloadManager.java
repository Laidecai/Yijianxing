package com.taptap.services.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.OkDownload;
import com.taptap.services.update.download.SpeedCalculator;
import com.taptap.services.update.download.core.breakpoint.BlockInfo;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.listener.DownloadListener4WithSpeed;
import com.taptap.services.update.download.core.listener.assist.Listener4SpeedAssistExtend;
import com.tds.common.net.constant.Constants;
import com.tds.common.tracker.annotations.Login;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateDownloadManager {
    private DownloadTask currentDownloadTask;
    private String defaultSaveRootPath;
    private boolean prepared;

    public interface TapUpdateDownloadListener {
        void onDownloadFailed(EndCause endCause, Exception exc);

        void onDownloadProgress(long j, long j2, long j3, int i, String str);

        void onDownloadStart(long j);

        void onDownloadSuccess(File file);
    }

    private static class Holder {
        private static final TapUpdateDownloadManager INSTANCE = new TapUpdateDownloadManager();

        private Holder() {
        }
    }

    public static TapUpdateDownloadManager getInstance() {
        return Holder.INSTANCE;
    }

    private TapUpdateDownloadManager() {
        this.prepared = false;
    }

    public void prepareDownload(Context context) {
        if (this.prepared) {
            return;
        }
        try {
            OkDownload.setSingletonInstance(new OkDownload.Builder(context).downloadStrategy(new TapUpdateDownloadStrategy()).build());
        } catch (Exception unused) {
        }
        this.prepared = true;
    }

    public void download(Context context, String str, String str2, final TapUpdateDownloadListener tapUpdateDownloadListener) {
        prepareDownload(context);
        if (this.currentDownloadTask == null) {
            HashMap map = new HashMap();
            ArrayList arrayList = new ArrayList();
            arrayList.add(TapUpdateAPI.makeCommonHeaders(context).getXUAValue());
            map.put(Constants.HTTP_COMMON_HEADERS.XUA, arrayList);
            DownloadTask downloadTaskBuild = new DownloadTask.Builder(str, getDefaultSaveRootPath(context), str2).setMinIntervalMillisCallbackProcess(1000).setHeaderMapFields(map).build();
            downloadTaskBuild.setTag(str);
            this.currentDownloadTask = downloadTaskBuild;
            downloadTaskBuild.enqueue(new DownloadListener4WithSpeed() { // from class: com.taptap.services.update.TapUpdateDownloadManager.1
                @Override // com.taptap.services.update.download.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
                public void progressBlock(DownloadTask downloadTask, int i, long j, SpeedCalculator speedCalculator) {
                }

                @Override // com.taptap.services.update.download.DownloadListener
                public void taskStart(DownloadTask downloadTask) {
                    TapUpdateLogger.d(downloadTask.toString() + " start");
                }

                @Override // com.taptap.services.update.download.DownloadListener
                public void connectStart(DownloadTask downloadTask, int i, Map<String, List<String>> map2) {
                    TapUpdateLogger.d(downloadTask.toString() + " connect start");
                }

                @Override // com.taptap.services.update.download.DownloadListener
                public void connectEnd(DownloadTask downloadTask, int i, int i2, Map<String, List<String>> map2) {
                    TapUpdateLogger.d(downloadTask.toString() + " connect end");
                }

                @Override // com.taptap.services.update.download.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
                public void infoReady(DownloadTask downloadTask, BreakpointInfo breakpointInfo, boolean z, Listener4SpeedAssistExtend.Listener4SpeedModel listener4SpeedModel) {
                    TapUpdateLogger.d(downloadTask.toString() + " info ready");
                    TapUpdateDownloadListener tapUpdateDownloadListener2 = tapUpdateDownloadListener;
                    if (tapUpdateDownloadListener2 != null) {
                        tapUpdateDownloadListener2.onDownloadStart(breakpointInfo.getTotalLength());
                    }
                }

                @Override // com.taptap.services.update.download.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
                public void progress(DownloadTask downloadTask, long j, SpeedCalculator speedCalculator) {
                    long totalLength;
                    TapUpdateDownloadListener tapUpdateDownloadListener2;
                    TapUpdateLogger.d(downloadTask.toString() + " progress " + j);
                    if (downloadTask.getInfo() != null) {
                        totalLength = downloadTask.getInfo().getTotalLength();
                        TapUpdateLogger.d("APKDownload PROGRESS fileTotalLength = " + totalLength + " currentOffset = " + j);
                    } else {
                        totalLength = 0;
                    }
                    if (totalLength == 0 || (tapUpdateDownloadListener2 = tapUpdateDownloadListener) == null) {
                        return;
                    }
                    tapUpdateDownloadListener2.onDownloadProgress(j, totalLength, speedCalculator.getBytesPerSecondAndFlush(), (int) ((100 * j) / totalLength), speedCalculator.speed());
                }

                @Override // com.taptap.services.update.download.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
                public void blockEnd(DownloadTask downloadTask, int i, BlockInfo blockInfo, SpeedCalculator speedCalculator) {
                    TapUpdateLogger.d(downloadTask.toString() + " block end ");
                }

                @Override // com.taptap.services.update.download.core.listener.assist.Listener4SpeedAssistExtend.Listener4SpeedCallback
                public void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc, SpeedCalculator speedCalculator) {
                    TapUpdateLogger.d(downloadTask.toString() + " task end " + endCause.name());
                    if (endCause == EndCause.COMPLETED) {
                        TapUpdateDownloadListener tapUpdateDownloadListener2 = tapUpdateDownloadListener;
                        if (tapUpdateDownloadListener2 != null) {
                            tapUpdateDownloadListener2.onDownloadSuccess(downloadTask.getFile());
                            return;
                        }
                        return;
                    }
                    TapUpdateDownloadListener tapUpdateDownloadListener3 = tapUpdateDownloadListener;
                    if (tapUpdateDownloadListener3 != null) {
                        tapUpdateDownloadListener3.onDownloadFailed(endCause, exc);
                    }
                }
            });
            return;
        }
        restartTask();
    }

    public void restartTask() {
        if (this.currentDownloadTask != null) {
            OkDownload.with().downloadDispatcher().enqueue(this.currentDownloadTask);
        }
    }

    public void cancelDownload() {
        this.currentDownloadTask = null;
        OkDownload.with().downloadDispatcher().cancelAll();
    }

    public void installAPK(Context context, File file, boolean z) {
        Uri uriFromFile;
        TapUpdateTracker.getInstance().trackInstall(z);
        try {
            TapUpdateLogger.d("installAPK" + file);
            Intent intent = new Intent("android.intent.action.VIEW");
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setFlags(268435457);
                uriFromFile = FileProvider.getUriForFile(context, context.getPackageName() + ".com.taptap.services.update.fileProvider", file);
            } else {
                intent.setFlags(268435456);
                uriFromFile = Uri.fromFile(file);
            }
            intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            TapUpdateLogger.e("installAPK failed " + e.getMessage());
        }
    }

    public void clearCache(Context context) {
        try {
            File file = new File(getDefaultSaveRootPath(context));
            if (file.exists()) {
                TapUpdateLogger.d("clearCache " + file.getAbsolutePath());
                TapUpdateLogger.d("clearCache result " + deleteFiles(file));
            }
        } catch (Exception e) {
            TapUpdateLogger.d("clearCache failed" + e);
        }
    }

    private boolean deleteFiles(File file) {
        if (file == null) {
            return true;
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                return true;
            }
            for (File file2 : fileArrListFiles) {
                deleteFiles(file2);
            }
        }
        return file.delete();
    }

    private String getDefaultSaveRootPath(Context context) {
        if (TextUtils.isEmpty(this.defaultSaveRootPath)) {
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir == null) {
                externalCacheDir = context.getExternalFilesDir(null);
            }
            this.defaultSaveRootPath = externalCacheDir.getAbsolutePath();
            this.defaultSaveRootPath += File.separator + Login.TAPTAP_LOGIN_TYPE + File.separator + "services" + File.separator + "update";
        }
        return this.defaultSaveRootPath;
    }
}
