package com.tds.xxhash;

import java.io.Closeable;
import java.util.zip.Checksum;

/* JADX INFO: loaded from: classes.dex */
public abstract class StreamingXXHash32 implements Closeable {
    final int seed;

    interface Factory {
        StreamingXXHash32 newStreamingHash(int i);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public abstract int getValue();

    public abstract void reset();

    public abstract void update(byte[] bArr, int i, int i2);

    StreamingXXHash32(int i) {
        this.seed = i;
    }

    public String toString() {
        return getClass().getSimpleName() + "(seed=" + this.seed + ")";
    }

    public final Checksum asChecksum() {
        return new Checksum() { // from class: com.tds.xxhash.StreamingXXHash32.1
            @Override // java.util.zip.Checksum
            public long getValue() {
                return ((long) StreamingXXHash32.this.getValue()) & 268435455;
            }

            @Override // java.util.zip.Checksum
            public void reset() {
                StreamingXXHash32.this.reset();
            }

            @Override // java.util.zip.Checksum
            public void update(int i) {
                StreamingXXHash32.this.update(new byte[]{(byte) i}, 0, 1);
            }

            @Override // java.util.zip.Checksum
            public void update(byte[] bArr, int i, int i2) {
                StreamingXXHash32.this.update(bArr, i, i2);
            }

            public String toString() {
                return StreamingXXHash32.this.toString();
            }
        };
    }
}
