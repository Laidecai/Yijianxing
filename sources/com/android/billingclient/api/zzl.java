package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzl implements Runnable {
    final /* synthetic */ PurchaseHistoryResponseListener zza;

    zzl(BillingClientImpl billingClientImpl, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        this.zza = purchaseHistoryResponseListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.onPurchaseHistoryResponse(zzam.zzr, null);
    }
}
