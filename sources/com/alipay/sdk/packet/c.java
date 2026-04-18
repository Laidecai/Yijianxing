package com.alipay.sdk.packet;

import com.alipay.sdk.util.l;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public boolean a;
    public String b = l.a(24);

    public c(boolean z) {
        this.a = z;
    }

    public static byte[] b(String str, byte[] bArr, String str2) {
        return com.alipay.sdk.encrypt.e.b(str, bArr, str2);
    }

    public d a(b bVar, boolean z, String str) {
        if (bVar == null) {
            return null;
        }
        byte[] bytes = bVar.b().getBytes();
        byte[] bytes2 = bVar.a().getBytes();
        if (z) {
            try {
                bytes2 = com.alipay.sdk.encrypt.b.a(bytes2);
            } catch (Exception unused) {
                z = false;
            }
        }
        return new d(z, this.a ? a(bytes, a(this.b, com.alipay.sdk.cons.a.e), b(this.b, bytes2, str)) : a(bytes, bytes2));
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x007b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.alipay.sdk.packet.b a(com.alipay.sdk.packet.d r6, java.lang.String r7) {
        /*
            r5 = this;
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60
            byte[] r2 = r6.a()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60
            r2 = 5
            byte[] r3 = new byte[r2]     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            r1.read(r3)     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            r4.<init>(r3)     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            int r3 = a(r4)     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            byte[] r3 = new byte[r3]     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            r1.read(r3)     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            r4.<init>(r3)     // Catch: java.lang.Exception -> L5b java.lang.Throwable -> L77
            byte[] r2 = new byte[r2]     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            r1.read(r2)     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            java.lang.String r3 = new java.lang.String     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            r3.<init>(r2)     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            int r2 = a(r3)     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            if (r2 <= 0) goto L52
            byte[] r2 = new byte[r2]     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            r1.read(r2)     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            boolean r3 = r5.a     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            if (r3 == 0) goto L42
            java.lang.String r3 = r5.b     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            byte[] r2 = a(r3, r2, r7)     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
        L42:
            boolean r6 = r6.b()     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            if (r6 == 0) goto L4c
            byte[] r2 = com.alipay.sdk.encrypt.b.b(r2)     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
        L4c:
            java.lang.String r6 = new java.lang.String     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            r6.<init>(r2)     // Catch: java.lang.Exception -> L59 java.lang.Throwable -> L77
            goto L53
        L52:
            r6 = r0
        L53:
            r1.close()     // Catch: java.lang.Exception -> L57
            goto L6c
        L57:
            goto L6c
        L59:
            r6 = move-exception
            goto L63
        L5b:
            r6 = move-exception
            r4 = r0
            goto L63
        L5e:
            r6 = move-exception
            goto L79
        L60:
            r6 = move-exception
            r1 = r0
            r4 = r1
        L63:
            com.alipay.sdk.util.c.a(r6)     // Catch: java.lang.Throwable -> L77
            if (r1 == 0) goto L6b
            r1.close()     // Catch: java.lang.Exception -> L6b
        L6b:
            r6 = r0
        L6c:
            if (r4 != 0) goto L71
            if (r6 != 0) goto L71
            return r0
        L71:
            com.alipay.sdk.packet.b r7 = new com.alipay.sdk.packet.b
            r7.<init>(r4, r6)
            return r7
        L77:
            r6 = move-exception
            r0 = r1
        L79:
            if (r0 == 0) goto L7e
            r0.close()     // Catch: java.lang.Exception -> L7e
        L7e:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.packet.c.a(com.alipay.sdk.packet.d, java.lang.String):com.alipay.sdk.packet.b");
    }

    public static byte[] a(String str, String str2) {
        return com.alipay.sdk.encrypt.d.a(str, str2);
    }

    public static byte[] a(String str, byte[] bArr, String str2) {
        return com.alipay.sdk.encrypt.e.a(str, bArr, str2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:129|74|127|75|(8:119|76|(1:78)|131|79|123|80|81)|121|99|100) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0059 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0060 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:132:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.ByteArrayOutputStream, java.io.OutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] a(byte[]... r7) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r7 == 0) goto L64
            int r1 = r7.length
            if (r1 != 0) goto L8
            goto L64
        L8:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L42
            r1.<init>()     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L42
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3c
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L3c
            int r3 = r7.length     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            r4 = 0
        L14:
            if (r4 >= r3) goto L2a
            r5 = r7[r4]     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            int r6 = r5.length     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            java.lang.String r6 = a(r6)     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            byte[] r6 = r6.getBytes()     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            r2.write(r6)     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            r2.write(r5)     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            int r4 = r4 + 1
            goto L14
        L2a:
            r2.flush()     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            byte[] r7 = r1.toByteArray()     // Catch: java.lang.Exception -> L36 java.lang.Throwable -> L55
            r1.close()     // Catch: java.lang.Exception -> L34
        L34:
            r0 = r7
            goto L51
        L36:
            r7 = move-exception
            goto L45
        L38:
            r7 = move-exception
            r2 = r0
        L3a:
            r0 = r1
            goto L57
        L3c:
            r7 = move-exception
            r2 = r0
            goto L45
        L3f:
            r7 = move-exception
            r2 = r0
            goto L57
        L42:
            r7 = move-exception
            r1 = r0
            r2 = r1
        L45:
            com.alipay.sdk.util.c.a(r7)     // Catch: java.lang.Throwable -> L55
            if (r1 == 0) goto L4f
            r1.close()     // Catch: java.lang.Exception -> L4e
            goto L4f
        L4e:
        L4f:
            if (r2 == 0) goto L54
        L51:
            r2.close()     // Catch: java.lang.Exception -> L54
        L54:
            return r0
        L55:
            r7 = move-exception
            goto L3a
        L57:
            if (r0 == 0) goto L5e
            r0.close()     // Catch: java.lang.Exception -> L5d
            goto L5e
        L5d:
        L5e:
            if (r2 == 0) goto L63
            r2.close()     // Catch: java.lang.Exception -> L63
        L63:
            throw r7
        L64:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.packet.c.a(byte[][]):byte[]");
    }

    public static String a(int i) {
        return String.format(Locale.getDefault(), "%05d", Integer.valueOf(i));
    }

    public static int a(String str) {
        return Integer.parseInt(str);
    }
}
