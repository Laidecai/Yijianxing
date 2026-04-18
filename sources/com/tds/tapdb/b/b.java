package com.tds.tapdb.b;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final com.tds.tapdb.b.o.a a;

    static {
        com.tds.tapdb.b.o.a aVar = new com.tds.tapdb.b.o.a(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        a = aVar;
        aVar.allowsCoreThreadTimeOut();
    }

    public static <T> Future<T> a(Callable<T> callable, long j, TimeUnit timeUnit) {
        return a.a(callable, j, timeUnit);
    }

    public static void a(Runnable runnable) {
        a.submit(runnable);
    }
}
