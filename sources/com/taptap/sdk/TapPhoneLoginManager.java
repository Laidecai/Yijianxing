package com.taptap.sdk;

/* JADX INFO: loaded from: classes.dex */
public class TapPhoneLoginManager {
    private ClientLoginCallback clientLoginCallback;
    private ClientLoginCallback2 clientLoginCallback2;
    private PhoneLoginCallback phoneLoginCallback;
    private PhoneLoginCallback2 phoneLoginCallback2;

    @Deprecated
    public interface ClientLoginCallback {
        void clientLoginFail();
    }

    public interface ClientLoginCallback2 {
        void clientLoginFail(String[] strArr);
    }

    @Deprecated
    public interface PhoneLoginCallback {
        void cancelLogin();

        void continueWebLogin(String str, String str2);
    }

    public interface PhoneLoginCallback2 {
        void cancelLogin();

        void continueWebLogin(String str, String str2, String... strArr);
    }

    public static TapPhoneLoginManager getInstance() {
        return Single.INSTANCE.tapPhoneLoginManager;
    }

    @Deprecated
    public ClientLoginCallback getClientLoginCallback() {
        return this.clientLoginCallback;
    }

    @Deprecated
    public void registerClientLoginCallback(ClientLoginCallback clientLoginCallback) {
        this.clientLoginCallback = clientLoginCallback;
    }

    public ClientLoginCallback2 getClientLoginCallback2() {
        return this.clientLoginCallback2;
    }

    public void registerClientLoginCallback2(ClientLoginCallback2 clientLoginCallback2) {
        this.clientLoginCallback2 = clientLoginCallback2;
    }

    public void removeClientLoginCallback() {
        this.clientLoginCallback = null;
        this.clientLoginCallback2 = null;
    }

    @Deprecated
    public PhoneLoginCallback getPhoneLoginCallback() {
        return this.phoneLoginCallback;
    }

    @Deprecated
    public void registerPhoneLoginCallback(PhoneLoginCallback phoneLoginCallback) {
        this.phoneLoginCallback = phoneLoginCallback;
    }

    public PhoneLoginCallback2 getPhoneLoginCallback2() {
        return this.phoneLoginCallback2;
    }

    public void registerPhoneLoginCallback2(PhoneLoginCallback2 phoneLoginCallback2) {
        this.phoneLoginCallback2 = phoneLoginCallback2;
    }

    public void removePhoneLoginCallback() {
        this.phoneLoginCallback = null;
        this.phoneLoginCallback2 = null;
    }

    enum Single {
        INSTANCE;

        final TapPhoneLoginManager tapPhoneLoginManager = new TapPhoneLoginManager();

        Single() {
        }
    }
}
