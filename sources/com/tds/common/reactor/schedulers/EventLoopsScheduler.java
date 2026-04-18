package com.tds.common.reactor.schedulers;

import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.internal.schedulers.NewThreadWorker;
import com.tds.common.reactor.schedulers.Scheduler;
import com.tds.common.reactor.subscriptions.Subscriptions;
import com.tds.common.reactor.util.RxThreadFactory;
import com.tds.common.reactor.util.SubscriptionList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class EventLoopsScheduler extends Scheduler implements SchedulerLifecycle {
    static final String KEY_MAX_THREADS = "rx.scheduler.max-computation-threads";
    static final int MAX_THREADS;
    static final FixedSchedulerPool NONE;
    static final PoolWorker SHUTDOWN_WORKER;
    final AtomicReference<FixedSchedulerPool> pool = new AtomicReference<>(NONE);
    final ThreadFactory threadFactory;

    static {
        int iIntValue = Integer.getInteger(KEY_MAX_THREADS, 0).intValue();
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        if (iIntValue <= 0 || iIntValue > iAvailableProcessors) {
            iIntValue = iAvailableProcessors;
        }
        MAX_THREADS = iIntValue;
        PoolWorker poolWorker = new PoolWorker(RxThreadFactory.NONE);
        SHUTDOWN_WORKER = poolWorker;
        poolWorker.unsubscribe();
        NONE = new FixedSchedulerPool(null, 0);
    }

    static final class FixedSchedulerPool {
        final int cores;
        final PoolWorker[] eventLoops;
        long n;

        FixedSchedulerPool(ThreadFactory threadFactory, int i) {
            this.cores = i;
            this.eventLoops = new PoolWorker[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.eventLoops[i2] = new PoolWorker(threadFactory);
            }
        }

        public PoolWorker getEventLoop() {
            int i = this.cores;
            if (i == 0) {
                return EventLoopsScheduler.SHUTDOWN_WORKER;
            }
            PoolWorker[] poolWorkerArr = this.eventLoops;
            long j = this.n;
            this.n = 1 + j;
            return poolWorkerArr[(int) (j % ((long) i))];
        }

        public void shutdown() {
            for (PoolWorker poolWorker : this.eventLoops) {
                poolWorker.unsubscribe();
            }
        }
    }

    public EventLoopsScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        start();
    }

    @Override // com.tds.common.reactor.schedulers.Scheduler
    public Scheduler.Worker createWorker() {
        return new EventLoopWorker(this.pool.get().getEventLoop());
    }

    @Override // com.tds.common.reactor.schedulers.SchedulerLifecycle
    public void start() {
        FixedSchedulerPool fixedSchedulerPool = new FixedSchedulerPool(this.threadFactory, MAX_THREADS);
        if (this.pool.compareAndSet(NONE, fixedSchedulerPool)) {
            return;
        }
        fixedSchedulerPool.shutdown();
    }

    @Override // com.tds.common.reactor.schedulers.SchedulerLifecycle
    public void shutdown() {
        FixedSchedulerPool fixedSchedulerPool;
        FixedSchedulerPool fixedSchedulerPool2;
        do {
            fixedSchedulerPool = this.pool.get();
            fixedSchedulerPool2 = NONE;
            if (fixedSchedulerPool == fixedSchedulerPool2) {
                return;
            }
        } while (!this.pool.compareAndSet(fixedSchedulerPool, fixedSchedulerPool2));
        fixedSchedulerPool.shutdown();
    }

    public Subscription scheduleDirect(Action0 action0) {
        return this.pool.get().getEventLoop().scheduleActual(action0, -1L, TimeUnit.NANOSECONDS);
    }

    static final class EventLoopWorker extends Scheduler.Worker {
        private final SubscriptionList both;
        private final PoolWorker poolWorker;
        private final SubscriptionList serial;

        EventLoopWorker(PoolWorker poolWorker) {
            SubscriptionList subscriptionList = new SubscriptionList();
            this.serial = subscriptionList;
            this.both = new SubscriptionList(subscriptionList);
            this.poolWorker = poolWorker;
        }

        @Override // com.tds.common.reactor.Subscription
        public void unsubscribe() {
            this.both.unsubscribe();
        }

        @Override // com.tds.common.reactor.Subscription
        public boolean isUnsubscribed() {
            return this.both.isUnsubscribed();
        }

        @Override // com.tds.common.reactor.schedulers.Scheduler.Worker
        public Subscription schedule(final Action0 action0) {
            if (isUnsubscribed()) {
                return Subscriptions.unsubscribed();
            }
            return this.poolWorker.scheduleActual(new Action0() { // from class: com.tds.common.reactor.schedulers.EventLoopsScheduler.EventLoopWorker.1
                @Override // com.tds.common.reactor.functions.Action0
                public void call() {
                    if (EventLoopWorker.this.isUnsubscribed()) {
                        return;
                    }
                    action0.call();
                }
            }, 0L, (TimeUnit) null, this.serial);
        }

        @Override // com.tds.common.reactor.schedulers.Scheduler.Worker
        public Subscription schedule(final Action0 action0, long j, TimeUnit timeUnit) {
            if (isUnsubscribed()) {
                return Subscriptions.unsubscribed();
            }
            return this.poolWorker.scheduleActual(new Action0() { // from class: com.tds.common.reactor.schedulers.EventLoopsScheduler.EventLoopWorker.2
                @Override // com.tds.common.reactor.functions.Action0
                public void call() {
                    if (EventLoopWorker.this.isUnsubscribed()) {
                        return;
                    }
                    action0.call();
                }
            }, j, timeUnit);
        }
    }

    static final class PoolWorker extends NewThreadWorker {
        PoolWorker(ThreadFactory threadFactory) {
            super(threadFactory);
        }
    }
}
