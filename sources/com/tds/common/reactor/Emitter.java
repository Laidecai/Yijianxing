package com.tds.common.reactor;

/* JADX INFO: loaded from: classes.dex */
public interface Emitter<T> extends Observer<T> {

    public enum BackpressureMode {
        NONE,
        ERROR,
        BUFFER,
        DROP,
        LATEST
    }

    long requested();

    void setSubscription(Subscription subscription);
}
