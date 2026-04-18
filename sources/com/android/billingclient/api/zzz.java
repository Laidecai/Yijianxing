package com.android.billingclient.api;

import android.os.Bundle;
import com.android.billingclient.api.BillingClient;
import java.util.Arrays;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzz implements Callable<Bundle> {
    final /* synthetic */ BillingFlowParams zza;
    final /* synthetic */ SkuDetails zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzz(BillingClientImpl billingClientImpl, BillingFlowParams billingFlowParams, SkuDetails skuDetails) {
        this.zzc = billingClientImpl;
        this.zza = billingFlowParams;
        this.zzb = skuDetails;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Bundle call() throws Exception {
        return this.zzc.zzg.zzf(5, this.zzc.zzf.getPackageName(), Arrays.asList(this.zza.getOldSku()), this.zzb.getSku(), BillingClient.SkuType.SUBS, null);
    }
}
