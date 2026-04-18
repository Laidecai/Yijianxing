package com.unity3d.splash.services.ads.placement;

import com.unity3d.splash.UnityAds;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class Placement {
    private static String _defaultBannerPlacement;
    private static String _defaultPlacement;
    private static HashMap _placementReadyMap;

    private static UnityAds.PlacementState currentState(String str) {
        HashMap map = _placementReadyMap;
        return (map == null || !map.containsKey(str)) ? UnityAds.PlacementState.NOT_AVAILABLE : (UnityAds.PlacementState) _placementReadyMap.get(str);
    }

    public static String getDefaultBannerPlacement() {
        return _defaultBannerPlacement;
    }

    public static String getDefaultPlacement() {
        return _defaultPlacement;
    }

    public static UnityAds.PlacementState getPlacementState() {
        String str = _defaultPlacement;
        return str == null ? UnityAds.PlacementState.NOT_AVAILABLE : getPlacementState(str);
    }

    public static UnityAds.PlacementState getPlacementState(String str) {
        return currentState(str);
    }

    public static boolean isReady() {
        return getPlacementState() == UnityAds.PlacementState.READY;
    }

    public static boolean isReady(String str) {
        return getPlacementState(str) == UnityAds.PlacementState.READY;
    }

    public static void reset() {
        _placementReadyMap = null;
        _defaultPlacement = null;
    }

    public static void setDefaultBannerPlacement(String str) {
        _defaultBannerPlacement = str;
    }

    public static void setDefaultPlacement(String str) {
        _defaultPlacement = str;
    }

    public static void setPlacementState(String str, String str2) {
        if (_placementReadyMap == null) {
            _placementReadyMap = new HashMap();
        }
        _placementReadyMap.put(str, UnityAds.PlacementState.valueOf(str2));
    }
}
