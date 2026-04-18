package kotlin.internal;

import java.util.Objects;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PlatformImplementations.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}, k = 2, mv = {1, 4, 1})
public final class PlatformImplementationsKt {
    public static final PlatformImplementations IMPLEMENTATIONS;

    /* JADX WARN: Removed duplicated region for block: B:43:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static {
        /*
            Method dump skipped, instruction units count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.PlatformImplementationsKt.<clinit>():void");
    }

    private static final /* synthetic */ <T> T castToBaseType(Object obj) throws Throwable {
        try {
            Intrinsics.reifiedOperationMarker(1, "T");
            return (T) obj;
        } catch (ClassCastException e) {
            ClassLoader classLoader = obj.getClass().getClassLoader();
            Intrinsics.reifiedOperationMarker(4, "T");
            Throwable thInitCause = new ClassCastException("Instance classloader: " + classLoader + ", base type classloader: " + Object.class.getClassLoader()).initCause(e);
            Intrinsics.checkNotNullExpressionValue(thInitCause, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
            throw thInitCause;
        }
    }

    private static final int getJavaVersion() {
        String property = System.getProperty("java.specification.version");
        if (property == null) {
            return 65542;
        }
        String str = property;
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, '.', 0, false, 6, (Object) null);
        if (iIndexOf$default < 0) {
            try {
                return Integer.parseInt(property) * 65536;
            } catch (NumberFormatException unused) {
                return 65542;
            }
        }
        int i = iIndexOf$default + 1;
        int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) str, '.', i, false, 4, (Object) null);
        if (iIndexOf$default2 < 0) {
            iIndexOf$default2 = property.length();
        }
        Objects.requireNonNull(property, "null cannot be cast to non-null type java.lang.String");
        String strSubstring = property.substring(0, iIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        Objects.requireNonNull(property, "null cannot be cast to non-null type java.lang.String");
        String strSubstring2 = property.substring(i, iIndexOf$default2);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        try {
            return (Integer.parseInt(strSubstring) * 65536) + Integer.parseInt(strSubstring2);
        } catch (NumberFormatException unused2) {
            return 65542;
        }
    }

    public static final boolean apiVersionIsAtLeast(int i, int i2, int i3) {
        return KotlinVersion.CURRENT.isAtLeast(i, i2, i3);
    }
}
