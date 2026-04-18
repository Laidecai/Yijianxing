package com.taptap.sdk.ui;

import com.taptap.sdk.LoginRequest;

/* JADX INFO: loaded from: classes.dex */
abstract class BaseHandler {
    abstract void authorize(LoginRequest loginRequest) throws Exception;

    BaseHandler() {
    }
}
