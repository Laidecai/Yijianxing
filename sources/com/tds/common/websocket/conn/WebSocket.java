package com.tds.common.websocket.conn;

import com.tds.common.websocket.drafts.Draft;
import com.tds.common.websocket.enums.Opcode;
import com.tds.common.websocket.enums.ReadyState;
import com.tds.common.websocket.framing.Framedata;
import com.tds.common.websocket.protocols.IProtocol;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Collection;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes.dex */
public interface WebSocket {
    void close();

    void close(int i);

    void close(int i, String str);

    void closeConnection(int i, String str);

    <T> T getAttachment();

    Draft getDraft();

    InetSocketAddress getLocalSocketAddress();

    IProtocol getProtocol();

    ReadyState getReadyState();

    InetSocketAddress getRemoteSocketAddress();

    String getResourceDescriptor();

    SSLSession getSSLSession() throws IllegalArgumentException;

    boolean hasBufferedData();

    boolean hasSSLSupport();

    boolean isClosed();

    boolean isClosing();

    boolean isFlushAndClose();

    boolean isOpen();

    void send(String str);

    void send(ByteBuffer byteBuffer);

    void send(byte[] bArr);

    void sendFragmentedFrame(Opcode opcode, ByteBuffer byteBuffer, boolean z);

    void sendFrame(Framedata framedata);

    void sendFrame(Collection<Framedata> collection);

    void sendPing();

    <T> void setAttachment(T t);
}
