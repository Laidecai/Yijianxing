package com.tds.common.net.json;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
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
    public static <T> T parse(JSONObject jSONObject, Class<T> cls) throws JSONException {
        try {
            return (T) parseObject(jSONObject, cls);
        } catch (Exception e) {
            if (e instanceof JSONException) {
                throw ((JSONException) e);
            }
            throw ((JSONException) new JSONException(jSONObject.toString()).initCause(e));
        }
    }

    public static <T> T parse(String str, Class<T> cls) throws JSONException {
        try {
            return (T) parseInternal(str, cls);
        } catch (Exception e) {
            if (e instanceof JSONException) {
                throw ((JSONException) e);
            }
            throw ((JSONException) new JSONException(str).initCause(e));
        }
    }

    private static <T> T parseInternal(String str, Class<T> cls) throws IllegalAccessException, JSONException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (cls.isArray()) {
            return (T) parseArray(str, cls);
        }
        if (cls.isPrimitive()) {
            return (T) parsePrimitive(str, cls);
        }
        if (cls == String.class) {
            return (T) parseString(str);
        }
        return (T) parseObject(str, cls);
    }

    public static <T> T parse(String str, TypeRef<T> typeRef) throws JSONException {
        try {
            return (T) parseInternal(str, typeRef);
        } catch (Exception e) {
            if (e instanceof JSONException) {
                throw ((JSONException) e);
            }
            throw ((JSONException) new JSONException(str).initCause(e));
        }
    }

    private static <T> T parseInternal(String str, TypeRef<T> typeRef) throws JSONException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Class cls = (Class) typeRef.type.getRawType();
        Type[] actualTypeArguments = typeRef.type.getActualTypeArguments();
        int length = actualTypeArguments.length + 1;
        Class<?>[] clsArr = new Class[length];
        clsArr[0] = JSONObject.class;
        for (int i = 1; i < length; i++) {
            clsArr[i] = Class.class;
        }
        Constructor<T> declaredConstructor = cls.getDeclaredConstructor(clsArr);
        declaredConstructor.setAccessible(true);
        Object[] objArr = new Object[length];
        objArr[0] = new JSONObject(str);
        for (int i2 = 1; i2 < length; i2++) {
            objArr[i2] = actualTypeArguments[i2 - 1];
        }
        return declaredConstructor.newInstance(objArr);
    }

    private static <T> T parseArray(String str, Class<T> cls) throws JSONException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Class<?> componentType = cls.getComponentType();
        JSONArray jSONArray = new JSONArray(str);
        int length = jSONArray.length();
        T t = (T) Array.newInstance(componentType, length);
        int i = 0;
        if (componentType.isPrimitive()) {
            if (componentType == Boolean.TYPE) {
                while (i < length) {
                    Array.setBoolean(t, i, jSONArray.getBoolean(i));
                    i++;
                }
            } else if (componentType == Integer.TYPE) {
                while (i < length) {
                    Array.setInt(t, i, jSONArray.getInt(i));
                    i++;
                }
            } else if (componentType == Long.TYPE) {
                while (i < length) {
                    Array.setLong(t, i, jSONArray.getLong(i));
                    i++;
                }
            } else if (componentType == Float.TYPE) {
                while (i < length) {
                    Array.setFloat(t, i, (float) jSONArray.getDouble(i));
                    i++;
                }
            } else {
                if (componentType != Double.TYPE) {
                    throw new UnsupportedOperationException(str + " - " + componentType.toString());
                }
                while (i < length) {
                    Array.setDouble(t, i, jSONArray.getDouble(i));
                    i++;
                }
            }
        } else {
            Constructor<?> declaredConstructor = componentType.getDeclaredConstructor(JSONObject.class);
            declaredConstructor.setAccessible(true);
            for (int i2 = 0; i2 < length; i2++) {
                Array.set(t, i2, declaredConstructor.newInstance(jSONArray.getJSONObject(i2)));
            }
        }
        return t;
    }

    private static <T> T parsePrimitive(String str, Class<T> cls) {
        if (cls == Boolean.TYPE) {
            return (T) Boolean.valueOf(str);
        }
        if (cls == Integer.TYPE) {
            return (T) Integer.valueOf(str);
        }
        if (cls == Long.TYPE) {
            return (T) Long.valueOf(str);
        }
        if (cls == Float.TYPE) {
            return (T) Float.valueOf(str);
        }
        if (cls == Double.TYPE) {
            return (T) Double.valueOf(str);
        }
        throw new UnsupportedOperationException(str + " - " + cls.toString());
    }

    private static String parseString(String str) {
        String strTrim = str.trim();
        return strTrim.substring(1, strTrim.length() - 1);
    }

    private static <T> T parseObject(String str, Class<T> cls) throws JSONException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        return (T) parseObject(new JSONObject(str), cls);
    }

    private static <T> T parseObject(JSONObject jSONObject, Class<T> cls) throws JSONException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Constructor<T> declaredConstructor = cls.getDeclaredConstructor(JSONObject.class);
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance(jSONObject);
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
}
