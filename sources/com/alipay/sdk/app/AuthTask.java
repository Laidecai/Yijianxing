package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.data.a;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.f;
import com.alipay.sdk.util.j;
import com.alipay.sdk.util.l;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AuthTask {
    public static final Object c = f.class;
    public Activity a;
    public com.alipay.sdk.widget.a b;

    public class a implements f.e {
        public a() {
        }

        @Override // com.alipay.sdk.util.f.e
        public void a() {
            AuthTask.this.a();
        }

        @Override // com.alipay.sdk.util.f.e
        public void b() {
        }
    }

    public AuthTask(Activity activity) {
        this.a = activity;
        com.alipay.sdk.sys.b.d().a(this.a);
        this.b = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.k);
    }

    private f.e b() {
        return new a();
    }

    private void c() {
        com.alipay.sdk.widget.a aVar = this.b;
        if (aVar != null) {
            aVar.d();
        }
    }

    public synchronized String auth(String str, boolean z) {
        return innerAuth(new com.alipay.sdk.sys.a(this.a, str, com.alipay.sdk.app.statistic.b.n), str, z);
    }

    public synchronized Map<String, String> authV2(String str, boolean z) {
        com.alipay.sdk.sys.a aVar;
        aVar = new com.alipay.sdk.sys.a(this.a, str, "authV2");
        return j.a(aVar, innerAuth(aVar, str, z));
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00bf A[Catch: all -> 0x0135, DONT_GENERATE, PHI: r7
  0x00bf: PHI (r7v11 java.lang.String) = (r7v2 java.lang.String), (r7v13 java.lang.String) binds: [B:15:0x00bd, B:8:0x0069] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {, blocks: (B:4:0x0003, B:5:0x0006, B:7:0x001e, B:17:0x00c8, B:16:0x00bf, B:20:0x00d4, B:22:0x0121, B:23:0x012a, B:24:0x0134, B:14:0x0072, B:6:0x0018, B:13:0x006f), top: B:30:0x0003, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String innerAuth(com.alipay.sdk.sys.a r5, java.lang.String r6, boolean r7) {
        /*
            Method dump skipped, instruction units count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.AuthTask.innerAuth(com.alipay.sdk.sys.a, java.lang.String, boolean):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        com.alipay.sdk.widget.a aVar = this.b;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b(android.app.Activity r4, java.lang.String r5, com.alipay.sdk.sys.a r6) {
        /*
            r3 = this;
            r3.c()
            r0 = 0
            com.alipay.sdk.packet.impl.a r1 = new com.alipay.sdk.packet.impl.a     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            r1.<init>()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            com.alipay.sdk.packet.b r4 = r1.a(r6, r4, r5)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            org.json.JSONObject r4 = r4.c()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            java.lang.String r5 = "form"
            org.json.JSONObject r4 = r4.optJSONObject(r5)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            java.lang.String r5 = "onload"
            org.json.JSONObject r4 = r4.optJSONObject(r5)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            java.util.List r4 = com.alipay.sdk.protocol.b.a(r4)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            r3.a()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            r5 = 0
        L25:
            int r1 = r4.size()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            if (r5 >= r1) goto L4a
            java.lang.Object r1 = r4.get(r5)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            com.alipay.sdk.protocol.b r1 = (com.alipay.sdk.protocol.b) r1     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            com.alipay.sdk.protocol.a r1 = r1.a()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            com.alipay.sdk.protocol.a r2 = com.alipay.sdk.protocol.a.WapPay     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            if (r1 != r2) goto L47
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            com.alipay.sdk.protocol.b r4 = (com.alipay.sdk.protocol.b) r4     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            java.lang.String r4 = r3.a(r6, r4)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L57
            r3.a()
            return r4
        L47:
            int r5 = r5 + 1
            goto L25
        L4a:
            r3.a()
            goto L6b
        L4e:
            r4 = move-exception
            java.lang.String r5 = "biz"
            java.lang.String r1 = "H5AuthDataAnalysisError"
            com.alipay.sdk.app.statistic.a.a(r6, r5, r1, r4)     // Catch: java.lang.Throwable -> L86
            goto L68
        L57:
            r4 = move-exception
            com.alipay.sdk.app.c r5 = com.alipay.sdk.app.c.NETWORK_ERROR     // Catch: java.lang.Throwable -> L86
            int r5 = r5.b()     // Catch: java.lang.Throwable -> L86
            com.alipay.sdk.app.c r5 = com.alipay.sdk.app.c.b(r5)     // Catch: java.lang.Throwable -> L86
            java.lang.String r0 = "net"
            com.alipay.sdk.app.statistic.a.a(r6, r0, r4)     // Catch: java.lang.Throwable -> L86
            r0 = r5
        L68:
            r3.a()
        L6b:
            if (r0 != 0) goto L77
            com.alipay.sdk.app.c r4 = com.alipay.sdk.app.c.FAILED
            int r4 = r4.b()
            com.alipay.sdk.app.c r0 = com.alipay.sdk.app.c.b(r4)
        L77:
            int r4 = r0.b()
            java.lang.String r5 = r0.a()
            java.lang.String r6 = ""
            java.lang.String r4 = com.alipay.sdk.app.b.a(r4, r5, r6)
            return r4
        L86:
            r4 = move-exception
            r3.a()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.AuthTask.b(android.app.Activity, java.lang.String, com.alipay.sdk.sys.a):java.lang.String");
    }

    private String a(Activity activity, String str, com.alipay.sdk.sys.a aVar) {
        String strA = aVar.a(str);
        List<a.b> listK = com.alipay.sdk.data.a.u().k();
        if (!com.alipay.sdk.data.a.u().g || listK == null) {
            listK = com.alipay.sdk.app.a.d;
        }
        if (l.b(aVar, this.a, listK)) {
            String strA2 = new f(activity, aVar, b()).a(strA);
            if (!TextUtils.equals(strA2, f.j) && !TextUtils.equals(strA2, f.k)) {
                return TextUtils.isEmpty(strA2) ? b.a() : strA2;
            }
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.m0);
            return b(activity, strA, aVar);
        }
        com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.n0);
        return b(activity, strA, aVar);
    }

    private String a(com.alipay.sdk.sys.a aVar, com.alipay.sdk.protocol.b bVar) {
        String[] strArrC = bVar.c();
        Bundle bundle = new Bundle();
        bundle.putString(BreakpointSQLiteKey.URL, strArrC[0]);
        Intent intent = new Intent(this.a, (Class<?>) H5AuthActivity.class);
        intent.putExtras(bundle);
        a.C0007a.a(aVar, intent);
        this.a.startActivity(intent);
        Object obj = c;
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException unused) {
                return b.a();
            }
        }
        String strD = b.d();
        return TextUtils.isEmpty(strD) ? b.a() : strD;
    }
}
