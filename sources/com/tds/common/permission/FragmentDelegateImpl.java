package com.tds.common.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class FragmentDelegateImpl implements FragmentDelegate, PermissionDialogCallback {
    public static final int FORWARD_TO_SETTING = 2;
    public static final int REQUEST_PERMISSION_CODE = 1;
    public static final int REQUEST_PERMISSION_FROM_SETTING = 3;
    private RequestPermissionCallback mCallback;
    private PermissionConfig mConfig;
    private Fragment mFragment;
    private Set<String> mPermissions = new HashSet();
    private Set<String> mGrantedPermissions = new HashSet();
    private Set<String> mDeniedPermissions = new HashSet();
    private Set<String> mPermanentDeniedPermissions = new HashSet();
    private Set<String> forwardPermissions = new HashSet();
    private boolean forwardSetting = false;

    @Override // com.tds.common.permission.FragmentDelegate
    public void bind(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override // com.tds.common.permission.FragmentDelegate
    public void requestPermission(final Set<String> set, final RequestPermissionCallback requestPermissionCallback, final PermissionConfig permissionConfig) {
        this.mCallback = requestPermissionCallback;
        this.mPermissions.clear();
        this.mGrantedPermissions.clear();
        this.mDeniedPermissions.clear();
        if (Build.VERSION.SDK_INT >= 23) {
            for (String str : set) {
                if (!TdsPermission.checkPermission(this.mFragment.getContext(), str)) {
                    this.mGrantedPermissions.remove(str);
                    this.mDeniedPermissions.add(str);
                } else {
                    this.mGrantedPermissions.add(str);
                    this.mDeniedPermissions.remove(str);
                }
            }
        }
        if (this.mDeniedPermissions.isEmpty()) {
            requestPermissionCallback.onResult(true, new ArrayList(set), new ArrayList());
        } else {
            PermissionDialog.newInstance(permissionConfig.intentTitle, permissionConfig.intentReason, permissionConfig.intentCancelText, permissionConfig.intentConfirmText, new PermissionDialogCallback() { // from class: com.tds.common.permission.FragmentDelegateImpl.1
                @Override // com.tds.common.permission.PermissionDialogCallback
                public void onConfirm() {
                    FragmentDelegateImpl.this.requestPermission(set, requestPermissionCallback, permissionConfig, 1);
                }

                @Override // com.tds.common.permission.PermissionDialogCallback
                public void onClose() {
                    FragmentDelegateImpl.this.onClose();
                }
            }).show(this.mFragment.getChildFragmentManager(), PermissionDialog.TAG);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPermission(Set<String> set, RequestPermissionCallback requestPermissionCallback, PermissionConfig permissionConfig, int i) {
        this.mPermissions = set;
        this.mCallback = requestPermissionCallback;
        this.mConfig = permissionConfig;
        this.forwardSetting = (TextUtils.isEmpty(permissionConfig.tipTitle) || TextUtils.isEmpty(this.mConfig.tipReason)) ? false : true;
        if (Build.VERSION.SDK_INT >= 23) {
            this.mFragment.requestPermissions((String[]) this.mPermissions.toArray(new String[0]), i);
        } else {
            requestPermissionCallback.onResult(true, new ArrayList(set), new ArrayList());
        }
    }

    @Override // com.tds.common.permission.FragmentDelegate
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1 || i == 3) {
            handlerRequestPermission(strArr, iArr, i);
        }
    }

    @Override // com.tds.common.permission.FragmentDelegate
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 2) {
            requestPermission(this.forwardPermissions, this.mCallback, this.mConfig, 3);
        }
    }

    private void handlerRequestPermission(String[] strArr, int[] iArr, int i) {
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            if (iArr[i2] == 0) {
                this.mGrantedPermissions.add(str);
                this.mDeniedPermissions.remove(str);
                this.mPermanentDeniedPermissions.remove(str);
            } else if (this.mFragment.shouldShowRequestPermissionRationale(str)) {
                this.mDeniedPermissions.add(str);
            } else {
                this.mPermanentDeniedPermissions.add(str);
                this.mDeniedPermissions.remove(str);
            }
        }
        ArrayList<String> arrayList = new ArrayList();
        arrayList.addAll(this.mPermanentDeniedPermissions);
        arrayList.addAll(this.mDeniedPermissions);
        for (String str2 : arrayList) {
            if (TdsPermission.checkPermission(this.mFragment.getActivity(), str2)) {
                this.mDeniedPermissions.remove(str2);
                this.mGrantedPermissions.add(str2);
            }
        }
        if (this.mGrantedPermissions.size() >= this.mPermissions.size() && this.mDeniedPermissions.size() <= 0) {
            RequestPermissionCallback requestPermissionCallback = this.mCallback;
            if (requestPermissionCallback != null) {
                requestPermissionCallback.onResult(true, new ArrayList(this.mGrantedPermissions), new ArrayList(this.mDeniedPermissions));
                return;
            }
            return;
        }
        if (this.mPermanentDeniedPermissions.size() <= 0) {
            RequestPermissionCallback requestPermissionCallback2 = this.mCallback;
            if (requestPermissionCallback2 != null) {
                requestPermissionCallback2.onResult(false, new ArrayList(this.mGrantedPermissions), new ArrayList(this.mDeniedPermissions));
                return;
            }
            return;
        }
        this.forwardPermissions.clear();
        this.forwardPermissions.addAll(this.mPermanentDeniedPermissions);
        if (i != 1) {
            if (i == 3) {
                this.mCallback.onResult(false, new ArrayList(this.mGrantedPermissions), new ArrayList(this.mDeniedPermissions));
            }
        } else if (this.forwardSetting) {
            showForwardSetting();
        } else {
            onConfirm();
        }
    }

    private void showForwardSetting() {
        PermissionDialog.newInstance(this.mConfig.tipTitle, this.mConfig.tipReason, this.mConfig.tipCancelText, this.mConfig.tipConfirmText, this).show(this.mFragment.getChildFragmentManager(), PermissionDialog.TAG);
    }

    @Override // com.tds.common.permission.PermissionDialogCallback
    public void onConfirm() {
        Activity activity = this.mFragment.getActivity();
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        Fragment fragment = this.mFragment;
        if (fragment != null) {
            fragment.startActivityForResult(intent, 2);
        }
    }

    @Override // com.tds.common.permission.PermissionDialogCallback
    public void onClose() {
        this.mCallback.onResult(false, new ArrayList(this.mGrantedPermissions), new ArrayList(this.mDeniedPermissions));
    }
}
