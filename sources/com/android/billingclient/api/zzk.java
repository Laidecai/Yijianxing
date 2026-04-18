package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzk implements Callable<Void> {
    final /* synthetic */ String zza;
    final /* synthetic */ PurchaseHistoryResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzk(BillingClientImpl billingClientImpl, String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = str;
        this.zzb = purchaseHistoryResponseListener;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Void call() throws Exception {
        this.zzc.zzB(new zzj(this, BillingClientImpl.zzg(this.zzc, this.zza)));
        return null;
    }
}
