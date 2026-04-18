package com.tds.common.account;

import com.tds.common.account.TdsAccount;

/* JADX INFO: loaded from: classes.dex */
public class XdgAccount implements TdsAccount<String> {
    private final String token;

    public XdgAccount(String str) {
        this.token = str;
    }

    @Override // com.tds.common.account.TdsAccount
    public TdsAccount.AccountType getAccountType() {
        return TdsAccount.AccountType.XDG;
    }

    @Override // com.tds.common.account.TdsAccount
    public String getToken() {
        return this.token;
    }
}
