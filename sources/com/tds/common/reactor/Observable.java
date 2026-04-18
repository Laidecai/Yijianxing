package com.tds.common.reactor;

import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.exceptions.OnErrorFailedException;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.functions.Action1;
import com.tds.common.reactor.functions.Actions;
import com.tds.common.reactor.functions.Func1;
import com.tds.common.reactor.internal.operators.EmptyObservableHolder;
import com.tds.common.reactor.internal.operators.OnSubscribeDoOnEach;
import com.tds.common.reactor.internal.operators.OnSubscribeFilter;
import com.tds.common.reactor.internal.operators.OnSubscribeFromArray;
import com.tds.common.reactor.internal.operators.OnSubscribeFromIterable;
import com.tds.common.reactor.internal.operators.OnSubscribeLift;
import com.tds.common.reactor.internal.operators.OnSubscribeMap;
import com.tds.common.reactor.internal.operators.OnSubscribeThrow;
import com.tds.common.reactor.internal.operators.OnSubscribeTimeoutTimedWithFallback;
import com.tds.common.reactor.internal.operators.OperatorDebounceWithSelector;
import com.tds.common.reactor.internal.operators.OperatorMerge;
import com.tds.common.reactor.internal.operators.OperatorObserveOn;
import com.tds.common.reactor.internal.operators.OperatorOnErrorResumeNextViaFunction;
import com.tds.common.reactor.internal.operators.OperatorSubscribeOn;
import com.tds.common.reactor.internal.operators.OperatorToObservableList;
import com.tds.common.reactor.internal.util.ActionObserver;
import com.tds.common.reactor.internal.util.ObserverSubscriber;
import com.tds.common.reactor.internal.util.ScalarSynchronousObservable;
import com.tds.common.reactor.observers.SafeSubscriber;
import com.tds.common.reactor.operators.OnSubscribeCreate;
import com.tds.common.reactor.plugins.RxJavaHooks;
import com.tds.common.reactor.schedulers.Scheduler;
import com.tds.common.reactor.schedulers.Schedulers;
import com.tds.common.reactor.subscriptions.Subscriptions;
import com.tds.common.reactor.util.ActionSubscriber;
import com.tds.common.reactor.util.InternalObservableUtils;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class Observable<T> {
    final OnSubscribe<T> onSubscribe;

    public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {
    }

    public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
    }

    protected Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(onSubscribe);
    }

    public final Subscription subscribe(Subscriber<? super T> subscriber) {
        return subscribe(subscriber, this);
    }

    static <T> Subscription subscribe(Subscriber<? super T> subscriber, Observable<T> observable) {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber can not be null");
        }
        if (observable.onSubscribe == null) {
            throw new IllegalStateException("onSubscribe function can not be null.");
        }
        subscriber.onStart();
        if (!(subscriber instanceof SafeSubscriber)) {
            subscriber = new SafeSubscriber(subscriber);
        }
        try {
            RxJavaHooks.onObservableStart(observable, observable.onSubscribe).call(subscriber);
            return RxJavaHooks.onObservableReturn(subscriber);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            if (subscriber.isUnsubscribed()) {
                RxJavaHooks.onError(RxJavaHooks.onObservableError(th));
            } else {
                try {
                    subscriber.onError(RxJavaHooks.onObservableError(th));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    OnErrorFailedException onErrorFailedException = new OnErrorFailedException("Error occurred attempting to subscribe [" + th.getMessage() + "] and then again while trying to pass to onError.", th2);
                    RxJavaHooks.onObservableError(onErrorFailedException);
                    throw onErrorFailedException;
                }
            }
            return Subscriptions.unsubscribed();
        }
    }

    public final Subscription subscribe() {
        return subscribe((Subscriber) new ActionSubscriber(Actions.empty(), InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Actions.empty()));
    }

    public final Subscription subscribe(Action1<? super T> action1) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        return subscribe((Subscriber) new ActionSubscriber(action1, InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Actions.empty()));
    }

    public final Subscription subscribe(Action1<? super T> action1, Action1<Throwable> action12) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        return subscribe((Subscriber) new ActionSubscriber(action1, action12, Actions.empty()));
    }

    public final Subscription subscribe(Action1<? super T> action1, Action1<Throwable> action12, Action0 action0) {
        if (action1 == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (action12 == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
        if (action0 == null) {
            throw new IllegalArgumentException("onComplete can not be null");
        }
        return subscribe((Subscriber) new ActionSubscriber(action1, action12, action0));
    }

    public final Subscription subscribe(Observer<? super T> observer) {
        if (observer instanceof Subscriber) {
            return subscribe((Subscriber) observer);
        }
        Objects.requireNonNull(observer, "observer is null");
        return subscribe((Subscriber) new ObserverSubscriber(observer));
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return subscribeOn(scheduler, !(this.onSubscribe instanceof OnSubscribeCreate));
    }

    public final Observable<T> subscribeOn(Scheduler scheduler, boolean z) {
        return unsafeCreate(new OperatorSubscribeOn(this, scheduler, z));
    }

    public static <T> Observable<T> unsafeCreate(OnSubscribe<T> onSubscribe) {
        return new Observable<>(RxJavaHooks.onCreate(onSubscribe));
    }

    public final Subscription unsafeSubscribe(Subscriber<? super T> subscriber) {
        try {
            subscriber.onStart();
            RxJavaHooks.onObservableStart(this, this.onSubscribe).call(subscriber);
            return RxJavaHooks.onObservableReturn(subscriber);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            try {
                subscriber.onError(RxJavaHooks.onObservableError(th));
                return Subscriptions.unsubscribed();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                OnErrorFailedException onErrorFailedException = new OnErrorFailedException("Error occurred attempting to subscribe [" + th.getMessage() + "] and then again while trying to pass to onError.", th2);
                RxJavaHooks.onObservableError(onErrorFailedException);
                throw onErrorFailedException;
            }
        }
    }

    public Observable<T> observeOn(Scheduler scheduler) {
        return observeOn(scheduler, 128);
    }

    public final Observable<T> observeOn(Scheduler scheduler, int i) {
        return observeOn(scheduler, false, i);
    }

    public final Observable<T> observeOn(Scheduler scheduler, boolean z, int i) {
        return (Observable<T>) lift(new OperatorObserveOn(scheduler, z, i));
    }

    public final <R> Observable<R> lift(Operator<? extends R, ? super T> operator) {
        return unsafeCreate(new OnSubscribeLift(this.onSubscribe, operator));
    }

    public final <R> Observable<R> map(Func1<? super T, ? extends R> func1) {
        return unsafeCreate(new OnSubscribeMap(this, func1));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func1) {
        return merge(map(func1));
    }

    public static <T> Observable<T> merge(Observable<? extends Observable<? extends T>> observable) {
        return (Observable<T>) observable.lift(OperatorMerge.instance(false));
    }

    public static <T> Observable<T> empty() {
        return EmptyObservableHolder.instance();
    }

    public static <T> Observable<T> just(T t) {
        return ScalarSynchronousObservable.create(t);
    }

    public static <T> Observable<T> just(T t, T t2) {
        return from(new Object[]{t, t2});
    }

    public static <T> Observable<T> just(T t, T t2, T t3) {
        return from(new Object[]{t, t2, t3});
    }

    public static <T> Observable<T> just(T t, T t2, T t3, T t4) {
        return from(new Object[]{t, t2, t3, t4});
    }

    public static <T> Observable<T> just(T t, T t2, T t3, T t4, T t5) {
        return from(new Object[]{t, t2, t3, t4, t5});
    }

    public static <T> Observable<T> from(T[] tArr) {
        int length = tArr.length;
        if (length == 0) {
            return empty();
        }
        if (length == 1) {
            return just(tArr[0]);
        }
        return unsafeCreate(new OnSubscribeFromArray(tArr));
    }

    public static <T> Observable<T> from(Iterable<? extends T> iterable) {
        return unsafeCreate(new OnSubscribeFromIterable(iterable));
    }

    public final Observable<T> filter(Func1<? super T, Boolean> func1) {
        return unsafeCreate(new OnSubscribeFilter(this, func1));
    }

    public final Observable<T> onErrorResumeNext(Func1<? super Throwable, ? extends Observable<? extends T>> func1) {
        return (Observable<T>) lift(new OperatorOnErrorResumeNextViaFunction(func1));
    }

    public static <T> Observable<T> error(Throwable th) {
        return unsafeCreate(new OnSubscribeThrow(th));
    }

    public final Observable<T> doOnError(Action1<? super Throwable> action1) {
        return unsafeCreate(new OnSubscribeDoOnEach(this, new ActionObserver(Actions.empty(), action1, Actions.empty())));
    }

    public final Observable<T> timeout(long j, TimeUnit timeUnit) {
        return timeout(j, timeUnit, null, Schedulers.computation());
    }

    public final Observable<T> timeout(long j, TimeUnit timeUnit, Observable<? extends T> observable, Scheduler scheduler) {
        return unsafeCreate(new OnSubscribeTimeoutTimedWithFallback(this, j, timeUnit, scheduler, observable));
    }

    public final <U> Observable<T> debounce(Func1<? super T, ? extends Observable<U>> func1) {
        return (Observable<T>) lift(new OperatorDebounceWithSelector(func1));
    }

    public final Observable<List<T>> toList() {
        return (Observable<List<T>>) lift(OperatorToObservableList.instance());
    }
}
