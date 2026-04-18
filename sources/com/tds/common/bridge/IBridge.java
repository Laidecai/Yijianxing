package com.tds.common.bridge;

import android.app.Activity;

/* JADX INFO: loaded from: classes.dex */
public interface IBridge {
    void callHandler(String str);

    void init(Activity activity);

    void register(Class<? extends IBridgeService> cls, IBridgeService iBridgeService);

    void registerHandler(String str, BridgeCallback bridgeCallback);
}
