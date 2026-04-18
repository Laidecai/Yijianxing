package com.tds.common.reactor.internal.schedulers;

import com.tds.common.reactor.functions.Func0;
import com.tds.common.reactor.plugins.RxJavaHooks;
import com.tds.common.reactor.util.RxThreadFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes.dex */
public enum GenericScheduledExecutorServiceFactory {
    ;

    static final String THREAD_NAME_PREFIX = "RxScheduledExecutorPool-";
    static final RxThreadFactory THREAD_FACTORY = new RxThreadFactory(THREAD_NAME_PREFIX);

    static ThreadFactory threadFactory() {
        return THREAD_FACTORY;
    }

    public static ScheduledExecutorService create() {
        Func0<? extends ScheduledExecutorService> onGenericScheduledExecutorService = RxJavaHooks.getOnGenericScheduledExecutorService();
        if (onGenericScheduledExecutorService == null) {
            return createDefault();
        }
        return onGenericScheduledExecutorService.call();
    }

    static ScheduledExecutorService createDefault() {
        return Executors.newScheduledThreadPool(1, threadFactory());
    }
}
