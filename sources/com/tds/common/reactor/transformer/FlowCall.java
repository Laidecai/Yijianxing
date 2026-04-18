package com.tds.common.reactor.transformer;

/* JADX INFO: loaded from: classes.dex */
public interface FlowCall<T> extends Cloneable {
    FlowCall<T> clone();

    void enqueue(FlowCallback<T> flowCallback);
}
