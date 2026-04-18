package com.alipay.sdk.tid;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.sdk.packet.impl.c;
import com.alipay.sdk.sys.b;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TidHelper {
    public static void a(Context context) {
        if (context == null) {
            return;
        }
        b.d().a(context);
    }

    public static Tid b(Context context) throws Exception {
        try {
            com.alipay.sdk.packet.b bVarA = new c().a(com.alipay.sdk.sys.a.e(), context);
            if (bVarA != null) {
                JSONObject jSONObject = new JSONObject(bVarA.a());
                a aVarA = a.a(context);
                String strOptString = jSONObject.optString("tid");
                String string = jSONObject.getString(a.j);
                if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(string)) {
                    aVarA.a(strOptString, string);
                }
                return a(context, aVarA);
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    public static void clearTID(Context context) {
        a.a(context).a();
    }

    public static String getIMEI(Context context) {
        a(context);
        return com.alipay.sdk.util.a.b(context).b();
    }

    public static String getIMSI(Context context) {
        a(context);
        return com.alipay.sdk.util.a.b(context).c();
    }

    public static synchronized String getTIDValue(Context context) {
        Tid tidLoadOrCreateTID;
        tidLoadOrCreateTID = loadOrCreateTID(context);
        return Tid.isEmpty(tidLoadOrCreateTID) ? "" : tidLoadOrCreateTID.getTid();
    }

    public static String getVirtualImei(Context context) {
        a(context);
        com.alipay.sdk.data.b.b();
        return com.alipay.sdk.data.b.f();
    }

    public static String getVirtualImsi(Context context) {
        a(context);
        com.alipay.sdk.data.b.b();
        return com.alipay.sdk.data.b.g();
    }

    public static Tid loadLocalTid(Context context) {
        a aVarA = a.a(context);
        if (aVarA.h()) {
            return null;
        }
        return new Tid(aVarA.d(), aVarA.c(), aVarA.e().longValue());
    }

    public static synchronized Tid loadOrCreateTID(Context context) {
        com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "load_create_tid");
        a(context);
        Tid tidLoadTID = loadTID(context);
        if (Tid.isEmpty(tidLoadTID)) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                return null;
            }
            try {
                tidLoadTID = b(context);
            } catch (Throwable unused) {
            }
        }
        return tidLoadTID;
    }

    public static Tid loadTID(Context context) {
        a(context);
        Tid tidA = a(context, a.a(context));
        if (tidA == null) {
            com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "load_tid null");
        }
        return tidA;
    }

    public static boolean resetTID(Context context) throws Exception {
        com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "reset_tid");
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new Exception("Must be called on worker thread");
        }
        a(context);
        clearTID(context);
        Tid tidB = null;
        try {
            tidB = b(context);
        } catch (Throwable unused) {
        }
        return !Tid.isEmpty(tidB);
    }

    public static Tid a(Context context, a aVar) {
        if (aVar == null || aVar.i()) {
            return null;
        }
        return new Tid(aVar.d(), aVar.c(), aVar.e().longValue());
    }
}
