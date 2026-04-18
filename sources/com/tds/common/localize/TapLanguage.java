package com.tds.common.localize;

/* JADX INFO: loaded from: classes.dex */
public enum TapLanguage {
    AUTO,
    ZH_HANS,
    EN,
    ZH_HANT,
    JA,
    KO,
    TH,
    ID,
    DE,
    ES,
    FR,
    PT,
    RU,
    TR,
    VI;

    public static TapLanguage fromInt(int i) {
        for (TapLanguage tapLanguage : values()) {
            if (tapLanguage.ordinal() == i) {
                return tapLanguage;
            }
        }
        return AUTO;
    }
}
