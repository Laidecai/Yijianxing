package com.tds.common.bridge.result;

import com.tds.common.log.constants.CommonParam;
import com.tds.common.tracker.model.NetworkStateModel;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Result implements Serializable {
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_SUCCESS = 0;
    public String callbackId;
    public int code;
    public String content;
    public String message;
    public boolean onceTime;

    public static Result newInstance(boolean z, String str, String str2, String str3) {
        Result result = new Result();
        result.callbackId = str3;
        result.content = str;
        result.code = z ? 0 : -1;
        result.message = str2;
        return result;
    }

    public static Result newInstance(boolean z, String str, String str2, String str3, boolean z2) {
        Result result = new Result();
        result.callbackId = str3;
        result.content = str;
        result.code = z ? 0 : -1;
        result.message = str2;
        result.onceTime = z2;
        return result;
    }

    public String toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NetworkStateModel.PARAM_CODE, this.code);
            jSONObject.put(CommonParam.MESSAGE, this.message);
            jSONObject.put("content", this.content);
            jSONObject.put("callbackId", this.callbackId);
            jSONObject.put("onceTime", this.onceTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getCallbackId() {
        return this.callbackId;
    }

    public void setCallbackId(String str) {
        this.callbackId = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String toString() {
        return "Result{code=" + this.code + ", message='" + this.message + "', callbackId='" + this.callbackId + "', content='" + this.content + "', onceTime=" + this.onceTime + '}';
    }
}
