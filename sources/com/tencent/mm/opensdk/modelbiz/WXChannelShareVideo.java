package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

/* JADX INFO: loaded from: classes.dex */
public class WXChannelShareVideo {

    public static class Req extends BaseReq {
        private static final int LENGTH_LIMIT = 1024;
        private static final String TAG = "MicroMsg.SDK.WXChannelShareVideo.Req";
        public String extData;
        public String title;
        public String url;
        public String videoPath;

        @Override // com.tencent.mm.opensdk.modelbase.BaseReq
        public boolean checkArgs() {
            String str;
            if (b.b(this.videoPath)) {
                str = "videoPath is null";
            } else if (this.title.length() > 1024) {
                str = "title.length too long";
            } else {
                if (this.url.length() <= 1024) {
                    return true;
                }
                str = "url.length too long";
            }
            Log.e(TAG, str);
            return false;
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseReq
        public void fromBundle(Bundle bundle) {
            super.fromBundle(bundle);
            this.videoPath = bundle.getString("_wxapi_finder_share_video_path");
            this.title = bundle.getString("_wxapi_finder_share_video_title");
            this.url = bundle.getString("_wxapi_finder_share_video_url");
            this.extData = bundle.getString("_wxapi_finder_share_video_extData");
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseReq
        public int getType() {
            return 33;
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseReq
        public void toBundle(Bundle bundle) {
            super.toBundle(bundle);
            bundle.putString("_wxapi_finder_share_video_path", this.videoPath);
            bundle.putString("_wxapi_finder_share_video_title", this.title);
            bundle.putString("_wxapi_finder_share_video_url", this.url);
            bundle.putString("_wxapi_finder_share_video_extData", this.extData);
        }
    }

    public static class Resp extends BaseResp {
        public String extMsg;

        public Resp() {
        }

        public Resp(Bundle bundle) {
            fromBundle(bundle);
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseResp
        public boolean checkArgs() {
            return true;
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseResp
        public void fromBundle(Bundle bundle) {
            super.fromBundle(bundle);
            this.extMsg = bundle.getString("_wxapi_finder_extMsg");
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseResp
        public int getType() {
            return 33;
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseResp
        public void toBundle(Bundle bundle) {
            super.toBundle(bundle);
            bundle.putString("_wxapi_finder_extMsg", this.extMsg);
        }
    }
}
