package com.tds.lz4;

import com.tds.util.ByteBufferUtils;
import com.tds.util.SafeUtils;
import java.nio.ByteBuffer;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
final class LZ4JavaSafeFastDecompressor extends LZ4FastDecompressor {
    public static final LZ4FastDecompressor INSTANCE = new LZ4JavaSafeFastDecompressor();

    LZ4JavaSafeFastDecompressor() {
    }

    @Override // com.tds.lz4.LZ4FastDecompressor, com.tds.lz4.LZ4Decompressor
    public int decompress(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4;
        byte b;
        int i5;
        byte b2;
        SafeUtils.checkRange(bArr, i);
        SafeUtils.checkRange(bArr2, i2, i3);
        if (i3 == 0) {
            if (SafeUtils.readByte(bArr, i) == 0) {
                return 1;
            }
            throw new LZ4Exception("Malformed input at " + i);
        }
        int i6 = i3 + i2;
        int i7 = i;
        int i8 = i2;
        while (true) {
            int i9 = SafeUtils.readByte(bArr, i7) & UByte.MAX_VALUE;
            int i10 = i7 + 1;
            int i11 = i9 >>> 4;
            if (i11 == 15) {
                while (true) {
                    i5 = i10 + 1;
                    b2 = SafeUtils.readByte(bArr, i10);
                    if (b2 != -1) {
                        break;
                    }
                    i11 += 255;
                    i10 = i5;
                }
                i11 += b2 & UByte.MAX_VALUE;
                i10 = i5;
            }
            int i12 = i8 + i11;
            int i13 = i6 - 8;
            if (i12 > i13) {
                if (i12 != i6) {
                    throw new LZ4Exception("Malformed input at " + i10);
                }
                LZ4SafeUtils.safeArraycopy(bArr, i10, bArr2, i8, i11);
                return (i10 + i11) - i;
            }
            LZ4SafeUtils.wildArraycopy(bArr, i10, bArr2, i8, i11);
            int i14 = i10 + i11;
            int shortLE = SafeUtils.readShortLE(bArr, i14);
            i7 = i14 + 2;
            int i15 = i12 - shortLE;
            if (i15 < i2) {
                throw new LZ4Exception("Malformed input at " + i7);
            }
            int i16 = i9 & 15;
            if (i16 == 15) {
                while (true) {
                    i4 = i7 + 1;
                    b = SafeUtils.readByte(bArr, i7);
                    if (b != -1) {
                        break;
                    }
                    i16 += 255;
                    i7 = i4;
                }
                i16 += b & UByte.MAX_VALUE;
                i7 = i4;
            }
            int i17 = i16 + 4;
            int i18 = i12 + i17;
            if (i18 <= i13) {
                LZ4SafeUtils.wildIncrementalCopy(bArr2, i15, i12, i18);
            } else {
                if (i18 > i6) {
                    throw new LZ4Exception("Malformed input at " + i7);
                }
                LZ4SafeUtils.safeIncrementalCopy(bArr2, i15, i12, i17);
            }
            i8 = i18;
        }
    }

    @Override // com.tds.lz4.LZ4FastDecompressor
    public int decompress(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
        int i4;
        byte b;
        int i5;
        byte b2;
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return decompress(byteBuffer.array(), i + byteBuffer.arrayOffset(), byteBuffer2.array(), i2 + byteBuffer2.arrayOffset(), i3);
        }
        ByteBuffer byteBufferInNativeByteOrder = ByteBufferUtils.inNativeByteOrder(byteBuffer);
        ByteBuffer byteBufferInNativeByteOrder2 = ByteBufferUtils.inNativeByteOrder(byteBuffer2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder, i);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder2, i2, i3);
        if (i3 == 0) {
            if (ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i) == 0) {
                return 1;
            }
            throw new LZ4Exception("Malformed input at " + i);
        }
        int i6 = i3 + i2;
        int i7 = i;
        int i8 = i2;
        while (true) {
            int i9 = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i7) & UByte.MAX_VALUE;
            int i10 = i7 + 1;
            int i11 = i9 >>> 4;
            if (i11 == 15) {
                while (true) {
                    i5 = i10 + 1;
                    b2 = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i10);
                    if (b2 != -1) {
                        break;
                    }
                    i11 += 255;
                    i10 = i5;
                }
                i11 += b2 & UByte.MAX_VALUE;
                i10 = i5;
            }
            int i12 = i8 + i11;
            int i13 = i6 - 8;
            if (i12 > i13) {
                if (i12 != i6) {
                    throw new LZ4Exception("Malformed input at " + i10);
                }
                LZ4ByteBufferUtils.safeArraycopy(byteBufferInNativeByteOrder, i10, byteBufferInNativeByteOrder2, i8, i11);
                return (i10 + i11) - i;
            }
            LZ4ByteBufferUtils.wildArraycopy(byteBufferInNativeByteOrder, i10, byteBufferInNativeByteOrder2, i8, i11);
            int i14 = i10 + i11;
            int shortLE = ByteBufferUtils.readShortLE(byteBufferInNativeByteOrder, i14);
            i7 = i14 + 2;
            int i15 = i12 - shortLE;
            if (i15 < i2) {
                throw new LZ4Exception("Malformed input at " + i7);
            }
            int i16 = i9 & 15;
            if (i16 == 15) {
                while (true) {
                    i4 = i7 + 1;
                    b = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i7);
                    if (b != -1) {
                        break;
                    }
                    i16 += 255;
                    i7 = i4;
                }
                i16 += b & UByte.MAX_VALUE;
                i7 = i4;
            }
            int i17 = i16 + 4;
            int i18 = i12 + i17;
            if (i18 <= i13) {
                LZ4ByteBufferUtils.wildIncrementalCopy(byteBufferInNativeByteOrder2, i15, i12, i18);
            } else {
                if (i18 > i6) {
                    throw new LZ4Exception("Malformed input at " + i7);
                }
                LZ4ByteBufferUtils.safeIncrementalCopy(byteBufferInNativeByteOrder2, i15, i12, i17);
            }
            i8 = i18;
        }
    }
}
