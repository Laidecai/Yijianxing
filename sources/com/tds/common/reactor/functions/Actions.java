package com.tds.common.reactor.functions;

/* JADX INFO: loaded from: classes.dex */
public final class Actions {
    private static final EmptyAction EMPTY_ACTION = new EmptyAction();

    private Actions() {
        throw new IllegalStateException("No instances!");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final class EmptyAction<T0, T1, T2> implements Action0, Action1<T0>, Action2<T0, T1>, Action3<T0, T1, T2> {
        @Override // com.tds.common.reactor.functions.Action0
        public void call() {
        }

        @Override // com.tds.common.reactor.functions.Action1
        public void call(T0 t0) {
        }

        @Override // com.tds.common.reactor.functions.Action2
        public void call(T0 t0, T1 t1) {
        }

        @Override // com.tds.common.reactor.functions.Action3
        public void call(T0 t0, T1 t1, T2 t2) {
        }

        EmptyAction() {
        }
    }

    public static <T0, T1, T2> EmptyAction<T0, T1, T2> empty() {
        return EMPTY_ACTION;
    }
}
