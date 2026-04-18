package com.tds.common.account;

import android.util.Log;
import com.tds.common.net.PlatformXUA;

/* JADX INFO: loaded from: classes.dex */
public class AccountUtil {
    public static String getCurrentTdsId() {
        try {
            Class.forName("com.unity3d.player.UnityPlayerActivity");
            return PlatformXUA.getInstance().getEngineTdsId();
        } catch (Throwable unused) {
            Log.i("Common", "current is not in unity , use native tds account");
            try {
                Class<?> cls = Class.forName("com.tapsdk.bootstrap.account.TDSUser");
                Object objInvoke = cls.getMethod("currentUser", new Class[0]).invoke(null, new Object[0]);
                return objInvoke != null ? cls.getMethod("getObjectId", new Class[0]).invoke(objInvoke, new Object[0]).toString() : "";
            } catch (Throwable th) {
                Log.i("Common", "get tds id failed ：" + th.getMessage());
                return "";
            }
        }
    }

    public static String getCurrentTapId() {
        try {
            Class<?> cls = Class.forName("com.taptap.sdk.Profile");
            Object objInvoke = cls.getMethod("getCurrentProfile", new Class[0]).invoke(null, new Object[0]);
            return objInvoke != null ? cls.getMethod("getOpenid", new Class[0]).invoke(objInvoke, new Object[0]).toString() : "";
        } catch (Throwable th) {
            Log.i("Common", "get openId id failed ：" + th.getMessage());
            return "";
        }
    }
}
