package com.tds.common.net;

import android.text.TextUtils;
import com.tds.common.net.TdsHttp;
import com.tds.common.net.exception.ServerException;
import com.tds.common.net.json.JsonUtil;
import com.tds.common.net.json.TypeRef;
import com.tds.common.net.util.HttpUtil;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.tapdb.b.g;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TdsApiClient {
    private String baseUrl;
    private TdsHttp.Client tdsClient;
    private String userAgent;

    private TdsApiClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.userAgent = builder.userAgent;
        this.tdsClient = builder.tdsClient;
    }

    public static class Builder {
        private String baseUrl;
        private TdsHttp.Client tdsClient;
        private String userAgent;

        public Builder() {
        }

        Builder(TdsApiClient tdsApiClient) {
            this.baseUrl = tdsApiClient.baseUrl;
            this.userAgent = tdsApiClient.userAgent;
            this.tdsClient = tdsApiClient.tdsClient;
        }

        public Builder baseUrl(String str) {
            this.baseUrl = str;
            return this;
        }

        public Builder userAgent(String str) {
            this.userAgent = str;
            return this;
        }

        public Builder tdsClient(TdsHttp.Client client) {
            this.tdsClient = client;
            return this;
        }

        public TdsApiClient build() {
            if (this.baseUrl == null) {
                throw new IllegalStateException("baseUrl required");
            }
            return new TdsApiClient(this);
        }
    }

    public <T> T get(Class<T> cls, String str) throws JSONException, IOException, ServerException {
        return (T) get(cls, str, (Map<String, String>) null);
    }

    public <T> T get(Class<T> cls, String str, Map<String, String> map) throws JSONException, IOException, ServerException {
        return (T) get(cls, str, map, (Map<String, String>) null);
    }

    public <T> T get(Class<T> cls, String str, Map<String, String> map, Map<String, String> map2) throws JSONException, IOException, ServerException {
        return (T) JsonUtil.parse(get(str, map, map2), cls);
    }

    public <T> T get(TypeRef<T> typeRef, String str) throws JSONException, IOException, ServerException {
        return (T) get(typeRef, str, (Map<String, String>) null);
    }

    public <T> T get(TypeRef<T> typeRef, String str, Map<String, String> map) throws JSONException, IOException, ServerException {
        return (T) get(typeRef, str, map, (Map<String, String>) null);
    }

    public <T> T get(TypeRef<T> typeRef, String str, Map<String, String> map, Map<String, String> map2) throws JSONException, IOException, ServerException {
        return (T) JsonUtil.parse(get(str, map, map2), typeRef);
    }

    public String get(String str) throws IOException, ServerException {
        return get(str, (Map<String, String>) null);
    }

    public String get(String str, Map<String, String> map) throws IOException, ServerException {
        return get(str, map, (Map<String, String>) null);
    }

    public String get(String str, Map<String, String> map, Map<String, String> map2) throws IOException, ServerException {
        return request(str, map, map2, g.L, null);
    }

    public <T> Observable<T> getAsync(Class<T> cls, String str) {
        return getAsync(cls, str, (Map<String, String>) null);
    }

    public <T> Observable<T> getAsync(Class<T> cls, String str, Map<String, String> map) {
        return getAsync(cls, str, map, (Map<String, String>) null);
    }

    public <T> Observable<T> getAsync(final Class<T> cls, final String str, final Map<String, String> map, final Map<String, String> map2) {
        return Observable.create(new Observable.OnSubscribe<T>() { // from class: com.tds.common.net.TdsApiClient.1
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super T> subscriber) {
                try {
                    try {
                        subscriber.onNext((Object) TdsApiClient.this.get(cls, str, map, map2));
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public <T> Observable<T> getAsync(TypeRef<T> typeRef, String str) {
        return getAsync(typeRef, str, (Map<String, String>) null);
    }

    public <T> Observable<T> getAsync(TypeRef<T> typeRef, String str, Map<String, String> map) {
        return getAsync(typeRef, str, map, (Map<String, String>) null);
    }

    public <T> Observable<T> getAsync(final TypeRef<T> typeRef, final String str, final Map<String, String> map, final Map<String, String> map2) {
        return Observable.create(new Observable.OnSubscribe<T>() { // from class: com.tds.common.net.TdsApiClient.2
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super T> subscriber) {
                try {
                    try {
                        subscriber.onNext((Object) TdsApiClient.this.get(typeRef, str, map, map2));
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<String> getAsync(String str) {
        return getAsync(str, (Map<String, String>) null);
    }

    public Observable<String> getAsync(String str, Map<String, String> map) {
        return getAsync(str, map, (Map<String, String>) null);
    }

    public Observable<String> getAsync(final String str, final Map<String, String> map, final Map<String, String> map2) {
        return Observable.create(new Observable.OnSubscribe<String>() { // from class: com.tds.common.net.TdsApiClient.3
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super String> subscriber) {
                try {
                    try {
                        subscriber.onNext(TdsApiClient.this.get(str, map, map2));
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public String post(String str, JSONObject jSONObject) throws IOException, ServerException {
        return request(str, null, null, g.O, jSONObject);
    }

    public String post(String str, Map<String, String> map, Map<String, String> map2, JSONObject jSONObject) throws IOException, ServerException {
        return request(str, map, map2, g.O, jSONObject);
    }

    public <T> T post(Class<T> cls, String str, JSONObject jSONObject) throws JSONException, IOException, ServerException {
        return (T) post(cls, str, (Map<String, String>) null, (Map<String, String>) null, jSONObject);
    }

    public <T> T post(Class<T> cls, String str, Map<String, String> map, Map<String, String> map2, JSONObject jSONObject) throws JSONException, IOException, ServerException {
        return (T) JsonUtil.parse(post(str, map, map2, jSONObject), cls);
    }

    public <T> T post(TypeRef<T> typeRef, String str, JSONObject jSONObject) throws JSONException, IOException, ServerException {
        return (T) post(typeRef, str, (Map<String, String>) null, (Map<String, String>) null, jSONObject);
    }

    public <T> T post(TypeRef<T> typeRef, String str, Map<String, String> map, Map<String, String> map2, JSONObject jSONObject) throws JSONException, IOException, ServerException {
        return (T) JsonUtil.parse(post(str, map, map2, jSONObject), typeRef);
    }

    public Observable<String> postAsync(String str, JSONObject jSONObject) {
        return postAsync(str, null, null, jSONObject);
    }

    public Observable<String> postAsync(final String str, final Map<String, String> map, final Map<String, String> map2, final JSONObject jSONObject) {
        return Observable.create(new Observable.OnSubscribe<String>() { // from class: com.tds.common.net.TdsApiClient.4
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super String> subscriber) {
                try {
                    try {
                        subscriber.onNext(TdsApiClient.this.post(str, map, map2, jSONObject));
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public <T> Observable<T> postAsync(Class<T> cls, String str, JSONObject jSONObject) {
        return postAsync(cls, str, (Map<String, String>) null, (Map<String, String>) null, jSONObject);
    }

    public <T> Observable<T> postAsync(final Class<T> cls, final String str, final Map<String, String> map, final Map<String, String> map2, final JSONObject jSONObject) {
        return Observable.create(new Observable.OnSubscribe<T>() { // from class: com.tds.common.net.TdsApiClient.5
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super T> subscriber) {
                try {
                    try {
                        subscriber.onNext((Object) TdsApiClient.this.post(cls, str, map, map2, jSONObject));
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public <T> Observable<T> postAsync(TypeRef<T> typeRef, String str, JSONObject jSONObject) {
        return postAsync(typeRef, str, (Map<String, String>) null, (Map<String, String>) null, jSONObject);
    }

    public <T> Observable<T> postAsync(final TypeRef<T> typeRef, final String str, final Map<String, String> map, final Map<String, String> map2, final JSONObject jSONObject) {
        return Observable.create(new Observable.OnSubscribe<T>() { // from class: com.tds.common.net.TdsApiClient.6
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super T> subscriber) {
                try {
                    try {
                        subscriber.onNext((Object) TdsApiClient.this.post(typeRef, str, map, map2, jSONObject));
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public int postProtoBuff(String str, Map<String, String> map, Map<String, String> map2, byte[] bArr) throws IOException {
        TdsHttp.Request.Builder builderUrl = new TdsHttp.Request.Builder().url(HttpUtil.buildUrl(this.baseUrl + str, map));
        if (map2 != null) {
            builderUrl.addHeaders(map2);
        }
        if (bArr == null) {
            builderUrl.method(g.O, null);
        } else {
            builderUrl.method(g.O, TdsHttp.RequestBody.createProtoBuffBody(bArr));
        }
        return this.tdsClient.newCall(builderUrl.build()).execute().code;
    }

    private String request(String str, Map<String, String> map, Map<String, String> map2, String str2, JSONObject jSONObject) throws IOException, ServerException {
        TdsHttp.Request.Builder builderUrl = new TdsHttp.Request.Builder().url(HttpUtil.buildUrl(this.baseUrl + str, map));
        if (map2 != null) {
            builderUrl.addHeaders(map2);
        }
        if (jSONObject == null) {
            builderUrl.method(str2, null);
        } else if (map2 != null && !TextUtils.isEmpty(map2.get("Content-Type"))) {
            String str3 = map2.get("Content-Type");
            Objects.requireNonNull(str3);
            if (str3.contains(g.p)) {
                TdsHttp.FormBody.Builder builder = new TdsHttp.FormBody.Builder();
                try {
                    for (Map.Entry<String, Object> entry : JsonUtil.toMap(jSONObject).entrySet()) {
                        if (entry.getValue() != null && !TextUtils.isEmpty(entry.getValue().toString())) {
                            builder.add(entry.getKey(), entry.getValue().toString());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                builderUrl.method(str2, builder.build());
            } else {
                builderUrl.method(str2, TdsHttp.RequestBody.create(map2.get("Content-Type"), jSONObject.toString()));
            }
        } else {
            builderUrl.method(str2, TdsHttp.RequestBody.createJsonBody(jSONObject.toString()));
        }
        try {
            TdsHttp.Response responseExecute = this.tdsClient.newCall(builderUrl.build()).execute();
            if (this.tdsClient.eventListener != null) {
                this.tdsClient.eventListener.callEnd(this.baseUrl);
            }
            if (responseExecute.isSuccessful()) {
                return responseExecute.body().string();
            }
            TDSNetInterceptor.interceptWithContent(responseExecute.code(), responseExecute.message, responseExecute.body().string());
            throw new ServerException(responseExecute.code(), responseExecute.message(), responseExecute.body().string());
        } catch (Error | Exception e2) {
            if (this.tdsClient.eventListener != null) {
                this.tdsClient.eventListener.callFailed(this.baseUrl, new IOException(e2));
            }
            throw e2;
        }
    }
}
