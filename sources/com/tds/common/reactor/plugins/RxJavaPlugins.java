package com.tds.common.reactor.plugins;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class RxJavaPlugins {
    private final AtomicReference<RxJavaErrorHandler> errorHandler = new AtomicReference<>();
    private final AtomicReference<RxJavaObservableExecutionHook> observableExecutionHook = new AtomicReference<>();
    private final AtomicReference<RxJavaSchedulersHook> schedulersHook = new AtomicReference<>();
    private static final RxJavaPlugins INSTANCE = new RxJavaPlugins();
    static final RxJavaErrorHandler DEFAULT_ERROR_HANDLER = new RxJavaErrorHandler() { // from class: com.tds.common.reactor.plugins.RxJavaPlugins.1
    };

    public void reset() {
        RxJavaPlugins rxJavaPlugins = INSTANCE;
        rxJavaPlugins.errorHandler.set(null);
        rxJavaPlugins.observableExecutionHook.set(null);
        rxJavaPlugins.schedulersHook.set(null);
    }

    @Deprecated
    public static RxJavaPlugins getInstance() {
        return INSTANCE;
    }

    public RxJavaErrorHandler getErrorHandler() {
        if (this.errorHandler.get() == null) {
            this.errorHandler.compareAndSet(null, DEFAULT_ERROR_HANDLER);
        }
        return this.errorHandler.get();
    }

    public RxJavaObservableExecutionHook getObservableExecutionHook() {
        if (this.observableExecutionHook.get() == null) {
            this.observableExecutionHook.compareAndSet(null, RxJavaObservableExecutionHookDefault.getInstance());
        }
        return this.observableExecutionHook.get();
    }

    public RxJavaSchedulersHook getSchedulersHook() {
        if (this.schedulersHook.get() == null) {
            this.schedulersHook.compareAndSet(null, RxJavaSchedulersHook.getDefaultInstance());
        }
        return this.schedulersHook.get();
    }
}
