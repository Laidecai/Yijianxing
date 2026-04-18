package com.alipay.security.mobile.module.http;

import android.content.Context;
import com.alipay.android.phone.mrpc.core.aa;
import com.alipay.android.phone.mrpc.core.h;
import com.alipay.android.phone.mrpc.core.w;
import com.alipay.tscenter.biz.rpc.deviceFp.BugTrackMessageService;
import com.alipay.tscenter.biz.rpc.report.general.DataReportService;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c implements a {
    public static c d;
    public static DataReportResult e;
    public w a;
    public BugTrackMessageService b;
    public DataReportService c;

    public c(Context context, String str) {
        this.a = null;
        this.b = null;
        this.c = null;
        aa aaVar = new aa();
        aaVar.a(str);
        h hVar = new h(context);
        this.a = hVar;
        this.b = (BugTrackMessageService) hVar.a(BugTrackMessageService.class, aaVar);
        this.c = (DataReportService) this.a.a(DataReportService.class, aaVar);
    }

    public static synchronized c a(Context context, String str) {
        if (d == null) {
            d = new c(context, str);
        }
        return d;
    }

    @Override // com.alipay.security.mobile.module.http.a
    public DataReportResult a(DataReportRequest dataReportRequest) throws InterruptedException {
        if (dataReportRequest == null) {
            return null;
        }
        if (this.c != null) {
            e = null;
            new Thread(new b(this, dataReportRequest)).start();
            for (int i = com.alipay.security.mobile.module.http.constant.a.a; e == null && i >= 0; i -= 50) {
                Thread.sleep(50L);
            }
        }
        return e;
    }

    @Override // com.alipay.security.mobile.module.http.a
    public boolean logCollect(String str) {
        BugTrackMessageService bugTrackMessageService;
        if (com.alipay.security.mobile.module.a.a.a(str) || (bugTrackMessageService = this.b) == null) {
            return false;
        }
        String strLogCollect = null;
        try {
            strLogCollect = bugTrackMessageService.logCollect(com.alipay.security.mobile.module.a.a.f(str));
        } catch (Throwable unused) {
        }
        if (com.alipay.security.mobile.module.a.a.a(strLogCollect)) {
            return false;
        }
        return ((Boolean) new JSONObject(strLogCollect).get("success")).booleanValue();
    }
}
