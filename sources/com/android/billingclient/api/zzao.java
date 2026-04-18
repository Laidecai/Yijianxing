package com.android.billingclient.api;

import android.os.Bundle;
import com.android.billingclient.api.BillingResult;
import java.util.ArrayList;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
final class zzao {
    static BillingResult zza(Bundle bundle, String str, String str2) {
        BillingResult billingResult = zzam.zzl;
        if (bundle == null) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", String.format("%s got null owned items list", str2));
            return billingResult;
        }
        int iZzd = com.google.android.gms.internal.play_billing.zza.zzd(bundle, "BillingClient");
        String strZze = com.google.android.gms.internal.play_billing.zza.zze(bundle, "BillingClient");
        BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
        builderNewBuilder.setResponseCode(iZzd);
        builderNewBuilder.setDebugMessage(strZze);
        BillingResult billingResultBuild = builderNewBuilder.build();
        if (iZzd != 0) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", String.format("%s failed. Response code: %s", str2, Integer.valueOf(iZzd)));
            return billingResultBuild;
        }
        if (!bundle.containsKey("INAPP_PURCHASE_ITEM_LIST") || !bundle.containsKey("INAPP_PURCHASE_DATA_LIST") || !bundle.containsKey("INAPP_DATA_SIGNATURE_LIST")) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", String.format("Bundle returned from %s doesn't contain required fields.", str2));
            return billingResult;
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
        ArrayList<String> stringArrayList2 = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
        ArrayList<String> stringArrayList3 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
        if (stringArrayList == null) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", String.format("Bundle returned from %s contains null SKUs list.", str2));
            return billingResult;
        }
        if (stringArrayList2 == null) {
            com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", String.format("Bundle returned from %s contains null purchases list.", str2));
            return billingResult;
        }
        if (stringArrayList3 != null) {
            return zzam.zzp;
        }
        com.google.android.gms.internal.play_billing.zza.zzb("BillingClient", String.format("Bundle returned from %s contains null signatures list.", str2));
        return billingResult;
    }
}
