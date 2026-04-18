package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Producer;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.exceptions.OnErrorThrowable;
import com.tds.common.reactor.functions.Func1;
import com.tds.common.reactor.plugins.RxJavaHooks;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeMap<T, R> implements Observable.OnSubscribe<R> {
    final Observable<T> source;
    final Func1<? super T, ? extends R> transformer;

    public OnSubscribeMap(Observable<T> observable, Func1<? super T, ? extends R> func1) {
        this.source = observable;
        this.transformer = func1;
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super R> subscriber) {
        MapSubscriber mapSubscriber = new MapSubscriber(subscriber, this.transformer);
        subscriber.add(mapSubscriber);
        this.source.unsafeSubscribe(mapSubscriber);
    }

    static final class MapSubscriber<T, R> extends Subscriber<T> {
        final Subscriber<? super R> actual;
        boolean done;
        final Func1<? super T, ? extends R> mapper;

        public MapSubscriber(Subscriber<? super R> subscriber, Func1<? super T, ? extends R> func1) {
            this.actual = subscriber;
            this.mapper = func1;
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            try {
                this.actual.onNext(this.mapper.call(t));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                unsubscribe();
                onError(OnErrorThrowable.addValueAsLastCause(th, t));
            }
        }

        @Override // com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaHooks.onError(th);
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // com.tds.common.reactor.Observer
        public void onCompleted() {
            if (this.done) {
                return;
            }
            this.actual.onCompleted();
        }

        @Override // com.tds.common.reactor.Subscriber
        public void setProducer(Producer producer) {
            this.actual.setProducer(producer);
        }
    }
}
