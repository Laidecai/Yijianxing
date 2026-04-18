package tds.androidx.viewpager;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class FragmentCompat {
    static final FragmentCompatImpl IMPL;
    private static PermissionCompatDelegate sDelegate;

    interface FragmentCompatImpl {
        void requestPermissions(Fragment fragment, String[] strArr, int i);

        void setUserVisibleHint(Fragment fragment, boolean z);

        boolean shouldShowRequestPermissionRationale(Fragment fragment, String str);
    }

    @Deprecated
    public interface OnRequestPermissionsResultCallback {
        @Deprecated
        void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);
    }

    @Deprecated
    public interface PermissionCompatDelegate {
        @Deprecated
        boolean requestPermissions(Fragment fragment, String[] strArr, int i);
    }

    @Deprecated
    public FragmentCompat() {
    }

    static class FragmentCompatBaseImpl implements FragmentCompatImpl {
        @Override // tds.androidx.viewpager.FragmentCompat.FragmentCompatImpl
        public void setUserVisibleHint(Fragment fragment, boolean z) {
        }

        @Override // tds.androidx.viewpager.FragmentCompat.FragmentCompatImpl
        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
            return false;
        }

        FragmentCompatBaseImpl() {
        }

        /* JADX INFO: renamed from: tds.androidx.viewpager.FragmentCompat$FragmentCompatBaseImpl$1 */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ Fragment val$fragment;
            final /* synthetic */ String[] val$permissions;
            final /* synthetic */ int val$requestCode;

            AnonymousClass1(String[] strArr, Fragment fragment, int i) {
                strArr = strArr;
                fragment = fragment;
                i = i;
            }

            @Override // java.lang.Runnable
            public void run() {
                int[] iArr = new int[strArr.length];
                Activity activity = fragment.getActivity();
                if (activity != null) {
                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();
                    int length = strArr.length;
                    for (int i = 0; i < length; i++) {
                        iArr[i] = packageManager.checkPermission(strArr[i], packageName);
                    }
                } else {
                    Arrays.fill(iArr, -1);
                }
                ((OnRequestPermissionsResultCallback) fragment).onRequestPermissionsResult(i, strArr, iArr);
            }
        }

        @Override // tds.androidx.viewpager.FragmentCompat.FragmentCompatImpl
        public void requestPermissions(Fragment fragment, String[] strArr, int i) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: tds.androidx.viewpager.FragmentCompat.FragmentCompatBaseImpl.1
                final /* synthetic */ Fragment val$fragment;
                final /* synthetic */ String[] val$permissions;
                final /* synthetic */ int val$requestCode;

                AnonymousClass1(String[] strArr2, Fragment fragment2, int i2) {
                    strArr = strArr2;
                    fragment = fragment2;
                    i = i2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    int[] iArr = new int[strArr.length];
                    Activity activity = fragment.getActivity();
                    if (activity != null) {
                        PackageManager packageManager = activity.getPackageManager();
                        String packageName = activity.getPackageName();
                        int length = strArr.length;
                        for (int i2 = 0; i2 < length; i2++) {
                            iArr[i2] = packageManager.checkPermission(strArr[i2], packageName);
                        }
                    } else {
                        Arrays.fill(iArr, -1);
                    }
                    ((OnRequestPermissionsResultCallback) fragment).onRequestPermissionsResult(i, strArr, iArr);
                }
            });
        }
    }

    static class FragmentCompatApi15Impl extends FragmentCompatBaseImpl {
        FragmentCompatApi15Impl() {
        }

        @Override // tds.androidx.viewpager.FragmentCompat.FragmentCompatBaseImpl, tds.androidx.viewpager.FragmentCompat.FragmentCompatImpl
        public void setUserVisibleHint(Fragment fragment, boolean z) {
            fragment.setUserVisibleHint(z);
        }
    }

    static class FragmentCompatApi23Impl extends FragmentCompatApi15Impl {
        FragmentCompatApi23Impl() {
        }

        @Override // tds.androidx.viewpager.FragmentCompat.FragmentCompatBaseImpl, tds.androidx.viewpager.FragmentCompat.FragmentCompatImpl
        public void requestPermissions(Fragment fragment, String[] strArr, int i) {
            fragment.requestPermissions(strArr, i);
        }

        @Override // tds.androidx.viewpager.FragmentCompat.FragmentCompatBaseImpl, tds.androidx.viewpager.FragmentCompat.FragmentCompatImpl
        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
            return fragment.shouldShowRequestPermissionRationale(str);
        }
    }

    static class FragmentCompatApi24Impl extends FragmentCompatApi23Impl {
        FragmentCompatApi24Impl() {
        }

        @Override // tds.androidx.viewpager.FragmentCompat.FragmentCompatApi15Impl, tds.androidx.viewpager.FragmentCompat.FragmentCompatBaseImpl, tds.androidx.viewpager.FragmentCompat.FragmentCompatImpl
        public void setUserVisibleHint(Fragment fragment, boolean z) {
            fragment.setUserVisibleHint(z);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = new FragmentCompatApi24Impl();
        } else if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new FragmentCompatApi23Impl();
        } else {
            IMPL = new FragmentCompatApi15Impl();
        }
    }

    @Deprecated
    public static void setPermissionCompatDelegate(PermissionCompatDelegate permissionCompatDelegate) {
        sDelegate = permissionCompatDelegate;
    }

    @Deprecated
    public static PermissionCompatDelegate getPermissionCompatDelegate() {
        return sDelegate;
    }

    @Deprecated
    public static void setMenuVisibility(Fragment fragment, boolean z) {
        fragment.setMenuVisibility(z);
    }

    @Deprecated
    public static void setUserVisibleHint(Fragment fragment, boolean z) {
        IMPL.setUserVisibleHint(fragment, z);
    }

    @Deprecated
    public static void requestPermissions(Fragment fragment, String[] strArr, int i) {
        PermissionCompatDelegate permissionCompatDelegate = sDelegate;
        if (permissionCompatDelegate == null || !permissionCompatDelegate.requestPermissions(fragment, strArr, i)) {
            IMPL.requestPermissions(fragment, strArr, i);
        }
    }

    @Deprecated
    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) {
        return IMPL.shouldShowRequestPermissionRationale(fragment, str);
    }
}
