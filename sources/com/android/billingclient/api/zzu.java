package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzu implements Runnable {
    final /* synthetic */ ConsumeResponseListener zza;
    final /* synthetic */ BillingResult zzb;
    final /* synthetic */ String zzc;

    zzu(BillingClientImpl billingClientImpl, ConsumeResponseListener consumeResponseListener, BillingResult billingResult, String str) {
        this.zza = consumeResponseListener;
        this.zzb = billingResult;
        this.zzc = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Successfully consumed purchase.");
        this.zza.onConsumeResponse(this.zzb, this.zzc);
    }
}
