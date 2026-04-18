package com.unity3d.player;

import android.content.Context;
import com.unity3d.player.a;

/* JADX INFO: loaded from: classes.dex */
public class AudioVolumeHandler implements a.b {
    private a a;

    AudioVolumeHandler(Context context) {
        a aVar = new a(context);
        this.a = aVar;
        aVar.a(this);
    }

    public final void a() {
        this.a.a();
        this.a = null;
    }

    @Override // com.unity3d.player.a.b
    public final native void onAudioVolumeChanged(int i);
}
