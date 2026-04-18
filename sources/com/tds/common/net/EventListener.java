package com.tds.common.net;

import com.tds.common.net.TdsHttp;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class EventListener {
    public void callEnd(String str) {
    }

    public void callFailed(String str, IOException iOException) {
    }

    public void callStart(TdsHttp.Call call) {
    }
}
