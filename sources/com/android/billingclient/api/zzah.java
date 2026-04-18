package com.android.billingclient.api;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzah implements ServiceConnection {
    final /* synthetic */ BillingClientImpl zza;
    private final Object zzb = new Object();
    private boolean zzc = false;
    private BillingClientStateListener zzd;

    /* synthetic */ zzah(BillingClientImpl billingClientImpl, BillingClientStateListener billingClientStateListener, zzs zzsVar) {
        this.zza = billingClientImpl;
        this.zzd = billingClientStateListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzf(BillingResult billingResult) {
        this.zza.zzB(new zzae(this, billingResult));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.google.android.gms.internal.play_billing.zza.zza("BillingClient", "Billing service connected.");
        this.zza.zzg = com.google.android.gms.internal.play_billing.zzc.zzo(iBinder);
        if (this.zza.zzz(new zzaf(this), 30000L, new zzag(this)) == null) {
            zzf(this.zza.zzC());
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", "Billing service disconnected.");
        this.zza.zzg = null;
        this.zza.zza = 0;
        synchronized (this.zzb) {
            BillingClientStateListener billingClientStateListener = this.zzd;
            if (billingClientStateListener != null) {
                billingClientStateListener.onBillingServiceDisconnected();
            }
        }
    }

    final void zza() {
        synchronized (this.zzb) {
            this.zzd = null;
            this.zzc = true;
        }
    }
}
