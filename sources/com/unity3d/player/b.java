package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import android.util.SizeF;
import android.view.Surface;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static CameraManager b;
    private static String[] c;
    private static Semaphore e = new Semaphore(1);
    private c a;
    private CameraDevice d;
    private HandlerThread f;
    private Handler g;
    private Rect h;
    private Rect i;
    private int j;
    private int k;
    private int n;
    private int o;
    private Range q;
    private Image s;
    private CaptureRequest.Builder t;
    private int w;
    private SurfaceTexture x;
    private float l = -1.0f;
    private float m = -1.0f;
    private boolean p = false;
    private ImageReader r = null;
    private CameraCaptureSession u = null;
    private Object v = new Object();
    private Surface y = null;
    private int z = a.c;
    private CameraCaptureSession.CaptureCallback A = new CameraCaptureSession.CaptureCallback() { // from class: com.unity3d.player.b.1
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            b.this.a(captureRequest.getTag());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            d.Log(5, "Camera2: Capture session failed " + captureRequest.getTag() + " reason " + captureFailure.getReason());
            b.this.a(captureRequest.getTag());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
        }
    };
    private final CameraDevice.StateCallback B = new CameraDevice.StateCallback() { // from class: com.unity3d.player.b.3
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(CameraDevice cameraDevice) {
            b.e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(CameraDevice cameraDevice) {
            d.Log(5, "Camera2: CameraDevice disconnected.");
            b.this.a(cameraDevice);
            b.e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(CameraDevice cameraDevice, int i) {
            d.Log(6, "Camera2: Error opeining CameraDevice " + i);
            b.this.a(cameraDevice);
            b.e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(CameraDevice cameraDevice) {
            b.this.d = cameraDevice;
            b.e.release();
        }
    };
    private final ImageReader.OnImageAvailableListener C = new ImageReader.OnImageAvailableListener() { // from class: com.unity3d.player.b.4
        @Override // android.media.ImageReader.OnImageAvailableListener
        public final void onImageAvailable(ImageReader imageReader) {
            if (b.e.tryAcquire()) {
                Image imageAcquireNextImage = imageReader.acquireNextImage();
                if (imageAcquireNextImage != null) {
                    Image.Plane[] planes = imageAcquireNextImage.getPlanes();
                    if (imageAcquireNextImage.getFormat() == 35 && planes != null && planes.length == 3) {
                        b.this.a.a(planes[0].getBuffer(), planes[1].getBuffer(), planes[2].getBuffer(), planes[0].getRowStride(), planes[1].getRowStride(), planes[1].getPixelStride());
                    } else {
                        d.Log(6, "Camera2: Wrong image format.");
                    }
                    if (b.this.s != null) {
                        b.this.s.close();
                    }
                    b.this.s = imageAcquireNextImage;
                }
                b.e.release();
            }
        }
    };
    private final SurfaceTexture.OnFrameAvailableListener D = new SurfaceTexture.OnFrameAvailableListener() { // from class: com.unity3d.player.b.5
        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
        public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
            b.this.a.a(surfaceTexture);
        }
    };

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    private static final class a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {1, 2, 3};
    }

    protected b(c cVar) {
        this.a = null;
        this.a = cVar;
        g();
    }

    public static int a(Context context) {
        return c(context).length;
    }

    public static int a(Context context, int i) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i]).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
            return 0;
        }
    }

    private static int a(Range[] rangeArr, int i) {
        int i2 = -1;
        double d = Double.MAX_VALUE;
        for (int i3 = 0; i3 < rangeArr.length; i3++) {
            int iIntValue = ((Integer) rangeArr[i3].getLower()).intValue();
            int iIntValue2 = ((Integer) rangeArr[i3].getUpper()).intValue();
            float f = i;
            if (f + 0.1f > iIntValue && f - 0.1f < iIntValue2) {
                return i;
            }
            if (dMin < d) {
                i2 = i3;
                d = dMin;
            }
        }
        return ((Integer) (i > ((Integer) rangeArr[i2].getUpper()).intValue() ? rangeArr[i2].getUpper() : rangeArr[i2].getLower())).intValue();
    }

    private static Rect a(Size[] sizeArr, double d, double d2) {
        double d3 = Double.MAX_VALUE;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < sizeArr.length; i3++) {
            int width = sizeArr[i3].getWidth();
            int height = sizeArr[i3].getHeight();
            double dAbs = Math.abs(Math.log(d / ((double) width))) + Math.abs(Math.log(d2 / ((double) height)));
            if (dAbs < d3) {
                i = width;
                i2 = height;
                d3 = dAbs;
            }
        }
        return new Rect(0, 0, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CameraDevice cameraDevice) {
        synchronized (this.v) {
            this.u = null;
        }
        cameraDevice.close();
        this.d = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj) {
        if (obj != "Focus") {
            if (obj == "Cancel focus") {
                synchronized (this.v) {
                    if (this.u != null) {
                        j();
                    }
                }
                return;
            }
            return;
        }
        this.p = false;
        synchronized (this.v) {
            if (this.u != null) {
                try {
                    this.t.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
                    this.t.setTag("Regular");
                    this.u.setRepeatingRequest(this.t.build(), this.A, this.g);
                } catch (CameraAccessException e2) {
                    d.Log(6, "Camera2: CameraAccessException " + e2);
                }
            }
        }
    }

    private static Size[] a(CameraCharacteristics cameraCharacteristics) {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null) {
            d.Log(6, "Camera2: configuration map is not available.");
            return null;
        }
        Size[] outputSizes = streamConfigurationMap.getOutputSizes(35);
        if (outputSizes == null || outputSizes.length == 0) {
            return null;
        }
        return outputSizes;
    }

    private static CameraManager b(Context context) {
        if (b == null) {
            b = (CameraManager) context.getSystemService("camera");
        }
        return b;
    }

    private void b(CameraCharacteristics cameraCharacteristics) {
        int iIntValue = ((Integer) cameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue();
        this.k = iIntValue;
        if (iIntValue > 0) {
            this.i = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            float fWidth = this.h.width() / this.h.height();
            if (fWidth > r4.width() / this.i.height()) {
                this.n = 0;
                this.o = (int) ((this.i.height() - (this.i.width() / fWidth)) / 2.0f);
            } else {
                this.o = 0;
                this.n = (int) ((this.i.width() - (this.i.height() * fWidth)) / 2.0f);
            }
            this.j = Math.min(this.i.width(), this.i.height()) / 20;
        }
    }

    public static boolean b(Context context, int i) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i]).get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
            return false;
        }
    }

    public static boolean c(Context context, int i) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i]).get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF)).intValue() > 0;
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
            return false;
        }
    }

    private static String[] c(Context context) {
        if (c == null) {
            try {
                c = b(context).getCameraIdList();
            } catch (CameraAccessException e2) {
                d.Log(6, "Camera2: CameraAccessException " + e2);
                c = new String[0];
            }
        }
        return c;
    }

    public static int d(Context context, int i) {
        try {
            CameraCharacteristics cameraCharacteristics = b(context).getCameraCharacteristics(c(context)[i]);
            float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
            SizeF sizeF = (SizeF) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
            if (fArr.length > 0) {
                return (int) ((fArr[0] * 36.0f) / sizeF.getWidth());
            }
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
        }
        return 0;
    }

    public static int[] e(Context context, int i) {
        try {
            Size[] sizeArrA = a(b(context).getCameraCharacteristics(c(context)[i]));
            if (sizeArrA == null) {
                return null;
            }
            int[] iArr = new int[sizeArrA.length * 2];
            for (int i2 = 0; i2 < sizeArrA.length; i2++) {
                int i3 = i2 * 2;
                iArr[i3] = sizeArrA[i2].getWidth();
                iArr[i3 + 1] = sizeArrA[i2].getHeight();
            }
            return iArr;
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
            return null;
        }
    }

    private void g() {
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.f = handlerThread;
        handlerThread.start();
        this.g = new Handler(this.f.getLooper());
    }

    private void h() {
        this.f.quit();
        try {
            this.f.join(4000L);
            this.f = null;
            this.g = null;
        } catch (InterruptedException e2) {
            this.f.interrupt();
            d.Log(6, "Camera2: Interrupted while waiting for the background thread to finish " + e2);
        }
    }

    private void i() {
        try {
            if (!e.tryAcquire(4L, TimeUnit.SECONDS)) {
                d.Log(5, "Camera2: Timeout waiting to lock camera for closing.");
                return;
            }
            this.d.close();
            try {
                if (!e.tryAcquire(4L, TimeUnit.SECONDS)) {
                    d.Log(5, "Camera2: Timeout waiting to close camera.");
                }
            } catch (InterruptedException e2) {
                d.Log(6, "Camera2: Interrupted while waiting to close camera " + e2);
            }
            this.d = null;
            e.release();
        } catch (InterruptedException e3) {
            d.Log(6, "Camera2: Interrupted while trying to lock camera for closing " + e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        try {
            if (this.k != 0) {
                float f = this.l;
                if (f >= 0.0f && f <= 1.0f) {
                    float f2 = this.m;
                    if (f2 >= 0.0f && f2 <= 1.0f) {
                        this.p = true;
                        int iWidth = this.i.width();
                        int i = (int) (((iWidth - (r2 * 2)) * this.l) + this.n);
                        int iHeight = this.i.height();
                        int i2 = this.o;
                        int i3 = (int) ((((double) (iHeight - (i2 * 2))) * (1.0d - ((double) this.m))) + ((double) i2));
                        int iMax = Math.max(this.j + 1, Math.min(i, (this.i.width() - this.j) - 1));
                        int iMax2 = Math.max(this.j + 1, Math.min(i3, (this.i.height() - this.j) - 1));
                        CaptureRequest.Builder builder = this.t;
                        CaptureRequest.Key key = CaptureRequest.CONTROL_AF_REGIONS;
                        int i4 = this.j;
                        builder.set(key, new MeteringRectangle[]{new MeteringRectangle(iMax - i4, iMax2 - i4, i4 * 2, i4 * 2, 999)});
                        this.t.set(CaptureRequest.CONTROL_AF_MODE, 1);
                        this.t.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
                        this.t.setTag("Focus");
                        this.u.capture(this.t.build(), this.A, this.g);
                        return;
                    }
                }
            }
            this.t.set(CaptureRequest.CONTROL_AF_MODE, 4);
            this.t.setTag("Regular");
            CameraCaptureSession cameraCaptureSession = this.u;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.setRepeatingRequest(this.t.build(), this.A, this.g);
            }
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
        }
    }

    private void k() {
        try {
            CameraCaptureSession cameraCaptureSession = this.u;
            if (cameraCaptureSession != null) {
                cameraCaptureSession.stopRepeating();
                this.t.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                this.t.set(CaptureRequest.CONTROL_AF_MODE, 0);
                this.t.setTag("Cancel focus");
                this.u.capture(this.t.build(), this.A, this.g);
            }
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
        }
    }

    public final Rect a() {
        return this.h;
    }

    public final boolean a(float f, float f2) {
        if (this.k <= 0) {
            return false;
        }
        if (this.p) {
            d.Log(5, "Camera2: Setting manual focus point already started.");
            return false;
        }
        this.l = f;
        this.m = f2;
        synchronized (this.v) {
            if (this.u != null && this.z != a.b) {
                k();
            }
        }
        return true;
    }

    public final boolean a(Context context, int i, int i2, int i3, int i4, int i5) {
        try {
            CameraCharacteristics cameraCharacteristics = b.getCameraCharacteristics(c(context)[i]);
            if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                d.Log(5, "Camera2: only LEGACY hardware level is supported.");
                return false;
            }
            Size[] sizeArrA = a(cameraCharacteristics);
            if (sizeArrA != null && sizeArrA.length != 0) {
                this.h = a(sizeArrA, i2, i3);
                Range[] rangeArr = (Range[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                if (rangeArr != null && rangeArr.length != 0) {
                    int iA = a(rangeArr, i4);
                    this.q = new Range(Integer.valueOf(iA), Integer.valueOf(iA));
                    try {
                        if (!e.tryAcquire(4L, TimeUnit.SECONDS)) {
                            d.Log(5, "Camera2: Timeout waiting to lock camera for opening.");
                            return false;
                        }
                        try {
                            b.openCamera(c(context)[i], this.B, this.g);
                            try {
                            } catch (InterruptedException e2) {
                                d.Log(6, "Camera2: Interrupted while waiting to open camera " + e2);
                            }
                            if (!e.tryAcquire(4L, TimeUnit.SECONDS)) {
                                d.Log(5, "Camera2: Timeout waiting to open camera.");
                                return false;
                            }
                            e.release();
                            this.w = i5;
                            b(cameraCharacteristics);
                            return this.d != null;
                        } catch (CameraAccessException e3) {
                            d.Log(6, "Camera2: CameraAccessException " + e3);
                            e.release();
                            return false;
                        }
                    } catch (InterruptedException e4) {
                        d.Log(6, "Camera2: Interrupted while trying to lock camera for opening " + e4);
                        return false;
                    }
                }
                d.Log(6, "Camera2: target FPS ranges are not avialable.");
            }
            return false;
        } catch (CameraAccessException e5) {
            d.Log(6, "Camera2: CameraAccessException " + e5);
            return false;
        }
    }

    public final void b() {
        if (this.d != null) {
            e();
            i();
            this.A = null;
            this.y = null;
            this.x = null;
            Image image = this.s;
            if (image != null) {
                image.close();
                this.s = null;
            }
            ImageReader imageReader = this.r;
            if (imageReader != null) {
                imageReader.close();
                this.r = null;
            }
        }
        h();
    }

    public final void c() {
        if (this.r == null) {
            ImageReader imageReaderNewInstance = ImageReader.newInstance(this.h.width(), this.h.height(), 35, 2);
            this.r = imageReaderNewInstance;
            imageReaderNewInstance.setOnImageAvailableListener(this.C, this.g);
            this.s = null;
            if (this.w != 0) {
                SurfaceTexture surfaceTexture = new SurfaceTexture(this.w);
                this.x = surfaceTexture;
                surfaceTexture.setDefaultBufferSize(this.h.width(), this.h.height());
                this.x.setOnFrameAvailableListener(this.D, this.g);
                this.y = new Surface(this.x);
            }
        }
        try {
            if (this.u == null) {
                CameraDevice cameraDevice = this.d;
                Surface surface = this.y;
                cameraDevice.createCaptureSession(surface != null ? Arrays.asList(surface, this.r.getSurface()) : Arrays.asList(this.r.getSurface()), new CameraCaptureSession.StateCallback() { // from class: com.unity3d.player.b.2
                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                    public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                        d.Log(6, "Camera2: CaptureSession configuration failed.");
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                    public final void onConfigured(CameraCaptureSession cameraCaptureSession) {
                        if (b.this.d == null) {
                            return;
                        }
                        synchronized (b.this.v) {
                            b.this.u = cameraCaptureSession;
                            try {
                                b bVar = b.this;
                                bVar.t = bVar.d.createCaptureRequest(1);
                                if (b.this.y != null) {
                                    b.this.t.addTarget(b.this.y);
                                }
                                b.this.t.addTarget(b.this.r.getSurface());
                                b.this.t.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, b.this.q);
                                b.this.j();
                            } catch (CameraAccessException e2) {
                                d.Log(6, "Camera2: CameraAccessException " + e2);
                            }
                        }
                    }
                }, this.g);
            } else if (this.z == a.b) {
                this.u.setRepeatingRequest(this.t.build(), this.A, this.g);
            }
            this.z = a.a;
        } catch (CameraAccessException e2) {
            d.Log(6, "Camera2: CameraAccessException " + e2);
        }
    }

    public final void d() {
        synchronized (this.v) {
            CameraCaptureSession cameraCaptureSession = this.u;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.stopRepeating();
                    this.z = a.b;
                } catch (CameraAccessException e2) {
                    d.Log(6, "Camera2: CameraAccessException " + e2);
                }
            }
        }
    }

    public final void e() {
        synchronized (this.v) {
            CameraCaptureSession cameraCaptureSession = this.u;
            if (cameraCaptureSession != null) {
                try {
                    cameraCaptureSession.abortCaptures();
                } catch (CameraAccessException e2) {
                    d.Log(6, "Camera2: CameraAccessException " + e2);
                }
                this.u.close();
                this.u = null;
                this.z = a.c;
            }
        }
    }
}
