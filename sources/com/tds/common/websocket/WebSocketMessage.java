package com.tds.common.websocket;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class WebSocketMessage {

    public enum Type {
        MESSAGE_TYPE_TAP_FRIEND,
        MESSAGE_TYPE_OTHER
    }

    public static Type getMessageType(String str) {
        if (TextUtils.isEmpty(str)) {
            return Type.MESSAGE_TYPE_OTHER;
        }
        return Type.MESSAGE_TYPE_TAP_FRIEND;
    }
}
