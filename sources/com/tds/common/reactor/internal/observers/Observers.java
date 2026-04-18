package com.tds.common.reactor.internal.observers;

import com.tds.common.reactor.Observer;
import com.tds.common.reactor.exceptions.OnErrorNotImplementedException;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public final class Observers {
    private static final Observer<Object> EMPTY = new Observer<Object>() { // from class: com.tds.common.reactor.internal.observers.Observers.1
        @Override // com.tds.common.reactor.Observer
        public final void onCompleted() {
        }

        @Override // com.tds.common.reactor.Observer
        public final void onNext(Object obj) {
        }

        @Override // com.tds.common.reactor.Observer
        public final void onError(Throwable th) {
            throw new OnErrorNotImplementedException(th);
        }
    };

    private Observers() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Observer<T> empty() {
        return (Observer<T>) EMPTY;
    }

    public static <T> Observer<T> create(final Action1<? super T> action1) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        return new Observer<T>() { // from class: com.tds.common.reactor.internal.observers.Observers.2
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

    public static <T> Observer<T> create(final Action1<? super T> action1, final Action1<Throwable> action12) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        return new Observer<T>() { // from class: com.tds.common.reactor.internal.observers.Observers.3
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

    public static <T> Observer<T> create(final Action1<? super T> action1, final Action1<Throwable> action12, final Action0 action0) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        if (action0 == null) {
            throw new IllegalArgumentException("onComplete can not be null");
        }
        return new Observer<T>() { // from class: com.tds.common.reactor.internal.observers.Observers.4
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
}
