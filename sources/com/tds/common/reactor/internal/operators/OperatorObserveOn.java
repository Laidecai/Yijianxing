package com.tds.common.reactor.internal.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Producer;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.MissingBackpressureException;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.internal.util.atomic.SpscAtomicArrayQueue;
import com.tds.common.reactor.operators.BackpressureUtils;
import com.tds.common.reactor.operators.NotificationLite;
import com.tds.common.reactor.plugins.RxJavaHooks;
import com.tds.common.reactor.schedulers.Scheduler;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public final class OperatorObserveOn<T> implements Observable.Operator<T, T> {
    private final int bufferSize;
    private final boolean delayError;
    private final Scheduler scheduler;

    public OperatorObserveOn(Scheduler scheduler, boolean z) {
        this(scheduler, z, 128);
    }

    public OperatorObserveOn(Scheduler scheduler, boolean z, int i) {
        this.scheduler = scheduler;
        this.delayError = z;
        this.bufferSize = i <= 0 ? 128 : i;
    }

    @Override // com.tds.common.reactor.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        ObserveOnSubscriber observeOnSubscriber = new ObserveOnSubscriber(this.scheduler, subscriber, this.delayError, this.bufferSize);
        observeOnSubscriber.init();
        return observeOnSubscriber;
    }

    static final class ObserveOnSubscriber<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super T> child;
        final boolean delayError;
        long emitted;
        Throwable error;
        volatile boolean finished;
        final int limit;
        final Queue<Object> queue;
        final Scheduler.Worker recursiveScheduler;
        final AtomicLong requested = new AtomicLong();
        final AtomicLong counter = new AtomicLong();

        public ObserveOnSubscriber(Scheduler scheduler, Subscriber<? super T> subscriber, boolean z, int i) {
            this.child = subscriber;
            this.recursiveScheduler = scheduler.createWorker();
            this.delayError = z;
            i = i <= 0 ? 128 : i;
            this.limit = i - (i >> 2);
            this.queue = new SpscAtomicArrayQueue(i);
            request(i);
        }

        void init() {
            Subscriber<? super T> subscriber = this.child;
            subscriber.setProducer(new Producer() { // from class: com.tds.common.reactor.internal.operators.OperatorObserveOn.ObserveOnSubscriber.1
                @Override // com.tds.common.reactor.Producer
                public void request(long j) {
                    if (j > 0) {
                        BackpressureUtils.getAndAddRequest(ObserveOnSubscriber.this.requested, j);
                        ObserveOnSubscriber.this.schedule();
                    }
                }
            });
            subscriber.add(this.recursiveScheduler);
            subscriber.add(this);
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            if (isUnsubscribed() || this.finished) {
                return;
            }
            if (!this.queue.offer(NotificationLite.next(t))) {
                onError(new MissingBackpressureException());
            } else {
                schedule();
            }
        }

        @Override // com.tds.common.reactor.Observer
        public void onCompleted() {
            if (isUnsubscribed() || this.finished) {
                return;
            }
            this.finished = true;
            schedule();
        }

        @Override // com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            if (isUnsubscribed() || this.finished) {
                RxJavaHooks.onError(th);
                return;
            }
            this.error = th;
            this.finished = true;
            schedule();
        }

        protected void schedule() {
            if (this.counter.getAndIncrement() == 0) {
                this.recursiveScheduler.schedule(this);
            }
        }

        @Override // com.tds.common.reactor.functions.Action0
        public void call() {
            long j = this.emitted;
            Queue<Object> queue = this.queue;
            Subscriber<? super T> subscriber = this.child;
            long jAddAndGet = 1;
            do {
                long jProduced = this.requested.get();
                while (jProduced != j) {
                    boolean z = this.finished;
                    Object objPoll = queue.poll();
                    boolean z2 = objPoll == null;
                    if (checkTerminated(z, z2, subscriber, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext((Object) NotificationLite.getValue(objPoll));
                    j++;
                    if (j == this.limit) {
                        jProduced = BackpressureUtils.produced(this.requested, j);
                        request(j);
                        j = 0;
                    }
                }
                if (jProduced == j && checkTerminated(this.finished, queue.isEmpty(), subscriber, queue)) {
                    return;
                }
                this.emitted = j;
                jAddAndGet = this.counter.addAndGet(-jAddAndGet);
            } while (jAddAndGet != 0);
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<? super T> subscriber, Queue<Object> queue) {
            if (subscriber.isUnsubscribed()) {
                queue.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            if (this.delayError) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.error;
                try {
                    if (th != null) {
                        subscriber.onError(th);
                    } else {
                        subscriber.onCompleted();
                    }
                    return false;
                } finally {
                }
            }
            Throwable th2 = this.error;
            if (th2 != null) {
                queue.clear();
                try {
                    subscriber.onError(th2);
                    return true;
                } finally {
                }
            }
            if (!z2) {
                return false;
            }
            try {
                subscriber.onCompleted();
                return true;
            } finally {
            }
        }
    }
}
