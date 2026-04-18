package com.tds.common.widgets.toast;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

/* JADX INFO: loaded from: classes.dex */
public class TapToast {
    private volatile Activity currentActivity;
    private String iconUrl;
    private volatile Handler mainHandler;
    private volatile String message;
    private final Queue<MessageEntity> messageQueue;

    private TapToast() {
        this.messageQueue = new LinkedList();
    }

    public static TapToast getInstance() {
        return Single.INSTANCE.tapToast;
    }

    public void show(final Activity activity, final String str) {
        if (checkParams(activity, str)) {
            if (this.mainHandler == null) {
                this.mainHandler = new Handler(Looper.getMainLooper());
            }
            this.mainHandler.post(new Runnable() { // from class: com.tds.common.widgets.toast.TapToast.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TapToast.this.currentActivity != null) {
                        TapToast.this.messageQueue.offer(new MessageEntity(activity, str));
                        return;
                    }
                    TapToast.this.currentActivity = activity;
                    TapToast.this.message = str;
                    TapToast.this.showInternal();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showInternal() {
        if (checkActivityValid(this.currentActivity)) {
            try {
                final TapToastDialog tapToastDialogInstance = TapToastDialog.instance(this.iconUrl, this.message);
                tapToastDialogInstance.show(this.currentActivity.getFragmentManager(), "TapToast");
                this.mainHandler.postDelayed(new Runnable() { // from class: com.tds.common.widgets.toast.TapToast.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (tapToastDialogInstance.getDialog() != null) {
                                tapToastDialogInstance.dismissAllowingStateLoss();
                            }
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                        TapToast.this.currentActivity = null;
                        TapToast.this.doNext();
                    }
                }, 2000L);
                return;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        this.currentActivity = null;
        doNext();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doNext() {
        MessageEntity messageEntityPoll = this.messageQueue.poll();
        if (messageEntityPoll == null || this.currentActivity != null || messageEntityPoll.activityWeakReference == null) {
            return;
        }
        Activity activity = messageEntityPoll.activityWeakReference.get();
        if (checkParams(activity, messageEntityPoll.message)) {
            show(activity, messageEntityPoll.message);
        } else {
            doNext();
        }
    }

    private boolean checkActivityValid(Activity activity) {
        return (activity == null || activity.isDestroyed() || activity.isFinishing() || activity.getWindow() == null || activity.getWindow().getDecorView().getVisibility() != 0) ? false : true;
    }

    private boolean checkParams(Activity activity, String str) {
        return checkActivityValid(activity) && !TextUtils.isEmpty(str);
    }

    public void setIconUrl(String str) {
        this.iconUrl = str;
    }

    static class MessageEntity {
        WeakReference<Activity> activityWeakReference;
        String message;

        MessageEntity(Activity activity, String str) {
            this.activityWeakReference = new WeakReference<>(activity);
            this.message = str;
        }
    }

    private enum Single {
        INSTANCE;

        final TapToast tapToast = new TapToast();

        Single() {
        }
    }
}
