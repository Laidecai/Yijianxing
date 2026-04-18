package com.tds.tapdb.b;

import com.taptap.services.update.download.core.Util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes.dex */
public class g {
    public static final String A = "Date";
    public static final String B = "ETag";
    public static final String C = "Expires";
    public static final String D = "If-None-Match";
    public static final String E = "Last-Modified";
    public static final String F = "Location";
    public static final String G = "Proxy-Authorization";
    public static final String H = "Referer";
    public static final String I = "Server";
    public static final String J = "User-Agent";
    public static final String K = "DELETE";
    public static final String L = "GET";
    public static final String M = "HEAD";
    public static final String N = "OPTIONS";
    public static final String O = "POST";
    public static final String P = "PUT";
    public static final String Q = "TRACE";
    public static final String R = "charset";
    private static final String S = "00content0boundary00";
    private static final String T = "multipart/form-data; boundary=00content0boundary00";
    private static final String U = "\r\n";
    private static SSLSocketFactory W = null;
    private static HostnameVerifier X = null;
    public static final String o = "UTF-8";
    public static final String p = "application/x-www-form-urlencoded";
    public static final String q = "application/json";
    public static final String r = "gzip";
    public static final String s = "Accept";
    public static final String t = "Accept-Charset";
    public static final String u = "Accept-Encoding";
    public static final String v = "Authorization";
    public static final String w = "Cache-Control";
    public static final String x = "Content-Encoding";
    public static final String y = "Content-Length";
    public static final String z = "Content-Type";
    private final URL b;
    private final String c;
    private q d;
    private boolean e;
    private boolean f;
    private String l;
    private int m;
    private static final String[] V = new String[0];
    private static m Y = m.a;
    private HttpURLConnection a = null;
    private boolean g = true;
    private boolean h = false;
    private int i = 8192;
    private long j = -1;
    private long k = 0;
    private r n = r.a;

    class a extends n<g> {
        final /* synthetic */ Reader b;
        final /* synthetic */ Writer c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Flushable flushable, Reader reader, Writer writer) {
            super(flushable);
            this.b = reader;
            this.c = writer;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.tds.tapdb.b.g.p
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public g b() throws IOException {
            return g.this.a(this.b, this.c);
        }
    }

    static class b implements X509TrustManager {
        b() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    static class c implements HostnameVerifier {
        c() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    }

    static class d implements PrivilegedAction<String> {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        d(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        @Override // java.security.PrivilegedAction
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public String run() {
            return System.setProperty(this.a, this.b);
        }
    }

    static class e implements PrivilegedAction<String> {
        final /* synthetic */ String a;

        e(String str) {
            this.a = str;
        }

        @Override // java.security.PrivilegedAction
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public String run() {
            return System.clearProperty(this.a);
        }
    }

    class f extends l<g> {
        final /* synthetic */ OutputStream c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        f(Closeable closeable, boolean z, OutputStream outputStream) {
            super(closeable, z);
            this.c = outputStream;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.tds.tapdb.b.g.p
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public g b() throws IOException, o {
            return g.this.a(this.c);
        }
    }

    /* JADX INFO: renamed from: com.tds.tapdb.b.g$g, reason: collision with other inner class name */
    class C0021g extends l<g> {
        final /* synthetic */ BufferedReader c;
        final /* synthetic */ Appendable d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C0021g(Closeable closeable, boolean z, BufferedReader bufferedReader, Appendable appendable) {
            super(closeable, z);
            this.c = bufferedReader;
            this.d = appendable;
        }

        @Override // com.tds.tapdb.b.g.p
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public g b() throws IOException {
            CharBuffer charBufferAllocate = CharBuffer.allocate(g.this.i);
            while (true) {
                int i = this.c.read(charBufferAllocate);
                if (i == -1) {
                    return g.this;
                }
                charBufferAllocate.rewind();
                this.d.append(charBufferAllocate, 0, i);
                charBufferAllocate.rewind();
            }
        }
    }

    class h extends l<g> {
        final /* synthetic */ BufferedReader c;
        final /* synthetic */ Writer d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        h(Closeable closeable, boolean z, BufferedReader bufferedReader, Writer writer) {
            super(closeable, z);
            this.c = bufferedReader;
            this.d = writer;
        }

        @Override // com.tds.tapdb.b.g.p
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public g b() throws IOException {
            return g.this.a((Reader) this.c, this.d);
        }
    }

    class i extends l<g> {
        final /* synthetic */ InputStream c;
        final /* synthetic */ OutputStream d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        i(Closeable closeable, boolean z, InputStream inputStream, OutputStream outputStream) {
            super(closeable, z);
            this.c = inputStream;
            this.d = outputStream;
        }

        @Override // com.tds.tapdb.b.g.p
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public g b() throws IOException {
            byte[] bArr = new byte[g.this.i];
            while (true) {
                int i = this.c.read(bArr);
                if (i == -1) {
                    return g.this;
                }
                this.d.write(bArr, 0, i);
                g.this.k += (long) i;
                g.this.n.a(g.this.k, g.this.j);
            }
        }
    }

