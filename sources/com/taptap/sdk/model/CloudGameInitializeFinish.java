package com.taptap.sdk.model;

import com.alipay.sdk.packet.e;
import com.tds.common.net.constant.Constants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameInitializeFinish {
    public CloudGameInitializeFinishData data;
    public String messageId;
    public String type;

    public static CloudGameInitializeFinish parseFromJSONObject(JSONObject jSONObject) {
        CloudGameInitializeFinish cloudGameInitializeFinish = new CloudGameInitializeFinish();
        if (jSONObject != null) {
            try {
                cloudGameInitializeFinish.type = jSONObject.optString(e.r);
                cloudGameInitializeFinish.messageId = jSONObject.optString("message_id");
                cloudGameInitializeFinish.data = CloudGameInitializeFinishData.parseFromJSONObject(jSONObject.optJSONObject("data"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cloudGameInitializeFinish;
    }

    public static class CloudGameInitializeFinishData {
        public CloudGameInitializeFinishCGPN cgpn;
        public String xua;

        public static CloudGameInitializeFinishData parseFromJSONObject(JSONObject jSONObject) {
            CloudGameInitializeFinishData cloudGameInitializeFinishData = new CloudGameInitializeFinishData();
            if (jSONObject != null) {
                try {
                    cloudGameInitializeFinishData.xua = jSONObject.optString(Constants.HTTP_COMMON_HEADERS.XUA);
                    cloudGameInitializeFinishData.cgpn = CloudGameInitializeFinishCGPN.parseFromJSONObject(jSONObject.optJSONObject("CGPN"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return cloudGameInitializeFinishData;
        }
    }

    public static class CloudGameInitializeFinishCGPN {
        public String login;
        public String pay;

        public static CloudGameInitializeFinishCGPN parseFromJSONObject(JSONObject jSONObject) {
            CloudGameInitializeFinishCGPN cloudGameInitializeFinishCGPN = new CloudGameInitializeFinishCGPN();
            if (jSONObject != null) {
                try {
                    cloudGameInitializeFinishCGPN.login = jSONObject.optString("login");
                    cloudGameInitializeFinishCGPN.pay = jSONObject.optString("pay");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return cloudGameInitializeFinishCGPN;
        }
    }
}
