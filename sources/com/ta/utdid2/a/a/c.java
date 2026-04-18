package com.ta.utdid2.a.a;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class c {
    public static boolean a() {
        if (Build.VERSION.SDK_INT < 29) {
            return Build.VERSION.CODENAME.length() == 1 && Build.VERSION.CODENAME.charAt(0) >= 'Q' && Build.VERSION.CODENAME.charAt(0) <= 'Z';
        }
        return true;
    }
}
