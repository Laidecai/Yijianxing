package com.tds.common.account;

import com.tds.common.account.TdsAccount;

/* JADX INFO: loaded from: classes.dex */
public class XdAccount implements TdsAccount<String> {
    private final String token;

    public XdAccount(String str) {
        this.token = str;
    }

    @Override // com.tds.common.account.TdsAccount
    public TdsAccount.AccountType getAccountType() {
        return TdsAccount.AccountType.XD;
    }

    @Override // com.tds.common.account.TdsAccount
    public String getToken() {
        return this.token;
    }
}
