package com.taptap.services.update.download;

import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.cause.ResumeFailedCause;

/* JADX INFO: loaded from: classes.dex */
public interface DownloadMonitor {
    void taskDownloadFromBeginning(DownloadTask downloadTask, BreakpointInfo breakpointInfo, ResumeFailedCause resumeFailedCause);

    void taskDownloadFromBreakpoint(DownloadTask downloadTask, BreakpointInfo breakpointInfo);

    void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc);

    void taskStart(DownloadTask downloadTask);
}
