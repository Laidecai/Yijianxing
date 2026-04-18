package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public final class AccountIdentifiers {
    private final String zza;
    private final String zzb;

    AccountIdentifiers(String str, String str2) {
        this.zza = str;
        this.zzb = str2;
    }

    public String getObfuscatedAccountId() {
        return this.zza;
    }

    public String getObfuscatedProfileId() {
        return this.zzb;
    }
}
