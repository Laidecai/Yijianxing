package com.tds.common.websocket.util;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class LogUtil {
    private static final String TAG = "webSocket";

    public static void logE(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.e(TAG, str);
    }

    public static void logE(String str, Throwable th) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (th != null) {
            Log.e(TAG, str + " error = " + th.getMessage());
            return;
        }
        logE(str);
    }

    public static void logE(String str, String str2, Throwable th) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (th != null) {
            Log.e(TAG, str + " thread = " + str2 + " error = " + th.getMessage());
            return;
        }
        logE(str + " thread = " + str2);
    }

    public static void logD(String str) {
        TextUtils.isEmpty(str);
    }

    public static void logD(String str, Object obj) {
        TextUtils.isEmpty(str);
    }
}
