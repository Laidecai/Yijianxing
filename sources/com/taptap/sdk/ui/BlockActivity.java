package com.taptap.sdk.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class BlockActivity extends Activity implements IBlockHost {
    private BlockManager mBlockManager;

    BlockActivity() {
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mBlockManager = new BlockManager(this);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Iterator<Block> it = this.mBlockManager.getBlocks().iterator();
        while (it.hasNext()) {
            it.next().onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Iterator<Block> it = this.mBlockManager.getBlocks().iterator();
        while (it.hasNext()) {
            it.next().onConfigurationChanged(configuration);
        }
    }

    @Override // com.taptap.sdk.ui.IBlockHost
    public BlockManager getBlockManager() {
        return this.mBlockManager;
    }
}
