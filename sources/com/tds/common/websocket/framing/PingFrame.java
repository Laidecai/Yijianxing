package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;

/* JADX INFO: loaded from: classes.dex */
public class PingFrame extends ControlFrame {
    public PingFrame() {
        super(Opcode.PING);
    }
}
