package com.tds.common.tracker.model;

import android.text.TextUtils;
import com.tds.common.tracker.exceptions.ModelConvertException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PageModel implements BaseTrackModel {
    private static final String PARAM_PAGE_ACTION = "page_action";
    private static final String PARAM_PAGE_ID = "page_id";
    private static final String PARAM_PAGE_NAME = "page_name";
    private String pageAction;
    private String pageId;
    private String pageName;

    public PageModel withPageId(String str) {
        this.pageId = str;
        return this;
    }

    public PageModel withPageName(String str) {
        this.pageName = str;
        return this;
    }

    public PageModel withPageAction(String str) {
        this.pageAction = str;
        return this;
    }

    @Override // com.tds.common.tracker.model.BaseTrackModel
    public Map<String, String> convert() throws ModelConvertException {
        if (TextUtils.isEmpty(this.pageId)) {
            throw new ModelConvertException("page model param pageId empty");
        }
        if (TextUtils.isEmpty(this.pageName)) {
            throw new ModelConvertException("page model param pageId empty");
        }
        HashMap map = new HashMap();
        map.put(PARAM_PAGE_ID, this.pageId);
        map.put(PARAM_PAGE_NAME, this.pageName);
        if (!TextUtils.isEmpty(this.pageAction)) {
            map.put(PARAM_PAGE_ACTION, this.pageAction);
        }
        return map;
    }
}
