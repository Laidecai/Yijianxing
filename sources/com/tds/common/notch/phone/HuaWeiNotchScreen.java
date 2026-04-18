package com.tds.common.notch.phone;

import android.os.Build;
import android.view.Window;
import com.tds.common.notch.core.AbsNotchScreenSupport;

/* JADX INFO: loaded from: classes.dex */
public class HuaWeiNotchScreen extends AbsNotchScreenSupport {
    private static final String DISPLAY_NOTCH_STATUS = "display_notch_status";
    private static final String TAG = "HuaWeiNotchScreen";

    @Override // com.tds.common.notch.core.INotchSupport
    public boolean isNotchScreen(Window window) {
        if (Build.VERSION.SDK_INT < 26) {
            return false;
        }
        try {
            Class<?> clsLoadClass = window.getContext().getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            return ((Boolean) clsLoadClass.getMethod("hasNotchInScreen", new Class[0]).invoke(clsLoadClass, new Object[0])).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchHeight(Window window) {
        if (Build.VERSION.SDK_INT < 26 || !isNotchScreen(window)) {
            return 0;
        }
        int[] iArr = {0, 0};
        try {
            Class<?> clsLoadClass = window.getContext().getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            iArr = (int[]) clsLoadClass.getMethod("getNotchSize", new Class[0]).invoke(clsLoadClass, new Object[0]);
        } catch (Exception unused) {
        }
        if (iArr != null) {
            return iArr[1];
        }
        return 0;
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchWidth(Window window) {
        if (Build.VERSION.SDK_INT < 26 || !isNotchScreen(window)) {
            return 0;
        }
        int[] iArr = {0, 0};
        try {
            Class<?> clsLoadClass = window.getContext().getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            iArr = (int[]) clsLoadClass.getMethod("getNotchSize", new Class[0]).invoke(clsLoadClass, new Object[0]);
        } catch (Exception unused) {
        }
        if (iArr != null) {
            return iArr[0];
        }
        return 0;
    }
}
