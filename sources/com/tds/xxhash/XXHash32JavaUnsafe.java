package com.tds.xxhash;

import com.tds.util.ByteBufferUtils;
import com.tds.util.UnsafeUtils;
import java.nio.ByteBuffer;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
final class XXHash32JavaUnsafe extends XXHash32 {
    public static final XXHash32 INSTANCE = new XXHash32JavaUnsafe();

    XXHash32JavaUnsafe() {
    }

    @Override // com.tds.xxhash.XXHash32
    public int hash(byte[] bArr, int i, int i2, int i3) {
        int iRotateLeft;
        UnsafeUtils.checkRange(bArr, i, i2);
        int i4 = i + i2;
        if (i2 >= 16) {
            int i5 = i4 - 16;
            int iRotateLeft2 = (i3 - 1640531535) - 2048144777;
            int iRotateLeft3 = i3 - 2048144777;
            int iRotateLeft4 = i3 + 0;
            int iRotateLeft5 = i3 - (-1640531535);
            do {
                iRotateLeft2 = Integer.rotateLeft(iRotateLeft2 + (UnsafeUtils.readIntLE(bArr, i) * (-2048144777)), 13) * (-1640531535);
                int i6 = i + 4;
                iRotateLeft3 = Integer.rotateLeft(iRotateLeft3 + (UnsafeUtils.readIntLE(bArr, i6) * (-2048144777)), 13) * (-1640531535);
                int i7 = i6 + 4;
                iRotateLeft4 = Integer.rotateLeft(iRotateLeft4 + (UnsafeUtils.readIntLE(bArr, i7) * (-2048144777)), 13) * (-1640531535);
                int i8 = i7 + 4;
                iRotateLeft5 = Integer.rotateLeft(iRotateLeft5 + (UnsafeUtils.readIntLE(bArr, i8) * (-2048144777)), 13) * (-1640531535);
                i = i8 + 4;
            } while (i <= i5);
            iRotateLeft = Integer.rotateLeft(iRotateLeft2, 1) + Integer.rotateLeft(iRotateLeft3, 7) + Integer.rotateLeft(iRotateLeft4, 12) + Integer.rotateLeft(iRotateLeft5, 18);
        } else {
            iRotateLeft = i3 + 374761393;
        }
        int iRotateLeft6 = iRotateLeft + i2;
        while (i <= i4 - 4) {
            iRotateLeft6 = Integer.rotateLeft(iRotateLeft6 + (UnsafeUtils.readIntLE(bArr, i) * (-1028477379)), 17) * 668265263;
            i += 4;
        }
        while (i < i4) {
            iRotateLeft6 = Integer.rotateLeft(iRotateLeft6 + ((UnsafeUtils.readByte(bArr, i) & UByte.MAX_VALUE) * 374761393), 11) * (-1640531535);
            i++;
        }
        int i9 = ((iRotateLeft6 >>> 15) ^ iRotateLeft6) * (-2048144777);
        int i10 = (i9 ^ (i9 >>> 13)) * (-1028477379);
        return i10 ^ (i10 >>> 16);
    }

    @Override // com.tds.xxhash.XXHash32
    public int hash(ByteBuffer byteBuffer, int i, int i2, int i3) {
        int iRotateLeft;
        if (byteBuffer.hasArray()) {
            return hash(byteBuffer.array(), i + byteBuffer.arrayOffset(), i2, i3);
        }
        ByteBufferUtils.checkRange(byteBuffer, i, i2);
        ByteBuffer byteBufferInLittleEndianOrder = ByteBufferUtils.inLittleEndianOrder(byteBuffer);
        int i4 = i + i2;
        if (i2 >= 16) {
            int i5 = i4 - 16;
            int iRotateLeft2 = (i3 - 1640531535) - 2048144777;
            int iRotateLeft3 = i3 - 2048144777;
            int iRotateLeft4 = i3 + 0;
            int iRotateLeft5 = i3 - (-1640531535);
            do {
                iRotateLeft2 = Integer.rotateLeft(iRotateLeft2 + (ByteBufferUtils.readIntLE(byteBufferInLittleEndianOrder, i) * (-2048144777)), 13) * (-1640531535);
                int i6 = i + 4;
                iRotateLeft3 = Integer.rotateLeft(iRotateLeft3 + (ByteBufferUtils.readIntLE(byteBufferInLittleEndianOrder, i6) * (-2048144777)), 13) * (-1640531535);
                int i7 = i6 + 4;
                iRotateLeft4 = Integer.rotateLeft(iRotateLeft4 + (ByteBufferUtils.readIntLE(byteBufferInLittleEndianOrder, i7) * (-2048144777)), 13) * (-1640531535);
                int i8 = i7 + 4;
                iRotateLeft5 = Integer.rotateLeft(iRotateLeft5 + (ByteBufferUtils.readIntLE(byteBufferInLittleEndianOrder, i8) * (-2048144777)), 13) * (-1640531535);
                i = i8 + 4;
            } while (i <= i5);
            iRotateLeft = Integer.rotateLeft(iRotateLeft2, 1) + Integer.rotateLeft(iRotateLeft3, 7) + Integer.rotateLeft(iRotateLeft4, 12) + Integer.rotateLeft(iRotateLeft5, 18);
        } else {
            iRotateLeft = i3 + 374761393;
        }
        int iRotateLeft6 = iRotateLeft + i2;
        while (i <= i4 - 4) {
            iRotateLeft6 = Integer.rotateLeft(iRotateLeft6 + (ByteBufferUtils.readIntLE(byteBufferInLittleEndianOrder, i) * (-1028477379)), 17) * 668265263;
            i += 4;
        }
        while (i < i4) {
            iRotateLeft6 = Integer.rotateLeft(iRotateLeft6 + ((ByteBufferUtils.readByte(byteBufferInLittleEndianOrder, i) & UByte.MAX_VALUE) * 374761393), 11) * (-1640531535);
            i++;
        }
        int i9 = ((iRotateLeft6 >>> 15) ^ iRotateLeft6) * (-2048144777);
        int i10 = (i9 ^ (i9 >>> 13)) * (-1028477379);
        return i10 ^ (i10 >>> 16);
    }
}
