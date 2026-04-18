package com.alipay.sdk.app.statistic;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.util.c;
import com.alipay.sdk.util.l;
import com.taptap.sdk.constant.LoginConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static final String A = "H5PayNetworkError";
    public static final String A0 = "TbCancel";
    public static final String B = "H5AuthNetworkError";
    public static final String B0 = "TbUnknown";
    public static final String C = "SSLError";
    public static final String C0 = "TbOk";
    public static final String D = "SSLProceed";
    public static final String D0 = "TbActFail";
    public static final String E = "SSLDenied";
    public static final String E0 = "tid_context_null";
    public static final String F = "H5PayDataAnalysisError";
    public static final String F0 = "partner";
    public static final String G = "H5AuthDataAnalysisError";
    public static final String G0 = "out_trade_no";
    public static final String H = "PublicKeyUnmatch";
    public static final String H0 = "trade_no";
    public static final String I = "ClientBindFailed";
    public static final String I0 = "biz_content";
    public static final String J = "TriDesEncryptError";
    public static final String J0 = "app_id";
    public static final String K = "TriDesDecryptError";
    public static final String L = "ClientBindException";
    public static final String M = "SaveTradeTokenError";
    public static final String N = "ClientBindServiceFailed";
    public static final String O = "TryStartServiceEx";
    public static final String P = "BindWaitTimeoutEx";
    public static final String Q = "CheckClientExistEx";
    public static final String R = "CheckClientSignEx";
    public static final String S = "GetInstalledAppEx";
    public static final String T = "ParserTidClientKeyEx";
    public static final String U = "PgApiInvoke";
    public static final String V = "PgBindStarting";
    public static final String W = "PgBinded";
    public static final String X = "PgBindEnd";
    public static final String Y = "PgBindPay";
    public static final String Z = "PgReturn";
    public static final String a0 = "PgReturnV";
    public static final String b0 = "PgWltVer";
    public static final String c0 = "PgOpenStarting";
    public static final String d0 = "ErrIntentEx";
    public static final String e0 = "ErrActNull";
    public static final String f0 = "ErrActNull";
    public static final String g0 = "GetInstalledAppEx";
    public static final String h0 = "StartLaunchAppTransEx";
    public static final String i0 = "CheckLaunchAppExistEx";
    public static final String j0 = "LogCurrentAppLaunchSwitch";
    public static final String k = "net";
    public static final String k0 = "LogCurrentQueryTime";
    public static final String l = "biz";
    public static final String l0 = "LogCalledPackage";
    public static final String m = "cp";
    public static final String m0 = "LogBindCalledH5";
    public static final String n = "auth";
    public static final String n0 = "LogCalledH5";
    public static final String o = "third";
    public static final String o0 = "LogHkLoginByIntent";
    public static final String p = "tid";
    public static final String p0 = "SchemePayWrongHashEx";
    public static final String q = "wlt";
    public static final String q0 = "LogAppLaunchSwitchEnabled";
    public static final String r = "FormatResultEx";
    public static final String r0 = "H5CbUrlEmpty";
    public static final String s = "GetApdidEx";
    public static final String s0 = "H5CbEx";
    public static final String t = "GetApdidNull";
    public static final String t0 = "BuildSchemePayUriError";
    public static final String u = "GetApdidTimeout";
    public static final String u0 = "StartActivityEx";
    public static final String v = "GetUtdidEx";
    public static final String v0 = "JSONEx";
    public static final String w = "GetPackageInfoEx";
    public static final String w0 = "ParseBundleSerializableError";
    public static final String x = "NotIncludeSignatures";
    public static final String x0 = "ParseSchemeQueryError";
    public static final String y = "GetInstalledPackagesEx";
    public static final String y0 = "TbChk";
    public static final String z = "GetPublicKeyFromSignEx";
    public static final String z0 = "TbStart";
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h = "";
    public String i = "";
    public String j;

    public b(Context context, boolean z2) {
        context = context != null ? context.getApplicationContext() : context;
        this.a = b();
        this.c = a(context);
        this.d = a(z2 ? 0L : a.c.a(context));
        this.e = a();
        this.f = b(context);
        this.g = "-";
        this.j = "-";
    }

    private synchronized void c(String str, String str2, String str3) {
        c.d(com.alipay.sdk.cons.a.x, String.format("event %s %s %s", str, str2, str3));
        String str4 = TextUtils.isEmpty(this.h) ? "" : "^";
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        Object[] objArr = new Object[4];
        objArr[0] = TextUtils.isEmpty(str) ? "-" : c(str);
        objArr[1] = c(str2);
        objArr[2] = c(str3);
        objArr[3] = c(c());
        sb.append(String.format("%s,%s,%s,-,-,-,-,-,-,-,-,-,-,%s", objArr));
        this.h += sb.toString();
    }

    private boolean d() {
        return TextUtils.isEmpty(this.i);
    }

    public static String e() {
        try {
            return UUID.randomUUID().toString();
        } catch (Throwable unused) {
            return "12345678uuid";
        }
    }

    public void a(String str, String str2, Throwable th) {
        d(str, str2, a(th));
    }

    public void b(String str, String str2, String str3) {
        d(str, str2, str3);
    }

    public static String b() {
        return String.format("%s,%s", e(), new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()));
    }

    private synchronized void d(String str, String str2, String str3) {
        c.c(com.alipay.sdk.cons.a.x, String.format("err %s %s %s", str, str2, str3));
        String str4 = TextUtils.isEmpty(this.i) ? "" : "^";
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        Object[] objArr = new Object[4];
        objArr[0] = str;
        objArr[1] = str2;
        objArr[2] = TextUtils.isEmpty(str3) ? "-" : c(str3);
        objArr[3] = c(c());
        sb.append(String.format("%s,%s,%s,%s", objArr));
        this.i += sb.toString();
    }

    public void a(String str, String str2, Throwable th, String str3) {
        d(str, str2, str3 + ": " + a(th));
    }

    public static String b(String str) {
        String string;
        String strReplace;
        if (str == null) {
            str = "";
        }
        String[] strArrSplit = str.split(com.alipay.sdk.sys.a.k);
        String strReplace2 = null;
        if (strArrSplit != null) {
            string = null;
            strReplace = null;
            for (String str2 : strArrSplit) {
                String[] strArrSplit2 = str2.split("=");
                if (strArrSplit2 != null && strArrSplit2.length == 2) {
                    if (strArrSplit2[0].equalsIgnoreCase(F0)) {
                        strReplace2 = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(G0)) {
                        string = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(H0)) {
                        strReplace = strArrSplit2[1].replace("\"", "");
                    } else if (strArrSplit2[0].equalsIgnoreCase(I0)) {
                        try {
                            JSONObject jSONObject = new JSONObject(l.e(com.alipay.sdk.sys.a.e(), strArrSplit2[1]));
                            if (TextUtils.isEmpty(string)) {
                                string = jSONObject.getString(G0);
                            }
                        } catch (Throwable unused) {
                        }
                    } else if (strArrSplit2[0].equalsIgnoreCase(J0) && TextUtils.isEmpty(strReplace2)) {
                        strReplace2 = strArrSplit2[1];
                    }
                }
            }
        } else {
            string = null;
            strReplace = null;
        }
        return String.format("%s,%s,-,%s,-,-,-", c(strReplace), c(string), c(strReplace2));
    }

    public void a(String str, String str2, String str3) {
        c("", str, str2 + "|" + str3);
    }

    public void a(String str, String str2) {
        c("", str, str2);
    }

    public static String a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(th.getClass().getName());
            stringBuffer.append(":");
            stringBuffer.append(th.getMessage());
            stringBuffer.append(" 》 ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                int i = 0;
                for (StackTraceElement stackTraceElement : stackTrace) {
                    stringBuffer.append(stackTraceElement.toString());
                    stringBuffer.append(" 》 ");
                    i++;
                    if (i > 5) {
                        break;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return stringBuffer.toString();
    }

    public static String d(String str) {
        return TextUtils.isEmpty(str) ? "-" : str;
    }

    public static String c() {
        return new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault()).format(new Date());
    }

    public static String c(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replace("[", "【").replace("]", "】").replace("(", "（").replace(")", "）").replace(",", "，").replace("^", "~").replace("#", "＃");
    }

    public String a(String str) {
        String strB = b(str);
        this.b = strB;
        return String.format("[(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s),(%s)]", this.a, strB, this.c, this.d, this.e, this.f, this.g, d(this.h), d(this.i), this.j);
    }

    public static String a(Context context) {
        String packageName;
        String str = "-";
        if (context != null) {
            try {
                Context applicationContext = context.getApplicationContext();
                packageName = applicationContext.getPackageName();
                try {
                    PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(packageName, 64);
                    str = packageInfo.versionName + "|" + a(packageInfo);
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
                packageName = "-";
            }
        } else {
            packageName = "-";
        }
        return String.format("%s,%s,-,-,-", c(packageName), c(str));
    }

    public static String b(Context context) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,-", c(com.alipay.sdk.util.a.c(context)), "android", c(Build.VERSION.RELEASE), c(Build.MODEL), "-", c(com.alipay.sdk.util.a.b(context).c()), c(com.alipay.sdk.util.a.d(context).b()), "gw", c(com.alipay.sdk.util.a.b(context).b()));
    }

    public static String a(PackageInfo packageInfo) {
        Signature[] signatureArr;
        String strSubstring;
        String strA;
        if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length == 0) {
            return LoginConstants.LOGIN_VERSION_RETURN_TOKEN_0;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(packageInfo.signatures.length);
            for (Signature signature : packageInfo.signatures) {
                try {
                    strA = l.a((com.alipay.sdk.sys.a) null, signature.toByteArray());
                } catch (Throwable unused) {
                }
                if (TextUtils.isEmpty(strA)) {
                    strSubstring = "?";
                    sb.append("-");
                    sb.append(strSubstring);
                } else {
                    strSubstring = l.e(strA).substring(0, 8);
                    sb.append("-");
                    sb.append(strSubstring);
                }
            }
            return sb.toString();
        } catch (Throwable unused2) {
            return "?";
        }
    }

    public static String a(long j) {
        return String.format("android,3,%s,%s,com.alipay.mcpay,5.0,-,%s,-", c("15.8.02"), c("h.a.3.8.02"), "~" + j);
    }

    public static String a() {
        return String.format("%s,%s,-,-,-", c(com.alipay.sdk.tid.a.a(com.alipay.sdk.sys.b.d().b()).d()), c(com.alipay.sdk.sys.b.d().c()));
    }
}
