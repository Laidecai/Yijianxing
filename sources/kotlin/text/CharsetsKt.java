package kotlin.text;

import com.tds.tapdb.b.g;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Charsets.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b¨\u0006\u0004"}, d2 = {g.R, "Ljava/nio/charset/Charset;", "charsetName", "", "kotlin-stdlib"}, k = 2, mv = {1, 4, 1})
public final class CharsetsKt {
    private static final Charset charset(String str) {
        Charset charsetForName = Charset.forName(str);
        Intrinsics.checkNotNullExpressionValue(charsetForName, "Charset.forName(charsetName)");
        return charsetForName;
    }
}
