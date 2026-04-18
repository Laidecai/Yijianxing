package com.tds.common.bridge.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.text.Typography;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class BridgeJsonHelper {
    private static final String TAG = "BridgeJsonHelper";

    public static <T> T parseJson2Object(Class<T> cls, String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject = null;
        }
        return (T) parseJson2Object(cls, jSONObject);
    }

    private static <T> T parseJson2Object(Class<T> cls, JSONObject jSONObject) {
        T tNewInstance;
        Object objNewInstance;
        try {
            tNewInstance = cls.newInstance();
            try {
                for (Field field : cls.getDeclaredFields()) {
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    if (type.isPrimitive()) {
                        setProperty(tNewInstance, field, jSONObject.opt(field.getName()));
                    } else {
                        if (type.isInterface() && type.getSimpleName().contains("List")) {
                            objNewInstance = ArrayList.class.newInstance();
                        } else {
                            objNewInstance = type.newInstance();
                        }
                        if (objNewInstance instanceof List) {
                            Class cls2 = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                            JSONArray jSONArray = jSONObject.getJSONArray(field.getName());
                            for (int i = 0; i < jSONArray.length(); i++) {
                                ((List) objNewInstance).add(parseJson2Object(cls2, jSONObject.getJSONArray(field.getName()).getJSONObject(i)));
                            }
                            setProperty(tNewInstance, field, objNewInstance);
                        } else if (objNewInstance instanceof String) {
                            setProperty(tNewInstance, field, jSONObject.opt(field.getName()));
                        } else {
                            setProperty(tNewInstance, field, parseJson2Object(type, jSONObject.getJSONObject(field.getName())));
                        }
                    }
                }
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e = e2;
            tNewInstance = null;
        }
        return tNewInstance;
    }

    private static void setProperty(Object obj, Field field, Object obj2) {
        try {
            Method declaredMethod = obj.getClass().getDeclaredMethod("set" + field.getName().substring(0, 1).toUpperCase(Locale.getDefault()) + field.getName().substring(1), field.getType());
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, obj2);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static String object2JsonString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (!(obj instanceof Number) && !(obj instanceof Boolean)) {
            if (!(obj instanceof String)) {
                return obj instanceof Map ? map2Json((Map) obj) : obj instanceof Collection ? coll2Json((Collection) obj) : obj.getClass().isArray() ? array2Json(obj) : customObject2Json(obj);
            }
            return "\"" + escape((String) obj) + "\"";
        }
        return obj.toString();
    }

    private static String array2Json(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int length = Array.getLength(obj) - 1;
        if (length > -1) {
            int i = 0;
            while (i < length) {
                sb.append(object2JsonString(Array.get(obj, i)));
                sb.append(", ");
                i++;
            }
            sb.append(object2JsonString(Array.get(obj, i)));
        }
        sb.append(']');
        return sb.toString();
    }

    private static String coll2Json(Collection<?> collection) {
        if (collection == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            sb.append(object2JsonString(it.next()));
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private static String customObject2Json(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        HashMap map = new HashMap();
        for (Field field : declaredFields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                field.setAccessible(true);
                Object obj2 = null;
                try {
                    obj2 = field.get(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put(name, obj2);
            }
        }
        return map2Json(map);
    }

    private static String map2Json(Map<?, ?> map) {
        if (map == null) {
            return "null";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('{');
        Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<?, ?> next = it.next();
            String str = (String) next.getKey();
            if (str != null) {
                stringBuffer.append(Typography.quote);
                escape(str, stringBuffer);
                stringBuffer.append(Typography.quote);
                stringBuffer.append(':');
                stringBuffer.append(object2JsonString(next.getValue()));
                if (it.hasNext()) {
                    stringBuffer.append(", ");
                }
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    private static String escape(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        escape(str, stringBuffer);
        return stringBuffer.toString();
    }

    private static void escape(String str, StringBuffer stringBuffer) {
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\f') {
                stringBuffer.append("\\f");
            } else if (cCharAt == '\r') {
                stringBuffer.append("\\r");
            } else if (cCharAt == '\"') {
                stringBuffer.append("\\\"");
            } else if (cCharAt == '/') {
                stringBuffer.append("\\/");
            } else if (cCharAt == '\\') {
                stringBuffer.append("\\\\");
            } else {
                switch (cCharAt) {
                    case '\b':
                        stringBuffer.append("\\b");
                        break;
                    case '\t':
                        stringBuffer.append("\\t");
                        break;
                    case '\n':
                        stringBuffer.append("\\n");
                        break;
                    default:
                        if (cCharAt <= 31 || ((cCharAt >= 127 && cCharAt <= 159) || (cCharAt >= 8192 && cCharAt <= 8447))) {
                            String hexString = Integer.toHexString(cCharAt);
                            stringBuffer.append("\\u");
                            for (int i2 = 0; i2 < 4 - hexString.length(); i2++) {
                                stringBuffer.append('0');
                            }
                            stringBuffer.append(hexString.toUpperCase());
                        } else {
                            stringBuffer.append(cCharAt);
                        }
                        break;
                }
            }
        }
    }
}
