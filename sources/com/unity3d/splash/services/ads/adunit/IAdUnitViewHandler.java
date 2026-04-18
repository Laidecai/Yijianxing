package com.unity3d.splash.services.ads.adunit;

import android.os.Bundle;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface IAdUnitViewHandler {
    boolean create(AdUnitActivity adUnitActivity);

    boolean destroy();

    View getView();

    void onCreate(AdUnitActivity adUnitActivity, Bundle bundle);

    void onDestroy(AdUnitActivity adUnitActivity);

    void onPause(AdUnitActivity adUnitActivity);

    void onResume(AdUnitActivity adUnitActivity);

    void onStart(AdUnitActivity adUnitActivity);

    void onStop(AdUnitActivity adUnitActivity);
}
