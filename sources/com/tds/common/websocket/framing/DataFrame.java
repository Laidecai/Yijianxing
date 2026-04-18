package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;
import com.tds.common.websocket.exceptions.InvalidDataException;

/* JADX INFO: loaded from: classes.dex */
public abstract class DataFrame extends FramedataImpl1 {
    @Override // com.tds.common.websocket.framing.FramedataImpl1
    public void isValid() throws InvalidDataException {
    }

    public DataFrame(Opcode opcode) {
        super(opcode);
    }
}
