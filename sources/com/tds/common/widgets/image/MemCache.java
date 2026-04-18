package com.tds.common.widgets.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/* JADX INFO: loaded from: classes.dex */
class MemCache {
    private final LruCache<String, Bitmap> lruCache;

    MemCache(Context context) {
        this.lruCache = new LruCache<String, Bitmap>(ImageUtil.calculateMemoryCacheSize(context)) { // from class: com.tds.common.widgets.image.MemCache.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.util.LruCache
            public int sizeOf(String str, Bitmap bitmap) {
                return ImageUtil.getBitmapBytes(bitmap);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.util.LruCache
            public void entryRemoved(boolean z, String str, Bitmap bitmap, Bitmap bitmap2) {
                super.entryRemoved(z, str, bitmap, bitmap2);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.util.LruCache
            public Bitmap create(String str) {
                return (Bitmap) super.create(str);
            }
        };
    }

    Bitmap get(String str) {
        return this.lruCache.get(str);
    }

    void put(String str, Bitmap bitmap) {
        this.lruCache.put(str, bitmap);
    }
}
