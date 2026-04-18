package com.tds.xxhash;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class XXHash32 {
    public abstract int hash(ByteBuffer byteBuffer, int i, int i2, int i3);

    public abstract int hash(byte[] bArr, int i, int i2, int i3);

    public final int hash(ByteBuffer byteBuffer, int i) {
        int iHash = hash(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), i);
        byteBuffer.position(byteBuffer.limit());
        return iHash;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
