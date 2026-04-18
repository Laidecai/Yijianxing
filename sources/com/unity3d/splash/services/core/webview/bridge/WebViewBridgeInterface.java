package com.unity3d.splash.services.core.webview.bridge;

import android.webkit.JavascriptInterface;
import com.unity3d.splash.services.core.log.DeviceLog;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class WebViewBridgeInterface {
    private Object[] getParameters(JSONArray jSONArray) {
        Object[] objArr = new Object[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            objArr[i] = jSONArray.get(i);
        }
        return objArr;
    }

    @JavascriptInterface
    public void handleCallback(String str, String str2, String str3) {
        Object[] objArr;
        DeviceLog.debug("handleCallback " + str + " " + str2 + " " + str3);
        JSONArray jSONArray = new JSONArray(str3);
        if (jSONArray.length() > 0) {
            objArr = new Object[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                objArr[i] = jSONArray.get(i);
            }
        } else {
            objArr = null;
        }
        WebViewBridge.handleCallback(str, str2, objArr);
    }

    @JavascriptInterface
    public void handleInvocation(String str) {
        DeviceLog.debug("handleInvocation " + str);
        JSONArray jSONArray = new JSONArray(str);
        Invocation invocation = new Invocation();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONArray jSONArray2 = (JSONArray) jSONArray.get(i);
            invocation.addInvocation((String) jSONArray2.get(0), (String) jSONArray2.get(1), getParameters((JSONArray) jSONArray2.get(2)), new WebViewCallback((String) jSONArray2.get(3), invocation.getId()));
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            invocation.nextInvocation();
        }
        invocation.sendInvocationCallback();
    }
}
