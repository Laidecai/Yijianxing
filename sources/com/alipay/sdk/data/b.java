package com.alipay.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.ConditionVariable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.sdk.app.OpenAuthTask;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.util.c;
import com.alipay.sdk.util.g;
import com.alipay.sdk.util.l;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static final String d = "virtualImeiAndImsi";
    public static final String e = "virtual_imei";
    public static final String f = "virtual_imsi";
    public static volatile b g;
    public String a;
    public String b = "sdk-and-lite";
    public String c;

    public static class a implements APSecuritySdk.InitResultListener {
        public final /* synthetic */ String[] a;
        public final /* synthetic */ ConditionVariable b;

        public a(String[] strArr, ConditionVariable conditionVariable) {
            this.a = strArr;
            this.b = conditionVariable;
        }

        @Override // com.alipay.apmobilesecuritysdk.face.APSecuritySdk.InitResultListener
        public void onResult(APSecuritySdk.TokenResult tokenResult) {
            if (tokenResult != null) {
                this.a[0] = tokenResult.apdidToken;
            }
            this.b.open();
        }
    }

    /* JADX INFO: renamed from: com.alipay.sdk.data.b$b */
    public static class CallableC0005b implements Callable<String> {
        public final /* synthetic */ com.alipay.sdk.sys.a a;
        public final /* synthetic */ Context b;
        public final /* synthetic */ HashMap c;

        public CallableC0005b(com.alipay.sdk.sys.a aVar, Context context, HashMap map) {
            this.a = aVar;
            this.b = context;
            this.c = map;
        }

        @Override // java.util.concurrent.Callable
        public String call() throws Exception {
            return b.c(this.a, this.b, this.c);
        }
    }

    public b() {
        String strA = com.alipay.sdk.app.a.a();
        if (com.alipay.sdk.app.a.b()) {
            return;
        }
        this.b += '_' + strA;
    }

    public static synchronized b b() {
        if (g == null) {
            g = new b();
        }
        return g;
    }

    public static String c() {
        return Long.toHexString(System.currentTimeMillis()) + (new Random().nextInt(OpenAuthTask.OK) + 1000);
    }

    public static String d() {
        return "-1;-1";
    }

    public static String d(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getSSID() : "-1";
    }

    public static String e() {
        return "1";
    }

    public static String f() {
        Context contextB = com.alipay.sdk.sys.b.d().b();
        SharedPreferences sharedPreferences = contextB.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(e, null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String strC = TextUtils.isEmpty(com.alipay.sdk.tid.a.a(contextB).d()) ? c() : com.alipay.sdk.util.a.b(contextB).b();
        sharedPreferences.edit().putString(e, strC).apply();
        return strC;
    }

    public static String g() {
        String strC;
        Context contextB = com.alipay.sdk.sys.b.d().b();
        SharedPreferences sharedPreferences = contextB.getSharedPreferences(d, 0);
        String string = sharedPreferences.getString(f, null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        if (TextUtils.isEmpty(com.alipay.sdk.tid.a.a(contextB).d())) {
            String strC2 = com.alipay.sdk.sys.b.d().c();
            strC = (TextUtils.isEmpty(strC2) || strC2.length() < 18) ? c() : strC2.substring(3, 18);
        } else {
            strC = com.alipay.sdk.util.a.b(contextB).c();
        }
        String str = strC;
        sharedPreferences.edit().putString(f, str).apply();
        return str;
    }

    public String a() {
        return this.c;
    }

    public static synchronized void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        PreferenceManager.getDefaultSharedPreferences(com.alipay.sdk.sys.b.d().b()).edit().putString(com.alipay.sdk.cons.b.i, str).apply();
        com.alipay.sdk.cons.a.e = str;
    }

    public static String b(com.alipay.sdk.sys.a aVar, Context context, HashMap<String, String> map) {
        try {
            return (String) Executors.newFixedThreadPool(2).submit(new CallableC0005b(aVar, context, map)).get(PayTask.j, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.o, com.alipay.sdk.app.statistic.b.u, th);
            return "";
        }
    }

    public static String c(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
        return connectionInfo != null ? connectionInfo.getBSSID() : "00";
    }

    public static String c(com.alipay.sdk.sys.a aVar, Context context, HashMap<String, String> map) {
        String[] strArr = {""};
        try {
            APSecuritySdk aPSecuritySdk = APSecuritySdk.getInstance(context);
            ConditionVariable conditionVariable = new ConditionVariable();
            aPSecuritySdk.initToken(0, map, new a(strArr, conditionVariable));
            conditionVariable.block(PayTask.j);
        } catch (Throwable th) {
            c.a(th);
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.o, com.alipay.sdk.app.statistic.b.s, th);
        }
        if (TextUtils.isEmpty(strArr[0])) {
            com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.o, com.alipay.sdk.app.statistic.b.t, "missing token");
        }
        c.b(com.alipay.sdk.cons.a.x, "ap:" + strArr[0]);
        return strArr[0];
    }

    public static String a(Context context) {
        return Float.toString(new TextView(context).getTextSize());
    }

    public String a(com.alipay.sdk.sys.a aVar, com.alipay.sdk.tid.a aVar2) {
        Context contextB = com.alipay.sdk.sys.b.d().b();
        com.alipay.sdk.util.a aVarB = com.alipay.sdk.util.a.b(contextB);
        if (TextUtils.isEmpty(this.a)) {
            this.a = "Msp/15.8.02 (" + l.e() + g.b + l.d() + g.b + l.c(contextB) + g.b + l.e(contextB) + g.b + l.f(contextB) + g.b + a(contextB);
        }
        String strB = com.alipay.sdk.util.a.d(contextB).b();
        String strB2 = l.b(contextB);
        String strE = e();
        String strC = aVarB.c();
        String strB3 = aVarB.b();
        String strG = g();
        String strF = f();
        if (aVar2 != null) {
            this.c = aVar2.c();
        }
        String strReplace = Build.MANUFACTURER.replace(g.b, " ");
        String strReplace2 = Build.MODEL.replace(g.b, " ");
        boolean zE = com.alipay.sdk.sys.b.e();
        String strD = aVarB.d();
        String strD2 = d(contextB);
        String strC2 = c(contextB);
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(g.b);
        sb.append(strB);
        sb.append(g.b);
        sb.append(strB2);
        sb.append(g.b);
        sb.append(strE);
        sb.append(g.b);
        sb.append(strC);
        sb.append(g.b);
        sb.append(strB3);
        sb.append(g.b);
        sb.append(this.c);
        sb.append(g.b);
        sb.append(strReplace);
        sb.append(g.b);
        sb.append(strReplace2);
        sb.append(g.b);
        sb.append(zE);
        sb.append(g.b);
        sb.append(strD);
        sb.append(g.b);
        sb.append(d());
        sb.append(g.b);
        sb.append(this.b);
        sb.append(g.b);
        sb.append(strG);
        sb.append(g.b);
        sb.append(strF);
        sb.append(g.b);
        sb.append(strD2);
        sb.append(g.b);
        sb.append(strC2);
        if (aVar2 != null) {
            HashMap map = new HashMap();
            map.put("tid", com.alipay.sdk.tid.a.a(contextB).d());
            map.put(com.alipay.sdk.cons.b.g, com.alipay.sdk.sys.b.d().c());
            String strB4 = b(aVar, contextB, map);
            if (!TextUtils.isEmpty(strB4)) {
                sb.append(";;;");
                sb.append(strB4);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String b(Context context) {
        if (context == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            sb.append("(");
            sb.append(packageName);
            sb.append(g.b);
            sb.append(packageInfo.versionCode);
            sb.append(")");
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }
}
