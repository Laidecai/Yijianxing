package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import com.taptap.sdk.constant.LoginConstants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    public static synchronized Map<String, String> a() {
        HashMap map;
        map = new HashMap();
        try {
            new com.alipay.apmobilesecuritysdk.c.b();
            map.put("AE16", "");
        } catch (Throwable unused) {
        }
        return map;
    }

    public static synchronized Map<String, String> a(Context context) {
        HashMap map;
        com.alipay.security.mobile.module.b.d.a();
        com.alipay.security.mobile.module.b.b.b();
        map = new HashMap();
        map.put("AE1", com.alipay.security.mobile.module.b.d.b());
        StringBuilder sb = new StringBuilder();
        sb.append(com.alipay.security.mobile.module.b.d.c() ? "1" : LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
        map.put("AE2", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(com.alipay.security.mobile.module.b.d.a(context) ? "1" : LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
        map.put("AE3", sb2.toString());
        map.put("AE4", com.alipay.security.mobile.module.b.d.d());
        map.put("AE5", com.alipay.security.mobile.module.b.d.e());
        map.put("AE6", com.alipay.security.mobile.module.b.d.f());
        map.put("AE7", com.alipay.security.mobile.module.b.d.g());
        map.put("AE8", com.alipay.security.mobile.module.b.d.h());
        map.put("AE9", com.alipay.security.mobile.module.b.d.i());
        map.put("AE10", com.alipay.security.mobile.module.b.d.j());
        map.put("AE11", com.alipay.security.mobile.module.b.d.k());
        map.put("AE12", com.alipay.security.mobile.module.b.d.l());
        map.put("AE13", com.alipay.security.mobile.module.b.d.m());
        map.put("AE14", com.alipay.security.mobile.module.b.d.n());
        map.put("AE15", com.alipay.security.mobile.module.b.d.o());
        map.put("AE21", com.alipay.security.mobile.module.b.b.g());
        return map;
    }
}
