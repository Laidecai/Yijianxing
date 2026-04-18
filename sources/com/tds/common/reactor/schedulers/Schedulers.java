package com.tds.common.reactor.schedulers;

import com.tds.common.reactor.internal.schedulers.ExecutorScheduler;
import com.tds.common.reactor.plugins.RxJavaHooks;
import com.tds.common.reactor.plugins.RxJavaPlugins;
import com.tds.common.reactor.plugins.RxJavaSchedulersHook;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class Schedulers {
    private static final AtomicReference<Schedulers> INSTANCE = new AtomicReference<>();
    private final Scheduler computationScheduler;
    private final Scheduler ioScheduler;
    private final Scheduler newThreadScheduler;

    private static Schedulers getInstance() {
        while (true) {
            AtomicReference<Schedulers> atomicReference = INSTANCE;
            Schedulers schedulers = atomicReference.get();
            if (schedulers != null) {
                return schedulers;
            }
            Schedulers schedulers2 = new Schedulers();
            if (atomicReference.compareAndSet(null, schedulers2)) {
                return schedulers2;
            }
            schedulers2.shutdownInstance();
        }
    }

    private Schedulers() {
        RxJavaSchedulersHook schedulersHook = RxJavaPlugins.getInstance().getSchedulersHook();
        Scheduler computationScheduler = schedulersHook.getComputationScheduler();
        if (computationScheduler != null) {
            this.computationScheduler = computationScheduler;
        } else {
            this.computationScheduler = RxJavaSchedulersHook.createComputationScheduler();
        }
        Scheduler iOScheduler = schedulersHook.getIOScheduler();
        if (iOScheduler != null) {
            this.ioScheduler = iOScheduler;
        } else {
            this.ioScheduler = RxJavaSchedulersHook.createIoScheduler();
        }
        Scheduler newThreadScheduler = schedulersHook.getNewThreadScheduler();
        if (newThreadScheduler != null) {
            this.newThreadScheduler = newThreadScheduler;
        } else {
            this.newThreadScheduler = RxJavaSchedulersHook.createNewThreadScheduler();
        }
    }

    public static Scheduler newThread() {
        return RxJavaHooks.onNewThreadScheduler(getInstance().newThreadScheduler);
    }

    public static Scheduler computation() {
        return RxJavaHooks.onComputationScheduler(getInstance().computationScheduler);
    }

    public static Scheduler io() {
        return RxJavaHooks.onIOScheduler(getInstance().ioScheduler);
    }

    public static void reset() {
        Schedulers andSet = INSTANCE.getAndSet(null);
        if (andSet != null) {
            andSet.shutdownInstance();
        }
    }

    synchronized void startInstance() {
        Object obj = this.computationScheduler;
        if (obj instanceof SchedulerLifecycle) {
            ((SchedulerLifecycle) obj).start();
        }
        Object obj2 = this.ioScheduler;
        if (obj2 instanceof SchedulerLifecycle) {
            ((SchedulerLifecycle) obj2).start();
        }
        Object obj3 = this.newThreadScheduler;
        if (obj3 instanceof SchedulerLifecycle) {
            ((SchedulerLifecycle) obj3).start();
        }
    }

    synchronized void shutdownInstance() {
        Object obj = this.computationScheduler;
        if (obj instanceof SchedulerLifecycle) {
            ((SchedulerLifecycle) obj).shutdown();
        }
        Object obj2 = this.ioScheduler;
        if (obj2 instanceof SchedulerLifecycle) {
            ((SchedulerLifecycle) obj2).shutdown();
        }
        Object obj3 = this.newThreadScheduler;
        if (obj3 instanceof SchedulerLifecycle) {
            ((SchedulerLifecycle) obj3).shutdown();
        }
    }

    public static Scheduler from(Executor executor) {
        return new ExecutorScheduler(executor);
    }
}
