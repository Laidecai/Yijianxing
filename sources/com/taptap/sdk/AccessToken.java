package com.taptap.sdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.taptap.sdk.tracker.TapTapLoginTrackerHelper;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AccessToken implements Parcelable {
    public static final Parcelable.Creator<AccessToken> CREATOR = new Parcelable.Creator<AccessToken>() { // from class: com.taptap.sdk.AccessToken.1
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
    private static final String SP_KEY = "token_data";
    private static volatile AccessToken cacheToken;
    public String access_token;
    private String json;
    public String kid;
    public String mac_algorithm;
    public String mac_key;
    public Set<String> scopeSet;
    public String token_type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    String getJson() {
        return this.json;
    }

    public AccessToken() {
        this.json = null;
    }

    public AccessToken(String str, String str2, String str3, String str4, String str5, String str6) {
        this.json = null;
        this.json = str6;
        this.kid = str;
        this.access_token = str2;
        this.token_type = str3;
        this.mac_key = str4;
        this.mac_algorithm = str5;
        try {
            this.scopeSet = convertScopeStringToSet(new JSONObject(str6).optString("scope"));
        } catch (Exception unused) {
            this.scopeSet = new HashSet();
        }
    }

    public AccessToken(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.json = null;
        this.json = str7;
        this.kid = str;
        this.access_token = str2;
        this.token_type = str3;
        this.mac_key = str4;
        this.mac_algorithm = str5;
        this.scopeSet = convertScopeStringToSet(str6);
    }

    public AccessToken(String str) throws JSONException {
        this.json = null;
        this.json = str;
        JSONObject jSONObject = new JSONObject(str);
        this.kid = jSONObject.optString("kid");
        this.access_token = jSONObject.optString("access_token");
        this.token_type = jSONObject.optString("token_type");
        this.mac_key = jSONObject.optString("mac_key");
        this.mac_algorithm = jSONObject.optString("mac_algorithm");
        this.scopeSet = convertScopeStringToSet(jSONObject.optString("scope"));
    }

    protected AccessToken(Parcel parcel) {
        this.json = null;
        this.kid = parcel.readString();
        this.access_token = parcel.readString();
        this.token_type = parcel.readString();
        this.mac_key = parcel.readString();
        this.mac_algorithm = parcel.readString();
        this.scopeSet = convertScopeStringToSet(parcel.readString());
    }

    @Deprecated
    public static AccessToken build(String str, String str2, String str3, String str4, String str5) {
        AccessToken accessToken = new AccessToken();
        accessToken.kid = str;
        accessToken.access_token = str2;
        accessToken.token_type = str3;
        accessToken.mac_key = str4;
        accessToken.mac_algorithm = str5;
        return accessToken;
    }

    public static AccessToken build(String str) throws JSONException {
        AccessToken accessToken = new AccessToken();
        JSONObject jSONObject = new JSONObject(str);
        accessToken.kid = jSONObject.optString("kid");
        accessToken.access_token = jSONObject.optString("access_token");
        accessToken.token_type = jSONObject.optString("token_type");
        accessToken.mac_key = jSONObject.optString("mac_key");
        accessToken.mac_algorithm = jSONObject.optString("mac_algorithm");
        accessToken.scopeSet = convertScopeStringToSet(jSONObject.optString("scope"));
        return accessToken;
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("kid", this.kid);
            jSONObject.put("access_token", this.access_token);
            jSONObject.put("token_type", this.token_type);
            jSONObject.put("mac_key", this.mac_key);
            jSONObject.put("mac_algorithm", this.mac_algorithm);
            jSONObject.put("scope", convertSetToString(this.scopeSet));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static Set<String> convertScopeStringToSet(String str) {
        HashSet hashSet = new HashSet();
        if (TextUtils.isEmpty(str)) {
            return hashSet;
        }
        try {
            return new HashSet(Arrays.asList(str.split("\\s+")));
        } catch (Exception unused) {
            return hashSet;
        }
    }

    public static String convertSetToString(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        if (set != null && !set.isEmpty()) {
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static synchronized AccessToken getCurrentAccessToken() {
        Validate.sdkHasInitialized();
        if (cacheToken != null) {
            return cacheToken;
        }
        String string = TapTapSharePreference.getInstance().getSp().getString(SP_KEY, null);
        try {
            if (!TextUtils.isEmpty(string)) {
                cacheToken = new AccessToken(string);
            }
            return cacheToken;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void setCurrentToken(AccessToken accessToken) {
        cacheToken = accessToken;
        if (accessToken == null) {
            clear();
        } else {
            TapTapLoginTrackerHelper.authorizationToken();
            cacheToken.save();
        }
    }

    void save() {
        Validate.sdkHasInitialized();
        if (TextUtils.isEmpty(this.json)) {
            return;
        }
        TapTapSharePreference.getInstance().getSp().edit().putString(SP_KEY, this.json).apply();
    }

    static void clear() {
        Validate.sdkHasInitialized();
        TapTapSharePreference.getInstance().getSp().edit().putString(SP_KEY, "").apply();
        cacheToken = null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.kid);
        parcel.writeString(this.access_token);
        parcel.writeString(this.token_type);
        parcel.writeString(this.mac_key);
        parcel.writeString(this.mac_algorithm);
        parcel.writeString(convertSetToString(this.scopeSet));
    }

    public String toString() {
        return "\"kid\"=" + this.kid + " \"access_token\"=" + this.access_token + " \"token_type\"=" + this.token_type + " \"mac_key\"=" + this.mac_key + " \"mac_algorithm\"=" + this.mac_algorithm + " \"scope\"=" + convertSetToString(this.scopeSet) + " ";
    }
}
