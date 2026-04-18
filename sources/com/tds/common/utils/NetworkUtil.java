package com.tds.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import androidx.core.os.EnvironmentCompat;
import tds.androidx.core.content.ContextCompat;

/* JADX INFO: loaded from: classes.dex */
public class NetworkUtil {
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    public static String getConnectedType(Context context) {
        try {
            NetworkInfo activeNetworkInfo = getConnectivityManager(context).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "not connected";
            }
            int type = activeNetworkInfo.getType();
            return type != 0 ? type != 1 ? EnvironmentCompat.MEDIA_UNKNOWN : "wifi" : "mobile";
        } catch (Error | Exception e) {
            System.out.println(e);
            return "not connected";
        }
    }

    public static boolean checkHasPermission(Context context, String str) {
        return ContextCompat.checkSelfPermission(context, str) == 0;
    }

    private static boolean isWiFiNetwork(ConnectivityManager connectivityManager) {
        NetworkCapabilities networkCapabilities;
        if (Build.VERSION.SDK_INT >= 23) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
                return false;
            }
            return networkCapabilities.hasTransport(1);
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static String getNetworkType(Context context) {
        try {
            if (!checkHasPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
                return EnvironmentCompat.MEDIA_UNKNOWN;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            return (connectivityManager == null || isNetworkAvailable(connectivityManager)) ? mobileNetworkType(context, (TelephonyManager) context.getSystemService("phone"), connectivityManager) : EnvironmentCompat.MEDIA_UNKNOWN;
        } catch (Exception e) {
            System.out.println(e.toString());
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static boolean isNetworkValid(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities != null) {
            return networkCapabilities.hasTransport(1) || networkCapabilities.hasTransport(0) || networkCapabilities.hasTransport(3) || networkCapabilities.hasTransport(7) || networkCapabilities.hasTransport(4) || networkCapabilities.hasCapability(16);
        }
        return false;
    }

    private static boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
        NetworkCapabilities networkCapabilities;
        if (connectivityManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
                return false;
            }
            return isNetworkValid(networkCapabilities);
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable()) ? false : true;
    }

    private static String mobileNetworkType(Context context, TelephonyManager telephonyManager, ConnectivityManager connectivityManager) {
        int subtype;
        NetworkInfo activeNetworkInfo;
        if (telephonyManager == null) {
            subtype = 0;
        } else if (Build.VERSION.SDK_INT >= 30 && (checkHasPermission(context, "android.permission.READ_PHONE_STATE") || telephonyManager.hasCarrierPrivileges())) {
            subtype = telephonyManager.getDataNetworkType();
        } else {
            try {
                subtype = telephonyManager.getNetworkType();
            } catch (Exception e) {
                System.out.println(e.toString());
                subtype = 0;
            }
        }
        if (subtype == 0) {
            if (Build.VERSION.SDK_INT >= 30) {
                return EnvironmentCompat.MEDIA_UNKNOWN;
            }
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                subtype = activeNetworkInfo.getSubtype();
            }
        }
        switch (subtype) {
        }
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }
}
