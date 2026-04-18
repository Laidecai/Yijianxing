package com.tds.plugin.click;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class SingleClickUtil {
    private static final Map<View, Long> viewWeakHashMap = new WeakHashMap();
    private static final Map<DialogInterface, Long> dialogWeakHashMap = new WeakHashMap();
    private static long FROZEN_TIME_MILLIS = 500;

    private SingleClickUtil() {
        throw new UnsupportedOperationException("Do not need instantiate!");
    }

    public static boolean isFastDoubleClick(View view) {
        Map<View, Long> map = viewWeakHashMap;
        Long l = map.get(view);
        long jNow = now();
        if (l == null) {
            map.put(view, Long.valueOf(jNow + FROZEN_TIME_MILLIS));
            return false;
        }
        if (jNow >= l.longValue()) {
            map.put(view, Long.valueOf(jNow + FROZEN_TIME_MILLIS));
            return false;
        }
        Log.d("SingleClick", "find double click button :" + (view instanceof TextView ? ((TextView) view).getText().toString() : ""));
        return true;
    }

    public static boolean isFastDoubleClick(DialogInterface dialogInterface) {
        Map<DialogInterface, Long> map = dialogWeakHashMap;
        Long l = map.get(dialogInterface);
        long jNow = now();
        if (l == null) {
            map.put(dialogInterface, Long.valueOf(jNow + FROZEN_TIME_MILLIS));
            return false;
        }
        if (jNow >= l.longValue()) {
            map.put(dialogInterface, Long.valueOf(jNow + FROZEN_TIME_MILLIS));
            return false;
        }
        Log.d("SingleClick", "find dialog double click   :" + dialogInterface.getClass().getSimpleName());
        return true;
    }

    private static long now() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    }

    public static void setFrozenTimeMillis(long j) {
        FROZEN_TIME_MILLIS = j;
    }
}
