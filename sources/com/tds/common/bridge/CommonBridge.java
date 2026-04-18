package com.tds.common.bridge;

import com.tds.common.tracker.TdsTrackerHandler;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.GUIDHelper;
import com.unity3d.player.UnityPlayer;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CommonBridge {
    public static String getDeviceId() {
        if (!GUIDHelper.INSTANCE.initialized()) {
            GUIDHelper.INSTANCE.init(UnityPlayer.currentActivity);
        }
        return GUIDHelper.INSTANCE.getUID();
    }

    public static int getDeviceType() {
        boolean zIsRunInSandbox = DeviceUtils.isRunInSandbox();
        if (DeviceUtils.isRunInCloud()) {
            return 2;
        }
        return zIsRunInSandbox ? 1 : 0;
    }

    public static String GetOpenLogCommonParams() {
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry<String, String> entry : TdsTrackerHandler.getCommonParams(UnityPlayer.currentActivity, "").entrySet()) {
                jSONObject.put(entry.getKey(), entry.getValue());
            }
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }
}
