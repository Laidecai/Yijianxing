package com.tds.common;

/* JADX INFO: loaded from: classes.dex */
public class SdkCore {
    public static native int onAppStarted(String str);

    public static native void onAppStopped();

    public static native void onBackground();

    public static native void onForeground();

    public static native void onLogin(String str);

    public static native void onLogout();

    public static native void setExtraParams(String str);
}
