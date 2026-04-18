package com.ta.utdid2.b.a;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import kotlin.text.Typography;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes.dex */
public class a implements XmlSerializer {
    public static String a = "xmlpull.org/v1/doc/features.html#indent-output";

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public static final String[] f5a = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null};

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public OutputStream f6a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public Writer f7a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public CharsetEncoder f9a;
    public boolean e;
    public int mPos;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public final char[] f10a = new char[8192];

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public ByteBuffer f8a = ByteBuffer.allocate(8192);

    private void a(String str, int i, int i2) throws IOException {
        if (i2 > 8192) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = i + 8192;
                a(str, i, i4 < i3 ? 8192 : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + i2 > 8192) {
            flush();
            i5 = this.mPos;
        }
        str.getChars(i, i + i2, this.f10a, i5);
        this.mPos = i5 + i2;
    }

    private void append(char c) throws IOException {
        int i = this.mPos;
        if (i >= 8191) {
            flush();
            i = this.mPos;
        }
        this.f10a[i] = c;
        this.mPos = i + 1;
    }

    public static String d() {
        return "http://" + a;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IllegalStateException, IOException, IllegalArgumentException {
        append(' ');
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        append("=\"");
        a(str3);
        append(Typography.quote);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IllegalStateException, IOException, IllegalArgumentException {
        flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.e) {
            append(" />\n");
        } else {
            append("</");
            if (str != null) {
                append(str);
                append(':');
            }
            append(str2);
            append(">\n");
        }
        this.e = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        int i = this.mPos;
        if (i > 0) {
            if (this.f6a != null) {
                CharBuffer charBufferWrap = CharBuffer.wrap(this.f10a, 0, i);
                CoderResult coderResultEncode = this.f9a.encode(charBufferWrap, this.f8a, true);
                while (!coderResultEncode.isError()) {
                    if (coderResultEncode.isOverflow()) {
                        a();
                        coderResultEncode = this.f9a.encode(charBufferWrap, this.f8a, true);
                    } else {
                        a();
                        this.f6a.flush();
                    }
                }
                throw new IOException(coderResultEncode.toString());
            }
            this.f7a.write(this.f10a, 0, i);
            this.f7a.flush();
            this.mPos = 0;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z) throws IllegalStateException, IllegalArgumentException {
        if (!str.equals(d())) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IllegalStateException, IOException, IllegalArgumentException {
        if (outputStream == null) {
            throw new IllegalArgumentException();
        }
        try {
            this.f9a = Charset.forName(str).newEncoder();
            this.f6a = outputStream;
        } catch (IllegalCharsetNameException e) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e));
        } catch (UnsupportedCharsetException e2) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e2));
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) throws IllegalStateException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IllegalStateException, IOException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='utf-8' standalone='");
        sb.append(bool.booleanValue() ? "yes" : "no");
        sb.append("' ?>\n");
        append(sb.toString());
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.e) {
            append(">\n");
        }
        append(Typography.less);
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        this.e = true;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i, int i2) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.e) {
            append(">");
            this.e = false;
        }
        a(cArr, i, i2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.e) {
            append(">");
            this.e = false;
        }
        a(str);
        return this;
    }

    private void append(char[] cArr, int i, int i2) throws IOException {
        if (i2 > 8192) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = i + 8192;
                append(cArr, i, i4 < i3 ? 8192 : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + i2 > 8192) {
            flush();
            i5 = this.mPos;
        }
        System.arraycopy(cArr, i, this.f10a, i5, i2);
        this.mPos = i5 + i2;
    }

    private void a(String str) throws IOException {
        String str2;
        int length = str.length();
        String[] strArr = f5a;
        char length2 = (char) strArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt < length2 && (str2 = strArr[cCharAt]) != null) {
                if (i2 < i) {
                    a(str, i2, i - i2);
                }
                i2 = i + 1;
                append(str2);
            }
            i++;
        }
        if (i2 < i) {
            a(str, i2, i - i2);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) throws IllegalStateException, IOException, IllegalArgumentException {
        this.f7a = writer;
    }

    private void append(String str) throws IOException {
        a(str, 0, str.length());
    }

    private void a(char[] cArr, int i, int i2) throws IOException {
        String str;
        String[] strArr = f5a;
        char length = (char) strArr.length;
        int i3 = i2 + i;
        int i4 = i;
        while (i < i3) {
            char c = cArr[i];
            if (c < length && (str = strArr[c]) != null) {
                if (i4 < i) {
                    append(cArr, i4, i - i4);
                }
                i4 = i + 1;
                append(str);
            }
            i++;
        }
        if (i4 < i) {
            append(cArr, i4, i - i4);
        }
    }

    private void a() throws IOException {
        int iPosition = this.f8a.position();
        if (iPosition > 0) {
            this.f8a.flip();
            this.f6a.write(this.f8a.array(), 0, iPosition);
            this.f8a.clear();
        }
    }
}
