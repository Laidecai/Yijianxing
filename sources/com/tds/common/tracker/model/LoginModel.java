package com.tds.common.tracker.model;

import android.text.TextUtils;
import com.tds.common.tracker.exceptions.ModelConvertException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LoginModel implements BaseTrackModel {
    public static final String PARAM_LOGIN_ACTION = "login_action";
    public static final String PARAM_LOGIN_ERROR_CODE = "login_error_code";
    public static final String PARAM_LOGIN_ERROR_MSG = "login_error_msg";
    public static final String PARAM_LOGIN_SESSION_ID = "login_session_id";
    public static final String PARAM_LOGIN_TYPE = "login_type";
    private String loginSessionId = "";
    private String loginAction = "";
    private String loginType = "";
    private String loginErrorCode = "";
    private String loginErrorMsg = "";

    public LoginModel withLoginSessionId(String str) {
        this.loginSessionId = str;
        return this;
    }

    public LoginModel withLoginAction(String str) {
        this.loginAction = str;
        return this;
    }

    public LoginModel withLoginType(String str) {
        this.loginType = str;
        return this;
    }

    public LoginModel withLoginErrorCode(String str) {
        this.loginErrorCode = str;
        return this;
    }

    public LoginModel withLoginErrorMsg(String str) {
        this.loginErrorMsg = str;
        return this;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() throws Exception {
        if (TextUtils.isEmpty(this.loginSessionId)) {
            throw new ModelConvertException("login model param session_id empty");
        }
        if (TextUtils.isEmpty(this.loginAction)) {
            throw new ModelConvertException("login model param login_action empty");
        }
        HashMap map = new HashMap();
        map.put(PARAM_LOGIN_SESSION_ID, this.loginSessionId);
        map.put(PARAM_LOGIN_ACTION, this.loginAction);
        if (!TextUtils.isEmpty(this.loginType)) {
            map.put(PARAM_LOGIN_TYPE, this.loginType);
        }
        if (!TextUtils.isEmpty(this.loginErrorCode)) {
            map.put(PARAM_LOGIN_ERROR_CODE, this.loginErrorCode);
        }
        if (!TextUtils.isEmpty(this.loginErrorMsg)) {
            map.put(PARAM_LOGIN_ERROR_MSG, this.loginErrorMsg);
        }
        return map;
    }
}
