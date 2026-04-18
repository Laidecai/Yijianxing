package com.unity3d.player;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.common.tracker.constants.CommonParam;
import com.unity3d.player.j;
import com.unity3d.player.n;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.services.core.device.Device;
import com.unity3d.splash.services.core.device.Storage;
import com.unity3d.splash.services.core.device.StorageManager;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.WebRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import kotlin.UByte;
import org.json.JSONException;
import org.json.JSONObject;
import tds.androidx.recyclerview.widget.ItemTouchHelper;

/* JADX INFO: loaded from: classes.dex */
public class UnityPlayer extends FrameLayout implements IUnityPlayerLifecycleEvents {
    private static final int ANR_TIMEOUT_SECONDS = 4;
    private static final String ARCORE_ENABLE_METADATA_NAME = "unity.arcore-enable";
    private static final int RUN_STATE_CHANGED_MSG_CODE = 2269;
    private static final String SPLASH_ADS_GAME_ID = "unity.splash-ads-game-id";
    private static final String SPLASH_ADS_SLOGAN = "unity.splash-ads-slogan";
    private static final String SPLASH_ADS_SLOGAN_HEIGHT = "unity.splash-ads-slogan-height";
    private static final String SPLASH_CHECK_URL = "https://check.unity.cn/api/check-license";
    private static final String SPLASH_ENABLE_METADATA_NAME = "unity.splash-enable";
    private static final String SPLASH_MODE_METADATA_NAME = "unity.splash-mode";
    private static final String UNITY_BUILDER_ID = "unity.builder";
    public static Activity currentActivity;
    AlertDialog ad;
    private boolean finishLaunchScreenAds;
    private Context mContext;
    private SurfaceView mGlView;
    private Handler mHanlder;
    private int mInitialScreenOrientation;
    private boolean mIsFullscreen;
    private BroadcastReceiver mKillingIsMyBusiness;
    private boolean mMainDisplayOverride;
    private int mNaturalOrientation;
    private OrientationEventListener mOrientationListener;
    private boolean mProcessKillRequested;
    private boolean mQuitting;
    com.unity3d.player.g mSoftInputDialog;
    private l mState;
    private n mVideoPlayerProxy;
    private GoogleARCoreApi m_ARCoreApi;
    private boolean m_AddPhoneCallListener;
    private AudioVolumeHandler m_AudioVolumeHandler;
    private Camera2Wrapper m_Camera2Wrapper;
    private ClipboardManager m_ClipboardManager;
    private final ConcurrentLinkedQueue m_Events;
    private a m_FakeListener;
    private HFPStatus m_HFPStatus;
    g m_MainThread;
    private NetworkConnectivity m_NetworkConnectivity;
    private com.unity3d.player.f m_PersistentUnitySurface;
    private c m_PhoneCallListener;
    private j m_SplashScreen;
    private TelephonyManager m_TelephonyManager;
    private IUnityPlayerLifecycleEvents m_UnityPlayerLifecycleEvents;
    private Uri m_launchUri;
    private i m_splashAdsScreen;
    private boolean shouldShowLaunchScreenAds;
    private Timer timer;
    private TimerTask timerTask;

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$1 */
    final class AnonymousClass1 implements DialogInterface.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            UnityPlayer.this.finish();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$10 */
    final class AnonymousClass10 implements Runnable {
        final /* synthetic */ int a;

