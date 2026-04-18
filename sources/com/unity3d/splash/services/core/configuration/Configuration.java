package com.unity3d.splash.services.core.configuration;

import android.content.SharedPreferences;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.common.tracker.constants.CommonParam;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Configuration {
    private String[] _moduleConfigurationList = {"com.unity3d.splash.services.core.configuration.CoreModuleConfiguration", "com.unity3d.splash.services.ads.configuration.AdsModuleConfiguration"};
    private Map _moduleConfigurations;
    private String _url;
    private Class[] _webAppApiClassList;
    private String _webViewData;
    private String _webViewHash;
    private String _webViewUrl;
    private String _webViewVersion;

    public Configuration() {
    }

    public Configuration(String str) {
        this._url = str;
    }

    private void createWebAppApiClassList() {
        ArrayList arrayList = new ArrayList();
        for (String str : getModuleConfigurationList()) {
            IModuleConfiguration moduleConfiguration = getModuleConfiguration(str);
            if (moduleConfiguration != null && moduleConfiguration.getWebAppApiClassList() != null) {
                arrayList.addAll(Arrays.asList(moduleConfiguration.getWebAppApiClassList()));
            }
        }
        this._webAppApiClassList = (Class[]) arrayList.toArray(new Class[arrayList.size()]);
    }

    protected String buildQueryString() {
        return "?ts=" + System.currentTimeMillis() + "&sdkVersion=" + SdkProperties.getVersionCode() + "&sdkVersionName=" + SdkProperties.getVersionName();
    }

    public String getConfigUrl() {
        return this._url;
    }

    public IModuleConfiguration getModuleConfiguration(String str) {
        Map map = this._moduleConfigurations;
        if (map != null && map.containsKey(str)) {
            return (IModuleConfiguration) this._moduleConfigurations.get(str);
        }
        try {
            IModuleConfiguration iModuleConfiguration = (IModuleConfiguration) Class.forName(str).newInstance();
            if (iModuleConfiguration != null) {
                if (this._moduleConfigurations == null) {
                    HashMap map2 = new HashMap();
                    this._moduleConfigurations = map2;
                    map2.put(str, iModuleConfiguration);
                }
                return iModuleConfiguration;
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public String[] getModuleConfigurationList() {
        return this._moduleConfigurationList;
    }

    public Class[] getWebAppApiClassList() {
        if (this._webAppApiClassList == null) {
            createWebAppApiClassList();
        }
        return this._webAppApiClassList;
    }

    public String getWebViewData() {
        return this._webViewData;
    }

    public String getWebViewHash() {
        return this._webViewHash;
    }

    public String getWebViewUrl() {
        return this._webViewUrl;
    }

    public String getWebViewVersion() {
        return this._webViewVersion;
    }

    protected void makeRequest() throws MalformedURLException {
        SharedPreferences sharedPreferences = ClientProperties.getApplicationContext().getSharedPreferences("game_detail", 0);
        this._webViewUrl = sharedPreferences.getString(BreakpointSQLiteKey.URL, null);
        this._webViewHash = sharedPreferences.getString("hash", null);
        this._webViewVersion = sharedPreferences.getString(CommonParam.VERSION, null);
        String str = this._webViewUrl;
        if (str == null || str.isEmpty()) {
            throw new MalformedURLException("Invalid data. Web view URL is null or empty");
        }
    }

    public void setConfigUrl(String str) {
        this._url = str;
    }

    public void setWebViewData(String str) {
        this._webViewData = str;
    }

    public void setWebViewHash(String str) {
        this._webViewHash = str;
    }

    public void setWebViewUrl(String str) {
        this._webViewUrl = str;
    }
}
