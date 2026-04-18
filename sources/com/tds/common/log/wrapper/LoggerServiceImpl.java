package com.tds.common.log.wrapper;

import android.app.Activity;
import com.tds.common.log.Logger;
import com.tds.common.log.entities.LogConfig;
import com.tds.common.net.json.JsonUtil;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class LoggerServiceImpl implements LoggerService {
    @Override // com.tds.common.log.wrapper.LoggerService
    public void init(Activity activity, String str) {
        try {
            LogConfig logConfig = (LogConfig) JsonUtil.parse(str, LogConfig.class);
            Logger.init(new LogConfig.Builder().withTopic(logConfig.sdkName).withSdkVersionCode(logConfig.sdkVersionCode).withSdkVersionName(logConfig.sdkVersionName).build(activity));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tds.common.log.wrapper.LoggerService
    public void log(String str, String str2, String str3) {
        Logger.get(str).logInfo(str2, str3);
    }
}
