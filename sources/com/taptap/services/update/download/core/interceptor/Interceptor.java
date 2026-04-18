package com.taptap.services.update.download.core.interceptor;

import com.taptap.services.update.download.core.connection.DownloadConnection;
import com.taptap.services.update.download.core.download.DownloadChain;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface Interceptor {

    public interface Connect {
        DownloadConnection.Connected interceptConnect(DownloadChain downloadChain) throws IOException;
    }

    public interface Fetch {
        long interceptFetch(DownloadChain downloadChain) throws IOException;
    }
}
