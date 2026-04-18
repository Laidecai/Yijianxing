package com.tds.common.bridge;

import com.tds.common.bridge.annotation.BridgeService;
import com.tds.common.bridge.exception.EngineBridgeException;
import com.tds.common.bridge.exception.EngineBridgeExceptionStatus;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public enum BridgeHolder {
    INSTANCE;

    private Map<Class<? extends IBridgeService>, IBridgeService> serviceMap = new ConcurrentHashMap();

    BridgeHolder() {
    }

    public void register(Class<? extends IBridgeService> cls, IBridgeService iBridgeService) {
        this.serviceMap.put(cls, iBridgeService);
    }

    public Map.Entry<Class<? extends IBridgeService>, IBridgeService> getBridgeService(String str) {
        for (Map.Entry<Class<? extends IBridgeService>, IBridgeService> entry : this.serviceMap.entrySet()) {
            BridgeService bridgeService = (BridgeService) entry.getKey().getAnnotation(BridgeService.class);
            if (bridgeService != null && str.equals(bridgeService.value())) {
                return entry;
            }
        }
        throw new EngineBridgeException(EngineBridgeExceptionStatus.COMMAND_SERVICE_ERROR.getMessage());
    }
}
