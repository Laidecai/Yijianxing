package com.tds.tapdb.b.o;

import com.tds.tapdb.b.n;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class b<V> implements Future<V> {
    private Future<V> a;
    private long b;
    private TimeUnit c;

    public b(Future future, long j, TimeUnit timeUnit) {
        this.a = future;
        this.b = j;
        this.c = timeUnit;
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return this.a.cancel(z);
    }

    @Override // java.util.concurrent.Future
    public V get() throws ExecutionException, InterruptedException {
        try {
            long j = this.b;
            return j > 0 ? this.a.get(j, this.c) : this.a.get();
        } catch (TimeoutException unused) {
            cancel(true);
            n.c("Timeout after " + this.b + " " + this.c.name());
            throw new ExecutionException("Timeout after " + this.b + " " + this.c.name(), null);
        }
    }

    @Override // java.util.concurrent.Future
    public V get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        try {
            return this.a.get(j, timeUnit);
        } catch (TimeoutException unused) {
            cancel(true);
            n.c("Timeout after " + j + " " + this.c.name());
            throw new ExecutionException("Timeout after" + j + " " + timeUnit.name(), null);
        }
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.a.isCancelled();
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.a.isDone();
    }
}
