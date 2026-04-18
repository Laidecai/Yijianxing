package com.unity3d.splash.services.core.properties;

import android.content.Context;
import com.unity3d.splash.BuildConfig;
import com.unity3d.splash.services.IUnityServicesListener;
import com.unity3d.splash.services.core.cache.CacheDirectory;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class SdkProperties {
    private static final String CACHE_DIR_NAME = "UnitySplashAdsCache";
    private static final String CHINA_ISO_ALPHA_2_CODE = "CN";
    private static final String CHINA_ISO_ALPHA_3_CODE = "CHN";
    private static final String LOCAL_CACHE_FILE_PREFIX = "UnitySplashAdsCache-";
    private static final String LOCAL_STORAGE_FILE_PREFIX = "UnitySplashAdsStorage-";
    private static CacheDirectory _cacheDirectory = null;
    private static String _configUrl = null;
    private static boolean _debugMode = false;
    private static long _initializationTime = 0;
    private static boolean _initialized = false;
    private static IUnityServicesListener _listener = null;
    private static boolean _perPlacementLoadEnabled = false;
    private static boolean _reinitialized = false;
    private static boolean _testMode = false;

    public static File getCacheDirectory() {
        return getCacheDirectory(ClientProperties.getApplicationContext());
    }

    public static File getCacheDirectory(Context context) {
        if (_cacheDirectory == null) {
            setCacheDirectory(new CacheDirectory(CACHE_DIR_NAME));
        }
        return _cacheDirectory.getCacheDirectory(context);
    }

    public static String getCacheDirectoryName() {
        return CACHE_DIR_NAME;
    }

    public static CacheDirectory getCacheDirectoryObject() {
        return _cacheDirectory;
    }

    public static String getCacheFilePrefix() {
        return LOCAL_CACHE_FILE_PREFIX;
    }

    public static String getConfigUrl() {
        if (_configUrl == null) {
            _configUrl = getDefaultConfigUrl("release");
        }
        return _configUrl;
    }

    public static boolean getDebugMode() {
        return _debugMode;
    }

    public static String getDefaultConfigUrl(String str) {
        return "https://splash-ads.unitychina.cn/webview/release/native/config.json";
    }

    public static long getInitializationTime() {
        return _initializationTime;
    }

    public static IUnityServicesListener getListener() {
        return _listener;
    }

    public static String getLocalStorageFilePrefix() {
        return LOCAL_STORAGE_FILE_PREFIX;
    }

    public static String getLocalWebViewFile() {
        return getCacheDirectory().getAbsolutePath() + "/UnitySplashAdsWebApp.html";
    }

    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    public static String getVersionName() {
        return "3.3.0";
    }

    private static String getWebViewBranch() {
        return BuildConfig.DEBUG ? "3.3.0" : getVersionName();
    }

    public static boolean isChinaLocale(String str) {
        return str.equalsIgnoreCase("CN") || str.equalsIgnoreCase(CHINA_ISO_ALPHA_3_CODE);
    }

    public static boolean isInitialized() {
        return _initialized;
    }

    public static boolean isPerPlacementLoadEnabled() {
        return _perPlacementLoadEnabled;
    }

    public static boolean isReinitialized() {
        return _reinitialized;
    }

    public static boolean isTestMode() {
        return _testMode;
    }

    public static void setCacheDirectory(CacheDirectory cacheDirectory) {
        _cacheDirectory = cacheDirectory;
    }

    public static void setConfigUrl(String str) throws MalformedURLException, URISyntaxException {
        if (str == null) {
            throw new MalformedURLException();
        }
        if (!str.startsWith("http://") && !str.startsWith("https://")) {
            throw new MalformedURLException();
        }
        new URL(str).toURI();
        _configUrl = str;
    }

    public static void setDebugMode(boolean z) {
        _debugMode = z;
        DeviceLog.setLogLevel(z ? 8 : 4);
    }

    public static void setInitializationTime(long j) {
        _initializationTime = j;
    }

    public static void setInitialized(boolean z) {
        _initialized = z;
    }

    public static void setListener(IUnityServicesListener iUnityServicesListener) {
        _listener = iUnityServicesListener;
    }

    public static void setPerPlacementLoadEnabled(boolean z) {
        _perPlacementLoadEnabled = z;
    }

    public static void setReinitialized(boolean z) {
        _reinitialized = z;
    }

    public static void setTestMode(boolean z) {
        _testMode = z;
    }
}
