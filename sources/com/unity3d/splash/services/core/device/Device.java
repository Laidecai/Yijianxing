package com.unity3d.splash.services.core.device;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.alipay.sdk.cons.c;
import com.tds.common.tracker.constants.CommonParam;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class Device {

    /* JADX INFO: renamed from: com.unity3d.splash.services.core.device.Device$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType;

        static {
            int[] iArr = new int[MemoryInfoType.values().length];
            $SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType = iArr;
            try {
                iArr[MemoryInfoType.TOTAL_MEMORY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType[MemoryInfoType.FREE_MEMORY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum MemoryInfoType {
        TOTAL_MEMORY,
        FREE_MEMORY
    }

    public static String getAdvertisingTrackingId() {
        return AdvertisingId.getAdvertisingTrackingId();
    }

    public static String getAndroidId() {
        try {
            return Settings.Secure.getString(ClientProperties.getApplicationContext().getContentResolver(), "android_id");
        } catch (Exception e) {
            DeviceLog.exception("Problems fetching androidId", e);
            return null;
        }
    }

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    public static String getApkDigest() throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(new File(ClientProperties.getApplicationContext().getPackageCodePath()));
        } catch (Throwable th) {
            th = th;
        }
        try {
            String strSha256 = Utilities.Sha256(fileInputStream);
            try {
                fileInputStream.close();
            } catch (IOException unused) {
            }
            return strSha256;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    public static float getBatteryLevel() {
        Intent intentRegisterReceiver;
        if (ClientProperties.getApplicationContext() == null || (intentRegisterReceiver = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) == null) {
            return -1.0f;
        }
        return intentRegisterReceiver.getIntExtra("level", -1) / intentRegisterReceiver.getIntExtra("scale", -1);
    }

    public static int getBatteryStatus() {
        Intent intentRegisterReceiver;
        if (ClientProperties.getApplicationContext() == null || (intentRegisterReceiver = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) == null) {
            return -1;
        }
        return intentRegisterReceiver.getIntExtra("status", -1);
    }

    public static String getBoard() {
        return Build.BOARD;
    }

    public static String getBootloader() {
        return Build.BOOTLOADER;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getBuildId() {
        return Build.ID;
    }

    public static String getBuildVersionIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    public static long getCPUCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static String getCertificateFingerprint() {
        try {
            Signature[] signatureArr = ClientProperties.getApplicationContext().getPackageManager().getPackageInfo(ClientProperties.getApplicationContext().getPackageName(), 64).signatures;
            if (signatureArr == null || signatureArr.length <= 0) {
                return null;
            }
            return Utilities.toHexString(MessageDigest.getInstance("SHA-1").digest(((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signatureArr[0].toByteArray()))).getEncoded()));
        } catch (Exception e) {
            DeviceLog.exception("Exception when signing certificate fingerprint", e);
            return null;
        }
    }

    public static String getDevice() {
        return Build.DEVICE;
    }

    public static long getElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    public static long getFreeMemory() {
        return getMemoryInfo(MemoryInfoType.FREE_MEMORY);
    }

    public static long getFreeSpace(File file) {
        if (file == null || !file.exists()) {
            return -1L;
        }
        return Math.round(file.getFreeSpace() / ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLS);
    }

    public static String getHardware() {
        return Build.HARDWARE;
    }

    public static String getHost() {
        return Build.HOST;
    }

    public static List getInstalledPackages(boolean z) {
        ArrayList arrayList = new ArrayList();
        if (ClientProperties.getApplicationContext() != null) {
            PackageManager packageManager = ClientProperties.getApplicationContext().getPackageManager();
            for (PackageInfo packageInfo : packageManager.getInstalledPackages(0)) {
                HashMap map = new HashMap();
                String strSha256 = packageInfo.packageName;
                if (z) {
                    strSha256 = Utilities.Sha256(strSha256);
                }
                map.put(c.e, strSha256);
                if (packageInfo.firstInstallTime > 0) {
                    map.put(CommonParam.TIME, Long.valueOf(packageInfo.firstInstallTime));
                }
                String installerPackageName = packageManager.getInstallerPackageName(packageInfo.packageName);
                if (installerPackageName != null && !installerPackageName.isEmpty()) {
                    map.put("installer", installerPackageName);
                }
                arrayList.add(map);
            }
        }
        return arrayList;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static long getMemoryInfo(MemoryInfoType memoryInfoType) throws Throwable {
        RandomAccessFile randomAccessFile;
        IOException e;
        int i = AnonymousClass1.$SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType[memoryInfoType.ordinal()];
        int i2 = 2;
        if (i == 1) {
            i2 = 1;
        } else if (i != 2) {
            i2 = -1;
        }
        RandomAccessFile randomAccessFile2 = 0;
        String line = null;
        try {
            try {
                randomAccessFile = new RandomAccessFile("/proc/meminfo", "r");
                for (int i3 = 0; i3 < i2; i3++) {
                    try {
                        line = randomAccessFile.readLine();
                    } catch (IOException e2) {
                        e = e2;
                        DeviceLog.exception("Error while reading memory info: " + memoryInfoType, e);
                        try {
                            randomAccessFile.close();
                            return -1L;
                        } catch (IOException e3) {
                            DeviceLog.exception("Error closing RandomAccessFile", e3);
                            return -1L;
                        }
                    }
                }
                long memoryValueFromString = getMemoryValueFromString(line);
                try {
                    randomAccessFile.close();
                } catch (IOException e4) {
                    DeviceLog.exception("Error closing RandomAccessFile", e4);
                }
                return memoryValueFromString;
            } catch (IOException e5) {
                randomAccessFile = null;
                e = e5;
            } catch (Throwable th) {
                th = th;
                try {
                    randomAccessFile2.close();
                } catch (IOException e6) {
                    DeviceLog.exception("Error closing RandomAccessFile", e6);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile2 = 1;
            randomAccessFile2.close();
            throw th;
        }
    }

    private static long getMemoryValueFromString(String str) {
        if (str == null) {
            return -1L;
        }
        Matcher matcher = Pattern.compile("(\\d+)").matcher(str);
        String strGroup = "";
        while (matcher.find()) {
            strGroup = matcher.group(1);
        }
        return Long.parseLong(strGroup);
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getNetworkCountryISO() {
        return ClientProperties.getApplicationContext() != null ? ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkCountryIso() : "";
    }

    public static boolean getNetworkMetered() {
        ConnectivityManager connectivityManager;
        if (ClientProperties.getApplicationContext() == null || Build.VERSION.SDK_INT < 16 || (connectivityManager = (ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity")) == null) {
            return false;
        }
        return connectivityManager.isActiveNetworkMetered();
    }

    public static String getNetworkOperator() {
        return ClientProperties.getApplicationContext() != null ? ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkOperator() : "";
    }

    public static String getNetworkOperatorName() {
        return ClientProperties.getApplicationContext() != null ? ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkOperatorName() : "";
    }

    public static int getNetworkType() {
        if (ClientProperties.getApplicationContext() != null) {
            return ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkType();
        }
        return -1;
    }

    private static ArrayList getNewAbiList() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(Build.SUPPORTED_ABIS));
        return arrayList;
    }

    private static ArrayList getOldAbiList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Build.CPU_ABI);
        arrayList.add(Build.CPU_ABI2);
        return arrayList;
    }

    public static String getOpenAdvertisingTrackingId() {
        return OpenAdvertisingId.getOpenAdvertisingTrackingId();
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0030 -> B:28:0x0033). Please report as a decompilation issue!!! */
    public static Map getProcessInfo() throws Throwable {
        Throwable th;
        RandomAccessFile randomAccessFile;
        IOException e;
        HashMap map = new HashMap();
        RandomAccessFile randomAccessFile2 = null;
        try {
        } catch (IOException e2) {
            DeviceLog.exception("Error closing RandomAccessFile", e2);
        }
        try {
            try {
                randomAccessFile = new RandomAccessFile("/proc/self/stat", "r");
            } catch (IOException e3) {
                randomAccessFile = null;
                e = e3;
            } catch (Throwable th2) {
                th = th2;
                try {
                    randomAccessFile2.close();
                } catch (IOException e4) {
                    DeviceLog.exception("Error closing RandomAccessFile", e4);
                }
                throw th;
            }
            try {
                map.put("stat", randomAccessFile.readLine());
                randomAccessFile.close();
            } catch (IOException e5) {
                e = e5;
                DeviceLog.exception("Error while reading processor info: ", e);
                randomAccessFile.close();
            }
            return map;
        } catch (Throwable th3) {
            th = th3;
            randomAccessFile2 = randomAccessFile;
            randomAccessFile2.close();
            throw th;
        }
    }

    public static String getProduct() {
        return Build.PRODUCT;
    }

    public static int getRingerMode() {
        if (ClientProperties.getApplicationContext() == null) {
            return -1;
        }
        AudioManager audioManager = (AudioManager) ClientProperties.getApplicationContext().getSystemService("audio");
        if (audioManager != null) {
            return audioManager.getRingerMode();
        }
        return -2;
    }

    public static String getSIMMCC(Context context) {
        String simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
        return (simOperator.length() >= 3) & (simOperator != null) ? simOperator.substring(0, 3) : "";
    }

    public static int getScreenBrightness() {
        if (ClientProperties.getApplicationContext() != null) {
            return Settings.System.getInt(ClientProperties.getApplicationContext().getContentResolver(), "screen_brightness", -1);
        }
        return -1;
    }

    public static int getScreenDensity() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
        }
        return -1;
    }

    public static int getScreenHeight() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        }
        return -1;
    }

    public static int getScreenLayout() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getConfiguration().screenLayout;
        }
        return -1;
    }

    public static int getScreenWidth() {
        if (ClientProperties.getApplicationContext() != null) {
            return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        }
        return -1;
    }

    public static List getSensorList() {
        if (ClientProperties.getApplicationContext() != null) {
            return ((SensorManager) ClientProperties.getApplicationContext().getSystemService("sensor")).getSensorList(-1);
        }
        return null;
    }

    public static int getStreamMaxVolume(int i) {
        if (ClientProperties.getApplicationContext() == null) {
            return -1;
        }
        AudioManager audioManager = (AudioManager) ClientProperties.getApplicationContext().getSystemService("audio");
        if (audioManager != null) {
            return audioManager.getStreamMaxVolume(i);
        }
        return -2;
    }

    public static int getStreamVolume(int i) {
        if (ClientProperties.getApplicationContext() == null) {
            return -1;
        }
        AudioManager audioManager = (AudioManager) ClientProperties.getApplicationContext().getSystemService("audio");
        if (audioManager != null) {
            return audioManager.getStreamVolume(i);
        }
        return -2;
    }

    public static ArrayList getSupportedAbis() {
        return getApiLevel() < 21 ? getOldAbiList() : getNewAbiList();
    }

    public static String getSystemProperty(String str, String str2) {
        return str2 != null ? System.getProperty(str, str2) : System.getProperty(str);
    }

    public static long getTotalMemory() {
        return getMemoryInfo(MemoryInfoType.TOTAL_MEMORY);
    }

    public static long getTotalSpace(File file) {
        if (file == null || !file.exists()) {
            return -1L;
        }
        return Math.round(file.getTotalSpace() / ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLS);
    }

    public static String getUniqueEventId() {
        return UUID.randomUUID().toString();
    }

    public static long getUptime() {
        return SystemClock.uptimeMillis();
    }

    public static boolean isActiveNetworkConnected() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        return (ClientProperties.getApplicationContext() == null || (connectivityManager = (ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    public static Boolean isAdbEnabled() {
        return getApiLevel() < 17 ? oldAdbStatus() : newAdbStatus();
    }

    public static boolean isAppInstalled(String str) {
        if (ClientProperties.getApplicationContext() != null) {
            try {
                PackageInfo packageInfo = ClientProperties.getApplicationContext().getPackageManager().getPackageInfo(str, 0);
                if (packageInfo != null && packageInfo.packageName != null) {
                    if (str.equals(packageInfo.packageName)) {
                        return true;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return false;
    }

    public static boolean isLimitAdTrackingEnabled() {
        return AdvertisingId.getLimitedAdTracking();
    }

    public static boolean isLimitOpenAdTrackingEnabled() {
        return OpenAdvertisingId.getLimitedOpenAdTracking();
    }

    public static boolean isRooted() {
        try {
            return searchPathForBinary("su");
        } catch (Exception e) {
            DeviceLog.exception("Rooted check failed", e);
            return false;
        }
    }

    public static boolean isUSBConnected() {
        Intent intentRegisterReceiver;
        if (ClientProperties.getApplicationContext() == null || (intentRegisterReceiver = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"))) == null) {
            return false;
        }
        return intentRegisterReceiver.getBooleanExtra("connected", false);
    }

    public static boolean isUsingWifi() {
        ConnectivityManager connectivityManager;
        if (ClientProperties.getApplicationContext() == null || (connectivityManager = (ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity")) == null) {
            return false;
        }
        TelephonyManager telephonyManager = (TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && connectivityManager.getBackgroundDataSetting() && connectivityManager.getActiveNetworkInfo().isConnected() && telephonyManager != null && activeNetworkInfo.getType() == 1 && activeNetworkInfo.isConnected();
    }

    public static boolean isWiredHeadsetOn() {
        if (ClientProperties.getApplicationContext() != null) {
            return ((AudioManager) ClientProperties.getApplicationContext().getSystemService("audio")).isWiredHeadsetOn();
        }
        return false;
    }

    private static Boolean newAdbStatus() {
        try {
            return Boolean.valueOf(1 == Settings.Global.getInt(ClientProperties.getApplicationContext().getContentResolver(), "adb_enabled", 0));
        } catch (Exception e) {
            DeviceLog.exception("Problems fetching adb enabled status", e);
            return null;
        }
    }

    private static Boolean oldAdbStatus() {
        try {
            return Boolean.valueOf(1 == Settings.Secure.getInt(ClientProperties.getApplicationContext().getContentResolver(), "adb_enabled", 0));
        } catch (Exception e) {
            DeviceLog.exception("Problems fetching adb enabled status", e);
            return null;
        }
    }

    private static boolean searchPathForBinary(String str) {
        File[] fileArrListFiles;
        for (String str2 : System.getenv("PATH").split(":")) {
            File file = new File(str2);
            if (file.exists() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.getName().equals(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
