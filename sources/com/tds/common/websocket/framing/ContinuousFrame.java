package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;

/* JADX INFO: loaded from: classes.dex */
public class ContinuousFrame extends DataFrame {
    public ContinuousFrame() {
        super(Opcode.CONTINUOUS);
    }
}
