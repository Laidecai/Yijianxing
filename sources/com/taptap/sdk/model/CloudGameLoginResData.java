package com.taptap.sdk.model;

import com.tds.common.tracker.model.NetworkStateModel;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameLoginResData {
    public boolean cancel;
    public String code;
    public String errorMessage;
    public String serverUri;
    public String state;

    public static CloudGameLoginResData parseFromJSONObject(JSONObject jSONObject) {
        CloudGameLoginResData cloudGameLoginResData = new CloudGameLoginResData();
        try {
            cloudGameLoginResData.code = jSONObject.optString(NetworkStateModel.PARAM_CODE);
            cloudGameLoginResData.state = jSONObject.optString("state");
            cloudGameLoginResData.cancel = jSONObject.optBoolean("cancel");
            cloudGameLoginResData.errorMessage = jSONObject.optString("error");
            cloudGameLoginResData.serverUri = jSONObject.optString("server_uri");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudGameLoginResData;
    }
}
