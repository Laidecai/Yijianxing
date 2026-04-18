package com.unity3d.splash.services.core.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class LifecycleListener implements Application.ActivityLifecycleCallbacks {
    private ArrayList _events;

    public LifecycleListener(ArrayList arrayList) {
        this._events = arrayList;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (!this._events.contains("onActivityCreated") || WebViewApp.getCurrentApp() == null) {
            return;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.CREATED, activity.getClass().getName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        if (!this._events.contains("onActivityDestroyed") || WebViewApp.getCurrentApp() == null) {
            return;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.DESTROYED, activity.getClass().getName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        if (!this._events.contains("onActivityPaused") || WebViewApp.getCurrentApp() == null) {
            return;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.PAUSED, activity.getClass().getName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (!this._events.contains("onActivityResumed") || WebViewApp.getCurrentApp() == null) {
            return;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.RESUMED, activity.getClass().getName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        if (!this._events.contains("onActivitySaveInstanceState") || WebViewApp.getCurrentApp() == null) {
            return;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.SAVE_INSTANCE_STATE, activity.getClass().getName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        if (!this._events.contains("onActivityStarted") || WebViewApp.getCurrentApp() == null) {
            return;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.STARTED, activity.getClass().getName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        if (!this._events.contains("onActivityStopped") || WebViewApp.getCurrentApp() == null) {
            return;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LIFECYCLE, LifecycleEvent.STOPPED, activity.getClass().getName());
    }
}
