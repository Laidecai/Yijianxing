package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeThrow<T> implements Observable.OnSubscribe<T> {
    private final Throwable exception;

    public OnSubscribeThrow(Throwable th) {
        this.exception = th;
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        subscriber.onError(this.exception);
    }
}
