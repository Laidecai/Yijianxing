package com.tds.tapdb.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/* JADX INFO: loaded from: classes.dex */
public class i {
    private static String a(Context context, TelephonyManager telephonyManager, ConnectivityManager connectivityManager) {
        int subtype;
        NetworkInfo activeNetworkInfo;
        if (telephonyManager == null) {
            subtype = 0;
        } else if (Build.VERSION.SDK_INT < 30 || !(c.a(context, "android.permission.READ_PHONE_STATE") || telephonyManager.hasCarrierPrivileges())) {
            try {
                subtype = telephonyManager.getNetworkType();
            } catch (Exception e) {
                n.a(e);
                subtype = 0;
            }
        } else {
            subtype = telephonyManager.getDataNetworkType();
        }
        if (subtype == 0) {
            if (Build.VERSION.SDK_INT >= 30) {
                return "3";
            }
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                subtype = activeNetworkInfo.getSubtype();
            }
        }
        switch (subtype) {
        }
        return "3";
    }

    public static boolean a(Context context) {
        if (!c.a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return false;
        }
        try {
            return a((ConnectivityManager) context.getSystemService("connectivity"));
        } catch (Exception e) {
            n.a(e);
            return false;
        }
    }

    private static boolean a(ConnectivityManager connectivityManager) {
        NetworkCapabilities networkCapabilities;
        if (connectivityManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 23) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return false;
        }
        return a(networkCapabilities);
    }

    public static boolean a(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(0) || networkCapabilities.hasTransport(3) || networkCapabilities.hasTransport(7) || networkCapabilities.hasTransport(4) || networkCapabilities.hasCapability(16);
        }
        return false;
    }

    public static String b(Context context) {
        try {
            if (!c.a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                return "Unknown";
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                if (!a(connectivityManager)) {
                    return "Unknown";
                }
                if (b(connectivityManager)) {
                    return "2";
                }
            }
            return a(context, (TelephonyManager) context.getSystemService("phone"), connectivityManager);
        } catch (Exception e) {
            n.a(e);
            return "Unknown";
        }
    }

    private static boolean b(ConnectivityManager connectivityManager) {
        NetworkCapabilities networkCapabilities;
        if (Build.VERSION.SDK_INT < 23) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return false;
        }
        return networkCapabilities.hasTransport(1);
    }
}
