package com.taptap.services.update.bean;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UIInformation {
    private final String appInfo;
    private final List<TapUpdateFuncLink> linkList;
    private final String updateTitle;

    private UIInformation(Builder builder) {
        this.updateTitle = builder.updateTitle;
        this.appInfo = builder.appInfo;
        this.linkList = builder.linkList;
    }

    public String getUpdateTitle() {
        return this.updateTitle;
    }

    public String getAppInfo() {
        return this.appInfo;
    }

    public List<TapUpdateFuncLink> getLinkList() {
        return this.linkList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String appInfo;
        private List<TapUpdateFuncLink> linkList;
        private String updateTitle;

        private Builder() {
        }

        public Builder setUpdateTitle(String str) {
            this.updateTitle = str;
            return this;
        }

        public Builder setAppInfo(String str) {
            this.appInfo = str;
            return this;
        }

        public Builder setLinkList(List<TapUpdateFuncLink> list) {
            this.linkList = list;
            return this;
        }

        public UIInformation build() {
            return new UIInformation(this);
        }
    }
}
