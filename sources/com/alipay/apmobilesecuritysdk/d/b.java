package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.e.h;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static synchronized Map<String, String> a(Context context, Map<String, String> map) {
        HashMap map2;
        map2 = new HashMap();
        String strA = com.alipay.security.mobile.module.a.a.a(map, "tid", "");
        String strA2 = com.alipay.security.mobile.module.a.a.a(map, com.alipay.sdk.cons.b.g, "");
        String strA3 = com.alipay.security.mobile.module.a.a.a(map, "userId", "");
        String strA4 = com.alipay.security.mobile.module.a.a.a(map, "appName", "");
        String strA5 = com.alipay.security.mobile.module.a.a.a(map, "appKeyClient", "");
        String strA6 = com.alipay.security.mobile.module.a.a.a(map, "tmxSessionId", "");
        String strF = h.f(context);
        String strA7 = com.alipay.security.mobile.module.a.a.a(map, "sessionId", "");
        map2.put("AC1", strA);
        map2.put("AC2", strA2);
        map2.put("AC3", "");
        map2.put("AC4", strF);
        map2.put("AC5", strA3);
        map2.put("AC6", strA6);
        map2.put("AC7", "");
        map2.put("AC8", strA4);
        map2.put("AC9", strA5);
        if (com.alipay.security.mobile.module.a.a.b(strA7)) {
            map2.put("AC10", strA7);
        }
        return map2;
    }
}
