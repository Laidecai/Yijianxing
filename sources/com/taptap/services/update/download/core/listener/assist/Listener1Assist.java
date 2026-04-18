package com.taptap.services.update.download.core.listener.assist;

import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.cause.ResumeFailedCause;
import com.taptap.services.update.download.core.listener.assist.ListenerModelHandler;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public class Listener1Assist implements ListenerAssist, ListenerModelHandler.ModelCreator<Listener1Model> {
    private Listener1Callback callback;
    private final ListenerModelHandler<Listener1Model> modelHandler;

    public interface Listener1Callback {
        void connected(DownloadTask downloadTask, int i, long j, long j2);

        void progress(DownloadTask downloadTask, long j, long j2);

        void retry(DownloadTask downloadTask, ResumeFailedCause resumeFailedCause);

        void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc, Listener1Model listener1Model);

        void taskStart(DownloadTask downloadTask, Listener1Model listener1Model);
    }

    public Listener1Assist() {
        this.modelHandler = new ListenerModelHandler<>(this);
    }

    Listener1Assist(ListenerModelHandler<Listener1Model> listenerModelHandler) {
        this.modelHandler = listenerModelHandler;
    }

    public void setCallback(Listener1Callback listener1Callback) {
        this.callback = listener1Callback;
    }

    public void taskStart(DownloadTask downloadTask) {
        Listener1Model listener1Model = (Listener1Model) this.modelHandler.addAndGetModel(downloadTask, null);
        Listener1Callback listener1Callback = this.callback;
        if (listener1Callback != null) {
            listener1Callback.taskStart(downloadTask, listener1Model);
        }
    }

    public void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc) {
        Listener1Model listener1Model = (Listener1Model) this.modelHandler.removeOrCreate(downloadTask, downloadTask.getInfo());
        Listener1Callback listener1Callback = this.callback;
        if (listener1Callback != null) {
            listener1Callback.taskEnd(downloadTask, endCause, exc, listener1Model);
        }
    }

    public void downloadFromBeginning(DownloadTask downloadTask, BreakpointInfo breakpointInfo, ResumeFailedCause resumeFailedCause) {
        Listener1Callback listener1Callback;
        Listener1Model listener1Model = (Listener1Model) this.modelHandler.getOrRecoverModel(downloadTask, breakpointInfo);
        if (listener1Model == null) {
            return;
        }
        listener1Model.onInfoValid(breakpointInfo);
        if (listener1Model.isStarted.booleanValue() && (listener1Callback = this.callback) != null) {
            listener1Callback.retry(downloadTask, resumeFailedCause);
        }
        listener1Model.isStarted = true;
        listener1Model.isFromResumed = false;
        listener1Model.isFirstConnect = true;
    }

    public void downloadFromBreakpoint(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
        Listener1Model listener1Model = (Listener1Model) this.modelHandler.getOrRecoverModel(downloadTask, breakpointInfo);
        if (listener1Model == null) {
            return;
        }
        listener1Model.onInfoValid(breakpointInfo);
        listener1Model.isStarted = true;
        listener1Model.isFromResumed = true;
        listener1Model.isFirstConnect = true;
    }

    public void connectEnd(DownloadTask downloadTask) {
        Listener1Model listener1Model = (Listener1Model) this.modelHandler.getOrRecoverModel(downloadTask, downloadTask.getInfo());
        if (listener1Model == null) {
            return;
        }
        if (Boolean.TRUE.equals(listener1Model.isFromResumed) && Boolean.TRUE.equals(listener1Model.isFirstConnect)) {
            listener1Model.isFirstConnect = false;
        }
        Listener1Callback listener1Callback = this.callback;
        if (listener1Callback != null) {
            listener1Callback.connected(downloadTask, listener1Model.blockCount, listener1Model.currentOffset.get(), listener1Model.totalLength);
        }
    }

    public void fetchProgress(DownloadTask downloadTask, long j) {
        Listener1Model listener1Model = (Listener1Model) this.modelHandler.getOrRecoverModel(downloadTask, downloadTask.getInfo());
        if (listener1Model == null) {
            return;
        }
        listener1Model.currentOffset.addAndGet(j);
        Listener1Callback listener1Callback = this.callback;
        if (listener1Callback != null) {
            listener1Callback.progress(downloadTask, listener1Model.currentOffset.get(), listener1Model.totalLength);
        }
    }

    @Override // com.taptap.services.update.download.core.listener.assist.ListenerAssist
    public boolean isAlwaysRecoverAssistModel() {
        return this.modelHandler.isAlwaysRecoverAssistModel();
    }

    @Override // com.taptap.services.update.download.core.listener.assist.ListenerAssist
    public void setAlwaysRecoverAssistModel(boolean z) {
        this.modelHandler.setAlwaysRecoverAssistModel(z);
    }

    @Override // com.taptap.services.update.download.core.listener.assist.ListenerAssist
    public void setAlwaysRecoverAssistModelIfNotSet(boolean z) {
        this.modelHandler.setAlwaysRecoverAssistModelIfNotSet(z);
    }

    @Override // com.taptap.services.update.download.core.listener.assist.ListenerModelHandler.ModelCreator
    public Listener1Model create(int i) {
        return new Listener1Model(i);
    }

    public static class Listener1Model implements ListenerModelHandler.ListenerModel {
        int blockCount;
        final AtomicLong currentOffset = new AtomicLong();
        final int id;
        volatile Boolean isFirstConnect;
        Boolean isFromResumed;
        Boolean isStarted;
        long totalLength;

        Listener1Model(int i) {
            this.id = i;
        }

        public long getTotalLength() {
            return this.totalLength;
        }

        @Override // com.taptap.services.update.download.core.listener.assist.ListenerModelHandler.ListenerModel
        public int getId() {
            return this.id;
        }

        @Override // com.taptap.services.update.download.core.listener.assist.ListenerModelHandler.ListenerModel
        public void onInfoValid(BreakpointInfo breakpointInfo) {
            this.blockCount = breakpointInfo.getBlockCount();
            this.totalLength = breakpointInfo.getTotalLength();
            this.currentOffset.set(breakpointInfo.getTotalOffset());
            if (this.isStarted == null) {
                this.isStarted = false;
            }
            if (this.isFromResumed == null) {
                this.isFromResumed = Boolean.valueOf(this.currentOffset.get() > 0);
            }
            if (this.isFirstConnect == null) {
                this.isFirstConnect = true;
            }
        }
    }
}
