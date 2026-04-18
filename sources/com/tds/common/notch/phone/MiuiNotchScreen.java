package com.tds.common.notch.phone;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.view.Window;
import com.tds.common.notch.core.AbsNotchScreenSupport;
import com.tds.common.notch.helper.NotchStatusBarUtils;
import com.tds.common.notch.helper.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
public class MiuiNotchScreen extends AbsNotchScreenSupport {
    @Override // com.tds.common.notch.core.INotchSupport
    public boolean isNotchScreen(Window window) {
        if (Build.VERSION.SDK_INT < 26) {
            return false;
        }
        return "1".equals(SystemProperties.getInstance().get("ro.miui.notch"));
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchHeight(Window window) {
        if (Build.VERSION.SDK_INT < 26 || !isNotchScreen(window) || window == null) {
            return 0;
        }
        Context context = window.getContext();
        if (isHideNotch(window.getContext())) {
            return NotchStatusBarUtils.getStatusBarHeight(context);
        }
        return getRealNotchHeight(context);
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchWidth(Window window) {
        if (Build.VERSION.SDK_INT < 26 || !isNotchScreen(window) || window == null) {
            return 0;
        }
        Context context = window.getContext();
        if (isHideNotch(window.getContext())) {
            return NotchStatusBarUtils.getStatusBarHeight(context);
        }
        return getRealNotchWidth(context);
    }

    private int getRealNotchHeight(Context context) {
        int identifier = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private int getRealNotchWidth(Context context) {
        int identifier = context.getResources().getIdentifier("notch_width", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private boolean isHideNotch(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_black", 0) == 1;
    }
}
