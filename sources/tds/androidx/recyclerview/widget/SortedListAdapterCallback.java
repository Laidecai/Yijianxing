package tds.androidx.recyclerview.widget;

import tds.androidx.recyclerview.widget.RecyclerView;
import tds.androidx.recyclerview.widget.SortedList;

/* JADX INFO: loaded from: classes.dex */
public abstract class SortedListAdapterCallback<T2> extends SortedList.Callback<T2> {
    final RecyclerView.Adapter mAdapter;

    public SortedListAdapterCallback(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    @Override // tds.androidx.recyclerview.widget.ListUpdateCallback
    public void onInserted(int i, int i2) {
        this.mAdapter.notifyItemRangeInserted(i, i2);
    }

    @Override // tds.androidx.recyclerview.widget.ListUpdateCallback
    public void onRemoved(int i, int i2) {
        this.mAdapter.notifyItemRangeRemoved(i, i2);
    }

    @Override // tds.androidx.recyclerview.widget.ListUpdateCallback
    public void onMoved(int i, int i2) {
        this.mAdapter.notifyItemMoved(i, i2);
    }

    @Override // tds.androidx.recyclerview.widget.SortedList.Callback
    public void onChanged(int i, int i2) {
        this.mAdapter.notifyItemRangeChanged(i, i2);
    }

    @Override // tds.androidx.recyclerview.widget.SortedList.Callback, tds.androidx.recyclerview.widget.ListUpdateCallback
    public void onChanged(int i, int i2, Object obj) {
        this.mAdapter.notifyItemRangeChanged(i, i2, obj);
    }
}
