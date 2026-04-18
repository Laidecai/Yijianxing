package com.taptap.services.update.download.core.breakpoint;

import android.database.Cursor;

/* JADX INFO: loaded from: classes.dex */
public class BlockInfoRow {
    private final int breakpointId;
    private final long contentLength;
    private final long currentOffset;
    private final long startOffset;

    public BlockInfoRow(Cursor cursor) {
        this.breakpointId = cursor.getInt(cursor.getColumnIndex(BreakpointSQLiteKey.HOST_ID));
        this.startOffset = cursor.getInt(cursor.getColumnIndex(BreakpointSQLiteKey.START_OFFSET));
        this.contentLength = cursor.getInt(cursor.getColumnIndex(BreakpointSQLiteKey.CONTENT_LENGTH));
        this.currentOffset = cursor.getInt(cursor.getColumnIndex(BreakpointSQLiteKey.CURRENT_OFFSET));
    }

    public int getBreakpointId() {
        return this.breakpointId;
    }

    public long getStartOffset() {
        return this.startOffset;
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public long getCurrentOffset() {
        return this.currentOffset;
    }

    public BlockInfo toInfo() {
        return new BlockInfo(this.startOffset, this.contentLength, this.currentOffset);
    }
}
