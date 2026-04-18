package com.tds.common.websocket.exceptions;

import kotlin.jvm.internal.IntCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class LimitExceededException extends InvalidDataException {
    private static final long serialVersionUID = 6908339749836826785L;
    private final int limit;

    public LimitExceededException() {
        this(IntCompanionObject.MAX_VALUE);
    }

    public LimitExceededException(int i) {
        super(1009);
        this.limit = i;
    }

    public LimitExceededException(String str, int i) {
        super(1009, str);
        this.limit = i;
    }

    public LimitExceededException(String str) {
        this(str, IntCompanionObject.MAX_VALUE);
    }

    public int getLimit() {
        return this.limit;
    }
}
