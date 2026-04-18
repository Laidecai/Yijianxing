package com.ta.utdid2.a.a;

/* JADX INFO: loaded from: classes.dex */
public class f {

    public static class a {
        public int[] d;
        public int x;
        public int y;

        public a() {
            this.d = new int[256];
        }
    }

    public static byte[] a(byte[] bArr) {
        a aVarA;
        if (bArr == null || (aVarA = a("QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK")) == null) {
            return null;
        }
        return a(bArr, aVarA);
    }

    public static a a(String str) {
        if (str == null) {
            return null;
        }
        a aVar = new a();
        for (int i = 0; i < 256; i++) {
            aVar.d[i] = i;
        }
        aVar.x = 0;
        aVar.y = 0;
        int length = 0;
        int iCharAt = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            try {
                iCharAt = ((str.charAt(length) + aVar.d[i2]) + iCharAt) % 256;
                int i3 = aVar.d[i2];
                aVar.d[i2] = aVar.d[iCharAt];
                aVar.d[iCharAt] = i3;
                length = (length + 1) % str.length();
            } catch (Exception unused) {
                return null;
            }
        }
        return aVar;
    }

    public static byte[] a(byte[] bArr, a aVar) {
        if (bArr == null || aVar == null) {
            return null;
        }
        int i = aVar.x;
        int i2 = aVar.y;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) % 256;
            int[] iArr = aVar.d;
            i2 = (iArr[i] + i2) % 256;
            int i4 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i4;
            int i5 = (iArr[i] + iArr[i2]) % 256;
            bArr[i3] = (byte) (iArr[i5] ^ bArr[i3]);
        }
        aVar.x = i;
        aVar.y = i2;
        return bArr;
    }
}
