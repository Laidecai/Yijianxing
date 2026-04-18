package com.alipay.security.mobile.module.a;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r4, java.lang.String r5) throws java.lang.Throwable {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            r2.<init>(r4, r5)     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            boolean r4 = r2.exists()     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            if (r4 != 0) goto L12
            return r1
        L12:
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            java.lang.String r2 = "UTF-8"
            r5.<init>(r3, r2)     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L32 java.io.IOException -> L3a
        L23:
            java.lang.String r5 = r4.readLine()     // Catch: java.lang.Throwable -> L2d java.io.IOException -> L30
            if (r5 == 0) goto L3e
            r0.append(r5)     // Catch: java.lang.Throwable -> L2d java.io.IOException -> L30
            goto L23
        L2d:
            r5 = move-exception
            r1 = r4
            goto L34
        L30:
            r1 = r4
            goto L3b
        L32:
            r4 = move-exception
            r5 = r4
        L34:
            if (r1 == 0) goto L39
            r1.close()     // Catch: java.lang.Throwable -> L39
        L39:
            throw r5
        L3a:
        L3b:
            if (r1 == 0) goto L41
            r4 = r1
        L3e:
            r4.close()     // Catch: java.lang.Throwable -> L41
        L41:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.a.b.a(java.lang.String, java.lang.String):java.lang.String");
    }
}
