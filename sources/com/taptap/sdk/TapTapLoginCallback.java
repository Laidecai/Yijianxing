package com.taptap.sdk;

/* JADX INFO: loaded from: classes.dex */
public interface TapTapLoginCallback<RESULT> {
    void onCancel();

    void onError(Throwable th);

    void onSuccess(RESULT result);
}
