package com.taptap.services.update.download.core.interceptor.connect;

import com.taptap.services.update.download.OkDownload;
import com.taptap.services.update.download.core.connection.DownloadConnection;
import com.taptap.services.update.download.core.download.DownloadChain;
import com.taptap.services.update.download.core.interceptor.Interceptor;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class CallServerInterceptor implements Interceptor.Connect {
    @Override // com.taptap.services.update.download.core.interceptor.Interceptor.Connect
    public DownloadConnection.Connected interceptConnect(DownloadChain downloadChain) throws IOException {
        OkDownload.with().downloadStrategy().inspectNetworkOnWifi(downloadChain.getTask());
        OkDownload.with().downloadStrategy().inspectNetworkAvailable();
        return downloadChain.getConnectionOrCreate().execute();
    }
}
