package tds.com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewOverlay;

/* JADX INFO: loaded from: classes.dex */
class ViewOverlayApi18 implements ViewOverlayImpl {
    private final ViewOverlay viewOverlay;

    ViewOverlayApi18(View view) {
        this.viewOverlay = view.getOverlay();
    }

    @Override // tds.com.google.android.material.internal.ViewOverlayImpl
    public void add(Drawable drawable) {
        this.viewOverlay.add(drawable);
    }

    @Override // tds.com.google.android.material.internal.ViewOverlayImpl
    public void remove(Drawable drawable) {
        this.viewOverlay.remove(drawable);
    }
}
