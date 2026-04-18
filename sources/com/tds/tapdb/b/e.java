package com.tds.tapdb.b;

/* JADX INFO: loaded from: classes.dex */
public enum e {
    TRACK("track", true),
    DEVICE_INITIALIZE("initialise", false),
    DEVICE_UPDATE("update", false),
    DEVICE_ADD("add", false),
    USER_INITIALIZE("initialise", false),
    USER_UPDATE("update", false),
    USER_ADD("add", false);

    private String a;
    private boolean b;

    e(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    public String a() {
        return this.a;
    }

    public boolean b() {
        return this.b;
    }
}
