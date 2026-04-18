package com.tds.common.tracker.annotations;

/* JADX INFO: loaded from: classes.dex */
public interface Page {
    public static final String APPEAR_ACTION = "appear";
    public static final String DISAPPEAR_ACTION = "disappear";
    public static final String GAME_PAGE_ID = "page_game";
    public static final String TAPTAP_AUTHORIZATION_CLOUD_PLAY_ID = "taptap_authorization_cloud_play";
    public static final String TAPTAP_AUTHORIZATION_CLOUD_PLAY_NAME = "云玩";
    public static final String TAPTAP_AUTHORIZATION_TAPTAPCLIENT_PAGE_ID = "page_taptap_authorization_taptap_client";
    public static final String TAPTAP_AUTHORIZATION_TAPTAP_CLIENT_PAGE_NAME = "TapTap客户端";
    public static final String TAPTAP_AUTHORIZATION_WEB_PAGE_ID = "page_taptap_authorization_web";
    public static final String TAPTAP_AUTHORIZATION_WEB_PAGE_NAME = "TapTap网页授权";
    public static final String TAPTAP_GAME_PAGE_NAME = "游戏";

    public @interface StringAction {
    }

    public @interface StringID {
    }

    public @interface StringName {
    }
}
