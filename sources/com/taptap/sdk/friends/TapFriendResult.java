package com.taptap.sdk.friends;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapFriendResult {
    private String cursor;
    private final List<TapFriendInfo> data;

    public TapFriendResult(List<TapFriendInfo> list, String str) {
        this.cursor = null;
        this.data = list;
        this.cursor = str;
    }

    public List<TapFriendInfo> getData() {
        return this.data;
    }

    public String getCursor() {
        return this.cursor;
    }

    public static TapFriendResult parseData(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
        JSONArray jSONArray = jSONObject2.getJSONArray("list");
        String string = jSONObject2.getBoolean("is_truncated") ? jSONObject2.getString("next_continuation_token") : null;
        if (jSONArray.length() == 0) {
            return new TapFriendResult(Collections.emptyList(), string);
        }
        ArrayList arrayList = new ArrayList();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject3 = jSONArray.getJSONObject(i);
            arrayList.add(new TapFriendInfo(jSONObject3.getString("openid"), jSONObject3.getString("nickname"), jSONObject3.getString("avatar")));
        }
        return new TapFriendResult(arrayList, string);
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cursor", this.cursor);
            if (this.data != null) {
                JSONArray jSONArray = new JSONArray();
                for (TapFriendInfo tapFriendInfo : this.data) {
                    if (tapFriendInfo != null) {
                        jSONArray.put(tapFriendInfo.toJson());
                    }
                }
                jSONObject.put("data", jSONArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public String toString() {
        return "TapFriendResult{data=" + this.data + ", cursor='" + this.cursor + "'}";
    }
}
