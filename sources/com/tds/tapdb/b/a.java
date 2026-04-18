package com.tds.tapdb.b;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String a(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
