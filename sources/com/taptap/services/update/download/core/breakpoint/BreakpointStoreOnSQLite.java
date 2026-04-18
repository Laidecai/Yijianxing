package com.taptap.services.update.download.core.breakpoint;

import android.content.Context;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.Util;
import com.taptap.services.update.download.core.cause.EndCause;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class BreakpointStoreOnSQLite implements DownloadStore {
    private static final String TAG = "BreakpointStoreOnSQLite";
    protected final BreakpointSQLiteHelper helper;
    protected final BreakpointStoreOnCache onCache;

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public BreakpointInfo getAfterCompleted(int i) {
        return null;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public boolean isOnlyMemoryCache() {
        return false;
    }

    BreakpointStoreOnSQLite(BreakpointSQLiteHelper breakpointSQLiteHelper, BreakpointStoreOnCache breakpointStoreOnCache) {
        this.helper = breakpointSQLiteHelper;
        this.onCache = breakpointStoreOnCache;
    }

    public BreakpointStoreOnSQLite(Context context) {
        BreakpointSQLiteHelper breakpointSQLiteHelper = new BreakpointSQLiteHelper(context.getApplicationContext());
        this.helper = breakpointSQLiteHelper;
        this.onCache = new BreakpointStoreOnCache(breakpointSQLiteHelper.loadToCache(), breakpointSQLiteHelper.loadDirtyFileList(), breakpointSQLiteHelper.loadResponseFilenameToMap());
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public BreakpointInfo get(int i) {
        return this.onCache.get(i);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public BreakpointInfo createAndInsert(DownloadTask downloadTask) throws IOException {
        BreakpointInfo breakpointInfoCreateAndInsert = this.onCache.createAndInsert(downloadTask);
        this.helper.insert(breakpointInfoCreateAndInsert);
        return breakpointInfoCreateAndInsert;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public void onTaskStart(int i) {
        this.onCache.onTaskStart(i);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public void onSyncToFilesystemSuccess(BreakpointInfo breakpointInfo, int i, long j) throws IOException {
        this.onCache.onSyncToFilesystemSuccess(breakpointInfo, i, j);
        this.helper.updateBlockIncrease(breakpointInfo, i, breakpointInfo.getBlock(i).getCurrentOffset());
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public boolean update(BreakpointInfo breakpointInfo) throws IOException {
        boolean zUpdate = this.onCache.update(breakpointInfo);
        this.helper.updateInfo(breakpointInfo);
        String filename = breakpointInfo.getFilename();
        Util.d(TAG, "update " + breakpointInfo);
        if (breakpointInfo.isTaskOnlyProvidedParentPath() && filename != null) {
            this.helper.updateFilename(breakpointInfo.getUrl(), filename);
        }
        return zUpdate;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public void onTaskEnd(int i, EndCause endCause, Exception exc) {
        this.onCache.onTaskEnd(i, endCause, exc);
        if (endCause == EndCause.COMPLETED) {
            this.helper.removeInfo(i);
        }
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public boolean markFileDirty(int i) {
        if (!this.onCache.markFileDirty(i)) {
            return false;
        }
        this.helper.markFileDirty(i);
        return true;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public boolean markFileClear(int i) {
        if (!this.onCache.markFileClear(i)) {
            return false;
        }
        this.helper.markFileClear(i);
        return true;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public void remove(int i) {
        this.onCache.remove(i);
        this.helper.removeInfo(i);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public int findOrCreateId(DownloadTask downloadTask) {
        int iFindOrCreateId = this.onCache.findOrCreateId(downloadTask);
        try {
            BreakpointInfo breakpointInfo = this.onCache.get(iFindOrCreateId);
            if (breakpointInfo != null && !breakpointInfo.getUrl().equals(downloadTask.getUrl())) {
                breakpointInfo.setUrl(downloadTask.getUrl());
                update(breakpointInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iFindOrCreateId;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public BreakpointInfo findAnotherInfoFromCompare(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
        return this.onCache.findAnotherInfoFromCompare(downloadTask, breakpointInfo);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public boolean isFileDirty(int i) {
        return this.onCache.isFileDirty(i);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public String getResponseFilename(String str) {
        return this.onCache.getResponseFilename(str);
    }

    void close() {
        this.helper.close();
    }

    public DownloadStore createRemitSelf() {
        return new RemitStoreOnSQLite(this);
    }
}
