package com.taptap.sdk.tracker.event;

/* JADX INFO: loaded from: classes.dex */
public class AuthorizationBackEvent {
    private String authorizationType;

    public String getAuthorizationType() {
        return this.authorizationType;
    }

    public AuthorizationBackEvent(String str) {
        this.authorizationType = "";
        this.authorizationType = str;
    }
}
