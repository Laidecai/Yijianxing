package com.alipay.sdk.sys;

import android.content.Context;
import com.alipay.sdk.util.c;
import com.ta.utdid2.device.UTDevice;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static b b;
    public Context a;

    public static b d() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    public static boolean e() {
        String[] strArr = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (int i = 0; i < 10; i++) {
            if (new File(strArr[i]).exists()) {
                return true;
            }
        }
        return false;
    }

    public void a(Context context) {
        com.alipay.sdk.data.b.b();
        this.a = context.getApplicationContext();
    }

    public Context b() {
        return this.a;
    }

    public String c() {
        try {
            return UTDevice.getUtdid(this.a);
        } catch (Throwable th) {
            c.a(th);
            return "getUtdidEx";
        }
    }

    public com.alipay.sdk.data.b a() {
        return com.alipay.sdk.data.b.b();
    }
}
