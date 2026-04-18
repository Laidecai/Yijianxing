package com.tds.common.tracker;

import com.tds.common.tracker.constants.CommonParam;
import com.tds.common.tracker.model.ActionModel;
import com.tds.common.tracker.model.BehaviorModel;
import com.tds.common.tracker.model.LoginModel;
import com.tds.common.tracker.model.NetworkStateModel;
import com.tds.common.tracker.model.PageModel;
import com.tds.common.tracker.model.RawDataModel;
import com.tds.common.tracker.model.UserModel;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TdsTrackerManager {
    private TrackerEvent trackerEvent;
    private int trackerType;

    public static class Holder {
        public static TdsTrackerManager INSTANCE = new TdsTrackerManager();
    }

    public static void registerTracker(TdsTrackerConfig tdsTrackerConfig) {
        TdsTracker.initTdsTracker(tdsTrackerConfig);
    }

    public static TdsTrackerManager getInstance() {
        return Holder.INSTANCE;
    }

    public TdsTrackerManager withTrackerType(int i) {
        this.trackerType = i;
        return this;
    }

    public TdsTrackerManager withTrackerEvent(TrackerEvent trackerEvent) {
        this.trackerEvent = trackerEvent;
        return this;
    }

    public static class TrackerEvent {
        private PageModel pageModel = null;
        private ActionModel actionModel = null;
        private NetworkStateModel networkStateModel = null;
        private LoginModel loginModel = null;
        private UserModel meModel = null;
        private BehaviorModel behaviorModel = null;
        private RawDataModel rawDataModel = null;

        public TrackerEvent withMeModel(UserModel userModel) {
            this.meModel = userModel;
            return this;
        }

        public TrackerEvent withLoginModel(LoginModel loginModel) {
            this.loginModel = loginModel;
            return this;
        }

        public TrackerEvent withPageModel(PageModel pageModel) {
            this.pageModel = pageModel;
            return this;
        }

        public TrackerEvent withActionModel(ActionModel actionModel) {
            this.actionModel = actionModel;
            return this;
        }

        public TrackerEvent withNetworkStateModel(NetworkStateModel networkStateModel) {
            this.networkStateModel = networkStateModel;
            return this;
        }

        public TrackerEvent withBehaviorModel(BehaviorModel behaviorModel) {
            this.behaviorModel = behaviorModel;
            return this;
        }

        public TrackerEvent withRawDataModel(RawDataModel rawDataModel) {
            this.rawDataModel = rawDataModel;
            return this;
        }

        public Map<String, String> build() throws Exception {
            HashMap map = new HashMap();
            UserModel userModel = this.meModel;
            if (userModel != null) {
                map.putAll(userModel.convert());
            }
            ActionModel actionModel = this.actionModel;
            if (actionModel != null) {
                map.putAll(actionModel.convert());
            }
            PageModel pageModel = this.pageModel;
            if (pageModel != null) {
                map.putAll(pageModel.convert());
            }
            LoginModel loginModel = this.loginModel;
            if (loginModel != null) {
                map.putAll(loginModel.convert());
            }
            NetworkStateModel networkStateModel = this.networkStateModel;
            if (networkStateModel != null) {
                map.putAll(networkStateModel.convert());
            }
            BehaviorModel behaviorModel = this.behaviorModel;
            if (behaviorModel != null) {
                map.putAll(behaviorModel.convert());
            }
            RawDataModel rawDataModel = this.rawDataModel;
            if (rawDataModel != null) {
                map.putAll(rawDataModel.convert());
            }
            return map;
        }
    }

    public void track() {
        try {
            TdsTracker.get(this.trackerType).track(convert());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Map<String, String> convert() throws Exception {
        HashMap map = new HashMap();
        map.put(CommonParam.TIME, String.valueOf(System.currentTimeMillis() / 1000));
        map.putAll(this.trackerEvent.build());
        return map;
    }
}
