package com.tds.xxhash;

import com.tds.xxhash.StreamingXXHash64;

/* JADX INFO: loaded from: classes.dex */
final class StreamingXXHash64JNI extends StreamingXXHash64 {
    private long state;

    static class Factory implements StreamingXXHash64.Factory {
        public static final StreamingXXHash64.Factory INSTANCE = new Factory();

        Factory() {
        }

        @Override // com.tds.xxhash.StreamingXXHash64.Factory
        public StreamingXXHash64 newStreamingHash(long j) {
            return new StreamingXXHash64JNI(j);
        }
    }

    StreamingXXHash64JNI(long j) {
        super(j);
        this.state = XXHashJNI.XXH64_init(j);
    }

    private void checkState() {
        if (this.state == 0) {
            throw new AssertionError("Already finalized");
        }
    }

    @Override // com.tds.xxhash.StreamingXXHash64
    public synchronized void reset() {
        checkState();
        XXHashJNI.XXH64_free(this.state);
        this.state = XXHashJNI.XXH64_init(this.seed);
    }

    @Override // com.tds.xxhash.StreamingXXHash64
    public synchronized long getValue() {
        checkState();
        return XXHashJNI.XXH64_digest(this.state);
    }

    @Override // com.tds.xxhash.StreamingXXHash64
    public synchronized void update(byte[] bArr, int i, int i2) {
        checkState();
        XXHashJNI.XXH64_update(this.state, bArr, i, i2);
    }

    @Override // com.tds.xxhash.StreamingXXHash64, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        if (this.state != 0) {
            super.close();
            XXHashJNI.XXH64_free(this.state);
            this.state = 0L;
        }
    }

    protected synchronized void finalize() throws Throwable {
        super.finalize();
        long j = this.state;
        if (j != 0) {
            XXHashJNI.XXH64_free(j);
            this.state = 0L;
        }
    }
}
