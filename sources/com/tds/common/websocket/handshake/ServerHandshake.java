package com.tds.common.websocket.handshake;

/* JADX INFO: loaded from: classes.dex */
public interface ServerHandshake extends Handshakedata {
    short getHttpStatus();

    String getHttpStatusMessage();
}
