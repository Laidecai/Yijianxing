package com.taptap.sdk.ui;

import android.content.ServiceConnection;
import android.text.TextUtils;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.TapLoginInnerConfig;
import com.taptap.sdk.TapPhoneLoginManager;
import com.taptap.sdk.tracker.TapTapLoginTrackerHelper;
import com.taptap.sdk.ui.CloudGameHandler;
import com.tds.common.tracker.model.NetworkStateModel;

/* JADX INFO: loaded from: classes.dex */
class LoginClient implements CloudGameHandler.OnCloudGameLoginResult {
    private ActivityHandler activityHandler;
    private CloudGameHandler cloudGameHandler;
    private PhoneVerifyHandler phoneVerifyHandler;
    private LoginRequest request;
    private WebViewHandler webViewHandler;

    public LoginClient(ActivityDelegate activityDelegate) {
        this.cloudGameHandler = new CloudGameHandler(activityDelegate);
        this.activityHandler = new ActivityHandler(activityDelegate);
        this.webViewHandler = new WebViewHandler(activityDelegate);
        this.phoneVerifyHandler = new PhoneVerifyHandler(activityDelegate);
        this.cloudGameHandler.setOnCloudGameLoginResult(this);
    }

    public boolean sendLoginRequest(LoginRequest loginRequest) {
        WebViewHandler webViewHandler;
        TapLoginInnerConfig.codeVerifier = CodeUtil.getCodeVerifier(128);
        initLoginRequest(loginRequest);
        this.request = loginRequest;
        try {
            CloudGameHandler cloudGameHandler = this.cloudGameHandler;
            if (cloudGameHandler != null && cloudGameHandler.bindCloudGameService()) {
                this.cloudGameHandler.authorize(loginRequest);
                return false;
            }
            this.activityHandler.authorize(loginRequest);
            return true;
        } catch (Exception unused) {
            TapTapLoginTrackerHelper.authorizationBack();
            if (handlePhoneVerify(loginRequest) || handlePhoneVerify2(loginRequest) || (webViewHandler = this.webViewHandler) == null) {
                return false;
            }
            webViewHandler.authorize(loginRequest);
            return false;
        }
    }

    @Override // com.taptap.sdk.ui.CloudGameHandler.OnCloudGameLoginResult
    public void onLoginFailed() {
        LoginRequest loginRequest;
        TapTapLoginTrackerHelper.authorizationBack();
        WebViewHandler webViewHandler = this.webViewHandler;
        if (webViewHandler == null || (loginRequest = this.request) == null) {
            return;
        }
        webViewHandler.authorize(loginRequest);
    }

    public ServiceConnection getServiceConnection() {
        CloudGameHandler cloudGameHandler = this.cloudGameHandler;
        if (cloudGameHandler != null) {
            return cloudGameHandler.getCloudGameServiceConnection();
        }
        return null;
    }

    private void initLoginRequest(LoginRequest loginRequest) {
        loginRequest.setLoginVersion("1");
        loginRequest.setResponseType(NetworkStateModel.PARAM_CODE);
        loginRequest.setRedirectUri("tapoauth://authorize");
        loginRequest.setCodeChallenge(CodeUtil.getCodeChallenge(TapLoginInnerConfig.codeVerifier));
        loginRequest.setCodeChallengeMethod("S256");
    }

    private boolean handlePhoneVerify(final LoginRequest loginRequest) {
        if (TapPhoneLoginManager.getInstance().getClientLoginCallback() == null) {
            return false;
        }
        this.phoneVerifyHandler.authorize(loginRequest);
        TapPhoneLoginManager.getInstance().registerPhoneLoginCallback(new TapPhoneLoginManager.PhoneLoginCallback() { // from class: com.taptap.sdk.ui.LoginClient.1
            @Override // com.taptap.sdk.TapPhoneLoginManager.PhoneLoginCallback
            public void continueWebLogin(String str, String str2) {
                TapPhoneLoginManager.getInstance().removePhoneLoginCallback();
                if (!TextUtils.isEmpty(str)) {
                    loginRequest.setPhoneVerifyToken(str);
                }
                if (!TextUtils.isEmpty(str2)) {
                    loginRequest.setPreferredLoginType(str2);
                }
                if (LoginClient.this.webViewHandler != null) {
                    LoginClient.this.webViewHandler.authorize(loginRequest);
                }
            }

            @Override // com.taptap.sdk.TapPhoneLoginManager.PhoneLoginCallback
            public void cancelLogin() {
                if (LoginClient.this.webViewHandler.getActivity() != null) {
                    LoginClient.this.webViewHandler.getActivity().finish();
                }
            }
        });
        TapPhoneLoginManager.getInstance().getClientLoginCallback().clientLoginFail();
        return true;
    }

    private boolean handlePhoneVerify2(final LoginRequest loginRequest) {
        if (TapPhoneLoginManager.getInstance().getClientLoginCallback2() == null) {
            return false;
        }
        this.phoneVerifyHandler.authorize(loginRequest);
        TapPhoneLoginManager.getInstance().registerPhoneLoginCallback2(new TapPhoneLoginManager.PhoneLoginCallback2() { // from class: com.taptap.sdk.ui.LoginClient.2
            @Override // com.taptap.sdk.TapPhoneLoginManager.PhoneLoginCallback2
            public void continueWebLogin(String str, String str2, String... strArr) {
                TapPhoneLoginManager.getInstance().removePhoneLoginCallback();
                if (!TextUtils.isEmpty(str)) {
                    loginRequest.setPhoneVerifyToken(str);
                }
                if (!TextUtils.isEmpty(str2)) {
                    loginRequest.setPreferredLoginType(str2);
                }
                if (strArr != null && strArr.length > 0) {
                    loginRequest.setPermissions(strArr);
                }
                if (LoginClient.this.webViewHandler != null) {
                    LoginClient.this.webViewHandler.authorize(loginRequest);
                }
            }

            @Override // com.taptap.sdk.TapPhoneLoginManager.PhoneLoginCallback2
            public void cancelLogin() {
                if (LoginClient.this.webViewHandler.getActivity() != null) {
                    LoginClient.this.webViewHandler.getActivity().finish();
                }
            }
        });
        TapPhoneLoginManager.getInstance().getClientLoginCallback2().clientLoginFail(loginRequest.getPermissions());
        return true;
    }
}
