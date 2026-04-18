package com.taptap.services.update.download.core.listener.assist;

import android.util.SparseArray;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.breakpoint.BlockInfo;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.EndCause;
import com.taptap.services.update.download.core.listener.assist.Listener4Assist.Listener4Model;
import com.taptap.services.update.download.core.listener.assist.ListenerModelHandler;

/* JADX INFO: loaded from: classes.dex */
public class Listener4Assist<T extends Listener4Model> implements ListenerAssist {
    private AssistExtend assistExtend;
    Listener4Callback callback;
    private final ListenerModelHandler<T> modelHandler;

    public interface AssistExtend {
        boolean dispatchBlockEnd(DownloadTask downloadTask, int i, Listener4Model listener4Model);

        boolean dispatchFetchProgress(DownloadTask downloadTask, int i, long j, Listener4Model listener4Model);

        boolean dispatchInfoReady(DownloadTask downloadTask, BreakpointInfo breakpointInfo, boolean z, Listener4Model listener4Model);

        boolean dispatchTaskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc, Listener4Model listener4Model);
    }

    public interface Listener4Callback {
        void blockEnd(DownloadTask downloadTask, int i, BlockInfo blockInfo);

        void infoReady(DownloadTask downloadTask, BreakpointInfo breakpointInfo, boolean z, Listener4Model listener4Model);

        void progress(DownloadTask downloadTask, long j);

        void progressBlock(DownloadTask downloadTask, int i, long j);

        void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc, Listener4Model listener4Model);
    }

    Listener4Assist(ListenerModelHandler<T> listenerModelHandler) {
        this.modelHandler = listenerModelHandler;
    }

    public Listener4Assist(ListenerModelHandler.ModelCreator<T> modelCreator) {
        this.modelHandler = new ListenerModelHandler<>(modelCreator);
    }

    public void setCallback(Listener4Callback listener4Callback) {
        this.callback = listener4Callback;
    }

    public void setAssistExtend(AssistExtend assistExtend) {
        this.assistExtend = assistExtend;
    }

    public AssistExtend getAssistExtend() {
        return this.assistExtend;
    }

    public void infoReady(DownloadTask downloadTask, BreakpointInfo breakpointInfo, boolean z) {
        Listener4Callback listener4Callback;
        Listener4Model listener4Model = (Listener4Model) this.modelHandler.addAndGetModel(downloadTask, breakpointInfo);
        AssistExtend assistExtend = this.assistExtend;
        if ((assistExtend == null || !assistExtend.dispatchInfoReady(downloadTask, breakpointInfo, z, listener4Model)) && (listener4Callback = this.callback) != null) {
            listener4Callback.infoReady(downloadTask, breakpointInfo, z, listener4Model);
        }
    }

    public void setCurrentOffset(DownloadTask downloadTask, long j) {
        ((Listener4Model) this.modelHandler.addAndGetModel(downloadTask, downloadTask.getInfo())).setCurrentOffset(j);
    }

    public long getCurrentOffset(DownloadTask downloadTask) {
        Listener4Model listener4Model = (Listener4Model) this.modelHandler.getOrRecoverModel(downloadTask, downloadTask.getInfo());
        if (listener4Model != null) {
            return listener4Model.getCurrentOffset();
        }
        return 0L;
    }

    public void fetchProgress(DownloadTask downloadTask, int i, long j) {
        Listener4Callback listener4Callback;
        Listener4Model listener4Model = (Listener4Model) this.modelHandler.getOrRecoverModel(downloadTask, downloadTask.getInfo());
        if (listener4Model == null || listener4Model.blockCurrentOffsetMap.get(i) == null) {
            return;
        }
        long jLongValue = listener4Model.blockCurrentOffsetMap.get(i).longValue() + j;
        listener4Model.blockCurrentOffsetMap.put(i, Long.valueOf(jLongValue));
        listener4Model.currentOffset += j;
        AssistExtend assistExtend = this.assistExtend;
        if ((assistExtend == null || !assistExtend.dispatchFetchProgress(downloadTask, i, j, listener4Model)) && (listener4Callback = this.callback) != null) {
            listener4Callback.progressBlock(downloadTask, i, jLongValue);
            this.callback.progress(downloadTask, listener4Model.currentOffset);
        }
    }

    public void fetchEnd(DownloadTask downloadTask, int i) {
        Listener4Callback listener4Callback;
        Listener4Model listener4Model = (Listener4Model) this.modelHandler.getOrRecoverModel(downloadTask, downloadTask.getInfo());
        if (listener4Model == null) {
            return;
        }
        AssistExtend assistExtend = this.assistExtend;
        if ((assistExtend == null || !assistExtend.dispatchBlockEnd(downloadTask, i, listener4Model)) && (listener4Callback = this.callback) != null) {
            listener4Callback.blockEnd(downloadTask, i, listener4Model.info.getBlock(i));
        }
    }

    public synchronized void taskEnd(DownloadTask downloadTask, EndCause endCause, Exception exc) {
        Listener4Model listener4Model = (Listener4Model) this.modelHandler.removeOrCreate(downloadTask, downloadTask.getInfo());
        AssistExtend assistExtend = this.assistExtend;
        if (assistExtend == null || !assistExtend.dispatchTaskEnd(downloadTask, endCause, exc, listener4Model)) {
            Listener4Callback listener4Callback = this.callback;
            if (listener4Callback != null) {
                listener4Callback.taskEnd(downloadTask, endCause, exc, listener4Model);
            }
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

    public static class Listener4Model implements ListenerModelHandler.ListenerModel {
        SparseArray<Long> blockCurrentOffsetMap;
        long currentOffset;
        private final int id;
        BreakpointInfo info;

        public Listener4Model(int i) {
            this.id = i;
        }

        SparseArray<Long> getBlockCurrentOffsetMap() {
            return this.blockCurrentOffsetMap;
        }

        public long getCurrentOffset() {
            return this.currentOffset;
        }

        public void setCurrentOffset(long j) {
            this.currentOffset = j;
        }

        public long getBlockCurrentOffset(int i) {
            return this.blockCurrentOffsetMap.get(i).longValue();
        }

        public SparseArray<Long> cloneBlockCurrentOffsetMap() {
            return this.blockCurrentOffsetMap.clone();
        }

        public BreakpointInfo getInfo() {
            return this.info;
        }

        @Override // com.taptap.services.update.download.core.listener.assist.ListenerModelHandler.ListenerModel
        public int getId() {
            return this.id;
        }

        @Override // com.taptap.services.update.download.core.listener.assist.ListenerModelHandler.ListenerModel
        public void onInfoValid(BreakpointInfo breakpointInfo) {
            this.info = breakpointInfo;
            this.currentOffset = breakpointInfo.getTotalOffset();
            SparseArray<Long> sparseArray = new SparseArray<>();
            int blockCount = breakpointInfo.getBlockCount();
            for (int i = 0; i < blockCount; i++) {
                sparseArray.put(i, Long.valueOf(breakpointInfo.getBlock(i).getCurrentOffset()));
            }
            this.blockCurrentOffsetMap = sparseArray;
        }
    }
}
