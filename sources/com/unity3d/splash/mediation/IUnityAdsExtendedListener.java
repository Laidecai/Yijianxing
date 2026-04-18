package com.unity3d.splash.mediation;

import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds;

/* JADX INFO: loaded from: classes.dex */
public interface IUnityAdsExtendedListener extends IUnityAdsListener {
    void onUnityAdsClick(String str);

    void onUnityAdsPlacementStateChanged(String str, UnityAds.PlacementState placementState, UnityAds.PlacementState placementState2);
}
