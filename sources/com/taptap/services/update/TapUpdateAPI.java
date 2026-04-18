package com.taptap.services.update;

import android.content.Context;
import android.content.pm.PackageManager;
import com.taptap.services.update.bean.TapUpdateInfo;
import com.tds.common.net.ResponseBean;
import com.tds.common.net.Skynet;
import com.tds.common.net.TdsApiClient;
import com.tds.common.net.TdsHttp;
import com.tds.common.net.XUAParams;
import com.tds.common.net.intercerptor.AddXUAInterceptor;
import com.tds.common.net.json.TypeRef;
import com.tds.common.net.util.HostReplaceUtil;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.functions.Func1;
import com.tds.common.utils.GUIDHelper;
import com.tds.common.utils.TapGameUtil;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateAPI {
    private TapUpdateInfo.TapUpdateInfoTemplate cacheUpdateInfoTpl;

    public static void initSkyNet(Context context) {
        if (Skynet.getInstance().getTdsApiClient(TapUpdateLogger.TAG) != null) {
            return;
        }
        TapUpdateDialogManager.getInstance().registerPackageInstallReceiver(context);
        Skynet.getInstance().registerTdsClient(TapUpdateLogger.TAG, new TdsApiClient.Builder().baseUrl("").tdsClient(TdsHttp.newClientBuilder().trustAllCerts(TapUpdateLogger.isDebug).addInterceptor(new AddXUAInterceptor(makeCommonHeaders(context))).build()).build());
    }

    public static XUAParams makeCommonHeaders(Context context) {
        GUIDHelper.INSTANCE.init(context);
        return XUAParams.getCommonXUAParams(TapUpdateLogger.TAG, 32900001, "3.29.0");
    }

    public Observable<TapUpdateInfo> getUpdateInfo(Context context) {
        int tapVersionCode;
        if (this.cacheUpdateInfoTpl != null) {
            return Observable.create(new Observable.OnSubscribe<TapUpdateInfo>() { // from class: com.taptap.services.update.TapUpdateAPI.1
                @Override // com.tds.common.reactor.functions.Action1
                public void call(Subscriber<? super TapUpdateInfo> subscriber) {
                    subscriber.onNext(new TapUpdateInfo(TapUpdateAPI.this.cacheUpdateInfoTpl));
                }
            });
        }
        HashMap map = new HashMap();
        if (context != null) {
            map.put("identifier", context.getPackageName());
        }
        if (TapGameUtil.isTapTapInstalled(context) && (tapVersionCode = getTapVersionCode(context)) != -1) {
            map.put("taptap_version_code", String.valueOf(tapVersionCode));
        }
        return Skynet.getInstance().getTdsApiClient(TapUpdateLogger.TAG).getAsync(new TypeRef<ResponseBean<TapUpdateInfo.TapUpdateInfoTemplate>>() { // from class: com.taptap.services.update.TapUpdateAPI.3
        }, HostReplaceUtil.getInstance().getReplacedHost("https://store-release.tapapis.cn") + "/app/v1/update-info", map).map(new Func1<ResponseBean<TapUpdateInfo.TapUpdateInfoTemplate>, TapUpdateInfo>() { // from class: com.taptap.services.update.TapUpdateAPI.2
            @Override // com.tds.common.reactor.functions.Func1
            public TapUpdateInfo call(ResponseBean<TapUpdateInfo.TapUpdateInfoTemplate> responseBean) {
                TapUpdateInfo.TapUpdateInfoTemplate tapUpdateInfoTemplate = responseBean.data;
                if (tapUpdateInfoTemplate != null && tapUpdateInfoTemplate.isDataValid()) {
                    TapUpdateAPI.this.cacheUpdateInfoTpl = tapUpdateInfoTemplate;
                    return new TapUpdateInfo(TapUpdateAPI.this.cacheUpdateInfoTpl);
                }
                return new TapUpdateInfo(null);
            }
        });
    }

    private int getTapVersionCode(Context context) {
        PackageManager packageManager;
        if (context == null || (packageManager = context.getPackageManager()) == null) {
            return -1;
        }
        try {
            return packageManager.getPackageInfo(TapGameUtil.PACKAGE_NAME_TAPTAP, 0).versionCode;
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public static TapUpdateAPI getInstance() {
        return Single.INSTANCE.api;
    }

    enum Single {
        INSTANCE;

        TapUpdateAPI api = new TapUpdateAPI();

        Single() {
        }
    }
}
