package com.tds.tapdb.b;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class h {
    public static String a() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                if ((inetAddressNextElement instanceof Inet6Address) && !inetAddressNextElement.isLoopbackAddress() && !inetAddressNextElement.isLinkLocalAddress() && !inetAddressNextElement.isSiteLocalAddress()) {
                    return inetAddressNextElement.getCanonicalHostName();
                }
            }
        }
        return null;
    }
}
