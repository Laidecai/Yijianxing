package com.tds.common.utils;

import android.app.Activity;
import com.tds.common.reactor.Observable;
import com.tds.common.reactor.Subscriber;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ActivityUtils {
    public static boolean isActivityNotAlive(Activity activity) {
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }

    public static boolean isActivityAlive(Activity activity) {
        return !isActivityNotAlive(activity);
    }

    public static Observable<Activity> getStackTopActivity() {
        return Observable.create(new Observable.OnSubscribe<Activity>() { // from class: com.tds.common.utils.ActivityUtils.1
            @Override // com.tds.common.reactor.functions.Action1
            public void call(Subscriber<? super Activity> subscriber) {
                try {
                    Class<?> cls = Class.forName("android.app.ActivityThread");
                    boolean z = false;
                    Object objInvoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
                    Field declaredField = cls.getDeclaredField("mActivities");
                    declaredField.setAccessible(true);
                    Map map = (Map) declaredField.get(objInvoke);
                    if (map == null) {
                        subscriber.onError(new Throwable("can't get top activity"));
                    }
                    Iterator it = map.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        Class<?> cls2 = next.getClass();
                        Field declaredField2 = cls2.getDeclaredField("paused");
                        declaredField2.setAccessible(true);
                        if (!declaredField2.getBoolean(next)) {
                            Field declaredField3 = cls2.getDeclaredField("activity");
                            declaredField3.setAccessible(true);
                            subscriber.onNext((Activity) declaredField3.get(next));
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Throwable("can't get top activity"));
                    }
                } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("can't get top activity"));
                }
            }
        });
    }
}
