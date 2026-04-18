package com.taptap.sdk;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.taptap.sdk.CallbackManagerImpl;
import com.taptap.sdk.TapLoginWithCode;
import com.taptap.sdk.net.Api;
import com.taptap.sdk.ui.TapTapActivity;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class LoginManager {
    public static final String KEY_RESPONSE = "com.taptap.sdk.response";
    public static final String RES_KEY_CANCEL = "com.taptap.sdk.response.cancel";
    public static final String RES_KEY_CODE = "com.taptap.sdk.response.code";
    public static final String RES_KEY_CODE_VERIFIER = "com.taptap.sdk.response.codeVerifier";
    public static final String RES_KEY_ERROR = "com.taptap.sdk.response.error";
    public static final String RES_KEY_LOGIN_VERSION = "com.taptap.sdk.response.login_version";
    public static final String RES_KEY_PERMISSIONS = "com.taptap.sdk.response.permissions";
    public static final String RES_KEY_SERVER_URI = "com.taptap.sdk.response.server_uri";
    public static final String RES_KEY_STATE = "com.taptap.sdk.response.state";
    public static final String RES_KEY_TOKEN = "com.taptap.sdk.response.token";
    public static final String RES_KEY_TOKEN_PARCELABLE = "com.taptap.sdk.response.token.parcel";
    private static LoginManager instance;
    private LoginRequest mLastLoginRequest;
    private boolean preApproved = false;

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    private LoginManager() {
    }

    /* JADX INFO: renamed from: com.taptap.sdk.LoginManager$1 */
    class AnonymousClass1 implements CallbackManagerImpl.Callback {
        final /* synthetic */ TapTapLoginCallback val$callback;

        AnonymousClass1(TapTapLoginCallback tapTapLoginCallback) {
            tapTapLoginCallback = tapTapLoginCallback;
        }

        @Override // com.taptap.sdk.CallbackManagerImpl.Callback
        public boolean onActivityResult(int i, Intent intent) {
            return LoginManager.this.onActivityResult(i, intent, tapTapLoginCallback);
        }
    }

    public void registerCallback(CallBackManager callBackManager, TapTapLoginCallback<LoginResponse> tapTapLoginCallback) {
        if (callBackManager instanceof CallbackManagerImpl) {
            ((CallbackManagerImpl) callBackManager).registerCallback(new CallbackManagerImpl.Callback() { // from class: com.taptap.sdk.LoginManager.1
                final /* synthetic */ TapTapLoginCallback val$callback;

                AnonymousClass1(TapTapLoginCallback tapTapLoginCallback2) {
                    tapTapLoginCallback = tapTapLoginCallback2;
                }

                @Override // com.taptap.sdk.CallbackManagerImpl.Callback
                public boolean onActivityResult(int i, Intent intent) {
                    return LoginManager.this.onActivityResult(i, intent, tapTapLoginCallback);
                }
            }, 10);
            return;
        }
        throw new IllegalStateException("callbackManager must be instance of  CallbackManagerImpl");
    }

    public void logInWithReadPermissions(Activity activity, LoginRequest loginRequest, String str, String... strArr) {
        Validate.sdkHasInitialized();
        loginRequest.setPreApproved(this.preApproved);
        this.mLastLoginRequest = loginRequest;
        loginRequest.setVersionCode("3.29.0");
        loginRequest.setInfo(LoginRequest.generateInfo(activity, str, this.preApproved));
        Intent intent = new Intent();
        intent.setClass(activity, TapTapActivity.class);
        intent.putExtra("request", loginRequest);
        activity.startActivityForResult(intent, loginRequest.getRequestCode());
    }

    public void logout() {
        Validate.sdkHasInitialized();
        if (AccessToken.getCurrentAccessToken() != null) {
            AccessToken.clear();
        }
        if (Profile.getCurrentProfile() != null) {
            Profile.getCurrentProfile().clear();
        }
    }

    public void setPreApproved(boolean z) {
        this.preApproved = z;
    }

    public boolean onActivityResult(int i, Intent intent, TapTapLoginCallback<LoginResponse> tapTapLoginCallback) {
        if (i != -1) {
            if (i == 0) {
                tapTapLoginCallback.onCancel();
            }
            return true;
        }
        boolean z = intent == null || intent.getBooleanExtra(TapLoginHelperActivity.INTENT_KEY_LOGIN_REQUEST, true);
        LoginResponse resultFromIntent = null;
        try {
            resultFromIntent = LoginResponse.getResultFromIntent(intent);
        } catch (JSONException e) {
            tapTapLoginCallback.onError(e);
        }
        try {
            if (resultFromIntent == null) {
                tapTapLoginCallback.onError(new NullPointerException("result is null"));
                return false;
            }
            if (resultFromIntent.cancel) {
                tapTapLoginCallback.onCancel();
                return false;
            }
            if (resultFromIntent.state != null && resultFromIntent.state.equals(this.mLastLoginRequest.getState())) {
                if (!TextUtils.isEmpty(resultFromIntent.errorMessage)) {
                    if (TextUtils.equals(resultFromIntent.errorMessage, AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED)) {
                        tapTapLoginCallback.onCancel();
                    } else {
                        tapTapLoginCallback.onError(new IllegalArgumentException(resultFromIntent.errorMessage));
                    }
                } else if (resultFromIntent.token == null && resultFromIntent.code == null) {
                    tapTapLoginCallback.onError(new IllegalAccessException("token is null"));
                } else if ("1".equals(resultFromIntent.loginVersion)) {
                    TapLoginWithCode.loginWithCode(resultFromIntent.code, TapLoginInnerConfig.codeVerifier, resultFromIntent.state, resultFromIntent.getName(), new TapLoginWithCode.LoginResultCallBack() { // from class: com.taptap.sdk.LoginManager.2
                        final /* synthetic */ TapTapLoginCallback val$callback;
                        final /* synthetic */ boolean val$isLogin;

                        AnonymousClass2(TapTapLoginCallback tapTapLoginCallback2, boolean z2) {
                            tapTapLoginCallback = tapTapLoginCallback2;
                            z = z2;
                        }

                        @Override // com.taptap.sdk.TapLoginWithCode.LoginResultCallBack
                        public void onLoginResult(LoginResponse loginResponse) {
                            if (!TextUtils.isEmpty(loginResponse.errorMessage)) {
                                if (TextUtils.equals(loginResponse.errorMessage, AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED)) {
                                    tapTapLoginCallback.onCancel();
                                    return;
                                } else {
                                    tapTapLoginCallback.onError(new IllegalArgumentException(loginResponse.errorMessage));
                                    return;
                                }
                            }
                            if (z) {
                                LoginManager.this.loginWithToken(loginResponse, tapTapLoginCallback);
                            } else {
                                tapTapLoginCallback.onSuccess(loginResponse);
                            }
                        }
                    });
                } else if (z2) {
                    loginWithToken(resultFromIntent, tapTapLoginCallback2);
                } else {
                    tapTapLoginCallback2.onSuccess(resultFromIntent);
                }
                return true;
            }
            tapTapLoginCallback2.onError(new IllegalStateException("state not equal"));
            return false;
        } catch (Exception e2) {
            tapTapLoginCallback2.onError(e2);
            return false;
        }
    }

    /* JADX INFO: renamed from: com.taptap.sdk.LoginManager$2 */
    class AnonymousClass2 implements TapLoginWithCode.LoginResultCallBack {
        final /* synthetic */ TapTapLoginCallback val$callback;
        final /* synthetic */ boolean val$isLogin;

        AnonymousClass2(TapTapLoginCallback tapTapLoginCallback2, boolean z2) {
            tapTapLoginCallback = tapTapLoginCallback2;
            z = z2;
        }

        @Override // com.taptap.sdk.TapLoginWithCode.LoginResultCallBack
        public void onLoginResult(LoginResponse loginResponse) {
            if (!TextUtils.isEmpty(loginResponse.errorMessage)) {
                if (TextUtils.equals(loginResponse.errorMessage, AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED)) {
                    tapTapLoginCallback.onCancel();
                    return;
                } else {
                    tapTapLoginCallback.onError(new IllegalArgumentException(loginResponse.errorMessage));
                    return;
                }
            }
            if (z) {
                LoginManager.this.loginWithToken(loginResponse, tapTapLoginCallback);
            } else {
                tapTapLoginCallback.onSuccess(loginResponse);
            }
        }
    }

    public void loginWithToken(LoginResponse loginResponse, TapTapLoginCallback<LoginResponse> tapTapLoginCallback) {
        AccessToken.setCurrentToken(loginResponse.token);
        Profile.fetchProfileForCurrentAccessToken(new Api.ApiCallback<Profile>() { // from class: com.taptap.sdk.LoginManager.3
            final /* synthetic */ TapTapLoginCallback val$callback;
            final /* synthetic */ LoginResponse val$loginResponse;

            AnonymousClass3(TapTapLoginCallback tapTapLoginCallback2, LoginResponse loginResponse2) {
                tapTapLoginCallback = tapTapLoginCallback2;
                loginResponse = loginResponse2;
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onSuccess(Profile profile) {
                if (AccessToken.getCurrentAccessToken() == null) {
                    tapTapLoginCallback.onCancel();
                } else {
                    tapTapLoginCallback.onSuccess(loginResponse);
                }
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onError(Throwable th) {
                StringBuilder sb = new StringBuilder();
                sb.append("fetch profile fail error = ");
                sb.append(th == null ? EnvironmentCompat.MEDIA_UNKNOWN : th.getMessage());
                Log.DEBUG_LOG(sb.toString());
                AccessToken.clear();
                tapTapLoginCallback.onError(th);
            }
        });
    }

    /* JADX INFO: renamed from: com.taptap.sdk.LoginManager$3 */
    class AnonymousClass3 implements Api.ApiCallback<Profile> {
        final /* synthetic */ TapTapLoginCallback val$callback;
        final /* synthetic */ LoginResponse val$loginResponse;

        AnonymousClass3(TapTapLoginCallback tapTapLoginCallback2, LoginResponse loginResponse2) {
            tapTapLoginCallback = tapTapLoginCallback2;
            loginResponse = loginResponse2;
        }

        @Override // com.taptap.sdk.net.Api.ApiCallback
        public void onSuccess(Profile profile) {
            if (AccessToken.getCurrentAccessToken() == null) {
                tapTapLoginCallback.onCancel();
            } else {
                tapTapLoginCallback.onSuccess(loginResponse);
            }
        }

        @Override // com.taptap.sdk.net.Api.ApiCallback
        public void onError(Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("fetch profile fail error = ");
            sb.append(th == null ? EnvironmentCompat.MEDIA_UNKNOWN : th.getMessage());
            Log.DEBUG_LOG(sb.toString());
            AccessToken.clear();
            tapTapLoginCallback.onError(th);
        }
    }
}
