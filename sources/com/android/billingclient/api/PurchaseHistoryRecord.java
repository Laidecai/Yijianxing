package com.android.billingclient.api;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public class PurchaseHistoryRecord {
    private final String zza;
    private final String zzb;
    private final JSONObject zzc;

    public PurchaseHistoryRecord(String str, String str2) throws JSONException {
        this.zza = str;
        this.zzb = str2;
        this.zzc = new JSONObject(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PurchaseHistoryRecord)) {
            return false;
        }
        PurchaseHistoryRecord purchaseHistoryRecord = (PurchaseHistoryRecord) obj;
        return TextUtils.equals(this.zza, purchaseHistoryRecord.getOriginalJson()) && TextUtils.equals(this.zzb, purchaseHistoryRecord.getSignature());
    }

    public String getDeveloperPayload() {
        return this.zzc.optString("developerPayload");
    }

    public String getOriginalJson() {
        return this.zza;
    }

    public long getPurchaseTime() {
        return this.zzc.optLong("purchaseTime");
    }

    public String getPurchaseToken() {
        JSONObject jSONObject = this.zzc;
        return jSONObject.optString("token", jSONObject.optString("purchaseToken"));
    }

    public String getSignature() {
        return this.zzb;
    }

    public String getSku() {
        return this.zzc.optString("productId");
    }

    public int hashCode() {
        return this.zza.hashCode();
    }

    public String toString() {
        String strValueOf = String.valueOf(this.zza);
        return strValueOf.length() != 0 ? "PurchaseHistoryRecord. Json: ".concat(strValueOf) : new String("PurchaseHistoryRecord. Json: ");
    }
}
