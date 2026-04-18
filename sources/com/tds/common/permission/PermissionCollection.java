package com.tds.common.permission;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class PermissionCollection {
    private Activity activity;
    private PermissionConfig config;
    private RequestPermissionCallback mCallback;
    private Set<String> requestPermissions;

    public PermissionCollection(Activity activity) {
        this.activity = activity;
    }

    public PermissionCollection(Fragment fragment) {
        this.activity = fragment.getActivity();
    }

    public PermissionCollection permission(String... strArr) {
        this.requestPermissions = new HashSet(Arrays.asList(strArr));
        return this;
    }

    public PermissionCollection forwardSetting(PermissionConfig permissionConfig) {
        this.config = permissionConfig;
        return this;
    }

    public void request(RequestPermissionCallback requestPermissionCallback) {
        this.mCallback = requestPermissionCallback;
        Activity activity = this.activity;
        if (activity != null) {
            request(activity);
        }
    }

    private void request(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        PermissionFragment permissionFragment = (PermissionFragment) fragmentManager.findFragmentByTag(PermissionFragment.TAG);
        if (permissionFragment == null) {
            permissionFragment = new PermissionFragment();
            if (!activity.isFinishing()) {
                permissionFragment.start();
            }
            if (Build.VERSION.SDK_INT >= 24) {
                fragmentManager.beginTransaction().add(permissionFragment, PermissionFragment.TAG).commitNowAllowingStateLoss();
            } else {
                fragmentManager.beginTransaction().add(permissionFragment, PermissionFragment.TAG).commitAllowingStateLoss();
            }
        }
        permissionFragment.requestPermission(this.requestPermissions, this.mCallback, this.config);
    }
}
