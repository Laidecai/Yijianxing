package tds.com.google.android.material.internal;

import android.R;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import tds.androidx.core.view.ViewCompat;
import tds.androidx.core.view.WindowInsetsCompat;

/* JADX INFO: loaded from: classes.dex */
public class ViewUtils {

    public interface OnApplyWindowInsetsListener {
        WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding);
    }

    private ViewUtils() {
    }

    public static PorterDuff.Mode parseTintMode(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    public static float dpToPx(Context context, int i) {
        return TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }

    public static void requestFocusAndShowKeyboard(View view) {
        view.requestFocus();
        view.post(new Runnable() { // from class: tds.com.google.android.material.internal.ViewUtils.1
            final /* synthetic */ View val$view;

            AnonymousClass1(View view2) {
                view = view2;
            }

            @Override // java.lang.Runnable
            public void run() {
                ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 1);
            }
        });
    }

    /* JADX INFO: renamed from: tds.com.google.android.material.internal.ViewUtils$1 */
    static class AnonymousClass1 implements Runnable {
        final /* synthetic */ View val$view;

        AnonymousClass1(View view2) {
            view = view2;
        }

        @Override // java.lang.Runnable
        public void run() {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 1);
        }
    }

    public static class RelativePadding {
        public int bottom;
        public int end;
        public int start;
        public int top;

        public RelativePadding(int i, int i2, int i3, int i4) {
            this.start = i;
            this.top = i2;
            this.end = i3;
            this.bottom = i4;
        }

        public RelativePadding(RelativePadding relativePadding) {
            this.start = relativePadding.start;
            this.top = relativePadding.top;
            this.end = relativePadding.end;
            this.bottom = relativePadding.bottom;
        }

        public void applyToView(View view) {
            ViewCompat.setPaddingRelative(view, this.start, this.top, this.end, this.bottom);
        }
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attributeSet, int i, int i2) {
        doOnApplyWindowInsets(view, attributeSet, i, i2, null);
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attributeSet, int i, int i2, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        doOnApplyWindowInsets(view, new OnApplyWindowInsetsListener() { // from class: tds.com.google.android.material.internal.ViewUtils.2
            AnonymousClass2() {
            }

            @Override // tds.com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding) {
                ViewUtils.isLayoutRtl(view2);
                relativePadding.applyToView(view2);
                OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = onApplyWindowInsetsListener;
                return onApplyWindowInsetsListener2 != null ? onApplyWindowInsetsListener2.onApplyWindowInsets(view2, windowInsetsCompat, relativePadding) : windowInsetsCompat;
            }
        });
    }

    /* JADX INFO: renamed from: tds.com.google.android.material.internal.ViewUtils$2 */
    static class AnonymousClass2 implements OnApplyWindowInsetsListener {
        AnonymousClass2() {
        }

        @Override // tds.com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
        public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding) {
            ViewUtils.isLayoutRtl(view2);
            relativePadding.applyToView(view2);
            OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = onApplyWindowInsetsListener;
            return onApplyWindowInsetsListener2 != null ? onApplyWindowInsetsListener2.onApplyWindowInsets(view2, windowInsetsCompat, relativePadding) : windowInsetsCompat;
        }
    }

    public static void doOnApplyWindowInsets(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        ViewCompat.setOnApplyWindowInsetsListener(view, new tds.androidx.core.view.OnApplyWindowInsetsListener() { // from class: tds.com.google.android.material.internal.ViewUtils.3
            final /* synthetic */ RelativePadding val$initialPadding;

            AnonymousClass3(RelativePadding relativePadding) {
                relativePadding = relativePadding;
            }

            @Override // tds.androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return onApplyWindowInsetsListener.onApplyWindowInsets(view2, windowInsetsCompat, new RelativePadding(relativePadding));
            }
        });
        requestApplyInsetsWhenAttached(view);
    }

    /* JADX INFO: renamed from: tds.com.google.android.material.internal.ViewUtils$3 */
    static class AnonymousClass3 implements tds.androidx.core.view.OnApplyWindowInsetsListener {
        final /* synthetic */ RelativePadding val$initialPadding;

        AnonymousClass3(RelativePadding relativePadding) {
            relativePadding = relativePadding;
        }

        @Override // tds.androidx.core.view.OnApplyWindowInsetsListener
        public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
            return onApplyWindowInsetsListener.onApplyWindowInsets(view2, windowInsetsCompat, new RelativePadding(relativePadding));
        }
    }

    public static void requestApplyInsetsWhenAttached(View view) {
        if (ViewCompat.isAttachedToWindow(view)) {
            ViewCompat.requestApplyInsets(view);
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: tds.com.google.android.material.internal.ViewUtils.4
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view2) {
                }

                AnonymousClass4() {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view2) {
                    view2.removeOnAttachStateChangeListener(this);
                    ViewCompat.requestApplyInsets(view2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: tds.com.google.android.material.internal.ViewUtils$4 */
    static class AnonymousClass4 implements View.OnAttachStateChangeListener {
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view2) {
        }

        AnonymousClass4() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view2) {
            view2.removeOnAttachStateChangeListener(this);
            ViewCompat.requestApplyInsets(view2);
        }
    }

    public static float getParentAbsoluteElevation(View view) {
        float elevation = 0.0f;
        for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
            elevation += ViewCompat.getElevation((View) parent);
        }
        return elevation;
    }

    public static ViewOverlayImpl getOverlay(View view) {
        if (view == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            return new ViewOverlayApi18(view);
        }
        return ViewOverlayApi14.createFrom(view);
    }

    public static ViewGroup getContentView(View view) {
        if (view == null) {
            return null;
        }
        View rootView = view.getRootView();
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.content);
        if (viewGroup != null) {
            return viewGroup;
        }
        if (rootView == view || !(rootView instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) rootView;
    }

    public static ViewOverlayImpl getContentViewOverlay(View view) {
        return getOverlay(getContentView(view));
    }

    public static void addOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (view != null) {
            removeOnGlobalLayoutListener(view.getViewTreeObserver(), onGlobalLayoutListener);
        }
    }

    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT >= 16) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    }
}
