package com.taptap.services.update.wrapper;

import android.app.Activity;
import com.taptap.services.update.TapUpdate;
import com.taptap.services.update.TapUpdateCallback;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.log.Logger;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateServiceImpl implements TapUpdateService {
    private static final String TAG = "TapUpdateServiceImpl";
    private final Logger logger = Logger.getCommonLogger();

    @Override // com.taptap.services.update.wrapper.TapUpdateService
    public void init(Activity activity, String str, String str2) {
        this.logger.i(TAG, "init");
        TapUpdate.init(activity, str, str2);
    }

    @Override // com.taptap.services.update.wrapper.TapUpdateService
    public void updateGame(Activity activity, final BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "updateGame");
        TapUpdate.updateGame(activity, new TapUpdateCallback() { // from class: com.taptap.services.update.wrapper.TapUpdateServiceImpl.1
            @Override // com.taptap.services.update.TapUpdateCallback
            public void onCancel() {
                BridgeCallback bridgeCallback2 = bridgeCallback;
                if (bridgeCallback2 != null) {
                    bridgeCallback2.onResult("{'code':1,'msg':'cancel'}");
                }
            }
        });
    }
}
