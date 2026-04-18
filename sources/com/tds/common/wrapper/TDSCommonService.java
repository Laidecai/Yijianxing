package com.tds.common.wrapper;

import android.app.Activity;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;

/* JADX INFO: loaded from: classes.dex */
@BridgeService("TDSCommonService")
public interface TDSCommonService extends IBridgeService {
    @BridgeMethod("addReplacedHostPair")
    void addHost(@BridgeParam("hostToBeReplaced") String str, @BridgeParam("replacedHost") String str2);

    @BridgeMethod("getDeviceId")
    void getDeviceId(Activity activity, BridgeCallback bridgeCallback);

    @BridgeMethod("getDeviceType")
    void getDeviceType(BridgeCallback bridgeCallback);

    @BridgeMethod("getRegionCode")
    void getRegionCode(Activity activity, BridgeCallback bridgeCallback);

    @BridgeMethod("initWithConfig")
    void init(Activity activity, @BridgeParam("initWithConfig") String str, @BridgeParam("versionName") String str2);

    @BridgeMethod("isTapGlobalInstalled")
    void isTapGlobalInstalled(Activity activity, BridgeCallback bridgeCallback);

    @BridgeMethod("isTapTapInstalled")
    void isTapTapInstalled(Activity activity, BridgeCallback bridgeCallback);

    @BridgeMethod("openReviewInTapGlobal")
    void openReviewInTapGlobal(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    @BridgeMethod("openReviewInTapTap")
    void openReviewInTapTap(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    @BridgeMethod("openWebDownloadUrl")
    void openWebDownloadUrl(Activity activity, @BridgeParam(BreakpointSQLiteKey.URL) String str, BridgeCallback bridgeCallback);

    @BridgeMethod("openWebDownloadUrlOfTapGlobal")
    void openWebDownloadUrlOfTapGlobal(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    @BridgeMethod("openWebDownloadUrlOfTapTap")
    void openWebDownloadUrlOfTapTap(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    void registerProperties(String str, TapPropertiesProxy tapPropertiesProxy);

    @BridgeMethod("setCurrentTdsId")
    void setCurrentTdsId(@BridgeParam("setCurrentTdsId") String str);

    @BridgeMethod("setDurationStatisticsEnabled")
    void setDurationStatisticsEnabled(@BridgeParam("setDurationStatisticsEnabled") boolean z);

    @BridgeMethod("setPreferredLanguage")
    void setPreferredLanguage(@BridgeParam("preferredLanguage") int i);

    @BridgeMethod("setXUA")
    void setXUA(@BridgeParam("setXUA") String str);

    @BridgeMethod("updateGameAndFailToWebInTapGlobal")
    void updateGameAndFailToWebInTapGlobal(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    @BridgeMethod("updateGameAndFailToWebInTapGlobalWithWebUrl")
    void updateGameAndFailToWebInTapGlobal(Activity activity, @BridgeParam("appId") String str, @BridgeParam("webUrl") String str2, BridgeCallback bridgeCallback);

    @BridgeMethod("updateGameAndFailToWebInTapTap")
    void updateGameAndFailToWebInTapTap(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    @BridgeMethod("updateGameAndFailToWebInTapTapWithWebUrl")
    void updateGameAndFailToWebInTapTap(Activity activity, @BridgeParam("appId") String str, @BridgeParam("webUrl") String str2, BridgeCallback bridgeCallback);

    @BridgeMethod("updateGameInTapGlobal")
    void updateGameInTapGlobal(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    @BridgeMethod("updateGameInTapTap")
    void updateGameInTapTap(Activity activity, @BridgeParam("appId") String str, BridgeCallback bridgeCallback);

    @BridgeMethod("useNativeDataInCore")
    void useNativeDataInCore(@BridgeParam("useNativeDataInCore") boolean z);
}
