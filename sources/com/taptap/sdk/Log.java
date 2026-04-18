package com.taptap.sdk;

/* JADX INFO: loaded from: classes.dex */
public class Log {
    private static final String TAG = "TAPTAP_SDK";

    public static boolean DEBUG_LOG(String str) {
        android.util.Log.e(TAG, str);
        return true;
    }
}
