package com.taptap.sdk.tracker;

import com.taptap.sdk.tracker.event.AuthorizationBackEvent;
import com.taptap.sdk.tracker.event.AuthorizationOpenEvent;
import com.taptap.sdk.tracker.event.AuthorizationProfileEvent;
import com.taptap.sdk.tracker.event.AuthorizationTokenEvent;
import com.tds.common.reactor.RxBus;

/* JADX INFO: loaded from: classes.dex */
public class TapTapLoginTrackerHelper {
    private static String authorizationType = "not_defined";

    public static void authorizationOpen(String str) {
        authorizationType = str;
        RxBus.getInstance().send(new AuthorizationOpenEvent(authorizationType));
    }

    public static void authorizationBack() {
        RxBus.getInstance().send(new AuthorizationBackEvent(authorizationType));
    }

    public static void authorizationToken() {
        RxBus.getInstance().send(new AuthorizationTokenEvent());
    }

    public static void authorizationProfile() {
        RxBus.getInstance().send(new AuthorizationProfileEvent());
    }
}
