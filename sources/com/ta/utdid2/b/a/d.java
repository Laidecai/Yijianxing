package com.ta.utdid2.b.a;

import com.ta.utdid2.b.a.b;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static final Object b = new Object();
    public File a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public final Object f15a = new Object();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public HashMap<File, a> f16a = new HashMap<>();

    public static final class a implements b {
        public static final Object c = new Object();
        public Map a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        public WeakHashMap<b.InterfaceC0014b, Object> f17a;
        public final File b;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        public final int f18c;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        public final File f19c;
        public boolean j = false;

        public a(File file, int i, Map map) {
            this.b = file;
            this.f19c = d.a(file);
            this.f18c = i;
            this.a = map == null ? new HashMap() : map;
            this.f17a = new WeakHashMap<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean e() {
            if (this.b.exists()) {
                if (this.f19c.exists()) {
                    this.b.delete();
                } else if (!this.b.renameTo(this.f19c)) {
                    return false;
                }
            }
            try {
                FileOutputStream fileOutputStreamA = a(this.b);
                if (fileOutputStreamA == null) {
                    return false;
                }
                e.a(this.a, fileOutputStreamA);
                fileOutputStreamA.close();
                this.f19c.delete();
                return true;
            } catch (Exception unused) {
                if (this.b.exists()) {
                    this.b.delete();
                }
                return false;
            }
        }

        @Override // com.ta.utdid2.b.a.b
        public boolean b() {
            return this.b != null && new File(this.b.getAbsolutePath()).exists();
        }

        public boolean d() {
            boolean z;
            synchronized (this) {
                z = this.j;
            }
            return z;
        }

        @Override // com.ta.utdid2.b.a.b
        public Map<String, ?> getAll() {
            HashMap map;
            synchronized (this) {
                map = new HashMap(this.a);
            }
            return map;
        }

        @Override // com.ta.utdid2.b.a.b
        public long getLong(String str, long j) {
            synchronized (this) {
                Long l = (Long) this.a.get(str);
                if (l != null) {
                    j = l.longValue();
                }
            }
            return j;
        }

        @Override // com.ta.utdid2.b.a.b
        public String getString(String str, String str2) {
            synchronized (this) {
                String str3 = (String) this.a.get(str);
                if (str3 != null) {
                    str2 = str3;
                }
            }
            return str2;
        }

        /* JADX INFO: renamed from: com.ta.utdid2.b.a.d$a$a, reason: collision with other inner class name */
        public final class C0015a implements b.a {
            public final Map<String, Object> b = new HashMap();
            public boolean k = false;

            public C0015a() {
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str, String str2) {
                synchronized (this) {
                    this.b.put(str, str2);
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a b() {
                synchronized (this) {
                    this.k = true;
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public boolean commit() {
                boolean z;
                ArrayList arrayList;
                HashSet<b.InterfaceC0014b> hashSet;
                boolean zE;
                synchronized (d.b) {
                    z = a.this.f17a.size() > 0;
                    arrayList = null;
                    if (z) {
                        arrayList = new ArrayList();
                        hashSet = new HashSet(a.this.f17a.keySet());
                    } else {
                        hashSet = null;
                    }
                    synchronized (this) {
                        if (this.k) {
                            a.this.a.clear();
                            this.k = false;
                        }
                        for (Map.Entry<String, Object> entry : this.b.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            if (value == this) {
                                a.this.a.remove(key);
                            } else {
                                a.this.a.put(key, value);
                            }
                            if (z) {
                                arrayList.add(key);
                            }
                        }
                        this.b.clear();
                    }
                    zE = a.this.e();
                    if (zE) {
                        a.this.a(true);
                    }
                }
                if (z) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str = (String) arrayList.get(size);
                        for (b.InterfaceC0014b interfaceC0014b : hashSet) {
                            if (interfaceC0014b != null) {
                                interfaceC0014b.a(a.this, str);
                            }
                        }
                    }
                }
                return zE;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str, int i) {
                synchronized (this) {
                    this.b.put(str, Integer.valueOf(i));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str, long j) {
                synchronized (this) {
                    this.b.put(str, Long.valueOf(j));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str, float f) {
                synchronized (this) {
                    this.b.put(str, Float.valueOf(f));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str, boolean z) {
                synchronized (this) {
                    this.b.put(str, Boolean.valueOf(z));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str) {
                synchronized (this) {
                    this.b.put(str, this);
                }
                return this;
            }
        }

        public void a(boolean z) {
            synchronized (this) {
                this.j = z;
            }
        }

        public void a(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.a = map;
                }
            }
        }

        @Override // com.ta.utdid2.b.a.b
        public b.a a() {
            return new C0015a();
        }

        private FileOutputStream a(File file) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException unused) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException unused2) {
                    return null;
                }
            }
            return fileOutputStream;
        }
    }

    public d(String str) {
        if (str == null || str.length() <= 0) {
            throw new RuntimeException("Directory can not be empty");
        }
        this.a = new File(str);
    }

    private File a(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    private File b(String str) {
        return a(a(), str + ".xml");
    }

    private File a() {
        File file;
        synchronized (this.f15a) {
            file = this.a;
        }
        return file;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x008f A[Catch: all -> 0x005c, TRY_ENTER, TRY_LEAVE, TryCatch #16 {all -> 0x005c, blocks: (B:31:0x0059, B:55:0x008f), top: B:86:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.ta.utdid2.b.a.b, com.ta.utdid2.b.a.d$a] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.ta.utdid2.b.a.b] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v16, types: [java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.ta.utdid2.b.a.b a(java.lang.String r6, int r7) throws java.lang.Throwable {
        /*
            r5 = this;
            java.io.File r6 = r5.b(r6)
            java.lang.Object r0 = com.ta.utdid2.b.a.d.b
            monitor-enter(r0)
            java.util.HashMap<java.io.File, com.ta.utdid2.b.a.d$a> r1 = r5.f16a     // Catch: java.lang.Throwable -> Lb6
            java.lang.Object r1 = r1.get(r6)     // Catch: java.lang.Throwable -> Lb6
            com.ta.utdid2.b.a.d$a r1 = (com.ta.utdid2.b.a.d.a) r1     // Catch: java.lang.Throwable -> Lb6
            if (r1 == 0) goto L19
            boolean r2 = r1.d()     // Catch: java.lang.Throwable -> Lb6
            if (r2 != 0) goto L19
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb6
            return r1
        L19:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb6
            java.io.File r0 = a(r6)
            boolean r2 = r0.exists()
            if (r2 == 0) goto L2a
            r6.delete()
            r0.renameTo(r6)
        L2a:
            boolean r0 = r6.exists()
            r2 = 0
            if (r0 == 0) goto L93
            boolean r0 = r6.canRead()
            if (r0 == 0) goto L93
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56 org.xmlpull.v1.XmlPullParserException -> L5e
            r0.<init>(r6)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56 org.xmlpull.v1.XmlPullParserException -> L5e
            java.util.HashMap r2 = com.ta.utdid2.b.a.e.a(r0)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c org.xmlpull.v1.XmlPullParserException -> L50
            r0.close()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4c org.xmlpull.v1.XmlPullParserException -> L50
            r0.close()     // Catch: java.lang.Throwable -> L48
            goto L93
        L48:
            goto L93
        L4a:
            r6 = move-exception
            goto L80
        L4c:
            r4 = r2
            r2 = r0
            r0 = r4
            goto L57
        L50:
            r4 = r2
            r2 = r0
            r0 = r4
            goto L5f
        L54:
            r6 = move-exception
            goto L7f
        L56:
            r0 = r2
        L57:
            if (r2 == 0) goto L5c
            r2.close()     // Catch: java.lang.Throwable -> L5c
        L5c:
            r2 = r0
            goto L93
        L5e:
            r0 = r2
        L5f:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L86
            r3.<init>(r6)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L86
            int r2 = r3.available()     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L76
            byte[] r2 = new byte[r2]     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L76
            r3.read(r2)     // Catch: java.lang.Throwable -> L73 java.lang.Exception -> L76
            r3.close()     // Catch: java.lang.Throwable -> L71
            goto L8d
        L71:
            goto L8d
        L73:
            r6 = move-exception
            r2 = r3
            goto L79
        L76:
            r2 = r3
            goto L87
        L78:
            r6 = move-exception
        L79:
            if (r2 == 0) goto L7e
            r2.close()     // Catch: java.lang.Throwable -> L7e
        L7e:
            throw r6     // Catch: java.lang.Throwable -> L54
        L7f:
            r0 = r2
        L80:
            if (r0 == 0) goto L85
            r0.close()     // Catch: java.lang.Throwable -> L85
        L85:
            throw r6
        L86:
        L87:
            if (r2 == 0) goto L8c
            r2.close()     // Catch: java.lang.Throwable -> L8c
        L8c:
            r3 = r2
        L8d:
            if (r3 == 0) goto L5c
            r3.close()     // Catch: java.lang.Throwable -> L5c
            goto L5c
        L93:
            java.lang.Object r3 = com.ta.utdid2.b.a.d.b
            monitor-enter(r3)
            if (r1 == 0) goto L9c
            r1.a(r2)     // Catch: java.lang.Throwable -> Lb3
            goto Lb1
        L9c:
            java.util.HashMap<java.io.File, com.ta.utdid2.b.a.d$a> r0 = r5.f16a     // Catch: java.lang.Throwable -> Lb3
            java.lang.Object r0 = r0.get(r6)     // Catch: java.lang.Throwable -> Lb3
            r1 = r0
            com.ta.utdid2.b.a.d$a r1 = (com.ta.utdid2.b.a.d.a) r1     // Catch: java.lang.Throwable -> Lb3
            if (r1 != 0) goto Lb1
            com.ta.utdid2.b.a.d$a r1 = new com.ta.utdid2.b.a.d$a     // Catch: java.lang.Throwable -> Lb3
            r1.<init>(r6, r7, r2)     // Catch: java.lang.Throwable -> Lb3
            java.util.HashMap<java.io.File, com.ta.utdid2.b.a.d$a> r7 = r5.f16a     // Catch: java.lang.Throwable -> Lb3
            r7.put(r6, r1)     // Catch: java.lang.Throwable -> Lb3
        Lb1:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> Lb3
            return r1
        Lb3:
            r6 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> Lb3
            throw r6
        Lb6:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb6
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.d.a(java.lang.String, int):com.ta.utdid2.b.a.b");
    }

    public static File a(File file) {
        return new File(file.getPath() + ".bak");
    }
}
