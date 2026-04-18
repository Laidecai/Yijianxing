package com.android.billingclient.api;

import com.android.billingclient.api.BillingResult;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzac implements Runnable {
    final /* synthetic */ zzap zza;
    final /* synthetic */ zzad zzb;

    zzac(zzad zzadVar, zzap zzapVar) {
        this.zzb = zzadVar;
        this.zza = zzapVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SkuDetailsResponseListener skuDetailsResponseListener = this.zzb.zzc;
        BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
        builderNewBuilder.setResponseCode(this.zza.zzb());
        builderNewBuilder.setDebugMessage(this.zza.zzc());
        skuDetailsResponseListener.onSkuDetailsResponse(builderNewBuilder.build(), this.zza.zza());
    }
}
