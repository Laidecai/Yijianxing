package com.tds.common.oauth.utils;

import com.tds.common.oauth.RegionType;

/* JADX INFO: loaded from: classes.dex */
public class RegionUtil {
    public static RegionType getRegionType(int i) {
        if (1 == i) {
            return RegionType.IO;
        }
        return RegionType.CN;
    }
}
