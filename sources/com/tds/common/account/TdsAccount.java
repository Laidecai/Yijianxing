package com.tds.common.account;

/* JADX INFO: loaded from: classes.dex */
public interface TdsAccount<T> {

    public enum AccountType {
        XD,
        XDG,
        TAP,
        TDS,
        LC
    }

    AccountType getAccountType();

    T getToken();
}
