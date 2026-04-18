package com.taptap.services.update.download.core.download;

import android.net.Uri;
import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.OkDownload;
import com.taptap.services.update.download.core.Util;
import com.taptap.services.update.download.core.breakpoint.BreakpointInfo;
import com.taptap.services.update.download.core.cause.ResumeFailedCause;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class BreakpointLocalCheck {
    private boolean dirty;
    boolean fileExist;
    private final BreakpointInfo info;
    boolean infoRight;
    boolean outputStreamSupport;
    private final long responseInstanceLength;
    private final DownloadTask task;

    public BreakpointLocalCheck(DownloadTask downloadTask, BreakpointInfo breakpointInfo, long j) {
        this.task = downloadTask;
        this.info = breakpointInfo;
        this.responseInstanceLength = j;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public ResumeFailedCause getCauseOrThrow() {
        if (!this.infoRight) {
            return ResumeFailedCause.INFO_DIRTY;
        }
        if (!this.fileExist) {
            return ResumeFailedCause.FILE_NOT_EXIST;
        }
        if (!this.outputStreamSupport) {
            return ResumeFailedCause.OUTPUT_STREAM_NOT_SUPPORT;
        }
        throw new IllegalStateException("No cause find with dirty: " + this.dirty);
    }

    public boolean isInfoRightToResume() {
        int blockCount = this.info.getBlockCount();
        if (blockCount <= 0 || this.info.isChunked() || this.info.getFile() == null) {
            return false;
        }
        if (!this.info.getFile().equals(this.task.getFile()) || this.info.getFile().length() > this.info.getTotalLength()) {
            return false;
        }
        if (this.responseInstanceLength > 0 && this.info.getTotalLength() != this.responseInstanceLength) {
            return false;
        }
        for (int i = 0; i < blockCount; i++) {
            if (this.info.getBlock(i).getContentLength() <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isOutputStreamSupportResume() {
        if (OkDownload.with().outputStreamFactory().supportSeek()) {
            return true;
        }
        return this.info.getBlockCount() == 1 && !OkDownload.with().processFileStrategy().isPreAllocateLength(this.task);
    }

    public boolean isFileExistToResume() {
        Uri uri = this.task.getUri();
        if (Util.isUriContentScheme(uri)) {
            return Util.getSizeFromContentUri(uri) > 0;
        }
        File file = this.task.getFile();
        return file != null && file.exists();
    }

    public void check() {
        this.fileExist = isFileExistToResume();
        this.infoRight = isInfoRightToResume();
        boolean zIsOutputStreamSupportResume = isOutputStreamSupportResume();
        this.outputStreamSupport = zIsOutputStreamSupportResume;
        this.dirty = (this.infoRight && this.fileExist && zIsOutputStreamSupportResume) ? false : true;
    }

    public String toString() {
        return "fileExist[" + this.fileExist + "] infoRight[" + this.infoRight + "] outputStreamSupport[" + this.outputStreamSupport + "] " + super.toString();
    }
}
