package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.observers.SerializedSubscriber;
import com.tds.common.reactor.schedulers.Scheduler;
import com.tds.common.reactor.subscriptions.SerialSubscription;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorDebounceWithTime<T> implements Observable.Operator<T, T> {
    final Scheduler scheduler;
    final long timeout;
    final TimeUnit unit;

    public OperatorDebounceWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.timeout = j;
        this.unit = timeUnit;
        this.scheduler = scheduler;
    }

    @Override // com.tds.common.reactor.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        SerialSubscription serialSubscription = new SerialSubscription();
        serializedSubscriber.add(workerCreateWorker);
        serializedSubscriber.add(serialSubscription);
        return new AnonymousClass1(subscriber, serialSubscription, workerCreateWorker, serializedSubscriber);
    }

    /* JADX INFO: renamed from: com.tds.common.reactor.internal.operators.OperatorDebounceWithTime$1, reason: invalid class name */
    class AnonymousClass1 extends Subscriber<T> {
        final Subscriber<?> self;
        final DebounceState<T> state;
        final /* synthetic */ SerializedSubscriber val$s;
        final /* synthetic */ SerialSubscription val$serial;
        final /* synthetic */ Scheduler.Worker val$worker;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Subscriber subscriber, SerialSubscription serialSubscription, Scheduler.Worker worker, SerializedSubscriber serializedSubscriber) {
            super(subscriber);
            this.val$serial = serialSubscription;
            this.val$worker = worker;
            this.val$s = serializedSubscriber;
            this.state = new DebounceState<>();
            this.self = this;
        }

        @Override // com.tds.common.reactor.Subscriber
        public void onStart() {
            request(LongCompanionObject.MAX_VALUE);
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            final int next = this.state.next(t);
            this.val$serial.set(this.val$worker.schedule(new Action0() { // from class: com.tds.common.reactor.internal.operators.OperatorDebounceWithTime.1.1
                @Override // com.tds.common.reactor.functions.Action0
                public void call() {
                    AnonymousClass1.this.state.emit(next, AnonymousClass1.this.val$s, AnonymousClass1.this.self);
                }
            }, OperatorDebounceWithTime.this.timeout, OperatorDebounceWithTime.this.unit));
        }

        @Override // com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            this.val$s.onError(th);
            unsubscribe();
            this.state.clear();
        }

        @Override // com.tds.common.reactor.Observer
        public void onCompleted() {
            this.state.emitAndComplete(this.val$s, this);
        }
    }

    static final class DebounceState<T> {
        boolean emitting;
        boolean hasValue;
        int index;
        boolean terminate;
        T value;

        DebounceState() {
        }

        public synchronized int next(T t) {
            int i;
            this.value = t;
            this.hasValue = true;
            i = this.index + 1;
            this.index = i;
            return i;
        }

        public void emit(int i, Subscriber<T> subscriber, Subscriber<?> subscriber2) {
            synchronized (this) {
                if (!this.emitting && this.hasValue && i == this.index) {
                    T t = this.value;
                    this.value = null;
                    this.hasValue = false;
                    this.emitting = true;
                    try {
                        subscriber.onNext(t);
                        synchronized (this) {
                            if (!this.terminate) {
                                this.emitting = false;
                            } else {
                                subscriber.onCompleted();
                            }
                        }
                    } catch (Throwable th) {
                        Exceptions.throwOrReport(th, subscriber2, t);
                    }
                }
            }
        }

        public void emitAndComplete(Subscriber<T> subscriber, Subscriber<?> subscriber2) {
            synchronized (this) {
                if (this.emitting) {
                    this.terminate = true;
                    return;
                }
                T t = this.value;
                boolean z = this.hasValue;
                this.value = null;
                this.hasValue = false;
                this.emitting = true;
                if (z) {
                    try {
                        subscriber.onNext(t);
                    } catch (Throwable th) {
                        Exceptions.throwOrReport(th, subscriber2, t);
                        return;
                    }
                }
                subscriber.onCompleted();
            }
        }

        public synchronized void clear() {
            this.index++;
            this.value = null;
            this.hasValue = false;
        }
    }
}
