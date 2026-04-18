package com.tds.common.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public class CurrentConfigProvider extends ContentProvider {
    private static final int URI_CODE_CLIENT_ID = 1;
    private static final int URI_CODE_CLIENT_SERVER = 4;
    private static final int URI_CODE_CLIENT_TOKEN = 3;
    private static final int URI_CODE_LANG = 2;
    static final UriMatcher uriMatcher = new UriMatcher(-1);

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        UriMatcher uriMatcher2 = uriMatcher;
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/client_id", 1);
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/lang", 2);
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/client_token", 3);
        uriMatcher2.addURI(context.getPackageName() + ".tap_config.provider", "/client_server", 4);
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return buildCursor(uriMatcher.match(uri));
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("insert is not supported" + uri);
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("update is not supported" + uri);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("delete is not supported" + uri);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.database.Cursor buildCursor(int r5) {
        /*
            r4 = this;
            r0 = 0
            com.tds.common.entities.TapConfig r1 = com.tds.common.TapCommon.getTapConfig()     // Catch: java.lang.Exception -> L49
            r2 = 1
            if (r5 == r2) goto L2c
            r3 = 2
            if (r5 == r3) goto L1c
            r3 = 3
            if (r5 == r3) goto L17
            r3 = 4
            if (r5 == r3) goto L12
            goto L31
        L12:
            if (r1 == 0) goto L31
            java.lang.String r5 = r1.serverUrl     // Catch: java.lang.Exception -> L49
            goto L32
        L17:
            if (r1 == 0) goto L31
            java.lang.String r5 = r1.clientToken     // Catch: java.lang.Exception -> L49
            goto L32
        L1c:
            com.tds.common.localize.TapLanguage r5 = com.tds.common.localize.LocalizeManager.getGameSelectedLang()     // Catch: java.lang.Exception -> L49
            com.tds.common.localize.TapLanguage r1 = com.tds.common.localize.TapLanguage.AUTO     // Catch: java.lang.Exception -> L49
            if (r5 != r1) goto L27
            java.lang.String r5 = ""
            goto L32
        L27:
            java.lang.String r5 = com.tds.common.localize.LocalizeManager.getPreferredLanguageString()     // Catch: java.lang.Exception -> L49
            goto L32
        L2c:
            if (r1 == 0) goto L31
            java.lang.String r5 = r1.clientId     // Catch: java.lang.Exception -> L49
            goto L32
        L31:
            r5 = r0
        L32:
            if (r5 != 0) goto L35
            return r0
        L35:
            java.lang.String r1 = "data"
            java.lang.String[] r1 = new java.lang.String[]{r1}     // Catch: java.lang.Exception -> L49
            android.database.MatrixCursor r3 = new android.database.MatrixCursor     // Catch: java.lang.Exception -> L49
            r3.<init>(r1)     // Catch: java.lang.Exception -> L49
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch: java.lang.Exception -> L49
            r2 = 0
            r1[r2] = r5     // Catch: java.lang.Exception -> L49
            r3.addRow(r1)     // Catch: java.lang.Exception -> L49
            return r3
        L49:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tds.common.provider.CurrentConfigProvider.buildCursor(int):android.database.Cursor");
    }
}
