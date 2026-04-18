package com.tds.common.tracker;

import android.text.TextUtils;
import com.tds.common.tracker.TdsTrackerConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
class TdsTracker {
    private static final Map<String, TdsTracker> mTagCache = new ConcurrentHashMap();
    static TdsTrackerHandlerThread sHandlerThread;
    private TdsTrackerConfig tdsTrackerConfig;

    TdsTracker() {
    }

    static {
        TdsTrackerHandlerThread tdsTrackerHandlerThread = new TdsTrackerHandlerThread("TdsTracker_Thread");
        sHandlerThread = tdsTrackerHandlerThread;
        tdsTrackerHandlerThread.start();
    }

    public static TdsTracker getTracker(TdsTrackerConfig tdsTrackerConfig) {
        if (tdsTrackerConfig.topic.isEmpty()) {
            throw new IllegalArgumentException("topic name empty");
        }
        String str = "";
        if (!TextUtils.isEmpty(tdsTrackerConfig.topic)) {
            str = "" + tdsTrackerConfig.topic;
        }
        Map<String, TdsTracker> map = mTagCache;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        TdsTracker tdsTracker = new TdsTracker();
        tdsTracker.tdsTrackerConfig = tdsTrackerConfig;
        map.put(tdsTrackerConfig.topic, tdsTracker);
        return tdsTracker;
    }

    public static TdsTracker get(int i) {
        return getTracker(new TdsTrackerConfig.Builder().withTrackerType(i).build(null));
    }

    public static synchronized void initTdsTracker(TdsTrackerConfig tdsTrackerConfig) {
        getTracker(tdsTrackerConfig);
    }

    public void track(Map<String, String> map) {
        sHandlerThread.write(this.tdsTrackerConfig, map);
    }
}
