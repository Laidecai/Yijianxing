package com.tds.common.websocket.conn;

import com.tds.common.log.TdsBaseException;
import com.tds.common.websocket.drafts.Draft;
import com.tds.common.websocket.drafts.Draft_6455;
import com.tds.common.websocket.enums.CloseHandshakeType;
import com.tds.common.websocket.enums.HandshakeState;
import com.tds.common.websocket.enums.Opcode;
import com.tds.common.websocket.enums.ReadyState;
import com.tds.common.websocket.enums.Role;
import com.tds.common.websocket.exceptions.IncompleteHandshakeException;
import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.exceptions.InvalidHandshakeException;
import com.tds.common.websocket.exceptions.LimitExceededException;
import com.tds.common.websocket.exceptions.WebsocketNotConnectedException;
import com.tds.common.websocket.framing.CloseFrame;
import com.tds.common.websocket.framing.Framedata;
import com.tds.common.websocket.framing.PingFrame;
import com.tds.common.websocket.handshake.ClientHandshake;
import com.tds.common.websocket.handshake.ClientHandshakeBuilder;
import com.tds.common.websocket.handshake.Handshakedata;
import com.tds.common.websocket.handshake.ServerHandshake;
import com.tds.common.websocket.interfaces.ISSLChannel;
import com.tds.common.websocket.protocols.IProtocol;
import com.tds.common.websocket.server.WebSocketServer;
import com.tds.common.websocket.util.Charsetfunctions;
import com.tds.common.websocket.util.LogUtil;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes.dex */
public class WebSocketImpl implements WebSocket {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_PORT = 80;
    public static final int DEFAULT_WSS_PORT = 443;
    public static final int RCVBUF = 16384;
    private Object attachment;
    private ByteChannel channel;
    private Integer closecode;
    private Boolean closedremotely;
    private String closemessage;
    private Draft draft;
    private boolean flushandclosestate;
    private ClientHandshake handshakerequest;
    public final BlockingQueue<ByteBuffer> inQueue;
    private SelectionKey key;
    private List<Draft> knownDrafts;
    private long lastPong;
    public final BlockingQueue<ByteBuffer> outQueue;
    private volatile ReadyState readyState;
    private String resourceDescriptor;
    private Role role;
    private final Object synchronizeWriteObject;
    private ByteBuffer tmpHandshakeBytes;
    private WebSocketServer.WebSocketWorker workerThread;
    private final WebSocketListener wsl;

    public WebSocketImpl(WebSocketListener webSocketListener, List<Draft> list) {
        this(webSocketListener, (Draft) null);
        this.role = Role.SERVER;
        if (list == null || list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            this.knownDrafts = arrayList;
            arrayList.add(new Draft_6455());
            return;
        }
        this.knownDrafts = list;
    }

    public WebSocketImpl(WebSocketListener webSocketListener, Draft draft) {
        this.flushandclosestate = false;
        this.readyState = ReadyState.NOT_YET_CONNECTED;
        this.draft = null;
        this.tmpHandshakeBytes = ByteBuffer.allocate(0);
        this.handshakerequest = null;
        this.closemessage = null;
        this.closecode = null;
        this.closedremotely = null;
        this.resourceDescriptor = null;
        this.lastPong = System.nanoTime();
        this.synchronizeWriteObject = new Object();
        if (webSocketListener == null || (draft == null && this.role == Role.SERVER)) {
            throw new IllegalArgumentException("parameters must not be null");
        }
        this.outQueue = new LinkedBlockingQueue();
        this.inQueue = new LinkedBlockingQueue();
        this.wsl = webSocketListener;
        this.role = Role.CLIENT;
        if (draft != null) {
            this.draft = draft.copyInstance();
        }
    }

