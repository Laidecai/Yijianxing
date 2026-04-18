package com.tds.common.reactor.plugins;

import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.functions.Action1;
import com.tds.common.reactor.functions.Func0;
import com.tds.common.reactor.functions.Func1;
import com.tds.common.reactor.functions.Func2;
import com.tds.common.reactor.operators.OnSubscribeOnAssembly;
import com.tds.common.reactor.schedulers.Scheduler;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes.dex */
public final class RxJavaHooks {
    static volatile boolean lockdown;
    static volatile Func1<Throwable, Throwable> onCompletableSubscribeError;
    static volatile Func1<Scheduler, Scheduler> onComputationScheduler;
    static volatile Action1<Throwable> onError;
    static volatile Func0<? extends ScheduledExecutorService> onGenericScheduledExecutorService;
    static volatile Func1<Scheduler, Scheduler> onIOScheduler;
    static volatile Func1<Scheduler, Scheduler> onNewThreadScheduler;
    static volatile Func1<Observable.OnSubscribe, Observable.OnSubscribe> onObservableCreate;
    static volatile Func1<Observable.Operator, Observable.Operator> onObservableLift;
    static volatile Func1<Subscription, Subscription> onObservableReturn;
    static volatile Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> onObservableStart;
    static volatile Func1<Throwable, Throwable> onObservableSubscribeError;
    static volatile Func1<Action0, Action0> onScheduleAction;
    static volatile Func1<Subscription, Subscription> onSingleReturn;
    static volatile Func1<Throwable, Throwable> onSingleSubscribeError;

    static {
        init();
    }

    private RxJavaHooks() {
        throw new IllegalStateException("No instances!");
    }

