package com.tds.common.net;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PlatformXUA {
    private String engineTdsId;
    Map<String, String> xuaMap;

    private static class Holder {
        private static final PlatformXUA INSTANCE = new PlatformXUA();

        private Holder() {
        }
    }

    private PlatformXUA() {
        this.engineTdsId = "";
    }

    public static PlatformXUA getInstance() {
        return Holder.INSTANCE;
    }

    public Map<String, String> getXuaMap() {
        return this.xuaMap;
    }

    public void setXuaMap(Map<String, String> map) {
        this.xuaMap = map;
    }

    public void setEngineTdsId(String str) {
        this.engineTdsId = str;
    }

    public String getEngineTdsId() {
        return this.engineTdsId;
    }

    public String getTrackUA() {
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = this.xuaMap;
        if (map != null && map.containsKey("Engine-Platform") && this.xuaMap.containsKey("Engine-Version")) {
            sb.append("TapSDK-");
            sb.append(this.xuaMap.get("Engine-Platform"));
            sb.append("/");
            sb.append(this.xuaMap.get("Engine-Version"));
            sb.append(" ");
        }
        sb.append("TapSDK-Android/");
        sb.append("3.29.0");
        return sb.toString();
    }
}
