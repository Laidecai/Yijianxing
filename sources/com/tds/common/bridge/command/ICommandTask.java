package com.tds.common.bridge.command;

import com.tds.common.bridge.BridgeCallback;

/* JADX INFO: loaded from: classes.dex */
public interface ICommandTask {
    void execute(Command command, BridgeCallback bridgeCallback);
}
