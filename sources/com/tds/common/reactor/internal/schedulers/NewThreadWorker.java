package com.tds.common.reactor.internal.schedulers;

import com.tds.common.reactor.Subscription;
import com.tds.common.reactor.exceptions.Exceptions;
import com.tds.common.reactor.functions.Action0;
import com.tds.common.reactor.plugins.RxJavaHooks;
import com.tds.common.reactor.schedulers.ScheduledAction;
import com.tds.common.reactor.schedulers.Scheduler;
import com.tds.common.reactor.subscriptions.CompositeSubscription;
import com.tds.common.reactor.subscriptions.Subscriptions;
import com.tds.common.reactor.util.PlatformDependent;
import com.tds.common.reactor.util.RxThreadFactory;
import com.tds.common.reactor.util.SubscriptionList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class NewThreadWorker extends Scheduler.Worker implements Subscription {
    private static final String PURGE_FORCE_KEY = "rx.scheduler.jdk6.purge-force";
    private static final String PURGE_THREAD_PREFIX = "RxSchedulerPurge-";
    private static final boolean SHOULD_TRY_ENABLE_CANCEL_POLICY;
    private static volatile Object cachedSetRemoveOnCancelPolicyMethod;
    private final ScheduledExecutorService executor;
    volatile boolean isUnsubscribed;
    private static final Object SET_REMOVE_ON_CANCEL_POLICY_METHOD_NOT_SUPPORTED = new Object();
    private static final ConcurrentHashMap<ScheduledThreadPoolExecutor, ScheduledThreadPoolExecutor> EXECUTORS = new ConcurrentHashMap<>();
    private static final AtomicReference<ScheduledExecutorService> PURGE = new AtomicReference<>();
    private static final String FREQUENCY_KEY = "rx.scheduler.jdk6.purge-frequency-millis";
    public static final int PURGE_FREQUENCY = Integer.getInteger(FREQUENCY_KEY, 1000).intValue();

    static {
        boolean z = Boolean.getBoolean(PURGE_FORCE_KEY);
        int androidApiVersion = PlatformDependent.getAndroidApiVersion();
        SHOULD_TRY_ENABLE_CANCEL_POLICY = !z && (androidApiVersion == 0 || androidApiVersion >= 21);
    }

    public static void registerExecutor(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        while (true) {
            AtomicReference<ScheduledExecutorService> atomicReference = PURGE;
            if (atomicReference.get() != null) {
                break;
            }
            ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, new RxThreadFactory(PURGE_THREAD_PREFIX));
            if (atomicReference.compareAndSet(null, scheduledExecutorServiceNewScheduledThreadPool)) {
                Runnable runnable = new Runnable() { // from class: com.tds.common.reactor.internal.schedulers.NewThreadWorker.1
                    @Override // java.lang.Runnable
                    public void run() {
                        NewThreadWorker.purgeExecutors();
                    }
                };
                int i = PURGE_FREQUENCY;
                scheduledExecutorServiceNewScheduledThreadPool.scheduleAtFixedRate(runnable, i, i, TimeUnit.MILLISECONDS);
                break;
            }
            scheduledExecutorServiceNewScheduledThreadPool.shutdownNow();
        }
        EXECUTORS.putIfAbsent(scheduledThreadPoolExecutor, scheduledThreadPoolExecutor);
    }

    public static void deregisterExecutor(ScheduledExecutorService scheduledExecutorService) {
        EXECUTORS.remove(scheduledExecutorService);
    }

    static void purgeExecutors() {
        try {
            Iterator<ScheduledThreadPoolExecutor> it = EXECUTORS.keySet().iterator();
            while (it.hasNext()) {
                ScheduledThreadPoolExecutor next = it.next();
                if (!next.isShutdown()) {
                    next.purge();
                } else {
                    it.remove();
                }
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaHooks.onError(th);
        }
    }

    public static boolean tryEnableCancelPolicy(ScheduledExecutorService scheduledExecutorService) {
        Method methodFindSetRemoveOnCancelPolicyMethod;
        if (SHOULD_TRY_ENABLE_CANCEL_POLICY) {
            if (scheduledExecutorService instanceof ScheduledThreadPoolExecutor) {
                Object obj = cachedSetRemoveOnCancelPolicyMethod;
                Object obj2 = SET_REMOVE_ON_CANCEL_POLICY_METHOD_NOT_SUPPORTED;
                if (obj == obj2) {
                    return false;
                }
                if (obj == null) {
                    methodFindSetRemoveOnCancelPolicyMethod = findSetRemoveOnCancelPolicyMethod(scheduledExecutorService);
                    if (methodFindSetRemoveOnCancelPolicyMethod != null) {
                        obj2 = methodFindSetRemoveOnCancelPolicyMethod;
                    }
                    cachedSetRemoveOnCancelPolicyMethod = obj2;
                } else {
                    methodFindSetRemoveOnCancelPolicyMethod = (Method) obj;
                }
            } else {
                methodFindSetRemoveOnCancelPolicyMethod = findSetRemoveOnCancelPolicyMethod(scheduledExecutorService);
            }
            if (methodFindSetRemoveOnCancelPolicyMethod != null) {
                try {
                    methodFindSetRemoveOnCancelPolicyMethod.invoke(scheduledExecutorService, true);
                    return true;
                } catch (IllegalAccessException e) {
                    RxJavaHooks.onError(e);
                } catch (IllegalArgumentException e2) {
                    RxJavaHooks.onError(e2);
                } catch (InvocationTargetException e3) {
                    RxJavaHooks.onError(e3);
                }
            }
        }
        return false;
    }

    static Method findSetRemoveOnCancelPolicyMethod(ScheduledExecutorService scheduledExecutorService) {
        for (Method method : scheduledExecutorService.getClass().getMethods()) {
            if (method.getName().equals("setRemoveOnCancelPolicy")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0] == Boolean.TYPE) {
                    return method;
                }
            }
        }
        return null;
    }

    public NewThreadWorker(ThreadFactory threadFactory) {
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1, threadFactory);
        if (!tryEnableCancelPolicy(scheduledExecutorServiceNewScheduledThreadPool) && (scheduledExecutorServiceNewScheduledThreadPool instanceof ScheduledThreadPoolExecutor)) {
            registerExecutor((ScheduledThreadPoolExecutor) scheduledExecutorServiceNewScheduledThreadPool);
        }
        this.executor = scheduledExecutorServiceNewScheduledThreadPool;
    }

    @Override // com.tds.common.reactor.schedulers.Scheduler.Worker
    public Subscription schedule(Action0 action0) {
        return schedule(action0, 0L, null);
    }

    @Override // com.tds.common.reactor.schedulers.Scheduler.Worker
    public Subscription schedule(Action0 action0, long j, TimeUnit timeUnit) {
        if (this.isUnsubscribed) {
            return Subscriptions.unsubscribed();
        }
        return scheduleActual(action0, j, timeUnit);
    }

    public ScheduledAction scheduleActual(Action0 action0, long j, TimeUnit timeUnit) {
        Future<?> futureSchedule;
        ScheduledAction scheduledAction = new ScheduledAction(RxJavaHooks.onScheduledAction(action0));
        if (j <= 0) {
            futureSchedule = this.executor.submit(scheduledAction);
        } else {
            futureSchedule = this.executor.schedule(scheduledAction, j, timeUnit);
        }
        scheduledAction.add(futureSchedule);
        return scheduledAction;
    }

    public ScheduledAction scheduleActual(Action0 action0, long j, TimeUnit timeUnit, CompositeSubscription compositeSubscription) {
        Future<?> futureSchedule;
        ScheduledAction scheduledAction = new ScheduledAction(RxJavaHooks.onScheduledAction(action0), compositeSubscription);
        compositeSubscription.add(scheduledAction);
        if (j <= 0) {
            futureSchedule = this.executor.submit(scheduledAction);
        } else {
            futureSchedule = this.executor.schedule(scheduledAction, j, timeUnit);
        }
        scheduledAction.add(futureSchedule);
        return scheduledAction;
    }

    public ScheduledAction scheduleActual(Action0 action0, long j, TimeUnit timeUnit, SubscriptionList subscriptionList) {
        Future<?> futureSchedule;
        ScheduledAction scheduledAction = new ScheduledAction(RxJavaHooks.onScheduledAction(action0), subscriptionList);
        subscriptionList.add(scheduledAction);
        if (j <= 0) {
            futureSchedule = this.executor.submit(scheduledAction);
        } else {
            futureSchedule = this.executor.schedule(scheduledAction, j, timeUnit);
        }
        scheduledAction.add(futureSchedule);
        return scheduledAction;
    }

    @Override // com.tds.common.reactor.Subscription
    public void unsubscribe() {
        this.isUnsubscribed = true;
        this.executor.shutdownNow();
        deregisterExecutor(this.executor);
    }

    @Override // com.tds.common.reactor.Subscription
    public boolean isUnsubscribed() {
        return this.isUnsubscribed;
    }
}
