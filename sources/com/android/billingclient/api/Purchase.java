package com.android.billingclient.api;

import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public class Purchase {
    private final String zza;
    private final String zzb;
    private final JSONObject zzc;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface PurchaseState {
        public static final int PENDING = 2;
        public static final int PURCHASED = 1;
        public static final int UNSPECIFIED_STATE = 0;
    }

    /* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
    public static class PurchasesResult {
        private final List<Purchase> zza;
        private final BillingResult zzb;

        public PurchasesResult(BillingResult billingResult, List<Purchase> list) {
            this.zza = list;
            this.zzb = billingResult;
        }

        public BillingResult getBillingResult() {
            return this.zzb;
        }

        public List<Purchase> getPurchasesList() {
            return this.zza;
        }

        public int getResponseCode() {
            return getBillingResult().getResponseCode();
        }
    }

    public Purchase(String str, String str2) throws JSONException {
        this.zza = str;
        this.zzb = str2;
        this.zzc = new JSONObject(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Purchase)) {
            return false;
        }
        Purchase purchase = (Purchase) obj;
        return TextUtils.equals(this.zza, purchase.getOriginalJson()) && TextUtils.equals(this.zzb, purchase.getSignature());
    }

    public AccountIdentifiers getAccountIdentifiers() {
        String strOptString = this.zzc.optString("obfuscatedAccountId");
        String strOptString2 = this.zzc.optString("obfuscatedProfileId");
        if (strOptString == null && strOptString2 == null) {
            return null;
        }
        return new AccountIdentifiers(strOptString, strOptString2);
    }

    public String getDeveloperPayload() {
        return this.zzc.optString("developerPayload");
    }

    public String getOrderId() {
        return this.zzc.optString("orderId");
    }

    public String getOriginalJson() {
        return this.zza;
    }

    public String getPackageName() {
        return this.zzc.optString("packageName");
    }

    public int getPurchaseState() {
        return this.zzc.optInt("purchaseState", 1) != 4 ? 1 : 2;
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

    public boolean isAcknowledged() {
        return this.zzc.optBoolean("acknowledged", true);
    }

    public boolean isAutoRenewing() {
        return this.zzc.optBoolean("autoRenewing");
    }

    public String toString() {
        String strValueOf = String.valueOf(this.zza);
        return strValueOf.length() != 0 ? "Purchase. Json: ".concat(strValueOf) : new String("Purchase. Json: ");
    }
}
