package com.android.billingclient.api;

import android.os.Bundle;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzt implements Callable<Integer> {
    final /* synthetic */ String zza;
    final /* synthetic */ BillingClientImpl zzb;

    zzt(BillingClientImpl billingClientImpl, String str) {
        this.zzb = billingClientImpl;
        this.zza = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Integer call() throws Exception {
        com.google.android.gms.internal.play_billing.zzd zzdVar = this.zzb.zzg;
        String packageName = this.zzb.zzf.getPackageName();
        String str = this.zza;
        Bundle bundle = new Bundle();
        bundle.putBoolean(BillingFlowParams.EXTRA_PARAM_KEY_VR, true);
        return Integer.valueOf(zzdVar.zzi(7, packageName, str, bundle));
    }
}
