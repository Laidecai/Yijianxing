package com.alipay.sdk.sys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.alipay.sdk.util.c;
import com.alipay.sdk.util.l;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static final String j = "\"&";
    public static final String k = "&";
    public static final String l = "bizcontext=\"";
    public static final String m = "bizcontext=";
    public static final String n = "\"";
    public static final String o = "appkey";
    public static final String p = "ty";
    public static final String q = "sv";
    public static final String r = "an";
    public static final String s = "setting";
    public static final String t = "av";
    public static final String u = "sdk_start_time";
    public static final String v = "extInfo";
    public static final String w = "ap_link_token";
    public static final String x = "act_info";
    public static final String y = "UTF-8";
    public String a;
    public String b;
    public Context c;
    public final String d;
    public final long e;
    public final int f;
    public final String g;
    public final ActivityInfo h;
    public final com.alipay.sdk.app.statistic.b i;

    public a(Context context, String str, String str2) {
        String str3;
        this.a = "";
        this.b = "";
        this.c = null;
        boolean zIsEmpty = TextUtils.isEmpty(str2);
        this.i = new com.alipay.sdk.app.statistic.b(context, zIsEmpty);
        String strC = c(str, this.b);
        this.d = strC;
        this.e = SystemClock.elapsedRealtime();
        this.f = l.f();
        ActivityInfo activityInfoA = l.a(context);
        this.h = activityInfoA;
        this.g = str2;
        if (!zIsEmpty) {
            com.alipay.sdk.app.statistic.a.a(this, com.alipay.sdk.app.statistic.b.l, "eptyp", str2 + "|" + strC);
            if (activityInfoA != null) {
                str3 = activityInfoA.name + "|" + activityInfoA.launchMode;
            } else {
                str3 = "null";
            }
            com.alipay.sdk.app.statistic.a.a(this, com.alipay.sdk.app.statistic.b.l, "actInfo", str3);
            com.alipay.sdk.app.statistic.a.a(this, com.alipay.sdk.app.statistic.b.l, NotificationCompat.CATEGORY_SYSTEM, l.a(this));
        }
        try {
            this.c = context.getApplicationContext();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.a = packageInfo.versionName;
            this.b = packageInfo.packageName;
        } catch (Exception e) {
            c.a(e);
        }
        if (!zIsEmpty) {
            com.alipay.sdk.app.statistic.a.a(this, com.alipay.sdk.app.statistic.b.l, "u" + l.f());
            com.alipay.sdk.app.statistic.a.a(this, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.U, "" + SystemClock.elapsedRealtime());
            com.alipay.sdk.app.statistic.a.a(context, this, str, this.d);
        }
        if (zIsEmpty || !com.alipay.sdk.data.a.u().q()) {
            return;
        }
        com.alipay.sdk.data.a.u().a(this, this.c);
    }

    private boolean d(String str) {
        return !str.contains(j);
    }

    public static a e() {
        return null;
    }

    public Context a() {
        return this.c;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.a;
    }

    private String b(String str) {
        try {
            String strA = a(str, k, m);
            if (TextUtils.isEmpty(strA)) {
                str = str + k + b(m, "");
            } else {
                int iIndexOf = str.indexOf(strA);
                str = str.substring(0, iIndexOf) + a(strA, m, "", true) + str.substring(iIndexOf + strA.length());
            }
        } catch (Throwable unused) {
        }
        return str;
    }

    private String c(String str) {
        try {
            String strA = a(str, j, l);
            if (TextUtils.isEmpty(strA)) {
                return str + k + b(l, "\"");
            }
            if (!strA.endsWith("\"")) {
                strA = strA + "\"";
            }
            int iIndexOf = str.indexOf(strA);
            return str.substring(0, iIndexOf) + a(strA, l, "\"", false) + str.substring(iIndexOf + strA.length());
        } catch (Throwable unused) {
            return str;
        }
    }

    private JSONObject d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(w, this.d);
        } catch (Throwable unused) {
        }
        return jSONObject;
    }

    public String a(String str) {
        return (TextUtils.isEmpty(str) || str.startsWith("new_external_info==")) ? str : d(str) ? b(str) : c(str);
    }

    /* JADX INFO: renamed from: com.alipay.sdk.sys.a$a, reason: collision with other inner class name */
    public static final class C0007a {
        public static final HashMap<UUID, a> a = new HashMap<>();
        public static final HashMap<String, a> b = new HashMap<>();
        public static final String c = "i_uuid_b_c";

        public static void a(a aVar, Intent intent) {
            if (aVar == null || intent == null) {
                return;
            }
            UUID uuidRandomUUID = UUID.randomUUID();
            a.put(uuidRandomUUID, aVar);
            intent.putExtra(c, uuidRandomUUID);
        }

        public static a a(Intent intent) {
            if (intent == null) {
                return null;
            }
            Serializable serializableExtra = intent.getSerializableExtra(c);
            if (serializableExtra instanceof UUID) {
                return a.remove((UUID) serializableExtra);
            }
            return null;
        }

        public static void a(a aVar, String str) {
            if (aVar == null || TextUtils.isEmpty(str)) {
                return;
            }
            b.put(str, aVar);
        }

        public static a a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return b.remove(str);
        }
    }

    private String b(String str, String str2) throws JSONException, UnsupportedEncodingException {
        return str + a("", "") + str2;
    }

    private String a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(str2);
        for (int i = 0; i < strArrSplit.length; i++) {
            if (!TextUtils.isEmpty(strArrSplit[i]) && strArrSplit[i].startsWith(str3)) {
                return strArrSplit[i];
            }
        }
        return null;
    }

    public static String c(String str, String str2) {
        try {
            Locale locale = Locale.getDefault();
            Object[] objArr = new Object[4];
            if (str == null) {
                str = "";
            }
            objArr[0] = str;
            if (str2 == null) {
                str2 = "";
            }
            objArr[1] = str2;
            objArr[2] = Long.valueOf(System.currentTimeMillis());
            objArr[3] = UUID.randomUUID().toString();
            return String.format("EP%s%s_%s", "1", l.e(String.format(locale, "%s%s%d%s", objArr)), Long.valueOf(System.currentTimeMillis()));
        } catch (Throwable unused) {
            return "-";
        }
    }

    public String a(String str, String str2) {
        String str3;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(o, com.alipay.sdk.cons.a.f);
            jSONObject.put(p, "and_lite");
            jSONObject.put("sv", "h.a.3.8.02");
            if (!this.b.contains(s) || !l.h(this.c)) {
                jSONObject.put(r, this.b);
            }
            jSONObject.put(t, this.a);
            jSONObject.put(u, System.currentTimeMillis());
            jSONObject.put(v, d());
            if (this.h != null) {
                str3 = this.h.name + "|" + this.h.launchMode;
            } else {
                str3 = "null";
            }
            jSONObject.put(x, str3);
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put(str, str2);
            }
            return jSONObject.toString();
        } catch (Throwable th) {
            c.a(th);
            return "";
        }
    }

    private String a(String str, String str2, String str3, boolean z) throws JSONException, UnsupportedEncodingException {
        JSONObject jSONObject;
        String strSubstring = str.substring(str2.length());
        boolean z2 = false;
        String strSubstring2 = strSubstring.substring(0, strSubstring.length() - str3.length());
        if (strSubstring2.length() >= 2 && strSubstring2.startsWith("\"") && strSubstring2.endsWith("\"")) {
            jSONObject = new JSONObject(strSubstring2.substring(1, strSubstring2.length() - 1));
            z2 = true;
        } else {
            jSONObject = new JSONObject(strSubstring2);
        }
        if (!jSONObject.has(o)) {
            jSONObject.put(o, com.alipay.sdk.cons.a.f);
        }
        if (!jSONObject.has(p)) {
            jSONObject.put(p, "and_lite");
        }
        if (!jSONObject.has("sv")) {
            jSONObject.put("sv", "h.a.3.8.02");
        }
        if (!jSONObject.has(r) && (!this.b.contains(s) || !l.h(this.c))) {
            jSONObject.put(r, this.b);
        }
        if (!jSONObject.has(t)) {
            jSONObject.put(t, this.a);
        }
        if (!jSONObject.has(u)) {
            jSONObject.put(u, System.currentTimeMillis());
        }
        if (!jSONObject.has(v)) {
            jSONObject.put(v, d());
        }
        String string = jSONObject.toString();
        if (z2) {
            string = "\"" + string + "\"";
        }
        return str2 + string + str3;
    }

    public static HashMap<String, String> a(a aVar) {
        HashMap<String, String> map = new HashMap<>();
        if (aVar != null) {
            map.put("sdk_ver", "15.8.02");
            map.put("app_name", aVar.b);
            map.put("token", aVar.d);
            map.put("call_type", aVar.g);
            map.put("ts_api_invoke", String.valueOf(aVar.e));
        }
        return map;
    }
}
