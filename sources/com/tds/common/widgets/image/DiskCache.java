package com.tds.common.widgets.image;

import android.content.Context;
import com.tds.common.io.DiskLruCache;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class DiskCache {
    private final DiskLruCache diskLruCache;

    public interface Writer {
        boolean write(File file) throws IOException;
    }

    DiskCache(Context context) {
        this.diskLruCache = openCache(context);
    }

    private DiskLruCache openCache(Context context) {
        File file = new File(context.getCacheDir(), "tds-image");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            return DiskLruCache.open(file, 0, 1, ImageUtil.calculateDiskCacheSize(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    File get(String str) throws IOException {
        DiskLruCache.Value value = this.diskLruCache.get(str);
        if (value != null) {
            return value.getFile(0);
        }
        return null;
    }

    void put(String str, Writer writer) throws IOException {
        DiskLruCache.Editor editorEdit = this.diskLruCache.edit(str);
        try {
            if (writer.write(editorEdit.getFile(0))) {
                editorEdit.commit();
            }
        } finally {
            editorEdit.abortUnlessCommitted();
        }
    }
}
