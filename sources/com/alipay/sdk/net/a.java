package com.alipay.sdk.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final String a = "msp";
    public static final String b = "application/octet-stream;binary/octet-stream";
    public static final CookieManager c = new CookieManager();

    /* JADX INFO: renamed from: com.alipay.sdk.net.a$a, reason: collision with other inner class name */
    public static final class C0006a {
        public final String a;
        public final byte[] b;
        public final Map<String, String> c;

        public C0006a(String str, Map<String, String> map, byte[] bArr) {
            this.a = str;
            this.b = bArr;
            this.c = map;
        }

        public String toString() {
            return String.format("<UrlConnectionConfigure url=%s headers=%s>", this.a, this.c);
        }
    }

    public static final class b {
        public final Map<String, List<String>> a;
        public final String b;
        public final byte[] c;

        public b(Map<String, List<String>> map, String str, byte[] bArr) {
            this.a = map;
            this.b = str;
            this.c = bArr;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x01a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.alipay.sdk.net.a.b a(android.content.Context r11, com.alipay.sdk.net.a.C0006a r12) {
        /*
            Method dump skipped, instruction units count: 460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.net.a.a(android.content.Context, com.alipay.sdk.net.a$a):com.alipay.sdk.net.a$b");
    }

    public static String b(Context context) {
        try {
            NetworkInfo networkInfoA = a(context);
            if (networkInfoA != null && networkInfoA.isAvailable()) {
                return networkInfoA.getType() == 1 ? "wifi" : networkInfoA.getExtraInfo().toLowerCase();
            }
        } catch (Exception unused) {
        }
        return "none";
    }

    public static Proxy c(Context context) {
        String strB = b(context);
        if (strB != null && !strB.contains("wap")) {
            return null;
        }
        try {
            String property = System.getProperty("https.proxyHost");
            String property2 = System.getProperty("https.proxyPort");
            if (TextUtils.isEmpty(property)) {
                return null;
            }
            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(property, Integer.parseInt(property2)));
        } catch (Throwable unused) {
            return null;
        }
    }

    public static NetworkInfo a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr, 0, 1024);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }
}
