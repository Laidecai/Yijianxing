package com.taptap.services.update.download.core.interceptor;

import com.taptap.services.update.download.core.connection.DownloadConnection;
import com.taptap.services.update.download.core.download.DownloadCache;
import com.taptap.services.update.download.core.download.DownloadChain;
import com.taptap.services.update.download.core.exception.InterruptException;
import com.taptap.services.update.download.core.exception.RetryException;
import com.taptap.services.update.download.core.interceptor.Interceptor;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class RetryInterceptor implements Interceptor.Connect, Interceptor.Fetch {
    @Override // com.taptap.services.update.download.core.interceptor.Interceptor.Connect
    public DownloadConnection.Connected interceptConnect(DownloadChain downloadChain) throws IOException {
        DownloadCache cache = downloadChain.getCache();
        while (true) {
            try {
                if (cache.isInterrupt()) {
                    throw InterruptException.SIGNAL;
                }
                return downloadChain.processConnect();
            } catch (IOException e) {
                if (e instanceof RetryException) {
                    downloadChain.resetConnectForRetry();
                } else {
                    downloadChain.getCache().catchException(e);
                    downloadChain.getOutputStream().catchBlockConnectException(downloadChain.getBlockIndex());
                    throw e;
                }
            }
        }
    }

    @Override // com.taptap.services.update.download.core.interceptor.Interceptor.Fetch
    public long interceptFetch(DownloadChain downloadChain) throws IOException {
        try {
            return downloadChain.processFetch();
        } catch (IOException e) {
            downloadChain.getCache().catchException(e);
            throw e;
        }
    }
}
