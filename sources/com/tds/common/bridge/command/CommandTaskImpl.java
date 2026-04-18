package com.tds.common.bridge.command;

import com.tds.common.bridge.BridgeCallback;
import com.tds.common.bridge.BridgeHolder;
import com.tds.common.bridge.IBridgeService;
import com.tds.common.bridge.annotation.BridgeMethod;
import com.tds.common.bridge.exception.EngineBridgeException;
import com.tds.common.bridge.exception.EngineBridgeExceptionStatus;
import com.tds.common.bridge.utils.BridgeJsonHelper;
import com.tds.common.bridge.utils.BridgeReflect;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class CommandTaskImpl implements ICommandTask {
    @Override // com.tds.common.bridge.command.ICommandTask
    public void execute(Command command, BridgeCallback bridgeCallback) {
        BridgeReflect.checkCommand(command);
        Object objInvoke = invoke(command, bridgeCallback);
        if (objInvoke == null || !command.callback) {
            return;
        }
        bridgeCallback.onResult(BridgeJsonHelper.object2JsonString(objInvoke));
    }

    private Object invoke(Command command, BridgeCallback bridgeCallback) {
        IBridgeService value = BridgeHolder.INSTANCE.getBridgeService(command.service).getValue();
        try {
            Object[] objArr = null;
            Method method = null;
            for (Method method2 : BridgeReflect.getRegisterService(command).getMethods()) {
                BridgeMethod bridgeMethod = (BridgeMethod) method2.getAnnotation(BridgeMethod.class);
                if (bridgeMethod != null && command.method.equals(bridgeMethod.value())) {
                    Object[] objArrConstructorCommandArgs = BridgeReflect.constructorCommandArgs(method2, command, bridgeCallback);
                    if (objArrConstructorCommandArgs.length == method2.getParameterTypes().length) {
                        method = method2;
                        objArr = objArrConstructorCommandArgs;
                    }
                }
            }
            if (objArr == null || value == null) {
                throw new EngineBridgeException(EngineBridgeExceptionStatus.COMMAND_ARGS_ERROR.getMessage());
            }
            return method.invoke(value, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            throw new EngineBridgeException(EngineBridgeExceptionStatus.COMMAND_ARGS_ERROR.getExpandMessage(e.getCause() != null ? e.getCause().getMessage() : ""));
        }
    }
}
