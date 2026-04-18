package com.taptap.sdk.net;

/* JADX INFO: loaded from: classes.dex */
class TimeVerifier {
    private static long DELTA;

    TimeVerifier() {
    }

    static void setDelta(long j) {
        DELTA = j;
    }

    static final long getCurrentTimeMillions() {
        return System.currentTimeMillis() + DELTA;
    }
}
