package com.tds.common.entities;

/* JADX INFO: loaded from: classes.dex */
public class TapAntiAddictionConfig {
    private boolean showSwitchAccount;
    private boolean useAgeRange;

    public TapAntiAddictionConfig(boolean z) {
        this.useAgeRange = true;
        this.showSwitchAccount = z;
    }

    public TapAntiAddictionConfig(boolean z, boolean z2) {
        this.useAgeRange = true;
        this.showSwitchAccount = z;
        this.useAgeRange = z2;
    }

    public TapAntiAddictionConfig() {
        this.useAgeRange = true;
        this.showSwitchAccount = false;
    }

    public boolean isShowSwitchAccount() {
        return this.showSwitchAccount;
    }

    public boolean isUseAgeRange() {
        return this.useAgeRange;
    }

    public void append(TapAntiAddictionConfig tapAntiAddictionConfig) {
        if (tapAntiAddictionConfig == null) {
            return;
        }
        this.showSwitchAccount = tapAntiAddictionConfig.showSwitchAccount;
        this.useAgeRange = tapAntiAddictionConfig.useAgeRange;
    }
}
