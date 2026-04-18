package com.tds.common.isc;

import java.lang.reflect.Modifier;

/* JADX INFO: loaded from: classes.dex */
public class Service {
    private final Class<?> clazz;

    Service(Class<?> cls) {
        this.clazz = cls;
    }

    public boolean hasMethod(String str) {
        try {
            return method(str) != null;
        } catch (IscException unused) {
            return false;
        }
    }

    public Method method(String str) throws IscException {
        try {
            java.lang.reflect.Method methodFindStaticMethod = findStaticMethod(str);
            if (methodFindStaticMethod == null) {
                throw new IscException(this.clazz.getName() + " does not contain public static method annotated with @IscMethod " + str);
            }
            return new Method(methodFindStaticMethod);
        } catch (Throwable th) {
            if (th instanceof IscException) {
                throw th;
            }
            throw new IscException(th);
        }
    }

    public <T> T directCall(String str, Object... objArr) throws IscException {
        try {
            return (T) method(str).call(objArr);
        } catch (Throwable th) {
            if (th instanceof IscException) {
                throw th;
            }
            throw new IscException(th);
        }
    }

    private java.lang.reflect.Method findStaticMethod(String str) {
        IscMethod iscMethod;
        for (java.lang.reflect.Method method : this.clazz.getDeclaredMethods()) {
            if (isPublicStaticMethod(method) && (iscMethod = (IscMethod) method.getAnnotation(IscMethod.class)) != null && str.equals(iscMethod.value())) {
                return method;
            }
        }
        return null;
    }

    private boolean isPublicStaticMethod(java.lang.reflect.Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers);
    }
}
