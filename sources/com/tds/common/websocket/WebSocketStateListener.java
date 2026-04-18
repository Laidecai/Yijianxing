package com.tds.common.websocket;

/* JADX INFO: loaded from: classes.dex */
public interface WebSocketStateListener {
    void onClose();

    void onError(String str);

    void onOpen();
}
