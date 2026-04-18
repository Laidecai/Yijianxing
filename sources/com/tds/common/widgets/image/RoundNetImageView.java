package com.tds.common.widgets.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.tds.common.utils.UIUtils;

/* JADX INFO: loaded from: classes.dex */
public class RoundNetImageView extends NetworkImageView {
    int borderColor;
    int borderSize;
    Paint paint;
    int radius;
    Rect rectDest;
    Rect rectSrc;

    public RoundNetImageView(Context context) {
        super(context);
        this.radius = 50;
        this.borderSize = 0;
        this.borderColor = -1;
        this.paint = new Paint();
    }

    public RoundNetImageView(Context context, int i) {
        super(context);
        this.radius = 50;
        this.borderSize = 0;
        this.borderColor = -1;
        this.paint = new Paint();
        this.radius = i;
    }

    public RoundNetImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.radius = 50;
        this.borderSize = 0;
        this.borderColor = -1;
        this.paint = new Paint();
    }

    public RoundNetImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.radius = 50;
        this.borderSize = 0;
        this.borderColor = -1;
        this.paint = new Paint();
    }

    public void setRadius(int i) {
        this.radius = i;
    }

    public void setBorderSize(int i) {
        this.borderSize = i;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap roundBitmapByShader = getRoundBitmapByShader(getBitmapFromDrawable(drawable), getWidth(), getHeight(), this.radius, this.borderSize);
            Rect rect = this.rectSrc;
            if (rect == null || rect.width() != roundBitmapByShader.getWidth() || this.rectSrc.height() != roundBitmapByShader.getHeight()) {
                this.rectSrc = new Rect(0, 0, roundBitmapByShader.getWidth(), roundBitmapByShader.getHeight());
            }
            Rect rect2 = this.rectDest;
            if (rect2 == null || rect2.width() != getWidth() || this.rectDest.height() != getHeight()) {
                this.rectDest = new Rect(0, 0, getWidth(), getHeight());
            }
            canvas.drawBitmap(roundBitmapByShader, this.rectSrc, this.rectDest, this.paint);
            return;
        }
        super.onDraw(canvas);
    }

    public void setBitmapAlpha(float f) {
        this.paint.reset();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setAlpha((int) (f * 255.0f));
        postInvalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : UIUtils.dp2px(getContext(), 28.0f), drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : UIUtils.dp2px(getContext(), 28.0f), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        drawable.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    private Bitmap getRoundBitmapByShader(Bitmap bitmap, int i, int i2, int i3, int i4) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale((i * 1.0f) / bitmap.getWidth(), (i2 * 1.0f) / bitmap.getHeight());
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(1);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        float f = i4;
        RectF rectF = new RectF(f, f, i - i4, i2 - i4);
        float f2 = i3;
        canvas.drawRoundRect(rectF, f2, f2, paint);
        if (i4 > 0) {
            Paint paint2 = new Paint(1);
            paint2.setColor(this.borderColor);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeWidth(f);
            canvas.drawRoundRect(rectF, f2, f2, paint2);
        }
        return bitmapCreateBitmap;
    }
}
