package com.tds.common.websocket.conn;

import com.tds.common.websocket.drafts.Draft;
import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.framing.Framedata;
import com.tds.common.websocket.framing.PingFrame;
import com.tds.common.websocket.framing.PongFrame;
import com.tds.common.websocket.handshake.ClientHandshake;
import com.tds.common.websocket.handshake.HandshakeImpl1Server;
import com.tds.common.websocket.handshake.ServerHandshake;
import com.tds.common.websocket.handshake.ServerHandshakeBuilder;

/* JADX INFO: loaded from: classes.dex */
public abstract class WebSocketAdapter implements WebSocketListener {
    private PingFrame pingFrame;

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public void onWebsocketHandshakeReceivedAsClient(WebSocket webSocket, ClientHandshake clientHandshake, ServerHandshake serverHandshake) throws InvalidDataException {
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public void onWebsocketHandshakeSentAsClient(WebSocket webSocket, ClientHandshake clientHandshake) throws InvalidDataException {
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public void onWebsocketPong(WebSocket webSocket, Framedata framedata) {
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket webSocket, Draft draft, ClientHandshake clientHandshake) throws InvalidDataException {
        return new HandshakeImpl1Server();
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public void onWebsocketPing(WebSocket webSocket, Framedata framedata) {
        webSocket.sendFrame(new PongFrame((PingFrame) framedata));
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public PingFrame onPreparePing(WebSocket webSocket) {
        if (this.pingFrame == null) {
            this.pingFrame = new PingFrame();
        }
        return this.pingFrame;
    }
}
