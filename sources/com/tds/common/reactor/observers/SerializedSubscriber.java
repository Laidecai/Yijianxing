package com.tds.common.reactor.observers;

import com.tds.common.reactor.Observer;
import com.tds.common.reactor.Subscriber;

/* JADX INFO: loaded from: classes.dex */
public class SerializedSubscriber<T> extends Subscriber<T> {
    private final Observer<T> s;

    public SerializedSubscriber(Subscriber<? super T> subscriber) {
        this(subscriber, true);
    }

    public SerializedSubscriber(Subscriber<? super T> subscriber, boolean z) {
        super(subscriber, z);
        this.s = new SerializedObserver(subscriber);
    }

    @Override // com.tds.common.reactor.Observer
    public void onCompleted() {
        this.s.onCompleted();
    }

    @Override // com.tds.common.reactor.Observer
    public void onError(Throwable th) {
        this.s.onError(th);
    }

    @Override // com.tds.common.reactor.Observer
    public void onNext(T t) {
        this.s.onNext(t);
    }
}
