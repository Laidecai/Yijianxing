package com.android.billingclient.api;

import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public interface PurchasesUpdatedListener {
    void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list);
}
