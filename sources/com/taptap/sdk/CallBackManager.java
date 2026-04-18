package com.taptap.sdk;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public interface CallBackManager {
    boolean onActivityResult(int i, int i2, Intent intent);

    public static class Factory {
        public static CallBackManager create() {
            return new CallbackManagerImpl();
        }
    }
}
