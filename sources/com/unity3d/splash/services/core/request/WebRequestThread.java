package com.unity3d.splash.services.core.request;

import android.os.ConditionVariable;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.WebRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class WebRequestThread {
    private static int _corePoolSize = 1;
    private static long _keepAliveTime = 1000;
    private static int _maximumPoolSize = 1;
    private static CancelableThreadPoolExecutor _pool = null;
    private static LinkedBlockingQueue _queue = null;
    private static boolean _ready = false;
    private static final Object _readyLock = new Object();

    public static synchronized void cancel() {
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.cancel();
            for (Runnable runnable : _queue) {
                if (runnable instanceof WebRequestRunnable) {
                    ((WebRequestRunnable) runnable).setCancelStatus(true);
                }
            }
            _queue.clear();
            _pool.purge();
        }
    }

    private static synchronized void init() {
        _queue = new LinkedBlockingQueue();
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = new CancelableThreadPoolExecutor(_corePoolSize, _maximumPoolSize, _keepAliveTime, TimeUnit.MILLISECONDS, _queue);
        _pool = cancelableThreadPoolExecutor;
        cancelableThreadPoolExecutor.prestartAllCoreThreads();
        _queue.add(new Runnable() { // from class: com.unity3d.splash.services.core.request.WebRequestThread.1
            @Override // java.lang.Runnable
            public final void run() {
                boolean unused = WebRequestThread._ready = true;
                synchronized (WebRequestThread._readyLock) {
                    WebRequestThread._readyLock.notify();
                }
            }
        });
        while (!_ready) {
            try {
                Object obj = _readyLock;
                synchronized (obj) {
                    obj.wait();
                }
            } catch (InterruptedException unused) {
                DeviceLog.debug("Couldn't synchronize thread");
                return;
            }
        }
    }

    public static synchronized void request(String str, WebRequest.RequestType requestType, Map map, Integer num, Integer num2, IWebRequestListener iWebRequestListener) {
        request(str, requestType, map, null, num, num2, iWebRequestListener);
    }

    public static synchronized void request(String str, WebRequest.RequestType requestType, Map map, String str2, Integer num, Integer num2, IWebRequestListener iWebRequestListener) {
        if (!_ready) {
            init();
        }
        if (str != null && str.length() >= 3) {
            _queue.add(new WebRequestRunnable(str, requestType.name(), str2, num.intValue(), num2.intValue(), map, iWebRequestListener));
            return;
        }
        iWebRequestListener.onFailed(str, "Request is NULL or too short");
    }

    public static synchronized void reset() {
        cancel();
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.shutdown();
            try {
                _pool.awaitTermination(LongCompanionObject.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException unused) {
            }
            _queue.clear();
            _pool = null;
            _queue = null;
            _ready = false;
        }
    }

    public static synchronized boolean resolve(final String str, final IResolveHostListener iResolveHostListener) {
        boolean z;
        if (str == null) {
            iResolveHostListener.onFailed(str, ResolveHostError.INVALID_HOST, "Host is NULL");
            z = false;
        } else if (str.length() < 3) {
            iResolveHostListener.onFailed(str, ResolveHostError.INVALID_HOST, "Host is NULL");
            z = false;
        } else {
            new Thread(new Runnable() { // from class: com.unity3d.splash.services.core.request.WebRequestThread.2
                @Override // java.lang.Runnable
                public final void run() {
                    Thread thread;
                    Exception e;
                    final ConditionVariable conditionVariable = new ConditionVariable();
                    try {
                        thread = new Thread(new Runnable() { // from class: com.unity3d.splash.services.core.request.WebRequestThread.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    iResolveHostListener.onResolve(str, InetAddress.getByName(str).getHostAddress());
                                } catch (UnknownHostException e2) {
                                    DeviceLog.exception("Unknown host", e2);
                                    iResolveHostListener.onFailed(str, ResolveHostError.UNKNOWN_HOST, e2.getMessage());
                                }
                                conditionVariable.open();
                            }
                        });
                        try {
                            thread.start();
                        } catch (Exception e2) {
                            e = e2;
                            DeviceLog.exception("Exception while resolving host", e);
                            iResolveHostListener.onFailed(str, ResolveHostError.UNEXPECTED_EXCEPTION, e.getMessage());
                        }
                    } catch (Exception e3) {
                        thread = null;
                        e = e3;
                    }
                    if (conditionVariable.block(20000L) || thread == null) {
                        return;
                    }
                    thread.interrupt();
                    iResolveHostListener.onFailed(str, ResolveHostError.TIMEOUT, "Timeout");
                }
            }).start();
            z = true;
        }
        return z;
    }

    public static synchronized void setConcurrentRequestCount(int i) {
        _corePoolSize = i;
        _maximumPoolSize = i;
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.setCorePoolSize(i);
            _pool.setMaximumPoolSize(_maximumPoolSize);
        }
    }

    public static synchronized void setKeepAliveTime(long j) {
        _keepAliveTime = j;
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.setKeepAliveTime(j, TimeUnit.MILLISECONDS);
        }
    }

    public static synchronized void setMaximumPoolSize(int i) {
        _maximumPoolSize = i;
        CancelableThreadPoolExecutor cancelableThreadPoolExecutor = _pool;
        if (cancelableThreadPoolExecutor != null) {
            cancelableThreadPoolExecutor.setMaximumPoolSize(i);
        }
    }
}
