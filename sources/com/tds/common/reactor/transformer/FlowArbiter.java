package com.tds.common.reactor.transformer;

import com.tds.common.reactor.Producer;
import com.tds.common.reactor.Subscriber;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class FlowArbiter<T> extends AtomicInteger implements Producer {
    private static final int STATE_HAS_RESPONSE = 2;
    private static final int STATE_REQUESTED = 1;
    private static final int STATE_TERMINATED = 3;
    private static final int STATE_WAITING = 0;
    private final FlowCall<T> call;
    private volatile FlowResult<T> flowResult;
    private final Subscriber<? super FlowResult<T>> subscriber;

    public static class FlowResult<T> {
        public final int code;
        public final T data;

        public FlowResult() {
            this(Integer.MIN_VALUE, null);
        }

        public FlowResult(int i, T t) {
            this.code = i;
            this.data = t;
        }
    }

    public FlowArbiter(FlowCall<T> flowCall, Subscriber<? super FlowResult<T>> subscriber) {
        this.subscriber = subscriber;
        this.call = flowCall;
    }

    void emitResponse(FlowResult<T> flowResult) {
        while (true) {
            int i = get();
            if (i == 0) {
                this.flowResult = flowResult;
                if (compareAndSet(0, 1)) {
                    return;
                }
            } else if (i == 2 && compareAndSet(1, 3)) {
                deliverResponse(flowResult);
                return;
            }
        }
    }

    void emitError(Throwable th) {
        set(3);
        try {
            this.subscriber.onError(th);
        } catch (Exception unused) {
        }
    }

    private void deliverResponse(FlowResult<T> flowResult) {
        try {
            this.subscriber.onNext(flowResult);
            this.subscriber.onCompleted();
        } catch (Exception unused) {
        }
    }

    @Override // com.tds.common.reactor.Producer
    public void request(long j) {
        if (j == 0) {
            return;
        }
        while (true) {
            int i = get();
            if (i != 0) {
                if (i == 1) {
                    return;
                }
                if (i != 2) {
                    if (i == 3) {
                        return;
                    }
                    throw new IllegalStateException("Unknown state: " + i);
                }
                if (compareAndSet(2, 3)) {
                    deliverResponse(this.flowResult);
                    return;
                }
            } else if (compareAndSet(0, 1)) {
                return;
            }
        }
    }
}