    class j extends l<g> {
        final /* synthetic */ Reader c;
        final /* synthetic */ Writer d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        j(Closeable closeable, boolean z, Reader reader, Writer writer) {
            super(closeable, z);
            this.c = reader;
            this.d = writer;
        }

        @Override // com.tds.tapdb.b.g.p
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public g b() throws IOException {
            char[] cArr = new char[g.this.i];
            while (true) {
                int i = this.c.read(cArr);
                if (i == -1) {
                    return g.this;
                }
                this.d.write(cArr, 0, i);
                g.this.k += (long) i;
                g.this.n.a(g.this.k, -1L);
            }
        }
    }

    public static class k {
        private static final byte a = 61;
        private static final String b = "US-ASCII";
        private static final byte[] c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

        private k() {
        }

        public static String a(String str) {
            byte[] bytes;
            try {
                bytes = str.getBytes(b);
            } catch (UnsupportedEncodingException unused) {
                bytes = str.getBytes();
            }
            return a(bytes);
        }

        public static String a(byte[] bArr) {
            return a(bArr, 0, bArr.length);
        }

        public static String a(byte[] bArr, int i, int i2) {
            byte[] bArrB = b(bArr, i, i2);
            try {
                return new String(bArrB, b);
            } catch (UnsupportedEncodingException unused) {
                return new String(bArrB);
            }
        }

        private static byte[] a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
            byte[] bArr3 = c;
            int i4 = (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0) | (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0) | (i2 > 2 ? (bArr[i + 2] << 24) >>> 24 : 0);
            if (i2 == 1) {
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = a;
                bArr2[i3 + 3] = a;
                return bArr2;
            }
            if (i2 == 2) {
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                bArr2[i3 + 3] = a;
                return bArr2;
            }
            if (i2 != 3) {
                return bArr2;
            }
            bArr2[i3] = bArr3[i4 >>> 18];
            bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
            bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
            bArr2[i3 + 3] = bArr3[i4 & 63];
            return bArr2;
        }

        public static byte[] b(byte[] bArr, int i, int i2) {
            Objects.requireNonNull(bArr, "Cannot serialize a null array.");
            if (i < 0) {
                throw new IllegalArgumentException("Cannot have negative offset: " + i);
            }
            if (i2 < 0) {
                throw new IllegalArgumentException("Cannot have length offset: " + i2);
            }
            if (i + i2 > bArr.length) {
                throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(bArr.length)));
            }
            int i3 = ((i2 / 3) * 4) + (i2 % 3 <= 0 ? 0 : 4);
            byte[] bArr2 = new byte[i3];
            int i4 = i2 - 2;
            int i5 = 0;
            int i6 = 0;
            while (i5 < i4) {
                a(bArr, i5 + i, 3, bArr2, i6);
                i5 += 3;
                i6 += 4;
            }
            if (i5 < i2) {
                a(bArr, i + i5, i2 - i5, bArr2, i6);
                i6 += 4;
            }
            if (i6 > i3 - 1) {
                return bArr2;
            }
            byte[] bArr3 = new byte[i6];
            System.arraycopy(bArr2, 0, bArr3, 0, i6);
            return bArr3;
        }
    }

    protected static abstract class l<V> extends p<V> {
        private final Closeable a;
        private final boolean b;

        protected l(Closeable closeable, boolean z) {
            this.a = closeable;
            this.b = z;
        }

        @Override // com.tds.tapdb.b.g.p
        protected void a() throws IOException {
            Closeable closeable = this.a;
            if (closeable instanceof Flushable) {
                ((Flushable) closeable).flush();
            }
            if (!this.b) {
                this.a.close();
            } else {
                try {
                    this.a.close();
                } catch (IOException unused) {
                }
            }
        }
    }

    public interface m {
        public static final m a = new a();

        static class a implements m {
            a() {
            }

            @Override // com.tds.tapdb.b.g.m
            public HttpURLConnection a(URL url) throws IOException {
                return (HttpURLConnection) url.openConnection();
            }

            @Override // com.tds.tapdb.b.g.m
            public HttpURLConnection a(URL url, Proxy proxy) throws IOException {
                return (HttpURLConnection) url.openConnection(proxy);
            }
        }

        HttpURLConnection a(URL url) throws IOException;

        HttpURLConnection a(URL url, Proxy proxy) throws IOException;
    }

    protected static abstract class n<V> extends p<V> {
        private final Flushable a;

        protected n(Flushable flushable) {
            this.a = flushable;
        }

        @Override // com.tds.tapdb.b.g.p
        protected void a() throws IOException {
            this.a.flush();
        }
    }

    public static class o extends RuntimeException {
        private static final long a = -1170466989781746231L;

        public o(IOException iOException) {
            super(iOException);
        }

        @Override // java.lang.Throwable
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public IOException getCause() {
            return (IOException) super.getCause();
        }
    }

    protected static abstract class p<V> implements Callable<V> {
        protected p() {
        }

        protected abstract void a() throws IOException;

        protected abstract V b() throws IOException, o;

