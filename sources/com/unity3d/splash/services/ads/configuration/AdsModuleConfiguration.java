package com.unity3d.splash.services.ads.configuration;

import android.os.ConditionVariable;
import com.tds.common.tracker.annotations.Login;
import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.services.ads.adunit.VideoPlayerHandler;
import com.unity3d.splash.services.ads.adunit.WebPlayerHandler;
import com.unity3d.splash.services.ads.adunit.WebViewHandler;
import com.unity3d.splash.services.ads.api.AdUnit;
import com.unity3d.splash.services.ads.api.AdsProperties;
import com.unity3d.splash.services.ads.api.Listener;
import com.unity3d.splash.services.ads.api.Placement;
import com.unity3d.splash.services.ads.api.VideoPlayer;
import com.unity3d.splash.services.ads.webplayer.api.WebPlayer;
import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AdsModuleConfiguration implements IAdsModuleConfiguration {
    private InetAddress _address;

    @Override // com.unity3d.splash.services.ads.configuration.IAdsModuleConfiguration
    public Map getAdUnitViewHandlers() {
        HashMap map = new HashMap();
        map.put("videoplayer", VideoPlayerHandler.class);
        map.put("webplayer", WebPlayerHandler.class);
        map.put(Login.WEBVIEW_LOGIN_TYPE, WebViewHandler.class);
        return map;
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public Class[] getWebAppApiClassList() {
        return new Class[]{AdUnit.class, Listener.class, VideoPlayer.class, Placement.class, WebPlayer.class, AdsProperties.class};
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean initCompleteState(Configuration configuration) {
        return true;
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean initErrorState(Configuration configuration, String str, String str2) {
        final String str3 = "Init failed in " + str;
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.configuration.AdsModuleConfiguration.3
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = com.unity3d.splash.services.ads.properties.AdsProperties.getListeners().iterator();
                while (it.hasNext()) {
                    ((IUnityAdsListener) it.next()).onUnityAdsError(UnityAds.UnityAdsError.INITIALIZE_FAILED, str3);
                }
            }
        });
        return true;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.unity3d.splash.services.ads.configuration.AdsModuleConfiguration$1] */
    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean initModuleState(Configuration configuration) {
        InetAddress inetAddress;
        DeviceLog.debug("Unity Ads init: checking for ad blockers");
        try {
            final String host = new URL(configuration.getConfigUrl()).getHost();
            final ConditionVariable conditionVariable = new ConditionVariable();
            new Thread() { // from class: com.unity3d.splash.services.ads.configuration.AdsModuleConfiguration.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        AdsModuleConfiguration.this._address = InetAddress.getByName(host);
                        conditionVariable.open();
                    } catch (Exception e) {
                        DeviceLog.exception("Couldn't get address. Host: " + host, e);
                        conditionVariable.open();
                    }
                }
            }.start();
            if (conditionVariable.block(2000L) && (inetAddress = this._address) != null && inetAddress.isLoopbackAddress()) {
                DeviceLog.error("Unity Ads init: halting init because Unity Ads config resolves to loopback address (due to ad blocker?)");
                Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.configuration.AdsModuleConfiguration.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = com.unity3d.splash.services.ads.properties.AdsProperties.getListeners().iterator();
                        while (it.hasNext()) {
                            ((IUnityAdsListener) it.next()).onUnityAdsError(UnityAds.UnityAdsError.AD_BLOCKER_DETECTED, "Unity Ads config server resolves to loopback address (due to ad blocker?)");
                        }
                    }
                });
                return false;
            }
        } catch (MalformedURLException unused) {
        }
        return true;
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean resetState(Configuration configuration) {
        com.unity3d.splash.services.ads.placement.Placement.reset();
        return true;
    }
}
