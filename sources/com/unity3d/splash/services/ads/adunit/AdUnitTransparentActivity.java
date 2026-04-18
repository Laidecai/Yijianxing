package com.unity3d.splash.services.ads.adunit;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.unity3d.splash.services.core.misc.ViewUtilities;

/* JADX INFO: loaded from: classes.dex */
public class AdUnitTransparentActivity extends AdUnitActivity {
    @Override // com.unity3d.splash.services.ads.adunit.AdUnitActivity
    protected void createLayout() {
        super.createLayout();
        ViewUtilities.setBackground(this._layout, new ColorDrawable(0));
    }

    @Override // com.unity3d.splash.services.ads.adunit.AdUnitActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ViewUtilities.setBackground(this._layout, new ColorDrawable(0));
    }
}
