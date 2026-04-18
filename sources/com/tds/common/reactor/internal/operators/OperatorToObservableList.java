package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.internal.producers.SingleDelayedProducer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorToObservableList<T> implements Observable.Operator<List<T>, T> {

    static final class Holder {
        static final OperatorToObservableList<Object> INSTANCE = new OperatorToObservableList<>();

        Holder() {
        }
    }

    public static <T> OperatorToObservableList<T> instance() {
        return (OperatorToObservableList<T>) Holder.INSTANCE;
    }

    OperatorToObservableList() {
    }

    @Override // com.tds.common.reactor.functions.Func1
    public Subscriber<? super T> call(final Subscriber<? super List<T>> subscriber) {
        final SingleDelayedProducer singleDelayedProducer = new SingleDelayedProducer(subscriber);
        Subscriber<T> subscriber2 = new Subscriber<T>() { // from class: com.tds.common.reactor.internal.operators.OperatorToObservableList.1
            boolean completed;
            List<T> list = new LinkedList();

            @Override // com.tds.common.reactor.Subscriber
            public void onStart() {
                request(LongCompanionObject.MAX_VALUE);
            }

            @Override // com.tds.common.reactor.Observer
            public void onCompleted() {
                if (this.completed) {
                    return;
                }
                this.completed = true;
                try {
                    ArrayList arrayList = new ArrayList(this.list);
                    this.list = null;
                    singleDelayedProducer.setValue(arrayList);
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, this);
                }
            }

            @Override // com.tds.common.reactor.Observer
            public void onError(Throwable th) {
                subscriber.onError(th);
            }

            @Override // com.tds.common.reactor.Observer
            public void onNext(T t) {
                if (this.completed) {
                    return;
                }
                this.list.add(t);
            }
        };
        subscriber.add(subscriber2);
        subscriber.setProducer(singleDelayedProducer);
        return subscriber2;
    }
}
