package com.ta.utdid2.a.a;

import java.io.UnsupportedEncodingException;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static final /* synthetic */ boolean a = true;

    public static abstract class a {
        public int a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        public byte[] f0a;
    }

    /* JADX INFO: renamed from: com.ta.utdid2.a.a.b$b, reason: collision with other inner class name */
    public static class C0013b extends a {
        public static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        public static final int[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        public final int[] c;
        public int state;
        public int value;

        public C0013b(int i, byte[] bArr) {
            ((a) this).f0a = bArr;
            this.c = (i & 8) == 0 ? a : b;
            this.state = 0;
            this.value = 0;
        }

        public boolean a(byte[] bArr, int i, int i2, boolean z) {
            int i3 = this.state;
            if (i3 == 6) {
                return false;
            }
            int i4 = i2 + i;
            int i5 = this.value;
            byte[] bArr2 = ((a) this).f0a;
            int[] iArr = this.c;
            int i6 = i5;
            int i7 = 0;
            int i8 = i3;
            int i9 = i;
            while (i9 < i4) {
                if (i8 == 0) {
                    while (true) {
                        int i10 = i9 + 4;
                        if (i10 > i4 || (i6 = (iArr[bArr[i9] & UByte.MAX_VALUE] << 18) | (iArr[bArr[i9 + 1] & UByte.MAX_VALUE] << 12) | (iArr[bArr[i9 + 2] & UByte.MAX_VALUE] << 6) | iArr[bArr[i9 + 3] & UByte.MAX_VALUE]) < 0) {
                            break;
                        }
                        bArr2[i7 + 2] = (byte) i6;
                        bArr2[i7 + 1] = (byte) (i6 >> 8);
                        bArr2[i7] = (byte) (i6 >> 16);
                        i7 += 3;
                        i9 = i10;
                    }
                    if (i9 >= i4) {
                        break;
                    }
                }
                int i11 = i9 + 1;
                int i12 = iArr[bArr[i9] & UByte.MAX_VALUE];
                if (i8 != 0) {
                    if (i8 == 1) {
                        if (i12 < 0) {
                            if (i12 != -1) {
                                this.state = 6;
                                return false;
                            }
                        }
                        i12 |= i6 << 6;
                    } else if (i8 == 2) {
                        if (i12 < 0) {
                            if (i12 == -2) {
                                bArr2[i7] = (byte) (i6 >> 4);
                                i7++;
                                i8 = 4;
                            } else if (i12 != -1) {
                                this.state = 6;
                                return false;
                            }
                        }
                        i12 |= i6 << 6;
                    } else if (i8 != 3) {
                        if (i8 != 4) {
                            if (i8 == 5 && i12 != -1) {
                                this.state = 6;
                                return false;
                            }
                        } else if (i12 == -2) {
                            i8++;
                        } else if (i12 != -1) {
                            this.state = 6;
                            return false;
                        }
                    } else if (i12 >= 0) {
                        int i13 = i12 | (i6 << 6);
                        bArr2[i7 + 2] = (byte) i13;
                        bArr2[i7 + 1] = (byte) (i13 >> 8);
                        bArr2[i7] = (byte) (i13 >> 16);
                        i7 += 3;
                        i6 = i13;
                        i8 = 0;
                    } else if (i12 == -2) {
                        bArr2[i7 + 1] = (byte) (i6 >> 2);
                        bArr2[i7] = (byte) (i6 >> 10);
                        i7 += 2;
                        i8 = 5;
                    } else if (i12 != -1) {
                        this.state = 6;
                        return false;
                    }
                    i8++;
                    i6 = i12;
                } else if (i12 >= 0) {
                    i8++;
                    i6 = i12;
                } else if (i12 != -1) {
                    this.state = 6;
                    return false;
                }
                i9 = i11;
            }
            if (!z) {
                this.state = i8;
                this.value = i6;
                ((a) this).a = i7;
                return true;
            }
            if (i8 == 1) {
                this.state = 6;
                return false;
            }
            if (i8 == 2) {
                bArr2[i7] = (byte) (i6 >> 4);
                i7++;
            } else if (i8 == 3) {
                int i14 = i7 + 1;
                bArr2[i7] = (byte) (i6 >> 10);
                i7 = i14 + 1;
                bArr2[i14] = (byte) (i6 >> 2);
            } else if (i8 == 4) {
                this.state = 6;
                return false;
            }
            this.state = i8;
            ((a) this).a = i7;
            return true;
        }
    }

    public static class c extends a {
        public static final /* synthetic */ boolean a = true;
        public static final byte[] b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        public static final byte[] c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        public int f1b;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        public final boolean f2b;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        public final boolean f3c;
        public int count;
        public final boolean d;

        /* JADX INFO: renamed from: d, reason: collision with other field name */
        public final byte[] f4d;
        public final byte[] e;

        public c(int i, byte[] bArr) {
            ((a) this).f0a = bArr;
            this.f2b = (i & 1) == 0;
            boolean z = (i & 2) == 0;
            this.f3c = z;
            this.d = (i & 4) != 0;
            this.e = (i & 8) == 0 ? b : c;
            this.f4d = new byte[2];
            this.f1b = 0;
            this.count = z ? 19 : -1;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0050  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0094  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x01bb  */
        /* JADX WARN: Removed duplicated region for block: B:86:0x01c8 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:99:0x00e6 A[SYNTHETIC] */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean a(byte[] r18, int r19, int r20, boolean r21) {
            /*
                Method dump skipped, instruction units count: 511
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.a.a.b.c.a(byte[], int, int, boolean):boolean");
        }
    }

    public static byte[] decode(String str, int i) {
        return decode(str.getBytes(), i);
    }

    public static byte[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, bArr.length, i);
    }

    public static String encodeToString(byte[] bArr, int i) {
        try {
            return new String(encode(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] decode(byte[] bArr, int i) {
        return decode(bArr, 0, bArr.length, i);
    }

    public static byte[] encode(byte[] bArr, int i, int i2, int i3) {
        c cVar = new c(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!cVar.f2b) {
            int i5 = i2 % 3;
            if (i5 == 1) {
                i4 += 2;
            } else if (i5 == 2) {
                i4 += 3;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (cVar.f3c && i2 > 0) {
            i4 += (((i2 - 1) / 57) + 1) * (cVar.d ? 2 : 1);
        }
        ((a) cVar).f0a = new byte[i4];
        cVar.a(bArr, i, i2, true);
        if (a || ((a) cVar).a == i4) {
            return ((a) cVar).f0a;
        }
        throw new AssertionError();
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) {
        C0013b c0013b = new C0013b(i3, new byte[(i2 * 3) / 4]);
        if (c0013b.a(bArr, i, i2, true)) {
            int i4 = ((a) c0013b).a;
            byte[] bArr2 = ((a) c0013b).f0a;
            if (i4 == bArr2.length) {
                return bArr2;
            }
            byte[] bArr3 = new byte[i4];
            System.arraycopy(bArr2, 0, bArr3, 0, i4);
            return bArr3;
        }
        throw new IllegalArgumentException("bad base-64");
    }
}
