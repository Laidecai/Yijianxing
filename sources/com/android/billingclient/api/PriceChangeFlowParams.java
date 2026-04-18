package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public class PriceChangeFlowParams {
    private SkuDetails zza;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
    public static class Builder {
        private SkuDetails zza;

        public PriceChangeFlowParams build() {
            if (this.zza == null) {
                throw new IllegalArgumentException("SkuDetails must be set");
            }
            PriceChangeFlowParams priceChangeFlowParams = new PriceChangeFlowParams();
            priceChangeFlowParams.zza = this.zza;
            return priceChangeFlowParams;
        }

        public Builder setSkuDetails(SkuDetails skuDetails) {
            this.zza = skuDetails;
            return this;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public SkuDetails getSkuDetails() {
        return this.zza;
    }
}
