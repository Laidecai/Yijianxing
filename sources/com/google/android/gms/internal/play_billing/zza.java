package com.google.android.gms.internal.play_billing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.zzat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public final class zza {
    public static final int zza = Runtime.getRuntime().availableProcessors();

    public static void zza(String str, String str2) {
        if (Log.isLoggable(str, 2)) {
            Log.v(str, str2);
        }
    }

    public static void zzb(String str, String str2) {
        if (Log.isLoggable(str, 5)) {
            Log.w(str, str2);
        }
    }

    public static BillingResult zzc(Intent intent, String str) {
        if (intent != null) {
            BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
            builderNewBuilder.setResponseCode(zzd(intent.getExtras(), str));
            builderNewBuilder.setDebugMessage(zze(intent.getExtras(), str));
            return builderNewBuilder.build();
        }
        zzb("BillingHelper", "Got null intent!");
        BillingResult.Builder builderNewBuilder2 = BillingResult.newBuilder();
        builderNewBuilder2.setResponseCode(6);
        builderNewBuilder2.setDebugMessage("An internal error occurred.");
        return builderNewBuilder2.build();
    }

    public static int zzd(Bundle bundle, String str) {
        if (bundle == null) {
            zzb(str, "Unexpected null bundle received!");
            return 6;
        }
        Object obj = bundle.get("RESPONSE_CODE");
        if (obj == null) {
            zza(str, "getResponseCodeFromBundle() got null response code, assuming OK");
            return 0;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        String strValueOf = String.valueOf(obj.getClass().getName());
        zzb(str, strValueOf.length() != 0 ? "Unexpected type for bundle response code: ".concat(strValueOf) : new String("Unexpected type for bundle response code: "));
        return 6;
    }

    public static String zze(Bundle bundle, String str) {
        if (bundle == null) {
            zzb(str, "Unexpected null bundle received!");
            return "";
        }
        Object obj = bundle.get("DEBUG_MESSAGE");
        if (obj == null) {
            zza(str, "getDebugMessageFromBundle() got null response code, assuming OK");
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        String strValueOf = String.valueOf(obj.getClass().getName());
        zzb(str, strValueOf.length() != 0 ? "Unexpected type for debug message: ".concat(strValueOf) : new String("Unexpected type for debug message: "));
        return "";
    }

    public static List<Purchase> zzf(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
        ArrayList<String> stringArrayList2 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
        ArrayList arrayList = new ArrayList();
        if (stringArrayList == null || stringArrayList2 == null) {
            zzb("BillingHelper", "Couldn't find purchase lists, trying to find single data.");
            Purchase purchaseZzl = zzl(bundle.getString("INAPP_PURCHASE_DATA"), bundle.getString("INAPP_DATA_SIGNATURE"));
            if (purchaseZzl == null) {
                zzb("BillingHelper", "Couldn't find single purchase data as well.");
                return null;
            }
            arrayList.add(purchaseZzl);
        } else {
            for (int i = 0; i < stringArrayList.size() && i < stringArrayList2.size(); i++) {
                Purchase purchaseZzl2 = zzl(stringArrayList.get(i), stringArrayList2.get(i));
                if (purchaseZzl2 != null) {
                    arrayList.add(purchaseZzl2);
                }
            }
        }
        return arrayList;
    }

    public static Bundle zzg(BillingFlowParams billingFlowParams, boolean z, boolean z2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("playBillingLibraryVersion", str);
        if (billingFlowParams.getReplaceSkusProrationMode() != 0) {
            bundle.putInt(BillingFlowParams.EXTRA_PARAM_KEY_REPLACE_SKUS_PRORATION_MODE, billingFlowParams.getReplaceSkusProrationMode());
        }
        if (!TextUtils.isEmpty(billingFlowParams.zzb())) {
            bundle.putString(BillingFlowParams.EXTRA_PARAM_KEY_ACCOUNT_ID, billingFlowParams.zzb());
        }
        if (!TextUtils.isEmpty(billingFlowParams.zzd())) {
            bundle.putString("obfuscatedProfileId", billingFlowParams.zzd());
        }
        if (billingFlowParams.getVrPurchaseFlow()) {
            bundle.putBoolean(BillingFlowParams.EXTRA_PARAM_KEY_VR, true);
        }
        if (!TextUtils.isEmpty(billingFlowParams.getOldSku())) {
            bundle.putStringArrayList(BillingFlowParams.EXTRA_PARAM_KEY_OLD_SKUS, new ArrayList<>(Arrays.asList(billingFlowParams.getOldSku())));
        }
        if (!TextUtils.isEmpty(billingFlowParams.getOldSkuPurchaseToken())) {
            bundle.putString(BillingFlowParams.EXTRA_PARAM_KEY_OLD_SKU_PURCHASE_TOKEN, billingFlowParams.getOldSkuPurchaseToken());
        }
        if (!TextUtils.isEmpty(null)) {
            bundle.putString("oldSkuPurchaseId", null);
        }
        if (!TextUtils.isEmpty(null)) {
            bundle.putString("paymentsPurchaseParams", null);
        }
        if (z && z2) {
            bundle.putBoolean("enablePendingPurchases", true);
        }
        return bundle;
    }

    public static Bundle zzh(boolean z, boolean z2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("playBillingLibraryVersion", str);
        if (z && z2) {
            bundle.putBoolean("enablePendingPurchases", true);
        }
        return bundle;
    }

    public static Bundle zzi(int i, boolean z, String str, String str2, ArrayList<zzat> arrayList) {
        Bundle bundle = new Bundle();
        if (i >= 9) {
            bundle.putString("playBillingLibraryVersion", str);
        }
        if (i >= 9 && z) {
            bundle.putBoolean("enablePendingPurchases", true);
        }
        if (i >= 14) {
            ArrayList<String> arrayList2 = new ArrayList<>();
            int size = arrayList.size();
            boolean z2 = false;
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.get(i2);
                arrayList2.add(null);
                z2 |= !TextUtils.isEmpty(null);
            }
            if (z2) {
                bundle.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList2);
            }
        }
        return bundle;
    }

    public static Bundle zzj(ConsumeParams consumeParams, boolean z, String str) {
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putString("playBillingLibraryVersion", str);
        }
        return bundle;
    }

    public static Bundle zzk(AcknowledgePurchaseParams acknowledgePurchaseParams, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("playBillingLibraryVersion", str);
        return bundle;
    }

    private static Purchase zzl(String str, String str2) {
        if (str == null || str2 == null) {
            zzb("BillingHelper", "Received a bad purchase data.");
            return null;
        }
        try {
            return new Purchase(str, str2);
        } catch (JSONException e) {
            String strValueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 47);
            sb.append("Got JSONException while parsing purchase data: ");
            sb.append(strValueOf);
            zzb("BillingHelper", sb.toString());
            return null;
        }
    }
}
