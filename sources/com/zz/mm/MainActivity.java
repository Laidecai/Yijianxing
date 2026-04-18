package com.zz.mm;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends UnityPlayerActivity {
    public static MainActivity instance;
    private IWXAPI msgApi;

    public int GetSum(int i, int i2) {
        return i + i2;
    }

    @Override // com.unity3d.player.UnityPlayerActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        instance = this;
    }

    public void SayHi(final String str) {
        runOnUiThread(new Runnable() { // from class: com.zz.mm.MainActivity.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(MainActivity.this.getApplicationContext(), str, 0).show();
                UnityPlayer.UnitySendMessage("GamePay", "AndroidResult", "data returned");
            }
        });
    }

    public static void SendUnity(String str) {
        UnityPlayer.UnitySendMessage("UnityAndroidConnect", "AndroidCallUnityMsg", "我是Android传来的消息:" + str);
    }

    public void weichatPay(String str, String str2, String str3, String str4, String str5, String str6) {
        this.msgApi = WXAPIFactory.createWXAPI(this, str);
        PayReq payReq = new PayReq();
        payReq.appId = str;
        payReq.partnerId = str2;
        payReq.prepayId = str3;
        payReq.nonceStr = str4;
        payReq.timeStamp = str5;
        payReq.sign = str6;
        payReq.signType = "MD5";
        payReq.packageValue = "Sign=WXPay";
        Log.d("Unity", payReq.checkArgs() + "");
        Log.d("Unity", "onPay Start ");
        this.msgApi.sendReq(payReq);
    }
}
