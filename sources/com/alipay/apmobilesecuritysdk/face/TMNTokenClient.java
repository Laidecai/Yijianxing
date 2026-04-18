package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.otherid.UtdidWrapper;
import com.alipay.sdk.cons.b;
import com.alipay.security.mobile.module.a.a;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class TMNTokenClient {
    public static TMNTokenClient a;
    public Context b;

    public interface InitResultListener {
        void onResult(String str, int i);
    }

    public TMNTokenClient(Context context) {
        this.b = null;
        if (context == null) {
            throw new IllegalArgumentException("TMNTokenClient initialization error: context is null.");
        }
        this.b = context;
    }

    public static TMNTokenClient getInstance(Context context) {
        if (a == null) {
            synchronized (TMNTokenClient.class) {
                if (a == null) {
                    a = new TMNTokenClient(context);
                }
            }
        }
        return a;
    }

    public void intiToken(final String str, String str2, String str3, final InitResultListener initResultListener) {
        if (a.a(str) && initResultListener != null) {
            initResultListener.onResult("", 2);
        }
        if (a.a(str2) && initResultListener != null) {
            initResultListener.onResult("", 3);
        }
        final HashMap map = new HashMap();
        map.put(b.g, UtdidWrapper.getUtdid(this.b));
        map.put("tid", "");
        map.put("userId", "");
        map.put("appName", str);
        map.put("appKeyClient", str2);
        map.put("appchannel", "openapi");
        map.put("sessionId", str3);
        map.put("rpcVersion", "8");
        com.alipay.apmobilesecuritysdk.f.b.a().a(new Runnable() { // from class: com.alipay.apmobilesecuritysdk.face.TMNTokenClient.1
            @Override // java.lang.Runnable
            public void run() {
                int iA = new com.alipay.apmobilesecuritysdk.a.a(TMNTokenClient.this.b).a(map);
                InitResultListener initResultListener2 = initResultListener;
                if (initResultListener2 == null) {
                    return;
                }
                if (iA != 0) {
                    initResultListener2.onResult("", iA);
                } else {
                    initResultListener.onResult(com.alipay.apmobilesecuritysdk.a.a.a(TMNTokenClient.this.b, str), 0);
                }
            }
        });
    }
}
