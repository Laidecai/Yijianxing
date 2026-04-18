package com.unity3d.player;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
final class a {
    private final Context a;
    private final AudioManager b;
    private C0025a c;

    /* JADX INFO: renamed from: com.unity3d.player.a$a, reason: collision with other inner class name */
    private class C0025a extends ContentObserver {
        private final b b;
        private final AudioManager c;
        private final int d;
        private int e;

        public C0025a(Handler handler, AudioManager audioManager, int i, b bVar) {
            super(handler);
            this.c = audioManager;
            this.d = 3;
            this.b = bVar;
            this.e = audioManager.getStreamVolume(3);
        }

        @Override // android.database.ContentObserver
        public final boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            int streamVolume;
            AudioManager audioManager = this.c;
            if (audioManager == null || this.b == null || (streamVolume = audioManager.getStreamVolume(this.d)) == this.e) {
                return;
            }
            this.e = streamVolume;
            this.b.onAudioVolumeChanged(streamVolume);
        }
    }

    public interface b {
        void onAudioVolumeChanged(int i);
    }

    public a(Context context) {
        this.a = context;
        this.b = (AudioManager) context.getSystemService("audio");
    }

    public final void a() {
        if (this.c != null) {
            this.a.getContentResolver().unregisterContentObserver(this.c);
            this.c = null;
        }
    }

    public final void a(b bVar) {
        this.c = new C0025a(new Handler(), this.b, 3, bVar);
        this.a.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.c);
    }
}
