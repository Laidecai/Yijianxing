package com.tds.tapdb;

/* JADX INFO: loaded from: classes.dex */
public interface Callback<T> {
    void onFail(Throwable th);

    void onSuccess(T t);
}