        @Override // java.util.concurrent.Callable
        public V call() throws Throwable {
            boolean z;
            try {
                try {
                    V vB = b();
                    try {
                        a();
                        return vB;
                    } catch (IOException e) {
                        throw new o(e);
                    }
                } catch (o e2) {
                    throw e2;
                } catch (IOException e3) {
                    throw new o(e3);
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        a();
                    } catch (IOException e4) {
                        if (!z) {
                            throw new o(e4);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                z = true;
                a();
                throw th;
            }
        }
    }

    public static class q extends BufferedOutputStream {
        private final CharsetEncoder a;

        public q(OutputStream outputStream, String str, int i) {
            super(outputStream, i);
            this.a = Charset.forName(g.l(str)).newEncoder();
        }

        public q a(String str) throws IOException {
            ByteBuffer byteBufferEncode = this.a.encode(CharBuffer.wrap(str));
            super.write(byteBufferEncode.array(), 0, byteBufferEncode.limit());
            return this;
        }
    }

    public interface r {
        public static final r a = new a();

        static class a implements r {
            a() {
            }

            @Override // com.tds.tapdb.b.g.r
            public void a(long j, long j2) {
            }
        }

        void a(long j, long j2);
    }

    public g(CharSequence charSequence, String str) throws o {
        try {
            this.b = new URL(charSequence.toString());
            this.c = str;
        } catch (MalformedURLException e2) {
            throw new o(e2);
        }
    }

    public g(URL url, String str) throws o {
        this.b = url;
        this.c = str;
    }

    private static HostnameVerifier A() {
        if (X == null) {
            X = new c();
        }
        return X;
    }

    public static g a(CharSequence charSequence) throws o {
        return new g(charSequence, K);
    }

    public static g a(CharSequence charSequence, Map<?, ?> map, boolean z2) {
        String strA = a(charSequence, map);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return a((CharSequence) strA);
    }

    public static g a(CharSequence charSequence, boolean z2, Object... objArr) {
        String strA = a(charSequence, objArr);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return a((CharSequence) strA);
    }

    public static g a(URL url) throws o {
        return new g(url, K);
    }

    public static String a(CharSequence charSequence, Map<?, ?> map) {
        String string = charSequence.toString();
        if (map == null || map.isEmpty()) {
            return string;
        }
        StringBuilder sb = new StringBuilder(string);
        b(string, sb);
        a(string, sb);
        Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
        while (true) {
            Map.Entry<?, ?> next = it.next();
            a(next.getKey().toString(), next.getValue(), sb);
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(Typography.amp);
        }
    }

    public static String a(CharSequence charSequence, Object... objArr) {
        String string = charSequence.toString();
        if (objArr == null || objArr.length == 0) {
            return string;
        }
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("Must specify an even number of parameter names/values");
        }
        StringBuilder sb = new StringBuilder(string);
        b(string, sb);
        a(string, sb);
        a(objArr[0], objArr[1], sb);
        for (int i2 = 2; i2 < objArr.length; i2 += 2) {
            sb.append(Typography.amp);
            a(objArr[i2], objArr[i2 + 1], sb);
        }
        return sb.toString();
    }

