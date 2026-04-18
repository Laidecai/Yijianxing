package com.tds.common.tracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tds.common.SdkCore;
import com.tds.common.account.AccountUser;
import com.tds.common.account.LoginStatusListener;
import com.tds.common.account.LoginStatusManager;
import com.tds.common.account.TdsAccount;
import com.tds.common.net.PlatformXUA;
import com.tds.common.net.util.HostReplaceUtil;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.JsonUtil;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class SdkDurationStatistics {
    public static final String ENVIRONMENT_RELEASE = "duration_statistics_release";
    private static final String TAG = "durationStatistics";
    private static final String TAP_ANTI_SYMBOL = "com.tapsdk.antiaddictionui.AntiAddictionUIKit";
    private static final String TAP_USER_SYMBOL = "open_id";
    private static final String TDS_USER_SYMBOL = "tds_user_id";
    private static String currentUser = null;
    private static volatile boolean enableNativeDataStatistics = true;
    private static volatile boolean enableSdkDurationStatistics = true;
    private static volatile boolean hasInit = false;
    private static LoginStatusListener loginStatusListener;
    private static final List<WeakReference<Activity>> aliveActivityList = new ArrayList();
    private static final Object activityLock = new Object();
    private static volatile boolean isBackground = false;

    public static void init(Context context, String str, String str2, int i) {
        if (context == null || TextUtils.isEmpty(str) || hasInit) {
            return;
        }
        if (!enableSdkDurationStatistics) {
            Log.i(TAG, "current app disable sdk durationStatistics, just return");
            return;
        }
        try {
            loadNativeLib();
            if (!enableNativeDataStatistics) {
                Log.i(TAG, "just load lib, do not use device and user data");
                return;
            }
            initCore(context, str, str2, i);
            registerAppRunState((Application) context.getApplicationContext());
            registerLoginStatusListener();
            hasInit = true;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void updateUserInfo(Object obj) {
        if (!hasInit || obj == null) {
            return;
        }
        String accountUserString = getAccountUserString();
        currentUser = accountUserString;
        if (TextUtils.isEmpty(accountUserString)) {
            return;
        }
        SdkCore.onLogin(getAccountUserString());
        Log.i(TAG, " update userLogin : " + currentUser);
    }

    public static void notifyUserLogout() {
        if (currentUser != null) {
            Log.i(TAG, " userLogout ");
            currentUser = null;
        }
        SdkCore.onLogout();
    }

    private static boolean isSupportSwitch() {
        boolean z;
        try {
            Class.forName(TAP_ANTI_SYMBOL);
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        return true ^ z;
    }

    private static void initCore(Context context, String str, String str2, int i) {
        String str3;
        JSONObject jSONObject = new JSONObject();
        if (DeviceUtils.isRunInSandbox()) {
            Log.i(TAG, "current device running in sandbox");
            str3 = "sandbox";
        } else {
            str3 = DeviceUtils.isRunInCloud() ? "cloud" : "local";
        }
        File file = new File(context.getFilesDir(), "tapsdk");
        if (!file.exists()) {
            file.mkdirs();
        }
        boolean z = !HostReplaceUtil.getInstance().getReplacedHost(ENVIRONMENT_RELEASE).equals(ENVIRONMENT_RELEASE) || i == 2;
        try {
            jSONObject.put("env", str3);
            jSONObject.put("data_dir", file.getAbsolutePath());
            jSONObject.put("log_to_console", 1);
            if (z) {
                i = 2;
            }
            jSONObject.put("region", i);
            jSONObject.put("ua", PlatformXUA.getInstance().getTrackUA());
            jSONObject.put(CommonParam.CLIENT_ID, str);
            if (str2 == null) {
                str2 = "";
            }
            jSONObject.put("client_token", str2);
            jSONObject.put("log_level", 1);
            JSONObject jSONObject2 = new JSONObject();
            Map<String, String> commonParams = TdsTrackerHandler.getCommonParams(context, "");
            String[] strArr = {CommonParam.SDK_VERSION, CommonParam.SDK_VERSION_NAME, CommonParam.SR};
            for (int i2 = 0; i2 < 3; i2++) {
                commonParams.remove(strArr[i2]);
            }
            for (Map.Entry<String, String> entry : commonParams.entrySet()) {
                jSONObject2.put(entry.getKey(), entry.getValue());
            }
            jSONObject.put("common", jSONObject2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "init sdkCore  code = " + SdkCore.onAppStarted(jSONObject.toString()) + " data length= " + jSONObject.toString().length());
    }

    private static void loadNativeLib() {
        System.loadLibrary("tapsdkcore");
    }

    /* JADX INFO: renamed from: com.tds.common.tracker.SdkDurationStatistics$1 */
    static class AnonymousClass1 implements LoginStatusListener {
        AnonymousClass1() {
        }

        @Override // com.tds.common.account.LoginStatusListener
        public void onLoginSuccess(AccountUser accountUser) {
            SdkDurationStatistics.updateUserInfo(accountUser);
        }

        @Override // com.tds.common.account.LoginStatusListener
        public void onBindAccount(AccountUser accountUser) {
            SdkDurationStatistics.updateUserInfo(accountUser);
        }

        @Override // com.tds.common.account.LoginStatusListener
        public void onUnBindAccount(String str) {
            SdkDurationStatistics.updateUserInfo(str);
        }

        @Override // com.tds.common.account.LoginStatusListener
        public void onLogout(TdsAccount.AccountType accountType) {
            SdkDurationStatistics.notifyUserLogout();
        }
    }

    private static void registerLoginStatusListener() {
        AnonymousClass1 anonymousClass1 = new LoginStatusListener() { // from class: com.tds.common.tracker.SdkDurationStatistics.1
            AnonymousClass1() {
            }

            @Override // com.tds.common.account.LoginStatusListener
            public void onLoginSuccess(AccountUser accountUser) {
                SdkDurationStatistics.updateUserInfo(accountUser);
            }

            @Override // com.tds.common.account.LoginStatusListener
            public void onBindAccount(AccountUser accountUser) {
                SdkDurationStatistics.updateUserInfo(accountUser);
            }

            @Override // com.tds.common.account.LoginStatusListener
            public void onUnBindAccount(String str) {
                SdkDurationStatistics.updateUserInfo(str);
            }

            @Override // com.tds.common.account.LoginStatusListener
            public void onLogout(TdsAccount.AccountType accountType) {
                SdkDurationStatistics.notifyUserLogout();
            }
        };
        loginStatusListener = anonymousClass1;
        LoginStatusManager.registerLoginStatusListener(anonymousClass1);
    }

    public static void setExtraCommonMessage(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        Log.i("duration", " set durationExtra message = " + map);
        try {
            SdkCore.setExtraParams(JsonUtil.toJsonStr(map));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, " setExtraCommonMessage fail error =   " + e.getMessage());
        }
    }

    /* JADX INFO: renamed from: com.tds.common.tracker.SdkDurationStatistics$2 */
    static class AnonymousClass2 implements Application.ActivityLifecycleCallbacks {
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        AnonymousClass2() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            SdkDurationStatistics.handleAppActive(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            SdkDurationStatistics.handleAppActive(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            SdkDurationStatistics.handleAppActive(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            if (SdkDurationStatistics.checkActivityExist(activity, true) && SdkDurationStatistics.aliveActivityList.size() == 0 && !SdkDurationStatistics.isBackground) {
                Log.i(SdkDurationStatistics.TAG, " enterBackground  ");
                SdkCore.onBackground();
                boolean unused = SdkDurationStatistics.isBackground = true;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            if (SdkDurationStatistics.isBackground) {
                SdkCore.onAppStopped();
            }
        }
    }

    private static void registerAppRunState(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.tds.common.tracker.SdkDurationStatistics.2
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            AnonymousClass2() {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                SdkDurationStatistics.handleAppActive(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                SdkDurationStatistics.handleAppActive(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
                SdkDurationStatistics.handleAppActive(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                if (SdkDurationStatistics.checkActivityExist(activity, true) && SdkDurationStatistics.aliveActivityList.size() == 0 && !SdkDurationStatistics.isBackground) {
                    Log.i(SdkDurationStatistics.TAG, " enterBackground  ");
                    SdkCore.onBackground();
                    boolean unused = SdkDurationStatistics.isBackground = true;
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
                if (SdkDurationStatistics.isBackground) {
                    SdkCore.onAppStopped();
                }
            }
        });
    }

    public static void handleAppActive(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            synchronized (activityLock) {
                List<WeakReference<Activity>> list = aliveActivityList;
                if (list.size() == 0 && isBackground) {
                    Log.i(TAG, " enterForeground  ");
                    SdkCore.onForeground();
                    isBackground = false;
                }
                if (!checkActivityExist(activity, false)) {
                    list.add(new WeakReference<>(activity));
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static boolean checkActivityExist(Activity activity, boolean z) {
        try {
            synchronized (activityLock) {
                Iterator<WeakReference<Activity>> it = aliveActivityList.iterator();
                while (it.hasNext()) {
                    if (it.next().get() == activity) {
                        if (z) {
                            it.remove();
                        }
                        return true;
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private static String getAccountUserString() {
        AccountUser accountUser = LoginStatusManager.getAccountUser();
        if (accountUser == null || !accountUser.isValid()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            setUserId(jSONObject, accountUser);
            List<AccountUser> boundAccountUsers = accountUser.getBoundAccountUsers();
            if (boundAccountUsers != null && boundAccountUsers.size() > 0) {
                for (AccountUser accountUser2 : boundAccountUsers) {
                    if (accountUser2.isValid() && accountUser2.getAccountTypeString().equals(TdsAccount.AccountType.TAP.name().toLowerCase())) {
                        setUserId(jSONObject, accountUser2);
                    }
                }
            }
            return jSONObject.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static void setUserId(JSONObject jSONObject, AccountUser accountUser) {
        if (jSONObject == null || accountUser == null || !accountUser.isValid()) {
            return;
        }
        try {
            if (accountUser.getAccountType() == TdsAccount.AccountType.TAP) {
                jSONObject.put("open_id", accountUser.getUserId());
            } else if (accountUser.getAccountType() == TdsAccount.AccountType.TDS) {
                jSONObject.put("tds_user_id", accountUser.getUserId());
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void setEnableNativeDataStatistics(boolean z) {
        enableNativeDataStatistics = z;
    }

    public static void setEnableSdkDurationStatistics(boolean z) {
        if (isSupportSwitch()) {
            enableSdkDurationStatistics = z;
        }
    }
}
