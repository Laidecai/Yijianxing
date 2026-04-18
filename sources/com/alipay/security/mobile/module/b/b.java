package com.alipay.security.mobile.module.b;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import androidx.core.app.NotificationCompat;
import androidx.core.os.EnvironmentCompat;
import com.taptap.sdk.constant.LoginConstants;
import com.tds.common.tracker.constants.CommonParam;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.UByte;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static b a = new b();

    /* JADX WARN: Removed duplicated region for block: B:10:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r2) {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = a(r2, r0)
            java.lang.String r1 = ""
            if (r0 == 0) goto Lb
            return r1
        Lb:
            if (r2 == 0) goto L1c
            java.lang.String r0 = "phone"
            java.lang.Object r2 = r2.getSystemService(r0)     // Catch: java.lang.Throwable -> L1c
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch: java.lang.Throwable -> L1c
            if (r2 == 0) goto L1c
            java.lang.String r2 = r2.getDeviceId()     // Catch: java.lang.Throwable -> L1c
            goto L1d
        L1c:
            r2 = 0
        L1d:
            if (r2 != 0) goto L20
            goto L21
        L20:
            r1 = r2
        L21:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.a(android.content.Context):java.lang.String");
    }

    public static boolean a(Context context, String str) {
        return !(context.getPackageManager().checkPermission(str, context.getPackageName()) == 0);
    }

    public static b b() {
        return a;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String b(android.content.Context r2) {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = a(r2, r0)
            java.lang.String r1 = ""
            if (r0 == 0) goto Lb
            return r1
        Lb:
            if (r2 == 0) goto L1c
            java.lang.String r0 = "phone"
            java.lang.Object r2 = r2.getSystemService(r0)     // Catch: java.lang.Throwable -> L1c
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch: java.lang.Throwable -> L1c
            if (r2 == 0) goto L1c
            java.lang.String r2 = r2.getSubscriberId()     // Catch: java.lang.Throwable -> L1c
            goto L1d
        L1c:
            r2 = r1
        L1d:
            if (r2 != 0) goto L20
            goto L21
        L20:
            r1 = r2
        L21:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.b(android.content.Context):java.lang.String");
    }

    public static String c() {
        long availableBlocks;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            availableBlocks = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable unused) {
            availableBlocks = 0;
        }
        return String.valueOf(availableBlocks);
    }

    public static String c(Context context) {
        int i = 0;
        try {
            i = Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Throwable unused) {
        }
        return i == 1 ? "1" : LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
    }

    public static String d() {
        long blockSize;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            StatFs statFs = new StatFs(com.alipay.security.mobile.module.a.a.a().getPath());
            blockSize = ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } else {
            blockSize = 0;
        }
        return String.valueOf(blockSize);
    }

    public static String d(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            int i = audioManager.getRingerMode() == 0 ? 1 : 0;
            int streamVolume = audioManager.getStreamVolume(0);
            int streamVolume2 = audioManager.getStreamVolume(1);
            int streamVolume3 = audioManager.getStreamVolume(2);
            int streamVolume4 = audioManager.getStreamVolume(3);
            int streamVolume5 = audioManager.getStreamVolume(4);
            jSONObject.put("ringermode", String.valueOf(i));
            jSONObject.put(NotificationCompat.CATEGORY_CALL, String.valueOf(streamVolume));
            jSONObject.put("system", String.valueOf(streamVolume2));
            jSONObject.put("ring", String.valueOf(streamVolume3));
            jSONObject.put("music", String.valueOf(streamVolume4));
            jSONObject.put(NotificationCompat.CATEGORY_ALARM, String.valueOf(streamVolume5));
        } catch (Throwable unused) {
        }
        return jSONObject.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002d, code lost:
    
        r0 = r6.substring(r6.indexOf(":") + 1, r6.length()).trim();
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String e() {
        /*
            java.lang.String r0 = "0000000000000000"
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L51
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L51
            java.lang.String r4 = "/proc/cpuinfo"
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L51
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L51
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L4f
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L4f
            java.io.LineNumberReader r4 = new java.io.LineNumberReader     // Catch: java.lang.Throwable -> L4d
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L4d
            r1 = 1
            r5 = 1
        L1b:
            r6 = 100
            if (r5 >= r6) goto L46
            java.lang.String r6 = r4.readLine()     // Catch: java.lang.Throwable -> L44
            if (r6 == 0) goto L46
            java.lang.String r7 = "Serial"
            int r7 = r6.indexOf(r7)     // Catch: java.lang.Throwable -> L44
            if (r7 < 0) goto L41
            java.lang.String r5 = ":"
            int r5 = r6.indexOf(r5)     // Catch: java.lang.Throwable -> L44
            int r5 = r5 + r1
            int r1 = r6.length()     // Catch: java.lang.Throwable -> L44
            java.lang.String r1 = r6.substring(r5, r1)     // Catch: java.lang.Throwable -> L44
            java.lang.String r0 = r1.trim()     // Catch: java.lang.Throwable -> L44
            goto L46
        L41:
            int r5 = r5 + 1
            goto L1b
        L44:
            r1 = r4
            goto L53
        L46:
            r4.close()     // Catch: java.lang.Throwable -> L49
        L49:
            r3.close()     // Catch: java.lang.Throwable -> L63
            goto L63
        L4d:
            goto L53
        L4f:
            r3 = r1
            goto L53
        L51:
            r2 = r1
            r3 = r2
        L53:
            if (r1 == 0) goto L5a
            r1.close()     // Catch: java.lang.Throwable -> L59
            goto L5a
        L59:
        L5a:
            if (r3 == 0) goto L61
            r3.close()     // Catch: java.lang.Throwable -> L60
            goto L61
        L60:
        L61:
            if (r2 == 0) goto L68
        L63:
            r2.close()     // Catch: java.lang.Throwable -> L67
            goto L68
        L67:
        L68:
            if (r0 != 0) goto L6c
            java.lang.String r0 = ""
        L6c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.e():java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String e(android.content.Context r1) {
        /*
            if (r1 == 0) goto L11
            java.lang.String r0 = "phone"
            java.lang.Object r1 = r1.getSystemService(r0)     // Catch: java.lang.Throwable -> L11
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1     // Catch: java.lang.Throwable -> L11
            if (r1 == 0) goto L11
            java.lang.String r1 = r1.getNetworkOperatorName()     // Catch: java.lang.Throwable -> L11
            goto L12
        L11:
            r1 = 0
        L12:
            if (r1 == 0) goto L1c
            java.lang.String r0 = "null"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L1e
        L1c:
            java.lang.String r1 = ""
        L1e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.e(android.content.Context):java.lang.String");
    }

    public static String f() {
        String strV = v();
        return !com.alipay.security.mobile.module.a.a.a(strV) ? strV : w();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String f(android.content.Context r3) {
        /*
            if (r3 == 0) goto L4d
            java.lang.String r0 = "sensor"
            java.lang.Object r3 = r3.getSystemService(r0)     // Catch: java.lang.Throwable -> L4d
            android.hardware.SensorManager r3 = (android.hardware.SensorManager) r3     // Catch: java.lang.Throwable -> L4d
            if (r3 == 0) goto L4d
            r0 = -1
            java.util.List r3 = r3.getSensorList(r0)     // Catch: java.lang.Throwable -> L4d
            if (r3 == 0) goto L4d
            int r0 = r3.size()     // Catch: java.lang.Throwable -> L4d
            if (r0 <= 0) goto L4d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4d
            r0.<init>()     // Catch: java.lang.Throwable -> L4d
            java.util.Iterator r3 = r3.iterator()     // Catch: java.lang.Throwable -> L4d
        L22:
            boolean r1 = r3.hasNext()     // Catch: java.lang.Throwable -> L4d
            if (r1 == 0) goto L44
            java.lang.Object r1 = r3.next()     // Catch: java.lang.Throwable -> L4d
            android.hardware.Sensor r1 = (android.hardware.Sensor) r1     // Catch: java.lang.Throwable -> L4d
            java.lang.String r2 = r1.getName()     // Catch: java.lang.Throwable -> L4d
            r0.append(r2)     // Catch: java.lang.Throwable -> L4d
            int r2 = r1.getVersion()     // Catch: java.lang.Throwable -> L4d
            r0.append(r2)     // Catch: java.lang.Throwable -> L4d
            java.lang.String r1 = r1.getVendor()     // Catch: java.lang.Throwable -> L4d
            r0.append(r1)     // Catch: java.lang.Throwable -> L4d
            goto L22
        L44:
            java.lang.String r3 = r0.toString()     // Catch: java.lang.Throwable -> L4d
            java.lang.String r3 = com.alipay.security.mobile.module.a.a.e(r3)     // Catch: java.lang.Throwable -> L4d
            goto L4e
        L4d:
            r3 = 0
        L4e:
            if (r3 != 0) goto L52
            java.lang.String r3 = ""
        L52:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.f(android.content.Context):java.lang.String");
    }

    public static String g() {
        BufferedReader bufferedReader;
        FileReader fileReader = null;
        try {
            FileReader fileReader2 = new FileReader("/proc/cpuinfo");
            try {
                bufferedReader = new BufferedReader(fileReader2);
                try {
                    String[] strArrSplit = bufferedReader.readLine().split(":\\s+", 2);
                    if (strArrSplit != null && strArrSplit.length > 1) {
                        String str = strArrSplit[1];
                        try {
                            fileReader2.close();
                        } catch (Throwable unused) {
                        }
                        try {
                            bufferedReader.close();
                        } catch (Throwable unused2) {
                        }
                        return str;
                    }
                    try {
                        fileReader2.close();
                    } catch (Throwable unused3) {
                    }
                } catch (Throwable unused4) {
                    fileReader = fileReader2;
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable unused5) {
                        }
                    }
                    if (bufferedReader == null) {
                        return "";
                    }
                }
            } catch (Throwable unused6) {
                bufferedReader = null;
            }
        } catch (Throwable unused7) {
            bufferedReader = null;
        }
        try {
            bufferedReader.close();
            return "";
        } catch (Throwable unused8) {
            return "";
        }
    }

    public static String g(Context context) {
        List<Sensor> sensorList;
        JSONArray jSONArray = new JSONArray();
        if (context != null) {
            try {
                SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
                if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null && sensorList.size() > 0) {
                    for (Sensor sensor : sensorList) {
                        if (sensor != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put(com.alipay.sdk.cons.c.e, sensor.getName());
                            jSONObject.put(CommonParam.VERSION, sensor.getVersion());
                            jSONObject.put("vendor", sensor.getVendor());
                            jSONArray.put(jSONObject);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return jSONArray.toString();
    }

    public static String h() {
        BufferedReader bufferedReader;
        FileReader fileReader = null;
        try {
            FileReader fileReader2 = new FileReader("/proc/meminfo");
            try {
                bufferedReader = new BufferedReader(fileReader2, 8192);
                try {
                    j = bufferedReader.readLine() != null ? Integer.parseInt(r1.split("\\s+")[1]) : 0L;
                    try {
                        fileReader2.close();
                    } catch (Throwable unused) {
                    }
                } catch (Throwable unused2) {
                    fileReader = fileReader2;
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable unused3) {
                        }
                    }
                    if (bufferedReader != null) {
                    }
                    return String.valueOf(j);
                }
            } catch (Throwable unused4) {
                bufferedReader = null;
            }
        } catch (Throwable unused5) {
            bufferedReader = null;
        }
        try {
            bufferedReader.close();
        } catch (Throwable unused6) {
        }
        return String.valueOf(j);
    }

    public static String h(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return Integer.toString(displayMetrics.widthPixels) + "*" + Integer.toString(displayMetrics.heightPixels);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String i() {
        long blockCount;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            blockCount = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable unused) {
            blockCount = 0;
        }
        return String.valueOf(blockCount);
    }

    public static String i(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.widthPixels);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String j() {
        long blockSize;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            blockSize = ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
        } else {
            blockSize = 0;
        }
        return String.valueOf(blockSize);
    }

    public static String j(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.heightPixels);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String k() {
        String str;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", String.class, String.class).invoke(cls.newInstance(), "gsm.version.baseband", "no message");
        } catch (Throwable unused) {
            str = "";
        }
        return str == null ? "" : str;
    }

    public static String k(Context context) {
        String macAddress = "";
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            return "";
        }
        try {
            macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress == null || macAddress.length() == 0 || "02:00:00:00:00:00".equals(macAddress)) {
                return u();
            }
        } catch (Throwable unused) {
        }
        return macAddress;
    }

    public static String l() {
        String string;
        try {
            string = Locale.getDefault().toString();
        } catch (Throwable unused) {
            string = "";
        }
        return string == null ? "" : string;
    }

    public static String l(Context context) {
        if (a(context, "android.permission.READ_PHONE_STATE")) {
            return "";
        }
        try {
            String simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            if (simSerialNumber == null) {
                return "";
            }
            if (simSerialNumber != null) {
                try {
                    if (simSerialNumber.length() == 0) {
                        return "";
                    }
                } catch (Throwable unused) {
                }
            }
            return simSerialNumber;
        } catch (Throwable unused2) {
            return "";
        }
    }

    public static String m() {
        String displayName;
        try {
            displayName = TimeZone.getDefault().getDisplayName(false, 0);
        } catch (Throwable unused) {
            displayName = "";
        }
        return displayName == null ? "" : displayName;
    }

    public static String m(Context context) {
        String string;
        try {
            string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            string = "";
        }
        return string == null ? "" : string;
    }

    public static String n() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            StringBuilder sb = new StringBuilder();
            sb.append(jCurrentTimeMillis - (jCurrentTimeMillis % 1000));
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String n(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return telephonyManager != null ? String.valueOf(telephonyManager.getNetworkType()) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String o() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(SystemClock.elapsedRealtime());
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String o(Context context) {
        WifiManager wifiManager;
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            return "";
        }
        try {
            wifiManager = (WifiManager) context.getSystemService("wifi");
        } catch (Throwable unused) {
        }
        String bssid = wifiManager.isWifiEnabled() ? wifiManager.getConnectionInfo().getBSSID() : "";
        return bssid == null ? "" : bssid;
    }

    public static String p() {
        try {
            StringBuilder sb = new StringBuilder();
            String[] strArr = {"/dev/qemu_pipe", "/dev/socket/qemud", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/genyd", "/dev/socket/baseband_genyd"};
            sb.append("00:");
            for (int i = 0; i < 7; i++) {
                sb.append(new File(strArr[i]).exists() ? "1" : LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
            }
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:18:0x0021
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String p(android.content.Context r3) {
        /*
            java.lang.String r0 = ""
            android.content.pm.ApplicationInfo r3 = r3.getApplicationInfo()
            int r3 = r3.targetSdkVersion
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> L21
            r2 = 29
            if (r1 < r2) goto Lf
            goto L21
        Lf:
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> L21
            r2 = 26
            if (r1 < r2) goto L1e
            r1 = 28
            if (r3 < r1) goto L1e
            java.lang.String r3 = android.os.Build.getSerial()     // Catch: java.lang.Throwable -> L21
            goto L22
        L1e:
            java.lang.String r3 = android.os.Build.SERIAL     // Catch: java.lang.Throwable -> L21
            goto L22
        L21:
            r3 = r0
        L22:
            if (r3 != 0) goto L25
            goto L26
        L25:
            r0 = r3
        L26:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.p(android.content.Context):java.lang.String");
    }

    public static String q() {
        String[] strArr = {"dalvik.system.Taint"};
        StringBuilder sb = new StringBuilder();
        sb.append("00");
        sb.append(":");
        for (int i = 0; i <= 0; i++) {
            try {
                Class.forName(strArr[0]);
                sb.append("1");
            } catch (Throwable unused) {
                sb.append(LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
            }
        }
        return sb.toString();
    }

    public static String q(Context context) {
        try {
            String strT = t(context);
            String strX = x();
            if (com.alipay.security.mobile.module.a.a.b(strT) && com.alipay.security.mobile.module.a.a.b(strX)) {
                return strT + ":" + x();
            }
        } catch (Throwable unused) {
        }
        return "";
    }

    public static String r() {
        StringBuilder sb = new StringBuilder();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("/system/build.prop", "ro.product.name=sdk");
        linkedHashMap.put("/proc/tty/drivers", "goldfish");
        linkedHashMap.put("/proc/cpuinfo", "goldfish");
        sb.append("00:");
        for (String str : linkedHashMap.keySet()) {
            LineNumberReader lineNumberReader = null;
            char c = '0';
            try {
                LineNumberReader lineNumberReader2 = new LineNumberReader(new InputStreamReader(new FileInputStream(str)));
                while (true) {
                    try {
                        String line = lineNumberReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.toLowerCase().contains((CharSequence) linkedHashMap.get(str))) {
                            c = '1';
                            break;
                        }
                    } catch (Throwable unused) {
                        lineNumberReader = lineNumberReader2;
                        sb.append('0');
                        if (lineNumberReader != null) {
                            lineNumberReader.close();
                        }
                    }
                }
                sb.append(c);
                try {
                    lineNumberReader2.close();
                } catch (Throwable unused2) {
                }
            } catch (Throwable unused3) {
            }
        }
        return sb.toString();
    }

    public static String r(Context context) {
        try {
            long jMax = 0;
            if (!((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure()) {
                return "0:0";
            }
            String[] strArr = {"/data/system/password.key", "/data/system/gesture.key", "/data/system/gatekeeper.password.key", "/data/system/gatekeeper.gesture.key", "/data/system/gatekeeper.pattern.key"};
            for (int i = 0; i < 5; i++) {
                long jLastModified = -1;
                try {
                    jLastModified = new File(strArr[i]).lastModified();
                } catch (Throwable unused) {
                }
                jMax = Math.max(jLastModified, jMax);
            }
            return "1:" + jMax;
        } catch (Throwable unused2) {
            return "";
        }
    }

    public static String s() {
        StringBuilder sb = new StringBuilder();
        sb.append("00:");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("BRAND", "generic");
        linkedHashMap.put("BOARD", EnvironmentCompat.MEDIA_UNKNOWN);
        linkedHashMap.put("DEVICE", "generic");
        linkedHashMap.put("HARDWARE", "goldfish");
        linkedHashMap.put("PRODUCT", "sdk");
        linkedHashMap.put("MODEL", "sdk");
        for (String str : linkedHashMap.keySet()) {
            char c = '0';
            try {
                String str2 = (String) Build.class.getField(str).get(null);
                String str3 = (String) linkedHashMap.get(str);
                String lowerCase = str2 != null ? str2.toLowerCase() : null;
                if (lowerCase != null && lowerCase.contains(str3)) {
                    c = '1';
                }
            } catch (Throwable unused) {
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String s(Context context) {
        try {
            Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            int intExtra = intentRegisterReceiver.getIntExtra("level", -1);
            int intExtra2 = intentRegisterReceiver.getIntExtra("status", -1);
            boolean z = intExtra2 == 2 || intExtra2 == 5;
            StringBuilder sb = new StringBuilder();
            sb.append(z ? "1" : LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
            sb.append(":");
            sb.append(intExtra);
            return sb.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String t() {
        StringBuilder sb = new StringBuilder();
        sb.append("00:");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("ro.hardware", "goldfish");
        linkedHashMap.put("ro.kernel.qemu", "1");
        linkedHashMap.put("ro.product.device", "generic");
        linkedHashMap.put("ro.product.model", "sdk");
        linkedHashMap.put("ro.product.brand", "generic");
        linkedHashMap.put("ro.product.name", "sdk");
        linkedHashMap.put("ro.build.fingerprint", "test-keys");
        linkedHashMap.put("ro.product.manufacturer", "unknow");
        for (String str : linkedHashMap.keySet()) {
            char c = '0';
            String str2 = (String) linkedHashMap.get(str);
            String strB = com.alipay.security.mobile.module.a.a.b(str, "");
            if (strB != null && strB.contains(str2)) {
                c = '1';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String t(Context context) {
        if (a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return "";
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return "WIFI";
            }
            if (activeNetworkInfo.getType() != 0) {
                return null;
            }
            int subtype = activeNetworkInfo.getSubtype();
            return (subtype == 4 || subtype == 1 || subtype == 2 || subtype == 7 || subtype == 11) ? "2G" : (subtype == 3 || subtype == 5 || subtype == 6 || subtype == 8 || subtype == 9 || subtype == 10 || subtype == 12 || subtype == 14 || subtype == 15) ? "3G" : subtype == 13 ? "4G" : "UNKNOW";
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String u() {
        try {
            ArrayList<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            if (list == null) {
                return "02:00:00:00:00:00";
            }
            for (NetworkInterface networkInterface : list) {
                if (networkInterface != null && networkInterface.getName() != null && networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "02:00:00:00:00:00";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        sb.append(String.format("%02X:", Integer.valueOf(b & UByte.MAX_VALUE)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "02:00:00:00:00:00";
        } catch (Throwable unused) {
            return "02:00:00:00:00:00";
        }
    }

    public static String v() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            fileReader = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq");
            try {
                bufferedReader = new BufferedReader(fileReader, 8192);
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            fileReader = null;
        }
        try {
            String line = bufferedReader.readLine();
            if (!com.alipay.security.mobile.module.a.a.a(line)) {
                String strTrim = line.trim();
                try {
                    bufferedReader.close();
                } catch (Throwable unused3) {
                }
                try {
                    fileReader.close();
                } catch (Throwable unused4) {
                }
                return strTrim;
            }
            try {
                bufferedReader.close();
            } catch (Throwable unused5) {
            }
        } catch (Throwable unused6) {
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (Throwable unused7) {
                }
            }
            if (fileReader == null) {
                return "";
            }
        }
        try {
            fileReader.close();
            return "";
        } catch (Throwable unused8) {
            return "";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0034, code lost:
    
        r1 = r2[1].trim();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String w() {
        /*
            java.lang.String r0 = "/proc/cpuinfo"
            java.lang.String r1 = ""
            r2 = 0
            java.io.FileReader r3 = new java.io.FileReader     // Catch: java.lang.Throwable -> L41
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L41
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L3e
            r4 = 8192(0x2000, float:1.148E-41)
            r0.<init>(r3, r4)     // Catch: java.lang.Throwable -> L3e
        L11:
            java.lang.String r2 = r0.readLine()     // Catch: java.lang.Throwable -> L3f
            if (r2 == 0) goto L3a
            boolean r4 = com.alipay.security.mobile.module.a.a.a(r2)     // Catch: java.lang.Throwable -> L3f
            if (r4 != 0) goto L11
            java.lang.String r4 = ":"
            java.lang.String[] r2 = r2.split(r4)     // Catch: java.lang.Throwable -> L3f
            if (r2 == 0) goto L11
            int r4 = r2.length     // Catch: java.lang.Throwable -> L3f
            r5 = 1
            if (r4 <= r5) goto L11
            r4 = 0
            r4 = r2[r4]     // Catch: java.lang.Throwable -> L3f
            java.lang.String r6 = "BogoMIPS"
            boolean r4 = r4.contains(r6)     // Catch: java.lang.Throwable -> L3f
            if (r4 == 0) goto L11
            r2 = r2[r5]     // Catch: java.lang.Throwable -> L3f
            java.lang.String r1 = r2.trim()     // Catch: java.lang.Throwable -> L3f
        L3a:
            r3.close()     // Catch: java.lang.Throwable -> L4b
            goto L4b
        L3e:
            r0 = r2
        L3f:
            r2 = r3
            goto L42
        L41:
            r0 = r2
        L42:
            if (r2 == 0) goto L49
            r2.close()     // Catch: java.lang.Throwable -> L48
            goto L49
        L48:
        L49:
            if (r0 == 0) goto L4e
        L4b:
            r0.close()     // Catch: java.lang.Throwable -> L4e
        L4e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.b.b.w():java.lang.String");
    }

    public static String x() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                        return inetAddressNextElement.getHostAddress().toString();
                    }
                }
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public final String a() {
        try {
            return String.valueOf(new File("/sys/devices/system/cpu/").listFiles(new c(this)).length);
        } catch (Throwable unused) {
            return "1";
        }
    }
}
