package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzh implements Callable<Void> {
    final /* synthetic */ ConsumeParams zza;
    final /* synthetic */ ConsumeResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzh(BillingClientImpl billingClientImpl, ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = consumeParams;
        this.zzb = consumeResponseListener;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Void call() throws Exception {
        BillingClientImpl.zzf(this.zzc, this.zza, this.zzb);
        return null;
    }
}
