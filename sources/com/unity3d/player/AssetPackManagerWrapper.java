package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.alipay.sdk.app.PayTask;
import com.google.android.play.core.assetpacks.AssetPackLocation;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;
import com.google.android.play.core.assetpacks.AssetPackState;
import com.google.android.play.core.assetpacks.AssetPackStateUpdateListener;
import com.google.android.play.core.assetpacks.AssetPackStates;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.RuntimeExecutionException;
import com.google.android.play.core.tasks.Task;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class AssetPackManagerWrapper {
    private static AssetPackManagerWrapper a;
    private AssetPackManager b;
    private HashSet c;
    private Object d;

    private static class a implements Runnable {
        private Set a;
        private String b;
        private int c;
        private long d;
        private long e;
        private int f;
        private int g;

        a(Set set, String str, int i, long j, long j2, int i2, int i3) {
            this.a = set;
            this.b = str;
            this.c = i;
            this.d = j;
            this.e = j2;
            this.f = i2;
            this.g = i3;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                ((IAssetPackManagerDownloadStatusCallback) it.next()).onStatusUpdate(this.b, this.c, this.d, this.e, this.f, this.g);
            }
        }
    }

    private class b implements AssetPackStateUpdateListener {
        private HashSet b;
        private Looper c;

        public b(AssetPackManagerWrapper assetPackManagerWrapper, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
            this(iAssetPackManagerDownloadStatusCallback, Looper.myLooper());
        }

        public b(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback, Looper looper) {
            HashSet hashSet = new HashSet();
            this.b = hashSet;
            hashSet.add(iAssetPackManagerDownloadStatusCallback);
            this.c = looper;
        }

        private static Set a(HashSet hashSet) {
            return (Set) hashSet.clone();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public synchronized void onStateUpdate(AssetPackState assetPackState) {
            if (assetPackState.status() == 4 || assetPackState.status() == 5 || assetPackState.status() == 0) {
                synchronized (AssetPackManagerWrapper.a) {
                    AssetPackManagerWrapper.this.c.remove(assetPackState.name());
                    if (AssetPackManagerWrapper.this.c.isEmpty()) {
                        AssetPackManagerWrapper assetPackManagerWrapper = AssetPackManagerWrapper.this;
                        assetPackManagerWrapper.unregisterDownloadStatusListener(assetPackManagerWrapper.d);
                        AssetPackManagerWrapper.c(AssetPackManagerWrapper.this);
                    }
                }
            }
            if (this.b.size() == 0) {
                return;
            }
            new Handler(this.c).post(new a(a(this.b), assetPackState.name(), assetPackState.status(), assetPackState.totalBytesToDownload(), assetPackState.bytesDownloaded(), assetPackState.transferProgressPercentage(), assetPackState.errorCode()));
        }

        public final synchronized void a(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
            this.b.add(iAssetPackManagerDownloadStatusCallback);
        }
    }

    private static class c implements OnSuccessListener {
        private IAssetPackManagerMobileDataConfirmationCallback a;
        private Looper b = Looper.myLooper();

        private static class a implements Runnable {
            private IAssetPackManagerMobileDataConfirmationCallback a;
            private boolean b;

            a(IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback, boolean z) {
                this.a = iAssetPackManagerMobileDataConfirmationCallback;
                this.b = z;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.a.onMobileDataConfirmationResult(this.b);
            }
        }

        public c(IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback) {
            this.a = iAssetPackManagerMobileDataConfirmationCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onSuccess(Integer num) {
            if (this.a != null) {
                new Handler(this.b).post(new a(this.a, num.intValue() == -1));
            }
        }
    }

    private static class d implements OnCompleteListener {
        private IAssetPackManagerDownloadStatusCallback a;
        private Looper b = Looper.myLooper();
        private String c;

        public d(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback, String str) {
            this.a = iAssetPackManagerDownloadStatusCallback;
            this.c = str;
        }

        private void a(String str, int i, int i2, long j) {
            new Handler(this.b).post(new a(Collections.singleton(this.a), str, i, j, i == 4 ? j : 0L, 0, i2));
        }

        public final void onComplete(Task task) {
            try {
                AssetPackStates assetPackStates = (AssetPackStates) task.getResult();
                Map mapPackStates = assetPackStates.packStates();
                if (mapPackStates.size() == 0) {
                    return;
                }
                for (AssetPackState assetPackState : mapPackStates.values()) {
                    if (assetPackState.errorCode() != 0 || assetPackState.status() == 4 || assetPackState.status() == 5 || assetPackState.status() == 0) {
                        a(assetPackState.name(), assetPackState.status(), assetPackState.errorCode(), assetPackStates.totalBytes());
                    } else {
                        AssetPackManagerWrapper.a.a(assetPackState.name(), this.a, this.b);
                    }
                }
            } catch (RuntimeExecutionException e) {
                a(this.c, 0, e.getErrorCode(), 0L);
            }
        }
    }

    private static class e implements OnCompleteListener {
        private IAssetPackManagerStatusQueryCallback a;
        private Looper b = Looper.myLooper();
        private String[] c;

        private static class a implements Runnable {
            private IAssetPackManagerStatusQueryCallback a;
            private long b;
            private String[] c;
            private int[] d;
            private int[] e;

            a(IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback, long j, String[] strArr, int[] iArr, int[] iArr2) {
                this.a = iAssetPackManagerStatusQueryCallback;
                this.b = j;
                this.c = strArr;
                this.d = iArr;
                this.e = iArr2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.a.onStatusResult(this.b, this.c, this.d, this.e);
            }
        }

        public e(IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback, String[] strArr) {
            this.a = iAssetPackManagerStatusQueryCallback;
            this.c = strArr;
        }

        public final void onComplete(Task task) {
            if (this.a == null) {
                return;
            }
            int i = 0;
            try {
                AssetPackStates assetPackStates = (AssetPackStates) task.getResult();
                Map mapPackStates = assetPackStates.packStates();
                int size = mapPackStates.size();
                String[] strArr = new String[size];
                int[] iArr = new int[size];
                int[] iArr2 = new int[size];
                for (AssetPackState assetPackState : mapPackStates.values()) {
                    strArr[i] = assetPackState.name();
                    iArr[i] = assetPackState.status();
                    iArr2[i] = assetPackState.errorCode();
                    i++;
                }
                new Handler(this.b).post(new a(this.a, assetPackStates.totalBytes(), strArr, iArr, iArr2));
            } catch (RuntimeExecutionException e) {
                String message = e.getMessage();
                for (String str : this.c) {
                    if (message.contains(str)) {
                        new Handler(this.b).post(new a(this.a, 0L, new String[]{str}, new int[]{0}, new int[]{e.getErrorCode()}));
                        return;
                    }
                }
                String[] strArr2 = this.c;
                int[] iArr3 = new int[strArr2.length];
                int[] iArr4 = new int[strArr2.length];
                for (int i2 = 0; i2 < this.c.length; i2++) {
                    iArr3[i2] = 0;
                    iArr4[i2] = e.getErrorCode();
                }
                new Handler(this.b).post(new a(this.a, 0L, this.c, iArr3, iArr4));
            }
        }
    }

    private AssetPackManagerWrapper(Context context) {
        if (a != null) {
            throw new RuntimeException("AssetPackManagerWrapper should be created only once. Use getInstance() instead.");
        }
        try {
            this.b = AssetPackManagerFactory.getInstance(context);
        } catch (NoClassDefFoundError unused) {
            this.b = null;
        }
        this.c = new HashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback, Looper looper) {
        synchronized (a) {
            Object obj = this.d;
            if (obj == null) {
                b bVar = new b(iAssetPackManagerDownloadStatusCallback, looper);
                this.b.registerListener(bVar);
                this.d = bVar;
            } else {
                ((b) obj).a(iAssetPackManagerDownloadStatusCallback);
            }
            this.c.add(str);
            this.b.fetch(Collections.singletonList(str));
        }
    }

    private void b() {
        if (playCoreApiMissing()) {
            throw new RuntimeException("AssetPackManager API is not available! Make sure your gradle project includes \"com.google.android.play:core\" dependency.");
        }
    }

    static /* synthetic */ Object c(AssetPackManagerWrapper assetPackManagerWrapper) {
        assetPackManagerWrapper.d = null;
        return null;
    }

    public static synchronized AssetPackManagerWrapper getInstance() {
        AssetPackManagerWrapper assetPackManagerWrapper;
        while (true) {
            assetPackManagerWrapper = a;
            if (assetPackManagerWrapper != null) {
                break;
            }
            try {
                AssetPackManagerWrapper.class.wait(PayTask.j);
            } catch (InterruptedException e2) {
                com.unity3d.player.d.Log(6, e2.getMessage());
            }
        }
        if (assetPackManagerWrapper == null) {
            throw new RuntimeException("AssetPackManagerWrapper is not yet initialised.");
        }
        return assetPackManagerWrapper;
    }

    public static synchronized AssetPackManagerWrapper init(Context context) {
        if (a != null) {
            throw new RuntimeException("AssetPackManagerWrapper.init() should be called only once. Use getInstance() instead.");
        }
        a = new AssetPackManagerWrapper(context);
        AssetPackManagerWrapper.class.notifyAll();
        return a;
    }

    public void cancelAssetPackDownload(String str) {
        cancelAssetPackDownloads(new String[]{str});
    }

    public void cancelAssetPackDownloads(String[] strArr) {
        b();
        this.b.cancel(Arrays.asList(strArr));
    }

    public void downloadAssetPack(String str, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        downloadAssetPacks(new String[]{str}, iAssetPackManagerDownloadStatusCallback);
    }

    public void downloadAssetPacks(String[] strArr, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        b();
        for (String str : strArr) {
            this.b.getPackStates(Collections.singletonList(str)).addOnCompleteListener(new d(iAssetPackManagerDownloadStatusCallback, str));
        }
    }

    public String getAssetPackPath(String str) {
        b();
        AssetPackLocation packLocation = this.b.getPackLocation(str);
        return packLocation == null ? "" : packLocation.assetsPath();
    }

    public void getAssetPackState(String str, IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback) {
        getAssetPackStates(new String[]{str}, iAssetPackManagerStatusQueryCallback);
    }

    public void getAssetPackStates(String[] strArr, IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback) {
        b();
        this.b.getPackStates(Arrays.asList(strArr)).addOnCompleteListener(new e(iAssetPackManagerStatusQueryCallback, strArr));
    }

    public boolean playCoreApiMissing() {
        return this.b == null;
    }

    public Object registerDownloadStatusListener(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        b();
        b bVar = new b(this, iAssetPackManagerDownloadStatusCallback);
        this.b.registerListener(bVar);
        return bVar;
    }

    public void removeAssetPack(String str) {
        b();
        this.b.removePack(str);
    }

    public void requestToUseMobileData(Activity activity, IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback) {
        b();
        this.b.showCellularDataConfirmation(activity).addOnSuccessListener(new c(iAssetPackManagerMobileDataConfirmationCallback));
    }

    public void unregisterDownloadStatusListener(Object obj) {
        b();
        if (obj instanceof b) {
            this.b.unregisterListener((b) obj);
        }
    }
}
