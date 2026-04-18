package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzm implements Runnable {
    final /* synthetic */ Exception zza;
    final /* synthetic */ zzo zzb;

    zzm(zzo zzoVar, Exception exc) {
        this.zzb = zzoVar;
        this.zza = exc;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String strValueOf = String.valueOf(this.zza);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 32);
        sb.append("Error acknowledge purchase; ex: ");
        sb.append(strValueOf);
        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
        this.zzb.zzb.onAcknowledgePurchaseResponse(zzam.zzq);
    }
}
