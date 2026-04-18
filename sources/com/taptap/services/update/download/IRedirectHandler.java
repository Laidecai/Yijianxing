package com.taptap.services.update.download;

import com.taptap.services.update.download.core.connection.DownloadConnection;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface IRedirectHandler {
    String getRedirectLocation();

    void handleRedirect(DownloadConnection downloadConnection, DownloadConnection.Connected connected, Map<String, List<String>> map) throws IOException;
}
