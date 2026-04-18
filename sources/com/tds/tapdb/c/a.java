package com.tds.tapdb.c;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import com.tds.tapdb.b.n;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static final String a = "com.xdwl.smantifraud.XdwlSmAntiFraud$XdwlSmOption";
    private static final String b = "com.xdwl.smantifraud.XdwlSmAntiFraud";

    public static String a() {
        try {
            return (String) Class.forName(b).getDeclaredMethod("getDeviceId", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            n.a(e, true);
            return null;
        }
    }

    private static String a(Context context) {
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static void a(Context context, String str, boolean z) {
        try {
            if (context.getPackageName().equals(a(context))) {
                Class<?> cls = Class.forName(a);
                Object objNewInstance = cls.newInstance();
                Method declaredMethod = cls.getDeclaredMethod("setOrganization", String.class);
                Method declaredMethod2 = cls.getDeclaredMethod("setAppId", String.class);
                Method declaredMethod3 = cls.getDeclaredMethod("setPublicKey", String.class);
                Method declaredMethod4 = cls.getDeclaredMethod("setAinfoKey", String.class);
                Method declaredMethod5 = cls.getDeclaredMethod("setUrl", String.class);
                declaredMethod.invoke(objNewInstance, "CEm5FkrNQT5uU1lrUNyI");
                declaredMethod2.invoke(objNewInstance, str);
                declaredMethod3.invoke(objNewInstance, "MIIDLzCCAhegAwIBAgIBMDANBgkqhkiG9w0BAQUFADAyMQswCQYDVQQGEwJDTjELMAkGA1UECwwCU00xFjAUBgNVBAMMDWUuaXNodW1laS5jb20wHhcNMjAxMTA5MDkxODI1WhcNNDAxMTA0MDkxODI1WjAyMQswCQYDVQQGEwJDTjELMAkGA1UECwwCU00xFjAUBgNVBAMMDWUuaXNodW1laS5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCDX+yT/mjf9Qv4PUxGO3ZGEElF9So/w03crdSzpq5vJhYLuRWzlqpmg4hkA9n/lvfS3c08q4gF2d4dllkHX0JP64FDA2CQs+xRnpy/7kD4rT8kT0aUqsrgDf3GQH7lAsfL67/krKeG8Jh6/Rh09A0OvPUqQDBz4lTDjnPopi0OwdD3EpIIPEjwbfwPY9eOFxwPRI7zqYqKPaMbHnGkXKSZoAlH8M4AfwjRstCEEVmxZN9QwPPc67t4Z6EB92YFmca5JR0Um/pIZP2kHPoxTkQej00nC46wI66sV7y7IVSmLS1RvCiePISrOiVkFz3scMzcHVRdBuZw/7xe2d0fA9QJAgMBAAGjUDBOMB0GA1UdDgQWBBSN+vHlQ6UNpfxoMvJVDxF3YQhPQTAfBgNVHSMEGDAWgBSN+vHlQ6UNpfxoMvJVDxF3YQhPQTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBQUAA4IBAQBdE6i7sun4pA+c6Dm2GxIE/D9dGh66R+MRaXXow+0OfJY44k6deiVbo6ZRSduEiWPvQwTpDO5y3or1qQ31p3beoSuwfytQfzs5FUtZVjHEen3Q0wmEbDCshst02azSOyFKcI5rAPK2+h39OO5hW4P7WCx0WB8hOt+0DTTzJE3BCt9mFt/kTZZ9APOZ7PMnzAWzmiq7bYKFnFJjZrjNS+y4rYQVwch7z+NPTFps+eOR2ajAjXjOWInxCATT36fcB8kd0ZpIaIlTWdtSLyKVMZJZB9oqv/vmKcqhTFJVwWvn2496ediHUvzA4xwriyIboeNXJfGxfkeVS78waRNQrXj/");
                declaredMethod4.invoke(objNewInstance, "VkqOinnKqWdfyVZDYevlXnRIoLIsZtHHmyVwIbQJxuRtEJoSELVIsBQCPTjeiMxh");
                declaredMethod5.invoke(objNewInstance, z ? "https://sapi.taptap.com/v3/profile/android" : "https://hk.sapi.taptap.com/v3/profile/android");
                Class.forName(b).getDeclaredMethod("create", Context.class, cls).invoke(null, context, objNewInstance);
            }
        } catch (Exception e) {
            n.a(e, true);
        }
    }
}
