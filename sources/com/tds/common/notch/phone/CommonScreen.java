package com.tds.common.notch.phone;

import android.view.Window;
import com.tds.common.notch.core.AbsNotchScreenSupport;

/* JADX INFO: loaded from: classes.dex */
public class CommonScreen extends AbsNotchScreenSupport {
    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchHeight(Window window) {
        return 0;
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchWidth(Window window) {
        return 0;
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public boolean isNotchScreen(Window window) {
        return false;
    }
}
