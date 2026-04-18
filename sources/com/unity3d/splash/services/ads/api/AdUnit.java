package com.unity3d.splash.services.ads.api;

import android.content.Intent;
import android.os.Build;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.WindowInsets;
import com.taptap.sdk.TapLoginHelperActivity;
import com.tds.common.tracker.constants.CommonParam;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitError;
import com.unity3d.splash.services.ads.adunit.AdUnitMotionEvent;
import com.unity3d.splash.services.ads.adunit.AdUnitSoftwareActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitTransparentActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitTransparentSoftwareActivity;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdUnit {
    private static AdUnitActivity _adUnitActivity = null;
    private static int _currentActivityId = -1;

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.api.AdUnit$1 */
    static class AnonymousClass1 implements Runnable {
        final /* synthetic */ JSONArray val$views;

        AnonymousClass1(JSONArray jSONArray) {
            jSONArray = jSONArray;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (AdUnit.getAdUnitActivity() != null) {
                try {
                    AdUnit.getAdUnitActivity().setViews(AdUnit.getViewList(jSONArray));
                } catch (Exception e) {
                    DeviceLog.exception("Corrupted viewlist", e);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.api.AdUnit$2 */
    static class AnonymousClass2 implements Runnable {
        final /* synthetic */ Integer val$orientation;

        AnonymousClass2(Integer num) {
            num = num;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (AdUnit.getAdUnitActivity() != null) {
                AdUnit.getAdUnitActivity().setOrientation(num.intValue());
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.api.AdUnit$3 */
    static class AnonymousClass3 implements Runnable {
        final /* synthetic */ Boolean val$screenOn;

        AnonymousClass3(Boolean bool) {
            bool = bool;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (AdUnit.getAdUnitActivity() != null) {
                AdUnit.getAdUnitActivity().setKeepScreenOn(bool.booleanValue());
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.api.AdUnit$4 */
    static class AnonymousClass4 implements Runnable {
        final /* synthetic */ Integer val$systemUiVisibility;

        AnonymousClass4(Integer num) {
            num = num;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (AdUnit.getAdUnitActivity() != null) {
                AdUnit.getAdUnitActivity().setSystemUiVisibility(num.intValue());
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.api.AdUnit$5 */
    static class AnonymousClass5 implements Runnable {
        final /* synthetic */ Integer val$height;
        final /* synthetic */ String val$view;
        final /* synthetic */ Integer val$width;
        final /* synthetic */ Integer val$x;
        final /* synthetic */ Integer val$y;

        AnonymousClass5(String str, Integer num, Integer num2, Integer num3, Integer num4) {
            str = str;
            num = num;
            num = num2;
            num = num3;
            num = num4;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (AdUnit.getAdUnitActivity() != null) {
                AdUnit.getAdUnitActivity().setViewFrame(str, num.intValue(), num.intValue(), num.intValue(), num.intValue());
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.splash.services.ads.api.AdUnit$6 */
    static class AnonymousClass6 implements Runnable {
        final /* synthetic */ Integer val$displayCutoutMode;

        AnonymousClass6(Integer num) {
            num = num;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (AdUnit.getAdUnitActivity() != null) {
                AdUnit.getAdUnitActivity().setLayoutInDisplayCutoutMode(num.intValue());
            }
        }
    }

    private AdUnit() {
    }

    @WebViewExposed
    public static void clearMotionEventCapture(WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().getLayout().clearCapture();
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void close(WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().finish();
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void endMotionEventCapture(WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().getLayout().endCapture();
            webViewCallback.invoke(new Object[0]);
        }
    }

    public static AdUnitActivity getAdUnitActivity() {
        return _adUnitActivity;
    }

    public static int getCurrentAdUnitActivityId() {
        return _currentActivityId;
    }

    @WebViewExposed
    public static void getCurrentMotionEventCount(WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() != null) {
            webViewCallback.invoke(Integer.valueOf(getAdUnitActivity().getLayout().getCurrentEventCount()));
        } else {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        }
    }

    private static ArrayList getKeyEventList(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        int iIntValue = 0;
        while (true) {
            Integer numValueOf = Integer.valueOf(iIntValue);
            if (numValueOf.intValue() >= jSONArray.length()) {
                return arrayList;
            }
            arrayList.add(Integer.valueOf(jSONArray.getInt(numValueOf.intValue())));
            iIntValue = numValueOf.intValue() + 1;
        }
    }

    @WebViewExposed
    public static void getMotionEventCount(JSONArray jSONArray, WebViewCallback webViewCallback) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                arrayList.add(Integer.valueOf(jSONArray.getInt(i)));
            } catch (Exception e) {
                DeviceLog.exception("Error retrieving int from eventTypes", e);
            }
        }
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout().getCurrentEventCount() >= getAdUnitActivity().getLayout().getMaxEventCount()) {
            webViewCallback.error(AdUnitError.MAX_MOTION_EVENT_COUNT_REACHED, new Object[0]);
            return;
        }
        SparseIntArray eventCount = getAdUnitActivity().getLayout().getEventCount(arrayList);
        JSONObject jSONObject = new JSONObject();
        for (int i2 = 0; i2 < eventCount.size(); i2++) {
            int iKeyAt = eventCount.keyAt(i2);
            try {
                jSONObject.put(Integer.toString(iKeyAt), eventCount.get(iKeyAt));
            } catch (Exception e2) {
                DeviceLog.exception("Error building response JSON", e2);
            }
        }
        webViewCallback.invoke(jSONObject);
    }

    @WebViewExposed
    public static void getMotionEventData(JSONObject jSONObject, WebViewCallback webViewCallback) {
        Iterator<String> itKeys = jSONObject.keys();
        SparseArray sparseArray = new SparseArray();
        while (true) {
            if (!itKeys.hasNext()) {
                break;
            }
            String next = itKeys.next();
            int i = Integer.parseInt(next);
            if (sparseArray.get(i) == null) {
                sparseArray.put(i, new ArrayList());
            }
            JSONArray jSONArray = null;
            try {
                jSONArray = jSONObject.getJSONArray(next);
            } catch (Exception e) {
                DeviceLog.exception("Couldn't fetch keyIndices", e);
            }
            if (jSONArray != null) {
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    try {
                        ((ArrayList) sparseArray.get(i)).add(Integer.valueOf(jSONArray.getInt(i2)));
                    } catch (Exception e2) {
                        DeviceLog.exception("Couldn't add value to requested infos", e2);
                    }
                }
            }
        }
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout().getCurrentEventCount() >= getAdUnitActivity().getLayout().getMaxEventCount()) {
            webViewCallback.error(AdUnitError.MAX_MOTION_EVENT_COUNT_REACHED, new Object[0]);
            return;
        }
        SparseArray events = getAdUnitActivity().getLayout().getEvents(sparseArray);
        JSONObject jSONObject2 = new JSONObject();
        for (int i3 = 0; i3 < events.size(); i3++) {
            int iKeyAt = events.keyAt(i3);
            SparseArray sparseArray2 = (SparseArray) events.get(iKeyAt);
            JSONObject jSONObject3 = new JSONObject();
            for (int i4 = 0; i4 < sparseArray2.size(); i4++) {
                JSONObject jSONObject4 = new JSONObject();
                int iKeyAt2 = sparseArray2.keyAt(i4);
                AdUnitMotionEvent adUnitMotionEvent = (AdUnitMotionEvent) sparseArray2.get(iKeyAt2);
                try {
                    jSONObject4.put("action", adUnitMotionEvent.getAction());
                    jSONObject4.put("isObscured", adUnitMotionEvent.isObscured());
                    jSONObject4.put("toolType", adUnitMotionEvent.getToolType());
                    jSONObject4.put(TapLoginHelperActivity.INTENT_KEY_SOURCE, adUnitMotionEvent.getSource());
                    jSONObject4.put("deviceId", adUnitMotionEvent.getDeviceId());
                    jSONObject4.put("x", adUnitMotionEvent.getX());
                    jSONObject4.put("y", adUnitMotionEvent.getY());
                    jSONObject4.put("eventTime", adUnitMotionEvent.getEventTime());
                    jSONObject4.put("pressure", adUnitMotionEvent.getPressure());
                    jSONObject4.put("size", adUnitMotionEvent.getSize());
                    jSONObject3.put(Integer.toString(iKeyAt2), jSONObject4);
                } catch (Exception e3) {
                    DeviceLog.debug("Couldn't construct event info", e3);
                }
            }
            try {
                jSONObject2.put(Integer.toString(iKeyAt), jSONObject3);
            } catch (Exception e4) {
                DeviceLog.debug("Couldn't construct info object", e4);
            }
        }
        webViewCallback.invoke(jSONObject2);
    }

    @WebViewExposed
    public static void getOrientation(WebViewCallback webViewCallback) {
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(Integer.valueOf(getAdUnitActivity().getRequestedOrientation()));
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getSafeAreaInsets(WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null || getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        if (Build.VERSION.SDK_INT < 28) {
            webViewCallback.error(AdUnitError.API_LEVEL_ERROR, new Object[0]);
            return;
        }
        WindowInsets rootWindowInsets = getAdUnitActivity().getLayout().getRootWindowInsets();
        if (rootWindowInsets == null) {
            webViewCallback.error(AdUnitError.NO_DISPLAY_CUTOUT_AVAILABLE, new Object[0]);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            Object objInvoke = rootWindowInsets.getClass().getMethod("getDisplayCutout", new Class[0]).invoke(rootWindowInsets, new Object[0]);
            if (objInvoke == null) {
                webViewCallback.error(AdUnitError.NO_DISPLAY_CUTOUT_AVAILABLE, new Object[0]);
                return;
            }
            Object objInvoke2 = objInvoke.getClass().getMethod("getSafeInsetTop", new Class[0]).invoke(objInvoke, new Object[0]);
            Object objInvoke3 = objInvoke.getClass().getMethod("getSafeInsetRight", new Class[0]).invoke(objInvoke, new Object[0]);
            Object objInvoke4 = objInvoke.getClass().getMethod("getSafeInsetBottom", new Class[0]).invoke(objInvoke, new Object[0]);
            Object objInvoke5 = objInvoke.getClass().getMethod("getSafeInsetLeft", new Class[0]).invoke(objInvoke, new Object[0]);
            jSONObject.put("top", objInvoke2);
            jSONObject.put("right", objInvoke3);
            jSONObject.put("bottom", objInvoke4);
            jSONObject.put("left", objInvoke5);
            webViewCallback.invoke(jSONObject);
        } catch (IllegalAccessException e) {
            e = e;
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_INVOKE_FAILED, new Object[0]);
            DeviceLog.debug("Error while calling displayCutout getter", e);
        } catch (NoSuchMethodException e2) {
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_METHOD_NOT_AVAILABLE, new Object[0]);
            DeviceLog.debug("Method getDisplayCutout not found", e2);
        } catch (InvocationTargetException e3) {
            e = e3;
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_INVOKE_FAILED, new Object[0]);
            DeviceLog.debug("Error while calling displayCutout getter", e);
        } catch (JSONException e4) {
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_JSON_ERROR, new Object[0]);
            DeviceLog.debug("JSON error while constructing display cutout object", e4);
        }
    }

    @WebViewExposed
    public static void getViewFrame(String str, WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getViewFrame(str) == null) {
            webViewCallback.error(AdUnitError.UNKNOWN_VIEW, new Object[0]);
        } else {
            Map viewFrame = getAdUnitActivity().getViewFrame(str);
            webViewCallback.invoke(viewFrame.get("x"), viewFrame.get("y"), viewFrame.get(CommonParam.SR_WIDTH), viewFrame.get(CommonParam.SR_HEIGHT));
        }
    }

    public static String[] getViewList(JSONArray jSONArray) {
        String[] strArr = new String[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            strArr[i] = jSONArray.getString(i);
        }
        return strArr;
    }

    @WebViewExposed
    public static void getViews(WebViewCallback webViewCallback) {
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(new JSONArray((Collection) Arrays.asList(getAdUnitActivity().getViews())));
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, WebViewCallback webViewCallback) {
        open(num, jSONArray, num2, null, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, WebViewCallback webViewCallback) {
        open(num, jSONArray, num2, jSONArray2, 0, true, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, Integer num3, Boolean bool, WebViewCallback webViewCallback) {
        open(num, jSONArray, num2, jSONArray2, num3, bool, false, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, Integer num3, Boolean bool, Boolean bool2, WebViewCallback webViewCallback) {
        open(num, jSONArray, num2, jSONArray2, num3, bool, bool2, 0, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, Integer num3, Boolean bool, Boolean bool2, Integer num4, WebViewCallback webViewCallback) {
        Intent intent;
        if (!bool.booleanValue() && bool2.booleanValue()) {
            DeviceLog.debug("Unity Ads opening new transparent ad unit activity, hardware acceleration disabled");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitTransparentSoftwareActivity.class);
        } else if (bool.booleanValue() && !bool2.booleanValue()) {
            DeviceLog.debug("Unity Ads opening new hardware accelerated ad unit activity");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitActivity.class);
        } else if (bool.booleanValue() && bool2.booleanValue()) {
            DeviceLog.debug("Unity Ads opening new hardware accelerated transparent ad unit activity");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitTransparentActivity.class);
        } else {
            DeviceLog.debug("Unity Ads opening new ad unit activity, hardware acceleration disabled");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitSoftwareActivity.class);
        }
        intent.addFlags(268500992);
        if (num == null) {
            DeviceLog.error("Activity ID is NULL");
            webViewCallback.error(AdUnitError.ACTIVITY_ID, "Activity ID NULL");
            return;
        }
        try {
            intent.putExtra(AdUnitActivity.EXTRA_ACTIVITY_ID, num.intValue());
            setCurrentAdUnitActivityId(num.intValue());
            try {
                intent.putExtra(AdUnitActivity.EXTRA_VIEWS, getViewList(jSONArray));
                if (jSONArray2 != null) {
                    try {
                        intent.putExtra(AdUnitActivity.EXTRA_KEY_EVENT_LIST, getKeyEventList(jSONArray2));
                    } catch (Exception e) {
                        DeviceLog.exception("Error parsing views from viewList", e);
                        webViewCallback.error(AdUnitError.CORRUPTED_KEYEVENTLIST, jSONArray2, e.getMessage());
                        return;
                    }
                }
                intent.putExtra(AdUnitActivity.EXTRA_SYSTEM_UI_VISIBILITY, num3);
                intent.putExtra(AdUnitActivity.EXTRA_ORIENTATION, num2);
                intent.putExtra(AdUnitActivity.EXTRA_DISPLAY_CUTOUT_MODE, num4);
                ClientProperties.getActivity().startActivity(intent);
                DeviceLog.debug("Opened AdUnitActivity with: " + jSONArray.toString());
                webViewCallback.invoke(new Object[0]);
            } catch (Exception e2) {
                DeviceLog.exception("Error parsing views from viewList", e2);
                webViewCallback.error(AdUnitError.CORRUPTED_VIEWLIST, jSONArray, e2.getMessage());
            }
        } catch (Exception e3) {
            DeviceLog.exception("Could not set activityId for intent", e3);
            webViewCallback.error(AdUnitError.ACTIVITY_ID, Integer.valueOf(num.intValue()), e3.getMessage());
        }
    }

    public static void setAdUnitActivity(AdUnitActivity adUnitActivity) {
        _adUnitActivity = adUnitActivity;
    }

    public static void setCurrentAdUnitActivityId(int i) {
        _currentActivityId = i;
    }

    @WebViewExposed
    public static void setKeepScreenOn(Boolean bool, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.3
            final /* synthetic */ Boolean val$screenOn;

            AnonymousClass3(Boolean bool2) {
                bool = bool2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setKeepScreenOn(bool.booleanValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setKeyEventList(JSONArray jSONArray, WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        try {
            getAdUnitActivity().setKeyEventList(getKeyEventList(jSONArray));
            webViewCallback.invoke(jSONArray);
        } catch (Exception e) {
            DeviceLog.exception("Error parsing views from viewList", e);
            webViewCallback.error(AdUnitError.CORRUPTED_KEYEVENTLIST, jSONArray, e.getMessage());
        }
    }

    @WebViewExposed
    public static void setLayoutInDisplayCutoutMode(Integer num, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.6
            final /* synthetic */ Integer val$displayCutoutMode;

            AnonymousClass6(Integer num2) {
                num = num2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setLayoutInDisplayCutoutMode(num.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(num2);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setOrientation(Integer num, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.2
            final /* synthetic */ Integer val$orientation;

            AnonymousClass2(Integer num2) {
                num = num2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setOrientation(num.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(num2);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setSystemUiVisibility(Integer num, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.4
            final /* synthetic */ Integer val$systemUiVisibility;

            AnonymousClass4(Integer num2) {
                num = num2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setSystemUiVisibility(num.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(num2);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setViewFrame(String str, Integer num, Integer num2, Integer num3, Integer num4, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.5
            final /* synthetic */ Integer val$height;
            final /* synthetic */ String val$view;
            final /* synthetic */ Integer val$width;
            final /* synthetic */ Integer val$x;
            final /* synthetic */ Integer val$y;

            AnonymousClass5(String str2, Integer num5, Integer num22, Integer num32, Integer num42) {
                str = str2;
                num = num5;
                num = num22;
                num = num32;
                num = num42;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setViewFrame(str, num.intValue(), num.intValue(), num.intValue(), num.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setViews(JSONArray jSONArray, WebViewCallback webViewCallback) {
        boolean z;
        try {
            getViewList(jSONArray);
            z = false;
        } catch (JSONException unused) {
            webViewCallback.error(AdUnitError.CORRUPTED_VIEWLIST, jSONArray);
            z = true;
        }
        if (!z) {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.1
                final /* synthetic */ JSONArray val$views;

                AnonymousClass1(JSONArray jSONArray2) {
                    jSONArray = jSONArray2;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    if (AdUnit.getAdUnitActivity() != null) {
                        try {
                            AdUnit.getAdUnitActivity().setViews(AdUnit.getViewList(jSONArray));
                        } catch (Exception e) {
                            DeviceLog.exception("Corrupted viewlist", e);
                        }
                    }
                }
            });
        }
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(jSONArray2);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void startMotionEventCapture(Integer num, WebViewCallback webViewCallback) {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().getLayout().startCapture(num.intValue());
            webViewCallback.invoke(new Object[0]);
        }
    }
}
