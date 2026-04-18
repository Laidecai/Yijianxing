package com.taptap.services.update.bean;

import android.text.TextUtils;
import com.alipay.sdk.app.statistic.b;
import com.taptap.services.update.TapUpdateTracker;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateInfo {
    public String appId;
    public String clientUpdateUri;
    public String developerName;
    public TapClientDownloadInfo downloadInfo;
    public List<TapUpdateFuncLink> funcLinks;
    public boolean notify;
    public String webUpdateUrl;

    public TapUpdateInfo(TapUpdateInfoTemplate tapUpdateInfoTemplate) {
        this.notify = true;
        if (tapUpdateInfoTemplate != null) {
            updateSessionId(tapUpdateInfoTemplate);
            this.appId = tapUpdateInfoTemplate.appId;
            this.developerName = tapUpdateInfoTemplate.developerName;
            this.funcLinks = tapUpdateInfoTemplate.funcLinks;
            this.notify = tapUpdateInfoTemplate.notify;
        }
    }

    public String getAppInfo() {
        if (this.downloadInfo != null) {
            return this.developerName + " 版本号：" + this.downloadInfo.versionName;
        }
        return this.developerName;
    }

    private void updateSessionId(TapUpdateInfoTemplate tapUpdateInfoTemplate) {
        String sessionId = TapUpdateTracker.getInstance().getSessionId();
        if (tapUpdateInfoTemplate.webUpdateUrlTpl != null) {
            this.webUpdateUrl = tapUpdateInfoTemplate.webUpdateUrlTpl.replace("{SID}", sessionId);
        }
        if (tapUpdateInfoTemplate.clientUpdateUriTpl != null) {
            this.clientUpdateUri = tapUpdateInfoTemplate.clientUpdateUriTpl.replace("{SID}", sessionId);
        }
        if (tapUpdateInfoTemplate.downloadInfoTpl == null || tapUpdateInfoTemplate.downloadInfoTpl.url == null) {
            return;
        }
        this.downloadInfo = new TapClientDownloadInfo(tapUpdateInfoTemplate.downloadInfoTpl.url.replace("{SID}", sessionId), tapUpdateInfoTemplate.downloadInfoTpl.versionName);
    }

    public boolean isDataValid() {
        TapClientDownloadInfo tapClientDownloadInfo;
        return (TextUtils.isEmpty(this.clientUpdateUri) || TextUtils.isEmpty(this.webUpdateUrl) || (tapClientDownloadInfo = this.downloadInfo) == null || !tapClientDownloadInfo.isDataValid()) ? false : true;
    }

    public static class TapClientDownloadInfo {
        public String url;
        public String versionName;

        public TapClientDownloadInfo(String str, String str2) {
            this.url = str;
            this.versionName = str2;
        }

        public boolean isDataValid() {
            return (TextUtils.isEmpty(this.url) || TextUtils.isEmpty(this.versionName)) ? false : true;
        }
    }

    public static class TapUpdateInfoTemplate {
        public String appId;
        public String clientUpdateUriTpl;
        public String developerName;
        public TapClientDownloadInfo downloadInfoTpl;
        public List<TapUpdateFuncLink> funcLinks;
        public boolean notify;
        public String webUpdateUrlTpl;

        public TapUpdateInfoTemplate(JSONObject jSONObject) {
            this.notify = true;
            if (jSONObject != null) {
                this.appId = jSONObject.optString(b.J0);
                this.clientUpdateUriTpl = jSONObject.optString("update_uri_tpl");
                this.webUpdateUrlTpl = jSONObject.optString("update_url_tpl");
                this.developerName = jSONObject.optString("developer_name");
                this.notify = jSONObject.optBoolean("notify", true);
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("links");
                this.funcLinks = new ArrayList();
                if (jSONArrayOptJSONArray != null) {
                    for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                        JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                        if (jSONObjectOptJSONObject != null) {
                            this.funcLinks.add(new TapUpdateFuncLink(jSONObjectOptJSONObject.optString("text"), jSONObjectOptJSONObject.optString(BreakpointSQLiteKey.URL)));
                        }
                    }
                }
                JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("download");
                if (jSONObjectOptJSONObject2 != null) {
                    this.downloadInfoTpl = new TapClientDownloadInfo(jSONObjectOptJSONObject2.optString("url_tpl"), jSONObjectOptJSONObject2.optString("version_name"));
                }
            }
        }

        public boolean isDataValid() {
            TapClientDownloadInfo tapClientDownloadInfo;
            return (TextUtils.isEmpty(this.clientUpdateUriTpl) || TextUtils.isEmpty(this.webUpdateUrlTpl) || (tapClientDownloadInfo = this.downloadInfoTpl) == null || !tapClientDownloadInfo.isDataValid()) ? false : true;
        }
    }
}
