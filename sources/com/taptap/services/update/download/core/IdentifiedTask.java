package com.taptap.services.update.download.core;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public abstract class IdentifiedTask {
    public static final File EMPTY_FILE = new File("");
    public static final String EMPTY_URL = "";

    public abstract String getFilename();

    public abstract int getId();

    public abstract File getParentFile();

    protected abstract File getProvidedPathFile();

    public abstract String getUrl();

    public boolean compareIgnoreId(IdentifiedTask identifiedTask) {
        if (getProvidedPathFile().equals(identifiedTask.getProvidedPathFile())) {
            return true;
        }
        if (!getParentFile().equals(identifiedTask.getParentFile())) {
            return false;
        }
        String filename = getFilename();
        String filename2 = identifiedTask.getFilename();
        return (filename2 == null || filename == null || !filename2.equals(filename)) ? false : true;
    }
}
