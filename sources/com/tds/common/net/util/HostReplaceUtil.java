package com.tds.common.net.util;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class HostReplaceUtil {
    private static final HostReplaceUtil instance = new HostReplaceUtil();
    private Map<String, String> hostMap;

    private HostReplaceUtil() {
    }

    public static HostReplaceUtil getInstance() {
        return instance;
    }

    public void addReplacedHostPair(String str, String str2) {
        if (this.hostMap == null) {
            this.hostMap = new HashMap();
        }
        this.hostMap.put(str, str2);
    }

    public void clearReplacedHostPair(String str) {
        Map<String, String> map = this.hostMap;
        if (map != null) {
            map.remove(str);
        }
    }

    public void clear() {
        Map<String, String> map = this.hostMap;
        if (map != null) {
            map.clear();
        }
    }

    public String getReplacedHost(String str) {
        Map<String, String> map = this.hostMap;
        if (map != null && map.size() > 0 && this.hostMap.containsKey(str)) {
            String str2 = this.hostMap.get(str);
            if (!TextUtils.isEmpty(str2)) {
                return str2;
            }
        }
        return str;
    }
}
