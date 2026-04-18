package com.alipay.sdk.app;

import com.alipay.sdk.util.g;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static boolean a = false;
    public static String b;

    public static void a(String str) {
        b = str;
    }

    public static String b() {
        c cVarB = c.b(c.DOUBLE_REQUEST.b());
        return a(cVarB.b(), cVarB.a(), "");
    }

    public static boolean c() {
        return a;
    }

    public static String d() {
        return b;
    }

    public static String e() {
        c cVarB = c.b(c.PARAMS_ERROR.b());
        return a(cVarB.b(), cVarB.a(), "");
    }

    public static void a(boolean z) {
        a = z;
    }

    public static String a() {
        c cVarB = c.b(c.CANCELED.b());
        return a(cVarB.b(), cVarB.a(), "");
    }

    public static String a(int i, String str, String str2) {
        return "resultStatus={" + i + "};memo={" + str + "};result={" + str2 + g.d;
    }
}
