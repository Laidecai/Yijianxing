package com.tds.tapdb.sdk;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
class a {
    private static final String b = "app_start_time";
    private static final String c = "app_end_time";
    private static final String d = "app_user_id";
    private static a e;
    private final SharedPreferences a;

    private a(Context context) {
        this.a = context.getSharedPreferences(context.getPackageName() + "_app_time", 0);
    }

    public static a a(Context context) {
        if (e == null) {
            e = new a(context);
        }
        return e;
    }

    public static a d() {
        a aVar = e;
        if (aVar != null) {
            return aVar;
        }
        throw new IllegalStateException("The static method getInstance(Context context) should be called before calling getInstance()");
    }

    public long a() {
        return this.a.getLong(c, -1L);
    }

    public boolean a(long j) {
        return this.a.edit().putLong(c, j).commit();
    }

    public boolean a(String str) {
        return this.a.edit().putString(d, str).commit();
    }

    public long b() {
        return this.a.getLong(b, -1L);
    }

    public boolean b(long j) {
        return this.a.edit().putLong(b, j).commit();
    }

    public String c() {
        return this.a.getString(d, null);
    }

    public void e() {
        a(-1L);
    }

    public void f() {
        b(-1L);
    }

    public void g() {
        a((String) null);
    }
}
