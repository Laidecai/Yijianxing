package com.tds.common;

import com.tds.common.entities.TapConfig;
import com.tds.common.localize.LocalizeManager;
import com.tds.common.tracker.SdkDurationStatistics;
import com.tds.common.utils.GUIDHelper;

/* JADX INFO: loaded from: classes.dex */
public class TapCommon {
    private static ITapCommon tapCommon = new TapCommonImpl();

    public static void init(TapConfig tapConfig) {
        tapCommon.init(tapConfig);
        GUIDHelper.INSTANCE.init(tapConfig.appContext);
        SdkDurationStatistics.init(tapConfig.appContext, tapConfig.clientId, tapConfig.clientToken, tapConfig.regionType);
        LocalizeManager.configSDKLocalizeWith(tapConfig.regionType);
    }

    public static TapConfig getTapConfig() {
        return tapCommon.getTapConfig();
    }

    public static boolean isTapCommonInitialized() {
        ITapCommon iTapCommon = tapCommon;
        if (iTapCommon != null) {
            return iTapCommon.isInitialized();
        }
        return false;
    }

    public static void setDurationStatisticsEnabled(boolean z) {
        tapCommon.setDurationStatisticsEnabled(z);
    }
}
