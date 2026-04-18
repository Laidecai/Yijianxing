package com.alipay.security.mobile.module.b;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static a a = new a();

    public static a a() {
        return a;
    }

    public static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16).versionName;
        } catch (Exception unused) {
            return "0.0.0";
        }
    }
}
