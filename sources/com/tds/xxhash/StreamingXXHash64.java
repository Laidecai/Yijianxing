package com.tds.xxhash;

import java.io.Closeable;
import java.util.zip.Checksum;

/* JADX INFO: loaded from: classes.dex */
public abstract class StreamingXXHash64 implements Closeable {
    final long seed;

    interface Factory {
        StreamingXXHash64 newStreamingHash(long j);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public abstract long getValue();

    public abstract void reset();

    public abstract void update(byte[] bArr, int i, int i2);

    StreamingXXHash64(long j) {
        this.seed = j;
    }

    public String toString() {
        return getClass().getSimpleName() + "(seed=" + this.seed + ")";
    }

    public final Checksum asChecksum() {
        return new Checksum() { // from class: com.tds.xxhash.StreamingXXHash64.1
            @Override // java.util.zip.Checksum
            public long getValue() {
                return StreamingXXHash64.this.getValue();
            }

            @Override // java.util.zip.Checksum
            public void reset() {
                StreamingXXHash64.this.reset();
            }

            @Override // java.util.zip.Checksum
            public void update(int i) {
                StreamingXXHash64.this.update(new byte[]{(byte) i}, 0, 1);
            }

            @Override // java.util.zip.Checksum
            public void update(byte[] bArr, int i, int i2) {
                StreamingXXHash64.this.update(bArr, i, i2);
            }

            public String toString() {
                return StreamingXXHash64.this.toString();
            }
        };
    }
}
