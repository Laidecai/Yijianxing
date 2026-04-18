package com.tds.common.tracker.model;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class RawDataModel implements BaseTrackModel {
    private Map<String, String> rawData;

    public RawDataModel(Map<String, String> map) {
        this.rawData = map;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() throws Exception {
        return this.rawData;
    }
}
