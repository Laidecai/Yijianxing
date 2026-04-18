package com.ta.utdid2.a.a;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static byte[] getBytes(int i) {
        byte[] bArr = {(byte) ((i >> 8) % 256), (byte) (i % 256), (byte) (i % 256), (byte) (i % 256)};
        int i2 = i >> 8;
        int i3 = i2 >> 8;
        return bArr;
    }
}
