package com.tds.common.websocket.extensions;

import com.alipay.sdk.util.g;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ExtensionRequestData {
    public static String EMPTY_VALUE = "";
    private String extensionName;
    private Map<String, String> extensionParameters = new LinkedHashMap();

    private ExtensionRequestData() {
    }

    public static ExtensionRequestData parseExtensionRequest(String str) {
        ExtensionRequestData extensionRequestData = new ExtensionRequestData();
        String[] strArrSplit = str.split(g.b);
        extensionRequestData.extensionName = strArrSplit[0].trim();
        for (int i = 1; i < strArrSplit.length; i++) {
            String[] strArrSplit2 = strArrSplit[i].split("=");
            String strTrim = EMPTY_VALUE;
            if (strArrSplit2.length > 1) {
                strTrim = strArrSplit2[1].trim();
                if ((strTrim.startsWith("\"") && strTrim.endsWith("\"")) || (strTrim.startsWith("'") && strTrim.endsWith("'") && strTrim.length() > 2)) {
                    strTrim = strTrim.substring(1, strTrim.length() - 1);
                }
            }
            extensionRequestData.extensionParameters.put(strArrSplit2[0].trim(), strTrim);
        }
        return extensionRequestData;
    }

    public String getExtensionName() {
        return this.extensionName;
    }

    public Map<String, String> getExtensionParameters() {
        return this.extensionParameters;
    }
}
