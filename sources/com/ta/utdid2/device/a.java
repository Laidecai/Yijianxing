package com.ta.utdid2.device;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public String d = "";
    public String e = "";
    public String f = "";
    public String g = "";
    public long a = 0;
    public long b = 0;

    public void a(long j) {
        this.b = j;
    }

    public void b(long j) {
        this.a = j;
    }

    public void c(String str) {
        this.e = str;
    }

    public void d(String str) {
        this.f = str;
    }

    public String e() {
        return this.d;
    }

    public String f() {
        return this.g;
    }

    public String getDeviceId() {
        return this.f;
    }

    public String getImsi() {
        return this.e;
    }

    public long a() {
        return this.a;
    }

    public void b(String str) {
        this.d = str;
    }

    public void e(String str) {
        this.g = str;
    }
}
