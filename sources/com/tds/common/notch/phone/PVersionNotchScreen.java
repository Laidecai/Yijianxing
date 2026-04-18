package com.tds.common.notch.phone;

import android.os.Build;
import android.view.DisplayCutout;
import android.view.Window;
import android.view.WindowInsets;
import com.tds.common.notch.core.AbsNotchScreenSupport;

/* JADX INFO: loaded from: classes.dex */
public class PVersionNotchScreen extends AbsNotchScreenSupport {
    @Override // com.tds.common.notch.core.INotchSupport
    public boolean isNotchScreen(Window window) {
        WindowInsets rootWindowInsets;
        DisplayCutout displayCutout;
        return (Build.VERSION.SDK_INT < 28 || (rootWindowInsets = window.getDecorView().getRootWindowInsets()) == null || (displayCutout = rootWindowInsets.getDisplayCutout()) == null || displayCutout.getBoundingRects() == null) ? false : true;
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchHeight(Window window) {
        WindowInsets rootWindowInsets;
        DisplayCutout displayCutout;
        int safeInsetTop;
        if (Build.VERSION.SDK_INT < 28 || (rootWindowInsets = window.getDecorView().getRootWindowInsets()) == null || (displayCutout = rootWindowInsets.getDisplayCutout()) == null || displayCutout.getBoundingRects() == null) {
            return 0;
        }
        try {
            if (window.getContext().getResources().getConfiguration().orientation == 2) {
                safeInsetTop = displayCutout.getSafeInsetLeft() == 0 ? displayCutout.getSafeInsetRight() : displayCutout.getSafeInsetLeft();
            } else {
                safeInsetTop = displayCutout.getSafeInsetTop();
            }
            return safeInsetTop;
        } catch (Exception unused) {
            return displayCutout.getSafeInsetTop();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v8 */
    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchWidth(Window window) {
        WindowInsets rootWindowInsets;
        int displayCutout;
        int safeInsetRight;
        if (Build.VERSION.SDK_INT < 28 || (rootWindowInsets = window.getDecorView().getRootWindowInsets()) == null || (displayCutout = rootWindowInsets.getDisplayCutout()) == 0 || displayCutout.getBoundingRects() == null) {
            return 0;
        }
        try {
            if (window.getContext().getResources().getConfiguration().orientation == 2) {
                safeInsetRight = displayCutout.getSafeInsetBottom();
                displayCutout = displayCutout.getSafeInsetTop();
            } else {
                safeInsetRight = displayCutout.getSafeInsetRight();
                displayCutout = displayCutout.getSafeInsetLeft();
            }
        } catch (Exception unused) {
            safeInsetRight = displayCutout.getSafeInsetRight();
            displayCutout = displayCutout.getSafeInsetLeft();
        }
        return safeInsetRight - displayCutout;
    }
}
