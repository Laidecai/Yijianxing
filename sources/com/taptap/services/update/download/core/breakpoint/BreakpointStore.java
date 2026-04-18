package com.taptap.services.update.download.core.breakpoint;

import com.taptap.services.update.download.DownloadTask;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface BreakpointStore {
    BreakpointInfo createAndInsert(DownloadTask downloadTask) throws IOException;

    BreakpointInfo findAnotherInfoFromCompare(DownloadTask downloadTask, BreakpointInfo breakpointInfo);

    int findOrCreateId(DownloadTask downloadTask);

    BreakpointInfo get(int i);

    String getResponseFilename(String str);

    boolean isFileDirty(int i);

    boolean isOnlyMemoryCache();

    void remove(int i);

    boolean update(BreakpointInfo breakpointInfo) throws IOException;
}
