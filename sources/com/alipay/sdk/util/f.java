package com.alipay.sdk.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.core.os.EnvironmentCompat;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.app.APayEntranceActivity;
import com.alipay.sdk.app.AlipayResultActivity;
import com.alipay.sdk.data.a;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.l;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f {
    public static final String j = "failed";
    public static final String k = "scheme_failed";
    public Activity a;
    public volatile IAlixPay b;
    public boolean d;
    public e e;
    public final com.alipay.sdk.sys.a f;
    public final Object c = IAlixPay.class;
    public boolean g = false;
    public String h = null;
    public String i = null;

    public class a implements AlipayResultActivity.a {
        public final /* synthetic */ CountDownLatch a;

        public a(CountDownLatch countDownLatch) {
            this.a = countDownLatch;
        }

        @Override // com.alipay.sdk.app.AlipayResultActivity.a
        public void a(int i, String str, String str2) {
            f.this.h = com.alipay.sdk.app.b.a(i, str, str2);
            this.a.countDown();
        }
    }

    public class b implements APayEntranceActivity.a {
        public final /* synthetic */ CountDownLatch a;

        public b(CountDownLatch countDownLatch) {
            this.a = countDownLatch;
        }

        @Override // com.alipay.sdk.app.APayEntranceActivity.a
        public void a(String str) {
            f.this.i = str;
            this.a.countDown();
        }
    }

    public class c extends IRemoteServiceCallback.Stub {
        public c() {
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public int getVersion() throws RemoteException {
            return 3;
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public boolean isHideLoadingScreen() throws RemoteException {
            return false;
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public void payEnd(boolean z, String str) throws RemoteException {
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public void r03(String str, String str2, Map map) throws RemoteException {
            com.alipay.sdk.app.statistic.a.a(f.this.f, com.alipay.sdk.app.statistic.b.q, str, str2);
        }

        @Override // com.alipay.android.app.IRemoteServiceCallback
        public void startActivity(String str, String str2, int i, Bundle bundle) throws RemoteException {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            if (bundle == null) {
                bundle = new Bundle();
            }
            try {
                bundle.putInt("CallingPid", i);
                intent.putExtras(bundle);
            } catch (Exception e) {
                com.alipay.sdk.app.statistic.a.a(f.this.f, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.d0, e);
            }
            intent.setClassName(str, str2);
            try {
                if (Build.VERSION.SDK_INT >= 16) {
                    ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
                    ActivityManager.getMyMemoryState(runningAppProcessInfo);
                    com.alipay.sdk.app.statistic.a.a(f.this.f, com.alipay.sdk.app.statistic.b.l, "isFg", runningAppProcessInfo.processName + "|" + runningAppProcessInfo.importance + "|");
                }
            } catch (Throwable unused) {
            }
            try {
                if (f.this.a != null) {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    f.this.a.startActivity(intent);
                    com.alipay.sdk.app.statistic.a.a(f.this.f, com.alipay.sdk.app.statistic.b.l, "stAct2", "" + (SystemClock.elapsedRealtime() - jElapsedRealtime));
                } else {
                    com.alipay.sdk.app.statistic.a.b(f.this.f, com.alipay.sdk.app.statistic.b.l, "ErrActNull", "");
                    Context contextA = f.this.f.a();
                    if (contextA != null) {
                        contextA.startActivity(intent);
                    }
                }
                f.this.e.a();
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(f.this.f, com.alipay.sdk.app.statistic.b.l, "ErrActNull", th);
                throw th;
            }
        }

        public /* synthetic */ c(f fVar, a aVar) {
            this();
        }
    }

    public class d implements ServiceConnection {
        public d() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            com.alipay.sdk.app.statistic.a.a(f.this.f, com.alipay.sdk.app.statistic.b.l, "srvCon");
            synchronized (f.this.c) {
                f.this.b = IAlixPay.Stub.asInterface(iBinder);
                f.this.c.notify();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            com.alipay.sdk.app.statistic.a.a(f.this.f, com.alipay.sdk.app.statistic.b.l, "srvDis");
            f.this.b = null;
        }

        public /* synthetic */ d(f fVar, a aVar) {
            this();
        }
    }

    public interface e {
        void a();

        void b();
    }

    public f(Activity activity, com.alipay.sdk.sys.a aVar, e eVar) {
        this.a = activity;
        this.f = aVar;
        this.e = eVar;
    }

    private String b(String str, String str2, PackageInfo packageInfo) {
        if (packageInfo != null) {
            int i = packageInfo.versionCode;
        }
        String str3 = packageInfo != null ? packageInfo.versionName : "";
        com.alipay.sdk.util.c.d(com.alipay.sdk.cons.a.x, "pay bind or scheme");
        com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.b0, str2 + "|" + str3);
        Activity activity = this.a;
        com.alipay.sdk.sys.a aVar = this.f;
        com.alipay.sdk.app.statistic.a.a(activity, aVar, str, aVar.d);
        return b(str, str2);
    }

    public String a(String str) {
        String strA = "";
        try {
            List<a.b> listK = com.alipay.sdk.data.a.u().k();
            if (!com.alipay.sdk.data.a.u().g || listK == null) {
                listK = com.alipay.sdk.app.a.d;
            }
            l.b bVarA = l.a(this.f, this.a, listK);
            if (bVarA == null || bVarA.a(this.f) || bVarA.a() || l.a(bVarA.a)) {
                return j;
            }
            if (bVarA.a != null && !l.b.equals(bVarA.a.packageName)) {
                strA = bVarA.a.packageName;
            } else {
                strA = l.a();
            }
            packageInfo = bVarA.a != null ? bVarA.a : null;
            String strB = com.alipay.sdk.data.a.u().b();
            if (strB != null && strB.length() > 0) {
                try {
                    JSONObject jSONObjectOptJSONObject = new JSONObject(strB).optJSONObject(strA);
                    if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.length() > 0) {
                        Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            int i = Integer.parseInt(next);
                            if (packageInfo != null && packageInfo.versionCode >= i) {
                                try {
                                    boolean zA = com.alipay.sdk.data.a.u().a(this.a, Integer.parseInt(jSONObjectOptJSONObject.getString(next)));
                                    this.g = zA;
                                    if (zA) {
                                        break;
                                    }
                                } catch (Exception unused) {
                                    continue;
                                }
                            }
                        }
                    }
                } catch (Throwable unused2) {
                }
            }
            if (!this.g && !com.alipay.sdk.data.a.u().o()) {
                a(bVarA);
            }
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.R, th);
        }
        if (this.g) {
            return b(str, strA, packageInfo);
        }
        return a(str, strA, packageInfo);
    }

    private String b(String str, String str2) {
        JSONObject jSONObject;
        String str3;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String strA = l.a(32);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSAStart", strA + "|" + jElapsedRealtime);
        a.C0007a.a(this.f, strA);
        APayEntranceActivity.g.put(strA, new b(countDownLatch));
        try {
            try {
                HashMap<String, String> mapA = com.alipay.sdk.sys.a.a(this.f);
                mapA.put("ts_intent", String.valueOf(jElapsedRealtime));
                jSONObject = new JSONObject(mapA);
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSALocEx", th);
                jSONObject = null;
            }
            Intent intent = new Intent(this.a, (Class<?>) APayEntranceActivity.class);
            intent.putExtra(APayEntranceActivity.c, str);
            intent.putExtra(APayEntranceActivity.d, str2);
            intent.putExtra(APayEntranceActivity.e, strA);
            if (jSONObject != null) {
                intent.putExtra(APayEntranceActivity.f, jSONObject.toString());
            }
            Activity activity = this.a;
            com.alipay.sdk.sys.a aVar = this.f;
            com.alipay.sdk.app.statistic.a.a(activity, aVar, str, aVar.d);
            this.a.startActivity(intent);
            com.alipay.sdk.data.a.u().a(this.f, this.a.getApplicationContext());
            countDownLatch.await();
            String str4 = this.i;
            try {
                str3 = j.a(this.f, str4).get(j.a);
                if (str3 == null) {
                    str3 = "null";
                }
            } catch (Throwable th2) {
                com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSAStatEx", th2);
                str3 = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSADone-" + str3);
            if (!TextUtils.isEmpty(str4)) {
                return str4;
            }
            com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSAEmpty");
            return k;
        } catch (InterruptedException e2) {
            com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSAWaiting", e2);
            return com.alipay.sdk.app.b.a(com.alipay.sdk.app.c.PAY_WAITTING.b(), com.alipay.sdk.app.c.PAY_WAITTING.a(), "");
        } catch (Throwable th3) {
            com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSAEx", th3);
            return k;
        }
    }

    private void a(l.b bVar) throws InterruptedException {
        PackageInfo packageInfo;
        if (bVar == null || (packageInfo = bVar.a) == null) {
            return;
        }
        String str = packageInfo.packageName;
        Intent intent = new Intent();
        intent.setClassName(str, "com.alipay.android.app.TransProcessPayActivity");
        try {
            this.a.startActivity(intent);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.h0, th);
        }
        Thread.sleep(200L);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(java.lang.String r10, java.lang.String r11, android.content.pm.PackageInfo r12) {
        /*
            Method dump skipped, instruction units count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.util.f.a(java.lang.String, java.lang.String, android.content.pm.PackageInfo):java.lang.String");
    }

    private String a(String str, String str2) {
        String str3;
        JSONObject jSONObject;
        String strSubstring;
        String strSubstring2;
        String str4;
        String strReplace = str;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String strA = l.a(32);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPStart", strA + "|" + jElapsedRealtime);
        a.C0007a.a(this.f, strA);
        AlipayResultActivity.a.put(strA, new a(countDownLatch));
        try {
            try {
                String[] strArrSplit = strReplace.split(com.alipay.sdk.sys.a.k, -1);
                int length = strArrSplit.length;
                int i = 0;
                while (true) {
                    jSONObject = null;
                    if (i >= length) {
                        strSubstring = "";
                        strSubstring2 = strSubstring;
                        str4 = null;
                        break;
                    }
                    str4 = strArrSplit[i];
                    if (str4.startsWith(com.alipay.sdk.sys.a.m)) {
                        String strSubstring3 = str4.substring(str4.indexOf("{"), str4.lastIndexOf(g.d) + 1);
                        int iIndexOf = str4.indexOf(strSubstring3);
                        strSubstring2 = str4.substring(0, iIndexOf);
                        strSubstring = str4.substring(iIndexOf + strSubstring3.length());
                        JSONObject jSONObject2 = new JSONObject(strSubstring3);
                        if (jSONObject2.optString("sc").equals("h5tonative")) {
                            jSONObject2.put("sc", "h5tonative_scheme");
                        } else {
                            jSONObject2.put("sc", "h5tonative_sdkscheme");
                        }
                        jSONObject = jSONObject2;
                    } else {
                        i++;
                    }
                }
            } catch (Exception e2) {
                try {
                    com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPSCReplaceEx", e2, Base64.encodeToString(str.getBytes(), 2));
                } catch (InterruptedException e3) {
                    com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPWaiting", e3);
                    return com.alipay.sdk.app.b.a(com.alipay.sdk.app.c.PAY_WAITTING.b(), com.alipay.sdk.app.c.PAY_WAITTING.a(), "");
                }
            }
            if (!TextUtils.isEmpty(str4)) {
                if (strReplace.indexOf(str4) == strReplace.lastIndexOf(str4)) {
                    strReplace = strReplace.replace(str4, strSubstring2 + jSONObject.toString() + strSubstring);
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("sourcePid", Binder.getCallingPid());
                    jSONObject3.put(com.alipay.sdk.cons.b.d, strReplace);
                    jSONObject3.put("pkgName", this.a.getPackageName());
                    jSONObject3.put("session", strA);
                    String strEncodeToString = Base64.encodeToString(jSONObject3.toString().getBytes("UTF-8"), 2);
                    Uri.Builder builderAppendQueryParameter = new Uri.Builder().scheme("alipays").authority("platformapi").path("startapp").appendQueryParameter("appId", "20000125");
                    builderAppendQueryParameter.appendQueryParameter("mqpSchemePay", strEncodeToString);
                    try {
                        HashMap<String, String> mapA = com.alipay.sdk.sys.a.a(this.f);
                        mapA.put("ts_scheme", String.valueOf(jElapsedRealtime));
                        builderAppendQueryParameter.appendQueryParameter("mqpLoc", new JSONObject(mapA).toString());
                    } catch (Throwable th) {
                        com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPLocEx", th);
                    }
                    String string = builderAppendQueryParameter.build().toString();
                    Intent intent = new Intent();
                    intent.setPackage(str2);
                    intent.addFlags(268435456);
                    intent.setData(Uri.parse(string));
                    Activity activity = this.a;
                    com.alipay.sdk.sys.a aVar = this.f;
                    com.alipay.sdk.app.statistic.a.a(activity, aVar, strReplace, aVar.d);
                    this.a.startActivity(intent);
                    com.alipay.sdk.data.a.u().a(this.f, this.a.getApplicationContext());
                    com.alipay.sdk.util.c.d(com.alipay.sdk.cons.a.x, "pay scheme waiting " + string);
                    countDownLatch.await();
                    String str5 = this.h;
                    try {
                        str3 = j.a(this.f, str5).get(j.a);
                        if (str3 == null) {
                            str3 = "null";
                        }
                    } catch (Throwable th2) {
                        com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPStatEx", th2);
                        str3 = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPDone-" + str3);
                    if (!TextUtils.isEmpty(str5)) {
                        return str5;
                    }
                    com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPEmpty");
                    return k;
                }
                throw new RuntimeException("multi ctx_args");
            }
            throw new RuntimeException("empty ctx_args");
        } catch (Throwable th3) {
            com.alipay.sdk.app.statistic.a.a(this.f, com.alipay.sdk.app.statistic.b.l, "BSPEx", th3);
            return k;
        }
    }

    public static boolean a(String str, Context context, com.alipay.sdk.sys.a aVar) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.setClassName(str, "com.alipay.android.msp.ui.views.MspContainerActivity");
            if (intent.resolveActivityInfo(context.getPackageManager(), 0) != null) {
                return true;
            }
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "BSPDetectFail");
            return false;
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "BSPDetectFail", th);
            return false;
        }
    }

    private Pair<String, Boolean> a(String str, String str2, com.alipay.sdk.sys.a aVar) {
        int i;
        d dVar;
        IRemoteServiceCallback cVar;
        Activity activity;
        int version;
        String strA;
        boolean z;
        Activity activity2;
        Activity activity3;
        Intent intent = new Intent();
        intent.setPackage(str2);
        intent.setAction(l.b(str2));
        String strA2 = l.a(this.a, str2);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(jElapsedRealtime);
        sb.append("|");
        sb.append(str != null ? str.length() : 0);
        com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.V, sb.toString());
        com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
        try {
            try {
                if (!com.alipay.sdk.data.a.u().e()) {
                    ComponentName componentNameStartService = this.a.getApplication().startService(intent);
                    com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "stSrv", componentNameStartService != null ? componentNameStartService.getPackageName() : "null");
                } else {
                    com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "stSrv", "skipped");
                }
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.O, th);
            }
            if (com.alipay.sdk.data.a.u().a()) {
                i = 65;
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, "bindFlg", "imp");
            } else {
                i = 1;
            }
            a aVar2 = null;
            d dVar2 = new d(this, aVar2);
            if (this.a.getApplicationContext().bindService(intent, dVar2, i)) {
                synchronized (this.c) {
                    if (this.b == null) {
                        try {
                            this.c.wait(com.alipay.sdk.data.a.u().j());
                        } catch (InterruptedException e2) {
                            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.P, e2);
                        }
                    }
                }
                IAlixPay iAlixPay = this.b;
                try {
                    if (iAlixPay == null) {
                        com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.I, strA2 + "|" + l.a(this.a, str2));
                        Pair<String, Boolean> pair = new Pair<>(j, true);
                        try {
                            this.a.getApplicationContext().unbindService(dVar2);
                        } catch (Throwable th2) {
                            com.alipay.sdk.util.c.a(th2);
                        }
                        com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.X, "" + SystemClock.elapsedRealtime());
                        com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                        this.b = null;
                        if (this.d && (activity3 = this.a) != null) {
                            activity3.setRequestedOrientation(0);
                            this.d = false;
                        }
                        return pair;
                    }
                    long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                    com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.W, "" + jElapsedRealtime2);
                    e eVar = this.e;
                    if (eVar != null) {
                        eVar.b();
                    }
                    if (this.a.getRequestedOrientation() == 0) {
                        this.a.setRequestedOrientation(1);
                        this.d = true;
                    }
                    try {
                        version = iAlixPay.getVersion();
                    } catch (Throwable th3) {
                        com.alipay.sdk.util.c.a(th3);
                        version = 0;
                    }
                    cVar = new c(this, aVar2);
                    try {
                        if (version >= 3) {
                            iAlixPay.registerCallback03(cVar, str, null);
                        } else {
                            iAlixPay.registerCallback(cVar);
                        }
                        try {
                            long jElapsedRealtime3 = SystemClock.elapsedRealtime();
                            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.Y, "" + jElapsedRealtime3);
                            if (version >= 3) {
                                iAlixPay.r03(com.alipay.sdk.app.statistic.b.l, "bind_pay", null);
                            }
                            try {
                                if (version >= 2) {
                                    Map mapA = com.alipay.sdk.sys.a.a(aVar);
                                    mapA.put("ts_bind", String.valueOf(jElapsedRealtime));
                                    mapA.put("ts_bend", String.valueOf(jElapsedRealtime2));
                                    mapA.put("ts_pay", String.valueOf(jElapsedRealtime3));
                                    strA = iAlixPay.pay02(str, mapA);
                                } else {
                                    strA = iAlixPay.Pay(str);
                                }
                            } catch (Throwable th4) {
                                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.L, th4);
                                strA = com.alipay.sdk.app.b.a();
                            }
                            String str3 = strA;
                            try {
                                iAlixPay.unregisterCallback(cVar);
                            } catch (Throwable th5) {
                                com.alipay.sdk.util.c.a(th5);
                            }
                            try {
                                this.a.getApplicationContext().unbindService(dVar2);
                            } catch (Throwable th6) {
                                com.alipay.sdk.util.c.a(th6);
                            }
                            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.X, "" + SystemClock.elapsedRealtime());
                            com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                            this.b = null;
                            if (!this.d || (activity2 = this.a) == null) {
                                z = false;
                            } else {
                                z = false;
                                activity2.setRequestedOrientation(0);
                                this.d = false;
                            }
                            return new Pair<>(str3, Boolean.valueOf(z));
                        } catch (Throwable th7) {
                            th = th7;
                            dVar = dVar2;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        dVar = dVar2;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    dVar = dVar2;
                    cVar = null;
                }
                try {
                    com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.I, th, "in_bind");
                    Pair<String, Boolean> pair2 = new Pair<>(j, true);
                    if (cVar != null) {
                        try {
                            iAlixPay.unregisterCallback(cVar);
                        } catch (Throwable th10) {
                            com.alipay.sdk.util.c.a(th10);
                        }
                    }
                    try {
                        this.a.getApplicationContext().unbindService(dVar);
                    } catch (Throwable th11) {
                        com.alipay.sdk.util.c.a(th11);
                    }
                    com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.X, "" + SystemClock.elapsedRealtime());
                    com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                    this.b = null;
                    if (this.d && (activity = this.a) != null) {
                        activity.setRequestedOrientation(0);
                        this.d = false;
                    }
                    return pair2;
                } finally {
                }
            } else {
                throw new Throwable("bindService fail");
            }
        } catch (Throwable th12) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.N, th12);
            return new Pair<>(j, true);
        }
    }

    public void a() {
        this.a = null;
        this.e = null;
    }
}
