package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;

/* JADX INFO: loaded from: classes.dex */
public class BinaryFrame extends DataFrame {
    public BinaryFrame() {
        super(Opcode.BINARY);
    }
}
