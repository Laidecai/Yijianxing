package com.tds.common.account;

import com.tds.common.account.TdsAccount;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapAccount implements TdsAccount<JSONObject> {
    private JSONObject accessToken;
    public String access_token;
    public String kid;
    public String mac_algorithm;
    public String mac_key;
    private String tokenString;
    public String token_type;

    public TapAccount(String str) {
        try {
            this.tokenString = str;
            JSONObject jSONObject = new JSONObject(str);
            this.accessToken = jSONObject;
            this.kid = jSONObject.optString("kid");
            this.access_token = this.accessToken.optString("access_token");
            this.token_type = this.accessToken.optString("token_type");
            this.mac_key = this.accessToken.optString("mac_key");
            this.mac_algorithm = this.accessToken.optString("mac_algorithm");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tds.common.account.TdsAccount
    public TdsAccount.AccountType getAccountType() {
        return TdsAccount.AccountType.TAP;
    }

    @Override // com.tds.common.account.TdsAccount
    public JSONObject getToken() {
        return this.accessToken;
    }

    public String getTokenString() {
        return this.tokenString;
    }
}
