package com.tds.common.bridge;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tds.common.bridge.command.Command;
import com.tds.common.bridge.command.CommandTaskImpl;
import com.tds.common.bridge.exception.EngineBridgeException;
import com.tds.common.bridge.result.Result;
import com.tds.common.bridge.utils.BridgeLogger;
import com.tds.common.bridge.utils.BridgeReflect;
import com.tds.common.utils.EngineUtil;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;

/* JADX INFO: loaded from: classes.dex */
public class Bridge implements IBridge {
    private Map<String, BridgeCallback> mBridgeCallbackMaps;
    private Handler mEngineHandler;
    private WeakReference<Activity> mWeakReference;

    public static native synchronized void nativeOnResult(String str) throws EngineBridgeException;

    private Bridge() {
    }

    private static class Holder {
        private static Bridge sInstance = new Bridge();

        private Holder() {
        }
    }

    public static Bridge getInstance() {
        return Holder.sInstance;
    }

    @Override // com.tds.common.bridge.IBridge
    public void init(Activity activity) {
        BridgeLogger.i("[EngineBridge] init!");
        this.mWeakReference = new WeakReference<>(activity);
        this.mBridgeCallbackMaps = new ConcurrentHashMap();
    }

    @Override // com.tds.common.bridge.IBridge
    public void register(Class<? extends IBridgeService> cls, IBridgeService iBridgeService) {
        if (cls.isInterface() && BridgeReflect.checkServiceLegal(cls)) {
            BridgeHolder.INSTANCE.register(cls, iBridgeService);
            return;
        }
        Class<? extends IBridgeService> legalService = BridgeReflect.getLegalService(cls);
        if (legalService != null) {
            BridgeHolder.INSTANCE.register(legalService, iBridgeService);
            return;
        }
        throw new EngineBridgeException("注册的IBridgeService出现错误");
    }

    @Override // com.tds.common.bridge.IBridge
    public void registerHandler(String str, final BridgeCallback bridgeCallback) {
        Map<String, BridgeCallback> map;
        BridgeLogger.i("Command from Engine Bridge:" + str);
        if (getActivity() == null) {
            BridgeLogger.i("Bridge must be init!");
            return;
        }
        final Command command = new Command(str);
        if (command.callback && (map = this.mBridgeCallbackMaps) != null && !map.containsKey(command.callbackId)) {
            this.mBridgeCallbackMaps.put(command.callbackId, bridgeCallback);
        }
        createEngineHandler();
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            getActivity().runOnUiThread(new Runnable() { // from class: com.tds.common.bridge.Bridge.1
                @Override // java.lang.Runnable
                public void run() {
                    Bridge.this.execute(command, bridgeCallback);
                }
            });
        } else {
            execute(command, bridgeCallback);
        }
    }

    @Override // com.tds.common.bridge.IBridge
    public void callHandler(String str) {
        registerHandler(str, new BridgeCallback() { // from class: com.tds.common.bridge.Bridge.2
            @Override // com.tds.common.bridge.BridgeCallback
            public void onResult(String str2) {
                try {
                    if (EngineUtil.isUnreal()) {
                        BridgeLogger.i("BridgeCallback send Message to Unreal Engine");
                        Bridge.nativeOnResult(str2);
                    }
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execute(final Command command, final BridgeCallback bridgeCallback) {
        BridgeLogger.i("Bridge start to execute command");
        try {
            new CommandTaskImpl().execute(command, new BridgeCallback() { // from class: com.tds.common.bridge.Bridge.3
                @Override // com.tds.common.bridge.BridgeCallback
                public void onResult(String str) {
                    Bridge.this.sendMessage(Result.newInstance(true, str, "Success", command.callbackId, command.onceTime), bridgeCallback);
                }
            });
        } catch (EngineBridgeException e) {
            BridgeLogger.i("BridgeException:" + e.getClass().getName());
            sendMessage(Result.newInstance(false, null, e.getMessage(), command.callbackId, command.onceTime), bridgeCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(Result result, BridgeCallback bridgeCallback) {
        if (this.mEngineHandler == null) {
            BridgeLogger.i("EngineHandler isn't init, BridgeCallback send Message to Engine");
            bridgeCallback.onResult(result.toJSON());
            return;
        }
        Message messageObtain = Message.obtain();
        messageObtain.obj = result;
        BridgeLogger.i("EngineHandler send Message to Engine");
        if (!this.mEngineHandler.sendMessage(messageObtain)) {
            throw new RejectedExecutionException("Current Thread is Shutting Down");
        }
    }

    private void createEngineHandler() {
        if (EngineUtil.isUnreal()) {
            return;
        }
        if (Thread.currentThread() == Looper.getMainLooper().getThread() || this.mEngineHandler != null) {
            BridgeLogger.i("Bridge Engine Handler already init or Current Thread is main Thread");
            return;
        }
        if (Looper.myLooper() != null) {
            BridgeLogger.i("Looper is already prepare,start to create EngineHandler");
            constructorEngineHandler(Looper.myLooper());
            return;
        }
        BridgeLogger.i("Looper prepare,start to create EngineHandler");
        Looper.prepare();
        constructorEngineHandler(Looper.myLooper());
        StringBuilder sb = new StringBuilder();
        sb.append("Looper start loop:");
        Looper looperMyLooper = Looper.myLooper();
        Objects.requireNonNull(looperMyLooper);
        sb.append(looperMyLooper.getThread());
        BridgeLogger.i(sb.toString());
        Looper.loop();
    }

    private void constructorEngineHandler(Looper looper) {
        BridgeLogger.i("初始化 Bridge Thread Handler:" + Thread.currentThread().getName());
        this.mEngineHandler = new Handler(looper, new Handler.Callback() { // from class: com.tds.common.bridge.Bridge.4
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                BridgeLogger.i("Bridge Engine Handler Thread:" + Thread.currentThread().getName() + "\n Message:" + message.obj);
                if (message.obj == null) {
                    return false;
                }
                Result result = (Result) message.obj;
                BridgeCallback bridgeCallback = (BridgeCallback) Bridge.this.mBridgeCallbackMaps.get(result.callbackId);
                if (bridgeCallback == null) {
                    return false;
                }
                bridgeCallback.onResult(result.toJSON());
                if (result.onceTime) {
                    BridgeLogger.i("Bridge Engine CallbackHolder remove currentCallback:" + result.callbackId);
                    Bridge.this.mBridgeCallbackMaps.remove(result.callbackId);
                }
                BridgeLogger.i("Bridge Engine Callback Holder Last:" + Bridge.this.mBridgeCallbackMaps.size());
                return false;
            }
        });
        BridgeLogger.i("启动 Looper:" + Thread.currentThread().getId());
    }

    public Activity getActivity() {
        return this.mWeakReference.get();
    }
}
