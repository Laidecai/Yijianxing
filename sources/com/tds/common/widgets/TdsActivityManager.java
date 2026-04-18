package com.tds.common.widgets;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TdsActivityManager {
    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;
    private List<AppLifecycleCallback> appLifecycleCallbackList;
    private WeakReference<Activity> rootActivityReference;
    private static final List<WeakReference<Activity>> aliveActivityList = new ArrayList();
    private static final Object activityLock = new Object();
    private static volatile boolean isBackground = false;

    public interface AppLifecycleCallback {
        void onEnterBackground();

        void onEnterForeground();
    }

    public static TdsActivityManager getInstance() {
        return Single.INSTANCE.manager;
    }

    public Activity getCurrentActivity() {
        WeakReference<Activity> weakReference;
        List<WeakReference<Activity>> list = aliveActivityList;
        if (list.size() > 0 && (weakReference = list.get(list.size() - 1)) != null && weakReference.get() != null && !weakReference.get().isDestroyed()) {
            return weakReference.get();
        }
        WeakReference<Activity> weakReference2 = this.rootActivityReference;
        if (weakReference2 != null) {
            return weakReference2.get();
        }
        return null;
    }

    public void setRootActivity(Activity activity) {
        WeakReference<Activity> weakReference = this.rootActivityReference;
        if ((weakReference != null && weakReference.get() != null && !this.rootActivityReference.get().isDestroyed()) || activity == null || activity.isDestroyed()) {
            return;
        }
        this.rootActivityReference = new WeakReference<>(activity);
        registerCallback(activity.getApplication());
    }

    private void registerCallback(Application application) {
        if (this.activityLifecycleCallbacks == null) {
            Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() { // from class: com.tds.common.widgets.TdsActivityManager.1
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityDestroyed(Activity activity) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStarted(Activity activity) {
                    TdsActivityManager.this.handleAppActive(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityResumed(Activity activity) {
                    TdsActivityManager.this.handleAppActive(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityPaused(Activity activity) {
                    TdsActivityManager.this.handleAppActive(activity);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public void onActivityStopped(Activity activity) {
                    if (TdsActivityManager.checkActivityExist(activity, true) && TdsActivityManager.aliveActivityList.size() == 0 && !TdsActivityManager.isBackground) {
                        boolean unused = TdsActivityManager.isBackground = true;
                        TdsActivityManager.this.notifyAppLifecycleCallback();
                    }
                }
            };
            this.activityLifecycleCallbacks = activityLifecycleCallbacks;
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAppActive(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            synchronized (activityLock) {
                List<WeakReference<Activity>> list = aliveActivityList;
                if (list.size() == 0 && isBackground) {
                    isBackground = false;
                    notifyAppLifecycleCallback();
                }
                if (!checkActivityExist(activity, false)) {
                    list.add(new WeakReference<>(activity));
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkActivityExist(Activity activity, boolean z) {
        try {
            synchronized (activityLock) {
                Iterator<WeakReference<Activity>> it = aliveActivityList.iterator();
                while (it.hasNext()) {
                    if (it.next().get() == activity) {
                        if (z) {
                            it.remove();
                        }
                        return true;
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public void registerAppLifecycleCallback(AppLifecycleCallback appLifecycleCallback) {
        if (this.appLifecycleCallbackList == null) {
            this.appLifecycleCallbackList = new ArrayList();
        }
        this.appLifecycleCallbackList.add(appLifecycleCallback);
    }

    public void unregisterAppLifecycleCallback(AppLifecycleCallback appLifecycleCallback) {
        List<AppLifecycleCallback> list = this.appLifecycleCallbackList;
        if (list != null) {
            list.remove(appLifecycleCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAppLifecycleCallback() {
        List<AppLifecycleCallback> list = this.appLifecycleCallbackList;
        if (list != null) {
            for (AppLifecycleCallback appLifecycleCallback : list) {
                if (isBackground) {
                    appLifecycleCallback.onEnterBackground();
                } else {
                    appLifecycleCallback.onEnterForeground();
                }
            }
        }
    }

    enum Single {
        INSTANCE;

        TdsActivityManager manager = new TdsActivityManager();

        Single() {
        }
    }
}
