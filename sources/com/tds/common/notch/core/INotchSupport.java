package com.tds.common.notch.core;

import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public interface INotchSupport {
    int getNotchHeight(Window window);

    int getNotchWidth(Window window);

    int getStatusHeight(Window window);

    boolean isNotchScreen(Window window);
}
