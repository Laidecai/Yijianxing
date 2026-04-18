package com.taptap.sdk;

import android.content.Intent;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class CallbackManagerImpl implements CallBackManager {
    private HashMap<String, Callback> callbacks = new HashMap<>();

    public interface Callback {
        boolean onActivityResult(int i, Intent intent);
    }

    CallbackManagerImpl() {
    }

    @Override // com.taptap.sdk.CallBackManager
    public boolean onActivityResult(int i, int i2, Intent intent) {
        Callback callback = this.callbacks.get(String.valueOf(i));
        if (callback != null) {
            return callback.onActivityResult(i2, intent);
        }
        return false;
    }

    public void registerCallback(Callback callback, int i) {
        this.callbacks.put(String.valueOf(i), callback);
    }
}
