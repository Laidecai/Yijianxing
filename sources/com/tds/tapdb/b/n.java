package com.tds.tapdb.b;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class n {
    private static boolean a = true;
    private static final String b = "TapDB";

    public static void a(Exception exc) {
        if (!a || exc == null) {
            return;
        }
        a(exc, false);
    }

    public static void a(Exception exc, boolean z) {
        if (!a || exc == null) {
            return;
        }
        String message = exc.getMessage();
        if (z) {
            c(message);
        } else {
            b(message);
        }
    }

    public static void a(String str) {
        if (a) {
            Log.d(b, "debug ----- message: " + str);
        }
    }

    public static void a(Throwable th) {
        if (a) {
            b(th.getMessage());
        }
    }

    public static void a(boolean z) {
        a = z;
    }

    public static void b(String str) {
        if (a) {
            Log.e(b, "error ----- message: " + str);
        }
    }

    public static void c(String str) {
        if (a) {
            Log.w(b, "warning ----- message: " + str);
        }
    }
}
