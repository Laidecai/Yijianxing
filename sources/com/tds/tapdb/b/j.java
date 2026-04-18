package com.tds.tapdb.b;

import android.content.Context;
import android.text.TextUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class j {
    private static String a = "";
    private static CountDownLatch b;
    private static Class<?> c;
    private static Class<?> d;
    private static Class<?> e;
    private static Class<?> f;

    static class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException unused) {
            }
            j.b.countDown();
        }
    }

    static class b implements InvocationHandler {
        b() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            try {
                if (!"OnSupport".equals(method.getName())) {
                    return null;
                }
                if (((Boolean) objArr[0]).booleanValue()) {
                    String unused = j.a = (String) j.d.getDeclaredMethod("getOAID", new Class[0]).invoke(objArr[1], new Object[0]);
                    n.a("oaid:" + j.a);
                }
                j.b.countDown();
                return null;
            } catch (Exception e) {
                n.a(e);
                j.b.countDown();
                return null;
            }
        }
    }

    public static String a(Context context) {
        try {
            b = new CountDownLatch(1);
            d();
            if (f != null && c != null && d != null) {
                if (!TextUtils.isEmpty(a)) {
                    return a;
                }
                a(context, 2);
                try {
                    b.await();
                } catch (InterruptedException e2) {
                    n.a((Exception) e2);
                }
                n.a("CountDownLatch await");
                return a;
            }
            n.b("OAID 读取类创建失败");
            return "";
        } catch (Exception e3) {
            n.a(e3);
            return "";
        }
    }

    private static void a(Context context, int i) {
        if (i == 0) {
            return;
        }
        try {
            Class<?> cls = e;
            if (cls != null) {
                cls.getDeclaredMethod("InitEntry", Context.class).invoke(null, context);
            }
            int iIntValue = ((Integer) f.getDeclaredMethod("InitSdk", Context.class, Boolean.TYPE, c).invoke(null, context, Boolean.TRUE, Proxy.newProxyInstance(context.getClassLoader(), new Class[]{c}, new b()))).intValue();
            n.c("MdidSdkHelper ErrorCode : " + iIntValue);
            if (iIntValue != 1008614) {
                i--;
                a(context, i);
                if (i == 0) {
                    b.countDown();
                }
            }
            new Thread(new a()).start();
        } catch (Exception e2) {
            n.a(e2);
            int i2 = i - 1;
            a(context, i2);
            if (i2 == 0) {
                b.countDown();
            }
        }
    }

    private static void d() {
        try {
            f = Class.forName("com.bun.miitmdid.core.MdidSdkHelper");
            try {
                try {
                    try {
                        c = Class.forName("com.bun.miitmdid.interfaces.IIdentifierListener");
                        d = Class.forName("com.bun.miitmdid.interfaces.IdSupplier");
                    } catch (Exception unused) {
                        c = Class.forName("com.bun.miitmdid.core.IIdentifierListener");
                        d = Class.forName("com.bun.miitmdid.supplier.IdSupplier");
                        e = Class.forName("com.bun.miitmdid.core.JLibrary");
                    }
                } catch (Exception unused2) {
                }
            } catch (Exception unused3) {
                c = Class.forName("com.bun.supplier.IIdentifierListener");
                d = Class.forName("com.bun.supplier.IdSupplier");
                e = Class.forName("com.bun.miitmdid.core.JLibrary");
            }
        } catch (ClassNotFoundException e2) {
            n.a((Exception) e2);
        }
    }
}
