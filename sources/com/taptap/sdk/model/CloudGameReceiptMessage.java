package com.taptap.sdk.model;

import com.alipay.sdk.packet.e;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameReceiptMessage {
    public CloudGameReceiptData data;
    public String messageId;
    public String type;

    public static CloudGameReceiptMessage parseFromJSONObject(JSONObject jSONObject) {
        CloudGameReceiptMessage cloudGameReceiptMessage = new CloudGameReceiptMessage();
        try {
            cloudGameReceiptMessage.type = jSONObject.optString(e.r);
            cloudGameReceiptMessage.messageId = jSONObject.optString("message_id");
            cloudGameReceiptMessage.data = CloudGameReceiptData.parseFromJSONObject(jSONObject.optJSONObject("data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloudGameReceiptMessage;
    }
}
