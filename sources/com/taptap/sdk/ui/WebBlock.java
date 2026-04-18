package com.taptap.sdk.ui;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.alipay.sdk.cons.c;
import com.alipay.sdk.encrypt.a;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.LoginResponse;
import com.taptap.sdk.R;
import com.taptap.sdk.TapLoginInnerConfig;
import com.taptap.sdk.TapLoginWithCode;
import com.taptap.sdk.Utils;
import com.tds.common.localize.LocalizeManager;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.common.utils.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.text.Typography;
import org.json.JSONException;
import org.json.JSONObject;
import tds.androidx.recyclerview.widget.ItemTouchHelper;

/* JADX INFO: loaded from: classes.dex */
public class WebBlock extends Block {
    private IWebLoginCallback callback;
    private String codeVerifier;
    private FrameLayout container;
    int cornerRadius;
    ValueAnimator loadingAnimator1;
    ValueAnimator loadingAnimator2;
    private ImageView mClose;
    private WebView mWebView;
    private ProgressBar progressBar;
    LoginRequest request;
    private FrameLayout webContainer;
    private int landscapeWidth = -1;
    private int portraitHeight = -1;

    public interface IWebLoginCallback {
        void onResponse(LoginResponse loginResponse, String str);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ Activity getActivity() {
        return super.getActivity();
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ BlockManager getBlockManager() {
        return super.getBlockManager();
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i) {
        super.startActivityForResult(intent, i);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i, Bundle bundle) {
        super.startActivityForResult(intent, i, bundle);
    }

    public void setLoginCallback(IWebLoginCallback iWebLoginCallback) {
        this.callback = iWebLoginCallback;
    }

    @Override // com.taptap.sdk.ui.Block
    protected View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_webview_login, viewGroup, false);
    }

