package com.taptap.sdk.friends;

/* JADX INFO: loaded from: classes.dex */
public interface TapFriendsCallback<Result> {
    void onFail(Throwable th);

    void onSuccess(Result result);
}
