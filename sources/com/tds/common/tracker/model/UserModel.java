package com.tds.common.tracker.model;

import com.tds.common.tracker.exceptions.ModelConvertException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class UserModel implements BaseTrackModel {
    public static final String PARAM_TAPTAP_OPEN_ID = "taptap_open_id";
    private static final String PARAM_TDS_USER_ID = "tds_user_id";
    private static final String PARAM_TDS_USER_NAME = "tds_user_name";
    public String tdsUserId = "";
    public String tdsUserName = "";
    public String taptapOpenId = "";

    public UserModel withTdsUserId(String str) {
        this.tdsUserId = str;
        return this;
    }

    public UserModel withTdsUserName(String str) {
        this.tdsUserName = str;
        return this;
    }

    public UserModel withTapTapOpenId(String str) {
        this.taptapOpenId = str;
        return this;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() throws ModelConvertException {
        HashMap map = new HashMap();
        map.put("tds_user_id", this.tdsUserId);
        map.put(PARAM_TDS_USER_NAME, this.tdsUserName);
        map.put(PARAM_TAPTAP_OPEN_ID, this.taptapOpenId);
        return map;
    }
}
