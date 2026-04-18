package com.unity3d.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class j extends View {
    final int a;
    final int b;
    Bitmap c;
    Bitmap d;

    /* JADX INFO: renamed from: com.unity3d.player.j$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[a.a().length];
            a = iArr;
            try {
                iArr[a.a - 1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[a.b - 1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[a.c - 1] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    static final class a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {1, 2, 3};

        public static int[] a() {
            return (int[]) d.clone();
        }
    }

    public j(Context context, int i) {
        super(context);
        this.a = i;
        int identifier = getResources().getIdentifier("unity_static_splash", "drawable", getContext().getPackageName());
        this.b = identifier;
        if (identifier != 0) {
            forceLayout();
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Bitmap bitmap = this.c;
        if (bitmap != null) {
            bitmap.recycle();
            this.c = null;
        }
        Bitmap bitmap2 = this.d;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.d = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x006c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onLayout(boolean r10, int r11, int r12, int r13, int r14) {
        /*
            Method dump skipped, instruction units count: 202
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.j.onLayout(boolean, int, int, int, int):void");
    }
}
