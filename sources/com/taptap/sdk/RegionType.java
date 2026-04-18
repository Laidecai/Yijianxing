package com.taptap.sdk;

import com.tds.common.net.util.HostReplaceUtil;

/* JADX INFO: loaded from: classes.dex */
public enum RegionType {
    CN { // from class: com.taptap.sdk.RegionType.1
        @Override // com.taptap.sdk.RegionType
        public String targetActionName() {
            return "com.taptap.sdk.action";
        }

        @Override // com.taptap.sdk.RegionType
        public String basicInfoUrl() {
            return getOpenApiHost() + "/account/basic-info/v1?client_id=%s";
        }

        @Override // com.taptap.sdk.RegionType
        public String profileUrl() {
            return getOpenApiHost() + "/account/profile/v1?client_id=%s";
        }

        @Override // com.taptap.sdk.RegionType
        public String testQualificationUrl() {
            return getOpenApiHost() + "/test/user-status/v1?client_id=%s";
        }

        @Override // com.taptap.sdk.RegionType
        public String tokenUrl() {
            return getTokenHost() + "/oauth2/v1/token";
        }

        @Override // com.taptap.sdk.RegionType
        public String authorizeUrl() {
            return getWebHost() + "/authorize";
        }

        @Override // com.taptap.sdk.RegionType
        public String getOpenApiHost() {
            return HostReplaceUtil.getInstance().getReplacedHost("https://open.tapapis.cn");
        }

        @Override // com.taptap.sdk.RegionType
        public String getWebHost() {
            return HostReplaceUtil.getInstance().getReplacedHost("https://accounts.taptap.cn");
        }

        @Override // com.taptap.sdk.RegionType
        public String getTokenHost() {
            return HostReplaceUtil.getInstance().getReplacedHost("https://accounts.tapapis.cn");
        }
    },
    IO { // from class: com.taptap.sdk.RegionType.2
        @Override // com.taptap.sdk.RegionType
        public String targetActionName() {
            return "com.taptap.global.sdk.action";
        }

        @Override // com.taptap.sdk.RegionType
        public String basicInfoUrl() {
            return getOpenApiHost() + "/account/basic-info/v1?client_id=%s";
        }

        @Override // com.taptap.sdk.RegionType
        public String profileUrl() {
            return getOpenApiHost() + "/account/profile/v1?client_id=%s";
        }

        @Override // com.taptap.sdk.RegionType
        public String testQualificationUrl() {
            return getOpenApiHost() + "/test/user-status/v1?client_id=%s";
        }

        @Override // com.taptap.sdk.RegionType
        public String tokenUrl() {
            return getTokenHost() + "/oauth2/v1/token";
        }

        @Override // com.taptap.sdk.RegionType
        public String authorizeUrl() {
            return getWebHost() + "/authorize";
        }

        @Override // com.taptap.sdk.RegionType
        public String getOpenApiHost() {
            return HostReplaceUtil.getInstance().getReplacedHost("https://open.tapapis.com");
        }

        @Override // com.taptap.sdk.RegionType
        public String getWebHost() {
            return HostReplaceUtil.getInstance().getReplacedHost("https://accounts.taptap.io");
        }

        @Override // com.taptap.sdk.RegionType
        public String getTokenHost() {
            return HostReplaceUtil.getInstance().getReplacedHost("https://accounts.tapapis.com");
        }
    };

    public abstract String authorizeUrl();

    public abstract String basicInfoUrl();

    public abstract String getOpenApiHost();

    public abstract String getTokenHost();

    public abstract String getWebHost();

    public abstract String profileUrl();

    public abstract String targetActionName();

    public abstract String testQualificationUrl();

    public abstract String tokenUrl();
}
