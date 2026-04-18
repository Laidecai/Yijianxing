package com.ta.utdid2.device;

import android.content.Context;
import android.os.Binder;
import android.provider.Settings;
import android.text.TextUtils;
import com.ta.utdid2.a.a.f;
import com.ta.utdid2.a.a.g;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class c {
    public static c a;
    public static final Object e = new Object();
    public static final String k = ".UTSystemConfig" + File.separator + "Global";

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public com.ta.utdid2.b.a.c f20a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public d f21a;
    public com.ta.utdid2.b.a.c b;
    public String i;
    public String j;
    public Context mContext;
    public String h = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    public Pattern f22b = Pattern.compile("[^0-9a-zA-Z=/+]+");

    public c(Context context) {
        this.mContext = null;
        this.f21a = null;
        this.i = "xx_utdid_key";
        this.j = "xx_utdid_domain";
        this.f20a = null;
        this.b = null;
        this.mContext = context;
        this.b = new com.ta.utdid2.b.a.c(context, k, "Alvin2", false, true);
        this.f20a = new com.ta.utdid2.b.a.c(context, ".DataStorage", "ContextData", false, true);
        this.f21a = new d();
        this.i = String.format("K_%d", Integer.valueOf(g.a(this.i)));
        this.j = String.format("D_%d", Integer.valueOf(g.a(this.j)));
    }

    public static c a(Context context) {
        if (context != null && a == null) {
            synchronized (e) {
                if (a == null) {
                    c cVar = new c(context);
                    a = cVar;
                    cVar.c();
                }
            }
        }
        return a;
    }

    private boolean b(String str) {
        if (str != null) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length() && !this.f22b.matcher(str).find()) {
                return true;
            }
        }
        return false;
    }

    private void c() throws Throwable {
        com.ta.utdid2.b.a.c cVar = this.b;
        if (cVar != null) {
            if (g.m6a(cVar.getString("UTDID2"))) {
                String string = this.b.getString("UTDID");
                if (!g.m6a(string)) {
                    f(string);
                }
            }
            boolean z = false;
            boolean z2 = true;
            if (!g.m6a(this.b.getString("DID"))) {
                this.b.remove("DID");
                z = true;
            }
            if (!g.m6a(this.b.getString("EI"))) {
                this.b.remove("EI");
                z = true;
            }
            if (g.m6a(this.b.getString("SI"))) {
                z2 = z;
            } else {
                this.b.remove("SI");
            }
            if (z2) {
                this.b.commit();
            }
        }
    }

    private void f(String str) throws Throwable {
        com.ta.utdid2.b.a.c cVar;
        if (b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() != 24 || (cVar = this.b) == null) {
                return;
            }
            cVar.putString("UTDID2", str);
            this.b.commit();
        }
    }

    private void g(String str) throws Throwable {
        com.ta.utdid2.b.a.c cVar;
        if (str == null || (cVar = this.f20a) == null || str.equals(cVar.getString(this.i))) {
            return;
        }
        this.f20a.putString(this.i, str);
        this.f20a.commit();
    }

    private void h(String str) {
        if (f() && b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length()) {
                String string = null;
                try {
                    string = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
                } catch (Exception unused) {
                }
                if (b(string)) {
                    return;
                }
                try {
                    Settings.System.putString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk", str);
                } catch (Exception unused2) {
                }
            }
        }
    }

    private void i(String str) {
        String string;
        try {
            string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception unused) {
            string = null;
        }
        if (str.equals(string)) {
            return;
        }
        try {
            Settings.System.putString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp", str);
        } catch (Exception unused2) {
        }
    }

    private void j(String str) {
        if (!f() || str == null) {
            return;
        }
        i(str);
    }

    public synchronized String getValue() {
        String str = this.h;
        if (str != null) {
            return str;
        }
        return h();
    }

    private String g() throws Throwable {
        com.ta.utdid2.b.a.c cVar = this.b;
        if (cVar == null) {
            return null;
        }
        String string = cVar.getString("UTDID2");
        if (g.m6a(string) || this.f21a.c(string) == null) {
            return null;
        }
        return string;
    }

    public static String b(byte[] bArr) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(f.a(new byte[]{69, 114, 116, -33, 125, -54, -31, 86, -11, 11, -78, -96, -17, -99, 64, 23, -95, -126, -82, -64, 113, 116, -16, -103, 49, -30, 9, -39, 33, -80, -68, -78, -117, 53, 30, -122, 64, -104, 74, -49, 106, 85, -38, -93}), mac.getAlgorithm()));
        return com.ta.utdid2.a.a.b.encodeToString(mac.doFinal(bArr), 2);
    }

    private boolean f() {
        return this.mContext.checkPermission("android.permission.WRITE_SETTINGS", Binder.getCallingPid(), Binder.getCallingUid()) == 0;
    }

    public synchronized String i() {
        String string;
        String string2 = "";
        try {
            string2 = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
        } catch (Exception unused) {
        }
        if (b(string2)) {
            return string2;
        }
        e eVar = new e();
        boolean z = false;
        try {
            string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception unused2) {
            string = null;
        }
        if (g.m6a(string)) {
            z = true;
        } else {
            String strE = eVar.e(string);
            if (b(strE)) {
                h(strE);
                return strE;
            }
            String strD = eVar.d(string);
            if (b(strD)) {
                String strC = this.f21a.c(strD);
                if (!g.m6a(strC)) {
                    j(strC);
                    try {
                        string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
                    } catch (Exception unused3) {
                    }
                }
            }
            String strD2 = this.f21a.d(string);
            if (b(strD2)) {
                this.h = strD2;
                f(strD2);
                g(string);
                h(this.h);
                return this.h;
            }
        }
        String strG = g();
        if (b(strG)) {
            String strC2 = this.f21a.c(strG);
            if (z) {
                j(strC2);
            }
            h(strG);
            g(strC2);
            this.h = strG;
            return strG;
        }
        String string3 = this.f20a.getString(this.i);
        if (!g.m6a(string3)) {
            String strD3 = eVar.d(string3);
            if (!b(strD3)) {
                strD3 = this.f21a.d(string3);
            }
            if (b(strD3)) {
                String strC3 = this.f21a.c(strD3);
                if (!g.m6a(strD3)) {
                    this.h = strD3;
                    if (z) {
                        j(strC3);
                    }
                    f(this.h);
                    return this.h;
                }
            }
        }
        return null;
    }

    public synchronized String h() {
        String strI = i();
        this.h = strI;
        if (!TextUtils.isEmpty(strI)) {
            return this.h;
        }
        try {
            byte[] bArrM13c = m13c();
            if (bArrM13c != null) {
                String strEncodeToString = com.ta.utdid2.a.a.b.encodeToString(bArrM13c, 2);
                this.h = strEncodeToString;
                f(strEncodeToString);
                String strC = this.f21a.c(bArrM13c);
                if (strC != null) {
                    j(strC);
                    g(strC);
                }
                return this.h;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    /* JADX INFO: renamed from: c, reason: collision with other method in class */
    private byte[] m13c() throws Exception {
        String strA;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNextInt = new Random().nextInt();
        byte[] bytes = com.ta.utdid2.a.a.d.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = com.ta.utdid2.a.a.d.getBytes(iNextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            strA = com.ta.utdid2.a.a.e.a(this.mContext);
        } catch (Exception unused) {
            strA = "" + new Random().nextInt();
        }
        byteArrayOutputStream.write(com.ta.utdid2.a.a.d.getBytes(g.a(strA)), 0, 4);
        byteArrayOutputStream.write(com.ta.utdid2.a.a.d.getBytes(g.a(b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }
}
