package com.android.billingclient.api;

import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzai {
    private final List<PurchaseHistoryRecord> zza;
    private final BillingResult zzb;

    zzai(BillingResult billingResult, List<PurchaseHistoryRecord> list) {
        this.zza = list;
        this.zzb = billingResult;
    }

    final BillingResult zza() {
        return this.zzb;
    }

    final List<PurchaseHistoryRecord> zzb() {
        return this.zza;
    }
}
