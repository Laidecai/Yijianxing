package com.tds.common.widgets.toast;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.tds.common.R;

/* JADX INFO: loaded from: classes.dex */
public enum TapToastManager {
    INSTANCE;

    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static final String TAG = "TDSToastManager";
    private static Handler handler;
    private static HandlerThread handlerThread;

    public static TapToastManager instance() {
        return INSTANCE;
    }

    public void show(Activity activity, String str, int i) {
        Toast toast = new Toast(activity);
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.tds_common_view_toast_message, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.tv_toast_message)).setText(str);
        toast.setView(viewInflate);
        if (i == 0) {
            toast.setDuration(0);
        } else if (i == 1) {
            toast.setDuration(1);
        }
        toast.setGravity(17, 0, 0);
        toast.show();
    }

    public void showWithTapIcon(Activity activity, String str) {
        TapToast.getInstance().show(activity, str);
    }
}
