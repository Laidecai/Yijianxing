package com.unity3d.splash.services.core.device;

import android.content.Context;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class StorageManager {
    protected static final Map _storageFileMap = new HashMap();
    protected static final List _storages = new ArrayList();

    public enum StorageType {
        PRIVATE,
        PUBLIC
    }

    public static synchronized void addStorageLocation(StorageType storageType, String str) {
        Map map = _storageFileMap;
        if (!map.containsKey(storageType)) {
            map.put(storageType, str);
        }
    }

    public static Storage getStorage(StorageType storageType) {
        List<Storage> list = _storages;
        if (list == null) {
            return null;
        }
        for (Storage storage : list) {
            if (storage != null && storage.getType().equals(storageType)) {
                return storage;
            }
        }
        return null;
    }

    public static boolean hasStorage(StorageType storageType) {
        List<Storage> list = _storages;
        if (list == null) {
            return false;
        }
        for (Storage storage : list) {
            if (storage != null && storage.getType().equals(storageType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean init(Context context) {
        File filesDir;
        if (context == null || (filesDir = context.getFilesDir()) == null) {
            return false;
        }
        addStorageLocation(StorageType.PUBLIC, filesDir + "/" + SdkProperties.getLocalStorageFilePrefix() + "public-data.json");
        if (!setupStorage(StorageType.PUBLIC)) {
            return false;
        }
        addStorageLocation(StorageType.PRIVATE, filesDir + "/" + SdkProperties.getLocalStorageFilePrefix() + "private-data.json");
        return setupStorage(StorageType.PRIVATE);
    }

    public static void initStorage(StorageType storageType) {
        if (hasStorage(storageType)) {
            Storage storage = getStorage(storageType);
            if (storage != null) {
                storage.initStorage();
                return;
            }
            return;
        }
        Map map = _storageFileMap;
        if (map.containsKey(storageType)) {
            Storage storage2 = new Storage((String) map.get(storageType), storageType);
            storage2.initStorage();
            _storages.add(storage2);
        }
    }

    public static synchronized void removeStorage(StorageType storageType) {
        if (getStorage(storageType) != null) {
            _storages.remove(getStorage(storageType));
        }
        Map map = _storageFileMap;
        if (map != null) {
            map.remove(storageType);
        }
    }

    private static boolean setupStorage(StorageType storageType) {
        if (hasStorage(storageType)) {
            return true;
        }
        initStorage(storageType);
        Storage storage = getStorage(storageType);
        if (storage != null && !storage.storageFileExists()) {
            storage.writeStorage();
        }
        return storage != null;
    }
}
