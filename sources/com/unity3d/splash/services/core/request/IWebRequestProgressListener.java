package com.unity3d.splash.services.core.request;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface IWebRequestProgressListener {
    void onRequestProgress(String str, long j, long j2);

    void onRequestStart(String str, long j, int i, Map map);
}
