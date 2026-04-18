package com.alipay.sdk.app;

/* JADX INFO: loaded from: classes.dex */
public enum c {
    SUCCEEDED(OpenAuthTask.OK, "处理成功"),
    FAILED(OpenAuthTask.SYS_ERR, "系统繁忙，请稍后再试"),
    CANCELED(6001, "用户取消"),
    NETWORK_ERROR(6002, "网络连接异常"),
    PARAMS_ERROR(OpenAuthTask.NOT_INSTALLED, "参数错误"),
    DOUBLE_REQUEST(5000, "重复请求"),
    PAY_WAITTING(8000, "支付结果确认中");

    public int a;
    public String b;

    c(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public void a(int i) {
        this.a = i;
    }

    public int b() {
        return this.a;
    }

    public static c b(int i) {
        return i != 4001 ? i != 5000 ? i != 8000 ? i != 9000 ? i != 6001 ? i != 6002 ? FAILED : NETWORK_ERROR : CANCELED : SUCCEEDED : PAY_WAITTING : DOUBLE_REQUEST : PARAMS_ERROR;
    }

    public void a(String str) {
        this.b = str;
    }

    public String a() {
        return this.b;
    }
}
