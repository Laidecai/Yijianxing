package com.tds.lz4;

import com.tds.util.SafeUtils;
import com.tds.xxhash.XXHashFactory;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Checksum;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class LZ4BlockInputStream extends FilterInputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private byte[] buffer;
    private final Checksum checksum;
    private byte[] compressedBuffer;
    private final LZ4FastDecompressor decompressor;
    private boolean finished;
    private int o;
    private int originalLen;
    private final boolean stopOnEmptyBlock;

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    public LZ4BlockInputStream(InputStream inputStream, LZ4FastDecompressor lZ4FastDecompressor, Checksum checksum, boolean z) {
        super(inputStream);
        this.decompressor = lZ4FastDecompressor;
        this.checksum = checksum;
        this.stopOnEmptyBlock = z;
        this.buffer = new byte[0];
        this.compressedBuffer = new byte[LZ4BlockOutputStream.HEADER_LENGTH];
        this.originalLen = 0;
        this.o = 0;
        this.finished = false;
    }

    public LZ4BlockInputStream(InputStream inputStream, LZ4FastDecompressor lZ4FastDecompressor, Checksum checksum) {
        this(inputStream, lZ4FastDecompressor, checksum, true);
    }

    public LZ4BlockInputStream(InputStream inputStream, LZ4FastDecompressor lZ4FastDecompressor) {
        this(inputStream, lZ4FastDecompressor, XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum(), true);
    }

    public LZ4BlockInputStream(InputStream inputStream, boolean z) {
        this(inputStream, LZ4Factory.fastestInstance().fastDecompressor(), XXHashFactory.fastestInstance().newStreamingHash32(-1756908916).asChecksum(), z);
    }

    public LZ4BlockInputStream(InputStream inputStream) {
        this(inputStream, LZ4Factory.fastestInstance().fastDecompressor());
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        return this.originalLen - this.o;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (this.finished) {
            return -1;
        }
        if (this.o == this.originalLen) {
            refill();
        }
        if (this.finished) {
            return -1;
        }
        byte[] bArr = this.buffer;
        int i = this.o;
        this.o = i + 1;
        return bArr[i] & UByte.MAX_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        SafeUtils.checkRange(bArr, i, i2);
        if (this.finished) {
            return -1;
        }
        if (this.o == this.originalLen) {
            refill();
        }
        if (this.finished) {
            return -1;
        }
        int iMin = Math.min(i2, this.originalLen - this.o);
        System.arraycopy(this.buffer, this.o, bArr, i, iMin);
        this.o += iMin;
        return iMin;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        if (j <= 0 || this.finished) {
            return 0L;
        }
        if (this.o == this.originalLen) {
            refill();
        }
        if (this.finished) {
            return 0L;
        }
        int iMin = (int) Math.min(j, this.originalLen - this.o);
        this.o += iMin;
        return iMin;
    }

    private void refill() throws IOException {
        if (!tryReadFully(this.compressedBuffer, LZ4BlockOutputStream.HEADER_LENGTH)) {
            if (!this.stopOnEmptyBlock) {
                this.finished = true;
                return;
            }
            throw new EOFException("Stream ended prematurely");
        }
        for (int i = 0; i < LZ4BlockOutputStream.MAGIC_LENGTH; i++) {
            if (this.compressedBuffer[i] != LZ4BlockOutputStream.MAGIC[i]) {
                throw new IOException("Stream is corrupted");
            }
        }
        int i2 = this.compressedBuffer[LZ4BlockOutputStream.MAGIC_LENGTH] & UByte.MAX_VALUE;
        int i3 = i2 & 240;
        int i4 = (i2 & 15) + 10;
        if (i3 != 16 && i3 != 32) {
            throw new IOException("Stream is corrupted");
        }
        int intLE = SafeUtils.readIntLE(this.compressedBuffer, LZ4BlockOutputStream.MAGIC_LENGTH + 1);
        this.originalLen = SafeUtils.readIntLE(this.compressedBuffer, LZ4BlockOutputStream.MAGIC_LENGTH + 5);
        int intLE2 = SafeUtils.readIntLE(this.compressedBuffer, LZ4BlockOutputStream.MAGIC_LENGTH + 9);
        int i5 = this.originalLen;
        if (i5 > (1 << i4) || i5 < 0 || intLE < 0 || ((i5 == 0 && intLE != 0) || ((i5 != 0 && intLE == 0) || (i3 == 16 && i5 != intLE)))) {
            throw new IOException("Stream is corrupted");
        }
        if (i5 == 0 && intLE == 0) {
            if (intLE2 != 0) {
                throw new IOException("Stream is corrupted");
            }
            if (!this.stopOnEmptyBlock) {
                refill();
                return;
            } else {
                this.finished = true;
                return;
            }
        }
        byte[] bArr = this.buffer;
        if (bArr.length < i5) {
            this.buffer = new byte[Math.max(i5, (bArr.length * 3) / 2)];
        }
        if (i3 == 16) {
            readFully(this.buffer, this.originalLen);
        } else if (i3 == 32) {
            byte[] bArr2 = this.compressedBuffer;
            if (bArr2.length < intLE) {
                this.compressedBuffer = new byte[Math.max(intLE, (bArr2.length * 3) / 2)];
            }
            readFully(this.compressedBuffer, intLE);
            try {
                if (intLE != this.decompressor.decompress(this.compressedBuffer, 0, this.buffer, 0, this.originalLen)) {
                    throw new IOException("Stream is corrupted");
                }
            } catch (LZ4Exception e) {
                throw new IOException("Stream is corrupted", e);
            }
        } else {
            throw new AssertionError();
        }
        this.checksum.reset();
        this.checksum.update(this.buffer, 0, this.originalLen);
        if (((int) this.checksum.getValue()) != intLE2) {
            throw new IOException("Stream is corrupted");
        }
        this.o = 0;
    }

    private boolean tryReadFully(byte[] bArr, int i) throws IOException {
        int i2 = 0;
        while (i2 < i) {
            int i3 = this.in.read(bArr, i2, i - i2);
            if (i3 < 0) {
                return false;
            }
            i2 += i3;
        }
        return true;
    }

    private void readFully(byte[] bArr, int i) throws IOException {
        if (!tryReadFully(bArr, i)) {
            throw new EOFException("Stream ended prematurely");
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    public String toString() {
        return getClass().getSimpleName() + "(in=" + this.in + ", decompressor=" + this.decompressor + ", checksum=" + this.checksum + ")";
    }
}
