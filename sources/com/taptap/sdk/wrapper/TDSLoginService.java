package com.taptap.sdk.wrapper;

import android.app.Activity;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;

/* JADX INFO: loaded from: classes.dex */
@BridgeService("TDSLoginService")
public interface TDSLoginService extends IBridgeService {
    @BridgeMethod("appendPermission")
    void appendPermission(String str);

    @BridgeMethod("changeConfig")
    void changeConfig(@BridgeParam("roundCorner") boolean z, @BridgeParam("isPortrait") boolean z2);

    @BridgeMethod("currentAccessToken")
    void currentAccessToken(BridgeCallback bridgeCallback);

    @BridgeMethod("currentProfile")
    void currentProfile(BridgeCallback bridgeCallback);

    @BridgeMethod("fetchProfileForCurrentAccessToken")
    void fetchProfileForCurrentAccessToken(BridgeCallback bridgeCallback);

    @BridgeMethod("getTestQualification")
    void getTestQualification(BridgeCallback bridgeCallback);

    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("clientID") String str);

    @BridgeMethod("initWithClientID")
    void init(Activity activity, @BridgeParam("clientID") String str, @BridgeParam("regionType") boolean z, @BridgeParam("roundCorner") boolean z2);

    @BridgeMethod("logout")
    void logout();

    @BridgeMethod("registerLoginCallback")
    void registerLoginCallback(BridgeCallback bridgeCallback);

    @BridgeMethod("startTapLogin")
    void startTapLogin(Activity activity, @BridgeParam(arrayClz = String.class, value = "permissions") String[] strArr);

    @BridgeMethod("unregisterLoginCallback")
    void unregisterLoginCallback();
}
