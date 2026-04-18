package com.android.billingclient.api;

import android.os.Bundle;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzaa implements Callable<Bundle> {
    final /* synthetic */ SkuDetails zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzaa(BillingClientImpl billingClientImpl, SkuDetails skuDetails, String str) {
        this.zzc = billingClientImpl;
        this.zza = skuDetails;
        this.zzb = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Bundle call() throws Exception {
        return this.zzc.zzg.zzc(3, this.zzc.zzf.getPackageName(), this.zza.getSku(), this.zzb, null);
    }
}
