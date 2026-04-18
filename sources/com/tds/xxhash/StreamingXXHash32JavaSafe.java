package com.tds.xxhash;

import com.tds.util.SafeUtils;
import com.tds.xxhash.StreamingXXHash32;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
final class StreamingXXHash32JavaSafe extends AbstractStreamingXXHash32Java {

    static class Factory implements StreamingXXHash32.Factory {
        public static final StreamingXXHash32.Factory INSTANCE = new Factory();

        Factory() {
        }

        @Override // com.tds.xxhash.StreamingXXHash32.Factory
        public StreamingXXHash32 newStreamingHash(int i) {
            return new StreamingXXHash32JavaSafe(i);
        }
    }

    StreamingXXHash32JavaSafe(int i) {
        super(i);
    }

    @Override // com.tds.xxhash.StreamingXXHash32
    public int getValue() {
        int iRotateLeft;
        if (this.totalLen >= 16) {
            iRotateLeft = Integer.rotateLeft(this.v1, 1) + Integer.rotateLeft(this.v2, 7) + Integer.rotateLeft(this.v3, 12) + Integer.rotateLeft(this.v4, 18);
        } else {
            iRotateLeft = this.seed + 374761393;
        }
        int iRotateLeft2 = (int) (((long) iRotateLeft) + this.totalLen);
        int i = 0;
        while (i <= this.memSize - 4) {
            iRotateLeft2 = Integer.rotateLeft(iRotateLeft2 + (SafeUtils.readIntLE(this.memory, i) * (-1028477379)), 17) * 668265263;
            i += 4;
        }
        while (i < this.memSize) {
            iRotateLeft2 = Integer.rotateLeft(iRotateLeft2 + ((SafeUtils.readByte(this.memory, i) & UByte.MAX_VALUE) * 374761393), 11) * (-1640531535);
            i++;
        }
        int i2 = ((iRotateLeft2 >>> 15) ^ iRotateLeft2) * (-2048144777);
        int i3 = (i2 ^ (i2 >>> 13)) * (-1028477379);
        return i3 ^ (i3 >>> 16);
    }

    @Override // com.tds.xxhash.StreamingXXHash32
    public void update(byte[] bArr, int i, int i2) {
        SafeUtils.checkRange(bArr, i, i2);
        this.totalLen += (long) i2;
        if (this.memSize + i2 < 16) {
            System.arraycopy(bArr, i, this.memory, this.memSize, i2);
            this.memSize += i2;
            return;
        }
        int i3 = i2 + i;
        if (this.memSize > 0) {
            System.arraycopy(bArr, i, this.memory, this.memSize, 16 - this.memSize);
            this.v1 += SafeUtils.readIntLE(this.memory, 0) * (-2048144777);
            this.v1 = Integer.rotateLeft(this.v1, 13);
            this.v1 *= -1640531535;
            this.v2 += SafeUtils.readIntLE(this.memory, 4) * (-2048144777);
            this.v2 = Integer.rotateLeft(this.v2, 13);
            this.v2 *= -1640531535;
            this.v3 += SafeUtils.readIntLE(this.memory, 8) * (-2048144777);
            this.v3 = Integer.rotateLeft(this.v3, 13);
            this.v3 *= -1640531535;
            this.v4 += SafeUtils.readIntLE(this.memory, 12) * (-2048144777);
            this.v4 = Integer.rotateLeft(this.v4, 13);
            this.v4 *= -1640531535;
            i += 16 - this.memSize;
            this.memSize = 0;
        }
        int i4 = i3 - 16;
        int iRotateLeft = this.v1;
        int iRotateLeft2 = this.v2;
        int iRotateLeft3 = this.v3;
        int iRotateLeft4 = this.v4;
        while (i <= i4) {
            iRotateLeft = Integer.rotateLeft(iRotateLeft + (SafeUtils.readIntLE(bArr, i) * (-2048144777)), 13) * (-1640531535);
            int i5 = i + 4;
            iRotateLeft2 = Integer.rotateLeft(iRotateLeft2 + (SafeUtils.readIntLE(bArr, i5) * (-2048144777)), 13) * (-1640531535);
            int i6 = i5 + 4;
            iRotateLeft3 = Integer.rotateLeft(iRotateLeft3 + (SafeUtils.readIntLE(bArr, i6) * (-2048144777)), 13) * (-1640531535);
            int i7 = i6 + 4;
            iRotateLeft4 = Integer.rotateLeft(iRotateLeft4 + (SafeUtils.readIntLE(bArr, i7) * (-2048144777)), 13) * (-1640531535);
            i = i7 + 4;
        }
        this.v1 = iRotateLeft;
        this.v2 = iRotateLeft2;
        this.v3 = iRotateLeft3;
        this.v4 = iRotateLeft4;
        if (i < i3) {
            int i8 = i3 - i;
            System.arraycopy(bArr, i, this.memory, 0, i8);
            this.memSize = i8;
        }
    }
}
