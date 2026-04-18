package com.tds.common.notch.core;

import android.view.Window;
import com.tds.common.notch.helper.NotchStatusBarUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbsNotchScreenSupport implements INotchSupport {
    @Override // com.tds.common.notch.core.INotchSupport
    public int getStatusHeight(Window window) {
        return NotchStatusBarUtils.getStatusBarHeight(window.getContext());
    }
}
