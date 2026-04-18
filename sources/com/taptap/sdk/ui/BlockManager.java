package com.taptap.sdk.ui;

import android.app.Activity;
import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class BlockManager {
    private Activity mActivity;
    private List<Block> mBlocks = new LinkedList();

    public BlockManager(Activity activity) {
        if (activity instanceof IBlockHost) {
            this.mActivity = activity;
            return;
        }
        throw new IllegalArgumentException("Activity must be implements IBlockHost");
    }

    public BlockManager add(int i, Block block) {
        if (this.mBlocks.contains(block)) {
            throw new IllegalArgumentException("this Block has been added");
        }
        this.mBlocks.add(block);
        return add((ViewGroup) this.mActivity.findViewById(i), block);
    }

    public BlockManager remove(Block block) {
        if (this.mBlocks.contains(block)) {
            block.onDestroy();
            this.mBlocks.remove(block);
        }
        return this;
    }

    public BlockManager replace(int i, Block block) {
        if (!this.mBlocks.isEmpty()) {
            this.mBlocks.remove(r0.size() - 1).onDestroy();
        }
        return add(i, block);
    }

    private BlockManager add(ViewGroup viewGroup, Block block) {
        block.attachActivity(this.mActivity, viewGroup);
        return this;
    }

    public void onDestroy() {
        this.mBlocks.clear();
        this.mBlocks = null;
    }

    public List<Block> getBlocks() {
        return this.mBlocks;
    }

    public Block getCurrent() {
        if (this.mBlocks.isEmpty()) {
            return null;
        }
        return this.mBlocks.get(r0.size() - 1);
    }
}
