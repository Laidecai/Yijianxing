package com.taptap.sdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.alipay.sdk.cons.c;
import com.taptap.sdk.exceptions.ServerError;
import com.taptap.sdk.net.Api;
import com.taptap.sdk.tracker.TapTapLoginTrackerHelper;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Profile implements Parcelable {
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() { // from class: com.taptap.sdk.Profile.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Profile createFromParcel(Parcel parcel) {
            return new Profile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Profile[] newArray(int i) {
            return new Profile[i];
        }
    };
    private static final String KEY_PROFILE = "profile";
    protected static volatile Profile profile;
    private String avatar;
    private String email;
    private boolean emailVerified;
    protected int isCertified;
    private String name;
    private String openid;
    private String unionid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Profile() {
        this.isCertified = -1;
    }

    public static synchronized Profile getCurrentProfile() {
        if (profile != null) {
            return profile;
        }
        String string = TapTapSharePreference.getInstance().getSp().getString(KEY_PROFILE, "");
        if (!TextUtils.isEmpty(string)) {
            try {
                profile = fromJson(new JSONObject(string));
                return profile;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected static void save(JSONObject jSONObject) {
        TapTapSharePreference.getInstance().getSp().edit().putString(KEY_PROFILE, jSONObject.toString()).apply();
    }

    public static Profile fromJson(JSONObject jSONObject) {
        Profile profile2 = new Profile();
        profile2.avatar = jSONObject.optString("avatar");
        profile2.name = jSONObject.optString(c.e);
        profile2.openid = jSONObject.optString("openid");
        profile2.unionid = jSONObject.optString("unionid");
        profile2.email = jSONObject.optString("email");
        profile2.emailVerified = jSONObject.optBoolean("email_verified", false);
        if (jSONObject.has("is_certified")) {
            profile2.isCertified = jSONObject.optBoolean("is_certified") ? 1 : 0;
        }
        return profile2;
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(c.e, this.name);
            jSONObject.put("avatar", this.avatar);
            jSONObject.put("openid", this.openid);
            jSONObject.put("unionid", this.unionid);
            jSONObject.put("email", this.email);
            jSONObject.put("email_verified", this.emailVerified);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public static void fetchProfileForCurrentAccessToken(final Api.ApiCallback<Profile> apiCallback) {
        String strBasicInfoUrl;
        if (AccessToken.getCurrentAccessToken() != null) {
            TapTapLoginTrackerHelper.authorizationProfile();
            if (AccessToken.getCurrentAccessToken().scopeSet != null && AccessToken.getCurrentAccessToken().scopeSet.contains("public_profile")) {
                strBasicInfoUrl = TapTapSdk.regionType().profileUrl();
            } else {
                strBasicInfoUrl = TapTapSdk.regionType().basicInfoUrl();
            }
            Api.get(String.format(strBasicInfoUrl, TapTapSdk.getClientId()), null, new Api.ApiCallback<JSONObject>() { // from class: com.taptap.sdk.Profile.1
                @Override // com.taptap.sdk.net.Api.ApiCallback
                public void onSuccess(JSONObject jSONObject) {
                    if (jSONObject == null || !jSONObject.optBoolean("success")) {
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                    Profile profileFromJson = Profile.fromJson(jSONObjectOptJSONObject);
                    Profile.profile = profileFromJson;
                    Profile.save(jSONObjectOptJSONObject);
                    Api.ApiCallback apiCallback2 = apiCallback;
                    if (apiCallback2 != null) {
                        apiCallback2.onSuccess(profileFromJson);
                    }
                }

                @Override // com.taptap.sdk.net.Api.ApiCallback
                public void onError(Throwable th) {
                    if ((th instanceof ServerError) && !TextUtils.isEmpty(th.getMessage()) && th.getMessage().contains(AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED)) {
                        LoginManager.getInstance().logout();
                    }
                    Api.ApiCallback apiCallback2 = apiCallback;
                    if (apiCallback2 != null) {
                        apiCallback2.onError(th);
                    }
                }
            });
            return;
        }
        if (apiCallback != null) {
            apiCallback.onError(new RuntimeException("Login first"));
        }
        Log.DEBUG_LOG("Need login first!!");
    }

    public void clear() {
        TapTapSharePreference.getInstance().getSp().edit().putString(KEY_PROFILE, "").apply();
        profile = null;
    }

    public String toString() {
        return "\"name\":" + this.name + " \"avatar\":" + this.avatar + " \"openid\":" + this.openid + " \"unionid\":" + this.unionid + " \"email\":" + this.email + " \"email_verified\":" + this.emailVerified;
    }

    public String getName() {
        return this.name;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getOpenid() {
        return this.openid;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isEmailVerified() {
        return this.emailVerified;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.avatar);
        parcel.writeString(this.openid);
        parcel.writeString(this.unionid);
        parcel.writeInt(this.isCertified);
        parcel.writeString(this.email);
        parcel.writeInt(this.emailVerified ? 1 : 0);
    }

    protected Profile(Parcel parcel) {
        this.isCertified = -1;
        this.name = parcel.readString();
        this.avatar = parcel.readString();
        this.openid = parcel.readString();
        this.unionid = parcel.readString();
        this.isCertified = parcel.readInt();
        this.email = parcel.readString();
        this.emailVerified = parcel.readInt() != 0;
    }
}
