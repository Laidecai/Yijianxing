package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzw implements Runnable {
    final /* synthetic */ Exception zza;
    final /* synthetic */ ConsumeResponseListener zzb;
    final /* synthetic */ String zzc;

    zzw(BillingClientImpl billingClientImpl, Exception exc, ConsumeResponseListener consumeResponseListener, String str) {
        this.zza = exc;
        this.zzb = consumeResponseListener;
        this.zzc = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String strValueOf = String.valueOf(this.zza);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 30);
        sb.append("Error consuming purchase; ex: ");
        sb.append(strValueOf);
        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", sb.toString());
        this.zzb.onConsumeResponse(zzam.zzq, this.zzc);
    }
}
