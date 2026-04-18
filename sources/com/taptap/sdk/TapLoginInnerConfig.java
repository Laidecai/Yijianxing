package com.taptap.sdk;

import com.tds.common.tracker.constants.CommonParam;

/* JADX INFO: loaded from: classes.dex */
public class TapLoginInnerConfig {
    private static String clientId;
    public static String codeVerifier;
    private static RegionType regionType = RegionType.CN;
    public static boolean roundCorner = true;
    public static boolean isPortrait = true;
    public static boolean includeAntiAddictionIfUsed = true;

    public static synchronized void setClientId(String str) {
        clientId = str;
    }

    public static synchronized String getClientId() {
        Validate.notNull(clientId, CommonParam.CLIENT_ID);
        return clientId;
    }

    public static synchronized void setRegionType(RegionType regionType2) {
        regionType = regionType2;
    }

    public static synchronized RegionType getRegionType() {
        return regionType;
    }
}
