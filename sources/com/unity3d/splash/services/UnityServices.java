package com.unity3d.splash.services;

import android.app.Activity;
import android.os.Build;
import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.configuration.EnvironmentCheck;
import com.unity3d.splash.services.core.configuration.InitializeThread;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class UnityServices {
    private static boolean _configurationInitialized = false;

    public enum UnityServicesError {
        INVALID_ARGUMENT,
        INIT_SANITY_CHECK_FAIL
    }

    public static boolean getDebugMode() {
        return SdkProperties.getDebugMode();
    }

    public static String getVersion() {
        return SdkProperties.getVersionName();
    }

    public static void initialize(Activity activity, String str, IUnityServicesListener iUnityServicesListener, boolean z, boolean z2) {
        StringBuilder sb;
        String str2;
        DeviceLog.entered();
        if (_configurationInitialized) {
            if (ClientProperties.getGameId() == null || ClientProperties.getGameId().equals(str)) {
                return;
            }
            DeviceLog.warning("You are trying to re-initialize with a different gameId");
            return;
        }
        _configurationInitialized = true;
        if (!isSupported()) {
            DeviceLog.error("Error while initializing Unity Services: device is not supported");
            return;
        }
        DeviceLog.info("Application start initializing at " + new Date().getTime());
        SdkProperties.setInitializationTime(System.currentTimeMillis());
        if (str == null || str.length() == 0) {
            DeviceLog.error("Error while initializing Unity Services: empty game ID, halting Unity Ads init");
            if (iUnityServicesListener != null) {
                iUnityServicesListener.onUnityServicesError(UnityServicesError.INVALID_ARGUMENT, "Empty game ID");
                return;
            }
            return;
        }
        if (activity == null) {
            DeviceLog.error("Error while initializing Unity Services: null activity, halting Unity Ads init");
            if (iUnityServicesListener != null) {
                iUnityServicesListener.onUnityServicesError(UnityServicesError.INVALID_ARGUMENT, "Null activity");
                return;
            }
            return;
        }
        if (z) {
            sb = new StringBuilder("Initializing Unity Services ");
            sb.append(SdkProperties.getVersionName());
            sb.append(" (");
            sb.append(SdkProperties.getVersionCode());
            sb.append(") with game id ");
            sb.append(str);
            str2 = " in test mode";
        } else {
            sb = new StringBuilder("Initializing Unity Services ");
            sb.append(SdkProperties.getVersionName());
            sb.append(" (");
            sb.append(SdkProperties.getVersionCode());
            sb.append(") with game id ");
            sb.append(str);
            str2 = " in production mode";
        }
        sb.append(str2);
        DeviceLog.info(sb.toString());
        SdkProperties.setDebugMode(SdkProperties.getDebugMode());
        SdkProperties.setListener(iUnityServicesListener);
        ClientProperties.setGameId(str);
        ClientProperties.setApplicationContext(activity.getApplicationContext());
        ClientProperties.setApplication(activity.getApplication());
        SdkProperties.setPerPlacementLoadEnabled(z2);
        SdkProperties.setTestMode(z);
        if (EnvironmentCheck.isEnvironmentOk()) {
            DeviceLog.info("Unity Services environment check OK");
            InitializeThread.initialize(new Configuration());
        } else {
            DeviceLog.error("Error during Unity Services environment check, halting Unity Services init");
            if (iUnityServicesListener != null) {
                iUnityServicesListener.onUnityServicesError(UnityServicesError.INIT_SANITY_CHECK_FAIL, "Unity Services init environment check failed");
            }
        }
    }

    public static boolean isInitialized() {
        return SdkProperties.isInitialized();
    }

    public static boolean isSupported() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static void setDebugMode(boolean z) {
        SdkProperties.setDebugMode(z);
    }
}
