package com.taptap.services.update;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.taptap.services.update.utils.UIUtils;
import com.tds.common.utils.TapGameUtil;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateInstallConfirmDialog extends Dialog {
    private static final int DEFAULT_HEIGHT = 128;
    private static final int DEFAULT_WIDTH = 320;
    private FrameLayout flRootContainer;
    private TextView mNegativeButton;
    private TextView mPositiveButton;
    private TextView mTitle;

    enum TapUpdateConfirmDialogType {
        INSTALL_CONFIRM,
        INSTALL_FAIL
    }

    public TapUpdateInstallConfirmDialog(Context context) {
        super(context);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            attributes.height = -1;
            attributes.gravity = 17;
            window.setAttributes(attributes);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.requestFeature(1);
        }
        setContentView(R.layout.tapupdate_dialog_confrim);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView(context);
    }

    @Override // android.app.Dialog
    protected void onStart() {
        Configuration configuration;
        super.onStart();
        Resources resources = getContext().getResources();
        if (resources == null || (configuration = resources.getConfiguration()) == null) {
            return;
        }
        setupWindowAttribute(getContext(), 2 == configuration.orientation);
    }

    private void initView(final Context context) {
        this.flRootContainer = (FrameLayout) findViewById(R.id.fl_root_container);
        this.mTitle = (TextView) findViewById(R.id.tv_ready_tips);
        this.mNegativeButton = (TextView) findViewById(R.id.tv_func_negative);
        this.mPositiveButton = (TextView) findViewById(R.id.tv_func_positive);
        this.mNegativeButton.setText("取消");
        this.mNegativeButton.setOnClickListener(new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateInstallConfirmDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TapUpdateInstallConfirmDialog.this.dismiss();
                TapUpdateTracker.getInstance().trackInstallConfirmCancelButtonClick(TextUtils.equals(TapUpdateInstallConfirmDialog.this.mPositiveButton.getText(), "安装完成"));
            }
        });
        this.mPositiveButton.setOnClickListener(new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateInstallConfirmDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TapUpdateTracker.getInstance().trackInstallConfirmPositiveButtonClick(TextUtils.equals(TapUpdateInstallConfirmDialog.this.mPositiveButton.getText(), "安装完成"));
                if (!TapGameUtil.isTapTapInstalled(context)) {
                    if (TextUtils.equals(TapUpdateInstallConfirmDialog.this.mPositiveButton.getText(), "重新安装")) {
                        TapUpdateInstallConfirmDialog.this.dismiss();
                        return;
                    } else {
                        TapUpdateInstallConfirmDialog.this.switchState(TapUpdateConfirmDialogType.INSTALL_FAIL);
                        TapUpdateTracker.getInstance().trackNotInstallDialogVisible();
                        return;
                    }
                }
                TapUpdateDialogManager.getInstance().installSuccess(context);
                TapUpdateInstallConfirmDialog.this.dismiss();
            }
        });
    }

    public void switchState(TapUpdateConfirmDialogType tapUpdateConfirmDialogType) {
        if (tapUpdateConfirmDialogType == TapUpdateConfirmDialogType.INSTALL_CONFIRM) {
            this.mTitle.setText("TapTap 是否已经安装完成？");
            this.mPositiveButton.setText("安装完成");
        } else if (tapUpdateConfirmDialogType == TapUpdateConfirmDialogType.INSTALL_FAIL) {
            this.mTitle.setText("TapTap 尚未安装成功，请重新安装。");
            this.mPositiveButton.setText("重新安装");
        }
    }

    private void setupWindowAttribute(Context context, boolean z) {
        int iMin;
        ViewGroup.LayoutParams layoutParams = this.flRootContainer.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        int iDp2px = UIUtils.dp2px(15.0f);
        int screenWidth = UIUtils.getScreenWidth(context) - iDp2px;
        int screenHeight = UIUtils.getScreenHeight(context) - iDp2px;
        int i = -2;
        if (z) {
            iMin = Math.min(UIUtils.dp2px(320.0f), screenHeight);
            if (UIUtils.dp2px(128.0f) <= screenHeight) {
                screenHeight = -2;
            }
            i = screenHeight;
        } else {
            iMin = Math.min(UIUtils.dp2px(320.0f), screenWidth);
        }
        layoutParams.width = iMin;
        layoutParams.height = i;
        this.flRootContainer.setLayoutParams(layoutParams);
    }
}
