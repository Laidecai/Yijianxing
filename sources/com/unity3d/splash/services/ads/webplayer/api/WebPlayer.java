package com.unity3d.splash.services.ads.webplayer.api;

import android.view.View;
import com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler;
import com.unity3d.splash.services.ads.api.AdUnit;
import com.unity3d.splash.services.ads.webplayer.WebPlayerError;
import com.unity3d.splash.services.ads.webplayer.WebPlayerSettingsCache;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WebPlayer {
    @WebViewExposed
    public static void clearSettings(String str, WebViewCallback webViewCallback) {
        WebPlayerSettingsCache webPlayerSettingsCache = WebPlayerSettingsCache.getInstance();
        webPlayerSettingsCache.removeWebSettings(str);
        webPlayerSettingsCache.removeWebPlayerSettings(str);
        webPlayerSettingsCache.removeWebPlayerEventSettings(str);
        webViewCallback.invoke(new Object[0]);
    }

    private static com.unity3d.splash.services.ads.webplayer.WebPlayer getAdUnitWebPlayer() {
        IAdUnitViewHandler viewHandler;
        View view;
        if (AdUnit.getAdUnitActivity() == null || (viewHandler = AdUnit.getAdUnitActivity().getViewHandler("webplayer")) == null || (view = viewHandler.getView()) == null) {
            return null;
        }
        return (com.unity3d.splash.services.ads.webplayer.WebPlayer) view;
    }

    @WebViewExposed
    public static void getErroredSettings(String str, WebViewCallback webViewCallback) {
        com.unity3d.splash.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str);
        if (webPlayer == null) {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
            return;
        }
        Map erroredSettings = webPlayer.getErroredSettings();
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry entry : erroredSettings.entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            DeviceLog.exception("Error forming JSON object", e);
        }
        webViewCallback.invoke(jSONObject);
        webViewCallback.invoke(new Object[0]);
    }

    private static com.unity3d.splash.services.ads.webplayer.WebPlayer getWebPlayer(String str) {
        str.hashCode();
        if (str.equals("webplayer")) {
            return getAdUnitWebPlayer();
        }
        return null;
    }

    @WebViewExposed
    public static void sendEvent(JSONArray jSONArray, String str, WebViewCallback webViewCallback) {
        com.unity3d.splash.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str);
        if (webPlayer == null) {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        } else {
            webPlayer.sendEvent(jSONArray);
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void setData(final String str, final String str2, final String str3, String str4, WebViewCallback webViewCallback) {
        final com.unity3d.splash.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str4);
        if (webPlayer == null) {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        } else {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.webplayer.api.WebPlayer.2
                @Override // java.lang.Runnable
                public final void run() {
                    webPlayer.loadData(str, str2, str3);
                }
            });
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void setDataWithUrl(final String str, final String str2, final String str3, final String str4, String str5, WebViewCallback webViewCallback) {
        final com.unity3d.splash.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str5);
        if (webPlayer == null) {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        } else {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.webplayer.api.WebPlayer.3
                @Override // java.lang.Runnable
                public final void run() {
                    webPlayer.loadDataWithBaseURL(str, str2, str3, str4, null);
                }
            });
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void setEventSettings(JSONObject jSONObject, String str, WebViewCallback webViewCallback) {
        WebPlayerSettingsCache.getInstance().addWebPlayerEventSettings(str, jSONObject);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setSettings(JSONObject jSONObject, JSONObject jSONObject2, String str, WebViewCallback webViewCallback) {
        WebPlayerSettingsCache.getInstance().addWebSettings(str, jSONObject);
        WebPlayerSettingsCache.getInstance().addWebPlayerSettings(str, jSONObject2);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setUrl(final String str, String str2, WebViewCallback webViewCallback) {
        final com.unity3d.splash.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str2);
        if (webPlayer == null) {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        } else {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.webplayer.api.WebPlayer.1
                @Override // java.lang.Runnable
                public final void run() {
                    webPlayer.loadUrl(str);
                }
            });
            webViewCallback.invoke(new Object[0]);
        }
    }
}
