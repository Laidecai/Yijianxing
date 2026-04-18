package com.android.billingclient.api;

import android.text.TextUtils;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public final class zzas {
    private String zza;

    private zzas() {
    }

    /* synthetic */ zzas(zzar zzarVar) {
    }

    public final zzas zza(String str) {
        this.zza = str;
        return this;
    }

    public final zzat zzb() {
        if (TextUtils.isEmpty(this.zza)) {
            throw new IllegalArgumentException("SKU must be set.");
        }
        return new zzat(this.zza, null, null);
    }
}
