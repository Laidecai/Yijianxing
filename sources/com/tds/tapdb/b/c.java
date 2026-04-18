package com.tds.tapdb.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Process;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.os.EnvironmentCompat;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static final String a = "com.tds.common.isc.IscServiceManager";
    private static final String b = "com.tds.common.isc.Service";

    public static String a() {
        try {
            Class<?> cls = Class.forName(a);
            Class<?> cls2 = Class.forName(b);
            return new JSONObject((String) cls2.getDeclaredMethod("directCall", String.class, Object[].class).invoke(cls.getDeclaredMethod(NotificationCompat.CATEGORY_SERVICE, String.class).invoke(null, "TapLogin"), "currentProfile", null)).optString("openid");
        } catch (Exception e) {
            n.c(e.getMessage());
            return null;
        }
    }

    public static String a(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            n.a(e);
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(java.lang.String r6) {
        /*
            java.lang.String r0 = "0000000000000000"
            java.lang.String r1 = "ffffffffffffffff"
            java.lang.String[] r0 = new java.lang.String[]{r0, r1}
            if (r6 == 0) goto L3f
            java.lang.String r1 = "^[0-9a-zA-Z]{8,16}$"
            boolean r1 = r6.matches(r1)
            if (r1 != 0) goto L13
            goto L3e
        L13:
            int r1 = r6.length()
            r2 = 16
            if (r1 >= r2) goto L34
            r3 = 0
        L1c:
            int r4 = 16 - r1
            if (r3 >= r4) goto L34
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "0"
            r4.append(r5)
            r4.append(r6)
            java.lang.String r6 = r4.toString()
            int r3 = r3 + 1
            goto L1c
        L34:
            java.util.List r0 = java.util.Arrays.asList(r0)
            boolean r0 = r0.contains(r6)
            if (r0 == 0) goto L3f
        L3e:
            r6 = 0
        L3f:
            if (r6 == 0) goto L42
            goto L4a
        L42:
            java.util.UUID r6 = java.util.UUID.randomUUID()
            java.lang.String r6 = r6.toString()
        L4a:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.tapdb.b.c.a(java.lang.String):java.lang.String");
    }

    public static JSONObject a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        JSONObject jSONObject2 = new JSONObject();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (next.startsWith("#")) {
                jSONObject2.put(next, jSONObject.get(next));
            } else {
                jSONObject2.put("#" + next, jSONObject.get(next));
            }
        }
        return jSONObject2;
    }

    public static void a(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (jSONObject == null || TextUtils.isEmpty(str) || obj == null) {
            return;
        }
        if ((obj instanceof CharSequence) && TextUtils.isEmpty((CharSequence) obj)) {
            return;
        }
        jSONObject.put(str, obj);
    }

    public static void a(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject == null) {
            return;
        }
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Object obj = jSONObject.get(next);
                if (!jSONObject2.has(next)) {
                    jSONObject2.put(next, obj);
                }
            }
        } catch (Exception e) {
            n.a(e);
        }
    }

    public static boolean a(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    public static String b(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return (telephonyManager == null || TextUtils.isEmpty(telephonyManager.getNetworkOperatorName())) ? EnvironmentCompat.MEDIA_UNKNOWN : telephonyManager.getNetworkOperatorName();
        } catch (Exception e) {
            n.a((Throwable) e);
            return null;
        }
    }

    public static JSONObject b(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        if (jSONObject2 == null) {
            return jSONObject;
        }
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Iterator<String> itKeys2 = jSONObject2.keys();
                while (itKeys2.hasNext()) {
                    String next2 = itKeys2.next();
                    if (!TextUtils.isEmpty(next) && next.equalsIgnoreCase(next2)) {
                        itKeys2.remove();
                    }
                }
            }
            a(jSONObject, jSONObject2);
        } catch (Exception e) {
            n.a(e);
        }
        return jSONObject2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0079, code lost:
    
        if (r8.length() <= 256) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String c(android.content.Context r8) {
        /*
            android.content.SharedPreferences r0 = android.preference.PreferenceManager.getDefaultSharedPreferences(r8)
            java.lang.String r1 = "tapdb_game_mobile_identify"
            r2 = 0
            if (r0 == 0) goto L3e
            java.lang.String r3 = r0.getString(r1, r2)     // Catch: java.lang.ClassCastException -> L10
            if (r3 == 0) goto L15
            return r3
        L10:
            r3 = move-exception
            r3.printStackTrace()
            r3 = r2
        L15:
            java.util.Map r4 = r0.getAll()
            java.util.Set r5 = r4.keySet()
            java.util.Iterator r5 = r5.iterator()
        L21:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L3b
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r7 = "__game_mobile__0__"
            boolean r7 = r6.contains(r7)
            if (r7 == 0) goto L21
            java.lang.Object r3 = r4.get(r6)
            java.lang.String r3 = (java.lang.String) r3
        L3b:
            if (r3 == 0) goto L3f
            return r3
        L3e:
            r3 = r2
        L3f:
            r4 = 256(0x100, float:3.59E-43)
            if (r3 != 0) goto L65
            java.lang.String r8 = a(r8)
            java.lang.String r8 = a(r8)
            boolean r3 = android.text.TextUtils.isEmpty(r8)
            if (r3 != 0) goto L64
            int r3 = r8.length()
            if (r3 <= r4) goto L58
            goto L64
        L58:
            android.content.SharedPreferences$Editor r0 = r0.edit()
            android.content.SharedPreferences$Editor r0 = r0.putString(r1, r8)
            r0.commit()
            return r8
        L64:
            r3 = r2
        L65:
            if (r3 != 0) goto L7c
            java.util.UUID r8 = java.util.UUID.randomUUID()
            java.lang.String r8 = r8.toString()
            boolean r3 = android.text.TextUtils.isEmpty(r8)
            if (r3 != 0) goto L7c
            int r3 = r8.length()
            if (r3 > r4) goto L7c
            goto L58
        L7c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.tapdb.b.c.c(android.content.Context):java.lang.String");
    }

    public static String d(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences == null) {
            return "";
        }
        String string = null;
        try {
            string = defaultSharedPreferences.getString("tapdb_game_mobile_identify", null);
            if (string != null) {
                return string;
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        Map<String, ?> all = defaultSharedPreferences.getAll();
        Iterator<String> it = all.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            if (next.contains("__game_mobile__0__")) {
                string = (String) all.get(next);
                break;
            }
        }
        return string != null ? string : "";
    }

    public static String e(Context context) {
        TelephonyManager telephonyManager;
        String deviceId;
        try {
            if (!h(context) || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
                return "";
            }
            if (Build.VERSION.SDK_INT <= 28) {
                if (Build.VERSION.SDK_INT < 26) {
                    deviceId = telephonyManager.getDeviceId();
                }
                return deviceId;
            }
            if (!telephonyManager.hasCarrierPrivileges()) {
                n.b("Can not get IMEI info.");
                return "";
            }
            deviceId = telephonyManager.getImei();
            return deviceId;
        } catch (Exception e) {
            n.a(e);
            return "";
        }
    }

    public static String f(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences == null) {
            return UUID.randomUUID().toString();
        }
        String string = defaultSharedPreferences.getString("tapdb_install_uuid", null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String string2 = UUID.randomUUID().toString();
        defaultSharedPreferences.edit().putString("tapdb_install_uuid", string2).commit();
        return string2;
    }

    public static String g(Context context) {
        return f(context);
    }

    private static boolean h(Context context) {
        String str;
        if (Build.VERSION.SDK_INT > 28) {
            if (a(context, "android.permission.READ_PRECISE_PHONE_STATE")) {
                return true;
            }
            str = "Don't have permission android.permission.READ_PRECISE_PHONE_STATE,getDeviceID failed";
        } else {
            if (a(context, "android.permission.READ_PHONE_STATE")) {
                return true;
            }
            str = "Don't have permission android.permission.READ_PHONE_STATE,getDeviceID failed";
        }
        n.b(str);
        return false;
    }
}
