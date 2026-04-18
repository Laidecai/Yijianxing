package com.tds.common.widgets.behavior;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import com.tds.common.utils.UIUtils;
import com.tds.common.widgets.behavior.BottomSheetBehavior;
import java.lang.ref.WeakReference;
import tds.androidx.coordinatorlayout.widget.CoordinatorLayout;
import tds.androidx.core.math.MathUtils;
import tds.androidx.core.view.ViewCompat;
import tds.androidx.core.view.WindowInsetsCompat;
import tds.androidx.customview.widget.ViewDragHelper;
import tds.com.google.android.material.internal.ViewUtils;

/* JADX INFO: loaded from: classes.dex */
public class RightSheetBehavior<V extends View> extends BottomSheetBehavior<V> {
    private final ViewDragHelper.Callback dragCallback;

    public RightSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.tds.common.widgets.behavior.RightSheetBehavior.2
            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                if (RightSheetBehavior.this.state == 1 || RightSheetBehavior.this.touchingScrollingChild) {
                    return false;
                }
                if (RightSheetBehavior.this.state == 3 && RightSheetBehavior.this.activePointerId == i) {
                    View view2 = RightSheetBehavior.this.nestedScrollingChildRef != null ? RightSheetBehavior.this.nestedScrollingChildRef.get() : null;
                    if (view2 != null && view2.canScrollHorizontally(-1)) {
                        return false;
                    }
                }
                return RightSheetBehavior.this.viewRef != null && RightSheetBehavior.this.viewRef.get() == view;
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
                RightSheetBehavior.this.dispatchOnSlide(i);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1 && RightSheetBehavior.this.draggable) {
                    RightSheetBehavior.this.setStateInternal(1);
                }
            }

            private boolean releasedLow(View view) {
                return view.getLeft() > (RightSheetBehavior.this.parentHeight + RightSheetBehavior.this.getExpandedOffset()) / 2;
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(View view, float f, float f2) {
                int i;
                int i2 = 4;
                if (f < 0.0f) {
                    if (RightSheetBehavior.this.fitToContents) {
                        i = RightSheetBehavior.this.fitToContentsOffset;
                    } else if (view.getLeft() > RightSheetBehavior.this.halfExpandedOffset) {
                        i = RightSheetBehavior.this.halfExpandedOffset;
                        i2 = 6;
                    } else {
                        i = RightSheetBehavior.this.expandedOffset;
                    }
                    i2 = 3;
                } else if (RightSheetBehavior.this.hideable && RightSheetBehavior.this.shouldHide(view, f)) {
                    if ((Math.abs(f2) < Math.abs(f) && f > 500.0f) || releasedLow(view)) {
                        i = RightSheetBehavior.this.parentHeight;
                        i2 = 5;
                    } else {
                        if (RightSheetBehavior.this.fitToContents) {
                            i = RightSheetBehavior.this.fitToContentsOffset;
                        } else if (Math.abs(view.getLeft() - RightSheetBehavior.this.expandedOffset) < Math.abs(view.getLeft() - RightSheetBehavior.this.halfExpandedOffset)) {
                            i = RightSheetBehavior.this.expandedOffset;
                        } else {
                            i = RightSheetBehavior.this.halfExpandedOffset;
                            i2 = 6;
                        }
                        i2 = 3;
                    }
                } else if (f == 0.0f || Math.abs(f2) > Math.abs(f)) {
                    int left = view.getLeft();
                    if (RightSheetBehavior.this.fitToContents) {
                        if (Math.abs(left - RightSheetBehavior.this.fitToContentsOffset) < Math.abs(left - RightSheetBehavior.this.collapsedOffset)) {
                            i = RightSheetBehavior.this.fitToContentsOffset;
                            i2 = 3;
                        } else {
                            i = RightSheetBehavior.this.collapsedOffset;
                        }
                    } else {
                        if (left < RightSheetBehavior.this.halfExpandedOffset) {
                            if (left < Math.abs(left - RightSheetBehavior.this.collapsedOffset)) {
                                i = RightSheetBehavior.this.expandedOffset;
                                i2 = 3;
                            } else {
                                i = RightSheetBehavior.this.halfExpandedOffset;
                            }
                        } else if (Math.abs(left - RightSheetBehavior.this.halfExpandedOffset) < Math.abs(left - RightSheetBehavior.this.collapsedOffset)) {
                            i = RightSheetBehavior.this.halfExpandedOffset;
                        } else {
                            i = RightSheetBehavior.this.collapsedOffset;
                        }
                        i2 = 6;
                    }
                } else if (RightSheetBehavior.this.fitToContents) {
                    i = RightSheetBehavior.this.collapsedOffset;
                } else {
                    int left2 = view.getLeft();
                    if (Math.abs(left2 - RightSheetBehavior.this.halfExpandedOffset) < Math.abs(left2 - RightSheetBehavior.this.collapsedOffset)) {
                        i = RightSheetBehavior.this.halfExpandedOffset;
                        i2 = 6;
                    } else {
                        i = RightSheetBehavior.this.collapsedOffset;
                    }
                }
                RightSheetBehavior.this.startSettlingAnimation(view, i2, i, true);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i, int i2) {
                return view.getTop();
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i, int i2) {
                return MathUtils.clamp(i, RightSheetBehavior.this.getExpandedOffset(), RightSheetBehavior.this.hideable ? RightSheetBehavior.this.parentHeight : RightSheetBehavior.this.collapsedOffset);
            }

            @Override // tds.androidx.customview.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                if (RightSheetBehavior.this.hideable) {
                    return RightSheetBehavior.this.parentHeight;
                }
                return RightSheetBehavior.this.collapsedOffset;
            }
        };
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior, tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
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
        int left = v.getLeft();
        coordinatorLayout.onLayoutChild(v, i);
        this.parentWidth = coordinatorLayout.getHeight();
        this.parentHeight = coordinatorLayout.getWidth();
        this.childHeight = v.getWidth();
        this.fitToContentsOffset = Math.max(0, this.parentHeight - this.childHeight);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        if (this.state == 3) {
            ViewCompat.offsetLeftAndRight(v, getExpandedOffset());
        } else if (this.state == 6) {
            ViewCompat.offsetLeftAndRight(v, this.halfExpandedOffset);
        } else if (this.hideable && this.state == 5) {
            ViewCompat.offsetLeftAndRight(v, this.parentHeight);
        } else if (this.state == 4) {
            ViewCompat.offsetLeftAndRight(v, this.collapsedOffset);
        } else if (this.state == 1 || this.state == 2) {
            ViewCompat.offsetLeftAndRight(v, left - v.getLeft());
        }
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(v));
        return true;
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior, tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
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
            int y = (int) motionEvent.getY();
            this.initialY = (int) motionEvent.getX();
            if (this.state != 2) {
                View view = this.nestedScrollingChildRef != null ? this.nestedScrollingChildRef.get() : null;
                if (view != null && coordinatorLayout.isPointInChildBounds(view, this.initialY, y)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
            }
            this.ignoreEvents = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds(v, this.initialY, y);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents && this.viewDragHelper != null && this.viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        View view2 = this.nestedScrollingChildRef != null ? this.nestedScrollingChildRef.get() : null;
        return (actionMasked != 2 || view2 == null || this.ignoreEvents || this.state == 1 || coordinatorLayout.isPointInChildBounds(view2, (int) motionEvent.getX(), (int) motionEvent.getY()) || this.viewDragHelper == null || Math.abs(((float) this.initialY) - motionEvent.getX()) <= ((float) this.viewDragHelper.getTouchSlop())) ? false : true;
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior, tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        if (this.viewDragHelper != null) {
            this.viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (this.viewDragHelper != null && actionMasked == 2 && !this.ignoreEvents && Math.abs(this.initialY - motionEvent.getX()) > this.viewDragHelper.getTouchSlop()) {
            this.viewDragHelper.captureChildView(v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior, tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i, int i2) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i & 1) != 0;
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior, tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3) {
        if (i3 == 1) {
            return;
        }
        if (view != (this.nestedScrollingChildRef != null ? this.nestedScrollingChildRef.get() : null)) {
            return;
        }
        int left = v.getLeft();
        int i4 = left - i;
        if (i > 0) {
            if (i4 < getExpandedOffset()) {
                iArr[0] = left - getExpandedOffset();
                ViewCompat.offsetLeftAndRight(v, -iArr[0]);
                setStateInternal(3);
            } else {
                if (!this.draggable) {
                    return;
                }
                iArr[0] = i;
                ViewCompat.offsetLeftAndRight(v, -i);
                setStateInternal(1);
            }
        } else if (i < 0 && !view.canScrollHorizontally(-1)) {
            if (i4 <= this.collapsedOffset || this.hideable) {
                if (!this.draggable) {
                    return;
                }
                iArr[0] = i;
                ViewCompat.offsetLeftAndRight(v, -i);
                setStateInternal(1);
            } else {
                iArr[0] = left - this.collapsedOffset;
                ViewCompat.offsetLeftAndRight(v, -iArr[0]);
                setStateInternal(4);
            }
        }
        dispatchOnSlide(v.getLeft());
        this.lastNestedScrollDy = i;
        this.nestedScrolled = true;
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior, tds.androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i) {
        int i2;
        int i3 = 3;
        if (v.getLeft() == getExpandedOffset()) {
            setStateInternal(3);
            return;
        }
        if (this.nestedScrollingChildRef != null && view == this.nestedScrollingChildRef.get() && this.nestedScrolled) {
            if (this.lastNestedScrollDy > 0) {
                if (this.fitToContents) {
                    i2 = this.fitToContentsOffset;
                } else if (v.getLeft() > this.halfExpandedOffset) {
                    i2 = this.halfExpandedOffset;
                    i3 = 6;
                } else {
                    i2 = this.expandedOffset;
                }
            } else if (this.hideable && shouldHide(v, getYVelocity())) {
                i2 = this.parentHeight;
                i3 = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int left = v.getLeft();
                if (this.fitToContents) {
                    if (Math.abs(left - this.fitToContentsOffset) < Math.abs(left - this.collapsedOffset)) {
                        i2 = this.fitToContentsOffset;
                    } else {
                        i2 = this.collapsedOffset;
                        i3 = 4;
                    }
                } else {
                    if (left < this.halfExpandedOffset) {
                        if (left < Math.abs(left - this.collapsedOffset)) {
                            i2 = this.expandedOffset;
                        } else {
                            i2 = this.halfExpandedOffset;
                        }
                    } else if (Math.abs(left - this.halfExpandedOffset) < Math.abs(left - this.collapsedOffset)) {
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
                    int left2 = v.getLeft();
                    if (Math.abs(left2 - this.halfExpandedOffset) < Math.abs(left2 - this.collapsedOffset)) {
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

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior
    boolean shouldHide(View view, float f) {
        if (this.skipCollapsed) {
            return true;
        }
        if (view.getLeft() < this.collapsedOffset) {
            return false;
        }
        return Math.abs((((float) view.getLeft()) + (f * 0.1f)) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f;
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior
    protected void setSystemGestureInsets(View view) {
        if (Build.VERSION.SDK_INT < 29 || isGestureInsetBottomIgnored() || this.peekHeightAuto) {
            return;
        }
        ViewUtils.doOnApplyWindowInsets(view, new ViewUtils.OnApplyWindowInsetsListener() { // from class: com.tds.common.widgets.behavior.RightSheetBehavior.1
            @Override // tds.com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                RightSheetBehavior.this.gestureInsetBottom = windowInsetsCompat.getMandatorySystemGestureInsets().right;
                RightSheetBehavior.this.updatePeekHeight(false);
                return windowInsetsCompat;
            }
        });
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior
    protected float getYVelocity() {
        if (this.velocityTracker == null) {
            return 0.0f;
        }
        this.velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getXVelocity(this.activePointerId);
    }

    @Override // com.tds.common.widgets.behavior.BottomSheetBehavior
    void startSettlingAnimation(View view, int i, int i2, boolean z) {
        if (this.viewDragHelper != null && (!z ? !this.viewDragHelper.smoothSlideViewTo(view, i2, view.getTop()) : !this.viewDragHelper.settleCapturedViewAt(i2, view.getTop()))) {
            setStateInternal(2);
            if (this.settleRunnable == null) {
                this.settleRunnable = new BottomSheetBehavior.SettleRunnable(view, i);
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
}
