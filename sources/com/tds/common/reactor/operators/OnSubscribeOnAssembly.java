package com.tds.common.reactor.operators;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import com.tds.common.reactor.exceptions.AssemblyStackTraceException;

/* JADX INFO: loaded from: classes.dex */
public final class OnSubscribeOnAssembly<T> implements Observable.OnSubscribe<T> {
    public static volatile boolean fullStackTrace;
    final Observable.OnSubscribe<T> source;
    final String stacktrace = createStacktrace();

    public OnSubscribeOnAssembly(Observable.OnSubscribe<T> onSubscribe) {
        this.source = onSubscribe;
    }

    static String createStacktrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder("Assembly trace:");
        for (StackTraceElement stackTraceElement : stackTrace) {
            String string = stackTraceElement.toString();
            if (fullStackTrace || (stackTraceElement.getLineNumber() > 1 && !string.contains("RxJavaHooks.") && !string.contains("OnSubscribeOnAssembly") && !string.contains(".junit.runner") && !string.contains(".junit4.runner") && !string.contains(".junit.internal") && !string.contains("sun.reflect") && !string.contains("java.lang.Thread.") && !string.contains("ThreadPoolExecutor") && !string.contains("org.apache.catalina.") && !string.contains("org.apache.tomcat."))) {
                sb.append("\n at ");
                sb.append(string);
            }
        }
        sb.append("\nOriginal exception:");
        return sb.toString();
    }

    @Override // com.tds.common.reactor.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        this.source.call((T) new OnAssemblySubscriber(subscriber, this.stacktrace));
    }

    static final class OnAssemblySubscriber<T> extends Subscriber<T> {
        final Subscriber<? super T> actual;
        final String stacktrace;

        public OnAssemblySubscriber(Subscriber<? super T> subscriber, String str) {
            super(subscriber);
            this.actual = subscriber;
            this.stacktrace = str;
        }

        @Override // com.tds.common.reactor.Observer
        public void onCompleted() {
            this.actual.onCompleted();
        }

        @Override // com.tds.common.reactor.Observer
        public void onError(Throwable th) {
            new AssemblyStackTraceException(this.stacktrace).attachTo(th);
            this.actual.onError(th);
        }

        @Override // com.tds.common.reactor.Observer
        public void onNext(T t) {
            this.actual.onNext(t);
        }
    }
}
