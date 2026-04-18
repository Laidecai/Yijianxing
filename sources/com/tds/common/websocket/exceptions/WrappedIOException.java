package com.tds.common.websocket.exceptions;

import com.tds.common.websocket.conn.WebSocket;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class WrappedIOException extends Exception {
    private final WebSocket connection;
    private final IOException ioException;

    public WrappedIOException(WebSocket webSocket, IOException iOException) {
        this.connection = webSocket;
        this.ioException = iOException;
    }

    public WebSocket getConnection() {
        return this.connection;
    }

    public IOException getIOException() {
        return this.ioException;
    }
}
