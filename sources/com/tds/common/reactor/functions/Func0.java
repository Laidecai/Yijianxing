package com.tds.common.reactor.functions;

import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public interface Func0<R> extends Function, Callable<R> {
    @Override // java.util.concurrent.Callable
    R call();
}
