package com.tds.common.log.wrapper;

import android.app.Activity;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.annotation.BridgeParam;
import com.tds.common.bridge.annotation.BridgeService;
import com.tds.common.log.constants.CommonParam;

/* JADX INFO: loaded from: classes.dex */
@BridgeService("TDSLoggerService")
public interface LoggerService extends IBridgeService {
    @BridgeMethod("init")
    void init(Activity activity, @BridgeParam("LogConfig") String str);

    @BridgeMethod("log")
    void log(@BridgeParam("sdkName") String str, @BridgeParam("tag") String str2, @BridgeParam(CommonParam.MESSAGE) String str3);
}
