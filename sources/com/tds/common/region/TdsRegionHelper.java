package com.tds.common.region;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.tds.common.net.ResponseBean;
import com.tds.common.net.TdsApiClient;
import com.tds.common.net.TdsHttp;
import com.tds.common.net.XUAParams;
import com.tds.common.net.intercerptor.AddXUAInterceptor;
import com.tds.common.net.json.TypeRef;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.functions.Func1;
import com.tds.common.reactor.rxandroid.schedulers.AndroidSchedulers;
import com.tds.common.reactor.schedulers.Schedulers;
import com.tds.common.utils.Validate;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class TdsRegionHelper {
    private static final String RegionKey = "tds_region_key";
    private static final String RegionStore = "tds_region_store";

    public interface RegionCallback {
        void onRegion(boolean z);
    }

    private static boolean isChinaOperator(int i) {
        return i == 1;
    }

    public static void getRegionCode(final Context context, final RegionCallback regionCallback) {
        Validate.notNull(regionCallback, "callback cannot be null");
        int localRegion = getLocalRegion(context);
        if (localRegion != -1) {
            regionCallback.onRegion(localRegion == 1);
        } else {
            final int operatorInfo = getOperatorInfo(context);
            getRemoteRegion(operatorInfo, context.getPackageName()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Subscriber<? super RegionBean>) new Subscriber<RegionBean>() { // from class: com.tds.common.region.TdsRegionHelper.1
                @Override // com.tds.common.reactor.Observer
                public void onCompleted() {
                }

                @Override // com.tds.common.reactor.Observer
                public void onError(Throwable th) {
                    TdsRegionHelper.doLocalJudge(context, regionCallback, operatorInfo);
                }

                @Override // com.tds.common.reactor.Observer
                public void onNext(RegionBean regionBean) {
                    if (regionBean == null || regionBean.regionCode == -1) {
                        TdsRegionHelper.doLocalJudge(context, regionCallback, operatorInfo);
                    } else {
                        TdsRegionHelper.setLocalRegion(context, regionBean.regionCode);
                        regionCallback.onRegion(regionBean.regionCode == 1);
                    }
                }
            });
        }
    }

    public static int getOperatorInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return 0;
        }
        String networkOperator = telephonyManager.getNetworkOperator();
        Log.i("TdsRegionHelper", "NetworkOperator=" + networkOperator);
        if (networkOperator != null) {
            return (networkOperator.startsWith("460") || networkOperator.startsWith("461")) ? 1 : 0;
        }
        return 0;
    }

    private static Observable<RegionBean> getRemoteRegion(int i, String str) {
        TdsApiClient tdsApiClientBuild = new TdsApiClient.Builder().baseUrl("https://tds.taptap-api.com/tool/check_ip_region").tdsClient(TdsHttp.newClientBuilder().addInterceptor(new AddXUAInterceptor(XUAParams.getCommonXUAParams("TdsCommon", 32900001, "3.29.0"))).build()).build();
        HashMap map = new HashMap();
        map.put("carrier", "" + i);
        map.put("bundle", str);
        return tdsApiClientBuild.getAsync(new TypeRef<ResponseBean<RegionBean>>() { // from class: com.tds.common.region.TdsRegionHelper.3
        }, "", map).map(new Func1<ResponseBean<RegionBean>, RegionBean>() { // from class: com.tds.common.region.TdsRegionHelper.2
            @Override // com.tds.common.reactor.functions.Func1
            public RegionBean call(ResponseBean<RegionBean> responseBean) {
                return responseBean.data;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v5 */
    public static void doLocalJudge(Context context, RegionCallback regionCallback, int i) {
        ?? r2 = (isChinaOperator(i) || isLocaleChina()) ? 1 : 0;
        setLocalRegion(context, r2);
        regionCallback.onRegion(r2);
    }

    private static int getLocalRegion(Context context) {
        return context.getSharedPreferences(RegionStore, 0).getInt(RegionKey, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setLocalRegion(Context context, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(RegionStore, 0).edit();
        editorEdit.putInt(RegionKey, i);
        editorEdit.apply();
    }

    private static boolean isLocaleChina() {
        return Locale.getDefault().getCountry().toLowerCase(Locale.US).contains("cn");
    }
}
