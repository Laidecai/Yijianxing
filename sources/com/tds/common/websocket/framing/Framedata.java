package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public interface Framedata {
    void append(Framedata framedata);

    Opcode getOpcode();

    ByteBuffer getPayloadData();

    boolean getTransfereMasked();

    boolean isFin();

    boolean isRSV1();

    boolean isRSV2();

    boolean isRSV3();
}
