package com.tds.common.reactor.plugins;

import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.internal.schedulers.NewThreadScheduler;
import com.tds.common.reactor.schedulers.CachedThreadScheduler;
import com.tds.common.reactor.schedulers.EventLoopsScheduler;
import com.tds.common.reactor.schedulers.Scheduler;
import com.tds.common.reactor.util.RxThreadFactory;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
public class RxJavaSchedulersHook {
    private static final RxJavaSchedulersHook DEFAULT_INSTANCE = new RxJavaSchedulersHook();

    public Scheduler getComputationScheduler() {
        return null;
    }

    public Scheduler getIOScheduler() {
        return null;
    }

    public Scheduler getNewThreadScheduler() {
        return null;
    }

    @Deprecated
    public Action0 onSchedule(Action0 action0) {
        return action0;
    }

    public static Scheduler createComputationScheduler() {
        return createComputationScheduler(new RxThreadFactory("RxComputationScheduler-"));
    }

    public static Scheduler createComputationScheduler(ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory == null");
        return new EventLoopsScheduler(threadFactory);
    }

    public static Scheduler createIoScheduler() {
        return createIoScheduler(new RxThreadFactory("RxIoScheduler-"));
    }

    public static Scheduler createIoScheduler(ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory == null");
        return new CachedThreadScheduler(threadFactory);
    }

    public static Scheduler createNewThreadScheduler() {
        return createNewThreadScheduler(new RxThreadFactory("RxNewThreadScheduler-"));
    }

    public static Scheduler createNewThreadScheduler(ThreadFactory threadFactory) {
        Objects.requireNonNull(threadFactory, "threadFactory == null");
        return new NewThreadScheduler(threadFactory);
    }

    public static RxJavaSchedulersHook getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }
}
