package com.taptap.services.update;

import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.download.DownloadStrategy;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateDownloadStrategy extends DownloadStrategy {
    @Override // com.taptap.services.update.download.core.download.DownloadStrategy
    protected String determineFilename(String str, DownloadTask downloadTask) throws IOException {
        try {
            return super.determineFilename(str, downloadTask);
        } catch (Exception e) {
            e.printStackTrace();
            return "TapTap_latest.apk";
        }
    }
}
