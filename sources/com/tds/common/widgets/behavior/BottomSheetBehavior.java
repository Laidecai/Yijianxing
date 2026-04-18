package com.tds.common.widgets.behavior;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.tds.common.utils.UIUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import tds.androidx.coordinatorlayout.widget.CoordinatorLayout;
import tds.androidx.core.math.MathUtils;
import tds.androidx.core.view.ViewCompat;
import tds.androidx.core.view.WindowInsetsCompat;
import tds.androidx.customview.view.AbsSavedState;
import tds.androidx.customview.widget.ViewDragHelper;
import tds.com.google.android.material.internal.ViewUtils;

/* JADX INFO: loaded from: classes.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    protected static final float HIDE_FRICTION = 0.1f;
    protected static final float HIDE_THRESHOLD = 0.5f;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    protected static final int SIGNIFICANT_VEL_THRESHOLD = 500;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";
    int activePointerId;
    private final ArrayList<BottomSheetCallback> callbacks;
    protected int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback;
    protected boolean draggable;
    float elevation;
    int expandedOffset;
    protected boolean fitToContents;
    int fitToContentsOffset;
    protected int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio;
    boolean hideable;
    protected boolean ignoreEvents;
    protected int initialY;
    protected int lastNestedScrollDy;
    protected float maximumVelocity;
    protected boolean nestedScrolled;
    WeakReference<View> nestedScrollingChildRef;
    int parentHeight;
    int parentWidth;
    private int peekHeight;
    protected boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    protected int peekHeightMin;
    private int saveFlags;
    protected BottomSheetBehavior<V>.SettleRunnable settleRunnable;
    protected boolean skipCollapsed;
    int state;
    boolean touchingScrollingChild;
    protected VelocityTracker velocityTracker;
    ViewDragHelper viewDragHelper;
    WeakReference<V> viewRef;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(View view, float f);

        public abstract void onStateChanged(View view, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SaveFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
    }

    public BottomSheetBehavior() {
        this.saveFlags = 0;
        this.fitToContents = true;
        this.settleRunnable = null;
        this.halfExpandedRatio = HIDE_THRESHOLD;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.callbacks = new ArrayList<>();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.tds.common.widgets.behavior.BottomSheetBehavior.3
            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                if (BottomSheetBehavior.this.state == 1 || BottomSheetBehavior.this.touchingScrollingChild) {
                    return false;
                }
                if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == i) {
                    View view2 = BottomSheetBehavior.this.nestedScrollingChildRef != null ? BottomSheetBehavior.this.nestedScrollingChildRef.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                return BottomSheetBehavior.this.viewRef != null && BottomSheetBehavior.this.viewRef.get() == view;
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
                BottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            private boolean releasedLow(View view) {
                return view.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2;
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view, float f, float f2) {
                int i;
                int i2 = 4;
                if (f2 < 0.0f) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        i = BottomSheetBehavior.this.fitToContentsOffset;
                    } else if (view.getTop() > BottomSheetBehavior.this.halfExpandedOffset) {
                        i = BottomSheetBehavior.this.halfExpandedOffset;
                        i2 = 6;
                    } else {
                        i = BottomSheetBehavior.this.expandedOffset;
                    }
                    i2 = 3;
                } else if (BottomSheetBehavior.this.hideable && BottomSheetBehavior.this.shouldHide(view, f2)) {
                    if ((Math.abs(f) < Math.abs(f2) && f2 > 500.0f) || releasedLow(view)) {
                        i = BottomSheetBehavior.this.parentHeight;
                        i2 = 5;
                    } else {
                        if (BottomSheetBehavior.this.fitToContents) {
                            i = BottomSheetBehavior.this.fitToContentsOffset;
                        } else if (Math.abs(view.getTop() - BottomSheetBehavior.this.expandedOffset) < Math.abs(view.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                            i = BottomSheetBehavior.this.expandedOffset;
                        } else {
                            i = BottomSheetBehavior.this.halfExpandedOffset;
                            i2 = 6;
                        }
                        i2 = 3;
                    }
                } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                    int top = view.getTop();
                    if (BottomSheetBehavior.this.fitToContents) {
                        if (Math.abs(top - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                            i = BottomSheetBehavior.this.fitToContentsOffset;
                            i2 = 3;
                        } else {
                            i = BottomSheetBehavior.this.collapsedOffset;
                        }
                    } else {
                        if (top < BottomSheetBehavior.this.halfExpandedOffset) {
                            if (top < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                                i = BottomSheetBehavior.this.expandedOffset;
                                i2 = 3;
                            } else {
                                i = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                        } else if (Math.abs(top - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                            i = BottomSheetBehavior.this.halfExpandedOffset;
                        } else {
                            i = BottomSheetBehavior.this.collapsedOffset;
                        }
                        i2 = 6;
                    }
                } else if (BottomSheetBehavior.this.fitToContents) {
                    i = BottomSheetBehavior.this.collapsedOffset;
                } else {
                    int top2 = view.getTop();
                    if (Math.abs(top2 - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(top2 - BottomSheetBehavior.this.collapsedOffset)) {
                        i = BottomSheetBehavior.this.halfExpandedOffset;
                        i2 = 6;
                    } else {
                        i = BottomSheetBehavior.this.collapsedOffset;
                    }
                }
                BottomSheetBehavior.this.startSettlingAnimation(view, i2, i, true);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i, int i2) {
                return MathUtils.clamp(i, BottomSheetBehavior.this.getExpandedOffset(), BottomSheetBehavior.this.hideable ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                if (BottomSheetBehavior.this.hideable) {
                    return BottomSheetBehavior.this.parentHeight;
                }
                return BottomSheetBehavior.this.collapsedOffset;
            }
        };
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.saveFlags = 0;
        this.fitToContents = true;
        this.settleRunnable = null;
        this.halfExpandedRatio = HIDE_THRESHOLD;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.callbacks = new ArrayList<>();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.tds.common.widgets.behavior.BottomSheetBehavior.3
            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                if (BottomSheetBehavior.this.state == 1 || BottomSheetBehavior.this.touchingScrollingChild) {
                    return false;
                }
                if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == i) {
                    View view2 = BottomSheetBehavior.this.nestedScrollingChildRef != null ? BottomSheetBehavior.this.nestedScrollingChildRef.get() : null;
                    if (view2 != null && view2.canScrollVertically(-1)) {
                        return false;
                    }
                }
                return BottomSheetBehavior.this.viewRef != null && BottomSheetBehavior.this.viewRef.get() == view;
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
                BottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1 && BottomSheetBehavior.this.draggable) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            private boolean releasedLow(View view) {
                return view.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2;
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view, float f, float f2) {
                int i;
                int i2 = 4;
                if (f2 < 0.0f) {
                    if (BottomSheetBehavior.this.fitToContents) {
                        i = BottomSheetBehavior.this.fitToContentsOffset;
                    } else if (view.getTop() > BottomSheetBehavior.this.halfExpandedOffset) {
                        i = BottomSheetBehavior.this.halfExpandedOffset;
                        i2 = 6;
                    } else {
                        i = BottomSheetBehavior.this.expandedOffset;
                    }
                    i2 = 3;
                } else if (BottomSheetBehavior.this.hideable && BottomSheetBehavior.this.shouldHide(view, f2)) {
                    if ((Math.abs(f) < Math.abs(f2) && f2 > 500.0f) || releasedLow(view)) {
                        i = BottomSheetBehavior.this.parentHeight;
                        i2 = 5;
                    } else {
                        if (BottomSheetBehavior.this.fitToContents) {
                            i = BottomSheetBehavior.this.fitToContentsOffset;
                        } else if (Math.abs(view.getTop() - BottomSheetBehavior.this.expandedOffset) < Math.abs(view.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                            i = BottomSheetBehavior.this.expandedOffset;
                        } else {
                            i = BottomSheetBehavior.this.halfExpandedOffset;
                            i2 = 6;
                        }
                        i2 = 3;
                    }
                } else if (f2 == 0.0f || Math.abs(f) > Math.abs(f2)) {
                    int top = view.getTop();
                    if (BottomSheetBehavior.this.fitToContents) {
                        if (Math.abs(top - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                            i = BottomSheetBehavior.this.fitToContentsOffset;
                            i2 = 3;
                        } else {
                            i = BottomSheetBehavior.this.collapsedOffset;
                        }
                    } else {
                        if (top < BottomSheetBehavior.this.halfExpandedOffset) {
                            if (top < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                                i = BottomSheetBehavior.this.expandedOffset;
                                i2 = 3;
                            } else {
                                i = BottomSheetBehavior.this.halfExpandedOffset;
                            }
                        } else if (Math.abs(top - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(top - BottomSheetBehavior.this.collapsedOffset)) {
                            i = BottomSheetBehavior.this.halfExpandedOffset;
                        } else {
                            i = BottomSheetBehavior.this.collapsedOffset;
                        }
                        i2 = 6;
                    }
                } else if (BottomSheetBehavior.this.fitToContents) {
                    i = BottomSheetBehavior.this.collapsedOffset;
                } else {
                    int top2 = view.getTop();
                    if (Math.abs(top2 - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(top2 - BottomSheetBehavior.this.collapsedOffset)) {
                        i = BottomSheetBehavior.this.halfExpandedOffset;
                        i2 = 6;
                    } else {
                        i = BottomSheetBehavior.this.collapsedOffset;
                    }
                }
                BottomSheetBehavior.this.startSettlingAnimation(view, i2, i, true);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i, int i2) {
                return MathUtils.clamp(i, BottomSheetBehavior.this.getExpandedOffset(), BottomSheetBehavior.this.hideable ? BottomSheetBehavior.this.parentHeight : BottomSheetBehavior.this.collapsedOffset);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                if (BottomSheetBehavior.this.hideable) {
                    return BottomSheetBehavior.this.parentHeight;
                }
                return BottomSheetBehavior.this.collapsedOffset;
            }
        };
        this.peekHeightGestureInsetBuffer = UIUtils.dp2px(context, 48.0f);
        if (Build.VERSION.SDK_INT >= 21) {
            this.elevation = -1.0f;
        }
        setPeekHeight(-1);
        setHideable(false);
        setGestureInsetBottomIgnored(false);
        setFitToContents(true);
        setSkipCollapsed(false);
        setDraggable(true);
        setSaveFlags(0);
        setHalfExpandedRatio(HIDE_THRESHOLD);
        setExpandedOffset(0);
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), (BottomSheetBehavior<?>) this);
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, savedState.getSuperState());
        restoreOptionalState(savedState);
        if (savedState.state == 1 || savedState.state == 2) {
            this.state = 4;
        } else {
            this.state = savedState.state;
        }
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v)) {
            v.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = UIUtils.dp2px(coordinatorLayout.getContext(), 64.0f);
            setSystemGestureInsets(v);
            this.viewRef = new WeakReference<>(v);
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int top = v.getTop();
        coordinatorLayout.onLayoutChild(v, i);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentHeight = coordinatorLayout.getHeight();
        int height = v.getHeight();
        this.childHeight = height;
        this.fitToContentsOffset = Math.max(0, this.parentHeight - height);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        int i2 = this.state;
        if (i2 == 3) {
            ViewCompat.offsetTopAndBottom(v, getExpandedOffset());
        } else if (i2 == 6) {
            ViewCompat.offsetTopAndBottom(v, this.halfExpandedOffset);
        } else if (this.hideable && i2 == 5) {
            ViewCompat.offsetTopAndBottom(v, this.parentHeight);
        } else if (i2 == 4) {
            ViewCompat.offsetTopAndBottom(v, this.collapsedOffset);
        } else if (i2 == 1 || i2 == 2) {
            ViewCompat.offsetTopAndBottom(v, top - v.getTop());
        }
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(v));
        return true;
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper;
        if (!v.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            if (this.state != 2) {
                WeakReference<View> weakReference = this.nestedScrollingChildRef;
                View view = weakReference != null ? weakReference.get() : null;
                if (view != null && coordinatorLayout.isPointInChildBounds(view, x, this.initialY)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
            }
            this.ignoreEvents = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds(v, x, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents && (viewDragHelper = this.viewDragHelper) != null && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference<View> weakReference2 = this.nestedScrollingChildRef;
        View view2 = weakReference2 != null ? weakReference2.get() : null;
        return (actionMasked != 2 || view2 == null || this.ignoreEvents || this.state == 1 || coordinatorLayout.isPointInChildBounds(view2, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.viewDragHelper == null || Math.abs(((float) this.initialY) - motionEvent.getY()) <= ((float) this.viewDragHelper.getTouchSlop())) ? false : true;
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper != null) {
            viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (this.viewDragHelper != null && actionMasked == 2 && !this.ignoreEvents && Math.abs(this.initialY - motionEvent.getY()) > this.viewDragHelper.getTouchSlop()) {
            this.viewDragHelper.captureChildView(v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i, int i2) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i & 2) != 0;
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3) {
        if (i3 == 1) {
            return;
        }
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        if (view != (weakReference != null ? weakReference.get() : null)) {
            return;
        }
        int top = v.getTop();
        int i4 = top - i2;
        if (i2 > 0) {
            if (i4 < getExpandedOffset()) {
                iArr[1] = top - getExpandedOffset();
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                setStateInternal(3);
            } else {
                if (!this.draggable) {
                    return;
                }
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                setStateInternal(1);
            }
        } else if (i2 < 0 && !view.canScrollVertically(-1)) {
            int i5 = this.collapsedOffset;
            if (i4 <= i5 || this.hideable) {
                if (!this.draggable) {
                    return;
                }
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                setStateInternal(1);
            } else {
                iArr[1] = top - i5;
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                setStateInternal(4);
            }
        }
        dispatchOnSlide(v.getTop());
        this.lastNestedScrollDy = i2;
        this.nestedScrolled = true;
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i) {
        int i2;
        int i3 = 3;
        if (v.getTop() == getExpandedOffset()) {
            setStateInternal(3);
            return;
        }
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        if (weakReference != null && view == weakReference.get() && this.nestedScrolled) {
            if (this.lastNestedScrollDy > 0) {
                if (this.fitToContents) {
                    i2 = this.fitToContentsOffset;
                } else {
                    int top = v.getTop();
                    int i4 = this.halfExpandedOffset;
                    if (top > i4) {
                        i2 = i4;
                        i3 = 6;
                    } else {
                        i2 = this.expandedOffset;
                    }
                }
            } else if (this.hideable && shouldHide(v, getYVelocity())) {
                i2 = this.parentHeight;
                i3 = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int top2 = v.getTop();
                if (this.fitToContents) {
                    if (Math.abs(top2 - this.fitToContentsOffset) < Math.abs(top2 - this.collapsedOffset)) {
                        i2 = this.fitToContentsOffset;
                    } else {
                        i2 = this.collapsedOffset;
                        i3 = 4;
                    }
                } else {
                    int i5 = this.halfExpandedOffset;
                    if (top2 < i5) {
                        if (top2 < Math.abs(top2 - this.collapsedOffset)) {
                            i2 = this.expandedOffset;
                        } else {
                            i2 = this.halfExpandedOffset;
                        }
                    } else if (Math.abs(top2 - i5) < Math.abs(top2 - this.collapsedOffset)) {
                        i2 = this.halfExpandedOffset;
                    } else {
                        i2 = this.collapsedOffset;
                        i3 = 4;
                    }
                    i3 = 6;
                }
            } else {
                if (this.fitToContents) {
                    i2 = this.collapsedOffset;
                } else {
                    int top3 = v.getTop();
                    if (Math.abs(top3 - this.halfExpandedOffset) < Math.abs(top3 - this.collapsedOffset)) {
                        i2 = this.halfExpandedOffset;
                        i3 = 6;
                    } else {
                        i2 = this.collapsedOffset;
                    }
                }
                i3 = 4;
            }
            startSettlingAnimation(v, i3, i2, false);
            this.nestedScrolled = false;
        }
    }

    @Override // tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
        WeakReference<View> weakReference = this.nestedScrollingChildRef;
        if (weakReference == null || view != weakReference.get()) {
            return false;
        }
        return this.state != 3 || super.onNestedPreFling(coordinatorLayout, v, view, f, f2);
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public void setFitToContents(boolean z) {
        if (this.fitToContents == z) {
            return;
        }
        this.fitToContents = z;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
        }
        setStateInternal((this.fitToContents && this.state == 6) ? 3 : this.state);
    }

    public void setPeekHeight(int i) {
        setPeekHeight(i, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPeekHeight(int r4, boolean r5) {
        /*
            r3 = this;
            r0 = 1
            r1 = 0
            r2 = -1
            if (r4 != r2) goto Lc
            boolean r4 = r3.peekHeightAuto
            if (r4 != 0) goto L15
            r3.peekHeightAuto = r0
            goto L1f
        Lc:
            boolean r2 = r3.peekHeightAuto
            if (r2 != 0) goto L17
            int r2 = r3.peekHeight
            if (r2 == r4) goto L15
            goto L17
        L15:
            r0 = 0
            goto L1f
        L17:
            r3.peekHeightAuto = r1
            int r4 = java.lang.Math.max(r1, r4)
            r3.peekHeight = r4
        L1f:
            if (r0 == 0) goto L24
            r3.updatePeekHeight(r5)
        L24:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.widgets.behavior.BottomSheetBehavior.setPeekHeight(int, boolean):void");
    }

    protected void updatePeekHeight(boolean z) {
        V v;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state != 4 || (v = this.viewRef.get()) == null) {
                return;
            }
            if (z) {
                settleToStatePendingLayout(this.state);
            } else {
                v.requestLayout();
            }
        }
    }

    public int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    public void setHalfExpandedRatio(float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = f;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    public void setExpandedOffset(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("offset must be greater than or equal to 0");
        }
        this.expandedOffset = i;
    }

    public int getExpandedOffset() {
        return this.fitToContents ? this.fitToContentsOffset : this.expandedOffset;
    }

    public void setHideable(boolean z) {
        if (this.hideable != z) {
            this.hideable = z;
            if (z || this.state != 5) {
                return;
            }
            setState(4);
        }
    }

    public boolean isHideable() {
        return this.hideable;
    }

    public void setSkipCollapsed(boolean z) {
        this.skipCollapsed = z;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public void setDraggable(boolean z) {
        this.draggable = z;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setSaveFlags(int i) {
        this.saveFlags = i;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        Log.w(TAG, "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.callbacks.clear();
        if (bottomSheetCallback != null) {
            this.callbacks.add(bottomSheetCallback);
        }
    }

    public void addBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        if (this.callbacks.contains(bottomSheetCallback)) {
            return;
        }
        this.callbacks.add(bottomSheetCallback);
    }

    public void removeBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.callbacks.remove(bottomSheetCallback);
    }

    public void setState(int i) {
        if (i == this.state) {
            return;
        }
        if (this.viewRef == null) {
            if (i == 4 || i == 3 || i == 6 || (this.hideable && i == 5)) {
                this.state = i;
                return;
            }
            return;
        }
        settleToStatePendingLayout(i);
    }

    public void setGestureInsetBottomIgnored(boolean z) {
        this.gestureInsetBottomIgnored = z;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    private void settleToStatePendingLayout(final int i) {
        final V v = this.viewRef.get();
        if (v == null) {
            return;
        }
        ViewParent parent = v.getParent();
        if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(v)) {
            v.post(new Runnable() { // from class: com.tds.common.widgets.behavior.BottomSheetBehavior.1
                @Override // java.lang.Runnable
                public void run() {
                    BottomSheetBehavior.this.settleToState(v, i);
                }
            });
        } else {
            settleToState(v, i);
        }
    }

    public int getState() {
        return this.state;
    }

    void setStateInternal(int i) {
        V v;
        if (this.state == i) {
            return;
        }
        this.state = i;
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference == null || (v = weakReference.get()) == null) {
            return;
        }
        for (int i2 = 0; i2 < this.callbacks.size(); i2++) {
            this.callbacks.get(i2).onStateChanged(v, i);
        }
    }

    protected int calculatePeekHeight() {
        int i;
        if (this.peekHeightAuto) {
            return Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight);
        }
        if (!this.gestureInsetBottomIgnored && (i = this.gestureInsetBottom) > 0) {
            return Math.max(this.peekHeight, i + this.peekHeightGestureInsetBuffer);
        }
        return this.peekHeight;
    }

    protected void calculateCollapsedOffset() {
        int iCalculatePeekHeight = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - iCalculatePeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - iCalculatePeekHeight;
        }
    }

    protected void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) (this.parentHeight * (1.0f - this.halfExpandedRatio));
    }

    protected void reset() {
        this.activePointerId = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void restoreOptionalState(SavedState savedState) {
        int i = this.saveFlags;
        if (i == 0) {
            return;
        }
        if (i == -1 || (i & 1) == 1) {
            this.peekHeight = savedState.peekHeight;
        }
        int i2 = this.saveFlags;
        if (i2 == -1 || (i2 & 2) == 2) {
            this.fitToContents = savedState.fitToContents;
        }
        int i3 = this.saveFlags;
        if (i3 == -1 || (i3 & 4) == 4) {
            this.hideable = savedState.hideable;
        }
        int i4 = this.saveFlags;
        if (i4 == -1 || (i4 & 8) == 8) {
            this.skipCollapsed = savedState.skipCollapsed;
        }
    }

    boolean shouldHide(View view, float f) {
        if (this.skipCollapsed) {
            return true;
        }
        if (view.getTop() < this.collapsedOffset) {
            return false;
        }
        return Math.abs((((float) view.getTop()) + (f * HIDE_FRICTION)) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > HIDE_THRESHOLD;
    }

    View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View viewFindScrollingChild = findScrollingChild(viewGroup.getChildAt(i));
            if (viewFindScrollingChild != null) {
                return viewFindScrollingChild;
            }
        }
        return null;
    }

    protected void setSystemGestureInsets(View view) {
        if (Build.VERSION.SDK_INT < 29 || isGestureInsetBottomIgnored() || this.peekHeightAuto) {
            return;
        }
        ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.tds.common.widgets.behavior.BottomSheetBehavior.2
            @Override // tds.com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                BottomSheetBehavior.this.gestureInsetBottom = windowInsetsCompat.getMandatorySystemGestureInsets().bottom;
                BottomSheetBehavior.this.updatePeekHeight(false);
                return windowInsetsCompat;
            }
        });
    }

    protected float getYVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    void settleToState(View view, int i) {
        int expandedOffset;
        int i2;
        if (i == 4) {
            expandedOffset = this.collapsedOffset;
        } else if (i == 6) {
            int i3 = this.halfExpandedOffset;
            if (!this.fitToContents || i3 > (i2 = this.fitToContentsOffset)) {
                expandedOffset = i3;
            } else {
                expandedOffset = i2;
                i = 3;
            }
        } else if (i == 3) {
            expandedOffset = getExpandedOffset();
        } else if (this.hideable && i == 5) {
            expandedOffset = this.parentHeight;
        } else {
            throw new IllegalArgumentException("Illegal state argument: " + i);
        }
        startSettlingAnimation(view, i, expandedOffset, false);
    }

    void startSettlingAnimation(View view, int i, int i2, boolean z) {
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper != null && (!z ? !viewDragHelper.smoothSlideViewTo(view, view.getLeft(), i2) : !viewDragHelper.settleCapturedViewAt(view.getLeft(), i2))) {
            setStateInternal(2);
            if (this.settleRunnable == null) {
                this.settleRunnable = new SettleRunnable(view, i);
            }
            if (!this.settleRunnable.isPosted) {
                this.settleRunnable.targetState = i;
                ViewCompat.postOnAnimation(view, this.settleRunnable);
                this.settleRunnable.isPosted = true;
                return;
            }
            this.settleRunnable.targetState = i;
            return;
        }
        setStateInternal(i);
    }

    void dispatchOnSlide(int i) {
        float f;
        float expandedOffset;
        V v = this.viewRef.get();
        if (v == null || this.callbacks.isEmpty()) {
            return;
        }
        int i2 = this.collapsedOffset;
        if (i > i2 || i2 == getExpandedOffset()) {
            int i3 = this.collapsedOffset;
            f = i3 - i;
            expandedOffset = this.parentHeight - i3;
        } else {
            int i4 = this.collapsedOffset;
            f = i4 - i;
            expandedOffset = i4 - getExpandedOffset();
        }
        float f2 = f / expandedOffset;
        for (int i5 = 0; i5 < this.callbacks.size(); i5++) {
            this.callbacks.get(i5).onSlide(v, f2);
        }
    }

    int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    protected class SettleRunnable implements Runnable {
        protected boolean isPosted;
        int targetState;
        private final View view;

        SettleRunnable(View view, int i) {
            this.view = view;
            this.targetState = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BottomSheetBehavior.this.viewDragHelper != null && BottomSheetBehavior.this.viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                BottomSheetBehavior.this.setStateInternal(this.targetState);
            }
            this.isPosted = false;
        }
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.tds.common.widgets.behavior.BottomSheetBehavior.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
            this.peekHeight = parcel.readInt();
            this.fitToContents = parcel.readInt() == 1;
            this.hideable = parcel.readInt() == 1;
            this.skipCollapsed = parcel.readInt() == 1;
        }

        public SavedState(Parcelable parcelable, BottomSheetBehavior<?> bottomSheetBehavior) {
            super(parcelable);
            this.state = bottomSheetBehavior.state;
            this.peekHeight = ((BottomSheetBehavior) bottomSheetBehavior).peekHeight;
            this.fitToContents = bottomSheetBehavior.fitToContents;
            this.hideable = bottomSheetBehavior.hideable;
            this.skipCollapsed = bottomSheetBehavior.skipCollapsed;
        }

        @Deprecated
        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.state = i;
        }

        @Override // tds.androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
            parcel.writeInt(this.peekHeight);
            parcel.writeInt(this.fitToContents ? 1 : 0);
            parcel.writeInt(this.hideable ? 1 : 0);
            parcel.writeInt(this.skipCollapsed ? 1 : 0);
        }
    }

    public static <V extends View> BottomSheetBehavior<V> from(V v) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (!(behavior instanceof BottomSheetBehavior)) {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        return (BottomSheetBehavior) behavior;
    }
}
