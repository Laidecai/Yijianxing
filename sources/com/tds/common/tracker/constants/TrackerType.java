package com.tds.common.tracker.constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TrackerType {
    public static final int TRACKER_FOR_FRIENDS = 1;
    public static final int TRACKER_FOR_NETWORK_MONITOR = 2;
    public static final int TRACKER_FOR_NETWORK_TAPSDK_MONITOR = 3;
    public static final int TRACKER_FOR_TAPSDK = 0;
    public static final int TRACKER_FOR_TAP_CONNECT = 4;
    public static final int TRACKER_FOR_TAP_UPDATE = 5;
    public static final String TRACKER_NAME_FOR_FRIENDS = "tracker_for_friends";
    public static final String TRACKER_NAME_FOR_NETWORK_MONITOR = "tracker_for_network";
    public static final String TRACKER_NAME_FOR_NETWORK_TAPSDK_MONITOR = "tracker_for_tapsdk_network";
    public static final String TRACKER_NAME_FOR_TAPSDK = "tracker_for_tapsdk";
    public static final String TRACKER_NAME_FOR_TAP_CONNECT = "tracker_for_tap_connect";
    public static final String TRACKER_NAME_FOR_TAP_UPDATE = "tracker_for_tap_update";
    private static final Map<Integer, String> trackerTypesMap;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    static {
        HashMap map = new HashMap();
        trackerTypesMap = map;
        map.put(0, TRACKER_NAME_FOR_TAPSDK);
        map.put(1, TRACKER_NAME_FOR_FRIENDS);
        map.put(2, TRACKER_NAME_FOR_NETWORK_MONITOR);
        map.put(3, TRACKER_NAME_FOR_NETWORK_TAPSDK_MONITOR);
        map.put(4, TRACKER_NAME_FOR_TAP_CONNECT);
        map.put(5, TRACKER_NAME_FOR_TAP_UPDATE);
    }

    public static String getTrackerName(int i) {
        return trackerTypesMap.get(Integer.valueOf(i));
    }
}
