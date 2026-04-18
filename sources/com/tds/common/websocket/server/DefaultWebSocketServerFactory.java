package com.tds.common.websocket.server;

import com.tds.common.websocket.conn.WebSocket;
import com.tds.common.websocket.conn.WebSocketAdapter;
import com.tds.common.websocket.conn.WebSocketImpl;
import com.tds.common.websocket.conn.WebSocketServerFactory;
import com.tds.common.websocket.drafts.Draft;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DefaultWebSocketServerFactory implements WebSocketServerFactory {
    @Override // com.tds.common.websocket.conn.WebSocketServerFactory
    public void close() {
    }

    @Override // com.tds.common.websocket.conn.WebSocketServerFactory
    public SocketChannel wrapChannel(SocketChannel socketChannel, SelectionKey selectionKey) {
        return socketChannel;
    }

    @Override // com.tds.common.websocket.conn.WebSocketFactory
    public /* bridge */ /* synthetic */ WebSocket createWebSocket(WebSocketAdapter webSocketAdapter, List list) {
        return createWebSocket(webSocketAdapter, (List<Draft>) list);
    }

    @Override // com.tds.common.websocket.conn.WebSocketFactory
    public WebSocketImpl createWebSocket(WebSocketAdapter webSocketAdapter, Draft draft) {
        return new WebSocketImpl(webSocketAdapter, draft);
    }

    @Override // com.tds.common.websocket.conn.WebSocketServerFactory, com.tds.common.websocket.conn.WebSocketFactory
    public WebSocketImpl createWebSocket(WebSocketAdapter webSocketAdapter, List<Draft> list) {
        return new WebSocketImpl(webSocketAdapter, list);
    }
}
