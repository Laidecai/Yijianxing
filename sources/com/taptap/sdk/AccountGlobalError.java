package com.taptap.sdk;

import com.tds.common.tracker.model.NetworkStateModel;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AccountGlobalError extends Throwable {
    public static final String LOGIN_ERROR_ACCESS_DENIED = "access_denied";
    public static final String LOGIN_ERROR_INVALID_GRANT = "invalid_grant";
    public static final String LOGIN_ERROR_PERMISSION_RESULT = "permission_result";
    private int code;
    private String error;
    private String errorDescription;
    private String msg;

    public AccountGlobalError() {
        this.code = -1;
        this.msg = "";
        this.error = "";
        this.errorDescription = "";
    }

    public AccountGlobalError(int i, Throwable th) {
        this.code = i;
        this.msg = th.getMessage();
        this.error = th.getMessage();
        this.errorDescription = th.getLocalizedMessage();
    }

    public AccountGlobalError(String str, Throwable th) {
        this.code = -1;
        this.msg = th.getMessage();
        this.error = str;
        this.errorDescription = th.getLocalizedMessage();
    }

    public AccountGlobalError(String str) {
        try {
            transformFromJsonObject(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public AccountGlobalError(JSONObject jSONObject) {
        transformFromJsonObject(jSONObject);
    }

    public void transformFromJsonObject(JSONObject jSONObject) {
        this.code = jSONObject.optInt(NetworkStateModel.PARAM_CODE);
        this.msg = jSONObject.optString("msg");
        this.error = jSONObject.optString("error");
        this.errorDescription = jSONObject.optString("error_description");
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NetworkStateModel.PARAM_CODE, this.code);
            jSONObject.put("msg", this.msg);
            jSONObject.put("error", this.error);
            jSONObject.put("error_description", this.errorDescription);
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

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.msg;
    }

    public void setMessage(String str) {
        this.msg = str;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String str) {
        this.error = str;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String str) {
        this.errorDescription = str;
    }

    public static class Builder {
        private int code = -1;
        private String msg = "";
        private String error = "";
        private String errorDescription = "";

        public Builder withCode(int i) {
            this.code = i;
            return this;
        }

        public Builder withMessage(String str) {
            this.msg = str;
            return this;
        }

        public Builder withError(String str) {
            this.error = str;
            return this;
        }

        public Builder withErrorDescription(String str) {
            this.errorDescription = str;
            return this;
        }

        public AccountGlobalError build() {
            AccountGlobalError accountGlobalError = new AccountGlobalError();
            accountGlobalError.code = this.code;
            accountGlobalError.msg = this.msg;
            accountGlobalError.errorDescription = this.errorDescription;
            accountGlobalError.error = this.error;
            return accountGlobalError;
        }
    }
}
