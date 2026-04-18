package com.tds.xxhash;

import com.tds.util.ByteBufferUtils;
import com.tds.util.SafeUtils;
import java.nio.ByteBuffer;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
final class XXHash64JavaSafe extends XXHash64 {
    public static final XXHash64 INSTANCE = new XXHash64JavaSafe();

    XXHash64JavaSafe() {
    }

    @Override // com.tds.xxhash.XXHash64
    public long hash(byte[] bArr, int i, int i2, long j) {
        int i3;
        long jRotateLeft;
        long jRotateLeft2;
        long jRotateLeft3;
        long jRotateLeft4;
        long jRotateLeft5;
        byte[] bArr2 = bArr;
        SafeUtils.checkRange(bArr, i, i2);
        int i4 = i + i2;
        long j2 = -4417276706812531889L;
        if (i2 >= 32) {
            int i5 = i4 - 32;
            long j3 = j - (-7046029288634856825L);
            long j4 = j + 0;
            long j5 = j - 4417276706812531889L;
            long j6 = (j - 7046029288634856825L) - 4417276706812531889L;
            i3 = i;
            while (true) {
                jRotateLeft2 = Long.rotateLeft(j6 + (SafeUtils.readLongLE(bArr2, i3) * j2), 31) * (-7046029288634856825L);
                int i6 = i3 + 8;
                jRotateLeft3 = Long.rotateLeft(j5 + (SafeUtils.readLongLE(bArr2, i6) * j2), 31) * (-7046029288634856825L);
                int i7 = i6 + 8;
                jRotateLeft4 = Long.rotateLeft(j4 + (SafeUtils.readLongLE(bArr2, i7) * j2), 31) * (-7046029288634856825L);
                int i8 = i7 + 8;
                jRotateLeft5 = Long.rotateLeft(j3 + (SafeUtils.readLongLE(bArr2, i8) * (-4417276706812531889L)), 31) * (-7046029288634856825L);
                i3 = i8 + 8;
                if (i3 > i5) {
                    break;
                }
                j3 = jRotateLeft5;
                j6 = jRotateLeft2;
                j5 = jRotateLeft3;
                j4 = jRotateLeft4;
                j2 = -4417276706812531889L;
                bArr2 = bArr;
            }
            jRotateLeft = (((Long.rotateLeft(jRotateLeft5 * (-4417276706812531889L), 31) * (-7046029288634856825L)) ^ ((((((((((Long.rotateLeft(jRotateLeft2 * (-4417276706812531889L), 31) * (-7046029288634856825L)) ^ (((Long.rotateLeft(jRotateLeft2, 1) + Long.rotateLeft(jRotateLeft3, 7)) + Long.rotateLeft(jRotateLeft4, 12)) + Long.rotateLeft(jRotateLeft5, 18))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(jRotateLeft3 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(jRotateLeft4 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L)) * (-7046029288634856825L)) - 8796714831421723037L;
        } else {
            i3 = i;
            jRotateLeft = j + 2870177450012600261L;
        }
        long jRotateLeft6 = jRotateLeft + ((long) i2);
        while (i3 <= i4 - 8) {
            jRotateLeft6 = (Long.rotateLeft(jRotateLeft6 ^ (Long.rotateLeft(SafeUtils.readLongLE(bArr, i3) * (-4417276706812531889L), 31) * (-7046029288634856825L)), 27) * (-7046029288634856825L)) - 8796714831421723037L;
            i3 += 8;
        }
        if (i3 <= i4 - 4) {
            jRotateLeft6 = (Long.rotateLeft(jRotateLeft6 ^ ((((long) SafeUtils.readIntLE(bArr, i3)) & 4294967295L) * (-7046029288634856825L)), 23) * (-4417276706812531889L)) + 1609587929392839161L;
            i3 += 4;
        }
        while (i3 < i4) {
            jRotateLeft6 = Long.rotateLeft(jRotateLeft6 ^ (((long) (SafeUtils.readByte(bArr, i3) & UByte.MAX_VALUE)) * 2870177450012600261L), 11) * (-7046029288634856825L);
            i3++;
        }
        long j7 = (jRotateLeft6 ^ (jRotateLeft6 >>> 33)) * (-4417276706812531889L);
        long j8 = (j7 ^ (j7 >>> 29)) * 1609587929392839161L;
        return j8 ^ (j8 >>> 32);
    }

    @Override // com.tds.xxhash.XXHash64
    public long hash(ByteBuffer byteBuffer, int i, int i2, long j) {
        int i3;
        long jRotateLeft;
        long jRotateLeft2;
        long jRotateLeft3;
        long jRotateLeft4;
        long jRotateLeft5;
        if (byteBuffer.hasArray()) {
            return hash(byteBuffer.array(), i + byteBuffer.arrayOffset(), i2, j);
        }
        ByteBufferUtils.checkRange(byteBuffer, i, i2);
        ByteBuffer byteBufferInLittleEndianOrder = ByteBufferUtils.inLittleEndianOrder(byteBuffer);
        int i4 = i + i2;
        long j2 = -4417276706812531889L;
        if (i2 >= 32) {
            int i5 = i4 - 32;
            long j3 = j - (-7046029288634856825L);
            long j4 = j + 0;
            long j5 = j - 4417276706812531889L;
            long j6 = (j - 7046029288634856825L) - 4417276706812531889L;
            i3 = i;
            while (true) {
                jRotateLeft2 = Long.rotateLeft(j6 + (ByteBufferUtils.readLongLE(byteBufferInLittleEndianOrder, i3) * j2), 31) * (-7046029288634856825L);
                int i6 = i3 + 8;
                jRotateLeft3 = Long.rotateLeft(j5 + (ByteBufferUtils.readLongLE(byteBufferInLittleEndianOrder, i6) * j2), 31) * (-7046029288634856825L);
                int i7 = i6 + 8;
                jRotateLeft4 = Long.rotateLeft(j4 + (ByteBufferUtils.readLongLE(byteBufferInLittleEndianOrder, i7) * j2), 31) * (-7046029288634856825L);
                int i8 = i7 + 8;
                jRotateLeft5 = Long.rotateLeft(j3 + (ByteBufferUtils.readLongLE(byteBufferInLittleEndianOrder, i8) * j2), 31) * (-7046029288634856825L);
                i3 = i8 + 8;
                if (i3 > i5) {
                    break;
                }
                j6 = jRotateLeft2;
                j5 = jRotateLeft3;
                j4 = jRotateLeft4;
                j3 = jRotateLeft5;
                j2 = -4417276706812531889L;
            }
            jRotateLeft = ((((((((((((Long.rotateLeft(jRotateLeft2 * (-4417276706812531889L), 31) * (-7046029288634856825L)) ^ (((Long.rotateLeft(jRotateLeft2, 1) + Long.rotateLeft(jRotateLeft3, 7)) + Long.rotateLeft(jRotateLeft4, 12)) + Long.rotateLeft(jRotateLeft5, 18))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(jRotateLeft3 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(jRotateLeft4 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(jRotateLeft5 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L;
        } else {
            i3 = i;
            jRotateLeft = j + 2870177450012600261L;
        }
        long jRotateLeft6 = jRotateLeft + ((long) i2);
        while (i3 <= i4 - 8) {
            jRotateLeft6 = (Long.rotateLeft(jRotateLeft6 ^ (Long.rotateLeft(ByteBufferUtils.readLongLE(byteBufferInLittleEndianOrder, i3) * (-4417276706812531889L), 31) * (-7046029288634856825L)), 27) * (-7046029288634856825L)) - 8796714831421723037L;
            i3 += 8;
        }
        if (i3 <= i4 - 4) {
            jRotateLeft6 = (Long.rotateLeft(jRotateLeft6 ^ ((((long) ByteBufferUtils.readIntLE(byteBufferInLittleEndianOrder, i3)) & 4294967295L) * (-7046029288634856825L)), 23) * (-4417276706812531889L)) + 1609587929392839161L;
            i3 += 4;
        }
        while (i3 < i4) {
            jRotateLeft6 = Long.rotateLeft(jRotateLeft6 ^ (((long) (ByteBufferUtils.readByte(byteBufferInLittleEndianOrder, i3) & UByte.MAX_VALUE)) * 2870177450012600261L), 11) * (-7046029288634856825L);
            i3++;
        }
        long j7 = ((jRotateLeft6 >>> 33) ^ jRotateLeft6) * (-4417276706812531889L);
        long j8 = (j7 ^ (j7 >>> 29)) * 1609587929392839161L;
        return j8 ^ (j8 >>> 32);
    }
}
