package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.packet.impl.d;
import com.alipay.sdk.packet.impl.e;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: com.alipay.sdk.app.statistic.a$a, reason: collision with other inner class name */
    public static final class C0001a {
        public static final String a = "RecordPref";
        public static final String b = "alipay_cashier_statistic_record";

        public static synchronized String a(Context context, String str, String str2) {
            com.alipay.sdk.util.c.b(a, "stat append " + str2 + " , " + str);
            if (context != null && !TextUtils.isEmpty(str)) {
                if (TextUtils.isEmpty(str2)) {
                    str2 = UUID.randomUUID().toString();
                }
                C0002a c0002aA = a(context);
                if (c0002aA.a.size() > 20) {
                    c0002aA.a.clear();
                }
                c0002aA.a.put(str2, str);
                a(context, c0002aA);
                return str2;
            }
            return null;
        }

        public static synchronized String b(Context context) {
            com.alipay.sdk.util.c.b(a, "stat peek");
            if (context == null) {
                return null;
            }
            C0002a c0002aA = a(context);
            if (c0002aA.a.isEmpty()) {
                return null;
            }
            try {
                return c0002aA.a.entrySet().iterator().next().getValue();
            } catch (Throwable th) {
                com.alipay.sdk.util.c.a(th);
                return null;
            }
        }

        /* JADX INFO: renamed from: com.alipay.sdk.app.statistic.a$a$a, reason: collision with other inner class name */
        public static final class C0002a {
            public final LinkedHashMap<String, String> a = new LinkedHashMap<>();

            public C0002a() {
            }

            public String a() {
                try {
                    JSONArray jSONArray = new JSONArray();
                    for (Map.Entry<String, String> entry : this.a.entrySet()) {
                        JSONArray jSONArray2 = new JSONArray();
                        jSONArray2.put(entry.getKey()).put(entry.getValue());
                        jSONArray.put(jSONArray2);
                    }
                    return jSONArray.toString();
                } catch (Throwable th) {
                    com.alipay.sdk.util.c.a(th);
                    return new JSONArray().toString();
                }
            }

            public C0002a(String str) {
                try {
                    JSONArray jSONArray = new JSONArray(str);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONArray jSONArray2 = jSONArray.getJSONArray(i);
                        this.a.put(jSONArray2.getString(0), jSONArray2.getString(1));
                    }
                } catch (Throwable th) {
                    com.alipay.sdk.util.c.a(th);
                }
            }
        }

        public static synchronized int a(Context context, String str) {
            com.alipay.sdk.util.c.b(a, "stat remove " + str);
            if (context != null && !TextUtils.isEmpty(str)) {
                C0002a c0002aA = a(context);
                if (c0002aA.a.isEmpty()) {
                    return 0;
                }
                try {
                    ArrayList arrayList = new ArrayList();
                    for (Map.Entry<String, String> entry : c0002aA.a.entrySet()) {
                        if (str.equals(entry.getValue())) {
                            arrayList.add(entry.getKey());
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        c0002aA.a.remove((String) it.next());
                    }
                    a(context, c0002aA);
                    return arrayList.size();
                } catch (Throwable th) {
                    com.alipay.sdk.util.c.a(th);
                    int size = c0002aA.a.size();
                    a(context, new C0002a());
                    return size;
                }
            }
            return 0;
        }

        public static synchronized C0002a a(Context context) {
            try {
                String strA = h.a(null, context, b, null);
                if (TextUtils.isEmpty(strA)) {
                    return new C0002a();
                }
                return new C0002a(strA);
            } catch (Throwable th) {
                com.alipay.sdk.util.c.a(th);
                return new C0002a();
            }
        }

        public static synchronized void a(Context context, C0002a c0002a) {
            if (c0002a == null) {
                try {
                    c0002a = new C0002a();
                } catch (Throwable th) {
                    com.alipay.sdk.util.c.a(th);
                }
            }
            h.b(null, context, b, c0002a.a());
        }
    }

    public static final class b {

        /* JADX INFO: renamed from: com.alipay.sdk.app.statistic.a$b$a, reason: collision with other inner class name */
        public static class RunnableC0003a implements Runnable {
            public final /* synthetic */ String a;
            public final /* synthetic */ Context b;

            public RunnableC0003a(String str, Context context) {
                this.a = str;
                this.b = context;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (TextUtils.isEmpty(this.a) || b.b(this.b, this.a)) {
                    for (int i = 0; i < 4; i++) {
                        String strB = C0001a.b(this.b);
                        if (TextUtils.isEmpty(strB) || !b.b(this.b, strB)) {
                            return;
                        }
                    }
                }
            }
        }

        public static synchronized boolean b(Context context, String str) {
            com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "stat sub " + str);
            try {
                if ((com.alipay.sdk.data.a.u().d() ? new d() : new e()).a((com.alipay.sdk.sys.a) null, context, str) == null) {
                    return false;
                }
                C0001a.a(context, str);
                return true;
            } catch (Throwable th) {
                com.alipay.sdk.util.c.a(th);
                return false;
            }
        }

        public static synchronized void a(Context context, com.alipay.sdk.app.statistic.b bVar, String str, String str2) {
            if (context == null || bVar == null || str == null) {
                return;
            }
            a(context, bVar.a(str), str2);
        }

        public static synchronized void a(Context context) {
            a(context, null, null);
        }

        public static synchronized void a(Context context, String str, String str2) {
            if (context == null) {
                return;
            }
            if (!TextUtils.isEmpty(str)) {
                C0001a.a(context, str, str2);
            }
            new Thread(new RunnableC0003a(str, context)).start();
        }
    }

    public static final class c {
        public static final String a = "alipay_cashier_statistic_v";

        /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
        public static synchronized long a(Context context) {
            long j;
            String strA;
            try {
                strA = h.a(null, context, a, null);
            } catch (Throwable unused) {
            }
            j = (!TextUtils.isEmpty(strA) ? Long.parseLong(strA) : 0L) + 1;
            try {
                h.b(null, context, a, Long.toString(j));
            } catch (Throwable unused2) {
            }
            return j;
        }
    }

    public static synchronized void a(Context context, com.alipay.sdk.sys.a aVar, String str, String str2) {
        if (context == null || aVar == null) {
            return;
        }
        try {
            C0001a.a(context, aVar.i.a(str), str2);
        } catch (Throwable th) {
            com.alipay.sdk.util.c.a(th);
        }
    }

    public static synchronized void b(Context context, com.alipay.sdk.sys.a aVar, String str, String str2) {
        if (context == null || aVar == null) {
            return;
        }
        b.a(context, aVar.i, str, str2);
    }

    public static void b(com.alipay.sdk.sys.a aVar, String str, String str2, String str3) {
        if (aVar == null) {
            return;
        }
        aVar.i.b(str, str2, str3);
    }

    public static synchronized void a(Context context) {
        b.a(context);
    }

    public static void a(com.alipay.sdk.sys.a aVar, String str, Throwable th) {
        if (aVar == null || th == null || th.getClass() == null) {
            return;
        }
        aVar.i.a(str, th.getClass().getSimpleName(), th);
    }

    public static void a(com.alipay.sdk.sys.a aVar, String str, String str2, Throwable th, String str3) {
        if (aVar == null) {
            return;
        }
        aVar.i.a(str, str2, th, str3);
    }

    public static void a(com.alipay.sdk.sys.a aVar, String str, String str2, Throwable th) {
        if (aVar == null) {
            return;
        }
        aVar.i.a(str, str2, th);
    }

    public static void a(com.alipay.sdk.sys.a aVar, String str, String str2, String str3) {
        if (aVar == null) {
            return;
        }
        aVar.i.a(str, str2, str3);
    }

    public static void a(com.alipay.sdk.sys.a aVar, String str, String str2) {
        if (aVar == null) {
            return;
        }
        aVar.i.a(str, str2);
    }
}
