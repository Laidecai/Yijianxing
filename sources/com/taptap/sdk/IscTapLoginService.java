package com.taptap.sdk;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.taptap.sdk.TapLoginHelper;
import com.taptap.sdk.net.Api;
import com.tds.common.isc.IscMethod;
import com.tds.common.isc.IscService;
import com.tds.common.net.TDSNetInterceptor;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.rxandroid.schedulers.AndroidSchedulers;

/* JADX INFO: loaded from: classes.dex */
@IscService("TapLogin")
public class IscTapLoginService {
    public loginInteracpter loginInteracpter;

    public interface loginInteracpter {
        void userChanged(String str);
    }

    @IscMethod("login")
    public static Observable<Intent> login(final Activity activity, final String str, final String... strArr) {
        return Observable.create(new Observable.OnSubscribe<Intent>() { // from class: com.taptap.sdk.IscTapLoginService.1
            @Override // com.tds.common.reactor.functions.Action1
            public void call(final Subscriber<? super Intent> subscriber) {
                if (TextUtils.isEmpty(str)) {
                    android.util.Log.e("IscTapLoginService", "sdkName must not empty");
                } else {
                    TapLoginHelper.addLoginResultCallbackByTag(str, new TapLoginHelper.TapLoginResultCallback() { // from class: com.taptap.sdk.IscTapLoginService.1.1
                        @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
                        public void onLoginSuccess(AccessToken accessToken) {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }
                            Intent intent = new Intent();
                            intent.putExtra("token", accessToken == null ? null : accessToken.toJsonString());
                            subscriber.onNext(intent);
                            subscriber.onCompleted();
                            TapLoginHelper.removeLoginResultCallbackByTag(str);
                        }

                        @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
                        public void onLoginCancel() {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }
                            subscriber.onError(new Throwable("Login cancel"));
                            subscriber.onCompleted();
                            TapLoginHelper.removeLoginResultCallbackByTag(str);
                        }

                        @Override // com.taptap.sdk.TapLoginHelper.TapLoginResultCallback
                        public void onLoginError(AccountGlobalError accountGlobalError) {
                            if (subscriber.isUnsubscribed()) {
                                return;
                            }
                            subscriber.onError(accountGlobalError);
                            subscriber.onCompleted();
                            TapLoginHelper.removeLoginResultCallbackByTag(str);
                        }
                    });
                    TapLoginHelper.startTapLoginByTag(activity, str, true, strArr);
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
    }

    @IscMethod("currentAccessToken")
    public static String getCurrentAccessToken() {
        if (TapLoginHelper.getCurrentAccessToken() != null) {
            return TapLoginHelper.getCurrentAccessToken().toJsonString();
        }
        return null;
    }

    @IscMethod("currentProfile")
    public static String getCurrentProfile() {
        if (TapLoginHelper.getCurrentProfile() != null) {
            return TapLoginHelper.getCurrentProfile().toJsonString();
        }
        return null;
    }

    @IscMethod("getTestQualification")
    public static Observable<Boolean> getTestQualificationAsync() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() { // from class: com.taptap.sdk.IscTapLoginService.2
            @Override // com.tds.common.reactor.functions.Action1
            public void call(final Subscriber<? super Boolean> subscriber) {
                TapLoginHelper.getTestQualification(new Api.ApiCallback<Boolean>() { // from class: com.taptap.sdk.IscTapLoginService.2.1
                    @Override // com.taptap.sdk.net.Api.ApiCallback
                    public void onSuccess(Boolean bool) {
                        subscriber.onNext(bool);
                        subscriber.onCompleted();
                    }

                    @Override // com.taptap.sdk.net.Api.ApiCallback
                    public void onError(Throwable th) {
                        subscriber.onError(th);
                    }
                });
            }
        });
    }

    @IscMethod("setCurrentAccessToken")
    public static void setCurrentAccessToken(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            AccessToken.setCurrentToken(new AccessToken(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @IscMethod("handleLoginError")
    public static void handleLoginError(String str) {
        checkLoginError(str, AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED);
    }

    public static void addSDKLoginCallback(String str, TapLoginHelper.TapLoginResultCallback tapLoginResultCallback) {
        TapLoginHelper.addLoginResultCallbackByTag(str, tapLoginResultCallback);
    }

    public static void removeSDKLoginCallback(String str) {
        TapLoginHelper.removeLoginResultCallbackByTag(str);
    }

    public static void startSDKLogin(Activity activity, String str, String... strArr) {
        TapLoginHelper.startTapLoginByTag(activity, str, true, strArr);
    }

    public static void checkLoginError(String str, String... strArr) {
        if (TapLoginHelper.getCurrentAccessToken() == null) {
            return;
        }
        TDSNetInterceptor.checkAuthError(str, new TDSNetInterceptor.CheckAuthCallback() { // from class: com.taptap.sdk.IscTapLoginService.3
            @Override // com.tds.common.net.TDSNetInterceptor.CheckAuthCallback
            public void onAuthError(String str2) {
                TapLoginHelper.logout();
                if (TapLoginHelper.getLoginCallback() != null) {
                    TapLoginHelper.getLoginCallback().onLoginError(new AccountGlobalError(str2));
                }
            }
        }, strArr);
    }

    public static void changeConfig(String str, RegionType regionType, AccessToken accessToken) {
        TapLoginInnerConfig.setClientId(str);
        TapLoginInnerConfig.setRegionType(regionType);
        if (accessToken != null) {
            AccessToken.setCurrentToken(accessToken);
        }
    }
}
