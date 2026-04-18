package com.tds.common.reactor.transformer;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.FlowException;
import com.tds.common.reactor.transformer.FlowArbiter;

/* JADX INFO: loaded from: classes.dex */
public class FlowOnSubscribe<T> implements Observable.OnSubscribe<T> {
    private final Observable.OnSubscribe<FlowArbiter.FlowResult<T>> upstream;

    public FlowOnSubscribe(Observable.OnSubscribe<FlowArbiter.FlowResult<T>> onSubscribe) {
        this.upstream = onSubscribe;
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        this.upstream.call((FlowArbiter.FlowResult<T>) new FlowOnSubscriber(subscriber));
    }

    private static class FlowOnSubscriber<R> extends Subscriber<FlowArbiter.FlowResult<R>> {
        private final Subscriber<? super R> subscriber;
        private boolean subscriberTerminated;

        FlowOnSubscriber(Subscriber<? super R> subscriber) {
            super(subscriber);
            this.subscriber = subscriber;
        }

        @Override // com.tds.common.reactor.Observer
        public void onCompleted() {
            if (this.subscriberTerminated) {
                return;
            }
            this.subscriber.onCompleted();
        }

        @Override // com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            if (this.subscriberTerminated) {
                return;
            }
            this.subscriber.onError(th);
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(FlowArbiter.FlowResult<R> flowResult) {
            if (flowResult.code == 0) {
                this.subscriber.onNext(flowResult.data);
                return;
            }
            this.subscriberTerminated = true;
            try {
                this.subscriber.onError(new FlowException(9999, "未知错误"));
            } catch (Exception unused) {
            }
        }
    }
}
