package com.unity3d.player;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public final class e extends Fragment {
    private final IPermissionRequestCallbacks a;
    private final Activity b;
    private final Looper c;

    class a implements Runnable {
        private IPermissionRequestCallbacks b;
        private String c;
        private int d;
        private boolean e;

        a(IPermissionRequestCallbacks iPermissionRequestCallbacks, String str, int i, boolean z) {
            this.b = iPermissionRequestCallbacks;
            this.c = str;
            this.d = i;
            this.e = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i = this.d;
            if (i != -1) {
                if (i == 0) {
                    this.b.onPermissionGranted(this.c);
                }
            } else if (this.e) {
                this.b.onPermissionDenied(this.c);
            } else {
                this.b.onPermissionDeniedAndDontAskAgain(this.c);
            }
        }
    }

    public e() {
        this.a = null;
        this.b = null;
        this.c = null;
    }

    public e(Activity activity, IPermissionRequestCallbacks iPermissionRequestCallbacks) {
        this.a = iPermissionRequestCallbacks;
        this.b = activity;
        this.c = Looper.myLooper();
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestPermissions(getArguments().getStringArray("PermissionNames"), 96489);
    }

    @Override // android.app.Fragment
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 96489) {
            return;
        }
        if (strArr.length == 0) {
            requestPermissions(getArguments().getStringArray("PermissionNames"), 96489);
            return;
        }
        for (int i2 = 0; i2 < strArr.length && i2 < iArr.length; i2++) {
            if (this.a != null && this.b != null && this.c != null) {
                String str = strArr[i2] == null ? "<null>" : strArr[i2];
                new Handler(this.c).post(new a(this.a, str, iArr[i2], this.b.shouldShowRequestPermissionRationale(str)));
            }
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.remove(this);
        fragmentTransactionBeginTransaction.commit();
    }
}
