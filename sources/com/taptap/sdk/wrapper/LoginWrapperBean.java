package com.taptap.sdk.wrapper;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class LoginWrapperBean<T> implements Serializable {
    private int loginCallbackCode;
    private T wrapper;

    public LoginWrapperBean(int i) {
        this.loginCallbackCode = i;
    }

    public LoginWrapperBean(T t, int i) {
        this.wrapper = t;
        this.loginCallbackCode = i;
    }

    public T getWrapper() {
        return this.wrapper;
    }

    public void setWrapper(T t) {
        this.wrapper = t;
    }

    public int getLoginCallbackCode() {
        return this.loginCallbackCode;
    }

    public void setLoginCallbackCode(int i) {
        this.loginCallbackCode = i;
    }
}
