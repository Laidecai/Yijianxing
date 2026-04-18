package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.app.PayResultActivity;
import com.alipay.sdk.data.a;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.H5PayResultModel;
import com.alipay.sdk.util.f;
import com.alipay.sdk.util.g;
import com.alipay.sdk.util.j;
import com.alipay.sdk.util.l;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class PayTask {
    public static final Object h = f.class;
    public static long i = 0;
    public static final long j = 3000;
    public static long k = -1;
    public Activity a;
    public com.alipay.sdk.widget.a b;
    public final String c = "wappaygw.alipay.com/service/rest.htm";
    public final String d = "mclient.alipay.com/service/rest.htm";
    public final String e = "mclient.alipay.com/home/exterfaceAssign.htm";
    public final String f = "mclient.alipay.com/cashier/mobilepay.htm";
    public Map<String, c> g = new HashMap();

    public class a implements Runnable {
        public final /* synthetic */ String a;
        public final /* synthetic */ boolean b;
        public final /* synthetic */ H5PayCallback c;

        public a(String str, boolean z, H5PayCallback h5PayCallback) {
            this.a = str;
            this.b = z;
            this.c = h5PayCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            H5PayResultModel h5PayResultModelH5Pay = PayTask.this.h5Pay(new com.alipay.sdk.sys.a(PayTask.this.a, this.a, "payInterceptorWithUrl"), this.a, this.b);
            com.alipay.sdk.util.c.d(com.alipay.sdk.cons.a.x, "inc finished: " + h5PayResultModelH5Pay.getResultCode());
            this.c.onPayResult(h5PayResultModelH5Pay);
        }
    }

    public class b implements f.e {
        public b() {
        }

        @Override // com.alipay.sdk.util.f.e
        public void a() {
            PayTask.this.dismissLoading();
        }

        @Override // com.alipay.sdk.util.f.e
        public void b() {
        }
    }

    public class c {
        public String a;
        public String b;
        public String c;
        public String d;

        public c() {
            this.a = "";
            this.b = "";
            this.c = "";
            this.d = "";
        }

        public String a() {
            return this.c;
        }

        public String b() {
            return this.a;
        }

        public String c() {
            return this.b;
        }

        public String d() {
            return this.d;
        }

        public void a(String str) {
            this.c = str;
        }

        public void b(String str) {
            this.a = str;
        }

        public void c(String str) {
            this.b = str;
        }

        public void d(String str) {
            this.d = str;
        }

        public /* synthetic */ c(PayTask payTask, a aVar) {
            this();
        }
    }

    public PayTask(Activity activity) {
        this.a = activity;
        com.alipay.sdk.sys.b.d().a(this.a);
        this.b = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.j);
    }

    public static boolean b() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - k < j) {
            return true;
        }
        k = jElapsedRealtime;
        return false;
    }

    public static synchronized boolean fetchSdkConfig(Context context) {
        try {
            com.alipay.sdk.sys.b.d().a(context);
            long jElapsedRealtime = SystemClock.elapsedRealtime() / 1000;
            if (jElapsedRealtime - i < com.alipay.sdk.data.a.u().c()) {
                return false;
            }
            i = jElapsedRealtime;
            com.alipay.sdk.data.a.u().a(com.alipay.sdk.sys.a.e(), context.getApplicationContext());
            return true;
        } catch (Exception e) {
            com.alipay.sdk.util.c.a(e);
            return false;
        }
    }

    public void dismissLoading() {
        com.alipay.sdk.widget.a aVar = this.b;
        if (aVar != null) {
            aVar.a();
            this.b = null;
        }
    }

    public synchronized String fetchOrderInfoFromH5PayUrl(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                String strTrim = str.trim();
                if (strTrim.startsWith("https://wappaygw.alipay.com/service/rest.htm") || strTrim.startsWith("http://wappaygw.alipay.com/service/rest.htm")) {
                    String strTrim2 = strTrim.replaceFirst("(http|https)://wappaygw.alipay.com/service/rest.htm\\?", "").trim();
                    if (!TextUtils.isEmpty(strTrim2)) {
                        return "_input_charset=\"utf-8\"&ordertoken=\"" + l.a("<request_token>", "</request_token>", l.a(strTrim2).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + new com.alipay.sdk.sys.a(this.a, "", "").a("sc", "h5tonative") + "\"";
                    }
                }
                if (strTrim.startsWith("https://mclient.alipay.com/service/rest.htm") || strTrim.startsWith("http://mclient.alipay.com/service/rest.htm")) {
                    String strTrim3 = strTrim.replaceFirst("(http|https)://mclient.alipay.com/service/rest.htm\\?", "").trim();
                    if (!TextUtils.isEmpty(strTrim3)) {
                        return "_input_charset=\"utf-8\"&ordertoken=\"" + l.a("<request_token>", "</request_token>", l.a(strTrim3).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + new com.alipay.sdk.sys.a(this.a, "", "").a("sc", "h5tonative") + "\"";
                    }
                }
                if ((strTrim.startsWith("https://mclient.alipay.com/home/exterfaceAssign.htm") || strTrim.startsWith("http://mclient.alipay.com/home/exterfaceAssign.htm")) && ((strTrim.contains("alipay.wap.create.direct.pay.by.user") || strTrim.contains("create_forex_trade_wap")) && !TextUtils.isEmpty(strTrim.replaceFirst("(http|https)://mclient.alipay.com/home/exterfaceAssign.htm\\?", "").trim()))) {
                    com.alipay.sdk.sys.a aVar = new com.alipay.sdk.sys.a(this.a, "", "");
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(BreakpointSQLiteKey.URL, str);
                    jSONObject.put("bizcontext", aVar.a("sc", "h5tonative"));
                    return "new_external_info==" + jSONObject.toString();
                }
                a aVar2 = null;
                if (Pattern.compile("^(http|https)://(maliprod\\.alipay\\.com/w/trade_pay\\.do.?|mali\\.alipay\\.com/w/trade_pay\\.do.?|mclient\\.alipay\\.com/w/trade_pay\\.do.?)").matcher(str).find()) {
                    String strA = l.a("?", "", str);
                    if (!TextUtils.isEmpty(strA)) {
                        Map<String, String> mapA = l.a(strA);
                        StringBuilder sb = new StringBuilder();
                        if (a(false, true, com.alipay.sdk.app.statistic.b.H0, sb, mapA, com.alipay.sdk.app.statistic.b.H0, "alipay_trade_no")) {
                            a(true, false, "pay_phase_id", sb, mapA, "payPhaseId", "pay_phase_id", "out_relation_id");
                            sb.append("&biz_sub_type=\"TRADE\"");
                            sb.append("&biz_type=\"trade\"");
                            String str2 = mapA.get("app_name");
                            if (TextUtils.isEmpty(str2) && !TextUtils.isEmpty(mapA.get("cid"))) {
                                str2 = "ali1688";
                            } else if (TextUtils.isEmpty(str2) && (!TextUtils.isEmpty(mapA.get("sid")) || !TextUtils.isEmpty(mapA.get("s_id")))) {
                                str2 = "tb";
                            }
                            sb.append("&app_name=\"" + str2 + "\"");
                            if (!a(true, true, "extern_token", sb, mapA, "extern_token", "cid", "sid", "s_id")) {
                                return "";
                            }
                            a(true, false, "appenv", sb, mapA, "appenv");
                            sb.append("&pay_channel_id=\"alipay_sdk\"");
                            c cVar = new c(this, aVar2);
                            cVar.b(mapA.get("return_url"));
                            cVar.c(mapA.get("show_url"));
                            cVar.a(mapA.get("pay_order_id"));
                            String str3 = sb.toString() + "&bizcontext=\"" + new com.alipay.sdk.sys.a(this.a, "", "").a("sc", "h5tonative") + "\"";
                            this.g.put(str3, cVar);
                            return str3;
                        }
                    }
                }
                if (!strTrim.startsWith("https://mclient.alipay.com/cashier/mobilepay.htm") && !strTrim.startsWith("http://mclient.alipay.com/cashier/mobilepay.htm") && (!EnvUtils.isSandBox() || !strTrim.contains("mobileclientgw.alipaydev.com/cashier/mobilepay.htm"))) {
                    if (com.alipay.sdk.data.a.u().g() && Pattern.compile("^https?://(maliprod\\.alipay\\.com|mali\\.alipay\\.com)/batch_payment\\.do\\?").matcher(strTrim).find()) {
                        Uri uri = Uri.parse(strTrim);
                        String queryParameter = uri.getQueryParameter("return_url");
                        String queryParameter2 = uri.getQueryParameter("show_url");
                        String queryParameter3 = uri.getQueryParameter("pay_order_id");
                        String strA2 = a(uri.getQueryParameter("trade_nos"), uri.getQueryParameter("alipay_trade_no"));
                        String strA3 = a(uri.getQueryParameter("payPhaseId"), uri.getQueryParameter("pay_phase_id"), uri.getQueryParameter("out_relation_id"));
                        String[] strArr = new String[4];
                        strArr[0] = uri.getQueryParameter("app_name");
                        strArr[1] = !TextUtils.isEmpty(uri.getQueryParameter("cid")) ? "ali1688" : "";
                        strArr[2] = !TextUtils.isEmpty(uri.getQueryParameter("sid")) ? "tb" : "";
                        strArr[3] = !TextUtils.isEmpty(uri.getQueryParameter("s_id")) ? "tb" : "";
                        String strA4 = a(strArr);
                        String strA5 = a(uri.getQueryParameter("extern_token"), uri.getQueryParameter("cid"), uri.getQueryParameter("sid"), uri.getQueryParameter("s_id"));
                        String strA6 = a(uri.getQueryParameter("appenv"));
                        if (!TextUtils.isEmpty(strA2) && !TextUtils.isEmpty(strA4) && !TextUtils.isEmpty(strA5)) {
                            String str4 = String.format("trade_no=\"%s\"&pay_phase_id=\"%s\"&biz_type=\"trade\"&biz_sub_type=\"TRADE\"&app_name=\"%s\"&extern_token=\"%s\"&appenv=\"%s\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"%s\"", strA2, strA3, strA4, strA5, strA6, new com.alipay.sdk.sys.a(this.a, "", "").a("sc", "h5tonative"));
                            c cVar2 = new c(this, null);
                            cVar2.b(queryParameter);
                            cVar2.c(queryParameter2);
                            cVar2.a(queryParameter3);
                            cVar2.d(strA2);
                            this.g.put(str4, cVar2);
                            return str4;
                        }
                    }
                }
                String strA7 = new com.alipay.sdk.sys.a(this.a, "", "").a("sc", "h5tonative");
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(BreakpointSQLiteKey.URL, strTrim);
                jSONObject2.put("bizcontext", strA7);
                return String.format("new_external_info==%s", jSONObject2.toString());
            }
        } catch (Throwable th) {
            com.alipay.sdk.util.c.a(th);
        }
        return "";
    }

    public synchronized String fetchTradeToken() {
        return g.a(new com.alipay.sdk.sys.a(this.a, "", "fetchTradeToken"), this.a.getApplicationContext());
    }

    public String getVersion() {
        return "15.8.02";
    }

    public synchronized H5PayResultModel h5Pay(com.alipay.sdk.sys.a aVar, String str, boolean z) {
        H5PayResultModel h5PayResultModel;
        h5PayResultModel = new H5PayResultModel();
        try {
            String[] strArrSplit = a(aVar, str, z).split(g.b);
            HashMap map = new HashMap();
            for (String str2 : strArrSplit) {
                int iIndexOf = str2.indexOf("={");
                if (iIndexOf >= 0) {
                    String strSubstring = str2.substring(0, iIndexOf);
                    map.put(strSubstring, a(str2, strSubstring));
                }
            }
            if (map.containsKey(j.a)) {
                h5PayResultModel.setResultCode(map.get(j.a));
            }
            h5PayResultModel.setReturnUrl(a(str, map));
            if (TextUtils.isEmpty(h5PayResultModel.getReturnUrl())) {
                com.alipay.sdk.app.statistic.a.b(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.r0, "");
            }
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.s0, th);
            com.alipay.sdk.util.c.a(th);
        }
        return h5PayResultModel;
    }

    public synchronized String pay(String str, boolean z) {
        return a(new com.alipay.sdk.sys.a(this.a, str, "pay"), str, z);
    }

    public synchronized boolean payInterceptorWithUrl(String str, boolean z, H5PayCallback h5PayCallback) {
        String strFetchOrderInfoFromH5PayUrl;
        strFetchOrderInfoFromH5PayUrl = fetchOrderInfoFromH5PayUrl(str);
        if (!TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl)) {
            com.alipay.sdk.util.c.d(com.alipay.sdk.cons.a.x, "intercepted: " + strFetchOrderInfoFromH5PayUrl);
            new Thread(new a(strFetchOrderInfoFromH5PayUrl, z, h5PayCallback)).start();
        }
        return !TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl);
    }

    public synchronized Map<String, String> payV2(String str, boolean z) {
        com.alipay.sdk.sys.a aVar;
        aVar = new com.alipay.sdk.sys.a(this.a, str, "payV2");
        return j.a(aVar, a(aVar, str, z));
    }

    public void showLoading() {
        com.alipay.sdk.widget.a aVar = this.b;
        if (aVar != null) {
            aVar.d();
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0173 A[Catch: all -> 0x0210, PHI: r7
  0x0173: PHI (r7v18 java.lang.String) = (r7v17 java.lang.String), (r7v20 java.lang.String) binds: [B:38:0x0171, B:33:0x011b] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:9:0x0018, B:10:0x001b, B:12:0x0023, B:14:0x0037, B:15:0x003c, B:17:0x005d, B:19:0x0065, B:20:0x0068, B:22:0x006c, B:24:0x0074, B:25:0x0081, B:27:0x0089, B:32:0x00d0, B:40:0x0180, B:39:0x0173, B:37:0x0126, B:44:0x01a7, B:46:0x01f4, B:47:0x0201, B:48:0x020f, B:16:0x0058, B:36:0x011f, B:29:0x0099, B:31:0x00b3), top: B:52:0x0001, inners: #1, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized java.lang.String a(com.alipay.sdk.sys.a r5, java.lang.String r6, boolean r7) {
        /*
            Method dump skipped, instruction units count: 531
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.a(com.alipay.sdk.sys.a, java.lang.String, boolean):java.lang.String");
    }

    public static final String a(String... strArr) {
        if (strArr == null) {
            return "";
        }
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return "";
    }

    private boolean a(boolean z, boolean z2, String str, StringBuilder sb, Map<String, String> map, String... strArr) {
        String str2;
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                str2 = "";
                break;
            }
            String str3 = strArr[i2];
            if (!TextUtils.isEmpty(map.get(str3))) {
                str2 = map.get(str3);
                break;
            }
            i2++;
        }
        if (TextUtils.isEmpty(str2)) {
            return !z2;
        }
        if (z) {
            sb.append(com.alipay.sdk.sys.a.k);
            sb.append(str);
            sb.append("=\"");
            sb.append(str2);
            sb.append("\"");
            return true;
        }
        sb.append(str);
        sb.append("=\"");
        sb.append(str2);
        sb.append("\"");
        return true;
    }

    private String a(String str, Map<String, String> map) throws UnsupportedEncodingException {
        boolean zEquals = "9000".equals(map.get(j.a));
        String str2 = map.get(j.c);
        c cVarRemove = this.g.remove(str);
        String[] strArr = new String[2];
        strArr[0] = cVarRemove != null ? cVarRemove.a() : "";
        strArr[1] = cVarRemove != null ? cVarRemove.d() : "";
        a(strArr);
        if (map.containsKey("callBackUrl")) {
            return map.get("callBackUrl");
        }
        if (str2.length() > 15) {
            String strA = a(l.a("&callBackUrl=\"", "\"", str2), l.a("&call_back_url=\"", "\"", str2), l.a(com.alipay.sdk.cons.a.r, "\"", str2), URLDecoder.decode(l.a(com.alipay.sdk.cons.a.s, com.alipay.sdk.sys.a.k, str2), "utf-8"), URLDecoder.decode(l.a("&callBackUrl=", com.alipay.sdk.sys.a.k, str2), "utf-8"), l.a("call_back_url=\"", "\"", str2));
            if (!TextUtils.isEmpty(strA)) {
                return strA;
            }
        }
        if (cVarRemove != null) {
            String strB = zEquals ? cVarRemove.b() : cVarRemove.c();
            if (!TextUtils.isEmpty(strB)) {
                return strB;
            }
        }
        return cVarRemove != null ? com.alipay.sdk.data.a.u().p() : "";
    }

    private String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf(g.d));
    }

    private f.e a() {
        return new b();
    }

    private String a(String str, com.alipay.sdk.sys.a aVar) {
        String strA = aVar.a(str);
        if (strA.contains("paymethod=\"expressGateway\"")) {
            return a(aVar, strA);
        }
        List<a.b> listK = com.alipay.sdk.data.a.u().k();
        if (!com.alipay.sdk.data.a.u().g || listK == null) {
            listK = com.alipay.sdk.app.a.d;
        }
        if (l.b(aVar, this.a, listK)) {
            f fVar = new f(this.a, aVar, a());
            com.alipay.sdk.util.c.d(com.alipay.sdk.cons.a.x, "pay inner started: " + strA);
            String strA2 = fVar.a(strA);
            com.alipay.sdk.util.c.d(com.alipay.sdk.cons.a.x, "pay inner raw result: " + strA2);
            fVar.a();
            if (!TextUtils.equals(strA2, f.j) && !TextUtils.equals(strA2, f.k)) {
                if (TextUtils.isEmpty(strA2)) {
                    return com.alipay.sdk.app.b.a();
                }
                if (!strA2.contains(PayResultActivity.b)) {
                    return strA2;
                }
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.o0);
                return a(aVar, strA, listK, strA2, this.a);
            }
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.m0);
            return a(aVar, strA);
        }
        com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.n0);
        return a(aVar, strA);
    }

    public static String a(com.alipay.sdk.sys.a aVar, String str, List<a.b> list, String str2, Activity activity) {
        l.b bVarA = l.a(aVar, activity, list);
        if (bVarA == null || bVarA.a(aVar) || bVarA.a() || !TextUtils.equals(bVarA.a.packageName, PayResultActivity.d)) {
            return str2;
        }
        com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "PayTask not_login");
        String strValueOf = String.valueOf(str.hashCode());
        PayResultActivity.c.put(strValueOf, new Object());
        Intent intent = new Intent(activity, (Class<?>) PayResultActivity.class);
        intent.putExtra(PayResultActivity.f, str);
        intent.putExtra(PayResultActivity.g, activity.getPackageName());
        intent.putExtra(PayResultActivity.e, strValueOf);
        a.C0007a.a(aVar, intent);
        activity.startActivity(intent);
        synchronized (PayResultActivity.c.get(strValueOf)) {
            try {
                com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "PayTask wait");
                PayResultActivity.c.get(strValueOf).wait();
            } catch (InterruptedException unused) {
                com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "PayTask interrupted");
                return com.alipay.sdk.app.b.a();
            }
        }
        String str3 = PayResultActivity.b.b;
        com.alipay.sdk.util.c.b(com.alipay.sdk.cons.a.x, "PayTask ret: " + str3);
        return str3;
    }

    private String a(com.alipay.sdk.sys.a aVar, String str) {
        showLoading();
        com.alipay.sdk.app.c cVarB = null;
        try {
            try {
                try {
                    JSONObject jSONObjectC = new com.alipay.sdk.packet.impl.f().a(aVar, this.a.getApplicationContext(), str).c();
                    String strOptString = jSONObjectC.optString("end_code", null);
                    List<com.alipay.sdk.protocol.b> listA = com.alipay.sdk.protocol.b.a(jSONObjectC.optJSONObject(com.alipay.sdk.cons.c.c).optJSONObject(com.alipay.sdk.cons.c.d));
                    for (int i2 = 0; i2 < listA.size(); i2++) {
                        if (listA.get(i2).a() == com.alipay.sdk.protocol.a.Update) {
                            com.alipay.sdk.protocol.b.a(listA.get(i2));
                        }
                    }
                    a(aVar, jSONObjectC);
                    dismissLoading();
                    com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                    for (int i3 = 0; i3 < listA.size(); i3++) {
                        com.alipay.sdk.protocol.b bVar = listA.get(i3);
                        if (bVar.a() == com.alipay.sdk.protocol.a.WapPay) {
                            String strA = a(aVar, bVar);
                            dismissLoading();
                            com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                            return strA;
                        }
                        if (bVar.a() == com.alipay.sdk.protocol.a.OpenWeb) {
                            String strA2 = a(aVar, bVar, strOptString);
                            dismissLoading();
                            com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                            return strA2;
                        }
                    }
                    dismissLoading();
                    com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                } catch (Throwable th) {
                    com.alipay.sdk.util.c.a(th);
                    com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.F, th);
                    dismissLoading();
                    com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                }
            } catch (IOException e) {
                com.alipay.sdk.app.c cVarB2 = com.alipay.sdk.app.c.b(com.alipay.sdk.app.c.NETWORK_ERROR.b());
                com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.k, e);
                dismissLoading();
                com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
                cVarB = cVarB2;
            }
            if (cVarB == null) {
                cVarB = com.alipay.sdk.app.c.b(com.alipay.sdk.app.c.FAILED.b());
            }
            return com.alipay.sdk.app.b.a(cVarB.b(), cVarB.a(), "");
        } catch (Throwable th2) {
            dismissLoading();
            com.alipay.sdk.app.statistic.a.a(this.a, aVar, str, aVar.d);
            throw th2;
        }
    }

    private void a(com.alipay.sdk.sys.a aVar, JSONObject jSONObject) {
        try {
            String strOptString = jSONObject.optString("tid");
            String strOptString2 = jSONObject.optString(com.alipay.sdk.tid.a.j);
            if (TextUtils.isEmpty(strOptString) || TextUtils.isEmpty(strOptString2)) {
                return;
            }
            com.alipay.sdk.tid.a.a(com.alipay.sdk.sys.b.d().b()).a(strOptString, strOptString2);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.T, th);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x008f, code lost:
    
        r0 = r6.c();
        r11 = com.alipay.sdk.app.b.a(java.lang.Integer.valueOf(r0[1]).intValue(), r0[0], com.alipay.sdk.util.l.e(r10, r0[2]));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(com.alipay.sdk.sys.a r10, com.alipay.sdk.protocol.b r11, java.lang.String r12) {
        /*
            Method dump skipped, instruction units count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.a(com.alipay.sdk.sys.a, com.alipay.sdk.protocol.b, java.lang.String):java.lang.String");
    }

    private String a(com.alipay.sdk.sys.a aVar, com.alipay.sdk.protocol.b bVar) {
        String[] strArrC = bVar.c();
        Intent intent = new Intent(this.a, (Class<?>) H5PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(BreakpointSQLiteKey.URL, strArrC[0]);
        if (strArrC.length == 2) {
            bundle.putString("cookie", strArrC[1]);
        }
        intent.putExtras(bundle);
        a.C0007a.a(aVar, intent);
        this.a.startActivity(intent);
        Object obj = h;
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException e) {
                com.alipay.sdk.util.c.a(e);
                return com.alipay.sdk.app.b.a();
            }
        }
        String strD = com.alipay.sdk.app.b.d();
        return TextUtils.isEmpty(strD) ? com.alipay.sdk.app.b.a() : strD;
    }
}
