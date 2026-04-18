package com.tds.common.net.json;

import java.lang.reflect.ParameterizedType;

/* JADX INFO: loaded from: classes.dex */
public class TypeRef<T> {
    public final ParameterizedType type = (ParameterizedType) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected TypeRef() {
    }
}
