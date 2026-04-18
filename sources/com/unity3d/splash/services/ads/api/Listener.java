package com.unity3d.splash.services.ads.api;

import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.mediation.IUnityAdsExtendedListener;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class Listener {
    @WebViewExposed
    public static void sendClickEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.Listener.4
            @Override // java.lang.Runnable
            public final void run() {
                for (IUnityAdsListener iUnityAdsListener : com.unity3d.splash.services.ads.properties.AdsProperties.getListeners()) {
                    if (iUnityAdsListener instanceof IUnityAdsExtendedListener) {
                        ((IUnityAdsExtendedListener) iUnityAdsListener).onUnityAdsClick(str);
                    }
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendErrorEvent(final String str, final String str2, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.Listener.6
            @Override // java.lang.Runnable
            public final void run() {
                for (IUnityAdsListener iUnityAdsListener : com.unity3d.splash.services.ads.properties.AdsProperties.getListeners()) {
                    if (iUnityAdsListener instanceof IUnityAdsExtendedListener) {
                        iUnityAdsListener.onUnityAdsError(UnityAds.UnityAdsError.valueOf(str), str2);
                    }
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendFinishEvent(final String str, final String str2, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.Listener.3
            @Override // java.lang.Runnable
            public final void run() {
                Iterator it = com.unity3d.splash.services.ads.properties.AdsProperties.getListeners().iterator();
                while (it.hasNext()) {
                    ((IUnityAdsListener) it.next()).onUnityAdsFinish(str, UnityAds.FinishState.valueOf(str2));
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendPlacementStateChangedEvent(final String str, final String str2, final String str3, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.Listener.5
            @Override // java.lang.Runnable
            public final void run() {
                for (IUnityAdsListener iUnityAdsListener : com.unity3d.splash.services.ads.properties.AdsProperties.getListeners()) {
                    if (iUnityAdsListener instanceof IUnityAdsExtendedListener) {
                        ((IUnityAdsExtendedListener) iUnityAdsListener).onUnityAdsPlacementStateChanged(str, UnityAds.PlacementState.valueOf(str2), UnityAds.PlacementState.valueOf(str3));
                    }
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendReadyEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.Listener.1
            @Override // java.lang.Runnable
            public final void run() {
                Iterator it = com.unity3d.splash.services.ads.properties.AdsProperties.getListeners().iterator();
                while (it.hasNext()) {
                    ((IUnityAdsListener) it.next()).onUnityAdsReady(str);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendStartEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.Listener.2
            @Override // java.lang.Runnable
            public final void run() {
                Iterator it = com.unity3d.splash.services.ads.properties.AdsProperties.getListeners().iterator();
                while (it.hasNext()) {
                    ((IUnityAdsListener) it.next()).onUnityAdsStart(str);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }
}
