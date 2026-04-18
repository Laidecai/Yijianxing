package com.taptap.services.update.download;

import com.taptap.services.update.download.core.cause.EndCause;

/* JADX INFO: loaded from: classes.dex */
public interface DownloadContextListener {
    void queueEnd(DownloadContext downloadContext);

    void taskEnd(DownloadContext downloadContext, DownloadTask downloadTask, EndCause endCause, Exception exc, int i);
}
