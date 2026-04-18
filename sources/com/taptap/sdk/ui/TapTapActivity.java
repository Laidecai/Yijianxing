package com.taptap.sdk.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.R;
import com.taptap.sdk.Utils;

/* JADX INFO: loaded from: classes.dex */
public class TapTapActivity extends BlockActivity {
    private static final String RECREATE_AND_USE_CLIENT_KEY = "recreate_and_use_client";
    private static boolean isRequesting = false;
    private boolean isFinishByDuplicateCreated = false;
    private boolean isRecreateAndLoginByClient = false;

    public void setLayout(Configuration configuration) {
    }

    @Override // com.taptap.sdk.ui.BlockActivity, com.taptap.sdk.ui.IBlockHost
    public /* bridge */ /* synthetic */ BlockManager getBlockManager() {
        return super.getBlockManager();
    }

    @Override // com.taptap.sdk.ui.BlockActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        LoginRequest loginRequest;
        super.onCreate(bundle);
        if (bundle != null) {
            this.isRecreateAndLoginByClient = bundle.getBoolean(RECREATE_AND_USE_CLIENT_KEY, false);
        }
        Log.d("TapLoginResult", "TapTapLogin create isRecreate = " + this.isRecreateAndLoginByClient + " isRequesting = " + isRequesting);
        if (!Utils.isLoginSDKInitialized() || isRequesting) {
            if (isRequesting) {
                this.isFinishByDuplicateCreated = true;
            }
            finish();
            return;
        }
        setContentView(R.layout.sdk_activity_container);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 1;
                window.getDecorView().setSystemUiVisibility(1280);
            }
            window.setAttributes(attributes);
        }
        if (getIntent() != null && (loginRequest = (LoginRequest) getIntent().getParcelableExtra("request")) != null) {
            LoginBlock loginBlock = new LoginBlock();
            loginBlock.setNeedRequestAuthorize(!this.isRecreateAndLoginByClient);
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("request", loginRequest);
            loginBlock.setArguments(bundle2);
            getBlockManager().add(R.id.taptap_sdk_container, loginBlock);
        }
        isRequesting = true;
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.d("TapLoginResult", "TapTapLogin onSaveInstanceState ");
        Block current = getBlockManager().getCurrent();
        boolean zIsAuthorizeByClient = (current == null || !(current instanceof LoginBlock)) ? false : ((LoginBlock) current).isAuthorizeByClient();
        bundle.putBoolean(RECREATE_AND_USE_CLIENT_KEY, zIsAuthorizeByClient);
        Log.d("TapLoginResult", "TapTapLogin onSaveInstanceState lastUseClient = " + zIsAuthorizeByClient);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        setLayout(getResources().getConfiguration());
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        Block current = getBlockManager().getCurrent();
        if (current != null && (current instanceof WebBlock)) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.taptap.sdk.ui.BlockActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setLayout(configuration);
    }

    @Override // com.taptap.sdk.ui.BlockActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.d("TapLoginResult", "TapTapLogin onActivityResult resultCode = " + i2);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        try {
            BlockManager blockManager = getBlockManager();
            if (blockManager != null) {
                blockManager.remove(blockManager.getCurrent());
            }
        } catch (Exception unused) {
        }
        Log.d("TapLoginResult", "TapTapLogin destroy");
        if (this.isFinishByDuplicateCreated) {
            return;
        }
        isRequesting = false;
    }
}
