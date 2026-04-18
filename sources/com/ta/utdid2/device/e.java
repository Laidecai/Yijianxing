package com.ta.utdid2.device;

import com.ta.utdid2.a.a.g;

/* JADX INFO: loaded from: classes.dex */
public class e {
    public String d(String str) {
        return com.ta.utdid2.a.a.a.b(str);
    }

    public String e(String str) {
        String strB = com.ta.utdid2.a.a.a.b(str);
        if (!g.m6a(strB)) {
            try {
                return new String(com.ta.utdid2.a.a.b.decode(strB, 0));
            } catch (IllegalArgumentException unused) {
            }
        }
        return null;
    }
}
