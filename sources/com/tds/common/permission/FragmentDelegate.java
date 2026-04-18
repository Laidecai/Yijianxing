package com.tds.common.permission;

import android.app.Fragment;
import android.content.Intent;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
interface FragmentDelegate {
    void bind(Fragment fragment);

    void onActivityResult(int i, int i2, Intent intent);

    void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);

    void requestPermission(Set<String> set, RequestPermissionCallback requestPermissionCallback, PermissionConfig permissionConfig);
}
