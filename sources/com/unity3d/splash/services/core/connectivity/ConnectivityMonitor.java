package com.unity3d.splash.services.core.connectivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class ConnectivityMonitor {
    private static int _connected = -1;
    private static HashSet _listeners = null;
    private static boolean _listening = false;
    private static int _networkType = -1;
    private static boolean _webappMonitoring = false;
    private static boolean _wifi = false;

    /* JADX INFO: renamed from: com.unity3d.splash.services.core.connectivity.ConnectivityMonitor$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$unity3d$splash$services$core$connectivity$ConnectivityEvent;

        static {
            int[] iArr = new int[ConnectivityEvent.values().length];
            $SwitchMap$com$unity3d$splash$services$core$connectivity$ConnectivityEvent = iArr;
            try {
                iArr[ConnectivityEvent.CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$connectivity$ConnectivityEvent[ConnectivityEvent.DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$connectivity$ConnectivityEvent[ConnectivityEvent.NETWORK_CHANGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static void addListener(IConnectivityListener iConnectivityListener) {
        if (_listeners == null) {
            _listeners = new HashSet();
        }
        _listeners.add(iConnectivityListener);
        updateListeningStatus();
    }

    public static void connected() {
        if (_connected == 1) {
            return;
        }
        DeviceLog.debug("Unity Ads connectivity change: connected");
        initConnectionStatus();
        HashSet hashSet = _listeners;
        if (hashSet != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ((IConnectivityListener) it.next()).onConnected();
            }
        }
        sendToWebview(ConnectivityEvent.CONNECTED, _wifi, _networkType);
    }

    public static void connectionStatusChanged() {
        NetworkInfo activeNetworkInfo;
        if (_connected == 1 && (activeNetworkInfo = ((ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected()) {
            boolean z = activeNetworkInfo.getType() == 1;
            int networkType = ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkType();
            boolean z2 = _wifi;
            if (z == z2 && (networkType == _networkType || z2)) {
                return;
            }
            _wifi = z;
            _networkType = networkType;
            DeviceLog.debug("Unity Ads connectivity change: network change");
            sendToWebview(ConnectivityEvent.NETWORK_CHANGE, z, networkType);
        }
    }

    public static void disconnected() {
        if (_connected == 0) {
            return;
        }
        _connected = 0;
        DeviceLog.debug("Unity Ads connectivity change: disconnected");
        HashSet hashSet = _listeners;
        if (hashSet != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ((IConnectivityListener) it.next()).onDisconnected();
            }
        }
        sendToWebview(ConnectivityEvent.DISCONNECTED, false, 0);
    }

    private static void initConnectionStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ClientProperties.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            _connected = 0;
            return;
        }
        _connected = 1;
        boolean z = activeNetworkInfo.getType() == 1;
        _wifi = z;
        if (z) {
            return;
        }
        _networkType = ((TelephonyManager) ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkType();
    }

    public static void removeListener(IConnectivityListener iConnectivityListener) {
        HashSet hashSet = _listeners;
        if (hashSet == null) {
            return;
        }
        hashSet.remove(iConnectivityListener);
        updateListeningStatus();
    }

    private static void sendToWebview(ConnectivityEvent connectivityEvent, boolean z, int i) {
        WebViewApp currentApp;
        if (_webappMonitoring && (currentApp = WebViewApp.getCurrentApp()) != null && currentApp.isWebAppLoaded()) {
            int i2 = AnonymousClass1.$SwitchMap$com$unity3d$splash$services$core$connectivity$ConnectivityEvent[connectivityEvent.ordinal()];
            if (i2 == 1) {
                WebViewEventCategory webViewEventCategory = WebViewEventCategory.CONNECTIVITY;
                if (z) {
                    currentApp.sendEvent(webViewEventCategory, ConnectivityEvent.CONNECTED, Boolean.valueOf(z), 0);
                    return;
                } else {
                    currentApp.sendEvent(webViewEventCategory, ConnectivityEvent.CONNECTED, Boolean.valueOf(z), Integer.valueOf(i));
                    return;
                }
            }
            if (i2 == 2) {
                currentApp.sendEvent(WebViewEventCategory.CONNECTIVITY, ConnectivityEvent.DISCONNECTED, new Object[0]);
                return;
            }
            if (i2 != 3) {
                return;
            }
            WebViewEventCategory webViewEventCategory2 = WebViewEventCategory.CONNECTIVITY;
            if (z) {
                currentApp.sendEvent(webViewEventCategory2, ConnectivityEvent.NETWORK_CHANGE, Boolean.valueOf(z), 0);
            } else {
                currentApp.sendEvent(webViewEventCategory2, ConnectivityEvent.NETWORK_CHANGE, Boolean.valueOf(z), Integer.valueOf(i));
            }
        }
    }

    public static void setConnectionMonitoring(boolean z) {
        _webappMonitoring = z;
        updateListeningStatus();
    }

    private static void startListening() {
        if (_listening) {
            return;
        }
        _listening = true;
        initConnectionStatus();
        if (Build.VERSION.SDK_INT < 21) {
            ConnectivityChangeReceiver.register();
        } else {
            ConnectivityNetworkCallback.register();
        }
    }

    public static void stopAll() {
        _listeners = null;
        _webappMonitoring = false;
        updateListeningStatus();
    }

    private static void stopListening() {
        if (_listening) {
            _listening = false;
            if (Build.VERSION.SDK_INT < 21) {
                ConnectivityChangeReceiver.unregister();
            } else {
                ConnectivityNetworkCallback.unregister();
            }
        }
    }

    private static void updateListeningStatus() {
        HashSet hashSet;
        if (_webappMonitoring || !((hashSet = _listeners) == null || hashSet.isEmpty())) {
            startListening();
        } else {
            stopListening();
        }
    }
}
