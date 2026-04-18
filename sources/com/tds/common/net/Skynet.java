package com.tds.common.net;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Skynet {
    final Map<String, TdsApiClient> tdsApiClientCache;

    private static class Holder {
        private static final Skynet INSTANCE = new Skynet();

        private Holder() {
        }
    }

    private Skynet() {
        this.tdsApiClientCache = new HashMap();
    }

    public static Skynet getInstance() {
        return Holder.INSTANCE;
    }

    public void registerTdsClient(String str, TdsApiClient tdsApiClient) {
        this.tdsApiClientCache.put(str, tdsApiClient);
    }

    public TdsApiClient getTdsApiClient(String str) {
        return this.tdsApiClientCache.get(str);
    }
}
