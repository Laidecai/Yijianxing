package com.alipay.sdk.packet.impl;

import android.content.Context;
import com.unity.purchasing.BuildConfig;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d extends com.alipay.sdk.packet.e {
    public static final String t = "log_v";

    @Override // com.alipay.sdk.packet.e
    public String a(com.alipay.sdk.sys.a aVar, String str, JSONObject jSONObject) {
        return str;
    }

    @Override // com.alipay.sdk.packet.e
    public Map<String, String> a(boolean z, String str) {
        HashMap map = new HashMap();
        map.put(com.alipay.sdk.packet.e.c, String.valueOf(z));
        map.put(com.alipay.sdk.packet.e.f, "application/octet-stream");
        map.put(com.alipay.sdk.packet.e.i, "CBC");
        return map;
    }

    @Override // com.alipay.sdk.packet.e
    public JSONObject a() throws JSONException {
        return null;
    }

    @Override // com.alipay.sdk.packet.e
    public String b() throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        map.put(com.alipay.sdk.packet.e.k, "/sdk/log");
        map.put(com.alipay.sdk.packet.e.l, "1.0.0");
        HashMap<String, String> map2 = new HashMap<>();
        map2.put(t, BuildConfig.VERSION_NAME);
        return a(map, map2);
    }

    @Override // com.alipay.sdk.packet.e
    public com.alipay.sdk.packet.b a(com.alipay.sdk.sys.a aVar, Context context, String str) throws Throwable {
        return a(aVar, context, str, com.alipay.sdk.cons.a.c, true);
    }
}
