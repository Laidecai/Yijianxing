package com.android.billingclient.api;

import com.android.billingclient.api.BillingResult;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzn implements Runnable {
    final /* synthetic */ int zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzo zzc;

    zzn(zzo zzoVar, int i, String str) {
        this.zzc = zzoVar;
        this.zza = i;
        this.zzb = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = this.zzc.zzb;
        BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
        builderNewBuilder.setResponseCode(this.zza);
        builderNewBuilder.setDebugMessage(this.zzb);
        acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(builderNewBuilder.build());
    }
}
