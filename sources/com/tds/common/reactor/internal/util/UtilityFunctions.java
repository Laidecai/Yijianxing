package com.tds.common.reactor.internal.util;

import com.tds.common.reactor.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public final class UtilityFunctions {

    enum Identity implements Func1<Object, Object> {
        INSTANCE;

        @Override // com.tds.common.reactor.functions.Func1
        public Object call(Object obj) {
            return obj;
        }
    }

    private UtilityFunctions() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Func1<? super T, Boolean> alwaysTrue() {
        return AlwaysTrue.INSTANCE;
    }

    public static <T> Func1<? super T, Boolean> alwaysFalse() {
        return AlwaysFalse.INSTANCE;
    }

    public static <T> Func1<T, T> identity() {
        return Identity.INSTANCE;
    }

    enum AlwaysTrue implements Func1<Object, Boolean> {
        INSTANCE;

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.tds.common.reactor.functions.Func1
        public Boolean call(Object obj) {
            return true;
        }
    }

    enum AlwaysFalse implements Func1<Object, Boolean> {
        INSTANCE;

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.tds.common.reactor.functions.Func1
        public Boolean call(Object obj) {
            return false;
        }
    }
}
