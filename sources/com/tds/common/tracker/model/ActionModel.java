package com.tds.common.tracker.model;

import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ActionModel implements BaseTrackModel {
    public static final int ACTION_CLICK = 0;
    public static final int ACTION_COLLECT = 3;
    public static final int ACTION_COMMENT = 2;
    public static final int ACTION_IMPRESSION = 4;
    public static final int ACTION_LIKE = 1;
    public static final String PARAM_NAME_CLICK = "click";
    public static final String PARAM_NAME_COLLECT = "collect";
    public static final String PARAM_NAME_COMMENT = "comment";
    public static final String PARAM_NAME_IMPRESSION = "impression";
    public static final String PARAM_NAME_LIKE = "like";
    private String targetViewName = "";
    private int actionType = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public ActionModel clickWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 0;
        return this;
    }

    public ActionModel likeWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 1;
        return this;
    }

    public ActionModel commentWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 2;
        return this;
    }

    public ActionModel collectWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 3;
        return this;
    }

    public ActionModel impressionWithViewName(String str) {
        this.targetViewName = str;
        this.actionType = 4;
        return this;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() {
        HashMap map = new HashMap();
        int i = this.actionType;
        String str = i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : PARAM_NAME_IMPRESSION : PARAM_NAME_COLLECT : PARAM_NAME_COMMENT : PARAM_NAME_LIKE : PARAM_NAME_CLICK;
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(this.targetViewName)) {
            map.put(str, this.targetViewName);
        }
        return map;
    }
}
