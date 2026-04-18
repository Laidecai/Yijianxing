package com.taptap.services.update.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static String generateSessionId() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        sb.append(simpleDateFormat.format(new Date()));
        sb.append(generateRandomString());
        return sb.toString();
    }

    public static String generateRandomString() {
        char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        int length = charArray.length;
        for (int i = 0; i < 12; i++) {
            sb.append(charArray[(int) Math.floor(Math.random() * ((double) length))]);
        }
        return sb.toString();
    }
}
