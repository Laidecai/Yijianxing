package com.tds.common.websocket.extensions;

import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.framing.Framedata;

/* JADX INFO: loaded from: classes.dex */
public interface IExtension {
    boolean acceptProvidedExtensionAsClient(String str);

    boolean acceptProvidedExtensionAsServer(String str);

    IExtension copyInstance();

    void decodeFrame(Framedata framedata) throws InvalidDataException;

    void encodeFrame(Framedata framedata);

    String getProvidedExtensionAsClient();

    String getProvidedExtensionAsServer();

    void isFrameValid(Framedata framedata) throws InvalidDataException;

    void reset();

    String toString();
}
