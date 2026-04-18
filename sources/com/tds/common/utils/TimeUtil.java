package com.tds.common.utils;

import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class TimeUtil {
    public static String getUnixTimestampStr() {
        return String.valueOf(getUnixTimestamp());
    }

    public static long getUnixTimestamp() {
        return new Date().getTime() / 1000;
    }
}
