package com.taptap.services.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tds.common.utils.TapGameUtil;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdatePackageInstallReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction() == null || !intent.getAction().equals("android.intent.action.PACKAGE_ADDED") || intent.getData() == null) {
                    return;
                }
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                TapUpdateLogger.d("PackageInstallReceiver addPackage " + schemeSpecificPart);
                if (TextUtils.equals(schemeSpecificPart, TapGameUtil.PACKAGE_NAME_TAPTAP) && TapGameUtil.isTapTapInstalled(context)) {
                    TapUpdateDialogManager.getInstance().installSuccess(context);
                }
            } catch (Throwable unused) {
            }
        }
    }
}
