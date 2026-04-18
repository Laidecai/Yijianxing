package com.tds.common.tracker.session;

import android.text.TextUtils;
import com.tds.common.utils.GUIDHelper;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class SessionIdManager {
    static Map<Integer, String> sessions = new ConcurrentHashMap();

    static class Holder {
        static SessionIdManager INSATNCE = new SessionIdManager();

        Holder() {
        }
    }

    public static SessionIdManager getInstance() {
        return Holder.INSATNCE;
    }

    public void registerSession(int i) {
        String string = GUIDHelper.INSTANCE.getUID() + UUID.randomUUID();
        if (i == 2) {
            string = UUID.randomUUID().toString();
        }
        sessions.put(Integer.valueOf(i), string);
    }

    public String getSessionId(int i) {
        return (!sessions.containsKey(Integer.valueOf(i)) || TextUtils.isEmpty(sessions.get(Integer.valueOf(i)))) ? "" : sessions.get(Integer.valueOf(i));
    }

    public void unRegisterSession(int i) {
        sessions.remove(Integer.valueOf(i));
    }
}
