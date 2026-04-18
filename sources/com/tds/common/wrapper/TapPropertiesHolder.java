package com.tds.common.wrapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public enum TapPropertiesHolder {
    INSTANCE;

    public Map<String, TapPropertiesProxy> mProxies = new ConcurrentHashMap();

    TapPropertiesHolder() {
    }

    public void registerProperties(String str, TapPropertiesProxy tapPropertiesProxy) {
        if (tapPropertiesProxy == null) {
            return;
        }
        this.mProxies.put(str, tapPropertiesProxy);
    }

    public String getProperty(String str) {
        TapPropertiesProxy tapPropertiesProxy;
        if (this.mProxies.containsKey(str) && (tapPropertiesProxy = this.mProxies.get(str)) != null) {
            return tapPropertiesProxy.getProperties();
        }
        return null;
    }
}
