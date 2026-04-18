package com.tds.common.reactor.subjects;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Observer;

/* JADX INFO: loaded from: classes.dex */
public abstract class Subject<T, R> extends Observable<R> implements Observer<T> {
    public abstract boolean hasObservers();

    public Subject(Observable.OnSubscribe<R> onSubscribe) {
        super(onSubscribe);
    }

    public final SerializedSubject<T, R> toSerialized() {
        if (getClass() == SerializedSubject.class) {
            return (SerializedSubject) this;
        }
        return new SerializedSubject<>(this);
    }
}
