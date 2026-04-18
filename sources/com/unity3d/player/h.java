package com.unity3d.player;

import android.graphics.BitmapFactory;
import com.tds.common.tracker.model.ActionModel;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    private String a;
    private String b;
    private String[] c;
    private String[] d;
    private int e;
    private long f;
    private String[] g;
    private String[] h;
    private String i;
    private String j;
    private String[] k;

    public h(JSONObject jSONObject) {
        this.a = jSONObject.optString("imageUrl");
        this.b = jSONObject.optString("clickUrl", "");
        this.e = jSONObject.optInt("duration", 5);
        this.f = jSONObject.optLong("expiration", 0L);
        this.c = a(jSONObject.optJSONArray(ActionModel.PARAM_NAME_IMPRESSION));
        this.d = a(jSONObject.optJSONArray("clickImpression"));
        this.g = a(jSONObject.optJSONArray("primaryClickImpression"));
        this.h = a(jSONObject.optJSONArray("fallbackClickImpression"));
        this.i = jSONObject.optString("mediaType");
        this.j = jSONObject.optString("videoUrl");
        this.k = a(jSONObject.optJSONArray("completeClickImpression"));
    }

    private static String[] a(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArray.optString(i);
        }
        return strArr;
    }

    public final boolean a() {
        if ("VIDEO".equals(j())) {
            return (k() == null || k() == "") ? false : true;
        }
        String strB = b();
        if (strB == null || strB == "") {
            return false;
        }
        if (strB.startsWith("file://")) {
            strB = strB.substring(7);
        }
        return BitmapFactory.decodeFile(strB) != null;
    }

    public final String b() {
        return this.a;
    }

    public final String c() {
        return this.b;
    }

    public final String[] d() {
        return this.c;
    }

    public final String[] e() {
        return this.d;
    }

    public final int f() {
        return this.e;
    }

    public final long g() {
        return this.f;
    }

    public final String[] h() {
        return this.g;
    }

    public final String[] i() {
        return this.h;
    }

    public final String j() {
        return this.i;
    }

    public final String k() {
        return this.j;
    }

    public final String[] l() {
        return this.k;
    }
}
