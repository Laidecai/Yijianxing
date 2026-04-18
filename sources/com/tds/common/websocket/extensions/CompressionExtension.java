package com.tds.common.websocket.extensions;

import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.exceptions.InvalidFrameException;
import com.tds.common.websocket.framing.ControlFrame;
import com.tds.common.websocket.framing.DataFrame;
import com.tds.common.websocket.framing.Framedata;

/* JADX INFO: loaded from: classes.dex */
public abstract class CompressionExtension extends DefaultExtension {
    @Override // com.tds.common.websocket.extensions.DefaultExtension, com.tds.common.websocket.extensions.IExtension
    public void isFrameValid(Framedata framedata) throws InvalidDataException {
        if ((framedata instanceof DataFrame) && (framedata.isRSV2() || framedata.isRSV3())) {
            throw new InvalidFrameException("bad rsv RSV1: " + framedata.isRSV1() + " RSV2: " + framedata.isRSV2() + " RSV3: " + framedata.isRSV3());
        }
        if (framedata instanceof ControlFrame) {
            if (framedata.isRSV1() || framedata.isRSV2() || framedata.isRSV3()) {
                throw new InvalidFrameException("bad rsv RSV1: " + framedata.isRSV1() + " RSV2: " + framedata.isRSV2() + " RSV3: " + framedata.isRSV3());
            }
        }
    }
}
