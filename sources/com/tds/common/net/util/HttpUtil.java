package com.tds.common.net.util;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.alipay.sdk.cons.b;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.common.account.LCAccount;
import com.tds.common.account.TDSPlatformAccount;
import com.tds.common.account.TapAccount;
import com.tds.common.account.TdsAccount;
import com.tds.common.net.exception.ServerException;
import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.common.utils.CommonUtils;
import com.tds.tapdb.b.g;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class HttpUtil {
    private static Random random = new Random();
    public static String HTTP_METHOD_POST = g.O;
    public static String HTTP_METHOD_GET = g.L;

    public static <T> T parseJsonArray(JSONArray jSONArray, Class<?> cls) throws JSONException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        int length = jSONArray.length();
        T t = (T) Array.newInstance(cls, length);
        for (int i = 0; i < length; i++) {
            Array.set(t, i, cls.getDeclaredConstructor(JSONObject.class).newInstance(jSONArray.getJSONObject(i)));
        }
        return t;
    }

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

    public static String getAuthorizationWithUrl(String str, String str2, String str3, String str4) {
        Uri uri = Uri.parse(str);
        String time = getTime();
        String randomString = getRandomString(5);
        String host = uri.getHost();
        String str5 = uri.getScheme().startsWith("wss") ? "443" : "80";
        String encodedQuery = uri.getEncodedQuery();
        String encodedPath = uri.getEncodedPath();
        if (encodedQuery != null && !encodedQuery.equals("null")) {
            encodedPath = encodedPath + "?" + encodedQuery;
        }
        return "MAC " + getAuthorizationParam(BreakpointSQLiteKey.ID, str3) + "," + getAuthorizationParam("ts", time) + "," + getAuthorizationParam("nonce", randomString) + "," + getAuthorizationParam("mac", sign(mergeSign(time, randomString, str2, encodedPath, host, str5, ""), str4));
    }

    public static String getTime() {
        return String.format(Locale.US, "%010d", Long.valueOf(System.currentTimeMillis() / 1000));
    }

    public static String getTimeMillis() {
        return String.format(Locale.US, "%010d", Long.valueOf(System.currentTimeMillis()));
    }

    public static String getRandomString(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt(36)));
        }
        return stringBuffer.toString();
    }

    public static String mergeSign(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str5) || TextUtils.isEmpty(str6)) {
            return null;
        }
        String str8 = str + "\n" + str2 + "\n" + str3 + "\n" + str4 + "\n" + str5 + "\n" + str6 + "\n";
        if (TextUtils.isEmpty(str7)) {
            return str8 + "\n";
        }
        return str8 + str7 + "\n";
    }

    public static String sign(String str, String str2) {
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

    private static String getAuthorizationParam(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        return str + "=\"" + str2 + "\"";
    }

    public static Map<String, String> getAuthorizeHeaders(TdsAccount tdsAccount, String str, String str2) {
        String authorization;
        HashMap map = new HashMap();
        int i = AnonymousClass1.$SwitchMap$com$tds$common$account$TdsAccount$AccountType[tdsAccount.getAccountType().ordinal()];
        if (i == 1 || i == 2) {
            authorization = "Bearer " + tdsAccount.getToken().toString();
        } else if (i == 3) {
            TapAccount tapAccount = (TapAccount) tdsAccount;
            authorization = getAuthorization(str, str2, tapAccount.kid, tapAccount.mac_key);
        } else if (i == 4) {
            TDSPlatformAccount tDSPlatformAccount = (TDSPlatformAccount) tdsAccount;
            authorization = getAuthorization(str, str2, tDSPlatformAccount.kid, tDSPlatformAccount.mac_key);
        } else {
            if (i == 5 && (tdsAccount instanceof LCAccount)) {
                LCAccount lCAccount = (LCAccount) tdsAccount;
                map.put("X-LC-Id", lCAccount.clientId);
                map.put("X-LC-Sign", getLcSign(lCAccount.clientToken));
                map.put("X-LC-Session", lCAccount.sessionToken);
            }
            authorization = null;
        }
        String strName = tdsAccount.getAccountType().name();
        if (!TextUtils.isEmpty(authorization)) {
            map.put(g.v, authorization);
        }
        map.put("X-Authorization-Type", strName);
        return map;
    }

    /* JADX INFO: renamed from: com.tds.common.net.util.HttpUtil$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$tds$common$account$TdsAccount$AccountType;

        static {
            int[] iArr = new int[TdsAccount.AccountType.values().length];
            $SwitchMap$com$tds$common$account$TdsAccount$AccountType = iArr;
            try {
                iArr[TdsAccount.AccountType.XD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tds$common$account$TdsAccount$AccountType[TdsAccount.AccountType.XDG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tds$common$account$TdsAccount$AccountType[TdsAccount.AccountType.TAP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tds$common$account$TdsAccount$AccountType[TdsAccount.AccountType.TDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$tds$common$account$TdsAccount$AccountType[TdsAccount.AccountType.LC.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static String getLcSign(String str) {
        String timeMillis = getTimeMillis();
        return CommonUtils.getMD5((timeMillis + str).getBytes()) + "," + timeMillis;
    }

    public static String buildUrl(String str, Map<String, String> map) {
        Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builderBuildUpon.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return builderBuildUpon.build().toString();
    }

    public static String buildUrl(String str, String str2, String str3) {
        try {
            return new Uri.Builder().scheme(str).authority(str2).path(str3).build().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractUrlHash(String str) {
        try {
            URL url = new URL(str);
            return url.getRef() != null ? url.getRef() : "";
        } catch (MalformedURLException unused) {
            return "";
        }
    }

    public static String extractUrlHost(String str) {
        try {
            URL url = new URL(str);
            return url.getHost() != null ? url.getHost() : "";
        } catch (MalformedURLException unused) {
            return "";
        }
    }

    public static String extractUrlFile(String str) {
        try {
            URL url = new URL(str);
            return url.getFile() != null ? url.getFile() : "";
        } catch (MalformedURLException unused) {
            return "";
        }
    }

    public static Pair<Integer, String> parseServerException(ServerException serverException) {
        JSONObject jSONObject;
        String message;
        try {
            jSONObject = new JSONObject(serverException.responseBody);
            message = "";
            if (jSONObject.has("data")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                i = jSONObject2.has(NetworkStateModel.PARAM_CODE) ? jSONObject2.getInt(NetworkStateModel.PARAM_CODE) : -1;
                message = jSONObject2.has("msg") ? jSONObject2.getString("msg") : "";
                if (message.length() == 0 && jSONObject2.has("error_description")) {
                    message = jSONObject2.getString("error_description");
                }
            }
        } catch (Exception e) {
            jSONObject = null;
            e.printStackTrace();
            message = !TextUtils.isEmpty(serverException.getMessage()) ? serverException.getMessage() : e.getMessage();
        }
        if (jSONObject == null && serverException.statusCode >= 400) {
            return new Pair<>(Integer.valueOf(serverException.statusCode), serverException.responseBody);
        }
        if (serverException.statusCode >= 400 && i == 0) {
            i = serverException.statusCode;
        }
        return new Pair<>(Integer.valueOf(i), message);
    }
}
