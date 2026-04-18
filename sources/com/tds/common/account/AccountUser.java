package com.tds.common.account;

import android.text.TextUtils;
import com.tds.common.account.TdsAccount;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class AccountUser {
    private static final String AUTHDATA_PLATFORM_TAP = "taptap";
    private static final String OAUTH_ID_KEY = "id";
    private static final String TAPTAP_OAUTH_OPEN_ID = "openid";
    String accountType;
    List<AccountUser> boundAccountUsers;
    String userId;

    public AccountUser(TdsAccount.AccountType accountType, String str) {
        if (accountType == null) {
            return;
        }
        this.accountType = accountType.name().toLowerCase();
        this.userId = str;
    }

    public AccountUser(String str, String str2) {
        this.accountType = str;
        this.userId = str2;
    }

    public boolean bindAccountUser(AccountUser accountUser) {
        if (accountUser == null || !isValid() || !accountUser.isValid()) {
            return false;
        }
        if (this.boundAccountUsers == null) {
            this.boundAccountUsers = new ArrayList();
        }
        if (Objects.equals(this.accountType, accountUser.accountType)) {
            return false;
        }
        AccountUser accountUser2 = null;
        Iterator<AccountUser> it = this.boundAccountUsers.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AccountUser next = it.next();
            if (next != null && next.isValid() && accountUser.accountType.equals(next.accountType)) {
                accountUser2 = next;
                break;
            }
        }
        if (accountUser2 != null) {
            this.boundAccountUsers.remove(accountUser2);
        }
        this.boundAccountUsers.add(accountUser);
        return true;
    }

    public void unbindAccountUser(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (str.equals("taptap")) {
            str = TdsAccount.AccountType.TAP.name().toLowerCase();
        }
        List<AccountUser> list = this.boundAccountUsers;
        if (list == null || list.size() <= 0) {
            return;
        }
        AccountUser accountUser = null;
        Iterator<AccountUser> it = this.boundAccountUsers.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AccountUser next = it.next();
            if (next != null && next.isValid() && next.getAccountTypeString().equals(str)) {
                accountUser = next;
                break;
            }
        }
        if (accountUser != null) {
            this.boundAccountUsers.remove(accountUser);
        }
    }

    public boolean bindAccountUser(String str, Map<String, Object> map) {
        return bindAccountUser(getAuthAccountUser(str, map));
    }

    public AccountUser getBindUser(TdsAccount.AccountType accountType) {
        List<AccountUser> list = this.boundAccountUsers;
        if (list == null || list.size() == 0) {
            return null;
        }
        for (AccountUser accountUser : this.boundAccountUsers) {
            if (accountUser != null && accountUser.accountType.equals(accountType.name().toLowerCase())) {
                return accountUser;
            }
        }
        return null;
    }

    public AccountUser(TdsAccount.AccountType accountType, String str, String str2, Map<String, Object> map) {
        this(accountType, str);
        AccountUser authAccountUser = getAuthAccountUser(str2, map);
        if (authAccountUser != null) {
            bindAccountUser(authAccountUser);
        }
    }

    public AccountUser(TdsAccount.AccountType accountType, String str, Map<String, Object> map) {
        AccountUser authAccountUser;
        this(accountType, str);
        if (map == null || map.isEmpty()) {
            return;
        }
        try {
            for (String str2 : map.keySet()) {
                Object obj = map.get(str2);
                if ((obj instanceof Map) && (authAccountUser = getAuthAccountUser(str2, (Map) obj)) != null) {
                    bindAccountUser(authAccountUser);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean isValid() {
        return (this.accountType == null || TextUtils.isEmpty(this.userId)) ? false : true;
    }

    public static AccountUser getAuthAccountUser(String str, Map<String, Object> map) {
        String next;
        String string;
        if (!TextUtils.isEmpty(str) && map != null && !map.isEmpty()) {
            if (str.equals("taptap")) {
                Object obj = map.get(TAPTAP_OAUTH_OPEN_ID);
                string = obj != null ? obj.toString() : null;
                str = TdsAccount.AccountType.TAP.name().toLowerCase();
            } else {
                Iterator<String> it = map.keySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (next.toLowerCase().contains("id")) {
                        break;
                    }
                }
                Object obj2 = map.get(next);
                string = obj2 != null ? obj2.toString() : null;
            }
            AccountUser accountUser = new AccountUser(str, string);
            if (accountUser.isValid()) {
                return accountUser;
            }
        }
        return null;
    }

    public String getAccountTypeString() {
        return this.accountType;
    }

    public String getUserId() {
        return this.userId;
    }

    public List<AccountUser> getBoundAccountUsers() {
        return this.boundAccountUsers;
    }

    public TdsAccount.AccountType getAccountType() {
        String str = this.accountType;
        if (str == null) {
            return null;
        }
        if (str.equals(TdsAccount.AccountType.TAP.name().toLowerCase())) {
            return TdsAccount.AccountType.TAP;
        }
        if (this.accountType.equals(TdsAccount.AccountType.TDS.name().toLowerCase())) {
            return TdsAccount.AccountType.TDS;
        }
        return null;
    }

    public boolean equals(AccountUser accountUser) {
        if (accountUser == null) {
            return false;
        }
        try {
            if (Objects.equals(this.accountType, accountUser.accountType) && Objects.equals(this.userId, accountUser.userId)) {
                List<AccountUser> list = this.boundAccountUsers;
                List<AccountUser> list2 = accountUser.boundAccountUsers;
                if (list == list2) {
                    return true;
                }
                if (list == null || list2 == null) {
                    return false;
                }
                for (AccountUser accountUser2 : list) {
                    AccountUser accountUser3 = null;
                    for (AccountUser accountUser4 : accountUser.boundAccountUsers) {
                        if (Objects.equals(accountUser2.accountType, accountUser4.accountType) && Objects.equals(accountUser2.userId, accountUser4.userId)) {
                            accountUser3 = accountUser4;
                        }
                    }
                    if (accountUser3 == null) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String toString() {
        return "AccountUser{accountType='" + this.accountType + "', userId='" + this.userId + "', bindAccountUsers=" + this.boundAccountUsers + '}';
    }
}
