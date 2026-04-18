package com.android.billingclient.api;

import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public interface SkuDetailsResponseListener {
    void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list);
}
