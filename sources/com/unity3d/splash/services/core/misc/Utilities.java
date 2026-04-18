package com.unity3d.splash.services.core.misc;

import android.os.Handler;
import android.os.Looper;
import com.taptap.sdk.constant.LoginConstants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import kotlin.UByte;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Utilities {
    public static String Sha256(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bArr = new byte[4096];
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    return toHexString(messageDigest.digest());
                }
                messageDigest.update(bArr, 0, i);
            }
        } catch (NoSuchAlgorithmException e) {
            DeviceLog.exception("SHA-256 algorithm not found", e);
            return null;
        }
    }

    public static String Sha256(String str) {
        return Sha256(str.getBytes());
    }

    public static String Sha256(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr, 0, bArr.length);
            return toHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            DeviceLog.exception("SHA-256 algorithm not found", e);
            return null;
        }
    }

    public static JSONObject mergeJsonObjects(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        if (jSONObject == null) {
            return jSONObject2;
        }
        if (jSONObject2 == null) {
            return jSONObject;
        }
        JSONObject jSONObject3 = new JSONObject();
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            jSONObject3.put(next, jSONObject2.get(next));
        }
        Iterator<String> itKeys2 = jSONObject.keys();
        while (itKeys2.hasNext()) {
            String next2 = itKeys2.next();
            jSONObject3.put(next2, (jSONObject3.has(next2) && (jSONObject3.get(next2) instanceof JSONObject) && (jSONObject.get(next2) instanceof JSONObject)) ? mergeJsonObjects(jSONObject.getJSONObject(next2), jSONObject3.getJSONObject(next2)) : jSONObject.get(next2));
        }
        return jSONObject3;
    }

    public static byte[] readFileBytes(File file) throws IOException {
        if (file == null) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[(int) file.length()];
        int i = 0;
        int length = file.length() < ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_PDF ? (int) file.length() : 4096;
        while (true) {
            int i2 = fileInputStream.read(bArr, i, length);
            if (i2 <= 0) {
                fileInputStream.close();
                return bArr;
            }
            i += i2;
            if (file.length() - ((long) i) < ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_PDF) {
                length = ((int) file.length()) - i;
            }
        }
    }

    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0L);
    }

    public static void runOnUiThread(Runnable runnable, long j) {
        Handler handler = new Handler(Looper.getMainLooper());
        if (j > 0) {
            handler.postDelayed(runnable, j);
        } else {
            handler.post(runnable);
        }
    }

    public static String toHexString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            int i = b & UByte.MAX_VALUE;
            if (i <= 15) {
                str = str + LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
            }
            str = str + Integer.toHexString(i);
        }
        return str;
    }

    public static boolean writeFile(File file, String str) throws Throwable {
        boolean z = false;
        if (file == null) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(str.getBytes());
                    fileOutputStream2.flush();
                    try {
                        fileOutputStream2.close();
                    } catch (Exception e) {
                        DeviceLog.exception("Error closing FileOutputStream", e);
                    }
                    z = true;
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    DeviceLog.exception("Could not write file", e);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e3) {
                            DeviceLog.exception("Error closing FileOutputStream", e3);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e4) {
                            DeviceLog.exception("Error closing FileOutputStream", e4);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e5) {
            e = e5;
        }
        if (z) {
            DeviceLog.debug("Wrote file: " + file.getAbsolutePath());
        }
        return z;
    }
}
