package com.alipay.sdk.protocol;

import android.text.TextUtils;
import com.alipay.sdk.cons.c;
import com.alipay.sdk.util.g;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public a a;
    public String b;
    public String[] c;

    public b(String str) {
        this.b = str;
    }

    public static void a(b bVar) {
        String[] strArrC = bVar.c();
        if (strArrC.length == 3 && TextUtils.equals("tid", strArrC[0])) {
            com.alipay.sdk.tid.a aVarA = com.alipay.sdk.tid.a.a(com.alipay.sdk.sys.b.d().b());
            if (TextUtils.isEmpty(strArrC[1]) || TextUtils.isEmpty(strArrC[2])) {
                return;
            }
            aVarA.a(strArrC[1], strArrC[2]);
        }
    }

    public static String[] b(String str) {
        ArrayList arrayList = new ArrayList();
        int iIndexOf = str.indexOf(40);
        int iLastIndexOf = str.lastIndexOf(41);
        if (iIndexOf == -1 || iLastIndexOf == -1 || iLastIndexOf <= iIndexOf) {
            return null;
        }
        for (String str2 : str.substring(iIndexOf + 1, iLastIndexOf).split("' *, *'", -1)) {
            arrayList.add(str2.trim().replaceAll("'", "").replaceAll("\"", ""));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public String[] c() {
        return this.c;
    }

    public b(String str, a aVar) {
        this.b = str;
        this.a = aVar;
    }

    public static List<b> a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        String[] strArrA = a(jSONObject.optString(c.e, ""));
        for (int i = 0; i < strArrA.length; i++) {
            a aVarA = a.a(strArrA[i]);
            if (aVarA != a.None) {
                b bVar = new b(strArrA[i], aVarA);
                bVar.c = b(strArrA[i]);
                arrayList.add(bVar);
            }
        }
        return arrayList;
    }

    public String b() {
        return this.b;
    }

    public static String[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split(g.b);
    }

    public a a() {
        return this.a;
    }
}
