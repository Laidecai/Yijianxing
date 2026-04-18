package com.tds.common.io;

import android.text.TextUtils;
import com.tds.common.log.Logger;
import com.tds.common.log.TdsBaseException;
import com.tds.common.log.constants.BusinessType;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: classes.dex */
public final class IoUtil {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final Logger LOG = Logger.get(BusinessType.COMMON_LOG);
    private static final String TAG = "IoUtil";

    public static String readString(File file) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            e = e;
            fileInputStream = null;
        } catch (Throwable th) {
            th = th;
            closeQuietly(fileInputStream2);
            throw th;
        }
        try {
            try {
                String string = readString(fileInputStream);
                closeQuietly(fileInputStream);
                return string;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                closeQuietly(fileInputStream2);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            LOG.w(TAG, new TdsBaseException(e));
            closeQuietly(fileInputStream);
            return null;
        }
    }

    public static String readString(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8192];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                return byteArrayOutputStream.toString();
            }
        }
    }

    public static void move(File file, File file2) {
        if (file.renameTo(file2) || !copy(file, file2)) {
            return;
        }
        deleteFile(file);
    }

    public static boolean copy(File file, File file2) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    copy(fileInputStream2, file2);
                    closeQuietly(fileInputStream2);
                    return true;
                } catch (IOException e) {
                    e = e;
                    fileInputStream = fileInputStream2;
                    LOG.w(TAG, new TdsBaseException(e));
                    closeQuietly(fileInputStream);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void copy(ZipFile zipFile, ZipEntry zipEntry, File file) {
        InputStream inputStream = null;
        try {
            try {
                inputStream = zipFile.getInputStream(zipEntry);
                copy(inputStream, file);
            } catch (IOException e) {
                LOG.w(TAG, new TdsBaseException(e));
            }
        } finally {
            closeQuietly(inputStream);
        }
    }

    public static void copy(InputStream inputStream, File file) throws Throwable {
        copy(inputStream, file, false);
    }

    public static void copy(InputStream inputStream, File file, boolean z) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file, z);
            try {
                copy(inputStream, fileOutputStream2);
                closeQuietly(fileOutputStream2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void copy(File file, OutputStream outputStream) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                copy(fileInputStream2, outputStream);
                closeQuietly(fileInputStream2);
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                closeQuietly(fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i <= 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static void writeString(File file, String str) throws Throwable {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(str.getBytes());
                    closeQuietly(fileOutputStream2);
                } catch (Exception e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    LOG.w(TAG, new TdsBaseException(e));
                    closeQuietly(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    closeQuietly(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOG.w(TAG, new TdsBaseException(e));
            }
        }
    }

    public static void closeQuietly(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException e) {
                LOG.w(TAG, new TdsBaseException(e));
            }
        }
    }

    public static boolean mkdirs(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return mkdirs(new File(str));
    }

    public static boolean mkdirs(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return true;
        }
        return file.mkdirs();
    }

    public static boolean deleteDir(File file) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                deleteDir(file2);
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(String str) {
        return deleteFile(new File(str));
    }

    public static boolean deleteFile(File file) {
        if (file.isFile()) {
            return file.delete();
        }
        return true;
    }
}
