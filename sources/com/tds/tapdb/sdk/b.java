package com.tds.tapdb.sdk;

import android.text.TextUtils;
import com.tds.tapdb.b.g;
import com.tds.tapdb.b.n;
import java.net.URLEncoder;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class b {
    private static String a = "https://e.tapdb.net/";
    private static String b = "https://ce.tapdb.net/custom";
    public static final String c = "event";
    public static final String d = "identify";

    b() {
    }

    public static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            n.b(" set customEventHost error : invalid customEventHost params");
        } else {
            b = str;
        }
    }

    public static void a(String str, JSONObject jSONObject) {
        StringBuilder sb = new StringBuilder(a);
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        String string = sb.toString();
        n.a("post data to:  " + string + "  data:  " + jSONObject.toString());
        int i = 1000;
        while (true) {
            i--;
            if (i <= 0) {
                return;
            }
            boolean zL = false;
            try {
                g gVarA = g.f((CharSequence) string).d(true).d("Content-Type", g.p).g(5000).c(5000).a(URLEncoder.encode(jSONObject.toString(), "UTF-8").replaceAll("\\+", "%20").getBytes("UTF-8"));
                zL = gVarA.L();
                if (zL) {
                    n.a("post data to: " + string + " success ");
                    return;
                }
                n.b("post data to: " + string + " failed:   " + gVarA.G());
            } catch (Exception e) {
                n.b("post data to:  " + string + " with error:  " + e.getCause().getMessage());
            }
            if (!zL) {
                try {
                    Thread.sleep(5000L);
                } catch (Exception e2) {
                    n.b(e2.getMessage());
                }
            }
        }
    }

    @Deprecated
    public static void a(JSONObject jSONObject) {
        n.a("post data to:  " + b + "  data:  " + jSONObject.toString());
        try {
            g gVarA = g.f((CharSequence) b).d(true).d("Content-Type", "application/json").g(5000).c(5000).a(jSONObject.toString().getBytes("UTF-8"));
            if (gVarA.L()) {
                n.a("post data to: " + b + " success ");
            } else {
                n.b("post data to: " + b + " failed:   " + gVarA.G());
            }
        } catch (Exception e) {
            n.b("post data to:  " + b + " with error:  " + e.getCause().getMessage());
        }
    }

    public static void b(String str) {
        if (TextUtils.isEmpty(str)) {
            n.b(" set host error : invalid host params");
        } else {
            a = str;
        }
    }
}
