package com.taptap.sdk.ui;

import android.content.Intent;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.TapLoginInnerConfig;
import com.taptap.sdk.tracker.TapTapLoginTrackerHelper;
import com.tds.common.tracker.annotations.Login;

/* JADX INFO: loaded from: classes.dex */
class ActivityHandler extends BaseHandler {
    public static final String KEY_REQUEST = "com.taptap.sdk.request";
    public static final String REQ_KEY_CLIENT_ID = "com.taptap.sdk.request.client_id";
    public static final String REQ_KEY_CODE_CHALLENGE = "com.taptap.sdk.request.code_challenge";
    public static final String REQ_KEY_CODE_CHALLENGE_METHOD = "com.taptap.sdk.request.code_challenge_method";
    public static final String REQ_KEY_EXTRA = "com.taptap.sdk.request.extra";
    public static final String REQ_KEY_INFO = "com.taptap.sdk.request.info";
    public static final String REQ_KEY_LOGIN_VERSION = "com.taptap.sdk.request.login_version";
    public static final String REQ_KEY_PERMISSIONS = "com.taptap.sdk.request.permissions";
    public static final String REQ_KEY_REDIRECT_URI = "com.taptap.sdk.request.redirect_uri";
    public static final String REQ_KEY_RESPONSE_TYPE = "com.taptap.sdk.request.response_type";
    public static final String REQ_KEY_SCREEN_PORTRAIT = "com.taptap.sdk.request.screen.portrait";
    public static final String REQ_KEY_SDK_VERSION = "com.taptap.sdk.request.sdk_version";
    public static final String REQ_KEY_STATE = "com.taptap.sdk.request.state";
    private ActivityDelegate activityDelegate;

    public ActivityHandler(ActivityDelegate activityDelegate) {
        this.activityDelegate = activityDelegate;
    }

    @Override // com.taptap.sdk.ui.BaseHandler
    public void authorize(LoginRequest loginRequest) throws Exception {
        Intent intent = toIntent(loginRequest);
        intent.setAction(TapLoginInnerConfig.getRegionType().targetActionName());
        TapTapLoginTrackerHelper.authorizationOpen(Login.TAPTAP_LOGIN_TYPE);
        this.activityDelegate.startActivityForResult(intent, loginRequest.getRequestCode());
    }

    public Intent toIntent(LoginRequest loginRequest) {
        Intent intent = new Intent();
        intent.putExtra(KEY_REQUEST, LoginUtils.createLoginBundle(loginRequest));
        return intent;
    }
}
