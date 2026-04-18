package com.tds.common.websocket.protocols;

/* JADX INFO: loaded from: classes.dex */
public interface IProtocol {
    boolean acceptProvidedProtocol(String str);

    IProtocol copyInstance();

    String getProvidedProtocol();

    String toString();
}
