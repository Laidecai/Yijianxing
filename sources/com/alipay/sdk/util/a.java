package com.alipay.sdk.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static final String b = "00:00:00:00:00:00";
    public static a c;
    public String a;

    public a(Context context) {
        String macAddress;
        try {
            try {
                macAddress = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
                this.a = macAddress;
            } catch (Exception e) {
                c.a(e);
                if (TextUtils.isEmpty(this.a)) {
                }
            }
            if (TextUtils.isEmpty(macAddress)) {
                this.a = b;
            }
        } catch (Throwable th) {
            if (TextUtils.isEmpty(this.a)) {
                this.a = b;
            }
            throw th;
        }
    }

    public static a b(Context context) {
        if (c == null) {
            c = new a(context);
        }
        return c;
    }

    public static String c(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getResources().getConfiguration().locale.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public String a() {
        String str = b() + "|";
        String strC = c();
        if (TextUtils.isEmpty(strC)) {
            return str + "000000000000000";
        }
        return str + strC;
    }

    public String b() {
        return "000000000000000";
    }

    public String c() {
        return "000000000000000";
    }

    public String d() {
        return this.a;
    }

    public static e d(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return (activeNetworkInfo == null || activeNetworkInfo.getType() != 0) ? (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) ? e.NONE : e.WIFI : e.a(activeNetworkInfo.getSubtype());
        } catch (Exception unused) {
            return e.NONE;
        }
    }

    public static String a(Context context) {
        return b(context).a().substring(0, 8);
    }
}
