package com.tds.protobuf;

import java.io.IOException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class UnsafeByteOperations {
    private UnsafeByteOperations() {
    }

    public static ByteString unsafeWrap(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return ByteString.wrap(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
        }
        return new NioByteString(byteBuffer);
    }

    public static void unsafeWriteTo(ByteString byteString, ByteOutput byteOutput) throws IOException {
        byteString.writeTo(byteOutput);
    }
}
