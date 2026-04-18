package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static synchronized Map<String, String> a(Context context, Map<String, String> map) {
        HashMap map2;
        String strA = com.alipay.security.mobile.module.a.a.a(map, "appchannel", "");
        map2 = new HashMap();
        map2.put("AA1", context.getPackageName());
        com.alipay.security.mobile.module.b.a.a();
        map2.put("AA2", com.alipay.security.mobile.module.b.a.a(context));
        map2.put("AA3", "APPSecuritySDK-ALIPAYSDK");
        map2.put("AA4", "3.4.0.201910161639");
        map2.put("AA6", strA);
        return map2;
    }
}
