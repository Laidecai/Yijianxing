package kotlin;

import kotlin.ranges.UIntRange;

/* JADX INFO: compiled from: UInt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 m2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001mB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u0000H\u0087\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0018\u0010\u0005J\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u000fJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u000bJ\u001b\u0010\u0019\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0016J\u001a\u0010\u001f\u001a\u00020 2\b\u0010\t\u001a\u0004\u0018\u00010!HÖ\u0003¢\u0006\u0004\b\"\u0010#J\u0010\u0010$\u001a\u00020\u0003HÖ\u0001¢\u0006\u0004\b%\u0010\u0005J\u0016\u0010&\u001a\u00020\u0000H\u0087\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b'\u0010\u0005J\u0016\u0010(\u001a\u00020\u0000H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b)\u0010\u0005J\u001b\u0010*\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b+\u0010\u000fJ\u001b\u0010*\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u000bJ\u001b\u0010*\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b-\u0010\u001dJ\u001b\u0010*\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b.\u0010\u0016J\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b0\u0010\u000bJ\u001b\u00101\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u000fJ\u001b\u00101\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u000bJ\u001b\u00101\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u001dJ\u001b\u00101\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u0016J\u001b\u00106\u001a\u0002072\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b8\u00109J\u001b\u0010:\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\b;\u0010\u000fJ\u001b\u0010:\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b<\u0010\u000bJ\u001b\u0010:\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b=\u0010\u001dJ\u001b\u0010:\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b>\u0010\u0016J\u001e\u0010?\u001a\u00020\u00002\u0006\u0010@\u001a\u00020\u0003H\u0087\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bA\u0010\u000bJ\u001e\u0010B\u001a\u00020\u00002\u0006\u0010@\u001a\u00020\u0003H\u0087\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bC\u0010\u000bJ\u001b\u0010D\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\nø\u0001\u0000¢\u0006\u0004\bE\u0010\u000fJ\u001b\u0010D\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bF\u0010\u000bJ\u001b\u0010D\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bG\u0010\u001dJ\u001b\u0010D\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bH\u0010\u0016J\u0010\u0010I\u001a\u00020JH\u0087\b¢\u0006\u0004\bK\u0010LJ\u0010\u0010M\u001a\u00020NH\u0087\b¢\u0006\u0004\bO\u0010PJ\u0010\u0010Q\u001a\u00020RH\u0087\b¢\u0006\u0004\bS\u0010TJ\u0010\u0010U\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bV\u0010\u0005J\u0010\u0010W\u001a\u00020XH\u0087\b¢\u0006\u0004\bY\u0010ZJ\u0010\u0010[\u001a\u00020\\H\u0087\b¢\u0006\u0004\b]\u0010^J\u000f\u0010_\u001a\u00020`H\u0016¢\u0006\u0004\ba\u0010bJ\u0016\u0010c\u001a\u00020\rH\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bd\u0010LJ\u0016\u0010e\u001a\u00020\u0000H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bf\u0010\u0005J\u0016\u0010g\u001a\u00020\u0011H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bh\u0010ZJ\u0016\u0010i\u001a\u00020\u0014H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bj\u0010^J\u001b\u0010k\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bl\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006n"}, d2 = {"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "getData$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-pVg5ArA", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(ILjava/lang/Object;)Z", "hashCode", "hashCode-impl", "inc", "inc-pVg5ArA", "inv", "inv-pVg5ArA", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-pVg5ArA", "shr", "shr-pVg5ArA", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 4, 1})
public final class UInt implements Comparable<UInt> {
    public static final int MAX_VALUE = -1;
    public static final int MIN_VALUE = 0;
    public static final int SIZE_BITS = 32;
    public static final int SIZE_BYTES = 4;
    private final int data;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UInt m101boximpl(int i) {
        return new UInt(i);
    }

    /* JADX INFO: renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private int m104compareToWZ4Q5Ns(int i) {
        return m105compareToWZ4Q5Ns(this.data, i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static int m107constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m113equalsimpl(int i, Object obj) {
        return (obj instanceof UInt) && i == ((UInt) obj).getData();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m114equalsimpl0(int i, int i2) {
        return i == i2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m115hashCodeimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: toByte-impl, reason: not valid java name */
    private static final byte m138toByteimpl(int i) {
        return (byte) i;
    }

    /* JADX INFO: renamed from: toInt-impl, reason: not valid java name */
    private static final int m141toIntimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: toLong-impl, reason: not valid java name */
    private static final long m142toLongimpl(int i) {
        return ((long) i) & 4294967295L;
    }

    /* JADX INFO: renamed from: toShort-impl, reason: not valid java name */
    private static final short m143toShortimpl(int i) {
        return (short) i;
    }

    /* JADX INFO: renamed from: toUInt-pVg5ArA, reason: not valid java name */
    private static final int m146toUIntpVg5ArA(int i) {
        return i;
    }

    public boolean equals(Object obj) {
        return m113equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m115hashCodeimpl(this.data);
    }

    public String toString() {
        return m144toStringimpl(this.data);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ int getData() {
        return this.data;
    }

    private /* synthetic */ UInt(int i) {
        this.data = i;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UInt uInt) {
        return m104compareToWZ4Q5Ns(uInt.getData());
    }

    /* JADX INFO: renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m102compareTo7apg3OU(int i, byte b) {
        return UnsignedKt.uintCompare(i, m107constructorimpl(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static final int m106compareToxj2QHRw(int i, short s) {
        return UnsignedKt.uintCompare(i, m107constructorimpl(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static int m105compareToWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2);
    }

    /* JADX INFO: renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m103compareToVKZWuLQ(int i, long j) {
        return UnsignedKt.ulongCompare(ULong.m177constructorimpl(((long) i) & 4294967295L), j);
    }

    /* JADX INFO: renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m123plus7apg3OU(int i, byte b) {
        return m107constructorimpl(i + m107constructorimpl(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m126plusxj2QHRw(int i, short s) {
        return m107constructorimpl(i + m107constructorimpl(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m125plusWZ4Q5Ns(int i, int i2) {
        return m107constructorimpl(i + i2);
    }

    /* JADX INFO: renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m124plusVKZWuLQ(int i, long j) {
        return ULong.m177constructorimpl(ULong.m177constructorimpl(((long) i) & 4294967295L) + j);
    }

    /* JADX INFO: renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m118minus7apg3OU(int i, byte b) {
        return m107constructorimpl(i - m107constructorimpl(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m121minusxj2QHRw(int i, short s) {
        return m107constructorimpl(i - m107constructorimpl(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m120minusWZ4Q5Ns(int i, int i2) {
        return m107constructorimpl(i - i2);
    }

    /* JADX INFO: renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m119minusVKZWuLQ(int i, long j) {
        return ULong.m177constructorimpl(ULong.m177constructorimpl(((long) i) & 4294967295L) - j);
    }

    /* JADX INFO: renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m134times7apg3OU(int i, byte b) {
        return m107constructorimpl(i * m107constructorimpl(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m137timesxj2QHRw(int i, short s) {
        return m107constructorimpl(i * m107constructorimpl(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m136timesWZ4Q5Ns(int i, int i2) {
        return m107constructorimpl(i * i2);
    }

    /* JADX INFO: renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m135timesVKZWuLQ(int i, long j) {
        return ULong.m177constructorimpl(ULong.m177constructorimpl(((long) i) & 4294967295L) * j);
    }

    /* JADX INFO: renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m109div7apg3OU(int i, byte b) {
        return UnsignedKt.m336uintDivideJ1ME1BU(i, m107constructorimpl(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m112divxj2QHRw(int i, short s) {
        return UnsignedKt.m336uintDivideJ1ME1BU(i, m107constructorimpl(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m111divWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.m336uintDivideJ1ME1BU(i, i2);
    }

    /* JADX INFO: renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m110divVKZWuLQ(int i, long j) {
        return UnsignedKt.m338ulongDivideeb3DHEI(ULong.m177constructorimpl(((long) i) & 4294967295L), j);
    }

    /* JADX INFO: renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m128rem7apg3OU(int i, byte b) {
        return UnsignedKt.m337uintRemainderJ1ME1BU(i, m107constructorimpl(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m131remxj2QHRw(int i, short s) {
        return UnsignedKt.m337uintRemainderJ1ME1BU(i, m107constructorimpl(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m130remWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.m337uintRemainderJ1ME1BU(i, i2);
    }

    /* JADX INFO: renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m129remVKZWuLQ(int i, long j) {
        return UnsignedKt.m339ulongRemaindereb3DHEI(ULong.m177constructorimpl(((long) i) & 4294967295L), j);
    }

    /* JADX INFO: renamed from: inc-pVg5ArA, reason: not valid java name */
    private static final int m116incpVg5ArA(int i) {
        return m107constructorimpl(i + 1);
    }

    /* JADX INFO: renamed from: dec-pVg5ArA, reason: not valid java name */
    private static final int m108decpVg5ArA(int i) {
        return m107constructorimpl(i - 1);
    }

    /* JADX INFO: renamed from: rangeTo-WZ4Q5Ns, reason: not valid java name */
    private static final UIntRange m127rangeToWZ4Q5Ns(int i, int i2) {
        return new UIntRange(i, i2, null);
    }

    /* JADX INFO: renamed from: shl-pVg5ArA, reason: not valid java name */
    private static final int m132shlpVg5ArA(int i, int i2) {
        return m107constructorimpl(i << i2);
    }

    /* JADX INFO: renamed from: shr-pVg5ArA, reason: not valid java name */
    private static final int m133shrpVg5ArA(int i, int i2) {
        return m107constructorimpl(i >>> i2);
    }

    /* JADX INFO: renamed from: and-WZ4Q5Ns, reason: not valid java name */
    private static final int m100andWZ4Q5Ns(int i, int i2) {
        return m107constructorimpl(i & i2);
    }

    /* JADX INFO: renamed from: or-WZ4Q5Ns, reason: not valid java name */
    private static final int m122orWZ4Q5Ns(int i, int i2) {
        return m107constructorimpl(i | i2);
    }

    /* JADX INFO: renamed from: xor-WZ4Q5Ns, reason: not valid java name */
    private static final int m149xorWZ4Q5Ns(int i, int i2) {
        return m107constructorimpl(i ^ i2);
    }

    /* JADX INFO: renamed from: inv-pVg5ArA, reason: not valid java name */
    private static final int m117invpVg5ArA(int i) {
        return m107constructorimpl(~i);
    }

    /* JADX INFO: renamed from: toUByte-w2LRezQ, reason: not valid java name */
    private static final byte m145toUBytew2LRezQ(int i) {
        return UByte.m39constructorimpl((byte) i);
    }

    /* JADX INFO: renamed from: toUShort-Mh2AYeg, reason: not valid java name */
    private static final short m148toUShortMh2AYeg(int i) {
        return UShort.m275constructorimpl((short) i);
    }

    /* JADX INFO: renamed from: toULong-s-VKNKU, reason: not valid java name */
    private static final long m147toULongsVKNKU(int i) {
        return ULong.m177constructorimpl(((long) i) & 4294967295L);
    }

    /* JADX INFO: renamed from: toFloat-impl, reason: not valid java name */
    private static final float m140toFloatimpl(int i) {
        return (float) UnsignedKt.uintToDouble(i);
    }

    /* JADX INFO: renamed from: toDouble-impl, reason: not valid java name */
    private static final double m139toDoubleimpl(int i) {
        return UnsignedKt.uintToDouble(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m144toStringimpl(int i) {
        return String.valueOf(((long) i) & 4294967295L);
    }
}
