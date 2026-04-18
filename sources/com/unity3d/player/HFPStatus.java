package com.unity3d.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

/* JADX INFO: loaded from: classes.dex */
public class HFPStatus {
    private Context a;
    private AudioManager e;
    private BroadcastReceiver b = null;
    private Intent c = null;
    private boolean d = false;
    private int f = a.a;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    static final class a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {1, 2, 3};
    }

    public HFPStatus(Context context) {
        this.e = null;
        this.a = context;
        this.e = (AudioManager) context.getSystemService("audio");
        initHFPStatusJni();
    }

    private final native void deinitHFPStatusJni();

    private final native void initHFPStatusJni();

    public final void a() {
        deinitHFPStatusJni();
    }

    protected boolean getHFPStat() {
        return this.f == a.b;
    }

    protected void requestHFPStat() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.unity3d.player.HFPStatus.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int intExtra = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1);
                if (intExtra == 0) {
                    if (HFPStatus.this.d) {
                        HFPStatus.this.e.setMode(0);
                    }
                    HFPStatus.this.d = false;
                    return;
                }
                if (intExtra == 1) {
                    HFPStatus.this.f = a.b;
                    if (HFPStatus.this.d) {
                        HFPStatus.this.e.setMode(3);
                        return;
                    } else {
                        HFPStatus.this.e.stopBluetoothSco();
                        return;
                    }
                }
                if (intExtra != 2) {
                    return;
                }
                if (HFPStatus.this.f == a.b) {
                    HFPStatus.this.d = true;
                } else {
                    HFPStatus.this.f = a.c;
                }
            }
        };
        this.b = broadcastReceiver;
        this.c = this.a.registerReceiver(broadcastReceiver, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
        try {
            this.e.startBluetoothSco();
        } catch (NullPointerException unused) {
            d.Log(5, "startBluetoothSco() failed. no bluetooth device connected.");
        }
    }
}
