package androidx.core.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.core.R;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import com.alipay.sdk.util.g;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class WindowInsetsAnimationCompat {
    private static final boolean DEBUG = false;
    private static final String TAG = "WindowInsetsAnimCompat";
    private Impl mImpl;

    public WindowInsetsAnimationCompat(int typeMask, Interpolator interpolator, long durationMillis) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(typeMask, interpolator, durationMillis);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new Impl21(typeMask, interpolator, durationMillis);
        } else {
            this.mImpl = new Impl(0, interpolator, durationMillis);
        }
    }

    private WindowInsetsAnimationCompat(WindowInsetsAnimation animation) {
        this(0, null, 0L);
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(animation);
        }
    }

    public int getTypeMask() {
        return this.mImpl.getTypeMask();
    }

    public float getFraction() {
        return this.mImpl.getFraction();
    }

    public float getInterpolatedFraction() {
        return this.mImpl.getInterpolatedFraction();
    }

    public Interpolator getInterpolator() {
        return this.mImpl.getInterpolator();
    }

    public long getDurationMillis() {
        return this.mImpl.getDurationMillis();
    }

    public void setFraction(float fraction) {
        this.mImpl.setFraction(fraction);
    }

    public float getAlpha() {
        return this.mImpl.getAlpha();
    }

    public void setAlpha(float alpha) {
        this.mImpl.setAlpha(alpha);
    }

    public static final class BoundsCompat {
        private final Insets mLowerBound;
        private final Insets mUpperBound;

        public BoundsCompat(Insets lowerBound, Insets upperBound) {
            this.mLowerBound = lowerBound;
            this.mUpperBound = upperBound;
        }

        private BoundsCompat(WindowInsetsAnimation.Bounds bounds) {
            this.mLowerBound = Impl30.getLowerBounds(bounds);
            this.mUpperBound = Impl30.getHigherBounds(bounds);
        }

        public Insets getLowerBound() {
            return this.mLowerBound;
        }

        public Insets getUpperBound() {
            return this.mUpperBound;
        }

        public BoundsCompat inset(Insets insets) {
            return new BoundsCompat(WindowInsetsCompat.insetInsets(this.mLowerBound, insets.left, insets.top, insets.right, insets.bottom), WindowInsetsCompat.insetInsets(this.mUpperBound, insets.left, insets.top, insets.right, insets.bottom));
        }

        public String toString() {
            return "Bounds{lower=" + this.mLowerBound + " upper=" + this.mUpperBound + g.d;
        }

        public WindowInsetsAnimation.Bounds toBounds() {
            return Impl30.createPlatformBounds(this);
        }

        public static BoundsCompat toBoundsCompat(WindowInsetsAnimation.Bounds bounds) {
            return new BoundsCompat(bounds);
        }
    }

    static WindowInsetsAnimationCompat toWindowInsetsAnimationCompat(WindowInsetsAnimation windowInsetsAnimation) {
        return new WindowInsetsAnimationCompat(windowInsetsAnimation);
    }

    public static abstract class Callback {
        public static final int DISPATCH_MODE_CONTINUE_ON_SUBTREE = 1;
        public static final int DISPATCH_MODE_STOP = 0;
        WindowInsets mDispachedInsets;
        private final int mDispatchMode;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DispatchMode {
        }

        public void onEnd(WindowInsetsAnimationCompat animation) {
        }

        public void onPrepare(WindowInsetsAnimationCompat animation) {
        }

        public abstract WindowInsetsCompat onProgress(WindowInsetsCompat insets, List<WindowInsetsAnimationCompat> runningAnimations);

        public BoundsCompat onStart(WindowInsetsAnimationCompat animation, BoundsCompat bounds) {
            return bounds;
        }

        public Callback(int dispatchMode) {
            this.mDispatchMode = dispatchMode;
        }

        public final int getDispatchMode() {
            return this.mDispatchMode;
        }
    }

    static void setCallback(View view, Callback callback) {
        if (Build.VERSION.SDK_INT >= 30) {
            Impl30.setCallback(view, callback);
        } else if (Build.VERSION.SDK_INT >= 21) {
            Impl21.setCallback(view, callback);
        }
    }

    private static class Impl {
        private float mAlpha;
        private final long mDurationMillis;
        private float mFraction;
        private final Interpolator mInterpolator;
        private final int mTypeMask;

        Impl(int typeMask, Interpolator interpolator, long durationMillis) {
            this.mTypeMask = typeMask;
            this.mInterpolator = interpolator;
            this.mDurationMillis = durationMillis;
        }

        public int getTypeMask() {
            return this.mTypeMask;
        }

        public float getFraction() {
            return this.mFraction;
        }

        public float getInterpolatedFraction() {
            Interpolator interpolator = this.mInterpolator;
            if (interpolator != null) {
                return interpolator.getInterpolation(this.mFraction);
            }
            return this.mFraction;
        }

        public Interpolator getInterpolator() {
            return this.mInterpolator;
        }

        public long getDurationMillis() {
            return this.mDurationMillis;
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public void setFraction(float fraction) {
            this.mFraction = fraction;
        }

        public void setAlpha(float alpha) {
            this.mAlpha = alpha;
        }
    }

    private static class Impl21 extends Impl {
        Impl21(int typeMask, Interpolator interpolator, long durationMillis) {
            super(typeMask, interpolator, durationMillis);
        }

        static void setCallback(final View view, final Callback callback) {
            Object tag = view.getTag(R.id.tag_on_apply_window_listener);
            if (callback == null) {
                view.setTag(R.id.tag_window_insets_animation_callback, null);
                if (tag == null) {
                    view.setOnApplyWindowInsetsListener(null);
                    return;
                }
                return;
            }
            View.OnApplyWindowInsetsListener onApplyWindowInsetsListenerCreateProxyListener = createProxyListener(view, callback);
            view.setTag(R.id.tag_window_insets_animation_callback, onApplyWindowInsetsListenerCreateProxyListener);
            if (tag == null) {
                view.setOnApplyWindowInsetsListener(onApplyWindowInsetsListenerCreateProxyListener);
            }
        }

        private static View.OnApplyWindowInsetsListener createProxyListener(View view, final Callback callback) {
            return new Impl21OnApplyWindowInsetsListener(view, callback);
        }

        static BoundsCompat computeAnimationBounds(WindowInsetsCompat targetInsets, WindowInsetsCompat startingInsets, int mask) {
            Insets insets = targetInsets.getInsets(mask);
            Insets insets2 = startingInsets.getInsets(mask);
            return new BoundsCompat(Insets.of(Math.min(insets.left, insets2.left), Math.min(insets.top, insets2.top), Math.min(insets.right, insets2.right), Math.min(insets.bottom, insets2.bottom)), Insets.of(Math.max(insets.left, insets2.left), Math.max(insets.top, insets2.top), Math.max(insets.right, insets2.right), Math.max(insets.bottom, insets2.bottom)));
        }

        static int buildAnimationMask(WindowInsetsCompat targetInsets, WindowInsetsCompat currentInsets) {
            int i = 0;
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if (!targetInsets.getInsets(i2).equals(currentInsets.getInsets(i2))) {
                    i |= i2;
                }
            }
            return i;
        }

        static WindowInsetsCompat interpolateInsets(WindowInsetsCompat target, WindowInsetsCompat starting, float fraction, int typeMask) {
            WindowInsetsCompat.Builder builder = new WindowInsetsCompat.Builder(target);
            for (int i = 1; i <= 256; i <<= 1) {
                if ((typeMask & i) == 0) {
                    builder.setInsets(i, target.getInsets(i));
                } else {
                    Insets insets = target.getInsets(i);
                    Insets insets2 = starting.getInsets(i);
                    float f = 1.0f - fraction;
                    builder.setInsets(i, WindowInsetsCompat.insetInsets(insets, (int) (((double) ((insets.left - insets2.left) * f)) + 0.5d), (int) (((double) ((insets.top - insets2.top) * f)) + 0.5d), (int) (((double) ((insets.right - insets2.right) * f)) + 0.5d), (int) (((double) ((insets.bottom - insets2.bottom) * f)) + 0.5d)));
                }
            }
            return builder.build();
        }

        private static class Impl21OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
            private static final int COMPAT_ANIMATION_DURATION = 160;
            final Callback mCallback;
            private WindowInsetsCompat mLastInsets;

            Impl21OnApplyWindowInsetsListener(View view, Callback callback) {
                this.mCallback = callback;
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(view);
                this.mLastInsets = rootWindowInsets != null ? new WindowInsetsCompat.Builder(rootWindowInsets).build() : null;
            }

            @Override // android.view.View.OnApplyWindowInsetsListener
            public WindowInsets onApplyWindowInsets(final View v, final WindowInsets insets) {
                if (!v.isLaidOut()) {
                    this.mLastInsets = WindowInsetsCompat.toWindowInsetsCompat(insets, v);
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets, v);
                if (this.mLastInsets == null) {
                    this.mLastInsets = ViewCompat.getRootWindowInsets(v);
                }
                if (this.mLastInsets == null) {
                    this.mLastInsets = windowInsetsCompat;
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                Callback callback = Impl21.getCallback(v);
                if (callback != null && Objects.equals(callback.mDispachedInsets, insets)) {
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                int iBuildAnimationMask = Impl21.buildAnimationMask(windowInsetsCompat, this.mLastInsets);
                if (iBuildAnimationMask == 0) {
                    return Impl21.forwardToViewIfNeeded(v, insets);
                }
                WindowInsetsCompat windowInsetsCompat2 = this.mLastInsets;
                WindowInsetsAnimationCompat windowInsetsAnimationCompat = new WindowInsetsAnimationCompat(iBuildAnimationMask, new DecelerateInterpolator(), 160L);
                windowInsetsAnimationCompat.setFraction(0.0f);
                ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(windowInsetsAnimationCompat.getDurationMillis());
                BoundsCompat boundsCompatComputeAnimationBounds = Impl21.computeAnimationBounds(windowInsetsCompat, windowInsetsCompat2, iBuildAnimationMask);
                Impl21.dispatchOnPrepare(v, windowInsetsAnimationCompat, insets, false);
                duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.core.view.WindowInsetsAnimationCompat.Impl21.Impl21OnApplyWindowInsetsListener.1
                    final /* synthetic */ WindowInsetsAnimationCompat val$anim;
                    final /* synthetic */ int val$animationMask;
                    final /* synthetic */ WindowInsetsCompat val$startingInsets;
                    final /* synthetic */ WindowInsetsCompat val$targetInsets;
                    final /* synthetic */ View val$v;

                    AnonymousClass1(WindowInsetsAnimationCompat windowInsetsAnimationCompat2, WindowInsetsCompat windowInsetsCompat3, WindowInsetsCompat windowInsetsCompat22, int iBuildAnimationMask2, final View v2) {
                        val$anim = windowInsetsAnimationCompat2;
                        val$targetInsets = windowInsetsCompat3;
                        val$startingInsets = windowInsetsCompat22;
                        val$animationMask = iBuildAnimationMask2;
                        val$v = v2;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator animator) {
                        val$anim.setFraction(animator.getAnimatedFraction());
                        Impl21.dispatchOnProgress(val$v, Impl21.interpolateInsets(val$targetInsets, val$startingInsets, val$anim.getInterpolatedFraction(), val$animationMask), Collections.singletonList(val$anim));
                    }
                });
                duration.addListener(new AnimatorListenerAdapter() { // from class: androidx.core.view.WindowInsetsAnimationCompat.Impl21.Impl21OnApplyWindowInsetsListener.2
                    final /* synthetic */ WindowInsetsAnimationCompat val$anim;
                    final /* synthetic */ View val$v;

                    AnonymousClass2(WindowInsetsAnimationCompat windowInsetsAnimationCompat2, final View v2) {
                        val$anim = windowInsetsAnimationCompat2;
                        val$v = v2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        val$anim.setFraction(1.0f);
                        Impl21.dispatchOnEnd(val$v, val$anim);
                    }
                });
                OneShotPreDrawListener.add(v2, new Runnable() { // from class: androidx.core.view.WindowInsetsAnimationCompat.Impl21.Impl21OnApplyWindowInsetsListener.3
                    final /* synthetic */ WindowInsetsAnimationCompat val$anim;
                    final /* synthetic */ BoundsCompat val$animationBounds;
                    final /* synthetic */ ValueAnimator val$animator;
                    final /* synthetic */ View val$v;

                    AnonymousClass3(final View v2, WindowInsetsAnimationCompat windowInsetsAnimationCompat2, BoundsCompat boundsCompatComputeAnimationBounds2, ValueAnimator duration2) {
                        val$v = v2;
                        val$anim = windowInsetsAnimationCompat2;
                        val$animationBounds = boundsCompatComputeAnimationBounds2;
                        val$animator = duration2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        Impl21.dispatchOnStart(val$v, val$anim, val$animationBounds);
                        val$animator.start();
                    }
                });
                this.mLastInsets = windowInsetsCompat3;
                return Impl21.forwardToViewIfNeeded(v2, insets);
            }

            /* JADX INFO: renamed from: androidx.core.view.WindowInsetsAnimationCompat$Impl21$Impl21OnApplyWindowInsetsListener$1 */
            class AnonymousClass1 implements ValueAnimator.AnimatorUpdateListener {
                final /* synthetic */ WindowInsetsAnimationCompat val$anim;
                final /* synthetic */ int val$animationMask;
                final /* synthetic */ WindowInsetsCompat val$startingInsets;
                final /* synthetic */ WindowInsetsCompat val$targetInsets;
                final /* synthetic */ View val$v;

                AnonymousClass1(WindowInsetsAnimationCompat windowInsetsAnimationCompat2, WindowInsetsCompat windowInsetsCompat3, WindowInsetsCompat windowInsetsCompat22, int iBuildAnimationMask2, final View v2) {
                    val$anim = windowInsetsAnimationCompat2;
                    val$targetInsets = windowInsetsCompat3;
                    val$startingInsets = windowInsetsCompat22;
                    val$animationMask = iBuildAnimationMask2;
                    val$v = v2;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animator) {
                    val$anim.setFraction(animator.getAnimatedFraction());
                    Impl21.dispatchOnProgress(val$v, Impl21.interpolateInsets(val$targetInsets, val$startingInsets, val$anim.getInterpolatedFraction(), val$animationMask), Collections.singletonList(val$anim));
                }
            }

            /* JADX INFO: renamed from: androidx.core.view.WindowInsetsAnimationCompat$Impl21$Impl21OnApplyWindowInsetsListener$2 */
            class AnonymousClass2 extends AnimatorListenerAdapter {
                final /* synthetic */ WindowInsetsAnimationCompat val$anim;
                final /* synthetic */ View val$v;

                AnonymousClass2(WindowInsetsAnimationCompat windowInsetsAnimationCompat2, final View v2) {
                    val$anim = windowInsetsAnimationCompat2;
                    val$v = v2;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    val$anim.setFraction(1.0f);
                    Impl21.dispatchOnEnd(val$v, val$anim);
                }
            }

            /* JADX INFO: renamed from: androidx.core.view.WindowInsetsAnimationCompat$Impl21$Impl21OnApplyWindowInsetsListener$3 */
            class AnonymousClass3 implements Runnable {
                final /* synthetic */ WindowInsetsAnimationCompat val$anim;
                final /* synthetic */ BoundsCompat val$animationBounds;
                final /* synthetic */ ValueAnimator val$animator;
                final /* synthetic */ View val$v;

                AnonymousClass3(final View v2, WindowInsetsAnimationCompat windowInsetsAnimationCompat2, BoundsCompat boundsCompatComputeAnimationBounds2, ValueAnimator duration2) {
                    val$v = v2;
                    val$anim = windowInsetsAnimationCompat2;
                    val$animationBounds = boundsCompatComputeAnimationBounds2;
                    val$animator = duration2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    Impl21.dispatchOnStart(val$v, val$anim, val$animationBounds);
                    val$animator.start();
                }
            }
        }

        static WindowInsets forwardToViewIfNeeded(View v, WindowInsets insets) {
            return v.getTag(R.id.tag_on_apply_window_listener) != null ? insets : v.onApplyWindowInsets(insets);
        }

        static void dispatchOnPrepare(View v, WindowInsetsAnimationCompat anim, WindowInsets insets, boolean stopDispatch) {
            Callback callback = getCallback(v);
            if (callback != null) {
                callback.mDispachedInsets = insets;
                if (!stopDispatch) {
                    callback.onPrepare(anim);
                    stopDispatch = callback.getDispatchMode() == 0;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    dispatchOnPrepare(viewGroup.getChildAt(i), anim, insets, stopDispatch);
                }
            }
        }

        static void dispatchOnStart(View v, WindowInsetsAnimationCompat anim, BoundsCompat animationBounds) {
            Callback callback = getCallback(v);
            if (callback != null) {
                callback.onStart(anim, animationBounds);
                if (callback.getDispatchMode() == 0) {
                    return;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    dispatchOnStart(viewGroup.getChildAt(i), anim, animationBounds);
                }
            }
        }

        static void dispatchOnProgress(View v, WindowInsetsCompat interpolateInsets, List<WindowInsetsAnimationCompat> runningAnimations) {
            Callback callback = getCallback(v);
            if (callback != null) {
                interpolateInsets = callback.onProgress(interpolateInsets, runningAnimations);
                if (callback.getDispatchMode() == 0) {
                    return;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    dispatchOnProgress(viewGroup.getChildAt(i), interpolateInsets, runningAnimations);
                }
            }
        }

        static void dispatchOnEnd(View v, WindowInsetsAnimationCompat anim) {
            Callback callback = getCallback(v);
            if (callback != null) {
                callback.onEnd(anim);
                if (callback.getDispatchMode() == 0) {
                    return;
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    dispatchOnEnd(viewGroup.getChildAt(i), anim);
                }
            }
        }

        static Callback getCallback(View child) {
            Object tag = child.getTag(R.id.tag_window_insets_animation_callback);
            if (tag instanceof Impl21OnApplyWindowInsetsListener) {
                return ((Impl21OnApplyWindowInsetsListener) tag).mCallback;
            }
            return null;
        }
    }

    private static class Impl30 extends Impl {
        private final WindowInsetsAnimation mWrapped;

        Impl30(WindowInsetsAnimation wrapped) {
            super(0, null, 0L);
            this.mWrapped = wrapped;
        }

        Impl30(int typeMask, Interpolator interpolator, long durationMillis) {
            this(new WindowInsetsAnimation(typeMask, interpolator, durationMillis));
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public int getTypeMask() {
            return this.mWrapped.getTypeMask();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public Interpolator getInterpolator() {
            return this.mWrapped.getInterpolator();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public long getDurationMillis() {
            return this.mWrapped.getDurationMillis();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public float getFraction() {
            return this.mWrapped.getFraction();
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public void setFraction(float fraction) {
            this.mWrapped.setFraction(fraction);
        }

        @Override // androidx.core.view.WindowInsetsAnimationCompat.Impl
        public float getInterpolatedFraction() {
            return this.mWrapped.getInterpolatedFraction();
        }

        private static class ProxyCallback extends WindowInsetsAnimation.Callback {
            private final HashMap<WindowInsetsAnimation, WindowInsetsAnimationCompat> mAnimations;
            private final Callback mCompat;
            private List<WindowInsetsAnimationCompat> mRORunningAnimations;
            private ArrayList<WindowInsetsAnimationCompat> mTmpRunningAnimations;

            ProxyCallback(final Callback compat) {
                super(compat.getDispatchMode());
                this.mAnimations = new HashMap<>();
                this.mCompat = compat;
            }

            private WindowInsetsAnimationCompat getWindowInsetsAnimationCompat(WindowInsetsAnimation animation) {
                WindowInsetsAnimationCompat windowInsetsAnimationCompat = this.mAnimations.get(animation);
                if (windowInsetsAnimationCompat != null) {
                    return windowInsetsAnimationCompat;
                }
                WindowInsetsAnimationCompat windowInsetsAnimationCompat2 = WindowInsetsAnimationCompat.toWindowInsetsAnimationCompat(animation);
                this.mAnimations.put(animation, windowInsetsAnimationCompat2);
                return windowInsetsAnimationCompat2;
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onPrepare(WindowInsetsAnimation animation) {
                this.mCompat.onPrepare(getWindowInsetsAnimationCompat(animation));
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation animation, WindowInsetsAnimation.Bounds bounds) {
                return this.mCompat.onStart(getWindowInsetsAnimationCompat(animation), BoundsCompat.toBoundsCompat(bounds)).toBounds();
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public WindowInsets onProgress(WindowInsets insets, List<WindowInsetsAnimation> runningAnimations) {
                ArrayList<WindowInsetsAnimationCompat> arrayList = this.mTmpRunningAnimations;
                if (arrayList == null) {
                    ArrayList<WindowInsetsAnimationCompat> arrayList2 = new ArrayList<>(runningAnimations.size());
                    this.mTmpRunningAnimations = arrayList2;
                    this.mRORunningAnimations = Collections.unmodifiableList(arrayList2);
                } else {
                    arrayList.clear();
                }
                for (int size = runningAnimations.size() - 1; size >= 0; size--) {
                    WindowInsetsAnimation windowInsetsAnimation = runningAnimations.get(size);
                    WindowInsetsAnimationCompat windowInsetsAnimationCompat = getWindowInsetsAnimationCompat(windowInsetsAnimation);
                    windowInsetsAnimationCompat.setFraction(windowInsetsAnimation.getFraction());
                    this.mTmpRunningAnimations.add(windowInsetsAnimationCompat);
                }
                return this.mCompat.onProgress(WindowInsetsCompat.toWindowInsetsCompat(insets), this.mRORunningAnimations).toWindowInsets();
            }

            @Override // android.view.WindowInsetsAnimation.Callback
            public void onEnd(WindowInsetsAnimation animation) {
                this.mCompat.onEnd(getWindowInsetsAnimationCompat(animation));
                this.mAnimations.remove(animation);
            }
        }

        public static void setCallback(View view, Callback callback) {
            view.setWindowInsetsAnimationCallback(callback != null ? new ProxyCallback(callback) : null);
        }

        public static WindowInsetsAnimation.Bounds createPlatformBounds(BoundsCompat bounds) {
            return new WindowInsetsAnimation.Bounds(bounds.getLowerBound().toPlatformInsets(), bounds.getUpperBound().toPlatformInsets());
        }

        public static Insets getLowerBounds(WindowInsetsAnimation.Bounds bounds) {
            return Insets.toCompatInsets(bounds.getLowerBound());
        }

        public static Insets getHigherBounds(WindowInsetsAnimation.Bounds bounds) {
            return Insets.toCompatInsets(bounds.getUpperBound());
        }
    }
}
