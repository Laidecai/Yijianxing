package com.tds.lz4;

import com.tds.util.ByteBufferUtils;
import com.tds.util.SafeUtils;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
final class LZ4JNICompressor extends LZ4Compressor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final LZ4Compressor INSTANCE = new LZ4JNICompressor();
    private static LZ4Compressor SAFE_INSTANCE;

    LZ4JNICompressor() {
    }

    @Override // com.tds.lz4.LZ4Compressor
    public int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        SafeUtils.checkRange(bArr, i, i2);
        SafeUtils.checkRange(bArr2, i3, i4);
        int iLZ4_compress_limitedOutput = LZ4JNI.LZ4_compress_limitedOutput(bArr, null, i, i2, bArr2, null, i3, i4);
        if (iLZ4_compress_limitedOutput > 0) {
            return iLZ4_compress_limitedOutput;
        }
        throw new LZ4Exception("maxDestLen is too small");
    }

    @Override // com.tds.lz4.LZ4Compressor
    public int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        ByteBuffer byteBuffer3;
        int iArrayOffset;
        byte[] bArrArray;
        ByteBuffer byteBuffer4;
        int iArrayOffset2;
        byte[] bArrArray2;
        ByteBufferUtils.checkNotReadOnly(byteBuffer2);
        ByteBufferUtils.checkRange(byteBuffer, i, i2);
        ByteBufferUtils.checkRange(byteBuffer2, i3, i4);
        if ((byteBuffer.hasArray() || byteBuffer.isDirect()) && (byteBuffer2.hasArray() || byteBuffer2.isDirect())) {
            if (byteBuffer.hasArray()) {
                bArrArray = byteBuffer.array();
                byteBuffer3 = null;
                iArrayOffset = byteBuffer.arrayOffset() + i;
            } else {
                byteBuffer3 = byteBuffer;
                iArrayOffset = i;
                bArrArray = null;
            }
            if (byteBuffer2.hasArray()) {
                bArrArray2 = byteBuffer2.array();
                byteBuffer4 = null;
                iArrayOffset2 = i3 + byteBuffer2.arrayOffset();
            } else {
                byteBuffer4 = byteBuffer2;
                iArrayOffset2 = i3;
                bArrArray2 = null;
            }
            int iLZ4_compress_limitedOutput = LZ4JNI.LZ4_compress_limitedOutput(bArrArray, byteBuffer3, iArrayOffset, i2, bArrArray2, byteBuffer4, iArrayOffset2, i4);
            if (iLZ4_compress_limitedOutput > 0) {
                return iLZ4_compress_limitedOutput;
            }
            throw new LZ4Exception("maxDestLen is too small");
        }
        LZ4Compressor lZ4CompressorFastCompressor = SAFE_INSTANCE;
        if (lZ4CompressorFastCompressor == null) {
            lZ4CompressorFastCompressor = LZ4Factory.safeInstance().fastCompressor();
            SAFE_INSTANCE = lZ4CompressorFastCompressor;
        }
        return lZ4CompressorFastCompressor.compress(byteBuffer, i, i2, byteBuffer2, i3, i4);
    }
}
