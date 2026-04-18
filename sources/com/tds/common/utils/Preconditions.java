package com.tds.common.utils;

import android.text.TextUtils;
import java.util.Collection;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Preconditions {
    public static <T extends CharSequence> T checkStringAndReplace(boolean z, T t, T t2) {
        return !z ? t : t2;
    }

    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean z, Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static <T extends CharSequence> T checkStringNotEmpty(T t) {
        if (TextUtils.isEmpty(t)) {
            throw new IllegalArgumentException();
        }
        return t;
    }

    public static <T extends CharSequence> T checkStringNotEmpty(T t, Object obj) {
        if (TextUtils.isEmpty(t)) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
        return t;
    }

    public static <T extends CharSequence> T checkStringNotExceed256(T t, Object obj) {
        return (T) checkStringNotExceed(256, t, obj);
    }

    public static <T extends CharSequence> T checkStringNotExceed128(T t, Object obj) {
        return (T) checkStringNotExceed(128, t, obj);
    }

    public static <T extends CharSequence> T checkStringNotExceed64(T t, Object obj) {
        return (T) checkStringNotExceed(64, t, obj);
    }

    public static <T extends CharSequence> T checkStringNotExceed8(T t, Object obj) {
        return (T) checkStringNotExceed(8, t, obj);
    }

    public static <T extends CharSequence> T checkStringNotExceed(int i, T t, Object obj) {
        checkStringNotEmpty(t, "string is empty");
        if (t.length() < i) {
            return t;
        }
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    public static <T> T checkNotNull(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    public static <T> T checkNotNull(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static <T> Collection<T> checkCollectionNotEmpty(Collection<T> collection, String str) {
        if (collection == null) {
            throw new NullPointerException(str + " must not be null");
        }
        if (!collection.isEmpty()) {
            return collection;
        }
        throw new IllegalArgumentException(str + " is empty");
    }

    public static <T extends CharSequence> boolean isEmptyOrExceed8(T t) {
        return isEmptyOrExceed(64, t);
    }

    public static <T extends CharSequence> boolean isEmptyOrExceed64(T t) {
        return isEmptyOrExceed(64, t);
    }

    public static <T extends CharSequence> boolean isEmptyOrExceed128(T t) {
        return isEmptyOrExceed(128, t);
    }

    public static <T extends CharSequence> boolean isEmptyOrExceed256(T t) {
        return isEmptyOrExceed(256, t);
    }

    public static <T extends CharSequence> boolean isEmptyOrExceed(int i, T t) {
        return TextUtils.isEmpty(t) || t.length() > i;
    }
}
