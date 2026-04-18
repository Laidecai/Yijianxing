package com.taptap.sdk;

/* JADX INFO: loaded from: classes.dex */
public class LoginSdkConfig {
    public boolean isPortrait;
    public RegionType regionType;
    public boolean roundCorner;

    public LoginSdkConfig() {
        this.roundCorner = true;
        this.isPortrait = true;
        this.regionType = RegionType.CN;
    }

    public LoginSdkConfig(boolean z, boolean z2) {
        this.roundCorner = true;
        this.isPortrait = true;
        this.regionType = RegionType.CN;
        this.roundCorner = z;
        this.isPortrait = z2;
    }

    public LoginSdkConfig(boolean z, boolean z2, RegionType regionType) {
        this.roundCorner = true;
        this.isPortrait = true;
        this.regionType = RegionType.CN;
        this.roundCorner = z;
        this.isPortrait = z2;
        this.regionType = regionType;
    }
}
