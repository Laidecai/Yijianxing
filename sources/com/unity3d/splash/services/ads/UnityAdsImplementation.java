package com.unity3d.splash.services.ads;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.tds.common.tracker.constants.CommonParam;
import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.services.IUnityServicesListener;
import com.unity3d.splash.services.UnityServices;
import com.unity3d.splash.services.ads.adunit.AdUnitOpen;
import com.unity3d.splash.services.ads.load.LoadModule;
import com.unity3d.splash.services.ads.placement.Placement;
import com.unity3d.splash.services.ads.properties.AdsProperties;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class UnityAdsImplementation {

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.UnityAdsImplementation$1 */
    static class AnonymousClass1 implements IUnityServicesListener {
        AnonymousClass1() {
        }

        @Override // com.unity3d.splash.services.IUnityServicesListener
        public final void onUnityServicesError(UnityServices.UnityServicesError unityServicesError, String str) {
            if (unityServicesError == UnityServices.UnityServicesError.INIT_SANITY_CHECK_FAIL) {
                iUnityAdsListener.onUnityAdsError(UnityAds.UnityAdsError.INIT_SANITY_CHECK_FAIL, str);
            } else if (unityServicesError == UnityServices.UnityServicesError.INVALID_ARGUMENT) {
                iUnityAdsListener.onUnityAdsError(UnityAds.UnityAdsError.INVALID_ARGUMENT, str);
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.UnityAdsImplementation$2 */
    static class AnonymousClass2 implements Runnable {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ String val$placementId;

        AnonymousClass2(Activity activity, String str) {
            activity = activity;
            str = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Display defaultDisplay = ((WindowManager) activity.getSystemService("window")).getDefaultDisplay();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("requestedOrientation", activity.getRequestedOrientation());
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("rotation", defaultDisplay.getRotation());
                Point point = new Point();
                defaultDisplay.getSize(point);
                jSONObject2.put(CommonParam.SR_WIDTH, point.x);
                jSONObject2.put(CommonParam.SR_HEIGHT, point.y);
                jSONObject.put("display", jSONObject2);
            } catch (JSONException e) {
                DeviceLog.exception("JSON error while constructing show options", e);
            }
            try {
                if (AdUnitOpen.open(str, jSONObject)) {
                    return;
                }
                UnityAdsImplementation.handleShowError(str, UnityAds.UnityAdsError.INTERNAL_ERROR, "Webapp timeout, shutting down Unity Ads");
            } catch (NoSuchMethodException e2) {
                DeviceLog.exception("Could not get callback method", e2);
                UnityAdsImplementation.handleShowError(str, UnityAds.UnityAdsError.SHOW_ERROR, "Could not get com.unity3d.ads.properties.showCallback method");
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.UnityAdsImplementation$3 */
    static class AnonymousClass3 implements Runnable {
        final /* synthetic */ String val$errorMessage;
        final /* synthetic */ String val$placementId;

        AnonymousClass3(String str, String str2) {
            str = str;
            str = str2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            for (IUnityAdsListener iUnityAdsListener : AdsProperties.getListeners()) {
                iUnityAdsListener.onUnityAdsError(unityAdsError, str);
                String str = str;
                if (str != null) {
                    iUnityAdsListener.onUnityAdsFinish(str, UnityAds.FinishState.ERROR);
                } else {
                    iUnityAdsListener.onUnityAdsFinish("", UnityAds.FinishState.ERROR);
                }
            }
        }
    }

    public static void addListener(IUnityAdsListener iUnityAdsListener) {
        AdsProperties.addListener(iUnityAdsListener);
    }

    public static boolean getDebugMode() {
        return UnityServices.getDebugMode();
    }

    public static String getDefaultPlacement() {
        return Placement.getDefaultPlacement();
    }

    @Deprecated
    public static IUnityAdsListener getListener() {
        Iterator it = AdsProperties.getListeners().iterator();
        if (it.hasNext()) {
            return (IUnityAdsListener) it.next();
        }
        return null;
    }

    public static UnityAds.PlacementState getPlacementState() {
        return (isSupported() && isInitialized()) ? Placement.getPlacementState() : UnityAds.PlacementState.NOT_AVAILABLE;
    }

    public static UnityAds.PlacementState getPlacementState(String str) {
        return (isSupported() && isInitialized() && str != null) ? Placement.getPlacementState(str) : UnityAds.PlacementState.NOT_AVAILABLE;
    }

    public static String getVersion() {
        return UnityServices.getVersion();
    }

    public static void handleShowError(String str, UnityAds.UnityAdsError unityAdsError, String str2) {
        String str3 = "Unity Ads show failed: " + str2;
        DeviceLog.error(str3);
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.UnityAdsImplementation.3
            final /* synthetic */ String val$errorMessage;
            final /* synthetic */ String val$placementId;

            AnonymousClass3(String str32, String str4) {
                str = str32;
                str = str4;
            }

            @Override // java.lang.Runnable
            public final void run() {
                for (IUnityAdsListener iUnityAdsListener : AdsProperties.getListeners()) {
                    iUnityAdsListener.onUnityAdsError(unityAdsError, str);
                    String str4 = str;
                    if (str4 != null) {
                        iUnityAdsListener.onUnityAdsFinish(str4, UnityAds.FinishState.ERROR);
                    } else {
                        iUnityAdsListener.onUnityAdsFinish("", UnityAds.FinishState.ERROR);
                    }
                }
            }
        });
    }

    public static void initialize(Activity activity, String str, IUnityAdsListener iUnityAdsListener) {
        initialize(activity, str, iUnityAdsListener, false);
    }

    public static void initialize(Activity activity, String str, IUnityAdsListener iUnityAdsListener, boolean z) {
        initialize(activity, str, iUnityAdsListener, z, false);
    }

    public static void initialize(Activity activity, String str, IUnityAdsListener iUnityAdsListener, boolean z, boolean z2) {
        DeviceLog.entered();
        addListener(iUnityAdsListener);
        UnityServices.initialize(activity, str, new IUnityServicesListener() { // from class: com.unity3d.splash.services.ads.UnityAdsImplementation.1
            AnonymousClass1() {
            }

            @Override // com.unity3d.splash.services.IUnityServicesListener
            public final void onUnityServicesError(UnityServices.UnityServicesError unityServicesError, String str2) {
                if (unityServicesError == UnityServices.UnityServicesError.INIT_SANITY_CHECK_FAIL) {
                    iUnityAdsListener.onUnityAdsError(UnityAds.UnityAdsError.INIT_SANITY_CHECK_FAIL, str2);
                } else if (unityServicesError == UnityServices.UnityServicesError.INVALID_ARGUMENT) {
                    iUnityAdsListener.onUnityAdsError(UnityAds.UnityAdsError.INVALID_ARGUMENT, str2);
                }
            }
        }, z, z2);
    }

    public static boolean isInitialized() {
        return UnityServices.isInitialized();
    }

    public static boolean isReady() {
        return isSupported() && isInitialized() && Placement.isReady();
    }

    public static boolean isReady(String str) {
        return isSupported() && isInitialized() && str != null && Placement.isReady(str);
    }

    public static boolean isSupported() {
        return UnityServices.isSupported();
    }

    public static void load(String str) {
        LoadModule.getInstance().load(str);
    }

    public static void removeListener(IUnityAdsListener iUnityAdsListener) {
        AdsProperties.removeListener(iUnityAdsListener);
    }

    public static void setDebugMode(boolean z) {
        UnityServices.setDebugMode(z);
    }

    @Deprecated
    public static void setListener(IUnityAdsListener iUnityAdsListener) {
        AdsProperties.addListener(iUnityAdsListener);
    }

    public static void show(Activity activity) {
        if (Placement.getDefaultPlacement() != null) {
            show(activity, Placement.getDefaultPlacement());
        } else {
            handleShowError("", UnityAds.UnityAdsError.NOT_INITIALIZED, "Unity Ads default placement is not initialized");
        }
    }

    public static void show(Activity activity, String str) {
        if (activity == null) {
            handleShowError(str, UnityAds.UnityAdsError.INVALID_ARGUMENT, "Activity must not be null");
            return;
        }
        if (isReady(str)) {
            DeviceLog.info("Unity Ads opening new ad unit for placement " + str);
            ClientProperties.setActivity(activity);
            new Thread(new Runnable() { // from class: com.unity3d.splash.services.ads.UnityAdsImplementation.2
                final /* synthetic */ Activity val$activity;
                final /* synthetic */ String val$placementId;

                AnonymousClass2(Activity activity2, String str2) {
                    activity = activity2;
                    str = str2;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    Display defaultDisplay = ((WindowManager) activity.getSystemService("window")).getDefaultDisplay();
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("requestedOrientation", activity.getRequestedOrientation());
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("rotation", defaultDisplay.getRotation());
                        Point point = new Point();
                        defaultDisplay.getSize(point);
                        jSONObject2.put(CommonParam.SR_WIDTH, point.x);
                        jSONObject2.put(CommonParam.SR_HEIGHT, point.y);
                        jSONObject.put("display", jSONObject2);
                    } catch (JSONException e) {
                        DeviceLog.exception("JSON error while constructing show options", e);
                    }
                    try {
                        if (AdUnitOpen.open(str, jSONObject)) {
                            return;
                        }
                        UnityAdsImplementation.handleShowError(str, UnityAds.UnityAdsError.INTERNAL_ERROR, "Webapp timeout, shutting down Unity Ads");
                    } catch (NoSuchMethodException e2) {
                        DeviceLog.exception("Could not get callback method", e2);
                        UnityAdsImplementation.handleShowError(str, UnityAds.UnityAdsError.SHOW_ERROR, "Could not get com.unity3d.ads.properties.showCallback method");
                    }
                }
            }).start();
            return;
        }
        if (!isSupported()) {
            handleShowError(str2, UnityAds.UnityAdsError.NOT_INITIALIZED, "Unity Ads is not supported for this device");
            return;
        }
        if (!isInitialized()) {
            handleShowError(str2, UnityAds.UnityAdsError.NOT_INITIALIZED, "Unity Ads is not initialized");
            return;
        }
        handleShowError(str2, UnityAds.UnityAdsError.SHOW_ERROR, "Placement \"" + str2 + "\" is not ready");
    }
}
