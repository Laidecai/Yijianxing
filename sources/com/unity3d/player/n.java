package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import com.unity3d.player.m;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
final class n {
    private UnityPlayer a;
    private a c;
    private Context b = null;
    private final Semaphore d = new Semaphore(0);
    private final Lock e = new ReentrantLock();
    private m f = null;
    private int g = 2;
    private boolean h = false;
    private boolean i = false;

    /* JADX INFO: renamed from: com.unity3d.player.n$1, reason: invalid class name */
    final class AnonymousClass1 implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ boolean e;
        final /* synthetic */ long f;
        final /* synthetic */ long g;

        AnonymousClass1(String str, int i, int i2, int i3, boolean z, long j, long j2) {
            this.a = str;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = z;
            this.f = j;
            this.g = j2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (n.this.f != null) {
                d.Log(5, "Video already playing");
                n.this.g = 2;
                n.this.d.release();
            } else {
                n.this.f = new m(n.this.b, this.a, this.b, this.c, this.d, this.e, this.f, this.g, new m.a() { // from class: com.unity3d.player.n.1.1
                    @Override // com.unity3d.player.m.a
                    public final void a(int i) {
                        n.this.e.lock();
                        n.this.g = i;
                        if (i == 3 && n.this.i) {
                            n.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.n.1.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    n.this.d();
                                    n.this.a.resume();
                                }
                            });
                        }
                        if (i != 0) {
                            n.this.d.release();
                        }
                        n.this.e.unlock();
                    }
                });
                if (n.this.f != null) {
                    n.this.a.addView(n.this.f);
                }
            }
        }
    }

    public interface a {
        void a();
    }

    n(UnityPlayer unityPlayer) {
        this.a = null;
        this.a = unityPlayer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        m mVar = this.f;
        if (mVar != null) {
            this.a.removeViewFromPlayer(mVar);
            this.i = false;
            this.f.destroyPlayer();
            this.f = null;
            a aVar = this.c;
            if (aVar != null) {
                aVar.a();
            }
        }
    }

    static /* synthetic */ boolean h(n nVar) {
        nVar.i = true;
        return true;
    }

    public final void a() {
        this.e.lock();
        m mVar = this.f;
        if (mVar != null) {
            if (this.g == 0) {
                mVar.CancelOnPrepare();
            } else if (this.i) {
                boolean zA = mVar.a();
                this.h = zA;
                if (!zA) {
                    this.f.pause();
                }
            }
        }
        this.e.unlock();
    }

    public final boolean a(Context context, String str, int i, int i2, int i3, boolean z, long j, long j2, a aVar) {
        this.e.lock();
        this.c = aVar;
        this.b = context;
        this.d.drainPermits();
        this.g = 2;
        runOnUiThread(new AnonymousClass1(str, i, i2, i3, z, j, j2));
        boolean z2 = false;
        try {
            this.e.unlock();
            this.d.acquire();
            this.e.lock();
            if (this.g != 2) {
                z2 = true;
            }
        } catch (InterruptedException unused) {
        }
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.n.2
            @Override // java.lang.Runnable
            public final void run() {
                n.this.a.pause();
            }
        });
        runOnUiThread((!z2 || this.g == 3) ? new Runnable() { // from class: com.unity3d.player.n.4
            @Override // java.lang.Runnable
            public final void run() {
                n.this.d();
                n.this.a.resume();
            }
        } : new Runnable() { // from class: com.unity3d.player.n.3
            @Override // java.lang.Runnable
            public final void run() {
                if (n.this.f != null) {
                    n.this.a.addViewToPlayer(n.this.f, true);
                    n.h(n.this);
                    n.this.f.requestFocus();
                }
            }
        });
        this.e.unlock();
        return z2;
    }

    public final void b() {
        this.e.lock();
        m mVar = this.f;
        if (mVar != null && this.i && !this.h) {
            mVar.start();
        }
        this.e.unlock();
    }

    public final void c() {
        this.e.lock();
        m mVar = this.f;
        if (mVar != null) {
            mVar.updateVideoLayout();
        }
        this.e.unlock();
    }

    protected final void runOnUiThread(Runnable runnable) {
        Context context = this.b;
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        } else {
            d.Log(5, "Not running from an Activity; Ignoring execution request...");
        }
    }
}
