package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzp implements Runnable {
    final /* synthetic */ AcknowledgePurchaseResponseListener zza;

    zzp(BillingClientImpl billingClientImpl, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        this.zza = acknowledgePurchaseResponseListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.onAcknowledgePurchaseResponse(zzam.zzr);
    }
}
