package com.unity3d.splash.services.ads.webplayer;

import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WebPlayerSettingsCache {
    private static WebPlayerSettingsCache instance;
    private HashMap _webSettings = new HashMap();
    private HashMap _webPlayerSettings = new HashMap();
    private HashMap _webPlayerEventSettings = new HashMap();

    public static WebPlayerSettingsCache getInstance() {
        if (instance == null) {
            instance = new WebPlayerSettingsCache();
        }
        return instance;
    }

    public synchronized void addWebPlayerEventSettings(String str, JSONObject jSONObject) {
        this._webPlayerEventSettings.put(str, jSONObject);
    }

    public synchronized void addWebPlayerSettings(String str, JSONObject jSONObject) {
        this._webPlayerSettings.put(str, jSONObject);
    }

    public synchronized void addWebSettings(String str, JSONObject jSONObject) {
        this._webSettings.put(str, jSONObject);
    }

    public synchronized JSONObject getWebPlayerEventSettings(String str) {
        if (this._webPlayerEventSettings.containsKey(str)) {
            return (JSONObject) this._webPlayerEventSettings.get(str);
        }
        return new JSONObject();
    }

    public synchronized JSONObject getWebPlayerSettings(String str) {
        if (this._webPlayerSettings.containsKey(str)) {
            return (JSONObject) this._webPlayerSettings.get(str);
        }
        return new JSONObject();
    }

    public synchronized JSONObject getWebSettings(String str) {
        if (this._webSettings.containsKey(str)) {
            return (JSONObject) this._webSettings.get(str);
        }
        return new JSONObject();
    }

    public synchronized void removeWebPlayerEventSettings(String str) {
        if (this._webPlayerEventSettings.containsKey(str)) {
            this._webPlayerEventSettings.remove(str);
        }
    }

    public synchronized void removeWebPlayerSettings(String str) {
        if (this._webPlayerSettings.containsKey(str)) {
            this._webPlayerSettings.remove(str);
        }
    }

    public synchronized void removeWebSettings(String str) {
        if (this._webSettings.containsKey(str)) {
            this._webSettings.remove(str);
        }
    }
}
