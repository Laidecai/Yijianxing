package com.tds.common.bridge.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class BridgeLogger {
    public static final String TAG = "BridgeLogger";

    public static void e(String str) {
        Log.e(TAG, str);
    }

    public static void i(String str) {
        Log.i(TAG, str);
    }

    public static void d(String str) {
        Log.d(TAG, str);
    }
}
