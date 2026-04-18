package com.tds.common.browser;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class GarbageCollectionHandler extends Handler {
    private final AttachFilter attachFilter;
    private final long delay;
    private final RecycleCallback recycleCallback;

    public interface AttachFilter {
        boolean attached(View view);
    }

    public interface RecycleCallback {
        void onRecycle(View view);
    }

    private GarbageCollectionHandler() {
        this.attachFilter = null;
        this.recycleCallback = null;
        this.delay = 60000L;
    }

    public GarbageCollectionHandler(Looper looper, AttachFilter attachFilter, RecycleCallback recycleCallback, long j) {
        super(looper);
        this.attachFilter = attachFilter;
        this.recycleCallback = recycleCallback;
        this.delay = j;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.attachFilter == null || this.recycleCallback == null || !(message.obj instanceof View)) {
            return;
        }
        View view = (View) message.obj;
        if (!this.attachFilter.attached(view)) {
            this.recycleCallback.onRecycle(view);
        } else if (this.delay > 0) {
            sendMessageDelayed(Message.obtain(message), this.delay);
        }
    }
}
