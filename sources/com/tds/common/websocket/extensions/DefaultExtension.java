package com.tds.common.websocket.extensions;

import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.exceptions.InvalidFrameException;
import com.tds.common.websocket.framing.Framedata;

/* JADX INFO: loaded from: classes.dex */
public class DefaultExtension implements IExtension {
    @Override // com.tds.common.websocket.extensions.IExtension
    public boolean acceptProvidedExtensionAsClient(String str) {
        return true;
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public boolean acceptProvidedExtensionAsServer(String str) {
        return true;
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public void decodeFrame(Framedata framedata) throws InvalidDataException {
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public void encodeFrame(Framedata framedata) {
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public String getProvidedExtensionAsClient() {
        return "";
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public String getProvidedExtensionAsServer() {
        return "";
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public void reset() {
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public void isFrameValid(Framedata framedata) throws InvalidDataException {
        if (framedata.isRSV1() || framedata.isRSV2() || framedata.isRSV3()) {
            throw new InvalidFrameException("bad rsv RSV1: " + framedata.isRSV1() + " RSV2: " + framedata.isRSV2() + " RSV3: " + framedata.isRSV3());
        }
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public IExtension copyInstance() {
        return new DefaultExtension();
    }

    @Override // com.tds.common.websocket.extensions.IExtension
    public String toString() {
        return getClass().getSimpleName();
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }
}
