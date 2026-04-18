package com.taptap.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    private static Handler mHandler;
    private static Executor mPools;

    public static boolean isLoginSDKInitialized() {
        return TapTapSdk.isInited;
    }

    private static synchronized Executor getPool() {
        if (mPools == null) {
            mPools = Executors.newFixedThreadPool(2);
        }
        return mPools;
    }

    public static boolean runOnAsync(Runnable runnable) {
        getPool().execute(runnable);
        return true;
    }

    public static boolean runOnUIThread(Runnable runnable) {
        if (mHandler == null) {
            synchronized (Utils.class) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        mHandler.post(runnable);
        return true;
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
