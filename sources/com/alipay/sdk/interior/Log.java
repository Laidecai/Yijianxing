package com.alipay.sdk.interior;

import android.content.Context;
import android.os.SystemClock;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.c;

/* JADX INFO: loaded from: classes.dex */
public class Log {
    public static long a;

    public interface ISdkLogCallback {
        void onLogLine(String str);
    }

    public static boolean forcedLogReport(Context context) {
        try {
            b.d().a(context);
            long jElapsedRealtime = SystemClock.elapsedRealtime() / 1000;
            if (jElapsedRealtime - a < 600) {
                return false;
            }
            a = jElapsedRealtime;
            a.a(context);
            return true;
        } catch (Exception e) {
            c.a(e);
            return false;
        }
    }

    public static void setupLogCallback(ISdkLogCallback iSdkLogCallback) {
        c.a(iSdkLogCallback);
    }
}
