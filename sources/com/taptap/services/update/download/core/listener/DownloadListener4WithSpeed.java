package com.taptap.services.update.download.core.listener;

import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.breakpoint.BlockInfo;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.listener.assist.Listener4Assist;
import com.taptap.services.update.download.core.listener.assist.Listener4SpeedAssistExtend;
import com.taptap.services.update.download.core.listener.assist.ListenerModelHandler;

/* JADX INFO: loaded from: classes.dex */
public abstract class DownloadListener4WithSpeed extends DownloadListener4 implements Listener4SpeedAssistExtend.Listener4SpeedCallback {
    @Override // com.taptap.services.update.download.core.listener.assist.Listener4Assist.Listener4Callback
    public final void blockEnd(DownloadTask downloadTask, int i, BlockInfo blockInfo) {
    }

    @Override // com.taptap.services.update.download.core.listener.assist.Listener4Assist.Listener4Callback
    public final void infoReady(DownloadTask downloadTask, BreakpointInfo breakpointInfo, boolean z, Listener4Assist.Listener4Model listener4Model) {
    }

    @Override // com.taptap.services.update.download.core.listener.assist.Listener4Assist.Listener4Callback
    public final void progress(DownloadTask downloadTask, long j) {
    }

    @Override // com.taptap.services.update.download.core.listener.assist.Listener4Assist.Listener4Callback
    public final void progressBlock(DownloadTask downloadTask, int i, long j) {
    }

    @Override // com.taptap.services.update.download.core.listener.assist.Listener4Assist.Listener4Callback
    public final void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc, Listener4Assist.Listener4Model listener4Model) {
    }

    private DownloadListener4WithSpeed(Listener4SpeedAssistExtend listener4SpeedAssistExtend) {
        super(new Listener4Assist(new Listener4WithSpeedModelCreator()));
        listener4SpeedAssistExtend.setCallback(this);
        setAssistExtend(listener4SpeedAssistExtend);
    }

    public DownloadListener4WithSpeed() {
        this(new Listener4SpeedAssistExtend());
    }

    private static class Listener4WithSpeedModelCreator implements ListenerModelHandler.ModelCreator<Listener4SpeedAssistExtend.Listener4SpeedModel> {
        private Listener4WithSpeedModelCreator() {
        }

        @Override // com.taptap.services.update.download.core.listener.assist.ListenerModelHandler.ModelCreator
        public Listener4SpeedAssistExtend.Listener4SpeedModel create(int i) {
            return new Listener4SpeedAssistExtend.Listener4SpeedModel(i);
        }
    }
}
