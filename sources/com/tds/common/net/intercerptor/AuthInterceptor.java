package com.tds.common.net.intercerptor;

import com.tds.common.account.TdsAccount;
import com.tds.common.net.TdsHttp;
import com.tds.common.net.util.HttpUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class AuthInterceptor implements TdsHttp.Interceptor {
    private TDSAccountProvider tdsAccountProvider;

    public interface TDSAccountProvider {
        TdsAccount<?> getTdsAccount();
    }

    public AuthInterceptor(TDSAccountProvider tDSAccountProvider) {
        this.tdsAccountProvider = tDSAccountProvider;
    }

    @Override // com.tds.common.net.TdsHttp.Interceptor
    public TdsHttp.Response intercept(TdsHttp.Interceptor.Chain chain) throws IOException {
        TdsHttp.Request request = chain.request();
        String strUrl = request.url();
        if (this.tdsAccountProvider.getTdsAccount() != null) {
            chain.request().headers().putAll(HttpUtil.getAuthorizeHeaders(this.tdsAccountProvider.getTdsAccount(), strUrl, request.method()));
        }
        return chain.proceed(chain.request());
    }
}
