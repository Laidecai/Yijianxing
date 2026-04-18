package com.taptap.sdk.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.taptap.sdk.R;

/* JADX INFO: loaded from: classes.dex */
public class PhoneVerifyBlock extends Block {
    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ Activity getActivity() {
        return super.getActivity();
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ BlockManager getBlockManager() {
        return super.getBlockManager();
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i) {
        super.startActivityForResult(intent, i);
    }

    @Override // com.taptap.sdk.ui.Block
    public /* bridge */ /* synthetic */ void startActivityForResult(Intent intent, int i, Bundle bundle) {
        super.startActivityForResult(intent, i, bundle);
    }

    @Override // com.taptap.sdk.ui.Block
    protected View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.sdk_activity_container, viewGroup, false);
    }
}
