package com.unity3d.splash.services.core.api;

import android.media.MediaMetadataRetriever;
import android.util.Base64;
import android.util.SparseArray;
import com.taptap.services.update.download.core.breakpoint.BreakpointSQLiteKey;
import com.unity3d.splash.services.core.cache.CacheDirectory;
import com.unity3d.splash.services.core.cache.CacheDirectoryType;
import com.unity3d.splash.services.core.cache.CacheError;
import com.unity3d.splash.services.core.cache.CacheThread;
import com.unity3d.splash.services.core.device.Device;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.request.WebRequestError;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Cache {
    @WebViewExposed
    public static void deleteFile(String str, WebViewCallback webViewCallback) {
        if (new File(fileIdToFilename(str)).delete()) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(CacheError.FILE_IO_ERROR, new Object[0]);
        }
    }

    @WebViewExposed
    public static void download(String str, String str2, JSONArray jSONArray, Boolean bool, WebViewCallback webViewCallback) {
        if (CacheThread.isActive()) {
            webViewCallback.error(CacheError.FILE_ALREADY_CACHING, new Object[0]);
            return;
        }
        if (!Device.isActiveNetworkConnected()) {
            webViewCallback.error(CacheError.NO_INTERNET, new Object[0]);
            return;
        }
        try {
            CacheThread.download(str, fileIdToFilename(str2), Request.getHeadersMap(jSONArray), bool.booleanValue());
            webViewCallback.invoke(new Object[0]);
        } catch (Exception e) {
            DeviceLog.exception("Error mapping headers for the request", e);
            webViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, str, str2);
        }
    }

    private static String fileIdToFilename(String str) {
        return SdkProperties.getCacheDirectory() + "/" + SdkProperties.getCacheFilePrefix() + str;
    }

    @WebViewExposed
    public static void getCacheDirectoryExists(WebViewCallback webViewCallback) {
        File cacheDirectory = SdkProperties.getCacheDirectory();
        if (cacheDirectory == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
        } else {
            webViewCallback.invoke(Boolean.valueOf(cacheDirectory.exists()));
        }
    }

    @WebViewExposed
    public static void getCacheDirectoryType(WebViewCallback webViewCallback) {
        CacheDirectory cacheDirectoryObject = SdkProperties.getCacheDirectoryObject();
        if (cacheDirectoryObject == null || cacheDirectoryObject.getCacheDirectory(ClientProperties.getApplicationContext()) == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
            return;
        }
        if (!cacheDirectoryObject.getCacheDirectory(ClientProperties.getApplicationContext()).exists()) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_DOESNT_EXIST, new Object[0]);
            return;
        }
        CacheDirectoryType type = cacheDirectoryObject.getType();
        if (type == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_TYPE_NULL, new Object[0]);
        } else {
            webViewCallback.invoke(type.name());
        }
    }

    @WebViewExposed
    public static void getFileContent(String str, String str2, WebViewCallback webViewCallback) {
        Object objEncodeToString;
        String strFileIdToFilename = fileIdToFilename(str);
        File file = new File(strFileIdToFilename);
        if (!file.exists()) {
            webViewCallback.error(CacheError.FILE_NOT_FOUND, str, strFileIdToFilename);
            return;
        }
        try {
            byte[] fileBytes = Utilities.readFileBytes(file);
            if (str2 == null) {
                webViewCallback.error(CacheError.UNSUPPORTED_ENCODING, str, strFileIdToFilename, str2);
                return;
            }
            if (str2.equals("UTF-8")) {
                objEncodeToString = Charset.forName("UTF-8").decode(ByteBuffer.wrap(fileBytes)).toString();
            } else {
                if (!str2.equals("Base64")) {
                    webViewCallback.error(CacheError.UNSUPPORTED_ENCODING, str, strFileIdToFilename, str2);
                    return;
                }
                objEncodeToString = Base64.encodeToString(fileBytes, 2);
            }
            webViewCallback.invoke(objEncodeToString);
        } catch (IOException e) {
            webViewCallback.error(CacheError.FILE_IO_ERROR, str, strFileIdToFilename, e.getMessage() + ", " + e.getClass().getName());
        }
    }

    @WebViewExposed
    public static void getFileInfo(String str, WebViewCallback webViewCallback) {
        try {
            webViewCallback.invoke(getFileJson(str));
        } catch (JSONException e) {
            DeviceLog.exception("Error creating JSON", e);
            webViewCallback.error(CacheError.JSON_ERROR, new Object[0]);
        }
    }

    private static JSONObject getFileJson(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(BreakpointSQLiteKey.ID, str);
        File file = new File(fileIdToFilename(str));
        if (file.exists()) {
            jSONObject.put("found", true);
            jSONObject.put("size", file.length());
            jSONObject.put("mtime", file.lastModified());
        } else {
            jSONObject.put("found", false);
        }
        return jSONObject;
    }

    @WebViewExposed
    public static void getFilePath(String str, WebViewCallback webViewCallback) {
        if (new File(fileIdToFilename(str)).exists()) {
            webViewCallback.invoke(fileIdToFilename(str));
        } else {
            webViewCallback.error(CacheError.FILE_NOT_FOUND, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getFiles(WebViewCallback webViewCallback) {
        File cacheDirectory = SdkProperties.getCacheDirectory();
        if (cacheDirectory == null) {
            return;
        }
        DeviceLog.debug("Unity Ads cache: checking app directory for Unity Ads cached files");
        File[] fileArrListFiles = cacheDirectory.listFiles(new FilenameFilter() { // from class: com.unity3d.splash.services.core.api.Cache.1
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return str.startsWith(SdkProperties.getCacheFilePrefix());
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            webViewCallback.invoke(new JSONArray());
        }
        try {
            JSONArray jSONArray = new JSONArray();
            for (File file : fileArrListFiles) {
                String strSubstring = file.getName().substring(SdkProperties.getCacheFilePrefix().length());
                DeviceLog.debug("Unity Ads cache: found " + strSubstring + ", " + file.length() + " bytes");
                jSONArray.put(getFileJson(strSubstring));
            }
            webViewCallback.invoke(jSONArray);
        } catch (JSONException e) {
            DeviceLog.exception("Error creating JSON", e);
            webViewCallback.error(CacheError.JSON_ERROR, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getFreeSpace(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getFreeSpace(SdkProperties.getCacheDirectory())));
    }

    @WebViewExposed
    public static void getHash(String str, WebViewCallback webViewCallback) {
        webViewCallback.invoke(Utilities.Sha256(str));
    }

    private static SparseArray getMetaData(String str, JSONArray jSONArray) throws JSONException, IOException {
        File file = new File(str);
        SparseArray sparseArray = new SparseArray();
        if (!file.exists()) {
            throw new IOException("File: " + file.getAbsolutePath() + " doesn't exist");
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        for (int i = 0; i < jSONArray.length(); i++) {
            int i2 = jSONArray.getInt(i);
            String strExtractMetadata = mediaMetadataRetriever.extractMetadata(i2);
            if (strExtractMetadata != null) {
                sparseArray.put(i2, strExtractMetadata);
            }
        }
        return sparseArray;
    }

    @WebViewExposed
    public static void getMetaData(String str, JSONArray jSONArray, WebViewCallback webViewCallback) {
        try {
            SparseArray metaData = getMetaData(fileIdToFilename(str), jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < metaData.size(); i++) {
                JSONArray jSONArray3 = new JSONArray();
                jSONArray3.put(metaData.keyAt(i));
                jSONArray3.put(metaData.valueAt(i));
                jSONArray2.put(jSONArray3);
            }
            webViewCallback.invoke(jSONArray2);
        } catch (IOException e) {
            webViewCallback.error(CacheError.FILE_IO_ERROR, e.getMessage());
        } catch (RuntimeException e2) {
            webViewCallback.error(CacheError.INVALID_ARGUMENT, e2.getMessage());
        } catch (JSONException e3) {
            webViewCallback.error(CacheError.JSON_ERROR, e3.getMessage());
        }
    }

    @WebViewExposed
    public static void getProgressInterval(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(CacheThread.getProgressInterval()));
    }

    @WebViewExposed
    public static void getTimeouts(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Integer.valueOf(CacheThread.getConnectTimeout()), Integer.valueOf(CacheThread.getReadTimeout()));
    }

    @WebViewExposed
    public static void getTotalSpace(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Long.valueOf(Device.getTotalSpace(SdkProperties.getCacheDirectory())));
    }

    @WebViewExposed
    public static void isCaching(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(CacheThread.isActive()));
    }

    @WebViewExposed
    public static void recreateCacheDirectory(WebViewCallback webViewCallback) {
        if (SdkProperties.getCacheDirectory().exists()) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_EXISTS, new Object[0]);
            return;
        }
        SdkProperties.setCacheDirectory(null);
        if (SdkProperties.getCacheDirectory() == null) {
            webViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
        } else {
            webViewCallback.invoke(new Object[0]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    @com.unity3d.splash.services.core.webview.bridge.WebViewExposed
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setFileContent(java.lang.String r9, java.lang.String r10, java.lang.String r11, com.unity3d.splash.services.core.webview.bridge.WebViewCallback r12) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "UTF-8"
            java.lang.String r1 = "Error closing FileOutputStream"
            java.lang.String r2 = fileIdToFilename(r9)
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            byte[] r7 = r11.getBytes(r0)     // Catch: java.io.UnsupportedEncodingException -> L95
            if (r10 == 0) goto L39
            int r8 = r10.length()
            if (r8 <= 0) goto L39
            java.lang.String r8 = "Base64"
            boolean r8 = r10.equals(r8)
            if (r8 == 0) goto L25
            byte[] r7 = android.util.Base64.decode(r11, r4)
            goto L39
        L25:
            boolean r11 = r10.equals(r0)
            if (r11 != 0) goto L39
            com.unity3d.splash.services.core.cache.CacheError r11 = com.unity3d.splash.services.core.cache.CacheError.UNSUPPORTED_ENCODING
            java.lang.Object[] r0 = new java.lang.Object[r3]
            r0[r6] = r9
            r0[r5] = r2
            r0[r4] = r10
            r12.error(r11, r0)
            return
        L39:
            r11 = 0
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57 java.io.FileNotFoundException -> L6a
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57 java.io.FileNotFoundException -> L6a
            r0.write(r7)     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L51 java.io.FileNotFoundException -> L53
            r0.flush()     // Catch: java.lang.Throwable -> L4e java.io.IOException -> L51 java.io.FileNotFoundException -> L53
            r0.close()     // Catch: java.lang.Exception -> L49
            goto L82
        L49:
            r9 = move-exception
            com.unity3d.splash.services.core.log.DeviceLog.exception(r1, r9)
            goto L82
        L4e:
            r9 = move-exception
            r11 = r0
            goto L8a
        L51:
            r11 = r0
            goto L57
        L53:
            r11 = r0
            goto L6a
        L55:
            r9 = move-exception
            goto L8a
        L57:
            com.unity3d.splash.services.core.cache.CacheError r0 = com.unity3d.splash.services.core.cache.CacheError.FILE_IO_ERROR     // Catch: java.lang.Throwable -> L55
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L55
            r3[r6] = r9     // Catch: java.lang.Throwable -> L55
            r3[r5] = r2     // Catch: java.lang.Throwable -> L55
            r3[r4] = r10     // Catch: java.lang.Throwable -> L55
            r12.error(r0, r3)     // Catch: java.lang.Throwable -> L55
            if (r11 == 0) goto L81
            r11.close()     // Catch: java.lang.Exception -> L7d
            goto L81
        L6a:
            com.unity3d.splash.services.core.cache.CacheError r0 = com.unity3d.splash.services.core.cache.CacheError.FILE_NOT_FOUND     // Catch: java.lang.Throwable -> L55
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L55
            r3[r6] = r9     // Catch: java.lang.Throwable -> L55
            r3[r5] = r2     // Catch: java.lang.Throwable -> L55
            r3[r4] = r10     // Catch: java.lang.Throwable -> L55
            r12.error(r0, r3)     // Catch: java.lang.Throwable -> L55
            if (r11 == 0) goto L81
            r11.close()     // Catch: java.lang.Exception -> L7d
            goto L81
        L7d:
            r9 = move-exception
            com.unity3d.splash.services.core.log.DeviceLog.exception(r1, r9)
        L81:
            r5 = 0
        L82:
            if (r5 == 0) goto L89
            java.lang.Object[] r9 = new java.lang.Object[r6]
            r12.invoke(r9)
        L89:
            return
        L8a:
            if (r11 == 0) goto L94
            r11.close()     // Catch: java.lang.Exception -> L90
            goto L94
        L90:
            r10 = move-exception
            com.unity3d.splash.services.core.log.DeviceLog.exception(r1, r10)
        L94:
            throw r9
        L95:
            com.unity3d.splash.services.core.cache.CacheError r11 = com.unity3d.splash.services.core.cache.CacheError.UNSUPPORTED_ENCODING
            java.lang.Object[] r0 = new java.lang.Object[r3]
            r0[r6] = r9
            r0[r5] = r2
            r0[r4] = r10
            r12.error(r11, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.splash.services.core.api.Cache.setFileContent(java.lang.String, java.lang.String, java.lang.String, com.unity3d.splash.services.core.webview.bridge.WebViewCallback):void");
    }

    @WebViewExposed
    public static void setProgressInterval(Integer num, WebViewCallback webViewCallback) {
        CacheThread.setProgressInterval(num.intValue());
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setTimeouts(Integer num, Integer num2, WebViewCallback webViewCallback) {
        CacheThread.setConnectTimeout(num.intValue());
        CacheThread.setReadTimeout(num2.intValue());
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void stop(WebViewCallback webViewCallback) {
        if (!CacheThread.isActive()) {
            webViewCallback.error(CacheError.NOT_CACHING, new Object[0]);
        } else {
            CacheThread.cancel();
            webViewCallback.invoke(new Object[0]);
        }
    }
}
