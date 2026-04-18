package com.tds.common.reactor.util;

import com.tds.common.reactor.Subscription;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class SubscriptionList implements Subscription {
    private List<Subscription> subscriptions;
    private volatile boolean unsubscribed;

    public SubscriptionList() {
    }

    public SubscriptionList(Subscription... subscriptionArr) {
        this.subscriptions = new LinkedList(Arrays.asList(subscriptionArr));
    }

    public SubscriptionList(Subscription subscription) {
        LinkedList linkedList = new LinkedList();
        this.subscriptions = linkedList;
        linkedList.add(subscription);
    }

    @Override // com.tds.common.reactor.Subscription
    public boolean isUnsubscribed() {
        return this.unsubscribed;
    }

    public void add(Subscription subscription) {
        if (subscription.isUnsubscribed()) {
            return;
        }
        if (!this.unsubscribed) {
            synchronized (this) {
                if (!this.unsubscribed) {
                    List linkedList = this.subscriptions;
                    if (linkedList == null) {
                        linkedList = new LinkedList();
                        this.subscriptions = linkedList;
                    }
                    linkedList.add(subscription);
                    return;
                }
            }
        }
        subscription.unsubscribe();
    }

    public void remove(Subscription subscription) {
        if (this.unsubscribed) {
            return;
        }
        synchronized (this) {
            List<Subscription> list = this.subscriptions;
            if (!this.unsubscribed && list != null) {
                boolean zRemove = list.remove(subscription);
                if (zRemove) {
                    subscription.unsubscribe();
                }
            }
        }
    }

    @Override // com.tds.common.reactor.Subscription
    public void unsubscribe() {
        if (this.unsubscribed) {
            return;
        }
        synchronized (this) {
            if (this.unsubscribed) {
                return;
            }
            this.unsubscribed = true;
            List<Subscription> list = this.subscriptions;
            this.subscriptions = null;
            unsubscribeFromAll(list);
        }
    }

    private static void unsubscribeFromAll(Collection<Subscription> collection) {
        if (collection == null) {
            return;
        }
        ArrayList arrayList = null;
        Iterator<Subscription> it = collection.iterator();
        while (it.hasNext()) {
            try {
                it.next().unsubscribe();
            } catch (Throwable th) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(th);
            }
        }
    }
}
