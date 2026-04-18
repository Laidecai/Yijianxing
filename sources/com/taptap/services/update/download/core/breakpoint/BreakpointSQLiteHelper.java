package com.taptap.services.update.download.core.breakpoint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.SparseArray;
import com.taptap.services.update.download.core.exception.SQLiteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BreakpointSQLiteHelper extends SQLiteOpenHelper {
    private static final String BLOCK_TABLE_NAME = "block";
    private static final String BREAKPOINT_TABLE_NAME = "breakpoint";
    private static final String NAME = "okdownload-breakpoint.db";
    private static final String RESPONSE_FILENAME_TABLE_NAME = "okdownloadResponseFilename";
    static final String TASK_FILE_DIRTY_TABLE_NAME = "taskFileDirty";
    private static final int VERSION = 3;

    private interface CreateTable {
        public static final String BLOCK = "CREATE TABLE IF NOT EXISTS block( id INTEGER PRIMARY KEY AUTOINCREMENT, breakpoint_id INTEGER, block_index INTEGER, start_offset INTEGER, content_length INTEGER, current_offset INTEGER)";
        public static final String BREAKPOINT = "CREATE TABLE IF NOT EXISTS breakpoint( id INTEGER PRIMARY KEY, url VARCHAR NOT NULL, etag VARCHAR, parent_path VARCHAR NOT NULL, filename VARCHAR, task_only_parent_path TINYINT(1) DEFAULT 0, chunked TINYINT(1) DEFAULT 0)";
        public static final String RESPONSE_FILENAME = "CREATE TABLE IF NOT EXISTS okdownloadResponseFilename( url VARCHAR NOT NULL PRIMARY KEY, filename VARCHAR NOT NULL)";
        public static final String TASK_FILE_DIRTY = "CREATE TABLE IF NOT EXISTS taskFileDirty( id INTEGER PRIMARY KEY)";
    }

    private interface Migration {
        public static final String RESPONSE_FILENAME_1_TO_2 = "CREATE TABLE IF NOT EXISTS okdownloadResponseFilename( url VARCHAR NOT NULL PRIMARY KEY, filename VARCHAR NOT NULL)";
        public static final String TASK_FILE_DIRTY_NONE_TO_2 = "CREATE TABLE IF NOT EXISTS taskFileDirty( id INTEGER PRIMARY KEY)";
    }

    private interface Select {
        public static final String ALL_FROM_BLOCK = "SELECT * FROM block";
        public static final String ALL_FROM_BREAKPOINT = "SELECT * FROM breakpoint";
        public static final String ALL_FROM_RESPONSE_FILENAME = "SELECT * FROM okdownloadResponseFilename";
        public static final String ALL_FROM_TASK_FILE_DIRTY = "SELECT * FROM taskFileDirty";
        public static final String FILENAME_FROM_RESPONSE_FILENAME_BY_URL = "SELECT filename FROM okdownloadResponseFilename WHERE url = ?";
        public static final String ID_FROM_BREAKPOINT_BY_ID = "SELECT id FROM breakpoint WHERE id = ? LIMIT 1";
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public BreakpointSQLiteHelper(Context context) {
        super(context, NAME, (SQLiteDatabase.CursorFactory) null, 3);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        if (Build.VERSION.SDK_INT >= 16) {
            setWriteAheadLoggingEnabled(true);
        } else {
            sQLiteDatabase.enableWriteAheadLogging();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CreateTable.BREAKPOINT);
        sQLiteDatabase.execSQL(CreateTable.BLOCK);
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS okdownloadResponseFilename( url VARCHAR NOT NULL PRIMARY KEY, filename VARCHAR NOT NULL)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS taskFileDirty( id INTEGER PRIMARY KEY)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1 && i2 == 2) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS okdownloadResponseFilename( url VARCHAR NOT NULL PRIMARY KEY, filename VARCHAR NOT NULL)");
        }
        if (i <= 2) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS taskFileDirty( id INTEGER PRIMARY KEY)");
        }
    }

    public void markFileDirty(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(BreakpointSQLiteKey.ID, Integer.valueOf(i));
        writableDatabase.insert(TASK_FILE_DIRTY_TABLE_NAME, null, contentValues);
    }

    public void markFileClear(int i) {
        getWritableDatabase().delete(TASK_FILE_DIRTY_TABLE_NAME, "id = ?", new String[]{String.valueOf(i)});
    }

    public List<Integer> loadDirtyFileList() {
        ArrayList arrayList = new ArrayList();
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = getWritableDatabase().rawQuery(Select.ALL_FROM_TASK_FILE_DIRTY, null);
            while (cursorRawQuery.moveToNext()) {
                arrayList.add(Integer.valueOf(cursorRawQuery.getInt(cursorRawQuery.getColumnIndex(BreakpointSQLiteKey.ID))));
            }
            return arrayList;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public SparseArray<BreakpointInfo> loadToCache() throws Throwable {
        Cursor cursor;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Cursor cursorRawQuery = null;
        try {
            Cursor cursorRawQuery2 = writableDatabase.rawQuery(Select.ALL_FROM_BREAKPOINT, null);
            while (cursorRawQuery2.moveToNext()) {
                try {
                    arrayList.add(new BreakpointInfoRow(cursorRawQuery2));
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorRawQuery;
                    cursorRawQuery = cursorRawQuery2;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            cursorRawQuery = writableDatabase.rawQuery(Select.ALL_FROM_BLOCK, null);
            while (cursorRawQuery.moveToNext()) {
                arrayList2.add(new BlockInfoRow(cursorRawQuery));
            }
            if (cursorRawQuery2 != null) {
                cursorRawQuery2.close();
            }
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            SparseArray<BreakpointInfo> sparseArray = new SparseArray<>();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                BreakpointInfo info = ((BreakpointInfoRow) it.next()).toInfo();
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    BlockInfoRow blockInfoRow = (BlockInfoRow) it2.next();
                    if (blockInfoRow.getBreakpointId() == info.id) {
                        info.addBlock(blockInfoRow.toInfo());
                        it2.remove();
                    }
                }
                sparseArray.put(info.id, info);
            }
            return sparseArray;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }

    public HashMap<String, String> loadResponseFilenameToMap() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        HashMap<String, String> map = new HashMap<>();
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = writableDatabase.rawQuery(Select.ALL_FROM_RESPONSE_FILENAME, null);
            while (cursorRawQuery.moveToNext()) {
                map.put(cursorRawQuery.getString(cursorRawQuery.getColumnIndex(BreakpointSQLiteKey.URL)), cursorRawQuery.getString(cursorRawQuery.getColumnIndex(BreakpointSQLiteKey.FILENAME)));
            }
            return map;
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public void updateFilename(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues(2);
        contentValues.put(BreakpointSQLiteKey.URL, str);
        contentValues.put(BreakpointSQLiteKey.FILENAME, str2);
        synchronized (str.intern()) {
            Cursor cursor = null;
            try {
                Cursor cursorRawQuery = writableDatabase.rawQuery(Select.FILENAME_FROM_RESPONSE_FILENAME_BY_URL, new String[]{str});
                try {
                    if (cursorRawQuery.moveToFirst()) {
                        if (!str2.equals(cursorRawQuery.getString(cursorRawQuery.getColumnIndex(BreakpointSQLiteKey.FILENAME)))) {
                            writableDatabase.replace(RESPONSE_FILENAME_TABLE_NAME, null, contentValues);
                        }
                    } else {
                        writableDatabase.insert(RESPONSE_FILENAME_TABLE_NAME, null, contentValues);
                    }
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorRawQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    public void insert(BreakpointInfo breakpointInfo) throws IOException {
        int blockCount = breakpointInfo.getBlockCount();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        for (int i = 0; i < blockCount; i++) {
            BlockInfo block = breakpointInfo.getBlock(i);
            if (writableDatabase.insert(BLOCK_TABLE_NAME, null, toValues(breakpointInfo.id, i, block)) == -1) {
                throw new SQLiteException("insert block " + block + " failed!");
            }
        }
        if (writableDatabase.insert(BREAKPOINT_TABLE_NAME, null, toValues(breakpointInfo)) != -1) {
            return;
        }
        throw new SQLiteException("insert info " + breakpointInfo + " failed!");
    }

    public void updateBlockIncrease(BreakpointInfo breakpointInfo, int i, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BreakpointSQLiteKey.CURRENT_OFFSET, Long.valueOf(j));
        getWritableDatabase().update(BLOCK_TABLE_NAME, contentValues, "breakpoint_id = ? AND block_index = ?", new String[]{Integer.toString(breakpointInfo.id), Integer.toString(i)});
    }

    public void updateInfo(BreakpointInfo breakpointInfo) throws IOException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        Cursor cursorRawQuery = null;
        try {
            cursorRawQuery = getWritableDatabase().rawQuery(Select.ID_FROM_BREAKPOINT_BY_ID, new String[]{Integer.toString(breakpointInfo.id)});
            if (cursorRawQuery.moveToNext()) {
                removeInfo(breakpointInfo.id);
                insert(breakpointInfo);
                writableDatabase.setTransactionSuccessful();
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                writableDatabase.endTransaction();
            }
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            writableDatabase.endTransaction();
        }
    }

    public void removeInfo(int i) {
        getWritableDatabase().delete(BREAKPOINT_TABLE_NAME, "id = ?", new String[]{String.valueOf(i)});
        removeBlock(i);
    }

    public void removeBlock(int i) {
        getWritableDatabase().delete(BLOCK_TABLE_NAME, "breakpoint_id = ?", new String[]{String.valueOf(i)});
    }

    private static ContentValues toValues(BreakpointInfo breakpointInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BreakpointSQLiteKey.ID, Integer.valueOf(breakpointInfo.id));
        contentValues.put(BreakpointSQLiteKey.URL, breakpointInfo.getUrl());
        contentValues.put(BreakpointSQLiteKey.ETAG, breakpointInfo.getEtag());
        contentValues.put(BreakpointSQLiteKey.PARENT_PATH, breakpointInfo.parentFile.getAbsolutePath());
        contentValues.put(BreakpointSQLiteKey.FILENAME, breakpointInfo.getFilename());
        contentValues.put(BreakpointSQLiteKey.TASK_ONLY_PARENT_PATH, Integer.valueOf(breakpointInfo.isTaskOnlyProvidedParentPath() ? 1 : 0));
        contentValues.put("chunked", Integer.valueOf(breakpointInfo.isChunked() ? 1 : 0));
        return contentValues;
    }

    private static ContentValues toValues(int i, int i2, BlockInfo blockInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BreakpointSQLiteKey.HOST_ID, Integer.valueOf(i));
        contentValues.put(BreakpointSQLiteKey.BLOCK_INDEX, Integer.valueOf(i2));
        contentValues.put(BreakpointSQLiteKey.START_OFFSET, Long.valueOf(blockInfo.getStartOffset()));
        contentValues.put(BreakpointSQLiteKey.CONTENT_LENGTH, Long.valueOf(blockInfo.getContentLength()));
        contentValues.put(BreakpointSQLiteKey.CURRENT_OFFSET, Long.valueOf(blockInfo.getCurrentOffset()));
        return contentValues;
    }
}
