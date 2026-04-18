package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.e.f;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public static Map<String, String> a(Context context) {
        com.alipay.security.mobile.module.b.b bVarB = com.alipay.security.mobile.module.b.b.b();
        HashMap map = new HashMap();
        f fVarA = com.alipay.apmobilesecuritysdk.e.e.a(context);
        String strA = com.alipay.security.mobile.module.b.b.a(context);
        String strB = com.alipay.security.mobile.module.b.b.b(context);
        String strK = com.alipay.security.mobile.module.b.b.k(context);
        String strM = com.alipay.security.mobile.module.b.b.m(context);
        if (fVarA != null) {
            if (com.alipay.security.mobile.module.a.a.a(strA)) {
                strA = fVarA.a();
            }
            if (com.alipay.security.mobile.module.a.a.a(strB)) {
                strB = fVarA.b();
            }
            if (com.alipay.security.mobile.module.a.a.a(strK)) {
                strK = fVarA.c();
            }
            if (com.alipay.security.mobile.module.a.a.a(strM)) {
                strM = fVarA.e();
            }
        }
        f fVar = new f(strA, strB, strK, "", strM);
        if (context != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("imei", fVar.a());
                jSONObject.put("imsi", fVar.b());
                jSONObject.put("mac", fVar.c());
                jSONObject.put("bluetoothmac", fVar.d());
                jSONObject.put("gsi", fVar.e());
                String string = jSONObject.toString();
                com.alipay.apmobilesecuritysdk.f.a.a("device_feature_file_name", "device_feature_file_key", string);
                com.alipay.apmobilesecuritysdk.f.a.a(context, "device_feature_prefs_name", "device_feature_prefs_key", string);
            } catch (Exception e) {
                com.alipay.apmobilesecuritysdk.c.a.a(e);
            }
        }
        map.put("AD1", strA);
        map.put("AD2", strB);
        map.put("AD3", com.alipay.security.mobile.module.b.b.f(context));
        map.put("AD5", com.alipay.security.mobile.module.b.b.h(context));
        map.put("AD6", com.alipay.security.mobile.module.b.b.i(context));
        map.put("AD7", com.alipay.security.mobile.module.b.b.j(context));
        map.put("AD8", strK);
        map.put("AD9", com.alipay.security.mobile.module.b.b.l(context));
        map.put("AD10", strM);
        map.put("AD11", com.alipay.security.mobile.module.b.b.e());
        map.put("AD12", bVarB.a());
        map.put("AD13", com.alipay.security.mobile.module.b.b.f());
        map.put("AD14", com.alipay.security.mobile.module.b.b.h());
        map.put("AD15", com.alipay.security.mobile.module.b.b.i());
        map.put("AD16", com.alipay.security.mobile.module.b.b.j());
        map.put("AD17", "");
        map.put("AD19", com.alipay.security.mobile.module.b.b.n(context));
        map.put("AD20", com.alipay.security.mobile.module.b.b.k());
        map.put("AD22", "");
        map.put("AD23", com.alipay.security.mobile.module.b.b.p(context));
        map.put("AD24", com.alipay.security.mobile.module.a.a.g(com.alipay.security.mobile.module.b.b.g(context)));
        map.put("AD26", com.alipay.security.mobile.module.b.b.e(context));
        map.put("AD27", com.alipay.security.mobile.module.b.b.p());
        map.put("AD28", com.alipay.security.mobile.module.b.b.r());
        map.put("AD29", com.alipay.security.mobile.module.b.b.t());
        map.put("AD30", com.alipay.security.mobile.module.b.b.q());
        map.put("AD31", com.alipay.security.mobile.module.b.b.s());
        map.put("AD32", com.alipay.security.mobile.module.b.b.n());
        map.put("AD33", com.alipay.security.mobile.module.b.b.o());
        map.put("AD34", com.alipay.security.mobile.module.b.b.r(context));
        map.put("AD35", com.alipay.security.mobile.module.b.b.s(context));
        map.put("AD36", com.alipay.security.mobile.module.b.b.q(context));
        map.put("AD37", com.alipay.security.mobile.module.b.b.m());
        map.put("AD38", com.alipay.security.mobile.module.b.b.l());
        map.put("AD39", com.alipay.security.mobile.module.b.b.c(context));
        map.put("AD40", com.alipay.security.mobile.module.b.b.d(context));
        map.put("AD41", com.alipay.security.mobile.module.b.b.c());
        map.put("AD42", com.alipay.security.mobile.module.b.b.d());
        map.put("AL3", com.alipay.security.mobile.module.b.b.o(context));
        return map;
    }
}
