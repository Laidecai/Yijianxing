package com.tds.common.notch;

import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.Window;
import android.view.WindowInsets;
import com.tds.common.notch.core.INotchSupport;
import com.tds.common.notch.helper.DeviceBrandTools;
import com.tds.common.notch.helper.NotchStatusBarUtils;
import com.tds.common.notch.phone.CommonScreen;
import com.tds.common.notch.phone.HuaWeiNotchScreen;
import com.tds.common.notch.phone.MiuiNotchScreen;
import com.tds.common.notch.phone.OppoNotchScreen;
import com.tds.common.notch.phone.PVersionNotchScreen;
import com.tds.common.notch.phone.VivoNotchScreen;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotchTools implements INotchSupport {
    private static final int CURRENT_SDK = Build.VERSION.SDK_INT;
    private static final int VERSION_P = 28;
    private static NotchTools sFullScreenTolls;
    private boolean mHasJudge;
    private boolean mIsNotchScreen;
    private INotchSupport notchScreenSupport = null;

    public static NotchTools getFullScreenTools() {
        if (sFullScreenTolls == null) {
            synchronized (NotchTools.class) {
                if (sFullScreenTolls == null) {
                    sFullScreenTolls = new NotchTools();
                }
            }
        }
        return sFullScreenTolls;
    }

    private NotchTools() {
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public boolean isNotchScreen(Window window) {
        if (!this.mHasJudge) {
            if (this.notchScreenSupport == null) {
                checkScreenSupportInit(window);
            }
            INotchSupport iNotchSupport = this.notchScreenSupport;
            if (iNotchSupport == null) {
                this.mHasJudge = true;
                this.mIsNotchScreen = false;
            } else {
                this.mIsNotchScreen = iNotchSupport.isNotchScreen(window);
            }
        }
        return this.mIsNotchScreen;
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchHeight(Window window) {
        if (this.notchScreenSupport == null) {
            checkScreenSupportInit(window);
        }
        INotchSupport iNotchSupport = this.notchScreenSupport;
        if (iNotchSupport == null) {
            return 0;
        }
        return iNotchSupport.getNotchHeight(window);
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getNotchWidth(Window window) {
        if (this.notchScreenSupport == null) {
            checkScreenSupportInit(window);
        }
        INotchSupport iNotchSupport = this.notchScreenSupport;
        if (iNotchSupport == null) {
            return 0;
        }
        return iNotchSupport.getNotchWidth(window);
    }

    @Override // com.tds.common.notch.core.INotchSupport
    public int getStatusHeight(Window window) {
        return NotchStatusBarUtils.getStatusBarHeight(window.getContext());
    }

    public List<Rect> getNotchRectList(Window window) {
        WindowInsets rootWindowInsets;
        DisplayCutout displayCutout;
        if (Build.VERSION.SDK_INT < 28 || window == null || window.getDecorView() == null || (rootWindowInsets = window.getDecorView().getRootWindowInsets()) == null || (displayCutout = rootWindowInsets.getDisplayCutout()) == null) {
            return null;
        }
        return displayCutout.getBoundingRects();
    }

    private void checkScreenSupportInit(Window window) {
        if (this.notchScreenSupport != null) {
            return;
        }
        int i = CURRENT_SDK;
        if (i < 26) {
            this.notchScreenSupport = new CommonScreen();
            return;
        }
        if (i <= 28) {
            DeviceBrandTools deviceBrandTools = DeviceBrandTools.getInstance();
            if (deviceBrandTools.isHuaWei()) {
                this.notchScreenSupport = new HuaWeiNotchScreen();
                return;
            }
            if (deviceBrandTools.isMiui()) {
                this.notchScreenSupport = new MiuiNotchScreen();
                return;
            }
            if (deviceBrandTools.isVivo()) {
                this.notchScreenSupport = new VivoNotchScreen();
                return;
            } else if (deviceBrandTools.isOppo()) {
                this.notchScreenSupport = new OppoNotchScreen();
                return;
            } else {
                this.notchScreenSupport = new CommonScreen();
                return;
            }
        }
        this.notchScreenSupport = new PVersionNotchScreen();
    }
}
