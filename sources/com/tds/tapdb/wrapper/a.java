package com.tds.tapdb.wrapper;

import com.tds.tapdb.sdk.LoginType;

/* JADX INFO: loaded from: classes.dex */
final class a {
    private static final String a = "alipay";
    private static final String b = "zhifubao";
    private static final String c = "taptap";
    private static final String d = "weixin";
    private static final String e = "qq";
    private static final String f = "tourist";
    private static final String g = "apple";
    private static final String h = "facebook";
    private static final String i = "google";
    private static final String j = "twitter";
    private static final String k = "phonenumber";
    private static final String l = "email";
    private static final String m = "custom";
    private static final String n = "none";

    a() {
    }

    static LoginType a(String str) {
        if (str == null) {
            return LoginType.NONE;
        }
        String lowerCase = str.toLowerCase();
        lowerCase.hashCode();
        switch (lowerCase) {
            case "alipay":
            case "zhifubao":
                return LoginType.Alipay;
            case "google":
                return LoginType.Google;
            case "tourist":
                return LoginType.Tourist;
            case "twitter":
                return LoginType.Twitter;
            case "taptap":
                return LoginType.TapTap;
            case "weixin":
                return LoginType.WeiXin;
            case "phonenumber":
                return LoginType.PhoneNumber;
            case "qq":
                return LoginType.QQ;
            case "apple":
                return LoginType.Apple;
            case "email":
                return LoginType.Email;
            case "facebook":
                return LoginType.Facebook;
            default:
                return LoginType.Custom;
        }
    }
}
