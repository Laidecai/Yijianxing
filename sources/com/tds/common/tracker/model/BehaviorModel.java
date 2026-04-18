package com.tds.common.tracker.model;

import android.text.TextUtils;
import com.taptap.sdk.TapLoginHelperActivity;
import com.tds.common.tracker.constants.CommonParam;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BehaviorModel implements BaseTrackModel {
    String action;
    String event;
    String eventExtra;
    String openId;
    int orientation;
    String project;
    public String source;
    String status;

    public BehaviorModel(String str, String str2, String str3, String str4, String str5, int i) {
        this.orientation = 1;
        this.project = str;
        this.action = str2;
        this.source = str3;
        this.event = str4;
        this.status = str5;
        this.orientation = i;
    }

    public BehaviorModel(String str, String str2, String str3, String str4, String str5, int i, String str6) {
        this(str, str2, str3, str4, str5, i);
        this.eventExtra = str6;
    }

    public void setOpenId(String str) {
        this.openId = str;
    }

    public void setEventExtra(String str) {
        this.eventExtra = str;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() throws Exception {
        HashMap map = new HashMap();
        map.put("project", this.project);
        map.put("action", this.action);
        map.put(TapLoginHelperActivity.INTENT_KEY_SOURCE, this.source);
        map.put("event", this.event);
        map.put("status", this.status);
        map.put(CommonParam.D_ORIENTATION, this.orientation + "");
        if (!TextUtils.isEmpty(this.eventExtra)) {
            map.put("event_attrs", this.eventExtra);
        }
        if (!TextUtils.isEmpty(this.openId)) {
            map.put(CommonParam.OPEN_ID, this.openId);
        }
        return map;
    }

    public String toString() {
        return "BehaviorModel{action='" + this.action + "', source='" + this.source + "', event='" + this.event + "', status='" + this.status + "', openId='" + this.openId + "', eventExtra='" + this.eventExtra + "'}";
    }
}
