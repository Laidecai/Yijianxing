package com.taptap.sdk;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
class TapTapSharePreference {
    private static volatile TapTapSharePreference mInstance;
    private SharedPreferences sp;

    public static TapTapSharePreference init(Context context) {
        mInstance = new TapTapSharePreference(context.getSharedPreferences("taptap_sharepreference", 0));
        return mInstance;
    }

    private TapTapSharePreference(SharedPreferences sharedPreferences) {
        this.sp = sharedPreferences;
    }

    public static TapTapSharePreference getInstance() {
        return mInstance;
    }

    public SharedPreferences getSp() {
        return this.sp;
    }
}
