package com.unity3d.splash;

import com.unity3d.splash.UnityAds;

/* JADX INFO: loaded from: classes.dex */
public interface IUnityAdsListener {
    void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String str);

    void onUnityAdsFinish(String str, UnityAds.FinishState finishState);

    void onUnityAdsReady(String str);

    void onUnityAdsStart(String str);
}
