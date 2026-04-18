package com.taptap.sdk.wrapper;

import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;

/* JADX INFO: loaded from: classes.dex */
@BridgeService("TapFriendsService")
public interface TapFriendsService extends IBridgeService {
    @BridgeMethod("queryMutualList")
    void queryMutualList(@BridgeParam("cursor") String str, @BridgeParam("size") int i, BridgeCallback bridgeCallback);
}
