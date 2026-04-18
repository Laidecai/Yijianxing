package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzj implements Runnable {
    final /* synthetic */ zzai zza;
    final /* synthetic */ zzk zzb;

    zzj(zzk zzkVar, zzai zzaiVar) {
        this.zzb = zzkVar;
        this.zza = zzaiVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzb.onPurchaseHistoryResponse(this.zza.zza(), this.zza.zzb());
    }
}
