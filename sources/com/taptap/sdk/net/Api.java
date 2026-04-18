package com.taptap.sdk.net;

import android.text.TextUtils;
import android.util.Base64;
import com.alipay.sdk.cons.b;
import com.alipay.sdk.data.a;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.Utils;
import com.taptap.sdk.exceptions.ServerError;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.tapdb.b.g;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Api {
    private static final int MAX_RETRY_COUNT = 1;
    private static int retryCount;

    public interface ApiCallback<T> {
        void onError(Throwable th);

        void onSuccess(T t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getUA() {
        return "TapTapAndroidSDK/3.29.0 " + System.getProperty("http.agent");
    }

    private static boolean isTimeError(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
        return jSONObjectOptJSONObject != null && TextUtils.equals(jSONObjectOptJSONObject.optString("error"), "invalid_time");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doGet(String str, AccessToken accessToken, ApiCallback<JSONObject> apiCallback) throws JSONException, IOException {
        int i;
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod(g.L);
        httpURLConnection.addRequestProperty("User-Agent", getUA());
        httpURLConnection.addRequestProperty(g.v, getAuthorization(str, g.L, accessToken.kid, accessToken.mac_key));
        httpURLConnection.setConnectTimeout(a.v);
        httpURLConnection.setReadTimeout(a.v);
        int responseCode = httpURLConnection.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (responseCode >= 200 && responseCode < 400) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                    sb.append("\n");
                } else {
                    notifyResult(apiCallback, new JSONObject(sb.toString()));
                    return;
                }
            }
        } else {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            while (true) {
                String line2 = bufferedReader2.readLine();
                if (line2 == null) {
                    break;
                }
                sb.append(line2);
                sb.append("\n");
            }
            JSONObject jSONObject = new JSONObject(sb.toString());
            if (isTimeError(jSONObject) && (i = retryCount) < 1) {
                retryCount = i + 1;
                checkTime(jSONObject.optLong("now"));
                doGet(str, accessToken, apiCallback);
            } else {
                retryCount = 0;
                notifyError(apiCallback, new ServerError(sb.toString(), responseCode));
            }
        }
    }

    public static void get(final String str, HashMap<String, String> map, final ApiCallback<JSONObject> apiCallback) {
        final AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        if (currentAccessToken == null) {
            return;
        }
        Utils.runOnAsync(new Runnable() { // from class: com.taptap.sdk.net.Api.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Api.doGet(str, currentAccessToken, apiCallback);
                } catch (Exception e) {
                    Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            apiCallback.onError(e);
                        }
                    });
                }
            }
        });
    }

    public static void post(final String str, final HashMap<String, String> map, final ApiCallback<JSONObject> apiCallback) {
        Utils.runOnAsync(new Runnable() { // from class: com.taptap.sdk.net.Api.2
            /* JADX WARN: Removed duplicated region for block: B:104:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:106:? A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:42:0x0114 A[Catch: IOException -> 0x013d, TRY_ENTER, TRY_LEAVE, TryCatch #11 {IOException -> 0x013d, blocks: (B:42:0x0114, B:61:0x0139), top: B:89:0x0003 }] */
            /* JADX WARN: Removed duplicated region for block: B:61:0x0139 A[Catch: IOException -> 0x013d, TRY_ENTER, TRY_LEAVE, TryCatch #11 {IOException -> 0x013d, blocks: (B:42:0x0114, B:61:0x0139), top: B:89:0x0003 }] */
            /* JADX WARN: Removed duplicated region for block: B:79:0x012f A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:81:0x014f A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:83:0x010a A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:85:0x0145 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() throws java.lang.Throwable {
                /*
                    Method dump skipped, instruction units count: 344
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.taptap.sdk.net.Api.AnonymousClass2.run():void");
            }
        });
    }

    public static void getWithHeader(final String str, final Map<String, String> map, final Map<String, String> map2, final ApiCallback<JSONObject> apiCallback) {
        if (TextUtils.isEmpty(str)) {
            notifyError(apiCallback, new RuntimeException("invalid url =" + str));
            return;
        }
        final AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        if (currentAccessToken == null) {
            notifyError(apiCallback, new RuntimeException("invalid token =" + str));
            return;
        }
        Utils.runOnAsync(new Runnable() { // from class: com.taptap.sdk.net.Api.3
            /* JADX WARN: Removed duplicated region for block: B:42:0x0120 A[Catch: IOException -> 0x0124, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0124, blocks: (B:42:0x0120, B:55:0x013c), top: B:66:0x004b }] */
            /* JADX WARN: Removed duplicated region for block: B:55:0x013c A[Catch: IOException -> 0x0124, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0124, blocks: (B:42:0x0120, B:55:0x013c), top: B:66:0x004b }] */
            /* JADX WARN: Removed duplicated region for block: B:63:0x0142 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:87:? A[RETURN, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() throws java.lang.Throwable {
                /*
                    Method dump skipped, instruction units count: 331
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.taptap.sdk.net.Api.AnonymousClass3.run():void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getAuthorization(String str, String str2, String str3, String str4) {
        try {
            URL url = new URL(str);
            String time = getTime();
            String randomString = getRandomString(5);
            String host = url.getHost();
            return "MAC " + getAuthorizationParam(BreakpointSQLiteKey.ID, str3) + "," + getAuthorizationParam("ts", time) + "," + getAuthorizationParam("nonce", randomString) + "," + getAuthorizationParam("mac", sign(mergeSign(time, randomString, str2, str.substring(str.lastIndexOf(host) + host.length()), host, str.startsWith(b.a) ? "443" : "80", ""), str4));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getRandomString(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(36)));
        }
        return stringBuffer.toString();
    }

    private static String sign(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return new String(Base64.encode(mac.doFinal(str.getBytes("UTF-8")), 2), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e2) {
            throw new IllegalStateException(e2);
        } catch (NoSuchAlgorithmException e3) {
            throw new IllegalStateException(e3);
        }
    }

    private static String mergeSign(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str5) || TextUtils.isEmpty(str6)) {
            return null;
        }
        String str8 = str + "\n" + str2 + "\n" + str3 + "\n" + str4 + "\n" + str5 + "\n" + str6 + "\n";
        if (TextUtils.isEmpty(str7)) {
            return str8 + "\n";
        }
        return str8 + str7 + "\n";
    }

    private static String getAuthorizationParam(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        return str + "=\"" + str2 + "\"";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyError(final ApiCallback apiCallback, final Throwable th) {
        Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.4
            @Override // java.lang.Runnable
            public void run() {
                apiCallback.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifyResult(final ApiCallback apiCallback, final JSONObject jSONObject) {
        Utils.runOnUIThread(new Runnable() { // from class: com.taptap.sdk.net.Api.5
            @Override // java.lang.Runnable
            public void run() {
                apiCallback.onSuccess(jSONObject);
            }
        });
    }

    private static void checkTime(long j) {
        if (j > 0) {
            try {
                TimeVerifier.setDelta((j * 1000) - System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getTime() {
        return String.format(Locale.US, "%010d", Long.valueOf(TimeVerifier.getCurrentTimeMillions() / 1000));
    }
}
