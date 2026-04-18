package com.tds.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JsonUtil {
    public static Map<String, Object> jsonToMap(JSONObject jSONObject) throws JSONException {
        return jSONObject != JSONObject.NULL ? toMap(jSONObject) : new HashMap();
    }

    public static Map<String, Object> toMap(JSONObject jSONObject) throws JSONException {
        HashMap map = new HashMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object map2 = jSONObject.get(next);
            if (map2 instanceof JSONArray) {
                map2 = toList((JSONArray) map2);
            } else if (map2 instanceof JSONObject) {
                map2 = toMap((JSONObject) map2);
            }
            map.put(next, map2);
        }
        return map;
    }

    public static List<Object> toList(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            Object map = jSONArray.get(i);
            if (map instanceof JSONArray) {
                map = toList((JSONArray) map);
            } else if (map instanceof JSONObject) {
                map = toMap((JSONObject) map);
            }
            arrayList.add(map);
        }
        return arrayList;
    }

    public static String toJsonStr(Map<String, Object> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getKey().length() > 0) {
                jSONObject.put(entry.getKey(), entry.getValue());
            }
        }
        return jSONObject.toString();
    }
}
