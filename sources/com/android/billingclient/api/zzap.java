package com.android.billingclient.api;

import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzap {
    private final List<SkuDetails> zza;
    private final int zzb;
    private final String zzc;

    public zzap(int i, String str, List<SkuDetails> list) {
        this.zzb = i;
        this.zzc = str;
        this.zza = list;
    }

    public final List<SkuDetails> zza() {
        return this.zza;
    }

    public final int zzb() {
        return this.zzb;
    }

    public final String zzc() {
        return this.zzc;
    }
}
