package com.tds.common.widgets.toast;

import android.R;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.tds.common.utils.UIUtils;
import com.tds.common.widgets.image.TdsImage;

/* JADX INFO: loaded from: classes.dex */
public class TapToastDialog extends DialogFragment {
    private String message;
    private TextView toastView;

    public static TapToastDialog instance(String str, String str2) {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("iconUrl", str);
        }
        bundle.putString("text", str2);
        TapToastDialog tapToastDialog = new TapToastDialog();
        tapToastDialog.setArguments(bundle);
        return tapToastDialog;
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, R.style.Theme.Material.Dialog.Alert);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(com.tds.common.R.layout.tds_common_tap_toast, viewGroup, false);
        this.toastView = (TextView) viewInflate.findViewById(com.tds.common.R.id.tv_tap_toast);
        ImageView imageView = (ImageView) viewInflate.findViewById(com.tds.common.R.id.iv_tap_toast);
        this.message = getArguments().getString("text", "");
        String string = getArguments().getString("iconUrl", null);
        if (string != null) {
            TdsImage.get(getActivity()).load(string).placeholder(com.tds.common.R.drawable.tds_common_tap_toast_avatar).roundCornerDp(100.0f).into(imageView);
        } else {
            imageView.setImageResource(com.tds.common.R.drawable.tds_common_tap_toast_avatar);
        }
        this.toastView.setText(this.message);
        return viewInflate;
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (getDialog() == null || getDialog().getWindow() == null) {
            return;
        }
        getDialog().getWindow().addFlags(8);
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onStart() {
        Window window;
        super.onStart();
        if (getDialog() == null || (window = getDialog().getWindow()) == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = getCurrentToastWidth(this.message);
        attributes.gravity = 49;
        attributes.y = UIUtils.dp2px(getActivity(), 50.0f);
        attributes.x = 0;
        attributes.windowAnimations = com.tds.common.R.style.tds_common_tap_toast;
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.clearFlags(2);
        window.addFlags(552);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        View decorView = window.getDecorView();
        if (Build.VERSION.SDK_INT >= 28) {
            attributes.layoutInDisplayCutoutMode = 1;
        }
        window.setAttributes(attributes);
        decorView.setSystemUiVisibility(1024);
        window.setFlags(1024, 1024);
    }

    private int getCurrentToastWidth(String str) {
        return Math.min(((int) this.toastView.getPaint().measureText(str)) + UIUtils.dp2px(getActivity(), 55.0f), (int) (((double) UIUtils.getRealScreenSize(getActivity()).x) * 0.8d));
    }
}
