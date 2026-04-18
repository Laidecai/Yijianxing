package com.tds.common.widgets.image;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.tds.common.net.TdsHttp;
import com.tds.common.reactor.Subscription;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class TdsImage {
    private static TdsImage instance;
    final TdsHttp.Client client;
    final Context context;
    final DiskCache diskCache;
    final MemCache memCache;
    final Map<Object, Subscription> targetToAction;

    private TdsImage(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.context = context;
        this.client = TdsHttp.newClientBuilder().readTimeout(3L, TimeUnit.SECONDS).build();
        this.memCache = new MemCache(context);
        this.diskCache = new DiskCache(context);
        this.targetToAction = new WeakHashMap();
    }

    public static TdsImage get(Context context) {
        if (instance == null) {
            synchronized (TdsImage.class) {
                if (instance == null) {
                    instance = new TdsImage(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public LoadBuilder load(String str) {
        if (TextUtils.isEmpty(str)) {
            return load((Uri) null);
        }
        return load(Uri.parse(str));
    }

    public LoadBuilder load(File file) {
        if (file == null) {
            return load((Uri) null);
        }
        return load(Uri.fromFile(file));
    }

    public LoadBuilder load(int i) {
        return new LoadBuilder(this, null, i);
    }

    LoadBuilder load(Uri uri) {
        return new LoadBuilder(this, uri, 0);
    }

    void enqueue(Object obj, Subscription subscription) {
        if (this.targetToAction.get(obj) != subscription) {
            cancelRequest(obj);
            this.targetToAction.put(obj, subscription);
        }
    }

    public void cancelRequest(Object obj) {
        Subscription subscriptionRemove;
        if (obj == null || (subscriptionRemove = this.targetToAction.remove(obj)) == null) {
            return;
        }
        subscriptionRemove.unsubscribe();
    }
}
