package com.tds.common.websocket.conn;

import com.tds.common.websocket.util.LogUtil;
import com.tds.common.websocket.util.NamedThreadFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractWebSocket extends WebSocketAdapter {
    private ScheduledFuture connectionLostCheckerFuture;
    private ScheduledExecutorService connectionLostCheckerService;
    private boolean reuseAddr;
    private boolean tcpNoDelay;
    private long connectionLostTimeout = TimeUnit.SECONDS.toNanos(60);
    private boolean websocketRunning = false;
    private final Object syncConnectionLost = new Object();

    protected abstract Collection<WebSocket> getConnections();

    public int getConnectionLostTimeout() {
        int seconds;
        synchronized (this.syncConnectionLost) {
            seconds = (int) TimeUnit.NANOSECONDS.toSeconds(this.connectionLostTimeout);
        }
        return seconds;
    }

    public void setConnectionLostTimeout(int i) {
        synchronized (this.syncConnectionLost) {
            long nanos = TimeUnit.SECONDS.toNanos(i);
            this.connectionLostTimeout = nanos;
            if (nanos <= 0) {
                LogUtil.logD("Connection lost timer stopped");
                cancelConnectionLostTimer();
                return;
            }
            if (this.websocketRunning) {
                LogUtil.logD("Connection lost timer restarted");
                try {
                    for (WebSocket webSocket : new ArrayList(getConnections())) {
                        if (webSocket instanceof WebSocketImpl) {
                            ((WebSocketImpl) webSocket).updateLastPong();
                        }
                    }
                } catch (Exception e) {
                    LogUtil.logE("Exception during connection lost restart", e);
                }
                restartConnectionLostTimer();
            }
        }
    }

    protected void stopConnectionLostTimer() {
        synchronized (this.syncConnectionLost) {
            if (this.connectionLostCheckerService != null || this.connectionLostCheckerFuture != null) {
                this.websocketRunning = false;
                LogUtil.logD("Connection lost timer stopped");
                cancelConnectionLostTimer();
            }
        }
    }

    protected void startConnectionLostTimer() {
        synchronized (this.syncConnectionLost) {
            if (this.connectionLostTimeout <= 0) {
                LogUtil.logD("Connection lost timer deactivated");
                return;
            }
            LogUtil.logD("Connection lost timer started");
            this.websocketRunning = true;
            restartConnectionLostTimer();
        }
    }

    private void restartConnectionLostTimer() {
        cancelConnectionLostTimer();
        this.connectionLostCheckerService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("connectionLostChecker"));
        Runnable runnable = new Runnable() { // from class: com.tds.common.websocket.conn.AbstractWebSocket.1
            private ArrayList<WebSocket> connections = new ArrayList<>();

            @Override // java.lang.Runnable
            public void run() {
                this.connections.clear();
                try {
                    this.connections.addAll(AbstractWebSocket.this.getConnections());
                    long jNanoTime = (long) (System.nanoTime() - (AbstractWebSocket.this.connectionLostTimeout * 1.5d));
                    Iterator<WebSocket> it = this.connections.iterator();
                    while (it.hasNext()) {
                        AbstractWebSocket.this.executeConnectionLostDetection(it.next(), jNanoTime);
                    }
                } catch (Exception unused) {
                }
                this.connections.clear();
            }
        };
        ScheduledExecutorService scheduledExecutorService = this.connectionLostCheckerService;
        long j = this.connectionLostTimeout;
        this.connectionLostCheckerFuture = scheduledExecutorService.scheduleAtFixedRate(runnable, j, j, TimeUnit.NANOSECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeConnectionLostDetection(WebSocket webSocket, long j) {
        if (webSocket instanceof WebSocketImpl) {
            WebSocketImpl webSocketImpl = (WebSocketImpl) webSocket;
            if (webSocketImpl.getLastPong() < j) {
                LogUtil.logD("Closing connection due to no pong received: {}", webSocketImpl);
                webSocketImpl.closeConnection(1006, "The connection was closed because the other endpoint did not respond with a pong in time. For more information check: https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection");
            } else if (webSocketImpl.isOpen()) {
                webSocketImpl.sendPing();
            } else {
                LogUtil.logD("Trying to ping a non open connection: {}", webSocketImpl);
            }
        }
    }

    private void cancelConnectionLostTimer() {
        ScheduledExecutorService scheduledExecutorService = this.connectionLostCheckerService;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
            this.connectionLostCheckerService = null;
        }
        ScheduledFuture scheduledFuture = this.connectionLostCheckerFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.connectionLostCheckerFuture = null;
        }
    }

    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }

    public void setTcpNoDelay(boolean z) {
        this.tcpNoDelay = z;
    }

    public boolean isReuseAddr() {
        return this.reuseAddr;
    }

    public void setReuseAddr(boolean z) {
        this.reuseAddr = z;
    }
}
