package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public enum EmptyObservableHolder implements Observable.OnSubscribe<Object> {
    INSTANCE;

    static final Observable<Object> EMPTY = Observable.unsafeCreate(INSTANCE);

    public static <T> Observable<T> instance() {
        return (Observable<T>) EMPTY;
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super Object> subscriber) {
        subscriber.onCompleted();
    }
}
