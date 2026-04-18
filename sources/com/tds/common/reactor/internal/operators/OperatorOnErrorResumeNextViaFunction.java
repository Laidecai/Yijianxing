package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Producer;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.functions.Func1;
import com.tds.common.reactor.internal.producers.ProducerArbiter;
import com.tds.common.reactor.plugins.RxJavaHooks;
import com.tds.common.reactor.subscriptions.SerialSubscription;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorOnErrorResumeNextViaFunction<T> implements Observable.Operator<T, T> {
    final Func1<? super Throwable, ? extends Observable<? extends T>> resumeFunction;

    public static <T> OperatorOnErrorResumeNextViaFunction<T> withSingle(final Func1<? super Throwable, ? extends T> func1) {
        return new OperatorOnErrorResumeNextViaFunction<>(new Func1<Throwable, Observable<? extends T>>() { // from class: com.tds.common.reactor.internal.operators.OperatorOnErrorResumeNextViaFunction.1
            @Override // com.tds.common.reactor.functions.Func1
            public Observable<? extends T> call(Throwable th) {
                return Observable.just(func1.call(th));
            }
        });
    }

    public static <T> OperatorOnErrorResumeNextViaFunction<T> withOther(final Observable<? extends T> observable) {
        return new OperatorOnErrorResumeNextViaFunction<>(new Func1<Throwable, Observable<? extends T>>() { // from class: com.tds.common.reactor.internal.operators.OperatorOnErrorResumeNextViaFunction.2
            @Override // com.tds.common.reactor.functions.Func1
            public Observable<? extends T> call(Throwable th) {
                return observable;
            }
        });
    }

    public static <T> OperatorOnErrorResumeNextViaFunction<T> withException(final Observable<? extends T> observable) {
        return new OperatorOnErrorResumeNextViaFunction<>(new Func1<Throwable, Observable<? extends T>>() { // from class: com.tds.common.reactor.internal.operators.OperatorOnErrorResumeNextViaFunction.3
            @Override // com.tds.common.reactor.functions.Func1
            public Observable<? extends T> call(Throwable th) {
                if (th instanceof Exception) {
                    return observable;
                }
                return Observable.error(th);
            }
        });
    }

    public OperatorOnErrorResumeNextViaFunction(Func1<? super Throwable, ? extends Observable<? extends T>> func1) {
        this.resumeFunction = func1;
    }

    @Override // com.tds.common.reactor.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        final ProducerArbiter producerArbiter = new ProducerArbiter();
        final SerialSubscription serialSubscription = new SerialSubscription();
        Subscriber<T> subscriber2 = new Subscriber<T>() { // from class: com.tds.common.reactor.internal.operators.OperatorOnErrorResumeNextViaFunction.4
            private boolean done;
            long produced;

            @Override // com.tds.common.reactor.Observer
            public void onCompleted() {
                if (this.done) {
                    return;
                }
                this.done = true;
                subscriber.onCompleted();
            }

            @Override // com.tds.common.reactor.Observer
            public void onError(Throwable th) {
                if (this.done) {
                    Exceptions.throwIfFatal(th);
                    RxJavaHooks.onError(th);
                    return;
                }
                this.done = true;
                try {
                    unsubscribe();
                    Subscriber<T> subscriber3 = new Subscriber<T>() { // from class: com.tds.common.reactor.internal.operators.OperatorOnErrorResumeNextViaFunction.4.1
                        @Override // com.tds.common.reactor.Observer
                        public void onNext(T t) {
                            subscriber.onNext(t);
                        }

                        @Override // com.tds.common.reactor.Observer
                        public void onError(Throwable th2) {
                            subscriber.onError(th2);
                        }

                        @Override // com.tds.common.reactor.Observer
                        public void onCompleted() {
                            subscriber.onCompleted();
                        }

                        @Override // com.tds.common.reactor.Subscriber
                        public void setProducer(Producer producer) {
                            producerArbiter.setProducer(producer);
                        }
                    };
                    serialSubscription.set(subscriber3);
                    long j = this.produced;
                    if (j != 0) {
                        producerArbiter.produced(j);
                    }
                    OperatorOnErrorResumeNextViaFunction.this.resumeFunction.call(th).unsafeSubscribe(subscriber3);
                } catch (Throwable th2) {
                    Exceptions.throwOrReport(th2, subscriber);
                }
            }

            @Override // com.tds.common.reactor.Observer
            public void onNext(T t) {
                if (this.done) {
                    return;
                }
                this.produced++;
                subscriber.onNext(t);
            }

            @Override // com.tds.common.reactor.Subscriber
            public void setProducer(Producer producer) {
                producerArbiter.setProducer(producer);
            }
        };
        serialSubscription.set(subscriber2);
        subscriber.add(serialSubscription);
        subscriber.setProducer(producerArbiter);
        return subscriber2;
    }
}
