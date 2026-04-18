package com.taptap.services.update.download.core.listener;

import com.taptap.services.update.download.DownloadListener;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.cause.ResumeFailedCause;
import com.taptap.services.update.download.core.listener.assist.Listener4Assist;
import com.taptap.services.update.download.core.listener.assist.ListenerAssist;
import com.taptap.services.update.download.core.listener.assist.ListenerModelHandler;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class DownloadListener4 implements DownloadListener, Listener4Assist.Listener4Callback, ListenerAssist {
    final Listener4Assist assist;

    @Override // com.taptap.services.update.download.DownloadListener
    public void connectTrialEnd(DownloadTask downloadTask, int i, Map<String, List<String>> map) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void connectTrialStart(DownloadTask downloadTask, Map<String, List<String>> map) {
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void fetchStart(DownloadTask downloadTask, int i, long j) {
    }

    DownloadListener4(Listener4Assist listener4Assist) {
        this.assist = listener4Assist;
        listener4Assist.setCallback(this);
    }

    public DownloadListener4() {
        this(new Listener4Assist(new Listener4ModelCreator()));
    }

    public void setAssistExtend(Listener4Assist.AssistExtend assistExtend) {
        this.assist.setAssistExtend(assistExtend);
    }

    @Override // com.taptap.services.update.download.core.listener.assist.ListenerAssist
    public boolean isAlwaysRecoverAssistModel() {
        return this.assist.isAlwaysRecoverAssistModel();
    }

    @Override // com.taptap.services.update.download.core.listener.assist.ListenerAssist
    public void setAlwaysRecoverAssistModel(boolean z) {
        this.assist.setAlwaysRecoverAssistModel(z);
    }

    @Override // com.taptap.services.update.download.core.listener.assist.ListenerAssist
    public void setAlwaysRecoverAssistModelIfNotSet(boolean z) {
        this.assist.setAlwaysRecoverAssistModelIfNotSet(z);
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public final void downloadFromBeginning(DownloadTask downloadTask, BreakpointInfo breakpointInfo, ResumeFailedCause resumeFailedCause) {
        this.assist.infoReady(downloadTask, breakpointInfo, false);
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public final void downloadFromBreakpoint(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
        this.assist.infoReady(downloadTask, breakpointInfo, true);
    }

    public void setCurrentOffset(DownloadTask downloadTask, long j) {
        this.assist.setCurrentOffset(downloadTask, j);
    }

    public long getCurrentOffset(DownloadTask downloadTask) {
        return this.assist.getCurrentOffset(downloadTask);
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public final void fetchProgress(DownloadTask downloadTask, int i, long j) {
        this.assist.fetchProgress(downloadTask, i, j);
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public void fetchEnd(DownloadTask downloadTask, int i, long j) {
        this.assist.fetchEnd(downloadTask, i);
    }

    @Override // com.taptap.services.update.download.DownloadListener
    public final void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc) {
        this.assist.taskEnd(downloadTask, endCause, exc);
    }

    static class Listener4ModelCreator implements ListenerModelHandler.ModelCreator<Listener4Assist.Listener4Model> {
        Listener4ModelCreator() {
        }

        @Override // com.taptap.services.update.download.core.listener.assist.ListenerModelHandler.ModelCreator
        public Listener4Assist.Listener4Model create(int i) {
            return new Listener4Assist.Listener4Model(i);
        }
    }
}
