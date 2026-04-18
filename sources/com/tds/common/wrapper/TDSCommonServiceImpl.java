package com.tds.common.wrapper;

import android.app.Activity;
import com.alipay.sdk.util.j;
import com.tds.common.TapCommon;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.utils.BridgeJsonHelper;
import com.tds.common.entities.TapConfig;
import com.tds.common.localize.LocalizeManager;
import com.tds.common.localize.TapLanguage;
import com.tds.common.log.Logger;
import com.tds.common.log.constants.BusinessType;
import com.tds.common.net.PlatformXUA;
import com.tds.common.net.util.HostReplaceUtil;
import com.tds.common.region.TdsRegionHelper;
import com.tds.common.tracker.SdkDurationStatistics;
import com.tds.common.tracker.TdsTrackerConfig;
import com.tds.common.tracker.TdsTrackerManager;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.GUIDHelper;
import com.tds.common.utils.SP;
import com.tds.common.utils.TapGameUtil;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TDSCommonServiceImpl implements TDSCommonService {
    private static final String TAG = "TDSCommonServiceImpl";
    public static final String TRACKER_ENDPOINT_DOMESTIC = "openlog.tapapis.cn";
    public static final String TRACKER_ENDPOINT_IO = "openlog.tapapis.com";
    private Logger logger = Logger.get(BusinessType.COMMON_LOG);

    @Override // com.tds.common.wrapper.TDSCommonService
    public void init(Activity activity, String str, String str2) {
        TapConfig tapConfigConstructorTapConfig = TapConfig.constructorTapConfig(activity, str);
        if (tapConfigConstructorTapConfig == null) {
            return;
        }
        TapCommon.init(tapConfigConstructorTapConfig);
        if (!SP.inited()) {
            SP.initialize(activity);
        }
        try {
            int i = tapConfigConstructorTapConfig.regionType;
            String str3 = TRACKER_ENDPOINT_IO;
            if (i == 1) {
                this.logger.i("begin init networkTrackerManager");
                TdsTrackerManager.registerTracker(new TdsTrackerConfig.Builder().withTrackerType(3).withAccessKeyId(tapConfigConstructorTapConfig.clientId).withAccessKeySecret(tapConfigConstructorTapConfig.clientToken).withEndPoint(TRACKER_ENDPOINT_IO).withProjectName("tds").withLogStore("sdk-network").withSdkVersion(32900001).withSdkVersionName("3.29.0").build(activity.getApplicationContext()));
            }
            this.logger.i("begin init userEventTrackerManager");
            HostReplaceUtil hostReplaceUtil = HostReplaceUtil.getInstance();
            if (tapConfigConstructorTapConfig.regionType == 0) {
                str3 = "openlog.tapapis.cn";
            }
            TdsTrackerManager.registerTracker(new TdsTrackerConfig.Builder().withTrackerType(0).withAccessKeyId(tapConfigConstructorTapConfig.clientId).withAccessKeySecret(tapConfigConstructorTapConfig.clientToken).withEndPoint(hostReplaceUtil.getReplacedHost(str3)).withProjectName("tds").withLogStore("sdk-user-event").withSdkVersion(32900001).withSdkVersionName("3.29.0").build(activity.getApplicationContext()));
            this.logger.i("initTrackerManager completed");
        } catch (Exception e) {
            this.logger.e("initTrackerManager:" + e.getMessage());
        }
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void setXUA(String str) {
        this.logger.i(TAG, "setXUA:" + str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            HashMap map = new HashMap(jSONObject.length());
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.optString(next));
            }
            PlatformXUA.getInstance().setXuaMap(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void setPreferredLanguage(int i) {
        LocalizeManager.changeGameSelectedLanguage(TapLanguage.fromInt(i));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void getRegionCode(Activity activity, final BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getRegionCode");
        TdsRegionHelper.getRegionCode(activity, new TdsRegionHelper.RegionCallback() { // from class: com.tds.common.wrapper.TDSCommonServiceImpl.1
            @Override // com.tds.common.region.TdsRegionHelper.RegionCallback
            public void onRegion(boolean z) {
                HashMap map = new HashMap();
                map.put("isMainland", Boolean.valueOf(z));
                bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
            }
        });
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void isTapTapInstalled(Activity activity, BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "isTapTapInstalled");
        HashMap map = new HashMap();
        map.put("isTapTapInstalled", Boolean.valueOf(TapGameUtil.isTapTapInstalled(activity)));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void isTapGlobalInstalled(Activity activity, BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "isTapGlobalInstalled");
        HashMap map = new HashMap();
        map.put("isTapGlobalInstalled", Boolean.valueOf(TapGameUtil.isTapGlobalInstalled(activity)));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void updateGameInTapTap(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "updateGameInTapTap");
        HashMap map = new HashMap();
        map.put("updateGameInTapTap", Boolean.valueOf(TapGameUtil.updateGameInTapTap(activity, str)));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void updateGameInTapGlobal(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "updateGameInTapGlobal");
        HashMap map = new HashMap();
        map.put("updateGameInTapGlobal", Boolean.valueOf(TapGameUtil.updateGameInTapGlobal(activity, str)));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void openReviewInTapTap(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "openReviewInTapTap");
        HashMap map = new HashMap();
        map.put("openReviewInTapTap", Boolean.valueOf(TapGameUtil.openReviewInTapTap(activity, str)));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void openReviewInTapGlobal(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "openReviewInTapGlobal");
        HashMap map = new HashMap();
        map.put("openReviewInTapGlobal", Boolean.valueOf(TapGameUtil.openReviewInTapGlobal(activity, str)));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void addHost(String str, String str2) {
        HostReplaceUtil.getInstance().addReplacedHostPair(str, str2);
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void updateGameAndFailToWebInTapTap(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i("updateGameAndFailToWebInTapTap :" + str);
        boolean zUpdateGameAndFailToWebInTapTap = TapGameUtil.updateGameAndFailToWebInTapTap(activity, str);
        HashMap map = new HashMap(1);
        map.put(j.c, Boolean.valueOf(zUpdateGameAndFailToWebInTapTap));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void updateGameAndFailToWebInTapGlobal(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i("updateGameAndFailToWebInTapGlobal :" + str);
        boolean zUpdateGameAndFailToWebInTapGlobal = TapGameUtil.updateGameAndFailToWebInTapGlobal(activity, str);
        HashMap map = new HashMap(1);
        map.put(j.c, Boolean.valueOf(zUpdateGameAndFailToWebInTapGlobal));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void updateGameAndFailToWebInTapTap(Activity activity, String str, String str2, BridgeCallback bridgeCallback) {
        this.logger.i("updateGameAndFailToWebInTapTap :" + str);
        boolean zUpdateGameAndFailToWebInTapTap = TapGameUtil.updateGameAndFailToWebInTapTap(activity, str, str2);
        HashMap map = new HashMap(1);
        map.put(j.c, Boolean.valueOf(zUpdateGameAndFailToWebInTapTap));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void updateGameAndFailToWebInTapGlobal(Activity activity, String str, String str2, BridgeCallback bridgeCallback) {
        this.logger.i("updateGameAndFailToWebInTapGlobal :" + str);
        boolean zUpdateGameAndFailToWebInTapGlobal = TapGameUtil.updateGameAndFailToWebInTapGlobal(activity, str, str2);
        HashMap map = new HashMap(1);
        map.put(j.c, Boolean.valueOf(zUpdateGameAndFailToWebInTapGlobal));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void openWebDownloadUrlOfTapTap(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i("openWebDownloadUrlOfTapTap :" + str);
        boolean zOpenWebDownloadUrlOfTapTap = TapGameUtil.openWebDownloadUrlOfTapTap(activity, str);
        HashMap map = new HashMap(1);
        map.put(j.c, Boolean.valueOf(zOpenWebDownloadUrlOfTapTap));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void openWebDownloadUrlOfTapGlobal(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i("openWebDownloadUrlOfTapGlobal :" + str);
        boolean zOpenWebDownloadUrlOfTapGlobal = TapGameUtil.openWebDownloadUrlOfTapGlobal(activity, str);
        HashMap map = new HashMap(1);
        map.put(j.c, Boolean.valueOf(zOpenWebDownloadUrlOfTapGlobal));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void openWebDownloadUrl(Activity activity, String str, BridgeCallback bridgeCallback) {
        this.logger.i("openWebDownloadUrl :" + str);
        boolean zOpenWebDownloadUrl = TapGameUtil.openWebDownloadUrl(activity, str);
        HashMap map = new HashMap(1);
        map.put(j.c, Boolean.valueOf(zOpenWebDownloadUrl));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void registerProperties(String str, TapPropertiesProxy tapPropertiesProxy) {
        this.logger.i(TAG, "registerProperties");
        TapPropertiesHolder.INSTANCE.registerProperties(str, tapPropertiesProxy);
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void useNativeDataInCore(boolean z) {
        SdkDurationStatistics.setEnableNativeDataStatistics(z);
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void setDurationStatisticsEnabled(boolean z) {
        SdkDurationStatistics.setEnableSdkDurationStatistics(z);
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void getDeviceId(Activity activity, BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getDeviceId");
        if (!GUIDHelper.INSTANCE.initialized()) {
            GUIDHelper.INSTANCE.init(activity);
        }
        String uid = GUIDHelper.INSTANCE.getUID();
        HashMap map = new HashMap();
        map.put("deviceId", uid);
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [int] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    @Override // com.tds.common.wrapper.TDSCommonService
    public void getDeviceType(BridgeCallback bridgeCallback) {
        this.logger.i(TAG, "getDeviceType");
        ?? IsRunInSandbox = DeviceUtils.isRunInSandbox();
        if (DeviceUtils.isRunInCloud()) {
            IsRunInSandbox = 2;
        }
        HashMap map = new HashMap();
        map.put("deviceType", Integer.valueOf((int) IsRunInSandbox));
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(map));
    }

    @Override // com.tds.common.wrapper.TDSCommonService
    public void setCurrentTdsId(String str) {
        PlatformXUA.getInstance().setEngineTdsId(str);
    }
}
