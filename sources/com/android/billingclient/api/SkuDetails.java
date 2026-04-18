package com.android.billingclient.api;

import android.text.TextUtils;
import com.alipay.sdk.packet.e;
import com.alipay.sdk.widget.d;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public class SkuDetails {
    private final String zza;
    private final JSONObject zzb;

    public SkuDetails(String str) throws JSONException {
        this.zza = str;
        JSONObject jSONObject = new JSONObject(str);
        this.zzb = jSONObject;
        if (TextUtils.isEmpty(jSONObject.optString("productId"))) {
            throw new IllegalArgumentException("SKU cannot be empty.");
        }
        if (TextUtils.isEmpty(jSONObject.optString(e.r))) {
            throw new IllegalArgumentException("SkuType cannot be empty.");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SkuDetails) {
            return TextUtils.equals(this.zza, ((SkuDetails) obj).zza);
        }
        return false;
    }

    public String getDescription() {
        return this.zzb.optString("description");
    }

    public String getFreeTrialPeriod() {
        return this.zzb.optString("freeTrialPeriod");
    }

    public String getIconUrl() {
        return this.zzb.optString("iconUrl");
    }

    public String getIntroductoryPrice() {
        return this.zzb.optString("introductoryPrice");
    }

    public long getIntroductoryPriceAmountMicros() {
        return this.zzb.optLong("introductoryPriceAmountMicros");
    }

    public int getIntroductoryPriceCycles() {
        return this.zzb.optInt("introductoryPriceCycles");
    }

    public String getIntroductoryPricePeriod() {
        return this.zzb.optString("introductoryPricePeriod");
    }

    public String getOriginalJson() {
        return this.zza;
    }

    public String getOriginalPrice() {
        return this.zzb.has("original_price") ? this.zzb.optString("original_price") : getPrice();
    }

    public long getOriginalPriceAmountMicros() {
        return this.zzb.has("original_price_micros") ? this.zzb.optLong("original_price_micros") : getPriceAmountMicros();
    }

    public String getPrice() {
        return this.zzb.optString("price");
    }

    public long getPriceAmountMicros() {
        return this.zzb.optLong("price_amount_micros");
    }

    public String getPriceCurrencyCode() {
        return this.zzb.optString("price_currency_code");
    }

    public String getSku() {
        return this.zzb.optString("productId");
    }

    public String getSubscriptionPeriod() {
        return this.zzb.optString("subscriptionPeriod");
    }

    public String getTitle() {
        return this.zzb.optString(d.v);
    }

    public String getType() {
        return this.zzb.optString(e.r);
    }

    public int hashCode() {
        return this.zza.hashCode();
    }

    public String toString() {
        String strValueOf = String.valueOf(this.zza);
        return strValueOf.length() != 0 ? "SkuDetails: ".concat(strValueOf) : new String("SkuDetails: ");
    }

    public final String zza() {
        return this.zzb.optString("packageName");
    }

    final String zzb() {
        return this.zzb.optString("skuDetailsToken");
    }

    public String zzc() {
        return this.zzb.optString("offer_id");
    }

    public int zzd() {
        return this.zzb.optInt("offer_type");
    }
}
