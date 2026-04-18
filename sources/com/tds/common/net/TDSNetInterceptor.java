package com.tds.common.net;

import android.text.TextUtils;
import com.taptap.sdk.AccountGlobalError;
import com.tds.common.net.error.ErrorHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TDSNetInterceptor {
    private HashMap<String, ErrorHandler> errorHandlerList;

    public interface CheckAuthCallback {
        void onAuthError(String str);
    }

    private static class Holder {
        private static final TDSNetInterceptor INSTANCE = new TDSNetInterceptor();

        private Holder() {
        }
    }

    private TDSNetInterceptor() {
    }

    public static TDSNetInterceptor getInstance() {
        return Holder.INSTANCE;
    }

    public static void registerNetInterceptor(String str, ErrorHandler errorHandler) {
        if (TextUtils.isEmpty(str) || errorHandler == null) {
            return;
        }
        getErrorHandlerList().put(str, errorHandler);
    }

    public static void unRegisterNetInterceptor(String str) {
        getErrorHandlerList().remove(str);
    }

    public static void interceptWithContent(int i, String str, String str2) {
        if (getErrorHandlerList().size() > 0) {
            Iterator<ErrorHandler> it = getErrorHandlerList().values().iterator();
            while (it.hasNext()) {
                it.next().invoke(i, str, str2);
            }
        }
    }

    private static HashMap<String, ErrorHandler> getErrorHandlerList() {
        if (getInstance().errorHandlerList == null) {
            getInstance().errorHandlerList = new HashMap<>();
        }
        return getInstance().errorHandlerList;
    }

    public static void checkAuthErrorAccessDenied(String str, CheckAuthCallback checkAuthCallback) {
        checkAuthError(str, checkAuthCallback, AccountGlobalError.LOGIN_ERROR_ACCESS_DENIED);
    }

    public static void checkAuthError(String str, CheckAuthCallback checkAuthCallback, String... strArr) {
        JSONObject jSONObjectOptJSONObject;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optBoolean("success", false) || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                return;
            }
            String string = jSONObjectOptJSONObject.getString("error");
            for (String str2 : strArr) {
                if (TextUtils.equals(str2.toLowerCase(Locale.US), string.toLowerCase(Locale.US))) {
                    if (checkAuthCallback != null) {
                        checkAuthCallback.onAuthError(jSONObjectOptJSONObject.toString());
                        return;
                    }
                    return;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
