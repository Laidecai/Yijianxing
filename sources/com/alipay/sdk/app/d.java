package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.alipay.sdk.data.a;
import com.alipay.sdk.util.l;
import java.util.Collections;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static final int a = 1010;
    public static a b;

    public interface a {
        void a(boolean z, JSONObject jSONObject, String str);
    }

    public static boolean a(com.alipay.sdk.sys.a aVar, Context context) {
        return l.b(aVar, context, Collections.singletonList(new a.b("com.taobao.taobao", 0, "")));
    }

    public static boolean a(com.alipay.sdk.sys.a aVar, Activity activity, int i, String str, String str2, a aVar2) {
        try {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.z0);
            activity.startActivityForResult(new Intent(str2, Uri.parse(str)), i);
            b = aVar2;
            return true;
        } catch (Throwable th) {
            aVar2.a(false, null, "UNKNOWN_ERROR");
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.D0, th);
            return false;
        }
    }

    public static boolean a(com.alipay.sdk.sys.a aVar, int i, int i2, Intent intent) {
        if (i != 1010 || intent == null) {
            return false;
        }
        a aVar2 = b;
        if (aVar2 == null) {
            return true;
        }
        b = null;
        if (i2 != -1) {
            if (i2 != 0) {
                com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.B0, "" + i2);
            } else {
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.A0, intent != null ? intent.toUri(1) : "");
                aVar2.a(false, null, "CANCELED");
            }
        } else {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.C0, intent.toUri(1));
            aVar2.a(true, l.a(intent), "OK");
        }
        return true;
    }
}
