package com.tds.tapdb.sdk;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: classes.dex */
public class LoginType {
    private static final /* synthetic */ LoginType[] $VALUES;
    public static final LoginType Alipay;
    public static final LoginType Apple;
    public static final LoginType Custom;
    public static final LoginType Email;
    public static final LoginType Facebook;
    public static final LoginType Google;
    public static final LoginType NONE;
    public static final LoginType PhoneNumber;
    public static final LoginType QQ;
    public static final LoginType TapTap;
    public static final LoginType Tourist;
    public static final LoginType Twitter;
    public static final LoginType WeiXin;

    enum a extends LoginType {
        a(String str, int i) {
            super(str, i, null);
        }

        @Override // com.tds.tapdb.sdk.LoginType
        public String getDecoratedName() {
            return null;
        }
    }

    static {
        a aVar = new a("NONE", 0);
        NONE = aVar;
        LoginType loginType = new LoginType("TapTap", 1);
        TapTap = loginType;
        LoginType loginType2 = new LoginType("WeiXin", 2);
        WeiXin = loginType2;
        LoginType loginType3 = new LoginType("QQ", 3);
        QQ = loginType3;
        LoginType loginType4 = new LoginType("Tourist", 4);
        Tourist = loginType4;
        LoginType loginType5 = new LoginType("Apple", 5);
        Apple = loginType5;
        LoginType loginType6 = new LoginType("Alipay", 6) { // from class: com.tds.tapdb.sdk.LoginType.b
            {
                a aVar2 = null;
            }

            @Override // com.tds.tapdb.sdk.LoginType
            public String getDecoratedName() {
                return "ZhiFuBao";
            }
        };
        Alipay = loginType6;
        LoginType loginType7 = new LoginType("Facebook", 7);
        Facebook = loginType7;
        LoginType loginType8 = new LoginType("Google", 8);
        Google = loginType8;
        LoginType loginType9 = new LoginType("Twitter", 9);
        Twitter = loginType9;
        LoginType loginType10 = new LoginType("PhoneNumber", 10);
        PhoneNumber = loginType10;
        LoginType loginType11 = new LoginType("Email", 11);
        Email = loginType11;
        LoginType loginType12 = new LoginType("Custom", 12);
        Custom = loginType12;
        $VALUES = new LoginType[]{aVar, loginType, loginType2, loginType3, loginType4, loginType5, loginType6, loginType7, loginType8, loginType9, loginType10, loginType11, loginType12};
    }

    private LoginType(String str, int i) {
    }

    /* synthetic */ LoginType(String str, int i, a aVar) {
        this(str, i);
    }

    public static LoginType valueOf(String str) {
        return (LoginType) Enum.valueOf(LoginType.class, str);
    }

    public static LoginType[] values() {
        return (LoginType[]) $VALUES.clone();
    }

    public String getDecoratedName() {
        return name();
    }
}
