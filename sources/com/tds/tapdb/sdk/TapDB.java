package com.tds.tapdb.sdk;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Process;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.tracker.model.LoginModel;
import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.tapdb.BuildConfig;
import com.tds.tapdb.Callback;
import com.tds.tapdb.service.TapTapDIDIntentService;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapDB {
    private static final String o = "taptapdid.tmp";
    private static final String p = "taptapdid_share_preference";
    private static final String q = "taptap_device_id_saved_param";
    private static final String r = "com.taptap";
    private static final String s = "com.taptap.global";
    private static volatile TapDB u;
    private JSONObject a;
    private TapDBDataDynamicProperties b;
    private final Context c;
    private Map<String, Object> d;
    private String e;
    private String f;
    private String g;
    private LoginType h;
    private String i;
    private String j = "";
    private long k = 0;
    private volatile LoginType l;
    private volatile String m;
    private static final String n = Environment.getExternalStorageDirectory() + "/taptap_did";
    private static String t = "com.taptap";
    private static String v = "";
    private static String w = "";
    private static boolean x = true;
    private static boolean y = false;
    private static boolean z = true;

    public interface TapDBDataDynamicProperties {
        JSONObject getDynamicProperties();
    }

    static class a implements Runnable {
        final /* synthetic */ JSONObject a;

        a(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TapDB.u.a(com.tds.tapdb.b.e.DEVICE_ADD, "event", null, this.a);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class b implements Runnable {
        final /* synthetic */ JSONObject a;

        b(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TapDB.u.a(com.tds.tapdb.b.e.USER_INITIALIZE, "event", null, this.a);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class c implements Runnable {
        final /* synthetic */ JSONObject a;

        c(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TapDB.u.a(com.tds.tapdb.b.e.USER_UPDATE, "event", null, this.a);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class d implements Runnable {
        final /* synthetic */ JSONObject a;

        d(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TapDB.u.a(com.tds.tapdb.b.e.USER_ADD, "event", null, this.a);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class e implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ JSONObject b;

        e(String str, JSONObject jSONObject) {
            this.a = str;
            this.b = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                com.tds.tapdb.b.l.a(this.a);
                TapDB.u.a(com.tds.tapdb.b.e.TRACK, "event", this.a, this.b);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class f implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ JSONObject b;

        f(String str, JSONObject jSONObject) {
            this.a = str;
            this.b = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            String str2;
            try {
                JSONObject jSONObject = new JSONObject();
                if (TapDB.x) {
                    str = TapDB.g().f;
                    str2 = CommonParam.CLIENT_ID;
                } else {
                    str = TapDB.g().f;
                    str2 = "index";
                }
                jSONObject.put(str2, str);
                jSONObject.put(com.alipay.sdk.cons.c.e, "custom");
                jSONObject.put(com.tds.tapdb.sdk.b.d, com.tds.tapdb.b.c.c(TapDB.g().c));
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(NetworkStateModel.PARAM_CODE, this.a);
                jSONObject2.put("props", this.b);
                jSONObject2.put(com.alipay.sdk.packet.e.p, "Android");
                jSONObject2.put("ga_ver", BuildConfig.VERSION_NAME);
                jSONObject2.put("channel", TapDB.g().e);
                jSONObject2.put("ver", com.tds.tapdb.b.a.a(TapDB.g().c));
                jSONObject2.put("sys_ver", com.tds.tapdb.b.d.d());
                jSONObject2.put("network", com.tds.tapdb.b.i.b(TapDB.g().c));
                if (TapDB.g().h != null) {
                    jSONObject2.put(LoginModel.PARAM_LOGIN_TYPE, TapDB.g().h.getDecoratedName());
                }
                if (System.getProperties().get("flag_running_in_sandbox") != null) {
                    jSONObject2.put("tap_sandbox", 1);
                }
                jSONObject2.put(com.alipay.sdk.packet.e.p, "Android");
                jSONObject.put("properties", jSONObject2);
                com.tds.tapdb.sdk.b.a(jSONObject);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class g implements Runnable {
        final /* synthetic */ JSONObject a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;
        final /* synthetic */ long d;
        final /* synthetic */ String e;
        final /* synthetic */ String f;

        g(JSONObject jSONObject, String str, String str2, long j, String str3, String str4) {
            this.a = jSONObject;
            this.b = str;
            this.c = str2;
            this.d = j;
            this.e = str3;
            this.f = str4;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObject = this.a;
                if (jSONObject == null) {
                    jSONObject = new JSONObject();
                }
                com.tds.tapdb.b.c.a(jSONObject, "order_id", this.b);
                com.tds.tapdb.b.c.a(jSONObject, "product", this.c);
                com.tds.tapdb.b.c.a(jSONObject, "amount", Long.valueOf(this.d));
                com.tds.tapdb.b.c.a(jSONObject, "currency_type", this.e);
                com.tds.tapdb.b.c.a(jSONObject, "payment", this.f);
                TapDB.u.a(com.tds.tapdb.b.e.TRACK, "event", "charge", jSONObject);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class h implements Runnable {
        final /* synthetic */ long a;
        final /* synthetic */ String b;

        h(long j, String str) {
            this.a = j;
            this.b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("duration", this.a);
                TapDB.u.a(com.tds.tapdb.b.e.TRACK, "event", "play_game", jSONObject, this.b);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a((Throwable) e);
            }
        }
    }

    static class i extends Handler {
        final /* synthetic */ Object a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        i(Looper looper, Object obj) {
            super(looper);
            this.a = obj;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Log.d(TapTapDIDIntentService.i, "handleMessage");
            this.a.notify();
        }
    }

    static class j extends ResultReceiver {
        final /* synthetic */ y a;
        final /* synthetic */ boolean b;
        final /* synthetic */ Context c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        j(Handler handler, y yVar, boolean z, Context context) {
            super(handler);
            this.a = yVar;
            this.b = z;
            this.c = context;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            super.onReceiveResult(i, bundle);
            try {
                if (bundle.containsKey(TapTapDIDIntentService.g) && bundle.get(TapTapDIDIntentService.g) != null) {
                    com.tds.tapdb.b.n.a("get did success:" + bundle.get(TapTapDIDIntentService.g));
                    this.a.a(bundle.get(TapTapDIDIntentService.g).toString());
                }
                TapDB.b(this.c, this.a.a, this.b ? z.c : z.a);
                this.a.a(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class k implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ TapDB b;
        final /* synthetic */ JSONObject c;

        k(Context context, TapDB tapDB, JSONObject jSONObject) {
            this.a = context;
            this.b = tapDB;
            this.c = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            TapDB.getTapTapDID(this.a);
            this.b.a(com.tds.tapdb.b.e.TRACK, com.tds.tapdb.sdk.b.d, "device_login", this.c);
        }
    }

    static class l implements Runnable {
        final /* synthetic */ AtomicBoolean a;
        final /* synthetic */ Context b;

        l(AtomicBoolean atomicBoolean, Context context) {
            this.a = atomicBoolean;
            this.b = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.d(TapTapDIDIntentService.i, "----get result from sdcard -----");
            if (this.a.get() || this.b.checkPermission("android.permission.READ_EXTERNAL_STORAGE", Process.myPid(), Process.myUid()) != 0) {
                return;
            }
            try {
                String str = new String(com.tds.tapdb.b.p.a.b(TapDB.n + "/" + TapDB.o));
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                TapDB.b(this.b, str, z.b);
                this.a.set(true);
            } catch (IOException e) {
                com.tds.tapdb.b.n.c("get did from sd card fail:" + e.getMessage());
            }
        }
    }

    static class m implements Runnable {
        final /* synthetic */ AtomicBoolean a;
        final /* synthetic */ Context b;

        m(AtomicBoolean atomicBoolean, Context context) {
            this.a = atomicBoolean;
            this.b = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.get()) {
                return;
            }
            Log.d(TapTapDIDIntentService.i, "----get taptap did from remote service directly -----");
            y yVarB = TapDB.b(this.b, false);
            TapDB.b(this.b, yVarB.a, z.a);
            this.a.set(yVarB.a());
        }
    }

    static class n implements Runnable {
        final /* synthetic */ AtomicBoolean a;
        final /* synthetic */ Context b;

        n(AtomicBoolean atomicBoolean, Context context) {
            this.a = atomicBoolean;
            this.b = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.get()) {
                return;
            }
            Log.d(TapTapDIDIntentService.i, "----get taptap did from remote service & start mock activity -----");
            if (this.a.get() || com.tds.tapdb.b.d.e()) {
                return;
            }
            TapDB.b(this.b, TapDB.b(this.b, true).a, z.c);
        }
    }

    static class o extends ResultReceiver {
        final /* synthetic */ Callback a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        o(Handler handler, Callback callback) {
            super(handler);
            this.a = callback;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            super.onReceiveResult(i, bundle);
            Log.d(TapTapDIDIntentService.i, "onReceiveResult");
            Log.d(TapTapDIDIntentService.i, "resultData:" + bundle.get(TapTapDIDIntentService.g));
            if (this.a != null) {
                if (!bundle.containsKey(TapTapDIDIntentService.g) || bundle.get(TapTapDIDIntentService.g) == null) {
                    this.a.onFail(new Throwable("failed to set did"));
                } else {
                    this.a.onSuccess(bundle.get(TapTapDIDIntentService.g).toString());
                }
            }
        }
    }

    static /* synthetic */ class p {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[com.tds.tapdb.b.e.values().length];
            a = iArr;
            try {
                iArr[com.tds.tapdb.b.e.USER_ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[com.tds.tapdb.b.e.USER_INITIALIZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[com.tds.tapdb.b.e.USER_UPDATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[com.tds.tapdb.b.e.DEVICE_ADD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[com.tds.tapdb.b.e.DEVICE_INITIALIZE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[com.tds.tapdb.b.e.DEVICE_UPDATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    static class q implements Runnable {
        final /* synthetic */ Context a;

        q(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TextUtils.isEmpty(TapDB.getTapTapDID(this.a))) {
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                if (!TextUtils.isEmpty(TapDB.v)) {
                    jSONObject.put("first_tap_did", TapDB.v);
                    jSONObject.put("first_tap_did_source", !TextUtils.isEmpty(TapDB.w) ? TapDB.w : z.d);
                }
                TapDB.deviceInitialize(jSONObject);
            } catch (Exception unused) {
                com.tds.tapdb.b.n.c("deviceInitialize report fail");
            }
        }
    }

    static class r implements Runnable {
        final /* synthetic */ Context a;

        r(Context context) {
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TextUtils.isEmpty(TapDB.getTapTapDID(this.a))) {
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject();
                if (!TextUtils.isEmpty(TapDB.v)) {
                    jSONObject.put("current_tap_did", TapDB.v);
                    jSONObject.put("current_tap_did_source", !TextUtils.isEmpty(TapDB.w) ? TapDB.w : z.d);
                }
                TapDB.deviceUpdate(jSONObject);
            } catch (Exception unused) {
                com.tds.tapdb.b.n.c("deviceInitialize report fail");
            }
        }
    }

    static class s implements Runnable {
        final /* synthetic */ JSONObject a;
        final /* synthetic */ LoginType b;

        s(JSONObject jSONObject, LoginType loginType) {
            this.a = jSONObject;
            this.b = loginType;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TapDB.u.a(com.tds.tapdb.b.e.TRACK, com.tds.tapdb.sdk.b.d, "user_login", this.a);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("has_user", true);
                jSONObject.put("current_login_type", TapDB.u.h.getDecoratedName());
                jSONObject.put("current_open_id", com.tds.tapdb.b.c.a());
                TapDB.u.a(com.tds.tapdb.b.e.DEVICE_UPDATE, "event", null, jSONObject);
                if (TapDB.u.m == null || TapDB.u.l == null) {
                    JSONObject jSONObject2 = new JSONObject();
                    if (TapDB.u.m == null) {
                        TapDB.u.m = com.tds.tapdb.b.c.a();
                        com.tds.tapdb.b.c.a(jSONObject2, "first_open_id", TapDB.u.m);
                    }
                    if (TapDB.u.l == null) {
                        TapDB.u.l = this.b;
                        if (TapDB.u.l != null) {
                            com.tds.tapdb.b.c.a(jSONObject2, "first_login_type", TapDB.u.l.getDecoratedName());
                        }
                    }
                    if (jSONObject2.length() > 0) {
                        TapDB.u.a(com.tds.tapdb.b.e.DEVICE_INITIALIZE, "event", null, jSONObject2);
                    }
                }
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class t implements Runnable {
        final /* synthetic */ String a;

        t(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("user_name", this.a);
                TapDB.u.a(com.tds.tapdb.b.e.USER_UPDATE, "event", null, jSONObject);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class u implements Runnable {
        final /* synthetic */ int a;

        u(int i) {
            this.a = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("level", this.a);
                TapDB.u.a(com.tds.tapdb.b.e.USER_UPDATE, "event", null, jSONObject);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class v implements Runnable {
        final /* synthetic */ String a;

        v(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("first_server", this.a);
                TapDB.u.a(com.tds.tapdb.b.e.USER_INITIALIZE, "event", null, jSONObject);
                jSONObject.remove("first_server");
                jSONObject.put("current_server", this.a);
                TapDB.u.a(com.tds.tapdb.b.e.USER_UPDATE, "event", null, jSONObject);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class w implements Runnable {
        final /* synthetic */ JSONObject a;

        w(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TapDB.u.a(com.tds.tapdb.b.e.DEVICE_INITIALIZE, "event", null, this.a);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    static class x implements Runnable {
        final /* synthetic */ JSONObject a;

        x(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TapDB.u.a(com.tds.tapdb.b.e.DEVICE_UPDATE, "event", null, this.a);
            } catch (Exception e) {
                com.tds.tapdb.b.n.a(e);
            }
        }
    }

    private static class y {
        private String a;
        private boolean b;

        public y(boolean z) {
            this.a = "";
            this.b = false;
            this.b = z;
            this.a = "";
        }

        public void a(String str) {
            this.a = str;
        }

        public void a(boolean z) {
            this.b = z;
        }

        public boolean a() {
            return this.b;
        }
    }

    private static class z {
        public static final String a = "service_directly";
        public static final String b = "sdcard";
        public static final String c = "wake_taptap";
        public static final String d = "defualt";

        private z() {
        }
    }

    private TapDB(Context context) {
        this.c = context.getApplicationContext();
    }

    private static ResultReceiver a(ResultReceiver resultReceiver) {
        Parcel parcelObtain = Parcel.obtain();
        resultReceiver.writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        ResultReceiver resultReceiver2 = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return resultReceiver2;
    }

    private static TapDB a(Context context) {
        synchronized (TapDB.class) {
            if (u == null) {
                u = new TapDB(context);
            }
        }
        return u;
    }

    static void a(long j2, String str) {
        if (u == null) {
            com.tds.tapdb.b.n.b("clearUser method called error, TapDB SDK not initialized yet, please call init method first");
        } else {
            com.tds.tapdb.b.b.a(new h(j2, str));
        }
    }

    private static void a(String str, LoginType loginType, JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("setUser method called error,TapDB SDK not initialized yet, please call init method first");
            return;
        }
        u.g = str;
        u.h = loginType == null ? LoginType.NONE : loginType;
        com.tds.tapdb.b.b.a(new s(jSONObject, loginType));
    }

    private void a(JSONObject jSONObject) throws JSONException {
        TapDB tapDBG = g();
        if (tapDBG != null) {
            JSONObject jSONObject2 = tapDBG.a != null ? new JSONObject(tapDBG.a.toString()) : new JSONObject();
            JSONObject jSONObject3 = null;
            try {
                TapDBDataDynamicProperties tapDBDataDynamicProperties = tapDBG.b;
                if (tapDBDataDynamicProperties != null) {
                    JSONObject dynamicProperties = tapDBDataDynamicProperties.getDynamicProperties();
                    com.tds.tapdb.b.l.a(dynamicProperties);
                    jSONObject3 = dynamicProperties;
                }
            } catch (Exception e2) {
                com.tds.tapdb.b.n.a(e2);
            }
            com.tds.tapdb.b.c.a(com.tds.tapdb.b.c.b(jSONObject3, jSONObject2), jSONObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0084 A[Catch: all -> 0x008c, Exception -> 0x008e, TryCatch #0 {Exception -> 0x008e, blocks: (B:8:0x0017, B:9:0x002b, B:12:0x0046, B:14:0x004f, B:16:0x005a, B:18:0x0084, B:19:0x0088), top: B:41:0x0017, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0088 A[Catch: all -> 0x008c, Exception -> 0x008e, TRY_LEAVE, TryCatch #0 {Exception -> 0x008e, blocks: (B:8:0x0017, B:9:0x002b, B:12:0x0046, B:14:0x004f, B:16:0x005a, B:18:0x0084, B:19:0x0088), top: B:41:0x0017, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.tds.tapdb.sdk.TapDB.y b(android.content.Context r8, boolean r9) {
        /*
            android.os.Looper r0 = android.os.Looper.myLooper()
            if (r0 != 0) goto L9
            android.os.Looper.prepare()
        L9:
            com.tds.tapdb.sdk.TapDB$y r0 = new com.tds.tapdb.sdk.TapDB$y
            r1 = 0
            r0.<init>(r1)
            java.lang.Object r1 = new java.lang.Object
            r1.<init>()
            monitor-enter(r1)
            if (r9 == 0) goto L2b
            android.content.Intent r2 = new android.content.Intent     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r2.<init>()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.content.ComponentName r3 = new android.content.ComponentName     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            java.lang.String r4 = com.tds.tapdb.sdk.TapDB.t     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            java.lang.String r5 = "com.tds.tapdb.proxy.FakeProxyActivity"
            r3.<init>(r4, r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r2.setComponent(r3)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r8.startActivity(r2)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
        L2b:
            android.content.Intent r2 = new android.content.Intent     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r2.<init>()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.os.Bundle r3 = new android.os.Bundle     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r3.<init>()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            java.lang.String r4 = "cmd"
            java.lang.String r5 = "getUnifiedId"
            r3.putString(r4, r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.os.Looper r4 = android.os.Looper.myLooper()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            if (r4 != 0) goto L4a
            java.lang.String r4 = "TapTapDIDIntentService"
            java.lang.String r5 = "my looper is null"
        L46:
            android.util.Log.d(r4, r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            goto L4f
        L4a:
            java.lang.String r4 = "TapTapDIDIntentService"
            java.lang.String r5 = "my looper is not null"
            goto L46
        L4f:
            com.tds.tapdb.sdk.TapDB$i r4 = new com.tds.tapdb.sdk.TapDB$i     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.os.Looper r5 = android.os.Looper.myLooper()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r4.<init>(r5, r1)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            java.lang.String r4 = "receiver"
            com.tds.tapdb.sdk.TapDB$j r5 = new com.tds.tapdb.sdk.TapDB$j     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.os.Handler r6 = new android.os.Handler     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.os.Looper r7 = android.os.Looper.getMainLooper()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r5.<init>(r6, r0, r9, r8)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.os.ResultReceiver r9 = a(r5)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r2.putExtra(r4, r9)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r2.putExtras(r3)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            android.content.ComponentName r9 = new android.content.ComponentName     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            java.lang.String r3 = com.tds.tapdb.sdk.TapDB.t     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            java.lang.String r4 = "com.tds.tapdb.service.TapTapDIDIntentService"
            r9.<init>(r3, r4)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r2.setComponent(r9)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            int r9 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r3 = 26
            if (r9 < r3) goto L88
            r8.startForegroundService(r2)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            goto L9b
        L88:
            r8.startService(r2)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            goto L9b
        L8c:
            r8 = move-exception
            goto Lad
        L8e:
            r8 = move-exception
            java.lang.String r9 = "TapTapDIDIntentService"
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L8c
            android.util.Log.e(r9, r8)     // Catch: java.lang.Throwable -> L8c
            r1.notify()     // Catch: java.lang.Throwable -> L8c
        L9b:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L8c
            monitor-enter(r1)
            r8 = 100
            r1.wait(r8)     // Catch: java.lang.Throwable -> La3 java.lang.Exception -> La5
            goto La9
        La3:
            r8 = move-exception
            goto Lab
        La5:
            r8 = move-exception
            r8.printStackTrace()     // Catch: java.lang.Throwable -> La3
        La9:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> La3
            return r0
        Lab:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> La3
            throw r8
        Lad:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L8c
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.tapdb.sdk.TapDB.b(android.content.Context, boolean):com.tds.tapdb.sdk.TapDB$y");
    }

    private static void b(Context context) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        com.tds.tapdb.b.b.a(new l(atomicBoolean, context));
        com.tds.tapdb.b.b.a(new m(atomicBoolean, context));
        com.tds.tapdb.b.b.a(new n(atomicBoolean, context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        com.tds.tapdb.b.n.a("saveTapTapDID:" + str + "," + str2);
        v = str;
        w = str2;
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(p, 0).edit();
            editorEdit.putString(q, str);
            editorEdit.commit();
        } catch (Exception unused) {
            com.tds.tapdb.b.n.c("save taptap device id fail");
        }
    }

    private static void c(Context context) {
        b(context);
    }

    public static void clearStaticProperties() {
        TapDB tapDBG = g();
        if (tapDBG != null) {
            tapDBG.a = null;
        }
    }

    public static void clearUser() {
        if (u == null) {
            com.tds.tapdb.b.n.b("clearUser method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("clearUser method called error, TapDB setUser method not called, please call setUser method first");
        } else {
            u.h = null;
            u.g = null;
        }
    }

    public static void closeFetchTapTapDeviceId() {
        z = false;
    }

    public static void deviceAdd(JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("deviceAdd method called error, TapDB SDK not initialized yet, please call init method first");
        } else {
            com.tds.tapdb.b.b.a(new a(jSONObject));
        }
    }

    public static void deviceInitialize(JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("deviceInitialize method called error, TapDB SDK not initialized yet, please call init method first");
        } else {
            com.tds.tapdb.b.b.a(new w(jSONObject));
        }
    }

    public static void deviceUpdate(JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("deviceUpdate method called error, TapDB SDK not initialized yet, please call init method first");
        } else {
            com.tds.tapdb.b.b.a(new x(jSONObject));
        }
    }

    public static void enableLog(boolean z2) {
        com.tds.tapdb.b.n.a(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TapDB g() {
        if (u == null) {
            com.tds.tapdb.b.n.b("The static method getInstance(Context context) should be called before calling getInstance(), so you must call init(Context context, String clientId, String channel) method first");
        }
        return u;
    }

    public static String getDeviceId(Context context) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return com.tds.tapdb.b.c.c(context);
        }
        throw new IllegalStateException("cannot call getDeviceId method on main thread");
    }

    public static String getTapTapDID(Context context) {
        if (!TextUtils.isEmpty(v)) {
            return v;
        }
        String string = context.getSharedPreferences(p, 0).getString(q, "");
        v = string;
        return string;
    }

    static String h() {
        if (u != null) {
            return u.g;
        }
        return null;
    }

    private Map<String, Object> i() {
        HashMap map = new HashMap();
        map.put(CommonParam.OS_PARAM, "Android");
        map.put("device_model", com.tds.tapdb.b.d.c());
        map.put("brand", com.tds.tapdb.b.d.a());
        map.put("model", com.tds.tapdb.b.d.b());
        map.put(CommonParam.INSTALL_UUID, com.tds.tapdb.b.c.f(this.c));
        map.put("persist_uuid", com.tds.tapdb.b.c.g(this.c));
        int[] iArrA = com.tds.tapdb.b.d.a(this.c);
        map.put(CommonParam.SR_WIDTH, Integer.valueOf(iArrA[0]));
        map.put(CommonParam.SR_HEIGHT, Integer.valueOf(iArrA[1]));
        map.put("os_version", com.tds.tapdb.b.d.d());
        map.put("provider", com.tds.tapdb.b.c.b(this.c));
        map.put(CommonParam.APP_VERSION, TextUtils.isEmpty(this.i) ? com.tds.tapdb.b.a.a(this.c) : this.i);
        map.put(CommonParam.SDK_VERSION, BuildConfig.VERSION_NAME);
        map.put("network", com.tds.tapdb.b.i.b(this.c));
        return Collections.unmodifiableMap(map);
    }

    public static synchronized void init(Context context, String str, String str2) {
        init(context, str, str2, true);
    }

    public static synchronized void init(Context context, String str, String str2, String str3) {
        x = false;
        init(context, str, str2, str3, true, null);
    }

    public static synchronized void init(Context context, String str, String str2, String str3, JSONObject jSONObject) {
        x = false;
        init(context, str, str2, str3, true, jSONObject);
    }

    public static synchronized void init(Context context, String str, String str2, String str3, boolean z2) {
        init(context, str, str2, str3, z2, null);
    }

    public static synchronized void init(Context context, String str, String str2, String str3, boolean z2, JSONObject jSONObject) {
        if (u == null) {
            if (context == null) {
                com.tds.tapdb.b.n.b("context is illegal");
                return;
            }
            if (com.tds.tapdb.b.k.c(str)) {
                com.tds.tapdb.b.n.b("clientId is illegal");
                return;
            }
            t = z2 ? "com.taptap" : "com.taptap.global";
            TapDB tapDBA = a(context);
            tapDBA.f = str;
            tapDBA.e = str2;
            tapDBA.i = str3;
            tapDBA.d = tapDBA.i();
            tapDBA.j = UUID.randomUUID().toString();
            if (z) {
                c(context);
            }
            com.tds.tapdb.b.b.a(new k(context, tapDBA, jSONObject));
            com.tds.tapdb.b.b.a(new q(context));
            com.tds.tapdb.b.b.a(new r(context));
            try {
                Application application = (Application) context.getApplicationContext();
                if (!y) {
                    application.registerActivityLifecycleCallbacks(new TapDbActivityLifecycleCallbacks(application));
                    y = true;
                }
                com.tds.tapdb.c.a.a(context.getApplicationContext(), str, z2);
            } catch (Exception e2) {
                com.tds.tapdb.b.n.a(e2);
            }
        }
    }

    public static synchronized void init(Context context, String str, String str2, boolean z2) {
        init(context, str, str2, null, z2, null);
    }

    public static boolean isTapEnable() {
        return x;
    }

    public static void onCharge(String str, String str2, long j2, String str3, String str4) {
        onCharge(str, str2, j2, str3, str4, null);
    }

    public static void onCharge(String str, String str2, long j2, String str3, String str4, JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("onCharge method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("TapDB setUser method not called, please call setUser method first");
        } else {
            com.tds.tapdb.b.b.a(new g(jSONObject, str, str2, j2, str3, str4));
        }
    }

    @Deprecated
    public static void onEvent(String str, JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("onEvent method called error, TapDB SDK not initialized yet, please call init method first");
        } else {
            com.tds.tapdb.b.b.a(new f(str, jSONObject));
        }
    }

    public static void registerDynamicProperties(TapDBDataDynamicProperties tapDBDataDynamicProperties) {
        TapDB tapDBG = g();
        if (tapDBG != null) {
            tapDBG.b = tapDBDataDynamicProperties;
        }
    }

    public static void registerStaticProperties(JSONObject jSONObject) {
        TapDB tapDBG = g();
        if (tapDBG != null) {
            tapDBG.a = jSONObject;
        }
    }

    public static void setCustomEventHost(String str) {
        com.tds.tapdb.sdk.b.a(str);
    }

    public static void setHost(String str) {
        com.tds.tapdb.sdk.b.b(str);
    }

    public static void setLevel(int i2) {
        if (u == null) {
            com.tds.tapdb.b.n.b("setLevel method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("setLevel method called error, TapDB setUser method not called, please call setUser method first");
        } else {
            com.tds.tapdb.b.b.a(new u(i2));
        }
    }

    public static void setName(String str) {
        if (u == null) {
            com.tds.tapdb.b.n.b("setName method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("setName method called error, TapDB setUser method not called, please call setUser method first");
        } else {
            com.tds.tapdb.b.b.a(new t(str));
        }
    }

    public static void setServer(String str) {
        if (u == null) {
            com.tds.tapdb.b.n.b("setServer method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("set Server method called error, TapDB setUser method not called, please call setUser method first");
        } else {
            com.tds.tapdb.b.b.a(new v(str));
        }
    }

    public static void setTapTapDID(Context context, String str, Callback<String> callback) {
        if (TextUtils.isEmpty(str)) {
            com.tds.tapdb.b.n.b("setTapTapDID with empty str!");
            return;
        }
        try {
            if (context.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", Process.myPid(), Process.myUid()) == 0) {
                String str2 = n;
                File file = new File(str2);
                if (file.exists() ? true : file.mkdirs()) {
                    com.tds.tapdb.b.p.a.a(str.getBytes(), str2 + "/" + o);
                }
            }
        } catch (Exception unused) {
        }
        try {
            Intent intentA = TapTapDIDIntentService.a(context, str);
            intentA.putExtra("receiver", a(new o(new Handler(Looper.getMainLooper()), callback)));
            context.startService(intentA);
        } catch (Exception e2) {
            callback.onFail(new Throwable(e2.getMessage()));
        }
    }

    public static void setUser(String str) {
        setUser(str, LoginType.NONE);
    }

    public static void setUser(String str, LoginType loginType) {
        a(str, loginType, (JSONObject) null);
    }

    public static void setUser(String str, JSONObject jSONObject) {
        a(str, LoginType.NONE, jSONObject);
    }

    public static void trackEvent(String str, JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("track method called error, TapDB SDK not initialized yet, please call init method first");
        } else {
            com.tds.tapdb.b.b.a(new e(str, jSONObject));
        }
    }

    public static void unregisterStaticProperty(String str) {
        TapDB tapDBG = g();
        if (tapDBG != null) {
            tapDBG.a.remove(str);
        }
    }

    public static void userAdd(JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("userAdd method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("userAdd method called error, TapDB setUser method not called, please call setUser method first");
        } else {
            com.tds.tapdb.b.b.a(new d(jSONObject));
        }
    }

    public static void userInitialize(JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("userInitialize method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("userInitialize method called error, TapDB setUser method not called, please call setUser method first");
        } else {
            com.tds.tapdb.b.b.a(new b(jSONObject));
        }
    }

    public static void userUpdate(JSONObject jSONObject) {
        if (u == null) {
            com.tds.tapdb.b.n.b("userUpdate method called error, TapDB SDK not initialized yet, please call init method first");
        } else if (TextUtils.isEmpty(u.g)) {
            com.tds.tapdb.b.n.b("userUpdate method called error, TapDB setUser method not called, please call setUser method first");
        } else {
            com.tds.tapdb.b.b.a(new c(jSONObject));
        }
    }

    void a(com.tds.tapdb.b.e eVar, String str, String str2, JSONObject jSONObject) {
        a(eVar, str, str2, jSONObject, this.g);
    }

    void a(com.tds.tapdb.b.e eVar, String str, String str2, JSONObject jSONObject, String str3) {
        String str4;
        String str5;
        try {
            if (eVar.b()) {
                com.tds.tapdb.b.l.a(str2);
            }
            com.tds.tapdb.b.l.a(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(com.alipay.sdk.packet.e.r, eVar.a());
            if (x) {
                str4 = this.f;
                str5 = CommonParam.CLIENT_ID;
            } else {
                str4 = this.f;
                str5 = "index";
            }
            jSONObject2.put(str5, str4);
            com.tds.tapdb.b.c.a(jSONObject2, "ip_v6", com.tds.tapdb.b.h.a());
            com.tds.tapdb.b.c.a(jSONObject2, com.alipay.sdk.cons.c.e, str2);
            if (eVar.b()) {
                com.tds.tapdb.b.c.a(jSONObject2, "user_id", str3);
                com.tds.tapdb.b.c.a(jSONObject2, CommonParam.DEVICE_ID, com.tds.tapdb.b.c.c(this.c));
                if (!TextUtils.equals("device_login", str2)) {
                    com.tds.tapdb.b.c.a(jSONObject2, CommonParam.OPEN_ID, com.tds.tapdb.b.c.a());
                }
                JSONObject jSONObject3 = this.d != null ? new JSONObject(this.d) : new JSONObject();
                if (!TextUtils.isEmpty(v)) {
                    com.tds.tapdb.b.c.a(jSONObject3, "tap_did", v);
                }
                com.tds.tapdb.b.c.a(jSONObject3, "channel", this.e);
                com.tds.tapdb.b.c.a(jSONObject3, "device_id1", com.tds.tapdb.b.c.e(this.c));
                com.tds.tapdb.b.c.a(jSONObject3, "device_id2", com.tds.tapdb.b.f.a(this.c));
                com.tds.tapdb.b.c.a(jSONObject3, "device_id3", com.tds.tapdb.b.c.a(this.c));
                com.tds.tapdb.b.c.a(jSONObject3, "device_id4", com.tds.tapdb.b.j.a(this.c));
                com.tds.tapdb.b.c.a(jSONObject3, "smaf_id", com.tds.tapdb.c.a.a());
                LoginType loginType = this.h;
                if (loginType != null) {
                    com.tds.tapdb.b.c.a(jSONObject3, LoginModel.PARAM_LOGIN_TYPE, loginType.getDecoratedName());
                }
                com.tds.tapdb.b.c.a(jSONObject, jSONObject3);
                a(jSONObject3);
                com.tds.tapdb.b.c.a(jSONObject3, "event_uuid", UUID.randomUUID());
                com.tds.tapdb.b.c.a(jSONObject3, "session_uuid", this.j);
                long j2 = this.k + 1;
                this.k = j2;
                com.tds.tapdb.b.c.a(jSONObject3, "event_index", Long.valueOf(j2));
                if (System.getProperties().get("flag_running_in_sandbox") != null) {
                    jSONObject3.put("tap_sandbox", 1);
                }
                jSONObject2.put("properties", jSONObject3);
            } else {
                switch (p.a[eVar.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                        com.tds.tapdb.b.c.a(jSONObject2, "user_id", str3);
                        break;
                    case 4:
                    case 5:
                    case 6:
                        com.tds.tapdb.b.c.a(jSONObject2, CommonParam.DEVICE_ID, com.tds.tapdb.b.c.c(this.c));
                        break;
                }
                if (jSONObject == null) {
                    jSONObject = new JSONObject();
                }
                jSONObject.put(CommonParam.SDK_VERSION, BuildConfig.VERSION_NAME);
                if (System.getProperties().get("flag_running_in_sandbox") != null) {
                    jSONObject.put("tap_sandbox", 1);
                }
                jSONObject2.put("properties", jSONObject);
            }
            com.tds.tapdb.sdk.b.a(str, jSONObject2);
        } catch (Exception e2) {
            com.tds.tapdb.b.n.a(e2);
        }
    }
}
