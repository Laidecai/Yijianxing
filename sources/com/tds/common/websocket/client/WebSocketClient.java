package com.tds.common.websocket.client;

import android.os.Build;
import com.tds.common.websocket.conn.AbstractWebSocket;
import com.tds.common.websocket.conn.WebSocket;
import com.tds.common.websocket.conn.WebSocketImpl;
import com.tds.common.websocket.drafts.Draft;
import com.tds.common.websocket.drafts.Draft_6455;
import com.tds.common.websocket.enums.Opcode;
import com.tds.common.websocket.enums.ReadyState;
import com.tds.common.websocket.exceptions.InvalidHandshakeException;
import com.tds.common.websocket.framing.Framedata;
import com.tds.common.websocket.handshake.HandshakeImpl1Client;
import com.tds.common.websocket.handshake.Handshakedata;
import com.tds.common.websocket.handshake.ServerHandshake;
import com.tds.common.websocket.protocols.IProtocol;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes.dex */
public abstract class WebSocketClient extends AbstractWebSocket implements Runnable, WebSocket {
    private CountDownLatch closeLatch;
    private CountDownLatch connectLatch;
    private Thread connectReadThread;
    private int connectTimeout;
    private DnsResolver dnsResolver;
    private Draft draft;
    private WebSocketImpl engine;
    private Map<String, String> headers;
    private OutputStream ostream;
    private Proxy proxy;
    private Socket socket;
    private SocketFactory socketFactory;
    protected URI uri;
    private Thread writeThread;

    public abstract void onClose(int i, String str, boolean z);

    public void onCloseInitiated(int i, String str) {
    }

    public void onClosing(int i, String str, boolean z) {
    }

    public abstract void onError(Exception exc);

    public abstract void onMessage(String str);

    public void onMessage(ByteBuffer byteBuffer) {
    }

    public abstract void onOpen(ServerHandshake serverHandshake);

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public final void onWriteDemand(WebSocket webSocket) {
    }

    public WebSocketClient(URI uri) {
        this(uri, new Draft_6455());
    }

    public WebSocketClient(URI uri, Draft draft) {
        this(uri, draft, null, 0);
    }

    public WebSocketClient(URI uri, Map<String, String> map) {
        this(uri, new Draft_6455(), map);
    }

    public WebSocketClient(URI uri, Draft draft, Map<String, String> map) {
        this(uri, draft, map, 0);
    }

    public WebSocketClient(URI uri, Draft draft, Map<String, String> map, int i) {
        this.uri = null;
        this.engine = null;
        this.socket = null;
        this.socketFactory = null;
        this.proxy = Proxy.NO_PROXY;
        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        this.connectTimeout = 0;
        this.dnsResolver = null;
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        if (draft == null) {
            throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
        }
        this.uri = uri;
        this.draft = draft;
        this.dnsResolver = new DnsResolver() { // from class: com.tds.common.websocket.client.WebSocketClient.1
            @Override // com.tds.common.websocket.client.DnsResolver
            public InetAddress resolve(URI uri2) throws UnknownHostException {
                return InetAddress.getByName(uri2.getHost());
            }
        };
        if (map != null) {
            TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            this.headers = treeMap;
            treeMap.putAll(map);
        }
        this.connectTimeout = i;
        setTcpNoDelay(false);
        setReuseAddr(false);
        this.engine = new WebSocketImpl(this, draft);
    }

