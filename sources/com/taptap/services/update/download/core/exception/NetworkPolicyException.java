package com.taptap.services.update.download.core.exception;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class NetworkPolicyException extends IOException {
    public NetworkPolicyException() {
        super("Only allows downloading this task on the wifi network type!");
    }
}
