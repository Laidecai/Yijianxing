package com.tds.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public enum ByteBufferUtils {
    ;

    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static void checkRange(ByteBuffer byteBuffer, int i, int i2) {
        SafeUtils.checkLength(i2);
        if (i2 > 0) {
            checkRange(byteBuffer, i);
            checkRange(byteBuffer, (i + i2) - 1);
        }
    }

    public static void checkRange(ByteBuffer byteBuffer, int i) {
        if (i < 0 || i >= byteBuffer.capacity()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
    }

    public static ByteBuffer inLittleEndianOrder(ByteBuffer byteBuffer) {
        return byteBuffer.order().equals(ByteOrder.LITTLE_ENDIAN) ? byteBuffer : byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
    }

    public static ByteBuffer inNativeByteOrder(ByteBuffer byteBuffer) {
        return byteBuffer.order().equals(Utils.NATIVE_BYTE_ORDER) ? byteBuffer : byteBuffer.duplicate().order(Utils.NATIVE_BYTE_ORDER);
    }

    public static byte readByte(ByteBuffer byteBuffer, int i) {
        return byteBuffer.get(i);
    }

    public static void writeInt(ByteBuffer byteBuffer, int i, int i2) {
        byteBuffer.putInt(i, i2);
    }

    public static int readInt(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getInt(i);
    }

    public static int readIntLE(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getInt(i);
    }

    public static void writeLong(ByteBuffer byteBuffer, int i, long j) {
        byteBuffer.putLong(i, j);
    }

    public static long readLong(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getLong(i);
    }

    public static long readLongLE(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getLong(i);
    }

    public static void writeByte(ByteBuffer byteBuffer, int i, int i2) {
        byteBuffer.put(i, (byte) i2);
    }

    public static void writeShortLE(ByteBuffer byteBuffer, int i, int i2) {
        byteBuffer.put(i, (byte) i2);
        byteBuffer.put(i + 1, (byte) (i2 >>> 8));
    }

    public static void checkNotReadOnly(ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
    }

    public static int readShortLE(ByteBuffer byteBuffer, int i) {
        return ((byteBuffer.get(i + 1) & UByte.MAX_VALUE) << 8) | (byteBuffer.get(i) & UByte.MAX_VALUE);
    }
}
