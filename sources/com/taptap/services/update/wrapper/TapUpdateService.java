package com.taptap.services.update.wrapper;

import android.app.Activity;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;

/* JADX INFO: loaded from: classes.dex */
@BridgeService("TapUpdateService")
public interface TapUpdateService extends IBridgeService {
    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("clientId") String str, @BridgeParam("clientToken") String str2);

    @BridgeMethod("updateGame")
    void updateGame(Activity activity, BridgeCallback bridgeCallback);
}
