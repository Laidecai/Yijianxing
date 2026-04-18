package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzg implements Runnable {
    final /* synthetic */ SkuDetailsResponseListener zza;

    zzg(BillingClientImpl billingClientImpl, SkuDetailsResponseListener skuDetailsResponseListener) {
        this.zza = skuDetailsResponseListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.onSkuDetailsResponse(zzam.zzr, null);
    }
}
