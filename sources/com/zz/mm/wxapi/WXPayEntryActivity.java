package com.zz.mm.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unity3d.player.UnityPlayer;

/* JADX INFO: loaded from: classes.dex */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onReq(BaseReq baseReq) {
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("Unity", "onPayStart Init ");
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(this, "wx04a9e2d69a3458fe");
        this.api = iwxapiCreateWXAPI;
        iwxapiCreateWXAPI.handleIntent(getIntent(), this);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Unity", "onPay Intent Enter ");
        setIntent(intent);
        this.api.handleIntent(intent, this);
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onResp(BaseResp baseResp) {
        Log.d("Unity", "onPayFinish, errCode = " + baseResp.errCode);
        UnityPlayer.UnitySendMessage("GamePay", "WXPayResult", baseResp.errCode + "");
        finish();
    }
}
