package com.taptap.sdk.ui;

import android.content.Intent;
import com.taptap.sdk.R;

/* JADX INFO: loaded from: classes.dex */
class ActivityDelegate {
    public Block block;

    public ActivityDelegate(Block block) {
        this.block = block;
    }

    public boolean isPortrait() {
        Block block = this.block;
        return block == null || block.getActivity() == null || this.block.getActivity().getResources().getConfiguration().orientation == 1;
    }

    public void startActivityForResult(Intent intent, int i) throws Exception {
        Block block = this.block;
        if (block != null) {
            block.startActivityForResult(intent, i);
        }
    }

    public void startBlock(Block block) {
        this.block.getBlockManager().replace(R.id.taptap_sdk_container, block);
        this.block = block;
    }

    public void setResult(int i, Intent intent) {
        Block block = this.block;
        if (block == null || block.getActivity() == null) {
            return;
        }
        this.block.getActivity().setResult(i, intent);
    }

    public void finish() {
        Block block = this.block;
        if (block == null || block.getActivity() == null) {
            return;
        }
        this.block.getActivity().finish();
    }
}
