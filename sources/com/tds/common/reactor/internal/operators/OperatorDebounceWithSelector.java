package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.functions.Func1;
import com.tds.common.reactor.internal.operators.OperatorDebounceWithTime;
import com.tds.common.reactor.observers.SerializedSubscriber;
import com.tds.common.reactor.subscriptions.SerialSubscription;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorDebounceWithSelector<T, U> implements Observable.Operator<T, T> {
    final Func1<? super T, ? extends Observable<U>> selector;

    public OperatorDebounceWithSelector(Func1<? super T, ? extends Observable<U>> func1) {
        this.selector = func1;
    }

    @Override // com.tds.common.reactor.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        SerialSubscription serialSubscription = new SerialSubscription();
        subscriber.add(serialSubscription);
        return new AnonymousClass1(subscriber, serializedSubscriber, serialSubscription);
    }

    /* JADX INFO: renamed from: com.tds.common.reactor.internal.operators.OperatorDebounceWithSelector$1, reason: invalid class name */
    class AnonymousClass1 extends Subscriber<T> {
        final Subscriber<?> self;
        final OperatorDebounceWithTime.DebounceState<T> state;
        final /* synthetic */ SerializedSubscriber val$s;
        final /* synthetic */ SerialSubscription val$serial;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Subscriber subscriber, SerializedSubscriber serializedSubscriber, SerialSubscription serialSubscription) {
            super(subscriber);
            this.val$s = serializedSubscriber;
            this.val$serial = serialSubscription;
            this.state = new OperatorDebounceWithTime.DebounceState<>();
            this.self = this;
        }

        @Override // com.tds.common.reactor.Subscriber
        public void onStart() {
            request(LongCompanionObject.MAX_VALUE);
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            try {
                Observable<U> observableCall = OperatorDebounceWithSelector.this.selector.call(t);
                final int next = this.state.next(t);
                Subscriber<U> subscriber = new Subscriber<U>() { // from class: com.tds.common.reactor.internal.operators.OperatorDebounceWithSelector.1.1
                    @Override // com.tds.common.reactor.Observer
                    public void onNext(U u) {
                        onCompleted();
                    }

                    @Override // com.tds.common.reactor.Observer
                    public void onError(Throwable th) {
                        AnonymousClass1.this.self.onError(th);
                    }

                    @Override // com.tds.common.reactor.Observer
                    public void onCompleted() {
                        AnonymousClass1.this.state.emit(next, AnonymousClass1.this.val$s, AnonymousClass1.this.self);
                        unsubscribe();
                    }
                };
                this.val$serial.set(subscriber);
                observableCall.unsafeSubscribe(subscriber);
            } catch (Throwable th) {
                Exceptions.throwOrReport(th, this);
            }
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
}
