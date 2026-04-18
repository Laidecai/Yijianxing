package com.taptap.services.update;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;
import com.taptap.services.update.bean.TapUpdateFuncLink;
import com.taptap.services.update.bean.UIInformation;
import com.taptap.services.update.utils.UIUtils;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateDialog extends Dialog {
    private static final int DEFAULT_HEIGHT = 128;
    private static final int DEFAULT_WIDTH = 320;
    private TapUpdateDialogState dialogState;
    private AnimatorSet downloadAnimatorSet;
    private FrameLayout flRootContainer;
    private FlexboxLayout flexLinkContainer;
    private ImageView ivDownloadSpeedTriangle1;
    private ImageView ivDownloadSpeedTriangle2;
    private ImageView ivIconRetry;
    private ImageView ivRequestError;
    private ImageView ivUpdateCancel;
    private final int linkTextColor;
    private LinearLayout llDownloadContainer;
    private LinearLayout llFuncContainer;
    private LinearLayout llProgressContainer;
    private LinearLayout llReadyContainer;
    private LinearLayout llRetry;
    private LinearLayout llSpeedTriangle;
    private IUpdateCancelListener mUpdateCancelListener;
    private ProgressBar pbDownloadProgress;
    private TextView tvDeveloperInfo;
    private TextView tvDownloadContentTitle;
    private TextView tvDownloadSpeed;
    private TextView tvErrorTips;
    private TextView tvFuncButton;
    private TextView tvFuncNegative;
    private TextView tvFuncPositive;
    private TextView tvFuncTips;
    private TextView tvOtherTips;
    private TextView tvReadyTips;
    private TextView tvUpdateTitle;

    public interface IUpdateCancelListener {
        void onUpdateCancel(TapUpdateDialogState tapUpdateDialogState, boolean z);
    }

    enum TapUpdateDialogState {
        NONE,
        API_ERROR,
        CONFIRM,
        DOWNLOAD,
        INSTALL,
        UPDATE
    }

    public TapUpdateDialog(Context context) {
        super(context);
        this.linkTextColor = Color.parseColor("#4B4B4B");
        this.dialogState = TapUpdateDialogState.NONE;
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
        setContentView(R.layout.tapupdate_dialog_update);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView(context);
    }

    public void setUpdateCancelListener(IUpdateCancelListener iUpdateCancelListener) {
        this.mUpdateCancelListener = iUpdateCancelListener;
    }

    public void showCloseButton(boolean z) {
        ImageView imageView = this.ivUpdateCancel;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 8);
        }
    }

    public TapUpdateDialogState getDialogState() {
        return this.dialogState;
    }

    public void setDialogState(TapUpdateDialogState tapUpdateDialogState) {
        this.dialogState = tapUpdateDialogState;
    }

    public void onDownloadReady(boolean z, View.OnClickListener onClickListener) {
        LinearLayout linearLayout = this.llReadyContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        LinearLayout linearLayout2 = this.llDownloadContainer;
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
        if (z) {
            setDialogState(TapUpdateDialogState.CONFIRM);
            ImageView imageView = this.ivRequestError;
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            TextView textView = this.tvReadyTips;
            if (textView != null) {
                textView.setText("使用 TapTap 更新游戏？");
            }
            TextView textView2 = this.tvFuncPositive;
            if (textView2 != null) {
                textView2.setText("更新");
            }
            TapUpdateTracker.getInstance().trackConfirmDialogVisible();
        } else {
            setDialogState(TapUpdateDialogState.API_ERROR);
            ImageView imageView2 = this.ivRequestError;
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            TextView textView3 = this.tvReadyTips;
            if (textView3 != null) {
                textView3.setText("请求失败，请重试");
            }
            TextView textView4 = this.tvFuncPositive;
            if (textView4 != null) {
                textView4.setText("点击重试");
            }
            TapUpdateTracker.getInstance().trackRequestFailedDialogVisible();
        }
        TextView textView5 = this.tvFuncPositive;
        if (textView5 != null) {
            textView5.setOnClickListener(onClickListener);
        }
    }

    public void showDownloadPage() {
        setDialogState(TapUpdateDialogState.DOWNLOAD);
        LinearLayout linearLayout = this.llReadyContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        LinearLayout linearLayout2 = this.llDownloadContainer;
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(0);
        }
        LinearLayout linearLayout3 = this.llProgressContainer;
        if (linearLayout3 != null) {
            linearLayout3.setVisibility(0);
        }
        LinearLayout linearLayout4 = this.llFuncContainer;
        if (linearLayout4 != null) {
            linearLayout4.setVisibility(8);
        }
        showDownloadSpeedAnim();
    }

    public void updateUIInformation(UIInformation uIInformation) {
        if (uIInformation == null) {
            return;
        }
        TextView textView = this.tvUpdateTitle;
        if (textView != null) {
            textView.setText(uIInformation.getUpdateTitle());
        }
        TextView textView2 = this.tvDeveloperInfo;
        if (textView2 != null) {
            textView2.setText(uIInformation.getAppInfo());
        }
        List<TapUpdateFuncLink> linkList = uIInformation.getLinkList();
        if (linkList != null && !linkList.isEmpty()) {
            FlexboxLayout flexboxLayout = this.flexLinkContainer;
            if (flexboxLayout != null) {
                flexboxLayout.removeAllViews();
                this.flexLinkContainer.setVisibility(0);
                Iterator<TapUpdateFuncLink> it = linkList.iterator();
                while (it.hasNext()) {
                    this.flexLinkContainer.addView(generateLinkView(it.next()));
                }
                return;
            }
            return;
        }
        FlexboxLayout flexboxLayout2 = this.flexLinkContainer;
        if (flexboxLayout2 != null) {
            flexboxLayout2.setVisibility(8);
        }
    }

    public void updateDownloadContentTitle(String str) {
        TextView textView = this.tvDownloadContentTitle;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void updateSpeed(String str) {
        this.tvDownloadSpeed.setText(str);
        if (this.llSpeedTriangle.getVisibility() != 0) {
            this.llSpeedTriangle.setVisibility(0);
        }
        showDownloadSpeedAnim();
    }

    public void setMaxProgress(int i) {
        ProgressBar progressBar = this.pbDownloadProgress;
        if (progressBar != null) {
            progressBar.setMax(i);
        }
    }

    public void updateProgress(int i) {
        ProgressBar progressBar = this.pbDownloadProgress;
        if (progressBar != null) {
            progressBar.setProgress(i);
        }
    }

    public void showDownloadErrorTips(CharSequence charSequence, final boolean z, CharSequence charSequence2, final View.OnClickListener onClickListener) {
        if (TextUtils.isEmpty(charSequence)) {
            TextView textView = this.tvErrorTips;
            if (textView != null) {
                textView.setVisibility(8);
                this.tvErrorTips.setText("");
            }
        } else {
            TextView textView2 = this.tvErrorTips;
            if (textView2 != null) {
                textView2.setVisibility(0);
                this.tvErrorTips.setText(charSequence);
            }
            stopAnim();
        }
        stopRetryAnim();
        LinearLayout linearLayout = this.llRetry;
        if (linearLayout != null) {
            linearLayout.setVisibility(z ? 0 : 8);
            this.llRetry.setOnClickListener(new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialog.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    View.OnClickListener onClickListener2 = onClickListener;
                    if (onClickListener2 != null) {
                        onClickListener2.onClick(view);
                    }
                    if (z) {
                        TapUpdateDialog.this.startRetryAnim();
                    }
                }
            });
        }
        if (this.tvOtherTips != null) {
            if (TextUtils.isEmpty(charSequence2)) {
                this.tvOtherTips.setVisibility(8);
                this.tvOtherTips.setText("");
            } else {
                this.tvOtherTips.setVisibility(0);
                this.tvOtherTips.setText(charSequence2);
            }
        }
        AnimatorSet animatorSet = this.downloadAnimatorSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return;
        }
        this.downloadAnimatorSet.cancel();
    }

    public void hideDownloadErrorTips() {
        TextView textView = this.tvErrorTips;
        if (textView != null) {
            textView.setVisibility(8);
        }
        LinearLayout linearLayout = this.llRetry;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        TextView textView2 = this.tvOtherTips;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
    }

    public void showFuncView(String str, View.OnClickListener onClickListener) {
        showFunViewInternal(str, null, onClickListener);
    }

    public void showFuncViewWithTips(String str, CharSequence charSequence, View.OnClickListener onClickListener) {
        showFunViewInternal(str, charSequence, onClickListener);
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

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        AnimatorSet animatorSet = this.downloadAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.downloadAnimatorSet = null;
        }
    }

    private void initView(Context context) {
        this.flRootContainer = (FrameLayout) findViewById(R.id.fl_root_container);
        this.llReadyContainer = (LinearLayout) findViewById(R.id.ll_ready_container);
        this.ivRequestError = (ImageView) findViewById(R.id.iv_request_error);
        this.tvReadyTips = (TextView) findViewById(R.id.tv_ready_tips);
        this.tvFuncNegative = (TextView) findViewById(R.id.tv_func_negative);
        this.tvFuncPositive = (TextView) findViewById(R.id.tv_func_positive);
        this.llDownloadContainer = (LinearLayout) findViewById(R.id.ll_download_container);
        this.tvUpdateTitle = (TextView) findViewById(R.id.tv_update_title);
        this.ivUpdateCancel = (ImageView) findViewById(R.id.iv_update_cancel);
        this.llProgressContainer = (LinearLayout) findViewById(R.id.ll_progress_container);
        this.tvDownloadContentTitle = (TextView) findViewById(R.id.tv_download_content_title);
        this.tvDownloadSpeed = (TextView) findViewById(R.id.tv_download_speed);
        this.llSpeedTriangle = (LinearLayout) findViewById(R.id.ll_speed_triangle);
        this.ivDownloadSpeedTriangle1 = (ImageView) findViewById(R.id.iv_download_speed_triangle_1);
        this.ivDownloadSpeedTriangle2 = (ImageView) findViewById(R.id.iv_download_speed_triangle_2);
        this.pbDownloadProgress = (ProgressBar) findViewById(R.id.pb_download_progress);
        this.tvErrorTips = (TextView) findViewById(R.id.tv_error_tips);
        this.llRetry = (LinearLayout) findViewById(R.id.ll_retry);
        this.tvOtherTips = (TextView) findViewById(R.id.tv_other_tips);
        this.ivIconRetry = (ImageView) findViewById(R.id.iv_icon_retry);
        this.llFuncContainer = (LinearLayout) findViewById(R.id.ll_func_container);
        this.tvFuncButton = (TextView) findViewById(R.id.tv_func_button);
        this.tvFuncTips = (TextView) findViewById(R.id.tv_func_tips);
        this.flexLinkContainer = (FlexboxLayout) findViewById(R.id.flex_link_container);
        this.tvDeveloperInfo = (TextView) findViewById(R.id.tv_developer_info);
        this.tvErrorTips.setMovementMethod(LinkMovementMethod.getInstance());
        this.tvOtherTips.setMovementMethod(LinkMovementMethod.getInstance());
        this.tvFuncTips.setMovementMethod(LinkMovementMethod.getInstance());
        this.tvErrorTips.setMovementMethod(LinkMovementMethod.getInstance());
        this.tvFuncTips.setMovementMethod(LinkMovementMethod.getInstance());
        this.ivUpdateCancel.setOnClickListener(new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TapUpdateDialog.this.mUpdateCancelListener != null) {
                    TapUpdateDialog.this.mUpdateCancelListener.onUpdateCancel(TapUpdateDialog.this.dialogState, true);
                }
            }
        });
        this.tvFuncNegative.setOnClickListener(new View.OnClickListener() { // from class: com.taptap.services.update.TapUpdateDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TapUpdateDialog.this.getDialogState() == TapUpdateDialogState.API_ERROR) {
                    TapUpdateTracker.getInstance().trackRequestFailedCancelButtonClick();
                }
                if (TapUpdateDialog.this.mUpdateCancelListener != null) {
                    TapUpdateDialog.this.mUpdateCancelListener.onUpdateCancel(TapUpdateDialog.this.dialogState, false);
                }
            }
        });
        this.tvFuncNegative.setText("取消");
    }

    private void showFunViewInternal(String str, CharSequence charSequence, View.OnClickListener onClickListener) {
        LinearLayout linearLayout = this.llReadyContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        LinearLayout linearLayout2 = this.llDownloadContainer;
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(0);
        }
        LinearLayout linearLayout3 = this.llProgressContainer;
        if (linearLayout3 != null) {
            linearLayout3.setVisibility(8);
        }
        LinearLayout linearLayout4 = this.llFuncContainer;
        if (linearLayout4 != null) {
            linearLayout4.setVisibility(0);
        }
        TextView textView = this.tvFuncButton;
        if (textView != null) {
            textView.setText(str);
            this.tvFuncButton.setOnClickListener(onClickListener);
        }
        if (this.tvFuncTips != null) {
            if (TextUtils.isEmpty(charSequence)) {
                this.tvFuncTips.setVisibility(8);
                this.tvFuncTips.setText("");
            } else {
                this.tvFuncTips.setVisibility(0);
                this.tvFuncTips.setText(charSequence);
            }
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

    private void showDownloadSpeedAnim() {
        if (this.downloadAnimatorSet == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivDownloadSpeedTriangle1, "alpha", 0.3f, 1.0f, 0.3f);
            objectAnimatorOfFloat.setRepeatCount(-1);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.ivDownloadSpeedTriangle2, "alpha", 1.0f, 0.3f, 1.0f);
            objectAnimatorOfFloat2.setRepeatCount(-1);
            AnimatorSet animatorSet = new AnimatorSet();
            this.downloadAnimatorSet = animatorSet;
            animatorSet.setDuration(1000L);
            this.downloadAnimatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
        }
        if (this.downloadAnimatorSet.isRunning()) {
            return;
        }
        this.downloadAnimatorSet.start();
    }

    private void stopAnim() {
        AnimatorSet animatorSet = this.downloadAnimatorSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return;
        }
        this.downloadAnimatorSet.cancel();
    }

    private TextView generateLinkView(final TapUpdateFuncLink tapUpdateFuncLink) {
        if (tapUpdateFuncLink == null) {
            return null;
        }
        TextView textView = new TextView(getContext());
        textView.setTextSize(1, 12.0f);
        SpannableString spannableString = new SpannableString(tapUpdateFuncLink.name);
        try {
            spannableString.setSpan(new ClickableSpan() { // from class: com.taptap.services.update.TapUpdateDialog.4
                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setColor(TapUpdateDialog.this.linkTextColor);
                    textPaint.setUnderlineText(false);
                }

                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    try {
                        TapUpdateDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(tapUpdateFuncLink.url)));
                    } catch (Exception e) {
                        TapUpdateLogger.e(e.getMessage());
                    }
                }
            }, 0, tapUpdateFuncLink.name.length(), 33);
        } catch (Exception e) {
            TapUpdateLogger.e(e.getMessage());
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return textView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRetryAnim() {
        if (this.ivIconRetry != null) {
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setDuration(1000L);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            this.ivIconRetry.startAnimation(rotateAnimation);
        }
    }

    private void stopRetryAnim() {
        ImageView imageView = this.ivIconRetry;
        if (imageView != null) {
            imageView.clearAnimation();
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            adapterBlackScreenOnUnity();
        }
    }

    private void adapterBlackScreenOnUnity() {
        try {
            Context context = getContext();
            if (context instanceof Activity) {
                ((Activity) context).onWindowFocusChanged(true);
            } else if (context instanceof ContextThemeWrapper) {
                Context baseContext = ((ContextThemeWrapper) context).getBaseContext();
                if (baseContext instanceof Activity) {
                    ((Activity) baseContext).onWindowFocusChanged(true);
                }
            }
        } catch (Exception unused) {
        }
    }
}