    public void decode(ByteBuffer byteBuffer) {
        StringBuilder sb = new StringBuilder();
        sb.append(byteBuffer.remaining());
        sb.append(byteBuffer.remaining() > 1000 ? "too big to display" : new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining()));
        LogUtil.logD("process({}): ({})", sb.toString());
        if (this.readyState != ReadyState.NOT_YET_CONNECTED) {
            if (this.readyState == ReadyState.OPEN) {
                decodeFrames(byteBuffer);
            }
        } else {
            if (!decodeHandshake(byteBuffer) || isClosing() || isClosed()) {
                return;
            }
            if (byteBuffer.hasRemaining()) {
                decodeFrames(byteBuffer);
            } else if (this.tmpHandshakeBytes.hasRemaining()) {
                decodeFrames(this.tmpHandshakeBytes);
            }
        }
    }

    private boolean decodeHandshake(ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2;
        Handshakedata handshakedataTranslateHandshake;
        if (this.tmpHandshakeBytes.capacity() == 0) {
            byteBuffer2 = byteBuffer;
        } else {
            if (this.tmpHandshakeBytes.remaining() < byteBuffer.remaining()) {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.tmpHandshakeBytes.capacity() + byteBuffer.remaining());
                this.tmpHandshakeBytes.flip();
                byteBufferAllocate.put(this.tmpHandshakeBytes);
                this.tmpHandshakeBytes = byteBufferAllocate;
            }
            this.tmpHandshakeBytes.put(byteBuffer);
            this.tmpHandshakeBytes.flip();
            byteBuffer2 = this.tmpHandshakeBytes;
        }
        byteBuffer2.mark();
        try {
            try {
            } catch (InvalidHandshakeException e) {
                LogUtil.logD("Closing due to invalid handshake", e);
                close(e);
            }
        } catch (IncompleteHandshakeException e2) {
            if (this.tmpHandshakeBytes.capacity() == 0) {
                byteBuffer2.reset();
                int preferredSize = e2.getPreferredSize();
                if (preferredSize == 0) {
                    preferredSize = byteBuffer2.capacity() + 16;
                }
                ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(preferredSize);
                this.tmpHandshakeBytes = byteBufferAllocate2;
                byteBufferAllocate2.put(byteBuffer);
            } else {
                ByteBuffer byteBuffer3 = this.tmpHandshakeBytes;
                byteBuffer3.position(byteBuffer3.limit());
                ByteBuffer byteBuffer4 = this.tmpHandshakeBytes;
                byteBuffer4.limit(byteBuffer4.capacity());
            }
        }
        if (this.role == Role.SERVER) {
            Draft draft = this.draft;
            if (draft == null) {
                Iterator<Draft> it = this.knownDrafts.iterator();
                while (it.hasNext()) {
                    Draft draftCopyInstance = it.next().copyInstance();
                    try {
                        draftCopyInstance.setParseMode(this.role);
                        byteBuffer2.reset();
                        handshakedataTranslateHandshake = draftCopyInstance.translateHandshake(byteBuffer2);
                    } catch (InvalidHandshakeException unused) {
                    }
                    if (!(handshakedataTranslateHandshake instanceof ClientHandshake)) {
                        LogUtil.logD("Closing due to wrong handshake");
                        closeConnectionDueToWrongHandshake(new InvalidDataException(1002, "wrong http function"));
                        return false;
                    }
                    ClientHandshake clientHandshake = (ClientHandshake) handshakedataTranslateHandshake;
                    if (draftCopyInstance.acceptHandshakeAsServer(clientHandshake) == HandshakeState.MATCHED) {
                        this.resourceDescriptor = clientHandshake.getResourceDescriptor();
                        try {
                            write(draftCopyInstance.createHandshake(draftCopyInstance.postProcessHandshakeResponseAsServer(clientHandshake, this.wsl.onWebsocketHandshakeReceivedAsServer(this, draftCopyInstance, clientHandshake))));
                            this.draft = draftCopyInstance;
                            open(clientHandshake);
                            return true;
                        } catch (InvalidDataException e3) {
                            LogUtil.logD("Closing due to wrong handshake. Possible handshake rejection", e3);
                            closeConnectionDueToWrongHandshake(e3);
                            return false;
                        } catch (RuntimeException e4) {
                            LogUtil.logE("Closing due to internal server error", e4);
                            this.wsl.onWebsocketError(this, e4);
                            closeConnectionDueToInternalServerError(e4);
                            return false;
                        }
                    }
                }
                if (this.draft == null) {
                    LogUtil.logD("Closing due to protocol error: no draft matches");
                    closeConnectionDueToWrongHandshake(new InvalidDataException(1002, "no draft matches"));
                }
                return false;
            }
            Handshakedata handshakedataTranslateHandshake2 = draft.translateHandshake(byteBuffer2);
            if (!(handshakedataTranslateHandshake2 instanceof ClientHandshake)) {
                LogUtil.logD("Closing due to protocol error: wrong http function");
                flushAndClose(1002, "wrong http function", false);
                return false;
            }
            ClientHandshake clientHandshake2 = (ClientHandshake) handshakedataTranslateHandshake2;
            if (this.draft.acceptHandshakeAsServer(clientHandshake2) == HandshakeState.MATCHED) {
                open(clientHandshake2);
                return true;
            }
            LogUtil.logD("Closing due to protocol error: the handshake did finally not match");
            close(1002, "the handshake did finally not match");
            return false;
        }
        if (this.role == Role.CLIENT) {
            this.draft.setParseMode(this.role);
            Handshakedata handshakedataTranslateHandshake3 = this.draft.translateHandshake(byteBuffer2);
            if (!(handshakedataTranslateHandshake3 instanceof ServerHandshake)) {
                LogUtil.logD("Closing due to protocol error: wrong http function");
                flushAndClose(1002, "wrong http function", false);
                return false;
            }
            ServerHandshake serverHandshake = (ServerHandshake) handshakedataTranslateHandshake3;
            if (this.draft.acceptHandshakeAsClient(this.handshakerequest, serverHandshake) == HandshakeState.MATCHED) {
                try {
                    this.wsl.onWebsocketHandshakeReceivedAsClient(this, this.handshakerequest, serverHandshake);
                    open(serverHandshake);
                    return true;
                } catch (InvalidDataException e5) {
                    LogUtil.logD("Closing due to invalid data exception. Possible handshake rejection", e5);
                    flushAndClose(e5.getCloseCode(), e5.getMessage(), false);
                    return false;
                } catch (RuntimeException e6) {
                    LogUtil.logE("Closing since client was never connected", e6);
                    this.wsl.onWebsocketError(this, e6);
                    flushAndClose(-1, e6.getMessage(), false);
                    return false;
                }
            }
            LogUtil.logD("Closing due to protocol error: draft {} refuses handshake", this.draft);
            close(1002, "draft " + this.draft + " refuses handshake");
        }
        return false;
    }

    private void decodeFrames(ByteBuffer byteBuffer) {
        try {
            for (Framedata framedata : this.draft.translateFrame(byteBuffer)) {
                LogUtil.logD("matched frame: {}", framedata);
                this.draft.processFrame(this, framedata);
            }
        } catch (LimitExceededException e) {
            if (e.getLimit() == Integer.MAX_VALUE) {
                LogUtil.logE("Closing due to invalid size of frame", e);
                this.wsl.onWebsocketError(this, e);
            }
            close(e);
        } catch (InvalidDataException e2) {
            LogUtil.logE("Closing due to invalid data in frame", e2);
            this.wsl.onWebsocketError(this, e2);
            close(e2);
        }
    }

    private void closeConnectionDueToWrongHandshake(InvalidDataException invalidDataException) {
        write(generateHttpResponseDueToError(404));
        flushAndClose(invalidDataException.getCloseCode(), invalidDataException.getMessage(), false);
    }

    private void closeConnectionDueToInternalServerError(RuntimeException runtimeException) {
        write(generateHttpResponseDueToError(TdsBaseException.SERVER_ERROR));
        flushAndClose(-1, runtimeException.getMessage(), false);
    }

    private ByteBuffer generateHttpResponseDueToError(int i) {
        String str = i != 404 ? "500 Internal Server Error" : "404 WebSocket Upgrade Failure";
        return ByteBuffer.wrap(Charsetfunctions.asciiBytes("HTTP/1.1 " + str + "\r\nContent-Type: text/html\r\nServer: TooTallNate Java-WebSocket\r\nContent-Length: " + (str.length() + 48) + "\r\n\r\n<html><head></head><body><h1>" + str + "</h1></body></html>"));
    }

    public synchronized void close(int i, String str, boolean z) {
        if (this.readyState == ReadyState.CLOSING || this.readyState == ReadyState.CLOSED) {
            return;
        }
        if (this.readyState == ReadyState.OPEN) {
            if (i == 1006) {
                this.readyState = ReadyState.CLOSING;
                flushAndClose(i, str, false);
                return;
            }
            if (this.draft.getCloseHandshakeType() != CloseHandshakeType.NONE) {
                try {
                    if (!z) {
                        try {
                            this.wsl.onWebsocketCloseInitiated(this, i, str);
                        } catch (RuntimeException e) {
                            this.wsl.onWebsocketError(this, e);
                        }
                    }
                    if (isOpen()) {
                        CloseFrame closeFrame = new CloseFrame();
                        closeFrame.setReason(str);
                        closeFrame.setCode(i);
                        closeFrame.isValid();
                        sendFrame(closeFrame);
                    }
                } catch (InvalidDataException e2) {
                    LogUtil.logE("generated frame is invalid", e2);
                    this.wsl.onWebsocketError(this, e2);
                    flushAndClose(1006, "generated frame is invalid", false);
                }
            }
            flushAndClose(i, str, z);
        } else if (i == -3) {
            flushAndClose(-3, str, true);
        } else if (i == 1002) {
            flushAndClose(i, str, z);
        } else {
            flushAndClose(-1, str, false);
        }
        this.readyState = ReadyState.CLOSING;
        this.tmpHandshakeBytes = null;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void close(int i, String str) {
        close(i, str, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0059 A[Catch: all -> 0x0065, TryCatch #1 {, blocks: (B:3:0x0001, B:7:0x0009, B:11:0x0013, B:12:0x0017, B:14:0x001b, B:15:0x001e, B:17:0x0022, B:26:0x0049, B:30:0x0055, B:32:0x0059, B:33:0x005c, B:29:0x0050, B:20:0x0027, B:22:0x002d, B:24:0x0039, B:25:0x003f), top: B:41:0x0001, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void closeConnection(int r4, java.lang.String r5, boolean r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            com.tds.common.websocket.enums.ReadyState r0 = r3.readyState     // Catch: java.lang.Throwable -> L65
            com.tds.common.websocket.enums.ReadyState r1 = com.tds.common.websocket.enums.ReadyState.CLOSED     // Catch: java.lang.Throwable -> L65
            if (r0 != r1) goto L9
            monitor-exit(r3)
            return
        L9:
            com.tds.common.websocket.enums.ReadyState r0 = r3.readyState     // Catch: java.lang.Throwable -> L65
            com.tds.common.websocket.enums.ReadyState r1 = com.tds.common.websocket.enums.ReadyState.OPEN     // Catch: java.lang.Throwable -> L65
            if (r0 != r1) goto L17
            r0 = 1006(0x3ee, float:1.41E-42)
            if (r4 != r0) goto L17
            com.tds.common.websocket.enums.ReadyState r0 = com.tds.common.websocket.enums.ReadyState.CLOSING     // Catch: java.lang.Throwable -> L65
            r3.readyState = r0     // Catch: java.lang.Throwable -> L65
        L17:
            java.nio.channels.SelectionKey r0 = r3.key     // Catch: java.lang.Throwable -> L65
            if (r0 == 0) goto L1e
            r0.cancel()     // Catch: java.lang.Throwable -> L65
        L1e:
            java.nio.channels.ByteChannel r0 = r3.channel     // Catch: java.lang.Throwable -> L65
            if (r0 == 0) goto L49
            r0.close()     // Catch: java.io.IOException -> L26 java.lang.Throwable -> L65
            goto L49
        L26:
            r0 = move-exception
            java.lang.String r1 = r0.getMessage()     // Catch: java.lang.Throwable -> L65
            if (r1 == 0) goto L3f
            java.lang.String r1 = r0.getMessage()     // Catch: java.lang.Throwable -> L65
            java.lang.String r2 = "Broken pipe"
            boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L65
            if (r1 == 0) goto L3f
            java.lang.String r1 = "Caught IOException: Broken pipe during closeConnection()"
            com.tds.common.websocket.util.LogUtil.logD(r1, r0)     // Catch: java.lang.Throwable -> L65
            goto L49
        L3f:
            java.lang.String r1 = "Exception during channel.close()"
            com.tds.common.websocket.util.LogUtil.logE(r1, r0)     // Catch: java.lang.Throwable -> L65
            com.tds.common.websocket.conn.WebSocketListener r1 = r3.wsl     // Catch: java.lang.Throwable -> L65
            r1.onWebsocketError(r3, r0)     // Catch: java.lang.Throwable -> L65
        L49:
            com.tds.common.websocket.conn.WebSocketListener r0 = r3.wsl     // Catch: java.lang.RuntimeException -> L4f java.lang.Throwable -> L65
            r0.onWebsocketClose(r3, r4, r5, r6)     // Catch: java.lang.RuntimeException -> L4f java.lang.Throwable -> L65
            goto L55
        L4f:
            r4 = move-exception
            com.tds.common.websocket.conn.WebSocketListener r5 = r3.wsl     // Catch: java.lang.Throwable -> L65
            r5.onWebsocketError(r3, r4)     // Catch: java.lang.Throwable -> L65
        L55:
            com.tds.common.websocket.drafts.Draft r4 = r3.draft     // Catch: java.lang.Throwable -> L65
            if (r4 == 0) goto L5c
            r4.reset()     // Catch: java.lang.Throwable -> L65
        L5c:
            r4 = 0
            r3.handshakerequest = r4     // Catch: java.lang.Throwable -> L65
            com.tds.common.websocket.enums.ReadyState r4 = com.tds.common.websocket.enums.ReadyState.CLOSED     // Catch: java.lang.Throwable -> L65
            r3.readyState = r4     // Catch: java.lang.Throwable -> L65
            monitor-exit(r3)
            return
        L65:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.websocket.conn.WebSocketImpl.closeConnection(int, java.lang.String, boolean):void");
    }

    protected void closeConnection(int i, boolean z) {
        closeConnection(i, "", z);
    }

    public void closeConnection() {
        if (this.closedremotely == null) {
            throw new IllegalStateException("this method must be used in conjunction with flushAndClose");
        }
        closeConnection(this.closecode.intValue(), this.closemessage, this.closedremotely.booleanValue());
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void closeConnection(int i, String str) {
        closeConnection(i, str, false);
    }

    public synchronized void flushAndClose(int i, String str, boolean z) {
        if (this.flushandclosestate) {
            return;
        }
        this.closecode = Integer.valueOf(i);
        this.closemessage = str;
        this.closedremotely = Boolean.valueOf(z);
        this.flushandclosestate = true;
        this.wsl.onWriteDemand(this);
        try {
            this.wsl.onWebsocketClosing(this, i, str, z);
        } catch (RuntimeException e) {
            LogUtil.logE("Exception in onWebsocketClosing", e);
            this.wsl.onWebsocketError(this, e);
        }
        Draft draft = this.draft;
        if (draft != null) {
            draft.reset();
        }
        this.handshakerequest = null;
    }

    public void eot() {
        if (this.readyState == ReadyState.NOT_YET_CONNECTED) {
            closeConnection(-1, true);
            return;
        }
        if (this.flushandclosestate) {
            closeConnection(this.closecode.intValue(), this.closemessage, this.closedremotely.booleanValue());
            return;
        }
        if (this.draft.getCloseHandshakeType() == CloseHandshakeType.NONE) {
            closeConnection(1000, true);
            return;
        }
        if (this.draft.getCloseHandshakeType() == CloseHandshakeType.ONEWAY) {
            if (this.role == Role.SERVER) {
                closeConnection(1006, true);
                return;
            } else {
                closeConnection(1000, true);
                return;
            }
        }
        closeConnection(1006, true);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void close(int i) {
        close(i, "", false);
    }

    public void close(InvalidDataException invalidDataException) {
        close(invalidDataException.getCloseCode(), invalidDataException.getMessage(), false);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void send(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        send(this.draft.createFrames(str, this.role == Role.CLIENT));
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void send(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("Cannot send 'null' data to a WebSocketImpl.");
        }
        send(this.draft.createFrames(byteBuffer, this.role == Role.CLIENT));
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void send(byte[] bArr) {
        send(ByteBuffer.wrap(bArr));
    }

    private void send(Collection<Framedata> collection) {
        if (!isOpen()) {
            throw new WebsocketNotConnectedException();
        }
        if (collection == null) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList();
        for (Framedata framedata : collection) {
            LogUtil.logD("send frame: {}", framedata);
            arrayList.add(this.draft.createBinaryFrame(framedata));
        }
        write(arrayList);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendFragmentedFrame(Opcode opcode, ByteBuffer byteBuffer, boolean z) {
        send(this.draft.continuousFrame(opcode, byteBuffer, z));
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendFrame(Collection<Framedata> collection) {
        send(collection);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendFrame(Framedata framedata) {
        send(Collections.singletonList(framedata));
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void sendPing() throws NullPointerException {
        PingFrame pingFrameOnPreparePing = this.wsl.onPreparePing(this);
        Objects.requireNonNull(pingFrameOnPreparePing, "onPreparePing(WebSocket) returned null. PingFrame to sent can't be null.");
        sendFrame(pingFrameOnPreparePing);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean hasBufferedData() {
        return !this.outQueue.isEmpty();
    }

    public void startHandshake(ClientHandshakeBuilder clientHandshakeBuilder) throws InvalidHandshakeException {
        this.handshakerequest = this.draft.postProcessHandshakeRequestAsClient(clientHandshakeBuilder);
        this.resourceDescriptor = clientHandshakeBuilder.getResourceDescriptor();
        try {
            this.wsl.onWebsocketHandshakeSentAsClient(this, this.handshakerequest);
            write(this.draft.createHandshake(this.handshakerequest));
        } catch (InvalidDataException unused) {
            throw new InvalidHandshakeException("Handshake data rejected by client.");
        } catch (RuntimeException e) {
            LogUtil.logE("Exception in startHandshake", e);
            this.wsl.onWebsocketError(this, e);
            throw new InvalidHandshakeException("rejected because of " + e);
        }
    }

    private void write(ByteBuffer byteBuffer) {
        LogUtil.logD("write({}): {}", byteBuffer.remaining() + byteBuffer.remaining() > 1000 ? "too big to display" : new String(byteBuffer.array()));
        this.outQueue.add(byteBuffer);
        this.wsl.onWriteDemand(this);
    }

    private void write(List<ByteBuffer> list) {
        synchronized (this.synchronizeWriteObject) {
            Iterator<ByteBuffer> it = list.iterator();
            while (it.hasNext()) {
                write(it.next());
            }
        }
    }

    private void open(Handshakedata handshakedata) {
        LogUtil.logD("open using draft: {}", this.draft);
        this.readyState = ReadyState.OPEN;
        try {
            this.wsl.onWebsocketOpen(this, handshakedata);
        } catch (RuntimeException e) {
            this.wsl.onWebsocketError(this, e);
        }
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isOpen() {
        return this.readyState == ReadyState.OPEN;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isClosing() {
        return this.readyState == ReadyState.CLOSING;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isFlushAndClose() {
        return this.flushandclosestate;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean isClosed() {
        return this.readyState == ReadyState.CLOSED;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public ReadyState getReadyState() {
        return this.readyState;
    }

    public void setSelectionKey(SelectionKey selectionKey) {
        this.key = selectionKey;
    }

    public SelectionKey getSelectionKey() {
        return this.key;
    }

    public String toString() {
        return super.toString();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public InetSocketAddress getRemoteSocketAddress() {
        return this.wsl.getRemoteSocketAddress(this);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public InetSocketAddress getLocalSocketAddress() {
        return this.wsl.getLocalSocketAddress(this);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public Draft getDraft() {
        return this.draft;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public void close() {
        close(1000);
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public String getResourceDescriptor() {
        return this.resourceDescriptor;
    }

    long getLastPong() {
        return this.lastPong;
    }

    public void updateLastPong() {
        this.lastPong = System.nanoTime();
    }

    public WebSocketListener getWebSocketListener() {
        return this.wsl;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public <T> T getAttachment() {
        return (T) this.attachment;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public boolean hasSSLSupport() {
        return this.channel instanceof ISSLChannel;
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public SSLSession getSSLSession() {
        if (!hasSSLSupport()) {
            throw new IllegalArgumentException("This websocket uses ws instead of wss. No SSLSession available.");
        }
        return ((ISSLChannel) this.channel).getSSLEngine().getSession();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public IProtocol getProtocol() {
        Draft draft = this.draft;
        if (draft == null) {
            return null;
        }
        if (!(draft instanceof Draft_6455)) {
            throw new IllegalArgumentException("This draft does not support Sec-WebSocket-Protocol");
        }
        return ((Draft_6455) draft).getProtocol();
    }

    @Override // com.tds.common.websocket.conn.WebSocket
    public <T> void setAttachment(T t) {
        this.attachment = t;
    }

    public ByteChannel getChannel() {
        return this.channel;
    }

    public void setChannel(ByteChannel byteChannel) {
        this.channel = byteChannel;
    }

    public WebSocketServer.WebSocketWorker getWorkerThread() {
        return this.workerThread;
    }

    public void setWorkerThread(WebSocketServer.WebSocketWorker webSocketWorker) {
        this.workerThread = webSocketWorker;
    }
}
