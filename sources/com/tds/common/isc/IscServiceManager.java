package com.tds.common.isc;

import android.text.TextUtils;
import com.tds.common.log.Logger;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class IscServiceManager {
    static final Logger LOG = Logger.getCommonLogger();
    static final Map<String, Class<?>> map = new HashMap();

    private IscServiceManager() {
    }

    public static void register(Class<?> cls) {
        IscService iscService = (IscService) cls.getAnnotation(IscService.class);
        if (iscService == null) {
            LOG.e("Isc service class must be annotated with @IscService");
            return;
        }
        String strValue = iscService.value();
        if (TextUtils.isEmpty(strValue)) {
            LOG.e("Isc service name cannot be null or empty");
            return;
        }
        LOG.i("register isc service " + strValue + " " + cls.getName());
        map.put(strValue, cls);
    }

    public static void unregister(Class<?> cls) {
        IscService iscService = (IscService) cls.getAnnotation(IscService.class);
        if (iscService != null) {
            String strValue = iscService.value();
            if (TextUtils.isEmpty(strValue)) {
                return;
            }
            LOG.i("unregister isc service " + strValue);
            map.remove(strValue);
        }
    }

    public static boolean hasService(String str) {
        return map.containsKey(str);
    }

    public static boolean hasMethod(String str, String str2) {
        try {
            return service(str).hasMethod(str2);
        } catch (IscException unused) {
            return false;
        }
    }

    public static Service service(String str) throws IscException {
        Class<?> cls = map.get(str);
        if (cls == null) {
            throw new IscException(str + " service not registered");
        }
        return new Service(cls);
    }
}
