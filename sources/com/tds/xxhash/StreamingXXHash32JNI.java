package com.tds.xxhash;

import com.tds.xxhash.StreamingXXHash32;

/* JADX INFO: loaded from: classes.dex */
final class StreamingXXHash32JNI extends StreamingXXHash32 {
    private long state;

    static class Factory implements StreamingXXHash32.Factory {
        public static final StreamingXXHash32.Factory INSTANCE = new Factory();

        Factory() {
        }

        @Override // com.tds.xxhash.StreamingXXHash32.Factory
        public StreamingXXHash32 newStreamingHash(int i) {
            return new StreamingXXHash32JNI(i);
        }
    }

    StreamingXXHash32JNI(int i) {
        super(i);
        this.state = XXHashJNI.XXH32_init(i);
    }

    private void checkState() {
        if (this.state == 0) {
            throw new AssertionError("Already finalized");
        }
    }

    @Override // com.tds.xxhash.StreamingXXHash32
    public synchronized void reset() {
        checkState();
        XXHashJNI.XXH32_free(this.state);
        this.state = XXHashJNI.XXH32_init(this.seed);
    }

    @Override // com.tds.xxhash.StreamingXXHash32
    public synchronized int getValue() {
        checkState();
        return XXHashJNI.XXH32_digest(this.state);
    }

    @Override // com.tds.xxhash.StreamingXXHash32
    public synchronized void update(byte[] bArr, int i, int i2) {
        checkState();
        XXHashJNI.XXH32_update(this.state, bArr, i, i2);
    }

    @Override // com.tds.xxhash.StreamingXXHash32, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        if (this.state != 0) {
            super.close();
            XXHashJNI.XXH32_free(this.state);
            this.state = 0L;
        }
    }

    protected synchronized void finalize() throws Throwable {
        super.finalize();
        long j = this.state;
        if (j != 0) {
            XXHashJNI.XXH32_free(j);
            this.state = 0L;
        }
    }
}
