package com.tds.common;

import com.tds.common.entities.TapConfig;

/* JADX INFO: loaded from: classes.dex */
public interface ITapCommon {
    TapConfig getTapConfig();

    void init(TapConfig tapConfig);

    boolean isInitialized();

    void setDurationStatisticsEnabled(boolean z);
}
