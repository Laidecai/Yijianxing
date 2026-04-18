package com.alipay.apmobilesecuritysdk.e;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    public static synchronized String a(Context context, String str) {
        String strA = com.alipay.security.mobile.module.c.e.a(context, "openapi_file_pri", "openApi" + str, "");
        if (com.alipay.security.mobile.module.a.a.a(strA)) {
            return "";
        }
        String strB = com.alipay.security.mobile.module.a.a.c.b(com.alipay.security.mobile.module.a.a.c.a(), strA);
        return com.alipay.security.mobile.module.a.a.a(strB) ? "" : strB;
    }

    public static synchronized void a() {
    }

    public static synchronized void a(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("openapi_file_pri", 0).edit();
        if (editorEdit != null) {
            editorEdit.clear();
            editorEdit.commit();
        }
    }

    public static synchronized void a(Context context, String str, String str2) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("openapi_file_pri", 0).edit();
            if (editorEdit != null) {
                editorEdit.putString("openApi" + str, com.alipay.security.mobile.module.a.a.c.a(com.alipay.security.mobile.module.a.a.c.a(), str2));
                editorEdit.commit();
            }
        } catch (Throwable unused) {
        }
    }
}
