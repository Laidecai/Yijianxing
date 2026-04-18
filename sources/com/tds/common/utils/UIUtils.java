package com.tds.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.common.localize.LocalizeManager;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class UIUtils {
    private static Map<String, Long> records = new HashMap();

    public static int dp2pxWithScale(float f, float f2) {
        return (int) (f2 * f);
    }

    public static boolean isFastClick() {
        if (records.size() > 1000) {
            records.clear();
        }
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String str = stackTraceElement.getFileName() + stackTraceElement.getLineNumber();
        Long l = records.get(str);
        long jCurrentTimeMillis = System.currentTimeMillis();
        records.put(str, Long.valueOf(jCurrentTimeMillis));
        if (l == null) {
            l = 0L;
        }
        long jLongValue = jCurrentTimeMillis - l.longValue();
        return 0 < jLongValue && jLongValue < 500;
    }

    public static String getLocalizedString(Context context, int i) {
        String string = getPreferredLocalizedResources(context).getString(i);
        return TextUtils.isEmpty(string) ? getLocalizedResourcesByDomestic(context).getString(i) : string;
    }

    public static Resources getPreferredLocalizedResources(Context context) {
        return getLocalizedResources(context, LocalizeManager.getLocale(LocalizeManager.getPreferredLang()));
    }

    public static Resources getLocalizedResourcesByDomestic(Context context) {
        return getLocalizedResources(context, LocalizeManager.getDefaultLocaleByDomestic());
    }

    public static Resources getLocalizedResources(Context context, Locale locale) {
        Resources resources = context.getResources();
        AssetManager assets = resources.getAssets();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.locale = locale;
        return new Resources(assets, displayMetrics, configuration);
    }

    public static int getLayoutId(Context context, String str) {
        return context.getResources().getIdentifier(str, "layout", context.getPackageName());
    }

    public static int getViewId(Context context, String str) {
        return context.getResources().getIdentifier(str, BreakpointSQLiteKey.ID, context.getPackageName());
    }

    public static int getDrawableId(Context context, String str) {
        return context.getResources().getIdentifier(str, "drawable", context.getPackageName());
    }

    public static int getStringId(Context context, String str) {
        int identifier = getPreferredLocalizedResources(context).getIdentifier(str, "string", context.getPackageName());
        return identifier == 0 ? getLocalizedResourcesByDomestic(context).getIdentifier(str, "string", context.getPackageName()) : identifier;
    }

    public static int getId(Context context, String str) {
        return context.getResources().getIdentifier(str, BreakpointSQLiteKey.ID, context.getPackageName());
    }

    public static String getString(Context context, String str) {
        try {
            return context.getString(getStringId(context, str));
        } catch (Exception unused) {
            return "";
        }
    }

    public static int dp2px(Context context, float f) {
        return (int) (f * context.getResources().getDisplayMetrics().density);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getWindowShortLength(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static int getWindowLongLength(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static int getWindowWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getWindowHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static Drawable getPackageIconDrawable(Activity activity) {
        PackageManager packageManager;
        if (activity != null && (packageManager = activity.getPackageManager()) != null) {
            try {
                return packageManager.getApplicationInfo(activity.getPackageName(), 0).loadIcon(packageManager);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getAppName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return String.valueOf(context.getPackageManager().getApplicationLabel(context.getApplicationInfo()));
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static Point getRealScreenSize(Context context) {
        if (context == null) {
            return new Point(0, 0);
        }
        try {
            Point point = new Point();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
            return point;
        } catch (Exception e) {
            e.printStackTrace();
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                return new Point(getWindowWidth(activity), getWindowHeight(activity));
            }
            return new Point(0, 0);
        }
    }

    public static String getScreenSizeInfo(Context context) {
        if (context == null) {
            return "";
        }
        try {
            Point point = new Point();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
            return point.x + "*" + point.y;
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }
}
