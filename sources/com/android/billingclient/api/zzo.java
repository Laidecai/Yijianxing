package com.android.billingclient.api;

import android.os.Bundle;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzo implements Callable<Void> {
    final /* synthetic */ AcknowledgePurchaseParams zza;
    final /* synthetic */ AcknowledgePurchaseResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzo(BillingClientImpl billingClientImpl, AcknowledgePurchaseParams acknowledgePurchaseParams, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = acknowledgePurchaseParams;
        this.zzb = acknowledgePurchaseResponseListener;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Void call() throws Exception {
        try {
            Bundle bundleZzn = this.zzc.zzg.zzn(9, this.zzc.zzf.getPackageName(), this.zza.getPurchaseToken(), com.google.android.gms.internal.play_billing.zza.zzk(this.zza, this.zzc.zzb));
            this.zzc.zzB(new zzn(this, com.google.android.gms.internal.play_billing.zza.zzd(bundleZzn, "BillingClient"), com.google.android.gms.internal.play_billing.zza.zze(bundleZzn, "BillingClient")));
            return null;
        } catch (Exception e) {
            this.zzc.zzB(new zzm(this, e));
            return null;
        }
    }
}
