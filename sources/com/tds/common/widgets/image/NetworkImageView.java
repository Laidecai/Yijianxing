package com.tds.common.widgets.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class NetworkImageView extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageTarget mImageTarget;
    private LoadBuilder mLoadBuilder;
    private TdsImage mTdsImage;
    private String mUrl;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setImageUrl(String str) {
        String str2 = this.mUrl;
        if (str2 == null || !str2.equals(str)) {
            this.mUrl = str;
            if (str != null) {
                TdsImage tdsImage = TdsImage.get(getContext());
                this.mTdsImage = tdsImage;
                this.mLoadBuilder = tdsImage.load(str);
            }
            loadImageIfNecessary();
        }
    }

    public void setDefaultImageResId(int i) {
        this.mDefaultImageId = i;
    }

    public void setErrorImageResId(int i) {
        this.mErrorImageId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void loadImageIfNecessary() {
        /*
            r7 = this;
            int r0 = r7.getWidth()
            int r1 = r7.getHeight()
            android.view.ViewGroup$LayoutParams r2 = r7.getLayoutParams()
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L26
            android.view.ViewGroup$LayoutParams r2 = r7.getLayoutParams()
            int r2 = r2.width
            r5 = -2
            if (r2 != r5) goto L1b
            r2 = 1
            goto L1c
        L1b:
            r2 = 0
        L1c:
            android.view.ViewGroup$LayoutParams r6 = r7.getLayoutParams()
            int r6 = r6.height
            if (r6 != r5) goto L27
            r5 = 1
            goto L28
        L26:
            r2 = 0
        L27:
            r5 = 0
        L28:
            if (r2 == 0) goto L2d
            if (r5 == 0) goto L2d
            goto L2e
        L2d:
            r3 = 0
        L2e:
            if (r0 != 0) goto L35
            if (r1 != 0) goto L35
            if (r3 != 0) goto L35
            return
        L35:
            java.lang.String r2 = r7.mUrl
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L4c
            com.tds.common.widgets.image.ImageTarget r0 = r7.mImageTarget
            if (r0 == 0) goto L48
            com.tds.common.widgets.image.TdsImage r1 = r7.mTdsImage
            if (r1 == 0) goto L48
            r1.cancelRequest(r0)
        L48:
            r7.setDefaultImageOrNull()
            return
        L4c:
            r7.initImageTarget()
            com.tds.common.widgets.image.LoadBuilder r2 = r7.mLoadBuilder
            if (r2 == 0) goto L6c
            int r3 = r7.mErrorImageId
            com.tds.common.widgets.image.LoadBuilder r2 = r2.error(r3)
            int r3 = r7.mDefaultImageId
            com.tds.common.widgets.image.LoadBuilder r2 = r2.placeholder(r3)
            android.widget.ImageView$ScaleType r3 = r7.getScaleType()
            com.tds.common.widgets.image.LoadBuilder r0 = r2.scale(r0, r1, r3)
            com.tds.common.widgets.image.ImageTarget r1 = r7.mImageTarget
            r0.into(r1)
        L6c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.widgets.image.NetworkImageView.loadImageIfNecessary():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void resetBitmapSize(android.graphics.Bitmap r13) {
        /*
            r12 = this;
            if (r13 != 0) goto L3
            return
        L3:
            int r0 = r12.getWidth()
            int r1 = r12.getHeight()
            android.view.ViewGroup$LayoutParams r2 = r12.getLayoutParams()
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L29
            android.view.ViewGroup$LayoutParams r2 = r12.getLayoutParams()
            int r2 = r2.width
            r5 = -2
            if (r2 != r5) goto L1e
            r2 = 1
            goto L1f
        L1e:
            r2 = 0
        L1f:
            android.view.ViewGroup$LayoutParams r6 = r12.getLayoutParams()
            int r6 = r6.height
            if (r6 != r5) goto L2a
            r5 = 1
            goto L2b
        L29:
            r2 = 0
        L2a:
            r5 = 0
        L2b:
            if (r2 == 0) goto L2e
            r0 = 0
        L2e:
            if (r5 == 0) goto L31
            r1 = 0
        L31:
            if (r0 != 0) goto L39
            if (r1 != 0) goto L39
            r12.setImageBitmapInMain(r13)
            goto L61
        L39:
            int r2 = r13.getWidth()
            int r4 = r13.getHeight()
            android.widget.ImageView$ScaleType r11 = r12.getScaleType()
            r6 = r12
            r7 = r0
            r8 = r1
            r9 = r2
            r10 = r4
            int r5 = r6.getResizedDimension(r7, r8, r9, r10, r11)
            android.widget.ImageView$ScaleType r11 = r12.getScaleType()
            r7 = r1
            r8 = r0
            r9 = r4
            r10 = r2
            int r0 = r6.getResizedDimension(r7, r8, r9, r10, r11)
            android.graphics.Bitmap r13 = android.graphics.Bitmap.createScaledBitmap(r13, r5, r0, r3)
            r12.setImageBitmapInMain(r13)
        L61:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.widgets.image.NetworkImageView.resetBitmapSize(android.graphics.Bitmap):void");
    }

    private void setDefaultImageOrNull() {
        int i = this.mDefaultImageId;
        if (i != 0) {
            setImageResource(i);
        } else {
            setImageBitmap(null);
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        loadImageIfNecessary();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        TdsImage tdsImage = this.mTdsImage;
        if (tdsImage != null && this.mLoadBuilder != null) {
            tdsImage.cancelRequest(this);
            setImageBitmap(null);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    private void initImageTarget() {
        if (this.mImageTarget == null) {
            this.mImageTarget = new ImageTarget() { // from class: com.tds.common.widgets.image.NetworkImageView.1
                @Override // com.tds.common.widgets.image.ImageTarget
                public void onSuccess(Bitmap bitmap) {
                    if (bitmap != null) {
                        NetworkImageView.this.setImageBitmapInMain(bitmap);
                    } else {
                        onFailure(new RuntimeException("get bitmap from net error"));
                    }
                }

                @Override // com.tds.common.widgets.image.ImageTarget
                public void onFailure(Throwable th) {
                    if (NetworkImageView.this.mErrorImageId > 0) {
                        NetworkImageView networkImageView = NetworkImageView.this;
                        networkImageView.setImageResource(networkImageView.mErrorImageId);
                    }
                }
            };
        }
    }

    private int getResizedDimension(int i, int i2, int i3, int i4, ImageView.ScaleType scaleType) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            return i == 0 ? i3 : i;
        }
        if (i == 0) {
            return (int) (((double) i3) * (((double) i2) / ((double) i4)));
        }
        if (i2 == 0) {
            return i;
        }
        double d = ((double) i4) / ((double) i3);
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            double d2 = i2;
            return ((double) i) * d < d2 ? (int) (d2 / d) : i;
        }
        double d3 = i2;
        return ((double) i) * d > d3 ? (int) (d3 / d) : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageBitmapInMain(final Bitmap bitmap) {
        if (bitmap != null) {
            post(new Runnable() { // from class: com.tds.common.widgets.image.NetworkImageView.2
                @Override // java.lang.Runnable
                public void run() {
                    NetworkImageView.this.setImageBitmap(bitmap);
                }
            });
        }
    }
}
