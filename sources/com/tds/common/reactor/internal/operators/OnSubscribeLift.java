package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.plugins.RxJavaHooks;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeLift<T, R> implements Observable.OnSubscribe<R> {
    final Observable.Operator<? extends R, ? super T> operator;
    final Observable.OnSubscribe<T> parent;

    public OnSubscribeLift(Observable.OnSubscribe<T> onSubscribe, Observable.Operator<? extends R, ? super T> operator) {
        this.parent = onSubscribe;
        this.operator = operator;
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super R> subscriber) {
        try {
            Subscriber<? super T> subscriberCall = RxJavaHooks.onObservableLift(this.operator).call(subscriber);
            try {
                subscriberCall.onStart();
                this.parent.call(subscriberCall);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                subscriberCall.onError(th);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            subscriber.onError(th2);
        }
    }
}