    public URI getURI() {
        return this.uri;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public Draft getDraft() {
        return this.draft;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void addHeader(String str, String str2) {
        if (this.headers == null) {
            this.headers = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        }
        this.headers.put(str, str2);
    }

    public String removeHeader(String str) {
        Map<String, String> map = this.headers;
        if (map == null) {
            return null;
        }
        return map.remove(str);
    }

    public void clearHeaders() {
        this.headers = null;
    }

    public void setDnsResolver(DnsResolver dnsResolver) {
        this.dnsResolver = dnsResolver;
    }

    public void reconnect() {
        reset();
        connect();
    }

    public boolean reconnectBlocking() throws InterruptedException {
        reset();
        return connectBlocking();
    }

    private void reset() {
        Thread threadCurrentThread = Thread.currentThread();
        if (threadCurrentThread == this.writeThread || threadCurrentThread == this.connectReadThread) {
            throw new IllegalStateException("You cannot initialize a reconnect out of the websocket thread. Use reconnect in another thread to ensure a successful cleanup.");
        }
        try {
            closeBlocking();
            Thread thread = this.writeThread;
            if (thread != null) {
                thread.interrupt();
                this.writeThread = null;
            }
            Thread thread2 = this.connectReadThread;
            if (thread2 != null) {
                thread2.interrupt();
                this.connectReadThread = null;
            }
            this.draft.reset();
            Socket socket = this.socket;
            if (socket != null) {
                socket.close();
                this.socket = null;
            }
            this.connectLatch = new CountDownLatch(1);
            this.closeLatch = new CountDownLatch(1);
            this.engine = new WebSocketImpl(this, this.draft);
        } catch (Exception e) {
            onError(e);
            this.engine.closeConnection(1006, e.getMessage());
        }
    }

    public void connect() {
        if (this.connectReadThread != null) {
            throw new IllegalStateException("WebSocketClient objects are not reuseable");
        }
        Thread thread = new Thread(this);
        this.connectReadThread = thread;
        thread.setName("WebSocketConnectReadThread-" + this.connectReadThread.getId());
        this.connectReadThread.start();
    }

    public boolean connectBlocking() throws InterruptedException {
        connect();
        this.connectLatch.await();
        return this.engine.isOpen();
    }

    public boolean connectBlocking(long j, TimeUnit timeUnit) throws InterruptedException {
        connect();
        return this.connectLatch.await(j, timeUnit) && this.engine.isOpen();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void close() {
        if (this.writeThread != null) {
            this.engine.close(1000);
        }
    }

    public void closeBlocking() throws InterruptedException {
        close();
        this.closeLatch.await();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void send(String str) {
        this.engine.send(str);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void send(byte[] bArr) {
        this.engine.send(bArr);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public <T> T getAttachment() {
        return (T) this.engine.getAttachment();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public <T> void setAttachment(T t) {
        this.engine.setAttachment(t);
    }

    @Override // com.tds.common.websocket.conn.AbstractWebSocket
    protected Collection<WebSocket> getConnections() {
        return Collections.singletonList(this.engine);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendPing() {
        this.engine.sendPing();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004e A[Catch: InternalError -> 0x0116, Exception -> 0x0145, TryCatch #3 {Exception -> 0x0145, InternalError -> 0x0116, blocks: (B:3:0x0001, B:5:0x0009, B:16:0x0034, B:18:0x004e, B:21:0x0069, B:23:0x0077, B:25:0x007b, B:27:0x007f, B:29:0x008f, B:28:0x0082, B:30:0x00a1, B:32:0x00a7, B:33:0x00b3, B:7:0x0014, B:9:0x0018, B:10:0x001f, B:12:0x0023, B:13:0x002d, B:50:0x0110, B:51:0x0115), top: B:65:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0082 A[Catch: InternalError -> 0x0116, Exception -> 0x0145, TryCatch #3 {Exception -> 0x0145, InternalError -> 0x0116, blocks: (B:3:0x0001, B:5:0x0009, B:16:0x0034, B:18:0x004e, B:21:0x0069, B:23:0x0077, B:25:0x007b, B:27:0x007f, B:29:0x008f, B:28:0x0082, B:30:0x00a1, B:32:0x00a7, B:33:0x00b3, B:7:0x0014, B:9:0x0018, B:10:0x001f, B:12:0x0023, B:13:0x002d, B:50:0x0110, B:51:0x0115), top: B:65:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a7 A[Catch: InternalError -> 0x0116, Exception -> 0x0145, TryCatch #3 {Exception -> 0x0145, InternalError -> 0x0116, blocks: (B:3:0x0001, B:5:0x0009, B:16:0x0034, B:18:0x004e, B:21:0x0069, B:23:0x0077, B:25:0x007b, B:27:0x007f, B:29:0x008f, B:28:0x0082, B:30:0x00a1, B:32:0x00a7, B:33:0x00b3, B:7:0x0014, B:9:0x0018, B:10:0x001f, B:12:0x0023, B:13:0x002d, B:50:0x0110, B:51:0x0115), top: B:65:0x0001 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.websocket.client.WebSocketClient.run():void");
    }

    protected void onSetSSLParameters(SSLParameters sSLParameters) {
        if (Build.VERSION.SDK_INT >= 24) {
            sSLParameters.setEndpointIdentificationAlgorithm("HTTPS");
        }
    }

    private int getPort() {
        int port = this.uri.getPort();
        String scheme = this.uri.getScheme();
        if ("wss".equals(scheme)) {
            return port == -1 ? WebSocketImpl.DEFAULT_WSS_PORT : port;
        }
        if ("ws".equals(scheme)) {
            if (port == -1) {
                return 80;
            }
            return port;
        }
        throw new IllegalArgumentException("unknown scheme: " + scheme);
    }

    private void sendHandshake() throws InvalidHandshakeException {
        String rawPath = this.uri.getRawPath();
        String rawQuery = this.uri.getRawQuery();
        if (rawPath == null || rawPath.length() == 0) {
            rawPath = "/";
        }
        if (rawQuery != null) {
            rawPath = rawPath + '?' + rawQuery;
        }
        int port = getPort();
        StringBuilder sb = new StringBuilder();
        sb.append(this.uri.getHost());
        sb.append((port == 80 || port == 443) ? "" : ":" + port);
        String string = sb.toString();
        HandshakeImpl1Client handshakeImpl1Client = new HandshakeImpl1Client();
        handshakeImpl1Client.setResourceDescriptor(rawPath);
        handshakeImpl1Client.put("Host", string);
        Map<String, String> map = this.headers;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                handshakeImpl1Client.put(entry.getKey(), entry.getValue());
            }
        }
        this.engine.startHandshake(handshakeImpl1Client);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public ReadyState getReadyState() {
        return this.engine.getReadyState();
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public final void onWebsocketMessage(WebSocket webSocket, String str) {
        onMessage(str);
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public final void onWebsocketMessage(WebSocket webSocket, ByteBuffer byteBuffer) {
        onMessage(byteBuffer);
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public final void onWebsocketOpen(WebSocket webSocket, Handshakedata handshakedata) {
        startConnectionLostTimer();
        onOpen((ServerHandshake) handshakedata);
        this.connectLatch.countDown();
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public final void onWebsocketClose(WebSocket webSocket, int i, String str, boolean z) {
        stopConnectionLostTimer();
        Thread thread = this.writeThread;
        if (thread != null) {
            thread.interrupt();
        }
        onClose(i, str, z);
        this.connectLatch.countDown();
        this.closeLatch.countDown();
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public final void onWebsocketError(WebSocket webSocket, Exception exc) {
        onError(exc);
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public void onWebsocketCloseInitiated(WebSocket webSocket, int i, String str) {
        onCloseInitiated(i, str);
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public void onWebsocketClosing(WebSocket webSocket, int i, String str, boolean z) {
        onClosing(i, str, z);
    }

    public WebSocket getConnection() {
        return this.engine;
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public InetSocketAddress getLocalSocketAddress(WebSocket webSocket) {
        Socket socket = this.socket;
        if (socket != null) {
            return (InetSocketAddress) socket.getLocalSocketAddress();
        }
        return null;
    }

    @Override // com.tds.common.websocket.conn.WebSocketListener
    public InetSocketAddress getRemoteSocketAddress(WebSocket webSocket) {
        Socket socket = this.socket;
        if (socket != null) {
            return (InetSocketAddress) socket.getRemoteSocketAddress();
        }
        return null;
    }

    private class WebsocketWriteThread implements Runnable {
        private final WebSocketClient webSocketClient;

        WebsocketWriteThread(WebSocketClient webSocketClient) {
            this.webSocketClient = webSocketClient;
        }

        @Override // java.lang.Runnable
        public void run() {
            Thread.currentThread().setName("WebSocketWriteThread-" + Thread.currentThread().getId());
            try {
                try {
                    runWriteData();
                } catch (IOException e) {
                    WebSocketClient.this.handleIOException(e);
                }
            } finally {
                closeSocket();
                WebSocketClient.this.writeThread = null;
            }
        }

        private void runWriteData() throws IOException {
            while (!Thread.interrupted()) {
                try {
                    ByteBuffer byteBufferTake = WebSocketClient.this.engine.outQueue.take();
                    WebSocketClient.this.ostream.write(byteBufferTake.array(), 0, byteBufferTake.limit());
                    WebSocketClient.this.ostream.flush();
                } catch (InterruptedException unused) {
                    for (ByteBuffer byteBuffer : WebSocketClient.this.engine.outQueue) {
                        WebSocketClient.this.ostream.write(byteBuffer.array(), 0, byteBuffer.limit());
                        WebSocketClient.this.ostream.flush();
                    }
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        private void closeSocket() {
            try {
                if (WebSocketClient.this.socket != null) {
                    WebSocketClient.this.socket.close();
                }
            } catch (IOException e) {
                WebSocketClient.this.onWebsocketError(this.webSocketClient, e);
            }
        }
    }

    public void setProxy(Proxy proxy) {
        if (proxy == null) {
            throw new IllegalArgumentException();
        }
        this.proxy = proxy;
    }

    @Deprecated
    public void setSocket(Socket socket) {
        if (this.socket != null) {
            throw new IllegalStateException("socket has already been set");
        }
        this.socket = socket;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendFragmentedFrame(Opcode opcode, ByteBuffer byteBuffer, boolean z) {
        this.engine.sendFragmentedFrame(opcode, byteBuffer, z);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isOpen() {
        return this.engine.isOpen();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isFlushAndClose() {
        return this.engine.isFlushAndClose();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isClosed() {
        return this.engine.isClosed();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isClosing() {
        return this.engine.isClosing();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean hasBufferedData() {
        return this.engine.hasBufferedData();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void close(int i) {
        this.engine.close(i);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void close(int i, String str) {
        this.engine.close(i, str);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void closeConnection(int i, String str) {
        this.engine.closeConnection(i, str);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void send(ByteBuffer byteBuffer) {
        this.engine.send(byteBuffer);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendFrame(Framedata framedata) {
        this.engine.sendFrame(framedata);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendFrame(Collection<Framedata> collection) {
        this.engine.sendFrame(collection);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public InetSocketAddress getLocalSocketAddress() {
        return this.engine.getLocalSocketAddress();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public InetSocketAddress getRemoteSocketAddress() {
        return this.engine.getRemoteSocketAddress();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public String getResourceDescriptor() {
        return this.uri.getPath();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean hasSSLSupport() {
        return this.engine.hasSSLSupport();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public SSLSession getSSLSession() {
        return this.engine.getSSLSession();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public IProtocol getProtocol() {
        return this.engine.getProtocol();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleIOException(IOException iOException) {
        if (iOException instanceof SSLException) {
            onError(iOException);
        }
        this.engine.eot();
    }
}
