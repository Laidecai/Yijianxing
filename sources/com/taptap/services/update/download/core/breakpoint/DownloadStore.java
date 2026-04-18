package com.taptap.services.update.download.core.breakpoint;

import com.taptap.services.update.download.core.cause.EndCause;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface DownloadStore extends BreakpointStore {
    BreakpointInfo getAfterCompleted(int i);

    boolean markFileClear(int i);

    boolean markFileDirty(int i);

    void onSyncToFilesystemSuccess(BreakpointInfo breakpointInfo, int i, long j) throws IOException;

    void onTaskEnd(int i, EndCause endCause, Exception exc);

    void onTaskStart(int i);
}
