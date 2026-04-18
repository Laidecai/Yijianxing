package com.taptap.sdk.model;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameInitializeData {
    public CloudGameInitializeCGPN cloudGameCGPN;
    public String sdkInfo;

    public static class CloudGameInitializeCGPN {
        public List<String> loginVerions;
        public List<String> payVersions;
    }
}
