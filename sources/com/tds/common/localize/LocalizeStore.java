package com.tds.common.localize;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LocalizeStore {
    private boolean isDomestic;
    private final HashMap<TapLanguage, JSONObject> langDictSparseArray = new HashMap<>();
    private TapLanguage preferredLang = TapLanguage.AUTO;

    private LocalizeStore() {
    }

    void setPreferredLang(TapLanguage tapLanguage) {
        this.preferredLang = tapLanguage;
    }

    public LocalizeStore(JSONObject jSONObject, boolean z) {
        for (Map.Entry<String, TapLanguage> entry : LocalizeManager.supportedLangMap.entrySet()) {
            try {
                this.langDictSparseArray.put(entry.getValue(), jSONObject.getJSONObject(entry.getKey()));
            } catch (JSONException unused) {
                System.out.println("" + entry.getKey() + " multi-language Not config");
            }
        }
        this.isDomestic = z;
    }

    public String getStringValue(String str) {
        try {
            TapLanguage tapLanguage = this.preferredLang;
            if (this.langDictSparseArray.get(tapLanguage) == null) {
                tapLanguage = this.isDomestic ? TapLanguage.ZH_HANS : TapLanguage.EN;
            }
            JSONObject jSONObject = this.langDictSparseArray.get(tapLanguage);
            return jSONObject == null ? "" : jSONObject.getString(str);
        } catch (Exception unused) {
            return "";
        }
    }
}
