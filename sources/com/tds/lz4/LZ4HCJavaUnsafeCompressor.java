package com.tds.lz4;

import com.tds.lz4.LZ4Utils;
import com.tds.util.ByteBufferUtils;
import com.tds.util.UnsafeUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;
import kotlin.UShort;

/* JADX INFO: loaded from: classes.dex */
final class LZ4HCJavaUnsafeCompressor extends LZ4Compressor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final LZ4Compressor INSTANCE = new LZ4HCJavaUnsafeCompressor();
    final int compressionLevel;
    private final int maxAttempts;

    LZ4HCJavaUnsafeCompressor() {
        this(9);
    }

    LZ4HCJavaUnsafeCompressor(int i) {
        this.maxAttempts = 1 << (i - 1);
        this.compressionLevel = i;
    }

    private class HashTable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int MASK = 65535;
        private final int base;
        private final short[] chainTable;
        private final int[] hashTable;
        int nextToUpdate;

        HashTable(int i) {
            this.base = i;
            this.nextToUpdate = i;
            int[] iArr = new int[32768];
            this.hashTable = iArr;
            Arrays.fill(iArr, -1);
            this.chainTable = new short[65536];
        }

        private int hashPointer(byte[] bArr, int i) {
            return hashPointer(UnsafeUtils.readInt(bArr, i));
        }

        private int hashPointer(ByteBuffer byteBuffer, int i) {
            return hashPointer(ByteBufferUtils.readInt(byteBuffer, i));
        }

        private int hashPointer(int i) {
            return this.hashTable[LZ4Utils.hashHC(i)];
        }

        private int next(int i) {
            return i - (this.chainTable[i & 65535] & UShort.MAX_VALUE);
        }

        private void addHash(byte[] bArr, int i) {
            addHash(UnsafeUtils.readInt(bArr, i), i);
        }

        private void addHash(ByteBuffer byteBuffer, int i) {
            addHash(ByteBufferUtils.readInt(byteBuffer, i), i);
        }

        private void addHash(int i, int i2) {
            int iHashHC = LZ4Utils.hashHC(i);
            int[] iArr = this.hashTable;
            int i3 = i2 - iArr[iHashHC];
            if (i3 >= 65536) {
                i3 = 65535;
            }
            this.chainTable[65535 & i2] = (short) i3;
            iArr[iHashHC] = i2;
        }

        void insert(int i, byte[] bArr) {
            while (true) {
                int i2 = this.nextToUpdate;
                if (i2 >= i) {
                    return;
                }
                addHash(bArr, i2);
                this.nextToUpdate++;
            }
        }

        void insert(int i, ByteBuffer byteBuffer) {
            while (true) {
                int i2 = this.nextToUpdate;
                if (i2 >= i) {
                    return;
                }
                addHash(byteBuffer, i2);
                this.nextToUpdate++;
            }
        }

        boolean insertAndFindBestMatch(byte[] bArr, int i, int i2, LZ4Utils.Match match) {
            int i3;
            int iCommonBytes;
            int iCommonBytes2;
            match.start = i;
            match.len = 0;
            insert(i, bArr);
            int iHashPointer = hashPointer(bArr, i);
            if (iHashPointer < i - 4 || iHashPointer > i || iHashPointer < this.base) {
                i3 = 0;
                iCommonBytes = 0;
            } else {
                if (LZ4UnsafeUtils.readIntEquals(bArr, iHashPointer, i)) {
                    i3 = i - iHashPointer;
                    iCommonBytes = LZ4UnsafeUtils.commonBytes(bArr, iHashPointer + 4, i + 4, i2) + 4;
                    match.len = iCommonBytes;
                    match.ref = iHashPointer;
                } else {
                    i3 = 0;
                    iCommonBytes = 0;
                }
                iHashPointer = next(iHashPointer);
            }
            for (int i4 = 0; i4 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i4++) {
                if (LZ4UnsafeUtils.readIntEquals(bArr, iHashPointer, i) && (iCommonBytes2 = LZ4UnsafeUtils.commonBytes(bArr, iHashPointer + 4, i + 4, i2) + 4) > match.len) {
                    match.ref = iHashPointer;
                    match.len = iCommonBytes2;
                }
                iHashPointer = next(iHashPointer);
            }
            if (iCommonBytes != 0) {
                int i5 = (iCommonBytes + i) - 3;
                while (i < i5 - i3) {
                    this.chainTable[65535 & i] = (short) i3;
                    i++;
                }
                do {
                    this.chainTable[i & 65535] = (short) i3;
                    this.hashTable[LZ4Utils.hashHC(UnsafeUtils.readInt(bArr, i))] = i;
                    i++;
                } while (i < i5);
                this.nextToUpdate = i5;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(byte[] bArr, int i, int i2, int i3, int i4, LZ4Utils.Match match) {
            match.len = i4;
            insert(i, bArr);
            int iHashPointer = hashPointer(bArr, i);
            for (int i5 = 0; i5 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i5++) {
                if (LZ4UnsafeUtils.readIntEquals(bArr, iHashPointer, i)) {
                    int iCommonBytes = LZ4UnsafeUtils.commonBytes(bArr, iHashPointer + 4, i + 4, i3) + 4;
                    int iCommonBytesBackward = LZ4UnsafeUtils.commonBytesBackward(bArr, iHashPointer, i, this.base, i2);
                    int i6 = iCommonBytes + iCommonBytesBackward;
                    if (i6 > match.len) {
                        match.len = i6;
                        match.ref = iHashPointer - iCommonBytesBackward;
                        match.start = i - iCommonBytesBackward;
                    }
                }
                iHashPointer = next(iHashPointer);
            }
            return match.len > i4;
        }

        boolean insertAndFindBestMatch(ByteBuffer byteBuffer, int i, int i2, LZ4Utils.Match match) {
            int i3;
            int iCommonBytes;
            int iCommonBytes2;
            match.start = i;
            match.len = 0;
            insert(i, byteBuffer);
            int iHashPointer = hashPointer(byteBuffer, i);
            if (iHashPointer < i - 4 || iHashPointer > i || iHashPointer < this.base) {
                i3 = 0;
                iCommonBytes = 0;
            } else {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, iHashPointer, i)) {
                    i3 = i - iHashPointer;
                    iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, iHashPointer + 4, i + 4, i2) + 4;
                    match.len = iCommonBytes;
                    match.ref = iHashPointer;
                } else {
                    i3 = 0;
                    iCommonBytes = 0;
                }
                iHashPointer = next(iHashPointer);
            }
            for (int i4 = 0; i4 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i4++) {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, iHashPointer, i) && (iCommonBytes2 = LZ4ByteBufferUtils.commonBytes(byteBuffer, iHashPointer + 4, i + 4, i2) + 4) > match.len) {
                    match.ref = iHashPointer;
                    match.len = iCommonBytes2;
                }
                iHashPointer = next(iHashPointer);
            }
            if (iCommonBytes != 0) {
                int i5 = (iCommonBytes + i) - 3;
                while (i < i5 - i3) {
                    this.chainTable[65535 & i] = (short) i3;
                    i++;
                }
                do {
                    this.chainTable[i & 65535] = (short) i3;
                    this.hashTable[LZ4Utils.hashHC(ByteBufferUtils.readInt(byteBuffer, i))] = i;
                    i++;
                } while (i < i5);
                this.nextToUpdate = i5;
            }
            return match.len != 0;
        }

        boolean insertAndFindWiderMatch(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, LZ4Utils.Match match) {
            match.len = i4;
            insert(i, byteBuffer);
            int iHashPointer = hashPointer(byteBuffer, i);
            for (int i5 = 0; i5 < LZ4HCJavaUnsafeCompressor.this.maxAttempts && iHashPointer >= Math.max(this.base, (i - 65536) + 1) && iHashPointer <= i; i5++) {
                if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, iHashPointer, i)) {
                    int iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, iHashPointer + 4, i + 4, i3) + 4;
                    int iCommonBytesBackward = LZ4ByteBufferUtils.commonBytesBackward(byteBuffer, iHashPointer, i, this.base, i2);
                    int i6 = iCommonBytes + iCommonBytesBackward;
                    if (i6 > match.len) {
                        match.len = i6;
                        match.ref = iHashPointer - iCommonBytesBackward;
                        match.start = i - iCommonBytesBackward;
                    }
                }
                iHashPointer = next(iHashPointer);
            }
            return match.len > i4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x0202, code lost:
    
        r3 = r16;
        r16 = r9;
        r6 = com.tds.lz4.LZ4UnsafeUtils.encodeSequence(r22, r3, r9.start, r9.ref, r9.len, r25, r17, r10);
        r3 = r16.end();
        r8 = r0;
        r2 = r3;
        r7 = r11;
        r9 = r16;
     */
    @Override // com.tds.lz4.LZ4Compressor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int compress(byte[] r22, int r23, int r24, byte[] r25, int r26, int r27) {
        /*
            Method dump skipped, instruction units count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.lz4.LZ4HCJavaUnsafeCompressor.compress(byte[], int, int, byte[], int, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x0260, code lost:
    
        r0 = com.tds.lz4.LZ4ByteBufferUtils.encodeSequence(r10, r0, r1.start, r1.ref, r1.len, r11, r16, r13);
        r3 = r1.end();
     */
    @Override // com.tds.lz4.LZ4Compressor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int compress(java.nio.ByteBuffer r22, int r23, int r24, java.nio.ByteBuffer r25, int r26, int r27) {
        /*
            Method dump skipped, instruction units count: 661
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.lz4.LZ4HCJavaUnsafeCompressor.compress(java.nio.ByteBuffer, int, int, java.nio.ByteBuffer, int, int):int");
    }
}
