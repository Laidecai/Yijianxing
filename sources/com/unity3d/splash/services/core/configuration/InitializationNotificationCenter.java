package com.unity3d.splash.services.core.configuration;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class InitializationNotificationCenter implements IInitializationNotificationCenter {
    private static InitializationNotificationCenter instance;
    private HashMap _sdkListeners = new HashMap();

    public static InitializationNotificationCenter getInstance() {
        if (instance == null) {
            instance = new InitializationNotificationCenter();
        }
        return instance;
    }

    private void removeListener(Integer num) {
        this._sdkListeners.remove(num);
    }

    @Override // com.unity3d.splash.services.core.configuration.IInitializationNotificationCenter
    public void addListener(IInitializationListener iInitializationListener) {
        synchronized (this._sdkListeners) {
            if (iInitializationListener != null) {
                this._sdkListeners.put(new Integer(iInitializationListener.hashCode()), new WeakReference(iInitializationListener));
            }
        }
    }

    @Override // com.unity3d.splash.services.core.configuration.IInitializationNotificationCenter
    public void removeListener(IInitializationListener iInitializationListener) {
        synchronized (this._sdkListeners) {
            if (iInitializationListener != null) {
                removeListener(new Integer(iInitializationListener.hashCode()));
            }
        }
    }

    @Override // com.unity3d.splash.services.core.configuration.IInitializationNotificationCenter
    public void triggerOnSdkInitializationFailed(String str, final int i) {
        synchronized (this._sdkListeners) {
            final String str2 = "SDK Failed to Initialize due to " + str;
            DeviceLog.error(str2);
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : this._sdkListeners.entrySet()) {
                if (((WeakReference) entry.getValue()).get() != null) {
                    final IInitializationListener iInitializationListener = (IInitializationListener) ((WeakReference) entry.getValue()).get();
                    Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.core.configuration.InitializationNotificationCenter.2
                        @Override // java.lang.Runnable
                        public void run() {
                            iInitializationListener.onSdkInitializationFailed(str2, i);
                        }
                    });
                } else {
                    arrayList.add(entry.getKey());
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this._sdkListeners.remove((Integer) it.next());
            }
        }
    }

    @Override // com.unity3d.splash.services.core.configuration.IInitializationNotificationCenter
    public void triggerOnSdkInitialized() {
        synchronized (this._sdkListeners) {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : this._sdkListeners.entrySet()) {
                if (((WeakReference) entry.getValue()).get() != null) {
                    final IInitializationListener iInitializationListener = (IInitializationListener) ((WeakReference) entry.getValue()).get();
                    Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.core.configuration.InitializationNotificationCenter.1
                        @Override // java.lang.Runnable
                        public void run() {
                            iInitializationListener.onSdkInitialized();
                        }
                    });
                } else {
                    arrayList.add(entry.getKey());
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this._sdkListeners.remove((Integer) it.next());
            }
        }
    }
}
