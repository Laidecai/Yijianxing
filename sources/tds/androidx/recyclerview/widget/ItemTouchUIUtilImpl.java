package tds.androidx.recyclerview.widget;

import android.graphics.Canvas;
import android.view.View;
import tds.androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
class ItemTouchUIUtilImpl implements ItemTouchUIUtil {
    static final ItemTouchUIUtil INSTANCE = new ItemTouchUIUtilImpl();

    @Override // tds.androidx.recyclerview.widget.ItemTouchUIUtil
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
    }

    @Override // tds.androidx.recyclerview.widget.ItemTouchUIUtil
    public void onSelected(View view) {
    }

    ItemTouchUIUtilImpl() {
    }

    @Override // tds.androidx.recyclerview.widget.ItemTouchUIUtil
    public void onDraw(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
    }

    private static float findMaxElevation(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        float f = 0.0f;
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            if (childAt != view) {
                float elevation = ViewCompat.getElevation(childAt);
                if (elevation > f) {
                    f = elevation;
                }
            }
        }
        return f;
    }

    @Override // tds.androidx.recyclerview.widget.ItemTouchUIUtil
    public void clearView(View view) {
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }
}
