package com.tds.common.entities;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class TapPaymentConfig {
    private String language;
    private String regionId;
    private String wxAuthorizedDomainName;

    public String getRegionId() {
        return this.regionId;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getWxAuthorizedDomainName() {
        return this.wxAuthorizedDomainName;
    }

    private TapPaymentConfig(Builder builder) {
        this.regionId = builder.regionId;
        this.language = builder.language;
        this.wxAuthorizedDomainName = builder.wxAuthorizedDomainName;
    }

    public void append(TapPaymentConfig tapPaymentConfig) {
        if (tapPaymentConfig == null) {
            return;
        }
        if (!TextUtils.isEmpty(tapPaymentConfig.wxAuthorizedDomainName)) {
            this.wxAuthorizedDomainName = tapPaymentConfig.wxAuthorizedDomainName;
        }
        if (!TextUtils.isEmpty(tapPaymentConfig.language)) {
            this.language = tapPaymentConfig.language;
        }
        if (TextUtils.isEmpty(tapPaymentConfig.regionId)) {
            return;
        }
        this.regionId = tapPaymentConfig.regionId;
    }

    public static class Builder {
        private String language = "";
        private String regionId;
        private String wxAuthorizedDomainName;

        public Builder withRegionId(String str) {
            this.regionId = str;
            return this;
        }

        public Builder withLanguage(String str) {
            this.language = str;
            return this;
        }

        public Builder withWXAuthorizedDomainName(String str) {
            this.wxAuthorizedDomainName = str;
            return this;
        }

        public TapPaymentConfig build() {
            return new TapPaymentConfig(this);
        }
    }

    public String toString() {
        return "TapPaymentConfig{regionId='" + this.regionId + "', language='" + this.language + "', wxAuthorizedDomainName='" + this.wxAuthorizedDomainName + "'}";
    }
}
