package com.tds.common.websocket.conn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

/* JADX INFO: loaded from: classes.dex */
public interface WrappedByteChannel extends ByteChannel {
    boolean isBlocking();

    boolean isNeedRead();

    boolean isNeedWrite();

    int readMore(ByteBuffer byteBuffer) throws IOException;

    void writeMore() throws IOException;
}
