package com.taptap.services.update;

import android.app.Activity;
import com.tds.common.entities.TapConfig;
import com.tds.common.isc.IscMethod;
import com.tds.common.isc.IscService;

/* JADX INFO: loaded from: classes.dex */
@IscService(TapUpdateLogger.TAG)
class IscTapUpdateService {
    IscTapUpdateService() {
    }

    @IscMethod("init")
    public static void init(Activity activity, TapConfig tapConfig) {
        TapUpdate.init(activity, tapConfig.clientId, tapConfig.clientToken);
    }
}
