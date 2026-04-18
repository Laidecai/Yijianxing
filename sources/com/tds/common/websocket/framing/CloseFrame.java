package com.tds.common.websocket.framing;

import com.tds.common.websocket.enums.Opcode;
import com.tds.common.websocket.exceptions.InvalidDataException;
import com.tds.common.websocket.exceptions.InvalidFrameException;
import com.tds.common.websocket.util.ByteBufferUtils;
import com.tds.common.websocket.util.Charsetfunctions;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class CloseFrame extends ControlFrame {
    public static final int ABNORMAL_CLOSE = 1006;
    public static final int BAD_GATEWAY = 1014;
    public static final int BUGGYCLOSE = -2;
    public static final int EXTENSION = 1010;
    public static final int FLASHPOLICY = -3;
    public static final int GOING_AWAY = 1001;
    public static final int NEVER_CONNECTED = -1;
    public static final int NOCODE = 1005;
    public static final int NORMAL = 1000;
    public static final int NO_UTF8 = 1007;
    public static final int POLICY_VALIDATION = 1008;
    public static final int PROTOCOL_ERROR = 1002;
    public static final int REFUSE = 1003;
    public static final int SERVICE_RESTART = 1012;
    public static final int TLS_ERROR = 1015;
    public static final int TOOBIG = 1009;
    public static final int TRY_AGAIN_LATER = 1013;
    public static final int UNEXPECTED_CONDITION = 1011;
    private int code;
    private String reason;

    public CloseFrame() {
        super(Opcode.CLOSING);
        setReason("");
        setCode(1000);
    }

    public void setCode(int i) {
        this.code = i;
        if (i == 1015) {
            this.code = NOCODE;
            this.reason = "";
        }
        updatePayload();
    }

    public void setReason(String str) {
        if (str == null) {
            str = "";
        }
        this.reason = str;
        updatePayload();
    }

    public int getCloseCode() {
        return this.code;
    }

    public String getMessage() {
        return this.reason;
    }

    @Override // com.tds.common.websocket.framing.FramedataImpl1
    public String toString() {
        return super.toString() + "code: " + this.code;
    }

    @Override // com.tds.common.websocket.framing.ControlFrame, com.tds.common.websocket.framing.FramedataImpl1
    public void isValid() throws InvalidDataException {
        super.isValid();
        if (this.code == 1007 && this.reason.isEmpty()) {
            throw new InvalidDataException(1007, "Received text is no valid utf8 string!");
        }
        if (this.code == 1005 && this.reason.length() > 0) {
            throw new InvalidDataException(1002, "A close frame must have a closecode if it has a reason");
        }
        int i = this.code;
        if (i > 1015 && i < 3000) {
            throw new InvalidDataException(1002, "Trying to send an illegal close code!");
        }
        if (i == 1006 || i == 1015 || i == 1005 || i > 4999 || i < 1000 || i == 1004) {
            throw new InvalidFrameException("closecode must not be sent over the wire: " + this.code);
        }
    }

    @Override // com.tds.common.websocket.framing.FramedataImpl1
    public void setPayload(ByteBuffer byteBuffer) {
        this.code = NOCODE;
        this.reason = "";
        byteBuffer.mark();
        if (byteBuffer.remaining() == 0) {
            this.code = 1000;
            return;
        }
        if (byteBuffer.remaining() == 1) {
            this.code = 1002;
            return;
        }
        if (byteBuffer.remaining() >= 2) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
            byteBufferAllocate.position(2);
            byteBufferAllocate.putShort(byteBuffer.getShort());
            byteBufferAllocate.position(0);
            this.code = byteBufferAllocate.getInt();
        }
        byteBuffer.reset();
        try {
            validateUtf8(byteBuffer, byteBuffer.position());
        } catch (InvalidDataException unused) {
            this.code = 1007;
            this.reason = null;
        }
    }

    private void validateUtf8(ByteBuffer byteBuffer, int i) throws InvalidDataException {
        try {
            try {
                byteBuffer.position(byteBuffer.position() + 2);
                this.reason = Charsetfunctions.stringUtf8(byteBuffer);
            } catch (IllegalArgumentException unused) {
                throw new InvalidDataException(1007);
            }
        } finally {
            byteBuffer.position(i);
        }
    }

    private void updatePayload() {
        byte[] bArrUtf8Bytes = Charsetfunctions.utf8Bytes(this.reason);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
        byteBufferAllocate.putInt(this.code);
        byteBufferAllocate.position(2);
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(bArrUtf8Bytes.length + 2);
        byteBufferAllocate2.put(byteBufferAllocate);
        byteBufferAllocate2.put(bArrUtf8Bytes);
        byteBufferAllocate2.rewind();
        super.setPayload(byteBufferAllocate2);
    }

    @Override // com.tds.common.websocket.framing.FramedataImpl1, com.tds.common.websocket.framing.Framedata
    public ByteBuffer getPayloadData() {
        if (this.code == 1005) {
            return ByteBufferUtils.getEmptyByteBuffer();
        }
        return super.getPayloadData();
    }

    @Override // com.tds.common.websocket.framing.FramedataImpl1
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        CloseFrame closeFrame = (CloseFrame) obj;
        if (this.code != closeFrame.code) {
            return false;
        }
        String str = this.reason;
        String str2 = closeFrame.reason;
        return str != null ? str.equals(str2) : str2 == null;
    }

    @Override // com.tds.common.websocket.framing.FramedataImpl1
    public int hashCode() {
        int iHashCode = ((super.hashCode() * 31) + this.code) * 31;
        String str = this.reason;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }
}
