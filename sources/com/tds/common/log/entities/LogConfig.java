package com.tds.common.log.entities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.taptap.sdk.TapLoginHelperActivity;
import com.tds.common.localize.LocalizeManager;
import com.tds.common.log.constants.CommonParam;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.GUIDHelper;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LogConfig {
    private static final int DEFAULT_GROUP_SIZE = 5;
    public final String deviceId;
    public final boolean enableUpload;
    private final String gamePackage;
    private final int gameVersionCode;
    private final String gameVersionName;
    public final int groupSize;
    public final String lang;
    public final String osVersion;
    public final String platform;
    public final String sdkName;
    public final int sdkVersionCode;
    public final String sdkVersionName;
    public final String source;
    public final Map<String, String> tagsMap;

    public LogConfig(Builder builder) {
        this.sdkName = builder.sdkName;
        this.source = builder.source;
        this.enableUpload = builder.enableUpload;
        String str = builder.platform;
        this.platform = str;
        String str2 = builder.osVersion;
        this.osVersion = str2;
        this.sdkVersionCode = builder.sdkVersionCode;
        this.sdkVersionName = builder.sdkVersionName;
        String str3 = builder.lang;
        this.lang = str3;
        String str4 = builder.deviceId;
        this.deviceId = str4;
        this.groupSize = builder.groupSize;
        String str5 = builder.gamePackage;
        this.gamePackage = str5;
        int i = builder.gameVersionCode;
        this.gameVersionCode = i;
        String str6 = builder.gameVersionName;
        this.gameVersionName = str6;
        HashMap map = new HashMap();
        this.tagsMap = map;
        map.put(CommonParam.PLATFORM, str);
        if (!TextUtils.isEmpty(str2)) {
            map.put(CommonParam.OS_VERSION, str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            map.put(CommonParam.LANG, str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            map.put(CommonParam.DEVIE_ID, str4);
        }
        if (!TextUtils.isEmpty(str5)) {
            map.put(CommonParam.GAME_ID, str5);
        }
        if (i != Integer.MIN_VALUE) {
            map.put(CommonParam.GAME_VERSION_CODE, String.valueOf(i));
        }
        if (TextUtils.isEmpty(str6)) {
            return;
        }
        map.put(CommonParam.GAME_VERSION_NAME, str6);
    }

    public static class Builder {
        private String sdkName = "";
        private String source = TapLoginHelperActivity.INTENT_KEY_SOURCE;
        private boolean enableUpload = false;
        private String platform = "";
        private String osVersion = "";
        private int sdkVersionCode = Integer.MIN_VALUE;
        private String sdkVersionName = "";
        private String lang = "";
        private String deviceId = "";
        private int groupSize = 5;
        private String gamePackage = "";
        private int gameVersionCode = Integer.MIN_VALUE;
        private String gameVersionName = "";

        public Builder withTopic(String str) {
            this.sdkName = str;
            return this;
        }

        public Builder withSource(String str) {
            this.source = str;
            return this;
        }

        public Builder withEnableUpload(boolean z) {
            this.enableUpload = z;
            return this;
        }

        public Builder withSdkVersionCode(int i) {
            this.sdkVersionCode = i;
            return this;
        }

        public Builder withSdkVersionName(String str) {
            this.sdkVersionName = str;
            return this;
        }

        public Builder withGroupSize(int i) {
            this.groupSize = i;
            return this;
        }

        public LogConfig build(Context context) {
            if (TextUtils.isEmpty(this.sdkName)) {
                throw new RuntimeException("topic not init");
            }
            if (context != null) {
                if (this.sdkVersionCode == Integer.MIN_VALUE) {
                    throw new RuntimeException("sdkVersionCode not init");
                }
                if (TextUtils.isEmpty(this.sdkVersionName)) {
                    throw new RuntimeException("sdkVersionName not init");
                }
                GUIDHelper.INSTANCE.init(context.getApplicationContext());
                this.deviceId = GUIDHelper.INSTANCE.getUID();
                this.osVersion = DeviceUtils.getOSVersion();
                this.platform = DeviceUtils.getPlatform();
                this.lang = LocalizeManager.getPreferredLanguageString();
                this.gamePackage = context.getPackageName();
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(this.gamePackage, 0);
                    this.gameVersionName = packageInfo.versionName;
                    this.gameVersionCode = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return new LogConfig(this);
        }
    }

    public String toString() {
        return "LogConfig{topic='" + this.sdkName + "', enableUpload=" + this.enableUpload + ", platform='" + this.platform + "', osVersion='" + this.osVersion + "', sdkVersionCode=" + this.sdkVersionCode + ", sdkVersionName='" + this.sdkVersionName + "', lang='" + this.lang + "', deviceId='" + this.deviceId + "'}";
    }
}
