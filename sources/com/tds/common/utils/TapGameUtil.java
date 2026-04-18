package com.tds.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.tds.common.net.util.HttpUtil;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TapGameUtil {
    public static final String CLIENT_URI_TAPTAP = "taptap://taptap.com";
    public static final String CLIENT_URI_TAPTAP_GLOBAL = "tapglobal://taptap.tw";
    public static final String DEFAULT_CLIENT_DOWNLOAD_URL_TAPTAP = "https://l.taptap.com/5d1NGyET";
    public static final String DEFAULT_CLIENT_DOWNLOAD_URL_TAPTAP_GLOBAL = "https://l.taptap.io/GNYwFaZr";
    public static final String PACKAGE_NAME_TAPTAP = "com.taptap";
    public static final String PACKAGE_NAME_TAPTAP_GLOBAL = "com.taptap.global";
    private static final String TAG = "com.tds.common.utils.TapGameUtil";

    public static boolean updateGameAndFailToWebInTapTap(Activity activity, String str) {
        return updateGameInTapTap(activity, str) || openWebDownloadUrlOfTapTap(activity, str);
    }

    public static boolean updateGameAndFailToWebInTapGlobal(Activity activity, String str) {
        return updateGameInTapGlobal(activity, str) || openWebDownloadUrlOfTapGlobal(activity, str);
    }

    public static boolean updateGameAndFailToWebInTapTap(Activity activity, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return updateGameAndFailToWebInTapTap(activity, str);
        }
        return updateGameInTapTap(activity, str) || openWebDownloadUrl(activity, str2);
    }

    public static boolean updateGameAndFailToWebInTapGlobal(Activity activity, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return updateGameAndFailToWebInTapGlobal(activity, str);
        }
        return updateGameInTapGlobal(activity, str) || openWebDownloadUrl(activity, str2);
    }

    public static boolean isTapTapInstalled(Context context) {
        return isTapClientInstalled(context, PACKAGE_NAME_TAPTAP);
    }

    public static boolean isTapGlobalInstalled(Context context) {
        return isTapClientInstalled(context, PACKAGE_NAME_TAPTAP_GLOBAL);
    }

    public static boolean isTapClientInstalled(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (Exception unused) {
            Log.e(TAG, str + " isInstalled=false");
            return false;
        }
    }

    public static boolean updateGameInTapTap(Activity activity, String str) {
        return updateGameInTapClient(activity, str, CLIENT_URI_TAPTAP);
    }

    public static boolean updateGameInTapGlobal(Activity activity, String str) {
        return updateGameInTapClient(activity, str, CLIENT_URI_TAPTAP_GLOBAL);
    }

    public static boolean updateGameInTapClient(Activity activity, String str, String str2) {
        if (activity != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(String.format(Locale.US, "%s/app?app_id=%s&source=outer|update", str2, str)));
                intent.setFlags(268435456);
                activity.startActivity(intent);
                return true;
            } catch (Exception unused) {
                Log.e(TAG, str2 + "updateGameInTapTap failed");
                return false;
            }
        }
        Log.e(TAG, str2 + "updateGameInTapTap failed");
        return false;
    }

    public static boolean openReviewInTapTap(Activity activity, String str) {
        return openReviewInTapClient(activity, str, CLIENT_URI_TAPTAP, null);
    }

    public static boolean openReviewInTapGlobal(Activity activity, String str) {
        return openReviewInTapClient(activity, str, CLIENT_URI_TAPTAP_GLOBAL, null);
    }

    public static boolean openReviewInTapTap(Activity activity, String str, Map<String, String> map) {
        return openReviewInTapClient(activity, str, CLIENT_URI_TAPTAP, map);
    }

    public static boolean openReviewInTapGlobal(Activity activity, String str, Map<String, String> map) {
        return openReviewInTapClient(activity, str, CLIENT_URI_TAPTAP_GLOBAL, map);
    }

    public static boolean openReviewInTapClient(Activity activity, String str, String str2, Map<String, String> map) {
        if (activity != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                String strBuildUrl = String.format(Locale.US, "%s/app?tab_name=review&app_id=%s", str2, str);
                if (map != null && !map.isEmpty()) {
                    strBuildUrl = HttpUtil.buildUrl(strBuildUrl, map);
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(strBuildUrl));
                intent.setFlags(268435456);
                activity.startActivity(intent);
                return true;
            } catch (Exception unused) {
                Log.e(TAG, str2 + "openTapTapReview failed");
                return false;
            }
        }
        Log.e(TAG, str2 + "openTapTapReview failed");
        return false;
    }

    public static boolean openWebDownloadUrlOfTapTap(Activity activity, String str) {
        return openWebDownloadUrl(activity, String.format(Locale.US, "https://l.taptap.com/5d1NGyET?subc1=%s", str));
    }

    public static boolean openWebDownloadUrlOfTapGlobal(Activity activity, String str) {
        return openWebDownloadUrl(activity, String.format(Locale.US, "https://l.taptap.io/GNYwFaZr?subc1=%s", str));
    }

    public static boolean openWebDownloadUrlOfTapTap(Activity activity, String str, Map<String, String> map) {
        String strBuildUrl = String.format(Locale.US, "https://l.taptap.com/5d1NGyET?subc1=%s", str);
        if (map != null && !map.isEmpty()) {
            strBuildUrl = HttpUtil.buildUrl(strBuildUrl, map);
        }
        return openWebDownloadUrl(activity, strBuildUrl);
    }

    public static boolean openWebDownloadUrlOfTapGlobal(Activity activity, String str, Map<String, String> map) {
        String strBuildUrl = String.format(Locale.US, "https://l.taptap.io/GNYwFaZr?subc1=%s", str);
        if (map != null && !map.isEmpty()) {
            strBuildUrl = HttpUtil.buildUrl(strBuildUrl, map);
        }
        return openWebDownloadUrl(activity, strBuildUrl);
    }

    public static boolean openWebDownloadUrl(Activity activity, String str) {
        if (activity != null && !TextUtils.isEmpty(str)) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setFlags(268435456);
                intent.setData(Uri.parse(str));
                activity.startActivity(intent);
                return true;
            } catch (Exception unused) {
                Log.e(TAG, "openWebUrl fail");
            }
        }
        return false;
    }
}
