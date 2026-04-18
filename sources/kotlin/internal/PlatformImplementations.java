package kotlin.internal;

import com.alipay.sdk.cons.c;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;

/* JADX INFO: compiled from: PlatformImplementations.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\u0013"}, d2 = {"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", c.e, "", "getSuppressed", "", "ReflectThrowable", "kotlin-stdlib"}, k = 1, mv = {1, 4, 1})
public class PlatformImplementations {

    /* JADX INFO: compiled from: PlatformImplementations.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lkotlin/internal/PlatformImplementations$ReflectThrowable;", "", "()V", "addSuppressed", "Ljava/lang/reflect/Method;", "getSuppressed", "kotlin-stdlib"}, k = 1, mv = {1, 4, 1})
    private static final class ReflectThrowable {
        public static final ReflectThrowable INSTANCE = new ReflectThrowable();
        public static final Method addSuppressed;
        public static final Method getSuppressed;

        /* JADX WARN: Removed duplicated region for block: B:37:0x0042  */
        static {
            /*
                kotlin.internal.PlatformImplementations$ReflectThrowable r0 = new kotlin.internal.PlatformImplementations$ReflectThrowable
                r0.<init>()
                kotlin.internal.PlatformImplementations.ReflectThrowable.INSTANCE = r0
                java.lang.Class<java.lang.Throwable> r0 = java.lang.Throwable.class
                java.lang.reflect.Method[] r1 = r0.getMethods()
                java.lang.String r2 = "throwableMethods"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
                int r2 = r1.length
                r3 = 0
                r4 = 0
            L15:
                java.lang.String r5 = "it"
                r6 = 0
                if (r4 >= r2) goto L49
                r7 = r1[r4]
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r5)
                java.lang.String r8 = r7.getName()
                java.lang.String r9 = "addSuppressed"
                boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r9)
                if (r8 == 0) goto L42
                java.lang.Class[] r8 = r7.getParameterTypes()
                java.lang.String r9 = "it.parameterTypes"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r9)
                java.lang.Object r8 = kotlin.collections.ArraysKt.singleOrNull(r8)
                java.lang.Class r8 = (java.lang.Class) r8
                boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r0)
                if (r8 == 0) goto L42
                r8 = 1
                goto L43
            L42:
                r8 = 0
            L43:
                if (r8 == 0) goto L46
                goto L4a
            L46:
                int r4 = r4 + 1
                goto L15
            L49:
                r7 = r6
            L4a:
                kotlin.internal.PlatformImplementations.ReflectThrowable.addSuppressed = r7
                int r0 = r1.length
            L4d:
                if (r3 >= r0) goto L65
                r2 = r1[r3]
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r5)
                java.lang.String r4 = r2.getName()
                java.lang.String r7 = "getSuppressed"
                boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r7)
                if (r4 == 0) goto L62
                r6 = r2
                goto L65
            L62:
                int r3 = r3 + 1
                goto L4d
            L65:
                kotlin.internal.PlatformImplementations.ReflectThrowable.getSuppressed = r6
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.PlatformImplementations.ReflectThrowable.<clinit>():void");
        }

        private ReflectThrowable() {
        }
    }

    public void addSuppressed(Throwable cause, Throwable exception) throws IllegalAccessException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(cause, "cause");
        Intrinsics.checkNotNullParameter(exception, "exception");
        Method method = ReflectThrowable.addSuppressed;
        if (method != null) {
            method.invoke(cause, exception);
        }
    }

    public List<Throwable> getSuppressed(Throwable exception) {
        Object objInvoke;
        Intrinsics.checkNotNullParameter(exception, "exception");
        Method method = ReflectThrowable.getSuppressed;
        if (method != null && (objInvoke = method.invoke(exception, new Object[0])) != null) {
            Objects.requireNonNull(objInvoke, "null cannot be cast to non-null type kotlin.Array<kotlin.Throwable>");
            List<Throwable> listAsList = ArraysKt.asList((Throwable[]) objInvoke);
            if (listAsList != null) {
                return listAsList;
            }
        }
        return CollectionsKt.emptyList();
    }

    public MatchGroup getMatchResultNamedGroup(MatchResult matchResult, String str) {
        Intrinsics.checkNotNullParameter(matchResult, "matchResult");
        Intrinsics.checkNotNullParameter(str, "name");
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }

    public Random defaultPlatformRandom() {
        return new FallbackThreadLocalRandom();
    }
}
