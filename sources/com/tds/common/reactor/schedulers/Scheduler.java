package com.tds.common.reactor.schedulers;

import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.functions.Action0;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class Scheduler {
    public abstract Worker createWorker();

    public static abstract class Worker implements Subscription {
        public abstract Subscription schedule(Action0 action0);

        public abstract Subscription schedule(Action0 action0, long j, TimeUnit timeUnit);

        public long now() {
            return System.currentTimeMillis();
        }
    }

    public long now() {
        return System.currentTimeMillis();
    }
}
