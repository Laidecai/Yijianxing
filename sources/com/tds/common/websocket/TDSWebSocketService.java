package com.tds.common.websocket;

import android.text.TextUtils;
import com.tds.common.websocket.WebSocketMessage;
import com.tds.common.websocket.util.LogUtil;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class TDSWebSocketService implements WebSocketEventListener {
    public static final int STATE_CLOSED = 2;
    public static final int STATE_CLOSING = 3;
    public static final int STATE_OPEN = 1;
    private static final int TRY_MAX_TIMES = 60;
    private static volatile TDSWebSocketService instance;
    private String destUrl;
    private volatile WebSocketClient webSocketClient;
    private WebSocketConfig webSocketConfig;
    private static AtomicInteger tryTimes = new AtomicInteger(1);
    private static volatile boolean hasOffLine = false;
    private final int EVENT_OPEN = 1;
    private final int EVENT_CLOSE = 2;
    private final int EVENT_MESSAGE = 3;
    private final int EVENT_ERROR = 4;
    private volatile boolean isRetryInSleep = false;
    private volatile boolean isConnecting = false;
    private HashMap<WebSocketMessage.Type, WeakReference<WebSocketEventListener>> messageListenerMap = new HashMap<>();
    private List<WebSocketStateListener> stateListenerList = new ArrayList();

    private TDSWebSocketService() {
    }

    public static TDSWebSocketService getInstance() {
        if (instance == null) {
            synchronized (TDSWebSocketService.class) {
                if (instance == null) {
                    instance = new TDSWebSocketService();
                }
            }
        }
        return instance;
    }

    public synchronized void setDestUrl(String str, WebSocketConfig webSocketConfig) {
        if (!TextUtils.isEmpty(str) && webSocketConfig != null) {
            if (str.equals(this.destUrl) && webSocketConfig.isEqual(this.webSocketConfig)) {
                return;
            }
            reset();
            this.webSocketConfig = webSocketConfig;
            this.webSocketClient = new WebSocketClient(URI.create(str), webSocketConfig, this);
            hasOffLine = false;
            this.destUrl = str;
            this.webSocketClient.connect();
            this.isConnecting = true;
        }
    }

    public synchronized void checkConnection() {
        if (!TextUtils.isEmpty(this.destUrl) && !this.isConnecting && this.webSocketConfig != null) {
            hasOffLine = false;
            if (this.webSocketClient == null || !this.webSocketClient.isOpen()) {
                clearWebSocketClient();
                this.webSocketClient = new WebSocketClient(URI.create(this.destUrl), this.webSocketConfig, this);
                this.webSocketClient.connect();
                this.isConnecting = true;
            }
        }
    }

    public synchronized void disconnect() {
        hasOffLine = true;
        if (this.webSocketClient != null && this.webSocketClient.isOpen()) {
            this.webSocketClient.close();
        }
        clearWebSocketClient();
    }

    public synchronized void reset() {
        disconnect();
        this.destUrl = null;
    }

    public void registerMessageListener(WebSocketMessage.Type type, WebSocketEventListener webSocketEventListener) {
        this.messageListenerMap.put(type, new WeakReference<>(webSocketEventListener));
    }

    public void unregisterMessageListener(WebSocketMessage.Type type) {
        this.messageListenerMap.remove(type);
    }

    public void addConnectionStateListener(WebSocketStateListener webSocketStateListener) {
        if (webSocketStateListener == null) {
            return;
        }
        if (getConnectionState() == 1) {
            webSocketStateListener.onOpen();
            return;
        }
        if (getConnectionState() == 2 && !this.isRetryInSleep && !this.isConnecting && !TextUtils.isEmpty(this.destUrl)) {
            webSocketStateListener.onClose();
        } else {
            this.stateListenerList.add(webSocketStateListener);
        }
    }

    @Override // com.tds.common.websocket.WebSocketEventListener
    public void onOpen() {
        LogUtil.logD("receive open = ");
        this.isConnecting = false;
        tryTimes.set(1);
        dispatchEvent(1, new Object[0]);
    }

    @Override // com.tds.common.websocket.WebSocketEventListener
    public void onClose(int i, String str, boolean z) {
        this.isConnecting = false;
        clearWebSocketClient();
        dispatchEvent(2, Integer.valueOf(i), str, Boolean.valueOf(z));
    }

    public synchronized void retryConnectWithDelay() {
        if (TextUtils.isEmpty(this.destUrl)) {
            return;
        }
        if (this.webSocketClient == null || !this.webSocketClient.isOpen()) {
            if (!hasOffLine && !this.isRetryInSleep && !this.isConnecting) {
                LogUtil.logE(" websocket reconnect after close wait " + tryTimes + "s in thread " + Thread.currentThread().getName());
                Thread thread = new Thread(new Runnable() { // from class: com.tds.common.websocket.TDSWebSocketService.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            TDSWebSocketService.tryTimes.incrementAndGet();
                            TDSWebSocketService.this.isRetryInSleep = true;
                            double dRandom = Math.random() + 0.1d;
                            int i = TDSWebSocketService.tryTimes.get();
                            int iIntValue = TDSWebSocketService.TRY_MAX_TIMES;
                            if (i <= TDSWebSocketService.TRY_MAX_TIMES) {
                                iIntValue = TDSWebSocketService.tryTimes.intValue();
                            }
                            Thread.sleep((int) (dRandom * ((double) iIntValue) * 5000.0d));
                            if (!TDSWebSocketService.hasOffLine && (TDSWebSocketService.this.webSocketClient == null || (!TDSWebSocketService.this.webSocketClient.isOpen() && !TDSWebSocketService.this.isConnecting))) {
                                if (!TextUtils.isEmpty(TDSWebSocketService.this.destUrl) && TDSWebSocketService.this.webSocketConfig != null) {
                                    TDSWebSocketService tDSWebSocketService = TDSWebSocketService.this;
                                    tDSWebSocketService.webSocketClient = new WebSocketClient(URI.create(tDSWebSocketService.destUrl), TDSWebSocketService.this.webSocketConfig, TDSWebSocketService.instance);
                                    TDSWebSocketService.this.webSocketClient.connect();
                                    TDSWebSocketService.this.isConnecting = true;
                                    LogUtil.logD(" websocket reconnect after close");
                                }
                                return;
                            }
                            TDSWebSocketService.this.isRetryInSleep = false;
                        } catch (Throwable th) {
                            TDSWebSocketService.this.isRetryInSleep = false;
                            LogUtil.logE(" websocket reconnect fail error =  " + th.getMessage());
                        }
                    }
                });
                StringBuilder sb = new StringBuilder();
                sb.append("webSocketReTryConnect-");
                sb.append(System.currentTimeMillis() / 1000);
                thread.setName(sb.toString());
                thread.start();
            }
        }
    }

    @Override // com.tds.common.websocket.WebSocketEventListener
    public void onMessage(String str) {
        LogUtil.logD("receive msg = " + str);
        WeakReference<WebSocketEventListener> weakReference = this.messageListenerMap.get(WebSocketMessage.getMessageType(str));
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        weakReference.get().onMessage(str);
    }

    @Override // com.tds.common.websocket.WebSocketEventListener
    public void onError(Exception exc) {
        this.isConnecting = false;
        LogUtil.logE(" webSocketService onError offline = " + hasOffLine);
        if (this.webSocketClient != null && this.webSocketClient.isOpen()) {
            this.webSocketClient.close();
        }
        dispatchEvent(4, exc);
    }

    private void clearWebSocketClient() {
        if (this.webSocketClient != null) {
            this.webSocketClient.removeEventListener();
            try {
                this.webSocketClient.close();
            } catch (Throwable th) {
                LogUtil.logE("clearWebSocketClient error = " + th.getMessage());
            }
            this.webSocketClient = null;
        }
    }

    private void dispatchEvent(int i, Object... objArr) {
        WeakReference<WebSocketEventListener> weakReference;
        WebSocketEventListener webSocketEventListener;
        for (WebSocketMessage.Type type : this.messageListenerMap.keySet()) {
            if (this.messageListenerMap.get(type) != null && (weakReference = this.messageListenerMap.get(type)) != null && (webSocketEventListener = weakReference.get()) != null) {
                if (i == 1) {
                    webSocketEventListener.onOpen();
                } else if (i == 2) {
                    webSocketEventListener.onClose(((Integer) objArr[0]).intValue(), (String) objArr[1], ((Boolean) objArr[2]).booleanValue());
                } else if (i == 4) {
                    webSocketEventListener.onError((Exception) objArr[0]);
                }
            }
        }
        if (i == 1 || i == 4) {
            for (WebSocketStateListener webSocketStateListener : this.stateListenerList) {
                if (webSocketStateListener != null) {
                    if (i == 1) {
                        webSocketStateListener.onOpen();
                    } else {
                        webSocketStateListener.onError("");
                    }
                }
                this.stateListenerList.clear();
            }
        }
    }

    public int getConnectionState() {
        if (this.webSocketClient != null) {
            if (this.webSocketClient.isOpen()) {
                return 1;
            }
            if (!this.webSocketClient.isClosed() && this.webSocketClient.isClosing()) {
                return 3;
            }
        }
        return 2;
    }

    public void sendMessage(String str) {
        if (TextUtils.isEmpty(str) || this.webSocketClient == null || !this.webSocketClient.isOpen()) {
            return;
        }
        this.webSocketClient.send(str);
    }

    public static class WebSocketConfig {
        int connectionTimeout;
        public Map<String, String> heads;

        private WebSocketConfig(int i, Map<String, String> map) {
            this.connectionTimeout = i;
            this.heads = map;
        }

        public boolean isEqual(WebSocketConfig webSocketConfig) {
            if (webSocketConfig != null && webSocketConfig.connectionTimeout == this.connectionTimeout) {
                Map<String, String> map = this.heads;
                Map<String, String> map2 = webSocketConfig.heads;
                if (map == map2) {
                    return true;
                }
                if (map == null || map2 == null || map.size() != webSocketConfig.heads.size()) {
                    return false;
                }
                if (this.heads.size() > 0) {
                    for (String str : this.heads.keySet()) {
                        if (!Objects.equals(this.heads.get(str), webSocketConfig.heads.get(str))) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }

    public static class WebSocketConfigBuilder {
        int connectionTimeout = 45;
        Map<String, String> heads;

        public static WebSocketConfigBuilder getBuilder() {
            return new WebSocketConfigBuilder();
        }

        public WebSocketConfigBuilder connectTimeOut(int i) {
            this.connectionTimeout = i;
            return this;
        }

        public WebSocketConfigBuilder heads(Map<String, String> map) {
            this.heads = map;
            return this;
        }

        public WebSocketConfig build() {
            return new WebSocketConfig(this.connectionTimeout, this.heads);
        }
    }
}
