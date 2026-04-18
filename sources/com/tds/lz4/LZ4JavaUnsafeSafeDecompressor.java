package com.tds.lz4;

/* JADX INFO: loaded from: classes.dex */
final class LZ4JavaUnsafeSafeDecompressor extends LZ4SafeDecompressor {
    public static final LZ4SafeDecompressor INSTANCE = new LZ4JavaUnsafeSafeDecompressor();

    LZ4JavaUnsafeSafeDecompressor() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00c5, code lost:
    
        if (r12 > r5) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c9, code lost:
    
        if ((r6 + r9) != r1) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00cb, code lost:
    
        com.tds.lz4.LZ4UnsafeUtils.safeArraycopy(r18, r6, r21, r7, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00cf, code lost:
    
        return r12 - r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e4, code lost:
    
        throw new com.tds.lz4.LZ4Exception("Malformed input at " + r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ea, code lost:
    
        throw new com.tds.lz4.LZ4Exception();
     */
    @Override // com.tds.lz4.LZ4SafeDecompressor, com.tds.lz4.LZ4UnknownSizeDecompressor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int decompress(byte[] r18, int r19, int r20, byte[] r21, int r22, int r23) {
        /*
            Method dump skipped, instruction units count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.lz4.LZ4JavaUnsafeSafeDecompressor.decompress(byte[], int, int, byte[], int, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f7, code lost:
    
        if (r12 > r6) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00fb, code lost:
    
        if ((r0 + r9) != r3) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00fd, code lost:
    
        com.tds.lz4.LZ4ByteBufferUtils.safeArraycopy(r2, r0, r4, r7, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0101, code lost:
    
        return r12 - r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0116, code lost:
    
        throw new com.tds.lz4.LZ4Exception("Malformed input at " + r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x011c, code lost:
    
        throw new com.tds.lz4.LZ4Exception();
     */
    @Override // com.tds.lz4.LZ4SafeDecompressor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int decompress(java.nio.ByteBuffer r18, int r19, int r20, java.nio.ByteBuffer r21, int r22, int r23) {
        /*
            Method dump skipped, instruction units count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.lz4.LZ4JavaUnsafeSafeDecompressor.decompress(java.nio.ByteBuffer, int, int, java.nio.ByteBuffer, int, int):int");
    }
}
