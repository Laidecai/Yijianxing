package com.taptap.sdk.friends;

import com.taptap.sdk.AccessToken;
import com.taptap.sdk.Log;
import com.taptap.sdk.RegionType;
import com.taptap.sdk.TapLoginInnerConfig;
import com.taptap.sdk.net.Api;
import com.tds.common.tracker.constants.CommonParam;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapFriends {
    private static final String URL_CN = "https://open.tapapis.cn/friends/v1/list";
    private static final String URL_IO = "https://open.tapapis.com/friends/v1/list";

    public static void queryMutualList(String str, int i, final TapFriendsCallback<TapFriendResult> tapFriendsCallback) {
        if (AccessToken.getCurrentAccessToken() == null) {
            if (tapFriendsCallback != null) {
                tapFriendsCallback.onFail(new RuntimeException(" query friend list before login"));
                return;
            }
            return;
        }
        if (i < 0 || i > 500) {
            i = 50;
        }
        HashMap map = new HashMap();
        map.put("max_size", i + "");
        map.put("continuation_token", str);
        map.put(CommonParam.CLIENT_ID, TapLoginInnerConfig.getClientId());
        Api.getWithHeader(TapLoginInnerConfig.getRegionType() == RegionType.CN ? URL_CN : URL_IO, null, map, new Api.ApiCallback<JSONObject>() { // from class: com.taptap.sdk.friends.TapFriends.1
            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onSuccess(JSONObject jSONObject) {
                try {
                    TapFriendResult data = TapFriendResult.parseData(jSONObject);
                    TapFriendsCallback tapFriendsCallback2 = tapFriendsCallback;
                    if (tapFriendsCallback2 != null) {
                        tapFriendsCallback2.onSuccess(data);
                    }
                } catch (Exception e) {
                    TapFriendsCallback tapFriendsCallback3 = tapFriendsCallback;
                    if (tapFriendsCallback3 != null) {
                        tapFriendsCallback3.onFail(e);
                    }
                }
            }

            @Override // com.taptap.sdk.net.Api.ApiCallback
            public void onError(Throwable th) {
                Log.DEBUG_LOG("friend query mutual list fail = " + th.getMessage());
                TapFriendsCallback tapFriendsCallback2 = tapFriendsCallback;
                if (tapFriendsCallback2 != null) {
                    tapFriendsCallback2.onFail(th);
                }
            }
        });
    }
}
