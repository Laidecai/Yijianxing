package com.taptap.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.taptap.sdk.CallBackManager;
import java.util.UUID;
import tds.androidx.core.view.WindowCompat;
import tds.androidx.core.view.WindowInsetsCompat;
import tds.androidx.core.view.WindowInsetsControllerCompat;

/* JADX INFO: loaded from: classes.dex */
public class TapLoginHelperActivity extends Activity {
    public static final String INTENT_KEY_LOGIN_REQUEST = "loginRequest";
    public static final String INTENT_KEY_PERMISSION = "permission";
    public static final String INTENT_KEY_SOURCE = "source";
    private static final String LOGIN_REQUEST_STATE_KEY = "login_request_state";
    CallBackManager callBackManager;
    private boolean isLoginRequest = true;
    private String lastLoginRequestState;
    private Handler sendMessageHandler;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!Utils.isLoginSDKInitialized()) {
            finish();
            return;
        }
        android.util.Log.d("TapLoginResult", "TapLoginHelper create");
        if (bundle != null) {
            this.lastLoginRequestState = bundle.getString(LOGIN_REQUEST_STATE_KEY);
        }
        this.callBackManager = CallBackManager.Factory.create();
        LoginManager.getInstance().registerCallback(this.callBackManager, new TapTapLoginCallback<LoginResponse>() { // from class: com.taptap.sdk.TapLoginHelperActivity.1
            @Override // com.taptap.sdk.TapTapLoginCallback
            public void onSuccess(LoginResponse loginResponse) {
                TapLoginHelper.getInstance().onLoginSuccess(loginResponse);
                TapLoginHelperActivity.this.finish();
            }

            @Override // com.taptap.sdk.TapTapLoginCallback
            public void onCancel() {
                TapLoginHelper.getInstance().onLoginCancel();
                TapLoginHelperActivity.this.finish();
            }

            @Override // com.taptap.sdk.TapTapLoginCallback
            public void onError(Throwable th) {
                TapLoginHelper.getInstance().onLoginError(th);
                TapLoginHelperActivity.this.finish();
            }
        });
        final String[] stringArrayExtra = getIntent().getStringArrayExtra(INTENT_KEY_PERMISSION);
        final String stringExtra = getIntent().getStringExtra(INTENT_KEY_SOURCE);
        boolean z = true;
        this.isLoginRequest = getIntent().getBooleanExtra(INTENT_KEY_LOGIN_REQUEST, true);
        if (TextUtils.isEmpty(this.lastLoginRequestState)) {
            this.lastLoginRequestState = UUID.randomUUID().toString();
            z = false;
        }
        if (z) {
            android.util.Log.d("TapLoginResult", "TapLoginHelp recreate");
            Handler handler = new Handler();
            this.sendMessageHandler = handler;
            handler.postDelayed(new Runnable() { // from class: com.taptap.sdk.TapLoginHelperActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    android.util.Log.d("TapLoginResult", "TapLoginHelp recreate start launch");
                    LoginManager.getInstance().logInWithReadPermissions(TapLoginHelperActivity.this, new LoginRequest(TapLoginHelperActivity.this.lastLoginRequestState, stringArrayExtra), stringExtra, stringArrayExtra);
                }
            }, 300L);
            return;
        }
        LoginManager.getInstance().logInWithReadPermissions(this, new LoginRequest(this.lastLoginRequestState, stringArrayExtra), stringExtra, stringArrayExtra);
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (TextUtils.isEmpty(this.lastLoginRequestState)) {
            return;
        }
        bundle.putString(LOGIN_REQUEST_STATE_KEY, this.lastLoginRequestState);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        android.util.Log.d("TapLoginResult", "onActivityResult resultCode = " + i2);
        if (intent != null) {
            intent.putExtra(INTENT_KEY_LOGIN_REQUEST, this.isLoginRequest);
        }
        this.callBackManager.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        setupWindowStyle();
    }

    protected void setupWindowStyle() {
        WindowInsetsControllerCompat insetsController;
        WindowManager.LayoutParams attributes;
        Window window = getWindow();
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 28 && (attributes = window.getAttributes()) != null) {
            attributes.layoutInDisplayCutoutMode = 1;
            window.setAttributes(attributes);
        }
        window.setStatusBarColor(0);
        WindowCompat.setDecorFitsSystemWindows(window, false);
        View decorView = window.getDecorView();
        if (decorView == null || (insetsController = WindowCompat.getInsetsController(window, decorView)) == null) {
            return;
        }
        insetsController.hide(WindowInsetsCompat.Type.navigationBars());
        insetsController.setAppearanceLightStatusBars(true);
        insetsController.setSystemBarsBehavior(1);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Handler handler = this.sendMessageHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.sendMessageHandler = null;
        }
    }
}
