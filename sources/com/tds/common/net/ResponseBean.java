package com.tds.common.net;

import com.tds.common.net.json.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ResponseBean<T> {
    public final T data;
    public final boolean success;

    ResponseBean(JSONObject jSONObject, Class<T> cls) throws JSONException {
        this.success = jSONObject.optBoolean("success");
        if (jSONObject.has("data")) {
            this.data = (T) JsonUtil.parse(jSONObject.getJSONObject("data"), cls);
        } else {
            this.data = null;
        }
    }
}
