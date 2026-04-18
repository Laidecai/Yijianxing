package com.tds.common.reactor.transformer;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.FlowException;
import com.tds.common.reactor.transformer.FlowArbiter;

/* JADX INFO: loaded from: classes.dex */
public class FlowEnqueueOnSubscribe<T> implements Observable.OnSubscribe<FlowArbiter.FlowResult<T>> {
    private final FlowCall<T> flowCall;

    public FlowEnqueueOnSubscribe(FlowCall<T> flowCall) {
        this.flowCall = flowCall;
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super FlowArbiter.FlowResult<T>> subscriber) {
        FlowCall<T> flowCallClone = this.flowCall.clone();
        final FlowArbiter flowArbiter = new FlowArbiter(flowCallClone, subscriber);
        subscriber.setProducer(flowArbiter);
        flowCallClone.enqueue(new FlowCallback<T>() { // from class: com.tds.common.reactor.transformer.FlowEnqueueOnSubscribe.1
            @Override // com.tds.common.reactor.transformer.FlowCallback
            public void onSuccess(FlowArbiter.FlowResult<T> flowResult) {
                flowArbiter.emitResponse(flowResult);
            }

            @Override // com.tds.common.reactor.transformer.FlowCallback
            public void onError(int i, String str) {
                flowArbiter.emitError(new FlowException(i, str));
            }
        });
    }
}
