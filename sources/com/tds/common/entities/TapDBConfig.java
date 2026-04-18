package com.tds.common.entities;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapDBConfig {
    private JSONObject deviceLoginProperties;
    private String channel = "";
    private String gameVersion = "";
    private boolean enable = true;

    public void setChannel(String str) {
        this.channel = str;
    }

    public void setGameVersion(String str) {
        this.gameVersion = str;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getGameVersion() {
        return this.gameVersion;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public JSONObject getDeviceLoginProperties() {
        return this.deviceLoginProperties;
    }

    public void setDeviceLoginProperties(JSONObject jSONObject) {
        this.deviceLoginProperties = jSONObject;
    }

    public void append(TapDBConfig tapDBConfig) {
        if (tapDBConfig == null) {
            return;
        }
        boolean z = true;
        boolean z2 = false;
        if (!TextUtils.isEmpty(tapDBConfig.channel)) {
            this.channel = tapDBConfig.channel;
            z = false;
        }
        if (!TextUtils.isEmpty(tapDBConfig.gameVersion)) {
            this.gameVersion = tapDBConfig.gameVersion;
            z = false;
        }
        JSONObject jSONObject = tapDBConfig.deviceLoginProperties;
        if (jSONObject != null) {
            this.deviceLoginProperties = jSONObject;
        } else {
            z2 = z;
        }
        if (z2) {
            return;
        }
        this.enable = tapDBConfig.enable;
    }

    public String toString() {
        return "TapDBConfig{channel='" + this.channel + "', gameVersion='" + this.gameVersion + "', enable=" + this.enable + ", deviceProperties =" + this.deviceLoginProperties + '}';
    }
}
