package com.tds.common.entities;

import android.os.Parcel;
import android.os.Parcelable;
import com.taptap.sdk.constant.LoginConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AccessToken implements Parcelable {
    public static final Parcelable.Creator<AccessToken> CREATOR = new Parcelable.Creator<AccessToken>() { // from class: com.tds.common.entities.AccessToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessToken createFromParcel(Parcel parcel) {
            return new AccessToken(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessToken[] newArray(int i) {
            return new AccessToken[i];
        }
    };
    public static final String ROOT_ELEMENT_NAME = "data";
    public final String accessToken;
    public final String expireIn;
    public final String kid;
    public final String macAlgorithm;
    public final String macKey;
    public final String originalJson;
    public final String tokenType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AccessToken(JSONObject jSONObject) {
        this.accessToken = jSONObject.optString("access_token");
        this.kid = jSONObject.optString("kid");
        this.tokenType = jSONObject.optString("token_type");
        this.macKey = jSONObject.optString("mac_key");
        this.macAlgorithm = jSONObject.optString("mac_algorithm");
        this.expireIn = jSONObject.optString("expire_in", LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
        this.originalJson = jSONObject.toString();
    }

    public AccessToken(JSONObject jSONObject, boolean z) {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
        if (jSONObjectOptJSONObject != null) {
            this.originalJson = jSONObjectOptJSONObject.toString();
            this.accessToken = jSONObjectOptJSONObject.optString("access_token");
            this.kid = jSONObjectOptJSONObject.optString("kid");
            this.tokenType = jSONObjectOptJSONObject.optString("token_type");
            this.macKey = jSONObjectOptJSONObject.optString("mac_key");
            this.macAlgorithm = jSONObjectOptJSONObject.optString("mac_algorithm");
            this.expireIn = jSONObjectOptJSONObject.optString("expire_in", LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0);
            return;
        }
        this.originalJson = "";
        this.accessToken = "";
        this.kid = "";
        this.tokenType = "";
        this.macKey = "";
        this.macAlgorithm = "";
        this.expireIn = "";
    }

    private AccessToken() {
        this.accessToken = "";
        this.kid = "";
        this.tokenType = "";
        this.macKey = "";
        this.macAlgorithm = "";
        this.expireIn = LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
        this.originalJson = "";
    }

    public AccessToken(String str) {
        this.accessToken = str;
        this.kid = "";
        this.tokenType = "";
        this.macKey = "";
        this.macAlgorithm = "";
        this.expireIn = LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
        this.originalJson = "";
    }

    public String toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("access_token", this.accessToken);
            jSONObject.put("kid", this.kid);
            jSONObject.put("token_type", this.tokenType);
            jSONObject.put("mac_key", this.macKey);
            jSONObject.put("mac_algorithm", this.macAlgorithm);
            jSONObject.put("expire_in", this.expireIn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public String toString() {
        return "AccessToken{accessToken='" + this.accessToken + "', kid='" + this.kid + "', tokenType='" + this.tokenType + "', macKey='" + this.macKey + "', macAlgorithm='" + this.macAlgorithm + "', expireIn='" + this.expireIn + "', originalJson='" + this.originalJson + "'}";
    }

    protected AccessToken(Parcel parcel) {
        this.accessToken = parcel.readString();
        this.kid = parcel.readString();
        this.tokenType = parcel.readString();
        this.macKey = parcel.readString();
        this.macAlgorithm = parcel.readString();
        this.expireIn = parcel.readString();
        this.originalJson = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.accessToken);
        parcel.writeString(this.kid);
        parcel.writeString(this.tokenType);
        parcel.writeString(this.macKey);
        parcel.writeString(this.macAlgorithm);
        parcel.writeString(this.expireIn);
        parcel.writeString(this.originalJson);
    }
}
