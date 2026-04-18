package com.tds.common.reactor.internal.observers;

import com.tds.common.reactor.Observer;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.OnErrorNotImplementedException;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public final class Subscribers {
    private Subscribers() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Subscriber<T> empty() {
        return from(Observers.empty());
    }

    public static <T> Subscriber<T> from(final Observer<? super T> observer) {
        return new Subscriber<T>() { // from class: com.tds.common.reactor.internal.observers.Subscribers.1
            @Override // com.tds.common.reactor.Observer
            public void onCompleted() {
                observer.onCompleted();
            }

            @Override // com.tds.common.reactor.Observer
            public void onError(Throwable th) {
                observer.onError(th);
            }

            @Override // com.tds.common.reactor.Observer
            public void onNext(T t) {
                observer.onNext(t);
            }
        };
    }

    public static <T> Subscriber<T> create(final Action1<? super T> action1) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        return new Subscriber<T>() { // from class: com.tds.common.reactor.internal.observers.Subscribers.2
            @Override // com.tds.common.reactor.Observer
            public final void onCompleted() {
            }

            @Override // com.tds.common.reactor.Observer
            public final void onError(Throwable th) {
                throw new OnErrorNotImplementedException(th);
            }

            @Override // com.tds.common.reactor.Observer
            public final void onNext(T t) {
                action1.call(t);
            }
        };
    }

    public static <T> Subscriber<T> create(final Action1<? super T> action1, final Action1<Throwable> action12) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        return new Subscriber<T>() { // from class: com.tds.common.reactor.internal.observers.Subscribers.3
            @Override // com.tds.common.reactor.Observer
            public final void onCompleted() {
            }

            @Override // com.tds.common.reactor.Observer
            public final void onError(Throwable th) {
                action12.call(th);
            }

            @Override // com.tds.common.reactor.Observer
            public final void onNext(T t) {
                action1.call(t);
            }
        };
    }

    public static <T> Subscriber<T> create(final Action1<? super T> action1, final Action1<Throwable> action12, final Action0 action0) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        if (action0 == null) {
            throw new IllegalArgumentException("onComplete can not be null");
        }
        return new Subscriber<T>() { // from class: com.tds.common.reactor.internal.observers.Subscribers.4
            @Override // com.tds.common.reactor.Observer
            public final void onCompleted() {
                action0.call();
            }

            @Override // com.tds.common.reactor.Observer
            public final void onError(Throwable th) {
                action12.call(th);
            }

            @Override // com.tds.common.reactor.Observer
            public final void onNext(T t) {
                action1.call(t);
            }
        };
    }

    public static <T> Subscriber<T> wrap(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) { // from class: com.tds.common.reactor.internal.observers.Subscribers.5
            @Override // com.tds.common.reactor.Observer
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override // com.tds.common.reactor.Observer
            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            @Override // com.tds.common.reactor.Observer
            public void onNext(T t) {
                subscriber.onNext(t);
            }
        };
    }
}
