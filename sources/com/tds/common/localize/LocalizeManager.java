package com.tds.common.localize;

import android.content.Context;
import android.text.TextUtils;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.tds.common.constants.Constants;
import com.tds.common.localize.model.PreferredLanguageChangedAction;
import com.tds.common.reactor.subjects.PublishSubject;
import com.tds.common.utils.DeviceUtils;
import com.tds.common.utils.FileUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LocalizeManager {
    private static boolean isDomestic;
    public static final Map<String, TapLanguage> supportedLangMap;
    public static final Set<String> supportedLangSet;
    public static final Map<TapLanguage, String> supportedLangStringMap;
    public static final Set<TapLanguage> supportedLangTypeSet;
    private static final Map<String, LocalizeStore> localizeCache = new HashMap();
    private static TapLanguage gameSelectedLang = TapLanguage.AUTO;
    private static final PublishSubject<PreferredLanguageChangedAction> preferredLanguageChangedActionPublishSubject = PublishSubject.create();

    static {
        HashSet hashSet = new HashSet();
        supportedLangSet = hashSet;
        HashSet hashSet2 = new HashSet();
        supportedLangTypeSet = hashSet2;
        HashMap map = new HashMap();
        supportedLangMap = map;
        HashMap map2 = new HashMap();
        supportedLangStringMap = map2;
        hashSet.addAll(Arrays.asList("zh_hans", "zh_hant", "en", "ja", "ko", "th", BreakpointSQLiteKey.ID, Constants.Language.DE, "es", Constants.Language.FR, "pt", Constants.Language.RU, Constants.Language.TR, "vi"));
        hashSet2.addAll(Arrays.asList(TapLanguage.AUTO, TapLanguage.ZH_HANS, TapLanguage.EN, TapLanguage.ZH_HANT, TapLanguage.JA, TapLanguage.KO, TapLanguage.TH, TapLanguage.ID, TapLanguage.DE, TapLanguage.ES, TapLanguage.FR, TapLanguage.PT, TapLanguage.RU, TapLanguage.TR, TapLanguage.VI));
        map.put("zh_hans", TapLanguage.ZH_HANS);
        map.put("en", TapLanguage.EN);
        map.put("zh_hant", TapLanguage.ZH_HANT);
        map.put("ja", TapLanguage.JA);
        map.put("ko", TapLanguage.KO);
        map.put("th", TapLanguage.TH);
        map.put(BreakpointSQLiteKey.ID, TapLanguage.ID);
        map.put(Constants.Language.DE, TapLanguage.DE);
        map.put("es", TapLanguage.ES);
        map.put(Constants.Language.FR, TapLanguage.FR);
        map.put("pt", TapLanguage.PT);
        map.put(Constants.Language.RU, TapLanguage.RU);
        map.put(Constants.Language.TR, TapLanguage.TR);
        map.put("vi", TapLanguage.VI);
        map2.put(TapLanguage.ZH_HANS, Constants.Language.CN);
        map2.put(TapLanguage.EN, Constants.Language.US);
        map2.put(TapLanguage.ZH_HANT, Constants.Language.TW);
        map2.put(TapLanguage.JA, Constants.Language.JA);
        map2.put(TapLanguage.KO, Constants.Language.KO);
        map2.put(TapLanguage.TH, Constants.Language.TH);
        map2.put(TapLanguage.ID, Constants.Language.ID);
        map2.put(TapLanguage.DE, "de_DE");
        map2.put(TapLanguage.ES, Constants.Language.ES);
        map2.put(TapLanguage.FR, "fr_FR");
        map2.put(TapLanguage.PT, Constants.Language.PT);
        map2.put(TapLanguage.RU, "ru_RU");
        map2.put(TapLanguage.TR, "tr_TR");
        map2.put(TapLanguage.VI, "vi_VN");
        isDomestic = true;
    }

    public static boolean isLangSupport(String str) {
        return supportedLangSet.contains(str);
    }

    public static PublishSubject<PreferredLanguageChangedAction> getLanguageChangedObservable() {
        return preferredLanguageChangedActionPublishSubject;
    }

    @Deprecated
    public static void changeGameSelectedLanguage(int i) {
        changeGameSelectedLanguage(TapLanguage.fromInt(i));
    }

    public static void changeGameSelectedLanguage(TapLanguage tapLanguage) {
        if (supportedLangTypeSet.contains(tapLanguage)) {
            gameSelectedLang = tapLanguage;
            preferredLanguageChangedActionPublishSubject.onNext(new PreferredLanguageChangedAction(tapLanguage));
        }
    }

    public static LocalizeStore getLocalizeStore(String str) {
        LocalizeStore localizeStore = localizeCache.get(str);
        if (localizeStore == null) {
            throw new IllegalStateException("please config localizestore(" + str + ") first");
        }
        localizeStore.setPreferredLang(getPreferredLang());
        return localizeStore;
    }

    public static void configSDKLocalizeWith(int i) {
        isDomestic = i == 0;
    }

    public static void configSDKLocalize(String str, JSONObject jSONObject, boolean z) {
        if (jSONObject == null) {
            return;
        }
        localizeCache.put(str, new LocalizeStore(jSONObject, z));
        isDomestic = z;
    }

    public static void configSDKLocalize(String str, Context context, String str2, boolean z) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(FileUtils.loadAssetTextAsString(context, str2));
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject = null;
        }
        configSDKLocalize(str, jSONObject, z);
    }

    public static TapLanguage getPreferredLang() {
        if (gameSelectedLang != TapLanguage.AUTO) {
            return gameSelectedLang;
        }
        String curLanguageDisplayName = DeviceUtils.getCurLanguageDisplayName();
        if (!TextUtils.isEmpty(curLanguageDisplayName)) {
            if (curLanguageDisplayName.startsWith("zh")) {
                String lowerCase = curLanguageDisplayName.toLowerCase(Locale.US);
                if (lowerCase.contains("hant")) {
                    return TapLanguage.ZH_HANT;
                }
                if (lowerCase.contains("hans")) {
                    return TapLanguage.ZH_HANS;
                }
                if (!lowerCase.contains("cn")) {
                    return TapLanguage.ZH_HANT;
                }
                return TapLanguage.ZH_HANS;
            }
            if (curLanguageDisplayName.startsWith("en")) {
                return TapLanguage.EN;
            }
            if (curLanguageDisplayName.startsWith("ja")) {
                return TapLanguage.JA;
            }
            if (curLanguageDisplayName.startsWith("ko")) {
                return TapLanguage.KO;
            }
            if (curLanguageDisplayName.startsWith("th")) {
                return TapLanguage.TH;
            }
            if (curLanguageDisplayName.startsWith(BreakpointSQLiteKey.ID) || curLanguageDisplayName.startsWith("in")) {
                return TapLanguage.ID;
            }
            if (curLanguageDisplayName.startsWith(Constants.Language.DE)) {
                return TapLanguage.DE;
            }
            if (curLanguageDisplayName.startsWith("es")) {
                return TapLanguage.ES;
            }
            if (curLanguageDisplayName.startsWith(Constants.Language.FR)) {
                return TapLanguage.FR;
            }
            if (curLanguageDisplayName.startsWith("pt")) {
                return TapLanguage.PT;
            }
            if (curLanguageDisplayName.startsWith(Constants.Language.RU)) {
                return TapLanguage.RU;
            }
            if (curLanguageDisplayName.startsWith(Constants.Language.TR)) {
                return TapLanguage.TR;
            }
            if (curLanguageDisplayName.startsWith("vi")) {
                return TapLanguage.VI;
            }
        }
        return isDomestic ? TapLanguage.ZH_HANS : TapLanguage.EN;
    }

    public static TapLanguage getGameSelectedLang() {
        return gameSelectedLang;
    }

    /* JADX INFO: renamed from: com.tds.common.localize.LocalizeManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$tds$common$localize$TapLanguage;

        static {
            int[] iArr = new int[TapLanguage.values().length];
            $SwitchMap$com$tds$common$localize$TapLanguage = iArr;
            try {
                iArr[TapLanguage.ZH_HANS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.EN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.ZH_HANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.JA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.KO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.TH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.ID.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.DE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.ES.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.FR.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.PT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.RU.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.TR.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$tds$common$localize$TapLanguage[TapLanguage.VI.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public static Locale getLocale(TapLanguage tapLanguage) {
        switch (AnonymousClass1.$SwitchMap$com$tds$common$localize$TapLanguage[tapLanguage.ordinal()]) {
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.US;
            case 3:
                return Locale.TRADITIONAL_CHINESE;
            case 4:
                return Locale.JAPAN;
            case 5:
                return Locale.KOREA;
            case 6:
                return new Locale("th");
            case 7:
                return new Locale("in");
            case 8:
                return Locale.GERMANY;
            case 9:
                return new Locale("es");
            case 10:
                return Locale.FRANCE;
            case 11:
                return new Locale("pt");
            case 12:
                return new Locale(Constants.Language.RU);
            case 13:
                return new Locale(Constants.Language.TR);
            case 14:
                return new Locale("vi");
            default:
                return getDefaultLocaleByDomestic();
        }
    }

    public static Locale getDefaultLocaleByDomestic() {
        return isDomestic ? Locale.CHINA : Locale.US;
    }

    public static String getPreferredLanguageString() {
        return supportedLangStringMap.get(getPreferredLang());
    }
}
