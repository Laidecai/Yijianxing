package com.taptap.sdk.ui;

import android.os.Bundle;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.LoginResponse;
import com.taptap.sdk.tracker.TapTapLoginTrackerHelper;
import com.taptap.sdk.ui.WebBlock;
import com.tds.common.tracker.annotations.Login;

/* JADX INFO: loaded from: classes.dex */
class WebViewHandler extends BaseHandler {
    private ActivityDelegate activity;

    public WebViewHandler(ActivityDelegate activityDelegate) {
        this.activity = activityDelegate;
    }

    @Override // com.taptap.sdk.ui.BaseHandler
    public void authorize(LoginRequest loginRequest) {
        WebBlock webBlock = new WebBlock();
        Bundle bundle = new Bundle();
        bundle.putParcelable("request", loginRequest);
        webBlock.setArguments(bundle);
        webBlock.setLoginCallback(new WebBlock.IWebLoginCallback() { // from class: com.taptap.sdk.ui.WebViewHandler.1
            @Override // com.taptap.sdk.ui.WebBlock.IWebLoginCallback
            public void onResponse(LoginResponse loginResponse, String str) {
                WebViewHandler.this.activity.setResult(-1, loginResponse.toIntent(str));
                WebViewHandler.this.activity.finish();
            }
        });
        TapTapLoginTrackerHelper.authorizationOpen(Login.WEBVIEW_LOGIN_TYPE);
        this.activity.startBlock(webBlock);
    }

    public ActivityDelegate getActivity() {
        return this.activity;
    }
}
