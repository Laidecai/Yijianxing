package com.tencent.mm.opensdk.diffdev.a;

import com.tds.common.log.TdsBaseException;

/* JADX INFO: loaded from: classes.dex */
public enum d {
    UUID_EXPIRED(402),
    UUID_CANCELED(403),
    UUID_SCANED(404),
    UUID_CONFIRM(405),
    UUID_KEEP_CONNECT(408),
    UUID_ERROR(TdsBaseException.SERVER_ERROR);

    private int a;

    d(int i) {
        this.a = i;
    }

    public int a() {
        return this.a;
    }

    @Override // java.lang.Enum
    public String toString() {
        return "UUIDStatusCode:" + this.a;
    }
}
