package com.tds.common.websocket;

/* JADX INFO: loaded from: classes.dex */
public interface WebSocketEventListener {
    void onClose(int i, String str, boolean z);

    void onError(Exception exc);

    void onMessage(String str);

    void onOpen();
}
