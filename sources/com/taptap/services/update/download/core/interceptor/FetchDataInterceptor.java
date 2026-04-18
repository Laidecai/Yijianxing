package com.taptap.services.update.download.core.interceptor;

import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.OkDownload;
import com.taptap.services.update.download.core.dispatcher.CallbackDispatcher;
import com.taptap.services.update.download.core.download.DownloadChain;
import com.taptap.services.update.download.core.exception.InterruptException;
import com.taptap.services.update.download.core.file.MultiPointOutputStream;
import com.taptap.services.update.download.core.interceptor.Interceptor;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class FetchDataInterceptor implements Interceptor.Fetch {
    private final int blockIndex;
    private final CallbackDispatcher dispatcher = OkDownload.with().callbackDispatcher();
    private final InputStream inputStream;
    private final MultiPointOutputStream outputStream;
    private final byte[] readBuffer;
    private final DownloadTask task;

    public FetchDataInterceptor(int i, InputStream inputStream, MultiPointOutputStream multiPointOutputStream, DownloadTask downloadTask) {
        this.blockIndex = i;
        this.inputStream = inputStream;
        this.readBuffer = new byte[downloadTask.getReadBufferSize()];
        this.outputStream = multiPointOutputStream;
        this.task = downloadTask;
    }

    @Override // com.taptap.services.update.download.core.interceptor.Interceptor.Fetch
    public long interceptFetch(DownloadChain downloadChain) throws IOException {
        if (downloadChain.getCache().isInterrupt()) {
            throw InterruptException.SIGNAL;
        }
        OkDownload.with().downloadStrategy().inspectNetworkOnWifi(downloadChain.getTask());
        int i = this.inputStream.read(this.readBuffer);
        if (i == -1) {
            return i;
        }
        this.outputStream.write(this.blockIndex, this.readBuffer, i);
        long j = i;
        downloadChain.increaseCallbackBytes(j);
        if (this.dispatcher.isFetchProcessMoment(this.task)) {
            downloadChain.flushNoCallbackIncreaseBytes();
        }
        return j;
    }
}
