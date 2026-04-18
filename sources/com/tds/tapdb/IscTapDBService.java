package com.tds.tapdb;

import android.content.Context;
import com.tds.common.entities.TapConfig;
import com.tds.common.isc.IscMethod;
import com.tds.common.isc.IscService;
import com.tds.tapdb.b.c;
import com.tds.tapdb.sdk.TapDB;

/* JADX INFO: loaded from: classes.dex */
@IscService("TapDB")
public class IscTapDBService {
    @IscMethod("getTapDBDeviceIdCache")
    public static String getTapDBDeviceIdCache(Context context) {
        return c.d(context);
    }

    @IscMethod("getTapTapDID")
    public static String getTapTapDID(Context context) {
        return TapDB.getTapTapDID(context);
    }

    @IscMethod("init")
    public static void init(Context context, TapConfig tapConfig) {
        TapDB.init(context, tapConfig.clientId, tapConfig.tapDBConfig.getChannel(), tapConfig.tapDBConfig.getGameVersion(), tapConfig.regionType == 1);
    }
}
