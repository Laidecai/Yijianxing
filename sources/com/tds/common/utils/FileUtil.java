package com.tds.common.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes.dex */
public class FileUtil {
    public static byte[] returnFileByte(String str) throws IOException {
        FileChannel channel = new FileInputStream(new File(str)).getChannel();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) channel.size());
        channel.read(byteBufferAllocate);
        return byteBufferAllocate.array();
    }

    public static void createFile(byte[] bArr, String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bArr);
            bufferedOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
