package com.unity3d.player;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.PixelCopy;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class f implements Application.ActivityLifecycleCallbacks {
    Activity b;
    WeakReference a = new WeakReference(null);
    View c = null;

    class a extends View implements PixelCopy.OnPixelCopyFinishedListener {
        Bitmap a;

        a(Context context) {
            super(context);
        }

        public final void a(SurfaceView surfaceView) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(surfaceView.getWidth(), surfaceView.getHeight(), Bitmap.Config.ARGB_8888);
            this.a = bitmapCreateBitmap;
            PixelCopy.request(surfaceView, bitmapCreateBitmap, this, new Handler(Looper.getMainLooper()));
        }

        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
        public final void onPixelCopyFinished(int i) {
            if (i == 0) {
                setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(-16777216), new BitmapDrawable(getResources(), this.a)}));
            }
        }
    }

    f(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            this.b = activity;
            activity.getApplication().registerActivityLifecycleCallbacks(this);
        }
    }

    public final void a() {
        Activity activity = this.b;
        if (activity != null) {
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }

    public final void a(SurfaceView surfaceView) {
        if (PlatformSupport.NOUGAT_SUPPORT && this.a.get() != this.b && this.c == null) {
            a aVar = new a(this.b);
            aVar.a(surfaceView);
            this.c = aVar;
        }
    }

    public final void a(ViewGroup viewGroup) {
        View view = this.c;
        if (view == null || view.getParent() != null) {
            return;
        }
        viewGroup.addView(this.c);
        viewGroup.bringChildToFront(this.c);
    }

    public final void b(ViewGroup viewGroup) {
        View view = this.c;
        if (view == null || view.getParent() == null) {
            return;
        }
        viewGroup.removeView(this.c);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        this.a = new WeakReference(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }
}
