package com.tds.common.reactor.subscriptions;

import com.tds.common.reactor.Subscription;

/* JADX INFO: loaded from: classes.dex */
public enum Unsubscribed implements Subscription {
    INSTANCE;

    @Override // com.tds.common.reactor.Subscription
    public boolean isUnsubscribed() {
        return true;
    }

    @Override // com.tds.common.reactor.Subscription
    public void unsubscribe() {
    }
}
