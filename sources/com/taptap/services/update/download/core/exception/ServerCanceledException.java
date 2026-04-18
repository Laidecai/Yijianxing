package com.taptap.services.update.download.core.exception;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class ServerCanceledException extends IOException {
    private final int responseCode;

    public ServerCanceledException(int i, long j) {
        super("Response code can't handled on internal " + i + " with current offset " + j);
        this.responseCode = i;
    }

    public int getResponseCode() {
        return this.responseCode;
    }
}
