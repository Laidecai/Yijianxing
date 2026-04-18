package com.taptap.sdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.cons.c;
import com.taptap.sdk.constant.LoginConstants;
import java.util.Arrays;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class LoginResponse {
    public boolean cancel;
    public String code;
    public String errorMessage;
    public String loginVersion;
    private String name;
    public String[] permissions;
    public String state;
    public AccessToken token;

    public LoginResponse(String[] strArr, String str, String str2, AccessToken accessToken, boolean z) {
        this.permissions = strArr;
        this.state = str;
        this.errorMessage = str2;
        this.token = accessToken;
        this.cancel = z;
    }

    public LoginResponse(String[] strArr, String str, String str2, AccessToken accessToken, boolean z, String str3) {
        this(strArr, str, str2, accessToken, z);
        this.name = str3;
    }

    public String getName() {
        return this.name;
    }

    public Intent toIntent(String str) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtra(LoginManager.KEY_RESPONSE, bundle);
        bundle.putBoolean(LoginManager.RES_KEY_CANCEL, this.cancel);
        AccessToken accessToken = this.token;
        bundle.putString(LoginManager.RES_KEY_TOKEN, accessToken == null ? null : accessToken.getJson());
        bundle.putParcelable(LoginManager.RES_KEY_TOKEN_PARCELABLE, this.token);
        bundle.putString(LoginManager.RES_KEY_ERROR, this.errorMessage);
        bundle.putString(LoginManager.RES_KEY_STATE, this.state);
        bundle.putString(LoginManager.RES_KEY_CODE, this.code);
        String str2 = this.loginVersion;
        if (str2 != null) {
            bundle.putString(LoginManager.RES_KEY_LOGIN_VERSION, str2);
        }
        if (!TextUtils.isEmpty(str)) {
            bundle.putString(LoginManager.RES_KEY_SERVER_URI, str);
        }
        bundle.putStringArray(LoginManager.RES_KEY_PERMISSIONS, this.permissions);
        return intent;
    }

    private LoginResponse() {
    }

    public static LoginResponse getResultFromIntent(Intent intent) throws JSONException {
        LoginResponse loginResponse = new LoginResponse();
        Bundle bundleExtra = intent.getBundleExtra(LoginManager.KEY_RESPONSE);
        loginResponse.cancel = bundleExtra.getBoolean(LoginManager.RES_KEY_CANCEL, false);
        String string = bundleExtra.getString(LoginManager.RES_KEY_TOKEN);
        if (!TextUtils.isEmpty(string)) {
            loginResponse.token = new AccessToken(string);
        } else {
            loginResponse.token = (AccessToken) bundleExtra.getParcelable(LoginManager.RES_KEY_TOKEN_PARCELABLE);
        }
        loginResponse.errorMessage = bundleExtra.getString(LoginManager.RES_KEY_ERROR);
        loginResponse.state = bundleExtra.getString(LoginManager.RES_KEY_STATE);
        loginResponse.permissions = bundleExtra.getStringArray(LoginManager.RES_KEY_PERMISSIONS);
        loginResponse.code = bundleExtra.getString(LoginManager.RES_KEY_CODE);
        loginResponse.loginVersion = bundleExtra.getString(LoginManager.RES_KEY_LOGIN_VERSION, LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
        loginResponse.name = getNameFromServerUri(bundleExtra.getString(LoginManager.RES_KEY_SERVER_URI));
        return loginResponse;
    }

    private static String getNameFromServerUri(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Uri uri = Uri.parse(str);
            if (uri != null) {
                return uri.getQueryParameter(c.e);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.permissions != null) {
            sb.append("\"permissions\"=");
            sb.append(Arrays.toString(this.permissions));
            sb.append(" ");
        }
        sb.append("\"state\"=");
        sb.append(this.state);
        sb.append(" ");
        sb.append("\"errorMessage\"=");
        sb.append(this.errorMessage);
        sb.append(" ");
        sb.append("\"token\"=");
        sb.append(this.token);
        sb.append(" ");
        sb.append("\"cancel\"=");
        sb.append(this.cancel);
        sb.append(" ");
        sb.append("\"code\"=");
        sb.append(this.code);
        sb.append(" ");
        sb.append("\"name\"=");
        sb.append(this.name);
        sb.append(" ");
        return sb.toString();
    }
}
