package com.taptap.sdk;

import android.os.Build;
import com.taptap.sdk.net.Api;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.tracker.model.NetworkStateModel;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapLoginWithCode {

    public interface LoginResultCallBack {
        void onLoginResult(LoginResponse loginResponse);
    }

    public static void loginWithCode(String str, String str2, final String str3, final String str4, final LoginResultCallBack loginResultCallBack) {
        HashMap map = new HashMap();
        map.put(CommonParam.CLIENT_ID, TapLoginInnerConfig.getClientId());
        map.put("grant_type", "authorization_code");
        map.put("secret_type", "hmac-sha-1");
        map.put(NetworkStateModel.PARAM_CODE, str);
        map.put("redirect_uri", "tapoauth://authorize");
        map.put("code_verifier", str2);
        map.put(CommonParam.VERSION, "3.29.0");
        map.put("platform", "android");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(CommonParam.DEVICE_ID, Build.MANUFACTURER + " " + Build.MODEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("info", jSONObject.toString());
        Api.post(TapLoginInnerConfig.getRegionType().tokenUrl(), map, new Api.ApiCallback<JSONObject>() { // from class: com.taptap.sdk.TapLoginWithCode.1
            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onSuccess(JSONObject jSONObject2) {
                boolean zOptBoolean = jSONObject2.optBoolean("success");
                JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("data");
                if (zOptBoolean) {
                    LoginResponse loginResponse = new LoginResponse(null, str3, null, new AccessToken(jSONObjectOptJSONObject.optString("kid"), jSONObjectOptJSONObject.optString("access_token"), jSONObjectOptJSONObject.optString("token_type"), jSONObjectOptJSONObject.optString("mac_key"), jSONObjectOptJSONObject.optString("mac_algorithm"), jSONObjectOptJSONObject.optString("scope"), jSONObjectOptJSONObject.toString()), false, str4);
                    LoginResultCallBack loginResultCallBack2 = loginResultCallBack;
                    if (loginResultCallBack2 != null) {
                        loginResultCallBack2.onLoginResult(loginResponse);
                        return;
                    }
                    return;
                }
                LoginResponse loginResponse2 = new LoginResponse(null, str3, jSONObjectOptJSONObject.optString("error_description"), null, false, str4);
                LoginResultCallBack loginResultCallBack3 = loginResultCallBack;
                if (loginResultCallBack3 != null) {
                    loginResultCallBack3.onLoginResult(loginResponse2);
                }
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onError(Throwable th) {
                LoginResponse loginResponse = new LoginResponse(null, str3, th.getMessage(), null, false);
                LoginResultCallBack loginResultCallBack2 = loginResultCallBack;
                if (loginResultCallBack2 != null) {
                    loginResultCallBack2.onLoginResult(loginResponse);
                }
            }
        });
    }
}
