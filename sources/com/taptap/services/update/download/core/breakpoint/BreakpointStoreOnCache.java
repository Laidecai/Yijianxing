package com.taptap.services.update.download.core.breakpoint;

import android.util.SparseArray;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.IdentifiedTask;
import com.taptap.services.update.download.core.cause.EndCause;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BreakpointStoreOnCache implements DownloadStore {
    public static final int FIRST_ID = 1;
    private final List<Integer> fileDirtyList;
    private final KeyToIdMap keyToIdMap;
    private final HashMap<String, String> responseFilenameMap;
    private final List<Integer> sortedOccupiedIds;
    private final SparseArray<BreakpointInfo> storedInfos;
    private final SparseArray<IdentifiedTask> unStoredTasks;

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public BreakpointInfo getAfterCompleted(int i) {
        return null;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public boolean isOnlyMemoryCache() {
        return true;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public void onTaskStart(int i) {
    }

    public BreakpointStoreOnCache() {
        this(new SparseArray(), new ArrayList(), new HashMap());
    }

    BreakpointStoreOnCache(SparseArray<BreakpointInfo> sparseArray, List<Integer> list, HashMap<String, String> map, SparseArray<IdentifiedTask> sparseArray2, List<Integer> list2, KeyToIdMap keyToIdMap) {
        this.unStoredTasks = sparseArray2;
        this.fileDirtyList = list;
        this.storedInfos = sparseArray;
        this.responseFilenameMap = map;
        this.sortedOccupiedIds = list2;
        this.keyToIdMap = keyToIdMap;
    }

    public BreakpointStoreOnCache(SparseArray<BreakpointInfo> sparseArray, List<Integer> list, HashMap<String, String> map) {
        this.unStoredTasks = new SparseArray<>();
        this.storedInfos = sparseArray;
        this.fileDirtyList = list;
        this.responseFilenameMap = map;
        this.keyToIdMap = new KeyToIdMap();
        int size = sparseArray.size();
        this.sortedOccupiedIds = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            this.sortedOccupiedIds.add(Integer.valueOf(sparseArray.valueAt(i).id));
        }
        Collections.sort(this.sortedOccupiedIds);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public BreakpointInfo get(int i) {
        return this.storedInfos.get(i);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public BreakpointInfo createAndInsert(DownloadTask downloadTask) {
        int id = downloadTask.getId();
        BreakpointInfo breakpointInfo = new BreakpointInfo(id, downloadTask.getUrl(), downloadTask.getParentFile(), downloadTask.getFilename());
        synchronized (this) {
            this.storedInfos.put(id, breakpointInfo);
            this.unStoredTasks.remove(id);
        }
        return breakpointInfo;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public void onSyncToFilesystemSuccess(BreakpointInfo breakpointInfo, int i, long j) throws IOException {
        BreakpointInfo breakpointInfo2 = this.storedInfos.get(breakpointInfo.id);
        if (breakpointInfo != breakpointInfo2) {
            throw new IOException("Info not on store!");
        }
        breakpointInfo2.getBlock(i).increaseCurrentOffset(j);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public boolean update(BreakpointInfo breakpointInfo) {
        String filename = breakpointInfo.getFilename();
        if (breakpointInfo.isTaskOnlyProvidedParentPath() && filename != null) {
            this.responseFilenameMap.put(breakpointInfo.getUrl(), filename);
        }
        BreakpointInfo breakpointInfo2 = this.storedInfos.get(breakpointInfo.id);
        if (breakpointInfo2 == null) {
            return false;
        }
        if (breakpointInfo2 == breakpointInfo) {
            return true;
        }
        synchronized (this) {
            this.storedInfos.put(breakpointInfo.id, breakpointInfo.copy());
        }
        return true;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public void onTaskEnd(int i, EndCause endCause, Exception exc) {
        if (endCause == EndCause.COMPLETED) {
            remove(i);
        }
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public boolean markFileDirty(int i) {
        if (this.fileDirtyList.contains(Integer.valueOf(i))) {
            return false;
        }
        synchronized (this.fileDirtyList) {
            if (this.fileDirtyList.contains(Integer.valueOf(i))) {
                return false;
            }
            this.fileDirtyList.add(Integer.valueOf(i));
            return true;
        }
    }

    @Override // com.taptap.services.update.download.core.breakpoint.DownloadStore
    public boolean markFileClear(int i) {
        boolean zRemove;
        synchronized (this.fileDirtyList) {
            zRemove = this.fileDirtyList.remove(Integer.valueOf(i));
        }
        return zRemove;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public synchronized void remove(int i) {
        this.storedInfos.remove(i);
        if (this.unStoredTasks.get(i) == null) {
            this.sortedOccupiedIds.remove(Integer.valueOf(i));
        }
        this.keyToIdMap.remove(i);
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public synchronized int findOrCreateId(DownloadTask downloadTask) {
        Integer num = this.keyToIdMap.get(downloadTask);
        if (num != null) {
            return num.intValue();
        }
        int size = this.storedInfos.size();
        for (int i = 0; i < size; i++) {
            BreakpointInfo breakpointInfoValueAt = this.storedInfos.valueAt(i);
            if (breakpointInfoValueAt != null && breakpointInfoValueAt.isSameFrom(downloadTask)) {
                return breakpointInfoValueAt.id;
            }
        }
        int size2 = this.unStoredTasks.size();
        for (int i2 = 0; i2 < size2; i2++) {
            IdentifiedTask identifiedTaskValueAt = this.unStoredTasks.valueAt(i2);
            if (identifiedTaskValueAt != null && identifiedTaskValueAt.compareIgnoreId(downloadTask)) {
                return identifiedTaskValueAt.getId();
            }
        }
        int iAllocateId = allocateId();
        this.unStoredTasks.put(iAllocateId, downloadTask.mock(iAllocateId));
        this.keyToIdMap.add(downloadTask, iAllocateId);
        return iAllocateId;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public BreakpointInfo findAnotherInfoFromCompare(DownloadTask downloadTask, BreakpointInfo breakpointInfo) {
        SparseArray<BreakpointInfo> sparseArrayClone;
        synchronized (this) {
            sparseArrayClone = this.storedInfos.clone();
        }
        int size = sparseArrayClone.size();
        for (int i = 0; i < size; i++) {
            BreakpointInfo breakpointInfoValueAt = sparseArrayClone.valueAt(i);
            if (breakpointInfoValueAt != breakpointInfo && breakpointInfoValueAt.isSameFrom(downloadTask)) {
                return breakpointInfoValueAt;
            }
        }
        return null;
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public boolean isFileDirty(int i) {
        return this.fileDirtyList.contains(Integer.valueOf(i));
    }

    @Override // com.taptap.services.update.download.core.breakpoint.BreakpointStore
    public String getResponseFilename(String str) {
        return this.responseFilenameMap.get(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x002e, code lost:
    
        r1 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    synchronized int allocateId() {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 0
            r2 = 0
        L4:
            java.util.List<java.lang.Integer> r3 = r5.sortedOccupiedIds     // Catch: java.lang.Throwable -> L5f
            int r3 = r3.size()     // Catch: java.lang.Throwable -> L5f
            r4 = 1
            if (r1 >= r3) goto L2e
            java.util.List<java.lang.Integer> r3 = r5.sortedOccupiedIds     // Catch: java.lang.Throwable -> L5f
            java.lang.Object r3 = r3.get(r1)     // Catch: java.lang.Throwable -> L5f
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: java.lang.Throwable -> L5f
            if (r3 != 0) goto L1a
            int r0 = r2 + 1
            goto L2f
        L1a:
            int r3 = r3.intValue()     // Catch: java.lang.Throwable -> L5f
            if (r2 != 0) goto L24
            if (r3 == r4) goto L2a
            r0 = 1
            goto L2e
        L24:
            int r2 = r2 + 1
            if (r3 == r2) goto L2a
            r0 = r2
            goto L2f
        L2a:
            int r1 = r1 + 1
            r2 = r3
            goto L4
        L2e:
            r1 = 0
        L2f:
            if (r0 != 0) goto L53
            java.util.List<java.lang.Integer> r0 = r5.sortedOccupiedIds     // Catch: java.lang.Throwable -> L5f
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L5f
            if (r0 == 0) goto L3a
            goto L54
        L3a:
            java.util.List<java.lang.Integer> r0 = r5.sortedOccupiedIds     // Catch: java.lang.Throwable -> L5f
            int r1 = r0.size()     // Catch: java.lang.Throwable -> L5f
            int r1 = r1 - r4
            java.lang.Object r0 = r0.get(r1)     // Catch: java.lang.Throwable -> L5f
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch: java.lang.Throwable -> L5f
            int r0 = r0.intValue()     // Catch: java.lang.Throwable -> L5f
            int r4 = r4 + r0
            java.util.List<java.lang.Integer> r0 = r5.sortedOccupiedIds     // Catch: java.lang.Throwable -> L5f
            int r1 = r0.size()     // Catch: java.lang.Throwable -> L5f
            goto L54
        L53:
            r4 = r0
        L54:
            java.util.List<java.lang.Integer> r0 = r5.sortedOccupiedIds     // Catch: java.lang.Throwable -> L5f
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L5f
            r0.add(r1, r2)     // Catch: java.lang.Throwable -> L5f
            monitor-exit(r5)
            return r4
        L5f:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taptap.services.update.download.core.breakpoint.BreakpointStoreOnCache.allocateId():int");
    }
}
