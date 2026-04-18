package com.unity3d.splash;

import android.app.Activity;
import com.unity3d.splash.mediation.IUnityAdsExtendedListener;
import com.unity3d.splash.services.ads.UnityAdsImplementation;

/* JADX INFO: loaded from: classes.dex */
public final class UnityAds {
    public static final String LAUNCH_SCREEN_PLACEMENT = "unity-launch-screen";
    private static String defaultGameId = "3194466";
    private static IUnityAdsListener launchScreenAdsListener = null;
    private static boolean shownOnce = false;
    private static boolean skipLaunchScreenAds = false;

    public enum FinishState {
        ERROR,
        SKIPPED,
        COMPLETED
    }

    public interface IAdsFinishListener {
        void onUnityAdsFinish(String str, FinishState finishState);
    }

    static class LaunchScreenAdsListener implements IUnityAdsExtendedListener {
        private Activity activity;
        private IAdsFinishListener adsFinishListener;

        public LaunchScreenAdsListener(Activity activity, IAdsFinishListener iAdsFinishListener) {
            this.activity = activity;
            this.adsFinishListener = iAdsFinishListener;
        }

        @Override // com.unity3d.splash.mediation.IUnityAdsExtendedListener
        public void onUnityAdsClick(String str) {
        }

        @Override // com.unity3d.splash.IUnityAdsListener
        public void onUnityAdsError(UnityAdsError unityAdsError, String str) {
            this.adsFinishListener.onUnityAdsFinish(null, FinishState.ERROR);
        }

        @Override // com.unity3d.splash.IUnityAdsListener
        public void onUnityAdsFinish(String str, FinishState finishState) {
            this.adsFinishListener.onUnityAdsFinish(str, finishState);
        }

        @Override // com.unity3d.splash.mediation.IUnityAdsExtendedListener
        public void onUnityAdsPlacementStateChanged(String str, PlacementState placementState, PlacementState placementState2) {
        }

        @Override // com.unity3d.splash.IUnityAdsListener
        public void onUnityAdsReady(String str) {
            if (!UnityAds.LAUNCH_SCREEN_PLACEMENT.equalsIgnoreCase(str) || UnityAds.skipLaunchScreenAds || UnityAds.shownOnce) {
                return;
            }
            UnityAdsImplementation.show(this.activity, str);
            boolean unused = UnityAds.shownOnce = true;
        }

        @Override // com.unity3d.splash.IUnityAdsListener
        public void onUnityAdsStart(String str) {
        }
    }

    public enum PlacementState {
        READY,
        NOT_AVAILABLE,
        DISABLED,
        WAITING,
        NO_FILL
    }

    public enum UnityAdsError {
        NOT_INITIALIZED,
        INITIALIZE_FAILED,
        INVALID_ARGUMENT,
        VIDEO_PLAYER_ERROR,
        INIT_SANITY_CHECK_FAIL,
        AD_BLOCKER_DETECTED,
        FILE_IO_ERROR,
        DEVICE_ID_ERROR,
        SHOW_ERROR,
        INTERNAL_ERROR
    }

    public static void initialize(Activity activity, String str, IUnityAdsListener iUnityAdsListener) {
        UnityAdsImplementation.initialize(activity, str, iUnityAdsListener, false, false);
    }

    public static boolean isSkipLaunchScreenAds() {
        return skipLaunchScreenAds;
    }

    public static void setSkipLaunchScreenAds(boolean z) {
        skipLaunchScreenAds = z;
    }

    public static void showLaunchScreenAds(Activity activity) {
        showLaunchScreenAds(activity, defaultGameId, null);
    }

    public static void showLaunchScreenAds(Activity activity, IAdsFinishListener iAdsFinishListener) {
        showLaunchScreenAds(activity, defaultGameId, iAdsFinishListener);
    }

    public static void showLaunchScreenAds(Activity activity, String str, IAdsFinishListener iAdsFinishListener) {
        if (launchScreenAdsListener == null) {
            if (iAdsFinishListener == null) {
                iAdsFinishListener = new IAdsFinishListener() { // from class: com.unity3d.splash.UnityAds.1
                    @Override // com.unity3d.splash.UnityAds.IAdsFinishListener
                    public final void onUnityAdsFinish(String str2, FinishState finishState) {
                    }
                };
            }
            launchScreenAdsListener = new LaunchScreenAdsListener(activity, iAdsFinishListener);
        }
        if (!UnityAdsImplementation.isInitialized()) {
            UnityAdsImplementation.initialize(activity, str, launchScreenAdsListener);
        } else if (UnityAdsImplementation.isReady(LAUNCH_SCREEN_PLACEMENT)) {
            UnityAdsImplementation.show(activity, LAUNCH_SCREEN_PLACEMENT);
        }
    }
}
