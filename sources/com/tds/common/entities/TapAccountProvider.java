package com.tds.common.entities;

import com.tds.common.reactor.Observable;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface TapAccountProvider {
    AccessToken getAccessToken();

    Observable<Map<String, String>> getUserInfo();
}
