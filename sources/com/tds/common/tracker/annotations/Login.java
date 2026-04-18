package com.tds.common.tracker.annotations;

/* JADX INFO: loaded from: classes.dex */
public interface Login {
    public static final String AUTO_LOGIN_TYPE = "auto";
    public static final String CLOUD_PLAY_TYPE = "cloud_play";
    public static final String NOT_DEFINED_TYPE = "not_defined";
    public static final String TAPTAP_AUTHORIZATION_BACK_LOGIN_ACTION = "taptap_authorization_back";
    public static final String TAPTAP_AUTHORIZATION_CANCEL_LOGIN_ACTION = "taptap_authorization_cancel";
    public static final String TAPTAP_AUTHORIZATION_FAIL_LOGIN_ACTION = "taptap_authorization_fail";
    public static final String TAPTAP_AUTHORIZATION_OPEN_LOGIN_ACTION = "taptap_authorization_open";
    public static final String TAPTAP_AUTHORIZATION_PROFILE_LOGIN_ACTION = "taptap_authorization_profile";
    public static final String TAPTAP_AUTHORIZATION_START_LOGIN_ACTION = "taptap_authorization_start";
    public static final String TAPTAP_AUTHORIZATION_SUCCESS_LOGIN_ACTION = "taptap_authorization_success";
    public static final String TAPTAP_AUTHORIZATION_TOKEN_LOGIN_ACTION = "taptap_authorization_token";
    public static final String TAPTAP_LOGIN_TYPE = "taptap";
    public static final String TDS_LOGIN_CANCEL_LOGIN_ACTION = "tds_login_cancel";
    public static final String TDS_LOGIN_FAIL_LOGIN_ACTION = "tds_login_fail";
    public static final String TDS_LOGIN_START_LOGIN_ACTION = "tds_login_start";
    public static final String TDS_LOGIN_SUCCESS_LOGIN_ACTION = "tds_login_success";
    public static final String WEBVIEW_LOGIN_TYPE = "webview";

    public @interface StringLoginActionType {
    }

    public @interface StringLoginType {
    }
}
