package com.tds.common.reactor.subscriptions;

import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.functions.Action0;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class BooleanSubscription implements Subscription {
    static final Action0 EMPTY_ACTION = new Action0() { // from class: com.tds.common.reactor.subscriptions.BooleanSubscription.1
        @Override // com.tds.common.reactor.functions.Action0
        public void call() {
        }
    };
    final AtomicReference<Action0> actionRef;

    public BooleanSubscription() {
        this.actionRef = new AtomicReference<>();
    }

    private BooleanSubscription(Action0 action0) {
        this.actionRef = new AtomicReference<>(action0);
    }

    public static BooleanSubscription create() {
        return new BooleanSubscription();
    }

    public static BooleanSubscription create(Action0 action0) {
        return new BooleanSubscription(action0);
    }

    @Override // com.tds.common.reactor.Subscription
    public boolean isUnsubscribed() {
        return this.actionRef.get() == EMPTY_ACTION;
    }

    @Override // com.tds.common.reactor.Subscription
    public void unsubscribe() {
        Action0 andSet;
        Action0 action0 = this.actionRef.get();
        Action0 action02 = EMPTY_ACTION;
        if (action0 == action02 || (andSet = this.actionRef.getAndSet(action02)) == null || andSet == action02) {
            return;
        }
        andSet.call();
    }
}
