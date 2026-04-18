package com.tds.tapdb.b.o;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class a extends ThreadPoolExecutor {
    private long a;
    private TimeUnit b;

    public a(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i2, j, timeUnit, blockingQueue);
        this.a = -1L;
        this.b = TimeUnit.SECONDS;
    }

    public a(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, RejectedExecutionHandler rejectedExecutionHandler) {
        super(i, i2, j, timeUnit, blockingQueue, rejectedExecutionHandler);
        this.a = -1L;
        this.b = TimeUnit.SECONDS;
    }

    public a(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        super(i, i2, j, timeUnit, blockingQueue, threadFactory);
        this.a = -1L;
        this.b = TimeUnit.SECONDS;
    }

    public a(int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(i, i2, j, timeUnit, blockingQueue, threadFactory, rejectedExecutionHandler);
        this.a = -1L;
        this.b = TimeUnit.SECONDS;
    }

    public <T> Future<T> a(Callable<T> callable, long j, TimeUnit timeUnit) {
        return new b(super.submit(callable), j, timeUnit);
    }

    public void a(long j, TimeUnit timeUnit) {
        this.a = j;
        this.b = timeUnit;
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    protected void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        if (th == null && (runnable instanceof Future)) {
            try {
                Future future = (Future) runnable;
                if (future.isDone()) {
                    future.get();
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (CancellationException e) {
                th = e;
            } catch (ExecutionException e2) {
                th = e2.getCause();
            }
        }
        if (th != null) {
            System.out.println(th);
        }
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public Future<?> submit(Runnable runnable) {
        return new b(super.submit(runnable), this.a, this.b);
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Runnable runnable, T t) {
        return new b(super.submit(runnable, t), this.a, this.b);
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Callable<T> callable) {
        return new b(super.submit(callable), this.a, this.b);
    }
}
