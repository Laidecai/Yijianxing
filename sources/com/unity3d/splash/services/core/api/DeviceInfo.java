package com.unity3d.splash.services.core.api;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.SparseArray;
import com.alipay.sdk.cons.c;
import com.alipay.sdk.packet.e;
import com.tds.common.tracker.constants.CommonParam;
import com.unity3d.splash.services.core.device.Device;
import com.unity3d.splash.services.core.device.DeviceError;
import com.unity3d.splash.services.core.device.IVolumeChangeListener;
import com.unity3d.splash.services.core.device.VolumeChange;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfo {
    private static SparseArray _volumeChangeListeners;

    /* JADX INFO: renamed from: com.unity3d.splash.services.core.api.DeviceInfo$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$unity3d$splash$services$core$api$DeviceInfo$StorageType;

        static {
            int[] iArr = new int[StorageType.values().length];
            $SwitchMap$com$unity3d$splash$services$core$api$DeviceInfo$StorageType = iArr;
            try {
                iArr[StorageType.INTERNAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$api$DeviceInfo$StorageType[StorageType.EXTERNAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum DeviceInfoEvent {
        VOLUME_CHANGED
    }

    public enum StorageType {
        EXTERNAL,
        INTERNAL
    }

    @WebViewExposed
    public static void getAdvertisingTrackingId(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getAdvertisingTrackingId());
    }

    @WebViewExposed
    public static void getAndroidId(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getAndroidId());
    }

    @WebViewExposed
    public static void getApiLevel(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(Device.getApiLevel()));
    }

    @WebViewExposed
    public static void getApkDigest(WebViewCallback webViewCallback) {
        try {
            webViewCallback.invoke(Device.getApkDigest());
        } catch (Exception e) {
            webViewCallback.error(DeviceError.COULDNT_GET_DIGEST, e.toString());
        }
    }

    @WebViewExposed
    public static void getBatteryLevel(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Float.valueOf(Device.getBatteryLevel()));
    }

    @WebViewExposed
    public static void getBatteryStatus(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(Device.getBatteryStatus()));
    }

    @WebViewExposed
    public static void getBoard(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getBoard());
    }

    @WebViewExposed
    public static void getBootloader(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getBootloader());
    }

    @WebViewExposed
    public static void getBrand(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getBrand());
    }

    @WebViewExposed
    public static void getBuildId(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getBuildId());
    }

    @WebViewExposed
    public static void getBuildVersionIncremental(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getBuildVersionIncremental());
    }

    @WebViewExposed
    public static void getCPUCount(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getCPUCount()));
    }

    @WebViewExposed
    public static void getCertificateFingerprint(WebViewCallback webViewCallback) {
        String certificateFingerprint = Device.getCertificateFingerprint();
        if (certificateFingerprint != null) {
            webViewCallback.invoke(certificateFingerprint);
        } else {
            webViewCallback.error(DeviceError.COULDNT_GET_FINGERPRINT, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getConnectionType(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.isUsingWifi() ? "wifi" : Device.isActiveNetworkConnected() ? "cellular" : "none");
    }

    @WebViewExposed
    public static void getDevice(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getDevice());
    }

    @WebViewExposed
    public static void getDeviceId(WebViewCallback webViewCallback) {
        getDeviceIdCommon(null, webViewCallback);
    }

    private static void getDeviceIdCommon(Integer num, WebViewCallback webViewCallback) {
        if (ClientProperties.getApplicationContext() == null) {
            webViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, new Object[0]);
            return;
        }
        if (ClientProperties.getApplicationContext().checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            webViewCallback.error(PermissionsError.PERMISSION_NOT_GRANTED, new Object[0]);
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone");
        if (telephonyManager != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                if (num == null) {
                    webViewCallback.invoke(telephonyManager.getImei());
                    return;
                } else {
                    webViewCallback.invoke(telephonyManager.getImei(num.intValue()));
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= 23) {
                if (num == null) {
                    webViewCallback.invoke(telephonyManager.getDeviceId());
                    return;
                } else {
                    webViewCallback.invoke(telephonyManager.getDeviceId(num.intValue()));
                    return;
                }
            }
            if (num == null) {
                webViewCallback.invoke(telephonyManager.getDeviceId());
            } else {
                webViewCallback.error(DeviceError.API_LEVEL_ERROR, Integer.valueOf(Build.VERSION.SDK_INT));
            }
        }
    }

    @WebViewExposed
    public static void getDeviceIdWithSlot(Integer num, WebViewCallback webViewCallback) {
        getDeviceIdCommon(num, webViewCallback);
    }

    @WebViewExposed
    public static void getDeviceMaxVolume(Integer num, WebViewCallback webViewCallback) {
        int streamMaxVolume = Device.getStreamMaxVolume(num.intValue());
        if (streamMaxVolume >= 0) {
            webViewCallback.invoke(Integer.valueOf(streamMaxVolume));
            return;
        }
        if (streamMaxVolume == -2) {
            webViewCallback.error(DeviceError.AUDIOMANAGER_NULL, Integer.valueOf(streamMaxVolume));
        } else {
            if (streamMaxVolume == -1) {
                webViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, Integer.valueOf(streamMaxVolume));
                return;
            }
            DeviceLog.error("Unhandled deviceMaxVolume error: " + streamMaxVolume);
        }
    }

    @WebViewExposed
    public static void getDeviceVolume(Integer num, WebViewCallback webViewCallback) {
        int streamVolume = Device.getStreamVolume(num.intValue());
        if (streamVolume >= 0) {
            webViewCallback.invoke(Integer.valueOf(streamVolume));
            return;
        }
        if (streamVolume == -2) {
            webViewCallback.error(DeviceError.AUDIOMANAGER_NULL, Integer.valueOf(streamVolume));
        } else {
            if (streamVolume == -1) {
                webViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, Integer.valueOf(streamVolume));
                return;
            }
            DeviceLog.error("Unhandled deviceVolume error: " + streamVolume);
        }
    }

    @WebViewExposed
    public static void getElapsedRealtime(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getElapsedRealtime()));
    }

    private static File getFileForStorageType(StorageType storageType) {
        int i = AnonymousClass2.$SwitchMap$com$unity3d$splash$services$core$api$DeviceInfo$StorageType[storageType.ordinal()];
        if (i == 1) {
            return ClientProperties.getApplicationContext().getCacheDir();
        }
        if (i == 2) {
            return ClientProperties.getApplicationContext().getExternalCacheDir();
        }
        DeviceLog.error("Unhandled storagetype: " + storageType);
        return null;
    }

    @WebViewExposed
    public static void getFingerprint(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getFingerprint());
    }

    @WebViewExposed
    public static void getFreeMemory(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getFreeMemory()));
    }

    @WebViewExposed
    public static void getFreeSpace(String str, WebViewCallback webViewCallback) {
        StorageType storageTypeFromString = getStorageTypeFromString(str);
        if (storageTypeFromString == null) {
            webViewCallback.error(DeviceError.INVALID_STORAGETYPE, str);
            return;
        }
        long freeSpace = Device.getFreeSpace(getFileForStorageType(storageTypeFromString));
        if (freeSpace > -1) {
            webViewCallback.invoke(Long.valueOf(freeSpace));
        } else {
            webViewCallback.error(DeviceError.COULDNT_GET_STORAGE_LOCATION, Long.valueOf(freeSpace));
        }
    }

    @WebViewExposed
    public static void getHardware(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getHardware());
    }

    @WebViewExposed
    public static void getHeadset(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(Device.isWiredHeadsetOn()));
    }

    @WebViewExposed
    public static void getHost(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getHost());
    }

    @WebViewExposed
    public static void getInstalledPackages(boolean z, WebViewCallback webViewCallback) {
        webViewCallback.invoke(new JSONArray((Collection) Device.getInstalledPackages(z)));
    }

    @WebViewExposed
    public static void getLimitAdTrackingFlag(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(Device.isLimitAdTrackingEnabled()));
    }

    @WebViewExposed
    public static void getLimitOpenAdTrackingFlag(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(Device.isLimitOpenAdTrackingEnabled()));
    }

    @WebViewExposed
    public static void getManufacturer(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getManufacturer());
    }

    @WebViewExposed
    public static void getModel(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getModel());
    }

    @WebViewExposed
    public static void getNetworkCountryISO(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getNetworkCountryISO());
    }

    @WebViewExposed
    public static void getNetworkMetered(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(Device.getNetworkMetered()));
    }

    @WebViewExposed
    public static void getNetworkOperator(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getNetworkOperator());
    }

    @WebViewExposed
    public static void getNetworkOperatorName(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getNetworkOperatorName());
    }

    @WebViewExposed
    public static void getNetworkType(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(Device.getNetworkType()));
    }

    @WebViewExposed
    public static void getOpenAdvertisingTrackingId(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getOpenAdvertisingTrackingId());
    }

    @WebViewExposed
    public static void getOsVersion(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getOsVersion());
    }

    @WebViewExposed
    public static void getPackageInfo(String str, WebViewCallback webViewCallback) {
        if (ClientProperties.getApplicationContext() == null) {
            webViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, new Object[0]);
            return;
        }
        PackageManager packageManager = ClientProperties.getApplicationContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("installer", packageManager.getInstallerPackageName(str));
                jSONObject.put("firstInstallTime", packageInfo.firstInstallTime);
                jSONObject.put("lastUpdateTime", packageInfo.lastUpdateTime);
                jSONObject.put("versionCode", packageInfo.versionCode);
                jSONObject.put("versionName", packageInfo.versionName);
                jSONObject.put("packageName", packageInfo.packageName);
                webViewCallback.invoke(jSONObject);
            } catch (JSONException e) {
                webViewCallback.error(DeviceError.JSON_ERROR, e.getMessage());
            }
        } catch (PackageManager.NameNotFoundException unused) {
            webViewCallback.error(DeviceError.APPLICATION_INFO_NOT_AVAILABLE, str);
        }
    }

    @WebViewExposed
    public static void getProcessInfo(WebViewCallback webViewCallback) throws Throwable {
        JSONObject jSONObject = new JSONObject();
        Map processInfo = Device.getProcessInfo();
        if (processInfo != null) {
            try {
                if (processInfo.containsKey("stat")) {
                    jSONObject.put("stat", processInfo.get("stat"));
                }
                if (processInfo.containsKey("uptime")) {
                    jSONObject.put("uptime", processInfo.get("uptime"));
                }
            } catch (Exception e) {
                DeviceLog.exception("Error while constructing process info", e);
            }
        }
        webViewCallback.invoke(jSONObject);
    }

    @WebViewExposed
    public static void getProduct(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getProduct());
    }

    @WebViewExposed
    public static void getRingerMode(WebViewCallback webViewCallback) {
        int ringerMode = Device.getRingerMode();
        if (ringerMode >= 0) {
            webViewCallback.invoke(Integer.valueOf(ringerMode));
            return;
        }
        if (ringerMode == -2) {
            webViewCallback.error(DeviceError.AUDIOMANAGER_NULL, Integer.valueOf(ringerMode));
        } else {
            if (ringerMode == -1) {
                webViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, Integer.valueOf(ringerMode));
                return;
            }
            DeviceLog.error("Unhandled ringerMode error: " + ringerMode);
        }
    }

    @WebViewExposed
    public static void getScreenBrightness(WebViewCallback webViewCallback) {
        int screenBrightness = Device.getScreenBrightness();
        if (screenBrightness >= 0) {
            webViewCallback.invoke(Integer.valueOf(screenBrightness));
        } else {
            if (screenBrightness == -1) {
                webViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, Integer.valueOf(screenBrightness));
                return;
            }
            DeviceLog.error("Unhandled screenBrightness error: " + screenBrightness);
        }
    }

    @WebViewExposed
    public static void getScreenDensity(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(Device.getScreenDensity()));
    }

    @WebViewExposed
    public static void getScreenHeight(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(Device.getScreenHeight()));
    }

    @WebViewExposed
    public static void getScreenLayout(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(Device.getScreenLayout()));
    }

    @WebViewExposed
    public static void getScreenWidth(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(Device.getScreenWidth()));
    }

    @WebViewExposed
    public static void getSensorList(WebViewCallback webViewCallback) {
        JSONArray jSONArray = new JSONArray();
        List<Sensor> sensorList = Device.getSensorList();
        if (sensorList != null) {
            for (Sensor sensor : sensorList) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(c.e, sensor.getName());
                    jSONObject.put(e.r, sensor.getType());
                    jSONObject.put("vendor", sensor.getVendor());
                    jSONObject.put("maximumRange", sensor.getMaximumRange());
                    jSONObject.put("power", sensor.getPower());
                    jSONObject.put(CommonParam.VERSION, sensor.getVersion());
                    jSONObject.put("resolution", sensor.getResolution());
                    jSONObject.put("minDelay", sensor.getMinDelay());
                    jSONArray.put(jSONObject);
                } catch (JSONException e) {
                    webViewCallback.error(DeviceError.JSON_ERROR, e.getMessage());
                    return;
                }
            }
        }
        webViewCallback.invoke(jSONArray);
    }

    private static StorageType getStorageTypeFromString(String str) {
        try {
            return StorageType.valueOf(str);
        } catch (IllegalArgumentException e) {
            DeviceLog.exception("Illegal argument: " + str, e);
            return null;
        }
    }

    @WebViewExposed
    public static void getSupportedAbis(WebViewCallback webViewCallback) {
        JSONArray jSONArray = new JSONArray();
        Iterator it = Device.getSupportedAbis().iterator();
        while (it.hasNext()) {
            jSONArray.put((String) it.next());
        }
        webViewCallback.invoke(jSONArray);
    }

    @WebViewExposed
    public static void getSystemLanguage(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Locale.getDefault().toString());
    }

    @WebViewExposed
    public static void getSystemProperty(String str, String str2, WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getSystemProperty(str, str2));
    }

    @WebViewExposed
    public static void getTimeZone(Boolean bool, WebViewCallback webViewCallback) {
        webViewCallback.invoke(TimeZone.getDefault().getDisplayName(bool.booleanValue(), 0, Locale.US));
    }

    @WebViewExposed
    public static void getTimeZoneOffset(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 1000));
    }

    @WebViewExposed
    public static void getTotalMemory(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getTotalMemory()));
    }

    @WebViewExposed
    public static void getTotalSpace(String str, WebViewCallback webViewCallback) {
        StorageType storageTypeFromString = getStorageTypeFromString(str);
        if (storageTypeFromString == null) {
            webViewCallback.error(DeviceError.INVALID_STORAGETYPE, str);
            return;
        }
        long totalSpace = Device.getTotalSpace(getFileForStorageType(storageTypeFromString));
        if (totalSpace > -1) {
            webViewCallback.invoke(Long.valueOf(totalSpace));
        } else {
            webViewCallback.error(DeviceError.COULDNT_GET_STORAGE_LOCATION, Long.valueOf(totalSpace));
        }
    }

    @WebViewExposed
    public static void getUniqueEventId(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Device.getUniqueEventId());
    }

    @WebViewExposed
    public static void getUptime(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getUptime()));
    }

    @WebViewExposed
    public static void isAdbEnabled(WebViewCallback webViewCallback) {
        Boolean boolIsAdbEnabled = Device.isAdbEnabled();
        if (boolIsAdbEnabled != null) {
            webViewCallback.invoke(boolIsAdbEnabled);
        } else {
            webViewCallback.error(DeviceError.COULDNT_GET_ADB_STATUS, new Object[0]);
        }
    }

    @WebViewExposed
    public static void isAppInstalled(String str, WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(Device.isAppInstalled(str)));
    }

    @WebViewExposed
    public static void isRooted(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(Device.isRooted()));
    }

    @WebViewExposed
    public static void isUSBConnected(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(Device.isUSBConnected()));
    }

    @WebViewExposed
    public static void registerVolumeChangeListener(Integer num, WebViewCallback webViewCallback) {
        if (_volumeChangeListeners == null) {
            _volumeChangeListeners = new SparseArray();
        }
        if (_volumeChangeListeners.get(num.intValue()) == null) {
            IVolumeChangeListener iVolumeChangeListener = new IVolumeChangeListener(num) { // from class: com.unity3d.splash.services.core.api.DeviceInfo.1
                private int _streamType;
                final /* synthetic */ Integer val$streamType;

                {
                    this.val$streamType = num;
                    this._streamType = num.intValue();
                }

                @Override // com.unity3d.splash.services.core.device.IVolumeChangeListener
                public final int getStreamType() {
                    return this._streamType;
                }

                @Override // com.unity3d.splash.services.core.device.IVolumeChangeListener
                public final void onVolumeChanged(int i) {
                    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.DEVICEINFO, DeviceInfoEvent.VOLUME_CHANGED, Integer.valueOf(getStreamType()), Integer.valueOf(i), Integer.valueOf(Device.getStreamMaxVolume(this._streamType)));
                }
            };
            _volumeChangeListeners.append(num.intValue(), iVolumeChangeListener);
            VolumeChange.registerListener(iVolumeChangeListener);
        }
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void unregisterVolumeChangeListener(Integer num, WebViewCallback webViewCallback) {
        SparseArray sparseArray = _volumeChangeListeners;
        if (sparseArray != null && sparseArray.get(num.intValue()) != null) {
            VolumeChange.unregisterListener((IVolumeChangeListener) _volumeChangeListeners.get(num.intValue()));
            _volumeChangeListeners.remove(num.intValue());
        }
        webViewCallback.invoke(new Object[0]);
    }
}
