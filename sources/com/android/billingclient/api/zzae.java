package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzae implements Runnable {
    final /* synthetic */ BillingResult zza;
    final /* synthetic */ zzah zzb;

    zzae(zzah zzahVar, BillingResult billingResult) {
        this.zzb = zzahVar;
        this.zza = billingResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb.zzb) {
            if (this.zzb.zzd != null) {
                this.zzb.zzd.onBillingSetupFinished(this.zza);
            }
        }
    }
}
