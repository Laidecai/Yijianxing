package com.tds.common.entities;

import android.text.TextUtils;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class TapBillboardConfig {
    public static final String TEMPLATE_IMAGE = "image";
    public static final String TEMPLATE_NAVIGATE = "navigate";
    public Set<Pair<String, String>> dimensionSet;
    public String serverUrl;

    @Deprecated
    public String template;

    public TapBillboardConfig(Builder builder) {
        this.dimensionSet = new HashSet();
        this.template = "";
        this.serverUrl = "";
        this.dimensionSet = builder.dimensionSet;
        this.template = builder.template;
        this.serverUrl = builder.serverUrl;
    }

    public void append(int i, TapBillboardConfig tapBillboardConfig) {
        if (tapBillboardConfig == null) {
            return;
        }
        if (i == 0 && !TextUtils.isEmpty(tapBillboardConfig.serverUrl)) {
            this.serverUrl = tapBillboardConfig.serverUrl;
        }
        Set<Pair<String, String>> set = tapBillboardConfig.dimensionSet;
        if (set == null || set.isEmpty()) {
            return;
        }
        this.dimensionSet = tapBillboardConfig.dimensionSet;
    }

    public static class Builder {
        private Set<Pair<String, String>> dimensionSet = new HashSet();
        private String serverUrl = "";
        private String template = TapBillboardConfig.TEMPLATE_NAVIGATE;

        public Builder withDimensionSet(Set<Pair<String, String>> set) {
            this.dimensionSet = set;
            return this;
        }

        public Builder withServerUrl(String str) {
            this.serverUrl = str;
            return this;
        }

        @Deprecated
        public Builder withTemplate(String str) {
            this.template = str;
            return this;
        }

        public TapBillboardConfig build() {
            return new TapBillboardConfig(this);
        }
    }
}