    private static StringBuilder a(Object obj, Object obj2, StringBuilder sb) {
        if (obj2 != null && obj2.getClass().isArray()) {
            obj2 = a(obj2);
        }
        if (obj2 instanceof Iterable) {
            Iterator it = ((Iterable) obj2).iterator();
            while (it.hasNext()) {
                sb.append(obj);
                sb.append("[]=");
                Object next = it.next();
                if (next != null) {
                    sb.append(next);
                }
                if (it.hasNext()) {
                    sb.append(com.alipay.sdk.sys.a.k);
                }
            }
        } else {
            sb.append(obj);
            sb.append("=");
            if (obj2 != null) {
                sb.append(obj2);
            }
        }
        return sb;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000f A[PHI: r0
  0x000f: PHI (r0v2 char) = (r0v0 char), (r0v1 char) binds: [B:3:0x000d, B:7:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.StringBuilder a(java.lang.String r4, java.lang.StringBuilder r5) {
        /*
            r0 = 63
            int r1 = r4.indexOf(r0)
            int r2 = r5.length()
            int r2 = r2 + (-1)
            r3 = -1
            if (r1 != r3) goto L13
        Lf:
            r5.append(r0)
            goto L1e
        L13:
            if (r1 >= r2) goto L1e
            char r4 = r4.charAt(r2)
            r0 = 38
            if (r4 == r0) goto L1e
            goto Lf
        L1e:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.tapdb.b.g.a(java.lang.String, java.lang.StringBuilder):java.lang.StringBuilder");
    }

    private static List<Object> a(Object obj) {
        if (obj instanceof Object[]) {
            return Arrays.asList((Object[]) obj);
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            int length = iArr.length;
            while (i2 < length) {
                arrayList.add(Integer.valueOf(iArr[i2]));
                i2++;
            }
        } else if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            int length2 = zArr.length;
            while (i2 < length2) {
                arrayList.add(Boolean.valueOf(zArr[i2]));
                i2++;
            }
        } else if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            int length3 = jArr.length;
            while (i2 < length3) {
                arrayList.add(Long.valueOf(jArr[i2]));
                i2++;
            }
        } else if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            int length4 = fArr.length;
            while (i2 < length4) {
                arrayList.add(Float.valueOf(fArr[i2]));
                i2++;
            }
        } else if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            int length5 = dArr.length;
            while (i2 < length5) {
                arrayList.add(Double.valueOf(dArr[i2]));
                i2++;
            }
        } else if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            int length6 = sArr.length;
            while (i2 < length6) {
                arrayList.add(Short.valueOf(sArr[i2]));
                i2++;
            }
        } else if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            int length7 = bArr.length;
            while (i2 < length7) {
                arrayList.add(Byte.valueOf(bArr[i2]));
                i2++;
            }
        } else if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            int length8 = cArr.length;
            while (i2 < length8) {
                arrayList.add(Character.valueOf(cArr[i2]));
                i2++;
            }
        }
        return arrayList;
    }

    public static void a(m mVar) {
        if (mVar == null) {
            mVar = m.a;
        }
        Y = mVar;
    }

    public static void a(String... strArr) {
        String string;
        if (strArr == null || strArr.length <= 0) {
            string = null;
        } else {
            StringBuilder sb = new StringBuilder();
            int length = strArr.length - 1;
            for (int i2 = 0; i2 < length; i2++) {
                sb.append(strArr[i2]);
                sb.append('|');
            }
            sb.append(strArr[length]);
            string = sb.toString();
        }
        i("http.nonProxyHosts", string);
    }

    private g b(long j2) {
        if (this.j == -1) {
            this.j = 0L;
        }
        this.j += j2;
        return this;
    }

    public static g b(CharSequence charSequence, Map<?, ?> map, boolean z2) {
        String strA = a(charSequence, map);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return c((CharSequence) strA);
    }

    public static g b(CharSequence charSequence, boolean z2, Object... objArr) {
        String strA = a(charSequence, objArr);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return c((CharSequence) strA);
    }

    public static g b(URL url) throws o {
        return new g(url, L);
    }

    public static String b(CharSequence charSequence) throws o {
        int i2;
        try {
            URL url = new URL(charSequence.toString());
            String host = url.getHost();
            int port = url.getPort();
            if (port != -1) {
                host = host + ':' + Integer.toString(port);
            }
            try {
                String aSCIIString = new URI(url.getProtocol(), host, url.getPath(), url.getQuery(), null).toASCIIString();
                int iIndexOf = aSCIIString.indexOf(63);
                if (iIndexOf <= 0 || (i2 = iIndexOf + 1) >= aSCIIString.length()) {
                    return aSCIIString;
                }
                return aSCIIString.substring(0, i2) + aSCIIString.substring(i2).replace("+", "%2B");
            } catch (URISyntaxException e2) {
                IOException iOException = new IOException("Parsing URI failed");
                iOException.initCause(e2);
                throw new o(iOException);
            }
        } catch (IOException e3) {
            throw new o(e3);
        }
    }

    private static StringBuilder b(String str, StringBuilder sb) {
        if (str.indexOf(58) + 2 == str.lastIndexOf(47)) {
            sb.append('/');
        }
        return sb;
    }

    public static g c(CharSequence charSequence) throws o {
        return new g(charSequence, L);
    }

    public static g c(CharSequence charSequence, Map<?, ?> map, boolean z2) {
        String strA = a(charSequence, map);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return d((CharSequence) strA);
    }

    public static g c(CharSequence charSequence, boolean z2, Object... objArr) {
        String strA = a(charSequence, objArr);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return d((CharSequence) strA);
    }

    public static g c(URL url) throws o {
        return new g(url, "HEAD");
    }

    public static void c(boolean z2) {
        i("http.keepAlive", Boolean.toString(z2));
    }

    public static g d(CharSequence charSequence) throws o {
        return new g(charSequence, "HEAD");
    }

    public static g d(CharSequence charSequence, Map<?, ?> map, boolean z2) {
        String strA = a(charSequence, map);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return f((CharSequence) strA);
    }

    public static g d(CharSequence charSequence, boolean z2, Object... objArr) {
        String strA = a(charSequence, objArr);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return f((CharSequence) strA);
    }

    public static g d(URL url) throws o {
        return new g(url, N);
    }

    public static g e(CharSequence charSequence) throws o {
        return new g(charSequence, N);
    }

    public static g e(CharSequence charSequence, Map<?, ?> map, boolean z2) {
        String strA = a(charSequence, map);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return g((CharSequence) strA);
    }

    public static g e(CharSequence charSequence, boolean z2, Object... objArr) {
        String strA = a(charSequence, objArr);
        if (z2) {
            strA = b((CharSequence) strA);
        }
        return g((CharSequence) strA);
    }

    public static g e(URL url) throws o {
        return new g(url, O);
    }

    public static void e(int i2) {
        i("http.maxConnections", Integer.toString(i2));
    }

    public static g f(CharSequence charSequence) throws o {
        return new g(charSequence, O);
    }

    public static g f(URL url) throws o {
        return new g(url, P);
    }

    public static void f(int i2) {
        String string = Integer.toString(i2);
        i("http.proxyPort", string);
        i("https.proxyPort", string);
    }

    public static g g(CharSequence charSequence) throws o {
        return new g(charSequence, P);
    }

    public static g g(URL url) throws o {
        return new g(url, Q);
    }

    public static g i(CharSequence charSequence) throws o {
        return new g(charSequence, Q);
    }

    private static String i(String str, String str2) {
        return (String) AccessController.doPrivileged(str2 != null ? new d(str, str2) : new e(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String l(String str) {
        return (str == null || str.length() <= 0) ? "UTF-8" : str;
    }

    private HttpURLConnection r() {
        try {
            HttpURLConnection httpURLConnectionA = this.l != null ? Y.a(this.b, s()) : Y.a(this.b);
            httpURLConnectionA.setRequestMethod(this.c);
            return httpURLConnectionA;
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    private Proxy s() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.l, this.m));
    }

    public static void s(String str) {
        i("http.proxyHost", str);
        i("https.proxyHost", str);
    }

    private static SSLSocketFactory z() throws o {
        if (W == null) {
            TrustManager[] trustManagerArr = {new b()};
            try {
                SSLContext sSLContext = SSLContext.getInstance("TLS");
                sSLContext.init(null, trustManagerArr, new SecureRandom());
                W = sSLContext.getSocketFactory();
            } catch (GeneralSecurityException e2) {
                IOException iOException = new IOException("Security exception configuring SSL context");
                iOException.initCause(e2);
                throw new o(iOException);
            }
        }
        return W;
    }

    public Map<String, List<String>> B() throws o {
        m();
        return y().getHeaderFields();
    }

    public boolean C() {
        return this.g;
    }

    public boolean D() throws o {
        return p() == 0;
    }

    public long E() {
        return j(E);
    }

    public String F() {
        return m(F);
    }

    public String G() throws o {
        try {
            l();
            return y().getResponseMessage();
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public String H() {
        return y().getRequestMethod();
    }

    public boolean I() throws o {
        return 204 == n();
    }

    public boolean J() throws o {
        return 404 == n();
    }

    public boolean K() throws o {
        return 304 == n();
    }

    public boolean L() throws o {
        return 200 == n();
    }

    protected g M() throws IOException {
        if (this.d != null) {
            return this;
        }
        y().setDoOutput(true);
        this.d = new q(y().getOutputStream(), c(y().getRequestProperty("Content-Type"), R), this.i);
        return this;
    }

    public InputStreamReader N() throws o {
        return t(k());
    }

    public String O() {
        return m(I);
    }

    public boolean P() throws o {
        return 500 == n();
    }

    protected g Q() throws IOException {
        q qVar;
        String str;
        if (this.e) {
            qVar = this.d;
            str = "\r\n--00content0boundary00\r\n";
        } else {
            this.e = true;
            i(T).M();
            qVar = this.d;
            str = "--00content0boundary00\r\n";
        }
        qVar.a(str);
        return this;
    }

    public InputStream R() throws o {
        InputStream inputStream;
        if (n() < 400) {
            try {
                inputStream = y().getInputStream();
            } catch (IOException e2) {
                throw new o(e2);
            }
        } else {
            inputStream = y().getErrorStream();
            if (inputStream == null) {
                try {
                    inputStream = y().getInputStream();
                } catch (IOException e3) {
                    if (p() > 0) {
                        throw new o(e3);
                    }
                    inputStream = new ByteArrayInputStream(new byte[0]);
                }
            }
        }
        if (!this.h || !r.equals(o())) {
            return inputStream;
        }
        try {
            return new GZIPInputStream(inputStream);
        } catch (IOException e4) {
            throw new o(e4);
        }
    }

    public g S() throws o {
        HttpURLConnection httpURLConnectionY = y();
        if (httpURLConnectionY instanceof HttpsURLConnection) {
            ((HttpsURLConnection) httpURLConnectionY).setSSLSocketFactory(z());
        }
        return this;
    }

    public g T() {
        HttpURLConnection httpURLConnectionY = y();
        if (httpURLConnectionY instanceof HttpsURLConnection) {
            ((HttpsURLConnection) httpURLConnectionY).setHostnameVerifier(A());
        }
        return this;
    }

    public URL U() {
        return y().getURL();
    }

    public OutputStreamWriter V() throws o {
        try {
            M();
            q qVar = this.d;
            return new OutputStreamWriter(qVar, qVar.a.charset());
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public int a(String str, int i2) throws o {
        m();
        return y().getHeaderFieldInt(str, i2);
    }

    public long a(String str, long j2) throws o {
        m();
        return y().getHeaderFieldDate(str, j2);
    }

    public g a() {
        return c(r);
    }

    public g a(int i2) {
        if (i2 < 1) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
        this.i = i2;
        return this;
    }

    public g a(long j2) {
        y().setIfModifiedSince(j2);
        return this;
    }

    public g a(r rVar) {
        if (rVar == null) {
            rVar = r.a;
        }
        this.n = rVar;
        return this;
    }

    public g a(File file) throws o {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), this.i);
            return new f(bufferedOutputStream, this.g, bufferedOutputStream).call();
        } catch (FileNotFoundException e2) {
            throw new o(e2);
        }
    }

    public g a(InputStream inputStream) throws o {
        try {
            M();
            a(inputStream, (OutputStream) this.d);
            return this;
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    protected g a(InputStream inputStream, OutputStream outputStream) throws IOException {
        return new i(inputStream, this.g, inputStream, outputStream).call();
    }

    public g a(OutputStream outputStream) throws o {
        try {
            return a((InputStream) e(), outputStream);
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public g a(PrintStream printStream) throws o {
        return a((OutputStream) printStream);
    }

    public g a(Reader reader) throws o {
        try {
            M();
            q qVar = this.d;
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(qVar, qVar.a.charset());
            return new a(outputStreamWriter, reader, outputStreamWriter).call();
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    protected g a(Reader reader, Writer writer) throws IOException {
        return new j(reader, this.g, reader, writer).call();
    }

    public g a(Writer writer) throws o {
        BufferedReader bufferedReaderG = g();
        return new h(bufferedReaderG, this.g, bufferedReaderG, writer).call();
    }

    public g a(Appendable appendable) throws o {
        BufferedReader bufferedReaderG = g();
        return new C0021g(bufferedReaderG, this.g, bufferedReaderG, appendable).call();
    }

    public g a(Object obj, Object obj2) throws o {
        return a(obj, obj2, "UTF-8");
    }

    public g a(Object obj, Object obj2, String str) throws o {
        if (obj == null || obj2 == null) {
            return this;
        }
        boolean z2 = !this.f;
        if (z2) {
            b(p, str);
            this.f = true;
        }
        String strL = l(str);
        try {
            M();
            if (!z2) {
                this.d.write(38);
            }
            this.d.a(URLEncoder.encode(obj.toString(), strL));
            this.d.write(61);
            if (obj2 != null) {
                this.d.a(URLEncoder.encode(obj2.toString(), strL));
            }
            return this;
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public g a(String str) {
        return d(s, str);
    }

    public g a(String str, File file) throws o {
        return a(str, (String) null, file);
    }

    public g a(String str, InputStream inputStream) throws o {
        return a(str, (String) null, (String) null, inputStream);
    }

    public g a(String str, Number number) {
        return d(str, number != null ? number.toString() : null);
    }

    public g a(String str, String str2) {
        return e("Basic " + k.a(str + ':' + str2));
    }

    public g a(String str, String str2, File file) throws o {
        return a(str, str2, (String) null, file);
    }

    public g a(String str, String str2, Number number) throws o {
        return a(str, str2, number != null ? number.toString() : null);
    }

    public g a(String str, String str2, String str3) throws o {
        return a(str, str2, (String) null, str3);
    }

    public g a(String str, String str2, String str3, File file) throws o {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            b(file.length());
            return a(str, str2, str3, bufferedInputStream);
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public g a(String str, String str2, String str3, InputStream inputStream) throws o {
        try {
            Q();
            b(str, str2, str3);
            a(inputStream, (OutputStream) this.d);
            return this;
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public g a(String str, String str2, String str3, String str4) throws o {
        try {
            Q();
            b(str, str2, str3);
            this.d.a(str4);
            return this;
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public g a(Map.Entry<?, ?> entry) throws o {
        return a(entry, "UTF-8");
    }

    public g a(Map.Entry<?, ?> entry, String str) throws o {
        return a(entry.getKey(), entry.getValue(), str);
    }

    public g a(Map<?, ?> map) throws o {
        return a(map, "UTF-8");
    }

    public g a(Map<?, ?> map, String str) throws o {
        if (!map.isEmpty()) {
            Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                a(it.next(), str);
            }
        }
        return this;
    }

    public g a(AtomicInteger atomicInteger) throws o {
        atomicInteger.set(n());
        return this;
    }

    public g a(AtomicReference<String> atomicReference) throws o {
        atomicReference.set(d());
        return this;
    }

    public g a(AtomicReference<String> atomicReference, String str) throws o {
        atomicReference.set(f(str));
        return this;
    }

    public g a(boolean z2) {
        y().setInstanceFollowRedirects(z2);
        return this;
    }

    public g a(byte[] bArr) throws o {
        if (bArr != null) {
            b(bArr.length);
        }
        return a((InputStream) new ByteArrayInputStream(bArr));
    }

    public g b() {
        return a("application/json");
    }

    public g b(int i2) {
        y().setChunkedStreamingMode(i2);
        return this;
    }

    public g b(File file) throws o {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            b(file.length());
            return a((InputStream) bufferedInputStream);
        } catch (FileNotFoundException e2) {
            throw new o(e2);
        }
    }

    public g b(String str) {
        return d(t, str);
    }

    public g b(String str, int i2) {
        if (this.a != null) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        this.l = str;
        this.m = i2;
        return this;
    }

    public g b(String str, Number number) throws o {
        return a(str, (String) null, number);
    }

    public g b(String str, String str2) {
        if (str2 == null || str2.length() <= 0) {
            return d("Content-Type", str);
        }
        return d("Content-Type", str + "; charset=" + str2);
    }

    protected g b(String str, String str2, String str3) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("form-data; name=\"");
        sb.append(str);
        if (str2 != null) {
            sb.append("\"; filename=\"");
            sb.append(str2);
        }
        sb.append(Typography.quote);
        g(Util.CONTENT_DISPOSITION, sb.toString());
        if (str3 != null) {
            g("Content-Type", str3);
        }
        return h(U);
    }

    public g b(Map.Entry<String, String> entry) {
        return d(entry.getKey(), entry.getValue());
    }

    public g b(Map<String, String> map) {
        if (!map.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                b(it.next());
            }
        }
        return this;
    }

    public g b(boolean z2) {
        this.g = z2;
        return this;
    }

    public g c(int i2) {
        y().setConnectTimeout(i2);
        return this;
    }

    public g c(String str) {
        return d(u, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0021, code lost:
    
        if (r5 == (-1)) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
    
        r5 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0025, code lost:
    
        if (r3 >= r5) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0027, code lost:
    
        r7 = r9.indexOf(61, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002d, code lost:
    
        if (r7 == (-1)) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x002f, code lost:
    
        if (r7 >= r5) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003d, code lost:
    
        if (r10.equals(r9.substring(r3, r7).trim()) == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003f, code lost:
    
        r3 = r9.substring(r7 + 1, r5).trim();
        r7 = r3.length();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004d, code lost:
    
        if (r7 == 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0050, code lost:
    
        if (r7 <= 2) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0059, code lost:
    
        if ('\"' != r3.charAt(0)) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005b, code lost:
    
        r7 = r7 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0060, code lost:
    
        if ('\"' != r3.charAt(r7)) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0066, code lost:
    
        return r3.substring(1, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0067, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0068, code lost:
    
        r3 = r5 + 1;
        r5 = r9.indexOf(59, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006e, code lost:
    
        if (r5 != (-1)) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0071, code lost:
    
        r1 = r5;
     */
    /* JADX WARN: Path cross not found for [B:13:0x0023, B:39:?], limit reached: 38 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x006e -> B:13:0x0023). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.lang.String c(java.lang.String r9, java.lang.String r10) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto L73
            int r1 = r9.length()
            if (r1 != 0) goto Lb
            goto L73
        Lb:
            int r1 = r9.length()
            r2 = 59
            int r3 = r9.indexOf(r2)
            r4 = 1
            int r3 = r3 + r4
            if (r3 == 0) goto L73
            if (r3 != r1) goto L1c
            goto L73
        L1c:
            int r5 = r9.indexOf(r2, r3)
            r6 = -1
            if (r5 != r6) goto L25
        L23:
            r5 = r1
            goto L71
        L25:
            if (r3 >= r5) goto L73
            r7 = 61
            int r7 = r9.indexOf(r7, r3)
            if (r7 == r6) goto L68
            if (r7 >= r5) goto L68
            java.lang.String r3 = r9.substring(r3, r7)
            java.lang.String r3 = r3.trim()
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L68
            int r7 = r7 + 1
            java.lang.String r3 = r9.substring(r7, r5)
            java.lang.String r3 = r3.trim()
            int r7 = r3.length()
            if (r7 == 0) goto L68
            r9 = 2
            if (r7 <= r9) goto L67
            r9 = 0
            char r9 = r3.charAt(r9)
            r10 = 34
            if (r10 != r9) goto L67
            int r7 = r7 - r4
            char r9 = r3.charAt(r7)
            if (r10 != r9) goto L67
            java.lang.String r9 = r3.substring(r4, r7)
            return r9
        L67:
            return r3
        L68:
            int r3 = r5 + 1
            int r5 = r9.indexOf(r2, r3)
            if (r5 != r6) goto L25
            goto L23
        L71:
            r1 = r5
            goto L25
        L73:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.tapdb.b.g.c(java.lang.String, java.lang.String):java.lang.String");
    }

    public boolean c() throws o {
        return 400 == n();
    }

    public g d(int i2) {
        y().setFixedLengthStreamingMode(i2);
        return this;
    }

    public g d(String str, String str2) {
        y().setRequestProperty(str, str2);
        return this;
    }

    public g d(boolean z2) throws o {
        S();
        T();
        return this;
    }

    public String d() throws o {
        return f(k());
    }

    public g e(String str) {
        return d(v, str);
    }

    public g e(boolean z2) {
        this.h = z2;
        return this;
    }

    public BufferedInputStream e() throws o {
        return new BufferedInputStream(R(), this.i);
    }

    public String e(String str, String str2) {
        return c(m(str), str2);
    }

    public int f() {
        return this.i;
    }

    public g f(String str, String str2) {
        return a(str, (String) null, str2);
    }

    public g f(boolean z2) {
        y().setUseCaches(z2);
        return this;
    }

    public String f(String str) throws o {
        ByteArrayOutputStream byteArrayOutputStreamH = h();
        try {
            a((InputStream) e(), (OutputStream) byteArrayOutputStreamH);
            return byteArrayOutputStreamH.toString(l(str));
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public g g(int i2) {
        y().setReadTimeout(i2);
        return this;
    }

    public g g(String str, String str2) throws o {
        return h((CharSequence) str).h(": ").h((CharSequence) str2).h(U);
    }

    public BufferedReader g() throws o {
        return g(k());
    }

    public BufferedReader g(String str) throws o {
        return new BufferedReader(t(str), this.i);
    }

    public g h(CharSequence charSequence) throws o {
        try {
            M();
            this.d.a(charSequence.toString());
            return this;
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public g h(String str) {
        return d(Integer.parseInt(str));
    }

    public g h(String str, String str2) {
        return r("Basic " + k.a(str + ':' + str2));
    }

    protected ByteArrayOutputStream h() {
        int iP = p();
        return iP > 0 ? new ByteArrayOutputStream(iP) : new ByteArrayOutputStream();
    }

    public g i(String str) {
        return b(str, (String) null);
    }

    public byte[] i() throws o {
        ByteArrayOutputStream byteArrayOutputStreamH = h();
        try {
            a((InputStream) e(), (OutputStream) byteArrayOutputStreamH);
            return byteArrayOutputStreamH.toByteArray();
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public long j(String str) throws o {
        return a(str, -1L);
    }

    protected g j(String str, String str2) throws IOException {
        return b(str, str2, (String) null);
    }

    public String j() {
        return m(w);
    }

    public String k() {
        return e("Content-Type", R);
    }

    protected Map<String, String> k(String str) {
        String strTrim;
        int length;
        if (str == null || str.length() == 0) {
            return Collections.emptyMap();
        }
        int length2 = str.length();
        int iIndexOf = str.indexOf(59) + 1;
        if (iIndexOf == 0 || iIndexOf == length2) {
            return Collections.emptyMap();
        }
        int iIndexOf2 = str.indexOf(59, iIndexOf);
        if (iIndexOf2 == -1) {
            iIndexOf2 = length2;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (iIndexOf < iIndexOf2) {
            int iIndexOf3 = str.indexOf(61, iIndexOf);
            if (iIndexOf3 != -1 && iIndexOf3 < iIndexOf2) {
                String strTrim2 = str.substring(iIndexOf, iIndexOf3).trim();
                if (strTrim2.length() > 0 && (length = (strTrim = str.substring(iIndexOf3 + 1, iIndexOf2).trim()).length()) != 0) {
                    if (length > 2 && '\"' == strTrim.charAt(0)) {
                        int i2 = length - 1;
                        if ('\"' == strTrim.charAt(i2)) {
                            strTrim = strTrim.substring(1, i2);
                        }
                    }
                    linkedHashMap.put(strTrim2, strTrim);
                }
            }
            iIndexOf = iIndexOf2 + 1;
            iIndexOf2 = str.indexOf(59, iIndexOf);
            if (iIndexOf2 == -1) {
                iIndexOf2 = length2;
            }
        }
        return linkedHashMap;
    }

    protected g l() throws IOException {
        a((r) null);
        q qVar = this.d;
        if (qVar == null) {
            return this;
        }
        if (this.e) {
            qVar.a("\r\n--00content0boundary00--\r\n");
        }
        if (this.g) {
            try {
                this.d.close();
            } catch (IOException unused) {
            }
        } else {
            this.d.close();
        }
        this.d = null;
        return this;
    }

    protected g m() throws o {
        try {
            return l();
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public String m(String str) throws o {
        m();
        return y().getHeaderField(str);
    }

    public int n() throws o {
        try {
            l();
            return y().getResponseCode();
        } catch (IOException e2) {
            throw new o(e2);
        }
    }

    public String[] n(String str) {
        Map<String, List<String>> mapB = B();
        if (mapB == null || mapB.isEmpty()) {
            return V;
        }
        List<String> list = mapB.get(str);
        return (list == null || list.isEmpty()) ? V : (String[]) list.toArray(new String[list.size()]);
    }

    public g o(String str) {
        return d(D, str);
    }

    public String o() {
        return m(x);
    }

    public int p() {
        return p("Content-Length");
    }

    public int p(String str) throws o {
        return a(str, -1);
    }

    public String q() {
        return m("Content-Type");
    }

    public Map<String, String> q(String str) {
        return k(m(str));
    }

    public g r(String str) {
        return d(G, str);
    }

    public InputStreamReader t(String str) throws o {
        try {
            return new InputStreamReader(R(), l(str));
        } catch (UnsupportedEncodingException e2) {
            throw new o(e2);
        }
    }

    public boolean t() throws o {
        return 201 == n();
    }

    public String toString() {
        return H() + ' ' + U();
    }

    public long u() {
        return j(A);
    }

    public g u(String str) {
        return d(H, str);
    }

    public g v() {
        y().disconnect();
        return this;
    }

    public g v(String str) {
        return d("User-Agent", str);
    }

    public String w() {
        return m(B);
    }

    public long x() {
        return j(C);
    }

    public HttpURLConnection y() {
        if (this.a == null) {
            this.a = r();
        }
        return this.a;
    }
}
