package com.tds.common.websocket.conn;

import com.tds.common.websocket.drafts.Draft;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface WebSocketServerFactory extends WebSocketFactory {
    void close();

    @Override // com.tds.common.websocket.conn.WebSocketFactory
    WebSocketImpl createWebSocket(WebSocketAdapter webSocketAdapter, Draft draft);

    @Override // com.tds.common.websocket.conn.WebSocketFactory
    WebSocketImpl createWebSocket(WebSocketAdapter webSocketAdapter, List<Draft> list);

    ByteChannel wrapChannel(SocketChannel socketChannel, SelectionKey selectionKey) throws IOException;
}
