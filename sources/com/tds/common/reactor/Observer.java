package com.tds.common.reactor;

/* JADX INFO: loaded from: classes.dex */
public interface Observer<T> {
    void onCompleted();

    void onError(Throwable th);

    void onNext(T t);
}
