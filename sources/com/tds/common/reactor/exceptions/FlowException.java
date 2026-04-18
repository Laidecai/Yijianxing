package com.tds.common.reactor.exceptions;

/* JADX INFO: loaded from: classes.dex */
public class FlowException extends RuntimeException {
    private String msg;
    private int resultCode;

    public FlowException(int i) {
        this.resultCode = i;
    }

    public FlowException(int i, String str) {
        this.resultCode = i;
        this.msg = str;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.msg;
    }
}
