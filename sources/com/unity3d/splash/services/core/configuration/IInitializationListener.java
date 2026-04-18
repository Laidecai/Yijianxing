package com.unity3d.splash.services.core.configuration;

/* JADX INFO: loaded from: classes.dex */
public interface IInitializationListener {
    void onSdkInitializationFailed(String str, int i);

    void onSdkInitialized();
}
