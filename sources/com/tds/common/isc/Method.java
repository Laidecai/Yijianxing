package com.tds.common.isc;

/* JADX INFO: loaded from: classes.dex */
public class Method {
    private final java.lang.reflect.Method method;

    Method(java.lang.reflect.Method method) {
        this.method = method;
    }

    public <T> T call(Object... objArr) throws IscException {
        try {
            return (T) this.method.invoke(null, objArr);
        } catch (Throwable th) {
            throw new IscException(th);
        }
    }
}
