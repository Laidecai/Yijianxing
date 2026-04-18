package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
/* JADX INFO: loaded from: classes.dex */
public final class BillingResult {
    private int zza;
    private String zzb;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@3.0.3 */
    public static class Builder {
        private int zza;
        private String zzb = "";

        private Builder() {
        }

        /* synthetic */ Builder(zzal zzalVar) {
        }

        public BillingResult build() {
            BillingResult billingResult = new BillingResult();
            billingResult.zza = this.zza;
            billingResult.zzb = this.zzb;
            return billingResult;
        }

        public Builder setDebugMessage(String str) {
            this.zzb = str;
            return this;
        }

        public Builder setResponseCode(int i) {
            this.zza = i;
            return this;
        }
    }

    public static Builder newBuilder() {
        return new Builder(null);
    }

    public String getDebugMessage() {
        return this.zzb;
    }

    public int getResponseCode() {
        return this.zza;
    }
}
