package com.taptap.sdk.ui;

import android.text.TextUtils;
import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
class CodeUtil {
    CodeUtil() {
    }

    public static String getCodeChallenge(String str) {
        return getEncodeStr(getSignCode(str));
    }

    private static String getEncodeStr(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return new String(Base64.encode(bArr, 11));
    }

    private static byte[] getSignCode(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCodeVerifier(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~".charAt(random.nextInt(66)));
        }
        return sb.toString();
    }
}
