package com.tds.common.notch.phone;

import android.os.Build;
import android.view.Window;
import com.tds.common.notch.core.AbsNotchScreenSupport;
import com.tds.common.notch.helper.NotchStatusBarUtils;
import com.tds.common.utils.UIUtils;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class VivoNotchScreen extends AbsNotchScreenSupport {
    private final int DEFAULT_NOTCH_WIDTH = 100;
    private Class mClass;
    private Method mMethod;

    @Override // com.tds.common.notch.core.INotchSupport
    public boolean isNotchScreen(Window window) {
        if (Build.VERSION.SDK_INT < 26 || window == null) {
            return false;
        }
        try {
            Class<?> clsLoadClass = window.getContext().getClassLoader().loadClass("android.util.FtFeature");
            this.mClass = clsLoadClass;
            Method method = clsLoadClass.getMethod("isFeatureSupport", Integer.TYPE);
            this.mMethod = method;
            return ((Boolean) method.invoke(this.mClass, 32)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchHeight(Window window) {
        if (Build.VERSION.SDK_INT >= 26 && isNotchScreen(window)) {
            return NotchStatusBarUtils.getStatusBarHeight(window.getContext());
        }
        return 0;
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchWidth(Window window) {
        return UIUtils.dp2px(window.getContext(), 100.0f);
    }
}
