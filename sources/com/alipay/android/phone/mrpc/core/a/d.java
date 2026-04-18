package com.alipay.android.phone.mrpc.core.a;

import com.alipay.android.phone.mrpc.core.RpcException;
import com.alipay.sdk.util.j;
import java.lang.reflect.Type;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class d extends a {
    public d(Type type, byte[] bArr) {
        super(type, bArr);
    }

    @Override // com.alipay.android.phone.mrpc.core.a.c
    public final Object a() {
        try {
            String str = new String(this.b);
            StringBuilder sb = new StringBuilder("threadid = ");
            sb.append(Thread.currentThread().getId());
            sb.append("; rpc response:  ");
            sb.append(str);
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt(j.a);
            if (i == 1000) {
                return this.a == String.class ? jSONObject.optString(j.c) : com.alipay.a.a.e.a(jSONObject.optString(j.c), this.a);
            }
            throw new RpcException(Integer.valueOf(i), jSONObject.optString("tips"));
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("response  =");
            sb2.append(new String(this.b));
            sb2.append(":");
            sb2.append(e);
            throw new RpcException((Integer) 10, sb2.toString() == null ? "" : e.getMessage());
        }
    }
}
