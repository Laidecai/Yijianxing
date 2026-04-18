package com.tds.common.websocket.handshake;

/* JADX INFO: loaded from: classes.dex */
public interface ServerHandshakeBuilder extends HandshakeBuilder, ServerHandshake {
    void setHttpStatus(short s);

    void setHttpStatusMessage(String str);
}
