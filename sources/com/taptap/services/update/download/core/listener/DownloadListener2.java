package com.taptap.services.update.download.core.listener;

import com.taptap.services.update.download.DownloadListener;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.ResumeFailedCause;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class DownloadListener2 implements DownloadListener {
    @Override // com.taptap.services.update.download.DownloadListener
    public void connectEnd(DownloadTask downloadTask, int i, int i2, Map<String, List<String>> map) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void connectStart(DownloadTask downloadTask, int i, Map<String, List<String>> map) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void connectTrialEnd(DownloadTask downloadTask, int i, Map<String, List<String>> map) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void connectTrialStart(DownloadTask downloadTask, Map<String, List<String>> map) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void downloadFromBeginning(DownloadTask downloadTask, BreakpointInfo breakpointInfo, ResumeFailedCause resumeFailedCause) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void downloadFromBreakpoint(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void fetchEnd(DownloadTask downloadTask, int i, long j) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void fetchProgress(DownloadTask downloadTask, int i, long j) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void fetchStart(DownloadTask downloadTask, int i, long j) {
    }
}
