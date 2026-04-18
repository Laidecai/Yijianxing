package com.taptap.sdk.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.R;
import com.taptap.sdk.tracker.TapTapLoginTrackerHelper;

/* JADX INFO: loaded from: classes.dex */
class LoginBlock extends Block {
    private LoginClient client = null;
    private boolean startAuthorizeByClient = false;
    private boolean needRequestAuthorize = true;

    LoginBlock() {
    }

    @Override // com.taptap.sdk.ui.Block
    protected View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_login, viewGroup, false);
    }

    @Override // com.taptap.sdk.ui.Block
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        if (this.needRequestAuthorize) {
            LoginRequest loginRequest = (LoginRequest) getArguments().getParcelable("request");
            LoginClient loginClient = new LoginClient(new ActivityDelegate(this));
            this.client = loginClient;
            this.startAuthorizeByClient = loginClient.sendLoginRequest(loginRequest);
        }
    }

    @Override // com.taptap.sdk.ui.Block
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != 0) {
            getActivity().setResult(i2, intent);
        }
        TapTapLoginTrackerHelper.authorizationBack();
        getActivity().finish();
    }

    public void setNeedRequestAuthorize(boolean z) {
        this.needRequestAuthorize = z;
    }

    protected boolean isAuthorizeByClient() {
        return this.startAuthorizeByClient;
    }

    @Override // com.taptap.sdk.ui.Block
    protected void onDestroy() {
        super.onDestroy();
        LoginClient loginClient = this.client;
        if (loginClient == null || loginClient.getServiceConnection() == null) {
            return;
        }
        getActivity().unbindService(this.client.getServiceConnection());
    }
}
