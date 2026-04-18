package com.tds.common.widgets.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.tds.common.io.IoUtil;
import com.tds.common.net.TdsHttp;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.rxandroid.schedulers.AndroidSchedulers;
import com.tds.common.reactor.schedulers.Schedulers;
import com.tds.common.utils.UIUtils;
import com.tds.common.widgets.image.DiskCache;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class LoadBuilder {
    Drawable errorDrawable;
    Drawable placeholderDrawable;
    final int resId;
    final TdsImage tdsImage;
    final Uri uri;
    int placeholderResId = 0;
    int errorRedId = 0;
    int roundCornerRadius = 0;
    int width = 0;
    int height = 0;
    ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;
    boolean circle = false;

    LoadBuilder(TdsImage tdsImage, Uri uri, int i) {
        this.tdsImage = tdsImage;
        this.uri = uri;
        this.resId = i;
    }

    public LoadBuilder placeholder(int i) {
        this.placeholderResId = i;
        return this;
    }

    public LoadBuilder placeholder(Drawable drawable) {
        this.placeholderDrawable = drawable;
        return this;
    }

    public LoadBuilder error(int i) {
        this.errorRedId = i;
        return this;
    }

    public LoadBuilder error(Drawable drawable) {
        this.errorDrawable = drawable;
        return this;
    }

    public LoadBuilder roundCornerPx(int i) {
        this.roundCornerRadius = Math.max(0, i);
        return this;
    }

    public LoadBuilder roundCornerDp(float f) {
        return roundCornerPx(UIUtils.dp2px(this.tdsImage.context, f));
    }

    public LoadBuilder setCircle(boolean z) {
        this.circle = true;
        return this;
    }

    public LoadBuilder scale(int i, int i2, ImageView.ScaleType scaleType) {
        this.width = i;
        this.height = i2;
        this.scaleType = scaleType;
        return this;
    }

    public String getKey() {
        Uri uri = this.uri;
        if (uri != null) {
            String string = uri.toString();
            if (this.width * this.height != 0) {
                string = string + "#W" + this.width + "#H" + this.height;
            }
            String str = string + "#S" + this.scaleType.ordinal();
            if (this.circle) {
                str = str + "#C";
            } else if (this.roundCornerRadius > 0) {
                str = str + "#R" + this.roundCornerRadius;
            }
            return getMd5(str);
        }
        return getMd5(String.valueOf(this.resId));
    }

    public String getScaleKey() {
        if (this.uri != null) {
            if (this.width * this.height != 0) {
                return getMd5(this.uri + "#W" + this.width + "#H" + this.height + "#S" + this.scaleType.ordinal());
            }
            return getDiskKey();
        }
        return getMd5(String.valueOf(this.resId));
    }

    public String getDiskKey() {
        Uri uri = this.uri;
        if (uri != null) {
            return getMd5(uri.toString());
        }
        return getMd5(String.valueOf(this.resId));
    }

    private String getMd5(String str) {
        return ImageUtil.getMd5(str);
    }

    public void into(ImageView imageView) {
        ImageUtil.checkMain();
        if (this.uri == null && this.resId == 0) {
            int i = this.errorRedId;
            if (i != 0) {
                imageView.setImageResource(i);
                return;
            }
            Drawable drawable = this.errorDrawable;
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
                return;
            }
            return;
        }
        this.tdsImage.cancelRequest(imageView);
        Bitmap bitmap = this.tdsImage.memCache.get(getKey());
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        int i2 = this.placeholderResId;
        if (i2 != 0) {
            imageView.setImageResource(i2);
        } else {
            Drawable drawable2 = this.placeholderDrawable;
            if (drawable2 != null) {
                imageView.setImageDrawable(drawable2);
            }
        }
        this.tdsImage.enqueue(imageView, loadImageInto(imageView));
    }

    public void into(ImageTarget imageTarget) {
        ImageUtil.checkMain();
        if (imageTarget == null) {
            return;
        }
        if (this.uri == null && this.resId == 0) {
            imageTarget.onFailure(new Exception("uri == null && resId == 0"));
            return;
        }
        this.tdsImage.cancelRequest(imageTarget);
        Bitmap bitmap = this.tdsImage.memCache.get(getKey());
        if (bitmap != null) {
            imageTarget.onSuccess(bitmap);
        } else {
            this.tdsImage.enqueue(imageTarget, loadImageInto(imageTarget));
        }
    }

    /* JADX INFO: renamed from: com.tds.common.widgets.image.LoadBuilder$1 */
    class AnonymousClass1 implements ImageTarget {
        final /* synthetic */ WeakReference val$view;

        AnonymousClass1(WeakReference weakReference) {
            weakReference = weakReference;
        }

        @Override // com.tds.common.widgets.image.ImageTarget
        public void onSuccess(Bitmap bitmap) {
            ImageView imageView = (ImageView) weakReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override // com.tds.common.widgets.image.ImageTarget
        public void onFailure(Throwable th) {
            ImageView imageView = (ImageView) weakReference.get();
            if (imageView != null) {
                if (LoadBuilder.this.errorRedId != 0) {
                    imageView.setImageResource(LoadBuilder.this.errorRedId);
                } else if (LoadBuilder.this.errorDrawable != null) {
                    imageView.setImageDrawable(LoadBuilder.this.errorDrawable);
                }
            }
        }
    }

    private Subscription loadImageInto(ImageView imageView) {
        return loadImageInto(new ImageTarget() { // from class: com.tds.common.widgets.image.LoadBuilder.1
            final /* synthetic */ WeakReference val$view;

            AnonymousClass1(WeakReference weakReference) {
                weakReference = weakReference;
            }

            @Override // com.tds.common.widgets.image.ImageTarget
            public void onSuccess(Bitmap bitmap) {
                ImageView imageView2 = (ImageView) weakReference.get();
                if (imageView2 != null) {
                    imageView2.setImageBitmap(bitmap);
                }
            }

            @Override // com.tds.common.widgets.image.ImageTarget
            public void onFailure(Throwable th) {
                ImageView imageView2 = (ImageView) weakReference.get();
                if (imageView2 != null) {
                    if (LoadBuilder.this.errorRedId != 0) {
                        imageView2.setImageResource(LoadBuilder.this.errorRedId);
                    } else if (LoadBuilder.this.errorDrawable != null) {
                        imageView2.setImageDrawable(LoadBuilder.this.errorDrawable);
                    }
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.tds.common.widgets.image.LoadBuilder$3 */
    class AnonymousClass3 implements Observable.OnSubscribe<Bitmap> {
        AnonymousClass3() {
        }

        @Override // com.tds.common.reactor.functions.Action1
        public void call(Subscriber<? super Bitmap> subscriber) {
            try {
                Bitmap bitmapLoadBitmap = loadBitmap();
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onNext(postProcess(bitmapLoadBitmap));
                subscriber.onCompleted();
            } catch (IOException e) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onError(e);
            }
        }

        private Bitmap postProcess(Bitmap bitmap) {
            if (LoadBuilder.this.circle) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(bitmapShader);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                canvas.drawColor(0);
                canvas.drawCircle((bitmapCreateBitmap.getWidth() * 1.0f) / 2.0f, (bitmapCreateBitmap.getWidth() * 1.0f) / 2.0f, (bitmapCreateBitmap.getWidth() * 1.0f) / 2.0f, paint);
                canvas.setBitmap(null);
                LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getKey(), bitmapCreateBitmap);
                bitmap.recycle();
                return bitmapCreateBitmap;
            }
            if (LoadBuilder.this.roundCornerRadius <= 0) {
                return bitmap;
            }
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            BitmapShader bitmapShader2 = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setShader(bitmapShader2);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            canvas2.drawColor(0);
            canvas2.drawRoundRect(new RectF(0.0f, 0.0f, bitmapCreateBitmap2.getWidth(), bitmapCreateBitmap2.getHeight()), LoadBuilder.this.roundCornerRadius, LoadBuilder.this.roundCornerRadius, paint2);
            canvas2.setBitmap(null);
            LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getKey(), bitmapCreateBitmap2);
            bitmap.recycle();
            return bitmapCreateBitmap2;
        }

        private Bitmap loadBitmap() throws IOException {
            Bitmap bitmapDecodeResource;
            if (LoadBuilder.this.resId != 0 && (bitmapDecodeResource = BitmapFactory.decodeResource(LoadBuilder.this.tdsImage.context.getResources(), LoadBuilder.this.resId)) != null) {
                LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getKey(), bitmapDecodeResource);
                return bitmapDecodeResource;
            }
            Bitmap bitmapLoadFromDisk = loadFromDisk();
            return bitmapLoadFromDisk != null ? bitmapLoadFromDisk : loadAndCacheFromNet();
        }

        private Bitmap loadFromDisk() throws IOException {
            File file;
            Bitmap scaleBitmap;
            if (!"file".equals(LoadBuilder.this.uri.getScheme())) {
                file = LoadBuilder.this.tdsImage.diskCache.get(LoadBuilder.this.getDiskKey());
            } else {
                file = new File(LoadBuilder.this.uri.getPath());
            }
            if (file == null) {
                return null;
            }
            if (LoadBuilder.this.width * LoadBuilder.this.height == 0) {
                scaleBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            } else {
                scaleBitmap = getScaleBitmap(file);
            }
            if (scaleBitmap != null) {
                LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getScaleKey(), scaleBitmap);
            }
            return scaleBitmap;
        }

        private Bitmap getScaleBitmap(File file) {
            if (file == null || !file.exists()) {
                return null;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            int i = options.outWidth;
            int i2 = options.outHeight;
            int resizedDimension = getResizedDimension(LoadBuilder.this.width, LoadBuilder.this.height, i, i2, LoadBuilder.this.scaleType);
            int resizedDimension2 = getResizedDimension(LoadBuilder.this.height, LoadBuilder.this.width, i2, i, LoadBuilder.this.scaleType);
            options.inJustDecodeBounds = false;
            options.inSampleSize = findBestSampleSize(i, i2, resizedDimension, resizedDimension2);
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            if (bitmapDecodeFile == null) {
                return null;
            }
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeFile, resizedDimension, resizedDimension2, true);
            bitmapDecodeFile.recycle();
            return bitmapCreateScaledBitmap;
        }

        private int findBestSampleSize(int i, int i2, int i3, int i4) {
            double dMin = Math.min(((double) i) / ((double) i3), ((double) i2) / ((double) i4));
            float f = 1.0f;
            while (true) {
                float f2 = 2.0f * f;
                if (f2 > dMin) {
                    return (int) f;
                }
                f = f2;
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

        private Bitmap loadAndCacheFromNet() throws IOException {
            TdsHttp.Response responseExecute = null;
            try {
                responseExecute = LoadBuilder.this.tdsImage.client.newCall(new TdsHttp.Request.Builder().url(LoadBuilder.this.uri.toString()).build()).execute();
                LoadBuilder.this.tdsImage.diskCache.put(LoadBuilder.this.getDiskKey(), new DiskCache.Writer() { // from class: com.tds.common.widgets.image.LoadBuilder.3.1
                    final /* synthetic */ TdsHttp.Response val$finalResponse;

                    AnonymousClass1(TdsHttp.Response responseExecute2) {
                        response = responseExecute2;
                    }

                    @Override // com.tds.common.widgets.image.DiskCache.Writer
                    public boolean write(File file) throws Throwable {
                        IoUtil.copy(response.body().byteStream(), file);
                        return true;
                    }
                });
                return loadFromDisk();
            } finally {
                if (responseExecute2 != null) {
                    responseExecute2.close();
                }
            }
        }

        /* JADX INFO: renamed from: com.tds.common.widgets.image.LoadBuilder$3$1 */
        class AnonymousClass1 implements DiskCache.Writer {
            final /* synthetic */ TdsHttp.Response val$finalResponse;

            AnonymousClass1(TdsHttp.Response responseExecute2) {
                response = responseExecute2;
            }

            @Override // com.tds.common.widgets.image.DiskCache.Writer
            public boolean write(File file) throws Throwable {
                IoUtil.copy(response.body().byteStream(), file);
                return true;
            }
        }
    }

    private Subscription loadImageInto(ImageTarget imageTarget) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() { // from class: com.tds.common.widgets.image.LoadBuilder.3
            AnonymousClass3() {
            }

            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {
                    Bitmap bitmapLoadBitmap = loadBitmap();
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onNext(postProcess(bitmapLoadBitmap));
                    subscriber.onCompleted();
                } catch (IOException e) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }
                    subscriber.onError(e);
                }
            }

            private Bitmap postProcess(Bitmap bitmap) {
                if (LoadBuilder.this.circle) {
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                    BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setAntiAlias(true);
                    paint.setShader(bitmapShader);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    canvas.drawColor(0);
                    canvas.drawCircle((bitmapCreateBitmap.getWidth() * 1.0f) / 2.0f, (bitmapCreateBitmap.getWidth() * 1.0f) / 2.0f, (bitmapCreateBitmap.getWidth() * 1.0f) / 2.0f, paint);
                    canvas.setBitmap(null);
                    LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getKey(), bitmapCreateBitmap);
                    bitmap.recycle();
                    return bitmapCreateBitmap;
                }
                if (LoadBuilder.this.roundCornerRadius <= 0) {
                    return bitmap;
                }
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                BitmapShader bitmapShader2 = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint2 = new Paint();
                paint2.setAntiAlias(true);
                paint2.setShader(bitmapShader2);
                Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                canvas2.drawColor(0);
                canvas2.drawRoundRect(new RectF(0.0f, 0.0f, bitmapCreateBitmap2.getWidth(), bitmapCreateBitmap2.getHeight()), LoadBuilder.this.roundCornerRadius, LoadBuilder.this.roundCornerRadius, paint2);
                canvas2.setBitmap(null);
                LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getKey(), bitmapCreateBitmap2);
                bitmap.recycle();
                return bitmapCreateBitmap2;
            }

            private Bitmap loadBitmap() throws IOException {
                Bitmap bitmapDecodeResource;
                if (LoadBuilder.this.resId != 0 && (bitmapDecodeResource = BitmapFactory.decodeResource(LoadBuilder.this.tdsImage.context.getResources(), LoadBuilder.this.resId)) != null) {
                    LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getKey(), bitmapDecodeResource);
                    return bitmapDecodeResource;
                }
                Bitmap bitmapLoadFromDisk = loadFromDisk();
                return bitmapLoadFromDisk != null ? bitmapLoadFromDisk : loadAndCacheFromNet();
            }

            private Bitmap loadFromDisk() throws IOException {
                File file;
                Bitmap scaleBitmap;
                if (!"file".equals(LoadBuilder.this.uri.getScheme())) {
                    file = LoadBuilder.this.tdsImage.diskCache.get(LoadBuilder.this.getDiskKey());
                } else {
                    file = new File(LoadBuilder.this.uri.getPath());
                }
                if (file == null) {
                    return null;
                }
                if (LoadBuilder.this.width * LoadBuilder.this.height == 0) {
                    scaleBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                } else {
                    scaleBitmap = getScaleBitmap(file);
                }
                if (scaleBitmap != null) {
                    LoadBuilder.this.tdsImage.memCache.put(LoadBuilder.this.getScaleKey(), scaleBitmap);
                }
                return scaleBitmap;
            }

            private Bitmap getScaleBitmap(File file) {
                if (file == null || !file.exists()) {
                    return null;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                int i = options.outWidth;
                int i2 = options.outHeight;
                int resizedDimension = getResizedDimension(LoadBuilder.this.width, LoadBuilder.this.height, i, i2, LoadBuilder.this.scaleType);
                int resizedDimension2 = getResizedDimension(LoadBuilder.this.height, LoadBuilder.this.width, i2, i, LoadBuilder.this.scaleType);
                options.inJustDecodeBounds = false;
                options.inSampleSize = findBestSampleSize(i, i2, resizedDimension, resizedDimension2);
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                if (bitmapDecodeFile == null) {
                    return null;
                }
                Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeFile, resizedDimension, resizedDimension2, true);
                bitmapDecodeFile.recycle();
                return bitmapCreateScaledBitmap;
            }

            private int findBestSampleSize(int i, int i2, int i3, int i4) {
                double dMin = Math.min(((double) i) / ((double) i3), ((double) i2) / ((double) i4));
                float f = 1.0f;
                while (true) {
                    float f2 = 2.0f * f;
                    if (f2 > dMin) {
                        return (int) f;
                    }
                    f = f2;
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

            private Bitmap loadAndCacheFromNet() throws IOException {
                TdsHttp.Response responseExecute2 = null;
                try {
                    responseExecute2 = LoadBuilder.this.tdsImage.client.newCall(new TdsHttp.Request.Builder().url(LoadBuilder.this.uri.toString()).build()).execute();
                    LoadBuilder.this.tdsImage.diskCache.put(LoadBuilder.this.getDiskKey(), new DiskCache.Writer() { // from class: com.tds.common.widgets.image.LoadBuilder.3.1
                        final /* synthetic */ TdsHttp.Response val$finalResponse;

                        AnonymousClass1(TdsHttp.Response responseExecute22) {
                            response = responseExecute22;
                        }

                        @Override // com.tds.common.widgets.image.DiskCache.Writer
                        public boolean write(File file) throws Throwable {
                            IoUtil.copy(response.body().byteStream(), file);
                            return true;
                        }
                    });
                    return loadFromDisk();
                } finally {
                    if (responseExecute22 != null) {
                        responseExecute22.close();
                    }
                }
            }

            /* JADX INFO: renamed from: com.tds.common.widgets.image.LoadBuilder$3$1 */
            class AnonymousClass1 implements DiskCache.Writer {
                final /* synthetic */ TdsHttp.Response val$finalResponse;

                AnonymousClass1(TdsHttp.Response responseExecute22) {
                    response = responseExecute22;
                }

                @Override // com.tds.common.widgets.image.DiskCache.Writer
                public boolean write(File file) throws Throwable {
                    IoUtil.copy(response.body().byteStream(), file);
                    return true;
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Subscriber) new Subscriber<Bitmap>() { // from class: com.tds.common.widgets.image.LoadBuilder.2
            final /* synthetic */ ImageTarget val$target;

            @Override // com.tds.common.reactor.Observer
            public void onCompleted() {
            }

            AnonymousClass2(ImageTarget imageTarget2) {
                imageTarget = imageTarget2;
            }

            @Override // com.tds.common.reactor.Observer
            public void onError(Throwable th) {
                imageTarget.onFailure(th);
            }

            @Override // com.tds.common.reactor.Observer
            public void onNext(Bitmap bitmap) {
                imageTarget.onSuccess(bitmap);
            }
        });
    }

    /* JADX INFO: renamed from: com.tds.common.widgets.image.LoadBuilder$2 */
    class AnonymousClass2 extends Subscriber<Bitmap> {
        final /* synthetic */ ImageTarget val$target;

        @Override // com.tds.common.reactor.Observer
        public void onCompleted() {
        }

        AnonymousClass2(ImageTarget imageTarget2) {
            imageTarget = imageTarget2;
        }

        @Override // com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            imageTarget.onFailure(th);
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(Bitmap bitmap) {
            imageTarget.onSuccess(bitmap);
        }
    }
}
