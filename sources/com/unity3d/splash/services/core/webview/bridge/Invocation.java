package com.unity3d.splash.services.core.webview.bridge;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class Invocation {
    private static AtomicInteger _idCount = new AtomicInteger(0);
    private static Map _invocationSets;
    private int _invocationId = _idCount.getAndIncrement();
    private ArrayList _invocations;
    private ArrayList _responses;

    public Invocation() {
        if (_invocationSets == null) {
            _invocationSets = new HashMap();
        }
        _invocationSets.put(Integer.valueOf(this._invocationId), this);
    }

    public static synchronized Invocation getInvocationById(int i) {
        Map map = _invocationSets;
        if (map == null || !map.containsKey(Integer.valueOf(i))) {
            return null;
        }
        return (Invocation) _invocationSets.get(Integer.valueOf(i));
    }

    public void addInvocation(String str, String str2, Object[] objArr, WebViewCallback webViewCallback) {
        if (this._invocations == null) {
            this._invocations = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(str2);
        arrayList.add(objArr);
        arrayList.add(webViewCallback);
        this._invocations.add(arrayList);
    }

    public int getId() {
        return this._invocationId;
    }

    public ArrayList getResponses() {
        return this._responses;
    }

    public boolean nextInvocation() {
        ArrayList arrayList = this._invocations;
        if (arrayList == null || arrayList.size() <= 0) {
            return false;
        }
        ArrayList arrayList2 = (ArrayList) this._invocations.remove(0);
        String str = (String) arrayList2.get(0);
        String str2 = (String) arrayList2.get(1);
        Object[] objArr = (Object[]) arrayList2.get(2);
        try {
            WebViewBridge.handleInvocation(str, str2, objArr, (WebViewCallback) arrayList2.get(3));
        } catch (Exception e) {
            DeviceLog.exception(String.format("Error handling invocation %s.%s(%s)", str, str2, Arrays.toString(objArr)), e);
        }
        return true;
    }

    public void sendInvocationCallback() {
        _invocationSets.remove(Integer.valueOf(getId()));
        WebViewApp.getCurrentApp().invokeCallback(this);
    }

    public void setInvocationResponse(CallbackStatus callbackStatus, Enum r3, Object... objArr) {
        if (this._responses == null) {
            this._responses = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(callbackStatus);
        arrayList.add(r3);
        arrayList.add(objArr);
        this._responses.add(arrayList);
    }
}
