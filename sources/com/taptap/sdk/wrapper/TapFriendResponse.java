package com.taptap.sdk.wrapper;

import com.tds.common.tracker.model.NetworkStateModel;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapFriendResponse {
    public int code;
    public String content;
    public boolean success;

    public TapFriendResponse(boolean z, String str) {
        this.success = z;
        this.content = str;
    }

    public TapFriendResponse(boolean z, String str, int i) {
        this.success = z;
        this.content = str;
        this.code = i;
    }

    public String toJson() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(NetworkStateModel.PARAM_CODE, this.code);
            jSONObject.put("content", this.content);
            jSONObject.put("success", this.success);
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
