package com.tds.common.net.intercerptor;

import android.text.TextUtils;
import com.tds.common.net.TdsHttp;
import com.tds.common.net.XUAParams;
import com.tds.common.net.constant.Constants;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class AddXUAInterceptor implements TdsHttp.Interceptor {
    private final XUAParams xuaParams;

    public AddXUAInterceptor(XUAParams xUAParams) {
        this.xuaParams = xUAParams;
    }

    public AddXUAInterceptor(String str, int i, String str2) {
        this(XUAParams.getCommonXUAParams(str, i, str2));
    }

    @Override // com.tds.common.net.TdsHttp.Interceptor
    public TdsHttp.Response intercept(TdsHttp.Interceptor.Chain chain) throws IOException {
        TdsHttp.Request request = chain.request();
        XUAParams xUAParams = this.xuaParams;
        String xUAValue = xUAParams != null ? xUAParams.getXUAValue() : "";
        if (!TextUtils.isEmpty(xUAValue)) {
            request.headers().put(Constants.HTTP_COMMON_HEADERS.XUA, xUAValue);
        }
        return chain.proceed(request);
    }
}
