package com.tds.common.websocket.conn;

import com.tds.common.websocket.drafts.Draft;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface WebSocketFactory {
    WebSocket createWebSocket(WebSocketAdapter webSocketAdapter, Draft draft);

    WebSocket createWebSocket(WebSocketAdapter webSocketAdapter, List<Draft> list);
}
