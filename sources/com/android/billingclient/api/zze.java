package com.android.billingclient.api;

import android.content.Context;
import android.content.IntentFilter;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zze {
    private final Context zza;
    private final zzd zzb;

    zze(Context context, PurchasesUpdatedListener purchasesUpdatedListener) {
        this.zza = context;
        this.zzb = new zzd(this, purchasesUpdatedListener, null);
    }

    final void zza() {
        this.zzb.zza(this.zza, new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED"));
    }

    final PurchasesUpdatedListener zzb() {
        return this.zzb.zzb;
    }

    final void zzc() {
        this.zzb.zzb(this.zza);
    }
}
