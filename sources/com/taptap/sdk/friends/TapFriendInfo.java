package com.taptap.sdk.friends;

import com.alipay.sdk.cons.c;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapFriendInfo {
    private String avatar;
    private String name;
    private String openid;

    public TapFriendInfo(String str, String str2, String str3) {
        this.name = str2;
        this.avatar = str3;
        this.openid = str;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getName() {
        return this.name;
    }

    public String getOpenid() {
        return this.openid;
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(c.e, this.name);
            jSONObject.put("avatar", this.avatar);
            jSONObject.put("openid", this.openid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public String toString() {
        return "TapFriendInfo{name='" + this.name + "', avatar='" + this.avatar + "', openid='" + this.openid + "'}";
    }
}
