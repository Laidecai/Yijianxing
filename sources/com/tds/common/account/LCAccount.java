package com.tds.common.account;

import com.tds.common.account.TdsAccount;

/* JADX INFO: loaded from: classes.dex */
public class LCAccount implements TdsAccount<String> {
    public String clientId;
    public String clientToken;
    public String sessionToken;

    public LCAccount(String str, String str2, String str3) {
        this.clientId = str;
        this.clientToken = str2;
        this.sessionToken = str3;
    }

    @Override // com.tds.common.account.TdsAccount
    public TdsAccount.AccountType getAccountType() {
        return TdsAccount.AccountType.LC;
    }

    @Override // com.tds.common.account.TdsAccount
    public String getToken() {
        return this.sessionToken;
    }
}
