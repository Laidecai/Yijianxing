package com.tds.tapdb.wrapper;

import android.app.Activity;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.utils.EngineUtil;
import com.tds.tapdb.b.n;
import com.tds.tapdb.sdk.TapDB;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapDBServiceImpl implements TapDBService {

    class a implements TapDB.TapDBDataDynamicProperties {
        final /* synthetic */ TapDBDynamicProperties a;

        a(TapDBDynamicProperties tapDBDynamicProperties) {
            this.a = tapDBDynamicProperties;
        }

        @Override // com.tds.tapdb.sdk.TapDB.TapDBDataDynamicProperties
        public JSONObject getDynamicProperties() {
            try {
                return new JSONObject(this.a.getDynamicProperties());
            } catch (JSONException e) {
                e.printStackTrace();
                n.a("TapDBServiceImpl [registerDynamicProperties] method getDynamicProperties failed");
                return null;
            }
        }
    }

    class b implements TapDB.TapDBDataDynamicProperties {
        b() {
        }

        @Override // com.tds.tapdb.sdk.TapDB.TapDBDataDynamicProperties
        public JSONObject getDynamicProperties() {
            try {
                return new JSONObject(TapDBServiceImpl.GetDynamicProperties());
            } catch (JSONException e) {
                e.printStackTrace();
                n.a("TapDBServiceImpl [registerDynamicProperties] method getDynamicProperties failed");
                return null;
            }
        }
    }

    public static native String GetDynamicProperties();

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void clearStaticProperties() {
        n.a("TapDBServiceImpl [clearStaticProperties] method called");
        TapDB.clearStaticProperties();
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void clearUser() {
        n.a("TapDBServiceImpl [clearUser] method called");
        TapDB.clearUser();
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void closeFetchTapTapDeviceId() {
        TapDB.closeFetchTapTapDeviceId();
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void deviceAdd(String str) {
        n.a("TapDBServiceImpl [deviceAdd] method called");
        try {
            TapDB.deviceAdd(new JSONObject(str));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void deviceInitialize(String str) {
        n.a("TapDBServiceImpl [deviceInitialize] method called");
        try {
            TapDB.deviceInitialize(new JSONObject(str));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void deviceUpdate(String str) {
        n.a("TapDBServiceImpl [deviceUpdate] method called");
        try {
            TapDB.deviceUpdate(new JSONObject(str));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void enableLog(boolean z) {
        TapDB.enableLog(z);
        n.a("TapDBServiceImpl [enableLog] method called");
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void getTapTapDID(Activity activity, BridgeCallback bridgeCallback) {
        bridgeCallback.onResult(TapDB.getTapTapDID(activity));
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void init(Activity activity, String str, String str2) {
        n.a("TapDBServiceImpl [init(Activity activity, String clientId, String channel)] method called");
        TapDB.init(activity, str, str2);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void init(Activity activity, String str, String str2, String str3) {
        n.a("TapDBServiceImpl [init(Activity activity, String clientId, String channel, String gameVersion)] method called");
        TapDB.init(activity, str, str2, str3);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void init(Activity activity, String str, String str2, String str3, String str4) {
        n.a("TapDBServiceImpl [init(Activity activity, String clientId, String channel, String gameVersion, String properties)] method called");
        try {
            TapDB.init(activity, str, str2, str3, new JSONObject(str4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void init(Activity activity, String str, String str2, String str3, boolean z) {
        n.a("TapDBServiceImpl [init(Activity activity, String clientId, String channel, String gameVersion,boolean isCN)] method called");
        TapDB.init(activity, str, str2, str3, z);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void init(Activity activity, String str, String str2, boolean z) {
        n.a("TapDBServiceImpl [init(Activity activity, String clientId, String channel, boolean isCN)] method called");
        TapDB.init(activity, str, str2, z);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void onCharge(String str, String str2, long j, String str3, String str4) {
        n.a("TapDBServiceImpl [onCharge] method called");
        TapDB.onCharge(str, str2, j, str3, str4);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void onChargeWithProperties(String str, String str2, long j, String str3, String str4, String str5) {
        n.a("TapDBService [onChargeWithProperties] method called");
        try {
            TapDB.onCharge(str, str2, j, str3, str4, new JSONObject(str5));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    @Deprecated
    public void onEvent(String str, String str2) {
        n.a("TapDBServiceImpl [onEvent] method called");
        try {
            TapDB.onEvent(str, new JSONObject(str2));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void registerDynamicProperties(TapDBDynamicProperties tapDBDynamicProperties) {
        if (tapDBDynamicProperties == null) {
            n.a("TapDBServiceImpl [registerDynamicProperties] method getDynamicProperties failed");
        } else {
            n.a(String.format("TapDBServiceImpl [registerDynamicProperties] method called:%s", tapDBDynamicProperties.getDynamicProperties()));
            TapDB.registerDynamicProperties(new a(tapDBDynamicProperties));
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void registerDynamicPropertiesUE() {
        if (EngineUtil.isUnreal()) {
            TapDB.registerDynamicProperties(new b());
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void registerStaticProperties(String str) {
        n.a("TapDBServiceImpl [registerStaticProperties] method called");
        try {
            TapDB.registerStaticProperties(new JSONObject(str));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void setCustomEventHost(String str) {
        n.a("TapDBServiceImpl [setCustomEventHost] method called");
        TapDB.setCustomEventHost(str);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void setHost(String str) {
        n.a("TapDBServiceImpl [setHost] method called");
        TapDB.setHost(str);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void setLevel(int i) {
        n.a("TapDBServiceImpl [setLevel] method called");
        TapDB.setLevel(i);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void setName(String str) {
        n.a("TapDBServiceImpl [setName] method called");
        TapDB.setName(str);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void setServer(String str) {
        n.a("TapDBServiceImpl [setServer] method called");
        TapDB.setServer(str);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void setUser(String str) {
        n.a("TapDBServiceImpl [setUser(String userId)] method called");
        TapDB.setUser(str);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void setUser(String str, String str2) {
        n.a("TapDBServiceImpl [setUser(String userId, String loginType)] method called");
        if (TapDB.isTapEnable()) {
            TapDB.setUser(str, com.tds.tapdb.wrapper.a.a(str2));
            return;
        }
        try {
            TapDB.setUser(str, new JSONObject(str2));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void track(String str, String str2) {
        n.a("TapDBServiceImpl [track] method called");
        try {
            TapDB.trackEvent(str, new JSONObject(str2));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void trackEvent(String str, String str2) {
        n.a("TapDBServiceImpl [track] method called");
        try {
            TapDB.trackEvent(str, new JSONObject(str2));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void unregisterStaticProperty(String str) {
        n.a("TapDBServiceImpl [unregisterStaticProperty] method called");
        TapDB.unregisterStaticProperty(str);
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void userAdd(String str) {
        n.a("TapDBServiceImpl [userAdd] method called");
        try {
            TapDB.userAdd(new JSONObject(str));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void userInitialize(String str) {
        n.a("TapDBServiceImpl [userInitialize] method called");
        try {
            TapDB.userInitialize(new JSONObject(str));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }

    @Override // com.tds.tapdb.wrapper.TapDBService
    public void userUpdate(String str) {
        n.a("TapDBServiceImpl [userUpdate] method called");
        try {
            TapDB.userUpdate(new JSONObject(str));
        } catch (JSONException e) {
            n.a((Exception) e);
        }
    }
}
