package com.android.billingclient.api;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.android.billingclient.api.BillingResult;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzx extends ResultReceiver {
    final /* synthetic */ PriceChangeConfirmationListener zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzx(BillingClientImpl billingClientImpl, Handler handler, PriceChangeConfirmationListener priceChangeConfirmationListener) {
        super(handler);
        this.zza = priceChangeConfirmationListener;
    }

    @Override // android.os.ResultReceiver
    public final void onReceiveResult(int i, Bundle bundle) {
        BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
        builderNewBuilder.setResponseCode(i);
        builderNewBuilder.setDebugMessage(com.google.android.gms.internal.play_billing.zza.zze(bundle, "BillingClient"));
        this.zza.onPriceChangeConfirmationResult(builderNewBuilder.build());
    }
}
