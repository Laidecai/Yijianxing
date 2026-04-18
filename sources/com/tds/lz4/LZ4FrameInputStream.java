package com.tds.lz4;

import com.tds.lz4.LZ4FrameOutputStream;
import com.tds.xxhash.XXHash32;
import com.tds.xxhash.XXHashFactory;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;
import kotlin.UByte;
import kotlin.jvm.internal.IntCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class LZ4FrameInputStream extends FilterInputStream {
    static final String BLOCK_HASH_MISMATCH = "Block checksum mismatch";
    static final String DESCRIPTOR_HASH_MISMATCH = "Stream frame descriptor corrupted";
    static final int MAGIC_SKIPPABLE_BASE = 407710288;
    static final String NOT_SUPPORTED = "Stream unsupported";
    static final String PREMATURE_EOS = "Stream ended prematurely";
    private ByteBuffer buffer;
    private final XXHash32 checksum;
    private byte[] compressedBuffer;
    private final LZ4SafeDecompressor decompressor;
    private long expectedContentSize;
    private LZ4FrameOutputStream.FrameInfo frameInfo;
    private final byte[] headerArray;
    private final ByteBuffer headerBuffer;
    private int maxBlockSize;
    private byte[] rawBuffer;
    private final ByteBuffer readNumberBuff;
    private final boolean readSingleFrame;
    private long totalContentSize;

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    public LZ4FrameInputStream(InputStream inputStream) throws IOException {
        this(inputStream, LZ4Factory.fastestInstance().safeDecompressor(), XXHashFactory.fastestInstance().hash32());
    }

    public LZ4FrameInputStream(InputStream inputStream, boolean z) throws IOException {
        this(inputStream, LZ4Factory.fastestInstance().safeDecompressor(), XXHashFactory.fastestInstance().hash32(), z);
    }

    public LZ4FrameInputStream(InputStream inputStream, LZ4SafeDecompressor lZ4SafeDecompressor, XXHash32 xXHash32) throws IOException {
        this(inputStream, lZ4SafeDecompressor, xXHash32, false);
    }

    public LZ4FrameInputStream(InputStream inputStream, LZ4SafeDecompressor lZ4SafeDecompressor, XXHash32 xXHash32, boolean z) throws IOException {
        super(inputStream);
        byte[] bArr = new byte[15];
        this.headerArray = bArr;
        this.headerBuffer = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        this.buffer = null;
        this.rawBuffer = null;
        this.maxBlockSize = -1;
        this.expectedContentSize = -1L;
        this.totalContentSize = 0L;
        this.frameInfo = null;
        this.readNumberBuff = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
        this.decompressor = lZ4SafeDecompressor;
        this.checksum = xXHash32;
        this.readSingleFrame = z;
        nextFrameInfo();
    }

    private boolean nextFrameInfo() throws IOException {
        while (true) {
            int i = 0;
            do {
                int i2 = this.in.read(this.readNumberBuff.array(), i, 4 - i);
                if (i2 < 0) {
                    return false;
                }
                i += i2;
            } while (i < 4);
            int i3 = this.readNumberBuff.getInt(0);
            if (i3 == 407708164) {
                readHeader();
                return true;
            }
            if ((i3 >>> 4) == 25481893) {
                skippableFrame();
            } else {
                throw new IOException(NOT_SUPPORTED);
            }
        }
    }

    private void skippableFrame() throws IOException {
        int i = readInt(this.in);
        byte[] bArr = new byte[1024];
        while (i > 0) {
            int i2 = this.in.read(bArr, 0, Math.min(i, 1024));
            if (i2 < 0) {
                throw new IOException(PREMATURE_EOS);
            }
            i -= i2;
        }
    }

    private void readHeader() throws IOException {
        this.headerBuffer.rewind();
        int i = this.in.read();
        if (i < 0) {
            throw new IOException(PREMATURE_EOS);
        }
        int i2 = this.in.read();
        if (i2 < 0) {
            throw new IOException(PREMATURE_EOS);
        }
        byte b = (byte) (i & 255);
        LZ4FrameOutputStream.FLG flgFromByte = LZ4FrameOutputStream.FLG.fromByte(b);
        this.headerBuffer.put(b);
        byte b2 = (byte) (i2 & 255);
        LZ4FrameOutputStream.BD bdFromByte = LZ4FrameOutputStream.BD.fromByte(b2);
        this.headerBuffer.put(b2);
        this.frameInfo = new LZ4FrameOutputStream.FrameInfo(flgFromByte, bdFromByte);
        if (flgFromByte.isEnabled(LZ4FrameOutputStream.FLG.Bits.CONTENT_SIZE)) {
            long j = readLong(this.in);
            this.expectedContentSize = j;
            this.headerBuffer.putLong(j);
        }
        this.totalContentSize = 0L;
        byte bHash = (byte) ((this.checksum.hash(this.headerArray, 0, this.headerBuffer.position(), 0) >> 8) & 255);
        int i3 = this.in.read();
        if (i3 < 0) {
            throw new IOException(PREMATURE_EOS);
        }
        if (bHash != ((byte) (i3 & 255))) {
            throw new IOException(DESCRIPTOR_HASH_MISMATCH);
        }
        int blockMaximumSize = this.frameInfo.getBD().getBlockMaximumSize();
        this.maxBlockSize = blockMaximumSize;
        this.compressedBuffer = new byte[blockMaximumSize];
        byte[] bArr = new byte[blockMaximumSize];
        this.rawBuffer = bArr;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        this.buffer = byteBufferWrap;
        byteBufferWrap.limit(0);
    }

    private long readLong(InputStream inputStream) throws IOException {
        int i = 0;
        do {
            int i2 = inputStream.read(this.readNumberBuff.array(), i, 8 - i);
            if (i2 < 0) {
                throw new IOException(PREMATURE_EOS);
            }
            i += i2;
        } while (i < 8);
        return this.readNumberBuff.getLong(0);
    }

    private int readInt(InputStream inputStream) throws IOException {
        int i = 0;
        do {
            int i2 = inputStream.read(this.readNumberBuff.array(), i, 4 - i);
            if (i2 < 0) {
                throw new IOException(PREMATURE_EOS);
            }
            i += i2;
        } while (i < 4);
        return this.readNumberBuff.getInt(0);
    }

    private void readBlock() throws IOException {
        byte[] bArr;
        int i = readInt(this.in);
        boolean z = (Integer.MIN_VALUE & i) == 0;
        int iDecompress = i & IntCompanionObject.MAX_VALUE;
        if (iDecompress == 0) {
            if (this.frameInfo.isEnabled(LZ4FrameOutputStream.FLG.Bits.CONTENT_CHECKSUM) && readInt(this.in) != this.frameInfo.currentStreamHash()) {
                throw new IOException("Content checksum mismatch");
            }
            if (this.frameInfo.isEnabled(LZ4FrameOutputStream.FLG.Bits.CONTENT_SIZE) && this.expectedContentSize != this.totalContentSize) {
                throw new IOException("Size check mismatch");
            }
            this.frameInfo.finish();
            return;
        }
        if (z) {
            bArr = this.compressedBuffer;
        } else {
            bArr = this.rawBuffer;
        }
        byte[] bArr2 = bArr;
        if (iDecompress > this.maxBlockSize) {
            throw new IOException(String.format(Locale.ROOT, "Block size %s exceeded max: %s", Integer.valueOf(iDecompress), Integer.valueOf(this.maxBlockSize)));
        }
        int i2 = 0;
        while (i2 < iDecompress) {
            int i3 = this.in.read(bArr2, i2, iDecompress - i2);
            if (i3 < 0) {
                throw new IOException(PREMATURE_EOS);
            }
            i2 += i3;
        }
        if (this.frameInfo.isEnabled(LZ4FrameOutputStream.FLG.Bits.BLOCK_CHECKSUM) && readInt(this.in) != this.checksum.hash(bArr2, 0, iDecompress, 0)) {
            throw new IOException(BLOCK_HASH_MISMATCH);
        }
        if (z) {
            try {
                LZ4SafeDecompressor lZ4SafeDecompressor = this.decompressor;
                byte[] bArr3 = this.rawBuffer;
                iDecompress = lZ4SafeDecompressor.decompress(bArr2, 0, iDecompress, bArr3, 0, bArr3.length);
            } catch (LZ4Exception e) {
                throw new IOException(e);
            }
        }
        if (this.frameInfo.isEnabled(LZ4FrameOutputStream.FLG.Bits.CONTENT_CHECKSUM)) {
            this.frameInfo.updateStreamHash(this.rawBuffer, 0, iDecompress);
        }
        this.totalContentSize += (long) iDecompress;
        this.buffer.limit(iDecompress);
        this.buffer.rewind();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        while (this.buffer.remaining() == 0) {
            if (this.frameInfo.isFinished() && (this.readSingleFrame || !nextFrameInfo())) {
                return -1;
            }
            readBlock();
        }
        return this.buffer.get() & UByte.MAX_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        while (this.buffer.remaining() == 0) {
            if (this.frameInfo.isFinished() && (this.readSingleFrame || !nextFrameInfo())) {
                return -1;
            }
            readBlock();
        }
        int iMin = Math.min(i2, this.buffer.remaining());
        this.buffer.get(bArr, i, iMin);
        return iMin;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        if (j <= 0) {
            return 0L;
        }
        while (this.buffer.remaining() == 0) {
            if (this.frameInfo.isFinished() && (this.readSingleFrame || !nextFrameInfo())) {
                return 0L;
            }
            readBlock();
        }
        long jMin = Math.min(j, this.buffer.remaining());
        ByteBuffer byteBuffer = this.buffer;
        byteBuffer.position(byteBuffer.position() + ((int) jMin));
        return jMin;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        return this.buffer.remaining();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i) {
        throw new UnsupportedOperationException("mark not supported");
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException("reset not supported");
    }

    public long getExpectedContentSize() {
        if (!this.readSingleFrame) {
            throw new UnsupportedOperationException("Operation not permitted when multiple frames can be read");
        }
        return this.expectedContentSize;
    }

    public boolean isExpectedContentSizeDefined() {
        return this.readSingleFrame && this.expectedContentSize >= 0;
    }
}
