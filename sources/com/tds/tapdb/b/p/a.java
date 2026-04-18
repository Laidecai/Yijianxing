package com.tds.tapdb.b.p;

import android.util.Log;
import com.tds.tapdb.service.TapTapDIDIntentService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static void a(byte[] bArr, String str) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bArr);
            bufferedOutputStream.close();
            fileOutputStream.close();
            Log.d(TapTapDIDIntentService.i, "save taptapdid to sdcard success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean a(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static byte[] b(String str) throws IOException {
        FileChannel channel = new FileInputStream(new File(str)).getChannel();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) channel.size());
        channel.read(byteBufferAllocate);
        return byteBufferAllocate.array();
    }
}
