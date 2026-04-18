package com.tds.common.reactor.transformer;

import com.tds.common.reactor.transformer.FlowArbiter;

/* JADX INFO: loaded from: classes.dex */
public interface FlowCallback<T> {
    void onError(int i, String str);

    void onSuccess(FlowArbiter.FlowResult<T> flowResult);
}
