package com.alipay.sdk.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class j {
    public static final String a = "resultStatus";
    public static final String b = "memo";
    public static final String c = "result";

    public static Map<String, String> a(com.alipay.sdk.sys.a aVar, String str) {
        Map<String, String> mapA = a();
        try {
            return a(str);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(aVar, com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.r, th);
            return mapA;
        }
    }

    public static String b(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf(g.d));
    }

    public static Map<String, String> a() {
        com.alipay.sdk.app.c cVarB = com.alipay.sdk.app.c.b(com.alipay.sdk.app.c.CANCELED.b());
        HashMap map = new HashMap();
        map.put(a, Integer.toString(cVarB.b()));
        map.put(b, cVarB.a());
        map.put(c, "");
        return map;
    }

    public static Map<String, String> a(String str) {
        String[] strArrSplit = str.split(g.b);
        HashMap map = new HashMap();
        for (String str2 : strArrSplit) {
            String strSubstring = str2.substring(0, str2.indexOf("={"));
            map.put(strSubstring, b(str2, strSubstring));
        }
        return map;
    }

    public static String a(String str, String str2) {
        try {
            Matcher matcher = Pattern.compile("(^|;)" + str2 + "=\\{([^}]*?)\\}").matcher(str);
            if (matcher.find()) {
                return matcher.group(2);
            }
        } catch (Throwable th) {
            c.a(th);
        }
        return "?";
    }
}
