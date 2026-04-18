package com.tds.common.reactor.subjects;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.observers.SerializedObserver;

/* JADX INFO: loaded from: classes.dex */
public class SerializedSubject<T, R> extends Subject<T, R> {
    private final Subject<T, R> actual;
    private final SerializedObserver<T> observer;

    /* JADX INFO: renamed from: com.tds.common.reactor.subjects.SerializedSubject$1 */
    class AnonymousClass1 implements Observable.OnSubscribe<R> {
        AnonymousClass1() {
        }

        @Override // com.tds.common.reactor.functions.Action1
        public void call(Subscriber<? super R> subscriber) {
            subject.unsafeSubscribe(subscriber);
        }
    }

    public SerializedSubject(Subject<T, R> subject) {
        super(new Observable.OnSubscribe<R>() { // from class: com.tds.common.reactor.subjects.SerializedSubject.1
            AnonymousClass1() {
            }

            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super R> subscriber) {
                subject.unsafeSubscribe(subscriber);
            }
        });
        this.actual = subject;
        this.observer = new SerializedObserver<>(subject);
    }

    @Override // com.tds.common.reactor.Observer
    public void onCompleted() {
        this.observer.onCompleted();
    }

    @Override // com.tds.common.reactor.Observer
    public void onError(Throwable th) {
        this.observer.onError(th);
    }

    @Override // com.tds.common.reactor.Observer
    public void onNext(T t) {
        this.observer.onNext(t);
    }

    @Override // com.tds.common.reactor.subjects.Subject
    public boolean hasObservers() {
        return this.actual.hasObservers();
    }
}
