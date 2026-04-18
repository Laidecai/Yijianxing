package com.tds.common.reactor.internal.observers;

import com.tds.common.reactor.Observer;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.operators.NotificationLite;

/* JADX INFO: loaded from: classes.dex */
public class SerializedObserver<T> implements Observer<T> {
    private final Observer<? super T> actual;
    private boolean emitting;
    private FastList queue;
    private volatile boolean terminated;

    static final class FastList {
        Object[] array;
        int size;

        FastList() {
        }

        public void add(Object obj) {
            int i = this.size;
            Object[] objArr = this.array;
            if (objArr == null) {
                objArr = new Object[16];
                this.array = objArr;
            } else if (i == objArr.length) {
                Object[] objArr2 = new Object[(i >> 2) + i];
                System.arraycopy(objArr, 0, objArr2, 0, i);
                this.array = objArr2;
                objArr = objArr2;
            }
            objArr[i] = obj;
            this.size = i + 1;
        }
    }

    public SerializedObserver(Observer<? super T> observer) {
        this.actual = observer;
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x002d, code lost:
    
        continue;
     */
    @Override // com.tds.common.reactor.Observer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onNext(T r7) {
        /*
            r6 = this;
            boolean r0 = r6.terminated
            if (r0 == 0) goto L5
            return
        L5:
            monitor-enter(r6)
            boolean r0 = r6.terminated     // Catch: java.lang.Throwable -> L6f
            if (r0 == 0) goto Lc
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L6f
            return
        Lc:
            boolean r0 = r6.emitting     // Catch: java.lang.Throwable -> L6f
            if (r0 == 0) goto L24
            com.tds.common.reactor.internal.observers.SerializedObserver$FastList r0 = r6.queue     // Catch: java.lang.Throwable -> L6f
            if (r0 != 0) goto L1b
            com.tds.common.reactor.internal.observers.SerializedObserver$FastList r0 = new com.tds.common.reactor.internal.observers.SerializedObserver$FastList     // Catch: java.lang.Throwable -> L6f
            r0.<init>()     // Catch: java.lang.Throwable -> L6f
            r6.queue = r0     // Catch: java.lang.Throwable -> L6f
        L1b:
            java.lang.Object r7 = com.tds.common.reactor.operators.NotificationLite.next(r7)     // Catch: java.lang.Throwable -> L6f
            r0.add(r7)     // Catch: java.lang.Throwable -> L6f
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L6f
            return
        L24:
            r0 = 1
            r6.emitting = r0     // Catch: java.lang.Throwable -> L6f
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L6f
            com.tds.common.reactor.Observer<? super T> r1 = r6.actual     // Catch: java.lang.Throwable -> L66
            r1.onNext(r7)     // Catch: java.lang.Throwable -> L66
        L2d:
            monitor-enter(r6)
            com.tds.common.reactor.internal.observers.SerializedObserver$FastList r1 = r6.queue     // Catch: java.lang.Throwable -> L63
            r2 = 0
            if (r1 != 0) goto L37
            r6.emitting = r2     // Catch: java.lang.Throwable -> L63
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L63
            return
        L37:
            r3 = 0
            r6.queue = r3     // Catch: java.lang.Throwable -> L63
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L63
            java.lang.Object[] r1 = r1.array
            int r3 = r1.length
        L3e:
            if (r2 >= r3) goto L2d
            r4 = r1[r2]
            if (r4 != 0) goto L45
            goto L2d
        L45:
            com.tds.common.reactor.Observer<? super T> r5 = r6.actual     // Catch: java.lang.Throwable -> L53
            boolean r4 = com.tds.common.reactor.operators.NotificationLite.accept(r5, r4)     // Catch: java.lang.Throwable -> L53
            if (r4 == 0) goto L50
            r6.terminated = r0     // Catch: java.lang.Throwable -> L53
            return
        L50:
            int r2 = r2 + 1
            goto L3e
        L53:
            r1 = move-exception
            r6.terminated = r0
            com.tds.common.reactor.exceptions.Exceptions.throwIfFatal(r1)
            com.tds.common.reactor.Observer<? super T> r0 = r6.actual
            java.lang.Throwable r7 = com.tds.common.reactor.exceptions.OnErrorThrowable.addValueAsLastCause(r1, r7)
            r0.onError(r7)
            return
        L63:
            r7 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L63
            throw r7
        L66:
            r1 = move-exception
            r6.terminated = r0
            com.tds.common.reactor.Observer<? super T> r0 = r6.actual
            com.tds.common.reactor.exceptions.Exceptions.throwOrReport(r1, r0, r7)
            return
        L6f:
            r7 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L6f
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.reactor.internal.observers.SerializedObserver.onNext(java.lang.Object):void");
    }

    @Override // com.tds.common.reactor.Observer
    public void onError(Throwable th) {
        Exceptions.throwIfFatal(th);
        if (this.terminated) {
            return;
        }
        synchronized (this) {
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            if (this.emitting) {
                FastList fastList = this.queue;
                if (fastList == null) {
                    fastList = new FastList();
                    this.queue = fastList;
                }
                fastList.add(NotificationLite.error(th));
                return;
            }
            this.emitting = true;
            this.actual.onError(th);
        }
    }

    @Override // com.tds.common.reactor.Observer
    public void onCompleted() {
        if (this.terminated) {
            return;
        }
        synchronized (this) {
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            if (this.emitting) {
                FastList fastList = this.queue;
                if (fastList == null) {
                    fastList = new FastList();
                    this.queue = fastList;
                }
                fastList.add(NotificationLite.completed());
                return;
            }
            this.emitting = true;
            this.actual.onCompleted();
        }
    }
}
