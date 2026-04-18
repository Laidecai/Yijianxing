package com.tds.common.reactor.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class RxThreadFactory extends AtomicLong implements ThreadFactory {
    public static final ThreadFactory NONE = new ThreadFactory() { // from class: com.tds.common.reactor.util.RxThreadFactory.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            throw new AssertionError("No threads allowed.");
        }
    };
    private static final long serialVersionUID = -8841098858898482335L;
    final String prefix;

    public RxThreadFactory(String str) {
        this.prefix = str;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, this.prefix + incrementAndGet());
        thread.setDaemon(true);
        return thread;
    }
}
