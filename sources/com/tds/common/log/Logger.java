package com.tds.common.log;

import android.text.TextUtils;
import android.util.Log;
import com.tds.common.log.constants.BusinessType;
import com.tds.common.log.entities.LogConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class Logger {
    private static Map<String, Logger> mTagCache = new ConcurrentHashMap();
    private LogConfig logConfig;
    private String tag;

    public void logInfo(String str, String str2) {
    }

    public static void init(LogConfig logConfig) {
        getLogger(logConfig);
    }

    public static Logger getLogger(LogConfig logConfig) {
        if (logConfig.sdkName.isEmpty()) {
            throw new IllegalArgumentException("topic name empty");
        }
        String str = "";
        if (!TextUtils.isEmpty(logConfig.sdkName)) {
            str = "" + logConfig.sdkName;
        }
        if (mTagCache.containsKey(str)) {
            return mTagCache.get(str);
        }
        Logger logger = new Logger(str);
        logger.logConfig = logConfig;
        mTagCache.put(logConfig.sdkName, logger);
        return logger;
    }

    public static Logger get(String str) {
        return getLogger(new LogConfig.Builder().withTopic(str).build(null));
    }

    public static Logger getCommonLogger() {
        return getLogger(new LogConfig.Builder().withTopic(BusinessType.COMMON_LOG).withSdkVersionCode(32900001).withSdkVersionName("3.29.0").build(null));
    }

    private Logger(String str) {
        this.tag = "TDS-" + str;
    }

    public void v(String str) {
        v("", str);
    }

    public void v(String str, String str2) {
        Log.v(getCombinationTag(str), str2);
        logInfo(str, str2);
    }

    public void v(String str, Throwable th) {
        v("", str, th);
    }

    public void v(String str, String str2, Throwable th) {
        Log.v(getCombinationTag(str), str2, th);
        logInfo(str, str2);
    }

    public void d(String str) {
        d("", str);
    }

    public void d(String str, String str2) {
        Log.d(getCombinationTag(str), str2);
        logInfo(str, str2);
    }

    public void d(String str, Throwable th) {
        d("", str, th);
    }

    public void d(String str, String str2, Throwable th) {
        Log.d(getCombinationTag(str), str2, th);
        logInfo(str, str2);
    }

    public void i(String str) {
        i("", str);
    }

    public void i(String str, String str2) {
        Log.i(getCombinationTag(str), str2);
        logInfo(str, str2);
    }

    public void i(String str, Throwable th) {
        i("", str, th);
    }

    public void i(String str, String str2, Throwable th) {
        Log.i(getCombinationTag(str), str2, th);
        logInfo(str, str2);
    }

    public void w(String str) {
        w("", str);
    }

    public void w(String str, String str2) {
        Log.w(getCombinationTag(str), str2);
    }

    public void w(Throwable th) {
        w("", th);
    }

    public void w(String str, Throwable th) {
        Log.w(getCombinationTag(str), th);
    }

    public void e(String str) {
        e("", str);
    }

    public void e(String str, String str2) {
        Log.e(getCombinationTag(str), str2);
    }

    public void e(Throwable th) {
        e("", th);
    }

    public void e(String str, Throwable th) {
        e("", str, th);
    }

    public void e(String str, String str2, Throwable th) {
        Log.e(getCombinationTag(str), str2, th);
    }

    private String getCombinationTag(String str) {
        if (TextUtils.isEmpty(str)) {
            return this.tag;
        }
        return this.tag + "-" + str;
    }
}