    @Override // com.taptap.sdk.ui.Block
    protected void onViewCreated(View view) {
        this.cornerRadius = (int) ((view.getResources().getDisplayMetrics().density * 10.0f) + 0.5f);
        this.container = (FrameLayout) view.findViewById(R.id.container);
        this.webContainer = (FrameLayout) view.findViewById(R.id.web_container);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress);
        this.mWebView = (WebView) view.findViewById(R.id.webview);
        this.mClose = (ImageView) view.findViewById(R.id.close);
        int screenMax = (int) (getScreenMax(getActivity()) * 0.6f);
        int screenMax2 = (int) (getScreenMax(getActivity()) * 0.8f);
        if (screenMax != -1) {
            this.landscapeWidth = screenMax;
        }
        if (screenMax2 != -1) {
            this.portraitHeight = screenMax2;
        }
        this.mClose.setOnClickListener(new View.OnClickListener() { // from class: com.taptap.sdk.ui.WebBlock.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                WebBlock.this.getActivity().finish();
            }
        });
        initWebView();
        resetLayout(getActivity().getResources().getConfiguration());
        this.request = (LoginRequest) getArguments().getParcelable("request");
        this.codeVerifier = CodeUtil.getCodeVerifier(128);
        HashMap map = new HashMap();
        map.put(CommonParam.CLIENT_ID, TapLoginInnerConfig.getClientId());
        map.put("response_type", NetworkStateModel.PARAM_CODE);
        map.put(CommonParam.VERSION, "3.29.0");
        map.put("platform", "android");
        StringBuilder sb = new StringBuilder();
        if (this.request.getPermissions() != null) {
            for (int i = 0; i < this.request.getPermissions().length; i++) {
                if (i == this.request.getPermissions().length - 1) {
                    sb.append(this.request.getPermissions()[i]);
                } else {
                    sb.append(this.request.getPermissions()[i]);
                    sb.append(",");
                }
            }
        }
        map.put("scope", sb.toString());
        map.put("redirect_uri", "tapoauth://authorize");
        map.put("state", this.request.getState());
        map.put("code_challenge", CodeUtil.getCodeChallenge(this.codeVerifier));
        map.put("code_challenge_method", "S256");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(CommonParam.DEVICE_ID, Build.MANUFACTURER + " " + Build.MODEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("info", jSONObject.toString());
        if (!TextUtils.isEmpty(this.request.getPhoneVerifyToken())) {
            map.put("x_phone_verify_token", this.request.getPhoneVerifyToken());
        }
        if (!TextUtils.isEmpty(this.request.getPreferredLoginType())) {
            map.put("x_login_type", this.request.getPreferredLoginType());
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("preapproved", this.request.isPreApproved() ? 1 : 0);
            map.put("extra", jSONObject2.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        String str = TapLoginInnerConfig.getRegionType().authorizeUrl() + "?" + convertHashMapToParameters(map);
        syncCookie();
        this.mWebView.getSettings().setUserAgentString("TapTapAndroidSDK/3.29.0 " + this.mWebView.getSettings().getUserAgentString());
        HashMap map2 = new HashMap();
        map2.put("X-SDK-UA", this.request.getInfo());
        this.mWebView.loadUrl(str, map2);
        startLoadingAnimation();
    }

    private void startLoadingAnimation() {
        this.progressBar.setMax(1000);
        ValueAnimator duration = ValueAnimator.ofInt(0, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION).setDuration(800L);
        this.loadingAnimator1 = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.taptap.sdk.ui.WebBlock.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                WebBlock.this.progressBar.setProgress(iIntValue);
                if (iIntValue == 200) {
                    WebBlock.this.startSecondLoadingAnimation();
                }
            }
        });
        this.loadingAnimator1.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSecondLoadingAnimation() {
        ValueAnimator duration = ValueAnimator.ofInt(this.progressBar.getProgress(), 800).setDuration(10000L);
        this.loadingAnimator2 = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.taptap.sdk.ui.WebBlock.3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                WebBlock.this.progressBar.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.loadingAnimator2.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endLoadingAnimation() {
        ValueAnimator valueAnimator = this.loadingAnimator1;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.loadingAnimator1.cancel();
        }
        ValueAnimator valueAnimator2 = this.loadingAnimator2;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.loadingAnimator2.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofInt(this.progressBar.getProgress(), 1000).setDuration(500L);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.taptap.sdk.ui.WebBlock.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator3) {
                int iIntValue = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                WebBlock.this.progressBar.setProgress(iIntValue);
                if (iIntValue == 1000) {
                    WebBlock.this.progressBar.setVisibility(8);
                }
            }
        });
        duration.start();
    }

    private void syncCookie() {
        CookieSyncManager.createInstance(getActivity());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie("https://www.xdrnd.com/", "skip_captcha=1");
        if (Build.VERSION.SDK_INT >= 21) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    public static String convertHashMapToParameters(HashMap<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append(a.h);
                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                if (i != map.size() - 1) {
                    sb.append(Typography.amp);
                }
                i++;
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initWebView() {
        if (TapLoginInnerConfig.roundCorner && Build.VERSION.SDK_INT >= 21) {
            this.mWebView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.taptap.sdk.ui.WebBlock.5
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), WebBlock.this.cornerRadius);
                }
            });
            this.mWebView.setClipToOutline(true);
        }
        this.mWebView.setDownloadListener(new DownloadListener() { // from class: com.taptap.sdk.ui.WebBlock.6
            @Override // android.webkit.DownloadListener
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                try {
                    WebBlock.this.getActivity().startActivity(intent);
                } catch (Exception unused) {
                }
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.taptap.sdk.ui.WebBlock.7
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, final String str) {
                if (!str.toLowerCase(Locale.US).startsWith("tapoauth")) {
                    return false;
                }
                Uri uri = Uri.parse(str);
                String queryParameter = uri.getQueryParameter("error");
                String queryParameter2 = uri.getQueryParameter(NetworkStateModel.PARAM_CODE);
                String queryParameter3 = uri.getQueryParameter(c.e);
                String queryParameter4 = uri.getQueryParameter("state");
                if (TextUtils.isEmpty(queryParameter)) {
                    TapLoginWithCode.loginWithCode(queryParameter2, WebBlock.this.codeVerifier, queryParameter4, queryParameter3, new TapLoginWithCode.LoginResultCallBack() { // from class: com.taptap.sdk.ui.WebBlock.7.1
                        @Override // com.taptap.sdk.TapLoginWithCode.LoginResultCallBack
                        public void onLoginResult(LoginResponse loginResponse) {
                            if (WebBlock.this.callback != null) {
                                WebBlock.this.callback.onResponse(loginResponse, str);
                            }
                        }
                    });
                    return true;
                }
                LoginResponse loginResponse = new LoginResponse(null, queryParameter4, queryParameter, null, false);
                if (WebBlock.this.callback != null) {
                    WebBlock.this.callback.onResponse(loginResponse, str);
                }
                return true;
            }

            @Override // android.webkit.WebViewClient
            public void onLoadResource(WebView webView, String str) {
                super.onLoadResource(webView, str);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                WebBlock.this.endLoadingAnimation();
                WebBlock.this.injectJsInterface();
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
            }
        });
        this.mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = this.mWebView.getSettings();
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowFileAccess(false);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setCacheMode(2);
        this.mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        this.mWebView.removeJavascriptInterface("accessibility");
        this.mWebView.removeJavascriptInterface("accessibilityTraversal");
        this.mWebView.addJavascriptInterface(new urlResource(), "urlResource");
        this.mWebView.setOverScrollMode(2);
        this.mWebView.setVerticalScrollBarEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectJsInterface() {
        this.mWebView.loadUrl("javascript:window.TapTapAPI = function(action, params) {return window.urlResource.TapTapAPI(action, params);}");
        this.mWebView.loadUrl("javascript:window.TapTapAPI.openBrowser = function(param){return window.TapTapAPI('openBrowser', param)}");
        this.mWebView.loadUrl("javascript:window.TapTapAPI.tapEnv = function(param){return window.TapTapAPI('tapEnv', param)}");
        this.mWebView.loadUrl("javascript:window.TapTapAPI.getSDKInfo = function(param){return window.TapTapAPI('getSDKInfo', param)}");
    }

    final class urlResource {
        urlResource() {
        }

        @JavascriptInterface
        public String TapTapAPI(String str, final String str2) {
            if (!TextUtils.isEmpty(str) && WebBlock.this.mWebView != null) {
                if ("openBrowser".equals(str)) {
                    WebBlock.this.mWebView.post(new Runnable() { // from class: com.taptap.sdk.ui.WebBlock.urlResource.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                WebBlock.this.getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str2)));
                            } catch (Exception unused) {
                            }
                        }
                    });
                } else {
                    if ("tapEnv".equals(str)) {
                        return WebBlock.getTapEnv();
                    }
                    if ("getSDKInfo".equals(str)) {
                        return WebBlock.this.request == null ? "" : WebBlock.this.request.getInfo();
                    }
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getTapEnv() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("MANUFACTURER", String.valueOf(Build.MANUFACTURER));
            jSONObject.put("MODEL", String.valueOf(Build.MODEL));
            jSONObject.put("VERSION_RELEASE", String.valueOf(Build.VERSION.RELEASE));
            jSONObject.put("VERSION_SDK_INT", String.valueOf(Build.VERSION.SDK_INT));
            jSONObject.put("VN_CODE", String.valueOf(32900001));
            jSONObject.put("VN_NAME", "3.29.0");
            jSONObject.put("LOC", CommonUtils.getCurrentLocale().getDisplayName());
            jSONObject.put(com.tds.common.log.constants.CommonParam.LANG, LocalizeManager.getPreferredLanguageString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    @Override // com.taptap.sdk.ui.Block
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        resetLayout(configuration);
    }

    private void resetLayout(Configuration configuration) {
        if (configuration.orientation == 2) {
            if (this.landscapeWidth != -1) {
                ViewGroup.LayoutParams layoutParams = this.container.getLayoutParams();
                layoutParams.width = this.landscapeWidth + Utils.dip2px(getActivity(), 84.0f);
                layoutParams.height = -1;
                this.container.setLayoutParams(layoutParams);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.webContainer.getLayoutParams();
                int iDip2px = Utils.dip2px(getActivity(), 16.0f);
                int iDip2px2 = Utils.dip2px(getActivity(), 42.0f);
                marginLayoutParams.topMargin = iDip2px;
                marginLayoutParams.leftMargin = iDip2px2;
                marginLayoutParams.rightMargin = iDip2px2;
                marginLayoutParams.bottomMargin = iDip2px;
                this.webContainer.setLayoutParams(marginLayoutParams);
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mClose.getLayoutParams();
                layoutParams2.gravity = 53;
                this.mClose.setLayoutParams(layoutParams2);
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
                marginLayoutParams2.setMargins(Utils.dip2px(getActivity(), 4.0f), iDip2px, 0, 0);
                this.mClose.setLayoutParams(marginLayoutParams2);
                return;
            }
            return;
        }
        if (configuration.orientation != 1 || this.portraitHeight == -1) {
            return;
        }
        ViewGroup.LayoutParams layoutParams3 = this.container.getLayoutParams();
        layoutParams3.height = this.portraitHeight + Utils.dip2px(getActivity(), 38.0f);
        layoutParams3.width = -1;
        this.container.setLayoutParams(layoutParams3);
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.webContainer.getLayoutParams();
        int iDip2px3 = Utils.dip2px(getActivity(), 20.0f);
        marginLayoutParams3.topMargin = Utils.dip2px(getActivity(), 38.0f);
        marginLayoutParams3.leftMargin = iDip2px3;
        marginLayoutParams3.rightMargin = iDip2px3;
        marginLayoutParams3.bottomMargin = iDip2px3;
        this.webContainer.setLayoutParams(marginLayoutParams3);
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) this.mClose.getLayoutParams();
        layoutParams4.gravity = 53;
        this.mClose.setLayoutParams(layoutParams4);
        ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
        marginLayoutParams4.setMargins(0, 0, iDip2px3, 0);
        this.mClose.setLayoutParams(marginLayoutParams4);
    }

    public static int getScreenMax(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager == null) {
            return -1;
        }
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return Math.max(displayMetrics.heightPixels, displayMetrics.widthPixels);
    }
}
