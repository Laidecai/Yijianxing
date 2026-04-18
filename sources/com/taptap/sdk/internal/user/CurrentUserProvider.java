package com.taptap.sdk.internal.user;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.taptap.sdk.AccessToken;
import com.taptap.sdk.Profile;
import com.tds.common.utils.JsonUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CurrentUserProvider extends ContentProvider {
    static final String AUTH_DATA_PARAM = "auth_data";
    public static Uri CONTENT_URI = null;
    static final String CREATE_DB_TABLE = " CREATE TABLE CurrentUser (auth_data TEXT NOT NULL);";
    static final String DATABASE_NAME = "TmpUserDB";
    static final int DATABASE_VERSION = 1;
    static String PROVIDER_NAME = null;
    static final String TABLE_NAME = "CurrentUser";
    static String URL = null;
    static final int uriCode = 1;
    static final UriMatcher uriMatcher = new UriMatcher(-1);
    private SQLiteDatabase db;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, CurrentUserProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(CurrentUserProvider.CREATE_DB_TABLE);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS CurrentUser");
            onCreate(sQLiteDatabase);
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        this.db = new DatabaseHelper(context).getWritableDatabase();
        PROVIDER_NAME = context.getPackageName() + ".tap_user.provider";
        String str = "content://" + PROVIDER_NAME + "/current_user";
        URL = str;
        CONTENT_URI = Uri.parse(str);
        uriMatcher.addURI(context.getPackageName() + ".tap_user.provider", "/current_user", 1);
        return this.db != null;
    }

    private Map<String, Object> jsonStrToMap(String str) {
        HashMap map = new HashMap();
        try {
            for (Map.Entry<String, Object> entry : JsonUtil.toMap(new JSONObject(str)).entrySet()) {
                map.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        } catch (Throwable unused) {
        }
        return map;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String jsonStr;
        try {
            SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
            sQLiteQueryBuilder.setTables(TABLE_NAME);
            if (uriMatcher.match(uri) == 1) {
                sQLiteQueryBuilder.setProjectionMap(new HashMap());
                this.db.delete(TABLE_NAME, "", null);
                AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
                if (currentAccessToken != null) {
                    String jsonString = currentAccessToken.toJsonString();
                    Map<String, Object> map = new HashMap<>();
                    if (jsonString != null && jsonString.length() > 0) {
                        map = jsonStrToMap(jsonString);
                    }
                    Profile currentProfile = Profile.getCurrentProfile();
                    if (currentProfile != null) {
                        String jsonString2 = currentProfile.toJsonString();
                        Map<String, Object> map2 = new HashMap<>();
                        if (jsonString2 != null && jsonString2.length() > 0) {
                            map2 = jsonStrToMap(jsonString2);
                        }
                        HashMap map3 = new HashMap(map);
                        map3.putAll(map2);
                        if (map3.size() > 0) {
                            try {
                                jsonStr = JsonUtil.toJsonStr(map3);
                            } catch (JSONException unused) {
                                jsonStr = "";
                            }
                            if (jsonStr.length() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(AUTH_DATA_PARAM, jsonStr);
                                long jInsert = this.db.insert(TABLE_NAME, "", contentValues);
                                if (jInsert > 0) {
                                    getContext().getContentResolver().notifyChange(ContentUris.withAppendedId(CONTENT_URI, jInsert), null);
                                }
                            }
                        }
                    }
                }
                Cursor cursorQuery = sQLiteQueryBuilder.query(this.db, strArr, str, strArr2, null, null, str2);
                cursorQuery.setNotificationUri(getContext().getContentResolver(), uri);
                return cursorQuery;
            }
            throw new IllegalArgumentException("Unknown URI " + uri);
        } catch (Exception unused2) {
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        if (uriMatcher.match(uri) == 1) {
            return "vnd.android.cursor.dir/current_user";
        }
        throw new IllegalArgumentException("Unsupported URI: " + uri);
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
}
