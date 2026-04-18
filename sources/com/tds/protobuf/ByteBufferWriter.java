package com.tds.protobuf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
final class ByteBufferWriter {
    private static final ThreadLocal<SoftReference<byte[]>> BUFFER = new ThreadLocal<>();
    private static final float BUFFER_REALLOCATION_THRESHOLD = 0.5f;
    private static final int MAX_CACHED_BUFFER_SIZE = 16384;
    private static final int MIN_CACHED_BUFFER_SIZE = 1024;

    private static boolean needToReallocate(int i, int i2) {
        return i2 < i && ((float) i2) < ((float) i) * BUFFER_REALLOCATION_THRESHOLD;
    }

    private ByteBufferWriter() {
    }

    static void clearCachedBuffer() {
        BUFFER.set(null);
    }

    static void write(ByteBuffer byteBuffer, OutputStream outputStream) throws IOException {
        int iPosition = byteBuffer.position();
        try {
            if (byteBuffer.hasArray()) {
                outputStream.write(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            } else if (outputStream instanceof FileOutputStream) {
                ((FileOutputStream) outputStream).getChannel().write(byteBuffer);
            } else {
                byte[] orCreateBuffer = getOrCreateBuffer(byteBuffer.remaining());
                while (byteBuffer.hasRemaining()) {
                    int iMin = Math.min(byteBuffer.remaining(), orCreateBuffer.length);
                    byteBuffer.get(orCreateBuffer, 0, iMin);
                    outputStream.write(orCreateBuffer, 0, iMin);
                }
            }
        } finally {
            byteBuffer.position(iPosition);
        }
    }

    private static byte[] getOrCreateBuffer(int i) {
        int iMax = Math.max(i, 1024);
        byte[] buffer = getBuffer();
        if (buffer == null || needToReallocate(iMax, buffer.length)) {
            buffer = new byte[iMax];
            if (iMax <= 16384) {
                setBuffer(buffer);
            }
        }
        return buffer;
    }

    private static byte[] getBuffer() {
        SoftReference<byte[]> softReference = BUFFER.get();
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }

    private static void setBuffer(byte[] bArr) {
        BUFFER.set(new SoftReference<>(bArr));
    }
}
