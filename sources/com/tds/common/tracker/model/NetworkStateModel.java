package com.tds.common.tracker.model;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NetworkStateModel implements BaseTrackModel {
    public static final String PARAM_CODE = "code";
    public static final String PARAM_DELAY = "delay";
    public static final String PARAM_HOST = "host";
    public static final String PARAM_SESSION_ID = "session_id";
    private int code = -1;
    private long delay = -1;
    private String host;
    private String sessionId;

    public NetworkStateModel withSessionId(String str) {
        this.sessionId = str;
        return this;
    }

    public NetworkStateModel withHost(String str) {
        this.host = str;
        return this;
    }

    public NetworkStateModel withHttpCode(int i) {
        this.code = i;
        return this;
    }

    public NetworkStateModel withDelay(long j) {
        this.delay = j;
        return this;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.sessionId)) {
            map.put(PARAM_SESSION_ID, this.sessionId);
        }
        if (!TextUtils.isEmpty(this.host)) {
            map.put("host", this.host);
        }
        int i = this.code;
        if (i != -1) {
            map.put(PARAM_CODE, String.valueOf(i));
        }
        long j = this.delay;
        if (j != -1) {
            map.put(PARAM_DELAY, String.valueOf(j));
        }
        return map;
    }
}
