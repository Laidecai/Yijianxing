package com.google.androidgamesdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;

/* JADX INFO: loaded from: classes.dex */
public class ChoreographerCallback implements Choreographer.FrameCallback {
    private static final String LOG_TAG = "ChoreographerCallback";
    private long mCookie;
    private a mLooper;

    private class a extends Thread {
        public Handler a;

        private a() {
        }

        /* synthetic */ a(ChoreographerCallback choreographerCallback, byte b) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Log.i(ChoreographerCallback.LOG_TAG, "Starting looper thread");
            Looper.prepare();
            this.a = new Handler();
            Looper.loop();
            Log.i(ChoreographerCallback.LOG_TAG, "Terminating looper thread");
        }
    }

    public ChoreographerCallback(long j) {
        this.mCookie = j;
        a aVar = new a(this, (byte) 0);
        this.mLooper = aVar;
        aVar.start();
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j) {
        nOnChoreographer(this.mCookie, j);
    }

    public native void nOnChoreographer(long j, long j2);

    public void postFrameCallback() {
        this.mLooper.a.post(new Runnable() { // from class: com.google.androidgamesdk.ChoreographerCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                Choreographer.getInstance().postFrameCallback(ChoreographerCallback.this);
            }
        });
    }

    public void postFrameCallbackDelayed(long j) {
        Choreographer.getInstance().postFrameCallbackDelayed(this, j);
    }

    public void terminate() {
        this.mLooper.a.getLooper().quit();
    }
}
