package com.alipay.sdk.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.data.a;
import com.tds.tapdb.service.TapTapDIDIntentService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class l {
    public static final String a = "com.alipay.android.app";
    public static final String b = "com.eg.android.AlipayGphone";
    public static final String c = "com.eg.android.AlipayGphoneRC";
    public static final int d = 99;
    public static final String[] e = {"10.1.5.1013151", "10.1.5.1013148"};
    public static final int f = 125;

    public static class a implements Runnable {
        public final /* synthetic */ Activity a;

        public a(Activity activity) {
            this.a = activity;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.finish();
        }
    }

    public static String a() {
        if (EnvUtils.isSandBox()) {
            return c;
        }
        try {
            return com.alipay.sdk.app.a.d.get(0).a;
        } catch (Throwable unused) {
            return b;
        }
    }

    public static String b(Context context) {
        return "-1;-1";
    }

    public static String b(String str) {
        return (EnvUtils.isSandBox() && TextUtils.equals(str, c)) ? "com.eg.android.AlipayGphoneRC.IAlixPay" : "com.eg.android.AlipayGphone.IAlixPay";
    }

    public static String c() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
            try {
                String line = bufferedReader.readLine();
                bufferedReader.close();
                Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(line);
                if (!matcher.matches() || matcher.groupCount() < 4) {
                    return "Unavailable";
                }
                return matcher.group(1) + "\n" + matcher.group(2) + " " + matcher.group(3) + "\n" + matcher.group(4);
            } catch (Throwable th) {
                bufferedReader.close();
                throw th;
            }
        } catch (IOException unused) {
            return "Unavailable";
        }
    }

    public static String d() {
        String strC = c();
        int iIndexOf = strC.indexOf("-");
        if (iIndexOf != -1) {
            strC = strC.substring(0, iIndexOf);
        }
        int iIndexOf2 = strC.indexOf("\n");
        if (iIndexOf2 != -1) {
            strC = strC.substring(0, iIndexOf2);
        }
        return "Linux " + strC;
    }

    public static String e(com.alipay.sdk.sys.a aVar, String str) {
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e2) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.F, e2);
            return "";
        }
    }

    public static JSONObject f(String str) {
        try {
            return new JSONObject(str);
        } catch (Throwable unused) {
            return new JSONObject();
        }
    }

    public static String g(Context context) {
        return " (" + e() + g.b + d() + g.b + c(context) + g.b + g.b + f(context) + ")(sdk android)";
    }

    public static boolean h(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a(), 128);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode < 99;
        } catch (Throwable th) {
            c.a(th);
            return false;
        }
    }

    public static boolean i(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(a, 128) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static Map<String, String> b(com.alipay.sdk.sys.a aVar, String str) {
        HashMap map = new HashMap(4);
        int iIndexOf = str.indexOf(63);
        if (iIndexOf != -1 && iIndexOf < str.length() - 1) {
            for (String str2 : str.substring(iIndexOf + 1).split(com.alipay.sdk.sys.a.k)) {
                int iIndexOf2 = str2.indexOf(61, 1);
                if (iIndexOf2 != -1 && iIndexOf2 < str2.length() - 1) {
                    map.put(str2.substring(0, iIndexOf2), e(aVar, str2.substring(iIndexOf2 + 1)));
                }
            }
        }
        return map;
    }

    public static String e() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static String f(Context context) {
        DisplayMetrics displayMetricsD = d(context);
        return displayMetricsD.widthPixels + "*" + displayMetricsD.heightPixels;
    }

    public static String e(Context context) {
        String strB = k.b(context);
        return strB.substring(0, strB.indexOf("://"));
    }

    public static Map<String, String> a(String str) {
        HashMap map = new HashMap();
        for (String str2 : str.split(com.alipay.sdk.sys.a.k)) {
            int iIndexOf = str2.indexOf("=", 1);
            if (-1 != iIndexOf) {
                map.put(str2.substring(0, iIndexOf), URLDecoder.decode(str2.substring(iIndexOf + 1)));
            }
        }
        return map;
    }

    public static String e(String str) {
        return a(str, true);
    }

    public static final class b {
        public final PackageInfo a;
        public final int b;
        public final String c;

        public b(PackageInfo packageInfo, int i, String str) {
            this.a = packageInfo;
            this.b = i;
            this.c = str;
        }

        public boolean a(com.alipay.sdk.sys.a aVar) {
            Signature[] signatureArr = this.a.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return false;
            }
            for (Signature signature : signatureArr) {
                String strA = l.a(aVar, signature.toByteArray());
                if (strA != null && !TextUtils.equals(strA, this.c)) {
                    com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.H, String.format("Got %s, expected %s", strA, this.c));
                    return true;
                }
            }
            return false;
        }

        public boolean a() {
            return this.a.versionCode < this.b;
        }
    }

    public static DisplayMetrics d(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int f() {
        try {
            return Process.myUid();
        } catch (Throwable th) {
            c.a(th);
            return -200;
        }
    }

    public static boolean d(String str) {
        return Pattern.compile("^http(s)?://([a-z0-9_\\-]+\\.)*(alipaydev|alipay|taobao)\\.(com|net)(:\\d+)?(/.*)?$").matcher(str).matches();
    }

    public static String g(String str) {
        try {
            Uri uri = Uri.parse(str);
            return String.format("%s%s", uri.getAuthority(), uri.getPath());
        } catch (Throwable th) {
            c.a(th);
            return "-";
        }
    }

    public static PackageInfo b(Context context, String str) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(str, 192);
    }

    public static boolean d(com.alipay.sdk.sys.a aVar, String str) {
        try {
            int iC = c(str);
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "bindExt", "" + iC);
            return com.alipay.sdk.data.a.u().n() && (iC & 2) == 2;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String a(String str, String str2, String str3) {
        try {
            int iIndexOf = str3.indexOf(str) + str.length();
            if (iIndexOf <= str.length()) {
                return "";
            }
            int iIndexOf2 = TextUtils.isEmpty(str2) ? 0 : str3.indexOf(str2, iIndexOf);
            if (iIndexOf2 < 1) {
                return str3.substring(iIndexOf);
            }
            return str3.substring(iIndexOf, iIndexOf2);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static boolean b(com.alipay.sdk.sys.a aVar, Context context, List<a.b> list) {
        try {
            for (a.b bVar : list) {
                if (bVar != null) {
                    String str = bVar.a;
                    if (EnvUtils.isSandBox() && b.equals(str)) {
                        str = c;
                    }
                    try {
                        if (context.getPackageManager().getPackageInfo(str, 128) != null) {
                            return true;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        continue;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.i0, th);
            return false;
        }
    }

    public static boolean g() {
        try {
            String[] strArrSplit = com.alipay.sdk.data.a.u().f().split("\\|");
            String str = Build.MODEL;
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            for (String str2 : strArrSplit) {
                if (TextUtils.equals(str, str2) || TextUtils.equals(str2, "all")) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            c.a(th);
            return false;
        }
    }

    public static String a(com.alipay.sdk.sys.a aVar, byte[] bArr) {
        BigInteger modulus;
        try {
            PublicKey publicKey = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))).getPublicKey();
            if (!(publicKey instanceof RSAPublicKey) || (modulus = ((RSAPublicKey) publicKey).getModulus()) == null) {
                return null;
            }
            return modulus.toString(16);
        } catch (Exception e2) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.n, com.alipay.sdk.app.statistic.b.z, e2);
            return null;
        }
    }

    public static String c(Context context) {
        return context.getResources().getConfiguration().locale.toString();
    }

    public static String c(com.alipay.sdk.sys.a aVar, String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
        } catch (Exception e2) {
            com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.l, "rflex", e2.getClass().getSimpleName());
            return null;
        }
    }

    public static int c(String str) {
        try {
            String strI = com.alipay.sdk.data.a.u().i();
            if (TextUtils.isEmpty(strI)) {
                return 0;
            }
            return (a(strI, "").contains(str) ? 2 : 0) | 1;
        } catch (Throwable unused) {
            return 61440;
        }
    }

    public static int b(int i) {
        return i / TapTapDIDIntentService.d;
    }

    public static int b() {
        try {
            String lowerCase = Build.BRAND.toLowerCase();
            String lowerCase2 = Build.MANUFACTURER.toLowerCase();
            if (a("huawei", lowerCase, lowerCase2)) {
                return 1;
            }
            if (a("oppo", lowerCase, lowerCase2)) {
                return 2;
            }
            if (a("vivo", lowerCase, lowerCase2)) {
                return 4;
            }
            if (a("lenovo", lowerCase, lowerCase2)) {
                return 8;
            }
            if (a("xiaomi", lowerCase, lowerCase2)) {
                return 16;
            }
            return a("oneplus", lowerCase, lowerCase2) ? 32 : 0;
        } catch (Exception unused) {
            return 61440;
        }
    }

    public static b a(com.alipay.sdk.sys.a aVar, Context context, List<a.b> list) {
        b bVarA;
        if (list == null) {
            return null;
        }
        for (a.b bVar : list) {
            if (bVar != null && (bVarA = a(aVar, context, bVar.a, bVar.b, bVar.c)) != null && !bVarA.a(aVar) && !bVarA.a()) {
                return bVarA;
            }
        }
        return null;
    }

    public static b a(com.alipay.sdk.sys.a aVar, Context context, String str, int i, String str2) {
        PackageInfo packageInfoB;
        if (EnvUtils.isSandBox() && b.equals(str)) {
            str = c;
        }
        try {
            packageInfoB = b(context, str);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.n, com.alipay.sdk.app.statistic.b.w, th.getMessage());
            packageInfoB = null;
        }
        if (a(aVar, packageInfoB)) {
            return a(packageInfoB, i, str2);
        }
        return null;
    }

    public static boolean a(com.alipay.sdk.sys.a aVar, PackageInfo packageInfo) {
        String str = "";
        boolean z = false;
        if (packageInfo == null) {
            str = "info == null";
        } else {
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null) {
                str = "info.signatures == null";
            } else if (signatureArr.length <= 0) {
                str = "info.signatures.length <= 0";
            } else {
                z = true;
            }
        }
        if (!z) {
            com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.n, com.alipay.sdk.app.statistic.b.x, str);
        }
        return z;
    }

    public static b a(PackageInfo packageInfo, int i, String str) {
        if (packageInfo == null) {
            return null;
        }
        return new b(packageInfo, i, str);
    }

    public static boolean a(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        try {
            String str = packageInfo.versionName;
            String[] strArr = e;
            if (!TextUtils.equals(str, strArr[0])) {
                if (!TextUtils.equals(str, strArr[1])) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String a(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            int iNextInt = random.nextInt(3);
            if (iNextInt == 0) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 65.0d)));
            } else if (iNextInt == 1) {
                sb.append(String.valueOf((char) Math.round((Math.random() * 25.0d) + 97.0d)));
            } else if (iNextInt == 2) {
                sb.append(String.valueOf(new Random().nextInt(10)));
            }
        }
        return sb.toString();
    }

    public static String a(Context context, String str) {
        String strSubstring = "";
        try {
            String string = "";
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getApplicationContext().getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.equals(str)) {
                    string = string + "#M";
                } else {
                    if (runningAppProcessInfo.processName.startsWith(str + ":")) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(string);
                        sb.append("#");
                        sb.append(runningAppProcessInfo.processName.replace(str + ":", ""));
                        string = sb.toString();
                    }
                }
            }
            strSubstring = string;
        } catch (Throwable unused) {
        }
        if (strSubstring.length() > 0) {
            strSubstring = strSubstring.substring(1);
        }
        return strSubstring.length() == 0 ? "N" : strSubstring;
    }

    public static boolean a(com.alipay.sdk.sys.a aVar, String str, Activity activity) {
        String strSubstring;
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if (activity == null) {
            return false;
        }
        if (!str.toLowerCase().startsWith(com.alipay.sdk.cons.a.l.toLowerCase()) && !str.toLowerCase().startsWith(com.alipay.sdk.cons.a.m.toLowerCase())) {
            if (!TextUtils.equals(str, com.alipay.sdk.cons.a.o) && !TextUtils.equals(str, com.alipay.sdk.cons.a.p)) {
                if (!str.startsWith(com.alipay.sdk.cons.a.n)) {
                    return false;
                }
                try {
                    String strSubstring2 = str.substring(str.indexOf(com.alipay.sdk.cons.a.n) + 24);
                    int i = Integer.parseInt(strSubstring2.substring(strSubstring2.lastIndexOf(com.alipay.sdk.cons.a.q) + 10));
                    if (i != com.alipay.sdk.app.c.SUCCEEDED.b() && i != com.alipay.sdk.app.c.PAY_WAITTING.b()) {
                        com.alipay.sdk.app.c cVarB = com.alipay.sdk.app.c.b(com.alipay.sdk.app.c.FAILED.b());
                        com.alipay.sdk.app.b.a(com.alipay.sdk.app.b.a(cVarB.b(), cVarB.a(), ""));
                    } else {
                        if (com.alipay.sdk.cons.a.u) {
                            StringBuilder sb = new StringBuilder();
                            String strDecode = URLDecoder.decode(str);
                            String strDecode2 = URLDecoder.decode(strDecode);
                            String str2 = strDecode2.substring(strDecode2.indexOf(com.alipay.sdk.cons.a.n) + 24, strDecode2.lastIndexOf(com.alipay.sdk.cons.a.q)).split(com.alipay.sdk.cons.a.s)[0];
                            int iIndexOf = strDecode.indexOf(com.alipay.sdk.cons.a.s) + 12;
                            sb.append(str2);
                            sb.append(com.alipay.sdk.cons.a.s);
                            sb.append(strDecode.substring(iIndexOf, strDecode.indexOf(com.alipay.sdk.sys.a.k, iIndexOf)));
                            sb.append(strDecode.substring(strDecode.indexOf(com.alipay.sdk.sys.a.k, iIndexOf)));
                            strSubstring = sb.toString();
                        } else {
                            String strDecode3 = URLDecoder.decode(str);
                            strSubstring = strDecode3.substring(strDecode3.indexOf(com.alipay.sdk.cons.a.n) + 24, strDecode3.lastIndexOf(com.alipay.sdk.cons.a.q));
                        }
                        com.alipay.sdk.app.c cVarB2 = com.alipay.sdk.app.c.b(i);
                        com.alipay.sdk.app.b.a(com.alipay.sdk.app.b.a(cVarB2.b(), cVarB2.a(), strSubstring));
                    }
                } catch (Exception unused) {
                    com.alipay.sdk.app.b.a(com.alipay.sdk.app.b.e());
                }
                activity.runOnUiThread(new a(activity));
                return true;
            }
            com.alipay.sdk.app.b.a(com.alipay.sdk.app.b.a());
            activity.finish();
            return true;
        }
        try {
            b bVarA = a(aVar, activity, com.alipay.sdk.app.a.d);
            if (bVarA != null && !bVarA.a() && !bVarA.a(aVar)) {
                if (str.startsWith("intent://platformapi/startapp")) {
                    str = str.replaceFirst("intent://platformapi/startapp\\?", com.alipay.sdk.cons.a.l);
                }
                activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        } catch (Throwable unused2) {
        }
        return true;
    }

    public static String a(com.alipay.sdk.sys.a aVar, Context context) {
        return a(aVar, context, context.getPackageName());
    }

    public static String a(com.alipay.sdk.sys.a aVar, Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 128).versionName;
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.w, th);
            return "";
        }
    }

    public static String a(String str, boolean z) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            if (z && bArrDigest.length > 16) {
                byte[] bArr = new byte[16];
                System.arraycopy(bArrDigest, 0, bArr, 0, 16);
                return a(bArr);
            }
            return a(bArrDigest);
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            sb.append(Character.forDigit((b2 & 240) >> 4, 16));
            sb.append(Character.forDigit(b2 & 15, 16));
        }
        return sb.toString();
    }

    public static ActivityInfo a(Context context) {
        try {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                for (ActivityInfo activityInfo : context.getPackageManager().getPackageInfo(context.getPackageName(), 1).activities) {
                    if (TextUtils.equals(activityInfo.name, activity.getClass().getName())) {
                        return activityInfo;
                    }
                }
            }
            return null;
        } catch (Throwable th) {
            c.a(th);
            return null;
        }
    }

    public static String a(com.alipay.sdk.sys.a aVar) {
        return c(aVar, "ro.build.fingerprint");
    }

    public static <T> T a(WeakReference<T> weakReference) {
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public static boolean a(com.alipay.sdk.sys.a aVar, String str) {
        try {
            String host = new URL(str).getHost();
            if (host.endsWith(com.alipay.sdk.cons.a.y)) {
                return true;
            }
            return host.endsWith(com.alipay.sdk.cons.a.z);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "ckUrlErr", th);
            return false;
        }
    }

    public static JSONObject a(Intent intent) {
        Bundle extras;
        JSONObject jSONObject = new JSONObject();
        if (intent != null && (extras = intent.getExtras()) != null) {
            for (String str : extras.keySet()) {
                try {
                    jSONObject.put(str, String.valueOf(extras.get(str)));
                } catch (Throwable unused) {
                }
            }
        }
        return jSONObject;
    }

    public static boolean a(Object obj, Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return obj == null;
        }
        for (Object obj2 : objArr) {
            if ((obj == null && obj2 == null) || (obj != null && obj.equals(obj2))) {
                return true;
            }
        }
        return false;
    }

    public static String a(String str, String str2) {
        String string = Settings.Secure.getString(((Application) com.alipay.sdk.sys.b.d().b()).getContentResolver(), str);
        return string != null ? string : str2;
    }
}