    static void init() {
        onError = new Action1<Throwable>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.1
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Throwable th) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(th);
            }
        };
        onObservableStart = new Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.2
            @Override // com.tds.common.reactor.functions.Func2
            public Observable.OnSubscribe call(Observable observable, Observable.OnSubscribe onSubscribe) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onSubscribeStart(observable, onSubscribe);
            }
        };
        onObservableReturn = new Func1<Subscription, Subscription>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.3
            @Override // com.tds.common.reactor.functions.Func1
            public Subscription call(Subscription subscription) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onSubscribeReturn(subscription);
            }
        };
        onScheduleAction = new Func1<Action0, Action0>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.4
            @Override // com.tds.common.reactor.functions.Func1
            public Action0 call(Action0 action0) {
                return RxJavaPlugins.getInstance().getSchedulersHook().onSchedule(action0);
            }
        };
        onObservableSubscribeError = new Func1<Throwable, Throwable>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.5
            @Override // com.tds.common.reactor.functions.Func1
            public Throwable call(Throwable th) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onSubscribeError(th);
            }
        };
        onObservableLift = new Func1<Observable.Operator, Observable.Operator>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.6
            @Override // com.tds.common.reactor.functions.Func1
            public Observable.Operator call(Observable.Operator operator) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onLift(operator);
            }
        };
        initCreate();
    }

    static void initCreate() {
        onObservableCreate = new Func1<Observable.OnSubscribe, Observable.OnSubscribe>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.7
            @Override // com.tds.common.reactor.functions.Func1
            public Observable.OnSubscribe call(Observable.OnSubscribe onSubscribe) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onCreate(onSubscribe);
            }
        };
    }

    public static void reset() {
        if (lockdown) {
            return;
        }
        init();
        onComputationScheduler = null;
        onIOScheduler = null;
        onNewThreadScheduler = null;
        onGenericScheduledExecutorService = null;
    }

    public static <T, R> Observable.Operator<R, T> onObservableLift(Observable.Operator<R, T> operator) {
        Func1<Observable.Operator, Observable.Operator> func1 = onObservableLift;
        return func1 != null ? func1.call(operator) : operator;
    }

    public static void clear() {
        if (lockdown) {
            return;
        }
        onError = null;
        onObservableCreate = null;
        onObservableStart = null;
        onObservableReturn = null;
        onObservableSubscribeError = null;
        onComputationScheduler = null;
        onIOScheduler = null;
        onNewThreadScheduler = null;
        onScheduleAction = null;
        onGenericScheduledExecutorService = null;
    }

    public static void lockdown() {
        lockdown = true;
    }

    public static boolean isLockdown() {
        return lockdown;
    }

    public static void onError(Throwable th) {
        Action1<Throwable> action1 = onError;
        if (action1 != null) {
            try {
                action1.call(th);
                return;
            } catch (Throwable th2) {
                System.err.println("The onError handler threw an Exception. It shouldn't. => " + th2.getMessage());
                th2.printStackTrace();
                signalUncaught(th2);
            }
        }
        signalUncaught(th);
    }

    static void signalUncaught(Throwable th) {
        Thread threadCurrentThread = Thread.currentThread();
        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th);
    }

    public static <T> Observable.OnSubscribe<T> onCreate(Observable.OnSubscribe<T> onSubscribe) {
        Func1<Observable.OnSubscribe, Observable.OnSubscribe> func1 = onObservableCreate;
        return func1 != null ? func1.call(onSubscribe) : onSubscribe;
    }

    public static Scheduler onComputationScheduler(Scheduler scheduler) {
        Func1<Scheduler, Scheduler> func1 = onComputationScheduler;
        return func1 != null ? func1.call(scheduler) : scheduler;
    }

    public static Scheduler onIOScheduler(Scheduler scheduler) {
        Func1<Scheduler, Scheduler> func1 = onIOScheduler;
        return func1 != null ? func1.call(scheduler) : scheduler;
    }

    public static Scheduler onNewThreadScheduler(Scheduler scheduler) {
        Func1<Scheduler, Scheduler> func1 = onNewThreadScheduler;
        return func1 != null ? func1.call(scheduler) : scheduler;
    }

    public static Action0 onScheduledAction(Action0 action0) {
        Func1<Action0, Action0> func1 = onScheduleAction;
        return func1 != null ? func1.call(action0) : action0;
    }

    public static <T> Observable.OnSubscribe<T> onObservableStart(Observable<T> observable, Observable.OnSubscribe<T> onSubscribe) {
        Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> func2 = onObservableStart;
        return func2 != null ? func2.call(observable, onSubscribe) : onSubscribe;
    }

    public static Subscription onObservableReturn(Subscription subscription) {
        Func1<Subscription, Subscription> func1 = onObservableReturn;
        return func1 != null ? func1.call(subscription) : subscription;
    }

    public static Throwable onObservableError(Throwable th) {
        Func1<Throwable, Throwable> func1 = onObservableSubscribeError;
        return func1 != null ? func1.call(th) : th;
    }

    public static void setOnError(Action1<Throwable> action1) {
        if (lockdown) {
            return;
        }
        onError = action1;
    }

    public static void setOnObservableCreate(Func1<Observable.OnSubscribe, Observable.OnSubscribe> func1) {
        if (lockdown) {
            return;
        }
        onObservableCreate = func1;
    }

    public static void setOnComputationScheduler(Func1<Scheduler, Scheduler> func1) {
        if (lockdown) {
            return;
        }
        onComputationScheduler = func1;
    }

    public static void setOnIOScheduler(Func1<Scheduler, Scheduler> func1) {
        if (lockdown) {
            return;
        }
        onIOScheduler = func1;
    }

    public static void setOnNewThreadScheduler(Func1<Scheduler, Scheduler> func1) {
        if (lockdown) {
            return;
        }
        onNewThreadScheduler = func1;
    }

    public static void setOnScheduleAction(Func1<Action0, Action0> func1) {
        if (lockdown) {
            return;
        }
        onScheduleAction = func1;
    }

    public static void setOnObservableStart(Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> func2) {
        if (lockdown) {
            return;
        }
        onObservableStart = func2;
    }

    public static void setOnObservableReturn(Func1<Subscription, Subscription> func1) {
        if (lockdown) {
            return;
        }
        onObservableReturn = func1;
    }

    public static void setOnSingleReturn(Func1<Subscription, Subscription> func1) {
        if (lockdown) {
            return;
        }
        onSingleReturn = func1;
    }

    public static void setOnSingleSubscribeError(Func1<Throwable, Throwable> func1) {
        if (lockdown) {
            return;
        }
        onSingleSubscribeError = func1;
    }

    public static Func1<Throwable, Throwable> getOnSingleSubscribeError() {
        return onSingleSubscribeError;
    }

    public static void setOnCompletableSubscribeError(Func1<Throwable, Throwable> func1) {
        if (lockdown) {
            return;
        }
        onCompletableSubscribeError = func1;
    }

    public static Func1<Throwable, Throwable> getOnCompletableSubscribeError() {
        return onCompletableSubscribeError;
    }

    public static void setOnObservableSubscribeError(Func1<Throwable, Throwable> func1) {
        if (lockdown) {
            return;
        }
        onObservableSubscribeError = func1;
    }

    public static Func1<Throwable, Throwable> getOnObservableSubscribeError() {
        return onObservableSubscribeError;
    }

    public static Func1<Scheduler, Scheduler> getOnComputationScheduler() {
        return onComputationScheduler;
    }

    public static Action1<Throwable> getOnError() {
        return onError;
    }

    public static Func1<Scheduler, Scheduler> getOnIOScheduler() {
        return onIOScheduler;
    }

    public static Func1<Scheduler, Scheduler> getOnNewThreadScheduler() {
        return onNewThreadScheduler;
    }

    public static Func1<Observable.OnSubscribe, Observable.OnSubscribe> getOnObservableCreate() {
        return onObservableCreate;
    }

    public static Func1<Action0, Action0> getOnScheduleAction() {
        return onScheduleAction;
    }

    public static Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> getOnObservableStart() {
        return onObservableStart;
    }

    public static Func1<Subscription, Subscription> getOnObservableReturn() {
        return onObservableReturn;
    }

    public static Func1<Subscription, Subscription> getOnSingleReturn() {
        return onSingleReturn;
    }

    public static void resetAssemblyTracking() {
        if (lockdown) {
            return;
        }
        initCreate();
    }

    public static void clearAssemblyTracking() {
        if (lockdown) {
            return;
        }
        onObservableCreate = null;
    }

    public static void enableAssemblyTracking() {
        if (lockdown) {
            return;
        }
        onObservableCreate = new Func1<Observable.OnSubscribe, Observable.OnSubscribe>() { // from class: com.tds.common.reactor.plugins.RxJavaHooks.8
            @Override // com.tds.common.reactor.functions.Func1
            public Observable.OnSubscribe call(Observable.OnSubscribe onSubscribe) {
                return new OnSubscribeOnAssembly(onSubscribe);
            }
        };
    }

    public static void setOnGenericScheduledExecutorService(Func0<? extends ScheduledExecutorService> func0) {
        if (lockdown) {
            return;
        }
        onGenericScheduledExecutorService = func0;
    }

    public static Func0<? extends ScheduledExecutorService> getOnGenericScheduledExecutorService() {
        return onGenericScheduledExecutorService;
    }
}
