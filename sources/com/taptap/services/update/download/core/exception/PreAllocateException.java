package com.taptap.services.update.download.core.exception;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class PreAllocateException extends IOException {
    private final long freeSpace;
    private final long requireSpace;

    public PreAllocateException(long j, long j2) {
        super("There is Free space less than Require space: " + j2 + " < " + j);
        this.requireSpace = j;
        this.freeSpace = j2;
    }

    public long getRequireSpace() {
        return this.requireSpace;
    }

    public long getFreeSpace() {
        return this.freeSpace;
    }
}
