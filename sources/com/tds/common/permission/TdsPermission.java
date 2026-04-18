package com.tds.common.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

/* JADX INFO: loaded from: classes.dex */
public class TdsPermission {
    public static boolean checkPermission(Context context, String str) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 30 || !str.equals("android.permission.MANAGE_EXTERNAL_STORAGE")) {
            return context.checkSelfPermission(str) == 0;
        }
        return Environment.isExternalStorageManager();
    }

    public static PermissionCollection with(Activity activity) {
        return new PermissionCollection(activity);
    }

    public static PermissionCollection with(Fragment fragment) {
        return new PermissionCollection(fragment);
    }
}
