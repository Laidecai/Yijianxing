package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public final class AcknowledgePurchaseParams {
    private String zza;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
    public static final class Builder {
        private String zza;

        private Builder() {
        }

        /* synthetic */ Builder(zza zzaVar) {
        }

        public AcknowledgePurchaseParams build() {
            if (this.zza == null) {
                throw new IllegalArgumentException("Purchase token must be set");
            }
            AcknowledgePurchaseParams acknowledgePurchaseParams = new AcknowledgePurchaseParams(null);
            acknowledgePurchaseParams.zza = this.zza;
            return acknowledgePurchaseParams;
        }

        public Builder setPurchaseToken(String str) {
            this.zza = str;
            return this;
        }
    }

    private AcknowledgePurchaseParams() {
    }

    /* synthetic */ AcknowledgePurchaseParams(zza zzaVar) {
    }

    public static Builder newBuilder() {
        return new Builder(null);
    }

    public String getPurchaseToken() {
        return this.zza;
    }
}
