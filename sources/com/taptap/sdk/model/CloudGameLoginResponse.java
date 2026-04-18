package com.taptap.sdk.model;

import com.alipay.sdk.packet.e;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameLoginResponse {
    public CloudGameLoginResData data;
    public String messageId;
    public String type;

    public static CloudGameLoginResponse parseFromJSONObject(JSONObject jSONObject) {
        CloudGameLoginResponse cloudGameLoginResponse = new CloudGameLoginResponse();
        try {
            cloudGameLoginResponse.type = jSONObject.optString(e.r);
            cloudGameLoginResponse.messageId = jSONObject.optString("message_id");
            cloudGameLoginResponse.data = CloudGameLoginResData.parseFromJSONObject(jSONObject.optJSONObject("data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudGameLoginResponse;
    }
}
