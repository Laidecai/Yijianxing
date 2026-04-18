package com.alipay.sdk.app;

/* JADX INFO: loaded from: classes.dex */
public class EnvUtils {
    public static EnvEnum mEnv = EnvEnum.ONLINE;

    public enum EnvEnum {
        ONLINE,
        SANDBOX
    }

    public static EnvEnum geEnv() {
        return mEnv;
    }

    public static boolean isSandBox() {
        return mEnv == EnvEnum.SANDBOX;
    }

    public static void setEnv(EnvEnum envEnum) {
        mEnv = envEnum;
    }
}
