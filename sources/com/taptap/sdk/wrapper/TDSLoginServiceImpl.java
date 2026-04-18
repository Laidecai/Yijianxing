package com.taptap.sdk.wrapper;

import android.app.Activity;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.AccountGlobalError;
import com.taptap.sdk.LoginSdkConfig;
import com.taptap.sdk.Profile;
import com.taptap.sdk.RegionType;
import com.taptap.sdk.TapLoginHelper;
import com.taptap.sdk.net.Api;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.utils.BridgeJsonHelper;
import com.tds.common.log.Logger;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class TDSLoginServiceImpl implements TDSLoginService {
    private static final String TAG = "TDSLoginServiceImpl";
    private final Logger logger = Logger.getCommonLogger();

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void init(Activity activity, String str) {
        init(activity, str, true, true);
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void init(Activity activity, String str, boolean z, boolean z2) {
        this.logger.i(TAG, "init");
        LoginSdkConfig loginSdkConfig = new LoginSdkConfig();
        loginSdkConfig.regionType = z ? RegionType.CN : RegionType.IO;
        loginSdkConfig.roundCorner = z2;
        TapLoginHelper.init(activity, str, loginSdkConfig);
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void changeConfig(boolean z, boolean z2) {
        this.logger.i(TAG, "changeConfig");
        LoginSdkConfig loginSdkConfig = new LoginSdkConfig();
        loginSdkConfig.roundCorner = z;
        loginSdkConfig.isPortrait = z2;
        TapLoginHelper.changeTapLoginConfig(loginSdkConfig);
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void registerLoginCallback(final BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "registerLoginCallback");
        TapLoginHelper.registerLoginCallback(new TapLoginHelper.TapLoginResultCallback() { // from class: com.taptap.sdk.wrapper.TDSLoginServiceImpl.1
            @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
            public void onLoginSuccess(AccessToken accessToken) {
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(new LoginWrapperBean(accessToken.toJsonString(), 0)));
            }

            @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
            public void onLoginCancel() {
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(new LoginWrapperBean(1)));
            }

            @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
            public void onLoginError(AccountGlobalError accountGlobalError) {
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(new LoginWrapperBean(accountGlobalError.toJsonString(), 2)));
            }
        });
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void unregisterLoginCallback() {
        this.logger.i(TAG, "unregisterLoginCallback");
        TapLoginHelper.unregisterLoginCallback();
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void startTapLogin(Activity activity, String[] strArr) {
        this.logger.i(TAG, "startTapLogin");
        TapLoginHelper.startTapLogin(activity, strArr);
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void currentAccessToken(BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getCurrentAccessToken");
        AccessToken currentAccessToken = TapLoginHelper.getCurrentAccessToken();
        if (currentAccessToken != null) {
            bridgeCallback.onResult(currentAccessToken.toJsonString());
        } else {
            bridgeCallback.onResult(null);
        }
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void currentProfile(BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getCurrentProfile");
        Profile currentProfile = TapLoginHelper.getCurrentProfile();
        if (currentProfile != null) {
            bridgeCallback.onResult(currentProfile.toJsonString());
        } else {
            bridgeCallback.onResult(null);
        }
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void fetchProfileForCurrentAccessToken(final BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "fetchProfileForCurrentAccessToken");
        TapLoginHelper.fetchProfileForCurrentAccessToken(new Api.ApiCallback<Profile>() { // from class: com.taptap.sdk.wrapper.TDSLoginServiceImpl.2
            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onSuccess(Profile profile) {
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(new LoginWrapperBean(profile.toJsonString(), 0)));
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onError(Throwable th) {
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(new LoginWrapperBean(th.getMessage(), 1)));
            }
        });
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void getTestQualification(final BridgeCallback bridgeCallback) {
        final HashMap map = new HashMap(1);
        TapLoginHelper.getTestQualification(new Api.ApiCallback<Boolean>() { // from class: com.taptap.sdk.wrapper.TDSLoginServiceImpl.3
            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onSuccess(Boolean bool) {
                map.put("userTestQualification", Integer.valueOf(bool.booleanValue() ? 1 : 0));
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onError(Throwable th) {
                map.put("userTestQualification", 0);
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
            }
        });
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void logout() {
        this.logger.i(TAG, "logout");
        TapLoginHelper.logout();
    }

    @Override // com.taptap.sdk.wrapper.TDSLoginService
    public void appendPermission(String str) {
        this.logger.i(TAG, "appendPermission");
        TapLoginHelper.appendPermission(str);
    }
}
