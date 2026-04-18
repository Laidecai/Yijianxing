package com.ta.utdid2.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.ta.utdid2.a.a.g;
import com.ta.utdid2.b.a.b;
import java.io.File;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public SharedPreferences f11a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public b f13a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public d f14a;
    public String b;
    public String c;
    public boolean f;
    public boolean g;
    public boolean h;
    public boolean i;
    public Context mContext;
    public SharedPreferences.Editor a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public b.a f12a = null;

    /* JADX WARN: Removed duplicated region for block: B:60:0x0145  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public c(android.content.Context r10, java.lang.String r11, java.lang.String r12, boolean r13, boolean r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.c.<init>(android.content.Context, java.lang.String, java.lang.String, boolean, boolean):void");
    }

    private d a(String str) {
        File fileM7a = m7a(str);
        if (fileM7a == null) {
            return null;
        }
        d dVar = new d(fileM7a.getAbsolutePath());
        this.f14a = dVar;
        return dVar;
    }

    private void b() throws Throwable {
        b bVar;
        SharedPreferences sharedPreferences;
        if (this.a == null && (sharedPreferences = this.f11a) != null) {
            this.a = sharedPreferences.edit();
        }
        if (this.h && this.f12a == null && (bVar = this.f13a) != null) {
            this.f12a = bVar.a();
        }
        c();
    }

    private boolean c() throws Throwable {
        b bVar = this.f13a;
        if (bVar == null) {
            return false;
        }
        boolean zB = bVar.b();
        if (!zB) {
            commit();
        }
        return zB;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean commit() throws java.lang.Throwable {
        /*
            r6 = this;
            long r0 = java.lang.System.currentTimeMillis()
            android.content.SharedPreferences$Editor r2 = r6.a
            r3 = 0
            if (r2 == 0) goto L20
            boolean r4 = r6.i
            if (r4 != 0) goto L16
            android.content.SharedPreferences r4 = r6.f11a
            if (r4 == 0) goto L16
            java.lang.String r4 = "t"
            r2.putLong(r4, r0)
        L16:
            android.content.SharedPreferences$Editor r0 = r6.a
            boolean r0 = r0.commit()
            if (r0 != 0) goto L20
            r0 = 0
            goto L21
        L20:
            r0 = 1
        L21:
            android.content.SharedPreferences r1 = r6.f11a
            if (r1 == 0) goto L31
            android.content.Context r1 = r6.mContext
            if (r1 == 0) goto L31
            java.lang.String r2 = r6.b
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            r6.f11a = r1
        L31:
            r1 = 0
            java.lang.String r1 = android.os.Environment.getExternalStorageState()     // Catch: java.lang.Exception -> L37
            goto L3b
        L37:
            r2 = move-exception
            r2.printStackTrace()
        L3b:
            boolean r2 = com.ta.utdid2.a.a.g.m6a(r1)
            if (r2 != 0) goto L9e
            java.lang.String r2 = "mounted"
            boolean r4 = r1.equals(r2)
            if (r4 == 0) goto L80
            com.ta.utdid2.b.a.b r4 = r6.f13a
            if (r4 != 0) goto L75
            java.lang.String r4 = r6.c
            com.ta.utdid2.b.a.d r4 = r6.a(r4)
            if (r4 == 0) goto L80
            java.lang.String r5 = r6.b
            com.ta.utdid2.b.a.b r4 = r4.a(r5, r3)
            r6.f13a = r4
            boolean r5 = r6.i
            if (r5 != 0) goto L67
            android.content.SharedPreferences r5 = r6.f11a
            r6.a(r5, r4)
            goto L6c
        L67:
            android.content.SharedPreferences r5 = r6.f11a
            r6.a(r4, r5)
        L6c:
            com.ta.utdid2.b.a.b r4 = r6.f13a
            com.ta.utdid2.b.a.b$a r4 = r4.a()
            r6.f12a = r4
            goto L80
        L75:
            com.ta.utdid2.b.a.b$a r4 = r6.f12a     // Catch: java.lang.Exception -> L7f
            if (r4 == 0) goto L80
            boolean r4 = r4.commit()     // Catch: java.lang.Exception -> L7f
            if (r4 != 0) goto L80
        L7f:
            r0 = 0
        L80:
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L92
            java.lang.String r2 = "mounted_ro"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L9e
            com.ta.utdid2.b.a.b r1 = r6.f13a
            if (r1 == 0) goto L9e
        L92:
            com.ta.utdid2.b.a.d r1 = r6.f14a     // Catch: java.lang.Exception -> L9e
            if (r1 == 0) goto L9e
            java.lang.String r2 = r6.b     // Catch: java.lang.Exception -> L9e
            com.ta.utdid2.b.a.b r1 = r1.a(r2, r3)     // Catch: java.lang.Exception -> L9e
            r6.f13a = r1     // Catch: java.lang.Exception -> L9e
        L9e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.c.commit():boolean");
    }

    public String getString(String str) throws Throwable {
        c();
        SharedPreferences sharedPreferences = this.f11a;
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString(str, "");
            if (!g.m6a(string)) {
                return string;
            }
        }
        b bVar = this.f13a;
        return bVar != null ? bVar.getString(str, "") : "";
    }

    public void putString(String str, String str2) throws Throwable {
        if (g.m6a(str) || str.equals("t")) {
            return;
        }
        b();
        SharedPreferences.Editor editor = this.a;
        if (editor != null) {
            editor.putString(str, str2);
        }
        b.a aVar = this.f12a;
        if (aVar != null) {
            aVar.a(str, str2);
        }
    }

    public void remove(String str) throws Throwable {
        if (g.m6a(str) || str.equals("t")) {
            return;
        }
        b();
        SharedPreferences.Editor editor = this.a;
        if (editor != null) {
            editor.remove(str);
        }
        b.a aVar = this.f12a;
        if (aVar != null) {
            aVar.a(str);
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private File m7a(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null) {
            return null;
        }
        File file = new File(String.format("%s%s%s", externalStorageDirectory.getAbsolutePath(), File.separator, str));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private void a(SharedPreferences sharedPreferences, b bVar) {
        b.a aVarA;
        if (sharedPreferences == null || bVar == null || (aVarA = bVar.a()) == null) {
            return;
        }
        aVarA.b();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                aVarA.a(key, (String) value);
            } else if (value instanceof Integer) {
                aVarA.a(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                aVarA.a(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                aVarA.a(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                aVarA.a(key, ((Boolean) value).booleanValue());
            }
        }
        try {
            aVarA.commit();
        } catch (Exception unused) {
        }
    }

    private void a(b bVar, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editorEdit;
        if (bVar == null || sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.clear();
        for (Map.Entry<String, ?> entry : bVar.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                editorEdit.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editorEdit.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                editorEdit.putLong(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                editorEdit.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                editorEdit.putBoolean(key, ((Boolean) value).booleanValue());
            }
        }
        editorEdit.commit();
    }
}
