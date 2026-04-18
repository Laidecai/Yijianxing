package com.tds.common.entities;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapConfig {
    public Context appContext;
    public String clientId;
    public String clientToken;
    public int regionType;
    public String serverUrl;
    public TapAntiAddictionConfig tapAntiAddictionConfig;
    public TapBillboardConfig tapBillboardConfig;
    public TapDBConfig tapDBConfig;
    public TapPaymentConfig tapPaymentConfig;

    private TapConfig(Builder builder) {
        this.clientId = builder.clientId;
        this.clientToken = builder.clientToken;
        this.serverUrl = builder.serverUrl;
        this.regionType = builder.regionType;
        this.appContext = builder.appContext;
        this.tapDBConfig = builder.tapDBConfig;
        this.tapPaymentConfig = builder.tapPaymentConfig;
        this.tapBillboardConfig = builder.tapBillboardConfig;
        this.tapAntiAddictionConfig = builder.tapAntiAddictionConfig;
    }

    public static TapConfig constructorTapConfig(Activity activity, String str) {
        TapDBConfig tapDBConfig;
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("dbConfig");
            if (jSONObjectOptJSONObject != null) {
                tapDBConfig = new TapDBConfig();
                String strOptString = jSONObjectOptJSONObject.optString("channel");
                String strOptString2 = jSONObjectOptJSONObject.optString("gameVersion");
                tapDBConfig.setEnable(jSONObjectOptJSONObject.optBoolean("enable", true));
                tapDBConfig.setChannel(strOptString);
                tapDBConfig.setGameVersion(strOptString2);
            } else {
                tapDBConfig = null;
            }
            return new Builder().withAppContext(activity).withClientId(jSONObject.optString("clientID")).withClientToken(jSONObject.optString("clientToken")).withRegionType(jSONObject.optBoolean("isCN") ? 0 : 1).withTapDBConfig(tapDBConfig).build();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void append(TapConfig tapConfig) {
        if (tapConfig == null) {
            return;
        }
        Context context = tapConfig.appContext;
        if (context != null) {
            this.appContext = context;
        }
        if (!TextUtils.isEmpty(tapConfig.clientId)) {
            this.clientId = tapConfig.clientId;
        }
        if (!TextUtils.isEmpty(tapConfig.clientToken)) {
            this.clientToken = tapConfig.clientToken;
        }
        if (!TextUtils.isEmpty(tapConfig.serverUrl)) {
            this.serverUrl = tapConfig.serverUrl;
        }
        TapDBConfig tapDBConfig = this.tapDBConfig;
        if (tapDBConfig == null) {
            this.tapDBConfig = tapConfig.tapDBConfig;
        } else {
            tapDBConfig.append(tapConfig.tapDBConfig);
        }
        TapPaymentConfig tapPaymentConfig = this.tapPaymentConfig;
        if (tapPaymentConfig == null) {
            this.tapPaymentConfig = tapConfig.tapPaymentConfig;
        } else {
            tapPaymentConfig.append(tapConfig.tapPaymentConfig);
        }
        TapBillboardConfig tapBillboardConfig = this.tapBillboardConfig;
        if (tapBillboardConfig == null) {
            this.tapBillboardConfig = tapConfig.tapBillboardConfig;
        } else {
            tapBillboardConfig.append(this.regionType, tapConfig.tapBillboardConfig);
        }
        TapAntiAddictionConfig tapAntiAddictionConfig = this.tapAntiAddictionConfig;
        if (tapAntiAddictionConfig == null) {
            this.tapAntiAddictionConfig = tapConfig.tapAntiAddictionConfig;
        } else {
            tapAntiAddictionConfig.append(tapConfig.tapAntiAddictionConfig);
        }
    }

    public static class Builder {
        private Context appContext;
        private String clientId;
        private String clientToken;
        private String serverUrl;
        private int regionType = 0;
        private TapDBConfig tapDBConfig = null;
        private TapPaymentConfig tapPaymentConfig = null;
        private TapBillboardConfig tapBillboardConfig = null;
        private TapAntiAddictionConfig tapAntiAddictionConfig = null;

        public Builder withClientId(String str) {
            this.clientId = str;
            return this;
        }

        public Builder withClientToken(String str) {
            this.clientToken = str;
            return this;
        }

        public Builder withServerUrl(String str) {
            this.serverUrl = str;
            return this;
        }

        public Builder withRegionType(int i) {
            this.regionType = i;
            return this;
        }

        public Builder withAppContext(Context context) {
            this.appContext = context.getApplicationContext();
            return this;
        }

        public Builder withTapDBConfig(TapDBConfig tapDBConfig) {
            this.tapDBConfig = tapDBConfig;
            return this;
        }

        public Builder withTapPaymentConfig(TapPaymentConfig tapPaymentConfig) {
            this.tapPaymentConfig = tapPaymentConfig;
            return this;
        }

        public Builder withBillboardConfig(TapBillboardConfig tapBillboardConfig) {
            this.tapBillboardConfig = tapBillboardConfig;
            return this;
        }

        public Builder withAntiAddictionConfig(TapAntiAddictionConfig tapAntiAddictionConfig) {
            this.tapAntiAddictionConfig = tapAntiAddictionConfig;
            return this;
        }

        public TapConfig build() {
            return new TapConfig(this);
        }
    }

    public String toString() {
        return "TapConfig{clientId='" + this.clientId + "', clientToken='" + this.clientToken + "', serverUrl='" + this.serverUrl + "', regionType=" + this.regionType + ", appContext=" + this.appContext + ", tapDBConfig=" + this.tapDBConfig.toString() + '}';
    }
}
