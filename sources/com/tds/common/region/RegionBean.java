package com.tds.common.region;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class RegionBean {
    public int regionCode;

    public RegionBean() {
        this.regionCode = -1;
    }

    public RegionBean(JSONObject jSONObject) {
        this.regionCode = -1;
        if (jSONObject != null) {
            this.regionCode = jSONObject.optInt("region", -1);
        }
    }
}
