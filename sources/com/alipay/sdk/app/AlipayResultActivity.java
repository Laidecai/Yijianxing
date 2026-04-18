package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.j;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AlipayResultActivity extends Activity {
    public static final ConcurrentHashMap<String, a> a = new ConcurrentHashMap<>();

    public interface a {
        void a(int i, String str, String str2);
    }

    private void a(String str, Bundle bundle) {
        a aVarRemove = a.remove(str);
        if (aVarRemove == null) {
            return;
        }
        try {
            aVarRemove.a(bundle.getInt("endCode"), bundle.getString(j.b), bundle.getString(j.c));
        } finally {
            finish();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        Throwable th;
        JSONObject jSONObject;
        Bundle bundle2;
        super.onCreate(bundle);
        try {
            Intent intent = getIntent();
            try {
                String stringExtra = intent.getStringExtra("session");
                Bundle bundleExtra = intent.getBundleExtra(j.c);
                String stringExtra2 = intent.getStringExtra("scene");
                com.alipay.sdk.sys.a aVarA = a.C0007a.a(stringExtra);
                if (aVarA == null) {
                    finish();
                    return;
                }
                com.alipay.sdk.app.statistic.a.a(aVarA, com.alipay.sdk.app.statistic.b.l, "BSPSession", stringExtra + "|" + SystemClock.elapsedRealtime());
                if (TextUtils.equals("mqpSchemePay", stringExtra2)) {
                    a(stringExtra, bundleExtra);
                    return;
                }
                if ((TextUtils.isEmpty(stringExtra) || bundleExtra == null) && intent.getData() != null) {
                    try {
                        JSONObject jSONObject2 = new JSONObject(new String(Base64.decode(intent.getData().getQuery(), 2), "UTF-8"));
                        jSONObject = jSONObject2.getJSONObject(j.c);
                        stringExtra = jSONObject2.getString("session");
                        com.alipay.sdk.app.statistic.a.a(aVarA, com.alipay.sdk.app.statistic.b.l, "BSPUriSession", stringExtra);
                        bundle2 = new Bundle();
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    try {
                        Iterator<String> itKeys = jSONObject.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            bundle2.putString(next, jSONObject.getString(next));
                        }
                        bundleExtra = bundle2;
                    } catch (Throwable th3) {
                        th = th3;
                        bundleExtra = bundle2;
                        com.alipay.sdk.app.statistic.a.a(aVarA, com.alipay.sdk.app.statistic.b.l, "BSPResEx", th);
                        com.alipay.sdk.app.statistic.a.a(aVarA, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.x0, th);
                    }
                }
                if (TextUtils.isEmpty(stringExtra) || bundleExtra == null) {
                    com.alipay.sdk.app.statistic.a.b(this, aVarA, "", aVarA.d);
                    finish();
                    return;
                }
                try {
                    com.alipay.sdk.app.statistic.a.a(aVarA, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.Z, "" + SystemClock.elapsedRealtime());
                    com.alipay.sdk.app.statistic.a.a(aVarA, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.a0, bundleExtra.getInt("endCode", -1) + "|" + bundleExtra.getString(j.b, "-"));
                    OpenAuthTask.a(stringExtra, OpenAuthTask.OK, "OK", bundleExtra);
                    com.alipay.sdk.app.statistic.a.b(this, aVarA, "", aVarA.d);
                    finish();
                } catch (Throwable th4) {
                    com.alipay.sdk.app.statistic.a.b(this, aVarA, "", aVarA.d);
                    finish();
                    throw th4;
                }
            } catch (Throwable th5) {
                com.alipay.sdk.app.statistic.a.a((com.alipay.sdk.sys.a) null, com.alipay.sdk.app.statistic.b.l, "BSPSerError", th5);
                com.alipay.sdk.app.statistic.a.a((com.alipay.sdk.sys.a) null, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.w0, th5);
                finish();
            }
        } catch (Throwable unused) {
            finish();
        }
    }
}
