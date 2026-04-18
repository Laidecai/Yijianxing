package com.tds.common.permission;

import android.app.Fragment;
import android.content.Intent;
import android.os.Handler;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class PermissionFragment extends Fragment implements FragmentLifecycle {
    public static final String TAG = "PermissionFragment";
    private FragmentDelegate mFragmentDelegate;

    public PermissionFragment() {
        this(new FragmentDelegateImpl());
    }

    public PermissionFragment(FragmentDelegate fragmentDelegate) {
        this.mFragmentDelegate = fragmentDelegate;
    }

    public void requestPermission(final Set<String> set, final RequestPermissionCallback requestPermissionCallback, final PermissionConfig permissionConfig) {
        new Handler().post(new Runnable() { // from class: com.tds.common.permission.PermissionFragment.1
            @Override // java.lang.Runnable
            public void run() {
                PermissionFragment.this.mFragmentDelegate.requestPermission(set, requestPermissionCallback, permissionConfig);
            }
        });
    }

    @Override // com.tds.common.permission.FragmentLifecycle
    public void start() {
        this.mFragmentDelegate.bind(this);
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        start();
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mFragmentDelegate.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.mFragmentDelegate.onRequestPermissionsResult(i, strArr, iArr);
    }
}
