package com.tds.common;

import com.tds.common.entities.TapConfig;
import com.tds.common.net.Skynet;
import com.tds.common.net.TdsApiClient;
import com.tds.common.net.TdsHttp;
import com.tds.common.tracker.SdkDurationStatistics;

/* JADX INFO: loaded from: classes.dex */
public class TapCommonImpl implements ITapCommon {
    public static final String TAP_COMMON_OPEN_API = "tap_common_open_api";
    private boolean initialized = false;
    private TapConfig tapConfig;

    @Override // com.tds.common.ITapCommon
    public void init(TapConfig tapConfig) {
        TapConfig tapConfig2 = this.tapConfig;
        if (tapConfig2 == null) {
            this.tapConfig = tapConfig;
        } else {
            tapConfig2.append(tapConfig);
        }
        if (this.initialized) {
            return;
        }
        initSkynet();
        this.initialized = true;
    }

    @Override // com.tds.common.ITapCommon
    public boolean isInitialized() {
        return this.initialized;
    }

    private void initSkynet() {
        Skynet.getInstance().registerTdsClient(TAP_COMMON_OPEN_API, new TdsApiClient.Builder().tdsClient(TdsHttp.newClientBuilder().build()).baseUrl("").build());
    }

    @Override // com.tds.common.ITapCommon
    public TapConfig getTapConfig() {
        return this.tapConfig;
    }

    @Override // com.tds.common.ITapCommon
    public void setDurationStatisticsEnabled(boolean z) {
        SdkDurationStatistics.setEnableSdkDurationStatistics(z);
    }
}
