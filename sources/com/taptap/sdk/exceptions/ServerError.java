package com.taptap.sdk.exceptions;

/* JADX INFO: loaded from: classes.dex */
public class ServerError extends Exception {
    public int serverCode;

    public ServerError(String str, int i) {
        super(str);
        this.serverCode = i;
    }
}
