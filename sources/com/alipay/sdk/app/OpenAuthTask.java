package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.sdk.data.a;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.l;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class OpenAuthTask {
    public static final int Duplex = 5000;
    public static final int NOT_INSTALLED = 4001;
    public static final int OK = 9000;
    public static final int SYS_ERR = 4000;
    public static final Map<String, Callback> e = new ConcurrentHashMap();
    public static long f = -1;
    public static final int g = 122;
    public final Activity b;
    public Callback c;
    public volatile boolean a = false;
    public final Handler d = new Handler(Looper.getMainLooper());

    public enum BizType {
        Invoice("20000920"),
        AccountAuth("20000067"),
        Deduct("60000157");

        public String appId;

        BizType(String str) {
            this.appId = str;
        }
    }

    public interface Callback {
        void onResult(int i, String str, Bundle bundle);
    }

    public static /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[BizType.values().length];
            a = iArr;
            try {
                iArr[BizType.Deduct.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[BizType.AccountAuth.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[BizType.Invoice.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public final class b implements Runnable {
        public final int a;
        public final String b;
        public final Bundle c;

        public /* synthetic */ b(OpenAuthTask openAuthTask, int i, String str, Bundle bundle, a aVar) {
            this(i, str, bundle);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (OpenAuthTask.this.c != null) {
                OpenAuthTask.this.c.onResult(this.a, this.b, this.c);
            }
        }

        public b(int i, String str, Bundle bundle) {
            this.a = i;
            this.b = str;
            this.c = bundle;
        }
    }

    public OpenAuthTask(Activity activity) {
        this.b = activity;
        com.alipay.sdk.sys.b.d().a(activity);
    }

    public void execute(String str, BizType bizType, Map<String, String> map, Callback callback, boolean z) {
        com.alipay.sdk.sys.a aVar = new com.alipay.sdk.sys.a(this.b, String.valueOf(map), "oa-" + bizType);
        this.c = callback;
        if (a(aVar, str, bizType, map, z)) {
            com.alipay.sdk.app.statistic.a.b(this.b, aVar, "", aVar.d);
        }
    }

    private boolean a(com.alipay.sdk.sys.a aVar, String str, BizType bizType, Map<String, String> map, boolean z) {
        PackageInfo packageInfo;
        if (this.a) {
            this.d.post(new b(this, SYS_ERR, "该 OpenAuthTask 已在执行", null, null));
            return true;
        }
        this.a = true;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - f <= PayTask.j) {
            this.d.post(new b(this, 5000, "3s 内重复支付", null, null));
            return true;
        }
        f = jElapsedRealtime;
        com.alipay.sdk.app.a.a("");
        String strA = l.a(32);
        HashMap map2 = new HashMap(map);
        map2.put("mqpPkgName", this.b.getPackageName());
        map2.put("mqpScene", "sdk");
        List<a.b> listK = com.alipay.sdk.data.a.u().k();
        if (!com.alipay.sdk.data.a.u().g || listK == null) {
            listK = com.alipay.sdk.app.a.d;
        }
        l.b bVarA = l.a(aVar, this.b, listK);
        if (bVarA == null || bVarA.a(aVar) || bVarA.a() || (packageInfo = bVarA.a) == null || packageInfo.versionCode < 122) {
            if (!z) {
                this.d.post(new b(this, NOT_INSTALLED, "支付宝未安装或签名错误", null, null));
                return true;
            }
            map2.put("mqpScheme", String.valueOf(str));
            map2.put("mqpNotifyName", strA);
            map2.put("mqpScene", "landing");
            String strA2 = a(bizType, map2);
            Intent intent = new Intent(this.b, (Class<?>) H5OpenAuthActivity.class);
            intent.putExtra(BreakpointSQLiteKey.URL, String.format("https://render.alipay.com/p/s/i?scheme=%s", Uri.encode(strA2)));
            a.C0007a.a(aVar, intent);
            this.b.startActivity(intent);
            return false;
        }
        try {
            try {
                HashMap<String, String> mapA = com.alipay.sdk.sys.a.a(aVar);
                mapA.put("ts_scheme", String.valueOf(SystemClock.elapsedRealtime()));
                map2.put("mqpLoc", new JSONObject(mapA).toString());
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "OpenAuthLocEx", th);
            }
            String strA3 = a(bizType, map2);
            e.put(strA, this.c);
            String strA4 = null;
            try {
                strA4 = a(jElapsedRealtime, strA, bizType, strA3);
            } catch (JSONException e2) {
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.v0, e2);
            }
            String str2 = strA4;
            if (TextUtils.isEmpty(str2)) {
                this.d.post(new b(this, SYS_ERR, "参数错误", null, null));
                return true;
            }
            Intent intent2 = new Intent("android.intent.action.VIEW", new Uri.Builder().scheme("alipays").authority("platformapi").path("startapp").appendQueryParameter("appId", "20001129").appendQueryParameter("payload", str2).build());
            intent2.addFlags(268435456);
            intent2.setPackage(bVarA.a.packageName);
            try {
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.c0, "" + jElapsedRealtime);
                a.C0007a.a(aVar, strA);
                this.b.startActivity(intent2);
            } catch (Throwable th2) {
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "StartWalletEx", th2);
            }
            return false;
        } catch (Throwable unused) {
            this.d.post(new b(this, SYS_ERR, "业务参数错误", null, null));
            return true;
        }
    }

    private String a(BizType bizType, Map<String, String> map) {
        if (bizType != null) {
            Uri.Builder builderAppendQueryParameter = new Uri.Builder().scheme("alipays").authority("platformapi").path("startapp").appendQueryParameter("appId", bizType.appId);
            if (a.a[bizType.ordinal()] == 1) {
                builderAppendQueryParameter.appendQueryParameter("appClearTop", "false").appendQueryParameter("startMultApp", "YES");
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builderAppendQueryParameter.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            return builderAppendQueryParameter.build().toString();
        }
        throw new RuntimeException("missing bizType");
    }

    private String a(long j, String str, BizType bizType, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("startTime", String.valueOf(j));
        jSONObject.put("session", str);
        jSONObject.put("package", this.b.getPackageName());
        if (bizType != null) {
            jSONObject.put("appId", bizType.appId);
        }
        jSONObject.put("sdkVersion", "h.a.3.8.02");
        jSONObject.put("mqpURL", str2);
        return Base64.encodeToString(jSONObject.toString().getBytes(Charset.forName("UTF-8")), 2);
    }

    public static void a(String str, int i, String str2, Bundle bundle) {
        Callback callbackRemove = e.remove(str);
        if (callbackRemove != null) {
            try {
                callbackRemove.onResult(i, str2, bundle);
            } catch (Throwable th) {
                com.alipay.sdk.util.c.a(th);
            }
        }
    }
}
