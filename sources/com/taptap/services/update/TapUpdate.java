package com.taptap.services.update;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tds.common.TapCommon;
import com.tds.common.entities.TapConfig;
import com.tds.common.utils.UIUtils;
import com.tds.common.widgets.TdsActivityManager;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdate {
    private static boolean hasInit = false;

    public static void init(Context context, String str, String str2) {
        if (hasInit) {
            TapUpdateLogger.e("duplicate init , just return");
            return;
        }
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        if (TapCommon.getTapConfig() == null) {
            TapCommon.init(new TapConfig.Builder().withClientId(str).withClientToken(str2).withAppContext(context.getApplicationContext()).build());
        }
        TapUpdateTracker.getInstance().initTracker(context, str, str2);
        TapUpdateDialogManager.getInstance().init(context);
        hasInit = true;
    }

    public static void updateGame(Activity activity, TapUpdateCallback tapUpdateCallback) {
        if (!hasInit) {
            TapUpdateLogger.d("updateGame: not init");
            throw new RuntimeException("not initialized error : please call init first");
        }
        if (UIUtils.isFastClick()) {
            TapUpdateLogger.d("updateGame: fast click");
        } else {
            TdsActivityManager.getInstance().setRootActivity(activity);
            TapUpdateDialogManager.getInstance().showDialog(activity, tapUpdateCallback);
        }
    }
}
