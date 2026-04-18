package com.tds.common.websocket.handshake;

/* JADX INFO: loaded from: classes.dex */
public class HandshakeImpl1Server extends HandshakedataImpl1 implements ServerHandshakeBuilder {
    private short httpstatus;
    private String httpstatusmessage;

    @Override // com.tds.common.websocket.handshake.ServerHandshake
    public String getHttpStatusMessage() {
        return this.httpstatusmessage;
    }

    @Override // com.tds.common.websocket.handshake.ServerHandshake
    public short getHttpStatus() {
        return this.httpstatus;
    }

    @Override // com.tds.common.websocket.handshake.ServerHandshakeBuilder
    public void setHttpStatusMessage(String str) {
        this.httpstatusmessage = str;
    }

    @Override // com.tds.common.websocket.handshake.ServerHandshakeBuilder
    public void setHttpStatus(short s) {
        this.httpstatus = s;
    }
}
