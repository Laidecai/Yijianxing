package com.tds.common.widgets.image;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public interface ImageTarget {
    void onFailure(Throwable th);

    void onSuccess(Bitmap bitmap);
}
