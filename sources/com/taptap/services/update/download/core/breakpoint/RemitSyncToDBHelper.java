package com.taptap.services.update.download.core.breakpoint;

import com.taptap.services.update.download.core.breakpoint.RemitSyncExecutor;

/* JADX INFO: loaded from: classes.dex */
class RemitSyncToDBHelper {
    long delayMillis;
    private final RemitSyncExecutor executor;

    RemitSyncToDBHelper(RemitSyncExecutor.RemitAgent remitAgent) {
        this(new RemitSyncExecutor(remitAgent));
    }

    RemitSyncToDBHelper(RemitSyncExecutor remitSyncExecutor) {
        this.executor = remitSyncExecutor;
        this.delayMillis = 1500L;
    }

    void shutdown() {
        this.executor.shutdown();
    }

    boolean isNotFreeToDatabase(int i) {
        return !this.executor.isFreeToDatabase(i);
    }

    void onTaskStart(int i) {
        this.executor.removePostWithId(i);
        this.executor.postSyncInfoDelay(i, this.delayMillis);
    }

    void endAndEnsureToDB(int i) {
        this.executor.removePostWithId(i);
        try {
            if (this.executor.isFreeToDatabase(i)) {
                return;
            }
            this.executor.postSync(i);
        } finally {
            this.executor.postRemoveFreeId(i);
        }
    }

    void discard(int i) {
        this.executor.removePostWithId(i);
        this.executor.postRemoveInfo(i);
    }
}
