package com.taptap.sdk.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.alipay.sdk.packet.e;
import com.taptap.sdk.LoginRequest;
import com.taptap.sdk.LoginResponse;
import com.taptap.sdk.TapLoginInnerConfig;
import com.taptap.sdk.constant.LoginConstants;
import com.taptap.sdk.model.CloudGameInitializeFinish;
import com.taptap.sdk.model.CloudGameLoginResponse;
import com.taptap.sdk.model.CloudGameReceiptMessage;
import com.taptap.sdk.tracker.TapTapLoginTrackerHelper;
import com.tds.common.log.constants.CommonParam;
import com.tds.common.tracker.annotations.Login;
import com.unity.purchasing.BuildConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CloudGameHandler extends BaseHandler {
    public static final String GSON_PARSE_ERROR_MESSAGE = "Gson parse JsonSyntaxException";
    private static final int MESSAGE_FROM_CLIENT = 10001;
    public static final String RES_INIT_MESSAGE = "initialize_finish";
    public static final String RES_MIDDLE_LAYER_INIT_FINISH = "middle_layer_init_finish";
    public static final String RES_RECEIPT_MESSAGE = "message_result";
    public static final String RES_TYPE_LOGIN = "login_taptap_finish";
    private ActivityDelegate activityDelegate;
    private String clientLoginVersion;
    private CloudGameInitializeFinish cloudGameInitializeFinish;
    private Message loginMessage;
    private LoginRequest loginRequest;
    private Messenger messenger;
    private OnCloudGameLoginResult onCloudGameLoginResult;
    private String loginRequestMid = UUID.randomUUID().toString();
    private String initializeMid = UUID.randomUUID().toString();
    private ArrayList<String> sdkLoginVersionArrayList = new ArrayList<>();
    private Messenger replyMessenger = new Messenger(new Handler(Looper.getMainLooper()) { // from class: com.taptap.sdk.ui.CloudGameHandler.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                String string = message.getData().getString(CommonParam.MESSAGE);
                if (CloudGameHandler.GSON_PARSE_ERROR_MESSAGE.equals(string)) {
                    CloudGameHandler.this.activityDelegate.setResult(-1, new LoginResponse(null, CloudGameHandler.this.loginRequest.getState(), CloudGameHandler.GSON_PARSE_ERROR_MESSAGE, null, false).toIntent(null));
                    CloudGameHandler.this.activityDelegate.finish();
                } else {
                    JSONObject jSONObject = new JSONObject(string);
                    String string2 = jSONObject.getString(e.r);
                    CloudGameHandler.this.toastMessage("中间件返回的消息: " + jSONObject.toString());
                    if (CloudGameHandler.RES_TYPE_LOGIN.equals(string2)) {
                        CloudGameHandler.this.handleLoginResponse(jSONObject);
                    } else if (CloudGameHandler.RES_RECEIPT_MESSAGE.equals(string2)) {
                        CloudGameHandler.this.handleReceiptMessage(jSONObject);
                    } else if (CloudGameHandler.RES_INIT_MESSAGE.equals(string2)) {
                        CloudGameHandler.this.handleInitializeFinishMessage(jSONObject);
                    } else if (CloudGameHandler.RES_MIDDLE_LAYER_INIT_FINISH.equals(string2)) {
                        CloudGameHandler.this.sendConfigInfoMessage();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                CloudGameHandler.this.failedCallback();
            }
        }
    });
    private CloudGameServiceConnection cloudGameServiceConnection = new CloudGameServiceConnection();

    public interface OnCloudGameLoginResult {
        void onLoginFailed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toastMessage(String str) {
    }

    public CloudGameHandler(ActivityDelegate activityDelegate) {
        this.activityDelegate = activityDelegate;
        initSDKLoginVersionList();
    }

    public void setOnCloudGameLoginResult(OnCloudGameLoginResult onCloudGameLoginResult) {
        this.onCloudGameLoginResult = onCloudGameLoginResult;
    }

    public CloudGameServiceConnection getCloudGameServiceConnection() {
        return this.cloudGameServiceConnection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLoginResponse(JSONObject jSONObject) {
        try {
            loginResultBack(CloudGameLoginResponse.parseFromJSONObject(jSONObject));
        } catch (Exception e) {
            e.printStackTrace();
            failedCallback();
        }
    }

    private void initSDKLoginVersionList() {
        this.sdkLoginVersionArrayList.add(BuildConfig.VERSION_NAME);
    }

    private String getLoginResponseVersion(String str) {
        str.hashCode();
        return !str.equals(BuildConfig.VERSION_NAME) ? LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0 : "1";
    }

    private void loginResultBack(CloudGameLoginResponse cloudGameLoginResponse) {
        LoginResponse loginResponse = new LoginResponse(null, cloudGameLoginResponse.data.state, cloudGameLoginResponse.data.errorMessage, null, cloudGameLoginResponse.data.cancel);
        loginResponse.loginVersion = getLoginResponseVersion(this.clientLoginVersion);
        loginResponse.code = cloudGameLoginResponse.data.code;
        if (GSON_PARSE_ERROR_MESSAGE.equals(cloudGameLoginResponse.data.errorMessage)) {
            failedCallback();
        } else {
            this.activityDelegate.setResult(-1, loginResponse.toIntent(cloudGameLoginResponse.data.serverUri));
            this.activityDelegate.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReceiptMessage(JSONObject jSONObject) {
        try {
            CloudGameReceiptMessage fromJSONObject = CloudGameReceiptMessage.parseFromJSONObject(jSONObject);
            if (fromJSONObject.data.success) {
                return;
            }
            if (this.loginRequestMid.equals(fromJSONObject.data.messageId) || this.initializeMid.equals(fromJSONObject.data.messageId)) {
                failedCallback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            failedCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInitializeFinishMessage(JSONObject jSONObject) {
        try {
            CloudGameInitializeFinish fromJSONObject = CloudGameInitializeFinish.parseFromJSONObject(jSONObject);
            this.cloudGameInitializeFinish = fromJSONObject;
            if (checkSupportLogin(fromJSONObject)) {
                sendLoginMessage();
            } else {
                failedCallback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            failedCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void failedCallback() {
        OnCloudGameLoginResult onCloudGameLoginResult = this.onCloudGameLoginResult;
        if (onCloudGameLoginResult != null) {
            onCloudGameLoginResult.onLoginFailed();
        }
    }

    private boolean checkSupportLogin(CloudGameInitializeFinish cloudGameInitializeFinish) {
        if (cloudGameInitializeFinish.data == null || cloudGameInitializeFinish.data.cgpn == null || cloudGameInitializeFinish.data.cgpn.login == null || cloudGameInitializeFinish.data.cgpn.login.isEmpty()) {
            return false;
        }
        String str = cloudGameInitializeFinish.data.cgpn.login;
        this.clientLoginVersion = str;
        return this.sdkLoginVersionArrayList.contains(str);
    }

    @Override // com.taptap.sdk.ui.BaseHandler
    void authorize(LoginRequest loginRequest) throws Exception {
        this.loginRequest = loginRequest;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(e.r, "login_taptap");
        jSONObject.put("message_id", this.loginRequestMid);
        JSONObject jSONObjectCreateCloudLoginJsonObject = LoginUtils.createCloudLoginJsonObject(LoginUtils.createLoginBundle(loginRequest));
        jSONObjectCreateCloudLoginJsonObject.put(com.tds.common.tracker.constants.CommonParam.CLIENT_ID, TapLoginInnerConfig.getClientId());
        JSONArray jSONArray = new JSONArray();
        for (String str : loginRequest.getPermissions()) {
            jSONArray.put(str);
        }
        jSONObjectCreateCloudLoginJsonObject.put("scopes", jSONArray);
        jSONObjectCreateCloudLoginJsonObject.put("state", loginRequest.getState());
        jSONObjectCreateCloudLoginJsonObject.put(com.tds.common.tracker.constants.CommonParam.SDK_VERSION, loginRequest.getVersionCode());
        jSONObjectCreateCloudLoginJsonObject.put("portrait", this.activityDelegate.isPortrait());
        jSONObjectCreateCloudLoginJsonObject.put("sdk_info", loginRequest.getInfo());
        jSONObjectCreateCloudLoginJsonObject.put("response_type", loginRequest.getResponseType());
        jSONObjectCreateCloudLoginJsonObject.put("code_challenge", loginRequest.getCodeChallenge());
        jSONObjectCreateCloudLoginJsonObject.put("code_challenge_method", loginRequest.getCodeChallengeMethod());
        jSONObject.put("data", jSONObjectCreateCloudLoginJsonObject);
        this.loginMessage = createMessage(jSONObject.toString());
        TapTapLoginTrackerHelper.authorizationOpen(Login.CLOUD_PLAY_TYPE);
        CloudGameInitializeFinish cloudGameInitializeFinish = this.cloudGameInitializeFinish;
        if (cloudGameInitializeFinish != null && !checkSupportLogin(cloudGameInitializeFinish)) {
            failedCallback();
            return;
        }
        Messenger messenger = this.messenger;
        if (messenger != null && this.cloudGameInitializeFinish != null) {
            messenger.send(this.loginMessage);
        }
        TapTapLoginTrackerHelper.authorizationBack();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendConfigInfoMessage() {
        try {
            Object objGenerateSDKInfo = LoginRequest.generateSDKInfo(this.activityDelegate.block.getActivity(), this.loginRequest.isPreApproved());
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(e.r, "initialize");
            jSONObject.put("message_id", this.initializeMid);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("package_name", this.activityDelegate.block.getActivity().getPackageName());
            jSONObject2.put("sdk_info", objGenerateSDKInfo);
            JSONObject jSONObject3 = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            Iterator<String> it = this.sdkLoginVersionArrayList.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next());
            }
            jSONObject3.put("login", jSONArray);
            jSONObject2.put("CGPN", jSONObject3);
            jSONObject.put("data", jSONObject2);
            this.messenger.send(createMessage(jSONObject.toString()));
        } catch (Exception unused) {
            failedCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMiddleLayerInitMessage() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(e.r, "middle_layer_init");
            jSONObject.put("package_name", this.activityDelegate.block.getActivity().getPackageName());
            this.messenger.send(createMessage(jSONObject.toString()));
        } catch (Exception unused) {
            failedCallback();
        }
    }

    private Message createMessage(String str) {
        Message messageObtain = Message.obtain();
        messageObtain.what = MESSAGE_FROM_CLIENT;
        Bundle bundle = new Bundle();
        bundle.putString(CommonParam.MESSAGE, str);
        messageObtain.setData(bundle);
        messageObtain.replyTo = this.replyMessenger;
        return messageObtain;
    }

    public boolean bindCloudGameService() {
        ActivityDelegate activityDelegate = this.activityDelegate;
        if (activityDelegate == null || activityDelegate.block == null || this.activityDelegate.block.getActivity() == null) {
            return false;
        }
        Intent intent = new Intent("com.cloud.taptap.gaming.daemon");
        intent.setPackage("com.cloud.taptap.gaming");
        return this.activityDelegate.block.getActivity().bindService(intent, this.cloudGameServiceConnection, 1);
    }

    private void sendLoginMessage() {
        Message message = this.loginMessage;
        if (message == null) {
            return;
        }
        try {
            this.messenger.send(message);
        } catch (Exception unused) {
            failedCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CloudGameServiceConnection implements ServiceConnection {
        private CloudGameServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CloudGameHandler.this.messenger = new Messenger(iBinder);
            CloudGameHandler.this.sendMiddleLayerInitMessage();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            CloudGameHandler.this.failedCallback();
        }
    }
}
