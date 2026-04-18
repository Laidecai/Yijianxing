package com.alipay.sdk.data;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.c;
import com.alipay.sdk.util.h;
import com.tds.common.tracker.constants.CommonParam;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final boolean A = false;
    public static final boolean B = true;
    public static final boolean C = true;
    public static final String D = "";
    public static final boolean E = false;
    public static final boolean F = false;
    public static final boolean G = false;
    public static final boolean H = true;
    public static final String I = "";
    public static final boolean J = false;
    public static final String K = "";
    public static final int L = 1000;
    public static final int M = 20000;
    public static final String N = "alipay_cashier_dynamic_config";
    public static final String O = "timeout";
    public static final String P = "h5_port_degrade";
    public static final String Q = "st_sdk_config";
    public static final String R = "tbreturl";
    public static final String S = "launchAppSwitch";
    public static final String T = "configQueryInterval";
    public static final String U = "deg_log_mcgw";
    public static final String V = "deg_start_srv_first";
    public static final String W = "prev_jump_dual";
    public static final String X = "use_sc_only";
    public static final String Y = "bind_use_imp";
    public static final String Z = "retry_bnd_once";
    public static final String a0 = "skip_trans";
    public static final String b0 = "up_before_pay";
    public static final String c0 = "lck_k";
    public static final String d0 = "use_sc_lck_a";
    public static final String e0 = "utdid_factor";
    public static final String f0 = "scheme_pay_2";
    public static final String g0 = "intercept_batch";
    public static final String h0 = "bind_with_startActivity";
    public static a i0 = null;
    public static final char[] j0 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '+', '/'};
    public static final String u = "DynCon";
    public static final int v = 10000;
    public static final String w = "https://h5.m.taobao.com/mlapp/olist.html";
    public static final int x = 10;
    public static final boolean y = true;
    public static final boolean z = true;
    public int a = v;
    public boolean b = false;
    public String c = w;
    public int d = 10;
    public boolean e = true;
    public boolean f = true;
    public boolean g = false;
    public boolean h = false;
    public boolean i = true;
    public boolean j = true;
    public String k = "";
    public boolean l = false;
    public boolean m = false;
    public boolean n = false;
    public boolean o = true;
    public String p = "";
    public String q = "";
    public boolean r = false;
    public List<b> s = null;
    public int t = -1;

    /* JADX INFO: renamed from: com.alipay.sdk.data.a$a, reason: collision with other inner class name */
    public class RunnableC0004a implements Runnable {
        public final /* synthetic */ com.alipay.sdk.sys.a a;
        public final /* synthetic */ Context b;

        public RunnableC0004a(com.alipay.sdk.sys.a aVar, Context context) {
            this.a = aVar;
            this.b = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                com.alipay.sdk.packet.b bVarA = new com.alipay.sdk.packet.impl.b().a(this.a, this.b);
                if (bVarA != null) {
                    a.this.d(bVarA.a());
                    a.this.a(com.alipay.sdk.sys.a.e());
                }
            } catch (Throwable th) {
                c.a(th);
            }
        }
    }

    private int t() {
        String strC = com.alipay.sdk.sys.b.d().c();
        if (TextUtils.isEmpty(strC)) {
            return -1;
        }
        String strReplaceAll = strC.replaceAll("=", "");
        if (strReplaceAll.length() >= 5) {
            strReplaceAll = strReplaceAll.substring(0, 5);
        }
        int iA = (int) (a(strReplaceAll) % 10000);
        return iA < 0 ? iA * (-1) : iA;
    }

    public static a u() {
        if (i0 == null) {
            a aVar = new a();
            i0 = aVar;
            aVar.r();
        }
        return i0;
    }

    private JSONObject v() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(O, j());
        jSONObject.put(P, s());
        jSONObject.put(R, p());
        jSONObject.put(T, c());
        jSONObject.put(S, b.a(k()));
        jSONObject.put(f0, h());
        jSONObject.put(g0, g());
        jSONObject.put(U, d());
        jSONObject.put(V, e());
        jSONObject.put(W, l());
        jSONObject.put(X, f());
        jSONObject.put(Y, a());
        jSONObject.put(Z, m());
        jSONObject.put(a0, o());
        jSONObject.put(b0, q());
        jSONObject.put(d0, n());
        jSONObject.put(c0, i());
        jSONObject.put(h0, b());
        return jSONObject;
    }

    public String b() {
        return this.q;
    }

    public int c() {
        return this.d;
    }

    public boolean d() {
        return this.h;
    }

    public boolean e() {
        return this.i;
    }

    public String f() {
        return this.k;
    }

    public boolean g() {
        return this.f;
    }

    public boolean h() {
        return this.e;
    }

    public String i() {
        return this.p;
    }

    public int j() {
        int i = this.a;
        if (i < 1000 || i > 20000) {
            c.b(u, "time(def) = 10000");
            return v;
        }
        c.b(u, "time = " + this.a);
        return this.a;
    }

    public List<b> k() {
        return this.s;
    }

    public boolean l() {
        return this.j;
    }

    public boolean m() {
        return this.m;
    }

    public boolean n() {
        return this.r;
    }

    public boolean o() {
        return this.n;
    }

    public String p() {
        return this.c;
    }

    public boolean q() {
        return this.o;
    }

    public void r() {
        Context contextB = com.alipay.sdk.sys.b.d().b();
        String strA = h.a(com.alipay.sdk.sys.a.e(), contextB, N, null);
        try {
            this.t = Integer.parseInt(h.a(com.alipay.sdk.sys.a.e(), contextB, e0, "-1"));
        } catch (Exception unused) {
        }
        c(strA);
    }

    public boolean s() {
        return this.b;
    }

    public static int b(String str) {
        for (int i = 0; i < 64; i++) {
            if (str.equals(String.valueOf(j0[i]))) {
                return i;
            }
        }
        return 0;
    }

    private void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            a(new JSONObject(str));
        } catch (Throwable th) {
            c.a(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject(Q);
            if (jSONObjectOptJSONObject != null) {
                a(jSONObjectOptJSONObject);
            } else {
                c.e(u, "empty config");
            }
        } catch (Throwable th) {
            c.a(th);
        }
    }

    public static final class b {
        public final String a;
        public final int b;
        public final String c;

        public b(String str, int i, String str2) {
            this.a = str;
            this.b = i;
            this.c = str2;
        }

        public static b a(JSONObject jSONObject) {
            if (jSONObject == null) {
                return null;
            }
            return new b(jSONObject.optString(CommonParam.PN), jSONObject.optInt("v", 0), jSONObject.optString("pk"));
        }

        public String toString() {
            return String.valueOf(a(this));
        }

        public static List<b> a(JSONArray jSONArray) {
            if (jSONArray == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                b bVarA = a(jSONArray.optJSONObject(i));
                if (bVarA != null) {
                    arrayList.add(bVarA);
                }
            }
            return arrayList;
        }

        public static JSONObject a(b bVar) {
            if (bVar == null) {
                return null;
            }
            try {
                return new JSONObject().put(CommonParam.PN, bVar.a).put("v", bVar.b).put("pk", bVar.c);
            } catch (JSONException e) {
                c.a(e);
                return null;
            }
        }

        public static JSONArray a(List<b> list) {
            if (list == null) {
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            Iterator<b> it = list.iterator();
            while (it.hasNext()) {
                jSONArray.put(a(it.next()));
            }
            return jSONArray;
        }
    }

    public boolean a() {
        return this.l;
    }

    public void a(boolean z2) {
        this.g = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.alipay.sdk.sys.a aVar) {
        try {
            JSONObject jSONObjectV = v();
            h.b(aVar, com.alipay.sdk.sys.b.d().b(), N, jSONObjectV.toString());
        } catch (Exception e) {
            c.a(e);
        }
    }

    private void a(JSONObject jSONObject) {
        this.a = jSONObject.optInt(O, v);
        this.b = jSONObject.optBoolean(P, false);
        this.c = jSONObject.optString(R, w).trim();
        this.d = jSONObject.optInt(T, 10);
        this.s = b.a(jSONObject.optJSONArray(S));
        this.e = jSONObject.optBoolean(f0, true);
        this.f = jSONObject.optBoolean(g0, true);
        this.h = jSONObject.optBoolean(U, false);
        this.i = jSONObject.optBoolean(V, true);
        this.j = jSONObject.optBoolean(W, true);
        this.k = jSONObject.optString(X, "");
        this.l = jSONObject.optBoolean(Y, false);
        this.m = jSONObject.optBoolean(Z, false);
        this.n = jSONObject.optBoolean(a0, false);
        this.o = jSONObject.optBoolean(b0, true);
        this.p = jSONObject.optString(c0, "");
        this.r = jSONObject.optBoolean(d0, false);
        this.q = jSONObject.optString(h0, "");
    }

    public void a(com.alipay.sdk.sys.a aVar, Context context) {
        new Thread(new RunnableC0004a(aVar, context)).start();
    }

    public boolean a(Context context, int i) {
        if (this.t == -1) {
            this.t = t();
            h.b(com.alipay.sdk.sys.a.e(), context, e0, String.valueOf(this.t));
        }
        return this.t < i;
    }

    public static long a(String str) {
        return a(str, 6);
    }

    public static long a(String str, int i) {
        int iPow = (int) Math.pow(2.0d, i);
        int length = str.length();
        long j = 0;
        int i2 = 0;
        int i3 = length;
        while (i2 < length) {
            int i4 = i2 + 1;
            j += ((long) Integer.parseInt(String.valueOf(b(str.substring(i2, i4))))) * ((long) Math.pow(iPow, i3 - 1));
            i3--;
            i2 = i4;
        }
        return j;
    }
}
