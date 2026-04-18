package com.android.billingclient.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzd extends BroadcastReceiver {
    final /* synthetic */ zze zza;
    private final PurchasesUpdatedListener zzb;
    private boolean zzc;

    /* synthetic */ zzd(zze zzeVar, PurchasesUpdatedListener purchasesUpdatedListener, zzc zzcVar) {
        this.zza = zzeVar;
        this.zzb = purchasesUpdatedListener;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.zzb.onPurchasesUpdated(com.google.android.gms.internal.play_billing.zza.zzc(intent, "BillingBroadcastManager"), com.google.android.gms.internal.play_billing.zza.zzf(intent.getExtras()));
    }

    public final void zza(Context context, IntentFilter intentFilter) {
        if (this.zzc) {
            return;
        }
        context.registerReceiver(this.zza.zzb, intentFilter);
        this.zzc = true;
    }

    public final void zzb(Context context) {
        if (!this.zzc) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingBroadcastManager", "Receiver is not registered.");
        } else {
            context.unregisterReceiver(this.zza.zzb);
            this.zzc = false;
        }
    }
}
