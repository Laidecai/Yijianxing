package com.tds.common.widgets;

import android.app.Dialog;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class HoloThemeHelper {
    public static void fixHoloDialogBlueLine(Dialog dialog) {
        try {
            View viewFindViewById = dialog.findViewById(dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null));
            if (viewFindViewById != null) {
                viewFindViewById.setBackgroundColor(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
