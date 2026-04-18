package com.tds.common.reactor.rxandroid.plugins;

import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.schedulers.Scheduler;

/* JADX INFO: loaded from: classes.dex */
public class RxAndroidSchedulersHook {
    private static final RxAndroidSchedulersHook DEFAULT_INSTANCE = new RxAndroidSchedulersHook();

    public Scheduler getMainThreadScheduler() {
        return null;
    }

    public Action0 onSchedule(Action0 action0) {
        return action0;
    }

    public static RxAndroidSchedulersHook getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }
}
