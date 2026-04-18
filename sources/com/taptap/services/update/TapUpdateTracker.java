package com.taptap.services.update;

import android.content.Context;
import android.text.TextUtils;
import com.taptap.services.update.utils.Utils;
import com.tds.common.TapCommon;
import com.tds.common.net.util.HostReplaceUtil;
import com.tds.common.tracker.TdsTrackerConfig;
import com.tds.common.tracker.TdsTrackerManager;
import com.tds.common.tracker.model.RawDataModel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateTracker {
    private static final String ACTION_CLICK = "click";
    private static final String ACTION_OPEN_TAP_CLIENT = "tapItkAppOpen";
    private static final String ACTION_VIEW = "view";
    private static final String DIALOG_TYPE_CONFIRM = "tap_update_confirm_dialog";
    private static final String DIALOG_TYPE_DOWNLOAD = "tap_downloading_dialog";
    private static final String DIALOG_TYPE_INSTALL = "tap_install_dialog";
    private static final String DIALOG_TYPE_INSTALL_CONFIRM = "install_confirm_dialog";
    private static final String DIALOG_TYPE_NOT_INSTALL = "not_install_dialog";
    private static final String DIALOG_TYPE_REQUEST_FAIL = "request_failed_dialog";
    private static final String DIALOG_TYPE_UPDATE = "tap_update_dialog";
    public static final String OBJECT_TYPE_UPDATE = "tap_update";
    private static final String OBJECT_TYPE_UPDATE_CLOSE = "tap_update_close_but";
    public static final String OBJECT_TYPE_UPDATE_GO_TAP = "gototap_update_but";
    public static final String OBJECT_TYPE_UPDATE_INSTALL_TAP = "gototap_install_but";
    private static final String TAG = "TapUpdateTracker";
    public static final String TRACKER_ENDPOINT_DOMESTIC = "openlog.tapapis.cn";
    public static final String TRACKER_ENDPOINT_RND = "openlog.xdrnd.cn";
    private String chainId;
    private String clientId;
    private String clientToken;
    private volatile String currentSessionId;
    private String gameId;
    private int speedCount;

    public String getClientId() {
        if (TextUtils.isEmpty(this.clientId)) {
            try {
                this.clientId = TapCommon.getTapConfig().clientId;
            } catch (Exception unused) {
            }
        }
        return this.clientId;
    }

    public void setGameId(String str) {
        this.gameId = str;
    }

    public String getChainId() {
        if (TextUtils.isEmpty(this.chainId)) {
            this.chainId = UUID.randomUUID().toString();
        }
        return this.chainId;
    }

    public void clearChainId() {
        this.chainId = null;
    }

    public void initTracker(Context context, String str, String str2) {
        TdsTrackerManager.registerTracker(new TdsTrackerConfig.Builder().withTrackerType(5).withAccessKeyId(str).withClientId(str).withAccessKeySecret(str2).withEndPoint(HostReplaceUtil.getInstance().getReplacedHost("openlog.tapapis.cn")).withSDKModel(TapUpdateLogger.TAG).withProjectName("tds").withLogStore("tapsdk").withSdkVersion(32900001).withSdkVersionName("3.29.0").build(context.getApplicationContext()));
    }

    public void createSessionId() {
        this.currentSessionId = Utils.generateSessionId();
    }

    public String getSessionId() {
        if (TextUtils.isEmpty(this.currentSessionId)) {
            createSessionId();
        }
        return this.currentSessionId;
    }

    public void trackConfirmDialogVisible() {
        trackBase(ACTION_VIEW, DIALOG_TYPE_CONFIRM, null);
    }

    public void trackOpenTapClient(String str) {
        trackBase(ACTION_OPEN_TAP_CLIENT, str, null);
    }

    public void trackUpdateDialogVisible() {
        trackBase(ACTION_VIEW, DIALOG_TYPE_UPDATE, null);
    }

    public void trackInstallDialogVisible() {
        trackBase(ACTION_VIEW, DIALOG_TYPE_INSTALL, null);
    }

    public void trackDownloadDialogVisible() {
        trackBase(ACTION_VIEW, DIALOG_TYPE_DOWNLOAD, null);
    }

    public void trackRequestFailedDialogVisible() {
        trackBase(ACTION_VIEW, DIALOG_TYPE_REQUEST_FAIL, null);
    }

    public void trackInstallConfirmDialogVisible() {
        trackBase(ACTION_VIEW, DIALOG_TYPE_INSTALL_CONFIRM, null);
    }

    public void trackNotInstallDialogVisible() {
        trackBase(ACTION_VIEW, DIALOG_TYPE_NOT_INSTALL, null);
    }

    public void trackUpdateConfirmButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_CONFIRM);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "tap_update_confirm_but", map);
    }

    public void trackUpdateCancelButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_CONFIRM);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "tap_update_cancel_but", map);
    }

    public void trackUpdateCloseButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_DOWNLOAD);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", OBJECT_TYPE_UPDATE_CLOSE, map);
    }

    public void trackDownloadLinkButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_DOWNLOAD);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "tap_download_link_but", map);
    }

    public void trackToTapUpdateButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_UPDATE);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", OBJECT_TYPE_UPDATE_GO_TAP, map);
    }

    public void trackToTapUpdateCloseButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_UPDATE);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", OBJECT_TYPE_UPDATE_CLOSE, map);
    }

    public void trackInstallConfirmButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_INSTALL);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", OBJECT_TYPE_UPDATE_INSTALL_TAP, map);
    }

    public void trackInstallLinkButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_INSTALL);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "tap_install_link_but", map);
    }

    public void trackInstallCloseButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_INSTALL);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", OBJECT_TYPE_UPDATE_CLOSE, map);
    }

    public void trackDownloadRetryButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_DOWNLOAD);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "download_retry_but", map);
    }

    public void trackInstallConfirmCancelButtonClick(boolean z) {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", z ? DIALOG_TYPE_INSTALL_CONFIRM : DIALOG_TYPE_NOT_INSTALL);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "install_cancel_but", map);
    }

    public void trackInstallConfirmPositiveButtonClick(boolean z) {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", z ? DIALOG_TYPE_INSTALL_CONFIRM : DIALOG_TYPE_NOT_INSTALL);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", z ? "install_success_but" : "reinstall_but", map);
    }

    public void trackRequestFailedCancelButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_REQUEST_FAIL);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "install_cancel_but", map);
    }

    public void trackRequestFailedPositiveButtonClick() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("block", DIALOG_TYPE_REQUEST_FAIL);
            map.put("extra", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("click", "install_retry_but", map);
    }

    public void trackDownloadStart() {
        this.speedCount = 0;
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("chain_id", getChainId());
            map.put("args", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("tapItkApkDownload", null, map);
    }

    public void trackDownloadComplete() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("chain_id", getChainId());
            map.put("args", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("tapItkApkDownloadComplete", null, map);
    }

    public void trackDownloadFailed(int i, String str) {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("chain_id", getChainId());
            jSONObject.put("failed_code", i);
            jSONObject.put("failed_reason", str);
            map.put("args", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("tapItkApkDownloadFailed", null, map);
    }

    public void trackInstall(boolean z) {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("chain_id", getChainId());
            map.put("args", jSONObject.toString());
            jSONObject2.put("install_type", z ? "click_install" : "auto_install");
            map.put("extra", jSONObject2.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("tapItkApkInstall", null, map);
    }

    public void trackInstallComplete() {
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("chain_id", getChainId());
            map.put("args", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("tapItkApkInstallComplete", null, map);
        clearChainId();
    }

    public void trackDownloadingSpeed(String str, String str2, String str3) {
        int i = this.speedCount;
        this.speedCount = i + 1;
        if (i % 5 != 0) {
            return;
        }
        HashMap map = new HashMap();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("chain_id", getChainId());
            jSONObject.put("apk_size", str);
            jSONObject.put("download_size", str2);
            jSONObject.put("download_rate", str3);
            map.put("args", jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        trackBase("tapItkApkDownloading", null, map);
    }

    private void trackBase(String str, String str2, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("action", str);
        if (!TextUtils.isEmpty(str2)) {
            map.put("object_type", str2);
        }
        String string = map.get("extra");
        if (TextUtils.isEmpty(string)) {
            string = new JSONObject().toString();
        }
        try {
            JSONObject jSONObject = new JSONObject(string);
            jSONObject.put("update_id", this.currentSessionId);
            map.put("extra", jSONObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(this.gameId)) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("game_id", this.gameId);
                map.put("ctx", jSONObject2.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        TdsTrackerManager.getInstance().withTrackerType(5).withTrackerEvent(new TdsTrackerManager.TrackerEvent().withRawDataModel(new RawDataModel(map))).track();
    }

    public static TapUpdateTracker getInstance() {
        return Single.INSTANCE.tracker;
    }

    enum Single {
        INSTANCE;

        final TapUpdateTracker tracker = new TapUpdateTracker();

        Single() {
        }
    }
}
