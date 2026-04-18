package com.taptap.sdk.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
class Block {
    private Activity activity;
    Bundle arguments;
    private boolean called = false;
    private View view;

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    protected void onCreate() {
    }

    protected View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return null;
    }

    protected void onDestroy() {
    }

    protected void onViewCreated(View view) {
    }

    Block() {
    }

    void attachActivity(Activity activity, ViewGroup viewGroup) {
        onAttach(activity);
        this.called = true;
        this.view = onCreateView(LayoutInflater.from(activity), viewGroup);
        onCreate();
        viewGroup.addView(this.view);
        onViewCreated(this.view);
    }

    protected void onAttach(Activity activity) {
        this.activity = activity;
    }

    public void setArguments(Bundle bundle) {
        this.arguments = bundle;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public final Bundle getArguments() {
        return this.arguments;
    }

    public BlockManager getBlockManager() {
        return ((IBlockHost) this.activity).getBlockManager();
    }

    public void startActivityForResult(Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (!this.called) {
            throw new IllegalStateException("Block " + this + " not attached to Activity");
        }
        this.activity.startActivityForResult(intent, i, bundle);
    }
}
