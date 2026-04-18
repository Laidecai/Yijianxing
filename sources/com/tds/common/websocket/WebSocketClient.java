package com.tds.common.websocket;

import com.tds.common.websocket.TDSWebSocketService;
import com.tds.common.websocket.handshake.ServerHandshake;
import com.tds.common.websocket.util.LogUtil;
import java.net.URI;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class WebSocketClient extends com.tds.common.websocket.client.WebSocketClient {
    private WebSocketEventListener webClientListener;

    public WebSocketClient(URI uri, TDSWebSocketService.WebSocketConfig webSocketConfig, WebSocketEventListener webSocketEventListener) {
        super(uri);
        this.webClientListener = webSocketEventListener;
        setConnectionLostTimeout(webSocketConfig.connectionTimeout);
        if (webSocketConfig.heads != null) {
            for (String str : webSocketConfig.heads.keySet()) {
                addHeader(str, webSocketConfig.heads.get(str));
            }
        }
    }

    public WebSocketClient(URI uri, Map<String, String> map, WebSocketEventListener webSocketEventListener) {
        super(uri, map);
        this.webClientListener = webSocketEventListener;
    }

    public void removeEventListener() {
        this.webClientListener = null;
    }

    @Override // com.tds.common.websocket.client.WebSocketClient
    public void onOpen(ServerHandshake serverHandshake) {
        LogUtil.logE("---websocket open----");
        WebSocketEventListener webSocketEventListener = this.webClientListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onOpen();
        }
    }

    @Override // com.tds.common.websocket.client.WebSocketClient
    public void onMessage(String str) {
        LogUtil.logE("websocket message = " + str);
        WebSocketEventListener webSocketEventListener = this.webClientListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onMessage(str);
        }
    }

    @Override // com.tds.common.websocket.client.WebSocketClient
    public void onClose(int i, String str, boolean z) {
        LogUtil.logE("--websocket close --- code = " + i + " reason = " + str + " remote = " + z);
        WebSocketEventListener webSocketEventListener = this.webClientListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onClose(i, str, z);
        }
    }

    @Override // com.tds.common.websocket.client.WebSocketClient
    public void onError(Exception exc) {
        LogUtil.logE("websocket error e = " + exc.getMessage());
        WebSocketEventListener webSocketEventListener = this.webClientListener;
        if (webSocketEventListener != null) {
            webSocketEventListener.onError(exc);
        }
    }
}
