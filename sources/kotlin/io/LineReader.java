package kotlin.io;

import com.tds.tapdb.b.g;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* JADX INFO: compiled from: Console.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\u0010\u0010#\u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lkotlin/io/LineReader;", "", "()V", "BUFFER_SIZE", "", "byteBuf", "Ljava/nio/ByteBuffer;", "bytes", "", "charBuf", "Ljava/nio/CharBuffer;", "chars", "", "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "compactBytes", "decode", "endOfInput", "decodeEndOfInput", "nBytes", "nChars", "readLine", "", "inputStream", "Ljava/io/InputStream;", g.R, "Ljava/nio/charset/Charset;", "resetAll", "", "trimStringBuilder", "updateCharset", "kotlin-stdlib"}, k = 1, mv = {1, 4, 1})
public final class LineReader {
    private static final int BUFFER_SIZE = 32;
    public static final LineReader INSTANCE = new LineReader();
    private static final ByteBuffer byteBuf;
    private static final byte[] bytes;
    private static final CharBuffer charBuf;
    private static final char[] chars;
    private static CharsetDecoder decoder;
    private static boolean directEOL;
    private static final StringBuilder sb;

    static {
        byte[] bArr = new byte[32];
        bytes = bArr;
        char[] cArr = new char[32];
        chars = cArr;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        Intrinsics.checkNotNullExpressionValue(byteBufferWrap, "ByteBuffer.wrap(bytes)");
        byteBuf = byteBufferWrap;
        CharBuffer charBufferWrap = CharBuffer.wrap(cArr);
        Intrinsics.checkNotNullExpressionValue(charBufferWrap, "CharBuffer.wrap(chars)");
        charBuf = charBufferWrap;
        sb = new StringBuilder();
    }

    private LineReader() {
    }

    public static final /* synthetic */ CharsetDecoder access$getDecoder$p(LineReader lineReader) {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        return charsetDecoder;
    }

    /* JADX INFO: renamed from: kotlin.io.LineReader$readLine$1, reason: invalid class name */
    /* JADX INFO: compiled from: Console.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 1})
    final /* synthetic */ class AnonymousClass1 extends MutablePropertyReference0Impl {
        AnonymousClass1(LineReader lineReader) {
            super(lineReader, LineReader.class, "decoder", "getDecoder()Ljava/nio/charset/CharsetDecoder;", 0);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
        public Object get() {
            return LineReader.access$getDecoder$p((LineReader) this.receiver);
        }

        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
        public void set(Object obj) {
            LineReader.decoder = (CharsetDecoder) obj;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x007c, code lost:
    
        if (r10 <= 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x007e, code lost:
    
        r0 = kotlin.io.LineReader.chars;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0084, code lost:
    
        if (r0[r10 - 1] != '\n') goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0086, code lost:
    
        r10 = r10 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0088, code lost:
    
        if (r10 <= 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0090, code lost:
    
        if (r0[r10 - 1] != '\r') goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0092, code lost:
    
        r10 = r10 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0094, code lost:
    
        r0 = kotlin.io.LineReader.sb;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009d, code lost:
    
        if (r0.length() != 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a0, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00a1, code lost:
    
        if (r1 == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ab, code lost:
    
        return new java.lang.String(kotlin.io.LineReader.chars, 0, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ac, code lost:
    
        r0.append(kotlin.io.LineReader.chars, 0, r10);
        r10 = r0.toString();
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, "sb.toString()");
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00be, code lost:
    
        if (r0.length() <= 32) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c0, code lost:
    
        trimStringBuilder();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00c3, code lost:
    
        r0.setLength(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c7, code lost:
    
        return r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022 A[Catch: all -> 0x00ce, TryCatch #0 {, blocks: (B:3:0x0001, B:6:0x0012, B:7:0x0017, B:11:0x0028, B:13:0x0033, B:23:0x0049, B:37:0x007e, B:39:0x0086, B:41:0x008a, B:43:0x0092, B:44:0x0094, B:49:0x00a3, B:52:0x00ac, B:54:0x00c0, B:55:0x00c3, B:24:0x004e, B:27:0x0059, B:31:0x0060, B:33:0x0070, B:35:0x0078, B:58:0x00c8, B:9:0x0022), top: B:63:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.lang.String readLine(java.io.InputStream r10, java.nio.charset.Charset r11) {
        /*
            Method dump skipped, instruction units count: 209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.LineReader.readLine(java.io.InputStream, java.nio.charset.Charset):java.lang.String");
    }

    private final int decode(boolean endOfInput) throws CharacterCodingException {
        while (true) {
            CharsetDecoder charsetDecoder = decoder;
            if (charsetDecoder == null) {
                Intrinsics.throwUninitializedPropertyAccessException("decoder");
            }
            ByteBuffer byteBuffer = byteBuf;
            CharBuffer charBuffer = charBuf;
            CoderResult coderResultDecode = charsetDecoder.decode(byteBuffer, charBuffer, endOfInput);
            Intrinsics.checkNotNullExpressionValue(coderResultDecode, "decoder.decode(byteBuf, charBuf, endOfInput)");
            if (coderResultDecode.isError()) {
                resetAll();
                coderResultDecode.throwException();
            }
            int iPosition = charBuffer.position();
            if (!coderResultDecode.isOverflow()) {
                return iPosition;
            }
            StringBuilder sb2 = sb;
            char[] cArr = chars;
            int i = iPosition - 1;
            sb2.append(cArr, 0, i);
            charBuffer.position(0);
            charBuffer.limit(32);
            charBuffer.put(cArr[i]);
        }
    }

    private final int compactBytes() {
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.compact();
        int iPosition = byteBuffer.position();
        byteBuffer.position(0);
        return iPosition;
    }

    private final int decodeEndOfInput(int nBytes, int nChars) throws CharacterCodingException {
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.limit(nBytes);
        charBuf.position(nChars);
        int iDecode = decode(true);
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        charsetDecoder.reset();
        byteBuffer.position(0);
        return iDecode;
    }

    private final void updateCharset(Charset charset) {
        CharsetDecoder charsetDecoderNewDecoder = charset.newDecoder();
        Intrinsics.checkNotNullExpressionValue(charsetDecoderNewDecoder, "charset.newDecoder()");
        decoder = charsetDecoderNewDecoder;
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.clear();
        CharBuffer charBuffer = charBuf;
        charBuffer.clear();
        byteBuffer.put((byte) 10);
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        boolean z = false;
        charsetDecoder.decode(byteBuffer, charBuffer, false);
        if (charBuffer.position() == 1 && charBuffer.get(0) == '\n') {
            z = true;
        }
        directEOL = z;
        resetAll();
    }

    private final void resetAll() {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        sb.setLength(0);
    }

    private final void trimStringBuilder() {
        StringBuilder sb2 = sb;
        sb2.setLength(32);
        sb2.trimToSize();
    }
}
