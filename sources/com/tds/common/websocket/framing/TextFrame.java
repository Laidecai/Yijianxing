package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;
import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.util.Charsetfunctions;

/* JADX INFO: loaded from: classes.dex */
public class TextFrame extends DataFrame {
    public TextFrame() {
        super(Opcode.TEXT);
    }

    @Override // com.tds.common.websocket.framing.DataFrame, com.tds.common.websocket.framing.FramedataImpl1
    public void isValid() throws InvalidDataException {
        super.isValid();
        if (!Charsetfunctions.isValidUTF8(getPayloadData())) {
            throw new InvalidDataException(1007, "Received text is no valid utf8 string!");
        }
    }
}
