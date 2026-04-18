package com.unity3d.splash.services.core.webview.bridge;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class NativeCallback {
    private static AtomicInteger _callbackCount = new AtomicInteger(0);
    private Method _callback;
    private String _id;

    public NativeCallback(Method method) {
        this._callback = method;
        this._id = this._callback.getName().toUpperCase(Locale.US) + "_" + _callbackCount.getAndIncrement();
    }

    public String getId() {
        return this._id;
    }

    public void invoke(String str, Object... objArr) {
        Object[] array;
        try {
            CallbackStatus callbackStatusValueOf = CallbackStatus.valueOf(str);
            if (objArr == null) {
                array = new Object[]{callbackStatusValueOf};
            } else {
                ArrayList arrayList = new ArrayList(Arrays.asList(objArr));
                arrayList.add(0, callbackStatusValueOf);
                array = arrayList.toArray();
            }
            this._callback.invoke(null, array);
            WebViewApp.getCurrentApp().removeCallback(this);
        } catch (Exception e) {
            DeviceLog.error("Illegal status");
            WebViewApp.getCurrentApp().removeCallback(this);
            throw e;
        }
    }
}
