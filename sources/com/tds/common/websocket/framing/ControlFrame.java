package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;
import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.exceptions.InvalidFrameException;

/* JADX INFO: loaded from: classes.dex */
public abstract class ControlFrame extends FramedataImpl1 {
    public ControlFrame(Opcode opcode) {
        super(opcode);
    }

    @Override // com.tds.common.websocket.framing.FramedataImpl1
    public void isValid() throws InvalidDataException {
        if (!isFin()) {
            throw new InvalidFrameException("Control frame can't have fin==false set");
        }
        if (isRSV1()) {
            throw new InvalidFrameException("Control frame can't have rsv1==true set");
        }
        if (isRSV2()) {
            throw new InvalidFrameException("Control frame can't have rsv2==true set");
        }
        if (isRSV3()) {
            throw new InvalidFrameException("Control frame can't have rsv3==true set");
        }
    }
}
