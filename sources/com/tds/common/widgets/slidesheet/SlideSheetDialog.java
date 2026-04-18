package com.tds.common.widgets.slidesheet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import com.tds.common.R;
import com.tds.common.widgets.behavior.BottomSheetBehavior;
import com.tds.common.widgets.behavior.RightSheetBehavior;
import tds.androidx.coordinatorlayout.widget.CoordinatorLayout;

/* JADX INFO: loaded from: classes.dex */
public class SlideSheetDialog extends Dialog {
    private BottomSheetBehavior<FrameLayout> behavior;
    private FrameLayout bottomSheet;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    boolean cancelable;
    private boolean canceledOnTouchOutside;
    private boolean canceledOnTouchOutsideSet;
    private FrameLayout container;
    private CoordinatorLayout coordinator;
    boolean dismissWithAnimation;
    private final boolean isLandscape;

    public SlideSheetDialog(Context context, boolean z) {
        super(context);
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.tds.common.widgets.slidesheet.SlideSheetDialog.3
            @Override // com.tds.common.widgets.behavior.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(View view, float f) {
            }

            @Override // com.tds.common.widgets.behavior.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(View view, int i) {
                if (i == 5) {
                    SlideSheetDialog.this.cancel();
                }
            }
        };
        getWindow().setFlags(16777216, 16777216);
        requestWindowFeature(1);
        this.isLandscape = z;
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }

    public SlideSheetDialog(Context context, boolean z, boolean z2, DialogInterface.OnCancelListener onCancelListener) {
        super(context);
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.tds.common.widgets.slidesheet.SlideSheetDialog.3
            @Override // com.tds.common.widgets.behavior.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(View view, float f) {
            }

            @Override // com.tds.common.widgets.behavior.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(View view, int i) {
                if (i == 5) {
                    SlideSheetDialog.this.cancel();
                }
            }
        };
        requestWindowFeature(1);
        this.isLandscape = z;
        this.cancelable = z2;
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(67108864);
                window.addFlags(Integer.MIN_VALUE);
            }
            window.setLayout(-1, -1);
            window.getAttributes().windowAnimations = this.isLandscape ? R.style.tds_common_animation_slideSheetDialog_landscape : R.style.tds_common_animation_slideSheetDialog_portrait;
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
    }

    @Override // android.app.Dialog
    public void setContentView(int i) {
        super.setContentView(wrapInBottomSheet(i, null, null));
    }

    @Override // android.app.Dialog
    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    @Override // android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(wrapInBottomSheet(0, view, layoutParams));
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.cancelable != z) {
            this.cancelable = z;
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.behavior;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.setHideable(z);
            }
        }
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.behavior;
        if (bottomSheetBehavior == null || bottomSheetBehavior.getState() != 5) {
            return;
        }
        this.behavior.setState(4);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior = getBehavior();
        if (!this.dismissWithAnimation || behavior.getState() == 5) {
            super.cancel();
        } else {
            behavior.setState(5);
        }
    }

    @Override // android.app.Dialog
    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.cancelable) {
            this.cancelable = true;
        }
        this.canceledOnTouchOutside = z;
        this.canceledOnTouchOutsideSet = true;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        if (this.behavior == null) {
            ensureContainerAndBehavior();
        }
        return this.behavior;
    }

    public void setDismissWithAnimation(boolean z) {
        this.dismissWithAnimation = z;
    }

    public boolean getDismissWithAnimation() {
        return this.dismissWithAnimation;
    }

    private FrameLayout ensureContainerAndBehavior() {
        if (this.container == null) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.container = frameLayout;
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            this.container.setBackgroundColor(1711276032);
            CoordinatorLayout coordinatorLayout = new CoordinatorLayout(getContext());
            this.coordinator = coordinatorLayout;
            coordinatorLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            View view = new View(getContext());
            view.setFocusable(false);
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            view.setOnClickListener(new View.OnClickListener() { // from class: com.tds.common.widgets.slidesheet.SlideSheetDialog.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (SlideSheetDialog.this.cancelable && SlideSheetDialog.this.isShowing() && SlideSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                        SlideSheetDialog.this.cancel();
                    }
                }
            });
            this.coordinator.addView(view);
            this.bottomSheet = new FrameLayout(getContext());
            if (this.isLandscape) {
                this.behavior = new RightSheetBehavior(getContext(), null);
            } else {
                this.behavior = new BottomSheetBehavior<>(getContext(), null);
            }
            this.behavior.setHideable(true);
            this.behavior.addBottomSheetCallback(this.bottomSheetCallback);
            this.behavior.setHideable(this.cancelable);
            CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(-1, -1);
            layoutParams.setBehavior(this.behavior);
            this.bottomSheet.setLayoutParams(layoutParams);
            this.coordinator.addView(this.bottomSheet);
            this.container.addView(this.coordinator);
        }
        return this.container;
    }

    private View wrapInBottomSheet(int i, View view, ViewGroup.LayoutParams layoutParams) {
        ensureContainerAndBehavior();
        if (i != 0 && view == null) {
            view = getLayoutInflater().inflate(i, (ViewGroup) this.coordinator, false);
        }
        this.bottomSheet.removeAllViews();
        if (layoutParams != null) {
            this.bottomSheet.addView(view, layoutParams);
        } else {
            this.bottomSheet.addView(view);
        }
        this.bottomSheet.setOnTouchListener(new View.OnTouchListener() { // from class: com.tds.common.widgets.slidesheet.SlideSheetDialog.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return true;
            }
        });
        return this.container;
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (!this.canceledOnTouchOutsideSet) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{android.R.attr.windowCloseOnTouchOutside});
            this.canceledOnTouchOutside = typedArrayObtainStyledAttributes.getBoolean(0, true);
            typedArrayObtainStyledAttributes.recycle();
            this.canceledOnTouchOutsideSet = true;
        }
        return this.canceledOnTouchOutside;
    }
}
