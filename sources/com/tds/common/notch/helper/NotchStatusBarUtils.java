package com.tds.common.notch.helper;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class NotchStatusBarUtils {
    private static int statusBarHeight = -1;

    public static int getStatusBarHeight(Context context) {
        int identifier;
        int i = statusBarHeight;
        if (i != -1) {
            return i;
        }
        if (i <= 0 && (identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android")) > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(identifier);
        }
        return statusBarHeight;
    }
}
