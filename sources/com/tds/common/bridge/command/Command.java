package com.tds.common.bridge.command;

import androidx.core.app.NotificationCompat;
import com.alipay.sdk.packet.e;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Command implements Serializable {
    public String args;
    public boolean callback;
    public String callbackId;
    public String method;
    public boolean onceTime;
    public String service;

    public Command() {
    }

    public String getService() {
        return this.service;
    }

    public void setService(String str) {
        this.service = str;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public String getArgs() {
        return this.args;
    }

    public void setArgs(String str) {
        this.args = str;
    }

    public boolean isCallback() {
        return this.callback;
    }

    public void setCallback(boolean z) {
        this.callback = z;
    }

    public String getCallbackId() {
        return this.callbackId;
    }

    public void setCallbackId(String str) {
        this.callbackId = str;
    }

    public Command(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.service = jSONObject.optString(NotificationCompat.CATEGORY_SERVICE);
            this.method = jSONObject.optString(e.s);
            this.callback = jSONObject.optBoolean("callback");
            this.callbackId = jSONObject.optString("callbackId");
            this.args = jSONObject.optString("args");
            this.onceTime = jSONObject.optBoolean("onceTime");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NotificationCompat.CATEGORY_SERVICE, this.service);
            jSONObject.put(e.s, this.method);
            jSONObject.put("args", this.args);
            jSONObject.put("callback", this.callback);
            jSONObject.put("callbackId", this.callbackId);
            jSONObject.put("onceTime", this.onceTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
