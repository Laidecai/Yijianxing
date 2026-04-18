package com.tds.common.browser;

import android.content.Context;
import android.os.StatFs;
import android.util.Base64;
import com.tds.common.io.DiskLruCache;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class WebFileCache {
    private static WebFileCache INSTANCE = null;
    private static final int MAX_DISK_CACHE_SIZE = 104857600;
    private static final int MIN_DISK_CACHE_SIZE = 20971520;
    private final DiskLruCache diskLruCache;

    public interface Writer {
        boolean write(File file) throws IOException;
    }

    WebFileCache(Context context) {
        this.diskLruCache = openCache(context);
    }

    public static WebFileCache getInstance(Context context) {
        if (context == null) {
            return null;
        }
        if (INSTANCE == null) {
            INSTANCE = new WebFileCache(context);
        }
        return INSTANCE;
    }

    private DiskLruCache openCache(Context context) {
        File file = new File(context.getCacheDir(), "web_file_cache");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            return DiskLruCache.open(file, 0, 1, calculateDiskCacheSize(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File get(String str) throws IOException {
        DiskLruCache.Value value = this.diskLruCache.get(new String(Base64.encode(str.getBytes(StandardCharsets.UTF_8), 10)));
        if (value != null) {
            return value.getFile(0);
        }
        return null;
    }

    private long calculateDiskCacheSize(File file) {
        long blockCountLong;
        try {
            StatFs statFs = new StatFs(file.getAbsolutePath());
            blockCountLong = (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / 50;
        } catch (IllegalArgumentException unused) {
            blockCountLong = 20971520;
        }
        return Math.max(Math.min(blockCountLong, 104857600L), 20971520L);
    }

    public void put(String str, Writer writer) throws IOException {
        DiskLruCache.Editor editorEdit = this.diskLruCache.edit(new String(Base64.encode(str.getBytes(StandardCharsets.UTF_8), 10)));
        try {
            if (writer.write(editorEdit.getFile(0))) {
                editorEdit.commit();
            }
        } finally {
            editorEdit.abortUnlessCommitted();
        }
    }
}
