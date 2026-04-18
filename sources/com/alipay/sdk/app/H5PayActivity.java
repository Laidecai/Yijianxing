package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.alipay.sdk.packet.e;
import com.alipay.sdk.sys.a;
import com.alipay.sdk.util.l;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.common.tracker.constants.CommonParam;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class H5PayActivity extends Activity {
    public com.alipay.sdk.widget.c a;
    public String b;
    public String c;
    public String d;
    public String e;
    public boolean f;
    public String g;
    public WeakReference<com.alipay.sdk.sys.a> h;

    private void b() {
        try {
            super.requestWindowFeature(1);
            getWindow().addFlags(8192);
        } catch (Throwable th) {
            com.alipay.sdk.util.c.a(th);
        }
    }

    public void a() {
        Object obj = PayTask.h;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        a();
        super.finish();
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1010) {
            d.a((com.alipay.sdk.sys.a) l.a(this.h), i, i2, intent);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        com.alipay.sdk.widget.c cVar = this.a;
        if (cVar == null) {
            finish();
            return;
        }
        if (cVar.a()) {
            cVar.b();
            return;
        }
        if (!cVar.b()) {
            super.onBackPressed();
        }
        b.a(b.a());
        finish();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        b();
        super.onCreate(bundle);
        try {
            com.alipay.sdk.sys.a aVarA = a.C0007a.a(getIntent());
            if (aVarA == null) {
                finish();
                return;
            }
            this.h = new WeakReference<>(aVarA);
            if (com.alipay.sdk.data.a.u().s()) {
                setRequestedOrientation(3);
            } else {
                setRequestedOrientation(1);
            }
            try {
                Bundle extras = getIntent().getExtras();
                String string = extras.getString(BreakpointSQLiteKey.URL, null);
                this.b = string;
                if (!l.d(string)) {
                    finish();
                    return;
                }
                this.d = extras.getString("cookie", null);
                this.c = extras.getString(e.s, null);
                this.e = extras.getString(com.alipay.sdk.widget.d.v, null);
                this.g = extras.getString(CommonParam.VERSION, com.alipay.sdk.widget.c.c);
                this.f = extras.getBoolean("backisexit", false);
                try {
                    com.alipay.sdk.widget.d dVar = new com.alipay.sdk.widget.d(this, aVarA, this.g);
                    setContentView(dVar);
                    dVar.a(this.e, this.c, this.f);
                    dVar.a(this.b, this.d);
                    dVar.a(this.b);
                    this.a = dVar;
                } catch (Throwable th) {
                    com.alipay.sdk.app.statistic.a.a(aVarA, com.alipay.sdk.app.statistic.b.l, "GetInstalledAppEx", th);
                    finish();
                }
            } catch (Exception unused) {
                finish();
            }
        } catch (Exception unused2) {
            finish();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        com.alipay.sdk.widget.c cVar = this.a;
        if (cVar != null) {
            cVar.c();
        }
    }

    @Override // android.app.Activity
    public void setRequestedOrientation(int i) {
        try {
            super.setRequestedOrientation(i);
        } catch (Throwable th) {
            try {
                com.alipay.sdk.app.statistic.a.a((com.alipay.sdk.sys.a) l.a(this.h), com.alipay.sdk.app.statistic.b.l, com.alipay.sdk.app.statistic.b.F, th);
            } catch (Throwable unused) {
            }
        }
    }
}
