package com.taptap.sdk;

import android.text.TextUtils;
import com.taptap.sdk.exceptions.ServerError;
import com.taptap.sdk.net.Api;
import com.tds.common.tracker.model.NetworkStateModel;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TestQualificationModel {
    public void getTestQualificationAsync(final Api.ApiCallback<Boolean> apiCallback) {
        if (AccessToken.getCurrentAccessToken() != null) {
            Api.get(String.format(TapTapSdk.regionType().testQualificationUrl(), TapTapSdk.getClientId()), null, new Api.ApiCallback<JSONObject>() { // from class: com.taptap.sdk.TestQualificationModel.1
                @Override // com.taptap.sdk.net.Api.ApiCallback
                public void onSuccess(JSONObject jSONObject) {
                    JSONObject jSONObjectOptJSONObject;
                    if (jSONObject != null && jSONObject.optBoolean("success") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) != null && jSONObjectOptJSONObject.has("in_test")) {
                        apiCallback.onSuccess(Boolean.valueOf(jSONObjectOptJSONObject.optBoolean("in_test")));
                    } else {
                        apiCallback.onSuccess(false);
                    }
                }

                @Override // com.taptap.sdk.net.Api.ApiCallback
                public void onError(Throwable th) {
                    JSONObject jSONObjectOptJSONObject;
                    String message = "get testQualification error";
                    if (th instanceof ServerError) {
                        if (!TextUtils.isEmpty(th.getMessage()) && th.getMessage() != null && th.getMessage().contains(AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED)) {
                            LoginManager.getInstance().logout();
                        }
                        try {
                            String message2 = th.getMessage();
                            int iOptInt = 80000;
                            if (!TextUtils.isEmpty(message2) && (jSONObjectOptJSONObject = new JSONObject(message2).optJSONObject("data")) != null) {
                                message = jSONObjectOptJSONObject.optString("msg");
                                iOptInt = jSONObjectOptJSONObject.optInt(NetworkStateModel.PARAM_CODE);
                            }
                            apiCallback.onError(new ServerError(message, iOptInt));
                            return;
                        } catch (JSONException e) {
                            message = e.getMessage();
                        }
                    }
                    apiCallback.onError(new Throwable(message));
                }
            });
        } else {
            apiCallback.onError(new RuntimeException("Login first"));
            Log.DEBUG_LOG("Need login first!!");
        }
    }
}
