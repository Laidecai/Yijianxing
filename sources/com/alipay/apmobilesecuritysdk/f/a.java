package com.alipay.apmobilesecuritysdk.f;

import android.content.Context;
import android.os.Environment;
import com.alipay.security.mobile.module.c.e;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String a(Context context, String str, String str2) {
        if (context == null || com.alipay.security.mobile.module.a.a.a(str) || com.alipay.security.mobile.module.a.a.a(str2)) {
            return null;
        }
        try {
            String strA = e.a(context, str, str2, "");
            if (com.alipay.security.mobile.module.a.a.a(strA)) {
                return null;
            }
            return com.alipay.security.mobile.module.a.a.c.b(com.alipay.security.mobile.module.a.a.c.a(), strA);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String a(String str, String str2) {
        synchronized (a.class) {
            if (com.alipay.security.mobile.module.a.a.a(str) || com.alipay.security.mobile.module.a.a.a(str2)) {
                return null;
            }
            try {
                String strA = com.alipay.security.mobile.module.c.b.a(str);
                if (com.alipay.security.mobile.module.a.a.a(strA)) {
                    return null;
                }
                String string = new JSONObject(strA).getString(str2);
                if (com.alipay.security.mobile.module.a.a.a(string)) {
                    return null;
                }
                return com.alipay.security.mobile.module.a.a.c.b(com.alipay.security.mobile.module.a.a.c.a(), string);
            } catch (Throwable unused) {
                return null;
            }
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (com.alipay.security.mobile.module.a.a.a(str) || com.alipay.security.mobile.module.a.a.a(str2) || context == null) {
            return;
        }
        try {
            String strA = com.alipay.security.mobile.module.a.a.c.a(com.alipay.security.mobile.module.a.a.c.a(), str3);
            HashMap map = new HashMap();
            map.put(str2, strA);
            e.a(context, str, map);
        } catch (Throwable unused) {
        }
    }

    public static void a(String str, String str2, String str3) {
        synchronized (a.class) {
            if (com.alipay.security.mobile.module.a.a.a(str) || com.alipay.security.mobile.module.a.a.a(str2)) {
                return;
            }
            try {
                String strA = com.alipay.security.mobile.module.c.b.a(str);
                JSONObject jSONObject = new JSONObject();
                if (com.alipay.security.mobile.module.a.a.b(strA)) {
                    try {
                        jSONObject = new JSONObject(strA);
                    } catch (Exception unused) {
                        jSONObject = new JSONObject();
                    }
                }
                jSONObject.put(str2, com.alipay.security.mobile.module.a.a.c.a(com.alipay.security.mobile.module.a.a.c.a(), str3));
                jSONObject.toString();
                try {
                    System.clearProperty(str);
                } catch (Throwable unused2) {
                }
                if (com.alipay.security.mobile.module.c.c.a()) {
                    String str4 = ".SystemConfig" + File.separator + str;
                    if (com.alipay.security.mobile.module.c.c.a()) {
                        File file = new File(Environment.getExternalStorageDirectory(), str4);
                        if (file.exists() && file.isFile()) {
                            file.delete();
                        }
                    }
                }
            } catch (Throwable unused3) {
            }
        }
    }
}
