package com.taptap.services.update.download.core.breakpoint;

import com.taptap.services.update.download.DownloadTask;
import com.taptap.services.update.download.core.Util;
import com.taptap.services.update.download.core.download.DownloadStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BreakpointInfo {
    private final List<BlockInfo> blockInfoList = new ArrayList();
    private boolean chunked;
    private String etag;
    private final DownloadStrategy.FilenameHolder filenameHolder;
    final int id;
    final File parentFile;
    private File targetFile;
    private final boolean taskOnlyProvidedParentPath;
    private String url;

    public BreakpointInfo(int i, String str, File file, String str2) {
        this.id = i;
        this.url = str;
        this.parentFile = file;
        if (Util.isEmpty(str2)) {
            this.filenameHolder = new DownloadStrategy.FilenameHolder();
            this.taskOnlyProvidedParentPath = true;
        } else {
            this.filenameHolder = new DownloadStrategy.FilenameHolder(str2);
            this.taskOnlyProvidedParentPath = false;
            this.targetFile = new File(file, str2);
        }
    }

    BreakpointInfo(int i, String str, File file, String str2, boolean z) {
        this.id = i;
        this.url = str;
        this.parentFile = file;
        if (Util.isEmpty(str2)) {
            this.filenameHolder = new DownloadStrategy.FilenameHolder();
        } else {
            this.filenameHolder = new DownloadStrategy.FilenameHolder(str2);
        }
        this.taskOnlyProvidedParentPath = z;
    }

    public int getId() {
        return this.id;
    }

    public void setChunked(boolean z) {
        this.chunked = z;
    }

    public void addBlock(BlockInfo blockInfo) {
        this.blockInfoList.add(blockInfo);
    }

    public boolean isChunked() {
        return this.chunked;
    }

    public boolean isLastBlock(int i) {
        return i == this.blockInfoList.size() - 1;
    }

    public boolean isSingleBlock() {
        return this.blockInfoList.size() == 1;
    }

    boolean isTaskOnlyProvidedParentPath() {
        return this.taskOnlyProvidedParentPath;
    }

    public BlockInfo getBlock(int i) {
        return this.blockInfoList.get(i);
    }

    public void resetInfo() {
        this.blockInfoList.clear();
        this.etag = null;
    }

    public void resetBlockInfos() {
        this.blockInfoList.clear();
    }

    public int getBlockCount() {
        return this.blockInfoList.size();
    }

    public void setEtag(String str) {
        this.etag = str;
    }

    public long getTotalOffset() {
        Object[] array = this.blockInfoList.toArray();
        long currentOffset = 0;
        if (array != null) {
            for (Object obj : array) {
                if (obj instanceof BlockInfo) {
                    currentOffset += ((BlockInfo) obj).getCurrentOffset();
                }
            }
        }
        return currentOffset;
    }

    public long getTotalLength() {
        if (isChunked()) {
            return getTotalOffset();
        }
        long contentLength = 0;
        Object[] array = this.blockInfoList.toArray();
        if (array != null) {
            for (Object obj : array) {
                if (obj instanceof BlockInfo) {
                    contentLength += ((BlockInfo) obj).getContentLength();
                }
            }
        }
        return contentLength;
    }

    public String getEtag() {
        return this.etag;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getFilename() {
        return this.filenameHolder.get();
    }

    public DownloadStrategy.FilenameHolder getFilenameHolder() {
        return this.filenameHolder;
    }

    public File getFile() {
        String str = this.filenameHolder.get();
        if (str == null) {
            return null;
        }
        if (this.targetFile == null) {
            this.targetFile = new File(this.parentFile, str);
        }
        return this.targetFile;
    }

    public BreakpointInfo copy() {
        BreakpointInfo breakpointInfo = new BreakpointInfo(this.id, this.url, this.parentFile, this.filenameHolder.get(), this.taskOnlyProvidedParentPath);
        breakpointInfo.chunked = this.chunked;
        Iterator<BlockInfo> it = this.blockInfoList.iterator();
        while (it.hasNext()) {
            breakpointInfo.blockInfoList.add(it.next().copy());
        }
        return breakpointInfo;
    }

    public BreakpointInfo copyWithReplaceId(int i) {
        BreakpointInfo breakpointInfo = new BreakpointInfo(i, this.url, this.parentFile, this.filenameHolder.get(), this.taskOnlyProvidedParentPath);
        breakpointInfo.chunked = this.chunked;
        Iterator<BlockInfo> it = this.blockInfoList.iterator();
        while (it.hasNext()) {
            breakpointInfo.blockInfoList.add(it.next().copy());
        }
        return breakpointInfo;
    }

    public void reuseBlocks(BreakpointInfo breakpointInfo) {
        this.blockInfoList.clear();
        this.blockInfoList.addAll(breakpointInfo.blockInfoList);
    }

    public BreakpointInfo copyWithReplaceIdAndUrl(int i, String str) {
        BreakpointInfo breakpointInfo = new BreakpointInfo(i, str, this.parentFile, this.filenameHolder.get(), this.taskOnlyProvidedParentPath);
        breakpointInfo.chunked = this.chunked;
        Iterator<BlockInfo> it = this.blockInfoList.iterator();
        while (it.hasNext()) {
            breakpointInfo.blockInfoList.add(it.next().copy());
        }
        return breakpointInfo;
    }

    public boolean isSameFrom(DownloadTask downloadTask) {
        if (!this.parentFile.equals(downloadTask.getParentFile())) {
            return false;
        }
        String filename = downloadTask.getFilename();
        if (filename != null && filename.equals(this.filenameHolder.get())) {
            return true;
        }
        if (this.url.equals(downloadTask.getUrl()) && this.taskOnlyProvidedParentPath && downloadTask.isFilenameFromResponse()) {
            return filename == null || filename.equals(this.filenameHolder.get());
        }
        return false;
    }

    public String toString() {
        return "id[" + this.id + "] url[" + this.url + "] etag[" + this.etag + "] taskOnlyProvidedParentPath[" + this.taskOnlyProvidedParentPath + "] parent path[" + this.parentFile + "] filename[" + this.filenameHolder.get() + "] block(s):" + this.blockInfoList.toString();
    }
}
