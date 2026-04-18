package okhttp3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;

/* JADX INFO: loaded from: classes.dex */
public abstract class RequestBody {
    public long contentLength() throws IOException {
        return -1L;
    }

    @Nullable
    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink bufferedSink) throws IOException;

    public static RequestBody create(@Nullable MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null && (charset = mediaType.charset()) == null) {
            charset = Util.UTF_8;
            mediaType = MediaType.parse(mediaType + "; charset=utf-8");
        }
        return create(mediaType, str.getBytes(charset));
    }

    /* JADX INFO: renamed from: okhttp3.RequestBody$1 */
    class AnonymousClass1 extends RequestBody {
        final /* synthetic */ ByteString val$content;

        AnonymousClass1(ByteString byteString) {
            byteString = byteString;
        }

        @Override // okhttp3.RequestBody
        @Nullable
        public MediaType contentType() {
            return mediaType;
        }

        @Override // okhttp3.RequestBody
        public long contentLength() throws IOException {
            return byteString.size();
        }

        @Override // okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            bufferedSink.write(byteString);
        }
    }

    public static RequestBody create(@Nullable MediaType mediaType, ByteString byteString) {
        return new RequestBody() { // from class: okhttp3.RequestBody.1
            final /* synthetic */ ByteString val$content;

            AnonymousClass1(ByteString byteString2) {
                byteString = byteString2;
            }

            @Override // okhttp3.RequestBody
            @Nullable
            public MediaType contentType() {
                return mediaType;
            }

            @Override // okhttp3.RequestBody
            public long contentLength() throws IOException {
                return byteString.size();
            }

            @Override // okhttp3.RequestBody
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(byteString);
            }
        };
    }

    public static RequestBody create(@Nullable MediaType mediaType, byte[] bArr) {
        return create(mediaType, bArr, 0, bArr.length);
    }

    public static RequestBody create(@Nullable MediaType mediaType, byte[] bArr, int i, int i2) {
        Objects.requireNonNull(bArr, "content == null");
        Util.checkOffsetAndCount(bArr.length, i, i2);
        return new RequestBody() { // from class: okhttp3.RequestBody.2
            final /* synthetic */ int val$byteCount;
            final /* synthetic */ byte[] val$content;
            final /* synthetic */ int val$offset;

            AnonymousClass2(int i22, byte[] bArr2, int i3) {
                i = i22;
                bArr = bArr2;
                i = i3;
            }

            @Override // okhttp3.RequestBody
            @Nullable
            public MediaType contentType() {
                return mediaType;
            }

            @Override // okhttp3.RequestBody
            public long contentLength() {
                return i;
            }

            @Override // okhttp3.RequestBody
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(bArr, i, i);
            }
        };
    }

    /* JADX INFO: renamed from: okhttp3.RequestBody$2 */
    class AnonymousClass2 extends RequestBody {
        final /* synthetic */ int val$byteCount;
        final /* synthetic */ byte[] val$content;
        final /* synthetic */ int val$offset;

        AnonymousClass2(int i22, byte[] bArr2, int i3) {
            i = i22;
            bArr = bArr2;
            i = i3;
        }

        @Override // okhttp3.RequestBody
        @Nullable
        public MediaType contentType() {
            return mediaType;
        }

        @Override // okhttp3.RequestBody
        public long contentLength() {
            return i;
        }

        @Override // okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            bufferedSink.write(bArr, i, i);
        }
    }

    public static RequestBody create(@Nullable MediaType mediaType, File file) {
        Objects.requireNonNull(file, "file == null");
        return new RequestBody() { // from class: okhttp3.RequestBody.3
            final /* synthetic */ File val$file;

            AnonymousClass3(File file2) {
                file = file2;
            }

            @Override // okhttp3.RequestBody
            @Nullable
            public MediaType contentType() {
                return mediaType;
            }

            @Override // okhttp3.RequestBody
            public long contentLength() {
                return file.length();
            }

            @Override // okhttp3.RequestBody
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(file);
                    bufferedSink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    /* JADX INFO: renamed from: okhttp3.RequestBody$3 */
    class AnonymousClass3 extends RequestBody {
        final /* synthetic */ File val$file;

        AnonymousClass3(File file2) {
            file = file2;
        }

        @Override // okhttp3.RequestBody
        @Nullable
        public MediaType contentType() {
            return mediaType;
        }

        @Override // okhttp3.RequestBody
        public long contentLength() {
            return file.length();
        }

        @Override // okhttp3.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            Source source = null;
            try {
                source = Okio.source(file);
                bufferedSink.writeAll(source);
            } finally {
                Util.closeQuietly(source);
            }
        }
    }
}
