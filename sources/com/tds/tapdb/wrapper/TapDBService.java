package com.tds.tapdb.wrapper;

import android.app.Activity;
import com.alipay.sdk.cons.c;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;

/* JADX INFO: loaded from: classes.dex */
@BridgeService("TDSTapDBService")
public interface TapDBService extends IBridgeService {
    @BridgeMethod("clearStaticProperties")
    void clearStaticProperties();

    @BridgeMethod("clearUser")
    void clearUser();

    @BridgeMethod("closeFetchTapTapDeviceId")
    void closeFetchTapTapDeviceId();

    @BridgeMethod("deviceAdd")
    void deviceAdd(@BridgeParam("deviceAdd") String str);

    @BridgeMethod("deviceInitialize")
    void deviceInitialize(@BridgeParam("deviceInitialize") String str);

    @BridgeMethod("deviceUpdate")
    void deviceUpdate(@BridgeParam("deviceUpdate") String str);

    @BridgeMethod("enableLog")
    void enableLog(@BridgeParam("enableLog") boolean z);

    @BridgeMethod("getTapTapDID")
    void getTapTapDID(Activity activity, BridgeCallback bridgeCallback);

    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("clientId") String str, @BridgeParam("channel") String str2);

    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("clientId") String str, @BridgeParam("channel") String str2, @BridgeParam("gameVersion") String str3);

    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("clientId") String str, @BridgeParam("channel") String str2, @BridgeParam("gameVersion") String str3, @BridgeParam("properties") String str4);

    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("clientId") String str, @BridgeParam("channel") String str2, @BridgeParam("gameVersion") String str3, @BridgeParam("isCN") boolean z);

    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("clientId") String str, @BridgeParam("channel") String str2, @BridgeParam("isCN") boolean z);

    @BridgeMethod("onCharge")
    void onCharge(@BridgeParam("orderId") String str, @BridgeParam("product") String str2, @BridgeParam("amount") long j, @BridgeParam("currencyType") String str3, @BridgeParam("payment") String str4);

    @BridgeMethod("onChargeWithProperties")
    void onChargeWithProperties(@BridgeParam("orderId") String str, @BridgeParam("product") String str2, @BridgeParam("amount") long j, @BridgeParam("currencyType") String str3, @BridgeParam("payment") String str4, @BridgeParam("properties") String str5);

    @BridgeMethod("onEvent")
    void onEvent(@BridgeParam("eventCode") String str, @BridgeParam("properties") String str2);

    @BridgeMethod("registerDynamicProperties")
    void registerDynamicProperties(@BridgeParam("registerDynamicProperties") TapDBDynamicProperties tapDBDynamicProperties);

    @BridgeMethod("registerDynamicPropertiesUE")
    void registerDynamicPropertiesUE();

    @BridgeMethod("registerStaticProperties")
    void registerStaticProperties(@BridgeParam("registerStaticProperties") String str);

    @BridgeMethod("setCustomEventHost")
    void setCustomEventHost(@BridgeParam("setCustomEventHost") String str);

    @BridgeMethod("setHost")
    void setHost(@BridgeParam("setHost") String str);

    @BridgeMethod("setLevel")
    void setLevel(@BridgeParam("level") int i);

    @BridgeMethod("setName")
    void setName(@BridgeParam(c.e) String str);

    @BridgeMethod("setServer")
    void setServer(@BridgeParam("server") String str);

    @BridgeMethod("setUser")
    void setUser(@BridgeParam("userId") String str);

    @BridgeMethod("setUserWithParams")
    void setUser(@BridgeParam("userId") String str, @BridgeParam("loginType") String str2);

    @BridgeMethod("track")
    void track(@BridgeParam("eventName") String str, @BridgeParam("properties") String str2);

    @BridgeMethod("trackEvent")
    void trackEvent(@BridgeParam("eventName") String str, @BridgeParam("properties") String str2);

    @BridgeMethod("unregisterStaticProperty")
    void unregisterStaticProperty(@BridgeParam("unregisterStaticProperty") String str);

    @BridgeMethod("userAdd")
    void userAdd(@BridgeParam("userAdd") String str);

    @BridgeMethod("userInitialize")
    void userInitialize(@BridgeParam("userInitialize") String str);

    @BridgeMethod("userUpdate")
    void userUpdate(@BridgeParam("userUpdate") String str);
}
