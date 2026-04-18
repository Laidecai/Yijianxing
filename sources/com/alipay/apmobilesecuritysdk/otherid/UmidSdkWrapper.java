package com.alipay.apmobilesecuritysdk.otherid;

import android.content.Context;
import com.alipay.security.mobile.module.a.a;
import com.alipay.security.mobile.module.c.d;

/* JADX INFO: loaded from: classes.dex */
public class UmidSdkWrapper {
    public static final String UMIDTOKEN_FILE_NAME = "xxxwww_v2";
    public static final String UMIDTOKEN_KEY_NAME = "umidtk";
    public static volatile String cachedUmidToken = "";
    public static volatile boolean initUmidFinished = false;

    public static String compatUmidBug(Context context, String str) {
        if (!a.a(str) && !a.a(str, "000000000000000000000000")) {
            return str;
        }
        String utdid = UtdidWrapper.getUtdid(context);
        if (utdid != null && utdid.contains("?")) {
            utdid = "";
        }
        return a.a(utdid) ? "" : utdid;
    }

    public static synchronized String getSecurityToken(Context context) {
        return cachedUmidToken;
    }

    public static String startUmidTaskSync(Context context, int i) {
        return "";
    }

    public static synchronized void updateLocalUmidToken(Context context, String str) {
        if (a.b(str)) {
            d.a(context, UMIDTOKEN_FILE_NAME, UMIDTOKEN_KEY_NAME, str);
            cachedUmidToken = str;
        }
    }
}
