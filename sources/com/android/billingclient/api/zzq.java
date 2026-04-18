package com.android.billingclient.api;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzq implements ThreadFactory {
    private final ThreadFactory zza = Executors.defaultThreadFactory();
    private final AtomicInteger zzb = new AtomicInteger(1);

    zzq(BillingClientImpl billingClientImpl) {
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread threadNewThread = this.zza.newThread(runnable);
        int andIncrement = this.zzb.getAndIncrement();
        StringBuilder sb = new StringBuilder(30);
        sb.append("PlayBillingLibrary-");
        sb.append(andIncrement);
        threadNewThread.setName(sb.toString());
        return threadNewThread;
    }
}
