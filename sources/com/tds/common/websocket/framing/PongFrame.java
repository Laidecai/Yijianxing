package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;

/* JADX INFO: loaded from: classes.dex */
public class PongFrame extends ControlFrame {
    public PongFrame() {
        super(Opcode.PONG);
    }

    public PongFrame(PingFrame pingFrame) {
        super(Opcode.PONG);
        setPayload(pingFrame.getPayloadData());
    }
}
