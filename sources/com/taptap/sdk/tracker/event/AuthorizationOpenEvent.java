package com.taptap.sdk.tracker.event;

/* JADX INFO: loaded from: classes.dex */
public class AuthorizationOpenEvent {
    private String authorizationType;

    public String getAuthorizationType() {
        return this.authorizationType;
    }

    public AuthorizationOpenEvent(String str) {
        this.authorizationType = "";
        this.authorizationType = str;
    }
}
