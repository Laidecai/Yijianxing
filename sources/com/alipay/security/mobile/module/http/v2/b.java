package com.alipay.security.mobile.module.http.v2;

import android.content.Context;
import com.alipay.security.mobile.module.http.d;
import com.alipay.security.mobile.module.http.model.c;

/* JADX INFO: loaded from: classes.dex */
public class b implements a {
    public static a a;
    public static com.alipay.security.mobile.module.http.a b;

    public static a a(Context context, String str) {
        if (context == null) {
            return null;
        }
        if (a == null) {
            b = d.a(context, str);
            a = new b();
        }
        return a;
    }

    @Override // com.alipay.security.mobile.module.http.v2.a
    public c a(com.alipay.security.mobile.module.http.model.d dVar) {
        return com.alipay.security.mobile.module.http.model.b.a(b.a(com.alipay.security.mobile.module.http.model.b.a(dVar)));
    }

    @Override // com.alipay.security.mobile.module.http.v2.a
    public boolean logCollect(String str) {
        return b.logCollect(str);
    }
}
