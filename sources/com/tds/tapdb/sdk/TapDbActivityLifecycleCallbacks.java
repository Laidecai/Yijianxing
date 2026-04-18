package com.tds.tapdb.sdk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.tds.common.tracker.constants.CommonParam;
import com.tds.tapdb.b.m;
import com.tds.tapdb.b.n;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TapDbActivityLifecycleCallbacks extends m {
    private static final int h = 150;
    private static final int i = 200;
    private static final int j = 300;
    private static final int k = 3000;
    private final com.tds.tapdb.sdk.a a;
    private Handler b;
    private long d;
    private final String c = CommonParam.TIME;
    private final List<WeakReference<Activity>> e = new ArrayList();
    private final Object f = new Object();
    private volatile boolean g = true;

    class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == TapDbActivityLifecycleCallbacks.h) {
                TapDbActivityLifecycleCallbacks.this.a(message);
                return;
            }
            if (i == 200) {
                TapDbActivityLifecycleCallbacks.this.b(message);
                return;
            }
            if (i != TapDbActivityLifecycleCallbacks.j) {
                return;
            }
            String strA = TapDbActivityLifecycleCallbacks.this.a();
            if (!TextUtils.isEmpty(strA)) {
                TapDbActivityLifecycleCallbacks.this.a.a(strA);
                TapDbActivityLifecycleCallbacks.this.a.a(System.currentTimeMillis());
            }
            TapDbActivityLifecycleCallbacks.this.b.sendEmptyMessageDelayed(TapDbActivityLifecycleCallbacks.j, PayTask.j);
        }
    }

    public TapDbActivityLifecycleCallbacks(Context context) {
        this.a = com.tds.tapdb.sdk.a.a(context);
        c();
        this.d = System.currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a() {
        return TapDB.h();
    }

    private void a(int i2) {
        Message messageObtainMessage = this.b.obtainMessage();
        messageObtainMessage.what = i2;
        Bundle bundle = new Bundle();
        bundle.putLong(CommonParam.TIME, System.currentTimeMillis());
        messageObtainMessage.setData(bundle);
        this.b.sendMessage(messageObtainMessage);
    }

    private void a(Activity activity) {
        if (activity == null) {
            return;
        }
        synchronized (this.f) {
            if (this.e.size() == 0) {
                if (this.g) {
                    b();
                }
                a(h);
                this.b.sendEmptyMessage(j);
            }
            if (!a(activity, false)) {
                this.e.add(new WeakReference<>(activity));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        long j2 = message.getData().getLong(CommonParam.TIME);
        this.d = j2;
        this.a.b(j2);
    }

    private boolean a(Activity activity, boolean z) {
        synchronized (this.f) {
            Iterator<WeakReference<Activity>> it = this.e.iterator();
            while (it.hasNext()) {
                if (it.next().get() == activity) {
                    if (z) {
                        it.remove();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    private void b() {
        if (this.g) {
            long jB = this.a.b();
            long jA = this.a.a();
            long j2 = jA - jB;
            String strC = this.a.c();
            n.a("history start = " + jB + "  end = " + jA);
            if (jB > 0 && jA > 0 && j2 > 0 && !TextUtils.isEmpty(strC)) {
                TapDB.a(j2 / 1000, strC);
            }
            this.g = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Message message) {
        Bundle data = message.getData();
        if (data != null) {
            long j2 = data.getLong(CommonParam.TIME);
            long j3 = j2 - this.d;
            String strA = a();
            if (this.d <= 0 || j2 <= 0 || j3 <= 0 || TextUtils.isEmpty(strA)) {
                return;
            }
            TapDB.a(j3 / 1000, strA);
        }
    }

    private void c() {
        HandlerThread handlerThread = new HandlerThread("TAP_DB_DATA_THREAD");
        handlerThread.start();
        this.b = new a(handlerThread.getLooper());
    }

    @Override // com.tds.tapdb.b.m, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        a(activity);
        n.a("onActivityPaused ");
    }

    @Override // com.tds.tapdb.b.m, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        n.a("onActivityResumed ");
        a(activity);
    }

    @Override // com.tds.tapdb.b.m, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        a(activity);
        n.a("onActivityStarted ");
    }

    @Override // com.tds.tapdb.b.m, android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        n.a("onActivityStopped ");
        if (a(activity, true) && this.e.size() == 0) {
            this.b.removeMessages(j);
            a(200);
            this.a.e();
            this.a.f();
        }
    }
}
