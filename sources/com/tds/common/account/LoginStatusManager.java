package com.tds.common.account;

import android.text.TextUtils;
import android.util.Log;
import com.tds.common.account.TdsAccount;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LoginStatusManager {
    private static final String TAG = "LoginStatusManager";
    private static AccountUser accountUser;
    private static List<WeakReference<LoginStatusListener>> currentUserLoginStatusListenerList;
    private static List<WeakReference<LoginStatusListener>> tapLoginStatusListenerList;
    private static List<WeakReference<LoginStatusListener>> tdsLoginStatusListenerList;

    public static void registerLoginStatusListener(LoginStatusListener loginStatusListener) {
        if (loginStatusListener == null) {
            return;
        }
        log("register not definite listener");
        WeakReference<LoginStatusListener> weakReference = new WeakReference<>(loginStatusListener);
        if (currentUserLoginStatusListenerList == null) {
            currentUserLoginStatusListenerList = new ArrayList();
        }
        currentUserLoginStatusListenerList.add(weakReference);
    }

    public static void registerLoginStatusListener(TdsAccount.AccountType accountType, LoginStatusListener loginStatusListener) {
        if (loginStatusListener == null) {
            return;
        }
        log(" type = " + accountType.name());
        WeakReference<LoginStatusListener> weakReference = new WeakReference<>(loginStatusListener);
        if (accountType == TdsAccount.AccountType.TAP) {
            if (tapLoginStatusListenerList == null) {
                tapLoginStatusListenerList = new ArrayList();
            }
            tapLoginStatusListenerList.add(weakReference);
        } else {
            if (tdsLoginStatusListenerList == null) {
                tdsLoginStatusListenerList = new ArrayList();
            }
            tdsLoginStatusListenerList.add(weakReference);
        }
    }

    public static void unregisterLoginStatusListener(TdsAccount.AccountType accountType, LoginStatusListener loginStatusListener) {
        if (loginStatusListener == null) {
            return;
        }
        log(" listener  = " + loginStatusListener);
        WeakReference<LoginStatusListener> weakReference = null;
        List<WeakReference<LoginStatusListener>> list = tdsLoginStatusListenerList;
        if (accountType == TdsAccount.AccountType.TAP) {
            list = tapLoginStatusListenerList;
        }
        if (list == null || list.size() <= 0) {
            return;
        }
        for (WeakReference<LoginStatusListener> weakReference2 : list) {
            if (weakReference2 != null && weakReference2.get() != null && loginStatusListener == weakReference2.get()) {
                weakReference = weakReference2;
            }
        }
        if (weakReference != null) {
            list.remove(weakReference);
        }
    }

    public static void unregisterLoginStatusListener(LoginStatusListener loginStatusListener) {
        if (loginStatusListener == null) {
            return;
        }
        log(" listener  = " + loginStatusListener);
        WeakReference<LoginStatusListener> weakReference = null;
        List<WeakReference<LoginStatusListener>> list = currentUserLoginStatusListenerList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (WeakReference<LoginStatusListener> weakReference2 : currentUserLoginStatusListenerList) {
            if (weakReference2 != null && weakReference2.get() != null && loginStatusListener == weakReference2.get()) {
                weakReference = weakReference2;
            }
        }
        if (weakReference != null) {
            currentUserLoginStatusListenerList.remove(weakReference);
        }
    }

    public static void notifyLoginSuccess(TdsAccount.AccountType accountType, String str) {
        if (accountType == TdsAccount.AccountType.TAP) {
            notifyLoginSuccessInternal(new AccountUser(accountType, str), tapLoginStatusListenerList);
        } else {
            notifyLoginSuccessInternal(new AccountUser(accountType, str), tdsLoginStatusListenerList);
        }
        AccountUser accountUser2 = accountUser;
        if (accountUser2 != null && accountUser2.getAccountType() == TdsAccount.AccountType.TDS && accountType == TdsAccount.AccountType.TAP) {
            log("current user's type is tds but got a tap user so just return");
            return;
        }
        AccountUser accountUser3 = new AccountUser(accountType, str);
        AccountUser accountUser4 = accountUser;
        if (accountUser4 != null && accountUser4.equals(accountUser3)) {
            log("got a same user just return");
        } else {
            accountUser = accountUser3;
            notifyLoginSuccessInternal(accountUser3, currentUserLoginStatusListenerList);
        }
    }

    public static void notifyLogout(TdsAccount.AccountType accountType) {
        if (accountType == TdsAccount.AccountType.TAP) {
            notifyLogoutInternal(accountType, tapLoginStatusListenerList);
        } else {
            notifyLogoutInternal(accountType, tdsLoginStatusListenerList);
        }
        AccountUser accountUser2 = accountUser;
        if (accountUser2 != null && accountUser2.getAccountType() == accountType) {
            log("type = " + accountType.name().toLowerCase());
            accountUser = null;
            notifyLogoutInternal(accountType, currentUserLoginStatusListenerList);
        }
    }

    public static void notifyBindSuccess(String str, Map<String, Object> map) {
        AccountUser authAccountUser;
        if (accountUser == null || (authAccountUser = AccountUser.getAuthAccountUser(str, map)) == null || !accountUser.bindAccountUser(authAccountUser)) {
            return;
        }
        log("bind user platform = " + str + " extra = " + map);
        notifyBindInternal(authAccountUser, tdsLoginStatusListenerList);
        notifyBindInternal(authAccountUser, currentUserLoginStatusListenerList);
    }

    public static void notifyUnBindSuccess(String str) {
        if (TextUtils.isEmpty(str) || accountUser == null) {
            return;
        }
        log("unbind platform = " + str);
        accountUser.unbindAccountUser(str);
        notifyUnbindInternal(str, tdsLoginStatusListenerList);
        notifyUnbindInternal(str, currentUserLoginStatusListenerList);
    }

    public static void notifyLoginSuccess(AccountUser accountUser2) {
        if (accountUser2 == null) {
            return;
        }
        if (accountUser2.getAccountType() == TdsAccount.AccountType.TAP) {
            notifyLoginSuccessInternal(accountUser2, tapLoginStatusListenerList);
        } else {
            notifyLoginSuccessInternal(accountUser2, tdsLoginStatusListenerList);
        }
        AccountUser accountUser3 = accountUser;
        if (accountUser3 != null && accountUser3.getAccountType() == TdsAccount.AccountType.TDS && accountUser2.getAccountType() == TdsAccount.AccountType.TAP) {
            log("current user's type is tds but got a tap user so just return");
            return;
        }
        AccountUser accountUser4 = accountUser;
        if (accountUser4 != null && accountUser4.equals(accountUser2)) {
            log("got a same user just return");
        } else {
            accountUser = accountUser2;
            notifyLoginSuccessInternal(accountUser2, currentUserLoginStatusListenerList);
        }
    }

    private static void notifyLoginSuccessInternal(AccountUser accountUser2, List<WeakReference<LoginStatusListener>> list) {
        if (list != null) {
            for (WeakReference<LoginStatusListener> weakReference : list) {
                if (weakReference != null && weakReference.get() != null) {
                    weakReference.get().onLoginSuccess(accountUser2);
                }
            }
        }
    }

    private static void notifyLogoutInternal(TdsAccount.AccountType accountType, List<WeakReference<LoginStatusListener>> list) {
        if (list != null) {
            for (WeakReference<LoginStatusListener> weakReference : list) {
                if (weakReference != null && weakReference.get() != null) {
                    weakReference.get().onLogout(accountType);
                }
            }
        }
    }

    private static void notifyBindInternal(AccountUser accountUser2, List<WeakReference<LoginStatusListener>> list) {
        if (list != null) {
            for (WeakReference<LoginStatusListener> weakReference : list) {
                if (weakReference != null && weakReference.get() != null) {
                    weakReference.get().onBindAccount(accountUser2);
                }
            }
        }
    }

    private static void notifyUnbindInternal(String str, List<WeakReference<LoginStatusListener>> list) {
        if (list != null) {
            for (WeakReference<LoginStatusListener> weakReference : list) {
                if (weakReference != null && weakReference.get() != null) {
                    weakReference.get().onUnBindAccount(str);
                }
            }
        }
    }

    public static AccountUser getAccountUser() {
        return accountUser;
    }

    private static void log(String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Log.i(TAG, "method " + (stackTrace.length > 3 ? stackTrace[3].getMethodName() : "") + " : " + str);
    }
}
