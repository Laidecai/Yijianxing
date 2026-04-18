package com.tds.common.account;

import com.tds.common.account.TdsAccount;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TDSPlatformAccount implements TdsAccount<JSONObject> {
    public String access_token;
    public long expire_in;
    public String kid;
    public String mac_algorithm;
    public String mac_key;
    private JSONObject token;
    private String tokenString;
    public String token_type;

    @Override // com.tds.common.account.TdsAccount
    public JSONObject getToken() {
        return null;
    }

    public TDSPlatformAccount(String str) {
        try {
            this.tokenString = str;
            JSONObject jSONObject = new JSONObject(str);
            this.token = jSONObject;
            this.kid = jSONObject.optString("kid");
            this.access_token = this.token.optString("access_token");
            this.mac_algorithm = this.token.optString("mac_algorithm");
            this.token_type = this.token.optString("token_type");
            this.mac_key = this.token.optString("mac_key");
            this.expire_in = Long.parseLong(this.token.optString("expire_in"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tds.common.account.TdsAccount
    public TdsAccount.AccountType getAccountType() {
        return TdsAccount.AccountType.TDS;
    }

    public String getTokenString() {
        return this.tokenString;
    }
}
