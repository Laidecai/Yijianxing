package com.tds.common.net;

import android.text.TextUtils;
import com.alipay.sdk.sys.a;
import com.tds.common.io.IoUtil;
import com.tds.tapdb.b.g;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public final class TdsHttp {

    public interface Interceptor {

        public interface Chain {
            Call call();

            int connectTimeoutMillis();

            Response proceed(Request request) throws IOException;

            int readTimeoutMillis();

            Request request();

            Chain withConnectTimeout(int i, TimeUnit timeUnit);

            Chain withReadTimeout(int i, TimeUnit timeUnit);

            Chain withWriteTimeout(int i, TimeUnit timeUnit);

            int writeTimeoutMillis();
        }

        Response intercept(Chain chain) throws IOException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T requireNonNull(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    public static Client.Builder newClientBuilder() {
        return new Client.Builder();
    }

    public static Request.Builder newRequestBuilder() {
        return new Request.Builder();
    }

    public static class Client {
        final int connectTimeout;
        final EventListener eventListener;
        final HostnameVerifier hostnameVerifier;
        final List<Interceptor> interceptors;
        final Proxy proxy;
        final int readTimeout;
        final SSLSocketFactory sslSocketFactory;
        final boolean trustAllCerts;
        final int writeTimeout;

        public EventListener getEventListener() {
            return this.eventListener;
        }

        Client(Builder builder) {
            this.interceptors = Collections.unmodifiableList(new ArrayList(builder.interceptors));
            this.connectTimeout = builder.connectTimeout;
            this.readTimeout = builder.readTimeout;
            this.writeTimeout = builder.writeTimeout;
            this.sslSocketFactory = builder.sslSocketFactory;
            this.hostnameVerifier = builder.hostnameVerifier;
            this.trustAllCerts = builder.trustAllCerts;
            this.proxy = builder.proxy;
            this.eventListener = builder.eventListener;
        }

        public Call newCall(Request request) {
            return new Call(this, request);
        }

        public static class Builder {
            HostnameVerifier hostnameVerifier;
            SSLSocketFactory sslSocketFactory;
            final List<Interceptor> interceptors = new ArrayList();
            boolean trustAllCerts = true;
            Proxy proxy = null;
            EventListener eventListener = null;
            int connectTimeout = 5000;
            int readTimeout = 5000;
            int writeTimeout = 5000;

            public Builder addInterceptor(Interceptor interceptor) {
                TdsHttp.requireNonNull(interceptor);
                this.interceptors.add(interceptor);
                return this;
            }

            public Builder connectTimeout(long j, TimeUnit timeUnit) {
                this.connectTimeout = (int) timeUnit.toMillis(j);
                return this;
            }

            public Builder readTimeout(long j, TimeUnit timeUnit) {
                this.readTimeout = (int) timeUnit.toMillis(j);
                return this;
            }

            public Builder writeTimeout(long j, TimeUnit timeUnit) {
                this.writeTimeout = (int) timeUnit.toMillis(j);
                return this;
            }

            public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
                this.sslSocketFactory = sSLSocketFactory;
                return this;
            }

            public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
                this.hostnameVerifier = hostnameVerifier;
                return this;
            }

            public Builder trustAllCerts(boolean z) {
                this.trustAllCerts = z;
                return this;
            }

            public Builder proxy(Proxy proxy) {
                this.proxy = proxy;
                return this;
            }

            public Builder addEventListener(EventListener eventListener) {
                this.eventListener = eventListener;
                return this;
            }

            public Client build() {
                return new Client(this);
            }
        }
    }

    public static class Request {
        public final RequestBody body;
        public final Map<String, String> headers;
        public final String method;
        public final String url;

        public Request(Builder builder) {
            HashMap map = new HashMap();
            this.headers = map;
            this.method = builder.method;
            this.url = builder.url;
            map.putAll(builder.headers);
            this.body = builder.body;
        }

        public String method() {
            return this.method;
        }

        public String url() {
            return this.url;
        }

        public Map<String, String> headers() {
            return this.headers;
        }

        public static class Builder {
            RequestBody body;
            String url;
            String method = g.L;
            final Map<String, String> headers = new HashMap();

            public Builder url(String str) {
                TdsHttp.requireNonNull(str);
                this.url = str;
                return this;
            }

            public Builder addHeaders(Map<String, String> map) {
                this.headers.putAll(map);
                return this;
            }

            public Builder header(String str, String str2) {
                this.headers.put(str, str2);
                return this;
            }

            public Builder removeHeader(String str) {
                this.headers.remove(str);
                return this;
            }

            public Builder get() {
                return method(g.L, null);
            }

            public Builder head() {
                return method("HEAD", null);
            }

            public Builder post(RequestBody requestBody) {
                return method(g.O, requestBody);
            }

            public Builder delete() {
                return delete(null);
            }

            public Builder delete(RequestBody requestBody) {
                return method(g.K, requestBody);
            }

            public Builder method(String str, RequestBody requestBody) {
                this.method = str;
                this.body = requestBody;
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }

    public static abstract class RequestBody {
        public long contentLength() throws IOException {
            return -1L;
        }

        public abstract String contentType();

        public abstract void writeTo(OutputStream outputStream) throws IOException;

        public static RequestBody createJsonBody(String str) {
            return create("application/json", str);
        }

        public static RequestBody createProtoBuffBody(byte[] bArr) {
            return create("application/x-protobuf", bArr);
        }

        public static RequestBody create(String str, String str2) {
            return create(str, str2.getBytes());
        }

        public static RequestBody create(final String str, final byte[] bArr) {
            return new RequestBody() { // from class: com.tds.common.net.TdsHttp.RequestBody.1
                @Override // com.tds.common.net.TdsHttp.RequestBody
                public String contentType() {
                    return str;
                }

                @Override // com.tds.common.net.TdsHttp.RequestBody
                public long contentLength() throws IOException {
                    return bArr.length;
                }

                @Override // com.tds.common.net.TdsHttp.RequestBody
                public void writeTo(OutputStream outputStream) throws IOException {
                    outputStream.write(bArr);
                }
            };
        }

        public static RequestBody create(final String str, final File file) {
            TdsHttp.requireNonNull(file);
            return new RequestBody() { // from class: com.tds.common.net.TdsHttp.RequestBody.2
                @Override // com.tds.common.net.TdsHttp.RequestBody
                public String contentType() {
                    return str;
                }

                @Override // com.tds.common.net.TdsHttp.RequestBody
                public long contentLength() throws IOException {
                    return file.length();
                }

                @Override // com.tds.common.net.TdsHttp.RequestBody
                public void writeTo(OutputStream outputStream) throws Throwable {
                    IoUtil.copy(file, outputStream);
                }
            };
        }
    }

    public static class MultipartBody extends RequestBody {
        static final String CRLF = "\r\n";
        static final String DASHDASH = "--";
        final String boundary;
        private long contentLength = -1;
        final List<Part> parts;
        final String type;

        MultipartBody(Builder builder) {
            this.boundary = builder.boundary;
            this.type = builder.type;
            this.parts = Collections.unmodifiableList(new ArrayList(builder.parts));
        }

        @Override // com.tds.common.net.TdsHttp.RequestBody
        public String contentType() {
            return this.type;
        }

        @Override // com.tds.common.net.TdsHttp.RequestBody
        public long contentLength() throws IOException {
            long j = this.contentLength;
            if (j != -1) {
                return j;
            }
            long jWriteOrCountBytes = writeOrCountBytes(null);
            this.contentLength = jWriteOrCountBytes;
            return jWriteOrCountBytes;
        }

        @Override // com.tds.common.net.TdsHttp.RequestBody
        public void writeTo(OutputStream outputStream) throws IOException {
            writeOrCountBytes(outputStream);
        }

        private long writeOrCountBytes(OutputStream outputStream) throws IOException {
            boolean z = outputStream == null;
            if (z) {
                outputStream = new CountBytesOutputStream();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            int size = this.parts.size();
            for (int i = 0; i < size; i++) {
                Part part = this.parts.get(i);
                List<String> list = part.headers;
                RequestBody requestBody = part.body;
                outputStreamWriter.append((CharSequence) DASHDASH).append((CharSequence) this.boundary).append((CharSequence) CRLF);
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    outputStreamWriter.append((CharSequence) it.next()).append((CharSequence) CRLF);
                }
                if (z && requestBody.contentLength() == -1) {
                    return -1L;
                }
                outputStreamWriter.append((CharSequence) CRLF).flush();
                requestBody.writeTo(outputStream);
                outputStreamWriter.append((CharSequence) CRLF);
            }
            outputStreamWriter.append((CharSequence) DASHDASH).append((CharSequence) this.boundary).append((CharSequence) DASHDASH).append((CharSequence) CRLF).flush();
            if (z) {
                return ((CountBytesOutputStream) outputStream).length;
            }
            return 0L;
        }

        public static final class Part {
            final RequestBody body;
            final List<String> headers;

            Part(List<String> list, RequestBody requestBody) {
                this.headers = list;
                this.body = requestBody;
            }
        }

        public static final class Builder {
            final String boundary;
            final List<Part> parts;
            final String type;

            public Builder() {
                String str = "----" + System.currentTimeMillis();
                this.boundary = str;
                this.type = "multipart/form-data; boundary=" + str;
                this.parts = new ArrayList();
            }

            Builder addPart(Part part) {
                this.parts.add(part);
                return this;
            }

            public Builder addFormDataPart(Map<String, String> map) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    addFormDataPart(entry.getKey(), entry.getValue());
                }
                return this;
            }

            public Builder addFormDataPart(String str, String str2) {
                return addPart(new Part(Arrays.asList("Content-Disposition: form-data; name=\"" + str + "\"", "Content-Type: text/plain; charset=UTF-8"), RequestBody.create((String) null, str2)));
            }

            public Builder addFormDataPart(String str, File file) {
                return addFormDataPart(str, file.getName(), RequestBody.create((String) null, file));
            }

            public Builder addFormDataPart(String str, String str2, RequestBody requestBody) {
                return addPart(new Part(Arrays.asList("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str2 + "\"", "Content-Type: " + URLConnection.guessContentTypeFromName(str2), "Content-Transfer-Encoding: binary"), requestBody));
            }

            public MultipartBody build() {
                return new MultipartBody(this);
            }
        }
    }

    public static class FormBody extends RequestBody {
        private long contentLength = -1;
        private final List<String> encodedNames;
        private final List<String> encodedValues;

        @Override // com.tds.common.net.TdsHttp.RequestBody
        public String contentType() {
            return g.p;
        }

        FormBody(List<String> list, List<String> list2) {
            this.encodedNames = new ArrayList(list);
            this.encodedValues = new ArrayList(list2);
        }

        @Override // com.tds.common.net.TdsHttp.RequestBody
        public long contentLength() throws IOException {
            long j = this.contentLength;
            if (j != -1) {
                return j;
            }
            long jWriteOrCountBytes = writeOrCountBytes(null);
            this.contentLength = jWriteOrCountBytes;
            return jWriteOrCountBytes;
        }

        @Override // com.tds.common.net.TdsHttp.RequestBody
        public void writeTo(OutputStream outputStream) throws IOException {
            writeOrCountBytes(outputStream);
        }

        private long writeOrCountBytes(OutputStream outputStream) throws IOException {
            boolean z = outputStream == null;
            if (z) {
                outputStream = new CountBytesOutputStream();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            int size = this.encodedNames.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    outputStreamWriter.append((CharSequence) a.k);
                }
                outputStreamWriter.append((CharSequence) this.encodedNames.get(i));
                outputStreamWriter.append((CharSequence) "=");
                outputStreamWriter.append((CharSequence) this.encodedValues.get(i));
            }
            outputStreamWriter.flush();
            if (z) {
                return ((CountBytesOutputStream) outputStream).length;
            }
            return 0L;
        }

        public static final class Builder {
            private final List<String> names = new ArrayList();
            private final List<String> values = new ArrayList();
            private final String charset = "UTF-8";

            public Builder add(String str, String str2) {
                TdsHttp.requireNonNull(str);
                TdsHttp.requireNonNull(str2);
                this.names.add(encode(str));
                this.values.add(encode(str2));
                return this;
            }

            public Builder addEncoded(String str, String str2) {
                TdsHttp.requireNonNull(str);
                TdsHttp.requireNonNull(str2);
                this.names.add(str);
                this.values.add(str2);
                return this;
            }

            public FormBody build() {
                return new FormBody(this.names, this.values);
            }

            private String encode(String str) {
                try {
                    return URLEncoder.encode(str, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return str;
                }
            }
        }
    }

    static class CountBytesOutputStream extends OutputStream {
        long length = 0;

        CountBytesOutputStream() {
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            this.length++;
        }
    }

    public static class Response implements Closeable {
        final ResponseBody body;
        final int code;
        final Map<String, List<String>> headers;
        final String message;
        final Request request;

        public Response(Builder builder) {
            HashMap map = new HashMap();
            this.headers = map;
            this.request = builder.request;
            this.code = builder.code;
            this.message = builder.message;
            map.putAll(builder.headers);
            this.body = builder.body;
        }

        public int code() {
            return this.code;
        }

        public String message() {
            return this.message;
        }

        public ResponseBody body() {
            return this.body;
        }

        public Map<String, List<String>> getHeaders() {
            return this.headers;
        }

        public boolean isSuccessful() {
            int i = this.code;
            return i >= 200 && i < 300;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            ResponseBody responseBody = this.body;
            if (responseBody != null) {
                responseBody.close();
            }
        }

        public static class Builder {
            ResponseBody body;
            int code = -1;
            final Map<String, List<String>> headers = new HashMap();
            String message;
            Request request;

            public Builder request(Request request) {
                this.request = request;
                return this;
            }

            public Builder code(int i) {
                this.code = i;
                return this;
            }

            public Builder message(String str) {
                this.message = str;
                return this;
            }

            public Builder header(String str, String str2) {
                this.headers.put(str, Collections.singletonList(str2));
                return this;
            }

            public Builder headers(Map<String, List<String>> map) {
                this.headers.putAll(map);
                return this;
            }

            public Builder removeHeader(String str) {
                this.headers.remove(str);
                return this;
            }

            public Builder body(ResponseBody responseBody) {
                this.body = responseBody;
                return this;
            }

            public Response build() {
                return new Response(this);
            }
        }
    }

    public static abstract class ResponseBody implements Closeable {
        private String cacheResponse;

        public abstract InputStream byteStream() throws IOException;

        public long contentLength() {
            return -1L;
        }

        public abstract String contentType();

        public final String string() throws IOException {
            try {
                String str = this.cacheResponse;
                if (str != null && str.length() > 0) {
                    return this.cacheResponse;
                }
                String string = IoUtil.readString(byteStream());
                this.cacheResponse = string;
                return string;
            } finally {
                close();
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            IoUtil.closeQuietly(byteStream());
        }

        public static ResponseBody create(final HttpURLConnection httpURLConnection) {
            TdsHttp.requireNonNull(httpURLConnection);
            return new ResponseBody() { // from class: com.tds.common.net.TdsHttp.ResponseBody.1
                @Override // com.tds.common.net.TdsHttp.ResponseBody
                public String contentType() {
                    return httpURLConnection.getContentType();
                }

                @Override // com.tds.common.net.TdsHttp.ResponseBody
                public long contentLength() {
                    return httpURLConnection.getContentLength();
                }

                @Override // com.tds.common.net.TdsHttp.ResponseBody
                public InputStream byteStream() throws IOException {
                    InputStream errorStream = httpURLConnection.getErrorStream();
                    if (errorStream == null) {
                        errorStream = httpURLConnection.getInputStream();
                    }
                    String headerField = httpURLConnection.getHeaderField(g.x);
                    return (TextUtils.isEmpty(headerField) || headerField == null || !headerField.toLowerCase(Locale.US).contains(g.r)) ? errorStream : new GZIPInputStream(errorStream);
                }

                @Override // com.tds.common.net.TdsHttp.ResponseBody, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    try {
                        IoUtil.closeQuietly(httpURLConnection.getErrorStream());
                        IoUtil.closeQuietly(httpURLConnection.getInputStream());
                    } catch (IOException unused) {
                    } catch (Throwable th) {
                        httpURLConnection.disconnect();
                        throw th;
                    }
                    httpURLConnection.disconnect();
                }
            };
        }
    }

    public static class Call {
        final Client client;
        final Request originalRequest;

        public Request getOriginalRequest() {
            return this.originalRequest;
        }

        Call(Client client, Request request) {
            this.client = client;
            this.originalRequest = request;
        }

        public Response execute() throws IOException {
            ArrayList arrayList = new ArrayList(this.client.interceptors);
            arrayList.add(new CallServerInterceptor());
            return new RealInterceptorChain(arrayList, 0, this.originalRequest, this, this.client.connectTimeout, this.client.readTimeout, this.client.writeTimeout).proceed(this.originalRequest);
        }
    }

    static final class CallServerInterceptor implements Interceptor {
        CallServerInterceptor() {
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            HttpURLConnection httpURLConnectionOpen = open(request.url, chain.call().client);
            httpURLConnectionOpen.setConnectTimeout(chain.connectTimeoutMillis());
            httpURLConnectionOpen.setReadTimeout(chain.readTimeoutMillis());
            writeRequest(httpURLConnectionOpen, request);
            int responseCode = httpURLConnectionOpen.getResponseCode();
            String responseMessage = httpURLConnectionOpen.getResponseMessage();
            Map<String, List<String>> headerFields = httpURLConnectionOpen.getHeaderFields();
            return new Response.Builder().request(request).code(responseCode).message(responseMessage).headers(headerFields).body(ResponseBody.create(httpURLConnectionOpen)).build();
        }

        private void writeRequest(HttpURLConnection httpURLConnection, Request request) throws IOException {
            httpURLConnection.setRequestMethod(request.method);
            for (Map.Entry<String, String> entry : request.headers.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            if (request.body != null) {
                String strContentType = request.body.contentType();
                if (strContentType != null) {
                    httpURLConnection.setRequestProperty("Content-Type", strContentType);
                }
                long jContentLength = request.body.contentLength();
                if (jContentLength > 0) {
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(jContentLength));
                }
                httpURLConnection.setDoOutput(true);
                long jContentLength2 = request.body.contentLength();
                if (jContentLength2 > 0) {
                    httpURLConnection.setFixedLengthStreamingMode((int) jContentLength2);
                } else {
                    httpURLConnection.setChunkedStreamingMode(0);
                }
                OutputStream outputStream = null;
                try {
                    outputStream = httpURLConnection.getOutputStream();
                    request.body.writeTo(outputStream);
                } finally {
                    IoUtil.closeQuietly(outputStream);
                }
            }
        }

        private static HttpURLConnection open(String str, Client client) throws IOException {
            HttpURLConnection httpURLConnection;
            if (client.proxy != null) {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection(client.proxy);
            } else {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            }
            if (httpURLConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
                if (!client.trustAllCerts) {
                    if (client.sslSocketFactory != null) {
                        httpsURLConnection.setSSLSocketFactory(client.sslSocketFactory);
                    }
                    if (client.hostnameVerifier != null) {
                        httpsURLConnection.setHostnameVerifier(client.hostnameVerifier);
                    }
                }
            }
            return httpURLConnection;
        }
    }

    static final class RealInterceptorChain implements Interceptor.Chain {
        private final Call call;
        private final int connectTimeout;
        private final int index;
        private final List<Interceptor> interceptors;
        private final int readTimeout;
        private final Request request;
        private final int writeTimeout;

        RealInterceptorChain(List<Interceptor> list, int i, Request request, Call call, int i2, int i3, int i4) {
            this.interceptors = list;
            this.index = i;
            this.request = request;
            this.call = call;
            this.connectTimeout = i2;
            this.readTimeout = i3;
            this.writeTimeout = i4;
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public Request request() {
            return this.request;
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public Response proceed(Request request) throws IOException {
            if (this.index >= this.interceptors.size()) {
                throw new AssertionError();
            }
            RealInterceptorChain realInterceptorChain = new RealInterceptorChain(this.interceptors, this.index + 1, request, this.call, this.connectTimeout, this.readTimeout, this.writeTimeout);
            Interceptor interceptor = this.interceptors.get(this.index);
            Response responseIntercept = interceptor.intercept(realInterceptorChain);
            if (responseIntercept == null) {
                throw new NullPointerException(interceptor + " returned null");
            }
            if (responseIntercept.body != null) {
                return responseIntercept;
            }
            throw new IllegalStateException(interceptor + " returned a response with no body");
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public Call call() {
            return this.call;
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public int connectTimeoutMillis() {
            return this.connectTimeout;
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public Interceptor.Chain withConnectTimeout(int i, TimeUnit timeUnit) {
            return new RealInterceptorChain(this.interceptors, this.index, this.request, this.call, (int) timeUnit.toMillis(i), this.readTimeout, this.writeTimeout);
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public int readTimeoutMillis() {
            return this.readTimeout;
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public Interceptor.Chain withReadTimeout(int i, TimeUnit timeUnit) {
            return new RealInterceptorChain(this.interceptors, this.index, this.request, this.call, this.connectTimeout, (int) timeUnit.toMillis(i), this.writeTimeout);
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public int writeTimeoutMillis() {
            return this.writeTimeout;
        }

        @Override // com.tds.common.net.TdsHttp.Interceptor.Chain
        public Interceptor.Chain withWriteTimeout(int i, TimeUnit timeUnit) {
            return new RealInterceptorChain(this.interceptors, this.index, this.request, this.call, this.connectTimeout, this.readTimeout, (int) timeUnit.toMillis(i));
        }
    }
}
