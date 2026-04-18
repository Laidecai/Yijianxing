package com.tds.common.websocket.handshake;

/* JADX INFO: loaded from: classes.dex */
public class HandshakeImpl1Client extends HandshakedataImpl1 implements ClientHandshakeBuilder {
    private String resourceDescriptor = "*";

    @Override // com.tds.common.websocket.handshake.ClientHandshakeBuilder
    public void setResourceDescriptor(String str) {
        if (str == null) {
            throw new IllegalArgumentException("http resource descriptor must not be null");
        }
        this.resourceDescriptor = str;
    }

    @Override // com.tds.common.websocket.handshake.ClientHandshake
    public String getResourceDescriptor() {
        return this.resourceDescriptor;
    }
}
