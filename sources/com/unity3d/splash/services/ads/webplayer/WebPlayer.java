package com.unity3d.splash.services.ads.webplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.packet.e;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WebPlayer extends WebView {
    private Map _erroredSettings;
    private Method _evaluateJavascript;
    private JSONObject _eventSettings;
    private String viewId;

    private class JavaScriptInvocation implements Runnable {
        private String _jsString;
        private WebView _webView;

        public JavaScriptInvocation(String str, WebView webView) {
            this._jsString = null;
            this._webView = null;
            this._jsString = str;
            this._webView = webView;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this._jsString == null) {
                DeviceLog.error("Could not process JavaScript, the string is NULL");
                return;
            }
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    WebPlayer.this._evaluateJavascript.invoke(this._webView, this._jsString, null);
                } else {
                    WebPlayer.this.loadUrl(this._jsString);
                }
            } catch (Exception e) {
                DeviceLog.exception("Error while processing JavaScriptString", e);
            }
        }
    }

    private class WebPlayerChromeClient extends WebChromeClient {
        private WebPlayerChromeClient() {
        }

        @Override // android.webkit.WebChromeClient
        public void onCloseWindow(WebView webView) {
            if (WebPlayer.this.shouldCallSuper("onCloseWindow")) {
                super.onCloseWindow(webView);
            }
            if (WebPlayer.this.shouldSendEvent("onCloseWindow")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CLOSE_WINDOW, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("onConsoleMessage") ? Boolean.valueOf(super.onConsoleMessage(consoleMessage)) : false;
            if (WebPlayer.this.shouldSendEvent("onConsoleMessage")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CONSOLE_MESSAGE, consoleMessage != null ? consoleMessage.message() : "", WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("onConsoleMessage")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("onConsoleMessage", Boolean.class, true);
            }
            return boolValueOf.booleanValue();
        }

        @Override // android.webkit.WebChromeClient
        public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("onCreateWindow") ? Boolean.valueOf(super.onCreateWindow(webView, z, z2, message)) : false;
            if (WebPlayer.this.shouldSendEvent("onCreateWindow")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CREATE_WINDOW, Boolean.valueOf(z), Boolean.valueOf(z2), message, WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("onCreateWindow")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("onCreateWindow", Boolean.class, false);
            }
            return boolValueOf.booleanValue();
        }

        @Override // android.webkit.WebChromeClient
        public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
            if (WebPlayer.this.shouldCallSuper("onGeolocationPermissionsShowPrompt")) {
                super.onGeolocationPermissionsShowPrompt(str, callback);
            }
            if (WebPlayer.this.shouldSendEvent("onGeolocationPermissionsShowPrompt")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.GEOLOCATION_PERMISSIONS_SHOW, str, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onHideCustomView() {
            if (WebPlayer.this.shouldCallSuper("onHideCustomView")) {
                super.onHideCustomView();
            }
            if (WebPlayer.this.shouldSendEvent("onHideCustomView")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.HIDE_CUSTOM_VIEW, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("onJsAlert") ? Boolean.valueOf(super.onJsAlert(webView, str, str2, jsResult)) : false;
            if (WebPlayer.this.shouldSendEvent("onJsAlert")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.JS_ALERT, str, str2, jsResult, WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("onJsAlert")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("onJsAlert", Boolean.class, true);
            }
            return boolValueOf.booleanValue();
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("onJsConfirm") ? Boolean.valueOf(super.onJsConfirm(webView, str, str2, jsResult)) : false;
            if (WebPlayer.this.shouldSendEvent("onJsConfirm")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.JS_CONFIRM, str, str2, WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("onJsConfirm")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("onJsConfirm", Boolean.class, true);
            }
            return boolValueOf.booleanValue();
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("onJsPrompt") ? Boolean.valueOf(super.onJsPrompt(webView, str, str2, str3, jsPromptResult)) : false;
            if (WebPlayer.this.shouldSendEvent("onJsPrompt")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.JS_PROMPT, str, str2, str3, WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("onJsPrompt")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("onJsPrompt", Boolean.class, true);
            }
            return boolValueOf.booleanValue();
        }

        @Override // android.webkit.WebChromeClient
        public void onPermissionRequest(PermissionRequest permissionRequest) {
            if (WebPlayer.this.shouldCallSuper("onPermissionRequest")) {
                super.onPermissionRequest(permissionRequest);
            }
            if (WebPlayer.this.shouldSendEvent("onPermissionRequest")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PERMISSION_REQUEST, (permissionRequest == null || permissionRequest.getOrigin() == null) ? "" : permissionRequest.getOrigin().toString(), WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            if (WebPlayer.this.shouldCallSuper("onProgressChanged")) {
                super.onProgressChanged(webView, i);
            }
            if (WebPlayer.this.shouldSendEvent("onProgressChanged")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PROGRESS_CHANGED, Integer.valueOf(i), WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedIcon(WebView webView, Bitmap bitmap) {
            if (WebPlayer.this.shouldCallSuper("onReceivedIcon")) {
                super.onReceivedIcon(webView, bitmap);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedIcon")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.RECEIVED_ICON, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            if (WebPlayer.this.shouldCallSuper("onReceivedTitle")) {
                super.onReceivedTitle(webView, str);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedTitle")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.RECEIVED_TITLE, str, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTouchIconUrl(WebView webView, String str, boolean z) {
            if (WebPlayer.this.shouldCallSuper("onReceivedTouchIconUrl")) {
                super.onReceivedTouchIconUrl(webView, str, z);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedTouchIconUrl")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.RECEIVED_TOUCH_ICON_URL, str, Boolean.valueOf(z), WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onRequestFocus(WebView webView) {
            if (WebPlayer.this.shouldCallSuper("onRequestFocus")) {
                super.onRequestFocus(webView);
            }
            if (WebPlayer.this.shouldSendEvent("onRequestFocus")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.REQUEST_FOCUS, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
            if (WebPlayer.this.shouldCallSuper("onShowCustomView")) {
                super.onShowCustomView(view, customViewCallback);
            }
            if (WebPlayer.this.shouldSendEvent("onShowCustomView")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOW_CUSTOM_VIEW, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("onShowFileChooser") ? Boolean.valueOf(super.onShowFileChooser(webView, valueCallback, fileChooserParams)) : false;
            if (WebPlayer.this.shouldSendEvent("onShowFileChooser")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOW_FILE_CHOOSER, WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("onShowFileChooser")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("onShowFileChooser", Boolean.class, true);
                if (boolValueOf.booleanValue()) {
                    valueCallback.onReceiveValue(null);
                }
            }
            return boolValueOf.booleanValue();
        }
    }

    private class WebPlayerClient extends WebViewClient {
        private WebPlayerClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onFormResubmission(WebView webView, Message message, Message message2) {
            if (WebPlayer.this.shouldCallSuper("onFormResubmission")) {
                super.onFormResubmission(webView, message, message2);
            }
            if (WebPlayer.this.shouldSendEvent("onFormResubmission")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.FORM_RESUBMISSION, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onLoadResource(WebView webView, String str) {
            if (WebPlayer.this.shouldCallSuper("onLoadResource")) {
                super.onLoadResource(webView, str);
            }
            if (WebPlayer.this.shouldSendEvent("onLoadResource")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.LOAD_RESOUCE, str, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageCommitVisible(WebView webView, String str) {
            if (WebPlayer.this.shouldCallSuper("onPageCommitVisible")) {
                super.onPageCommitVisible(webView, str);
            }
            if (WebPlayer.this.shouldSendEvent("onPageCommitVisible")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PAGE_COMMIT_VISIBLE, str, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            if (WebPlayer.this.shouldCallSuper("onPageFinished")) {
                super.onPageFinished(webView, str);
            }
            if (WebPlayer.this.shouldSendEvent("onPageFinished")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PAGE_FINISHED, str, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (WebPlayer.this.shouldCallSuper("onPageStarted")) {
                super.onPageStarted(webView, str, bitmap);
            }
            if (WebPlayer.this.shouldSendEvent("onPageStarted")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PAGE_STARTED, str, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
            String host;
            int port;
            if (WebPlayer.this.shouldCallSuper("onReceivedClientCertRequest")) {
                super.onReceivedClientCertRequest(webView, clientCertRequest);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedClientCertRequest")) {
                if (clientCertRequest != null) {
                    host = clientCertRequest.getHost();
                    port = clientCertRequest.getPort();
                } else {
                    host = "";
                    port = -1;
                }
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CLIENT_CERT_REQUEST, host, Integer.valueOf(port), WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            if (WebPlayer.this.shouldCallSuper("onReceivedError")) {
                super.onReceivedError(webView, i, str, str2);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedError")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.ERROR, str2, str, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            if (WebPlayer.this.shouldCallSuper("onReceivedError")) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedError")) {
                String string = "";
                String string2 = (webResourceError == null || webResourceError.getDescription() == null) ? "" : webResourceError.getDescription().toString();
                if (webResourceRequest != null && webResourceRequest.getUrl() != null) {
                    string = webResourceRequest.getUrl().toString();
                }
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.ERROR, string, string2, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
            if (WebPlayer.this.shouldCallSuper("onReceivedHttpAuthRequest")) {
                super.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedHttpAuthRequest")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.HTTP_AUTH_REQUEST, str, str2, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
            if (WebPlayer.this.shouldCallSuper("onReceivedHttpError")) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedHttpError")) {
                String reasonPhrase = "";
                String string = (webResourceRequest == null || webResourceRequest.getUrl() == null) ? "" : webResourceRequest.getUrl().toString();
                int statusCode = -1;
                if (webResourceResponse != null) {
                    statusCode = webResourceResponse.getStatusCode();
                    reasonPhrase = webResourceResponse.getReasonPhrase();
                }
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.HTTP_ERROR, string, reasonPhrase, Integer.valueOf(statusCode), WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
            if (WebPlayer.this.shouldCallSuper("onReceivedLoginRequest")) {
                super.onReceivedLoginRequest(webView, str, str2, str3);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedLoginRequest")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.LOGIN_REQUEST, str, str2, str3, WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (WebPlayer.this.shouldCallSuper("onReceivedSslError")) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
            if (WebPlayer.this.shouldSendEvent("onReceivedSslError")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SSL_ERROR, sslError != null ? sslError.getUrl() : "", WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onScaleChanged(WebView webView, float f, float f2) {
            if (WebPlayer.this.shouldCallSuper("onScaleChanged")) {
                super.onScaleChanged(webView, f, f2);
            }
            if (WebPlayer.this.shouldSendEvent("onScaleChanged")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SCALE_CHANGED, Float.valueOf(f), Float.valueOf(f2), WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
            if (WebPlayer.this.shouldCallSuper("onUnhandledKeyEvent")) {
                super.onUnhandledKeyEvent(webView, keyEvent);
            }
            if (WebPlayer.this.shouldSendEvent("onUnhandledKeyEvent")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.UNHANDLED_KEY_EVENT, Integer.valueOf(keyEvent.getKeyCode()), Integer.valueOf(keyEvent.getAction()), WebPlayer.this.viewId);
            }
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            WebResourceResponse webResourceResponseShouldInterceptRequest = WebPlayer.this.shouldCallSuper("shouldInterceptRequest") ? super.shouldInterceptRequest(webView, webResourceRequest) : null;
            if (WebPlayer.this.shouldSendEvent("shouldInterceptRequest")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_INTERCEPT_REQUEST, webResourceRequest.getUrl().toString(), WebPlayer.this.viewId);
            }
            return webResourceResponseShouldInterceptRequest;
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("shouldOverrideKeyEvent") ? Boolean.valueOf(super.shouldOverrideKeyEvent(webView, keyEvent)) : false;
            if (WebPlayer.this.shouldSendEvent("shouldOverrideKeyEvent")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_OVERRIDE_KEY_EVENT, Integer.valueOf(keyEvent.getKeyCode()), Integer.valueOf(keyEvent.getAction()), WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("shouldOverrideKeyEvent")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("shouldOverrideKeyEvent", Boolean.class, true);
            }
            return boolValueOf.booleanValue();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("shouldOverrideUrlLoading") ? Boolean.valueOf(super.shouldOverrideUrlLoading(webView, webResourceRequest)) : false;
            if (WebPlayer.this.shouldSendEvent("shouldOverrideUrlLoading")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_OVERRIDE_URL_LOADING, webResourceRequest.getUrl().toString(), webResourceRequest.getMethod(), WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("shouldOverrideUrlLoading")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("shouldOverrideUrlLoading", Boolean.class, true);
            }
            return boolValueOf.booleanValue();
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Boolean boolValueOf = WebPlayer.this.shouldCallSuper("shouldOverrideUrlLoading") ? Boolean.valueOf(super.shouldOverrideUrlLoading(webView, str)) : false;
            if (WebPlayer.this.shouldSendEvent("shouldOverrideUrlLoading")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_OVERRIDE_URL_LOADING, str, WebPlayer.this.viewId);
            }
            if (WebPlayer.this.hasReturnValue("shouldOverrideUrlLoading")) {
                boolValueOf = (Boolean) WebPlayer.this.getReturnValue("shouldOverrideUrlLoading", Boolean.class, true);
            }
            return boolValueOf.booleanValue();
        }
    }

    private class WebPlayerDownloadListener implements DownloadListener {
        private WebPlayerDownloadListener() {
        }

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            if (WebPlayer.this.shouldSendEvent("onDownloadStart")) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.DOWNLOAD_START, str, str2, str3, str4, Long.valueOf(j), WebPlayer.this.viewId);
            }
        }
    }

    public WebPlayer(Context context, String str, JSONObject jSONObject, JSONObject jSONObject2) {
        super(context);
        this._evaluateJavascript = null;
        this.viewId = str;
        WebSettings settings = getSettings();
        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                this._evaluateJavascript = WebView.class.getMethod("evaluateJavascript", String.class, ValueCallback.class);
            } catch (NoSuchMethodException e) {
                DeviceLog.exception("Method evaluateJavascript not found", e);
                this._evaluateJavascript = null;
            }
        }
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(2);
        settings.setDatabaseEnabled(false);
        settings.setDomStorageEnabled(false);
        settings.setGeolocationEnabled(false);
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setInitialScale(0);
        setBackgroundColor(0);
        ViewUtilities.setBackground(this, new ColorDrawable(0));
        setBackgroundResource(0);
        setSettings(jSONObject, jSONObject2);
        setWebViewClient(new WebPlayerClient());
        setWebChromeClient(new WebPlayerChromeClient());
        setDownloadListener(new WebPlayerDownloadListener());
        addJavascriptInterface(new WebPlayerBridgeInterface(str), "webplayerbridge");
    }

    private void addErroredSetting(String str, String str2) {
        if (this._erroredSettings == null) {
            this._erroredSettings = new HashMap();
        }
        this._erroredSettings.put(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object getReturnValue(String str, Class cls, Object obj) {
        try {
            JSONObject jSONObject = this._eventSettings;
            if (jSONObject != null && jSONObject.has(str) && this._eventSettings.getJSONObject(str).has("returnValue")) {
                return cls.cast(this._eventSettings.getJSONObject(str).get("returnValue"));
            }
        } catch (Exception e) {
            DeviceLog.exception("Error getting default return value", e);
        }
        return obj;
    }

    private Class[] getTypes(JSONArray jSONArray) throws ClassNotFoundException {
        if (jSONArray == null) {
            return null;
        }
        Class[] clsArr = new Class[jSONArray.length()];
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                if (jSONArray.get(i) instanceof JSONObject) {
                    Class<?> cls = Class.forName(((JSONObject) jSONArray.get(i)).getString("className"));
                    if (cls != null) {
                        clsArr[i] = cls;
                    }
                } else {
                    clsArr[i] = getPrimitiveClass(jSONArray.get(i).getClass());
                }
            }
        }
        return clsArr;
    }

    private Object[] getValues(JSONArray jSONArray) throws JSONException {
        Class<?> cls;
        if (jSONArray == null) {
            return null;
        }
        Object[] objArr = new Object[jSONArray.length()];
        Object[] objArr2 = new Object[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            if (jSONArray.get(i) instanceof JSONObject) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                Object obj = jSONObject.get("value");
                String string = jSONObject.getString(e.r);
                String string2 = jSONObject.has("className") ? jSONObject.getString("className") : null;
                if (string2 != null && string.equals("Enum") && (cls = Class.forName(string2)) != null) {
                    objArr2[i] = Enum.valueOf(cls, (String) obj);
                }
            } else {
                objArr2[i] = jSONArray.get(i);
            }
        }
        if (jSONArray != null) {
            System.arraycopy(objArr2, 0, objArr, 0, jSONArray.length());
        }
        return objArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasReturnValue(String str) {
        try {
            JSONObject jSONObject = this._eventSettings;
            if (jSONObject == null || !jSONObject.has(str)) {
                return false;
            }
            return this._eventSettings.getJSONObject(str).has("returnValue");
        } catch (Exception e) {
            DeviceLog.exception("Error getting default return value", e);
            return false;
        }
    }

    private Object setTargetSettings(Object obj, JSONObject jSONObject) {
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    JSONArray jSONArray = jSONObject.getJSONArray(next);
                    obj.getClass().getMethod(next, getTypes(jSONArray)).invoke(obj, getValues(jSONArray));
                } catch (Exception e) {
                    addErroredSetting(next, e.getMessage());
                    DeviceLog.exception("Setting errored", e);
                }
            }
        }
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldCallSuper(String str) {
        try {
            JSONObject jSONObject = this._eventSettings;
            if (jSONObject != null && jSONObject.has(str) && this._eventSettings.getJSONObject(str).has("callSuper")) {
                return this._eventSettings.getJSONObject(str).getBoolean("callSuper");
            }
            return true;
        } catch (Exception e) {
            DeviceLog.exception("Error getting super call status", e);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldSendEvent(String str) {
        try {
            JSONObject jSONObject = this._eventSettings;
            if (jSONObject != null && jSONObject.has(str) && this._eventSettings.getJSONObject(str).has("sendEvent")) {
                return this._eventSettings.getJSONObject(str).getBoolean("sendEvent");
            }
            return false;
        } catch (Exception e) {
            DeviceLog.exception("Error getting send event status", e);
            return false;
        }
    }

    public Map getErroredSettings() {
        return this._erroredSettings;
    }

    public Class getPrimitiveClass(Class cls) {
        String name = cls.getName();
        return name.equals("java.lang.Byte") ? Byte.TYPE : name.equals("java.lang.Short") ? Short.TYPE : name.equals("java.lang.Integer") ? Integer.TYPE : name.equals("java.lang.Long") ? Long.TYPE : name.equals("java.lang.Character") ? Character.TYPE : name.equals("java.lang.Float") ? Float.TYPE : name.equals("java.lang.Double") ? Double.TYPE : name.equals("java.lang.Boolean") ? Boolean.TYPE : name.equals("java.lang.Void") ? Void.TYPE : cls;
    }

    public void invokeJavascript(String str) {
        Utilities.runOnUiThread(new JavaScriptInvocation(str, this));
    }

    public void sendEvent(JSONArray jSONArray) {
        invokeJavascript("javascript:window.nativebridge.receiveEvent(" + jSONArray.toString() + ")");
    }

    public void setEventSettings(JSONObject jSONObject) {
        this._eventSettings = jSONObject;
    }

    public void setSettings(JSONObject jSONObject, JSONObject jSONObject2) {
        Map map = this._erroredSettings;
        if (map != null) {
            map.clear();
        }
        setTargetSettings(getSettings(), jSONObject);
        setTargetSettings(this, jSONObject2);
    }
}
