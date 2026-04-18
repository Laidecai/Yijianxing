package com.tds.common.net.exception;

/* JADX INFO: loaded from: classes.dex */
public class ServerException extends Exception {
    public final String message;
    public final String responseBody;
    public final int statusCode;

    public ServerException(int i, String str, String str2) {
        super(str2);
        this.statusCode = i;
        this.message = str;
        this.responseBody = str2;
    }

    public boolean isServerError() {
        return this.statusCode >= 500;
    }

    public boolean isClientError() {
        int i = this.statusCode;
        return i < 500 && i >= 400;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "ServerException{statusCode=" + this.statusCode + ", message='" + this.message + "', responseBody='" + this.responseBody + "'}";
    }
}
