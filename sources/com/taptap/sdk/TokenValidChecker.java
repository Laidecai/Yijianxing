package com.taptap.sdk;

import android.os.Build;
import android.text.TextUtils;
import com.taptap.sdk.exceptions.ServerError;
import com.taptap.sdk.net.Api;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.utils.GUIDHelper;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class TokenValidChecker {
    TokenValidChecker() {
    }

    static void check(String str, String str2) {
        String string;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        HashMap map = new HashMap();
        map.put(CommonParam.CLIENT_ID, str2);
        map.put("grant_type", "refresh_token");
        map.put("token", str);
        map.put("token_type_hint", "access_token");
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(CommonParam.DEVICE_ID, Build.MANUFACTURER + " " + Build.MODEL);
            jSONObject.put("uuid", GUIDHelper.INSTANCE.getUID());
            string = jSONObject.toString();
        } catch (Exception e) {
            android.util.Log.e("TokenValidChecker", "TokenValidChecker check method:  " + e);
            string = "";
        }
        if (!TextUtils.isEmpty(string)) {
            map.put("info", string);
        }
        Api.post(TapTapSdk.regionType().tokenUrl(), map, new Api.ApiCallback<JSONObject>() { // from class: com.taptap.sdk.TokenValidChecker.1
            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onSuccess(JSONObject jSONObject2) {
                JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("data");
                if (jSONObjectOptJSONObject != null) {
                    try {
                        AccessToken.setCurrentToken(new AccessToken(jSONObjectOptJSONObject.toString()));
                    } catch (Exception e2) {
                        android.util.Log.e("TokenValidChecker", "TokenValidChecker onSuccess callback method:  " + e2);
                    }
                }
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onError(Throwable th) {
                if (th instanceof ServerError) {
                    IscTapLoginService.checkLoginError(th.getMessage(), AccountGlobalError.LOGIN_ERROR_INVALID_GRANT);
                }
            }
        });
    }
}
