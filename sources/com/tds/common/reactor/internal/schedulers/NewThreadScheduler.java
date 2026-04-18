package com.tds.common.reactor.internal.schedulers;

import com.tds.common.reactor.schedulers.Scheduler;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
public final class NewThreadScheduler extends Scheduler {
    private final ThreadFactory threadFactory;

    public NewThreadScheduler(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override // com.tds.common.reactor.schedulers.Scheduler
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(this.threadFactory);
    }
}
