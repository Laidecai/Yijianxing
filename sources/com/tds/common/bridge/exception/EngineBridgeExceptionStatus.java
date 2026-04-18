package com.tds.common.bridge.exception;

/* JADX INFO: loaded from: classes.dex */
public enum EngineBridgeExceptionStatus {
    COMMAND_PARSE_ERROR("json解析错误，检查Command是否符合规则"),
    COMMAND_SERVICE_ERROR("Service异常，检查Service是否注册或者符合规则"),
    COMMAND_METHOD_ERROR("Method异常，检查Method命名是否正确"),
    COMMAND_ARGS_ERROR("参数匹配错误或者执行方法错误，检查参数以及桥接方法执行是否正确");

    String message;

    EngineBridgeExceptionStatus(String str) {
        this.message = str;
    }

    public String getMessage() {
        return this.message;
    }

    public String getExpandMessage(String str) {
        return String.format(this.message + ",expandMessage:%s", str);
    }
}
