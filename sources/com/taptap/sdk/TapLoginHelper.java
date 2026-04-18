package com.taptap.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.taptap.sdk.net.Api;
import com.tds.common.TapCommon;
import com.tds.common.account.LoginStatusManager;
import com.tds.common.account.TdsAccount;
import com.tds.common.entities.TapConfig;
import com.tds.common.utils.GUIDHelper;
import com.tds.common.utils.UIUtils;
import com.tds.common.widgets.toast.TapToast;
import com.tds.common.widgets.toast.TapToastManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TapLoginHelper {
    public static final String SCOPE_BASIC_INFO = "basic_info";
    public static final String SCOPE_EMAIL = "email";
    public static final String SCOPE_PUBLIC_PROFILE = "public_profile";
    public static final String SCOPE_USER_FRIENDS = "user_friends";
    public static final String TAG_GAME = "GAME";
    private static List<String> appendPermissions;
    private WeakReference<Activity> currentLoginActivity;
    private HashMap<String, TapLoginResultCallback> loginResultCallbackList;
    private TestQualificationModel testQualificationModel;

    public interface TapLoginResultCallback {
        void onLoginCancel();

        void onLoginError(AccountGlobalError accountGlobalError);

        void onLoginSuccess(AccessToken accessToken);
    }

    private static class Holder {
        private static final TapLoginHelper INSTANCE = new TapLoginHelper();

        private Holder() {
        }
    }

    private TapLoginHelper() {
        this.testQualificationModel = new TestQualificationModel();
        this.loginResultCallbackList = new HashMap<>();
    }

    static TapLoginHelper getInstance() {
        return Holder.INSTANCE;
    }

    public static void init(Context context, String str) {
        TapTapSdk.sdkInitialize(context, str);
        initTapCommon(context, str);
    }

    public static void init(Context context, String str, LoginSdkConfig loginSdkConfig) {
        TapTapSdk.sdkInitialize(context, str, loginSdkConfig);
        initTapCommon(context, str);
    }

    private static void initTapCommon(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        if (TapCommon.getTapConfig() == null) {
            TapCommon.init(new TapConfig.Builder().withAppContext(context.getApplicationContext()).withClientId(str).build());
        }
        if (!GUIDHelper.INSTANCE.initialized()) {
            GUIDHelper.INSTANCE.init(context);
        }
        if (getCurrentProfile() != null) {
            notifyLoginStatusListener(true);
        }
    }

    public static void changeTapLoginConfig(LoginSdkConfig loginSdkConfig) {
        TapTapSdk.changeTapLoginConfig(loginSdkConfig);
    }

    public static void startTapLogin(Activity activity, String... strArr) {
        startTapLoginByTag(activity, TAG_GAME, true, strArr);
    }

    public static void startAuthorize(Activity activity, String... strArr) {
        startTapLoginByTag(activity, TAG_GAME, false, strArr);
    }

    public static void logout() {
        LoginManager.getInstance().logout();
        TapToast.getInstance().setIconUrl(null);
        notifyLoginStatusListener(false);
    }

    public static void appendPermission(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (appendPermissions == null) {
            appendPermissions = new ArrayList();
        }
        if (appendPermissions.contains(str)) {
            return;
        }
        appendPermissions.add(str);
    }

    private static void notifyLoginStatusListener(boolean z) {
        if (!z) {
            LoginStatusManager.notifyLogout(TdsAccount.AccountType.TAP);
        } else if (getCurrentProfile() != null) {
            LoginStatusManager.notifyLoginSuccess(TdsAccount.AccountType.TAP, getCurrentProfile().getOpenid());
        } else {
            fetchProfileForCurrentAccessToken(new Api.ApiCallback<Profile>() { // from class: com.taptap.sdk.TapLoginHelper.1
                @Override // com.taptap.sdk.net.Api.ApiCallback
                public void onError(Throwable th) {
                }

                @Override // com.taptap.sdk.net.Api.ApiCallback
                public void onSuccess(Profile profile) {
                    LoginStatusManager.notifyLoginSuccess(TdsAccount.AccountType.TAP, profile.getOpenid());
                }
            });
        }
    }

    public static AccessToken getCurrentAccessToken() {
        return AccessToken.getCurrentAccessToken();
    }

    public static Profile getCurrentProfile() {
        return Profile.getCurrentProfile();
    }

    public static void fetchProfileForCurrentAccessToken(Api.ApiCallback<Profile> apiCallback) {
        Profile.fetchProfileForCurrentAccessToken(apiCallback);
    }

    public static void getTestQualification(Api.ApiCallback<Boolean> apiCallback) {
        getInstance().testQualificationModel.getTestQualificationAsync(apiCallback);
    }

    public static void registerLoginCallback(TapLoginResultCallback tapLoginResultCallback) {
        addLoginResultCallbackByTag(TAG_GAME, tapLoginResultCallback);
    }

    public static TapLoginResultCallback getLoginCallback() {
        return getLoginResultCallbackByTag(TAG_GAME);
    }

    public static void unregisterLoginCallback() {
        removeLoginResultCallbackByTag(TAG_GAME);
    }

    static void startTapLoginByTag(Activity activity, String str, boolean z, String... strArr) {
        List<String> list;
        if (getInstance().loginResultCallbackList == null || getInstance().loginResultCallbackList.size() == 0) {
            android.util.Log.e("TapLoginHelper", "loginResultCallbackList has no valid item");
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        if (z) {
            boolean zContains = arrayList.contains("public_profile");
            boolean zContains2 = arrayList.contains(SCOPE_BASIC_INFO);
            if (zContains2 && zContains) {
                arrayList.remove(SCOPE_BASIC_INFO);
            } else if (!zContains2 && !zContains) {
                arrayList.add("public_profile");
            }
        }
        if (z && (list = appendPermissions) != null && list.size() > 0) {
            if (TapLoginInnerConfig.getRegionType() != RegionType.CN) {
                appendPermissions.remove("compliance");
            }
            for (String str2 : appendPermissions) {
                if (!arrayList.contains(str2)) {
                    arrayList.add(str2);
                }
            }
        }
        String[] strArr2 = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr2[i] = (String) arrayList.get(i);
        }
        Intent intent = new Intent(activity, (Class<?>) TapLoginHelperActivity.class);
        intent.putExtra(TapLoginHelperActivity.INTENT_KEY_PERMISSION, strArr2);
        intent.putExtra(TapLoginHelperActivity.INTENT_KEY_SOURCE, str);
        intent.putExtra(TapLoginHelperActivity.INTENT_KEY_LOGIN_REQUEST, z);
        activity.startActivity(intent);
        if (z) {
            getInstance().currentLoginActivity = new WeakReference<>(activity);
        }
    }

    static void addLoginResultCallbackByTag(String str, TapLoginResultCallback tapLoginResultCallback) {
        if (getInstance().loginResultCallbackList == null) {
            getInstance().loginResultCallbackList = new HashMap<>();
        }
        getInstance().loginResultCallbackList.put(str, tapLoginResultCallback);
        deleteNullCallback(getInstance().loginResultCallbackList);
    }

    static TapLoginResultCallback getLoginResultCallbackByTag(String str) {
        if (getInstance().loginResultCallbackList != null) {
            return getInstance().loginResultCallbackList.get(str);
        }
        return null;
    }

    static void removeLoginResultCallbackByTag(String str) {
        if (getInstance().loginResultCallbackList == null) {
            getInstance().loginResultCallbackList = new HashMap<>();
        }
        getInstance().loginResultCallbackList.remove(str);
        deleteNullCallback(getInstance().loginResultCallbackList);
    }

    void onLoginSuccess(LoginResponse loginResponse) {
        notifyLoginStatusListener(true);
        HashMap<String, TapLoginResultCallback> map = this.loginResultCallbackList;
        if (map != null && map.size() > 0) {
            deleteNullCallback(this.loginResultCallbackList);
            for (TapLoginResultCallback tapLoginResultCallback : this.loginResultCallbackList.values()) {
                if (tapLoginResultCallback != null) {
                    tapLoginResultCallback.onLoginSuccess(loginResponse.token);
                }
            }
        }
        WeakReference<Activity> weakReference = this.currentLoginActivity;
        if (weakReference != null && weakReference.get() != null) {
            Activity activity = this.currentLoginActivity.get();
            if (loginResponse != null) {
                String name = loginResponse.getName();
                if (!TextUtils.isEmpty(name)) {
                    TapToastManager.instance().showWithTapIcon(activity, String.format(UIUtils.getLocalizedString(activity, R.string.login_account_logged_tip), name));
                }
            }
        }
        this.currentLoginActivity = null;
    }

    void onLoginCancel() {
        this.currentLoginActivity = null;
        HashMap<String, TapLoginResultCallback> map = this.loginResultCallbackList;
        if (map == null || map.size() <= 0) {
            return;
        }
        deleteNullCallback(this.loginResultCallbackList);
        for (TapLoginResultCallback tapLoginResultCallback : this.loginResultCallbackList.values()) {
            if (tapLoginResultCallback != null) {
                tapLoginResultCallback.onLoginCancel();
            }
        }
    }

    void onLoginError(Throwable th) {
        this.currentLoginActivity = null;
        HashMap<String, TapLoginResultCallback> map = this.loginResultCallbackList;
        if (map == null || map.size() <= 0) {
            return;
        }
        deleteNullCallback(this.loginResultCallbackList);
        for (TapLoginResultCallback tapLoginResultCallback : this.loginResultCallbackList.values()) {
            if (tapLoginResultCallback != null) {
                tapLoginResultCallback.onLoginError(new AccountGlobalError(AccountGlobalError.LOGIN_ERROR_PERMISSION_RESULT, th));
            }
        }
    }

    static void deleteNullCallback(HashMap<String, TapLoginResultCallback> map) {
        if (map != null) {
            Iterator<Map.Entry<String, TapLoginResultCallback>> it = getInstance().loginResultCallbackList.entrySet().iterator();
            while (it.hasNext()) {
                if (it.next().getValue() == null) {
                    it.remove();
                }
            }
        }
    }
}
