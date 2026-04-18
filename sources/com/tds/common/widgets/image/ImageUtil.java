package com.tds.common.widgets.image;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Looper;
import android.os.StatFs;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
final class ImageUtil {
    private static final int MAX_DISK_CACHE_SIZE = 524288000;
    private static final int MIN_DISK_CACHE_SIZE = 52428800;

    ImageUtil() {
    }

    public static int getBitmapBytes(Bitmap bitmap) {
        int allocationByteCount = Build.VERSION.SDK_INT >= 19 ? bitmap.getAllocationByteCount() : bitmap.getByteCount();
        if (allocationByteCount >= 0) {
            return allocationByteCount;
        }
        throw new IllegalStateException("Negative size: " + bitmap);
    }

    static long calculateDiskCacheSize(File file) {
        long blockCountLong;
        try {
            StatFs statFs = new StatFs(file.getAbsolutePath());
            blockCountLong = (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / 50;
        } catch (IllegalArgumentException unused) {
            blockCountLong = 52428800;
        }
        return Math.max(Math.min(blockCountLong, 524288000L), 52428800L);
    }

    static int calculateMemoryCacheSize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        return (int) ((((long) ((context.getApplicationInfo().flags & 1048576) != 0 ? activityManager.getLargeMemoryClass() : activityManager.getMemoryClass())) * 1048576) / 4);
    }

    static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    static void checkMain() {
        if (!isMain()) {
            throw new IllegalStateException("method must called on main thread");
        }
    }

    static String getMd5(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        return new BigInteger(1, messageDigest.digest(str.getBytes())).toString(16);
    }
}
