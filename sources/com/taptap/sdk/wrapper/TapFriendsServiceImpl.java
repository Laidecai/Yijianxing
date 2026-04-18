package com.taptap.sdk.wrapper;

import com.taptap.sdk.friends.TapFriendResult;
import com.taptap.sdk.friends.TapFriends;
import com.taptap.sdk.friends.TapFriendsCallback;
import com.tds.common.bridge.BridgeCallback;
import com.tds.common.log.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapFriendsServiceImpl implements TapFriendsService {
    private final Logger mFriendLogger = Logger.get("TapFriends");

    @Override // com.taptap.sdk.wrapper.TapFriendsService
    public void queryMutualList(String str, int i, final BridgeCallback bridgeCallback) {
        this.mFriendLogger.i("TapFriend queryMutualList pageSize:" + i + " cursor:" + str);
        TapFriends.queryMutualList(str, i, new TapFriendsCallback<TapFriendResult>() { // from class: com.taptap.sdk.wrapper.TapFriendsServiceImpl.1
            @Override // com.taptap.sdk.friends.TapFriendsCallback
            public void onSuccess(TapFriendResult tapFriendResult) {
                bridgeCallback.onResult(new TapFriendResponse(true, tapFriendResult.toJson().toString()).toJson());
            }

            @Override // com.taptap.sdk.friends.TapFriendsCallback
            public void onFail(Throwable th) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("error_description", th.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bridgeCallback.onResult(new TapFriendResponse(false, jSONObject.toString()).toJson());
            }
        });
    }
}
