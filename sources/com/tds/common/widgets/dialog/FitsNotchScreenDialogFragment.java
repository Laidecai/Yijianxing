package com.tds.common.widgets.dialog;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.tds.common.notch.NotchTools;
import com.tds.common.utils.ActivityUtils;
import com.tds.common.utils.NavigationBarUtil;
import tds.androidx.core.view.WindowCompat;
import tds.androidx.core.view.WindowInsetsCompat;
import tds.androidx.core.view.WindowInsetsControllerCompat;

/* JADX INFO: loaded from: classes.dex */
public class FitsNotchScreenDialogFragment extends SafeDialogFragment {
    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, R.style.Theme.Material.Dialog.Alert);
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        setupDialogSize(-1, -1);
        super.onStart();
        adapterPhoneCompatibility();
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        adapterBlackScreenOnUnity();
    }

    protected void setupDialogSize(int i, int i2) {
        Dialog dialog;
        Window window;
        if (ActivityUtils.isActivityNotAlive(getActivity()) || (dialog = getDialog()) == null || (window = dialog.getWindow()) == null) {
            return;
        }
        setupWindowStyle(window);
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            attributes.width = i;
            attributes.height = i2;
            window.setAttributes(attributes);
        }
        window.setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }

    private void adapterPhoneCompatibility() {
        Window window;
        View decorView;
        Dialog dialog = getDialog();
        if (dialog == null || (window = dialog.getWindow()) == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        decorView.setPadding(0, 0, 0, 0);
    }

    private void adapterBlackScreenOnUnity() {
        Activity activity = getActivity();
        if (ActivityUtils.isActivityAlive(activity)) {
            activity.onWindowFocusChanged(true);
        }
    }

    private void setupWindowStyle(Window window) {
        WindowInsetsControllerCompat insetsController;
        WindowManager.LayoutParams attributes;
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 28 && NotchTools.getFullScreenTools().isNotchScreen(getActivity().getWindow()) && (attributes = window.getAttributes()) != null) {
            attributes.layoutInDisplayCutoutMode = 1;
            window.setAttributes(attributes);
        }
        WindowCompat.setDecorFitsSystemWindows(window, false);
        View decorView = window.getDecorView();
        if (decorView != null && (insetsController = WindowCompat.getInsetsController(window, decorView)) != null) {
            insetsController.hide(WindowInsetsCompat.Type.navigationBars());
            insetsController.setAppearanceLightStatusBars(true);
            insetsController.setAppearanceLightNavigationBars(true);
            insetsController.setSystemBarsBehavior(2);
        }
        window.setStatusBarColor(0);
        window.setFlags(16777216, 16777216);
        NavigationBarUtil.hideNavigationBar(window);
    }
}