        AnonymousClass10(int i) {
            i = i;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.mSoftInputDialog != null) {
                UnityPlayer.this.mSoftInputDialog.a(i);
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$11 */
    final class AnonymousClass11 implements Runnable {
        final /* synthetic */ boolean a;

        AnonymousClass11(boolean z) {
            z = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.mSoftInputDialog != null) {
                UnityPlayer.this.mSoftInputDialog.a(z);
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$12 */
    final class AnonymousClass12 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ int b;

        AnonymousClass12(int i, int i2) {
            i = i;
            i = i2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.mSoftInputDialog != null) {
                UnityPlayer.this.mSoftInputDialog.a(i, i);
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$13 */
    final class AnonymousClass13 extends h {
        final /* synthetic */ boolean a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass13(boolean z, String str, int i) {
            super(UnityPlayer.this, (byte) 0);
            z = z;
            str = str;
            i = i;
        }

        @Override // com.unity3d.player.UnityPlayer.h
        public final void a() {
            if (z) {
                UnityPlayer.this.nativeSoftInputCanceled();
            } else {
                String str = str;
                if (str != null) {
                    UnityPlayer.this.nativeSetInputString(str);
                }
            }
            if (i == 1) {
                UnityPlayer.this.nativeSoftInputClosed();
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$14 */
    final class AnonymousClass14 extends h {
        final /* synthetic */ int a;
        final /* synthetic */ int b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass14(int i, int i2) {
            super(UnityPlayer.this, (byte) 0);
            i = i;
            i = i2;
        }

        @Override // com.unity3d.player.UnityPlayer.h
        public final void a() {
            UnityPlayer.this.nativeSetInputSelection(i, i);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$15 */
    final class AnonymousClass15 extends h {
        final /* synthetic */ Rect a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass15(Rect rect) {
            super(UnityPlayer.this, (byte) 0);
            rect = rect;
        }

        @Override // com.unity3d.player.UnityPlayer.h
        public final void a() {
            UnityPlayer.this.nativeSetInputArea(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$16 */
    final class AnonymousClass16 extends h {
        final /* synthetic */ boolean a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass16(boolean z) {
            super(UnityPlayer.this, (byte) 0);
            z = z;
        }

        @Override // com.unity3d.player.UnityPlayer.h
        public final void a() {
            UnityPlayer.this.nativeSetKeyboardIsVisible(z);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$17 */
    final class AnonymousClass17 implements n.a {
        AnonymousClass17() {
        }

        @Override // com.unity3d.player.n.a
        public final void a() {
            UnityPlayer.this.mVideoPlayerProxy = null;
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$18 */
    final class AnonymousClass18 implements Runnable {
        AnonymousClass18() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.nativeIsAutorotationOn() && (UnityPlayer.this.mContext instanceof Activity)) {
                ((Activity) UnityPlayer.this.mContext).setRequestedOrientation(UnityPlayer.this.mInitialScreenOrientation);
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$19 */
    final class AnonymousClass19 implements Runnable {
        AnonymousClass19() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.pause();
            UnityPlayer.this.windowFocusChanged(false);
            UnityPlayer.this.m_UnityPlayerLifecycleEvents.onUnityPlayerUnloaded();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$2 */
    final class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.mMainDisplayOverride) {
                UnityPlayer unityPlayer = UnityPlayer.this;
                unityPlayer.removeView(unityPlayer.mGlView);
            } else {
                UnityPlayer unityPlayer2 = UnityPlayer.this;
                unityPlayer2.addView(unityPlayer2.mGlView);
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$20 */
    final class AnonymousClass20 implements DialogInterface.OnClickListener {
        AnonymousClass20() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            UnityPlayer.this.mHanlder.sendEmptyMessage(1);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$21 */
    final class AnonymousClass21 extends Handler {
        AnonymousClass21() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 0) {
                if (i == 1 && UnityPlayer.this.ad != null && UnityPlayer.this.ad.isShowing()) {
                    UnityPlayer.this.ad.hide();
                }
            } else if (UnityPlayer.this.ad != null && !UnityPlayer.this.ad.isShowing()) {
                UnityPlayer.this.ad.show();
            }
            super.handleMessage(message);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$22 */
    final class AnonymousClass22 implements Runnable {
        AnonymousClass22() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer unityPlayer = UnityPlayer.this;
            unityPlayer.removeView(unityPlayer.m_SplashScreen);
            UnityPlayer.this.m_SplashScreen = null;
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$23 */
    final class AnonymousClass23 extends TimerTask {
        AnonymousClass23() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public final void run() {
            UnityPlayer.this.mHanlder.sendEmptyMessage(0);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$24 */
    final class AnonymousClass24 extends OrientationEventListener {
        AnonymousClass24(Context context, int i) {
            super(context, i);
        }

        @Override // android.view.OrientationEventListener
        public final void onOrientationChanged(int i) {
            UnityPlayer.this.m_MainThread.a(UnityPlayer.this.mNaturalOrientation, i);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$25 */
    final class AnonymousClass25 implements Runnable {
        AnonymousClass25() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.m_splashAdsScreen != null) {
                UnityPlayer unityPlayer = UnityPlayer.this;
                unityPlayer.bringChildToFront(unityPlayer.m_splashAdsScreen);
                UnityPlayer.this.m_splashAdsScreen.a();
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$26 */
    final class AnonymousClass26 implements Animation.AnimationListener {

        /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$26$1 */
        final class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.removeView(UnityPlayer.this.m_splashAdsScreen);
            }
        }

        AnonymousClass26() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationEnd(Animation animation) {
            UnityPlayer.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.26.1
                AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.removeView(UnityPlayer.this.m_splashAdsScreen);
                }
            });
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public final void onAnimationStart(Animation animation) {
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$27 */
    final class AnonymousClass27 implements Runnable {
        final /* synthetic */ Animation a;

        AnonymousClass27(Animation animation) {
            animation = animation;
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.m_splashAdsScreen.startAnimation(animation);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$28 */
    final class AnonymousClass28 implements SurfaceHolder.Callback {
        AnonymousClass28() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            UnityPlayer.this.updateGLDisplay(0, surfaceHolder.getSurface());
            UnityPlayer.this.sendSurfaceChangedEvent();
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceCreated(SurfaceHolder surfaceHolder) {
            UnityPlayer.this.updateGLDisplay(0, surfaceHolder.getSurface());
            if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this.mGlView);
            }
            UnityPlayer.this.updateGLDisplay(0, null);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$29 */
    final class AnonymousClass29 implements Runnable {
        AnonymousClass29() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.nativeSendSurfaceChangedEvent();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$3 */
    final class AnonymousClass3 implements Runnable {
        final /* synthetic */ Semaphore a;

        AnonymousClass3(Semaphore semaphore) {
            semaphore = semaphore;
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.shutdown();
            semaphore.release();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$30 */
    final class AnonymousClass30 implements Runnable {
        final /* synthetic */ int a;
        final /* synthetic */ Surface b;
        final /* synthetic */ Semaphore c;

        AnonymousClass30(int i, Surface surface, Semaphore semaphore) {
            i = i;
            surface = surface;
            semaphore = semaphore;
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.nativeRecreateGfxState(i, surface);
            semaphore.release();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$4 */
    final class AnonymousClass4 implements Runnable {
        final /* synthetic */ Semaphore a;

        AnonymousClass4(Semaphore semaphore) {
            semaphore = semaphore;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (!UnityPlayer.this.nativePause()) {
                semaphore.release();
                return;
            }
            UnityPlayer.this.mQuitting = true;
            UnityPlayer.this.shutdown();
            semaphore.release(2);
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$5 */
    final class AnonymousClass5 implements Runnable {
        AnonymousClass5() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.nativeLowMemory();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$6 */
    final class AnonymousClass6 implements Runnable {

        /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$6$1 */
        final class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                    UnityPlayer.this.m_PersistentUnitySurface.b(UnityPlayer.this);
                }
            }
        }

        AnonymousClass6() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.nativeResume();
            UnityPlayer.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.6.1
                AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                        UnityPlayer.this.m_PersistentUnitySurface.b(UnityPlayer.this);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$7 */
    final class AnonymousClass7 implements Runnable {
        final /* synthetic */ UnityPlayer a;
        final /* synthetic */ String b;
        final /* synthetic */ int c;
        final /* synthetic */ boolean d;
        final /* synthetic */ boolean e;
        final /* synthetic */ boolean f;
        final /* synthetic */ boolean g;
        final /* synthetic */ String h;
        final /* synthetic */ int i;
        final /* synthetic */ boolean j;

        /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$7$1 */
        final class AnonymousClass1 implements DialogInterface.OnCancelListener {
            AnonymousClass1() {
            }

            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                UnityPlayer.this.nativeSoftInputLostFocus();
                UnityPlayer.this.reportSoftInputStr(null, 1, false);
            }
        }

        AnonymousClass7(UnityPlayer unityPlayer, String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2, boolean z5) {
            unityPlayer = unityPlayer;
            str = str;
            i = i;
            z = z;
            z = z2;
            z = z3;
            z = z4;
            str = str2;
            i = i2;
            z = z5;
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.mSoftInputDialog = new com.unity3d.player.g(UnityPlayer.this.mContext, unityPlayer, str, i, z, z, z, str, i, z);
            UnityPlayer.this.mSoftInputDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.unity3d.player.UnityPlayer.7.1
                AnonymousClass1() {
                }

                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    UnityPlayer.this.nativeSoftInputLostFocus();
                    UnityPlayer.this.reportSoftInputStr(null, 1, false);
                }
            });
            UnityPlayer.this.mSoftInputDialog.show();
            UnityPlayer.this.nativeReportKeyboardConfigChanged();
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$8 */
    final class AnonymousClass8 implements Runnable {
        AnonymousClass8() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            UnityPlayer.this.reportSoftInputArea(new Rect());
            UnityPlayer.this.reportSoftInputIsVisible(false);
            if (UnityPlayer.this.mSoftInputDialog != null) {
                UnityPlayer.this.mSoftInputDialog.dismiss();
                UnityPlayer.this.mSoftInputDialog = null;
                UnityPlayer.this.nativeReportKeyboardConfigChanged();
            }
        }
    }

    /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$9 */
    final class AnonymousClass9 implements Runnable {
        final /* synthetic */ String a;

        AnonymousClass9(String str) {
            str = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.mSoftInputDialog == null || str == null) {
                return;
            }
            UnityPlayer.this.mSoftInputDialog.a(str);
        }
    }

    class a implements SensorEventListener {
        a() {
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
        }
    }

    static final class b extends Enum {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {1, 2, 3};
    }

    private class c extends PhoneStateListener {
        private c() {
        }

        /* synthetic */ c(UnityPlayer unityPlayer, byte b) {
            this();
        }

        @Override // android.telephony.PhoneStateListener
        public final void onCallStateChanged(int i, String str) {
            UnityPlayer.this.nativeMuteMasterAudio(i == 1);
        }
    }

    class d extends AsyncTask {
        d() {
        }

        @Override // android.os.AsyncTask
        public final Void doInBackground(String... strArr) {
            Boolean.valueOf(false);
            String str = strArr[0];
            String str2 = strArr[1];
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("game_bundle_id", str);
                jSONObject.put("game_bundle_hash", str2);
                WebRequest webRequest = new WebRequest(UnityPlayer.SPLASH_CHECK_URL, com.tds.tapdb.b.g.O, null);
                webRequest.setBody(jSONObject.toString());
                String strMakeRequest = webRequest.makeRequest();
                if (webRequest.getResponseCode() == 200 && Boolean.valueOf(new JSONObject(strMakeRequest).optBoolean("registered")).booleanValue()) {
                    com.unity3d.player.d.Log(4, "Game Bundle Registered");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class e extends AsyncTask {
        e() {
        }

        @Override // android.os.AsyncTask
        public final Void doInBackground(String... strArr) {
            Boolean.valueOf(false);
            Boolean.valueOf(false);
            Boolean.valueOf(false);
            String str = strArr[0];
            try {
                String str2 = Build.MANUFACTURER + "/" + Build.MODEL + "/" + Build.DEVICE;
                String strHash_sha256 = UnityPlayer.hash_sha256(str);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("game_bundle_hash", strHash_sha256);
                jSONObject.put("device_model", str2);
                jSONObject.put("platform", "android");
                jSONObject.put("unity_hash", UnityPlayer.this.getDeviceId());
                jSONObject.put("splash_sdk_version", "2020.3.15f1c1");
                jSONObject.put("mcc", strArr[1]);
                jSONObject.put("builder", UnityPlayer.this.getBuilderUserId());
                WebRequest webRequest = new WebRequest(UnityPlayer.SPLASH_CHECK_URL + UnityPlayer.getQueryString(jSONObject), com.tds.tapdb.b.g.L, null);
                String strMakeRequest = webRequest.makeRequest();
                if (webRequest.getResponseCode() != 200) {
                    return null;
                }
                JSONObject jSONObject2 = new JSONObject(strMakeRequest);
                Boolean boolValueOf = Boolean.valueOf(jSONObject2.optBoolean("blocked"));
                Boolean boolValueOf2 = Boolean.valueOf(jSONObject2.optBoolean("show_ads"));
                String strOptString = jSONObject2.optString("game_id");
                if (!Boolean.valueOf(jSONObject2.optBoolean("registered")).booleanValue()) {
                    UnityPlayer.this.new d().execute(str, strHash_sha256);
                }
                if (UnityPlayer.currentActivity == null) {
                    return null;
                }
                String splashGameId = UnityPlayer.this.getSplashGameId();
                if (splashGameId != null && splashGameId.length() > 0) {
                    boolValueOf2 = true;
                    strOptString = splashGameId;
                }
                SharedPreferences.Editor editorEdit = UnityPlayer.currentActivity.getSharedPreferences("game_detail", 0).edit();
                editorEdit.putString("game_id", strOptString);
                editorEdit.putBoolean("show_ads", boolValueOf2.booleanValue());
                editorEdit.putBoolean("blocked", boolValueOf.booleanValue());
                editorEdit.putString(BreakpointSQLiteKey.URL, jSONObject2.optString(BreakpointSQLiteKey.URL));
                editorEdit.putString("hash", jSONObject2.optString("hash"));
                editorEdit.putString(CommonParam.VERSION, jSONObject2.optString(CommonParam.VERSION));
                editorEdit.commit();
                if (boolValueOf.booleanValue() || !boolValueOf2.booleanValue() || strOptString == null || strOptString == "") {
                    return null;
                }
                UnityAds.initialize(UnityPlayer.currentActivity, strOptString, null);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    enum f {
        PAUSE,
        RESUME,
        QUIT,
        SURFACE_LOST,
        SURFACE_ACQUIRED,
        FOCUS_LOST,
        FOCUS_GAINED,
        NEXT_FRAME,
        URL_ACTIVATED,
        ORIENTATION_ANGLE_CHANGE,
        SPLASH_ADS_DISMISS
    }

    private class g extends Thread {
        Handler a;
        boolean b;
        boolean c;
        int d;
        int e;
        int f;
        int g;
        boolean h;
        int i;
        int j;

        /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$g$1 */
        final class AnonymousClass1 implements Handler.Callback {
            AnonymousClass1() {
            }

            private void a() {
                if (g.this.d == b.c && g.this.c) {
                    UnityPlayer.this.nativeFocusChanged(true);
                    g.this.d = b.a;
                }
            }

            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                if (message.what != UnityPlayer.RUN_STATE_CHANGED_MSG_CODE) {
                    return false;
                }
                f fVar = (f) message.obj;
                if (fVar == f.NEXT_FRAME) {
                    g.this.e--;
                    UnityPlayer.this.executeGLThreadJobs();
                    if (!g.this.b || !g.this.c) {
                        return true;
                    }
                    if (g.this.i >= 0) {
                        if (g.this.i == 0 && UnityPlayer.this.getSplashEnabled()) {
                            UnityPlayer.this.DisableStaticSplashScreen();
                        }
                        g.this.i--;
                    }
                    if (g.this.i == 0 && UnityPlayer.this.shouldShowLaunchScreenAds) {
                        UnityPlayer.this.ShowSplashAdsScreen();
                    }
                    if (g.this.h && g.this.j >= 0) {
                        if (g.this.j == 0) {
                            UnityPlayer.this.DisableSplashAdsScreen();
                        }
                        g.this.j--;
                    }
                    if (!UnityPlayer.this.isFinishing() && !UnityPlayer.this.nativeRender()) {
                        UnityPlayer.this.finish();
                    }
                } else if (fVar == f.QUIT) {
                    Looper.myLooper().quit();
                } else if (fVar == f.RESUME) {
                    g.this.b = true;
                } else if (fVar == f.PAUSE) {
                    g.this.b = false;
                } else if (fVar == f.SURFACE_LOST) {
                    g.this.c = false;
                } else {
                    if (fVar == f.SURFACE_ACQUIRED) {
                        g.this.c = true;
                    } else if (fVar == f.FOCUS_LOST) {
                        if (g.this.d == b.a) {
                            UnityPlayer.this.nativeFocusChanged(false);
                        }
                        g.this.d = b.b;
                    } else if (fVar == f.FOCUS_GAINED) {
                        g.this.d = b.c;
                    } else if (fVar == f.URL_ACTIVATED) {
                        UnityPlayer.this.nativeSetLaunchURL(UnityPlayer.this.getLaunchURL());
                    } else if (fVar == f.ORIENTATION_ANGLE_CHANGE) {
                        UnityPlayer.this.nativeOrientationChanged(g.this.f, g.this.g);
                    } else if (fVar == f.SPLASH_ADS_DISMISS) {
                        g.this.h = true;
                    }
                    a();
                }
                if (g.this.b && g.this.e <= 0) {
                    Message.obtain(g.this.a, UnityPlayer.RUN_STATE_CHANGED_MSG_CODE, f.NEXT_FRAME).sendToTarget();
                    g.this.e++;
                }
                return true;
            }
        }

        private g() {
            this.b = false;
            this.c = false;
            this.d = b.b;
            this.e = 0;
            this.h = false;
            this.i = 5;
            this.j = 5;
        }

        /* synthetic */ g(UnityPlayer unityPlayer, byte b) {
            this();
        }

        private void a(f fVar) {
            Handler handler = this.a;
            if (handler != null) {
                Message.obtain(handler, UnityPlayer.RUN_STATE_CHANGED_MSG_CODE, fVar).sendToTarget();
            }
        }

        public final void a() {
            a(f.QUIT);
        }

        public final void a(int i, int i2) {
            this.f = i;
            this.g = i2;
            a(f.ORIENTATION_ANGLE_CHANGE);
        }

        public final void a(Runnable runnable) {
            if (this.a == null) {
                return;
            }
            a(f.PAUSE);
            Message.obtain(this.a, runnable).sendToTarget();
        }

        public final void b() {
            a(f.RESUME);
        }

        public final void b(Runnable runnable) {
            if (this.a == null) {
                return;
            }
            a(f.SURFACE_LOST);
            Message.obtain(this.a, runnable).sendToTarget();
        }

        public final void c() {
            a(f.FOCUS_GAINED);
        }

        public final void c(Runnable runnable) {
            Handler handler = this.a;
            if (handler == null) {
                return;
            }
            Message.obtain(handler, runnable).sendToTarget();
            a(f.SURFACE_ACQUIRED);
        }

        public final void d() {
            a(f.FOCUS_LOST);
        }

        public final void d(Runnable runnable) {
            Handler handler = this.a;
            if (handler != null) {
                Message.obtain(handler, runnable).sendToTarget();
            }
        }

        public final void e() {
            a(f.URL_ACTIVATED);
        }

        public final void f() {
            a(f.SPLASH_ADS_DISMISS);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            setName("UnityMain");
            Looper.prepare();
            this.a = new Handler(new Handler.Callback() { // from class: com.unity3d.player.UnityPlayer.g.1
                AnonymousClass1() {
                }

                private void a() {
                    if (g.this.d == b.c && g.this.c) {
                        UnityPlayer.this.nativeFocusChanged(true);
                        g.this.d = b.a;
                    }
                }

                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    if (message.what != UnityPlayer.RUN_STATE_CHANGED_MSG_CODE) {
                        return false;
                    }
                    f fVar = (f) message.obj;
                    if (fVar == f.NEXT_FRAME) {
                        g.this.e--;
                        UnityPlayer.this.executeGLThreadJobs();
                        if (!g.this.b || !g.this.c) {
                            return true;
                        }
                        if (g.this.i >= 0) {
                            if (g.this.i == 0 && UnityPlayer.this.getSplashEnabled()) {
                                UnityPlayer.this.DisableStaticSplashScreen();
                            }
                            g.this.i--;
                        }
                        if (g.this.i == 0 && UnityPlayer.this.shouldShowLaunchScreenAds) {
                            UnityPlayer.this.ShowSplashAdsScreen();
                        }
                        if (g.this.h && g.this.j >= 0) {
                            if (g.this.j == 0) {
                                UnityPlayer.this.DisableSplashAdsScreen();
                            }
                            g.this.j--;
                        }
                        if (!UnityPlayer.this.isFinishing() && !UnityPlayer.this.nativeRender()) {
                            UnityPlayer.this.finish();
                        }
                    } else if (fVar == f.QUIT) {
                        Looper.myLooper().quit();
                    } else if (fVar == f.RESUME) {
                        g.this.b = true;
                    } else if (fVar == f.PAUSE) {
                        g.this.b = false;
                    } else if (fVar == f.SURFACE_LOST) {
                        g.this.c = false;
                    } else {
                        if (fVar == f.SURFACE_ACQUIRED) {
                            g.this.c = true;
                        } else if (fVar == f.FOCUS_LOST) {
                            if (g.this.d == b.a) {
                                UnityPlayer.this.nativeFocusChanged(false);
                            }
                            g.this.d = b.b;
                        } else if (fVar == f.FOCUS_GAINED) {
                            g.this.d = b.c;
                        } else if (fVar == f.URL_ACTIVATED) {
                            UnityPlayer.this.nativeSetLaunchURL(UnityPlayer.this.getLaunchURL());
                        } else if (fVar == f.ORIENTATION_ANGLE_CHANGE) {
                            UnityPlayer.this.nativeOrientationChanged(g.this.f, g.this.g);
                        } else if (fVar == f.SPLASH_ADS_DISMISS) {
                            g.this.h = true;
                        }
                        a();
                    }
                    if (g.this.b && g.this.e <= 0) {
                        Message.obtain(g.this.a, UnityPlayer.RUN_STATE_CHANGED_MSG_CODE, f.NEXT_FRAME).sendToTarget();
                        g.this.e++;
                    }
                    return true;
                }
            });
            Looper.loop();
        }
    }

    private abstract class h implements Runnable {
        private h() {
        }

        /* synthetic */ h(UnityPlayer unityPlayer, byte b) {
            this();
        }

        public abstract void a();

        @Override // java.lang.Runnable
        public final void run() {
            if (UnityPlayer.this.isFinishing()) {
                return;
            }
            a();
        }
    }

    static {
        new k().a();
    }

    public UnityPlayer(Context context) {
        this(context, null);
    }

    public UnityPlayer(Context context, IUnityPlayerLifecycleEvents iUnityPlayerLifecycleEvents) {
        super(context);
        this.mInitialScreenOrientation = -1;
        this.mMainDisplayOverride = false;
        this.mIsFullscreen = true;
        this.mState = new l();
        this.m_Events = new ConcurrentLinkedQueue();
        this.mKillingIsMyBusiness = null;
        this.mOrientationListener = null;
        this.m_MainThread = new g(this, (byte) 0);
        this.m_AddPhoneCallListener = false;
        this.m_PhoneCallListener = new c(this, (byte) 0);
        this.m_ARCoreApi = null;
        this.m_FakeListener = new a();
        this.m_Camera2Wrapper = null;
        this.m_HFPStatus = null;
        this.m_AudioVolumeHandler = null;
        this.m_launchUri = null;
        this.m_NetworkConnectivity = null;
        this.finishLaunchScreenAds = false;
        this.shouldShowLaunchScreenAds = false;
        this.m_UnityPlayerLifecycleEvents = null;
        this.mProcessKillRequested = true;
        this.mSoftInputDialog = null;
        this.ad = null;
        this.mHanlder = null;
        this.timer = new Timer();
        this.timerTask = null;
        this.m_UnityPlayerLifecycleEvents = iUnityPlayerLifecycleEvents == null ? this : iUnityPlayerLifecycleEvents;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            currentActivity = activity;
            this.mInitialScreenOrientation = activity.getRequestedOrientation();
            this.m_launchUri = currentActivity.getIntent().getData();
        }
        EarlyEnableFullScreenIfVrLaunched(currentActivity);
        this.mContext = context;
        this.mNaturalOrientation = getNaturalOrientation(getResources().getConfiguration().orientation);
        if (currentActivity != null && getSplashEnabled()) {
            j jVar = new j(this.mContext, j.a.a()[getSplashMode()]);
            this.m_SplashScreen = jVar;
            addView(jVar);
        }
        hideStatusBar();
        if (currentActivity != null) {
            this.m_PersistentUnitySurface = new com.unity3d.player.f(this.mContext);
        }
        String strLoadNative = loadNative(getUnityNativeLibraryPath(context));
        if (!l.c()) {
            com.unity3d.player.d.Log(6, "Your hardware does not support this application.");
            AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mContext).setTitle("Failure to initialize!").setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.unity3d.player.UnityPlayer.1
                AnonymousClass1() {
                }

                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    UnityPlayer.this.finish();
                }
            }).setMessage("Your hardware does not support this application.\n\n" + strLoadNative + "\n\n Press OK to quit.").create();
            alertDialogCreate.setCancelable(false);
            alertDialogCreate.show();
            return;
        }
        initJni(context);
        this.mState.c(true);
        if (shouldRegisterLaunch(context)) {
            registerLaunch();
        }
        SurfaceView surfaceViewCreateGlView = CreateGlView();
        this.mGlView = surfaceViewCreateGlView;
        surfaceViewCreateGlView.setContentDescription(GetGlViewContentDescription(context));
        addView(this.mGlView);
        if (this.shouldShowLaunchScreenAds) {
            i iVarGenerateSplashView = generateSplashView(this.mContext);
            this.m_splashAdsScreen = iVarGenerateSplashView;
            if (iVarGenerateSplashView != null) {
                addView(iVarGenerateSplashView);
            } else {
                this.finishLaunchScreenAds = true;
            }
        }
        View view = this.m_SplashScreen;
        if (view != null) {
            bringChildToFront(view);
        }
        this.mQuitting = false;
        hideStatusBar();
        this.m_TelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        this.m_ClipboardManager = (ClipboardManager) this.mContext.getSystemService("clipboard");
        this.m_Camera2Wrapper = new Camera2Wrapper(this.mContext);
        this.m_HFPStatus = new HFPStatus(this.mContext);
        this.m_MainThread.start();
    }

    private SurfaceView CreateGlView() {
        SurfaceView surfaceView = new SurfaceView(this.mContext);
        surfaceView.setId(this.mContext.getResources().getIdentifier("unitySurfaceView", BreakpointSQLiteKey.ID, this.mContext.getPackageName()));
        if (IsWindowTranslucent()) {
            surfaceView.getHolder().setFormat(-3);
            surfaceView.setZOrderOnTop(true);
        } else {
            surfaceView.getHolder().setFormat(-1);
        }
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.unity3d.player.UnityPlayer.28
            AnonymousClass28() {
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                UnityPlayer.this.updateGLDisplay(0, surfaceHolder.getSurface());
                UnityPlayer.this.sendSurfaceChangedEvent();
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceCreated(SurfaceHolder surfaceHolder) {
                UnityPlayer.this.updateGLDisplay(0, surfaceHolder.getSurface());
                if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                    UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                    UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this.mGlView);
                }
                UnityPlayer.this.updateGLDisplay(0, null);
            }
        });
        surfaceView.setFocusable(true);
        surfaceView.setFocusableInTouchMode(true);
        return surfaceView;
    }

    public void DisableSplashAdsScreen() {
        if (this.m_splashAdsScreen != null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500L);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.unity3d.player.UnityPlayer.26

                /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$26$1 */
                final class AnonymousClass1 implements Runnable {
                    AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        UnityPlayer.this.removeView(UnityPlayer.this.m_splashAdsScreen);
                    }
                }

                AnonymousClass26() {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationEnd(Animation animation) {
                    UnityPlayer.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.26.1
                        AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            UnityPlayer.this.removeView(UnityPlayer.this.m_splashAdsScreen);
                        }
                    });
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public final void onAnimationStart(Animation animation) {
                }
            });
            runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.27
                final /* synthetic */ Animation a;

                AnonymousClass27(Animation alphaAnimation2) {
                    animation = alphaAnimation2;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.m_splashAdsScreen.startAnimation(animation);
                }
            });
        }
    }

    public void DisableStaticSplashScreen() {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.22
            AnonymousClass22() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer unityPlayer = UnityPlayer.this;
                unityPlayer.removeView(unityPlayer.m_SplashScreen);
                UnityPlayer.this.m_SplashScreen = null;
            }
        });
    }

    private void EarlyEnableFullScreenIfVrLaunched(Activity activity) {
        View decorView;
        if (activity == null || !activity.getIntent().getBooleanExtra("android.intent.extra.VR_LAUNCH", false) || activity.getWindow() == null || (decorView = activity.getWindow().getDecorView()) == null) {
            return;
        }
        decorView.setSystemUiVisibility(7);
    }

    private String GetGlViewContentDescription(Context context) {
        return context.getResources().getString(context.getResources().getIdentifier("game_view_content_description", "string", context.getPackageName()));
    }

    private boolean IsWindowTranslucent() {
        Activity activity = currentActivity;
        if (activity == null) {
            return false;
        }
        TypedArray typedArrayObtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowIsTranslucent});
        boolean z = typedArrayObtainStyledAttributes.getBoolean(0, false);
        typedArrayObtainStyledAttributes.recycle();
        return z;
    }

    public void ShowSplashAdsScreen() {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.25
            AnonymousClass25() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.m_splashAdsScreen != null) {
                    UnityPlayer unityPlayer = UnityPlayer.this;
                    unityPlayer.bringChildToFront(unityPlayer.m_splashAdsScreen);
                    UnityPlayer.this.m_splashAdsScreen.a();
                }
            }
        });
    }

    public static void UnitySendMessage(String str, String str2, String str3) {
        if (l.c()) {
            try {
                nativeUnitySendMessage(str, str2, str3.getBytes("UTF-8"));
                return;
            } catch (UnsupportedEncodingException unused) {
                return;
            }
        }
        com.unity3d.player.d.Log(5, "Native libraries not loaded - dropping message for " + str + "." + str2);
    }

    private static String bin2hex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b2 & UByte.MAX_VALUE)));
        }
        return sb.toString();
    }

    private void checkResumePlayer() {
        if (this.mState.f()) {
            this.mState.d(true);
            queueGLThreadEvent(new Runnable() { // from class: com.unity3d.player.UnityPlayer.6

                /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$6$1 */
                final class AnonymousClass1 implements Runnable {
                    AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                            UnityPlayer.this.m_PersistentUnitySurface.b(UnityPlayer.this);
                        }
                    }
                }

                AnonymousClass6() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeResume();
                    UnityPlayer.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.6.1
                        AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            if (UnityPlayer.this.m_PersistentUnitySurface != null) {
                                UnityPlayer.this.m_PersistentUnitySurface.b(UnityPlayer.this);
                            }
                        }
                    });
                }
            });
            this.m_MainThread.b();
        }
    }

    public void finish() {
        Context context = this.mContext;
        if (!(context instanceof Activity) || ((Activity) context).isFinishing()) {
            return;
        }
        ((Activity) this.mContext).finish();
    }

    private i generateSplashView(Context context) {
        StorageManager.init(context);
        Storage storage = StorageManager.getStorage(StorageManager.StorageType.PRIVATE);
        if (storage == null) {
            return null;
        }
        Object obj = storage.get("splash-show");
        if (obj != null) {
            try {
                com.unity3d.player.h hVar = new com.unity3d.player.h(new JSONObject(obj.toString()));
                if (hVar.a()) {
                    DeviceLog.info("splash show");
                    if (hVar.g() >= System.currentTimeMillis()) {
                        DeviceLog.info("splash show");
                        storage.delete("splash-show");
                        return new i(this.mContext, this, hVar);
                    }
                }
            } catch (JSONException unused) {
            }
        }
        Object obj2 = storage.get("splash-show-no-fill");
        if (obj2 != null) {
            try {
                DeviceLog.info("splash show no fill");
                com.unity3d.player.h hVar2 = new com.unity3d.player.h(new JSONObject(obj2.toString()));
                if (hVar2.a()) {
                    return new i(this.mContext, this, hVar2);
                }
            } catch (JSONException unused2) {
            }
        }
        DeviceLog.info("splash show nothing");
        return null;
    }

    private boolean getARCoreEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(ARCORE_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    private ApplicationInfo getApplicationInfo() {
        return this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
    }

    private int getNaturalOrientation(int i) {
        int rotation = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if ((rotation == 0 || rotation == 2) && i == 2) {
            return 0;
        }
        return ((rotation == 1 || rotation == 3) && i == 1) ? 0 : 1;
    }

    public static String getQueryString(JSONObject jSONObject) {
        if (jSONObject == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> itKeys = jSONObject.keys();
        String str = "?";
        while (true) {
            sb.append(str);
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String strOptString = jSONObject.optString(next);
                if (strOptString != "") {
                    try {
                        strOptString = URLEncoder.encode(strOptString, "utf-8");
                    } catch (UnsupportedEncodingException e2) {
                        e2.printStackTrace();
                    }
                    sb.append(next);
                    sb.append("=");
                    sb.append(strOptString);
                    str = com.alipay.sdk.sys.a.k;
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    public boolean getSplashEnabled() {
        try {
            return getApplicationInfo().metaData.getBoolean(SPLASH_ENABLE_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    public String getSplashGameId() {
        try {
            return getApplicationInfo().metaData.getString(SPLASH_ADS_GAME_ID);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getUnityNativeLibraryPath(Context context) {
        return context.getApplicationInfo().nativeLibraryDir;
    }

    public static String hash_sha256(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            return bin2hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private void hideStatusBar() {
        Context context = this.mContext;
        if (context instanceof Activity) {
            ((Activity) context).getWindow().setFlags(1024, 1024);
        }
    }

    private final native void initJni(Context context);

    private static String loadNative(String str) {
        String str2 = str + "/libmain.so";
        try {
            try {
                try {
                    System.load(str2);
                } catch (UnsatisfiedLinkError unused) {
                    System.loadLibrary("main");
                }
                if (NativeLoader.load(str)) {
                    l.a();
                    return "";
                }
                com.unity3d.player.d.Log(6, "NativeLoader.load failure, Unity libraries were not loaded.");
                return "NativeLoader.load failure, Unity libraries were not loaded.";
            } catch (UnsatisfiedLinkError e2) {
                return logLoadLibMainError(str2, e2.toString());
            }
        } catch (SecurityException e3) {
            return logLoadLibMainError(str2, e3.toString());
        }
    }

    private static String logLoadLibMainError(String str, String str2) {
        String str3 = "Failed to load 'libmain.so'\n\n" + str2;
        com.unity3d.player.d.Log(6, str3);
        return str3;
    }

    private final native void nativeApplicationUnload();

    private final native boolean nativeDone();

    public final native void nativeFocusChanged(boolean z);

    private final native boolean nativeInjectEvent(InputEvent inputEvent);

    public final native boolean nativeIsAutorotationOn();

    public final native void nativeLowMemory();

    public final native void nativeMuteMasterAudio(boolean z);

    public final native void nativeOrientationChanged(int i, int i2);

    public final native boolean nativePause();

    public final native void nativeRecreateGfxState(int i, Surface surface);

    public final native boolean nativeRender();

    public final native void nativeReportKeyboardConfigChanged();

    private final native void nativeRestartActivityIndicator();

    public final native void nativeResume();

    public final native void nativeSendSurfaceChangedEvent();

    public final native void nativeSetInputArea(int i, int i2, int i3, int i4);

    public final native void nativeSetInputSelection(int i, int i2);

    public final native void nativeSetInputString(String str);

    public final native void nativeSetKeyboardIsVisible(boolean z);

    public final native void nativeSetLaunchURL(String str);

    public final native void nativeSoftInputCanceled();

    public final native void nativeSoftInputClosed();

    public final native void nativeSoftInputLostFocus();

    private static native void nativeUnitySendMessage(String str, String str2, byte[] bArr);

    private void pauseUnity() {
        reportSoftInputStr(null, 1, true);
        if (this.mState.g()) {
            if (l.c()) {
                Semaphore semaphore = new Semaphore(0);
                this.m_MainThread.a(isFinishing() ? new Runnable() { // from class: com.unity3d.player.UnityPlayer.3
                    final /* synthetic */ Semaphore a;

                    AnonymousClass3(Semaphore semaphore2) {
                        semaphore = semaphore2;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        UnityPlayer.this.shutdown();
                        semaphore.release();
                    }
                } : new Runnable() { // from class: com.unity3d.player.UnityPlayer.4
                    final /* synthetic */ Semaphore a;

                    AnonymousClass4(Semaphore semaphore2) {
                        semaphore = semaphore2;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        if (!UnityPlayer.this.nativePause()) {
                            semaphore.release();
                            return;
                        }
                        UnityPlayer.this.mQuitting = true;
                        UnityPlayer.this.shutdown();
                        semaphore.release(2);
                    }
                });
                try {
                    if (!semaphore2.tryAcquire(4L, TimeUnit.SECONDS)) {
                        com.unity3d.player.d.Log(5, "Timeout while trying to pause the Unity Engine.");
                    }
                } catch (InterruptedException unused) {
                    com.unity3d.player.d.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
                }
                if (semaphore2.drainPermits() > 0) {
                    destroy();
                }
            }
            this.mState.d(false);
            this.mState.b(true);
            if (this.m_AddPhoneCallListener) {
                this.m_TelephonyManager.listen(this.m_PhoneCallListener, 0);
            }
        }
    }

    private void queueGLThreadEvent(h hVar) {
        if (isFinishing()) {
            return;
        }
        queueGLThreadEvent((Runnable) hVar);
    }

    private void queueGLThreadEvent(Runnable runnable) {
        if (l.c()) {
            if (Thread.currentThread() == this.m_MainThread) {
                runnable.run();
            } else {
                this.m_Events.add(runnable);
            }
        }
    }

    private void registerLaunch() {
        Boolean.valueOf(false);
        Boolean.valueOf(false);
        Activity activity = currentActivity;
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences("game_detail", 0);
            String string = sharedPreferences.getString("game_id", "");
            Boolean boolValueOf = Boolean.valueOf(sharedPreferences.getBoolean("show_ads", false));
            Boolean boolValueOf2 = Boolean.valueOf(sharedPreferences.getBoolean("blocked", false));
            if (boolValueOf2.booleanValue() || !boolValueOf.booleanValue() || string == "") {
                this.shouldShowLaunchScreenAds = false;
                if (boolValueOf2.booleanValue()) {
                    showBlockDialog();
                }
            } else {
                this.shouldShowLaunchScreenAds = true;
                UnityAds.initialize(currentActivity, string, null);
            }
        } else {
            this.shouldShowLaunchScreenAds = false;
        }
        new e().execute(getContext().getPackageName(), Device.getSIMMCC(getContext()));
    }

    public void sendSurfaceChangedEvent() {
        if (l.c() && this.mState.e()) {
            this.m_MainThread.d(new Runnable() { // from class: com.unity3d.player.UnityPlayer.29
                AnonymousClass29() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeSendSurfaceChangedEvent();
                }
            });
        }
    }

    private void showBlockDialog() {
        this.ad = new AlertDialog.Builder(this.mContext).setTitle("Sorry").setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.unity3d.player.UnityPlayer.20
            AnonymousClass20() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UnityPlayer.this.mHanlder.sendEmptyMessage(1);
            }
        }).setMessage("The app is using unauthorized engine, please contact the publisher!").setCancelable(false).create();
        this.mHanlder = new Handler() { // from class: com.unity3d.player.UnityPlayer.21
            AnonymousClass21() {
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                if (i != 0) {
                    if (i == 1 && UnityPlayer.this.ad != null && UnityPlayer.this.ad.isShowing()) {
                        UnityPlayer.this.ad.hide();
                    }
                } else if (UnityPlayer.this.ad != null && !UnityPlayer.this.ad.isShowing()) {
                    UnityPlayer.this.ad.show();
                }
                super.handleMessage(message);
            }
        };
        AnonymousClass23 anonymousClass23 = new TimerTask() { // from class: com.unity3d.player.UnityPlayer.23
            AnonymousClass23() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public final void run() {
                UnityPlayer.this.mHanlder.sendEmptyMessage(0);
            }
        };
        this.timerTask = anonymousClass23;
        this.timer.schedule(anonymousClass23, 0L, 600000L);
    }

    public void shutdown() {
        this.mProcessKillRequested = nativeDone();
        this.mState.c(false);
    }

    private void swapViews(View view, View view2) {
        boolean z;
        if (this.mState.d()) {
            z = false;
        } else {
            pause();
            z = true;
        }
        if (view != null) {
            ViewParent parent = view.getParent();
            if (!(parent instanceof UnityPlayer) || ((UnityPlayer) parent) != this) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(view);
                }
                addView(view);
                bringChildToFront(view);
                view.setVisibility(0);
            }
        }
        if (view2 != null && view2.getParent() == this) {
            view2.setVisibility(8);
            removeView(view2);
        }
        if (z) {
            resume();
        }
    }

    private static void unloadNative() {
        if (l.c()) {
            if (!NativeLoader.unload()) {
                throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
            }
            l.b();
        }
    }

    private boolean updateDisplayInternal(int i, Surface surface) {
        if (!l.c() || !this.mState.e()) {
            return false;
        }
        Semaphore semaphore = new Semaphore(0);
        AnonymousClass30 anonymousClass30 = new Runnable() { // from class: com.unity3d.player.UnityPlayer.30
            final /* synthetic */ int a;
            final /* synthetic */ Surface b;
            final /* synthetic */ Semaphore c;

            AnonymousClass30(int i2, Surface surface2, Semaphore semaphore2) {
                i = i2;
                surface = surface2;
                semaphore = semaphore2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.nativeRecreateGfxState(i, surface);
                semaphore.release();
            }
        };
        if (i2 == 0) {
            g gVar = this.m_MainThread;
            if (surface2 == null) {
                gVar.b(anonymousClass30);
            } else {
                gVar.c(anonymousClass30);
            }
        } else {
            anonymousClass30.run();
        }
        if (surface2 != null || i2 != 0) {
            return true;
        }
        try {
            if (semaphore2.tryAcquire(4L, TimeUnit.SECONDS)) {
                return true;
            }
            com.unity3d.player.d.Log(5, "Timeout while trying detaching primary window.");
            return true;
        } catch (InterruptedException unused) {
            com.unity3d.player.d.Log(5, "UI thread got interrupted while trying to detach the primary window from the Unity Engine.");
            return true;
        }
    }

    public void updateGLDisplay(int i, Surface surface) {
        if (this.mMainDisplayOverride) {
            return;
        }
        updateDisplayInternal(i, surface);
    }

    public void NotifySplashAdsFinished() {
        this.finishLaunchScreenAds = true;
        this.m_MainThread.f();
    }

    protected void addPhoneCallListener() {
        this.m_AddPhoneCallListener = true;
        this.m_TelephonyManager.listen(this.m_PhoneCallListener, 32);
    }

    public boolean addViewToPlayer(View view, boolean z) {
        swapViews(view, z ? this.mGlView : null);
        boolean z2 = true;
        boolean z3 = view.getParent() == this;
        boolean z4 = z && this.mGlView.getParent() == null;
        boolean z5 = this.mGlView.getParent() == this;
        if (!z3 || (!z4 && !z5)) {
            z2 = false;
        }
        if (!z2) {
            if (!z3) {
                com.unity3d.player.d.Log(6, "addViewToPlayer: Failure adding view to hierarchy");
            }
            if (!z4 && !z5) {
                com.unity3d.player.d.Log(6, "addViewToPlayer: Failure removing old view from hierarchy");
            }
        }
        return z2;
    }

    public void configurationChanged(Configuration configuration) {
        SurfaceView surfaceView = this.mGlView;
        if (surfaceView instanceof SurfaceView) {
            surfaceView.getHolder().setSizeFromLayout();
        }
        n nVar = this.mVideoPlayerProxy;
        if (nVar != null) {
            nVar.c();
        }
    }

    public void destroy() {
        com.unity3d.player.f fVar = this.m_PersistentUnitySurface;
        if (fVar != null) {
            fVar.a();
            this.m_PersistentUnitySurface = null;
        }
        Camera2Wrapper camera2Wrapper = this.m_Camera2Wrapper;
        if (camera2Wrapper != null) {
            camera2Wrapper.a();
            this.m_Camera2Wrapper = null;
        }
        HFPStatus hFPStatus = this.m_HFPStatus;
        if (hFPStatus != null) {
            hFPStatus.a();
            this.m_HFPStatus = null;
        }
        NetworkConnectivity networkConnectivity = this.m_NetworkConnectivity;
        if (networkConnectivity != null) {
            networkConnectivity.b();
            this.m_NetworkConnectivity = null;
        }
        this.mQuitting = true;
        if (!this.mState.d()) {
            pause();
        }
        this.m_MainThread.a();
        try {
            this.m_MainThread.join(4000L);
        } catch (InterruptedException unused) {
            this.m_MainThread.interrupt();
        }
        BroadcastReceiver broadcastReceiver = this.mKillingIsMyBusiness;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
        this.mKillingIsMyBusiness = null;
        if (l.c()) {
            removeAllViews();
        }
        if (this.mProcessKillRequested) {
            this.m_UnityPlayerLifecycleEvents.onUnityPlayerQuitted();
            kill();
        }
        unloadNative();
    }

    protected void disableLogger() {
        com.unity3d.player.d.a = true;
    }

    public boolean displayChanged(int i, Surface surface) {
        if (i == 0) {
            this.mMainDisplayOverride = surface != null;
            runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.2
                AnonymousClass2() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    if (UnityPlayer.this.mMainDisplayOverride) {
                        UnityPlayer unityPlayer = UnityPlayer.this;
                        unityPlayer.removeView(unityPlayer.mGlView);
                    } else {
                        UnityPlayer unityPlayer2 = UnityPlayer.this;
                        unityPlayer2.addView(unityPlayer2.mGlView);
                    }
                }
            });
        }
        return updateDisplayInternal(i, surface);
    }

    protected void executeGLThreadJobs() {
        while (true) {
            Runnable runnable = (Runnable) this.m_Events.poll();
            if (runnable == null) {
                return;
            } else {
                runnable.run();
            }
        }
    }

    protected String getBuilderUserId() {
        try {
            return getApplicationInfo().metaData.getString(UNITY_BUILDER_ID);
        } catch (Exception unused) {
            return null;
        }
    }

    protected String getClipboardText() {
        ClipData primaryClip = this.m_ClipboardManager.getPrimaryClip();
        return primaryClip != null ? primaryClip.getItemAt(0).coerceToText(this.mContext).toString() : "";
    }

    public String getDeviceId() {
        Activity activity = currentActivity;
        if (activity == null) {
            return "";
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences("device_detail", 0);
        String string = sharedPreferences.getString(CommonParam.DEVICE_ID, null);
        if (string != null) {
            return string;
        }
        String uniqueEventId = Device.getUniqueEventId();
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putString(CommonParam.DEVICE_ID, uniqueEventId);
        editorEdit.commit();
        return uniqueEventId;
    }

    protected String getKeyboardLayout() {
        com.unity3d.player.g gVar = this.mSoftInputDialog;
        if (gVar == null) {
            return null;
        }
        return gVar.a();
    }

    protected String getLaunchURL() {
        Uri uri = this.m_launchUri;
        if (uri != null) {
            return uri.toString();
        }
        return null;
    }

    protected int getNetworkConnectivity() {
        if (!PlatformSupport.NOUGAT_SUPPORT) {
            return 0;
        }
        if (this.m_NetworkConnectivity == null) {
            this.m_NetworkConnectivity = new NetworkConnectivity(this.mContext);
        }
        return this.m_NetworkConnectivity.a();
    }

    public String getNetworkProxySettings(String str) {
        String str2;
        String str3;
        if (!str.startsWith("http:")) {
            if (str.startsWith("https:")) {
                str2 = "https.proxyHost";
                str3 = "https.proxyPort";
            }
            return null;
        }
        str2 = "http.proxyHost";
        str3 = "http.proxyPort";
        String property = System.getProperties().getProperty(str2);
        if (property != null && !"".equals(property)) {
            StringBuilder sb = new StringBuilder(property);
            String property2 = System.getProperties().getProperty(str3);
            if (property2 != null && !"".equals(property2)) {
                sb.append(":");
                sb.append(property2);
            }
            String property3 = System.getProperties().getProperty("http.nonProxyHosts");
            if (property3 != null && !"".equals(property3)) {
                sb.append('\n');
                sb.append(property3);
            }
            return sb.toString();
        }
        return null;
    }

    public Bundle getSettings() {
        return Bundle.EMPTY;
    }

    protected Boolean getShowSplashSlogan() {
        try {
            return Boolean.valueOf(getApplicationInfo().metaData.getBoolean(SPLASH_ADS_SLOGAN));
        } catch (Exception unused) {
            return false;
        }
    }

    protected int getShowSplashSloganHeight() {
        try {
            return getApplicationInfo().metaData.getInt(SPLASH_ADS_SLOGAN_HEIGHT, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        } catch (Exception unused) {
            return 150;
        }
    }

    protected int getSplashMode() {
        try {
            return getApplicationInfo().metaData.getInt(SPLASH_MODE_METADATA_NAME);
        } catch (Exception unused) {
            return 0;
        }
    }

    public View getView() {
        return this;
    }

    protected void hideSoftInput() {
        postOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.8
            AnonymousClass8() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.reportSoftInputArea(new Rect());
                UnityPlayer.this.reportSoftInputIsVisible(false);
                if (UnityPlayer.this.mSoftInputDialog != null) {
                    UnityPlayer.this.mSoftInputDialog.dismiss();
                    UnityPlayer.this.mSoftInputDialog = null;
                    UnityPlayer.this.nativeReportKeyboardConfigChanged();
                }
            }
        });
    }

    public void init(int i, boolean z) {
    }

    protected boolean initializeGoogleAr() {
        if (this.m_ARCoreApi != null || currentActivity == null || !getARCoreEnabled()) {
            return false;
        }
        GoogleARCoreApi googleARCoreApi = new GoogleARCoreApi();
        this.m_ARCoreApi = googleARCoreApi;
        googleARCoreApi.initializeARCore(currentActivity);
        if (this.mState.d()) {
            return false;
        }
        this.m_ARCoreApi.resumeARCore();
        return false;
    }

    public boolean injectEvent(InputEvent inputEvent) {
        if (l.c()) {
            return nativeInjectEvent(inputEvent);
        }
        return false;
    }

    protected boolean isFinishing() {
        if (!this.mQuitting) {
            Context context = this.mContext;
            boolean z = (context instanceof Activity) && ((Activity) context).isFinishing();
            this.mQuitting = z;
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public boolean isLaunchScreenAdsFinished() {
        return this.finishLaunchScreenAds || UnityAds.isSkipLaunchScreenAds();
    }

    public boolean isShouldShowLaunchScreenAds() {
        return this.shouldShowLaunchScreenAds;
    }

    protected void kill() {
        Process.killProcess(Process.myPid());
    }

    protected boolean loadLibrary(String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (Exception | UnsatisfiedLinkError unused) {
            return false;
        }
    }

    public void lowMemory() {
        if (l.c()) {
            queueGLThreadEvent(new Runnable() { // from class: com.unity3d.player.UnityPlayer.5
                AnonymousClass5() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    UnityPlayer.this.nativeLowMemory();
                }
            });
        }
    }

    public void newIntent(Intent intent) {
        this.m_launchUri = intent.getData();
        this.m_MainThread.e();
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return injectEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return injectEvent(motionEvent);
    }

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerQuitted() {
    }

    @Override // com.unity3d.player.IUnityPlayerLifecycleEvents
    public void onUnityPlayerUnloaded() {
    }

    public void pause() {
        GoogleARCoreApi googleARCoreApi = this.m_ARCoreApi;
        if (googleARCoreApi != null) {
            googleARCoreApi.pauseARCore();
        }
        n nVar = this.mVideoPlayerProxy;
        if (nVar != null) {
            nVar.a();
        }
        AudioVolumeHandler audioVolumeHandler = this.m_AudioVolumeHandler;
        if (audioVolumeHandler != null) {
            audioVolumeHandler.a();
            this.m_AudioVolumeHandler = null;
        }
        i iVar = this.m_splashAdsScreen;
        if (iVar != null) {
            iVar.b();
        }
        pauseUnity();
    }

    protected void pauseJavaAndCallUnloadCallback() {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.19
            AnonymousClass19() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.pause();
                UnityPlayer.this.windowFocusChanged(false);
                UnityPlayer.this.m_UnityPlayerLifecycleEvents.onUnityPlayerUnloaded();
            }
        });
    }

    void postOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public void quit() {
        destroy();
    }

    public void removeViewFromPlayer(View view) {
        swapViews(this.mGlView, view);
        boolean z = view.getParent() == null;
        boolean z2 = this.mGlView.getParent() == this;
        if (z && z2) {
            return;
        }
        if (!z) {
            com.unity3d.player.d.Log(6, "removeViewFromPlayer: Failure removing view from hierarchy");
        }
        if (z2) {
            return;
        }
        com.unity3d.player.d.Log(6, "removeVireFromPlayer: Failure agging old view to hierarchy");
    }

    public void reportError(String str, String str2) {
        com.unity3d.player.d.Log(6, str + ": " + str2);
    }

    protected void reportSoftInputArea(Rect rect) {
        queueGLThreadEvent((h) new h() { // from class: com.unity3d.player.UnityPlayer.15
            final /* synthetic */ Rect a;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass15(Rect rect2) {
                super(UnityPlayer.this, (byte) 0);
                rect = rect2;
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                UnityPlayer.this.nativeSetInputArea(rect.left, rect.top, rect.right, rect.bottom);
            }
        });
    }

    protected void reportSoftInputIsVisible(boolean z) {
        queueGLThreadEvent((h) new h() { // from class: com.unity3d.player.UnityPlayer.16
            final /* synthetic */ boolean a;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass16(boolean z2) {
                super(UnityPlayer.this, (byte) 0);
                z = z2;
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                UnityPlayer.this.nativeSetKeyboardIsVisible(z);
            }
        });
    }

    protected void reportSoftInputSelection(int i, int i2) {
        queueGLThreadEvent((h) new h() { // from class: com.unity3d.player.UnityPlayer.14
            final /* synthetic */ int a;
            final /* synthetic */ int b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass14(int i3, int i22) {
                super(UnityPlayer.this, (byte) 0);
                i = i3;
                i = i22;
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                UnityPlayer.this.nativeSetInputSelection(i, i);
            }
        });
    }

    protected void reportSoftInputStr(String str, int i, boolean z) {
        if (i == 1) {
            hideSoftInput();
        }
        queueGLThreadEvent((h) new h() { // from class: com.unity3d.player.UnityPlayer.13
            final /* synthetic */ boolean a;
            final /* synthetic */ String b;
            final /* synthetic */ int c;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass13(boolean z2, String str2, int i2) {
                super(UnityPlayer.this, (byte) 0);
                z = z2;
                str = str2;
                i = i2;
            }

            @Override // com.unity3d.player.UnityPlayer.h
            public final void a() {
                if (z) {
                    UnityPlayer.this.nativeSoftInputCanceled();
                } else {
                    String str2 = str;
                    if (str2 != null) {
                        UnityPlayer.this.nativeSetInputString(str2);
                    }
                }
                if (i == 1) {
                    UnityPlayer.this.nativeSoftInputClosed();
                }
            }
        });
    }

    protected void requestUserAuthorization(String str) {
        Activity activity;
        if (str == null || str.isEmpty() || (activity = currentActivity) == null) {
            return;
        }
        UnityPermissions.requestUserPermissions(activity, new String[]{str}, null);
    }

    public void resume() {
        GoogleARCoreApi googleARCoreApi = this.m_ARCoreApi;
        if (googleARCoreApi != null) {
            googleARCoreApi.resumeARCore();
        }
        this.mState.b(false);
        n nVar = this.mVideoPlayerProxy;
        if (nVar != null) {
            nVar.b();
        }
        i iVar = this.m_splashAdsScreen;
        if (iVar != null) {
            iVar.c();
        }
        checkResumePlayer();
        if (l.c()) {
            nativeRestartActivityIndicator();
        }
        this.m_AudioVolumeHandler = new AudioVolumeHandler(this.mContext);
    }

    void runOnAnonymousThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    void runOnUiThread(Runnable runnable) {
        Context context = this.mContext;
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        } else {
            com.unity3d.player.d.Log(5, "Not running Unity from an Activity; ignored...");
        }
    }

    protected void setCharacterLimit(int i) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.10
            final /* synthetic */ int a;

            AnonymousClass10(int i2) {
                i = i2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.mSoftInputDialog != null) {
                    UnityPlayer.this.mSoftInputDialog.a(i);
                }
            }
        });
    }

    protected void setClipboardText(String str) {
        this.m_ClipboardManager.setPrimaryClip(ClipData.newPlainText("Text", str));
    }

    protected void setHideInputField(boolean z) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.11
            final /* synthetic */ boolean a;

            AnonymousClass11(boolean z2) {
                z = z2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.mSoftInputDialog != null) {
                    UnityPlayer.this.mSoftInputDialog.a(z);
                }
            }
        });
    }

    protected void setSelection(int i, int i2) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.12
            final /* synthetic */ int a;
            final /* synthetic */ int b;

            AnonymousClass12(int i3, int i22) {
                i = i3;
                i = i22;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.mSoftInputDialog != null) {
                    UnityPlayer.this.mSoftInputDialog.a(i, i);
                }
            }
        });
    }

    protected void setSoftInputStr(String str) {
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.9
            final /* synthetic */ String a;

            AnonymousClass9(String str2) {
                str = str2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                if (UnityPlayer.this.mSoftInputDialog == null || str == null) {
                    return;
                }
                UnityPlayer.this.mSoftInputDialog.a(str);
            }
        });
    }

    public boolean shouldRegisterLaunch(Context context) {
        String simmcc = Device.getSIMMCC(context);
        return (simmcc == null || "".equals(simmcc) || "466".equals(simmcc)) ? false : true;
    }

    protected void showSoftInput(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, String str2, int i2, boolean z5) {
        postOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.7
            final /* synthetic */ UnityPlayer a;
            final /* synthetic */ String b;
            final /* synthetic */ int c;
            final /* synthetic */ boolean d;
            final /* synthetic */ boolean e;
            final /* synthetic */ boolean f;
            final /* synthetic */ boolean g;
            final /* synthetic */ String h;
            final /* synthetic */ int i;
            final /* synthetic */ boolean j;

            /* JADX INFO: renamed from: com.unity3d.player.UnityPlayer$7$1 */
            final class AnonymousClass1 implements DialogInterface.OnCancelListener {
                AnonymousClass1() {
                }

                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    UnityPlayer.this.nativeSoftInputLostFocus();
                    UnityPlayer.this.reportSoftInputStr(null, 1, false);
                }
            }

            AnonymousClass7(UnityPlayer this, String str3, int i3, boolean z6, boolean z22, boolean z32, boolean z42, String str22, int i22, boolean z52) {
                unityPlayer = this;
                str = str3;
                i = i3;
                z = z6;
                z = z22;
                z = z32;
                z = z42;
                str = str22;
                i = i22;
                z = z52;
            }

            @Override // java.lang.Runnable
            public final void run() {
                UnityPlayer.this.mSoftInputDialog = new com.unity3d.player.g(UnityPlayer.this.mContext, unityPlayer, str, i, z, z, z, str, i, z);
                UnityPlayer.this.mSoftInputDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.unity3d.player.UnityPlayer.7.1
                    AnonymousClass1() {
                    }

                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        UnityPlayer.this.nativeSoftInputLostFocus();
                        UnityPlayer.this.reportSoftInputStr(null, 1, false);
                    }
                });
                UnityPlayer.this.mSoftInputDialog.show();
                UnityPlayer.this.nativeReportKeyboardConfigChanged();
            }
        });
    }

    protected boolean showVideoPlayer(String str, int i, int i2, int i3, boolean z, int i4, int i5) {
        if (this.mVideoPlayerProxy == null) {
            this.mVideoPlayerProxy = new n(this);
        }
        boolean zA = this.mVideoPlayerProxy.a(this.mContext, str, i, i2, i3, z, i4, i5, new n.a() { // from class: com.unity3d.player.UnityPlayer.17
            AnonymousClass17() {
            }

            @Override // com.unity3d.player.n.a
            public final void a() {
                UnityPlayer.this.mVideoPlayerProxy = null;
            }
        });
        if (zA) {
            runOnUiThread(new Runnable() { // from class: com.unity3d.player.UnityPlayer.18
                AnonymousClass18() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    if (UnityPlayer.this.nativeIsAutorotationOn() && (UnityPlayer.this.mContext instanceof Activity)) {
                        ((Activity) UnityPlayer.this.mContext).setRequestedOrientation(UnityPlayer.this.mInitialScreenOrientation);
                    }
                }
            });
        }
        return zA;
    }

    protected boolean skipPermissionsDialog() {
        Activity activity = currentActivity;
        if (activity != null) {
            return UnityPermissions.skipPermissionsDialog(activity);
        }
        return false;
    }

    public boolean startOrientationListener(int i) {
        String str;
        if (this.mOrientationListener != null) {
            str = "Orientation Listener already started.";
        } else {
            AnonymousClass24 anonymousClass24 = new OrientationEventListener(this.mContext, i) { // from class: com.unity3d.player.UnityPlayer.24
                AnonymousClass24(Context context, int i2) {
                    super(context, i2);
                }

                @Override // android.view.OrientationEventListener
                public final void onOrientationChanged(int i2) {
                    UnityPlayer.this.m_MainThread.a(UnityPlayer.this.mNaturalOrientation, i2);
                }
            };
            this.mOrientationListener = anonymousClass24;
            if (anonymousClass24.canDetectOrientation()) {
                this.mOrientationListener.enable();
                return true;
            }
            str = "Orientation Listener cannot detect orientation.";
        }
        com.unity3d.player.d.Log(5, str);
        return false;
    }

    public boolean stopOrientationListener() {
        OrientationEventListener orientationEventListener = this.mOrientationListener;
        if (orientationEventListener == null) {
            com.unity3d.player.d.Log(5, "Orientation Listener was not started.");
            return false;
        }
        orientationEventListener.disable();
        this.mOrientationListener = null;
        return true;
    }

    protected void toggleGyroscopeSensor(boolean z) {
        SensorManager sensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        Sensor defaultSensor = sensorManager.getDefaultSensor(11);
        if (z) {
            sensorManager.registerListener(this.m_FakeListener, defaultSensor, 1);
        } else {
            sensorManager.unregisterListener(this.m_FakeListener);
        }
    }

    public void unload() {
        nativeApplicationUnload();
    }

    public void windowFocusChanged(boolean z) {
        this.mState.a(z);
        if (this.mState.e()) {
            if (z) {
                this.m_MainThread.c();
            } else {
                this.m_MainThread.d();
            }
            checkResumePlayer();
        }
    }
}
