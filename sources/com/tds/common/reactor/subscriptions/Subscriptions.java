package com.tds.common.reactor.subscriptions;

import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.functions.Action0;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public final class Subscriptions {
    private static final Unsubscribed UNSUBSCRIBED = new Unsubscribed();

    private Subscriptions() {
        throw new IllegalStateException("No instances!");
    }

    public static Subscription empty() {
        return BooleanSubscription.create();
    }

    public static Subscription unsubscribed() {
        return UNSUBSCRIBED;
    }

    public static Subscription create(Action0 action0) {
        return BooleanSubscription.create(action0);
    }

    public static Subscription from(Future<?> future) {
        return new FutureSubscription(future);
    }

    static final class FutureSubscription implements Subscription {
        final Future<?> f;

        public FutureSubscription(Future<?> future) {
            this.f = future;
        }

        @Override // com.tds.common.reactor.Subscription
        public void unsubscribe() {
            this.f.cancel(true);
        }

        @Override // com.tds.common.reactor.Subscription
        public boolean isUnsubscribed() {
            return this.f.isCancelled();
        }
    }

    public static CompositeSubscription from(Subscription... subscriptionArr) {
        return new CompositeSubscription(subscriptionArr);
    }

    static final class Unsubscribed implements Subscription {
        @Override // com.tds.common.reactor.Subscription
        public boolean isUnsubscribed() {
            return true;
        }

        @Override // com.tds.common.reactor.Subscription
        public void unsubscribe() {
        }

        Unsubscribed() {
        }
    }
}
