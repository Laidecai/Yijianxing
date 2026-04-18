package com.taptap.services.update.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.taptap.services.update.R;
import com.tds.common.utils.NavigationBarUtil;

/* JADX INFO: loaded from: classes.dex */
public class LoadingDialog extends Dialog {
    private ImageView ivLoading;
    private Animation loadingAnimation;

    public LoadingDialog(Context context) {
        super(context);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            attributes.height = -1;
            attributes.gravity = 17;
            window.setAttributes(attributes);
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        setContentView(R.layout.tapupdate_dialog_loading);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        this.ivLoading = (ImageView) findViewById(R.id.iv_loading);
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 359.0f, 1, 0.5f, 1, 0.5f);
        this.loadingAnimation = rotateAnimation;
        rotateAnimation.setDuration(1500L);
        this.loadingAnimation.setRepeatCount(-1);
        this.loadingAnimation.setInterpolator(new LinearInterpolator());
    }

    @Override // android.app.Dialog
    public void show() {
        NavigationBarUtil.focusNotAle(getWindow());
        super.show();
        NavigationBarUtil.hideNavigationBar(getWindow());
        NavigationBarUtil.clearFocusNotAle(getWindow());
        this.ivLoading.startAnimation(this.loadingAnimation);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        Animation animation = this.loadingAnimation;
        if (animation != null) {
            animation.cancel();
            this.loadingAnimation = null;
        }
    }
}
