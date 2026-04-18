package com.taptap.services.update.download.core.exception;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class InterruptException extends IOException {
    public static final InterruptException SIGNAL = new InterruptException() { // from class: com.taptap.services.update.download.core.exception.InterruptException.1
        @Override // java.lang.Throwable
        public void printStackTrace() {
            throw new IllegalAccessError("Stack is ignored for signal");
        }
    };

    private InterruptException() {
        super("Interrupted");
    }
}
