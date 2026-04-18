package com.tds.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class SP {
    private static Map<String, SP> mSPCache = new ConcurrentHashMap();
    private static Context sContext;
    private SharedPreferences mSp;

    private SP(String str) {
        this.mSp = sContext.getApplicationContext().getSharedPreferences(str, 0);
    }

    public static boolean inited() {
        return sContext != null;
    }

    public static synchronized void initialize(Context context) {
        sContext = context.getApplicationContext();
    }

    public static SP getSP(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("sp name empty");
        }
        if (mSPCache.containsKey(str)) {
            return mSPCache.get(str);
        }
        if (sContext == null) {
            return null;
        }
        SP sp = new SP(str);
        mSPCache.put(str, sp);
        return sp;
    }

    public String getString(String str, String str2) {
        return this.mSp.getString(str, str2);
    }

    public Set<String> getStringSet(String str, Set<String> set) {
        return this.mSp.getStringSet(str, set);
    }

    public int getInt(String str, int i) {
        return this.mSp.getInt(str, i);
    }

    public long getLong(String str, long j) {
        return this.mSp.getLong(str, j);
    }

    public float getFloat(String str, float f) {
        return this.mSp.getFloat(str, f);
    }

    public boolean getBoolean(String str, boolean z) {
        return this.mSp.getBoolean(str, z);
    }

    public boolean contains(String str) {
        return this.mSp.contains(str);
    }

    public void putString(String str, String str2) {
        this.mSp.edit().putString(str, str2).apply();
    }

    public void putStringSet(String str, Set<String> set) {
        this.mSp.edit().putStringSet(str, set).apply();
    }

    public void putInt(String str, int i) {
        this.mSp.edit().putInt(str, i).apply();
    }

    public void putLong(String str, long j) {
        this.mSp.edit().putLong(str, j).apply();
    }

    public void putFloat(String str, float f) {
        this.mSp.edit().putFloat(str, f).apply();
    }

    public void putBoolean(String str, boolean z) {
        this.mSp.edit().putBoolean(str, z).apply();
    }

    public void remove(String str) {
        this.mSp.edit().remove(str).apply();
    }

    public void clear() {
        this.mSp.edit().clear().apply();
    }
}
