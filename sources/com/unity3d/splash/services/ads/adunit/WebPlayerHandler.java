package com.unity3d.splash.services.ads.adunit;

import android.os.Bundle;
import android.view.View;
import com.unity3d.splash.services.ads.webplayer.WebPlayer;
import com.unity3d.splash.services.ads.webplayer.WebPlayerSettingsCache;
import com.unity3d.splash.services.core.misc.ViewUtilities;

/* JADX INFO: loaded from: classes.dex */
public class WebPlayerHandler implements IAdUnitViewHandler {
    private WebPlayer _webPlayer;

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public boolean create(AdUnitActivity adUnitActivity) {
        if (this._webPlayer != null) {
            return true;
        }
        WebPlayerSettingsCache webPlayerSettingsCache = WebPlayerSettingsCache.getInstance();
        WebPlayer webPlayer = new WebPlayer(adUnitActivity, "webplayer", webPlayerSettingsCache.getWebSettings("webplayer"), webPlayerSettingsCache.getWebPlayerSettings("webplayer"));
        this._webPlayer = webPlayer;
        webPlayer.setEventSettings(webPlayerSettingsCache.getWebPlayerEventSettings("webplayer"));
        return true;
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public boolean destroy() {
        WebPlayer webPlayer = this._webPlayer;
        if (webPlayer != null) {
            ViewUtilities.removeViewFromParent(webPlayer);
            this._webPlayer.destroy();
        }
        this._webPlayer = null;
        return true;
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public View getView() {
        return this._webPlayer;
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onCreate(AdUnitActivity adUnitActivity, Bundle bundle) {
        create(adUnitActivity);
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onDestroy(AdUnitActivity adUnitActivity) {
        if (adUnitActivity.isFinishing()) {
            destroy();
        }
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onPause(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onResume(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onStart(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onStop(AdUnitActivity adUnitActivity) {
    }
}
