package com.taptap.sdk;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.taptap.sdk.constant.LoginConstants;
import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.common.utils.GUIDHelper;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class LoginRequest implements Parcelable {
    public static final Parcelable.Creator<LoginRequest> CREATOR = new Parcelable.Creator<LoginRequest>() { // from class: com.taptap.sdk.LoginRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoginRequest createFromParcel(Parcel parcel) {
            return new LoginRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoginRequest[] newArray(int i) {
            return new LoginRequest[i];
        }
    };
    private String codeChallenge;
    private String codeChallengeMethod;
    private String info;
    private String loginVersion;
    private String[] permissions;
    private String phoneVerifyToken;
    private boolean preApproved;
    private String preferredLoginType;
    private String redirectUri;
    private int requestCode;
    private String responseType;
    private String state;
    private String versionCode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getState() {
        return this.state;
    }

    public String getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(String str) {
        this.versionCode = str;
    }

    public int getRequestCode() {
        if (this.requestCode == -1) {
            this.requestCode = 10;
        }
        return this.requestCode;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String str) {
        this.info = str;
    }

    public String getLoginVersion() {
        return this.loginVersion;
    }

    public void setLoginVersion(String str) {
        this.loginVersion = str;
    }

    public String[] getPermissions() {
        return this.permissions;
    }

    public void setPermissions(String... strArr) {
        this.permissions = strArr;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String str) {
        this.responseType = str;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public void setRedirectUri(String str) {
        this.redirectUri = str;
    }

    public String getCodeChallenge() {
        return this.codeChallenge;
    }

    public void setCodeChallenge(String str) {
        this.codeChallenge = str;
    }

    public String getCodeChallengeMethod() {
        return this.codeChallengeMethod;
    }

    public void setCodeChallengeMethod(String str) {
        this.codeChallengeMethod = str;
    }

    public String getPhoneVerifyToken() {
        return this.phoneVerifyToken;
    }

    public void setPhoneVerifyToken(String str) {
        this.phoneVerifyToken = str;
    }

    public String getPreferredLoginType() {
        return this.preferredLoginType;
    }

    public void setPreferredLoginType(String str) {
        this.preferredLoginType = str;
    }

    public boolean isPreApproved() {
        return this.preApproved;
    }

    public void setPreApproved(boolean z) {
        this.preApproved = z;
    }

    public LoginRequest(String... strArr) {
        this.requestCode = -1;
        this.loginVersion = LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
        this.responseType = NetworkStateModel.PARAM_CODE;
        this.preApproved = false;
        this.permissions = strArr;
        this.state = UUID.randomUUID().toString();
    }

    public LoginRequest(String str, String... strArr) {
        this.requestCode = -1;
        this.loginVersion = LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
        this.responseType = NetworkStateModel.PARAM_CODE;
        this.preApproved = false;
        this.permissions = strArr;
        this.state = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.state);
        parcel.writeStringArray(this.permissions);
        parcel.writeInt(this.requestCode);
        parcel.writeString(this.versionCode);
        parcel.writeString(this.info);
        parcel.writeString(this.loginVersion);
        parcel.writeString(this.responseType);
        parcel.writeString(this.redirectUri);
        parcel.writeString(this.codeChallenge);
        parcel.writeString(this.codeChallengeMethod);
        parcel.writeString(this.phoneVerifyToken);
        parcel.writeString(this.preferredLoginType);
        parcel.writeInt(this.preApproved ? 1 : 0);
    }

    protected LoginRequest(Parcel parcel) {
        this.requestCode = -1;
        this.loginVersion = LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
        this.responseType = NetworkStateModel.PARAM_CODE;
        this.preApproved = false;
        this.state = parcel.readString();
        this.permissions = parcel.createStringArray();
        this.requestCode = parcel.readInt();
        this.versionCode = parcel.readString();
        this.info = parcel.readString();
        this.loginVersion = parcel.readString();
        this.responseType = parcel.readString();
        this.redirectUri = parcel.readString();
        this.codeChallenge = parcel.readString();
        this.codeChallengeMethod = parcel.readString();
        this.phoneVerifyToken = parcel.readString();
        this.preferredLoginType = parcel.readString();
        this.preApproved = parcel.readInt() == 1;
    }

    public static String generateInfo(Activity activity, String str, boolean z) {
        String str2 = "horizontal";
        try {
            if (activity.getResources().getConfiguration().orientation == 1) {
                str2 = "vertical";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.equals(str, TapLoginHelper.TAG_GAME)) {
            return "client_id=" + TapTapSdk.getClientId() + "&uuid=" + GUIDHelper.INSTANCE.getUID() + "&name=TapLoginAndroid&orientation=" + str2 + "&version=3.29.0&preapproved=" + (z ? 1 : 0);
        }
        return "biz_source=" + str + "&client_id=" + TapTapSdk.getClientId() + "&uuid=" + GUIDHelper.INSTANCE.getUID() + "&name=TapLoginAndroid&orientation=" + str2 + "&version=3.29.0&preapproved=" + (z ? 1 : 0);
    }

    public static String generateSDKInfo(Activity activity, boolean z) {
        String str = "horizontal";
        try {
            if (activity.getResources().getConfiguration().orientation == 1) {
                str = "vertical";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "client_id=" + TapTapSdk.getClientId() + "&uuid=" + GUIDHelper.INSTANCE.getUID() + "&name=TapSDK&orientation=" + str + "&version=3.29.0&version_code=32900001&preapproved=" + (z ? 1 : 0);
    }
}
