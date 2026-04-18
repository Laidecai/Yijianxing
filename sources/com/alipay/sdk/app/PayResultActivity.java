package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.alipay.sdk.sys.a;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class PayResultActivity extends Activity {
    public static final String b = "{\"isLogin\":\"false\"}";
    public static final HashMap<String, Object> c = new HashMap<>();
    public static final String d = "hk.alipay.wallet";
    public static final String e = "phonecashier.pay.hash";
    public static final String f = "orderSuffix";
    public static final String g = "externalPkgName";
    public static final String h = "phonecashier.pay.result";
    public static final String i = "phonecashier.pay.resultOrderHash";
    public com.alipay.sdk.sys.a a = null;

    public static class a implements Runnable {
        public final /* synthetic */ Activity a;

        public a(Activity activity) {
            this.a = activity;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.finish();
        }
    }

    public static final class b {
        public static volatile String a;
        public static volatile String b;
    }

    public static void a(Activity activity, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        Intent intent = new Intent();
        try {
            intent.setPackage(d);
            intent.setData(Uri.parse("alipayhk://platformapi/startApp?appId=20000125&schemePaySession=" + URLEncoder.encode(str, "UTF-8") + "&orderSuffix=" + URLEncoder.encode(str2, "UTF-8") + "&packageName=" + URLEncoder.encode(str3, "UTF-8") + "&externalPkgName=" + URLEncoder.encode(str3, "UTF-8")));
        } catch (UnsupportedEncodingException e2) {
            com.alipay.sdk.util.c.a(e2);
        }
        if (activity != null) {
            try {
                activity.startActivity(intent);
            } catch (Throwable unused) {
                activity.finish();
            }
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Intent intent = getIntent();
            if (!TextUtils.isEmpty(intent.getStringExtra(f))) {
                b.a = intent.getStringExtra(e);
                String stringExtra = intent.getStringExtra(f);
                String stringExtra2 = intent.getStringExtra(g);
                com.alipay.sdk.sys.a aVarA = a.C0007a.a(intent);
                this.a = aVarA;
                if (aVarA == null) {
                    finish();
                }
                a(this, b.a, stringExtra, stringExtra2);
                a(this, 300);
                return;
            }
            if (this.a == null) {
                finish();
            }
            String stringExtra3 = intent.getStringExtra(h);
            int intExtra = intent.getIntExtra(i, 0);
            if (intExtra != 0 && TextUtils.equals(b.a, String.valueOf(intExtra))) {
                if (TextUtils.isEmpty(stringExtra3)) {
                    a(b.a);
                } else {
                    a(stringExtra3, b.a);
                }
                b.a = "";
                a(this, 300);
                return;
            }
            com.alipay.sdk.app.statistic.a.b(this.a, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.p0, "Expected " + b.a + ", got " + intExtra);
            a(b.a);
            a(this, 300);
        } catch (Throwable unused) {
            finish();
        }
    }

    public static void a(String str) {
        b.b = com.alipay.sdk.app.b.a();
        a(c, str);
    }

    public static void a(String str, String str2) {
        b.b = str;
        a(c, str2);
    }

    public static void a(Activity activity, int i2) {
        new Handler().postDelayed(new a(activity), i2);
    }

    public static boolean a(HashMap<String, Object> map, String str) {
        Object obj;
        if (map == null || str == null || (obj = map.get(str)) == null) {
            return false;
        }
        synchronized (obj) {
            obj.notifyAll();
        }
        return true;
    }
}
