package com.tds.common.browser;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import com.tds.common.TapCommon;
import com.tds.common.browser.GarbageCollectionHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class PreLoader {
    private static final int CACHED_WEBVIEW_MAX_NUM = 1;
    private static final String EMPTY_WEBVIEW = "empty_webview";
    private static String TAG = "Preload";
    private static long cachedExpiredTime = 60000;
    private Handler handler;
    private HandlerThread handlerThread;
    private static final Map<String, CachedWebView> cachedWebViewMap = new ConcurrentHashMap();
    private static final Map<CachedWebView, String> cachedUrlMap = new ConcurrentHashMap();

    private PreLoader() {
    }

    private static class Holder {
        private static final PreLoader INSTANCE = new PreLoader();

        private Holder() {
        }
    }

    public void setCachedTime(long j) {
        cachedExpiredTime = j;
    }

    public static PreLoader getInstance() {
        return Holder.INSTANCE;
    }

    public void preload() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.tds.common.browser.PreLoader.1
            @Override // android.os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                if (PreLoader.cachedWebViewMap.size() >= 1) {
                    return false;
                }
                CachedWebView cachedWebViewPrepareWebView = PreLoader.this.prepareWebView();
                PreLoader.cachedWebViewMap.put(PreLoader.EMPTY_WEBVIEW, cachedWebViewPrepareWebView);
                PreLoader.cachedUrlMap.put(cachedWebViewPrepareWebView, PreLoader.EMPTY_WEBVIEW);
                return false;
            }
        });
    }

    public void preload(final String str) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.tds.common.browser.PreLoader.2
            @Override // android.os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                if (PreLoader.cachedWebViewMap.size() >= 1) {
                    return false;
                }
                CachedWebView cachedWebViewPrepareWebView = PreLoader.this.prepareWebView(str);
                PreLoader.cachedWebViewMap.put(str, cachedWebViewPrepareWebView);
                PreLoader.cachedUrlMap.put(cachedWebViewPrepareWebView, str);
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CachedWebView prepareWebView() {
        return prepareWebView("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CachedWebView prepareWebView(String str) {
        if (!URLUtil.isNetworkUrl(str) || URLUtil.isJavaScriptUrl(str)) {
            str = "";
        }
        if (TapCommon.getTapConfig() == null || TapCommon.getTapConfig().appContext == null) {
            throw new IllegalArgumentException(TAG + " createWebView params appContext can't be null");
        }
        Map<String, CachedWebView> map = cachedWebViewMap;
        if (map != null && map.get(str) != null) {
            return map.get(str);
        }
        CachedWebView cachedWebView = new CachedWebView(new MutableContextWrapper(TapCommon.getTapConfig().appContext));
        if (str != null && str.length() > 0) {
            cachedWebView.preload(str);
        }
        return cachedWebView;
    }

    public CachedWebView attachCachedView(ViewGroup viewGroup, Context context) {
        return attachCachedView(viewGroup, context, "");
    }

    public CachedWebView attachCachedView(ViewGroup viewGroup, Context context, String str) {
        CachedWebView webView;
        synchronized (PreLoader.class) {
            webView = getWebView(context, str);
            if (webView != null && webView.getParent() != null) {
                detachCachedView(webView);
            }
            viewGroup.addView(webView);
        }
        return webView;
    }

    public void detachCachedView(WebView webView) {
        synchronized (PreLoader.class) {
            if (webView.getParent() != null) {
                ((ViewGroup) webView.getParent()).removeView(webView);
            }
            removeCachedWebView(webView);
        }
    }

    private CachedWebView getWebView(Context context) {
        return getWebView(context, "");
    }

    private CachedWebView getWebView(Context context, String str) {
        CachedWebView cachedWebView;
        Map<String, CachedWebView> map = cachedWebViewMap;
        if (map.isEmpty() || ((str.isEmpty() && !map.containsKey(EMPTY_WEBVIEW)) || !map.containsKey(str))) {
            CachedWebView cachedWebViewPrepareWebView = prepareWebView(str);
            ((MutableContextWrapper) cachedWebViewPrepareWebView.getContext()).setBaseContext(context);
            if (str.isEmpty()) {
                str = EMPTY_WEBVIEW;
            }
            map.put(str, cachedWebViewPrepareWebView);
            cachedUrlMap.put(cachedWebViewPrepareWebView, str);
            return cachedWebViewPrepareWebView;
        }
        if (str.length() == 0) {
            cachedWebView = map.get(EMPTY_WEBVIEW);
        } else {
            cachedWebView = map.get(str);
        }
        if (cachedWebView == null) {
            return null;
        }
        ((MutableContextWrapper) cachedWebView.getContext()).setBaseContext(context);
        return cachedWebView;
    }

    private void bindGarbageCollector() {
        unBindGarbageCollector();
        HandlerThread handlerThread = new HandlerThread("cachedWebViewCollectionThread", 10);
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.handler = new GarbageCollectionHandler(this.handlerThread.getLooper(), new GarbageCollectionHandler.AttachFilter() { // from class: com.tds.common.browser.PreLoader.3
            @Override // com.tds.common.browser.GarbageCollectionHandler.AttachFilter
            public boolean attached(View view) {
                return view != null && view.isAttachedToWindow();
            }
        }, new GarbageCollectionHandler.RecycleCallback() { // from class: com.tds.common.browser.PreLoader.4
            @Override // com.tds.common.browser.GarbageCollectionHandler.RecycleCallback
            public void onRecycle(View view) {
                if ((view instanceof CachedWebView) && PreLoader.cachedWebViewMap.size() == 0) {
                    PreLoader.this.unBindGarbageCollector();
                }
            }
        }, cachedExpiredTime);
    }

    public void removeCachedWebView(WebView webView) {
        if (webView instanceof CachedWebView) {
            Map<CachedWebView, String> map = cachedUrlMap;
            if (map.size() <= 0 || !map.containsKey(webView)) {
                return;
            }
            String strRemove = map.remove(webView);
            if (strRemove != null) {
                cachedWebViewMap.remove(strRemove);
            }
            webView.removeAllViews();
            webView.setTag(null);
            webView.clearHistory();
            webView.destroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unBindGarbageCollector() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        HandlerThread handlerThread = this.handlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.handler = null;
        this.handlerThread = null;
    }
}
