package com.tds.common.websocket.client;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes.dex */
public interface DnsResolver {
    InetAddress resolve(URI uri) throws UnknownHostException;
}
