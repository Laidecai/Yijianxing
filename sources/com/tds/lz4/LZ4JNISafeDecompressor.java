package com.tds.lz4;

import com.tds.util.ByteBufferUtils;
import com.tds.util.SafeUtils;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
final class LZ4JNISafeDecompressor extends LZ4SafeDecompressor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final LZ4JNISafeDecompressor INSTANCE = new LZ4JNISafeDecompressor();
    private static LZ4SafeDecompressor SAFE_INSTANCE;

    LZ4JNISafeDecompressor() {
    }

    @Override // com.tds.lz4.LZ4SafeDecompressor, com.tds.lz4.LZ4UnknownSizeDecompressor
    public final int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        SafeUtils.checkRange(bArr, i, i2);
        SafeUtils.checkRange(bArr2, i3, i4);
        int iLZ4_decompress_safe = LZ4JNI.LZ4_decompress_safe(bArr, null, i, i2, bArr2, null, i3, i4);
        if (iLZ4_decompress_safe >= 0) {
            return iLZ4_decompress_safe;
        }
        throw new LZ4Exception("Error decoding offset " + (i - iLZ4_decompress_safe) + " of input buffer");
    }

    @Override // com.tds.lz4.LZ4SafeDecompressor
    public int decompress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        ByteBuffer byteBuffer3;
        int iArrayOffset;
        byte[] bArr;
        ByteBuffer byteBuffer4;
        int iArrayOffset2;
        byte[] bArrArray;
        ByteBufferUtils.checkNotReadOnly(byteBuffer2);
        ByteBufferUtils.checkRange(byteBuffer, i, i2);
        ByteBufferUtils.checkRange(byteBuffer2, i3, i4);
        if ((byteBuffer.hasArray() || byteBuffer.isDirect()) && (byteBuffer2.hasArray() || byteBuffer2.isDirect())) {
            if (byteBuffer.hasArray()) {
                byte[] bArrArray2 = byteBuffer.array();
                iArrayOffset = byteBuffer.arrayOffset() + i;
                bArr = bArrArray2;
                byteBuffer3 = null;
            } else {
                byteBuffer3 = byteBuffer;
                iArrayOffset = i;
                bArr = null;
            }
            if (byteBuffer2.hasArray()) {
                bArrArray = byteBuffer2.array();
                byteBuffer4 = null;
                iArrayOffset2 = i3 + byteBuffer2.arrayOffset();
            } else {
                byteBuffer4 = byteBuffer2;
                iArrayOffset2 = i3;
                bArrArray = null;
            }
            int iLZ4_decompress_safe = LZ4JNI.LZ4_decompress_safe(bArr, byteBuffer3, iArrayOffset, i2, bArrArray, byteBuffer4, iArrayOffset2, i4);
            if (iLZ4_decompress_safe >= 0) {
                return iLZ4_decompress_safe;
            }
            throw new LZ4Exception("Error decoding offset " + (iArrayOffset - iLZ4_decompress_safe) + " of input buffer");
        }
        LZ4SafeDecompressor lZ4SafeDecompressorSafeDecompressor = SAFE_INSTANCE;
        if (lZ4SafeDecompressorSafeDecompressor == null) {
            lZ4SafeDecompressorSafeDecompressor = LZ4Factory.safeInstance().safeDecompressor();
            SAFE_INSTANCE = lZ4SafeDecompressorSafeDecompressor;
        }
        return lZ4SafeDecompressorSafeDecompressor.decompress(byteBuffer, i, i2, byteBuffer2, i3, i4);
    }
}
