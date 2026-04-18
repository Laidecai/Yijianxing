package com.tds.common.reactor.rxandroid.schedulers;

import android.os.Looper;
import com.tds.common.reactor.rxandroid.plugins.RxAndroidPlugins;
import com.tds.common.reactor.schedulers.Scheduler;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class AndroidSchedulers {
    private static final AtomicReference<AndroidSchedulers> INSTANCE = new AtomicReference<>();
    private final Scheduler mainThreadScheduler;

    private static AndroidSchedulers getInstance() {
        AtomicReference<AndroidSchedulers> atomicReference;
        AndroidSchedulers androidSchedulers;
        do {
            atomicReference = INSTANCE;
            AndroidSchedulers androidSchedulers2 = atomicReference.get();
            if (androidSchedulers2 != null) {
                return androidSchedulers2;
            }
            androidSchedulers = new AndroidSchedulers();
        } while (!atomicReference.compareAndSet(null, androidSchedulers));
        return androidSchedulers;
    }

    private AndroidSchedulers() {
        Scheduler mainThreadScheduler = RxAndroidPlugins.getInstance().getSchedulersHook().getMainThreadScheduler();
        if (mainThreadScheduler != null) {
            this.mainThreadScheduler = mainThreadScheduler;
        } else {
            this.mainThreadScheduler = new LooperScheduler(Looper.getMainLooper());
        }
    }

    public static Scheduler mainThread() {
        return getInstance().mainThreadScheduler;
    }

    public static Scheduler from(Looper looper) {
        Objects.requireNonNull(looper, "looper == null");
        return new LooperScheduler(looper);
    }

    public static void reset() {
        INSTANCE.set(null);
    }
}
