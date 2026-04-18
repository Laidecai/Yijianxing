package com.unity3d.splash.services.core.configuration;

import com.unity3d.splash.services.core.api.Broadcast;
import com.unity3d.splash.services.core.api.Cache;
import com.unity3d.splash.services.core.api.Connectivity;
import com.unity3d.splash.services.core.api.DeviceInfo;
import com.unity3d.splash.services.core.api.Intent;
import com.unity3d.splash.services.core.api.Lifecycle;
import com.unity3d.splash.services.core.api.Permissions;
import com.unity3d.splash.services.core.api.Preferences;
import com.unity3d.splash.services.core.api.Request;
import com.unity3d.splash.services.core.api.Resolve;
import com.unity3d.splash.services.core.api.Sdk;
import com.unity3d.splash.services.core.api.SensorInfo;
import com.unity3d.splash.services.core.api.Storage;
import com.unity3d.splash.services.core.broadcast.BroadcastMonitor;
import com.unity3d.splash.services.core.cache.CacheThread;
import com.unity3d.splash.services.core.connectivity.ConnectivityMonitor;
import com.unity3d.splash.services.core.device.AdvertisingId;
import com.unity3d.splash.services.core.device.OpenAdvertisingId;
import com.unity3d.splash.services.core.device.StorageManager;
import com.unity3d.splash.services.core.device.VolumeChange;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.request.WebRequestThread;

/* JADX INFO: loaded from: classes.dex */
public class CoreModuleConfiguration implements IModuleConfiguration {
    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public Class[] getWebAppApiClassList() {
        return new Class[]{Broadcast.class, Cache.class, Connectivity.class, DeviceInfo.class, Storage.class, Sdk.class, Request.class, Resolve.class, Intent.class, Lifecycle.class, Preferences.class, SensorInfo.class, Permissions.class};
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean initCompleteState(Configuration configuration) {
        return true;
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean initErrorState(Configuration configuration, String str, String str2) {
        return true;
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean initModuleState(Configuration configuration) {
        return true;
    }

    @Override // com.unity3d.splash.services.core.configuration.IModuleConfiguration
    public boolean resetState(Configuration configuration) {
        BroadcastMonitor.removeAllBroadcastListeners();
        CacheThread.cancel();
        WebRequestThread.cancel();
        ConnectivityMonitor.stopAll();
        StorageManager.init(ClientProperties.getApplicationContext());
        AdvertisingId.init(ClientProperties.getApplicationContext());
        OpenAdvertisingId.init(ClientProperties.getApplicationContext());
        VolumeChange.clearAllListeners();
        return true;
    }
}
