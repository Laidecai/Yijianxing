package com.taptap.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import com.taptap.sdk.ui.TapTapActivity;

/* JADX INFO: loaded from: classes.dex */
public class Validate {
    public static void notNull(Object obj, String str) {
        if (obj == null) {
            Log.DEBUG_LOG("Argument '" + str + "' can not be null");
        }
    }

    public static void hasTapTapActivity(Context context, boolean z) {
        ActivityInfo activityInfo;
        notNull(context, "context");
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                activityInfo = packageManager.getActivityInfo(new ComponentName(context, (Class<?>) TapTapActivity.class), 1);
            } catch (PackageManager.NameNotFoundException unused) {
                activityInfo = null;
            }
        } else {
            activityInfo = null;
        }
        if (activityInfo == null) {
            if (z) {
                throw new IllegalStateException("TapTapActivity is not declared in the AndroidManifest.xml, please add com.taptap.sdk.ui.TapTapActivity to your AndroidManifest.xml file. ");
            }
            Log.DEBUG_LOG("TapTapActivity is not declared in the AndroidManifest.xml, please add com.taptap.sdk.ui.TapTapActivity to your AndroidManifest.xml file. ");
        }
    }

    public static void hasInternetPermissions(Context context, boolean z) {
        notNull(context, "context");
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") == -1) {
            if (z) {
                throw new IllegalStateException("No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.");
            }
            Log.DEBUG_LOG("No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.");
        }
    }

    public static void sdkHasInitialized() {
        if (TapTapSdk.isInited) {
            return;
        }
        Log.DEBUG_LOG("sdk has not initialized. please call TapTapSdk.sdkInitialize first");
    }
}
