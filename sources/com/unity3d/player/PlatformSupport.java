package com.unity3d.player;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class PlatformSupport {
    static final boolean LOLLIPOP_SUPPORT;
    static final boolean MARSHMALLOW_SUPPORT;
    static final boolean NOUGAT_SUPPORT;

    static {
        LOLLIPOP_SUPPORT = Build.VERSION.SDK_INT >= 21;
        MARSHMALLOW_SUPPORT = Build.VERSION.SDK_INT >= 23;
        NOUGAT_SUPPORT = Build.VERSION.SDK_INT >= 24;
    }
}
