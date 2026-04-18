package com.tds.common.reactor.subscriptions;

import com.tds.common.reactor.Subscription;

/* JADX INFO: loaded from: classes.dex */
public final class SerialSubscription implements Subscription {
    final SequentialSubscription state = new SequentialSubscription();

    @Override // com.tds.common.reactor.Subscription
    public boolean isUnsubscribed() {
        return this.state.isUnsubscribed();
    }

    @Override // com.tds.common.reactor.Subscription
    public void unsubscribe() {
        this.state.unsubscribe();
    }

    public void set(Subscription subscription) {
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription can not be null");
        }
        this.state.update(subscription);
    }

    public Subscription get() {
        return this.state.current();
    }
}
