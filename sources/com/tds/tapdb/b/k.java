package com.tds.tapdb.b;

import android.text.TextUtils;
import java.util.Collection;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class k {
    public static <T extends CharSequence> T a(int i, T t, Object obj) {
        a((CharSequence) t, (Object) "string is empty");
        if (t.length() < i) {
            return t;
        }
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    public static <T extends CharSequence> T a(T t) {
        if (TextUtils.isEmpty(t)) {
            throw new IllegalArgumentException();
        }
        return t;
    }

    public static <T extends CharSequence> T a(T t, Object obj) {
        if (TextUtils.isEmpty(t)) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
        return t;
    }

    public static <T extends CharSequence> T a(boolean z, T t, T t2) {
        return !z ? t : t2;
    }

    public static <T> T a(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    public static <T> T a(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static <T> Collection<T> a(Collection<T> collection, String str) {
        if (collection == null) {
            throw new NullPointerException(str + " must not be null");
        }
        if (!collection.isEmpty()) {
            return collection;
        }
        throw new IllegalArgumentException(str + " is empty");
    }

    public static void a(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static void a(boolean z, Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static <T extends CharSequence> boolean a(int i, T t) {
        return TextUtils.isEmpty(t) || t.length() > i;
    }

    public static <T extends CharSequence> T b(T t, Object obj) {
        return (T) a(128, t, obj);
    }

    public static <T extends CharSequence> boolean b(T t) {
        return a(128, t);
    }

    public static <T extends CharSequence> T c(T t, Object obj) {
        return (T) a(256, t, obj);
    }

    public static <T extends CharSequence> boolean c(T t) {
        return a(256, t);
    }

    public static <T extends CharSequence> T d(T t, Object obj) {
        return (T) a(64, t, obj);
    }

    public static <T extends CharSequence> boolean d(T t) {
        return a(64, t);
    }

    public static <T extends CharSequence> T e(T t, Object obj) {
        return (T) a(8, t, obj);
    }

    public static <T extends CharSequence> boolean e(T t) {
        return a(64, t);
    }
}
