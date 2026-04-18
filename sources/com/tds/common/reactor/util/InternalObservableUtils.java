package com.tds.common.reactor.util;

import com.tds.common.reactor.exceptions.OnErrorNotImplementedException;
import com.tds.common.reactor.functions.Action1;

/* JADX INFO: loaded from: classes.dex */
public enum InternalObservableUtils {
    ;

    public static final Action1<Throwable> ERROR_NOT_IMPLEMENTED = new Action1<Throwable>() { // from class: com.tds.common.reactor.util.InternalObservableUtils.ErrorNotImplementedAction
        @Override // com.tds.common.reactor.functions.Action1
        public void call(Throwable th) {
            throw new OnErrorNotImplementedException(th);
        }
    };
}
