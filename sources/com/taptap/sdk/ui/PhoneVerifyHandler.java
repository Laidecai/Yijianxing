package com.taptap.sdk.ui;

import com.taptap.sdk.LoginRequest;

/* JADX INFO: loaded from: classes.dex */
public class PhoneVerifyHandler extends BaseHandler {
    private ActivityDelegate activity;

    public PhoneVerifyHandler(ActivityDelegate activityDelegate) {
        this.activity = activityDelegate;
    }

    @Override // com.taptap.sdk.ui.BaseHandler
    void authorize(LoginRequest loginRequest) {
        this.activity.startBlock(new PhoneVerifyBlock());
    }
}
