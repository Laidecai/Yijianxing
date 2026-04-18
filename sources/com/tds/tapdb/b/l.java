package com.tds.tapdb.b;

import android.text.TextUtils;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class l {
    public static void a(String str) throws com.tds.tapdb.a.a {
        if (TextUtils.isEmpty(str)) {
            throw new com.tds.tapdb.a.a("The key is empty.");
        }
    }

    public static void a(JSONObject jSONObject) throws com.tds.tapdb.a.a {
        if (jSONObject == null) {
            return;
        }
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            a(next);
            try {
                Object obj = jSONObject.get(next);
                if (obj == JSONObject.NULL) {
                    itKeys.remove();
                } else if (!(obj instanceof CharSequence) && !(obj instanceof Number) && !(obj instanceof JSONArray) && !(obj instanceof Boolean) && !(obj instanceof Date)) {
                    throw new com.tds.tapdb.a.a("The property value must be an instance of CharSequence/Number/Boolean/JSONArray. [key='" + next + "', value='" + obj.toString() + "']");
                }
            } catch (JSONException unused) {
                throw new com.tds.tapdb.a.a("Unexpected property key. [key='" + next + "']");
            }
        }
    }

    public static void b(String str) throws com.tds.tapdb.a.a {
        if (TextUtils.isEmpty(str)) {
            throw new com.tds.tapdb.a.a("The value is empty.");
        }
        if (str.length() <= 255) {
            return;
        }
        throw new com.tds.tapdb.a.a("The " + str + " is too long, max length is 255.");
    }
}
