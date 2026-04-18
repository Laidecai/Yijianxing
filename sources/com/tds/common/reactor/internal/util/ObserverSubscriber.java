package com.tds.common.reactor.internal.util;

import com.tds.common.reactor.Observer;
import com.tds.common.reactor.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public class ObserverSubscriber<T> extends Subscriber<T> {
    final Observer<? super T> observer;

    public ObserverSubscriber(Observer<? super T> observer) {
        this.observer = observer;
    }

    @Override // com.tds.common.reactor.Observer
    public void onNext(T t) {
        this.observer.onNext(t);
    }

    @Override // com.tds.common.reactor.Observer
    public void onError(Throwable th) {
        this.observer.onError(th);
    }

    @Override // com.tds.common.reactor.Observer
    public void onCompleted() {
        this.observer.onCompleted();
    }
}
