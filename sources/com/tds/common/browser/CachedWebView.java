package com.tds.common.browser;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tds.common.browser.WebFileCache;
import com.tds.common.io.IoUtil;
import com.tds.common.net.util.HttpUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import tds.androidx.recyclerview.widget.ItemTouchHelper;

/* JADX INFO: loaded from: classes.dex */
public class CachedWebView extends WebView {
    private static final String CACHE_FILE_SYMBOL = "useSdkFileCache";
    private static final String CACHE_USE_ENABLE = "1";
    public static final int PRELOAD_STATE_COMPLETED = 2;
    public static final int PRELOAD_STATE_ERROR = 3;
    public static final int PRELOAD_STATE_STARTED = 1;
    public static final int PRELOAD_STATE_UNDEFINED = 0;
    private PreLoadStateListener preLoadStateListener;
    private int preloadState;
    private String preloadUrl;
    CachedWebChromeClientDelegate webChromeClientDelegate;
    CachedWebViewClientDelegate webViewClientDelegate;

    public interface CachedWebChromeClientDelegate {
        boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams);

        void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2);
    }

    public interface CachedWebViewClientDelegate {
        void onPageLoadingCompleted();

        void onReceivedError(WebView webView, int i, String str, String str2);

        void onReceivedTitle(String str);

        WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest);

        boolean shouldOverrideUrlLoading(String str);

        void showErrorView();
    }

    public interface PreLoadStateListener {
        void onPreloadStateChanged(int i);
    }

    public void registerPreLoadListener(PreLoadStateListener preLoadStateListener) {
        this.preLoadStateListener = preLoadStateListener;
    }

    public void unRegisterPreLoadListener() {
        this.preLoadStateListener = null;
    }

    public CachedWebView(Context context) {
        super(context);
        this.preloadState = 0;
        this.preloadUrl = "";
        init();
    }

    public CachedWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.preloadState = 0;
        this.preloadUrl = "";
        init();
    }

    public CachedWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.preloadState = 0;
        this.preloadUrl = "";
        init();
    }

    public CachedWebView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.preloadState = 0;
        this.preloadUrl = "";
        init();
    }

    public void preload(String str) {
        loadUrl(str);
        changePreloadState(1);
        this.preloadUrl = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePreloadState(int i) {
        this.preloadState = i;
        PreLoadStateListener preLoadStateListener = this.preLoadStateListener;
        if (preLoadStateListener != null) {
            preLoadStateListener.onPreloadStateChanged(i);
        }
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str) {
        int i = this.preloadState;
        if (i == 1 || i == 2) {
            return;
        }
        super.loadUrl(str);
    }

    @Override // android.webkit.WebView
    public void loadUrl(String str, Map<String, String> map) {
        int i = this.preloadState;
        if (i == 1 || i == 2) {
            return;
        }
        super.loadUrl(str, map);
    }

    public void setWebViewClientDelegate(CachedWebViewClientDelegate cachedWebViewClientDelegate) {
        this.webViewClientDelegate = cachedWebViewClientDelegate;
    }

    public void setWebChromeClientDelegate(CachedWebChromeClientDelegate cachedWebChromeClientDelegate) {
        this.webChromeClientDelegate = cachedWebChromeClientDelegate;
    }

    private void init() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        setWebViewClient(new WebViewClient() { // from class: com.tds.common.browser.CachedWebView.1
            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                String originalUrl = webView.getOriginalUrl();
                if (originalUrl != null && originalUrl.equals(CachedWebView.this.preloadUrl)) {
                    CachedWebView.this.changePreloadState(3);
                }
                if (CachedWebView.this.webViewClientDelegate != null) {
                    CachedWebView.this.webViewClientDelegate.onReceivedError(webView, i, str, str2);
                }
            }

            @Override // android.webkit.WebViewClient
            public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail renderProcessGoneDetail) {
                if (renderProcessGoneDetail.didCrash()) {
                    return false;
                }
                if (webView == null) {
                    return true;
                }
                ViewGroup viewGroup = (ViewGroup) webView.getParent();
                if (viewGroup != null && viewGroup.getChildCount() > 0) {
                    viewGroup.removeView(webView);
                }
                webView.destroy();
                return true;
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (CachedWebView.this.webViewClientDelegate == null || !CachedWebView.this.webViewClientDelegate.shouldOverrideUrlLoading(str)) {
                    return super.shouldOverrideUrlLoading(webView, str);
                }
                return true;
            }

            @Override // android.webkit.WebViewClient
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                WebResourceResponse webResourceResponseShouldInterceptRequest;
                if (CachedWebView.this.webViewClientDelegate != null && (webResourceResponseShouldInterceptRequest = CachedWebView.this.webViewClientDelegate.shouldInterceptRequest(webView, webResourceRequest)) != null) {
                    return webResourceResponseShouldInterceptRequest;
                }
                WebResourceResponse webResourceResponseCheckSdkCacheFileRequest = CachedWebView.this.checkSdkCacheFileRequest(webView, webResourceRequest);
                return webResourceResponseCheckSdkCacheFileRequest != null ? webResourceResponseCheckSdkCacheFileRequest : super.shouldInterceptRequest(webView, webResourceRequest);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (CachedWebView.this.webViewClientDelegate != null) {
                    CachedWebView.this.webViewClientDelegate.onPageLoadingCompleted();
                }
            }
        });
        setWebChromeClient(new WebChromeClient() { // from class: com.tds.common.browser.CachedWebView.2
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                String originalUrl;
                super.onProgressChanged(webView, i);
                if (webView == null || i != 100 || (originalUrl = webView.getOriginalUrl()) == null || !originalUrl.equals(CachedWebView.this.preloadUrl)) {
                    return;
                }
                CachedWebView.this.changePreloadState(2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WebResourceResponse checkSdkCacheFileRequest(WebView webView, WebResourceRequest webResourceRequest) {
        WebFileCache webFileCache;
        if (webResourceRequest != null && webView != null && HttpUtil.HTTP_METHOD_GET.equals(webResourceRequest.getMethod()) && webResourceRequest.getUrl() != null && "1".equals(webResourceRequest.getUrl().getQueryParameter(CACHE_FILE_SYMBOL)) && (webFileCache = WebFileCache.getInstance(webView.getContext())) != null) {
            try {
                final String string = webResourceRequest.getUrl().toString();
                File file = webFileCache.get(string);
                if (file == null || !file.exists()) {
                    webFileCache.put(string, new WebFileCache.Writer() { // from class: com.tds.common.browser.CachedWebView.3
                        @Override // com.tds.common.browser.WebFileCache.Writer
                        public boolean write(File file2) throws Throwable {
                            IoUtil.copy(new URL(string).openStream(), file2);
                            return true;
                        }
                    });
                    file = webFileCache.get(string);
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(string.substring(string.lastIndexOf("/") + 1, string.lastIndexOf("?")));
                HashMap map = new HashMap();
                map.put("Access-Control-Allow-Origin", "*");
                return new WebResourceResponse(contentTypeFor, "utf-8", ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, "success", map, bufferedInputStream);
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
