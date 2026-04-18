package com.taptap.services.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;
import com.taptap.services.update.TapUpdateDialog;
import com.taptap.services.update.TapUpdateDownloadManager;
import com.taptap.services.update.TapUpdateInstallConfirmDialog;
import com.taptap.services.update.bean.TapUpdateInfo;
import com.taptap.services.update.bean.UIInformation;
import com.taptap.services.update.download.core.Util;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.exception.ServerCanceledException;
import com.taptap.services.update.widget.LoadingDialog;
import com.tds.common.reactor.functions.Action1;
import com.tds.common.reactor.rxandroid.schedulers.AndroidSchedulers;
import com.tds.common.reactor.schedulers.Schedulers;
import com.tds.common.utils.NetworkUtil;
import com.tds.common.utils.TapGameUtil;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateDialogManager {
    private TapUpdateDialog mDialog;
    private TapUpdateInstallConfirmDialog mInstallConfirmDialog;
    private LoadingDialog mLoadingDialog;
    private TapUpdateCallback mUpdateCallback;
    private TapUpdateInfo mUpdateInfo;
    private int retryCount;

    /* synthetic */ TapUpdateDialogManager(AnonymousClass1 anonymousClass1) {
        this();
    }

    static /* synthetic */ int access$508(TapUpdateDialogManager tapUpdateDialogManager) {
        int i = tapUpdateDialogManager.retryCount;
        tapUpdateDialogManager.retryCount = i + 1;
        return i;
    }

    private static class Holder {
        private static final TapUpdateDialogManager INSTANCE = new TapUpdateDialogManager();

        private Holder() {
        }
    }

    public static TapUpdateDialogManager getInstance() {
        return Holder.INSTANCE;
    }

    private TapUpdateDialogManager() {
        this.retryCount = 0;
    }

    public TapUpdateDialog getDialog(Context context) {
        if (this.mDialog == null) {
            TapUpdateDialog tapUpdateDialog = new TapUpdateDialog(context);
            this.mDialog = tapUpdateDialog;
            tapUpdateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.1
                AnonymousClass1() {
                }

                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    TapUpdateDialogManager.getInstance().mDialog = null;
                    TapUpdateDialogManager.getInstance().mUpdateInfo = null;
                    TapUpdateDialogManager.getInstance().mUpdateCallback = null;
                    TapUpdateDialogManager.getInstance().retryCount = 0;
                }
            });
            this.mDialog.setUpdateCancelListener(new TapUpdateDialog.IUpdateCancelListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.2
                AnonymousClass2() {
                }

                @Override // com.taptap.services.update.TapUpdateDialog.IUpdateCancelListener
                public void onUpdateCancel(TapUpdateDialog.TapUpdateDialogState tapUpdateDialogState, boolean z) {
                    int i = AnonymousClass12.$SwitchMap$com$taptap$services$update$TapUpdateDialog$TapUpdateDialogState[tapUpdateDialogState.ordinal()];
                    if (i == 1) {
                        TapUpdateTracker.getInstance().trackUpdateCancelButtonClick();
                    } else if (i == 2) {
                        TapUpdateTracker.getInstance().trackUpdateCloseButtonClick();
                    } else if (i == 3) {
                        TapUpdateTracker.getInstance().trackInstallCloseButtonClick();
                    } else if (i == 4) {
                        TapUpdateTracker.getInstance().trackToTapUpdateCloseButtonClick();
                    }
                    TapUpdateDownloadManager.getInstance().cancelDownload();
                    if (TapUpdateDialogManager.this.mUpdateCallback != null) {
                        TapUpdateDialogManager.this.mUpdateCallback.onCancel();
                    }
                    TapUpdateDialogManager.this.dismissDialog();
                }
            });
        }
        return this.mDialog;
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$1 */
    class AnonymousClass1 implements DialogInterface.OnDismissListener {
        AnonymousClass1() {
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            TapUpdateDialogManager.getInstance().mDialog = null;
            TapUpdateDialogManager.getInstance().mUpdateInfo = null;
            TapUpdateDialogManager.getInstance().mUpdateCallback = null;
            TapUpdateDialogManager.getInstance().retryCount = 0;
        }
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$2 */
    class AnonymousClass2 implements TapUpdateDialog.IUpdateCancelListener {
        AnonymousClass2() {
        }

        @Override // com.taptap.services.update.TapUpdateDialog.IUpdateCancelListener
        public void onUpdateCancel(TapUpdateDialog.TapUpdateDialogState tapUpdateDialogState, boolean z) {
            int i = AnonymousClass12.$SwitchMap$com$taptap$services$update$TapUpdateDialog$TapUpdateDialogState[tapUpdateDialogState.ordinal()];
            if (i == 1) {
                TapUpdateTracker.getInstance().trackUpdateCancelButtonClick();
            } else if (i == 2) {
                TapUpdateTracker.getInstance().trackUpdateCloseButtonClick();
            } else if (i == 3) {
                TapUpdateTracker.getInstance().trackInstallCloseButtonClick();
            } else if (i == 4) {
                TapUpdateTracker.getInstance().trackToTapUpdateCloseButtonClick();
            }
            TapUpdateDownloadManager.getInstance().cancelDownload();
            if (TapUpdateDialogManager.this.mUpdateCallback != null) {
                TapUpdateDialogManager.this.mUpdateCallback.onCancel();
            }
            TapUpdateDialogManager.this.dismissDialog();
        }
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$12 */
    static /* synthetic */ class AnonymousClass12 {
        static final /* synthetic */ int[] $SwitchMap$com$taptap$services$update$TapUpdateDialog$TapUpdateDialogState;

        static {
            int[] iArr = new int[TapUpdateDialog.TapUpdateDialogState.values().length];
            $SwitchMap$com$taptap$services$update$TapUpdateDialog$TapUpdateDialogState = iArr;
            try {
                iArr[TapUpdateDialog.TapUpdateDialogState.CONFIRM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$taptap$services$update$TapUpdateDialog$TapUpdateDialogState[TapUpdateDialog.TapUpdateDialogState.DOWNLOAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$taptap$services$update$TapUpdateDialog$TapUpdateDialogState[TapUpdateDialog.TapUpdateDialogState.INSTALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$taptap$services$update$TapUpdateDialog$TapUpdateDialogState[TapUpdateDialog.TapUpdateDialogState.UPDATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void init(Context context) {
        TapUpdateAPI.initSkyNet(context.getApplicationContext());
    }

    public void showDialog(Context context, TapUpdateCallback tapUpdateCallback) {
        TapUpdateTracker.getInstance().createSessionId();
        getUpdateInfo(context, tapUpdateCallback);
    }

    public void getUpdateInfo(Context context, TapUpdateCallback tapUpdateCallback) {
        dismissDialog();
        showLoading(context);
        TapUpdateAPI.getInstance().getUpdateInfo(context).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<TapUpdateInfo>() { // from class: com.taptap.services.update.TapUpdateDialogManager.3
            final /* synthetic */ Context val$context;
            final /* synthetic */ TapUpdateCallback val$updateCallback;

            AnonymousClass3(TapUpdateCallback tapUpdateCallback2, Context context2) {
                tapUpdateCallback = tapUpdateCallback2;
                context = context2;
            }

            @Override // com.tds.common.reactor.functions.Action1
            public void call(TapUpdateInfo tapUpdateInfo) {
                TapUpdateDialogManager.this.hideLoading();
                TapUpdateDialogManager.this.mUpdateCallback = tapUpdateCallback;
                if (tapUpdateInfo != null && tapUpdateInfo.isDataValid()) {
                    TapUpdateDialogManager.this.mUpdateInfo = tapUpdateInfo;
                    TapUpdateTracker.getInstance().setGameId(TapUpdateDialogManager.this.mUpdateInfo.appId);
                    TapUpdateDialogManager.this.judgeShowConfirmOrDownload(context);
                } else {
                    TapUpdateLogger.e("tapUpdateInfo is null or not valid");
                    TapUpdateDialogManager.this.getDialog(context).onDownloadReady(false, new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.3.1
                        AnonymousClass1() {
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                            TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
                        }
                    });
                    TapUpdateDialogManager.this.showDialog();
                }
            }

            /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$3$1 */
            class AnonymousClass1 implements View.OnClickListener {
                AnonymousClass1() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                    TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
                }
            }
        }, new Action1<Throwable>() { // from class: com.taptap.services.update.TapUpdateDialogManager.4
            final /* synthetic */ Context val$context;
            final /* synthetic */ TapUpdateCallback val$updateCallback;

            AnonymousClass4(TapUpdateCallback tapUpdateCallback2, Context context2) {
                tapUpdateCallback = tapUpdateCallback2;
                context = context2;
            }

            @Override // com.tds.common.reactor.functions.Action1
            public void call(Throwable th) {
                TapUpdateDialogManager.this.hideLoading();
                TapUpdateDialogManager.this.mUpdateCallback = tapUpdateCallback;
                TapUpdateLogger.e("getUpdateInfo error", th);
                TapUpdateDialogManager.this.getDialog(context).onDownloadReady(false, new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.4.1
                    AnonymousClass1() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                        TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
                    }
                });
                TapUpdateDialogManager.this.showDialog();
            }

            /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$4$1 */
            class AnonymousClass1 implements View.OnClickListener {
                AnonymousClass1() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                    TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$3 */
    class AnonymousClass3 implements Action1<TapUpdateInfo> {
        final /* synthetic */ Context val$context;
        final /* synthetic */ TapUpdateCallback val$updateCallback;

        AnonymousClass3(TapUpdateCallback tapUpdateCallback2, Context context2) {
            tapUpdateCallback = tapUpdateCallback2;
            context = context2;
        }

        @Override // com.tds.common.reactor.functions.Action1
        public void call(TapUpdateInfo tapUpdateInfo) {
            TapUpdateDialogManager.this.hideLoading();
            TapUpdateDialogManager.this.mUpdateCallback = tapUpdateCallback;
            if (tapUpdateInfo != null && tapUpdateInfo.isDataValid()) {
                TapUpdateDialogManager.this.mUpdateInfo = tapUpdateInfo;
                TapUpdateTracker.getInstance().setGameId(TapUpdateDialogManager.this.mUpdateInfo.appId);
                TapUpdateDialogManager.this.judgeShowConfirmOrDownload(context);
            } else {
                TapUpdateLogger.e("tapUpdateInfo is null or not valid");
                TapUpdateDialogManager.this.getDialog(context).onDownloadReady(false, new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.3.1
                    AnonymousClass1() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                        TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
                    }
                });
                TapUpdateDialogManager.this.showDialog();
            }
        }

        /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$3$1 */
        class AnonymousClass1 implements View.OnClickListener {
            AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
            }
        }
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$4 */
    class AnonymousClass4 implements Action1<Throwable> {
        final /* synthetic */ Context val$context;
        final /* synthetic */ TapUpdateCallback val$updateCallback;

        AnonymousClass4(TapUpdateCallback tapUpdateCallback2, Context context2) {
            tapUpdateCallback = tapUpdateCallback2;
            context = context2;
        }

        @Override // com.tds.common.reactor.functions.Action1
        public void call(Throwable th) {
            TapUpdateDialogManager.this.hideLoading();
            TapUpdateDialogManager.this.mUpdateCallback = tapUpdateCallback;
            TapUpdateLogger.e("getUpdateInfo error", th);
            TapUpdateDialogManager.this.getDialog(context).onDownloadReady(false, new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.4.1
                AnonymousClass1() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                    TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
                }
            });
            TapUpdateDialogManager.this.showDialog();
        }

        /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$4$1 */
        class AnonymousClass1 implements View.OnClickListener {
            AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TapUpdateTracker.getInstance().trackRequestFailedPositiveButtonClick();
                TapUpdateDialogManager.this.getUpdateInfo(context, TapUpdateDialogManager.this.mUpdateCallback);
            }
        }
    }

    public void showDialog() {
        hideLoading();
        TapUpdateDialog tapUpdateDialog = this.mDialog;
        if (tapUpdateDialog == null || tapUpdateDialog.isShowing()) {
            return;
        }
        this.mDialog.show();
    }

    public void dismissDialog() {
        TapUpdateDialog tapUpdateDialog = this.mDialog;
        if (tapUpdateDialog == null || !tapUpdateDialog.isShowing()) {
            return;
        }
        this.mDialog.dismiss();
        this.mDialog = null;
    }

    private void showLoading(Context context) {
        if (this.mLoadingDialog == null) {
            this.mLoadingDialog = new LoadingDialog(context);
        }
        this.mLoadingDialog.show();
    }

    public void hideLoading() {
        LoadingDialog loadingDialog = this.mLoadingDialog;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            this.mLoadingDialog = null;
        }
    }

    public void judgeShowConfirmOrDownload(Context context) {
        if (this.mUpdateInfo == null) {
            return;
        }
        if (TapGameUtil.isTapTapInstalled(context)) {
            updateInTapTap(context, TapUpdateTracker.OBJECT_TYPE_UPDATE);
        } else if (this.mUpdateInfo.notify) {
            getDialog(context).onDownloadReady(true, new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.5
                final /* synthetic */ Context val$context;

                AnonymousClass5(Context context2) {
                    context = context2;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TapUpdateTracker.getInstance().trackUpdateConfirmButtonClick();
                    TapUpdateDialogManager.this.showDownloadPage(context);
                }
            });
            showDialog();
        } else {
            showDownloadPage(context2);
            showDialog();
        }
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$5 */
    class AnonymousClass5 implements View.OnClickListener {
        final /* synthetic */ Context val$context;

        AnonymousClass5(Context context2) {
            context = context2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TapUpdateTracker.getInstance().trackUpdateConfirmButtonClick();
            TapUpdateDialogManager.this.showDownloadPage(context);
        }
    }

    public void showDownloadPage(Context context) {
        if (this.mUpdateInfo == null) {
            return;
        }
        TapUpdateDialog dialog = getDialog(context);
        dialog.updateUIInformation(UIInformation.newBuilder().setUpdateTitle("更新游戏，需安装 TapTap 客户端").setAppInfo(this.mUpdateInfo.getAppInfo()).setLinkList(this.mUpdateInfo.funcLinks).build());
        dialog.showDownloadPage();
        dialog.showCloseButton(false);
        dialog.updateDownloadContentTitle("正在下载 TapTap");
        dialog.showDownloadErrorTips(null, false, null, null);
        dialog.setMaxProgress(100);
        dialog.updateSpeed("0 B/s");
        this.retryCount = 0;
        TapUpdateTracker.getInstance().trackDownloadDialogVisible();
        TapUpdateDownloadManager.getInstance().clearCache(context);
        if (!URLUtil.isNetworkUrl(this.mUpdateInfo.downloadInfo.url)) {
            TapUpdateTracker.getInstance().trackDownloadFailed(-20, "download url is not valid :" + this.mUpdateInfo.downloadInfo.url);
            showErrorAndRetry("文件下载链接异常,请通过浏览器链接重试", context, false);
            return;
        }
        TapUpdateDownloadManager.getInstance().download(context, this.mUpdateInfo.downloadInfo.url, null, new TapUpdateDownloadManager.TapUpdateDownloadListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.6
            final /* synthetic */ Context val$context;
            final /* synthetic */ TapUpdateDialog val$dialog;

            AnonymousClass6(Context context2, TapUpdateDialog dialog2) {
                context = context2;
                tapUpdateDialog = dialog2;
            }

            @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
            public void onDownloadStart(long j) {
                TapUpdateLogger.d("download start");
                TapUpdateDialog dialog2 = TapUpdateDialogManager.this.getDialog(context);
                dialog2.updateDownloadContentTitle("正在下载 TapTap (大小：" + Util.humanReadableBytes(j, true) + "）");
                dialog2.showDownloadErrorTips(null, false, null, null);
                dialog2.hideDownloadErrorTips();
                dialog2.updateSpeed("0 B/s");
                TapUpdateTracker.getInstance().trackDownloadStart();
            }

            @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
            public void onDownloadProgress(long j, long j2, long j3, int i, String str) {
                TapUpdateDialog dialog2 = TapUpdateDialogManager.this.getDialog(context);
                dialog2.showDownloadErrorTips(null, false, null, null);
                dialog2.hideDownloadErrorTips();
                dialog2.updateProgress(i);
                dialog2.updateSpeed(str);
                TapUpdateTracker.getInstance().trackDownloadingSpeed(String.valueOf(j2), String.valueOf(j), String.valueOf(j3));
            }

            @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
            public void onDownloadSuccess(File file) {
                int iCheckApkValid = TapUpdateDialogManager.this.checkApkValid(context, file);
                TapUpdateLogger.d("check apk file result = " + iCheckApkValid);
                if (iCheckApkValid < 0) {
                    TapUpdateTracker.getInstance().trackDownloadFailed(-50, iCheckApkValid < -1 ? "package info is not valid" : "not a apk file");
                    TapUpdateDownloadManager.getInstance().clearCache(context);
                    TapUpdateDialogManager.this.showErrorAndRetry("文件下载异常，请重试", context, true);
                } else {
                    TapUpdateTracker.getInstance().trackDownloadComplete();
                    TapUpdateDialogManager.this.showInstallPage(context, file);
                }
            }

            @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
            public void onDownloadFailed(EndCause endCause, Exception exc) {
                if (endCause == EndCause.CANCELED) {
                    return;
                }
                tapUpdateDialog.updateSpeed("0 B/s");
                String string = "error : " + endCause;
                if (exc != null) {
                    string = exc.toString();
                }
                int i = -30;
                String str = "网络异常，请重试";
                if (endCause == EndCause.PRE_ALLOCATE_FAILED) {
                    string = "设备存储空间不足，无法继续下载";
                    i = -40;
                    str = "设备存储空间不足，无法继续下载";
                } else if (endCause == EndCause.FILE_BUSY) {
                    string = "文件被占用";
                } else if (exc instanceof ServerCanceledException) {
                    i = -15;
                } else if ((exc instanceof UnknownHostException) || (exc instanceof SocketException)) {
                    i = -10;
                    if (NetworkUtil.isNetworkAvailable(context)) {
                        i = -13;
                    }
                } else if (exc instanceof SSLException) {
                    i = -14;
                } else if (exc instanceof IOException) {
                    i = -12;
                    if (exc instanceof SocketTimeoutException) {
                        i = -11;
                    }
                }
                TapUpdateLogger.d("errorCode = " + i + " , errorLog = " + string);
                TapUpdateTracker.getInstance().trackDownloadFailed(i, string);
                TapUpdateDialogManager.this.showErrorAndRetry(str, context, true);
            }
        });
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$6 */
    class AnonymousClass6 implements TapUpdateDownloadManager.TapUpdateDownloadListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ TapUpdateDialog val$dialog;

        AnonymousClass6(Context context2, TapUpdateDialog dialog2) {
            context = context2;
            tapUpdateDialog = dialog2;
        }

        @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
        public void onDownloadStart(long j) {
            TapUpdateLogger.d("download start");
            TapUpdateDialog dialog2 = TapUpdateDialogManager.this.getDialog(context);
            dialog2.updateDownloadContentTitle("正在下载 TapTap (大小：" + Util.humanReadableBytes(j, true) + "）");
            dialog2.showDownloadErrorTips(null, false, null, null);
            dialog2.hideDownloadErrorTips();
            dialog2.updateSpeed("0 B/s");
            TapUpdateTracker.getInstance().trackDownloadStart();
        }

        @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
        public void onDownloadProgress(long j, long j2, long j3, int i, String str) {
            TapUpdateDialog dialog2 = TapUpdateDialogManager.this.getDialog(context);
            dialog2.showDownloadErrorTips(null, false, null, null);
            dialog2.hideDownloadErrorTips();
            dialog2.updateProgress(i);
            dialog2.updateSpeed(str);
            TapUpdateTracker.getInstance().trackDownloadingSpeed(String.valueOf(j2), String.valueOf(j), String.valueOf(j3));
        }

        @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
        public void onDownloadSuccess(File file) {
            int iCheckApkValid = TapUpdateDialogManager.this.checkApkValid(context, file);
            TapUpdateLogger.d("check apk file result = " + iCheckApkValid);
            if (iCheckApkValid < 0) {
                TapUpdateTracker.getInstance().trackDownloadFailed(-50, iCheckApkValid < -1 ? "package info is not valid" : "not a apk file");
                TapUpdateDownloadManager.getInstance().clearCache(context);
                TapUpdateDialogManager.this.showErrorAndRetry("文件下载异常，请重试", context, true);
            } else {
                TapUpdateTracker.getInstance().trackDownloadComplete();
                TapUpdateDialogManager.this.showInstallPage(context, file);
            }
        }

        @Override // com.taptap.services.update.TapUpdateDownloadManager.TapUpdateDownloadListener
        public void onDownloadFailed(EndCause endCause, Exception exc) {
            if (endCause == EndCause.CANCELED) {
                return;
            }
            tapUpdateDialog.updateSpeed("0 B/s");
            String string = "error : " + endCause;
            if (exc != null) {
                string = exc.toString();
            }
            int i = -30;
            String str = "网络异常，请重试";
            if (endCause == EndCause.PRE_ALLOCATE_FAILED) {
                string = "设备存储空间不足，无法继续下载";
                i = -40;
                str = "设备存储空间不足，无法继续下载";
            } else if (endCause == EndCause.FILE_BUSY) {
                string = "文件被占用";
            } else if (exc instanceof ServerCanceledException) {
                i = -15;
            } else if ((exc instanceof UnknownHostException) || (exc instanceof SocketException)) {
                i = -10;
                if (NetworkUtil.isNetworkAvailable(context)) {
                    i = -13;
                }
            } else if (exc instanceof SSLException) {
                i = -14;
            } else if (exc instanceof IOException) {
                i = -12;
                if (exc instanceof SocketTimeoutException) {
                    i = -11;
                }
            }
            TapUpdateLogger.d("errorCode = " + i + " , errorLog = " + string);
            TapUpdateTracker.getInstance().trackDownloadFailed(i, string);
            TapUpdateDialogManager.this.showErrorAndRetry(str, context, true);
        }
    }

    public int checkApkValid(Context context, File file) {
        if (context == null || file == null || !file.exists()) {
            return -1;
        }
        try {
            String name = file.getName();
            if (name.substring(name.lastIndexOf(".") + 1).equals("apk")) {
                return context.getPackageManager().getPackageArchiveInfo(file.getPath(), 1) == null ? -2 : 0;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void showErrorAndRetry(String str, Context context, boolean z) {
        if (this.mUpdateInfo == null) {
            return;
        }
        if (this.retryCount >= 1 || !z) {
            getDialog(context).showCloseButton(true);
        }
        SpannableString webInstallSpannableString = (this.retryCount >= 2 || !z) ? getWebInstallSpannableString("或点击此 链接 前往浏览器安装", 4, 8, context, false) : null;
        int i = this.retryCount;
        if (i > 2 && i % 3 == 0) {
            TapUpdateDownloadManager.getInstance().clearCache(context);
        }
        getDialog(context).showDownloadErrorTips(str, z, webInstallSpannableString, new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.7
            AnonymousClass7() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TapUpdateTracker.getInstance().trackDownloadRetryButtonClick();
                TapUpdateDialogManager.access$508(TapUpdateDialogManager.this);
                TapUpdateDownloadManager.getInstance().restartTask();
            }
        });
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$7 */
    class AnonymousClass7 implements View.OnClickListener {
        AnonymousClass7() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TapUpdateTracker.getInstance().trackDownloadRetryButtonClick();
            TapUpdateDialogManager.access$508(TapUpdateDialogManager.this);
            TapUpdateDownloadManager.getInstance().restartTask();
        }
    }

    public void showInstallPage(Context context, File file) {
        if (this.mUpdateInfo == null) {
            return;
        }
        getDialog(context).showCloseButton(true);
        getDialog(context).showFuncViewWithTips("安装 TapTap", getWebInstallSpannableString("安装失败？可点击此 链接 前往浏览器安装", 9, 13, context, true), new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.8
            final /* synthetic */ Context val$context;
            final /* synthetic */ File val$file;

            AnonymousClass8(Context context2, File file2) {
                context = context2;
                file = file2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TapUpdateDialogManager.this.startInstallAPK(context, file, true);
                TapUpdateTracker.getInstance().trackInstallConfirmButtonClick();
            }
        });
        getDialog(context2).setDialogState(TapUpdateDialog.TapUpdateDialogState.INSTALL);
        TapUpdateTracker.getInstance().trackInstallDialogVisible();
        startInstallAPK(context2, file2, false);
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$8 */
    class AnonymousClass8 implements View.OnClickListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ File val$file;

        AnonymousClass8(Context context2, File file2) {
            context = context2;
            file = file2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TapUpdateDialogManager.this.startInstallAPK(context, file, true);
            TapUpdateTracker.getInstance().trackInstallConfirmButtonClick();
        }
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$9 */
    class AnonymousClass9 implements Runnable {
        final /* synthetic */ Context val$context;

        AnonymousClass9(Context context) {
            context = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TapUpdateDialogManager.this.mInstallConfirmDialog == null) {
                TapUpdateDialogManager.this.mInstallConfirmDialog = new TapUpdateInstallConfirmDialog(context);
            }
            TapUpdateDialogManager.this.mInstallConfirmDialog.switchState(TapUpdateInstallConfirmDialog.TapUpdateConfirmDialogType.INSTALL_CONFIRM);
            TapUpdateDialogManager.this.mInstallConfirmDialog.show();
            TapUpdateTracker.getInstance().trackInstallConfirmDialogVisible();
        }
    }

    public void startInstallAPK(Context context, File file, boolean z) {
        TapUpdateDownloadManager.getInstance().installAPK(context, file, z);
        new Handler().postDelayed(new Runnable() { // from class: com.taptap.services.update.TapUpdateDialogManager.9
            final /* synthetic */ Context val$context;

            AnonymousClass9(Context context2) {
                context = context2;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (TapUpdateDialogManager.this.mInstallConfirmDialog == null) {
                    TapUpdateDialogManager.this.mInstallConfirmDialog = new TapUpdateInstallConfirmDialog(context);
                }
                TapUpdateDialogManager.this.mInstallConfirmDialog.switchState(TapUpdateInstallConfirmDialog.TapUpdateConfirmDialogType.INSTALL_CONFIRM);
                TapUpdateDialogManager.this.mInstallConfirmDialog.show();
                TapUpdateTracker.getInstance().trackInstallConfirmDialogVisible();
            }
        }, 300L);
    }

    public void showOpenPage(Context context) {
        if (this.mUpdateInfo == null) {
            return;
        }
        getDialog(context).updateUIInformation(UIInformation.newBuilder().setUpdateTitle("更新游戏，需使用 TapTap 客户端").setAppInfo(this.mUpdateInfo.getAppInfo()).setLinkList(this.mUpdateInfo.funcLinks).build());
        getDialog(context).showFuncViewWithTips("打开 TapTap 去更新", "", new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialogManager.10
            final /* synthetic */ Context val$context;

            AnonymousClass10(Context context2) {
                context = context2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TapUpdateDialogManager.this.updateInTapTap(context, TapUpdateTracker.OBJECT_TYPE_UPDATE_GO_TAP);
                TapUpdateTracker.getInstance().trackToTapUpdateButtonClick();
            }
        });
        getDialog(context2).setDialogState(TapUpdateDialog.TapUpdateDialogState.UPDATE);
        TapUpdateTracker.getInstance().trackUpdateDialogVisible();
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$10 */
    class AnonymousClass10 implements View.OnClickListener {
        final /* synthetic */ Context val$context;

        AnonymousClass10(Context context2) {
            context = context2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TapUpdateDialogManager.this.updateInTapTap(context, TapUpdateTracker.OBJECT_TYPE_UPDATE_GO_TAP);
            TapUpdateTracker.getInstance().trackToTapUpdateButtonClick();
        }
    }

    public void updateInTapTap(Context context, String str) {
        try {
            TapUpdateTracker.getInstance().trackOpenTapClient(str);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(this.mUpdateInfo.clientUpdateUri));
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(context, "打开失败，请重试", 0).show();
        }
    }

    public void installSuccess(Context context) {
        TapUpdateInstallConfirmDialog tapUpdateInstallConfirmDialog = this.mInstallConfirmDialog;
        if (tapUpdateInstallConfirmDialog != null) {
            tapUpdateInstallConfirmDialog.dismiss();
        }
        TapUpdateTracker.getInstance().trackInstallComplete();
        getInstance().showOpenPage(context);
        TapUpdateDownloadManager.getInstance().clearCache(context);
    }

    private SpannableString getWebInstallSpannableString(String str, int i, int i2, Context context, boolean z) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ClickableSpan() { // from class: com.taptap.services.update.TapUpdateDialogManager.11
            final /* synthetic */ Context val$context;
            final /* synthetic */ boolean val$fromInstall;

            AnonymousClass11(Context context2, boolean z2) {
                context = context2;
                z = z2;
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(Color.parseColor("#00D9C5"));
                textPaint.setUnderlineText(false);
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                try {
                    if (TapUpdateDialogManager.this.mUpdateInfo == null) {
                        return;
                    }
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(TapUpdateDialogManager.this.mUpdateInfo.webUpdateUrl)));
                } catch (Exception e) {
                    TapUpdateLogger.e(e.getMessage());
                }
                if (z) {
                    TapUpdateTracker.getInstance().trackInstallLinkButtonClick();
                } else {
                    TapUpdateTracker.getInstance().trackDownloadLinkButtonClick();
                }
            }
        }, i, i2, 33);
        return spannableString;
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateDialogManager$11 */
    class AnonymousClass11 extends ClickableSpan {
        final /* synthetic */ Context val$context;
        final /* synthetic */ boolean val$fromInstall;

        AnonymousClass11(Context context2, boolean z2) {
            context = context2;
            z = z2;
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setColor(Color.parseColor("#00D9C5"));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            try {
                if (TapUpdateDialogManager.this.mUpdateInfo == null) {
                    return;
                }
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(TapUpdateDialogManager.this.mUpdateInfo.webUpdateUrl)));
            } catch (Exception e) {
                TapUpdateLogger.e(e.getMessage());
            }
            if (z) {
                TapUpdateTracker.getInstance().trackInstallLinkButtonClick();
            } else {
                TapUpdateTracker.getInstance().trackDownloadLinkButtonClick();
            }
        }
    }

    public void registerPackageInstallReceiver(Context context) {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addDataScheme("package");
            context.getApplicationContext().registerReceiver(new TapUpdatePackageInstallReceiver(), intentFilter);
        } catch (Throwable th) {
            TapUpdateLogger.e("registerPackageInstallReceiver error", th);
        }
    }
}
