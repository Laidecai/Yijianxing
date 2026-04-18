package com.tds.tapdb.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class TapTapDIDIntentService extends IntentService {
    private static final String b = "TapTapDID";
    private static final String c = "TapDB";
    public static final int d = 100000;
    public static final String e = "setUnifiedId";
    public static final String f = "getUnifiedId";
    public static final String g = "unified_id_result_param";
    public static final String h = "unified_id_saved_param";
    public static final String i = "TapTapDIDIntentService";
    private static final String j = "guid_share_preference";
    private String a;

    public TapTapDIDIntentService() {
        super(i);
    }

    public static Intent a() {
        Intent intent = new Intent("com.tds.tapdb.service.TapTapDIDIntentService");
        Bundle bundle = new Bundle();
        bundle.putString("cmd", f);
        intent.putExtras(bundle);
        return intent;
    }

    public static Intent a(Context context, String str) {
        Intent intent = new Intent(context, (Class<?>) TapTapDIDIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("cmd", e);
        bundle.putString("unified_id", str);
        intent.putExtras(bundle);
        return intent;
    }

    private void a(Intent intent) {
        Notification.Builder builder;
        Bundle extras = intent.getExtras();
        if (extras == null || e.equals(extras.getString("cmd"))) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(new NotificationChannel(b, c, 2));
            builder = new Notification.Builder(this, b);
        } else {
            builder = new Notification.Builder(this);
        }
        startForeground(10086, builder.build());
    }

    @Override // android.app.IntentService, android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        String string;
        SharedPreferences sharedPreferences = getSharedPreferences(j, 0);
        if (intent == null || intent.getExtras().getString("cmd") == null) {
            return;
        }
        String string2 = intent.getExtras().getString("cmd");
        Log.i(i, "onHandleIntent cmd " + string2);
        if (string2.equals(e)) {
            String string3 = intent.getExtras().getString("unified_id");
            ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra("receiver");
            if (TextUtils.isEmpty(string3)) {
                Log.e(i, "set empty str!");
            } else {
                this.a = string3;
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putString(h, string3);
                editorEdit.commit();
                Log.i(i, "set id success:" + this.a);
            }
            Bundle bundle = new Bundle();
            bundle.putString(g, string3);
            resultReceiver.send(d, bundle);
            return;
        }
        if (!string2.equals(f)) {
            Log.e(i, "unSupported cmd type");
            return;
        }
        ResultReceiver resultReceiver2 = (ResultReceiver) intent.getParcelableExtra("receiver");
        if (TextUtils.isEmpty(this.a)) {
            string = sharedPreferences.getString(h, "");
            this.a = string;
        } else {
            string = this.a;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString(g, string);
        Log.i(i, "get unified id success:" + string);
        resultReceiver2.send(d, bundle2);
    }

    @Override // android.app.IntentService, android.app.Service
    public void onStart(Intent intent, int i2) {
        a(intent);
        super.onStart(intent, i2);
    }

    @Override // android.app.IntentService, android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        a(intent);
        return super.onStartCommand(intent, i2, i3);
    }
}
