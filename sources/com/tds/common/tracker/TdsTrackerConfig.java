package com.tds.common.tracker;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tds.common.log.constants.CommonParam;
import com.tds.common.tracker.constants.TrackerType;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.GUIDHelper;
import com.tds.common.utils.NetworkUtil;
import com.tds.common.utils.UIUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TdsTrackerConfig implements Parcelable {
    public static final Parcelable.Creator<TdsTrackerConfig> CREATOR = new Parcelable.Creator<TdsTrackerConfig>() { // from class: com.tds.common.tracker.TdsTrackerConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TdsTrackerConfig createFromParcel(Parcel parcel) {
            return new TdsTrackerConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TdsTrackerConfig[] newArray(int i) {
            return new TdsTrackerConfig[i];
        }
    };
    private static final int DEFAULT_GROUP_SIZE = 5;
    public final String accessKeyId;
    public final String accessKeySecret;
    public final String androidId;
    public final String appPackageName;
    public final String appVersion;
    public String appVersionCode;
    public String appVersionName;
    public final String cachePath;
    public String clientId;
    public final String endPoint;
    public final int groupSize;
    public final String logStore;
    public final String mobileType;
    public final String networkType;
    public final String osVersion;
    public final String platform;
    public final String project;
    public final String ramSize;
    public final String romSize;
    public final String screenSize;
    public final String sdkModel;
    public final String sdkVersionCode;
    public final String sdkVersionName;
    public final Map<String, String> tagsMap;
    public final String topic;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TdsTrackerConfig() {
        this.project = "";
        this.endPoint = "";
        this.logStore = "";
        this.accessKeyId = "";
        this.accessKeySecret = "";
        this.groupSize = 0;
        this.topic = "";
        this.tagsMap = new HashMap();
        this.sdkVersionCode = "";
        this.sdkVersionName = "";
        this.platform = "";
        this.osVersion = "";
        this.androidId = "";
        this.appVersion = "";
        this.cachePath = "";
        this.appPackageName = "";
        this.ramSize = "";
        this.romSize = "";
        this.networkType = "";
        this.mobileType = "";
        this.appVersionCode = "";
        this.appVersionName = "";
        this.clientId = "";
        this.sdkModel = "";
        this.screenSize = "";
    }

    public TdsTrackerConfig(Builder builder) {
        this.project = builder.project;
        this.endPoint = builder.endPoint;
        this.logStore = builder.logStore;
        this.accessKeyId = builder.accessKeyId;
        this.accessKeySecret = builder.accessKeySecret;
        this.groupSize = builder.groupSize;
        this.topic = TrackerType.getTrackerName(builder.trackerType);
        HashMap map = new HashMap();
        this.tagsMap = map;
        map.put(CommonParam.PLATFORM, DeviceUtils.getPlatform());
        if (TextUtils.isEmpty(builder.sdkVersionName)) {
            this.sdkVersionName = "";
        } else {
            this.sdkVersionName = builder.sdkVersionName;
        }
        if (builder.sdkVersionCode == -1) {
            this.sdkVersionCode = "";
        } else {
            this.sdkVersionCode = String.valueOf(builder.sdkVersionCode);
        }
        this.platform = DeviceUtils.getPlatform();
        this.osVersion = DeviceUtils.getOSVersion();
        this.androidId = builder.androidId;
        this.appVersion = builder.appVersion;
        this.cachePath = builder.cachePath;
        this.appPackageName = builder.appPackageName;
        this.ramSize = builder.ramSize;
        this.romSize = builder.romSize;
        this.networkType = builder.networkType;
        this.mobileType = builder.mobileType;
        this.appVersionName = builder.appVersionName;
        this.appVersionCode = builder.appVersionCode;
        this.clientId = builder.clientId;
        this.sdkModel = builder.sdkModel;
        this.screenSize = builder.screenSize;
    }

    public static class Builder {
        private String accessKeyId;
        private String accessKeySecret;
        private String androidId;
        private String appPackageName;
        private String appVersion;
        private String appVersionCode;
        private String appVersionName;
        private String cachePath;
        private String endPoint;
        private String logStore;
        private String mobileType;
        private String networkType;
        private String project;
        private String ramSize;
        private String romSize;
        private int trackerType;
        private int groupSize = 5;
        private int sdkVersionCode = -1;
        private String sdkVersionName = "";
        private String clientId = "";
        private String sdkModel = "";
        private String screenSize = "";

        public Builder withTrackerType(int i) {
            this.trackerType = i;
            return this;
        }

        public Builder withAccessKeyId(String str) {
            this.accessKeyId = str;
            this.clientId = str;
            return this;
        }

        public Builder withAccessKeySecret(String str) {
            if (str == null) {
                str = "";
            }
            this.accessKeySecret = str;
            return this;
        }

        public Builder withProjectName(String str) {
            this.project = str;
            return this;
        }

        public Builder withEndPoint(String str) {
            this.endPoint = str;
            return this;
        }

        public Builder withLogStore(String str) {
            this.logStore = str;
            return this;
        }

        public Builder withGroupSize(int i) {
            this.groupSize = i;
            return this;
        }

        public Builder withSdkVersion(int i) {
            this.sdkVersionCode = i;
            return this;
        }

        public Builder withSdkVersionName(String str) {
            this.sdkVersionName = str;
            return this;
        }

        public Builder withClientId(String str) {
            this.clientId = str;
            this.accessKeyId = str;
            return this;
        }

        public Builder withSDKModel(String str) {
            this.sdkModel = str;
            return this;
        }

        public TdsTrackerConfig build(Context context) {
            String str;
            if (context != null) {
                GUIDHelper.INSTANCE.init(context.getApplicationContext());
            }
            if (TextUtils.isEmpty(this.accessKeyId)) {
                str = "accessKeyId";
            } else if (this.accessKeySecret == null) {
                str = "accessKeySecret";
            } else if (TextUtils.isEmpty(this.project)) {
                str = "project";
            } else if (TextUtils.isEmpty(this.endPoint)) {
                str = "endPoint";
            } else {
                str = TextUtils.isEmpty(this.logStore) ? "logStore" : "";
            }
            if (context != null && !TextUtils.isEmpty(str)) {
                throw new RuntimeException("lack of parameter [" + str + "]");
            }
            if (context != null) {
                this.androidId = DeviceUtils.getAndroidId(context);
                this.appVersion = DeviceUtils.getPackageVersion(context);
                this.appVersionName = DeviceUtils.getPackageVersion(context);
                this.appVersionCode = String.valueOf(DeviceUtils.getPackageVersionCode(context));
                this.cachePath = context.getFilesDir() + "/" + TrackerType.getTrackerName(this.trackerType);
                this.appPackageName = context.getPackageName();
                this.ramSize = DeviceUtils.getRemainingRamSize(context);
                this.romSize = DeviceUtils.getRemainingRomSize();
                this.networkType = NetworkUtil.getConnectedType(context);
                this.mobileType = NetworkUtil.getNetworkType(context);
                this.screenSize = UIUtils.getScreenSizeInfo(context);
            }
            return new TdsTrackerConfig(this);
        }
    }

    protected TdsTrackerConfig(Parcel parcel) {
        this.project = parcel.readString();
        this.endPoint = parcel.readString();
        this.logStore = parcel.readString();
        this.accessKeyId = parcel.readString();
        this.accessKeySecret = parcel.readString();
        this.groupSize = parcel.readInt();
        this.topic = parcel.readString();
        HashMap map = new HashMap();
        this.tagsMap = map;
        parcel.readMap(map, getClass().getClassLoader());
        this.sdkVersionCode = parcel.readString();
        this.sdkVersionName = parcel.readString();
        this.platform = parcel.readString();
        this.osVersion = parcel.readString();
        this.androidId = parcel.readString();
        this.appVersion = parcel.readString();
        this.cachePath = parcel.readString();
        this.appPackageName = parcel.readString();
        this.ramSize = parcel.readString();
        this.romSize = parcel.readString();
        this.networkType = parcel.readString();
        this.mobileType = parcel.readString();
        this.clientId = parcel.readString();
        this.appVersionCode = parcel.readString();
        this.appVersionName = parcel.readString();
        this.sdkModel = parcel.readString();
        this.screenSize = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.project);
        parcel.writeString(this.endPoint);
        parcel.writeString(this.logStore);
        parcel.writeString(this.accessKeyId);
        parcel.writeString(this.accessKeySecret);
        parcel.writeInt(this.groupSize);
        parcel.writeString(this.topic);
        parcel.writeMap(this.tagsMap);
        parcel.writeString(this.sdkVersionCode);
        parcel.writeString(this.sdkVersionName);
        parcel.writeString(this.platform);
        parcel.writeString(this.osVersion);
        parcel.writeString(this.androidId);
        parcel.writeString(this.appVersion);
        parcel.writeString(this.cachePath);
        parcel.writeString(this.appPackageName);
        parcel.writeString(this.ramSize);
        parcel.writeString(this.romSize);
        parcel.writeString(this.networkType);
        parcel.writeString(this.mobileType);
        parcel.writeString(this.clientId);
        parcel.writeString(this.appVersionCode);
        parcel.writeString(this.appVersionName);
        parcel.writeString(this.sdkModel);
        parcel.writeString(this.screenSize);
    }
}
