package com.alipay.a.a;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public final class g implements i, j {
    /* JADX WARN: Removed duplicated region for block: B:54:0x0049  */
    @Override // com.alipay.a.a.j
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object a(java.lang.Object r9) throws java.lang.IllegalAccessException {
        /*
            r8 = this;
            java.util.TreeMap r0 = new java.util.TreeMap
            r0.<init>()
            java.lang.Class r1 = r9.getClass()
        L9:
            java.lang.reflect.Field[] r2 = r1.getDeclaredFields()
            java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L5b
            if (r2 == 0) goto L56
            int r3 = r2.length
            if (r3 <= 0) goto L56
            int r3 = r2.length
            r4 = 0
        L1c:
            if (r4 >= r3) goto L56
            r5 = r2[r4]
            if (r5 == 0) goto L49
            if (r9 != 0) goto L25
            goto L49
        L25:
            java.lang.String r6 = r5.getName()
            java.lang.String r7 = "this$0"
            boolean r6 = r7.equals(r6)
            if (r6 == 0) goto L32
            goto L49
        L32:
            boolean r6 = r5.isAccessible()
            r7 = 1
            r5.setAccessible(r7)
            java.lang.Object r7 = r5.get(r9)
            if (r7 != 0) goto L41
            goto L49
        L41:
            r5.setAccessible(r6)
            java.lang.Object r6 = com.alipay.a.a.f.b(r7)
            goto L4a
        L49:
            r6 = 0
        L4a:
            if (r6 == 0) goto L53
            java.lang.String r5 = r5.getName()
            r0.put(r5, r6)
        L53:
            int r4 = r4 + 1
            goto L1c
        L56:
            java.lang.Class r1 = r1.getSuperclass()
            goto L9
        L5b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.a.a.g.a(java.lang.Object):java.lang.Object");
    }

    @Override // com.alipay.a.a.i
    public final Object a(Object obj, Type type) throws IllegalAccessException, InstantiationException {
        if (!obj.getClass().equals(org.json.alipay.b.class)) {
            return null;
        }
        org.json.alipay.b bVar = (org.json.alipay.b) obj;
        Class superclass = (Class) type;
        Object objNewInstance = superclass.newInstance();
        while (!superclass.equals(Object.class)) {
            Field[] declaredFields = superclass.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    String name = field.getName();
                    Type genericType = field.getGenericType();
                    if (bVar.b(name)) {
                        field.setAccessible(true);
                        field.set(objNewInstance, e.a(bVar.a(name), genericType));
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
        return objNewInstance;
    }

    @Override // com.alipay.a.a.i, com.alipay.a.a.j
    public final boolean a(Class<?> cls) {
        return true;
    }
}
