package com.tds.common.net;

import android.text.TextUtils;
import com.alipay.sdk.sys.a;
import com.tds.common.localize.LocalizeManager;
import com.tds.common.net.constant.Constants;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.GUIDHelper;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class XUAParams {
    private final String deviceId;
    private final String lang;
    private final String osVersion;
    private final String platform;
    private final String sdkName;
    private final int sdkVersionCode;
    private final String sdkVersionName;
    private TapDBDataObserver tapDBDataObserver;

    public interface TapDBDataObserver {
        String getTapDBDeviceId();
    }

    public XUAParams(Builder builder) {
        this.platform = builder.platform;
        this.osVersion = builder.osVersion;
        this.sdkName = builder.sdkName;
        this.sdkVersionCode = builder.sdkVersionCode;
        this.sdkVersionName = builder.sdkVersionName;
        this.lang = builder.lang;
        this.deviceId = builder.deviceId;
    }

    public void setTapDBDataObserver(TapDBDataObserver tapDBDataObserver) {
        this.tapDBDataObserver = tapDBDataObserver;
    }

    public static XUAParams getCommonXUAParams(String str, int i, String str2) {
        return new Builder().headersPlatform(DeviceUtils.getPlatform()).headersOSVersion(DeviceUtils.getOSVersion()).headersDeviceId(GUIDHelper.INSTANCE.getUID()).headersLang(LocalizeManager.getPreferredLanguageString()).headersSdkName(str).headersSdkVersionCode(i).headersSdkVersionName(str2).build();
    }

    public static final class Builder {
        private String deviceId;
        private String lang;
        private String osVersion;
        private String platform;
        private String sdkName;
        private int sdkVersionCode = Integer.MIN_VALUE;
        private String sdkVersionName;

        public Builder headersPlatform(String str) {
            this.platform = str;
            return this;
        }

        public Builder headersOSVersion(String str) {
            this.osVersion = str;
            return this;
        }

        public Builder headersSdkName(String str) {
            this.sdkName = str;
            return this;
        }

        public Builder headersSdkVersionCode(int i) {
            this.sdkVersionCode = i;
            return this;
        }

        public Builder headersSdkVersionName(String str) {
            this.sdkVersionName = str;
            return this;
        }

        public Builder headersLang(String str) {
            this.lang = str;
            return this;
        }

        public Builder headersDeviceId(String str) {
            this.deviceId = str;
            return this;
        }

        public XUAParams build() {
            return new XUAParams(this);
        }
    }

    public String getXUAValue() {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.platform)) {
            map.put(Constants.HTTP_COMMON_HEADERS.PLATFORM, this.platform);
        }
        if (!TextUtils.isEmpty(this.osVersion)) {
            map.put(Constants.HTTP_COMMON_HEADERS.OS_VERSION, this.osVersion);
        }
        if (!TextUtils.isEmpty(this.sdkName)) {
            map.put(Constants.HTTP_COMMON_HEADERS.SDK_NAME, this.sdkName);
        }
        int i = this.sdkVersionCode;
        if (i != Integer.MIN_VALUE) {
            map.put(Constants.HTTP_COMMON_HEADERS.SDK_VERSION_CODE, String.valueOf(i));
        }
        if (!TextUtils.isEmpty(this.sdkVersionName)) {
            map.put(Constants.HTTP_COMMON_HEADERS.SDK_VERSION_NAME, this.sdkVersionName);
        }
        map.put(Constants.HTTP_COMMON_HEADERS.LANG, LocalizeManager.getPreferredLanguageString());
        TapDBDataObserver tapDBDataObserver = this.tapDBDataObserver;
        String tapDBDeviceId = tapDBDataObserver != null ? tapDBDataObserver.getTapDBDeviceId() : "";
        if (!TextUtils.isEmpty(tapDBDeviceId)) {
            map.put(Constants.HTTP_COMMON_HEADERS.DEVICE_ID, tapDBDeviceId);
        } else if (!TextUtils.isEmpty(this.deviceId)) {
            map.put(Constants.HTTP_COMMON_HEADERS.DEVICE_ID, this.deviceId);
        }
        Map<String, String> map2 = PlatformXUA.getInstance().xuaMap;
        if (map2 != null && map2.size() > 0) {
            map.putAll(map2);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append(a.k);
            }
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append((String) entry.getValue());
        }
        return sb.toString();
    }
}
