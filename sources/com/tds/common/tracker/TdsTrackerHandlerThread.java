package com.tds.common.tracker;

import android.os.HandlerThread;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TdsTrackerHandlerThread extends HandlerThread {
    private TdsTrackerHandler sHandler;

    public TdsTrackerHandlerThread(String str) {
        super(str, 1);
    }

    @Override // android.os.HandlerThread
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        this.sHandler = new TdsTrackerHandler(getLooper());
    }

    void write(TdsTrackerConfig tdsTrackerConfig, Map<String, String> map) {
        TdsTrackerHandler tdsTrackerHandler = this.sHandler;
        if (tdsTrackerHandler == null) {
            return;
        }
        tdsTrackerHandler.sendTrackMessage(tdsTrackerConfig, map);
    }
}
