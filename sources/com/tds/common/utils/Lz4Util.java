package com.tds.common.utils;

import com.tds.lz4.LZ4Factory;

/* JADX INFO: loaded from: classes.dex */
public class Lz4Util {
    public static byte[] compressedByte(byte[] bArr) {
        return LZ4Factory.fastestInstance().fastCompressor().compress(bArr);
    }

    public static byte[] decompressorByte(byte[] bArr, int i) {
        return LZ4Factory.fastestInstance().fastDecompressor().decompress(bArr, i);
    }
}
