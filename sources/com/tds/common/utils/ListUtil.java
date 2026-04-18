package com.tds.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ListUtil {
    public static <T> byte[] toByteArray(List<T> list) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ObjectOutputStream(byteArrayOutputStream).writeObject(list);
        return byteArrayOutputStream.toByteArray();
    }

    public static <T> List<T> toList(byte[] bArr) throws IOException, ClassNotFoundException {
        return (List) new ObjectInputStream(new ByteArrayInputStream(bArr)).readObject();
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
