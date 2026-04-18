package com.tds.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.tds.common.tracker.annotations.Login;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class DeviceUtils {
    public static String getPlatform() {
        return "Android";
    }

    public static String getOSVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getCurLanguageDisplayName() {
        return CommonUtils.getCurrentLocale().toLanguageTag();
    }

    public static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDeviceTotalRam(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            if (activityManager == null) {
                return null;
            }
            activityManager.getMemoryInfo(memoryInfo);
            return Formatter.formatFileSize(context, memoryInfo.totalMem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDeviceTotalRom(Context context) {
        long blockCount;
        long blockSize;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            if (Build.VERSION.SDK_INT >= 18) {
                blockCount = statFs.getBlockCountLong();
                blockSize = statFs.getBlockSizeLong();
            } else {
                blockCount = statFs.getBlockCount();
                blockSize = statFs.getBlockSize();
            }
            return Formatter.formatFileSize(context, blockCount * blockSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRemainingRamSize(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return String.valueOf(memoryInfo.availMem);
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    public static String getRemainingRomSize() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return String.valueOf(statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong());
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    public static String getCpuInfo() {
        return Build.CPU_ABI;
    }

    public static String getCpuABIS() {
        String[] strArr = Build.SUPPORTED_ABIS;
        return (strArr == null || strArr.length == 0) ? "" : Arrays.toString(strArr);
    }

    public static int getPackageVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getPackageVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isAppExist(Context context, String str) {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        return applicationInfo != null;
    }

    public static boolean isRunInSandbox() {
        return System.getProperties().get("flag_running_in_sandbox") != null;
    }

    public static boolean isRunInCloud() {
        if (TextUtils.isEmpty(Build.MODEL)) {
            return false;
        }
        return Build.MODEL.contains("haima_cloudplay") || Build.MODEL.toLowerCase().contains(Login.TAPTAP_LOGIN_TYPE);
    }

    public static boolean isRunInCanary() {
        return !TextUtils.isEmpty((String) System.getProperties().get("flag_tap_canary"));
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001d, code lost:
    
        r0 = r1.split(":")[1];
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x002b -> B:28:0x003c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getCPUHardware() {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Throwable -> L31
            java.lang.String r3 = "/proc/cpuinfo"
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L31
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L31
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L31
        Lf:
            java.lang.String r1 = r3.readLine()     // Catch: java.lang.Throwable -> L2f
            if (r1 == 0) goto L26
            java.lang.String r2 = "Hardware"
            boolean r2 = r1.contains(r2)     // Catch: java.lang.Throwable -> L2f
            if (r2 == 0) goto Lf
            java.lang.String r2 = ":"
            java.lang.String[] r1 = r1.split(r2)     // Catch: java.lang.Throwable -> L2f
            r2 = 1
            r0 = r1[r2]     // Catch: java.lang.Throwable -> L2f
        L26:
            r3.close()     // Catch: java.io.IOException -> L2a
            goto L3c
        L2a:
            r1 = move-exception
            r1.printStackTrace()
            goto L3c
        L2f:
            r1 = move-exception
            goto L34
        L31:
            r2 = move-exception
            r3 = r1
            r1 = r2
        L34:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L3d
            if (r3 == 0) goto L3c
            r3.close()     // Catch: java.io.IOException -> L2a
        L3c:
            return r0
        L3d:
            r0 = move-exception
            if (r3 == 0) goto L48
            r3.close()     // Catch: java.io.IOException -> L44
            goto L48
        L44:
            r1 = move-exception
            r1.printStackTrace()
        L48:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.utils.DeviceUtils.getCPUHardware():java.lang.String");
    }
}
