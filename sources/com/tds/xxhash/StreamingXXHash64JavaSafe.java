package com.tds.xxhash;

import com.tds.util.SafeUtils;
import com.tds.xxhash.StreamingXXHash64;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
final class StreamingXXHash64JavaSafe extends AbstractStreamingXXHash64Java {

    static class Factory implements StreamingXXHash64.Factory {
        public static final StreamingXXHash64.Factory INSTANCE = new Factory();

        Factory() {
        }

        @Override // com.tds.xxhash.StreamingXXHash64.Factory
        public StreamingXXHash64 newStreamingHash(long j) {
            return new StreamingXXHash64JavaSafe(j);
        }
    }

    StreamingXXHash64JavaSafe(long j) {
        super(j);
    }

    @Override // com.tds.xxhash.StreamingXXHash64
    public long getValue() {
        long jRotateLeft;
        if (this.totalLen >= 32) {
            long j = this.v1;
            long j2 = this.v2;
            long j3 = this.v3;
            jRotateLeft = ((((((((((((Long.rotateLeft(j * (-4417276706812531889L), 31) * (-7046029288634856825L)) ^ (((Long.rotateLeft(j, 1) + Long.rotateLeft(j2, 7)) + Long.rotateLeft(j3, 12)) + Long.rotateLeft(r3, 18))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(j2 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(j3 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L) ^ (Long.rotateLeft(this.v4 * (-4417276706812531889L), 31) * (-7046029288634856825L))) * (-7046029288634856825L)) - 8796714831421723037L;
        } else {
            jRotateLeft = this.seed + 2870177450012600261L;
        }
        long jRotateLeft2 = jRotateLeft + this.totalLen;
        int i = 0;
        while (i <= this.memSize - 8) {
            jRotateLeft2 = (Long.rotateLeft(jRotateLeft2 ^ (Long.rotateLeft(SafeUtils.readLongLE(this.memory, i) * (-4417276706812531889L), 31) * (-7046029288634856825L)), 27) * (-7046029288634856825L)) - 8796714831421723037L;
            i += 8;
        }
        if (i <= this.memSize - 4) {
            jRotateLeft2 = (Long.rotateLeft(jRotateLeft2 ^ ((((long) SafeUtils.readIntLE(this.memory, i)) & 4294967295L) * (-7046029288634856825L)), 23) * (-4417276706812531889L)) + 1609587929392839161L;
            i += 4;
        }
        while (i < this.memSize) {
            jRotateLeft2 = Long.rotateLeft(jRotateLeft2 ^ (((long) (this.memory[i] & UByte.MAX_VALUE)) * 2870177450012600261L), 11) * (-7046029288634856825L);
            i++;
        }
        long j4 = (jRotateLeft2 ^ (jRotateLeft2 >>> 33)) * (-4417276706812531889L);
        long j5 = (j4 ^ (j4 >>> 29)) * 1609587929392839161L;
        return j5 ^ (j5 >>> 32);
    }

    @Override // com.tds.xxhash.StreamingXXHash64
    public void update(byte[] bArr, int i, int i2) {
        int i3 = i;
        SafeUtils.checkRange(bArr, i, i2);
        this.totalLen += (long) i2;
        if (this.memSize + i2 < 32) {
            System.arraycopy(bArr, i3, this.memory, this.memSize, i2);
            this.memSize += i2;
            return;
        }
        int i4 = i2 + i3;
        long j = -4417276706812531889L;
        if (this.memSize > 0) {
            System.arraycopy(bArr, i3, this.memory, this.memSize, 32 - this.memSize);
            this.v1 += SafeUtils.readLongLE(this.memory, 0) * (-4417276706812531889L);
            this.v1 = Long.rotateLeft(this.v1, 31);
            this.v1 *= -7046029288634856825L;
            this.v2 += SafeUtils.readLongLE(this.memory, 8) * (-4417276706812531889L);
            this.v2 = Long.rotateLeft(this.v2, 31);
            this.v2 *= -7046029288634856825L;
            this.v3 += SafeUtils.readLongLE(this.memory, 16) * (-4417276706812531889L);
            this.v3 = Long.rotateLeft(this.v3, 31);
            this.v3 *= -7046029288634856825L;
            this.v4 += SafeUtils.readLongLE(this.memory, 24) * (-4417276706812531889L);
            this.v4 = Long.rotateLeft(this.v4, 31);
            this.v4 *= -7046029288634856825L;
            i3 += 32 - this.memSize;
            this.memSize = 0;
        }
        int i5 = i4 - 32;
        long j2 = this.v1;
        long j3 = this.v2;
        long jRotateLeft = this.v3;
        long jRotateLeft2 = j2;
        long jRotateLeft3 = this.v4;
        long jRotateLeft4 = j3;
        while (i3 <= i5) {
            jRotateLeft2 = Long.rotateLeft(jRotateLeft2 + (SafeUtils.readLongLE(bArr, i3) * j), 31) * (-7046029288634856825L);
            int i6 = i3 + 8;
            jRotateLeft4 = Long.rotateLeft(jRotateLeft4 + (SafeUtils.readLongLE(bArr, i6) * (-4417276706812531889L)), 31) * (-7046029288634856825L);
            int i7 = i6 + 8;
            jRotateLeft = Long.rotateLeft(jRotateLeft + (SafeUtils.readLongLE(bArr, i7) * (-4417276706812531889L)), 31) * (-7046029288634856825L);
            int i8 = i7 + 8;
            jRotateLeft3 = Long.rotateLeft(jRotateLeft3 + (SafeUtils.readLongLE(bArr, i8) * (-4417276706812531889L)), 31) * (-7046029288634856825L);
            i3 = i8 + 8;
            j = -4417276706812531889L;
        }
        this.v1 = jRotateLeft2;
        this.v2 = jRotateLeft4;
        this.v3 = jRotateLeft;
        this.v4 = jRotateLeft3;
        if (i3 < i4) {
            int i9 = i4 - i3;
            System.arraycopy(bArr, i3, this.memory, 0, i9);
            this.memSize = i9;
        }
    }
}
