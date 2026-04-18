package com.taptap.sdk.ui;

import android.os.Bundle;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.TapLoginInnerConfig;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LoginUtils {
    public static Bundle createLoginBundle(LoginRequest loginRequest) {
        Bundle bundle = new Bundle();
        bundle.putString(ActivityHandler.REQ_KEY_CLIENT_ID, TapLoginInnerConfig.getClientId());
        bundle.putStringArray(ActivityHandler.REQ_KEY_PERMISSIONS, loginRequest.getPermissions());
        bundle.putString(ActivityHandler.REQ_KEY_STATE, loginRequest.getState());
        bundle.putString(ActivityHandler.REQ_KEY_SDK_VERSION, loginRequest.getVersionCode());
        bundle.putBoolean(ActivityHandler.REQ_KEY_SCREEN_PORTRAIT, TapLoginInnerConfig.isPortrait);
        bundle.putString(ActivityHandler.REQ_KEY_INFO, loginRequest.getInfo());
        bundle.putString(ActivityHandler.REQ_KEY_LOGIN_VERSION, loginRequest.getLoginVersion());
        bundle.putString(ActivityHandler.REQ_KEY_RESPONSE_TYPE, loginRequest.getResponseType());
        bundle.putString(ActivityHandler.REQ_KEY_REDIRECT_URI, loginRequest.getRedirectUri());
        bundle.putString(ActivityHandler.REQ_KEY_CODE_CHALLENGE, loginRequest.getCodeChallenge());
        bundle.putString(ActivityHandler.REQ_KEY_CODE_CHALLENGE_METHOD, loginRequest.getCodeChallengeMethod());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("preapproved", loginRequest.isPreApproved() ? 1 : 0);
            bundle.putString(ActivityHandler.REQ_KEY_EXTRA, jSONObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bundle;
    }

    public static JSONObject createCloudLoginJsonObject(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            try {
                if (obj instanceof Boolean) {
                    jSONObject.put(str, (Boolean) obj);
                } else if (obj instanceof String[]) {
                    JSONArray jSONArray = new JSONArray();
                    for (String str2 : (String[]) obj) {
                        jSONArray.put(str2);
                    }
                    jSONObject.put(str, jSONArray);
                } else {
                    jSONObject.put(str, obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }
}
