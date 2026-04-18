package com.tds.tapdb.b;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import androidx.core.os.EnvironmentCompat;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static String a() {
        return !TextUtils.isEmpty(Build.BRAND) ? Build.BRAND : "";
    }

    public static int[] a(Context context) {
        int[] iArr = new int[2];
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (i2 > i) {
            i = i2;
            i2 = i;
        }
        iArr[0] = i;
        iArr[1] = i2;
        return iArr;
    }

    public static String b() {
        return !TextUtils.isEmpty(Build.MODEL) ? Build.MODEL : "";
    }

    public static String c() {
        boolean zIsEmpty = TextUtils.isEmpty(Build.MODEL);
        String str = EnvironmentCompat.MEDIA_UNKNOWN;
        String str2 = !zIsEmpty ? Build.MODEL : EnvironmentCompat.MEDIA_UNKNOWN;
        if (!TextUtils.isEmpty(Build.BRAND)) {
            str = Build.BRAND;
        }
        return str2 + " " + str;
    }

    public static String d() {
        String str = Build.VERSION.RELEASE;
        return str == null ? "UNKNOWN" : str;
    }

    public static boolean e() {
        String str = Build.MANUFACTURER;
        n.a("manufacturer:" + str);
        return "xiaomi".equalsIgnoreCase(str);
    }
}
