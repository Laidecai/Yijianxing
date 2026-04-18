package com.tds.common.bridge.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.tds.common.bridge.Bridge;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.BridgeHolder;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;
import com.tds.common.bridge.command.Command;
import com.tds.common.bridge.exception.EngineBridgeException;
import com.tds.common.bridge.exception.EngineBridgeExceptionStatus;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class BridgeReflect {
    public static boolean checkServiceLegal(Class<?> cls) {
        if (cls.isInterface() && cls.getAnnotation(BridgeService.class) != null) {
            return IBridgeService.class.isAssignableFrom(cls);
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Class<? extends IBridgeService> getLegalService(Class<?> cls) {
        for (Class<? extends IBridgeService> cls2 : cls.getInterfaces()) {
            if (checkServiceLegal(cls2)) {
                return cls2;
            }
        }
        throw new EngineBridgeException(EngineBridgeExceptionStatus.COMMAND_SERVICE_ERROR.getMessage());
    }

    public static void checkCommand(Command command) {
        if (TextUtils.isEmpty(command.service) || TextUtils.isEmpty(command.method)) {
            throw new EngineBridgeException(EngineBridgeExceptionStatus.COMMAND_PARSE_ERROR.getMessage());
        }
    }

    public static Class<? extends IBridgeService> getRegisterService(Command command) {
        Class<? extends IBridgeService> key = BridgeHolder.INSTANCE.getBridgeService(command.service).getKey();
        BridgeService bridgeService = (BridgeService) key.getAnnotation(BridgeService.class);
        if (bridgeService != null && bridgeService.value().equals(command.service)) {
            return key;
        }
        throw new EngineBridgeException(EngineBridgeExceptionStatus.COMMAND_SERVICE_ERROR.getMessage());
    }

    public static Object[] constructorCommandArgs(Method method, Command command, BridgeCallback bridgeCallback) {
        JSONObject jSONObject;
        int length;
        Class<?>[] parameterTypes = method.getParameterTypes();
        int length2 = parameterTypes.length;
        Object[] objArr = new Object[length2];
        try {
            if (TextUtils.isEmpty(command.args)) {
                jSONObject = new JSONObject();
                length = 0;
            } else {
                jSONObject = new JSONObject(command.args);
                length = jSONObject.length();
            }
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> cls = parameterTypes[i];
                if (cls == Activity.class) {
                    objArr[i] = Bridge.getInstance().getActivity();
                } else if (cls == BridgeCallback.class) {
                    objArr[i] = bridgeCallback;
                } else {
                    objArr[i] = findParams(jSONObject, method);
                }
                length++;
            }
            if (!checkParamsMatchMethod(jSONObject) || length != length2) {
                return new Object[0];
            }
        } catch (IllegalArgumentException | JSONException e) {
            e.printStackTrace();
        }
        return objArr;
    }

    public static boolean checkParamsMatchMethod(JSONObject jSONObject) {
        return jSONObject.length() == 0;
    }

    public static Object findParams(JSONObject jSONObject, Method method) throws JSONException {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int length = parameterAnnotations.length;
        int i = 0;
        while (true) {
            Object obj = null;
            if (i >= length) {
                return null;
            }
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof BridgeParam) {
                    BridgeParam bridgeParam = (BridgeParam) annotation;
                    if (jSONObject.has(bridgeParam.value())) {
                        Object obj2 = jSONObject.get(bridgeParam.value());
                        jSONObject.remove(bridgeParam.value());
                        if (obj2 == JSONObject.NULL) {
                            Log.d("Command", " method " + method.getDeclaringClass().getName() + "." + method.getName() + " param " + bridgeParam.value() + " convert jsonNull to null");
                        } else {
                            obj = obj2;
                        }
                        return obj instanceof JSONArray ? filterArray((JSONArray) obj, bridgeParam.arrayClz()) : obj;
                    }
                }
            }
            i++;
        }
    }

    private static Object[] filterArray(JSONArray jSONArray, Class<?> cls) throws JSONException {
        int length = jSONArray.length();
        int i = 0;
        if (cls == Boolean.TYPE) {
            Boolean[] boolArr = new Boolean[length];
            while (i < length) {
                Array.setBoolean(boolArr, i, jSONArray.getBoolean(i));
                i++;
            }
            return boolArr;
        }
        if (cls == Integer.TYPE) {
            Integer[] numArr = new Integer[length];
            while (i < length) {
                Array.setInt(numArr, i, jSONArray.getInt(i));
                i++;
            }
            return numArr;
        }
        if (cls == Long.TYPE) {
            Long[] lArr = new Long[length];
            while (i < length) {
                Array.setLong(lArr, i, jSONArray.getLong(i));
                i++;
            }
            return lArr;
        }
        if (cls == Float.TYPE) {
            Float[] fArr = new Float[length];
            while (i < length) {
                Array.setFloat(fArr, i, (float) jSONArray.getDouble(i));
                i++;
            }
            return fArr;
        }
        if (cls == Double.TYPE) {
            Double[] dArr = new Double[length];
            while (i < length) {
                Array.setDouble(dArr, i, jSONArray.getDouble(i));
                i++;
            }
            return dArr;
        }
        if (cls == String.class) {
            String[] strArr = new String[length];
            while (i < length) {
                strArr[i] = jSONArray.getString(i);
                i++;
            }
            return strArr;
        }
        throw new EngineBridgeException(EngineBridgeExceptionStatus.COMMAND_ARGS_ERROR.getExpandMessage("数组类型的参数必须为基础数据类型!"));
    }
}
