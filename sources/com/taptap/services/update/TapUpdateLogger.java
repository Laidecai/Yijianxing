package com.taptap.services.update;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class TapUpdateLogger {
    public static final String TAG = "TapUpdate";
    public static boolean isDebug = false;
    private static Level level = Level.INFO;

    public enum Level {
        OFF(0),
        ERROR(1),
        WARNING(2),
        INFO(3),
        DEBUG(4),
        VERBOSE(5);

        private final int intValue;

        Level(int i) {
            this.intValue = i;
        }
    }

    public static void enableLog(boolean z) {
        isDebug = z;
        if (z) {
            level = Level.DEBUG;
        } else {
            level = Level.INFO;
        }
    }

    public static void setLevel(Level level2) {
        if (level == null) {
            return;
        }
        level = level2;
    }

    public static void v(String str) {
        writeLog(Level.VERBOSE, str);
    }

    public static void v(String str, Throwable th) {
        writeLog(Level.VERBOSE, str, th);
    }

    public static void d(String str) {
        writeLog(Level.DEBUG, str);
    }

    public static void d(String str, Throwable th) {
        writeLog(Level.DEBUG, str, th);
    }

    public static void w(String str) {
        writeLog(Level.WARNING, str);
    }

    public static void w(String str, Throwable th) {
        writeLog(Level.WARNING, str, th);
    }

    public static void e(String str) {
        writeLog(Level.ERROR, str);
    }

    public static void e(String str, Throwable th) {
        writeLog(Level.ERROR, str, th);
    }

    private static boolean isEnabled(Level level2) {
        return level.intValue >= level2.intValue;
    }

    private static void writeLog(Level level2, String str) {
        if (str == null) {
            str = "";
        }
        writeLog(level2, str, null);
    }

    /* JADX INFO: renamed from: com.taptap.services.update.TapUpdateLogger$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$taptap$services$update$TapUpdateLogger$Level;

        static {
            int[] iArr = new int[Level.values().length];
            $SwitchMap$com$taptap$services$update$TapUpdateLogger$Level = iArr;
            try {
                iArr[Level.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$taptap$services$update$TapUpdateLogger$Level[Level.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$taptap$services$update$TapUpdateLogger$Level[Level.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$taptap$services$update$TapUpdateLogger$Level[Level.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$taptap$services$update$TapUpdateLogger$Level[Level.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static void writeLog(Level level2, String str, Throwable th) {
        if (isEnabled(level2)) {
            int i = AnonymousClass1.$SwitchMap$com$taptap$services$update$TapUpdateLogger$Level[level2.ordinal()];
            if (i == 1) {
                if (th == null) {
                    Log.v(TAG, str);
                    return;
                } else {
                    Log.v(TAG, str, th);
                    return;
                }
            }
            if (i == 2) {
                if (th == null) {
                    Log.d(TAG, str);
                    return;
                } else {
                    Log.d(TAG, str, th);
                    return;
                }
            }
            if (i == 3) {
                if (th == null) {
                    Log.i(TAG, str);
                    return;
                } else {
                    Log.i(TAG, str, th);
                    return;
                }
            }
            if (i == 4) {
                if (th == null) {
                    Log.w(TAG, str);
                    return;
                } else {
                    Log.w(TAG, str, th);
                    return;
                }
            }
            if (i != 5) {
                return;
            }
            if (th == null) {
                Log.e(TAG, str);
            } else {
                Log.e(TAG, str, th);
            }
        }
    }
}
