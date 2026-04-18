package com.taptap.sdk;

import android.content.Context;
import com.tds.common.isc.IscServiceManager;

/* JADX INFO: loaded from: classes.dex */
public class TapTapSdk {
    public static final int LOGIN_SESSION_CODE = 10;
    public static final String SCOPE_PUIBLIC_PROFILE = "public_profile";
    public static volatile boolean isInited = false;

    public static String getSDKVersion() {
        return "3.29.0";
    }

    public static synchronized void sdkInitialize(Context context, String str) {
        sdkInitialize(context, str, checkLoginSdkConfig(null));
    }

    public static synchronized void sdkInitialize(Context context, String str, LoginSdkConfig loginSdkConfig) {
        LoginSdkConfig loginSdkConfigCheckLoginSdkConfig = checkLoginSdkConfig(loginSdkConfig);
        Validate.notNull(context, "application context");
        Validate.hasInternetPermissions(context, false);
        Validate.hasTapTapActivity(context, true);
        if (!isInited) {
            isInited = true;
            TapTapSharePreference.init(context);
            TapLoginInnerConfig.setClientId(str);
            TapLoginInnerConfig.setRegionType(loginSdkConfigCheckLoginSdkConfig.regionType);
            AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
            Profile currentProfile = Profile.getCurrentProfile();
            if (currentAccessToken != null) {
                TokenValidChecker.check(currentAccessToken.access_token, str);
                if (currentProfile == null) {
                    Profile.fetchProfileForCurrentAccessToken(null);
                }
            }
        }
        TapLoginInnerConfig.roundCorner = loginSdkConfigCheckLoginSdkConfig.roundCorner;
        TapLoginInnerConfig.isPortrait = loginSdkConfigCheckLoginSdkConfig.isPortrait;
        IscServiceManager.register(IscTapLoginService.class);
    }

    public static void changeTapLoginConfig(LoginSdkConfig loginSdkConfig) {
        TapLoginInnerConfig.roundCorner = loginSdkConfig.roundCorner;
        TapLoginInnerConfig.isPortrait = loginSdkConfig.isPortrait;
    }

    public static synchronized String getClientId() {
        return TapLoginInnerConfig.getClientId();
    }

    public static synchronized RegionType regionType() {
        return TapLoginInnerConfig.getRegionType();
    }

    private static synchronized LoginSdkConfig checkLoginSdkConfig(LoginSdkConfig loginSdkConfig) {
        if (loginSdkConfig != null) {
            return loginSdkConfig;
        }
        LoginSdkConfig loginSdkConfig2 = new LoginSdkConfig();
        loginSdkConfig2.roundCorner = true;
        loginSdkConfig2.isPortrait = true;
        loginSdkConfig2.regionType = RegionType.CN;
        return loginSdkConfig2;
    }
}
